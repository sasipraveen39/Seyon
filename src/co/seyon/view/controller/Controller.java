package co.seyon.view.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import co.seyon.dao.Finder;
import co.seyon.exception.InitialPasswordException;
import co.seyon.exception.UserDeActiveException;
import co.seyon.model.Login;
import co.seyon.model.User;
import co.seyon.service.SeyonService;
import co.seyon.view.model.Password;
import co.seyon.view.validator.PasswordValidator;

@org.springframework.stereotype.Controller
public class Controller {
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String USER = "user";
	public static final String LOGGEDIN = "loggedIn";
	public static final String LOGGEDINNAME = "loggedInName";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String RETURN_PAGE = "returnpage";
	
	private SeyonService service;
	private Finder finder;
	private PasswordValidator passwordValidator;
	
	public Controller() {
		service = new SeyonService();
		finder = new Finder();
		passwordValidator = new PasswordValidator();
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
	
	
	
	@InitBinder("Password")
	protected void initPasswordBinder(WebDataBinder binder){
		binder.addValidators(passwordValidator);
	}

	@RequestMapping("/")
	public String index(Model model, HttpServletRequest request) {
		Object loggedIn = request.getSession().getAttribute(LOGGEDIN);
		String nextPage  = null;
		if(loggedIn != null){
			nextPage="redirect:dashboard";
		}else {
			model.addAttribute("login", new Login());
			nextPage ="index";
		}
		return nextPage;
	}
	
	@RequestMapping("intro")
	public ModelAndView intro() {
		return new ModelAndView("intro");
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@ModelAttribute("login") Login login,
			BindingResult result, Model model, HttpServletRequest request) {
		String nextPage = "redirect:/";
		try {
			User loggedIn = service.login(login.getUsername(),
					login.getPassword());
			if (loggedIn != null) {
				request.getSession().setAttribute(LOGGEDIN, loggedIn);
				String name = loggedIn.getName();
				request.getSession().setAttribute(LOGGEDINNAME, name);
				nextPage = "redirect:dashboard";
			} else {
				model.addAttribute(ERROR_MESSAGE, "Invalid Username/Password");
			}
		} catch (UserDeActiveException e) {
			model.addAttribute(ERROR_MESSAGE, "Username has been de-activated");
		} catch (InitialPasswordException e) {
			model.addAttribute(USERNAME, login.getUsername());
			nextPage = "redirect:resetPassword";
		}
		return nextPage;
	}

	@RequestMapping("resetPassword")
	public String resetPassword(Model model, HttpServletRequest request) {
		model.addAttribute(USERNAME, request.getParameter(USERNAME));
		model.addAttribute(ERROR_MESSAGE, request.getParameter(ERROR_MESSAGE));
		return "resetpassword";
	}

	@RequestMapping("changePassword")
	public String changePassword(Model model, HttpServletRequest request) {
		User loggedIn = (User)request.getSession().getAttribute(LOGGEDIN);
		String nextPage = "redirect:/";
		if (loggedIn != null) {
			nextPage = "changepassword";
			Password pass = new Password();
			Login login = loggedIn.getLogin();
			pass.setUsername(login.getUsername());
			model.addAttribute("passwordhelper", pass);
		}
		model.addAttribute(ERROR_MESSAGE, request.getParameter(ERROR_MESSAGE));
		return nextPage;
	}
	
	
	
	@RequestMapping(value = "savePassword", method = RequestMethod.POST)
	public String savePassword(Model model, HttpServletRequest request) {
		String username = request.getParameter(USERNAME);
		String password = request.getParameter(PASSWORD);
		String nextPage = "redirect:resetPassword";
		if (service.updatePassword(username, password)) {
			try {
				User loggedIn = service.login(username, password);
				if (loggedIn != null) {
					request.getSession().setAttribute(LOGGEDIN, loggedIn);
					String name = loggedIn.getName();
					request.getSession().setAttribute(LOGGEDINNAME, name);
					nextPage = "redirect:dashboard";
				}
			} catch (UserDeActiveException | InitialPasswordException e) {
			}
		} else {
			model.addAttribute(USERNAME, username);
			model.addAttribute(ERROR_MESSAGE,
					"Problem while updating password. Please contact helpdesk");
		}
		return nextPage;
	}

	@RequestMapping("dashboard")
	public String dashboard(Model model, HttpServletRequest request) {
		User loggedIn = (User) request.getSession().getAttribute(LOGGEDIN);
		String nextPage = "redirect:/";
		if (loggedIn != null) {
			Login login = loggedIn.getLogin();
			switch (login.getUserType()) {
			case ADMIN:
				nextPage = "admindashboard";
				break;
			case VENDOR:
				nextPage = "vendordashboard";
				break;
			default:
				nextPage = "developerdashboard";
			}
		}
		return nextPage;
	}

		
	@RequestMapping(value = "saveChangedPassword", method = RequestMethod.POST)
	public String saveChangedPassword(@ModelAttribute("passwordhelper") Password password,
			BindingResult result, Model model, HttpServletRequest request) {
		String nextPage = "redirect:/";
		Object loggedIn = request.getSession().getAttribute(LOGGEDIN);
		if (loggedIn != null) {
			nextPage="changepassword";
			passwordValidator.validate(password, result);
			if(!result.hasErrors()){
				String username = password.getUsername();
				if (service.updatePassword(username, password.getNewPassword())) {
					try {
						User loggedInNow = (User)service.login(username, password.getNewPassword());
						if (loggedInNow != null) {
							request.getSession().setAttribute(LOGGEDIN, loggedInNow);
							String name = loggedInNow.getName();
							request.getSession().setAttribute(LOGGEDINNAME, name);
							nextPage = "redirect:dashboard";
						}
					} catch (UserDeActiveException | InitialPasswordException e) {
					}
				} else {
					model.addAttribute(USERNAME, username);
					model.addAttribute(ERROR_MESSAGE,
							"Problem while updating password. Please contact helpdesk");
				}
			}
		}
		return nextPage;
	}
	
	@RequestMapping(value = "logout")
	public String logout(Model model, HttpServletRequest request) {
		String nextPage = "redirect:/";
		User loggedIn = (User)request.getSession().getAttribute(LOGGEDIN);
		if (loggedIn != null) {
				Login login = loggedIn.getLogin();
				service.logOut(login);
				request.getSession().invalidate();
				nextPage = "redirect:/";
		}
		return nextPage;
	}
	

	@RequestMapping(value = "removeLogin", method = RequestMethod.GET)
	public @ResponseBody
	boolean removeLogin(@RequestParam String username) {
		return service.deactivateLogin(username);
	}

	@RequestMapping(value = "activateLogin", method = RequestMethod.GET)
	public @ResponseBody
	boolean activateLogin(@RequestParam String username) {
		return service.activateLogin(username);
	}

	
	@RequestMapping(value = "isLoggedIn", method = RequestMethod.GET)
	public @ResponseBody
	boolean isLoggedIN(HttpServletRequest request) {
		boolean result = false;
		User loggedIn = (User)request.getSession().getAttribute(LOGGEDIN);
		if (loggedIn != null) {
			Login login = loggedIn.getLogin();
			result = login.getLoggedIn();
		}
		return result;
	}
}
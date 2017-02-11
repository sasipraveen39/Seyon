package co.seyon.view.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.seyon.cache.Cache;
import co.seyon.dao.Finder;
import co.seyon.enums.AddressType;
import co.seyon.enums.UserType;
import co.seyon.exception.InitialPasswordException;
import co.seyon.exception.UserDeActiveException;
import co.seyon.model.Address;
import co.seyon.model.Login;
import co.seyon.model.Project;
import co.seyon.model.User;
import co.seyon.service.SeyonService;
import co.seyon.view.model.AccountSearch;
import co.seyon.view.model.AccountSearchResult;
import co.seyon.view.model.AjaxResponse;
import co.seyon.view.model.Password;
import co.seyon.view.model.ProjectSearch;
import co.seyon.view.model.ProjectSearchResult;
import co.seyon.view.model.Views;
import co.seyon.view.validator.PasswordValidator;

import com.fasterxml.jackson.annotation.JsonView;

@org.springframework.stereotype.Controller
public class Controller {
	public static final String LOGIN = "login";
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String USER = "user";
	public static final String LOGGEDIN = "loggedIn";
	public static final String LOGGEDINNAME = "loggedInName";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String RETURN_PAGE = "returnpage";

	private static final String VENDOR = UserType.VENDOR.toString()
			.toLowerCase();
	private static final String ADMIN = UserType.ADMIN.toString().toLowerCase();
	private static final String CLIENT = UserType.CLIENT.toString()
			.toLowerCase();

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
	protected void initPasswordBinder(WebDataBinder binder) {
		binder.addValidators(passwordValidator);
	}

	@RequestMapping("/")
	public String index(Model model, HttpServletRequest request) {
		Object loggedIn = request.getSession().getAttribute(LOGGEDIN);
		String nextPage = null;
		if (loggedIn != null) {
			nextPage = "redirect:dashboard";
		} else {
			Login login = null;
			if (model.containsAttribute(LOGIN)) {
				login = (Login) model.asMap().get(LOGIN);
			} else {
				login = new Login();
			}
			login.setPassword(null);
			model.addAttribute(LOGIN, login);
			nextPage = "index";
		}
		return nextPage;
	}

	@RequestMapping("intro")
	public ModelAndView intro() {
		return new ModelAndView("intro");
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@ModelAttribute(LOGIN) Login login,
			BindingResult result, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
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
				redirectAttributes.addFlashAttribute(LOGIN, login);
				redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
						"Invalid Username/Password");
			}
		} catch (UserDeActiveException e) {
			redirectAttributes.addFlashAttribute(LOGIN, login);
			redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
					"Username has been de-activated");
		} catch (InitialPasswordException e) {
			redirectAttributes.addFlashAttribute(USERNAME, login.getUsername());
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
		User loggedIn = (User) request.getSession().getAttribute(LOGGEDIN);
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

	@RequestMapping(value = "saveChangedPassword", method = RequestMethod.POST)
	public String saveChangedPassword(
			@ModelAttribute("passwordhelper") Password password,
			BindingResult result, Model model, HttpServletRequest request) {
		String nextPage = "redirect:/";
		Object loggedIn = request.getSession().getAttribute(LOGGEDIN);
		if (loggedIn != null) {
			nextPage = "changepassword";
			passwordValidator.validate(password, result);
			if (!result.hasErrors()) {
				String username = password.getUsername();
				if (service.updatePassword(username, password.getNewPassword())) {
					try {
						User loggedInNow = (User) service.login(username,
								password.getNewPassword());
						if (loggedInNow != null) {
							request.getSession().setAttribute(LOGGEDIN,
									loggedInNow);
							String name = loggedInNow.getName();
							request.getSession().setAttribute(LOGGEDINNAME,
									name);
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
		User loggedIn = (User) request.getSession().getAttribute(LOGGEDIN);
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
		User loggedIn = (User) request.getSession().getAttribute(LOGGEDIN);
		if (loggedIn != null) {
			Login login = loggedIn.getLogin();
			result = login.getLoggedIn();
		}
		return result;
	}

	@ResponseBody
	@JsonView(Views.Public.class)
	@RequestMapping(value = "seacrhAccount", method = RequestMethod.POST)
	public AjaxResponse searchAccount(@RequestBody AccountSearch accountSearch,
			HttpServletRequest request) {
		User loggedIn = (User) request.getSession().getAttribute(LOGGEDIN);
		AjaxResponse result = new AjaxResponse();
		if (loggedIn != null) {
			List<User> users = finder.findUsers(
					accountSearch.getAccountNumber(),
					accountSearch.getAccountName(),
					accountSearch.getMobileNumber(), accountSearch.getEmail());
			if (users.size() > 0) {
				List<AccountSearchResult> searchResult = new ArrayList<>();
				for (User u : users) {
					AccountSearchResult accountSearchResult = new AccountSearchResult();
					accountSearchResult.setAccountName(u.getName());
					accountSearchResult.setAccountNumber(u.getAccountNumber());
					accountSearchResult.setEmail(u.getEmail());
					accountSearchResult.setMobileNumber(u.getMobileNumber());
					searchResult.add(accountSearchResult);
				}
				result.setResult(searchResult);
				result.setCode("200");
				result.setMsg(searchResult.size() + " Accounts found");
			} else {
				result.setCode("204");
				result.setMsg("No Account found!");
			}
		}
		return result;
	}

	@RequestMapping("dashboard")
	public String dashboard(Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		return navigatePage(user, "_dashboard", request);
	}

	@RequestMapping("account")
	public String account(Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		if (user != null) {
			Login login = user.getLogin();
			switch (login.getUserType()) {
			case ADMIN:
			case VENDOR:
				List<String> lruAccounts = Cache.getLRUAccounts(user.getIduser()+"");
				List<AccountSearchResult> accountSearchResults = new ArrayList<>();
				if(lruAccounts != null){
					for(String acctNum : lruAccounts){
						List<User> accounts = finder.findUsers(acctNum, null, null, null);
						if(accounts.size() > 0){
							User account = accounts.get(0);
							AccountSearchResult accountSearchResult = new AccountSearchResult();
							accountSearchResult.setAccountName(account.getName());
							accountSearchResult.setAccountNumber(account.getAccountNumber());
							accountSearchResult.setMobileNumber(account.getMobileNumber());
							accountSearchResult.setEmail(account.getEmail());
							accountSearchResults.add(accountSearchResult);
						}
					}	
				}
				model.addAttribute("recentAccounts", accountSearchResults);
				break;
			case CLIENT:
				break;
			}
		}
		
		return navigatePage(user, "_account", request);
	}

	@RequestMapping("project")
	public String project(Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		if (user != null) {
			Login login = user.getLogin();
			switch (login.getUserType()) {
			case ADMIN:
			case VENDOR:
				List<String> lruProjects = Cache.getLRUProjects(user.getIduser()+"");
				List<ProjectSearchResult> projectSearchResults = new ArrayList<>();
				if(lruProjects != null){
					for(String acctNum : lruProjects){
						List<Project> projects = finder.findProjects(acctNum, null, null, null);
						if(projects.size() > 0){
							Project p = projects.get(0);
							ProjectSearchResult projectSearchResult = new ProjectSearchResult();
							projectSearchResult.setTitle(p.getTitle());
							projectSearchResult.setProjectNumber(p.getProjectNumber());
							projectSearchResult.setType(p.getProjectType().getValue());
							projectSearchResult.setAddress(p.getAddress().getCity() +" - " + p.getAddress().getPincode());
							projectSearchResults.add(projectSearchResult);
						}
					}	
				}
				model.addAttribute("recentProjects", projectSearchResults);
				break;
			case CLIENT:
				break;
			}
		}
		
		return navigatePage(user, "_project", request);
	}
	
	@RequestMapping("retrieveAccount")
	public String retrieveAccount(@RequestParam String num, Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		if (user != null) {
			Login login = user.getLogin();
			switch (login.getUserType()) {
			case ADMIN:
			case VENDOR:
				Cache.addToLRUAccounts(user.getIduser()+"", num);
				User account = finder.findUsers(num, null, null, null).get(0);
				model.addAttribute("account", account);
				model.addAttribute("canEditAccount", true);
				break;
			case CLIENT:
				model.addAttribute("account", user);
				model.addAttribute("canEditAccount", false);
				break;
			}
		}
		return navigatePage(user, "_accountdetail", request);
	}

	@RequestMapping("editAccount")
	public String editAccount(@RequestParam String num, Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		String nextPage = "redirect:/";
		if (user != null) {
			nextPage = "accountcreate";
			Login login = user.getLogin();
			switch (login.getUserType()) {
			case ADMIN:
			case VENDOR:
				User account = finder.findUsers(num, null, null, null).get(0);
				model.addAttribute("accountLogin", account.getLogin());
				model.addAttribute("canEdit", true);
				model.addAttribute("isNewAccount", false);
				model.addAttribute("accountNumber",account.getAccountNumber());
				break;
			case CLIENT:
				model.addAttribute("accountLogin", login);
				model.addAttribute("canEdit", true);
				model.addAttribute("isNewAccount", false);
				model.addAttribute("accountNumber",login.getUser().getAccountNumber());
				break;
			}
		}
		return nextPage;
	}
	
	@RequestMapping("newAccount")
	public String CreateNewAccount(Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		String nextPage = "redirect:/";
		if (user != null) {
			nextPage = "accountcreate";
			Login accountLogin = new Login();
			User account = new User();
			Address address = new Address();
			account.setAddress(address);
			accountLogin.setUser(account);
			model.addAttribute("accountLogin", accountLogin);
			model.addAttribute("canEdit", true);
			model.addAttribute("isNewAccount", true);
		}
		return nextPage;
	}
	
	@RequestMapping(value="submitaccount", method = RequestMethod.POST)
	public String submitAccount(@ModelAttribute("accountLogin") Login login, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		String nextPage = "redirect:/";
		if (user != null) {
			if(service.createNewUser(login)){
				nextPage = "redirect:retrieveAccount?num="+login.getUser().getAccountNumber();
			}
		}
		return nextPage;
	}
	
	@RequestMapping(value="updateaccount", method = RequestMethod.POST)
	public String updateAccount(@ModelAttribute("accountLogin") Login login, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		String nextPage = "redirect:/";
		if (user != null) {
			if(service.updateUser(login.getUser())){
				nextPage = "redirect:retrieveAccount?num="+login.getUser().getAccountNumber();
			}
		}
		return nextPage;
	}
	
	
	@ResponseBody
	@JsonView(Views.Public.class)
	@RequestMapping(value = "seacrhProject", method = RequestMethod.POST)
	public AjaxResponse searchProject(@RequestBody ProjectSearch projectSearch,
			HttpServletRequest request) {
		User loggedIn = (User) request.getSession().getAttribute(LOGGEDIN);
		AjaxResponse result = new AjaxResponse();
		if (loggedIn != null) {
			List<Project> projects = finder.findProjects(
					projectSearch.getProjectNumber(),
					projectSearch.getTitle(),
					projectSearch.getType(), projectSearch.getPincode());
			if (projects.size() > 0) {
				List<ProjectSearchResult> searchResult = new ArrayList<>();
				for (Project p : projects) {
					ProjectSearchResult projectSearchResult = new ProjectSearchResult();
					projectSearchResult.setTitle(p.getTitle());
					projectSearchResult.setProjectNumber(p.getProjectNumber());
					projectSearchResult.setType(p.getProjectType().getValue());
					projectSearchResult.setAddress(p.getAddress().getCity() +" - " + p.getAddress().getPincode());
					searchResult.add(projectSearchResult);
				}
				result.setResult(searchResult);
				result.setCode("200");
				result.setMsg(searchResult.size() + " Projects found");
			} else {
				result.setCode("204");
				result.setMsg("No Project found!");
			}
		}
		return result;
	}

	
	@RequestMapping("retrieveProject")
	public String retrieveProject(@RequestParam String num, Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		String prevURL = request.getHeader("Referer");
		if(StringUtils.isNotBlank(prevURL)){
			prevURL = prevURL.substring(prevURL.lastIndexOf("/")+1, prevURL.length());
			if(!"project".equalsIgnoreCase(prevURL)){
				model.addAttribute("prevURL", prevURL);	
			}
		}
		if (user != null) {
			Login login = user.getLogin();
			Project project = null;
			switch (login.getUserType()) {
			case ADMIN:
			case VENDOR:
				Cache.addToLRUProjects(user.getIduser()+"", num);
				project = finder.findProjects(num, null, null, null).get(0);
				model.addAttribute("project", project);
				model.addAttribute("canEditProject", true);
				break;
			case CLIENT:
				for(Project p : user.getProjects()){
					if(p.getProjectNumber().equalsIgnoreCase(num)){
						project = p;
						break;
					}
				}
				if(project == null){
					project = user.getProjects().get(0);
				}
				model.addAttribute("project", project);
				model.addAttribute("canEditProject", false);
				break;
			}
		}
		return navigatePage(user, "_projectdetail", request);
	}
	
	private String navigatePage(User user, String pageSuffix,
			HttpServletRequest request) {
		String nextPage = "redirect:/";
		if (user != null) {
			Login login = user.getLogin();
			switch (login.getUserType()) {
			case ADMIN:
				nextPage = ADMIN + pageSuffix;
				break;
			case VENDOR:
				nextPage = VENDOR + pageSuffix;
				break;
			case CLIENT:
				nextPage = CLIENT + pageSuffix;
				break;
			}
		}
		return nextPage;
	}
}
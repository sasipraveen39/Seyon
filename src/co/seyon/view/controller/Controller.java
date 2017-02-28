package co.seyon.view.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.seyon.cache.Cache;
import co.seyon.dao.Finder;
import co.seyon.enums.BillStatus;
import co.seyon.enums.BillType;
import co.seyon.enums.DocumentType;
import co.seyon.enums.DrawingStatus;
import co.seyon.enums.DrawingType;
import co.seyon.enums.ProjectType;
import co.seyon.enums.UserType;
import co.seyon.exception.InitialPasswordException;
import co.seyon.exception.UserDeActiveException;
import co.seyon.model.Address;
import co.seyon.model.Bill;
import co.seyon.model.Document;
import co.seyon.model.Drawing;
import co.seyon.model.Login;
import co.seyon.model.Project;
import co.seyon.model.User;
import co.seyon.service.SeyonService;
import co.seyon.util.EnvironmentUtil;
import co.seyon.view.model.AccountSearch;
import co.seyon.view.model.AccountSearchResult;
import co.seyon.view.model.AjaxResponse;
import co.seyon.view.model.Item;
import co.seyon.view.model.Password;
import co.seyon.view.model.ProjectSearch;
import co.seyon.view.model.ProjectSearchResult;
import co.seyon.view.model.Views;
import co.seyon.view.validator.PasswordValidator;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@RequestMapping("newDrawing")
	public String CreateNewDrawing(@RequestParam String num, Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		String nextPage = "redirect:/";
		if (user != null) {
			nextPage = "drawingcreate";
			Drawing drawing = new Drawing();
			drawing.setStatus(DrawingStatus.DRAFT);
			drawing.setDocument(new Document());
			model.addAttribute("canEdit", true);
			model.addAttribute("isNew", true);
			model.addAttribute("projectNumber", num);
			model.addAttribute("drawing", drawing);
			model.addAttribute("drawingTypes", DrawingType.values());
			model.addAttribute("statuses", DrawingStatus.values());
		}
		return nextPage;
	}
	
	@RequestMapping("newLegalDocument")
	public String CreateNewLegalDocument(@RequestParam String num, Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		String nextPage = "redirect:/";
		if (user != null) {
			nextPage = "legaldocumentcreate";
			Document document = new Document();
			model.addAttribute("canEdit", true);
			model.addAttribute("isNew", true);
			model.addAttribute("projectNumber", num);
			model.addAttribute("legalDocument", document);
		}
		return nextPage;
	}
	
	@RequestMapping("newBill")
	public String CreateNewBill(@RequestParam String num, Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		String nextPage = "redirect:/";
		if (user != null) {
			nextPage = "billcreate";
			Bill bill = new Bill();
			bill.setBillStatus(BillStatus.DRAFT);
			bill.setBillDate(new Date());
			bill.setDocument(new Document());
			model.addAttribute("canEdit", true);
			model.addAttribute("isNew", true);
			model.addAttribute("projectNumber", num);
			model.addAttribute("bill", bill);
			model.addAttribute("billTypes",BillType.values());
			model.addAttribute("statuses",BillStatus.values());
		}
		return nextPage;
	}
	
	
	@RequestMapping("newProject")
	public String CreateNewProject(@RequestParam String num, Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		String nextPage = "redirect:/";
		if (user != null) {
			nextPage = "projectcreate";
			User accUser = finder.findUsers(num, null, null, null).get(0);
			Project project = new Project();
			project.setUser(accUser);
			project.setAddress(new Address());
			model.addAttribute("canEdit", true);
			model.addAttribute("isNew", true);
			model.addAttribute("accountNumber", num);
			model.addAttribute("project", project);
			model.addAttribute("projectTypes",ProjectType.values());
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
	
	@RequestMapping(value="submitproject", method = RequestMethod.POST)
	public String submitProject(@ModelAttribute("project") Project project, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		String nextPage = "redirect:/";
		if (user != null) {
			if(service.createNewProject(project)){
				nextPage = "redirect:retrieveProject?num="+project.getProjectNumber();
			}
		}
		return nextPage;
	}
	
	@RequestMapping(value="submitlegaldocument", method = RequestMethod.POST)
	public String submitLegalDocument(@ModelAttribute("legalDocument") Document document,
			@RequestParam(value = "projNumber") String num,
			@RequestParam(value = "documentFile", required = false) MultipartFile documentFile,
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		String nextPage = "redirect:/";
		if (user != null) {
			try {
				Project project = finder.findProjects(num, null, null, null)
						.get(0);
				String docFileName = EnvironmentUtil.getDocumentPath(project
						.getUser().getAccountNumber(), project
						.getProjectNumber(), documentFile.getOriginalFilename(),
						true);
				File serverDocFile = new File(docFileName);
				if (!serverDocFile.getParentFile().exists()) {
					serverDocFile.getParentFile().mkdirs();
				}
				FileUtils.copyInputStreamToFile(documentFile.getInputStream(),
						serverDocFile);
				document.setProject(project);
				document.setDocumentType(DocumentType.CONTRACT);
				document.setFileLocation(EnvironmentUtil.getExposedDocumentPath(project.getUser().getAccountNumber(),
						project.getProjectNumber(), serverDocFile.getName()));
				if(service.createDocument(document)){
					nextPage = "redirect:retrieveProject?num=" + num;	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return nextPage;
	}
	
	@RequestMapping(value="submitdrawing", method = RequestMethod.POST)
	public String submitDrawing(@ModelAttribute("drawing") Drawing drawing,
			@RequestParam(value = "projNumber") String num,
			@RequestParam(value = "drawingFile", required = false) MultipartFile drawingFile,
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		String nextPage = "redirect:/";
		if (user != null) {
			try {
				Project project = finder.findProjects(num, null, null, null)
						.get(0);
				String docFileName = EnvironmentUtil.getDocumentPath(project
						.getUser().getAccountNumber(), project
						.getProjectNumber(), drawingFile.getOriginalFilename(),
						true);
				File serverDocFile = new File(docFileName);
				if (!serverDocFile.getParentFile().exists()) {
					serverDocFile.getParentFile().mkdirs();
				}
				FileUtils.copyInputStreamToFile(drawingFile.getInputStream(),
						serverDocFile);
				drawing.setProject(project);
				drawing.getDocument().setProject(project);
				drawing.getDocument().setDocumentType(DocumentType.DRAWING);
				drawing.getDocument().setFileLocation(EnvironmentUtil.getExposedDocumentPath(project.getUser().getAccountNumber(),
						project.getProjectNumber(), serverDocFile.getName()));
				if(service.createDrawing(drawing)){
					nextPage = "redirect:retrieveProject?num=" + num;	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return nextPage;
	}
	
	@RequestMapping(value="submitbill", method = RequestMethod.POST)
	public String submitBill(@ModelAttribute("bill") Bill bill,
			@RequestParam(value = "projNumber") String num,
			@RequestParam(value = "billFile", required = false) MultipartFile billFile,
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		String nextPage = "redirect:/";
		if (user != null) {
			try {
				Project project = finder.findProjects(num, null, null, null)
						.get(0);
				String docFileName = EnvironmentUtil.getDocumentPath(project
						.getUser().getAccountNumber(), project
						.getProjectNumber(), billFile.getOriginalFilename(),
						true);
				File serverDocFile = new File(docFileName);
				if (!serverDocFile.getParentFile().exists()) {
					serverDocFile.getParentFile().mkdirs();
				}
				FileUtils.copyInputStreamToFile(billFile.getInputStream(),
						serverDocFile);
				bill.setProject(project);
				bill.getDocument().setProject(project);
				bill.getDocument().setDocumentType(DocumentType.BILL);
				bill.getDocument().setFileLocation(EnvironmentUtil.getExposedDocumentPath(project.getUser().getAccountNumber(),
						project.getProjectNumber(), serverDocFile.getName()));
				if(service.createBill(bill)){
					nextPage = "redirect:retrieveProject?num=" + num;	
				}
			} catch (IOException e) {
				e.printStackTrace();
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
	
	@RequestMapping("retrieveLegalDocument")
	public String retrieveLegalDocument(@RequestParam String num, Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		if (user != null) {
			Login login = user.getLogin();
			Document document = null;
			switch (login.getUserType()) {
			case ADMIN:
			case VENDOR:
				document = finder.findDocuments(num, null).get(0);
				break;
			case CLIENT:
				for(Project p : user.getProjects()){
					for(Document d : p.getDocuments()){
						if(num.equalsIgnoreCase(d.getDocumentNumber())){
							document = d;
							break;	
						}		
					}
				}
				if(document == null){
					document = user.getProjects().get(0).getDocuments().get(0);
				}
				break;
			}
			model.addAttribute("document", document);
			model.addAttribute("canEdit", true);
		}
		return navigatePage(user, "_legaldocumentdetail", request);
	}
	
	@RequestMapping("retrieveBill")
	public String retrieveBill(@RequestParam String num, Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		if (user != null) {
			Login login = user.getLogin();
			Bill bill = null;
			switch (login.getUserType()) {
			case ADMIN:
			case VENDOR:
				bill = finder.findBills(num, null).get(0);
				break;
			case CLIENT:
				for(Project p : user.getProjects()){
					for(Bill b : p.getBills()){
						if(num.equalsIgnoreCase(b.getBillNumber())){
							bill = b;
							break;	
						}		
					}
				}
				if(bill == null){
					bill = user.getProjects().get(0).getBills().get(0);
				}
				break;
			}
			model.addAttribute("billDetail", bill);
			model.addAttribute("canEdit", true);
		}
		return navigatePage(user, "_billdetail", request);
	}
	
	
	@RequestMapping("retrieveDrawing")
	public String retrieveDrawing(@RequestParam String num, Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGEDIN);
		if (user != null) {
			Login login = user.getLogin();
			Drawing drawing = null;
			switch (login.getUserType()) {
			case ADMIN:
			case VENDOR:
				drawing = finder.findDrawings(num).get(0);
				break;
			case CLIENT:
				for(Project p : user.getProjects()){
					for(Drawing d : p.getDrawings()){
						if(num.equalsIgnoreCase(d.getDrawingNumber())){
							drawing = d;
							break;	
						}		
					}
				}
				if(drawing == null){
					drawing = user.getProjects().get(0).getDrawings().get(0);
				}
				break;
			}
			model.addAttribute("drawing", drawing);
			model.addAttribute("canEdit", true);
		}
		return navigatePage(user, "_drawingdetail", request);
	}
	
	@RequestMapping(value = "images/{accountNumber}/{projectNumber}/{imageName:.+}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Resource> getImageAsResource(@PathVariable String accountNumber, @PathVariable String projectNumber, @PathVariable String imageName) {
	    HttpHeaders headers = new HttpHeaders();
	    Resource resource = 
	      new FileSystemResource(EnvironmentUtil.getImagePath(accountNumber, projectNumber, imageName, false));
	    return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "documents/{accountNumber}/{projectNumber}/{documentName:.+}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Resource> getDocumentAsResource(@PathVariable String accountNumber, @PathVariable String projectNumber, @PathVariable String documentName) {
	    HttpHeaders headers = new HttpHeaders();
	    Resource resource = 
	      new FileSystemResource(EnvironmentUtil.getDocumentPath(accountNumber, projectNumber, documentName, false));
	    return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "uploadImage", method = RequestMethod.POST)
	public String uploadImage(@RequestParam("imageInfo") String imageInfo, 
			@RequestParam("imageFile") MultipartFile imageFile,
			HttpServletRequest request) {
		User loggedIn = (User) request.getSession().getAttribute(LOGGEDIN);
		String fileSaved = "Image not uploaded";
		if (loggedIn != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				co.seyon.view.model.Document info =  mapper.readValue(imageInfo, co.seyon.view.model.Document.class);
				String imageFileName = EnvironmentUtil.getImagePath(info.getAccountNumber(), 
						info.getProjectNumber(), imageFile.getOriginalFilename(), true);
				File serverImageFile = new File(imageFileName);
				if(!serverImageFile.getParentFile().exists()){
					serverImageFile.getParentFile().mkdirs();
				}
				FileUtils.copyInputStreamToFile(imageFile.getInputStream(), serverImageFile);
				Document document = new Document();
				document.setName(info.getName());
				document.setDescription(info.getDescription());
				document.setDocumentType(DocumentType.IMAGE);
				document.setFileLocation(EnvironmentUtil.getExposedImagePath(info.getAccountNumber(), info.getProjectNumber(), serverImageFile.getName()));
				service.createNewDocument(info.getProjectNumber(), document);
				fileSaved = "Image uploaded";
			} catch (IOException e) {
				fileSaved = e.getMessage();
			}
		}
		return fileSaved;
	}
	
	@ResponseBody
	@RequestMapping(value = "deleteItems", method = RequestMethod.POST)
	public String deleteImage(@RequestBody Item item, HttpServletRequest request) {
		User loggedIn = (User) request.getSession().getAttribute(LOGGEDIN);
		String fileDeleted = "Unable to delete";
		if (loggedIn != null) {
			String type = item.getType();
			if(type.equalsIgnoreCase("Image")){
				if(service.deleteDocuments(item.getIds())){
					fileDeleted = "Delete successful";
				}	
			} else if(type.equalsIgnoreCase("Document")){
				if(service.deleteDocuments(item.getIds())){
					fileDeleted = "Delete successful";
				}
			} else if(type.equalsIgnoreCase("Bill")){
				if(service.deleteBills(item.getIds())){
					fileDeleted = "Delete successful";
				}
			} else if(type.equalsIgnoreCase("Drawing")){
				if(service.deleteDrawings(item.getIds())){
					fileDeleted = "Delete successful";
				}
			}
			
		}
		return fileDeleted;
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
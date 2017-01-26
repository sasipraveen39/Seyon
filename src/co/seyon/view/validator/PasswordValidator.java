package co.seyon.view.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import co.seyon.model.Login;
import co.seyon.service.SeyonService;
import co.seyon.view.model.Password;

public class PasswordValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		return Password.class.equals(arg0);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Password password = (Password)obj;
		SeyonService service = new SeyonService();
		if(password != null){
			Login login = service.findByUsername(password.getUsername());
			if(login != null){
				if(!service.isPasswordMatch(login, password.getOldPassword())){
					errors.rejectValue("oldPassword", "", "Invalid current password.");	
				}else if(!password.getNewPassword().equals(password.getConfirmNewPassword())){
					errors.rejectValue("newPassword", "", "New password does not match.");
				}
			}
		}
	}
}

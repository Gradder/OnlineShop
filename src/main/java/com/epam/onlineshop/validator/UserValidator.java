package com.epam.onlineshop.validator;

import com.epam.onlineshop.entities.User;
import com.epam.onlineshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

  private static final String USERNAME = "username";
  public static final String NOT_EMPTY = "NotEmpty";
  private final UserService userService;

  @Override
  public boolean supports(Class<?> aClass) {
    return User.class.equals(aClass);
  }

  @Override
  public void validate(Object object, Errors errors) {
    if (object == null) {
      return;
    }

    User user = (User) object;

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, USERNAME, NOT_EMPTY);
    if (user.getUsername().length() < 1 || user.getUsername().length() > 32) {
      errors.rejectValue(USERNAME, "Size.userForm.username");
    }
    if (userService.findByUsername(user.getUsername()) != null) {
      errors.rejectValue(USERNAME, "Duplicate.userForm.username");
    }

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", NOT_EMPTY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", NOT_EMPTY);
    if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
      errors.rejectValue("password", "Size.userForm.password");
    }
  }
}

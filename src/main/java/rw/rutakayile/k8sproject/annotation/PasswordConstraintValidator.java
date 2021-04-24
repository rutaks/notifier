package rw.rutakayile.k8sproject.annotation;

import org.passay.*;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

@Component
public class PasswordConstraintValidator implements ConstraintValidator<rw.rutakayile.k8sproject.annotation.ValidPassword, String> {

  /**
   * There is no other constraints to be added that's why is empty.
   *
   * @param constraintAnnotation is for adding adding other constraints.
   */
  @Override
  public void initialize(rw.rutakayile.k8sproject.annotation.ValidPassword constraintAnnotation) {}

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    PasswordValidator validator =
        new PasswordValidator(
            Arrays.asList(

                // at least 8 characters
                new LengthRule(8, 30),

                // at least one lower-case or upper-case character
                new CharacterRule(EnglishCharacterData.Alphabetical, 1),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // no whitespace
                new WhitespaceRule()));

    RuleResult result = validator.validate(new PasswordData(password));

    if (result.isValid()) {
      return true;
    }

    List<String> messages = validator.getMessages(result);
    String messageTemplate = String.join(",", messages);
    context
        .buildConstraintViolationWithTemplate(messageTemplate)
        .addConstraintViolation()
        .disableDefaultConstraintViolation();
    return false;
  }
}

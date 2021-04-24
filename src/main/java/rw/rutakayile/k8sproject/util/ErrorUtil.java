package rw.rutakayile.k8sproject.util;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import rw.rutakayile.k8sproject.exception.ValidationException;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ErrorUtil {
    public static void checkForErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            String messages =
                    bindingResult.getAllErrors().stream()
                            .map(
                                    error -> {
                                        String fieldName = ((FieldError) error).getField();
                                        String errorMessage = error.getDefaultMessage();
                                        errors.put(fieldName, errorMessage);
                                        return error.getDefaultMessage();
                                    })
                            .collect(Collectors.joining("::"));
            throw new ValidationException(messages, errors);
        }
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}

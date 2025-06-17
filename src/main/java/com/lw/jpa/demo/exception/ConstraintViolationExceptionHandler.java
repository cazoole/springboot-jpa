package com.lw.jpa.demo.exception;

import org.springframework.util.Assert;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * @author wei.liuw
 * @date 2019/5/31
 */
public class ConstraintViolationExceptionHandler {

    public static String getCauseMessage(ConstraintViolationException exception) {
        Assert.notNull(exception, "Exception should not be null!");
        return exception.getConstraintViolations()
                .stream()
                .map(cv -> null == cv ? "null" : cv.getPropertyPath() + ":" + cv.getMessage())
                .collect(Collectors.joining(" | "));
    }
}

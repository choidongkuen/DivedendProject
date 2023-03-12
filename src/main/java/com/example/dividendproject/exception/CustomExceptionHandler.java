package com.example.dividendproject.exception;

import com.example.dividendproject.web.CompanyController;
import com.example.dividendproject.web.FinanceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice(basePackageClasses = {CompanyController.class, FinanceController.class})
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundCompanyException.class)
    public ResponseEntity<ErrorMessage> NotFoundCompanyExceptionHandler(
            Exception exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));

    }

    @ExceptionHandler(AlreadyCompanyExistsException.class)
    public ResponseEntity<ErrorMessage> AlreadyCompanyExistsExceptionHandler(
            Exception exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }
}

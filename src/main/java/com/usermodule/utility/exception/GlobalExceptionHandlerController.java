package com.usermodule.utility.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @Bean
    public ErrorAttributes errorAttributes(){
        //to hide exception field in the return object
        return new DefaultErrorAttributes(){

            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
                Map<String,Object> errorAttributes = super.getErrorAttributes(webRequest,includeStackTrace);
                errorAttributes.remove("exception");
                return errorAttributes;
            }
        };
    }

    @ExceptionHandler(CustomException.class)
    public void handleCustomException(HttpServletResponse response,CustomException ex)throws IOException{
        response.sendError(ex.getHttpStatus().value(), ex.getMessage());
    }


    @ExceptionHandler(AccessDeniedException.class)
    public void handleDeniedException(HttpServletResponse response)throws IOException{
        response.sendError(HttpStatus.FORBIDDEN.value(),"Access Denied");
    }

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse response)throws IOException{
        response.sendError(HttpStatus.BAD_REQUEST.value(),"Something went wrong");
    }
}

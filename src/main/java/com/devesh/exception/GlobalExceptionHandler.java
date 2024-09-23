package com.devesh.exception;

import com.devesh.utilities.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex, WebRequest webRequest){
        return new ResponseEntity<>(new GenericResponse(ex.getMessage(), false,HttpStatus.BAD_REQUEST.value(), null), HttpStatus.OK);
    }

    @ExceptionHandler(CodeException.class)
    public ResponseEntity<GenericResponse<Object>> handleCodeException(CodeException ex) {
        // Handle the CodeException and return a custom response
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GenericResponse<>(ex.getMessage(), false, ErrorCode.COMMON.getCode(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Object>> handleGeneralException(Exception ex) {
        // Handle general exceptions
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GenericResponse<>("An error occurred while processing the request", false, HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
    }


}

package com.uol.compasso.productms.exception;


import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.uol.compasso.productms.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.EOFException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandlingControllerAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> prodcutNotFoundException(ProductNotFoundException ex) throws Exception {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(404);
        errorDTO.setMessage(ex.getMessage());
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    @ExceptionHandler(ParamsInvalidException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> ParamsInvalidException(ParamsInvalidException ex) throws Exception {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(400);
        errorDTO.setMessage(ex.getMessage());
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> methodArgumentInvalid(MethodArgumentNotValidException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(400);
        if (ex.getFieldError() != null) {
            errorDTO.setMessage(ex.getFieldError().getDefaultMessage());
        } else {
            errorDTO.setMessage(ex.getMessage());
        }
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }


}

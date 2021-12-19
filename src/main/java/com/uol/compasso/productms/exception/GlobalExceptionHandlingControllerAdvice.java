package com.uol.compasso.productms.exception;


import com.uol.compasso.productms.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;


@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandlingControllerAdvice {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandlingControllerAdvice.class.getName());

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> prodcutNotFoundException(ProductNotFoundException ex)  {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(404);
        errorDTO.setMessage(ex.getMessage());
        logger.debug(ex.getMessage());
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    @ExceptionHandler(ParamsInvalidException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> ParamsInvalidException(ParamsInvalidException ex)  {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(400);
        errorDTO.setMessage(ex.getMessage());
        logger.debug(ex.getMessage());
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
        logger.error(ex.getMessage());
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> sqlException(SQLException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(500);
        errorDTO.setMessage("Failed to perform query");
        logger.error(ex.getMessage());
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> generalException(SQLException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(500);
        errorDTO.setMessage("Failed");
        logger.error(ex.getMessage());
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

}

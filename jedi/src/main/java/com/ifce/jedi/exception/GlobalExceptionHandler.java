package com.ifce.jedi.exception;

import com.ifce.jedi.exception.custom.EmailAlreadyUsedException;
import com.ifce.jedi.exception.custom.EmailSendingException;
import com.ifce.jedi.exception.custom.TokenExpiredException;
import com.ifce.jedi.exception.custom.TokenNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Validação de @Valid no corpo da requisição
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("errors", fieldErrors);

        return ResponseEntity.badRequest().body(responseBody);
    }

    // Exceções lançadas explicitamente na regra de negócio
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        logger.warn("Erro previsto: {}", ex.getMessage());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(responseBody);
    }

    // Token inválido
    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTokenNotFound(TokenNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Token expirado
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Map<String, Object>> handleTokenExpired(TokenExpiredException ex) {
        return buildErrorResponse(HttpStatus.GONE, ex.getMessage());
    }

    // Erro ao enviar e-mail
    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<Map<String, Object>> handleEmailError(EmailSendingException ex) {
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "Erro ao enviar e-mail: " + ex.getMessage());
    }

    // Erros relacionados a envio de arquivos (multipart/form-data)
    @ExceptionHandler({MultipartException.class, MissingServletRequestPartException.class})
    public ResponseEntity<Map<String, Object>> handleMultipartErrors(Exception ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Erro no envio de arquivos: " + ex.getMessage());
    }

    // Erros inesperados (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralError(Exception ex) {
        logger.error("Erro interno inesperado", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor. Tente novamente mais tarde.");
    }

    // 🔧 Método utilitário para retornar JSON padronizado
    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", "Dados inválidos: possível duplicidade de e-mail.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyUsed(EmailAlreadyUsedException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(responseBody);
    }
}

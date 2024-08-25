package br.com.lutamber.simplesdental.configuration;

import br.com.lutamber.simplesdental.exception.DatabaseException;
import br.com.lutamber.simplesdental.exception.InvalidFieldException;
import br.com.lutamber.simplesdental.exception.NoStackTraceException;
import br.com.lutamber.simplesdental.dto.CustomErrorDTO;
import br.com.lutamber.simplesdental.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

	public static final String CAMPOS_NAO_INFORMADOS = "Campos necessários para requisição não informados";

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<CustomErrorDTO> customName(DatabaseException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		CustomErrorDTO err = new CustomErrorDTO(Instant.now(), e.getMessage());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomErrorDTO> customName(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		CustomErrorDTO err = new CustomErrorDTO(Instant.now(), e.getMessage());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(NoStackTraceException.class)
	public ResponseEntity<CustomErrorDTO> customName(NoStackTraceException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		CustomErrorDTO err = new CustomErrorDTO(Instant.now(), e.getMessage());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(InvalidFieldException.class)
	public ResponseEntity<CustomErrorDTO> customName(InvalidFieldException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		CustomErrorDTO err = new CustomErrorDTO(Instant.now(), e.getMessage());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(PropertyValueException.class)
	public ResponseEntity<CustomErrorDTO> customName(PropertyValueException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		CustomErrorDTO err = new CustomErrorDTO(Instant.now(), CAMPOS_NAO_INFORMADOS);
		return ResponseEntity.status(status).body(err);
	}

}

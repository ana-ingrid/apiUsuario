package br.com.cadastro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResponseEntityStatus {
    default <T extends Object> ResponseEntity<T> response(final HttpStatus httpStatus, final T response) {
        return ResponseEntity.status(httpStatus.value()).body(response);
    }

    default <T extends Object> ResponseEntity<T> responseNoContent(final HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus.value()).build();
    }
}

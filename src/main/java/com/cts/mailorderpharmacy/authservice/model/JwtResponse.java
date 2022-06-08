package com.cts.mailorderpharmacy.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class JwtResponse<T> extends ResponseEntity<T> {

    private String jwt;

    public JwtResponse(String jwt, T body, HttpStatus status) {
        this(body, status);
        this.jwt = jwt;
    }

    public JwtResponse(T body, HttpStatus status) {
        super(body, status);
    }
}

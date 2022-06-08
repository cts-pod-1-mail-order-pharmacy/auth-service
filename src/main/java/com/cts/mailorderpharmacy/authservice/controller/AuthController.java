package com.cts.mailorderpharmacy.authservice.controller;

import com.cts.mailorderpharmacy.authservice.jwt.CustomerDetailsService;
import com.cts.mailorderpharmacy.authservice.jwt.JwtUtil;
import com.cts.mailorderpharmacy.authservice.model.AuthResponse;
import com.cts.mailorderpharmacy.authservice.model.JwtResponse;
import com.cts.mailorderpharmacy.authservice.model.User;
import com.cts.mailorderpharmacy.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerDetailsService custdetailservice;

    @Autowired
    private UserRepository userservice;

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody User userlogincredentials) {
        //Generates token for login
        final UserDetails userdetails = custdetailservice.loadUserByUsername(userlogincredentials.getUserid());
        String uid = "";
        String generateToken = "";
        if (userdetails.getPassword().equals(userlogincredentials.getUpassword())  ) {
            uid = userlogincredentials.getUserid();
            generateToken = jwtUtil.generateToken(userdetails);
            return new ResponseEntity<>(new User(uid,null, null, generateToken), HttpStatus.OK);
        } else {

            return new ResponseEntity<>("Invalid Login Credentials!!!", HttpStatus.FORBIDDEN);
        }
    }


    // Use JwtResponse instead of ResponseEntity
//    @PostMapping(value = "/login")
//    public JwtResponse<Object> login(@RequestBody User userlogincredentials) {
//        //Generates token for login
//        final UserDetails userdetails = custdetailservice.loadUserByUsername(userlogincredentials.getUserid());
//        String uid = "";
//        String generateToken = "";
//        if (userdetails.getPassword().equals(userlogincredentials.getUpassword())  ) {
//            uid = userlogincredentials.getUserid();
//            generateToken = jwtUtil.generateToken(userdetails);
//            return new JwtResponse<>(generateToken, HttpStatus.OK);
//        } else {
//
//            return new JwtResponse<>("Invalid Login Credentials!!!", HttpStatus.FORBIDDEN);
//        }
//    }

    @GetMapping(value = "/validate")
    public ResponseEntity<Object> getValidity(@RequestHeader("Authorization") final String token) {

        //Returns response after Validating received token

        String token1 = token.substring(7);
        AuthResponse res = new AuthResponse();
        if (jwtUtil.validateToken(token1)) {
            res.setUid(jwtUtil.extractUsername(token1));
            res.setValid(true);
            res.setName(userservice.findById(jwtUtil.extractUsername(token1)).get().getUname());
        } else {
            res.setValid(false);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

}

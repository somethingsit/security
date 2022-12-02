package com.example.security.controller;

import com.example.security.dto.JwtRequestDTO;
import com.example.security.dto.JwtResponseDTO;
import com.example.security.dto.RefreshRequestDTO;
import com.example.security.dto.RefreshResponseDTO;
import com.example.security.fw.config.JwtTokenUtil;
import com.example.security.model.RefreshToken;
import com.example.security.service.RefreshTokenService;
import com.example.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v01")
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenService refreshTokenService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, UserService userService, JwtTokenUtil jwtTokenUtil, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody JwtRequestDTO jwtRequestDTO)  throws Exception {
            authenticate(jwtRequestDTO.getUsername(), jwtRequestDTO.getPassword());

            final UserDetails userDetails = userService
                    .loadUserByUsername(jwtRequestDTO.getUsername());

            final String token = jwtTokenUtil.generateToken(userDetails);

            //create refreshToken
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
            return ResponseEntity.ok(new JwtResponseDTO(token, refreshToken.getToken(), null));
    }

    @PostMapping(path = "/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshRequestDTO refreshRequestDTO)  throws Exception {
        String refreshToken = refreshRequestDTO.getRefreshToken();

        final UserDetails userDetails = userService.loadUserByUsername(null);

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new RefreshResponseDTO(token, refreshToken));
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
        throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

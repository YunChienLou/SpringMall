package com.ryanlou.springmall.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanlou.springmall.congfig.JwtService;
import com.ryanlou.springmall.service.UserService;
import com.ryanlou.springmall.util.responseBuilder.ErrorResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    private final UserDetailsService userDetailsService;

    @Autowired
    private ErrorResponseBuilder errorResponseBuilder;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ){
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.resgister(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ){
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt;
        String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            log.error("Refresh token is missing");
            // 不需要繼續驗證了
            return;
        }
        try {
            // Bearer 後的字 都是 jwt token
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt); // todo extract userEmail form JWT

            // user isnt connected yet

                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                if(jwtService.isTokenValid(jwt,userDetails)){
                    var user = userService.getUserByEmail(userEmail);
                    var jwtToken = jwtService.generateToken(user);
                    var authenticationResponse  = AuthenticationResponse.builder().token(jwtToken).refreshToken(jwt).build();

                    new ObjectMapper().writeValue(response.getOutputStream(),authenticationResponse);
                }else {
                    log.error("Error refreshToken : token is not valid");
                    errorResponseBuilder.errorResponse(response,403, "token is not valid",request.getRequestURI());
                }

        }catch (Exception exception){
            log.error("Error refreshToken :{}", exception.getMessage());
            errorResponseBuilder.errorResponse(response,400, exception.getMessage(),request.getRequestURI());
        }
    }
}

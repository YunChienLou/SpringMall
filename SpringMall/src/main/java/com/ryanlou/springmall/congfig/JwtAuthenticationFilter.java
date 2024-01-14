package com.ryanlou.springmall.congfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanlou.springmall.util.responseBuilder.ErrorResponseBuilder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
@RequiredArgsConstructor // 創造一個 constructor 用 第一個 變數
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    private ErrorResponseBuilder errorResponseBuilder;

    @Override
    protected void doFilterInternal(

            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain

    ) throws ServletException, IOException {
        //擷取 Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            // 不需要繼續驗證了
            return;
        }
        try {
            // Bearer 後的字 都是 jwt token
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt); // todo extract userEmail form JWT

            // user isnt connected yet
            if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if(jwtService.isTokenValid(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            // always pass down
            filterChain.doFilter(request,response);
        }catch (Exception exception){
            log.error("Error log in :{}", exception.getMessage());
            errorResponseBuilder.errorResponse(response,403, exception.getMessage(),request.getRequestURI());
        }

    }
}

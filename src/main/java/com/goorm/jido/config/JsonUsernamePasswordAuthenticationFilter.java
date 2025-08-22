package com.goorm.jido.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        if (!MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
            return super.attemptAuthentication(request, response);
        }

        try (InputStream is = request.getInputStream()) {
            Map<String, String> loginRequest = objectMapper.readValue(is, new TypeReference<>() {});
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");

            if (username == null || password == null) {
                throw new AuthenticationServiceException("Username or password not provided");
            }

            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(username, password);

            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            throw new AuthenticationServiceException("Invalid JSON login request", e);
        }
    }

    // ✅ 인증 성공 후 세션/컨텍스트에 인증 정보 저장
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        request.getSession(true); // 세션 강제 생성
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
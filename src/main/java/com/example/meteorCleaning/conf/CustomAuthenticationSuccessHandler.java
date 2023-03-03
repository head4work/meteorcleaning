package com.example.meteorCleaning.conf;

import com.example.meteorCleaning.AuthorizedUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        AuthorizedUser authorizedUser = safeGet(authentication);
      if(authorizedUser != null){
          Map<String, Object> data = new HashMap<>();
          data.put(
                  "email",
                  authorizedUser.getUserTo().getEmail());
          data.put(
                  "name",
                  authorizedUser.getUserTo().getName());
          response.getOutputStream()
                  .println(objectMapper.writeValueAsString(data));
      }
    }

    private AuthorizedUser safeGet(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }
}

package com.azaroff.x3.web.security;

import com.azaroff.x3.web.model.CommonResponse;
import com.azaroff.x3.web.util.CommonResponseFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) {
        try {
            response.setHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            CommonResponseFactory<CommonResponse> commonResponseFactory = CommonResponse::new;
            CommonResponse commonSyncResponse = commonResponseFactory.create(HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(), authEx.getMessage());

            PrintWriter writer = response.getWriter();
            writer.println(new ObjectMapper().writeValueAsString(commonSyncResponse));
        } catch (IOException ex) {
            new RuntimeException("Failed Authentication Response " + ex.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("App X3");
        super.afterPropertiesSet();
    }

}
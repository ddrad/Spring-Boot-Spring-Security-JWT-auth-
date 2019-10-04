package com.azaroff.x3.web.security;

import com.azaroff.x3.web.auth.dao.entity.AuthenticationData;
import com.azaroff.x3.web.model.CommonResponse;
import com.azaroff.x3.web.util.CommonResponseFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import static com.azaroff.x3.web.security.SecurityConstants.EXPIRATION_TIME;
import static com.azaroff.x3.web.security.SecurityConstants.HEADER_STRING;
import static com.azaroff.x3.web.security.SecurityConstants.SECRET;
import static com.azaroff.x3.web.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            AuthenticationData creds = new ObjectMapper()
                    .readValue(req.getInputStream(), AuthenticationData.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail().toLowerCase(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.addHeader("Access-Control-Expose-Headers","Authorization");
        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        CommonResponseFactory<CommonResponse> commonResponseFactory = CommonResponse::new;
        CommonResponse commonSyncResponse = commonResponseFactory.create(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase(), null);
        PrintWriter writer = res.getWriter();
        writer.println(new ObjectMapper().writeValueAsString(commonSyncResponse));
    }
}

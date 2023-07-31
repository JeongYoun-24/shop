package com.springboot.pople.security.handler;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class Custom403Handler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("--=-=-=-=-=--=--=-=-=-=-=-=-=ACCeSS DENIED=-=-=-=-=-=-=-=-=--=-=-=-=--==-==-=-=");

        response.setStatus(HttpStatus.FORBIDDEN.value());

        // JSON 요청었는지 확인
        String contenType = request.getHeader("Content-Type");
        boolean jsonRequest = contenType.startsWith("application/json");
        log.info("isJSON ="+ jsonRequest);

        // 일반 request
        if(!jsonRequest){
            response.sendRedirect("/users/login?error=ACCESS_DENIED");
        }

    }
}

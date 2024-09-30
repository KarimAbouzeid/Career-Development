package com.example.demo.components;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


//@Component
public class AdminFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException, ServletException {
        String uri = request.getRequestURI();
        System.out.println(uri);

        String token = request.getHeader("Authorization");

        System.out.println("inside filter");

        if (token==null ) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied");
            return;
        }

        if (!"Admin".equals(token)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }


        filterChain.doFilter(request, response);

    }

}
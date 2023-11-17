package com.aid.configuration;

import com.aid.log.LogDto;
import com.aid.log.LogThreadLocal;
import com.aid.util.Constants;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class HttpLogger extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean dontLog = request.getServletPath().equalsIgnoreCase("/")
                || request.getServletPath().contains("swagger")
                || request.getServletPath().contains("actuator")
                || request.getServletPath()
                .equalsIgnoreCase("/v2/api-docs");

        if (dontLog) {
            filterChain.doFilter(request, response);
            return;
        }

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {

            try {
                LogThreadLocal.set(LogDto.builder().startTime(System.currentTimeMillis()).txId(RandomString.make()).build());
                filterChain.doFilter(request, response);
                LogDto logDto = LogThreadLocal.get();
                log.info("path = [{}], txId=[{}] response time = [{}] ms ",
                        request.getRequestURI().replace(Constants.API_REQUEST_MAPPING, ""), logDto.getTxId(), System.currentTimeMillis() - logDto.getStartTime());
            } finally {
                LogThreadLocal.unset();
            }


        }
    }

}


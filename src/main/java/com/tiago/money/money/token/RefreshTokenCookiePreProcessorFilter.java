package com.tiago.money.money.token;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

            if ("/oauth/token".equalsIgnoreCase(request.getRequestURI())
                && request.getCookies() != null
                && "refresh_token".equals(request.getParameter("grant_type"))) {
            String refreshToken = getRefreshTokenInCookiesRequest(request);
            request = new RequestWithRefreshTokenWrapper(request, refreshToken);
        }

        chain.doFilter(request, servletResponse);

    }

    private String getRefreshTokenInCookiesRequest(HttpServletRequest request) {
       for(Cookie cookie : request.getCookies()) {
           if (cookie.getName().equals("refreshToken")) {
               return cookie.getValue();
           }
       }
        return null;
    }

    static class RequestWithRefreshTokenWrapper extends HttpServletRequestWrapper {

        private String refreshToken;

        public RequestWithRefreshTokenWrapper(HttpServletRequest request, String refreshToken) {
            super(request);
            this.refreshToken = refreshToken;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            ParameterMap<String, String[]> parameters = new ParameterMap<>(getRequest().getParameterMap());
            parameters.put("refresh_token", new String[]{ this.refreshToken });
            parameters.setLocked(true);
            return parameters;
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}

package com.tiago.money.money.resource;

import com.tiago.money.money.config.profile.MoneyProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/tokens")
public class TokenResource {

    @Autowired
    private MoneyProfile moneyProfile;

    @DeleteMapping(value = "/revoke")
    public void revokeToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(this.moneyProfile.getSeguranca().isEnableHttps());
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath() + "/oauth/token");

        response.addCookie(cookie);
        response.setStatus(HttpStatus.NO_CONTENT.value());

    }
}

package com.ptit.viet.ssosv.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.refresh-secret}")
    private String jwtRefreshSecret;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @GetMapping("/login/success")
    public void getLoginInfo(@AuthenticationPrincipal OAuth2User oauth2User,
                             HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        // Print all attributes of the OAuth2User
        Map<String, Object> attributes = oauth2User.getAttributes();
        System.out.println("OAuth2User Attributes:");
        attributes.forEach((key, value) -> System.out.println(key + ": " + value));

        String email = (String) oauth2User.getAttributes().get("email");
        if (email == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "GitHub email not verified");
            return;
        }

        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        String accessToken = JWT.create()
                .withSubject(email)
                .withClaim("type", "access_token")
                .withExpiresAt(new Date(System.currentTimeMillis() + 900_000)) // 15 minutes
                .sign(algorithm);

        Algorithm refreshAlgorithm = Algorithm.HMAC256(jwtRefreshSecret);
        String refreshToken = JWT.create()
                .withSubject(email)
                .withClaim("type", "refresh_token")
                .withExpiresAt(new Date(System.currentTimeMillis() + 8_640_000_000L)) // 100 days
                .sign(refreshAlgorithm);

        redirectStrategy.sendRedirect(
                request,
                response,
                "http://localhost:3000/login/oauth?access_token=" + accessToken + "&refresh_token=" + refreshToken
        );

        // TODO: save user's info, create account, the oauth2 only for the user's info
    }
}

package com.matus.cursomc.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matus.cursomc.dto.CredenciaisDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

// Com UsernamePasswordAuthenticationFilter o Spring sabe automaticamente que vai ter que
// interceptar a requisição de Login
// /login é reservado para o Spring Security
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

        try {
            // req.getInputStream() ==> Vai pegar os dados da requisição
            // Ultimo parametro ==> Vai pegar os dados da requisição e converte-los para o que tiver no ultimo parametro
            CredenciaisDTO creds = new ObjectMapper()
                    .readValue(req.getInputStream(), CredenciaisDTO.class);

            //Com esses dados vai ser gerado um token (Não é token do JWT é do Spring Security)
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());

            // É aqui q vai ser verificado se o usuario e senha são validos
            Authentication auth = authenticationManager.authenticate(authToken);
            return auth;
        }catch (IOException e ){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        //auth.getPrincipal() ==> Retornar o usuario do Spring Security, depois faz casting para o UserSS
        //.getUsername() ==> Pega o email da pessoa
        String username = ((UserSS) auth.getPrincipal()).getUsername();
        // Gera o Token do JWT apartir do email do usuario
        String token = jwtUtil.generateToken(username);
        // Vai retornar esse Token na resposta da requisição como um cabeçalho
        res.addHeader("Authorization", "Bearer " + token);
        res.addHeader("access-control-expose-headers", "Authorization");
    }

    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                    + "\"status\": 401, "
                    + "\"error\": \"Não autorizado\", "
                    + "\"message\": \"Email ou senha inválidos\", "
                    + "\"path\": \"/login\"}";
        }
    }
}

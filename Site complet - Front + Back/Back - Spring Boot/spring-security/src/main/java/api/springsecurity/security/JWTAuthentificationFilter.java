package api.springsecurity.security;

import api.springsecurity.security.*;
import api.springsecurity.entities.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
import java.util.Date;

public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthentificationFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AppUser user = null; //On va recuperer le username et le password
        //String username=request.getParameter("username"); si on veut recuperer les données a partir d'un format www_url_encoder
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), AppUser.class); //ObjectMapper '> deseréaliser l'objet et le mettre au format json || Request (contenu de la requete)  va mettre les données en format JSON dans user (type AppUser)
        } catch (Exception e) {
            throw new RuntimeException(e); // Si erreur on montre pourquoi
        }
        //System.out.println("++++++++++++++++++++++++");
        //System.out.println("username"+user.getUsername()+"password"+user.getPassword());
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())); // On se connecte avec les données en JSON
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User springUser = (User)authResult.getPrincipal(); // on recupere les données de l'user

        String jwtToken= Jwts.builder(). // creation du token
                setSubject(springUser.getUsername()). // dans le token->subject on va trouver l'username
                setExpiration(new Date(System.currentTimeMillis()+ api.springsecurity.security.SecurityConstants.EXPIRATION_TIME)). // dans le token->expiration : durée de 10 jours
                signWith(SignatureAlgorithm.HS512, api.springsecurity.security.SecurityConstants.SECRET). // On choisit l'algo de cryptage et on le signe avec le secret
                claim("roles",springUser.getAuthorities()). //on dit le role dans le token
                compact(); // compacter en base64URL (important)
        response.addHeader(api.springsecurity.security.SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + jwtToken); // Une fois le token genéré, on met tout dans le header
        //super.successfulAuthentication(request, response, chain, authResult);
    }
}

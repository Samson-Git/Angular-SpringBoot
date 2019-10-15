package api.springsecurity.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //pour autoriser tous les domaines
        response.addHeader("Access-Control-Allow-Origin","*");
        //Autoriser l'envoie des entetes par Angular || Entete autorisé
        response.addHeader("Access-Control-Allow-Headers","Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization");
        //Donner le droit de lire ces entetes a angular
        response.addHeader("Access-Control-Expose-Headers","Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
        //Si le client envoie option, on lui renvoie la réponse OK  et il lui dit les entetes autorisées (ci-dessus)|| on passe pas par la sécu spring
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            String jwt=request.getHeader(SecurityConstants.HEADER_STRING); // On stock le token dans -> jwt
            System.out.println(jwt);
            if (jwt == null || !jwt.startsWith(SecurityConstants.TOKEN_PREFIX)) { // Si il le trouve pas ou commence pas par le prefix, on stop ! no need to continue
                filterChain.doFilter(request,response);
                return;
            }
            Claims claims= Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(jwt.replace(SecurityConstants.TOKEN_PREFIX,"")) // on supprime le prefix
                    .getBody(); // on recupere le contenu du token
            String username = claims.getSubject(); // on recupere l'username || subject -> username dans le token
            ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>)claims.get("roles"); // objet de type map || contient le nom et la valeur
            Collection<GrantedAuthority> authorities=new ArrayList<>(); // on recupere les roles
            roles.forEach(r->{authorities.add(new SimpleGrantedAuthority(r.get("authority")));}); // on recupere les roles
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities); //on stock les infos dans un une classe de type UsernamePassword... (pas besoin du mot de passe, il est deja authentifié)
            SecurityContextHolder.getContext().setAuthentication(authenticationToken); //On va charger l'utilisateur
            filterChain.doFilter(request, response);
        }
    }
}


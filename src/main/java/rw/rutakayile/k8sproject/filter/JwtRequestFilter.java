package rw.rutakayile.k8sproject.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import rw.rutakayile.k8sproject.model.Admin;
import rw.rutakayile.k8sproject.service.AdminService;
import rw.rutakayile.k8sproject.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A Custom {@link OncePerRequestFilter} class that handles JWT generation & filtering as well token
 * expiration time
 *
 * @author Samuel Rutakayile
 * @version 1.0
 * @since 1.0
 */
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private  AdminService authService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain)
      throws ServletException, IOException {
    final String authorizationHeader = httpServletRequest.getHeader("Authorization");
    String email;
    String jwt;

    try {
      if (authorizationHeader != null
          && authorizationHeader.startsWith("Bearer ")
          && SecurityContextHolder.getContext().getAuthentication() == null) {
        jwt = authorizationHeader.substring(7);
        email = jwtUtil.extractSubject(jwt);
        Admin admin = authService.findAdminByEmail(email);
        if (admin != null && jwtUtil.validateToken(jwt, admin)) {
          UsernamePasswordAuthenticationToken token =
              new UsernamePasswordAuthenticationToken(admin, null,null);
          token.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
          SecurityContextHolder.getContext().setAuthentication(token);
        }
      }
    } catch (Exception e) {
      log.error("An error occurred while validating the token", e);
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}

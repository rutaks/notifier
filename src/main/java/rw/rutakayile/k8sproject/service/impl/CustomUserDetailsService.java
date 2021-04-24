package rw.rutakayile.k8sproject.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rw.rutakayile.k8sproject.service.AdminService;

/**
 * A Class representing the authentication service layer
 *
 * @author Samuel Rutakayile
 * @version 1.0
 * @since 1.0
 */
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired private AdminService adminService;

  /**
   * Will find account by Email, Phone No or other identifier.
   *
   * @param identifier Email, Phone No or other identifier of account
   * @return {@link UserDetails}
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
    return adminService.findAdminByEmail(identifier);
  }
}

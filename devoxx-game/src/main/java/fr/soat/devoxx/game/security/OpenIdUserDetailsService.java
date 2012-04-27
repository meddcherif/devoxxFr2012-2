package fr.soat.devoxx.game.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import com.google.common.base.Strings;

import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.UserRole;
import fr.soat.devoxx.game.services.UserRoleServices;
import fr.soat.devoxx.game.services.UserServices;

/**
 * @author aurelien
 */
public class OpenIdUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    @Autowired
    UserServices userServices;
    
    @Autowired
    UserRoleServices userRolesServices;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenIdUserDetailsService.class);

    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
        DevoxxUser user = null;
        String email = null;
        String firstName = null;
        String lastName = null;
        String fullName = null;
        String urlId = token.getIdentityUrl();

        if (Strings.isNullOrEmpty(urlId)) {
            throw new UsernameNotFoundException("UrlId is null");
        }
        
        try {
            user = userServices.getUserByName(urlId);
        } catch (RuntimeException e) {
            user = null;
            // TODO une exception plus explicite dans le catch
            LOGGER.debug("user not found !", e);
        }

        List<OpenIDAttribute> attributes = token.getAttributes();

        for (OpenIDAttribute attribute : attributes) {
            if ("email".equals(attribute.getName())) {
                email = attribute.getValues().get(0);
            }
            if ("firstname".equals(attribute.getName())) {
                firstName = attribute.getValues().get(0);
            }
            if ("lastname".equals(attribute.getName())) {
                lastName = attribute.getValues().get(0);
            }
            if ("fullname".equals(attribute.getName())) {
                fullName = attribute.getValues().get(0);
            }
        }

        if (Strings.isNullOrEmpty(fullName) && (!Strings.isNullOrEmpty(firstName) || !Strings.isNullOrEmpty(lastName))) {
            fullName = firstName + " " + lastName;
        }
        
        if(null == user) {
            user = new DevoxxUser();
            UserRole role;
            try {
                role = userRolesServices.getUserRoleByName("ROLE_USER");
            } catch (RuntimeException e) {
                LOGGER.debug("No Roles found");
                role = new UserRole("ROLE_USER");
            }
            if(null == role) {
                role = new UserRole("ROLE_USER");
            }            
            user.addUserRole(role);
            user.setUsername(urlId);
            user.setUserForname(fullName);
            user.setUserEmail(email);
            user.setEnabled(true);
            user.setRulesApproved(true);
            userServices.createUser(user);
        }
        else {
            user.setUserForname(fullName);
            user.setUserEmail(email);
            user.setEnabled(true);
            user.setRulesApproved(true);
            userServices.updateUser(user);
        }

        /*List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (UserRoles role : user.getUserRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }        
        OpenIdUserDetails openIdUser = new OpenIdUserDetails(urlId, grantedAuthorities);
        openIdUser.setUser(user);*/
        
        return user;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userServices.getUserByName(username);
        } catch (RuntimeException e) {
            LOGGER.debug("user not found", e);
            throw new UsernameNotFoundException(username + " not found", e);
        }
    }

}

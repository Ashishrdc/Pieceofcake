package com.develop.pieceofcake.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.develop.pieceofcake.model.Role;
import com.develop.pieceofcake.model.User;
import com.develop.pieceofcake.repository.RoleRepository;
import com.develop.pieceofcake.repository.UserRepository;

@Component
public class GoogleOAuth2 implements AuthenticationSuccessHandler{
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;


    private DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication authentication)
            throws IOException, ServletException {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication; 
            String email = token.getPrincipal().getAttributes().get("email").toString();
            if(userRepository.findUserByEmail(email).isPresent()){

            }
            else{
                User user = new User();
                user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
                user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
                user.setEmail(email);
                List<Role> roles = new ArrayList<>();
                roles.add(roleRepository.findById(2).get());
                user.setRoles(roles);
                userRepository.save(user);
            }
            redirectStrategy.sendRedirect(arg0, arg1, "/");
        
    }
    
    

}

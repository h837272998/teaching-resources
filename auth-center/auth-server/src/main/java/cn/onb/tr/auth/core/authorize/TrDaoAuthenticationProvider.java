//package cn.onb.tr.auth.core.authorize;
//
//import cn.onb.tr.auth.server.service.impl.UserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Import;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Assert;
//
//@Component
//@Primary
//public class TrDaoAuthenticationProvider extends DaoAuthenticationProvider {
////
////
////    public TrDaoAuthenticationProvider(UserDetailsService userDetailsService) {
////        setUserDetailsService(userDetailsServicey);
////    }
////
////    @Override
////    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
////        logger.info("xxxxxxxxxxxxxxxxxxxxx");
////        super.additionalAuthenticationChecks(userDetails, authentication);
////    }
////}

package dev.aquashdw.auth.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
/**
 * facade : 일련의 복잡한 과정들을 단순하게 해주는 디자인 패턴의 일종
 */
public class AuthenticationFacade  {

    public Authentication getUsername(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

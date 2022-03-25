package dev.aquashdw.auth.config;

import dev.aquashdw.auth.infra.NaverOauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { //extend로 기본 설정 되어 있는 spring security를 어느정도 확인 가능
    private final UserDetailsService userDetailsService;
    private final NaverOauth2Service naverOauth2Service;

    public WebSecurityConfig(@Autowired UserDetailsService userDetailsService,
                             @Autowired NaverOauth2Service naverOauth2Service) {
        this.userDetailsService = userDetailsService;
        this.naverOauth2Service = naverOauth2Service;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication() //메모리 상에서 유저 검증하겠다는 의믜
//                .withUser("user1")
//                .password(passwordEncoder().encode("user1pass"))
//                .roles("USER")
//                .and()
//                .withUser("admin1")
//                .password(passwordEncoder().encode("admin1pass"))
//                .roles("ADMIN");
       auth.userDetailsService(this.userDetailsService); // 얘를 이용하면 더 이상 메모리 구현체가 아닌 DB에 유저 저장 가능
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { //filter와 비슷한 느낌
        http
                .authorizeRequests()
                .antMatchers("/home/**", "/user/**") // 이 url에서는 아래 사람들도 허용해줘라.  (home 이하 모든 url), 여러 개 인자로 받을 수 있음.
                //.anyRequest() // 어떤 요청이든 먼저 검증해라
                .anonymous() //authenticated, denyAll, anonymous 등이 존재. anonymous -> 로그인 하지 않은 사용자들 접근 허용
                .anyRequest() //어느 요청에나 (else 같은 느낌으로) 처리 가능하도록 넣는 것으로, 꼭 다른 url 들 다 기준 세운 후에 마지막에 넣어주기
                .authenticated()
        .and() // authorizeRequest는 끝났고, httpSecurity에 대한 다음 설정 가능.
                .formLogin() //403이면 /login으로 redirect 해라 라는 뜻
                .loginPage("/user/login") // 기본 로그인 경로
                .defaultSuccessUrl("/home") // 성공 시의 기본 경로
                .permitAll()
        .and()
                .oauth2Login()
                    .userInfoEndpoint()
                    .userService(this.naverOauth2Service)
                .and()
                    .defaultSuccessUrl("/home")
        .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/home")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true) //http session 개체를 사용할 때 거기에 저장해둔 정보를 지우는 것
                .permitAll()
        // signup은 수동으로 만들어줘야 함
        ;
    }
}

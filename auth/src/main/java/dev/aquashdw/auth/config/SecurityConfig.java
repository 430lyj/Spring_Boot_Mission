package dev.aquashdw.auth.config;

import dev.aquashdw.auth.filter.MyFilter1;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CorsConfig corsConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.addFilterBefore(new MyFilter1(), BasicAuthenticationFilter.class);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 x -> JWT 사용
                .and()
                .addFilter(corsConfig.corsFilter()) // 모든 요청은 이 필터를 탄다, @CrossOrigin(인증 X), 시큐리티 필터에 인증
                .formLogin().disable() // form tag 만들어서 로그인 안 한다
                .httpBasic().disable() // http basic 방식 : header에 Authorization 에 Id와 pw를 담아 보냄. -> 우리가 사용하는 건 토큰을 담아 보내는 bearer 방식
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER')")
                .anyRequest().permitAll();
    }
}
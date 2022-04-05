package dev.aquashdw.auth.config.jwt;

import dev.aquashdw.auth.controller.HomeController;
import dev.aquashdw.auth.infra.CustomUserDetailsService;
import io.jsonwebtoken.*;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private Long tokenValidMillisecond = 60 * 60 * 1000L;

    private final CustomUserDetailsService userDetailsService;

    private final Key key;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    public JwtProvider(@Value("spring.jwt.secret") String secretKey, @Autowired CustomUserDetailsService userDetailsService){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService = userDetailsService;
    }

    //jwt 생성
    public String createToken(String userPk, List<String> roles){

        //User 구분을 위해 Claims에 userPk 값 넣어줌.
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // jwt로 인증정보 조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // jwt에서 회원 구분 Pk 추출
    private String getUserPk(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // HTTP request의 Header에서 Token Parsing -> "X-AUTH-TOKEN : jwt"
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (SecurityException | MalformedJwtException e){
            logger.info("잘못된 JWT 서명입니다.");
        }catch (ExpiredJwtException e){
            logger.info("만료된 JWT 토큰입니다.");
        }catch (UnsupportedJwtException e){
            logger.info("지원되지 않는 JWT 토큰입니다.");
        }catch (IllegalArgumentException e){
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}

package dev.aquashdw.auth.infra;

import dev.aquashdw.auth.entity.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class NaverOauth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(NaverOauth2Service.class);

    public NaverOauth2Service(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String nameAttributeName= userRequest
                .getClientRegistration() // application.yml에서 정의한 registration 중에 어떤 것을 기준으로 들어온 요청인지
                .getProviderDetails() //application.yml에서 정의한 provider의 detail 정보들을 받아오는 과정
                .getUserInfoEndpoint() // user 정보는 어디에서 받아오는지
                .getUserNameAttributeName(); //결과적으로 username이 무엇인지 파악하는 코드
        logger.info(oAuth2User.getAttributes().get("response").toString());
        oAuth2User.getAttributes().get("response");
        return oAuth2User;
    }
}

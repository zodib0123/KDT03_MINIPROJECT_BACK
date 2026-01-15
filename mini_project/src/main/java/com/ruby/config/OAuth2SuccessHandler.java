package com.ruby.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ruby.domain.Member;
import com.ruby.persistence.MemberRepo;
import com.ruby.util.JWTUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	private final MemberRepo mrepo;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//System.out.println("OAuth2SuccessHandler: entered!");
		Map<String, String> map = getUserInfo(authentication);
		
		String username = map.get("provider") + "_" + map.get("email");
		String alias = username.split("@")[0];
		if (!mrepo.existsById(username)) {
			Member member = Member.builder()
					.mid(username)
					.alias(alias)
					.build();
			mrepo.save(member);
		}
		String rawtoken = JWTUtil.getJWT(username);
		
		sendJWTtoClient(response, rawtoken);
		String token = rawtoken;
		if(rawtoken.startsWith("Bearer"))
			token = rawtoken.split(" ")[1];
		
		String encodedAlias = URLEncoder.encode(alias, StandardCharsets.UTF_8);
		
		//use URL handoff
		String target = "http://10.125.121.184.nip.io:3000/signin/callback"
						+"?token="+token
						+"&mid="+username
						+"&alias="+encodedAlias;
				
		getRedirectStrategy().sendRedirect(request, response, target);
	}
	
	//provider, email을 가져오는
	@SuppressWarnings("unchecked")
	Map<String, String> getUserInfo(Authentication authentication){
		//authentication을 oauth2토큰으로 파싱
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)authentication;
		
		//provider 추출
		String provider = token.getAuthorizedClientRegistrationId();
		
		//user객체 추출
		OAuth2User user = (OAuth2User)token.getPrincipal();
		String email = "unknown";
		if(provider.equalsIgnoreCase("naver"))
			email = (String)((Map<String,Object>)user.getAttribute("response")).get("email");
		else if(provider.equalsIgnoreCase("google"))
			email = (String)user.getAttributes().get("email");
		
		return Map.of("provider",provider,"email",email);
	}
	
	void sendJWTtoClient(HttpServletResponse response, String token) throws IOException{
		response.addHeader(HttpHeaders.AUTHORIZATION, token);
	}
	
}

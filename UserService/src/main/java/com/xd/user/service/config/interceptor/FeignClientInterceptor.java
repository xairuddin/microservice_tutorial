package com.xd.user.service.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

public class FeignClientInterceptor implements RequestInterceptor {
    private OAuth2AuthorizedClientManager manager;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build()).getAccessToken().getTokenValue();
        requestTemplate.header("Authorization", "Bearer " + token);
    }
}

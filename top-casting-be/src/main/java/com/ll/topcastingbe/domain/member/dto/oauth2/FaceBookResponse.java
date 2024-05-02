package com.ll.topcastingbe.domain.member.dto.oauth2;

import java.util.Map;

public class FaceBookResponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    public FaceBookResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "facebook";
    }

    @Override
    public String getProviderId() {
        return (String) attribute.get("id");
    }

    @Override
    public String getEmail() {
        return (String) attribute.get("email");
    }

    @Override
    public String getName() {
        return (String) attribute.get("name");
    }
}

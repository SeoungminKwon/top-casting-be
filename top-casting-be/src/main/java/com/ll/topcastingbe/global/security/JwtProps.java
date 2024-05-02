package com.ll.topcastingbe.global.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class JwtProps {

    @Value("${custom.jwt.secretKey}")
    public String secretKey;
    @Value("${custom.jwt.refreshKey}")
    public String refreshKey;
}

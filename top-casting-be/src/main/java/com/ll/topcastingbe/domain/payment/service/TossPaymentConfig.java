package com.ll.topcastingbe.domain.payment.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;


@Configuration
@Getter
public class TossPaymentConfig {

    private final String tossApiUri = "https://api.tosspayments.com/v1/payments/confirm";

    private final String tossCancelApiuri = "https://api.tosspayments.com/v1/payments/";
    //test시크릿키
    private final String testTossAuthorizationHeader = "Basic dGVzdF9za196WExrS0V5cE5BcldtbzUwblgzbG1lYXhZRzVSOg==";

    private final String contentTypeHeader = "application/json";

    private final String testClientApiKey = "test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq";

    //진짜로 넣어야할 시크릿 키
    //안보이게 바꿔야함
    private final String secretKey = "test_sk_zXLkKEypNArWmo50nX3lmeaxYG5R";

    private final String tossAuthorizationHeader = new String(
            Base64.getEncoder().encode((secretKey + ":").getBytes(StandardCharsets.UTF_8)));

    private final String successUrl = "\"http://localhost:8081/api/v1/payments/toss/success\"";

    private final String failUrl = "http://localhost:8081/api/v1/payments/toss/fail";
}

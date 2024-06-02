package com.fastcampuspay.banking;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// common 패키지에 있는 모든 클래스를 bean 으로 등록하고, Banking Service 시작하겠다.
@ComponentScan("com.fastcampuspay.common")
public class BankingConfig {
}

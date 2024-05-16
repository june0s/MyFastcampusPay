package com.fastcampuspay.common;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

// 영속성 계층의 Adapter 를 의미하는 어노테이션, 별도의 기능을 하지는 않는다.
// out.persistence 클래스에 정의한다.
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PersistenceAdapter {

    @AliasFor(annotation = Component.class)
    String value() default "";
}

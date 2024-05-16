package com.fastcampuspay.common;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

// 사용을 하는 예에 해당하는 어노테이션, 별도의 기능을 하지는 않는다.
// service 클래스에 정의한다. 예: 멤버 등록한다. 멤버 조회한다.
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface UseCase {

    @AliasFor(annotation = Component.class)
    String value() default "";
}

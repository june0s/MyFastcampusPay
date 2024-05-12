package common;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

// WebAdapter 사용자 정의 interface.
// 단순히 WebAdapter 라는 표시를 위한 어노테이션, 별도의 기능을 하지는 않는다.
// - 핵사고날 아키텍처. controller 에 해당하므로 controller 에 붙여준다.
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface WebAdapter {

    @AliasFor(annotation = Component.class)
    String value() default "";
}

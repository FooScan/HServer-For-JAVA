package net.hserver.core.ioc.annotation;

import java.lang.annotation.*;

/**
 * @author hxm
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {

    String value() default "";

    String name() default "";

}
package top.hserver.core.ioc.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
  String value();

  RequestMethod[] method() default {};
}
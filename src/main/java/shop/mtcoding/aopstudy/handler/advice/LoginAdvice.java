package shop.mtcoding.aopstudy.handler.advice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import shop.mtcoding.aopstudy.handler.aop.LoginUser;
import shop.mtcoding.aopstudy.model.User;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class LoginAdvice {

    // @Pointcut("@annotation(shop.mtcoding.aopstudy.handler.aop.LoginUser)")
    // public void loginUser() {}

    // @Around("loginUser()") // 파라미터는 다른 방법이 필요

    @Around("execution(* shop.mtcoding.aopstudy.controller..*.*(..))")
    public Object loginUserAdvice(ProceedingJoinPoint jp) throws Throwable {
        Object[] args = jp.getArgs();
        // 메서드의 파라미터 어노테이션 정보를 가져온다
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        Annotation[][] annotationsPA = method.getParameterAnnotations();

        for (int i = 0; i < args.length; i++) {
            Annotation[] annotations = annotationsPA[i];
            for (Annotation anno : annotations) {
                if (anno instanceof LoginUser) {
                    HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                    HttpSession session = req.getSession();
                    User principal = (User) session.getAttribute("principal");
                    if (principal != null) {
                        args[i] = principal;
                    }
                }
            }
        }

        return jp.proceed(args);
    }
}
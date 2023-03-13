package shop.mtcoding.aopstudy.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import shop.mtcoding.aopstudy.model.User;

@Aspect
@Component
public class LoginAdvice {

    // @Pointcut("@annotation(shop.mtcoding.aopstudy.handler.aop.LoginUser)")
    // public void loginUser() {}

    // @Around("loginUser()") // 파라미터는 다른 방법이 필요
    // @Around("execution(* *(.., @LoginUser (*), ..))") // 블로그 보고 왔는데 일단 터짐
    @Around("execution(* shop.mtcoding.aopstudy.controller..*.*(..))") // 강사님이 알려주신
    public Object loginUserAdvice(ProceedingJoinPoint jp) throws Throwable {
        Object result = jp.proceed();
        Object[] args = jp.getArgs();
        Object[] param = new Object[1];
        for (Object arg : args) {
            if (arg instanceof User) {
                System.out.println("테스트 : 유저 있음");
                HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
                HttpSession session = req.getSession();
                User principal = (User) session.getAttribute("principal");
                param[0] = principal;
                result = jp.proceed(param);
            }
        }
        return result;
    }

    // private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    // @Around("execution(* shop.mtcoding.aopstudy.controller..*.*(..))")
    // public Object loginUserAdvice(ProceedingJoinPoint jp) throws Throwable {
    //     Object[] args = jp.getArgs();
    //     MethodSignature signature = (MethodSignature) jp.getSignature();
    //     Method method = signature.getMethod();
    //     Annotation[][] annotationsPA = method.getParameterAnnotations();

    //     for (int i = 0; i < args.length; i++) {
    //         Annotation[] annotations = annotationsPA[i]; // 첫번째 파라메터의 어노테이션, 두번째 파라메터의 어노테이션
    //         for (Annotation anno : annotations) {
    //             if (anno instanceof LoginUser) {
    //                 HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    //                 HttpSession session = req.getSession();
    //                 User principal = (User) session.getAttribute("principal");
    //                 return jp.proceed(new Object[]{principal});
    //             }
    //         }
    //     }

    //     return jp.proceed();
    // }

}
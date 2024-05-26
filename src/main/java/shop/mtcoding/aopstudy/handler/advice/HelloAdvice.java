package shop.mtcoding.aopstudy.handler.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HelloAdvice {

    // @Hello 어노테이션이 붙은 메서드를 포인트컷으로 지정
    @Pointcut("@annotation(shop.mtcoding.aopstudy.handler.aop.Hello)")
    public void hello(){
        // 포인트컷 메서드는 빈 메서드로 선언
    }

    // 포인트컷에 의해 선택된 메서드가 호출되기 전에 실행될 로직
    @Before("hello()")  // 별칭이나 풀네임 넣어준다.
    public void helloAdvice(){
        System.out.println("안녕 Hello");
    }

    // 포인트컷에 의해 선택된 메서드가 호출된 후에 실행될 로직
    @After("hello()")
    public void getAdvice(){
        System.out.println("잘가 Bye");
    }

    // 기존의 어노테이션도 추가 설정할 수 있다
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMapping(){}

    @Around("hello()") // 메서드 실행 전후와 실행 자체를 모두 제어할 수 있다
    public Object helloAdvice(ProceedingJoinPoint jp) throws Throwable{
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if(arg instanceof String){
                String username = (String) arg;
                // ?username=
                System.out.println(username+"님 안녕 !");
            }
        }

        // 직전 @Before 실행
        // 메서드 실행
        return jp.proceed();
        // 직후 @After 실행
    }
}

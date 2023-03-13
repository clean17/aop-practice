package shop.mtcoding.aopstudy.handler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HelloAdvice {
    
    // 깃발에 별칭을 주면 편하게 쓸수 있다.
    @Pointcut("@annotation(shop.mtcoding.aopstudy.handler.aop.Hello)")
    public void hello(){}

    // 겟매핑을 aop 설정 넣고 싶다면 동일한 매커니즘을 이용
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMapping(){}
    
    // @Before("hello()")  // 별칭이나 풀네임 넣어준다. 
    // // 매핑시킨 메소드에 @Hello 가 붙어있어서 그 전에 이 메소드를 실행한다.
    // public void helloAdvice(){
    //     System.out.println("테스트 : 안녕안녕");
    // }

    // @After("getMapping()")  
    // // 매핑 시킨 어노테이션을 aop 가 리플렉션으로 가져온다.
    // public void getAdvice(){
    //     System.out.println("테스트 : 헉헉22");
    // }

    // // 근데 around 는 왜 getmapping이 실행 안돼 ?? 

    // ========================================================

    @Around("hello()") // 실행 안되는 이유는 이제 나옴
    public Object helloAdvice(ProceedingJoinPoint jp) throws Throwable{ // 해당 메소드의 리플렉션한 값을 알고 있다. 
        // 금요일에 리턴받은 메소드를 얘가 알고 있어

        Object[] args = jp.getArgs(); // aop가 매핑된 메소드의 파라미터를 분석한다. -> 내부를 동적으로 분석할 수 있다.
        // System.out.println("테스트 : 파라미터 사이즈" + args.length);
        for (Object arg : args) {
            if(arg instanceof String){
                String username = (String) arg;
                // System.out.println("테스트 : String 매개변수가 있구나"); // http://localhost:8080/v2?username=ssar 입력시 출력됨
                System.out.println("테스트 : "+ username+"님 안녕 !");
            }
        }

        // System.out.println("테스트 : 하하하하");
        return jp.proceed();
        // around 는 기본적으로 내부 메소드로 진입하지 않고 null 을 리턴
        // proceed() 가 무시하고 진입하라고 명령 - > 결국 before 랑 같은 효과 ? 

        // 이걸 이용해서 세션을 주입해보라고 하심
    }
}

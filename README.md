#  의존성

```java
dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-aop'
}
```

# @Pointcut

AOP에서 포인트컷(Pointcut)을 정의하는 데 사용<br>
`Advice`를 적용할 지점을 지정<br>
단독으로 사용되지 않고 `@Before`, `@After`, `@AfterReturning`, `@AfterThrowing`, `@Around` 와 함께 사용<br>
```java
@Aspect
@Component
public class HelloAdvice {

    @Pointcut("@annotation(shop.mtcoding.aopstudy.handler.aop.Hello)")
    public void helloPointcut() {
        // 포인트컷 메서드는 내용이 없습니다. 단순히 Pointcut을 정의하는 역할을 합니다.
    }

    @Before("helloPointcut()")
    public void beforeHello() {
        System.out.println("Aspect - Before");
    }
}
```
# @Around
 AOP에서 가장 강력한 어노테이션으로, 메서드 실행 전후와 실행 자체를 모두 제어할 수 있는 조언(Advice)을 정의하는 데 사용<br>
 단독으로 사용한다<br>
```java
@Aspect
@Component
public class HelloAdvice {

    @Around("@annotation(shop.mtcoding.aopstudy.handler.aop.Hello)")
    public Object aroundHello(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Aspect - Around (Before)");

        // 직전 @Before 실행        
        // 메서드 실행
        Object result = joinPoint.proceed();
        // 직후 @After 실행

        System.out.println("Aspect - Around (After)");
        
        return result;
    }
}
```

# JoinPoint

 메서드 실행의 특정 지점에 대한 정보를 제공 <br>
  주로 `@Before`, `@After`, `@AfterReturning`, `@AfterThrowing`과 같은 조언에서 사용<br>

`getArgs()`: 호출된 메서드의 인수를 반환<br>
`getSignature()`: 호출된 메서드의 서명을 반환<br>
`getTarget()`: 호출된 메서드의 대상 객체를 반환<br>
`getThis()`: 프록시 객체를 반환<br>

```java
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Method: " + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            System.out.println("Argument: " + arg);
        }
    }
}
```

# ProceedingJoinPoint

`JoinPoint`를 확장하여 `@Around`에서 사용<br>
실제 메서드를 실행하거나, 전후 작업 지정하거나 메서드를 건너뛰는 기능을 제공<br>

`proceed()`: 원래의 메서드를 실행<br>
`proceed(Object[] args)`: 원래의 메서드를 새로운 인수로 실행<br>
`getArgs()`: 호출된 메서드의 인수를 반환<br>
`getSignature()`: 호출된 메서드의 서명을 반환<br>
`getTarget()`: 호출된 메서드의 대상 객체를 반환<br>
`getThis()`: 프록시 객체를 반환<br>

```java
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.example.service.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Method: " + joinPoint.getSignature().getName() + " is starting");
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            System.out.println("Argument: " + arg);
        }
        
        // 메서드 실행 전 로직
        Object result = joinPoint.proceed(); // 실제 메서드 실행
        // 메서드 실행 후 로직

        System.out.println("Method: " + joinPoint.getSignature().getName() + " has finished");
        return result;
    }
}
```
# 파라미터 어노테이션
메서드 실행 전 동적으로 조건을 만족하면 파라미터를 변경한다
```java
    @GetMapping("/auth/1")
    public String authInfo(@LoginUser User principal){
        System.out.println("로그인한 사용자: " + principal.getUsername());
        return "auth ok";
    }
```
```java
@Aspect
@Component
public class LoginAdvice {

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
```
for문으로 메서드의 모든 인수를 체크한다<br>
조건이 맞으면 파라미터에 세션정보를 주입한다<br>
### RequestContextHolder
현재 스레드의 요청 컨텍스트를 저장하고 접근할 수 있게 해주는 스프링의 유틸리티 클래스<br>
웹 요청이 들어올 때마다, Spring은 현재 요청과 연관된 정보를 스레드 로컬에 저장<br>

### ServletRequestAttributes
`RequestAttributes` 인터페이스의 구현체로, 서블릿 요청과 응답 객체(`HttpServletRequest`와 `HttpServletResponse`)를 포함<br>
`RequestContextHolder.getRequestAttributes()`: 현재 스레드의 요청 컨텍스트를 반환<br>
`getRequest()`: `HttpServletRequest` 객체를 반환

### HttpServletRequest
현재 HTTP 요청에 대한 정보를 얻는다

# HandlerMethodArgumentResolver
메서드 실행 전 동적으로 조건을 만족하면 파라미터를 변경하는 두번째 방법<br>
Spring MVC에서 컨트롤러 메서드의 특정 파라미터를 동적으로 처리할 수 있도록 해주는 인터페이스<br>
컨트롤러 메서드의 특정 파라미터를 자동으로 해결(resolving)하는 기능을 구현<br>
```java
@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new HandlerMethodArgumentResolver() { // 익명 클래스로 HandlerMethodArgumentResolver 추가
            @Override
            public boolean supportsParameter(MethodParameter parameter) {
                boolean check1 = parameter.getParameterAnnotation(SessionUser.class) != null;
                boolean check2 = User.class.equals(parameter.getParameterType());
                return check1 && check2;
            }

            @Override
            public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
                return session.getAttribute("principal");
            }
        });
    }
}
```
`supportsParameter`:  주어진 파라미터가 이 Argument Resolver에 의해 처리될 수 있는지 여부를 결정<br>
`resolveArgument`: 파라미터를 실제로 해결(resolving)하는 역할<br>

`HandlerMethodArgumentResolver`가 `@SessionUser User`를 찾아서 세션 정보를 주입한다 
```java
@RestController
public class UserController {

    @GetMapping("/profile")
    public String getProfile(@SessionUser User user) {
        return "User profile: " + user.getUsername();
    }
}
```
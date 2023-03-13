package shop.mtcoding.aopstudy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.aopstudy.handler.aop.LoginUser;
import shop.mtcoding.aopstudy.handler.aop.SessionUser;
import shop.mtcoding.aopstudy.model.User;

@RestController
@RequiredArgsConstructor  // final 이 붙은 생성자를 만들어 준다.
public class UserController {
    
    private final HttpSession session;

    @GetMapping("/login")
    public String login(){ // HttpSessoin session 이라고 해도 리플렉션이 자동으로 받아준다.
        User user = User.builder()
                    .username("ssar")
                    .password("1234")
                    .tel("0102222")
                    .build();
        session.setAttribute("principal", user);
        return "login ok";
    }

    @GetMapping("/user/1")
    public String userInfo(){ // 인증 필요 없음
        return "user ok";
    }

    @GetMapping("/auth/1")
    public String authInfo(@LoginUser User principal){ // 인증 필요함 
        // 이코드가 필요 없어 session.getAttribute("principal") ~~~ ; if ( 머시기 )
        // 인터셉터에서는 아예 접근 자체를 막아버리고 aop가 로그인창으로 보내버리는구나
        System.out.println("테스트 로그인한 사용자: " + principal.getUsername());
        return "auth ok";
    }

    @GetMapping("/auth/ss/1")
    public String authInfoss(@SessionUser User principal){
        System.out.println("테스트 로그인한 사용자: " + principal.getUsername());
        return "auth ok";
    }
}

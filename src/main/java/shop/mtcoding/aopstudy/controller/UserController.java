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
    public String login(){
        User user = User.builder()
                    .username("ssar")
                    .password("1234")
                    .tel("0102222")
//                    .maxValue(2100000)
                    .build();
        session.setAttribute("principal", user);
        System.out.println("로그인 완료");
        return "login ok";
    }

    // 인증 필요함 
    @GetMapping("/auth/1")
    public String authInfo(@LoginUser User principal){ 
        System.out.println("로그인한 사용자: " + principal.getUsername());
        return "auth ok";
    }

    @GetMapping("/auth/ss/1")
    public String authInfoss(@SessionUser User principal){
        System.out.println("로그인한 사용자: " + principal.getUsername());
        return "auth ok";
    }
}

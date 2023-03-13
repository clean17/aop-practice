package shop.mtcoding.aopstudy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
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
    public String userInfo(){
        return "user ok";
    }

    @GetMapping("/auto/1")
    public String authInfo(){
        return "auth ok";
    }

}

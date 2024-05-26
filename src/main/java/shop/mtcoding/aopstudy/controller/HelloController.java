package shop.mtcoding.aopstudy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.mtcoding.aopstudy.handler.aop.Hello;

@RestController
public class HelloController {
   
    @GetMapping("/hello1")
    public String v1(){
        System.out.println("컨트롤러");
        return "hello1";
    }

    @Hello
    @GetMapping("/hello2")
    public String v2(String username){
        System.out.println("컨트롤러");
        return "hello2";
    }
}

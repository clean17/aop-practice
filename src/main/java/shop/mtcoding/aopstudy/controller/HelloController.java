package shop.mtcoding.aopstudy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.mtcoding.aopstudy.handler.aop.Hello;

@RestController
public class HelloController {
   
    @GetMapping("/v1")
    public String v1(){
        System.out.println("테스트 : 순서");
        return "v1";
    }

    @Hello
    @GetMapping("/v2")
    public String v2(String username){
        System.out.println("테스트 : 순서");
        return "v2";
    }

    @Hello
    @GetMapping("/v3")
    public String v3(String username){
        // System.out.println("테스트 : 순서");
        return "v3";
    }
}

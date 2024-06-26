package shop.mtcoding.aopstudy.handler.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import shop.mtcoding.aopstudy.model.User;

public class LoginInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
            User principal = (User) request.getSession().getAttribute("principal");
            if (principal == null) {
                response.setContentType("text/html; charset=utf-8");
                response.getWriter().println("잘못된 접근입니다.");
                System.out.println("인터셉터");
                return false;
            }else{
                return true;
            }
    }
}

package shop.mtcoding.aopstudy.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class User { // 리플렉션은 private 필드에 접근도 한다고 ?
    private final Integer id;
    private final String username;
    private final String password;
    private final String tel;    
}

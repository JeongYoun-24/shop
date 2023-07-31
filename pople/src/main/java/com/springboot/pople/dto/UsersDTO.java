package com.springboot.pople.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class UsersDTO {
    @NotEmpty(message="아이디는 필수 입력 값입니다.")
    private String userid;
    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=4,max=16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요")
    private String password;
    @NotEmpty(message="이름은 필수 입력 값입니다.")
    private String name;
    @NotEmpty(message = "이메일은 필수 입력입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
    @NotEmpty(message = "번호는 필수 입력 값입니다.")
    private String phone;
    @NotEmpty(message = "생년월일은 필수 입력 값입니다.")
    private String birthDate;

    private LocalDateTime regDate;

}

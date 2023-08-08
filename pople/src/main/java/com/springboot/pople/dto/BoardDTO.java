package com.springboot.pople.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long id;

    private String title;

    private String content;

    private String writer;
    private int hitcount; // 조회수
    private LocalDateTime regDate;

}

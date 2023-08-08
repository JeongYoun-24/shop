package com.springboot.pople.dto;

import com.springboot.pople.dto.theater.TheaterFormDTO;
import com.springboot.pople.entity.Comment;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.Theater;
import com.springboot.pople.entity.Users;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CommentDTO {


    private Long id;

    private Long movieid;
    private String usersid;
    @NotEmpty
    @Size(min=3, max=100)
    private String title; // 제목
    private String content; // 댓글 내용
    private LocalDateTime regDate; // 작성 날짜
    private int star; //별점


    private static ModelMapper modelMapper = new ModelMapper();
    public static CommentDTO of(Comment comment){
        // entity -> dto
        return modelMapper.map(comment, CommentDTO.class);
    }


}

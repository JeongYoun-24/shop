package com.springboot.pople.dto;

import com.springboot.pople.entity.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class BoardFormDTO {

    private Long id;
    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max=100)
    private String title;
    @NotBlank(message = "공지내용은 필수 입력값입니다.")
    private String content;
    @NotBlank(message = "작성자는 필수 입력값입니다.")
    private String writer;
    private String hitcount;
    private LocalDateTime regDate;

    // 상품 저장 후 수정할 때 상품 이미지 정보를 저장하는 리스트
    private List<BoardImgDTO> boairImgDTOList = new ArrayList<>();
    // 상품의 이미지 아이디를 저장하는 리스트
    private List<Long> boardImgIds = new ArrayList<>();


    // entity -> DTO
    private static ModelMapper modelMapper = new ModelMapper();
    public static BoardFormDTO of (Board board){
        return modelMapper.map(board, BoardFormDTO.class);
    }
    // DTO -> entity
    public  Board createBoard(){
        return modelMapper.map(this, Board.class);
    }


}

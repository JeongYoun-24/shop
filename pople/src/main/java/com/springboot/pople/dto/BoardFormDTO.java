package com.springboot.pople.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardFormDTO {

    private Long id;
    @NotEmpty
    @Size(min=3, max=100)
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String writer;
    private int hitcount; // 조회수
    private LocalDateTime regDate;

    // 상품 저장 후 수정할 때 상품 이미지 정보를 저장하는 리스트
    private List<BoardDTO> boairImgList = new ArrayList<>();
    // 상품의 이미지 아이디를 저장하는 리스트
    private List<Long> boardImgIds = new ArrayList<>();


}

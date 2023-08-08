package com.springboot.pople.controller;

import com.springboot.pople.dto.BoardDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {


    @GetMapping(value = "list")
    public String getBoard(Model model){
        log.info("공지사항 리스트 ㄱㄱ");


        return "board/board";
    }
    @GetMapping(value = "form")
    public String getBoardForm(Model model){
        log.info("공지사항 리스트 작성 ㄱㄱ");


        return "board/boardForm";
    }
    @PostMapping(value ="form")
    public String postBoardForm(@Valid BoardDTO boardDTO, BindingResult bindingResult, Model model,
                                @RequestParam("boardImgFile") List<MultipartFile> itemImgFileList){
        log.info("공지사항 리스트 작성 ㄱㄱ");

//        redirect
        return "board/boardForm";
    }



}

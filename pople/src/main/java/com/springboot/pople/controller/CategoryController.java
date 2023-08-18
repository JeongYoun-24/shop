package com.springboot.pople.controller;

import com.springboot.pople.dto.item.CategoryDTO;
import com.springboot.pople.service.item.CategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@RequiredArgsConstructor
@Controller
//@RequestMapping("")
public class CategoryController {

        private final CategoryService categoryService;

    @PreAuthorize("isAuthenticated()") // 로그인 상태인 경우만 처리
    @GetMapping(value = "/category")
    public String GetategoryForm(){


        return "item/category";
    }


    @PostMapping(value = "/category")
    public String PostcategoryForm(String name,Long id){
        log.info("카테고리 등록 요청");
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(id)
                .name(name)
                .build();

        log.info(categoryDTO);

        Long Categoryid= categoryService.save(categoryDTO);



        return "item/category";
    }




}

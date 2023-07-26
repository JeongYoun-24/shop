package com.springstudy.shop.controller;

import com.springstudy.shop.dto.ItemFormDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
public class ItemController {


    @GetMapping(value = "/admin/item/new")
    public String item(Model model){
        model.addAttribute("itemFormDTO",new ItemFormDTO());

        return "item/itemForm";
    }
    @PostMapping(value = "/admin/item/new")
    public String itemNew(){
    log.info("======> /admin/item/new");

        return "redifect : /admin/item/new";
    }


}

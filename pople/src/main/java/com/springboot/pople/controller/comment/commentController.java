package com.springboot.pople.controller.comment;

import com.springboot.pople.dto.CommentDTO;
import com.springboot.pople.dto.CommentFormDTO;
import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.entity.Users;
import com.springboot.pople.repository.UsersRepository;
import com.springboot.pople.service.comment.MovieCommentService;
import com.springboot.pople.service.movie.MovieService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class commentController {
    private final MovieService movieService;
    private final MovieCommentService movieCommentService;
    private final UsersRepository usersRepository;

    // 댓글 페이지

    @GetMapping(value ="/form/{movieName}")
    public String getCommentFind(@PathVariable("movieName") String movieName, Model model,Principal principal){
        log.info("댓글 쓰기 페이지 요청 ");
        if(principal == null){
            model.addAttribute("msg","로그인후 이용가능 합니다.");
            return "users/login";
        }


        MovieDTO movieDTO = movieService.nameOne(movieName);
        log.info(movieDTO);
        model.addAttribute("movie",movieDTO);


        return "movie/commentForm";
    }

    // 댓글 데이터 저장
    @PostMapping (value ="/form")
    public String getComment(HttpServletRequest req, Model model,RedirectAttributes redirectAttributes){
        log.info("댓글 쓰기 페이지 요청 ");
        String movieid =req.getParameter("movieid");
        String movieName =req.getParameter("movieName");
        String userName =req.getParameter("userName");
        String content =req.getParameter("content");
        String title =req.getParameter("title");

        Long movieid1 = Long.parseLong(movieid);

        Users users =  usersRepository.findByName(userName);

        CommentDTO commentDTO = CommentDTO.builder()
                .content(content)
                .title(title)
                .movieid(movieid1)
                .usersid(users.getUserid())
                .build();


//        if (bindingResult.hasErrors()){
//            log.info("has errors ...");
//
//            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
//            return "redirect:/comment/form";
//        }

        Long id =  movieCommentService.register(commentDTO);

        redirectAttributes.addFlashAttribute("result", "("+movieName+")"+"영화에"+id+"번 댓글 작성완료.");
        return "redirect:/main";
    }


    @GetMapping(value ="/find/{commentid}")
    public String getComment(@PathVariable("commentid") Long commentid, Model model){
        log.info("댓글 쓰기 페이지 요청 ");

        CommentFormDTO commentDTO =  movieCommentService.readOne(commentid);

        log.info("값을 찾아라 "+commentDTO);
        model.addAttribute("comment",commentDTO);

        return "movie/commentFind";
    }

    @ResponseBody
    @PostMapping(value ="/remove")
    public String CommentRemove(@RequestBody HashMap<String,Object> map, Model model){
        log.info("댓글 쓰기 페이지 요청 ");
        String commentid = (String) map.get("commentid");

        Long id = Long.parseLong(commentid);

        movieCommentService.remove(id);

        return commentid;
    }




}

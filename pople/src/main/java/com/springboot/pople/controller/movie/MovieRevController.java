package com.springboot.pople.controller.movie;

import com.springboot.pople.dto.*;
import com.springboot.pople.entity.Users;
import com.springboot.pople.repository.MovieRevRepository;
import com.springboot.pople.repository.UsersRepository;
import com.springboot.pople.service.comment.MovieCommentService;
import com.springboot.pople.service.movie.MovieService;
import com.springboot.pople.service.movierev.MovieRevService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/movieRev")
public class MovieRevController {

    private final MovieService movieService;
    private final MovieCommentService movieCommentService;
    private final UsersRepository usersRepository;
    private final MovieRevRepository movieRevRepository;
    private final MovieRevService movieRevService;


    @GetMapping(value ="/form/{movieName}")
    public String getCommentFind(@PathVariable("movieName") String movieName, Model model, Principal principal){
        log.info("리뷰 쓰기 페이지 요청 ");

        if(principal == null){
            model.addAttribute("msg","로그인후 이용가능합니다.");
            return "/users/login";
        }


        MovieDTO movieDTO = movieService.nameOne(movieName);
        log.info(movieDTO);
        model.addAttribute("movie",movieDTO);


        return "movie/revForm";
    }
    @PostMapping(value ="/form") // 리뷰 쓰기
    public String getComment(HttpServletRequest req, Model model, RedirectAttributes redirectAttributes){
        log.info("댓글 쓰기 페이지 요청 ");
        String movieid =req.getParameter("movieid");
        String movieName =req.getParameter("movieName");
        String userName =req.getParameter("userName");
        String star =req.getParameter("star");
        String content =req.getParameter("content");
        String title =req.getParameter("title");

        int star1 = Integer.parseInt(star);
        Long movieid1 = Long.parseLong(movieid);

        Users users =  usersRepository.findByName(userName);

        MovieRevDTO movieRevDTO = MovieRevDTO.builder()
                .revContent(content)
                .revTitle(title)
                .star(star1)
                .movieId(movieid1)
                .userId(users.getUserid())
                .build();
        try {
            Long id =  movieRevService.register(movieRevDTO);
            redirectAttributes.addFlashAttribute("result", "("+movieName+")"+"영화에"+id+"번 리뷰 작성완료.");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("result", "("+movieName+")"+" 리뷰작성 오류(실패) ");

            return "redirect:/form/"+movieName;
        }

        return "redirect:/main";
    }

    @GetMapping(value ="/find/{revid}")
    public String getComment(@PathVariable("revid") Long revid, Model model){
        log.info("댓글 쓰기 페이지 요청 ");



        MovieRevFormDTO movieRevFormDTO =  movieRevService.readOne(revid);

        log.info("값을 찾아라 "+movieRevFormDTO);
        model.addAttribute("movieRev",movieRevFormDTO);

        return "movie/revFind";
    }

    @ResponseBody
    @PostMapping(value ="/remove")
    public String CommentRemove(@RequestBody HashMap<String,Object> map, Model model){
        log.info("댓글 쓰기 페이지 요청 ");
        String revid = (String) map.get("revid");

        Long id = Long.parseLong(revid);

        movieRevService.remove(id);





        return revid;
    }





}

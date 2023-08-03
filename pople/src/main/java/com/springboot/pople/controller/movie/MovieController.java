package com.springboot.pople.controller.movie;

import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.dto.MovieListCountDTO;
import com.springboot.pople.dto.PageRequestDTO;
import com.springboot.pople.dto.PageResponseDTO;
import com.springboot.pople.dto.movie.MovieFormDTO;
import com.springboot.pople.service.movie.MovieService;

import com.springboot.pople.service.movie.MovieService2;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/movie")
public class MovieController {

    private static String ARTICLE_IMAGE_REPO = "C:\\web\\pople\\src\\main\\resources\\static\\imgs";

    private final MovieService2 movieService2;
    private final MovieService movieService;


    @GetMapping("/findList")
    public String getfind(){

        return "movie/movieFind";
    }

    @GetMapping(value = "/find/{id}")
    public String getMovieFind(@PathVariable("id") Long id, Model model){
    log.info("영화 상세 정보 요청 ㄱㄱ~~");
    Long movieid = id;
    log.info(movieid);

      MovieFormDTO movieFormDTO =  movieService2.getMovieDtl(movieid);
      log.info(movieFormDTO.getMovieImgDTOList());


        model.addAttribute("movie",movieFormDTO);


        return "movie/movieFind";
    }




    @GetMapping("/register")
    public String getregister(){

        return "movie/register";
    }
    @PostMapping("/register")
    public String postregister(MovieDTO movieDTO ,Model model,HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {

//        movieDTO = new MovieDTO();

//        Map<String, String> articleMap = upload(req, resp);// 업로드기능 호출
//        movieDTO.setMovieName(articleMap.get("movieName"));
//        movieDTO.setMoviePoster(articleMap.get("moviePoster"));
//        movieDTO.setMovieSummary(articleMap.get("movieSummary"));
//        movieDTO.setMoveiRating(articleMap.get("movieRating"));
//        movieDTO.setMovieTime(articleMap.get("movieTime"));
//        movieDTO.setMovieDate(articleMap.get("movieDate"));

        movieDTO = MovieDTO.builder()
                .movieName(req.getParameter("movieName"))
                .moviePoster(req.getParameter("moviePoster"))
                .movieSummary(req.getParameter("movieSummary"))
                .moveiRating(req.getParameter("movieRating"))
                .movieTime(req.getParameter("movieTime"))
                .movieDate(req.getParameter("movieDate"))
                .build();
        System.out.println(movieDTO);



     Long result =  movieService.register(movieDTO);

        log.info(result);


        if(result == 19){
            model.addAttribute("isOk","등록완료");

        }else {
            model.addAttribute("isOk","등록실패");
        }

//       String isOk = "등록완료";


        return "movie/register";
    }
    @GetMapping("/isOk")
    public String loginerror(Model model){

        model.addAttribute("insertOk","등록완료");
        return "movie/register";
    }

    @GetMapping(value="/admin/register")
    public String itemForm(Model model){
        log.info("===> Get /admin/item/new 요청");

        model.addAttribute("movieFormDTO", new MovieFormDTO());
        return "movie/register";
    }

    // 상품 정보 DB등록 처리
    @PostMapping(value="/admin/register")
    public String itemNew(@Valid MovieFormDTO movieFormDTO, BindingResult bindingResult, Model model,
            @RequestParam("movieImgFile") List<MultipartFile> movieImgFileList   //"itemImgFile" 클라이언트로 넘겨받은 매개변수(files객체)
    ){
        log.info("===> Post /movie/admin/register 요청");

        if (bindingResult.hasErrors()){
            return "movie/register";
        }

        if(movieImgFileList.get(0).isEmpty() && movieFormDTO.getId() == null){
            model.addAttribute("errorMessage", "첫번째 영화 이미지는 필수 입력 값입니다.");
            return "movie/register";
        }

        try {
            // 전달 받은 데이터 서비스로 보내서 db 로 저장
            movieService2.saveMovie(movieFormDTO, movieImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage","영회 등록 중 에러가 발생하였습니다.");
            return "movie/register";
        }

        return "redirect:/main";
    }

    // 상품 조회
    @GetMapping(value = "/admin/movie/{movieId}")
    public String itemDtl(@PathVariable("movieId") Long movieId, Model model){

        try{
            MovieFormDTO movieFormDTO = movieService2.getMovieDtl(movieId);
            model.addAttribute("movieFormDTO",movieFormDTO);
            log.info("==> itemformDTO: "+movieFormDTO.getMovieImgDTOList());

        }catch (EntityNotFoundException e){

            model.addAttribute("errorMessage","존재하지 않는 상품입니다.");
            model.addAttribute("movieFormDTO", new MovieFormDTO());

            return "movie/register";
        }

        return "movie/register";
    }

    // 상품 정보 수정
    @PostMapping(value="/admin/movie/{movieId}")
    public String itemUpdate(
            @Valid MovieFormDTO movieFormDTO,
            BindingResult bindingResult,
            @RequestParam("movieImgFile2") List<MultipartFile> itemImgFileList,
            Model model){

        // 데이터 검증 확인
        if (bindingResult.hasErrors()){
            return "movie/register";
        }
        // 첨부파일 여부 체크
        if (itemImgFileList.get(0).isEmpty() && movieFormDTO.getId() == null){
            model.addAttribute("errorMessage", "첫번째 영화 이미지는 필수 입력 값입니다.");
            return "movie/register";
        }

        // 상품 수정 서비스 호출
        try{
            movieService2.updateItem(movieFormDTO, itemImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage","영화 수정 중 에러가 발생했습니다.");
            return "movie/register";
        }

        return "redirect:/main";
    }





//    private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        Map<String, String> articleMap = new HashMap<>();
//        String encoding = "utf-8";
//        //문자열 -<> 시스템 파일로 변환
//        File currentPath = new File(ARTICLE_IMAGE_REPO);
//
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//
//        factory.setRepository(currentPath);
//        factory.setSizeThreshold(1024*1024);
//
//        ServletFileUpload upload = new ServletFileUpload(factory);
//
//        try {
//            // req에 저장되어있는 매개변수를 List에 저장
//            List<FileItem> items = upload.parseRequest((RequestContext) request);
//
//
//            for(int i = 0; i < items.size(); i++) {
//
//                FileItem fileItem = items.get(i);
//                if(fileItem.isFormField()) { //form 요소이면
//                    System.out.println(fileItem.getFieldName() + fileItem.getString(encoding));
//
//                    articleMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
//
//                }else {
//
//                    System.out.println("파라미터이름 : " + fileItem.getFieldName());
//                    System.out.println("파일 이름 : " + fileItem.getName() );
//                    System.out.println("파일 크기 : "+ fileItem.getSize());
//
//
//
//                    articleMap.put(fileItem.getFieldName(),fileItem.getName());
//
//                    if(fileItem.getSize() > 0) {
//                        int idx = fileItem.getName().lastIndexOf("\\");
//                        if(idx == -1) {
//                            idx = fileItem.getName().lastIndexOf("/");
//
//                        }
//
//                        String fileName = fileItem.getName().substring(idx +1);
//                        File uploadFile = new File(currentPath +"\\"+"temp"+"\\"+fileName);
//
//                        fileItem.write(uploadFile);
//                    }
//
//                }
//            }
//
//        } catch (Exception e) {
//
//        }
//
//        return articleMap;
//    }




//    @GetMapping("/movieList")
//    public String movieList(PageRequestDTO pageRequestDTO, Model model, RedirectAttributes redirectAttributes){
//
//        PageResponseDTO<MovieListCountDTO> responseDTO = movieService.listReplyCount(pageRequestDTO);
//    Long movie_code = 1L;
////     MovieDTO movieDTO = movieService.readOne(movie_code);
//
////        log.info(responseDTO);
//        model.addAttribute("responseDTO", responseDTO);
////        model.addAttribute("responseDTO", movieDTO);
//
//
//        return  "views/movie";
//    }





}

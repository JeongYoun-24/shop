package com.springboot.pople.controller;

import com.springboot.pople.dto.*;
import com.springboot.pople.dto.movie.MovieFormDTO;
import com.springboot.pople.entity.*;
import com.springboot.pople.repository.OrderRepository;
import com.springboot.pople.repository.UsersRepository;
import com.springboot.pople.repository.movie.MovieRepository;
import com.springboot.pople.service.cinema.CinemaService;
import com.springboot.pople.service.movie.MovieService;
import com.springboot.pople.service.movie.MovieService2;
import com.springboot.pople.service.movieSchedule.MovieScheduleService;
import com.springboot.pople.service.order.OrderService;
import com.springboot.pople.service.seat.SeatService;
import com.springboot.pople.service.theater.TheaterFormService;
import com.springboot.pople.service.tickting.TicktingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("/tickting")
@RequiredArgsConstructor
public class TicktingController {

    private final TicktingService ticktingService;
    private final MovieScheduleService movieScheduleService;
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final MovieService2 movieService2;
    private final MovieRepository movieRepository;
    private final SeatService seatService;
    private final TheaterFormService theaterFormService;
    private final UsersRepository usersRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;


    // 예매 하기
    @ResponseBody
    @RequestMapping(value = "/form",method = {RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public  ResponseEntity order(@RequestBody HashMap<String,Object> map , BindingResult bindingResult, Principal principal)throws BindException {
        log.info("티켓예매 데이터 받음");
        String count = (String.valueOf( map.get("count")));
        String costs = (String.valueOf( map.get("costs")));
        String cinemaid = (String.valueOf( map.get("cinemaid")));
        String theaterid = (String.valueOf( map.get("theaterid")));
        String movieName = (String.valueOf( map.get("movieName")));
        String movieTime = (String.valueOf( map.get("movieTime")));
        String name = (String) map.get("userName");
        String scheduleid = (String) map.get("scheduleid");
        String movieRating = (String) map.get("movieRating");


        int count1 = Integer.parseInt(count);
        int costs1 = Integer.parseInt(costs);

        log.info("좌석수"+count);
        log.info("총가격"+costs);
        log.info("영화관Id"+cinemaid);
        log.info("상영관아이디"+theaterid);

        log.info("영화이름"+movieName);
        log.info("영화러닝타임"+movieTime);
        log.info("회원이름"+name);
        log.info("상영시간아이디"+scheduleid);
        

        Long id = Long.parseLong(cinemaid);
        Long theaterId =Long.parseLong(theaterid);
        Long scheduleId =Long.parseLong(scheduleid);

        log.info("이름값좀 나와라 "+name);
        Users users = usersRepository.findByName(name);

        log.info("dasdasdsdas"+users);



        CinemaDTO cinemaDTO = cinemaService.readOne(id);
        log.info(cinemaDTO);
        MovieDTO movieDTO = movieService.nameOne(movieName);
        log.info(movieDTO);

        TicktingDTO ticktingDTO = TicktingDTO.builder()
                .userid(users.getUserid())
                .phone(users.getPhone())
                .theaterCode(theaterId)
                .movieCode(movieDTO.getMovieid())
                .movieName(movieName)
                .movieTime(movieTime)
                .scheduleid(scheduleId)
                .cinemaName(cinemaDTO.getCinemaName())
                .count(count1)
                .ticktingPrice(costs1)
                .movieRating(movieRating)
                .dayDate(LocalDateTime.now())
                .regDate(LocalDateTime.now())

                .build();

        log.info(ticktingDTO);

        Long ticktingId;
        try{
            ticktingId = ticktingService.order(ticktingDTO,users.getEmail());
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        Long ticktingId2;
        try{
            ticktingId2 = ticktingService.register(ticktingDTO);
            log.info("저장값 "+ticktingId2);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


//        private Long movieCode;  // 영화 번호
//        private Long theaterCode;  // 상영관 번호
//        private LocalDateTime dayDate; // 애매 날짜
//        private String movieName; // 영화 이름
//        private String movieTime; // 영화 런닝타임
//        private String cinemaName; // 영화관 이름
//        private String seat;       // 좌석
//        private int count;       // 예매 인원수
//        private int ticktingPrice; // 티켓 가격
//        private String card;       // 카드사
//        private String cardNum;   // 카드 번호
//        private String phone;     // 핸드폰 번호
//        private String cardCheck;   // 카드 결제 유무
//        private LocalDateTime regDate;  // 결제 날짜


//        TicktingDTO ticktingDTO  = new TicktingDTO();


//        // 주문 정보를 받은 orderDTO객체 데이터 바인딩시 에러가 있는 체크
        if (bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();

            // FieldError: 지정한 에러가 발생하면 입력값이 초기화
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError: fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }

            // 데이터 검증 결과 에러시 , 에러코드와 메시지 전송
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
//
//        log.info("==> orderDTO:"+ticktingDTO.toString());
//
//        String email = principal.getName();// 로그인 아이디(이메일)
//        Long tickId;
//        try{
//            tickId = ticktingService.order(ticktingDTO, email);
//        }catch (Exception e){
//            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }

        // 주문이 정상적으로 처리되면 생성된 주문번호와 요청 성공했다는 http응답 상태코드를 반환
//        new ResponseEntity<Long>(tickId, HttpStatus.OK)
        return  new ResponseEntity<Long>(ticktingId, HttpStatus.OK);
    }

    // 2. 회원(고객) 구매 이력 조회

    @GetMapping(value = {"/orders","/orders/{page}"} )
    public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){

        if(principal == null){
            return "/users/login";
        }


        // 페이지 번호가 매개변수에 없으면 0으로 설정, 페이지에 보여질 레코드 수)
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0, 4);

        // 현재 로그인한 회원의 이메일과 페이징 객체를 인자로 전달하여 구매이력을 조회하는 서비스 요청
        Page<OrderHistDTO> orderHistDTOList = ticktingService.getOrderList(principal.getName(),pageable );
        log.info(orderHistDTOList);



        model.addAttribute("orders", orderHistDTOList);
        model.addAttribute("page", pageable.getPageNumber());// 총 페이지 수
        model.addAttribute("maxPage", 5);// 한줄에 보여질 페이지 번호 개수

        log.info("==> 구매이력");
        orderHistDTOList.getContent().forEach(x -> {
            log.info(x);
        });

        return "order/orderHist";
    }


    // 3. 예매 취소 처리
    @PostMapping(value="/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId, Principal principal){


        log.info("==> 주문취소 상품: "+orderId);
        // 주문 취소 권한 검사
        if (!ticktingService.validateOrder(orderId, principal.getName())){
            return new ResponseEntity("주문 취소 권한이 없습니다.",HttpStatus.FORBIDDEN);
        }
        log.info("==> 주문취소 요청: "+orderId);
        // 현재 로그인 사용자 상품 주문한 사용자가 동일하면 주문취소 서비스 처리
        ticktingService.cancelOrder(orderId);

        return new ResponseEntity(orderId, HttpStatus.OK);

    }
    @PostMapping(value="/order/{orderId}/find")
    public @ResponseBody ResponseEntity ticktingFind(@PathVariable("orderId") Long orderId, Principal principal){



        log.info("티켓조회좀 부탁합시다 .");
        OrderDTO orderDTO =   orderService.readOne(orderId);
        log.info("1232132132132132132133"+orderDTO);

        Optional<Order> order =orderRepository.findById(orderId);



        return new ResponseEntity(orderId, HttpStatus.OK);

    }
    // 2. 회원(고객) 구매 이력 조회
    @GetMapping(value = {"/tick","/tick/{page}"} )
    public String ticktingHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){
        if(principal == null){
            return "/users/login";
        }
        // 페이지 번호가 매개변수에 없으면 0으로 설정, 페이지에 보여질 레코드 수)
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0, 4);
        Page<TicktingHistDTO> orderHistDTOList = ticktingService.getTickList(principal.getName(),pageable );



        // 현재 로그인한 회원의 이메일과 페이징 객체를 인자로 전달하여 구매이력을 조회하는 서비스 요청
//        Page<TicktingDTO> orderHistDTOList = ticktingService.getTicktingList(principal.getName(),pageable );
//        Page<MovieImg> orderHistDTOList2 = ticktingService.getImgTick(principal.getName(),pageable );
//        log.info(orderHistDTOList);
//        log.info("이미지받아라~~~!~!~"+orderHistDTOList2);
//
//
//        model.addAttribute("img", orderHistDTOList2);
        model.addAttribute("orders", orderHistDTOList);
        model.addAttribute("page", pageable.getPageNumber());// 총 페이지 수
        model.addAttribute("maxPage", 5);// 한줄에 보여질 페이지 번호 개수

        log.info("==> 구매이력");
        orderHistDTOList.getContent().forEach(x -> {
            log.info(x);
        });

        return "order/test";
    }




}

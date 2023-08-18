package com.springboot.pople.service.tickting;

import com.springboot.pople.constant.TicktingStatus;
import com.springboot.pople.dto.*;
import com.springboot.pople.dto.movie.MovieFormDTO;
import com.springboot.pople.dto.movie.MovieImgDTO;
import com.springboot.pople.entity.*;
import com.springboot.pople.repository.MovieImgRepository;
import com.springboot.pople.repository.OrderRepository;
import com.springboot.pople.repository.TicktingRepository;
import com.springboot.pople.repository.UsersRepository;
import com.springboot.pople.repository.movie.MovieRepository;
import com.springboot.pople.service.movie.MovieService2;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class TicktingService {

    private final MovieRepository movieRepository;
    private final UsersRepository usersRepository;
    private final MovieImgRepository movieImgRepository;
    private final TicktingRepository ticktingRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final MovieService2 movieService2;
    public Long register(TicktingDTO ticktingDTO) {
        log.info("티켓 저장값 나와라 "+ticktingDTO);

        Users users = Users.builder().userid(ticktingDTO.getUserid()).build();
        Movie movie = Movie.builder().movieid(ticktingDTO.getMovieCode()).build();
        Theater theater = Theater.builder().id(ticktingDTO.getTheaterCode()).build();
        MovieSchedule movieSchedule =MovieSchedule.builder().id(ticktingDTO.getScheduleid()).build();

        Tickting tickting = Tickting.builder()
                .ticktingPrice(ticktingDTO.getTicktingPrice()) // 가격
                .movieName(ticktingDTO.getMovieName())        // 영화 이름
                .cinemaName(ticktingDTO.getCinemaName())     // 영화관 이름
                .dayDate(ticktingDTO.getDayDate())            // 상영날짜
                .card(ticktingDTO.getCard())                 // 카드사
                .cardCheck(ticktingDTO.getCardCheck())        // 카드 결제 여부
                .movieCode(ticktingDTO.getMovieCode())       // 영화 아이디
                .phone(ticktingDTO.getPhone())              // 회원 전화번호
                .movieTime(ticktingDTO.getMovieTime())          // 영화 러닝타임
                .movieRating(ticktingDTO.getMovieRating()) // 관람등급
                .regDate(LocalDateTime.now())    // 결제 날짜
                .users(users)                                       // 회원정보
                .movieSchedule(movieSchedule)                  // 상영일정 아이디
                .theaterCode(ticktingDTO.getTheaterCode())     // 상영관 아이디
                .ticktingStatus(TicktingStatus.Reserve)     // 예역 여부                                           // 결제 날짜
                .count(ticktingDTO.getCount())                  // 인원수
                .seat(ticktingDTO.getSeat())                    // 좌석번호
                .build();

//        Tickting tickting = modelMapper.map(ticktingDTO, Tickting.class);
        Long ticktingid = ticktingRepository.save(tickting).getId();


        return ticktingid;
    }



    // 상품 주문 서비스
    public Long order(TicktingDTO ticktingDTO, String email){

     Movie movie1 =  movieRepository.findByMovieName(ticktingDTO.getMovieName());
        ticktingDTO.setMovieCode(movie1.getMovieid());
        log.info("값을찾아줘세요"+ticktingDTO.getTicktingPrice());
        
        

        // 주문 상품에 대한 기본 정보 조회
        Movie movie = movieRepository.findById(ticktingDTO.getMovieCode())
                .orElseThrow(EntityNotFoundException::new);

        // 현재 로그인한 회원의 이메일(아이디) 정보를 이용해서 회원 정보조회
        Users users = usersRepository.findByEmail(email);

        // 주문 상품목록을 저장할 객체 생성
        List<OrderMovie> orderMovieList = new ArrayList<>();

        // 주문할 상품 엔티티와 주문 수량을 이용하여 주문 상품 엔티티를 생성
        OrderMovie orderMovie = OrderMovie.createOrderItem(movie, ticktingDTO.getCount(),ticktingDTO.getTicktingPrice());
         log.info("최종값은??"+orderMovie);

        // 생성된 주문 상품 엔티티를 리스트에 보관
        orderMovieList.add(orderMovie);
        // 회원정보, 주문할 상품 리스트 정보를 가지고 주문 엔티티 생성
        Order order = Order.createOrder(users, orderMovieList);
        // 생성된 주문 엔티티를 저장
        orderRepository.save(order);

        return order.getId();

    }
    // 주문 목록 서비스
    @Transactional(readOnly = true)
    public Page<OrderHistDTO> getOrderList(String Name, Pageable pageable){
        log.info("이름??"+Name);
        // 1. 사용자의 아이디(email)와 페이징 조건을 이용해 주문 목록 조회 요청
        List<Order> orders = orderRepository.findOrders(Name, pageable);

        // 2. 사용자의 총 주문 개수
        Long totalCount = orderRepository.countOreder(Name);

        // 3. 검색하여 가져온 주문 목록을 순회하여 구매이력 페이지에 전달할 DTO생성
        List<OrderHistDTO> orderHistDTOS = new ArrayList<>();


        for(Order order : orders){
            OrderHistDTO orderHistDTO = new OrderHistDTO(order);// 주문내역

            // 성능저하 : for문 순회할 때마다 매번 조회쿼리문이 추가 되어 실행
            /*
            쿼리문 실행결과 =>
                from order_item orderitems0_  where   orderitems0_.order_id=?

                해결 방안
                # 기본 batch size 설정
                # 조회 쿼리 한 번으로 지정한 사이즈 만큼 한 번에 조회
                spring.jpa.properties.hibernate.default_batch_fetch_size=1000

            쿼리문 실행 결과 =>
                from order_item orderitems0_  where  orderitems0_.order_id in ( ?, ?, ?, ? )
             */
            List<OrderMovie> orderMoview = order.getOrderMovies();// 주문상품


            for(OrderMovie orderItem: orderMoview){     // 주문 상품 대표 이미지 조회: 주문서에 있는 상품entity로 통행 상품id추출하여 상품대표상품이미지 조회
                MovieImg movieImg = movieImgRepository
                        .findByMovie_MovieidAndRepImgYn((orderItem.getMovie().getMovieid()),"Y");

                // 주문 상품 정보(수량, 개수...), 상품 이미지
                OrderMovieDTO orderMovieDTO = new OrderMovieDTO(orderItem, movieImg.getImgUrl());

                // 주문이력 리스트에 주문상품 추가
                orderHistDTO.addOrderitemDTO(orderMovieDTO);

                log.info("==> 주문 이력 상품 목록");
                log.info(orderItem.getMovie().toString());
                log.info(movieImg.getImgUrl().toString());
                log.info(orderMovieDTO.toString());
                log.info("===");
                List<Tickting> ticktings = ticktingRepository.findTicktingss(Name);
                for(Tickting tickting : ticktings) {
                    TicktingDTO ticktingDTO = TicktingDTO.builder()
                            .phone(tickting.getPhone())
                            .movieName(tickting.getMovieName())
                            .movieTime(tickting.getMovieTime())
                            .movieCode(tickting.getMovieCode())
                            .cinemaName(tickting.getCinemaName())
                            .dayDate(tickting.getDayDate())
                            .theaterCode(tickting.getTheaterCode())
                            .ticktingPrice(tickting.getTicktingPrice())
                            .count(tickting.getCount())
                            .build();

                    orderHistDTO.TicktingTOList(ticktingDTO);

                }





            }// end for (orderItems : 주문서에 있는 주문상품목록)

            orderHistDTOS.add(orderHistDTO); // 주문이력 List객체에 주문이력 상품 추가

            log.info("==> 고객이 주문한 주문서목록 (1:N)");
            log.info(orderHistDTOS.toString());
            log.info("===");

        } // end for (orders: 주문목록)


        return new PageImpl<>(orderHistDTOS, pageable, totalCount);
    }
    @Transactional(readOnly = true)
    public Page<TicktingHistDTO> getTickList(String Name, Pageable pageable){

        // 1. 사용자의 아이디(email)와 페이징 조건을 이용해 주문 목록 조회 요청
        List<Tickting> ticktings = ticktingRepository.findTicktings(Name, pageable);

        // 2. 사용자의 총 주문 개수
        Long totalCount = orderRepository.countOreder(Name);

        // 3. 검색하여 가져온 주문 목록을 순회하여 구매이력 페이지에 전달할 DTO생성
        List<TicktingHistDTO> orderHistDTOS = new ArrayList<>();


        for(Tickting tickting : ticktings){
            TicktingHistDTO ticktingHistDTO = new TicktingHistDTO(tickting);// 주문내역

            List<Tickting> orderMoview = tickting.getOrderMovies();// 주문상품



//            for(OrderMovie orderItem: orderMoview){     // 주문 상품 대표 이미지 조회: 주문서에 있는 상품entity로 통행 상품id추출하여 상품대표상품이미지 조회
//                MovieImg movieImg = movieImgRepository
//                        .findByMovie_MovieidAndRepImgYn((orderItem.getMovie().getMovieid()),"Y");
//
//                // 주문 상품 정보(수량, 개수...), 상품 이미지
//                OrderMovieDTO orderMovieDTO = new OrderMovieDTO(orderItem, movieImg.getImgUrl());
//
//                // 주문이력 리스트에 주문상품 추가
//                orderHistDTO.addOrderitemDTO(orderMovieDTO);

               // 주문 상품 대표 이미지 조회: 주문서에 있는 상품entity로 통행 상품id추출하여 상품대표상품이미지 조회
                MovieImg movieImg = movieImgRepository
                        .findByMovie_MovieidAndRepImgYn((tickting.getMovieCode()),"Y");

                // 주문 상품 정보(수량, 개수...), 상품 이미지
                TicktingOrderDTO ticktingOrderDTO = new TicktingOrderDTO(tickting, movieImg.getImgUrl());

                // 주문이력 리스트에 주문상품 추가
            ticktingHistDTO.addMovieDTO(ticktingOrderDTO);
            log.info("==> 주문 이력 상품 목록");
            log.info(tickting.getMovieCode());
            log.info(movieImg.getImgUrl().toString());
            log.info(ticktingHistDTO.toString());
            log.info("===");



            orderHistDTOS.add(ticktingHistDTO); // 주문이력 List객체에 주문이력 상품 추가

            log.info("==> 고객이 주문한 주문서목록 (1:N)");
            log.info(orderHistDTOS.toString());
            log.info("===");

        } // end for (orders: 주문목록)


        return new PageImpl<>(orderHistDTOS, pageable, totalCount);
    }



    // 로그인한 사용자와 주문 데이터를 생성한 사용자가 같은지 검사, 같은 경우 true반환
    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String Name){
        Users curUsers = usersRepository.findByName(Name);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        // 현재 로그인 사용자와 상품주문한 사용자가 동일한지 검사
        Users saveUsers = order.getUsers();
        if (!StringUtils.equals(curUsers.getName(), saveUsers.getName())){
            return false;// 일치하지 않으면 false
        }

        return true;
    }

    // 주문 취소 상태로 변경하면 변경 감지 기능에 의해서 트랜잭션이 끝날 대 update쿼리가 실행
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

       order.cancelOrder();
    }



    @Transactional(readOnly = true)
    public Page<MovieImg> getImgTick(String Name, Pageable pageable){
        // 1. 사용자의 아이디(email)와 페이징 조건을 이용해 주문 목록 조회 요청
        List<Tickting> ticktings = ticktingRepository.findTicktings(Name, pageable);
        log.info("ㅇㄴㄹㅇㄴㄹㅇㄴㄹㅇㄹ"+ticktings);
        // 2. 사용자의 총 주문 개수
        Long totalCount = ticktingRepository.countTickting(Name);

        // 3. 검색하여 가져온 주문 목록을 순회하여 구매이력 페이지에 전달할 DTO생성
        List<MovieImg> orderHistDTOS = new ArrayList<>();

        for(Tickting order : ticktings){
            TicktingDTO orderHistDTO = TicktingDTO.builder()
                    .phone(order.getPhone())
                    .movieName(order.getMovieName())
                    .movieTime(order.getMovieTime())
                    .movieCode(order.getMovieCode())
                    .cinemaName(order.getCinemaName())
                    .dayDate(order.getDayDate())
                    .theaterCode(order.getTheaterCode())
                    .ticktingPrice(order.getTicktingPrice())
                    .count(order.getCount())
                    .build();
//            MovieImg movieImg = movieImgRepository.findByMovie_MovieidAndRepImgYn((orderHistDTO.getMovieCode()),"Y");

            // 주문 상품 대표 이미지 조회: 주문서에 있는 상품entity로 통행 상품id추출하여 상품대표상품이미지 조회
//            MovieFormDTO movieFormDTO =  movieService2.getMovieDtl(orderHistDTO.getMovieCode());
            MovieImg movieImg = movieImgRepository.findByMovie_MovieidAndRepImgYn((orderHistDTO.getMovieCode()),"Y");
            log.info("==> 주문 이력 상품 목록");
            log.info(movieImg.getImgUrl().toString());
            log.info(movieImg.toString());
            log.info("===");


            orderHistDTOS.add(movieImg); // 주문이력 List객체에 주문이력 상품 추가

            log.info("==> 고객이 주문한 주문서목록 (1:N)");
            log.info(orderHistDTOS.toString());
            log.info("===");

        } // end for (orders: 주문목록)

        return new PageImpl<>(orderHistDTOS, pageable, totalCount);
    }






    // 로그인한 사용자와 주문 데이터를 생성한 사용자가 같은지 검사, 같은 경우 true반환
//    @Transactional(readOnly = true)
//    public boolean validateOrder(Long orderId, String email){
//        Users curMember = usersRepository.findByEmail(email);
//
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(EntityNotFoundException::new);
//
//        // 현재 로그인 사용자와 상품주문한 사용자가 동일한지 검사
//        Users saveMember = order.getUsers();
//        if (!StringUtils.equals(curMember.getEmail(), saveMember.getEmail())){
//            return false;// 일치하지 않으면 false
//        }
//
//        return true;
//    }





}

package com.springboot.pople.controller;

import com.springboot.pople.dto.*;
import com.springboot.pople.dto.item.CartDetailDTO;
import com.springboot.pople.dto.item.CartItemDTO;
import com.springboot.pople.dto.item.ItemFormDTO;
import com.springboot.pople.dto.item.ItemSearchDTO;
import com.springboot.pople.entity.Item;
import com.springboot.pople.service.cart.CartService;
import com.springboot.pople.service.item.ItemService;
import com.springboot.pople.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final CartService cartService;
    private final OrderService orderService;


    @GetMapping(value ="list")
    public String getItem(Model model){
        log.info("상품 주소 ㄱㄱ ");
        /*List<BoardDTO> boardDTO = boardService.AllList();
        log.info("1111"+boardDTO);*/

       /* model.addAttribute("board",boardDTO);*/

        return "item/itemTest";
    }


    @GetMapping(value="/admin/item/new")
    public String itemForm(Model model){
        log.info("===> Get /admin/item/new 요청");

        model.addAttribute("itemFormDTO", new ItemFormDTO());



        return "item/itemForm";
    }

    // 상품 정보 DB등록 처리
    @PostMapping(value="/admin/item/new")
    public String itemNew(@Valid ItemFormDTO itemFormDTO, BindingResult bindingResult, Model model,
            @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList   //"itemImgFile" 클라이언트로 넘겨받은 매개변수(files객체)
    ){
        log.info("===> Post /admin/item/new 요청");

        if (bindingResult.hasErrors()){
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDTO.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다.");
            return "item/itemForm";
        }

        try {
            itemService.saveItem(itemFormDTO, itemImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage","상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/main";
    }

    // 상품 조회
    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){

        try{
            ItemFormDTO itemFormDTO = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDTO",itemFormDTO);
            log.info("==> itemformDTO: "+itemFormDTO.getItemImgDTOList());

        }catch (EntityNotFoundException e){

            model.addAttribute("errorMessage","존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDTO", new ItemFormDTO());

            return "item/itemForm";
        }

        return "item/itemForm";
    }

    // 상품 정보 수정
    @PostMapping(value="/admin/item/{itemId}")
    public String itemUpdate(
            @Valid ItemFormDTO itemFormDTO,
            BindingResult bindingResult,
            @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
            Model model){

        // 데이터 검증 확인
        if (bindingResult.hasErrors()){
            return "item/itemForm";
        }
        // 첨부파일 여부 체크
        if (itemImgFileList.get(0).isEmpty() && itemFormDTO.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다.");
            return "item/itemForm";
        }

        // 상품 수정 서비스 호출
        try{
            itemService.updateItem(itemFormDTO, itemImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage","상품 수정 중 에러가 발생했습니다.");
            return "item/itemForm";
        }

        return "redirect:/";
    }

    // 상품 관리 조회 - 관리자 페이지
//    @GetMapping(value={"/admin/items","/admin/items/{page}"})
//    public String itemMange(ItemSearchDTO itemSearchDTO,
//                            @PathVariable("page") Optional<Integer> page,
//                            Model model){
//
//        Pageable pageable = PageRequest.of(page.isPresent()? page.get(): 0, 5);
//        Page<Item> items = itemService.getAdminItemPage(itemSearchDTO, pageable);
//
//        model.addAttribute("items", items);
//        model.addAttribute("itemSearchDTO", itemSearchDTO);
//        model.addAttribute("maxPage",5);// 하단에 보여질 페이지번호 범위(페이지 블럭)
//
//        return "item/itemMng";
//    }

    // 상품 상세 페이지
    @GetMapping(value="/list/{categoryid}")
    public String itemList(Model model, @PathVariable("categoryid") Long categoryid){



//        ItemFormDTO itemFormDTO = itemService.getItemDtl(categoryid);
//        model.addAttribute("item",itemFormDTO);


        return "item/itemDtl";
    }


    @GetMapping(value="/category/{categoryid}")
    public String itemDtl(Model model, @PathVariable("categoryid") Long categoryid){
//        List<ItemFormDTO> itemFormDTO = itemService.itemList(categoryid);
        List<ItemFormDTO> itemFormDTOList=  itemService.itemList(categoryid);
        log.info(itemFormDTOList);


        model.addAttribute("itemList",itemFormDTOList);
        model.addAttribute("categoryid",categoryid);

//        log.info("=> 상품 상세페이지");
//        log.info("item => "+itemFormDTO.toString());
//        log.info("================");


        return "item/itemList";
    }
    @GetMapping(value="/itemFind/{itemId}")
    public String itemFind(Model model, @PathVariable("itemId") Long itemId){
    log.info("상품 상세정보");
    
        try{
            ItemFormDTO itemFormDTO = itemService.getItemDtl(itemId);
            model.addAttribute("item",itemFormDTO);
            log.info("==> itemformDTO: "+itemFormDTO);
            log.info("==> itemformDTO: "+itemFormDTO.getItemImgDTOList());


        }catch (EntityNotFoundException e){

            model.addAttribute("errorMessage","존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDTO", new ItemFormDTO());

            return "item/itemForm";
        }


        return "item/itemFind";
    }


    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDTO cartItemDTO, BindingResult bindingResult, Principal principal){
        // 장바구니에 담을 상품 정보를 받아 cartItemDTO 객체에 데이터 검증하여 에러 유무 처리 체크
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            // 현제 로그인한 회원의 이메일 정보를 변수에 저장
            List<FieldError> fileErrors = bindingResult.getFieldErrors();
            // 필드항목 순회하여 에러가 있을경우 메세지 작성하여 응답
            for(FieldError fieldError: fileErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        // 현제 로그인한 회원의 이메일 정보를 변수에 저장
        String name = principal.getName();
        Long cartItemId;
        try {
            cartItemId = cartService.addCart(cartItemDTO,name);

        }catch (Exception e){

            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(cartItemId,HttpStatus.OK);
    }

    @PostMapping(value="/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDTO2 orderDTO, BindingResult bindingResult,Principal principal){

        // 주문 정보를 받은 orderDTO객체 데이터 바인딩시 에러가 있는 체크
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

        log.info("==> orderDTO:"+orderDTO.toString());

        String name = principal.getName();// 로그인 아이디(이메일)
        Long orderId;
        try{
            orderId = orderService.order(orderDTO, name);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        // 주문이 정상적으로 처리되면 생성된 주문번호와 요청 성공했다는 http응답 상태코드를 반환
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }


    // 2. 회원(고객) 구매 이력 조회
    @PreAuthorize("isAuthenticated()") // 로그인 상태인 경우만 처리
    @GetMapping(value = {"/test","/test/{page}"} )
    public String ticktingHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){
        if(principal == null){
            return "/users/login";
        }
        // 페이지 번호가 매개변수에 없으면 0으로 설정, 페이지에 보여질 레코드 수)
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0, 4);
        Page<OrderHistDTO2> orderHistDTOList = orderService.getOrderList(principal.getName(),pageable );


        model.addAttribute("orders", orderHistDTOList);
        model.addAttribute("page", pageable.getPageNumber());// 총 페이지 수
        model.addAttribute("maxPage", 5);// 한줄에 보여질 페이지 번호 개수
        log.info(" ㅋㅋㅋ"+orderHistDTOList);


        log.info("==> 구매이력");
        orderHistDTOList.getContent().forEach(x -> {
            log.info(x);
        });

        return "order/orderitem";
    }

    // 2. 회원(고객) 장바구니 이력 조회
//    @GetMapping(value = {"/cart","/cart/{page}"} )
//    public String itemCart(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){
//
//        // 페이지 번호가 매개변수에 없으면 0으로 설정, 페이지에 보여질 레코드 수)
//        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0, 4);
//
//
//        return "cart/cart";
//    }

//    @GetMapping(value ={"cart","cart/{page}"})
//    public String cartList(@PathVariable("page")Optional<Integer> page,  Principal principal, Model model){
//        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0, 4);
//        // 현재 사용자 이름을 이용하여 장바구니에 담겨있는 상품정보를 조회
//        log.info("ㅎㅇ?");
//        List<CartDetailDTO> cartDetailDTOList = cartService.cartList(principal.getName(),pageable);
//
//
//
//
//
//        // 조회한 장바구니 상품정보를 뷰로 전달
//        model.addAttribute("cartList",cartDetailDTOList);
//
//
//
//        return "cart/cart";
//    }



    // 3. 장바구니에서 주문하기 요청처리


}

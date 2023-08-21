package com.springboot.pople.repository.cart;

import com.springboot.pople.dto.item.CartDetailDTO;
import com.springboot.pople.entity.CartItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository  extends JpaRepository<CartItem,Long> {
    // 1. 장바구니 아이디와 상품아이디를 이용해서 상품이 장바구니에 들어있는 조회
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    List<CartItem> findByCartId(Long cartId, Pageable pageable);

    @Query("select count(o) from CartItem o where o.cart.id = :id")
    Long countCartItem(@Param("id") Long id);


    @Query(value = "select new com.springboot.pople.dto.item.CartDetailDTO(ci.id, i.itemName, i.itemDetail,i.price, ci.count, im.imgUrl)" +
            " from CartItem ci, ItemImg im " +
            // 상품 정보 조인
            " join ci.item i " +
            //장바구니 아이디와 인자로 넘오온 장바구니아이디(조회하고자하는 장바구니)
            " where ci.cart.id = :orderid " +
            // 장바구니 상품아이디와 상품이미지에 있는 상품아이디 조인
            "       and im.item.id = ci.item.id "+
            // 장바구니에 보여질 상품대표이미지만 가져오는 조건처리
            "       and im.repImgYn = 'Y' " +
            " order by ci.regDate desc " )
    List<CartDetailDTO> findCartDetailDTOList(@Param("orderid") Long orderid);


//    @Modifying
//    @Query(value = ("select new com.springboot.pople.dto.item.CartDetailDTO(ci.id,i.itemName,i.price,ci.count,im.imgUrl)" +
//            " form CartItem ci,ItemImg im " +
//            // 상품 정보 조인
//            " join ci.item i "+
//            // 장바구니 아이디와 인자로 넘어오면 장바구니아이디 (조회하고자하는 장바구니)
//            " where ci.cart.id=:id "+
//            // 장바구니 상품아이디와 상품이미지에 잇는 상품아이디 조인
//            " and im.item.id = ci.item.id "+
//            // 장바구니에 보여질 상품대표 이미지 만 가져오는 조건처리
//            " and im.repImgYn = 'Y' " +
//            " order by ci.regDate desc "
//    ),nativeQuery=true)
//    List<CartDetailDTO> findCartDetailDTOList(@Param("id") Long id);
//
//    @Query(" SELECT c.cart_id, p.imgUrl, p.imteName, p.price, c.count"+
//           " FROM CartItem c"+
//            "JOIN ItemImg p ON c.item.id = p.item.id"+
//            "WHERE c.cart.cart_id = :id"
//            )
//    List<CartItem> findCartDetailDTOList22(@Param("id") Long id);



//    @Modifying
//    @Query(value =("delete from Board_Img where board_id=:boardid"), nativeQuery=true)
//    void findDelete(@Param("boardid")Long boardid);


    // 2. 장바구니 페이지에 전달 할 CartDeatilDTO리스트를 쿼리하나로 조회하는 JPQL문

//    @Query("")
//    List<CartDetailDTO> findCartDetailDTOList(Long cartId);


}

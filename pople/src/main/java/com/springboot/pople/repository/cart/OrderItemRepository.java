package com.springboot.pople.repository.cart;

import com.springboot.pople.dto.item.CartDetailDTO;
import com.springboot.pople.dto.item.OrderDetailDTO;
import com.springboot.pople.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository  extends JpaRepository<OrderItem,Long> {


    @Query(value = "select new com.springboot.pople.dto.item.OrderDetailDTO(ci.id, i.itemName, i.itemDetail,ci.orderPrice, ci.count, im.imgUrl )" +
            " from OrderItem ci, ItemImg im " +
            // 상품 정보 조인
            " join ci.item i " +
            " where ci.order.id = :id " +
            "       and im.item.id = ci.item.id "+
            "       and im.repImgYn = 'Y' " +
            " order by ci.regDate desc " )
    List<OrderDetailDTO> findOrderDetailDTOList(@Param("id") Long id);

//    @Query(value = "select new com.springboot.pople.dto.item.CartDetailDTO(ci.id, i.itemName, i.itemDetail,i.price, ci.count, im.imgUrl )" +
//            " from CartItem ci, ItemImg im " +
//            // 상품 정보 조인
//            " join ci.item i " +
//            //장바구니 아이디와 인자로 넘오온 장바구니아이디(조회하고자하는 장바구니)
//            " where ci.cart.id = :cartId " +
//            // 장바구니 상품아이디와 상품이미지에 있는 상품아이디 조인
//            "       and im.item.id = ci.item.id "+
//            // 장바구니에 보여질 상품대표이미지만 가져오는 조건처리
//            "       and im.repImgYn = 'Y' " +
//            " order by ci.regDate desc " )
//    List<CartDetailDTO> findCartDetailDTOList(@Param("cartId") Long cartId);



}

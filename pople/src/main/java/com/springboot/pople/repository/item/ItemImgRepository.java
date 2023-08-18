package com.springboot.pople.repository.item;

import com.springboot.pople.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository  extends JpaRepository<ItemImg, Long> {

    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

    // 주문 상품 대표이미지 조회
    ItemImg findByItemIdAndRepImgYn(Long itemId, String repimgYn);

}

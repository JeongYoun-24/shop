package com.springstudy.shop.repository;

import com.springstudy.shop.dto.ItemSearchDTO;
import com.springstudy.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);


}

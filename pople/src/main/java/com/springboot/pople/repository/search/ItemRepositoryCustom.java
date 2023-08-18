package com.springboot.pople.repository.search;

import com.springboot.pople.dto.item.ItemSearchDTO;
import com.springboot.pople.dto.item.MainItemDTO;
import com.springboot.pople.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
//    Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);

    Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);
}

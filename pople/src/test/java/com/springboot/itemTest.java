package com.springboot;

import com.springboot.pople.PopleApplication;
import com.springboot.pople.dto.item.ItemFormDTO;
import com.springboot.pople.entity.Category;
import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.Item;
import com.springboot.pople.repository.OrderRepository;
import com.springboot.pople.repository.item.ItemRepository;
import com.springboot.pople.service.item.ItemService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@Log4j2
@AutoConfigureMockMvc
@SpringBootTest(classes = PopleApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class itemTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ItemService itemService;


    @Test
    @DisplayName("Cinema 영화 조회 테스트 ")
    public void testselect(){
        Long id = 3L;

        List<Item> movieList = itemRepository.findByCategoryId(id);
        movieList.forEach(item -> {
            log.info(item);
        });

//        List<ItemFormDTO> itemFormDTOList=  itemService.itemList(id);
//
//     log.info(itemFormDTOList);

    }


}

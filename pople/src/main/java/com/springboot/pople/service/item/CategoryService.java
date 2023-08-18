package com.springboot.pople.service.item;

import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.dto.item.CategoryDTO;
import com.springboot.pople.entity.Category;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public Long save (CategoryDTO categoryDTO){

        Category category = modelMapper.map(categoryDTO, Category.class);
        Long categoryId = categoryRepository.save(category).getId();



        return categoryId;
    }

    public CategoryDTO readOne(Long categoryId){
        Optional<Category> category=  categoryRepository.findById(categoryId);

        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);

        return categoryDTO;
    }
    public void modify(MovieDTO movieDTO){

    }
    public void remove(Long movieid){

    }



}

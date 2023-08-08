package com.springboot.pople.service.movierev;

import com.springboot.pople.dto.CommentFormDTO;
import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.dto.MovieRevDTO;
import com.springboot.pople.dto.MovieRevFormDTO;
import com.springboot.pople.entity.MovieRev;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieRevService {


    public Long register(MovieRevDTO movieRevDTO);
    public MovieRevFormDTO readOne(Long movieid);
    public void modify(MovieRevDTO movieRevDTO);
    public void remove(Long movieid);

    public Page<MovieRevFormDTO> findList(Long movieid , Pageable pageable);



}

package com.springboot.pople.service.comment;

import com.springboot.pople.dto.CommentDTO;
import com.springboot.pople.dto.CommentFormDTO;
import com.springboot.pople.dto.TheaterDTO;
import com.springboot.pople.dto.theater.TheaterFormDTO;
import com.springboot.pople.entity.Comment;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.Theater;
import com.springboot.pople.entity.Users;
import com.springboot.pople.repository.MovieCommentRepository;
import com.springboot.pople.repository.UsersRepository;
import com.springboot.pople.repository.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MovieCommentService {


    private final ModelMapper modelMapper;
    private final MovieCommentRepository movieCommentRepository;//dao
    private final MovieRepository movieRepository;
    private final UsersRepository usersRepository;

    public Long register(CommentDTO commentDTO) {
        // dto -> entity로 데이터 복사
        Movie movie = Movie.builder().movieid(commentDTO.getMovieid()).build();

        Users users =Users.builder().userid(commentDTO.getUsersid()).build();


        Comment comment = Comment.builder()
                .movie(movie)
                .users(users)
                .content(commentDTO.getContent())
                .title(commentDTO.getTitle())
                .star(commentDTO.getStar())
                .regDate(LocalDateTime.now())
                .build();


        Long id = movieCommentRepository.save(comment).getId();

        return id;
    }


    public CommentFormDTO readOne(Long id) {
        Optional<Comment> result = movieCommentRepository.findById(id);
        Comment comment = result.orElseThrow();


//        CommentDTO boardDto = modelMapper.map(board, CommentDTO.class);

        CommentFormDTO commentDTO = CommentFormDTO.builder()
                .id(comment.getId())
                .users(comment.getUsers())
                .movie(comment.getMovie())
                .title(comment.getTitle())
                .content(comment.getContent())
                .star(comment.getStar())
                .regDate(comment.getRegDate())
                .build();



        return commentDTO;
    }


    public void modify(CommentDTO commentDTO) {

        Optional<Comment> result = movieCommentRepository.findById(commentDTO.getId());
        Comment board = result.orElseThrow();

//        board.change(boardDto.getTitle(), boardDto.getContent());
//        movieCommentRepository.save(board);

    }


    public void remove(Long id) {

        movieCommentRepository.deleteById(id);
    }

    public Page<CommentFormDTO> findList(Long movieid , Pageable pageable){
        //영화에 해당하는  댓글 조회
        List<Comment> commentList = movieCommentRepository.findComment(movieid,pageable);

        // 영화에 해당하는댓글 총 갯수
        Long totalCount = movieCommentRepository.countComment(movieid);
        log.info(""+commentList);
        List<CommentFormDTO> commentDTOList= new ArrayList<>();
        for(Comment comment : commentList){
            CommentFormDTO commentDTO = CommentFormDTO.builder()
                    .id(comment.getId())
                    .users(comment.getUsers())
                    .movie(comment.getMovie())
                    .title(comment.getTitle())
                    .content(comment.getContent())
                    .star(comment.getStar())
                    .regDate(comment.getRegDate())
                    .build();
            commentDTOList.add(commentDTO);

//            List<CommentDTO> theaterDTOList2 = new ArrayList<>();
//            for(CommentFormDTO formDTO : commentDTOList){
//                CommentDTO theaterListDTO2 = CommentDTO.builder()
//                        .id(formDTO.getId())
//                        .usersid(formDTO.getUsers())
//                        .theaterName(formDTO.getTheaterName())
//                        .build();// entity->dto 메서드호출
//                theaterDTOList2.add(theaterListDTO2);
//            }



//            CommentDTO theaterListDTO = CommentDTO.of(comment);// entity->dto 메서드호출
//            commentDTOList.add(theaterListDTO);
        }
        log.info("fsdfsdfsdfsd"+commentDTOList);


        return new PageImpl<>(commentDTOList, pageable, totalCount);
    }





}

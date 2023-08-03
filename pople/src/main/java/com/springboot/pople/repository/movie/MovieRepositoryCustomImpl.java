package com.springboot.pople.repository.movie;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springboot.pople.dto.movie.MainMovieDTO;
import com.springboot.pople.dto.movie.MovieSearchDTO;
import com.springboot.pople.dto.movie.QMainMovieDTO;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.QMovie;
import com.springboot.pople.entity.QMovieImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class MovieRepositoryCustomImpl implements  MovieRepositoryCustom{
    private JPAQueryFactory queryFactory;// 동적 쿼리 객체변수
    public MovieRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em); // 동적 쿼리 객체 생성
    }
    private BooleanExpression MovieNameLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery)? null : QMovie.movie.movieName.like("%"+searchQuery+"%");

    }

    @Override
    public Page<Movie> getAdminMoviePage(MovieSearchDTO movieSearchDTO, Pageable pageable) {
        return null;
    }

    @Override
    public Page<MainMovieDTO> getMainItemPage(MovieSearchDTO movieSearchDTO, Pageable pageable) {
        QMovie movie = QMovie.movie;
        QMovieImg movieImg = QMovieImg.movieImg;

        //조인(inner join) : 기준 테이블과 조인 테이블 모두 데이터가 존재해야 조회
        List<MainMovieDTO> content = queryFactory.select(new QMainMovieDTO(
                        movie.movieid,
                        movie.movieName,
                        movie.movieSummary,
                        movieImg.imgUrl,
                        movie.movieTime,
                        movie.movieRating,
                        movie.movieDate


                )).from(movieImg)
                .join(movieImg.movie,movie) // inner join(교집합)
                .where(movieImg.repImgYn.eq("Y"))
                .where(MovieNameLike(movieSearchDTO.getSearchQuery()))
                .orderBy(movie.movieid.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); //여러개 반환 시 - 컬렉션 구조

        long total = queryFactory.select(Wildcard.count)  // count(*)와 비슷
                .from(movieImg)
                .join(movieImg.movie,movie)
                .where(movieImg.repImgYn.eq("Y"))
                .where(MovieNameLike(movieSearchDTO.getSearchQuery()))
                .fetchOne(); // 하나만 - 단일 구조


        return new PageImpl<>(content,pageable,total);
    }
}

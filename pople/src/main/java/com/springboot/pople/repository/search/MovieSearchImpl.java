package com.springboot.pople.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.springboot.pople.dto.MovieListCountDTO;

import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.QMovie;
import com.springboot.pople.entity.QMovieRev;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class MovieSearchImpl extends QuerydslRepositorySupport implements  MovieSearch {

    public MovieSearchImpl(){
        super(Movie.class);
    }

    @Override
    public Page<Movie> search1(Pageable pageable) {
        // 작업대상인 Q도메인, JPQL 설정
        QMovie   movie = QMovie.movie;
        JPQLQuery<Movie> query = from(movie);

        // 조건식 설정
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(movie.movieName.contains("1")); // title like ...
        booleanBuilder.or(movie.movieSummary.contains("1"));

        query.where(booleanBuilder);
        query.where(movie.movieid.gt(0L));

        // 페이징 처리
        this.getQuerydsl().applyPagination(pageable, query);

        List<Movie> list = query.fetch();
        long count = query.fetchCount();



        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<Movie> searchAll(String[] types, String keyword, Pageable pageable) {

        // 작업대상인 Q도메인, JPQL 설정
        QMovie movie = QMovie.movie;
        JPQLQuery<Movie> query = from(movie);

        if ((types != null && types.length > 0) && keyword != null){

            // 조건식 설정
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type: types){
                switch (type){
                    case "n":
                        booleanBuilder.or(movie.movieName.contains(keyword));
                        break;
                    case "r":
                        booleanBuilder.or(movie.movieRating.contains(keyword));
                        break;
                    case "d":
                        booleanBuilder.or(movie.movieDate.contains(keyword));
                        break;
                }// end switch
            }// end for
            query.where(booleanBuilder);
        }// end if

        query.where(movie.movieid.gt(0L));

        // 페이징 처리
        this.getQuerydsl().applyPagination(pageable, query);

        List<Movie> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<MovieListCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
        QMovie movie = QMovie.movie;
        QMovieRev movieRev = QMovieRev.movieRev;

        JPQLQuery<Movie> query = from(movie);
        query.leftJoin(movieRev).on(movieRev.movie.eq(movie));// 댓글을 작성한 게시글 번호형식으로 연결
        query.groupBy(movie);

        if ((types != null && types.length > 0) && keyword != null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String type: types){
                switch (type){
                    case "n":
                        booleanBuilder.or(movie.movieName.contains(keyword));
                        break;
                    case "r":
                        booleanBuilder.or(movie.movieRating.contains(keyword));
                        break;
                    case "d":
                        booleanBuilder.or(movie.movieDate.contains(keyword));
                        break;
                }// end switch
            }// end for
            query.where(booleanBuilder);
        }// end if

        query.where(movie.movieid.gt(0L));

        JPQLQuery<MovieListCountDTO> dtoQuery =
                query.select(
                        Projections.bean(
                                MovieListCountDTO.class,
                                movie.movieid,
                                movie.movieName,
                                movie.movieRating,
                                movie.movieDate,
                                movieRev.count().as("replyCount")));

        this.getQuerydsl().applyPagination(pageable,dtoQuery);
        List<MovieListCountDTO> dtoList = dtoQuery.fetch();
        long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);
    }
}

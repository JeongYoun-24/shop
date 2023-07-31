package com.springboot.pople.service.movie;

import com.springboot.pople.dto.movie.MovieFormDTO;

import com.springboot.pople.dto.movie.MovieImgDTO;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.MovieImg;
import com.springboot.pople.repository.MovieImgRepository;
import com.springboot.pople.repository.MovieRepository;
import com.springboot.pople.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class MovieService2 {

    private final MovieRepository movieRepository;
    private final MovieImgService movieImgService;
    private final MovieImgRepository movieImgRepository;
    private final FileService fileService;

    @Value("${org.zerock.upload.path}")
    private String movieImgLocation;

    public Long saveMovie(
            MovieFormDTO movieFormDTO,
            List<MultipartFile> movieImgFileList ) throws Exception{

        log.info("fdsfdsffdsfsffdsf12121213"+movieFormDTO.getMovieName());
        // 영화 등록


        Movie movie = movieFormDTO.createMovie();//dto->entity로 전달
        log.info(movie+"이름좀나와라 시발아");
        movieRepository.save(movie);

        // 이미지등록
        for(int i=0; i<movieImgFileList.size(); i++){
            MovieImg movieImg = new MovieImg();

            movieImg.setMovie(movie);// 상품 이미지 엔티티에 상품엔티티를 맵핑

            // 대표 이미지 여부 설정
            if (i==0){
                movieImg.setRepImgYn("Y");
            }else{
                movieImg.setRepImgYn("N");
            }

            // 영화 이미지 정보 저장: 영화이미지 엔티티 DB 반영 및 파일업로드처리
            movieImgService.saveMovieImg(movieImg, movieImgFileList.get(i));
            movie.setMoviePoster(movieImg.getImgName());
            movieRepository.save(movie);
        }

        return movie.getMovieid();// 상품 엔티티 아이디 반환
    }

    // 영화 정보(기본정보, 이미지) 읽어오기
    // JPA가 더티체킹(변경감지)를 수행하지않도록 설정(성능향상)
    @Transactional(readOnly = true)
    public MovieFormDTO getMovieDtl(Long Id){

        // 1. db-> entity : 특정 상품에 대한 상품이미지 모두 조회
        List<MovieImg> movieImgList =
                movieImgRepository.findByIdOrderByIdAsc(Id);

        // 2. List안에 entity 값 -> List구조에 dto로 변환
        List<MovieImgDTO> movieImgDTOList = new ArrayList<>();
        for(MovieImg movieImg: movieImgList){
            MovieImgDTO movieImgDTO = MovieImgDTO.of(movieImg);// entity->dto 메서드호출
            movieImgDTOList.add(movieImgDTO);
        }

        // 3. 상품 정보 읽기
        Movie movie = movieRepository
                .findById(Id)
                .orElseThrow(EntityNotFoundException::new);

        // 4. entity -> dto
        MovieFormDTO movieFormDTO = MovieFormDTO.of(movie);
        movieFormDTO.setMovieImgDTOList(movieImgDTOList);;

        return movieFormDTO;

    }
    // 5. 상품 정보 수정
    public Long updateItem(MovieFormDTO movieFormDTO, List<MultipartFile> itemImgFileList) throws Exception{

        // 5.1 기존에 상품 정보 호출
        Movie movie = movieRepository
                .findById(movieFormDTO.getId())
                .orElseThrow(EntityNotFoundException::new);

        // 5.2 상품 정보 수정폼으로 부터 전달 받은 data -> entity전달
        movie.updateItem(movieFormDTO);
        //itemeRpository.save(item); // 생략
        // => 영속성상태=> 엔티티변경시 변경감지기능동작하여 update쿼리실행
        List<Long> movieImgIds = movieFormDTO.getMovieImgIds();

        // 5.3 상품 이미지 정보 수정 서비스 호출하여 상품이미지 정보 수정
        for(int i=0; i<itemImgFileList.size(); i++){// 상품 이미지 개수 만큼 반복
            // 수정된 정보를 상품이미지 entity에 반영 => 영속성상태 적용받음
            movieImgService.updateItemImg(movieImgIds.get(i), itemImgFileList.get(i));
        }

        // 수정작업 완료된 상품 아이디 반환
        return movie.getMovieid();
    }


}

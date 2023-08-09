package com.springboot.pople.service.board;

import com.springboot.pople.dto.BoardImgDTO;
import com.springboot.pople.entity.BoardImg;
import com.springboot.pople.repository.BoardImgRepository;
import com.springboot.pople.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BoardImgService {

    @Value("${boardImgLocation}")
    private String boardImgLocation;

    private final BoardImgRepository boardImgRepository;
    private final FileService fileService;

    // 1. 공지사항 이미지 정보 등록 서비스
    public void saveBoardImg(BoardImg boardImg, MultipartFile boardImgFile) throws Exception{
        String oriImgName = boardImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(
                    boardImgLocation, // 실제 업로드할 파일 위치=>"d:/shop/item"
                    oriImgName, // 파일이름
                    boardImgFile.getBytes());

            // path: "d:/shop" => url:"/images" 와 1:1 연결(맵핑)
            imgUrl ="/images2/board/"+imgName;
        }

        // 상품 이미지 정보 저장
        boardImg.updateItemImg(oriImgName, imgName, imgUrl);
        boardImgRepository.save(boardImg);

    }

    // 2. 상품 이미지 정보 수정 서비스
    public void updateBoardImg(Long boardImgId, MultipartFile boardImgFile ) throws Exception {

        if (!boardImgFile.isEmpty()) {// 첨부(상품이미지)파일이 있으면 처리

            // 등록된 상품이미지 정보 호출
            BoardImg savedBoardImg = boardImgRepository
                    .findById(boardImgId)
                    .orElseThrow(EntityNotFoundException::new);

            // 등록된(기존) 상품이미지 파일 삭제
            if (!StringUtils.isEmpty(savedBoardImg.getImgName())) {
                fileService.deleteFile(boardImgLocation + "/" + savedBoardImg.getImgName());
            }

            // 변경된 상품이미지 업로드 및 상품이미지 entity 정보 변경(DB에는 반영안된 상태)
            String oriImgName = boardImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(
                    boardImgLocation,
                    oriImgName,
                    boardImgFile.getBytes());

            String imgUrl = "/images2/board/" + imgName;

            // 수정 폼으로 받은 상품이미지 정보 entity로 전달
            // entity가 변경되면 영속성 상태에서 자동으로 update쿼리 실행

            log.info("==> 상품이미지수정 서비스 => "+oriImgName+","+imgName);
            savedBoardImg.updateItemImg(oriImgName, imgName, imgUrl);
            log.info("==> 상품이미지 entity변경후 => "+savedBoardImg.getOriImgName());

        }
    }

    public BoardImgDTO boardImg(Long boardid,String repimgYn){
       BoardImg boardImg =  boardImgRepository.findByBoardIdAndRepImgYn(boardid,repimgYn);
        log.info(boardImg);

        BoardImgDTO boardImgDTO = new BoardImgDTO();
        boardImgDTO.setRepImgYn(boardImg.getRepImgYn());
        boardImgDTO.setImgName(boardImg.getImgName());
        boardImgDTO.setImgUrl(boardImg.getImgUrl());
        boardImgDTO.setOriImgName(boardImg.getOriImgName());
        boardImgDTO.setId(boardImg.getId());
        log.info(boardImgDTO);
       return boardImgDTO;
    }

    public void remove(Long boardid) {
        boardImgRepository.findDelete(boardid);
    }

    public BoardImgDTO findImg(Long boardid){
     BoardImg boardImg= boardImgRepository.findByBoardId(boardid);

     BoardImgDTO boardImgDTO = BoardImgDTO.of(boardImg);

        return boardImgDTO;
    }



}

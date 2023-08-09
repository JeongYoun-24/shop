package com.springboot.pople.service.board;

import com.springboot.pople.dto.BoardDTO;
import com.springboot.pople.dto.BoardFormDTO;
import com.springboot.pople.dto.BoardImgDTO;
import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.entity.Board;
import com.springboot.pople.entity.BoardImg;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.repository.BoardImgRepository;
import com.springboot.pople.repository.BoardRopository;
import com.springboot.pople.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class BoardService {
    @Value("${boardImgLocation}")
    private String boardImgLocation;

    private final ModelMapper modelMapper;
    private final BoardRopository boardRopository;
    private final BoardImgService boardImgSevice;
    private final BoardImgRepository boardImgRepository;
    private final FileService fileService;

    // 공지사항 정보 등록
    public Long saveBoard(BoardFormDTO boardFormDTO, List<MultipartFile> boardImgFileList ) throws Exception{
        log.info(boardFormDTO);
        // 공지 등록
//        Board board = boardFormDTO.createBoard();//dto->entity로 전달
        Board board = Board.builder()
                .title(boardFormDTO.getTitle())
                .content(boardFormDTO.getContent())
                .writer(boardFormDTO.getWriter())
                .regDate(LocalDateTime.now())
                .build();

        log.info("sss"+board);
        boardRopository.save(board);

        // 이미지등록
        for(int i=0; i<boardImgFileList.size(); i++){
            BoardImg boardImg = new BoardImg();

            boardImg.setBoard(board);// 상품 이미지 엔티티에 상품엔티티를 맵핑

            // 대표 이미지 여부 설정
            if (i==0){
                boardImg.setRepImgYn("Y");
            }else{
                boardImg.setRepImgYn("N");
            }
        log.info("ㄱㄱㄱㄴ"+boardImg.toString());
            // 공지 이미지 정보 저장: 상품이미지 엔티티 DB 반영 및 파일업로드처리
            boardImgSevice.saveBoardImg(boardImg, boardImgFileList.get(i));
            board.setBoardImg(boardImg.getImgName());
            boardRopository.save(board);
        }

        return board.getId();// 상품 엔티티 아이디 반환
    }

    // 공지 정보(기본정보, 이미지) 읽어오기
    // JPA가 더티체킹(변경감지)를 수행하지않도록 설정(성능향상)
    @Transactional(readOnly = true)
    public BoardFormDTO getItemDtl(Long boardId){

        // 1. db-> entity : 특정 공지에 대한 공지이미지 모두 조회
        List<BoardImg> itemImgList =
                boardImgRepository.findByBoardIdOrderByIdAsc(boardId);

        // 2. List안에 entity 값 -> List구조에 dto로 변환
        List<BoardImgDTO> boardImgDTOList = new ArrayList<>();
        for(BoardImg boardImg: itemImgList){
            BoardImgDTO boardImgDTO = BoardImgDTO.of(boardImg);// entity->dto 메서드호출
            boardImgDTOList.add(boardImgDTO);
        }

        // 3. 공지 정보 읽기
        Board board = boardRopository
                .findById(boardId)
                .orElseThrow(EntityNotFoundException::new);

        // 4. entity -> dto
        BoardFormDTO boardFormDTO = BoardFormDTO.of(board);
        boardFormDTO.setBoairImgDTOList(boardImgDTOList);;

        return boardFormDTO;

    }
    // 5. 공지 정보 수정
    public Long updateBoard(BoardFormDTO boardFormDTO, List<MultipartFile> boardImgFileList) throws Exception{
        log.info("서비스 접근 "+boardFormDTO);
        log.info("서비스 접근 "+boardImgFileList);
        // 5.1 기존에 상품 정보 호출
        Board board = boardRopository
                .findById(boardFormDTO.getId())
                .orElseThrow(EntityNotFoundException::new);

        // 5.2 상품 정보 수정폼으로 부터 전달 받은 data -> entity전달
        board.updateItem(boardFormDTO);
        //itemeRpository.save(item); // 생략
        // => 영속성상태=> 엔티티변경시 변경감지기능동작하여 update쿼리실행
        List<Long> boardImgIds = boardFormDTO.getBoardImgIds();

        // 5.3 상품 이미지 정보 수정 서비스 호출하여 상품이미지 정보 수정
        for(int i=0; i<boardImgFileList.size(); i++){// 상품 이미지 개수 만큼 반복
            // 수정된 정보를 상품이미지 entity에 반영 => 영속성상태 적용받음
            boardImgSevice.updateBoardImg(boardImgIds.get(i), boardImgFileList.get(i));
        }

        // 수정작업 완료된 상품 아이디 반환
        return board.getId();
    }


    public List<BoardDTO> AllList(){ // 공지사항 전체 조회
        List<Board> boardList = boardRopository.findAll();
        log.info(boardList);
        BoardDTO boardDTO = modelMapper.map(boardList,BoardDTO.class);
        log.info(boardDTO);
        List<BoardDTO> boardDTOList = new ArrayList<>();
        log.info(boardDTOList);

        for(Board board: boardList){
            BoardDTO movieImgDTO = BoardDTO.of(board);// entity->dto 메서드호출
            boardDTOList.add(movieImgDTO);

        }
        return boardDTOList;
    }

    public void hitcount(Long boardid) {
        Optional<Board> result = boardRopository.findById(boardid);
        Board board = result.orElseThrow();

        board.countUp();
        boardRopository.save(board);
    }

    public void remove(Long boardid) {
        boardRopository.deleteById(boardid);
    }

}

package com.springstudy.shop.controller;

import com.springstudy.shop.dto.upload.UploadFileDTO;
import com.springstudy.shop.dto.upload.UploadResultDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
public class UpDownController {

    // appLicotion.properties파일의 설정 정보를 읽어서 변수의 값으로 사용할수 잇게 해주는 어노테이션
    @Value("${itemImgLocation}")
    private String uploadPath;


    @ApiOperation(value = "Upload POST",notes = "POST방식으로 파일 등록 ")
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> upload(UploadFileDTO uploadFileDTO){
        log.info("-->"+uploadFileDTO);

        final List<UploadResultDTO> list = new ArrayList<>();

        // 전송한(첨부파일) 파일이 있는지 체크
        if(uploadFileDTO.getFiles() != null){
            uploadFileDTO.getFiles().forEach(multipartFile -> {
                log.info(multipartFile.getOriginalFilename());


                String originalName =multipartFile.getOriginalFilename();

//                String folderPath = makeFolder();

                String uuid = UUID.randomUUID().toString();
                // 이미지 이름 중복을 없에기 위해

                Path savePath = Paths.get(uploadPath,uuid+"_"+originalName);

                boolean image = false;

                try {
                    //파일업로드 완료
                    multipartFile.transferTo(savePath);

                    // 섬네일 생성
                    // 이미지 파일인 경우
                    if(Files.probeContentType(savePath).startsWith("image")){
                        File thumbFile = new File(uploadPath,"s_"+uuid+"_"+originalName);
                        Thumbnailator.createThumbnail(savePath.toFile(),thumbFile,400,400);
                    }
                list.add(UploadResultDTO.builder()
                        .uuid(uuid)
                        .fileName(originalName)
                        .img(image) // true ,false
                        .build());

                }catch (IOException e){

                }

            }); // end forEach

            return list;
        }// if end

        return null;
    }

    // 첨부파일 조회

    // 첨부파일 삭제







}

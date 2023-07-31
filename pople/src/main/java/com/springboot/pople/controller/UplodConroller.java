package com.springboot.pople.controller;

import com.springboot.pople.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping
@Log4j2
public class UplodConroller {
    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    @PostMapping("/upload222")
    public void uploadFile(MultipartFile[] uploadFiles){
        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for (MultipartFile uploadFile: uploadFiles){

            String originName = uploadFile.getOriginalFilename();
            String fileName = originName.substring(originName.lastIndexOf("\\")+1);

            log.info("fileName:"+fileName );
            //s날짜 파일
            String folderPath = makeFolder();
            //UUID
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_"+ fileName;

            Path sevePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(sevePath); // 실제 이미지 저장
                resultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }//end for

    }

    private String makeFolder(){
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/",File.separator);

        File uploadPathFolder = new File(uploadPath,folderPath);
        if(uploadPathFolder.exists() ==false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }


}

package com.springboot.pople.dto.movie;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class UploadFileDTO {
    private List<MultipartFile> files;
}

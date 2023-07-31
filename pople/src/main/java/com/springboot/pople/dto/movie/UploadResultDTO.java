package com.springboot.pople.dto.movie;

import lombok.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {
    private String uuid;
    private String fileName;
    private boolean img;//파일이 이미지파일 여부확인
    public String getLink(){
        if (img){
            return "s_"+uuid+"_"+fileName;
        }else {
            return uuid+"_"+fileName;
        }
    }
}



//    public String getImageURL(){
//        try {
//            return URLEncoder.encode(folderPath+"/"+uuid+"_"+fileName,"UTF-8");
//
//        }catch(UnsupportedEncodingException e){
//            e.printStackTrace();
//        }
//        return  "";
//    }
//public String getThumbnailURL(){
//
//        try {
//            return URLEncoder.encode(folderPath+"/s_"+uuid+"_"+"fileName","UTF-8");
//        }catch(UnsupportedEncodingException e){
//            e.printStackTrace();
//        }
//        return "";
//}




//}

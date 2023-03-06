package com.icia.work.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("notice")
public class NoticeDTO {

    private String NoNum;
    private String NoTitle;
    private String NoContent;
    private String NoDate;

    private MultipartFile NoFile;
    private String NoFileName ="";             // 참고자료

    private int NoHit;
}

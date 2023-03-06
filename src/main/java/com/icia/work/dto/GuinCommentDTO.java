package com.icia.work.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;


@Data
@Alias("gcomment")
public class GuinCommentDTO {
    private int GCBNum;             //게시글 번호
    private int GCNum;              // 게시글 댓글 번호
    private String GCWriter;
    private String GCProfileName;
    private String GCContent;
    private String GCDate;

}
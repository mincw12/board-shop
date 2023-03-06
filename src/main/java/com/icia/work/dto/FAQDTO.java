package com.icia.work.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("faq")
public class FAQDTO {

    private int FAQNum;
    private String FAQTitle;
    private String FAQContent;
    private String FAQWriter;
    private String FAQDate;

    private String AnswerTitle="";
    private String AnswerContent="";
    private int FAQOX;

    private MultipartFile FAQFile;
    private String FAQFileName ="";

    private int FAQHit;
}

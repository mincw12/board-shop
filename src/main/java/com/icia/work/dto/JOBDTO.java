package com.icia.work.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("job")

public class JOBDTO {

    private int JBNum;      // 번호
    private String JBId;    // 작성자 아이디
    private String JBTitle; // 제목
    private String JBEdu;   // 학력
    private String JBContent; // 내용
    private String JBPay;   // 희망급여
    private String JBPeriod;// 근무 기간
    private String JBCategory;  // 일 유형
    private String JBWorkDate; // 근무 요일
    private String JBWorkStart; // 출근 시간
    private String JBWorkTime;  // 근무 시간
    private String JBLicense1;   // 자격증1
    private String JBLicense2;   // 자격증2
    private String JBLicense3;   // 자격증3
    private String JBAddr1;      // 우편번호
    private String JBAddr2;      // 주소
    private String JBAddr3;      // 상세주소
    private String JBAddr;

    private MultipartFile JBDatafile1;
    private String JBDatafile1Name = "";             // 참고자료

    private MultipartFile JBDatafile2;
    private String JBDatafile2Name = "";

    private MultipartFile JBDatafile3;
    private String JBDatafile3Name = "";

    private String JBDate;      // 작성일
    private int JBDeadline;  // 마감여부
    private int JBHit;          // 조회수
}

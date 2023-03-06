package com.icia.work.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("guin")
public class GuinDTO {

    private int GBNum;      // 번호
    private String GBScale; // (개인, 기업)
    private String GBId;    // 작성자 아이디
    private String GBTitle; // 제목
    private String GBEdu;   // 요구 학력
    private String GBContent; // 내용
    private String GBPay;   // 급여
    private String GBPeriod;// 근무 기간
    private String GBCategory;  // 일 유형
    private String GBWorkDate; // 근무 요일
    private String GBWorkStart; // 출근 시간
    private String GBWorkTime;  // 근무 시간
    private String GBLicense1;   // 자격증1
    private String GBLicense2;   // 자격증2
    private String GBLicense3;   // 자격증3
    private String GBAddr1;      // 우편번호
    private String GBAddr2;      // 주소
    private String GBAddr3;      // 상세주소
    private String GBAddr;

    private String GBPostDate; //작성일

    private MultipartFile GBDatafile1;
    private String GBDatafile1Name = "";             // 참고자료

    private MultipartFile GBDatafile2;
    private String GBDatafile2Name = "";

    private MultipartFile GBDatafile3;
    private String GBDatafile3Name = "";

    private String GBDate;      // 작성일
    private String GBCloseDate;  // 마감일
    private int GBDeadline;  // 마감여부
    private int GBHit;          // 조회수
}

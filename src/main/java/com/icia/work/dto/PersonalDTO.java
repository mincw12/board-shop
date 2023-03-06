package com.icia.work.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("personal")
public class PersonalDTO {
    private String MId;//개인 회원 아이디
    private String PMPw;//개인 회원 비밀번호
    private String PMName="";//개인 회원 이름
    private String PMBirth="";//개인 회원 생일
    private String PMPhone="";//개인 회원 전화번호
    private String PMGender="";//개인 회원 성별
    private String RType;
    private String PMEmail;//개인 회원 이메일
    private String PMAddr="";//개인 회원 주소
    private String PMEdu="";//개인 회원 학력
    private int PMStar;//개인 회원 별점
    private int PMGrade;//개인 회원 등급
    private MultipartFile PMProfile;
    private String PMProfileName="";
   private String Addr1;
   private String Addr2;
   private String Addr3;

    private int age;

    private String addr4;
}
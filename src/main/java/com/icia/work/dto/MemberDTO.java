package com.icia.work.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("member")
public class MemberDTO {
    private String MId;//개인 회원 아이디
    private String MPw;//개인 회원 비밀번호
    private String MPwn;//개인 회원 2차 비밀번호
    private String MName="";//개인 회원 이름
    private String MBirth="";//개인 회원 생일
    private String MPhone="";//개인 회원 전화번호
    private String MGender="";//개인 회원 성별
    private String MEmail;//개인 회원 이메일
    private String MAddr="";//개인 회원 주소
    private int MPoint;//개인 회원 포인트
    private MultipartFile MProfile;
    private String MProfileName="";
    private String Addr1;
    private String Addr2;
    private String Addr3;
    private String addr4;
}
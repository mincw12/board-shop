package com.icia.work.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("company")
public class CompanyDTO {
    private String MId;//기업 회원 아이디
    private String CMPw;//기업 회원 비밀번호
    private String CMName="";//기업 이름
    private String CMPhone="";//기업 전화번호
    private String CMEmail;//기업 이메일
    private String CMSite;
    private String CMAddr="";//기업 주소
    private String CMBn;   //기업 사업자 번호
    private String RType;
    private int CMStar;//기업 별점
    private MultipartFile CMProfile;
    private String CMProfileName="";
    private String CMBusinessNumber;
    private String Addr1;
    private String Addr2;
    private String Addr3;
}

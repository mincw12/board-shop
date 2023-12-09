package com.icia.work.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("nShop")
public class NShopDTO {
    private int BNo;
    private String BName;
    private String BGenre;
    private String BPrice;
    private String BDetail;//BDETAIL
    private String People;//인원수 4~8인 => S45678E 로 표기할 예정
    private String BPeople;//추천 인원수
    private String BImg; //이미지 링크



}

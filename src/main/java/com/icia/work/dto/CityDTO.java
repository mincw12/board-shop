package com.icia.work.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("city")
public class CityDTO {
    private String sido;
    private String siGoon;
    private String Dong;
    private String Category;
    private String Scale;
    private String searchv;
}
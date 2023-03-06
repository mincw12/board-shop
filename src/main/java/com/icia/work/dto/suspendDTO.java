package com.icia.work.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("suspend")
public class suspendDTO {
    private String MId;
    private int suspendDays;
    private String startDate;
    private String suspendDate="";

}

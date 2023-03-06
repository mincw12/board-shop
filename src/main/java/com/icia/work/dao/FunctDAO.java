package com.icia.work.dao;

import com.icia.work.dto.CityDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FunctDAO {

    List<CityDTO> getsigun(String sido);

    String getRType(String mId);

}

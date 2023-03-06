package com.icia.work.dao;

import com.icia.work.dto.CityDTO;
import com.icia.work.dto.CompanyDTO;
import com.icia.work.dto.GuinDTO;
import com.icia.work.dto.PersonalDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GuinDAO {
    List<GuinDTO> GuinList();

    GuinDTO GuinView(int GBNum);

    int PMGuinWrite(GuinDTO guin);

    int GuinDelete(int GBNum);

    int GuinModify(GuinDTO guin);

    CompanyDTO othercmView(String gbId);

    PersonalDTO otherpmView(String gbId);
    void gbHitpl(int gbNum);

    List<GuinDTO> getGuinList5();

    List<GuinDTO> findGList(CityDTO city);

    int gConunt();

    int closeGuinPost(int gbNum);
}

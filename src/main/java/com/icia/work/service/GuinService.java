package com.icia.work.service;

import com.icia.work.dto.CityDTO;
import com.icia.work.dto.GuinDTO;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

public interface GuinService {

    ModelAndView GuinList();

    ModelAndView GuinView(int GBNum);


    ModelAndView PMGuinWrite(GuinDTO guin) throws IOException;


    ModelAndView GuinDelete(int GBNum);

    ModelAndView GuinModify(int GBNum);

    ModelAndView GuinModify(GuinDTO guin) throws IOException;

    ModelAndView OtherView(String gbId, String gbScale);

    void gbHitpl(int gbNum);

    List<GuinDTO> getGuinList5();

    List<GuinDTO> findGList(CityDTO city);

    ModelAndView countGuin();

    ModelAndView closeGuinPost(int gbNum);
}

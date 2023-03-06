package com.icia.work.service;

import com.icia.work.dto.CityDTO;
import com.icia.work.dto.CompanyDTO;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

public interface FunctService {

    List<CityDTO> getsigun(String sido);

    String getRType(String MId);

    ModelAndView OthersView(String mId);
}

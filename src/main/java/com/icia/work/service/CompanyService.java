package com.icia.work.service;

import com.icia.work.dto.CompanyDTO;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

public interface CompanyService {
    ModelAndView CMJoin(CompanyDTO company) throws IOException;
    ModelAndView CMLogin(CompanyDTO company);

    ModelAndView cmMyView(String mId);

    ModelAndView cmModify(String mId);

    ModelAndView cmModify(CompanyDTO company) throws IOException;

    ModelAndView cmDelete(String mId);

    ModelAndView cmidfind(String cmEmail);

    ModelAndView cmPwChange(CompanyDTO company, String newPw);

    ModelAndView cmFindPw(CompanyDTO company);

    ModelAndView companyList();
}

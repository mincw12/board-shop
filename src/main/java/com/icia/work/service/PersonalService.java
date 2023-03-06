package com.icia.work.service;


import com.icia.work.dto.CompanyDTO;
import com.icia.work.dto.PersonalDTO;
import com.icia.work.dto.suspendDTO;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

public interface PersonalService {
    String idoverlap(String MId);

    ModelAndView PMJoin(PersonalDTO personal) throws IOException;

    String mCheckEmail(String PMEmail);

    ModelAndView PMLogin(PersonalDTO person);

    ModelAndView pmMyView(String MId);

    ModelAndView pmModify(PersonalDTO personal) throws IOException;

    ModelAndView pmModify(String MId);

    ModelAndView pmDelete(String MId);

    ModelAndView idfind(String pmEmail);

    ModelAndView pmPwChange(PersonalDTO personal, String newPw);

    ModelAndView pmFindPw(PersonalDTO personal);

    ModelAndView personList();

    String setSuspended(suspendDTO suspend);
}

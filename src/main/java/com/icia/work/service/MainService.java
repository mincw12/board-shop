package com.icia.work.service;

import com.icia.work.dto.MemberDTO;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

public interface MainService {
    String idoverlap(String mId);

    String MCheckEmail(String mEmail);

    ModelAndView MJoin(MemberDTO member) throws IOException;

    ModelAndView mLogin(MemberDTO member);

    ModelAndView MyView(String mId);

    ModelAndView mDelete(String mId);

    ModelAndView ModifyPage(String mId);

    ModelAndView Modify(MemberDTO personal) throws IOException;

    ModelAndView contactEmail(String EMail, String msg);


    String changePw(String mId, String nowPw, String newPw);
}

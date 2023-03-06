package com.icia.work.service;

import com.icia.work.dto.CompanyDTO;
import com.icia.work.dto.GuinCommentDTO;
import com.icia.work.dto.PersonalDTO;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface GCommentService {
    List<GuinCommentDTO> GuinCommentList(int GCBNum);


    List<GuinCommentDTO> GuinCommentWrite(GuinCommentDTO gcomment);

    PersonalDTO getPmProfileName(String mId);

    CompanyDTO getCmProfileName(String mId);

    List<GuinCommentDTO> GuinCommentDelete(GuinCommentDTO gcomment);
}
package com.icia.work.service;

import com.icia.work.dao.CompanyDAO;
import com.icia.work.dao.GCommentDAO;
import com.icia.work.dao.PersonalDAO;
import com.icia.work.dto.CompanyDTO;
import com.icia.work.dto.GuinCommentDTO;
import com.icia.work.dto.GuinDTO;
import com.icia.work.dto.PersonalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GCommentServiceImpl implements GCommentService{

    private ModelAndView mav;

    private final GCommentDAO gcdao;
    private final PersonalDAO pdao;
    private final CompanyDAO cdao;


    @Override
    public List<GuinCommentDTO> GuinCommentList(int GCBNum) {
        List<GuinCommentDTO> gcList = gcdao.GuinCommentList(GCBNum);
        System.out.println("clist : " + gcList);
        return gcList;
    }

    @Override
    public List<GuinCommentDTO> GuinCommentWrite(GuinCommentDTO gcomment) {
        List<GuinCommentDTO> gcList;
        System.out.println("댓글 on "+ gcomment);
        int result = gcdao.GuinCommentWrite(gcomment);

        if (result > 0) {
            gcList = gcdao.GuinCommentList(gcomment.getGCBNum());
        } else {
            gcList = null;
        }
        System.out.println("댓글 on 2"+ gcomment);
        return gcList;
    }

    @Override
    public PersonalDTO getPmProfileName(String MId) {
        return pdao.PMView(MId);
    }

    @Override
    public CompanyDTO getCmProfileName(String MId) {
        return cdao.CMView(MId);
    }

    @Override
    public List<GuinCommentDTO> GuinCommentDelete(GuinCommentDTO gcomment) {
        List<GuinCommentDTO> gcList;

        int result = gcdao.GuinCommentDelete(gcomment);

        if (result > 0) {
            gcList = gcdao.GuinCommentList(gcomment.getGCBNum());
        } else {
            gcList = null;
        }

        return gcList;
    }


}
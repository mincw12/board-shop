package com.icia.work.controller;

import com.icia.work.dto.CompanyDTO;
import com.icia.work.dto.GuinCommentDTO;
import com.icia.work.dto.PersonalDTO;
import com.icia.work.service.FunctService;
import com.icia.work.service.GCommentService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GCommentController {

    private ModelAndView mav;

    private final GCommentService gcsvc;
    private final FunctService fcsvc;


    @PostMapping("/GuinCommentList")
    public List<GuinCommentDTO> GuinCommentList(@RequestParam("GCBNum") int GCBNum){
        System.out.println("여기까지옴 "+ GCBNum);
        return gcsvc.GuinCommentList(GCBNum);
    }

    @PostMapping("/GuinCommentWrite")
    public List<GuinCommentDTO> GuinCommentWrite(@ModelAttribute GuinCommentDTO gcomment){
        System.out.println("댓글데이터 : "+gcomment);
        String MId=gcomment.getGCWriter();
        String RType=fcsvc.getRType(MId);
        System.out.println(RType);
        //개인회원
        if(RType.equals("개인")){
            PersonalDTO personalMember=gcsvc.getPmProfileName(MId);
            gcomment.setGCProfileName(personalMember.getPMProfileName());
            System.out.println(gcomment);
        }
        //기업회원
        else{
            CompanyDTO companyMember=gcsvc.getCmProfileName(MId);
            gcomment.setGCProfileName(companyMember.getCMProfileName());
            System.out.println(gcomment);
        }
        System.out.println("받아온 댓글 데이터 : "+gcomment);
        return gcsvc.GuinCommentWrite(gcomment);
    }

    @PostMapping("/GuinCommentDelete")
    public List<GuinCommentDTO> GuinCommentDelete(@ModelAttribute GuinCommentDTO gcomment){
        System.out.println("여기까지옴 "+ gcomment);
        return gcsvc.GuinCommentDelete(gcomment);
    }

}
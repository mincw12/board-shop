package com.icia.work.controller;

import com.icia.work.dto.CompanyDTO;
import com.icia.work.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService csvc;
    private ModelAndView mav;

    @GetMapping("/CMJoin")
    public String CMJoin(){
        return "cmJoin";
    }

    @PostMapping("/CMJoin")
    public ModelAndView CMJoin(@ModelAttribute CompanyDTO company) throws IOException {
        System.out.println(company);
        mav=csvc.CMJoin(company);
        return mav;
    }



    // CMLogin (post) : 로그인
    @PostMapping("/CMLogin")
    public ModelAndView CMLogin(@ModelAttribute CompanyDTO company){

        return csvc.CMLogin(company);
    }

    // MyView(get) : 회원목록 페이지 요청
    @GetMapping ("/cmMyView")
    public ModelAndView cmMyView(@RequestParam("MId") String MId){

        return csvc.cmMyView(MId);
    }

    // cmModify(GET) : 회원수정 페이지 요청
    @GetMapping("/cmModify")
    public ModelAndView cmModify(@RequestParam("MId") String MId){
        mav = csvc.cmModify(MId);
        return mav;
    }

    // pmModify(POST) : 회원수정
    @PostMapping("/cmModify")
    public ModelAndView cmModify(@ModelAttribute CompanyDTO company) throws IOException {
        mav = csvc.cmModify(company);
        return mav;
    }

    // cmDelete(get) : 회원삭제
    @GetMapping("/cmDelete")
    public ModelAndView cmDelete(@RequestParam("MId") String MId) {

        mav = csvc.cmDelete(MId);

        return mav;
    }

    // 아이디찾기 0131, 오전 9시 30분 추가
    @GetMapping("/cmidfind")
    public String cmidfind(){

        return "cmFindId";
    }


    @PostMapping ("/cmidfind")
    public ModelAndView cmidfind(@RequestParam("CMEmail") String CMEmail) throws IOException {
        System.out.println(CMEmail);
        mav=csvc.cmidfind(CMEmail);
        return mav;
    }

    // 비번변경
    @GetMapping("/cmPwChangeForm")
    public String cmPwChangeForm(){

        return "cmPwChange";
    }

    @PostMapping("/cmPwChange")
    public ModelAndView pmPwChange(@RequestParam("newPw") String newPw,@ModelAttribute CompanyDTO company ) throws IOException {
        mav = csvc.cmPwChange(company, newPw);
        return mav;
    }

    // 비밀번호 찾기 0202, 오전 9시 추가
    @GetMapping("/cmFindPw")
    public String cmFindPw(){

        return "cmFindPw";
    }

    @PostMapping("/cmFindPw")
    public ModelAndView cmFindPw(@ModelAttribute CompanyDTO company ) throws IOException {
        mav = csvc.cmFindPw(company);
        return mav;
    }

    // 회원목록(관리자) 0202, 오전 10시 추가
    @GetMapping("/companyList")
    public ModelAndView companyList(){

        mav = csvc.companyList();
        return mav;
    }

}


package com.icia.work.controller;

import com.icia.work.dto.MemberDTO;
import com.icia.work.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MainService msvc;
    private ModelAndView mav;
    //기본 페이지 인덱스
    @GetMapping("/")
    public String Home(){
        return "index";
    }
    //index 이동
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    //로그인 페이지 이동(get)
    @GetMapping("/login")
    public String login(){
        return "loginPage";
    }

    //로그인(get)
    @PostMapping ("/MLogin")
    public ModelAndView MLogin(@ModelAttribute MemberDTO member) throws IOException {
        System.out.println(member);
        mav=msvc.mLogin(member);
        return mav;
    }
    //회원 가입 페이지 이동(get)
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    //회원 가입(post)
    @PostMapping("/MJoin")
    public ModelAndView PMJoin(@ModelAttribute MemberDTO member) throws IOException {
        System.out.println(member);
        mav=msvc.MJoin(member);
        return mav;
    }
    //로그아웃(get)
    @GetMapping("/Logout")
    public String Logout(HttpSession session){
        session.invalidate();
        return "index";
    }
    //회원 조회(get)
    @GetMapping ("/MyView")
    public ModelAndView pmMyView(@RequestParam("MId") String MId){

        return msvc.MyView(MId);
    }

    // 회원 삭제(get)
    @GetMapping("/mDelete")
    public ModelAndView mDelete(@RequestParam("MId") String MId) {

        mav = msvc.mDelete(MId);

        return mav;
    }
    //회원 수정 페이지로 이동(get)
    @GetMapping("/ModifyPage")
    public ModelAndView mModify(@RequestParam("MId") String MId) {

        mav = msvc.ModifyPage(MId);

        return mav;
    }
    //회원 수정(post)
    @PostMapping("/Modify")
    public ModelAndView Modify(@ModelAttribute MemberDTO personal) throws IOException {
        mav = msvc.Modify(personal);
        return mav;
    }

    //contact 페이지 이동(get)
    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }

    //문의 보내기 (get)
    @GetMapping("/contactEmail")
    public ModelAndView contactEmail(@RequestParam("EMail") String EMail,@RequestParam("msg") String msg) throws IOException {
        mav = msvc.contactEmail(EMail,msg);
        return mav;
    }
    //news 페이지 이동(get)
    @GetMapping("/news")
    public String news(){
        return "news";
    }
    //news 페이지 이동(get)
    @GetMapping("/news-detail")
    public String news_detail(){
        return "news-detail";
    }

}


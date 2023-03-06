package com.icia.work.controller;

import com.icia.work.dto.NoticeDTO;
import com.icia.work.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private ModelAndView mav;

    private final NoticeService ntcsvc;


    @GetMapping("/NoticeList")
    public ModelAndView NoticeList(){

        return ntcsvc.NoticeList();
    }

    @GetMapping("/NoticeWrite")
    public String NoticeWrite() {

        return "noticeWrite";
    }

    @PostMapping("NoticeWrite")
    public ModelAndView NoticeWrite(@ModelAttribute NoticeDTO notice) throws IOException {
        System.out.println(notice);
        mav = ntcsvc.NoticeWrite(notice);
        return mav;
    }

    @GetMapping("NoticeView")
    public ModelAndView NoticeView(@RequestParam("NoNum") int NoNum,
                                   HttpServletRequest request,
                                   HttpServletResponse response){
        mav = ntcsvc.NoticeView(NoNum);
        String checkCookie="OK";
        /* 조회수 로직 */
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().contains("Notice_visit_"+NoNum) && cookie.getValue().contains(request.getParameter("NoNum"))) {
                    checkCookie="NO";
                    System.out.println(checkCookie);
                }
            }
        }
        if(checkCookie == "OK"){
            Cookie newCookie = new Cookie("Notice_visit_"+NoNum, request.getParameter("NoNum"));
            newCookie.setMaxAge(60*30);
            response.addCookie(newCookie);
            System.out.println("요기");
            ntcsvc.noHitpl(NoNum);
        }
        return mav;
    }

    @GetMapping("NoticeDelete")
    public ModelAndView NoticeDelete(@RequestParam("NoNum") int NoNum){
        mav = ntcsvc.NoticeDelete(NoNum);
        return mav;
    }
}

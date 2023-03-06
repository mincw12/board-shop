package com.icia.work.controller;

import com.icia.work.dto.FAQDTO;
import com.icia.work.service.FAQService;
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
public class FAQController {

    private ModelAndView mav;

    private final FAQService faqsvc;

    @GetMapping("/FAQList")
    public ModelAndView FAQList(){
        return faqsvc.FAQList();
    }

    @GetMapping("/FAQWrite")
    public String FAQWrite() {

        return "FAQWrite";
    }


    @PostMapping("FAQWrite")
    public ModelAndView FAQWrite(@ModelAttribute FAQDTO faq) throws IOException {
        System.out.println(faq);
        mav = faqsvc.FAQWrite(faq);
        return mav;
    }

    @GetMapping("FAQView")
    public ModelAndView FAQView(@RequestParam("FAQNum") int FAQNum,
                                HttpServletRequest request,
                                HttpServletResponse response){
        mav = faqsvc.FAQView(FAQNum);
        String checkCookie="OK";
        /* 조회수 로직 */
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().contains("FAQ_visit_"+FAQNum) && cookie.getValue().contains(request.getParameter("FAQNum"))) {
                    checkCookie="NO";
                    System.out.println(checkCookie);
                }
            }
        }
        if(checkCookie == "OK"){
            Cookie newCookie = new Cookie("FAQ_visit_"+FAQNum, request.getParameter("FAQNum"));
            newCookie.setMaxAge(60*30);
            response.addCookie(newCookie);
            System.out.println("요기");
            faqsvc.faqHitpl(FAQNum);
        }
        return mav;
    }

    @GetMapping("FAQDelete")
    public ModelAndView FAQDelete(@RequestParam("FAQNum") int FAQNum){
        mav = faqsvc.FAQDelete(FAQNum);
        return mav;
    }

    @GetMapping("/FAQAnswer")
    public ModelAndView FAQAnswer(@RequestParam("FAQNum") int FAQNum){

        return faqsvc.FAQAnswer(FAQNum);
    }

    @PostMapping("FAQAnswer")
    public ModelAndView FAQAnswer(@ModelAttribute FAQDTO faq) throws IOException {
        System.out.println(faq);
        mav = faqsvc.FAQAnswerWrite(faq);
        System.out.println(mav);
        return mav;
    }

}

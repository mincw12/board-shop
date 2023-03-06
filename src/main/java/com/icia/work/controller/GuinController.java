package com.icia.work.controller;

import com.icia.work.dto.GuinDTO;
import com.icia.work.service.GuinService;
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
public class GuinController {

    private ModelAndView mav;

    private final GuinService gbsvc;

    @GetMapping("/GuinList")
    public ModelAndView GuinList(){
        return gbsvc.countGuin();
    }
    @GetMapping("GuinView")
    public ModelAndView GuinView(@RequestParam("GBNum") int GBNum,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        mav = gbsvc.GuinView(GBNum);
        String checkCookie="OK";
        /* 조회수 로직 */
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().contains("Guin_visit_"+GBNum) && cookie.getValue().contains(request.getParameter("GBNum"))) {
                    checkCookie="NO";
                    System.out.println(checkCookie);
                }
            }
        }
        if(checkCookie == "OK"){
            Cookie newCookie = new Cookie("Guin_visit_"+GBNum, request.getParameter("GBNum"));
            newCookie.setMaxAge(60*30);
            response.addCookie(newCookie);
            System.out.println("요기");
            gbsvc.gbHitpl(GBNum);
        }
        return mav;
    }

    @GetMapping("/PMGuinWrite")
    public String PMwriteForm() {
        return "pmGuinWrite";
    }

    @GetMapping("/CMGuinWrite")
    public String CMwriteForm() {
        return "cmGuinWrite";
    }


    @PostMapping("PMGuinWrite")
    public ModelAndView PMGuinWrite(@ModelAttribute GuinDTO guin) throws IOException {
        System.out.println(guin);
        mav = gbsvc.PMGuinWrite(guin);
        return mav;
    }

    @GetMapping("GuinDelete")
    public ModelAndView GuinDelete(@RequestParam("GBNum") int GBNum){
        mav = gbsvc.GuinDelete(GBNum);
        return mav;
    }

    @GetMapping("/GuinModify")
    public ModelAndView GuinModify(@RequestParam("GBNum") int GBNum){
        mav = gbsvc.GuinModify(GBNum);
        return mav;
    }

    @PostMapping("/GuinModify")
    public ModelAndView GuinModify(@ModelAttribute GuinDTO guin) throws IOException {
        mav = gbsvc.GuinModify(guin);
        return mav;
    }

    @GetMapping("/OtherView")
    public ModelAndView OtherView(@RequestParam("GBId") String GBId, @RequestParam("GBScale") String GBScale){
        mav = gbsvc.OtherView(GBId,GBScale);
        System.out.println(mav);
        return mav;
    }

    @GetMapping("closeGuinPost")
    public ModelAndView closeGuinPost(@RequestParam("GBNum") int GBNum){
        mav = gbsvc.closeGuinPost(GBNum);
        return mav;
    }


}

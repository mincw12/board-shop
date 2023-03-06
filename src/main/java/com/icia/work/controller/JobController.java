package com.icia.work.controller;

import com.icia.work.dto.JOBDTO;
import com.icia.work.service.JobService;
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
public class JobController {

    private ModelAndView mav;

    private final JobService jbsvc;

    @GetMapping("/JobList")
    public ModelAndView JobList(){

        return jbsvc.JobList();

    }
    @GetMapping("/JobWrite")
    public String JobWrite() {
        return "jobWrite";
    }

    @PostMapping("JobWrite")
    public ModelAndView JobWrite(@ModelAttribute JOBDTO job) throws IOException {
        System.out.println(job);
        mav = jbsvc.JobWrite(job);
        return mav;
    }

    @GetMapping("JobView")
    public ModelAndView JobView(@RequestParam("JBNum") int JBNum,
                                HttpServletRequest request,
                                HttpServletResponse response){
        mav = jbsvc.JobView(JBNum);
        String checkCookie="OK";
        /* 조회수 로직 */
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().contains("Job_visit_"+JBNum) && cookie.getValue().contains(request.getParameter("JBNum"))) {
                    checkCookie="NO";
                }
            }
        }
        if(checkCookie == "OK"){
            Cookie newCookie = new Cookie("Job_visit_"+JBNum, request.getParameter("JBNum"));
            newCookie.setMaxAge(60*30);
            response.addCookie(newCookie);
            jbsvc.jbHitpl(JBNum);
        }
        return mav;
    }

    @GetMapping("JobModify")
    public ModelAndView JobModify(@RequestParam("JBNum") int JBNum){
        mav = jbsvc.JobModify(JBNum);
        return mav;
    }

    @PostMapping("/JobModify")
    public ModelAndView JobModify(@ModelAttribute JOBDTO job) throws IOException {
        mav = jbsvc.JobModify(job);
        return mav;
    }

    @GetMapping("JobDelete")
    public ModelAndView JobDelete(@RequestParam("JBNum") int JBNum){
        mav = jbsvc.JobDelete(JBNum);
        return mav;
    }

    @GetMapping("/otherpmView")
    public ModelAndView otherpmView(@RequestParam("JBId") String JBId){
        mav = jbsvc.otherpmView(JBId);
        System.out.println(mav);
        return mav;
    }

    @GetMapping("closeJobPost")
    public ModelAndView closeJobPost(@RequestParam("JBNum") int JBNum){
        mav = jbsvc.closeJobPost(JBNum);
        return mav;
    }


}

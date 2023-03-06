package com.icia.work.controller;

import com.icia.work.dto.*;
import com.icia.work.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class restController {

    private final PersonalService psvc;

    private final FunctService fsvc;

    private final GuinService gbsvc;

    private final JobService jbsvc;

    private final NoticeService ntcsvc;

    private final FAQService faqsvc;

    private ModelAndView mav;

    @PostMapping("/idoverlap")
    public String idoverlap(@RequestParam("MId") String MId) {
        System.out.println("memId:" + MId);
        String msg = psvc.idoverlap(MId);
        return msg;
    }

    @PostMapping("/PMCheckEmail")
    public String PMCheckEmail(@RequestParam("PMEmail") String PMEmail){

        return psvc.mCheckEmail(PMEmail);
    }

    @PostMapping("/CMCheckEmail")
    public String CMCheckEmail(@RequestParam("CMEmail") String CMEmail){

        return psvc.mCheckEmail(CMEmail);
    }
    @PostMapping("/getsigun")
    public List<CityDTO> getsigun(@RequestParam("sido") String sido) {

        return fsvc.getsigun(sido);
    }

    @PostMapping("/getGuinList5")
    public List<GuinDTO> getGuinList5() {

        return gbsvc.getGuinList5();
    }

    @PostMapping("/getJobList5")
    public List<JOBDTO> getJobList5() {

        return jbsvc.getJobList5();
    }

    @PostMapping("/getNoticeList5")
    public List<NoticeDTO> getNoticeList5() {

        return ntcsvc.getNoticeList5();
    }

    @PostMapping("/getFAQList5")
    public List<FAQDTO> getFAQList5() {

        return faqsvc.getFAQlist5();
    }
    @PostMapping("/findGList")
    public List<GuinDTO> findGList(@ModelAttribute CityDTO City) {
        System.out.println("City값:" + City);
        return gbsvc.findGList(City);
    }

    @PostMapping("/FAQPList")
    public List<FAQDTO> FAQPList() {
        return faqsvc.FAQPList();
    }
    @PostMapping("/findJList")
    public List<JOBDTO> findJList(@ModelAttribute CityDTO City) {
        System.out.println("City값:" + City);
        return jbsvc.findJList(City);
    }

    @PostMapping("/findNoticeList")
    public List<NoticeDTO> findNoticeList() {
        return ntcsvc.findNoticeList();
    }

    @PostMapping("/setSuspended")
    public String setSuspended(@ModelAttribute suspendDTO suspend){
        System.out.println("여기옴 "+ suspend);
        String suspendText=psvc.setSuspended(suspend);
        return suspendText;}
}

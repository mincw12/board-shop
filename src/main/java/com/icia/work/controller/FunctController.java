package com.icia.work.controller;


import com.icia.work.service.FunctService;
import com.icia.work.service.GCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class FunctController {

    private ModelAndView mav;
    private final FunctService fcsvc;

    @GetMapping("/OthersView")
    public ModelAndView OthersView(@RequestParam("MId") String MId){
        mav = fcsvc.OthersView(MId);
        return mav;
    }

}

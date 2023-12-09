package com.icia.work.controller;

import com.icia.work.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shsc;
    private ModelAndView mav;
    //쇼핑 페이지 이동
    @GetMapping("/shop")
    public String shop(){
        return "shop";
    }

@GetMapping("/shopDetail")
    public ModelAndView shopDetail(@RequestParam("bNo") int bNo){

        return shsc.shopDetail(bNo);
    }
}


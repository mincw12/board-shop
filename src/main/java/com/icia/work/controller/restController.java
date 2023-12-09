package com.icia.work.controller;

import com.icia.work.dao.ShopDAO;
import com.icia.work.dto.NShopDTO;
import com.icia.work.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class restController {

    private final MainService msvc;
    private final ShopService shvc;
    private ModelAndView mav;

    @PostMapping("/idoverlap")
    public String idoverlap(@RequestParam("MId") String MId) {
        System.out.println("memId:" + MId);
        String msg = msvc.idoverlap(MId);
        return msg;
    }
    @PostMapping("/MCheckEmail")
    public String PMCheckEmail(@RequestParam("MEmail") String MEmail){

        return msvc.MCheckEmail(MEmail);
    }
    @PostMapping("/changePw")
    public String changePw(@RequestParam("MId") String MId,@RequestParam("nowPw") String nowPw,@RequestParam("newPw") String newPw) {
       String msg = msvc.changePw(MId,nowPw,newPw);
        return msg;
    }
    @PostMapping("/NAllList")
    public List<NShopDTO> NAllList() {
         List<NShopDTO> NList = shvc.NAllList();
        System.out.println(NList);
        return NList;
    }
    @PostMapping("/basketList")
    public List<NShopDTO> basketList(@RequestParam("cartList[]") String[] cartList) {
        return shvc.basketList(cartList);
    }
}

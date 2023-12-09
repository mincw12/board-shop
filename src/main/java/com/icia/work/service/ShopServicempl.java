package com.icia.work.service;

import com.icia.work.dao.ShopDAO;
import com.icia.work.dto.NShopDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServicempl implements ShopService {
    private final HttpSession session;
    private final ShopDAO Shdao;
    private ModelAndView mav;


    @Override
    public List<NShopDTO> NAllList() {
        List<NShopDTO> NShopList=Shdao.NShopList();
        return NShopList;
    }

    @Override
    public ModelAndView shopDetail(int bNo) {
        ModelAndView mav=new ModelAndView();
        NShopDTO NDetail=Shdao.NDetail(bNo);
        mav.addObject("NDetail",NDetail);
        mav.setViewName("shdetail");
        return mav;
    }

    @Override
    public List<NShopDTO> basketList(String[] cartList) {
        int cartNum=cartList.length;
        List<NShopDTO> NShopList = new ArrayList<>();
        int[] newList = new int[cartList.length];
        //string 타입으로 받아온 카트 리스트 인트 타입으로 변경
        //cartList => newList
        for (int i = 0; i < cartList.length; i++) {
            newList[i] = Integer.parseInt(cartList[i]);
        }

        for(int i : newList) {
            NShopList.add(Shdao.NDetail(i));
        }
        System.out.println(NShopList);
        return NShopList;
    }
}





package com.icia.work.service;

import com.icia.work.dto.NShopDTO;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface ShopService {

    List<NShopDTO> NAllList();

    ModelAndView shopDetail(int bNo);

    List<NShopDTO> basketList(String[] cartList);
}

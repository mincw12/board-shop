package com.icia.work.dao;

import com.icia.work.dto.NShopDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopDAO {

    List<NShopDTO> NShopList();

    NShopDTO NDetail(int bNo);
}

package com.icia.work.dao;


import com.icia.work.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeDAO {
    List<NoticeDTO> NoticeList();


    int NoticeWrite(NoticeDTO notice);

    void noHitpl(int noNum);

    NoticeDTO NoticeView(int noNum);

    int NoticeDelete(int noNum);

    List<NoticeDTO> getNoticeList5();

    List<NoticeDTO> findNoticeList();
}

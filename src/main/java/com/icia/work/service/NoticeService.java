package com.icia.work.service;

import com.icia.work.dto.NoticeDTO;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

public interface NoticeService {
    ModelAndView NoticeList();

    ModelAndView NoticeWrite(NoticeDTO notice) throws IOException;

    ModelAndView NoticeView(int noNum);

    void noHitpl(int noNum);

    ModelAndView NoticeDelete(int noNum);

    List<NoticeDTO> getNoticeList5();

    List<NoticeDTO> findNoticeList();
}

package com.icia.work.dao;

import com.icia.work.dto.FAQDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FAQDAO {
    List<FAQDTO> FAQList();

    int FAQWrite(FAQDTO faq);

    FAQDTO FAQView(int faqNum);

    void faqHitpl(int faqNum);

    int FAQDelete(int faqNum);

    List<FAQDTO> getFAQList5();

    void FAQAnswerWrite(FAQDTO faq);
}

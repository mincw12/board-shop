package com.icia.work.service;

import com.icia.work.dto.FAQDTO;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

public interface FAQService {
    ModelAndView FAQList();

    ModelAndView FAQWrite(FAQDTO faq) throws IOException;

    ModelAndView FAQView(int faqNum);

    void faqHitpl(int faqNum);

    ModelAndView FAQDelete(int faqNum);

    List<FAQDTO> getFAQlist5();

    ModelAndView FAQAnswer(int faqNum);

    ModelAndView FAQAnswerWrite(FAQDTO faq);

    List<FAQDTO> FAQPList();
}

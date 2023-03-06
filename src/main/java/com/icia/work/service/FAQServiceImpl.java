package com.icia.work.service;

import com.icia.work.dao.FAQDAO;
import com.icia.work.dto.FAQDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService {

    private ModelAndView mav;

    private final FAQDAO faqdao;

    @Override
    public ModelAndView FAQList() {
        mav = new ModelAndView();

        List<FAQDTO> FAQList = faqdao.FAQList();

        mav.setViewName("FAQList");
        mav.addObject("FAQList", FAQList);
        System.out.println(FAQList);
        return mav;
    }

    @Override
    public ModelAndView FAQWrite(FAQDTO faq) throws IOException {
        mav = new ModelAndView();

        // 파일 불러오기
        MultipartFile FAQFile = faq.getFAQFile();

        // 파일 선택여부 확인
        // profile.isEmpty() : 파일이 선택되지 않았다.
        // !profile.isEmpty() : 파일이 선택됐다.
        if (!FAQFile.isEmpty()) {

            // 파일 저장 위치 (상대경로) 설정
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/faqfile");


            // 난수 생성하기
            String uuid = UUID.randomUUID().toString().substring(0, 8);

            // 파일 이름 불러오기
            String originalFilename = FAQFile.getOriginalFilename();

            // 난수 + 파일이름(새로운 파일 이름 생성)
            String FAQFileName = uuid + "_" + originalFilename;

            // member객체에 새로운 파일 이름 저장
            faq.setFAQFileName(FAQFileName);

            // 저장될 폴더와 파일 이름
            String savePath = path + "/" + FAQFileName;

            // 파일 업로드
            FAQFile.transferTo(new File(savePath));

            System.out.println("faq" + faq);
        }
            int result = faqdao.FAQWrite(faq);
        if(result>0){
            mav.setViewName("redirect:/FAQList");}
        else{
            mav.setViewName("FAQWrite");
        }
        return mav;
    }

    @Override
    public ModelAndView FAQView(int faqNum) {
        mav = new ModelAndView();

        FAQDTO faqv = faqdao.FAQView(faqNum);

        mav.setViewName("FAQView");
        mav.addObject("FAQView", faqv);


        return mav;
    }

    @Override
    public void faqHitpl(int faqNum) {
        faqdao.faqHitpl(faqNum);

    }

    @Override
    public ModelAndView FAQDelete(int faqNum) {
        mav = new ModelAndView();
        FAQDTO faq = faqdao.FAQView(faqNum);
        int result = faqdao.FAQDelete(faqNum);

        if (result > 0) {
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/guinfile");
            ;

            // 기존에 있던 파일 지우기
            String deletePath1 = path + "/" + faq.getFAQFileName();
            File deleteFile1 = new File(deletePath1);

            if (deleteFile1.exists()) {
                deleteFile1.delete();
            }

            mav.setViewName("redirect:/FAQList");
        } else {
            mav.setViewName("index");
        }

        return mav;
    }

    @Override
    public List<FAQDTO> getFAQlist5() {

        return faqdao.getFAQList5();
    }

    @Override
    public ModelAndView FAQAnswer(int faqNum) {
        mav = new ModelAndView();

        mav.setViewName("FAQAnswer");
        mav.addObject("FAQNum", faqNum);

        return mav;
    }

    @Override
    public ModelAndView FAQAnswerWrite(FAQDTO faq) {
        faqdao.FAQAnswerWrite(faq);
        mav.setViewName("redirect:/FAQView?FAQNum="+faq.getFAQNum());
        return mav;
    }

    @Override
    public List<FAQDTO> FAQPList() {
        List<FAQDTO> faqp = faqdao.FAQList();
        return faqp;
    }

}

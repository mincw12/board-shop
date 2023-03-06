package com.icia.work.service;

import com.icia.work.dao.NoticeDAO;
import com.icia.work.dto.NoticeDTO;
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
public class NoticeServiceImpl implements NoticeService{

    private ModelAndView mav;

    private final NoticeDAO ntcdao;
    @Override
    public ModelAndView NoticeList() {
        mav = new ModelAndView();
        mav.setViewName("noticeList");
        return mav;
    }
    @Override
    public ModelAndView NoticeWrite(NoticeDTO notice) throws IOException {
        // 파일 불러오기
        MultipartFile NoFile = notice.getNoFile();

        // 파일 선택여부 확인
        // profile.isEmpty() : 파일이 선택되지 않았다.
        // !profile.isEmpty() : 파일이 선택됐다.
        if(!NoFile.isEmpty()) {

            // 파일 저장 위치 (상대경로) 설정
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/noticefile");


            // 난수 생성하기
            String uuid = UUID.randomUUID().toString().substring(0, 8);

            // 파일 이름 불러오기
            String originalFilename = NoFile.getOriginalFilename();

            // 난수 + 파일이름(새로운 파일 이름 생성)
            String NoFileName = uuid + "_" + originalFilename;

            // member객체에 새로운 파일 이름 저장
            notice.setNoFileName(NoFileName);

            // 저장될 폴더와 파일 이름
            String savePath = path + "/" + NoFileName;

            // 파일 업로드
            NoFile.transferTo(new File(savePath));

            System.out.println("notice"+notice);

        }

            int result = ntcdao.NoticeWrite(notice);
            mav.setViewName("redirect:/NoticeList");
        return mav;
    }

    @Override
    public ModelAndView NoticeView(int noNum) {
        mav = new ModelAndView();

        NoticeDTO notV = ntcdao.NoticeView(noNum);

        mav.setViewName("noticeView");
        mav.addObject("NoticeView", notV);

        return mav;
    }

    @Override
    public void noHitpl(int noNum) {
        ntcdao.noHitpl(noNum);
    }

    @Override
    public ModelAndView NoticeDelete(int noNum) {
        mav = new ModelAndView();
        NoticeDTO notice = ntcdao.NoticeView(noNum);
        int result = ntcdao.NoticeDelete(noNum);

        if(result>0){
            Path path = Paths.get(System.getProperty("user.dir"),"src/main/resources/static/noticefile");;

            // 기존에 있던 파일 지우기
            String deletePath1 = path + "/" + notice.getNoFileName();
            File deleteFile1 = new File(deletePath1);

            if(deleteFile1.exists()){
                deleteFile1.delete();
            }

            mav.setViewName("redirect:/NoticeList");
        } else {
            mav.setViewName("index");
        }

        return mav;
    }

    @Override
    public List<NoticeDTO> getNoticeList5() {
        return ntcdao.getNoticeList5();
    }

    @Override
    public List<NoticeDTO> findNoticeList() {
        List<NoticeDTO> N=ntcdao.findNoticeList();
        return N;
    }


}

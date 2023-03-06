package com.icia.work.service;

import com.icia.work.dao.JobDAO;
import com.icia.work.dto.CityDTO;
import com.icia.work.dto.JOBDTO;
import com.icia.work.dto.PersonalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private ModelAndView mav;

    private final JobDAO jbdao;

    public ModelAndView JobList() {
        mav = new ModelAndView();

        List<JOBDTO> JbList = jbdao.JobList();

        mav.setViewName("jobList");
        mav.addObject("JbList", JbList);
        System.out.println(JbList);
        return mav;
    }

    @Override
    public ModelAndView JobWrite(JOBDTO job) throws IOException {
        mav = new ModelAndView();

        // 파일 불러오기
        MultipartFile JBDatafile1 = job.getJBDatafile1();
        MultipartFile JBDatafile2 = job.getJBDatafile2();
        MultipartFile JBDatafile3 = job.getJBDatafile3();

        // 파일 선택여부 확인
        // profile.isEmpty() : 파일이 선택되지 않았다.
        // !profile.isEmpty() : 파일이 선택됐다.

        if (!JBDatafile1.isEmpty()) {

            // 파일 저장 위치 (상대경로) 설정
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/jobfile");

            // 기존에 있던 파일 지우기
            String deletePath1 = path + "/" + job.getJBDatafile1Name();
            File deleteFile1 = new File(deletePath1);

            if (deleteFile1.exists()) {
                deleteFile1.delete();
            }

            // 난수 생성하기
            String uuid = UUID.randomUUID().toString().substring(0, 8);

            // 파일 이름 불러오기
            String originalFilename = JBDatafile1.getOriginalFilename();

            // 난수 + 파일이름(새로운 파일 이름 생성)
            String JBDatafile1Name = uuid + "_" + originalFilename;

            // member객체에 새로운 파일 이름 저장
            job.setJBDatafile1Name(JBDatafile1Name);

            // 저장될 폴더와 파일 이름
            String savePath = path + "/" + JBDatafile1Name;

            // 파일 업로드
            JBDatafile1.transferTo(new File(savePath));

        }

        if (!JBDatafile2.isEmpty()) {

            // 파일 저장 위치 (상대경로) 설정
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/jobfile");

            // 기존에 있던 파일 지우기
            String deletePath2 = path + "/" + job.getJBDatafile2Name();
            File deleteFile2 = new File(deletePath2);

            if (deleteFile2.exists()) {
                deleteFile2.delete();
            }

            // 난수 생성하기
            String uuid = UUID.randomUUID().toString().substring(0, 8);

            // 파일 이름 불러오기
            String originalFilename = JBDatafile2.getOriginalFilename();

            // 난수 + 파일이름(새로운 파일 이름 생성)
            String JBDatafile2Name = uuid + "_" + originalFilename;

            // member객체에 새로운 파일 이름 저장
            job.setJBDatafile2Name(JBDatafile2Name);

            // 저장될 폴더와 파일 이름
            String savePath = path + "/" + JBDatafile2Name;

            // 파일 업로드
            JBDatafile2.transferTo(new File(savePath));

        }

        if (!JBDatafile3.isEmpty()) {

            // 파일 저장 위치 (상대경로) 설정
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/jobfile");

            // 기존에 있던 파일 지우기
            String deletePath3 = path + "/" + job.getJBDatafile3Name();
            File deleteFile3 = new File(deletePath3);

            if (deleteFile3.exists()) {
                deleteFile3.delete();
            }

            // 난수 생성하기
            String uuid = UUID.randomUUID().toString().substring(0, 8);

            // 파일 이름 불러오기
            String originalFilename = JBDatafile3.getOriginalFilename();

            // 난수 + 파일이름(새로운 파일 이름 생성)
            String JBDatafile3Name = uuid + "_" + originalFilename;

            // member객체에 새로운 파일 이름 저장
            job.setJBDatafile3Name(JBDatafile3Name);

            // 저장될 폴더와 파일 이름
            String savePath = path + "/" + JBDatafile3Name;

            // 파일 업로드
            JBDatafile3.transferTo(new File(savePath));

        }


        int result = jbdao.JobWrite(job);
        System.out.println(job);
        mav.setViewName("redirect:/JobList");

        return mav;
    }

    @Override
    public ModelAndView JobView(int JBNum) {
        mav = new ModelAndView();

        JOBDTO join = jbdao.JobView(JBNum);

        mav.setViewName("jobView");
        mav.addObject("JobView", join);

        return mav;
    }

    @Override
    public ModelAndView JobModify(int jbNum) {
        mav = new ModelAndView();

        JOBDTO job = jbdao.JobView(jbNum);

        mav.setViewName("jobModify");
        mav.addObject("JobModify", job);
        return mav;
    }

    @Override
    public ModelAndView JobModify(JOBDTO job) throws IOException {
        mav = new ModelAndView();

        // 파일업로드 설정
        MultipartFile JBDatafile1 = job.getJBDatafile1();
        MultipartFile JBDatafile2 = job.getJBDatafile2();
        MultipartFile JBDatafile3 = job.getJBDatafile3();

        if (!JBDatafile1.isEmpty()) {
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/jobfile");

            String deletePath1 = path + "/" + job.getJBDatafile1Name();
            File deleteFile1 = new File(deletePath1);
            if (deleteFile1.exists()) {
                deleteFile1.delete();
            }

            String uuid = UUID.randomUUID().toString().substring(0, 8);

            String originalFileName = JBDatafile1.getOriginalFilename();

            String JBDatafile1Name = uuid + "_" + originalFileName;

            job.setJBDatafile1Name(JBDatafile1Name);

            String savePath = path + "/" + JBDatafile1Name;
            System.out.println("[3]service : " + savePath);

            JBDatafile1.transferTo(new File(savePath));
        }

        if (!JBDatafile2.isEmpty()) {
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/jobfile");

            String deletePath2 = path + "/" + job.getJBDatafile2Name();
            File deleteFile2 = new File(deletePath2);

            if (deleteFile2.exists()) {
                deleteFile2.delete();
            }

            String uuid = UUID.randomUUID().toString().substring(0, 8);

            String originalFileName = JBDatafile2.getOriginalFilename();

            String JBDatafile2Name = uuid + "_" + originalFileName;

            job.setJBDatafile2Name(JBDatafile2Name);

            String savePath = path + "/" + JBDatafile2Name;
            System.out.println("[3]service : " + savePath);

            JBDatafile2.transferTo(new File(savePath));
        }

        if (!JBDatafile3.isEmpty()) {
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/jobfile");

            String deletePath3 = path + "/" + job.getJBDatafile3Name();
            File deleteFile3 = new File(deletePath3);

            if (deleteFile3.exists()) {
                deleteFile3.delete();
            }

            String uuid = UUID.randomUUID().toString().substring(0, 8);

            String originalFileName = JBDatafile3.getOriginalFilename();

            String JBDatafile3Name = uuid + "_" + originalFileName;

            job.setJBDatafile3Name(JBDatafile3Name);

            String savePath = path + "/" + JBDatafile3Name;
            System.out.println("[3]service : " + savePath);

            JBDatafile3.transferTo(new File(savePath));
        }


        try {
            // 회원가입 성공시 (에러나 예외처리가 없을 경우)
            System.out.println(job);
            int result = jbdao.JobModify(job);

            mav.setViewName("redirect:/JobView?JBNum=" + job.getJBNum());
        } catch (Exception e) {
            System.out.println("수정 실패!" + e);
            // 예외처리 발생 시
            mav.setViewName("redirect:/JobModify?JBNum=" + job.getJBNum());
            // 파일 삭제
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/jobfile");
            String deletePath1 = path + "/" + job.getJBDatafile1Name();

            File deleteFile1 = new File(deletePath1);

            if (deleteFile1.exists()) {
                deleteFile1.delete();
            }
        }


        return mav;
    }


    @Override
    public ModelAndView JobDelete(int jbNum) {
        mav = new ModelAndView();
        JOBDTO job = jbdao.JobView(jbNum);
        int result = jbdao.JobDelete(jbNum);

        if (result > 0) {
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/jobfile");
            ;

            // 기존에 있던 파일 지우기
            String deletePath1 = path + "/" + job.getJBDatafile1Name();
            String deletePath2 = path + "/" + job.getJBDatafile2Name();
            String deletePath3 = path + "/" + job.getJBDatafile3Name();
            File deleteFile1 = new File(deletePath1);

            if (deleteFile1.exists()) {
                deleteFile1.delete();
            }

            File deleteFile2 = new File(deletePath2);

            if (deleteFile2.exists()) {
                deleteFile2.delete();
            }

            File deleteFile3 = new File(deletePath3);

            if (deleteFile3.exists()) {
                deleteFile3.delete();
            }

            mav.setViewName("redirect:/JobList");
        } else {
            mav.setViewName("index");
        }

        return mav;
    }

    @Override
    public ModelAndView otherpmView(String jbId) {
        LocalDate now = LocalDate.now();
        PersonalDTO personal = jbdao.otherpmView(jbId);
        mav.addObject("personal", personal);
        int BirthYear = Integer.parseInt(personal.getPMBirth().substring(0, 4));
        int year = now.getYear();
        personal.setAge(year - BirthYear);

        String PMAddr = personal.getPMAddr().substring(7, 10);
        personal.setPMAddr(PMAddr);

        System.out.println("개인:" + personal);
        System.out.println(jbId);
        mav.setViewName("pmOhtersView");

        return mav;
    }

    @Override
    public void jbHitpl(int jbNum) {

        jbdao.jbHitpl(jbNum);

    }

    @Override
    public List<JOBDTO> getJobList5() {
        return jbdao.getJobList5();
    }

    @Override
    public ModelAndView closeJobPost(int JBNum) {
        mav = new ModelAndView();

        int result = jbdao.closeJobPost(JBNum);
        mav.setViewName("redirect:/JobList");
        return mav;
    }

    @Override
    public ModelAndView countJob() {
        mav=new ModelAndView();
        int jCount=jbdao.jCount();
        mav.addObject("jCount",jCount);
        mav.setViewName("jobList");
        return mav;
    }

    @Override
    public List<JOBDTO> findJList(CityDTO city) {
        List<JOBDTO> J=jbdao.findJList(city);
        return J;
    }

}
package com.icia.work.service;

import com.icia.work.dao.GuinDAO;
import com.icia.work.dto.CityDTO;
import com.icia.work.dto.CompanyDTO;
import com.icia.work.dto.GuinDTO;
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
public class GuinServiceImpl implements GuinService {

    private ModelAndView mav;

    private final GuinDAO guinDAO;

    @Override
    public ModelAndView GuinList() {
        mav = new ModelAndView();

        List<GuinDTO> GbList = guinDAO.GuinList();

        mav.setViewName("guinList");
        mav.addObject("GbList", GbList);
        System.out.println(GbList);
        return mav;
    }

    @Override
    public ModelAndView GuinView(int GBNum) {
        mav = new ModelAndView();

        GuinDTO guin = guinDAO.GuinView(GBNum);

        mav.setViewName("guinView");
        mav.addObject("GuinView", guin);

        return mav;
    }



    @Override
    public ModelAndView PMGuinWrite(GuinDTO guin) throws IOException {
        mav = new ModelAndView();

        // 파일 불러오기
        MultipartFile GBDatafile1 = guin.getGBDatafile1();
        MultipartFile GBDatafile2 = guin.getGBDatafile2();
        MultipartFile GBDatafile3 = guin.getGBDatafile3();

        // 파일 선택여부 확인
        // profile.isEmpty() : 파일이 선택되지 않았다.
        // !profile.isEmpty() : 파일이 선택됐다.
        if(!GBDatafile1.isEmpty()){

            // 파일 저장 위치 (상대경로) 설정
            Path path = Paths.get(System.getProperty("user.dir"),"src/main/resources/static/guinfile");

            // 기존에 있던 파일 지우기
            String deletePath = path + "/" + guin.getGBDatafile1Name();
            File deleteFile = new File(deletePath);

            if(deleteFile.exists()){
                deleteFile.delete();
            }

            // 난수 생성하기
            String uuid = UUID.randomUUID().toString().substring(0,8);

            // 파일 이름 불러오기
            String originalFilename = GBDatafile1.getOriginalFilename();

            // 난수 + 파일이름(새로운 파일 이름 생성)
            String GBDatafile1Name = uuid + "_" + originalFilename;

            // member객체에 새로운 파일 이름 저장
            guin.setGBDatafile1Name(GBDatafile1Name);

            // 저장될 폴더와 파일 이름
            String savePath = path + "/" + GBDatafile1Name;

            // 파일 업로드
            GBDatafile1.transferTo(new File(savePath));

        }

        if(!GBDatafile2.isEmpty()){

            // 파일 저장 위치 (상대경로) 설정
            Path path = Paths.get(System.getProperty("user.dir"),"src/main/resources/static/guinfile");

            // 기존에 있던 파일 지우기
            String deletePath = path + "/" + guin.getGBDatafile2Name();
            File deleteFile = new File(deletePath);

            if(deleteFile.exists()){
                deleteFile.delete();
            }

            // 난수 생성하기
            String uuid = UUID.randomUUID().toString().substring(0,8);

            // 파일 이름 불러오기
            String originalFilename = GBDatafile2.getOriginalFilename();

            // 난수 + 파일이름(새로운 파일 이름 생성)
            String GBDatafile2Name = uuid + "_" + originalFilename;

            // member객체에 새로운 파일 이름 저장
            guin.setGBDatafile2Name(GBDatafile2Name);

            // 저장될 폴더와 파일 이름
            String savePath = path + "/" + GBDatafile2Name;

            // 파일 업로드
            GBDatafile2.transferTo(new File(savePath));

        }

        if(!GBDatafile3.isEmpty()){

            // 파일 저장 위치 (상대경로) 설정
            Path path = Paths.get(System.getProperty("user.dir"),"src/main/resources/static/guinfile");

            // 기존에 있던 파일 지우기
            String deletePath = path + "/" + guin.getGBDatafile3Name();
            File deleteFile = new File(deletePath);

            if(deleteFile.exists()){
                deleteFile.delete();
            }

            // 난수 생성하기
            String uuid = UUID.randomUUID().toString().substring(0,8);

            // 파일 이름 불러오기
            String originalFilename = GBDatafile3.getOriginalFilename();

            // 난수 + 파일이름(새로운 파일 이름 생성)
            String GBDatafile3Name = uuid + "_" + originalFilename;

            // member객체에 새로운 파일 이름 저장
            guin.setGBDatafile3Name(GBDatafile3Name);

            // 저장될 폴더와 파일 이름
            String savePath = path + "/" + GBDatafile3Name;

            // 파일 업로드
            GBDatafile3.transferTo(new File(savePath));

        }

        String addr1 = guin.getGBAddr1();
        String addr2 = guin.getGBAddr2();
        String addr3 = guin.getGBAddr3();
        String addr = "(" + addr1 + ")" + addr2 + "," + addr3;
        guin.setGBAddr(addr);

        int result = guinDAO.PMGuinWrite(guin);
        System.out.println(guin);
        mav.setViewName("redirect:/GuinList");

        return mav;
    }



    @Override
    public ModelAndView GuinDelete(int GBNum) {
        mav = new ModelAndView();
        GuinDTO guin = guinDAO.GuinView(GBNum);
        int result = guinDAO.GuinDelete(GBNum);

        if(result>0){
            Path path = Paths.get(System.getProperty("user.dir"),"src/main/resources/static/guinfile");;

            // 기존에 있던 파일 지우기
            String deletePath1 = path + "/" + guin.getGBDatafile1Name();
            String deletePath2 = path + "/" + guin.getGBDatafile2Name();
            String deletePath3 = path + "/" + guin.getGBDatafile3Name();
            File deleteFile1 = new File(deletePath1);

            if(deleteFile1.exists()){
                deleteFile1.delete();
            }

            File deleteFile2 = new File(deletePath2);

            if(deleteFile2.exists()){
                deleteFile2.delete();
            }

            File deleteFile3 = new File(deletePath3);

            if(deleteFile3.exists()){
                deleteFile3.delete();
            }

            mav.setViewName("redirect:/GuinList");
        } else {
            mav.setViewName("index");
        }

        return mav;
    }

    @Override
    public ModelAndView GuinModify(int GBNum) {
        mav = new ModelAndView();
        GuinDTO guin = guinDAO.GuinView(GBNum);

        mav.setViewName("guinModify");
        mav.addObject("GuinModify", guin);

        return mav;
    }

    @Override
    public ModelAndView GuinModify(GuinDTO guin) throws IOException {
        mav = new ModelAndView();

        // 파일업로드 설정
        MultipartFile GBDatafile1 = guin.getGBDatafile1();
        MultipartFile GBDatafile2 = guin.getGBDatafile2();
        MultipartFile GBDatafile3 = guin.getGBDatafile3();

        if (!GBDatafile1.isEmpty()) {
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/guinfile");

            String deletePath1 = path + "/" + guin.getGBDatafile1Name();
            File deleteFile1 = new File(deletePath1);

            if(deleteFile1.exists()){
                deleteFile1.delete();
            }

            String uuid = UUID.randomUUID().toString().substring(0, 8);

            String originalFileName = GBDatafile1.getOriginalFilename();

            String GBDatafile1Name = uuid + "_" + originalFileName;

            guin.setGBDatafile1Name(GBDatafile1Name);

            String savePath = path + "/" + GBDatafile1Name;
            System.out.println("[3]service : " + savePath);

            GBDatafile1.transferTo(new File(savePath));
        }

        if (!GBDatafile2.isEmpty()) {
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/guinfile");

            String deletePath2 = path + "/" + guin.getGBDatafile2Name();
            File deleteFile2 = new File(deletePath2);

            if(deleteFile2.exists()){
                deleteFile2.delete();
            }

            String uuid = UUID.randomUUID().toString().substring(0, 8);

            String originalFileName = GBDatafile2.getOriginalFilename();

            String GBDatafile2Name = uuid + "_" + originalFileName;

            guin.setGBDatafile2Name(GBDatafile2Name);

            String savePath = path + "/" + GBDatafile2Name;
            System.out.println("[3]service : " + savePath);

            GBDatafile2.transferTo(new File(savePath));
        }

        if (!GBDatafile3.isEmpty()) {
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/guinfile");

            String deletePath3 = path + "/" + guin.getGBDatafile3Name();
            File deleteFile3 = new File(deletePath3);

            if(deleteFile3.exists()){
                deleteFile3.delete();
            }

            String uuid = UUID.randomUUID().toString().substring(0, 8);

            String originalFileName = GBDatafile3.getOriginalFilename();

            String GBDatafile3Name = uuid + "_" + originalFileName;

            guin.setGBDatafile3Name(GBDatafile3Name);

            String savePath = path + "/" + GBDatafile3Name;
            System.out.println("[3]service : " + savePath);

            GBDatafile3.transferTo(new File(savePath));
        }



        // 주소 api 결합(나중에 따로 해보기)
        String addr1 = guin.getGBAddr1();
        String addr2 = guin.getGBAddr2();
        String addr3 = guin.getGBAddr3();
        String addr = "(" + addr1 + ")" + addr2 + "," + addr3;
        guin.setGBAddr(addr);
        try {
            // 회원가입 성공시 (에러나 예외처리가 없을 경우)
            System.out.println(guin);
            int result = guinDAO.GuinModify(guin);

            mav.setViewName("redirect:/GuinView?GBNum="+guin.getGBNum());
        } catch (Exception e) {
            System.out.println("수정 실패!"+e);
            // 예외처리 발생 시
            mav.setViewName("redirect:/GuinModify?GBNum="+guin.getGBNum());
            // 파일 삭제
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/guinfile");
            String deletePath1 = path + "/" + guin.getGBDatafile1Name();

            File deleteFile1 = new File(deletePath1);

            if (deleteFile1.exists()) {
                deleteFile1.delete();
            }
        }


        return mav;
    }

    @Override
    public ModelAndView OtherView(String gbId, String gbScale) {
        mav = new ModelAndView();

        if(gbScale== "기업"){
            CompanyDTO company= guinDAO.othercmView(gbId);
            mav.addObject("company",company);
            mav.setViewName("cmOthersView");
            System.out.println("회사:"+company);
            System.out.println(gbId);
        }else {
            LocalDate now = LocalDate.now();
            PersonalDTO personal= guinDAO.otherpmView(gbId);
            System.out.println("개인:"+personal);
            System.out.println(gbId);
            int BirthYear = Integer.parseInt(personal.getPMBirth().substring(0,4));
            int year=now.getYear();

            personal.setAge(year - BirthYear);

            String PMAddr = personal.getPMAddr().substring(7,10);
            personal.setPMAddr(PMAddr);


            mav.addObject("personal", personal);
            mav.setViewName("pmOhtersView");
        }
            return mav;
    }

    @Override
    public void gbHitpl(int gbNum){
        guinDAO.gbHitpl(gbNum);
    }

    @Override
    public List<GuinDTO> getGuinList5() {
        return guinDAO.getGuinList5();
    }

    @Override
    public ModelAndView countGuin() {
        mav=new ModelAndView();
        int gCount=guinDAO.gConunt();
        mav.addObject("gCount",gCount);
        mav.setViewName("guinList");
        return mav;
    }

    @Override
    public ModelAndView closeGuinPost(int gbNum) {
        mav = new ModelAndView();
        System.out.println("마감게시글번호"+gbNum);
        int result = guinDAO.closeGuinPost(gbNum);
        System.out.println(result);
        mav.setViewName("redirect:/GuinList");

        return mav;
    }

    @Override
    public List<GuinDTO> findGList(CityDTO city) {
        List<GuinDTO> G=guinDAO.findGList(city);
        return G;
    }
}

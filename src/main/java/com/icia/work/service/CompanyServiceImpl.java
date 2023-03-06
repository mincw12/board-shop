package com.icia.work.service;

import com.icia.work.dao.CompanyDAO;
import com.icia.work.dto.CompanyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final JavaMailSender mailSender;
    private final CompanyDAO CMdao;

    private final PasswordEncoder pwEnc;

    private ModelAndView mav;

    private final HttpSession session;


    @Override
    public ModelAndView CMJoin(CompanyDTO company) throws IOException {
        mav = new ModelAndView();

        // 비밀번호 암호화
        // [1] 입력한 비밀번호 가져오기 : member.getMemPw()
        // [2] 입력한 비밀번호 암호화 : pwEnc.encode()
        // [3] 암호화 된 비밀번호 저장 : member.setMemPw()

        company.setCMPw(pwEnc.encode(company.getCMPw()));


        // 파일업로드 설정
        MultipartFile PMProfile = company.getCMProfile();
        System.out.println("[2]service member : " + company);

        System.out.println("암호화 된 비밀번호 : " + company.getCMPw());

        if (!PMProfile.isEmpty()) {
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");

            String uuid = UUID.randomUUID().toString().substring(0, 8);

            String originalFileName = PMProfile.getOriginalFilename();

            String companyProfileName = uuid + "_" + originalFileName;

            company.setCMProfileName(companyProfileName);

            String savePath = path + "/" + companyProfileName;
            System.out.println("[3]service member : " + savePath);

            PMProfile.transferTo(new File(savePath));
        }


        // 주소 api 결합(나중에 따로 해보기)
        String addr1 = company.getAddr1();
        String addr2 = company.getAddr2();
        String addr3 = company.getAddr3();
        String addr = "(" + addr1 + ")" + addr2 + "," + addr3;
        company.setCMAddr(addr);
        try {
            // 회원가입 성공시 (에러나 예외처리가 없을 경우)
            System.out.println(company);
            int result = CMdao.CMJoin(company);

            mav.setViewName("index");
        } catch (Exception e) {
            System.out.println("회원가입 실패!" + e);
            // 예외처리 발생 시
            mav.setViewName("pmJoin");
            // 파일 삭제
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");
            String deletePath = path + "/" + company.getCMProfileName();

            File deleteFile = new File(deletePath);

            if (deleteFile.exists()) {
                deleteFile.delete();
            }
        }
        return mav;
    }

    @Override
    public ModelAndView CMLogin(CompanyDTO company) {
        mav = new ModelAndView();

        CompanyDTO loginMember = CMdao.CMView(company.getMId());

        // member 객체 : memId, memPw(입력한값)

        // loginMember 객체 : memId, memPw(암호화o), ........ all

        //입력한 비밀번호 : getMemPw()와 암호화 된 비밀번호 : loginMember.getMemPw()를
        // pwEnc.mathces()로 비교하여서 일치하면 true값, 불일치하면 false값을 반환한다.
        try {
            if (pwEnc.matches(company.getCMPw(), loginMember.getCMPw())) {
                session.setAttribute("login", loginMember);
                mav.setViewName("redirect:/index");
            } else {
                mav.addObject("loginfail","아이디 혹은 비밀번호가 일치하지 않습니다.");
                mav.setViewName("login");
            }}catch (Exception e){
            System.out.println(e);
            System.out.println(loginMember);
            mav.addObject("loginfail","아이디 혹은 비밀번호가 일치하지 않습니다.");
            mav.setViewName("login");
        }

        return mav;
    }

    @Override
    public ModelAndView cmMyView(String mId) {
        mav = new ModelAndView();
        CompanyDTO company = CMdao.CMView(mId);
        mav.addObject("company", company);
        mav.setViewName("cmMyView");
        return mav;
    }

    @Override
    public ModelAndView cmModify(String mId) {
        mav = new ModelAndView();
        CompanyDTO cmModifyMember = CMdao.CMView(mId);

        mav.setViewName("cmModify");
        mav.addObject("cmModify", cmModifyMember);

        return mav;
    }

    @Override
    public ModelAndView cmModify(CompanyDTO company) throws IOException {
        mav = new ModelAndView();

        // 파일업로드 설정
        MultipartFile PMProfile = company.getCMProfile();
        System.out.println("[2]service member : " + company);

        System.out.println("암호화 된 비밀번호 : " + company.getCMPw());

        if (!PMProfile.isEmpty()) {
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");

            String deletePath = path + "/" + company.getCMProfileName();
            File deleteFile = new File(deletePath);

            if (deleteFile.exists()) {
                deleteFile.delete();
            }

            String uuid = UUID.randomUUID().toString().substring(0, 8);

            String originalFileName = PMProfile.getOriginalFilename();

            String memProfileName = uuid + "_" + originalFileName;

            company.setCMProfileName(memProfileName);

            String savePath = path + "/" + memProfileName;
            System.out.println("[3]service member : " + savePath);

            PMProfile.transferTo(new File(savePath));
        }


        // 주소 api 결합(나중에 따로 해보기)
        String addr1 = company.getAddr1();
        String addr2 = company.getAddr2();
        String addr3 = company.getAddr3();
        String addr = "(" + addr1 + ")" + addr2 + "," + addr3;
        company.setCMAddr(addr);
        try {
            // 회원가입 성공시 (에러나 예외처리가 없을 경우)
            System.out.println(company);
            int result = CMdao.CMmodify(company);

            mav.setViewName("redirect:/cmMyView?MId=" + company.getMId());
        } catch (Exception e) {
            System.out.println("수정 실패!" + e);
            // 예외처리 발생 시
            mav.setViewName("redirect:/cmModify?MId=" + company.getMId());
            // 파일 삭제
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");
            String deletePath = path + "/" + company.getCMProfileName();

            File deleteFile = new File(deletePath);

            if (deleteFile.exists()) {
                deleteFile.delete();
            }
        }

        return mav;
    }

    @Override
    public ModelAndView cmDelete(String MId) {
        mav = new ModelAndView();

        int result = CMdao.cmDelete(MId);

        if(result>0) {
            mav.setViewName("redirect:/Logout");
        } else {
            mav.setViewName("redirect:/cmMyView?MId="+MId);
        }

        return mav;
    }

    @Override
    public ModelAndView cmidfind(String cmEmail) {
        mav = new ModelAndView();

        String result = CMdao.cmidfind(cmEmail);

        if(result != null){
            MimeMessage mail = mailSender.createMimeMessage();

            String mailContent = "<h2>안녕하세요. 준서위의 그라운드입니다.</h2><br/>"
                    + "<h3>아이디는 " + result + " 입니다.</h3>";

            try {
                mail.setSubject("[아이디 찾기]", "UTF-8");
                mail.setText(mailContent, "UTF-8", "html");
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress(cmEmail));
                mailSender.send(mail);


            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            mav.setViewName("login");

        }else{
            mav.setViewName("redirect:/cmidfind");
        }
        return mav;
    }

    @Override
    public ModelAndView cmPwChange(CompanyDTO company, String newPw) {
        mav = new ModelAndView();

        System.out.println("비밀번호 변경 중 가져온 비번: "+company+"변경할비번:"+newPw);
        System.out.println(company.getMId());
        CompanyDTO loginMember = CMdao.company(company.getMId());
        System.out.println("에러체크:"+loginMember);

        // member 객체 : memId, memPw(입력한값)

        // loginMember 객체 : memId, memPw(암호화o), ........ all

        //입력한 비밀번호 : getMemPw()와 암호화 된 비밀번호 : loginMember.getMemPw()를
        // pwEnc.mathces()로 비교하여서 일치하면 true값, 불일치하면 false값을 반환한다.
        try{
            if(pwEnc.matches(company.getCMPw(), loginMember.getCMPw())){
                company.setCMPw(pwEnc.encode(newPw));
                CMdao.UpdatePw(company);
                mav.setViewName("redirect:/index");

            } else {
                System.out.println("에러없음"+loginMember);
                mav.addObject("pwFail","비밀번호가 일치하지 않습니다.");
                mav.setViewName("pmPwChange");

            }}catch (Exception e){
            System.out.println(e);
            System.out.println(loginMember);
            mav.addObject("pwFail","비밀번호가 일치하지 않습니다.");
            mav.setViewName("pmPwChange");
        }

        return mav;
    }

    @Override
    public ModelAndView cmFindPw(CompanyDTO company) {
        mav = new ModelAndView();
        String newpw = company.getCMPw();
        company.setCMPw(pwEnc.encode(newpw));

        CMdao.UpdatePw(company);

        mav.setViewName("redirect:/Login");

        return mav;
    }

    @Override
    public ModelAndView companyList() {
        mav = new ModelAndView();

        List<CompanyDTO> cList = CMdao.companyList();

        mav.setViewName("cmList");
        mav.addObject("cList", cList);
        System.out.println(cList);
        return mav;
    }

}


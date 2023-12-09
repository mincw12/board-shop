package com.icia.work.service;

import com.icia.work.dao.MainDAO;
import com.icia.work.dto.MemberDTO;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MainServicempl implements MainService {
    private final JavaMailSender mailSender;
    private final HttpSession session;
    private final MainDAO Mdao;
    private final PasswordEncoder pwEnc;
    private ModelAndView mav;

    @Override
    public String idoverlap(String mId) {
        String result = Mdao.idoverlap(mId);

        if (result != null) {
            // 아이디 존재(중복o)
            return "NO";
        } else {
            // 아이디 존재x (중복x)
            return "OK";
        }
    }

    @Override
    public String MCheckEmail(String mEmail) {
        String uuid = UUID.randomUUID().toString().substring(0, 6);

        MimeMessage mail = mailSender.createMimeMessage();

        String mailContent = "<h2>안녕하세요. BOARDShop입니다.</h2><br/>"
                + "<h3>인증번호는 " + uuid + " 입니다.</h3>";

        try {
            mail.setSubject("[이메일 인증] BOARDShop 이메일 인증", "UTF-8");
            mail.setText(mailContent, "UTF-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
            mailSender.send(mail);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return uuid;
    }

    @Override
    public ModelAndView MJoin(MemberDTO member) throws IOException {
        mav = new ModelAndView();

        // 비밀번호 암호화
        // [1] 입력한 비밀번호 가져오기 : member.getMemPw()
        // [2] 입력한 비밀번호 암호화 : pwEnc.encode()
        // [3] 암호화 된 비밀번호 저장 : member.setMemPw()

        member.setMPw(pwEnc.encode(member.getMPw()));


        // 파일업로드 설정
        MultipartFile PMProfile = member.getMProfile();
        System.out.println("[2]service member : " + member);

        System.out.println("암호화 된 비밀번호 : " + member.getMPw());

        if (!PMProfile.isEmpty()) {
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/userProfile");

            String uuid = UUID.randomUUID().toString().substring(0, 8);

            String originalFileName = PMProfile.getOriginalFilename();

            String memProfileName = uuid + "_" + originalFileName;

            member.setMProfileName(memProfileName);

            String savePath = path + "/" + memProfileName;
            System.out.println("[3]service member : " + savePath);

            PMProfile.transferTo(new File(savePath));
        }


        // 주소 api 결합(나중에 따로 해보기)
        String addr1 = member.getAddr1();
        String addr2 = member.getAddr2();
        String addr3 = member.getAddr3();
        String addr = "(" + addr1 + ")" + addr2 + "," + addr3;
        member.setMAddr(addr);
        try {
            // 회원가입 성공시 (에러나 예외처리가 없을 경우)
            System.out.println(member);
            int result = Mdao.mJoin(member);

            mav.setViewName("loginPage");
        } catch (Exception e) {
            System.out.println("회원가입 실패!" + e);
            // 예외처리 발생 시
            mav.setViewName("register");
            // 파일 삭제
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");
            String deletePath = path + "/" + member.getMProfileName();

            File deleteFile = new File(deletePath);

            if (deleteFile.exists()) {
                deleteFile.delete();
            }
        }
        return mav;}

    @Override
    public ModelAndView mLogin(MemberDTO member) {
        mav = new ModelAndView();

        MemberDTO loginMember = Mdao.MyView(member.getMId());

        try{
            if(pwEnc.matches(member.getMPw(), loginMember.getMPw())){
                    session.setAttribute("login", loginMember);
                    mav.setViewName("redirect:/index");

            } else {
                System.out.println("에러없음"+loginMember);
                mav.addObject("loginfail","아이디 혹은 비밀번호가 일치하지 않습니다.");
                mav.setViewName("loginPage");

            }}catch (Exception e){
            System.out.println(e);
            System.out.println(loginMember);
            mav.addObject("loginfail","아이디 혹은 비밀번호가 일치하지 않습니다.");
            mav.setViewName("loginPage");
        }

        return mav;
    }

    @Override
    public ModelAndView MyView(String mId) {
        mav=new ModelAndView();
        MemberDTO member = Mdao.MyView(mId);

        mav.addObject("member",member);
        mav.setViewName("myInfo");
        return mav;
    }

    @Override
    public ModelAndView mDelete(String mId) {
        mav = new ModelAndView();

        int result = Mdao.mDelete(mId);

        if(result>0) {
            mav.setViewName("redirect:/Logout");
        } else {
            mav.setViewName("redirect:/pmMyView?MId="+mId);
        }

        return mav;
    }

    @Override
    public ModelAndView ModifyPage(String mId) {
        mav = new ModelAndView();
        MemberDTO ModifyMember = Mdao.MyView(mId);
        System.out.println(ModifyMember);
        mav.setViewName("EditMyInfo");
        mav.addObject("Modify", ModifyMember);

        return mav;
    }

    @Override
    public ModelAndView Modify(MemberDTO member) throws IOException {
        mav = new ModelAndView();

        // 파일업로드 설정
        MultipartFile PMProfile = member.getMProfile();
        System.out.println("[2]service member : " + member);

        System.out.println("암호화 된 비밀번호 : " + member.getMPw());

        if (!PMProfile.isEmpty()) {
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/userProfile");

            String deletePath = path + "/" + member.getMProfileName();
            File deleteFile = new File(deletePath);

            if (deleteFile.exists()) {
                deleteFile.delete();
            }

            String uuid = UUID.randomUUID().toString().substring(0, 8);

            String originalFileName = PMProfile.getOriginalFilename();

            String memProfileName = uuid + "_" + originalFileName;

            member.setMProfileName(memProfileName);

            String savePath = path + "/" + memProfileName;
            System.out.println("[3]service member : " + savePath);

            PMProfile.transferTo(new File(savePath));
        }

        try {
            // 회원가입 성공시 (에러나 예외처리가 없을 경우)
            System.out.println(member);
            int result = Mdao.Modify(member);

            mav.setViewName("redirect:/MyView?MId="+member.getMId());
        } catch (Exception e) {
            System.out.println("수정 실패!"+e);
            // 예외처리 발생 시
            mav.setViewName("redirect:/ModifyPage?MId="+member.getMId());
            // 파일 삭제
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");
            String deletePath = path + "/" + member.getMProfileName();

            File deleteFile = new File(deletePath);

            if (deleteFile.exists()) {
                deleteFile.delete();
            }
        }


        return mav;}

    @Override
    public ModelAndView contactEmail(String EMail, String msg) {
        mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @Override
    public String changePw(String mId, String nowPw, String newPw) {
        String msg = null;
        MemberDTO member = Mdao.MyView(mId);
        System.out.println(member);
        System.out.println(nowPw);
        try{
            if(pwEnc.matches(nowPw, member.getMPw())){
                member.setMPw(pwEnc.encode(newPw));
                int result = Mdao.setMPw(member);
                if(result>0){
                    msg="OK";
                }else{
                    msg="fail";
                }
            } else {
                System.out.println("에러없음"+member);
                msg="pn";//비밀 번호 불일치
            }}catch (Exception e){
            System.out.println(e);
            msg="er"; //오류 발생
        }
        return msg;
    }


}





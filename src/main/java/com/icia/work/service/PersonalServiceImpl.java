package com.icia.work.service;

import com.icia.work.dao.PersonalDAO;
import com.icia.work.dto.PersonalDTO;
import com.icia.work.dto.suspendDTO;
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
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonalServiceImpl implements PersonalService {
    private final JavaMailSender mailSender;
    private final PersonalDAO PMdao;

    private final PasswordEncoder pwEnc;

    private ModelAndView mav;

    private final HttpSession session;

    @Override
    public String idoverlap(String MId) {
        String result = PMdao.idoverlap(MId);

        if (result != null) {
            // 아이디 존재(중복o)
            return "NO";
        } else {
            // 아이디 존재x (중복x)
            return "OK";
        }
    }

    @Override
    public ModelAndView PMJoin(PersonalDTO personal) throws IOException {
        mav = new ModelAndView();

        // 비밀번호 암호화
        // [1] 입력한 비밀번호 가져오기 : member.getMemPw()
        // [2] 입력한 비밀번호 암호화 : pwEnc.encode()
        // [3] 암호화 된 비밀번호 저장 : member.setMemPw()

        personal.setPMPw(pwEnc.encode(personal.getPMPw()));


        // 파일업로드 설정
        MultipartFile PMProfile = personal.getPMProfile();
        System.out.println("[2]service member : " + personal);

        System.out.println("암호화 된 비밀번호 : " + personal.getPMPw());

        if (!PMProfile.isEmpty()) {
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");

            String uuid = UUID.randomUUID().toString().substring(0, 8);

            String originalFileName = PMProfile.getOriginalFilename();

            String memProfileName = uuid + "_" + originalFileName;

            personal.setPMProfileName(memProfileName);

            String savePath = path + "/" + memProfileName;
            System.out.println("[3]service member : " + savePath);

            PMProfile.transferTo(new File(savePath));
        }


        // 주소 api 결합(나중에 따로 해보기)
        String addr1 = personal.getAddr1();
        String addr2 = personal.getAddr2();
        String addr3 = personal.getAddr3();
        String addr = "(" + addr1 + ")" + addr2 + "," + addr3;
        personal.setPMAddr(addr);
        try {
            // 회원가입 성공시 (에러나 예외처리가 없을 경우)
            System.out.println(personal);
            int result = PMdao.PMJoin(personal);

            mav.setViewName("index");
        } catch (Exception e) {
            System.out.println("회원가입 실패!"+e);
            // 예외처리 발생 시
            mav.setViewName("login");
            // 파일 삭제
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");
            String deletePath = path + "/" + personal.getPMProfileName();

            File deleteFile = new File(deletePath);

            if (deleteFile.exists()) {
                deleteFile.delete();
            }
        }


        return mav;
    }

    @Override
    public String mCheckEmail(String PMEmail) {

        String uuid = UUID.randomUUID().toString().substring(0, 6);

        MimeMessage mail = mailSender.createMimeMessage();

        String mailContent = "<h2>안녕하세요. ALABA입니다.</h2><br/>"
                + "<h3>인증번호는 " + uuid + " 입니다.</h3>";

        try {
            mail.setSubject("[이메일 인증] ALABA 이메일 인증", "UTF-8");
            mail.setText(mailContent, "UTF-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(PMEmail));
            mailSender.send(mail);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return uuid;
    }

    @Override
    public ModelAndView PMLogin(PersonalDTO person) {
        mav = new ModelAndView();

        PersonalDTO loginMember = PMdao.PMView(person.getMId());

        // member 객체 : memId, memPw(입력한값)

        // loginMember 객체 : memId, memPw(암호화o), ........ all

        //입력한 비밀번호 : getMemPw()와 암호화 된 비밀번호 : loginMember.getMemPw()를
        // pwEnc.mathces()로 비교하여서 일치하면 true값, 불일치하면 false값을 반환한다.
        try{
            if(pwEnc.matches(person.getPMPw(), loginMember.getPMPw())){
                suspendDTO suspend=PMdao.checkSuspend(person.getMId());
                if(suspend==null){
                session.setAttribute("login", loginMember);
                mav.setViewName("redirect:/index");
                }else{
                    String suspendDate=suspend.getSuspendDate();
                    String suspendset=suspendDate.substring(0,4)+"년 "+suspendDate.substring(4,6)+"월 "+suspendDate.substring(6,8)+"일";
                    mav.addObject("loginfail","이 계정은 "+suspendset+"까지 사용하실 수 없습니다.");
                    mav.setViewName("login");
                }
            } else {
                System.out.println("에러없음"+loginMember);
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
    public ModelAndView pmMyView(String mId) {
        mav=new ModelAndView();
        PersonalDTO personal = PMdao.PMView(mId);
        suspendDTO suspend = PMdao.checkSuspend(mId);
        if(suspend!=null){
            mav.addObject("susDate",suspend.getSuspendDate());
        }
        mav.addObject("personal",personal);
        mav.setViewName("pmMyView");
        return mav;
    }

    @Override
    public ModelAndView pmModify(String MId) {
        mav = new ModelAndView();
        PersonalDTO pmModifyMember = PMdao.PMView(MId);

        mav.setViewName("pmModify");
        mav.addObject("pmModify", pmModifyMember);

        return mav;
    }

    @Override
    public ModelAndView pmModify(PersonalDTO personal) throws IOException {
        mav = new ModelAndView();

        // 파일업로드 설정
        MultipartFile PMProfile = personal.getPMProfile();
        System.out.println("[2]service member : " + personal);

        System.out.println("암호화 된 비밀번호 : " + personal.getPMPw());

        if (!PMProfile.isEmpty()) {
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");

            String deletePath = path + "/" + personal.getPMProfileName();
            File deleteFile = new File(deletePath);

            if(deleteFile.exists()){
                deleteFile.delete();
            }

            String uuid = UUID.randomUUID().toString().substring(0, 8);

            String originalFileName = PMProfile.getOriginalFilename();

            String memProfileName = uuid + "_" + originalFileName;

            personal.setPMProfileName(memProfileName);

            String savePath = path + "/" + memProfileName;
            System.out.println("[3]service member : " + savePath);

            PMProfile.transferTo(new File(savePath));
        }



        // 주소 api 결합(나중에 따로 해보기)
        String addr1 = personal.getAddr1();
        String addr2 = personal.getAddr2();
        String addr3 = personal.getAddr3();
        String addr = "(" + addr1 + ")" + addr2 + "," + addr3;
        personal.setPMAddr(addr);
        try {
            // 회원가입 성공시 (에러나 예외처리가 없을 경우)
            System.out.println(personal);
            int result = PMdao.PMmodify(personal);

            mav.setViewName("redirect:/pmMyView?MId="+personal.getMId());
        } catch (Exception e) {
            System.out.println("수정 실패!"+e);
            // 예외처리 발생 시
            mav.setViewName("redirect:/pmModify?MId="+personal.getMId());
            // 파일 삭제
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");
            String deletePath = path + "/" + personal.getPMProfileName();

            File deleteFile = new File(deletePath);

            if (deleteFile.exists()) {
                deleteFile.delete();
            }
        }


        return mav;
    }

    @Override
    public ModelAndView pmDelete(String MId) {

        mav = new ModelAndView();

        int result = PMdao.pmDelete(MId);

        if(result>0) {
            mav.setViewName("redirect:/Logout");
        } else {
            mav.setViewName("redirect:/pmMyView?MId="+MId);
        }

        return mav;
    }

    @Override
    public ModelAndView idfind(String pmEmail) {
        mav = new ModelAndView();

        String result = PMdao.idfind(pmEmail);

        if(result != null){
            MimeMessage mail = mailSender.createMimeMessage();

            String mailContent = "<h2>안녕하세요. ALABA입니다.</h2><br/>"
                    + "<h3>회원님의 아이디는 " + result + " 입니다.</h3>";

            try {
                mail.setSubject("[아이디 찾기]", "UTF-8");
                mail.setText(mailContent, "UTF-8", "html");
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress(pmEmail));
                mailSender.send(mail);


            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            mav.setViewName("login");

        }else{
            mav.setViewName("redirect:/find");
        }
        return mav;
    }

    @Override
    public ModelAndView pmPwChange(PersonalDTO person, String newPw) {
        mav = new ModelAndView();
        System.out.println("비밀번호 변경 중 가져온 비번: "+person+"변경할비번:"+newPw);
        System.out.println(person.getMId());
        PersonalDTO loginMember = PMdao.personal(person.getMId());
        System.out.println("에러체크:"+loginMember);

        // member 객체 : memId, memPw(입력한값)

        // loginMember 객체 : memId, memPw(암호화o), ........ all

        //입력한 비밀번호 : getMemPw()와 암호화 된 비밀번호 : loginMember.getMemPw()를
        // pwEnc.mathces()로 비교하여서 일치하면 true값, 불일치하면 false값을 반환한다.
        try{
            if(pwEnc.matches(person.getPMPw(), loginMember.getPMPw())){
                person.setPMPw(pwEnc.encode(newPw));
                PMdao.UpdatePw(person);
                mav.setViewName("redirect:/index");
                /*mav.setViewName("redirect:/pmMyview?MId="+person.getMId());*/
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
    public ModelAndView pmFindPw(PersonalDTO personal) {
        mav = new ModelAndView();
        String newpw = personal.getPMPw();
        personal.setPMPw(pwEnc.encode(newpw));
        //비밀번호 찾기 인증후 비밀번호 변경
        PMdao.UpdatePw(personal);
        // member 객체 : memId, memPw(입력한값)

        // loginMember 객체 : memId, memPw(암호화o), ........ all

        //입력한 비밀번호 : getMemPw()와 암호화 된 비밀번호 : loginMember.getMemPw()를
        // pwEnc.mathces()로 비교하여서 일치하면 true값, 불일치하면 false값을 반환한다.

        mav.setViewName("redirect:/Login");

        return mav;
    }

    @Override
    public ModelAndView personList() {
        mav = new ModelAndView();

        List<PersonalDTO> pList = PMdao.personList();

        //정지 해제일이 된 회원 정지 해제
        List<suspendDTO> suspend = PMdao.suspendList();
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int monthValue = now.getMonthValue();
        int dayOfMonth = now.getDayOfMonth();
        String nowdate=year+""+monthValue+""+dayOfMonth;
        int nowdates=Integer.parseInt(nowdate);
        for(int i=0;i<suspend.size();i++){
            int suspenddate=Integer.parseInt(suspend.get(i).getSuspendDate());
            if(nowdates<=suspenddate){
                //정지 해제 기간이 된 회원 정지 데이터 삭제
                PMdao.deletesuspend(suspend.get(i).getMId());
            }
        }

        mav.setViewName("pmList");
        mav.addObject("pList", pList);
        System.out.println(pList);
        return mav;
    }


    @Override
    public String setSuspended(suspendDTO suspend) {
        String suspendText="실패했습니다.";
        try{
            PMdao.deletesuspend(suspend.getMId());
        }catch (Exception e){

        }
        if(suspend.getSuspendDays()!=0) {
            int result = PMdao.setSuspend(suspend);
            if (result > 0) {
                suspendText = suspend.getSuspendDays() + "일 정지 되었습니다";
            }
        }else{
            suspendText = "정지가 해제 되었습니다.";
        }

        return suspendText;
    }

}

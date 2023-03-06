package com.icia.work.service;

import com.icia.work.dao.CompanyDAO;
import com.icia.work.dao.FunctDAO;
import com.icia.work.dao.GuinDAO;
import com.icia.work.dto.CityDTO;
import com.icia.work.dto.CompanyDTO;
import com.icia.work.dto.PersonalDTO;
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
public class FunctServicempl implements FunctService {
    private final JavaMailSender mailSender;
    private final FunctDAO fdao;
    private final GuinDAO gdao;
    private final PasswordEncoder pwEnc;

    private ModelAndView mav;

    private final HttpSession session;


    @Override
    public List<CityDTO> getsigun(String sido) {

        return fdao.getsigun(sido);
    }

    @Override
    public String getRType(String MId) {
        return fdao.getRType(MId);
    }
    @Override
    public ModelAndView OthersView(String MId) {
        mav = new ModelAndView();
        String RType=getRType(MId);
        if(RType.equals("개인")){
            LocalDate now = LocalDate.now();
            PersonalDTO personal= gdao.otherpmView(MId);
            System.out.println("개인:"+personal);
            System.out.println(MId);
            int BirthYear = Integer.parseInt(personal.getPMBirth().substring(0,4));
            int year=now.getYear();
            personal.setAge(year - BirthYear);
            String PMAddr = personal.getPMAddr().substring(7,10);
            personal.setPMAddr(PMAddr);
            mav.addObject("personal", personal);
            mav.setViewName("pmOhtersView");}
        else{
            CompanyDTO company= gdao.othercmView(MId);
            mav.addObject("company",company);
            mav.setViewName("cmOthersView");
            System.out.println("회사:"+company);}
        return mav;
    }


}


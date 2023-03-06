package com.icia.work.controller;

import com.icia.work.dto.PersonalDTO;
import com.icia.work.service.PersonalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PersonalController {
    private final PersonalService psvc;
    private ModelAndView mav;
    @GetMapping("/index")
    public String index(){
        return "index";
    }
    @GetMapping("/PMJoin")
    public String PMJoin(){
        return "pmJoin";
    }
    @PostMapping ("/PMJoin")
    public ModelAndView PMJoin(@ModelAttribute PersonalDTO personal) throws IOException {
        System.out.println(personal);
        mav=psvc.PMJoin(personal);
        return mav;
    }
    // PMLogin(get) : 로그인
    @GetMapping("/Login")
    public String Login(){

        return "login";
    }
    @GetMapping("/test02")
    public String test02(){

        return "test02";
    }
    // PMLogin (post) : 로그인
    @PostMapping("/PMLogin")
    public ModelAndView PMLogin(@ModelAttribute PersonalDTO person){
        System.out.println("personal:"+person);
        return psvc.PMLogin(person);
    }

    // Logout (get) : 로그아웃
    @GetMapping("/Logout")
    public String Logout(HttpSession session){

        session.invalidate();
        return "index";
    }

    // MyView(get) : 회원목록 페이지 요청
    @GetMapping ("/pmMyView")
    public ModelAndView pmMyView(@RequestParam("MId") String MId){

        return psvc.pmMyView(MId);
    }

    // pmModify(GET) : 회원수정 페이지 요청
    @GetMapping("/pmModify")
    public ModelAndView pmModify(@RequestParam("MId") String MId){
        mav = psvc.pmModify(MId);
        return mav;
    }

    // pmModify(POST) : 회원수정
    @PostMapping("/pmModify")
    public ModelAndView pmModify(@ModelAttribute PersonalDTO personal) throws IOException {
        mav = psvc.pmModify(personal);
        return mav;
    }

    // pmDelete(get) : 회원삭제
    @GetMapping("/pmDelete")
    public ModelAndView pmDelete(@RequestParam("MId") String MId) {

        mav = psvc.pmDelete(MId);

        return mav;
    }

    // 아이디찾기 0131, 오전 9시 추가
    @GetMapping("/idfind")
    public String idfind(){

        return "pmFindId";
    }

    @PostMapping ("/idfind")
    public ModelAndView idfind(@RequestParam("PMEmail") String PMEmail) throws IOException {
        System.out.println(PMEmail);
        mav=psvc.idfind(PMEmail);
        return mav;
    }

    // 비번변경 0131, 오후 3시 추가
    @GetMapping("/pmPwChangeForm")
    public String pmPwChangeForm(){
        return "pmPwChange";
    }

    @PostMapping("/pmPwChange")
    public ModelAndView pmPwChange(@RequestParam("newPw") String newPw,@ModelAttribute PersonalDTO personal ) throws IOException {
        mav = psvc.pmPwChange(personal, newPw);
        return mav;
    }

    // 비번찾기 0202, 오전 9시 추가
    @GetMapping("/pmFindPw")
    public String pmFindPw(){

        return "pmFindPw";
    }

    @PostMapping("/pmFindPw")
    public ModelAndView pmFindPw(@ModelAttribute PersonalDTO personal ) throws IOException {
        mav = psvc.pmFindPw(personal);
        return mav;
    }

    // 회원목록(관리자) 0202, 오전 10시 추가
    @GetMapping("/personList")
    public ModelAndView personList(){

        mav = psvc.personList();
        return mav;
    }

    @GetMapping("/chatMessage")
    public String chatmasage(){

        return "chatMessage";
    }

}

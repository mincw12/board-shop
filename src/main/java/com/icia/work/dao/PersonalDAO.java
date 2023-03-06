package com.icia.work.dao;

import com.icia.work.dto.PersonalDTO;
import com.icia.work.dto.suspendDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Mapper
public interface PersonalDAO {
    
    String idoverlap(String MId);

    int PMJoin(PersonalDTO personal);

    PersonalDTO PMView(String MId);

    int PMmodify(PersonalDTO personal);

    int pmDelete(String mId);

    String idfind(String pmEmail);

    PersonalDTO personal(String mId);

    void UpdatePw(PersonalDTO person);

    List<PersonalDTO> personList();

    int setSuspend(suspendDTO suspend);

    void deletesuspend(String mId);

    suspendDTO checkSuspend(String mId);

    List<suspendDTO> suspendList();
}

package com.icia.work.dao;

import com.icia.work.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainDAO {
    String idoverlap(String mId);

    int mJoin(MemberDTO member);

    MemberDTO MyView(String mId);

    int mDelete(String mId);

    int Modify(MemberDTO member);


    int setMPw(MemberDTO member);
}

package com.icia.work.dao;

import com.icia.work.dto.CompanyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyDAO {
    int CMJoin(CompanyDTO company);
    CompanyDTO CMView(String MId);

    int CMmodify(CompanyDTO company);

    int cmDelete(String mId);

    String cmidfind(String cmEmail);

    CompanyDTO company(String mId);

    void UpdatePw(CompanyDTO company);

    List<CompanyDTO> companyList();
}

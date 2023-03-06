package com.icia.work.dao;

import com.icia.work.dto.CityDTO;
import com.icia.work.dto.JOBDTO;
import com.icia.work.dto.PersonalDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobDAO {
    List<JOBDTO> JobList();

    int JobWrite(JOBDTO job);

    JOBDTO JobView(int JBNum);



    int JobDelete(int jbNum);

    int JobModify(JOBDTO job);

    PersonalDTO otherpmView(String jbId);
    void jbHitpl(int JBNum);

    List<JOBDTO> getJobList5();

    int closeJobPost(int JBNum);

    List<JOBDTO> findJList(CityDTO city);

    int jCount();
}


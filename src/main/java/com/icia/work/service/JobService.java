package com.icia.work.service;

import com.icia.work.dto.CityDTO;
import com.icia.work.dto.JOBDTO;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

public interface JobService {
    ModelAndView JobList();

    ModelAndView JobWrite(JOBDTO job) throws IOException;

    ModelAndView JobView(int JBNum);

    ModelAndView JobModify(int jbNum);

    ModelAndView JobModify(JOBDTO job) throws IOException;

    ModelAndView JobDelete(int jbNum);

    ModelAndView otherpmView(String jbId);

    void jbHitpl(int jbNum);

    List<JOBDTO> getJobList5();

    ModelAndView closeJobPost(int JBNum);


    ModelAndView countJob();

    List<JOBDTO> findJList(CityDTO city);
}



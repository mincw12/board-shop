package com.icia.work.dao;

import com.icia.work.dto.GuinCommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GCommentDAO {
    List<GuinCommentDTO> GuinCommentList(int gcbNum);

    int GuinCommentWrite(GuinCommentDTO gcomment);

    int GuinCommentDelete(GuinCommentDTO gcomment);
}

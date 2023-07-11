package com.jlkj.human.ex.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.human.ex.domain.ExamBankPicture;
import com.jlkj.human.ex.mapper.ExamBankPictureMapper;
import com.jlkj.human.ex.service.IExamBankPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 题库图片Service业务层处理
 * 
 * @author qnsdt
 * @date 2022-12-22
 */
@Service
public class ExamBankPictureServiceImpl implements IExamBankPictureService
{
    @Autowired
    private ExamBankPictureMapper examBankPictureMapper;

    /**
     * 查询题库图片
     * 
     * @param photoCode 题库图片ID
     * @return 题库图片
     */
    @Override
    public ExamBankPicture selectExamBankPictureById(String photoCode)
    {
        return examBankPictureMapper.selectExamBankPictureById(photoCode);
    }

    /**
     * 查询题库图片列表
     * 
     * @param examBankPicture 题库图片
     * @return 题库图片
     */
    @Override
    public List<ExamBankPicture> selectExamBankPictureList(ExamBankPicture examBankPicture)
    {
        return examBankPictureMapper.selectExamBankPictureList(examBankPicture);
    }

    /**
     * 新增题库图片
     * 
     * @param examBankPicture 题库图片
     * @return 结果
     */
    @Override
    public int insertExamBankPicture(ExamBankPicture examBankPicture)
    {
        examBankPicture.setCreateTime(DateUtils.getNowDate());
        return examBankPictureMapper.insertExamBankPicture(examBankPicture);
    }

    /**
     * 修改题库图片
     * 
     * @param examBankPicture 题库图片
     * @return 结果
     */
    @Override
    public int updateExamBankPicture(ExamBankPicture examBankPicture)
    {
        return examBankPictureMapper.updateExamBankPicture(examBankPicture);
    }

    /**
     * 批量删除题库图片
     * 
     * @param photoCodes 需要删除的题库图片ID
     * @return 结果
     */
    @Override
    public int deleteExamBankPictureByIds(String[] photoCodes)
    {
        return examBankPictureMapper.deleteExamBankPictureByIds(photoCodes);
    }

    /**
     * 删除题库图片信息
     * 
     * @param photoCode 题库图片ID
     * @return 结果
     */
    @Override
    public int deleteExamBankPictureById(String photoCode)
    {
        return examBankPictureMapper.deleteExamBankPictureById(photoCode);
    }
}

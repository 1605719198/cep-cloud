package com.jlkj.human.ex.mapper;


import com.jlkj.human.ex.domain.ExamBankPicture;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 题库图片Mapper接口
 * 
 * @author qnsdt
 * @date 2022-12-22
 */
@Repository
public interface ExamBankPictureMapper 
{
    /**
     * 查询题库图片
     * 
     * @param photoCode 题库图片ID
     * @return 题库图片
     */
    public ExamBankPicture selectExamBankPictureById(String photoCode);

    /**
     * 查询题库图片列表
     * 
     * @param examBankPicture 题库图片
     * @return 题库图片集合
     */
    public List<ExamBankPicture> selectExamBankPictureList(ExamBankPicture examBankPicture);

    /**
     * 新增题库图片
     * 
     * @param examBankPicture 题库图片
     * @return 结果
     */
    public int insertExamBankPicture(ExamBankPicture examBankPicture);

    /**
     * 修改题库图片
     * 
     * @param examBankPicture 题库图片
     * @return 结果
     */
    public int updateExamBankPicture(ExamBankPicture examBankPicture);

    /**
     * 删除题库图片
     * 
     * @param photoCode 题库图片ID
     * @return 结果
     */
    public int deleteExamBankPictureById(String photoCode);

    /**
     * 批量删除题库图片
     * 
     * @param photoCodes 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamBankPictureByIds(String[] photoCodes);
}

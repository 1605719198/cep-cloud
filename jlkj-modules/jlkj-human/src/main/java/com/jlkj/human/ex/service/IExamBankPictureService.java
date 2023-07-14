package com.jlkj.human.ex.service;


import com.jlkj.human.ex.domain.ExamBankPicture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 题库图片Service接口
 * 
 * @author qnsdt
 * @date 2022-12-22
 */
public interface IExamBankPictureService
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
     *  新增题库图片
     *
     * @Author: 111191
     * @Date: 2023年7月14日, 0014 上午 11:07:07
     * @param bankcode 题库代码
     * @param file 题库图片
     * @return int 结果笔数
     */
    int insertExamBankPicture(String bankcode, MultipartFile file);

    /**
     * 修改题库图片
     * 
     * @param examBankPicture 题库图片
     * @return 结果
     */
    public int updateExamBankPicture(ExamBankPicture examBankPicture);

    /**
     * 批量删除题库图片
     * 
     * @param photoCodes 需要删除的题库图片ID
     * @return 结果
     */
    public int deleteExamBankPictureByIds(String[] photoCodes);

    /**
     * 删除题库图片信息
     * 
     * @param photoCode 题库图片ID
     * @return 结果
     */
    public int deleteExamBankPictureById(String photoCode);
}

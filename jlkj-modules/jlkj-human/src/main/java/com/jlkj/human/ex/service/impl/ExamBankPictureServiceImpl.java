package com.jlkj.human.ex.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jlkj.common.core.domain.R;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.file.FileTypeUtils;
import com.jlkj.human.ex.domain.ExamBankPicture;
import com.jlkj.human.ex.mapper.ExamBankPictureMapper;
import com.jlkj.human.ex.service.IExamBankPictureService;
import com.jlkj.system.api.RemoteFileService;
import com.jlkj.system.api.domain.SysFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private RemoteFileService remoteFileService;

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
        return examBankPictureMapper.insertExamBankPicture(examBankPicture);
    }
    @Override
    public int insertExamBankPicture(String bankCode, MultipartFile file){
        // 取得文件扩展名
        String extension = FileTypeUtils.getExtension(file);
        // 取得原始文件名
        String originalFile = file.getOriginalFilename();

        R<SysFile> fileResult = remoteFileService.upload(file);
        if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData())) {
           new ServiceException("文件服务异常，请联系管理员");
        }
        ExamBankPicture examBankPicture = new ExamBankPicture();
        examBankPicture.setPhotoCode(bankCode);
        examBankPicture.setPhotoPath(fileResult.getData().getUrl());
        examBankPicture.setPhotoUrl(fileResult.getData().getUrl());
        examBankPicture.setFileName(fileResult.getData().getName());
        examBankPicture.setOriginalName(originalFile);

        ExamBankPicture bankPicture = examBankPictureMapper.selectExamBankPictureById(bankCode);
        if(!ObjectUtil.isEmpty(bankPicture)){
            examBankPictureMapper.deleteExamBankPictureById(bankCode);
        }

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

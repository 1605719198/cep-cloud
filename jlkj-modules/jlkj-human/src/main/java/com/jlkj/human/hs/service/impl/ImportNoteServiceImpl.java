package com.jlkj.human.hs.service.impl;

import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.ImportNote;
import com.jlkj.human.hs.mapper.ImportNoteMapper;
import com.jlkj.human.hs.service.IImportNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 薪资导入记录Service业务层处理
 *
 * @author 266861
 * @date 2023-06-05
 */
@Service
public class ImportNoteServiceImpl implements IImportNoteService
{
    @Autowired
    private ImportNoteMapper importNoteMapper;

    /**
     * 查询薪资导入记录
     *
     * @param id 薪资导入记录主键
     * @return 薪资导入记录
     */
    @Override
    public ImportNote selectImportNoteById(String id)
    {
        return importNoteMapper.selectImportNoteById(id);
    }

    /**
     * 查询薪资导入记录
     *
     * @param importNote 薪资导入记录
     * @return 薪资导入记录
     */
    @Override
    public ImportNote selectImportNote(ImportNote importNote)
    {
        return importNoteMapper.selectImportNote(importNote);
    }

    /**
     * 新增薪资导入记录
     *
     * @param importNote 薪资导入记录
     * @return 结果
     */
    @Override
    public int insertImportNote(ImportNote importNote)
    {
        //导入中
        String status1 = "1";
        try {
            importNote.setCreator(SecurityUtils.getNickName());
            importNote.setCreatorId(SecurityUtils.getUserId().toString());
            importNote.setCreatorNo(SecurityUtils.getUsername());
            importNote.setCreateDate(new Date());
            ImportNote oldNote = importNoteMapper.queryImportNote(importNote);
            if(status1.equals(oldNote.getStatus())){
                return -1;
            }else{
                importNoteMapper.deleteImportNoteById(oldNote.getId());
                return importNoteMapper.insertImportNote(importNote);
            }
        } catch (Exception e) {
            return importNoteMapper.insertImportNote(importNote);
        }

    }

    /**
     * 修改薪资导入记录
     *
     * @param importNote 薪资导入记录
     * @return 结果
     */
    @Override
    public int updateImportNote(ImportNote importNote)
    {
        importNote.setCreator(SecurityUtils.getNickName());
        importNote.setCreatorId(SecurityUtils.getUserId().toString());
        importNote.setCreatorNo(SecurityUtils.getUsername());
        importNote.setCreateDate(new Date());
        return importNoteMapper.updateImportNote(importNote);
    }

    /**
     * 批量删除薪资导入记录
     *
     * @param ids 需要删除的薪资导入记录主键
     * @return 结果
     */
    @Override
    public int deleteImportNoteByIds(String[] ids)
    {
        return importNoteMapper.deleteImportNoteByIds(ids);
    }

    /**
     * 删除薪资导入记录信息
     *
     * @param id 薪资导入记录主键
     * @return 结果
     */
    @Override
    public int deleteImportNoteById(String id)
    {
        return importNoteMapper.deleteImportNoteById(id);
    }
}

package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.ImportNote;

/**
 * 薪资导入记录Mapper接口
 * 
 * @author 266861
 * @date 2023-06-05
 */
public interface ImportNoteMapper 
{
    /**
     * 查询薪资导入记录
     * 
     * @param id 薪资导入记录主键
     * @return 薪资导入记录
     */
    public ImportNote selectImportNoteById(String id);

    /**
     * 查询薪资导入记录
     *
     * @param importNote 薪资导入记录
     * @return 薪资导入记录
     */
    public ImportNote queryImportNote(ImportNote importNote);

    /**
     * 查询薪资导入记录
     * 
     * @param importNote 薪资导入记录
     * @return 薪资导入记录集合
     */
    public ImportNote selectImportNote(ImportNote importNote);

    /**
     * 新增薪资导入记录
     * 
     * @param importNote 薪资导入记录
     * @return 结果
     */
    public int insertImportNote(ImportNote importNote);

    /**
     * 修改薪资导入记录
     * 
     * @param importNote 薪资导入记录
     * @return 结果
     */
    public int updateImportNote(ImportNote importNote);

    /**
     * 删除薪资导入记录
     * 
     * @param id 薪资导入记录主键
     * @return 结果
     */
    public int deleteImportNoteById(String id);

    /**
     * 批量删除薪资导入记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImportNoteByIds(String[] ids);
}

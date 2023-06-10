package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.ImportError;

import java.util.List;

/**
 * 薪资导入错误信息Service接口
 * 
 * @author 266861
 * @date 2023-06-05
 */
public interface IImportErrorService 
{
    /**
     * 查询薪资导入错误信息
     * 
     * @param id 薪资导入错误信息主键
     * @return 薪资导入错误信息
     */
    public ImportError selectImportErrorById(String id);

    /**
     * 查询薪资导入错误信息列表
     * 
     * @param importError 薪资导入错误信息
     * @return 薪资导入错误信息集合
     */
    public List<ImportError> selectImportErrorList(ImportError importError);

    /**
     * 新增薪资导入错误信息
     * 
     * @param importError 薪资导入错误信息
     * @return 结果
     */
    public int insertImportError(ImportError importError);

    /**
     * 修改薪资导入错误信息
     * 
     * @param importError 薪资导入错误信息
     * @return 结果
     */
    public int updateImportError(ImportError importError);

    /**
     * 批量删除薪资导入错误信息
     * 
     * @param ids 需要删除的薪资导入错误信息主键集合
     * @return 结果
     */
    public int deleteImportErrorByIds(String[] ids);

    /**
     * 删除薪资导入错误信息信息
     * 
     * @param id 薪资导入错误信息主键
     * @return 结果
     */

    public int deleteImportErrorById(String id);

    /**
     * 删除薪资导入错误信息信息
     *
     * @param importError 薪资导入错误信息
     * @return 结果
     */
    public int deleteImportError(ImportError importError);
}

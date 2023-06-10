package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.ImportError;
import com.jlkj.human.hs.mapper.ImportErrorMapper;
import com.jlkj.human.hs.service.IImportErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 薪资导入错误信息Service业务层处理
 *
 * @author 266861
 * @date 2023-06-05
 */
@Service
public class ImportErrorServiceImpl implements IImportErrorService {
    @Autowired
    private ImportErrorMapper importErrorMapper;

    /**
     * 查询薪资导入错误信息
     *
     * @param id 薪资导入错误信息主键
     * @return 薪资导入错误信息
     */
    @Override
    public ImportError selectImportErrorById(String id) {
        return importErrorMapper.selectImportErrorById(id);
    }

    /**
     * 查询薪资导入错误信息列表
     *
     * @param importError 薪资导入错误信息
     * @return 薪资导入错误信息
     */
    @Override
    public List<ImportError> selectImportErrorList(ImportError importError) {
        return importErrorMapper.selectImportErrorList(importError);
    }

    /**
     * 新增薪资导入错误信息
     *
     * @param importError 薪资导入错误信息
     * @return 结果
     */
    @Override
    public int insertImportError(ImportError importError) {
            importError.setId(UUID.randomUUID().toString().substring(0,32));
            importError.setCreator(SecurityUtils.getNickName());
            importError.setCreatorId(SecurityUtils.getUserId().toString());
            importError.setCreatorNo(SecurityUtils.getUsername());
            importError.setCreateDate(new Date());
            return importErrorMapper.insertImportError(importError);
    }

    /**
     * 修改薪资导入错误信息
     *
     * @param importError 薪资导入错误信息
     * @return 结果
     */
    @Override
    public int updateImportError(ImportError importError) {
        return importErrorMapper.updateImportError(importError);
    }

    /**
     * 批量删除薪资导入错误信息
     *
     * @param ids 需要删除的薪资导入错误信息主键
     * @return 结果
     */
    @Override
    public int deleteImportErrorByIds(String[] ids) {
        return importErrorMapper.deleteImportErrorByIds(ids);
    }

    /**
     * 删除薪资导入错误信息信息
     *
     * @param id 薪资导入错误信息主键
     * @return 结果
     */
    @Override
    public int deleteImportErrorById(String id) {
        return importErrorMapper.deleteImportErrorById(id);
    }

    /**
     * 删除薪资导入错误信息信息
     *
     * @param importError 薪资导入错误信息
     * @return 结果
     */
    @Override
    public int deleteImportError(ImportError importError) {
        return importErrorMapper.deleteImportError(importError);
    }
}

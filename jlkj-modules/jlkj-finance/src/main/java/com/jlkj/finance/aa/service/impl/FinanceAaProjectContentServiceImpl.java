package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAaProjectContent;
import com.jlkj.finance.aa.mapper.FinanceAaProjectContentMapper;
import com.jlkj.finance.aa.service.IFinanceAaProjectContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 集团管理-报表内容资料Service业务层处理
 *
 * @author 116514
 * @date 2023-05-16
 */
@Service
public class FinanceAaProjectContentServiceImpl implements IFinanceAaProjectContentService
{
    @Autowired
    private FinanceAaProjectContentMapper financeAaProjectContentMapper;

    /**
     * 查询集团管理-报表内容资料
     *
     * @param id 集团管理-报表内容资料主键
     * @return 集团管理-报表内容资料
     */
    @Override
    public FinanceAaProjectContent selectFinanceAaProjectContentById(String id)
    {
        return financeAaProjectContentMapper.selectFinanceAaProjectContentById(id);
    }

    /**
     * 查询集团管理-报表内容资料列表
     *
     * @param financeAaProjectContent 集团管理-报表内容资料
     * @return 集团管理-报表内容资料
     */
    @Override
    public List<FinanceAaProjectContent> selectFinanceAaProjectContentList(FinanceAaProjectContent financeAaProjectContent)
    {
        return financeAaProjectContentMapper.selectFinanceAaProjectContentList(financeAaProjectContent);
    }

    /**
     * 新增集团管理-报表内容资料
     *
     * @param financeAaProjectContent 集团管理-报表内容资料
     * @return 结果
     */
    @Override
    public int insertFinanceAaProjectContent(FinanceAaProjectContent financeAaProjectContent)
    {
        financeAaProjectContent.setCreateTime(DateUtils.getNowDate());
        return financeAaProjectContentMapper.insertFinanceAaProjectContent(financeAaProjectContent);
    }
    /**
     *  新增集团管理-报表内容资料（多笔）
     * @param dataList
     * @return
     */
    @Override
    public int insertFinanceAaProjectContentItem( List<FinanceAaProjectContent> dataList){
        int count = 0;
        for ( FinanceAaProjectContent financeProjectContent : dataList){
            if (financeProjectContent.getId()!=null){
                // 会计科目/印表项目不可重复
                FinanceAaProjectContent projectContent = financeAaProjectContentMapper.selectFinanceAaProjectContentById(financeProjectContent.getId());
                String dbKey = projectContent.getCompanyId()+projectContent.getReportNo() + projectContent.getItemCode() + projectContent.getCode();
                String inKey = financeProjectContent.getCompanyId()+financeProjectContent.getReportNo() + financeProjectContent.getItemCode() + financeProjectContent.getCode();
                if(!dbKey.equals(inKey)){
                    int num = financeAaProjectContentMapper.updateCheckProjectContent(financeProjectContent);
                    if(num>0){
                        throw new ServiceException("保存失败！会计科目/印表项目："+financeProjectContent.getCode()+"已经存在！");
                    }
                }
                financeProjectContent.setUpdateBy(SecurityUtils.getUsername());
                financeProjectContent.setUpdateName(SecurityUtils.getNickName());
                financeProjectContent.setUpdateTime(DateUtils.getNowDate());
                count =  financeAaProjectContentMapper.updateFinanceAaProjectContent(financeProjectContent);
            }else {
                int num = financeAaProjectContentMapper.updateCheckProjectContent(financeProjectContent);
                if(num>0){
                    throw new ServiceException("会计科目/印表项目："+financeProjectContent.getCode()+"已经存在，不可重复新增！");
                }
                financeProjectContent.setId(IdUtils.simpleUUID());
                financeProjectContent.setCreateBy(SecurityUtils.getUsername());
                financeProjectContent.setCreateName(SecurityUtils.getNickName());
                financeProjectContent.setCreateTime(DateUtils.getNowDate());
                count = financeAaProjectContentMapper.insertFinanceAaProjectContent(financeProjectContent);
            }
        }
        return count;
    }

    /**
     * 修改集团管理-报表内容资料
     *
     * @param financeAaProjectContent 集团管理-报表内容资料
     * @return 结果
     */
    @Override
    public int updateFinanceAaProjectContent(FinanceAaProjectContent financeAaProjectContent)
    {
        financeAaProjectContent.setUpdateBy(SecurityUtils.getUsername());
        financeAaProjectContent.setUpdateName(SecurityUtils.getNickName());
        financeAaProjectContent.setUpdateTime(DateUtils.getNowDate());
        return financeAaProjectContentMapper.updateFinanceAaProjectContent(financeAaProjectContent);
    }

    /**
     * 批量删除集团管理-报表内容资料
     *
     * @param ids 需要删除的集团管理-报表内容资料主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaProjectContentByIds(String[] ids)
    {
        return financeAaProjectContentMapper.deleteFinanceAaProjectContentByIds(ids);
    }

    /**
     * 删除集团管理-报表内容资料信息
     *
     * @param id 集团管理-报表内容资料主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaProjectContentById(String id)
    {
        return financeAaProjectContentMapper.deleteFinanceAaProjectContentById(id);
    }
}

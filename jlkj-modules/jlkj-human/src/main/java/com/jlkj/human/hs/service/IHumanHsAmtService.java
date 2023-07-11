package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.HumanHsAmt;
import com.jlkj.human.hs.domain.HumanHsAmtDetail;

import java.util.List;

/**
 * 薪资应付作业Service接口
 * 
 * @author jlkj
 * @date 2023-05-24
 */
public interface IHumanHsAmtService 
{
    /**
     * 查询薪资应付作业
     * 
     * @param id 薪资应付作业主键
     * @return 薪资应付作业
     */
    public HumanHsAmt selectHumanHsAmtById(String id);

    /**
     * 查询薪资应付作业列表
     * 
     * @param humanHsAmt 薪资应付作业
     * @return 薪资应付作业集合
     */
    public List<HumanHsAmt> selectHumanHsAmtList(HumanHsAmt humanHsAmt);

    /**
     * 新增薪资应付作业
     * 
     * @param humanHsAmt 薪资应付作业
     * @return 结果
     */
    public int insertHumanHsAmt(HumanHsAmt humanHsAmt);

    /**
     * 修改薪资应付作业
     * 
     * @param humanHsAmt 薪资应付作业
     * @return 结果
     */
    public int updateHumanHsAmt(HumanHsAmt humanHsAmt);

    /**
     * 批量删除薪资应付作业
     * 
     * @param ids 需要删除的薪资应付作业主键集合
     * @return 结果
     */
    public int deleteHumanHsAmtByIds(String[] ids);

    /**
     * 删除薪资应付作业信息
     * 
     * @param id 薪资应付作业主键
     * @return 结果
     */
    public int deleteHumanHsAmtById(String id);

    /**
     * 查询薪资应付作业列表
     *
     * @param humanHsAmt 薪资应付作业
     * @return 薪资应付作业集合
     */
    public List<HumanHsAmtDetail> getDetailAmtList(HumanHsAmt humanHsAmt);
    /**
     * 批量删除薪资应付作业
     *
     * @param ids 需要删除的薪资应付作业主键集合
     * @return 结果
     */
    public int sendAA(String[] ids);
}

package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.HumanHsAmt;
import com.jlkj.human.hs.domain.HumanHsAmtDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 薪资应付作业Mapper接口
 * 
 * @author jlkj
 * @date 2023-05-24
 */
public interface HumanHsAmtMapper 
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
     * 删除薪资应付作业
     * 
     * @param id 薪资应付作业主键
     * @return 结果
     */
    public int deleteHumanHsAmtById(String id);




    /**
     * 批量删除薪资应付作业
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHumanHsAmtByIds(String[] ids);

    /**
     * 批量删除薪资应付明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHumanHsAmtDetailByAmtIds(String[] ids);
    
    /**
     * 批量新增薪资应付明细
     * 
     * @param humanHsAmtDetailList 薪资应付明细列表
     * @return 结果
     */
    public int batchHumanHsAmtDetail(List<HumanHsAmtDetail> humanHsAmtDetailList);
    

    /**
     * 通过薪资应付作业主键删除薪资应付明细信息
     * 
     * @param id 薪资应付作业ID
     * @return 结果
     */
    public int deleteHumanHsAmtDetailByAmtId(String id);

    /**
     * 通过id查询附件清单
     * @param ids
     * @return
     */
    public List<Map<String,String>> selectFileList(String[] ids);

    /**
     * 根据公司别、年月和作业时点验证数据是否重复
     * @param compId
     * @param yyMonth
     * @param operTime
     * @return
     */
    public int updateCheckUnique(@Param("compId") String compId,@Param("yyMonth") String yyMonth,@Param("operTime") String operTime);

    /**
     * 根据年月查询薪资明细汇总 月初
     * @param year
     * @param month
     * @return
     */
    public List<HumanHsAmtDetail>  selectDetailAmtList01(@Param("year") String year,@Param("month") String month);

    /**
     * 根据年月查询薪资明细汇总 月终
     * @param year
     * @param month
     * @return
     */
    public List<HumanHsAmtDetail>  selectDetailAmtList02(@Param("year") String year,@Param("month") String month);
    /**
     * 根据付款申请主档id 查询明细档金额总额
     * @param parentId
     * @return
     */
    public Map selectSumAmt(@Param("parentId") String parentId);
}

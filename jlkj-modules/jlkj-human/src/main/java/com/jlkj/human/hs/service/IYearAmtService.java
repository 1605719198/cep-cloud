package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.YearAmt;

import java.util.List;

/**
 * 年收入维护Service接口
 * 
 * @author 266861
 * @date 2023-05-17
 */
public interface IYearAmtService 
{
    /**
     * 查询年收入维护
     * 
     * @param id 年收入维护主键
     * @return 年收入维护
     */
    public YearAmt selectYearAmtById(String id);

    /**
     * 查询年收入维护列表
     * 
     * @param yearAmt 年收入维护
     * @return 年收入维护集合
     */
    public List<YearAmt> selectYearAmtList(YearAmt yearAmt);

    /**
     * 新增年收入维护
     * 
     * @param yearAmtList 年收入维护
     * @return 结果
     */
    public int insertYearAmt(List<YearAmt> yearAmtList);

    /**
     * 修改年收入维护
     * 
     * @param yearAmt 年收入维护
     * @return 结果
     */
    public int updateYearAmt(YearAmt yearAmt);

    /**
     * 批量删除年收入维护
     * 
     * @param ids 需要删除的年收入维护主键集合
     * @return 结果
     */
    public int deleteYearAmtByIds(String[] ids);

    /**
     * 删除年收入维护信息
     * 
     * @param id 年收入维护主键
     * @return 结果
     */
    public int deleteYearAmtById(String id);

    /**
     * 导入年收入资料数据
     *
     * @param yearAmtList 年收入资料列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importYearAmt(List<YearAmt> yearAmtList, Boolean isUpdateSupport, String operName);
}

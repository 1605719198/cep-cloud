package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.YearAmt;

import java.util.List;

/**
 * 年收入维护Mapper接口
 * 
 * @author 266861
 * @date 2023-05-17
 */
public interface YearAmtMapper 
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
     * @param yearAmt 年收入维护
     * @return 结果
     */
    public int insertYearAmt(YearAmt yearAmt);

    /**
     * 修改年收入维护
     * 
     * @param yearAmt 年收入维护
     * @return 结果
     */
    public int updateYearAmt(YearAmt yearAmt);

    /**
     * 删除年收入维护
     * 
     * @param id 年收入维护主键
     * @return 结果
     */
    public int deleteYearAmtById(String id);

    /**
     * 批量删除年收入维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteYearAmtByIds(String[] ids);
}

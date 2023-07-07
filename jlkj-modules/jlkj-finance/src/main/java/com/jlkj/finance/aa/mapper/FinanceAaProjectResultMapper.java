package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaProjectResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 印项目计算结果Mapper接口
 * 
 * @author jlkj
 * @date 2023-06-28
 */
@Repository
public interface FinanceAaProjectResultMapper 
{
    /**
     * 查询印项目计算结果
     * 
     * @param id 印项目计算结果主键
     * @return 印项目计算结果
     */
    public FinanceAaProjectResult selectFinanceAaProjectResultById(Long id);

    /**
     * 查询印项目计算结果列表
     * 
     * @param financeAaProjectResult 印项目计算结果
     * @return 印项目计算结果集合
     */
    public List<FinanceAaProjectResult> selectFinanceAaProjectResultList(FinanceAaProjectResult financeAaProjectResult);

    /**
     * 新增印项目计算结果
     * 
     * @param financeAaProjectResult 印项目计算结果
     * @return 结果
     */
    public int insertFinanceAaProjectResult(FinanceAaProjectResult financeAaProjectResult);

    /**
     * 修改印项目计算结果
     * 
     * @param financeAaProjectResult 印项目计算结果
     * @return 结果
     */
    public int updateFinanceAaProjectResult(FinanceAaProjectResult financeAaProjectResult);

    /**
     * 批量删除
    * @description 批量删除
    * @param id id
    * @return int
    * @Author 114288
    * @Date 2023/7/6 16:30
    **/
    public int deleteFinanceAaProjectResultById(Long id);

    /**
     * 批量删除印项目计算结果
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaProjectResultByIds(Long[] ids);

    /**
     * 按会计周期和报表代号删除计算公式值
    * @description 按会计周期和报表代号删除计算公式值
    * @param companyId 公司别
    * @param acctPeriod 会计周期
    * @param reportNo 报表代号
    * @return int 笔数
    * @Author 114288
    * @Date 2023/7/6 16:17
    **/
    int deleteFinanceAaProjectResultByPeriod(@Param("companyId")String companyId, @Param("acctPeriod")String acctPeriod, @Param("reportNo")String reportNo);
}

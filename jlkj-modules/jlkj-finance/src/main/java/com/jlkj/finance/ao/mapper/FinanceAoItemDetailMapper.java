package com.jlkj.finance.ao.mapper;

import com.jlkj.finance.ao.domain.FinanceAoItemDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报支管理-报支类别集团设定明细档Mapper接口
 * 
 * @author 116514
 * @date 2023-05-21
 */
@Repository
public interface FinanceAoItemDetailMapper 
{
    /**
     * 查询报支管理-报支类别集团设定明细档
     * 
     * @param id 报支管理-报支类别集团设定明细档主键
     * @return 报支管理-报支类别集团设定明细档
     */
    public FinanceAoItemDetail selectFinanceAoItemDetailById(String id);

    /**
     * 查询报支管理-报支类别集团设定明细档列表
     * 
     * @param financeAoItemDetail 报支管理-报支类别集团设定明细档
     * @return 报支管理-报支类别集团设定明细档集合
     */
    public List<FinanceAoItemDetail> selectFinanceAoItemDetailList(FinanceAoItemDetail financeAoItemDetail);

    /**
     * 新增报支管理-报支类别集团设定明细档
     * 
     * @param financeAoItemDetail 报支管理-报支类别集团设定明细档
     * @return 结果
     */
    public int insertFinanceAoItemDetail(FinanceAoItemDetail financeAoItemDetail);

    /**
     * 修改报支管理-报支类别集团设定明细档
     * 
     * @param financeAoItemDetail 报支管理-报支类别集团设定明细档
     * @return 结果
     */
    public int updateFinanceAoItemDetail(FinanceAoItemDetail financeAoItemDetail);

    /**
     * 删除报支管理-报支类别集团设定明细档
     * 
     * @param id 报支管理-报支类别集团设定明细档主键
     * @return 结果
     */
    public int deleteFinanceAoItemDetailById(String id);

    /**
     * 批量删除报支管理-报支类别集团设定明细档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoItemDetailByIds(String[] ids);


    /**
     * 通过itemNo查询明细档资料
     * @param itemNos
     * @return
     */
    public List<FinanceAoItemDetail> selectItemDetailsByNos(String[] itemNos);


    /**
     * 检核数据唯一性
     * @param itemNo
     * @param detailNo
     * @return
     */
    public int updateCheckUnique(@Param("itemNo") String itemNo, @Param("detailNo")String detailNo);
}

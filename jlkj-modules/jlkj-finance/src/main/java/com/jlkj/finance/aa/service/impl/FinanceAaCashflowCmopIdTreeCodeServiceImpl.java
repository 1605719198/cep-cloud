package com.jlkj.finance.aa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.finance.aa.domain.FinanceAaCashflowCodeCompid;
import com.jlkj.finance.aa.domain.FinanceAaCashflowCodeCompid;
import com.jlkj.finance.aa.dto.FinanceAaCashflowCodeDTO;
import com.jlkj.finance.aa.mapper.FinanceAaCashflowCmopIdTreeCodeMapper;
import com.jlkj.finance.aa.mapper.FinanceAaCashflowCodeTreeMapper;
import com.jlkj.finance.aa.service.IFinanceAaCashflowCmopIdTreeCodeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 现金流量代码Service业务层处理
 *
 * @author 265799
 * @date 2023-04-17
 */
@Service
public class FinanceAaCashflowCmopIdTreeCodeServiceImpl
        extends ServiceImpl<FinanceAaCashflowCmopIdTreeCodeMapper, 
        FinanceAaCashflowCodeCompid>
        implements IFinanceAaCashflowCmopIdTreeCodeService {
    /**
     * 选单配置树结构列表
     * @return
     */
    @Override
    public List<FinanceAaCashflowCodeDTO> getBaseInfoTreeCompanyIdList(String companyId) {
        List<FinanceAaCashflowCodeDTO> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();

        //查询选单配置资料
        List<FinanceAaCashflowCodeCompid> equipmentDownTypeTrees =
                baseMapper.selectList(new QueryWrapper<FinanceAaCashflowCodeCompid>().eq("company_id",companyId).orderByAsc("cash_flow_code").orderByAsc("cash_flow_name"));
        //树结构DTO
        List<FinanceAaCashflowCodeDTO> treeDTOList = new ArrayList<>();
        //循环取出id值，存入tempList
        if (StringUtils.isNotNull(equipmentDownTypeTrees)) {
            for (FinanceAaCashflowCodeCompid financeAaCashflowCodeCompid : equipmentDownTypeTrees) {
                FinanceAaCashflowCodeDTO financeAaCashflowCodeDTO = new FinanceAaCashflowCodeDTO();
                financeAaCashflowCodeDTO.setId(financeAaCashflowCodeCompid.getId());
                financeAaCashflowCodeDTO.setCompanyId(financeAaCashflowCodeCompid.getCompanyId());
                financeAaCashflowCodeDTO.setCashFlowCode(financeAaCashflowCodeCompid.getCashFlowCode());
                financeAaCashflowCodeDTO.setCashFlowName(financeAaCashflowCodeCompid.getCashFlowName());
                financeAaCashflowCodeDTO.setParentId(financeAaCashflowCodeCompid.getParentId());
                tempList.add(financeAaCashflowCodeCompid.getId());
                treeDTOList.add(financeAaCashflowCodeDTO);
            }
        }
        for (FinanceAaCashflowCodeDTO typeTree : treeDTOList) {
            //如果是Root节点，遍历该父节点的所有子节点
            if (!tempList.contains(typeTree.getParentId())) {
                //进行递归操作
                recursionFn(treeDTOList, typeTree);
                returnList.add(typeTree);
            }
        }

        if (returnList.isEmpty()) {
            returnList = treeDTOList;
        }
        return returnList.stream().map(FinanceAaCashflowCodeDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 递归列表
     * @param list
     * @param financeAaCashflowCodeDTO
     */
    private void recursionFn(List<FinanceAaCashflowCodeDTO> list, FinanceAaCashflowCodeDTO financeAaCashflowCodeDTO) {
        //得到子节点列表
        List<FinanceAaCashflowCodeDTO> childList = getChildList(list,financeAaCashflowCodeDTO);
        financeAaCashflowCodeDTO.setChildren(childList);
        for (FinanceAaCashflowCodeDTO orgChild : childList) {
            if (hasChild(list, orgChild)) {
                recursionFn(list, orgChild);
            }
        }
    }

    /**
     * 得到子节点列表
     * @param list
     * @param baseInfo
     * @return
     */
    private List<FinanceAaCashflowCodeDTO> getChildList(List<FinanceAaCashflowCodeDTO> list, FinanceAaCashflowCodeDTO baseInfo) {
        List<FinanceAaCashflowCodeDTO> orgList = new ArrayList<>();
        //声明迭代器

        for (FinanceAaCashflowCodeDTO humanOrg : list) {
            //判断当前迭代对象的夫ID是否等于传入的ID
            if (StringUtils.isNotNull(humanOrg.getParentId()) && humanOrg.getParentId().equals(baseInfo.getId())) {
                orgList.add(humanOrg);
            }

        }
        return orgList;
    }

    /**
     * 判断是否有子节点
     * @param list
     * @param baseInfo
     * @return
     */
    private boolean hasChild(List<FinanceAaCashflowCodeDTO> list, FinanceAaCashflowCodeDTO baseInfo) {
        return getChildList(list,baseInfo).size() > 0;
    }

}

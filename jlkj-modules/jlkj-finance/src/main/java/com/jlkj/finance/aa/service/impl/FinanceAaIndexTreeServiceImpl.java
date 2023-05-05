package com.jlkj.finance.aa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.finance.aa.domain.FinanceAaIndex;



import com.jlkj.finance.aa.dto.FinanceAaIndexDTO;

import com.jlkj.finance.aa.mapper.FinanceAaIndexTreeMapper;

import com.jlkj.finance.aa.service.IFinanceAaIndexTreeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统选单-索引设定Service业务层处理
 *
 * @author 265799
 * @date 2023-04-17
 */
@Service
public class FinanceAaIndexTreeServiceImpl extends ServiceImpl<FinanceAaIndexTreeMapper, FinanceAaIndex>
        implements IFinanceAaIndexTreeService {

    @Override
    public List<FinanceAaIndexDTO> getBaseInfoTreeList( String companyId) {
        List<FinanceAaIndexDTO> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();

        //查询选单配置资料
        List<FinanceAaIndex> equipmentDownTypeTrees =
                baseMapper.selectList(new QueryWrapper<FinanceAaIndex>().eq("company_id",companyId).orderByDesc("node_no").orderByAsc("node_name"));
        //树结构DTO
        List<FinanceAaIndexDTO> treeDTOList = new ArrayList<>();
        //循环取出id值，存入tempList
        if (StringUtils.isNotNull(equipmentDownTypeTrees)) {
            for (FinanceAaIndex financeAaIndex : equipmentDownTypeTrees) {
                FinanceAaIndexDTO financeAaIndexDTO = new FinanceAaIndexDTO();
                financeAaIndexDTO.setId(financeAaIndex.getId());
                financeAaIndexDTO.setNodeNo(financeAaIndex.getNodeNo());
                financeAaIndexDTO.setCompanyId(financeAaIndex.getCompanyId());
                financeAaIndexDTO.setParentNo(financeAaIndex.getParentNo());
                financeAaIndexDTO.setNodeName(financeAaIndex.getNodeName());
                financeAaIndexDTO.setParentId(financeAaIndex.getParentId());
                tempList.add(financeAaIndex.getId());
                treeDTOList.add(financeAaIndexDTO);
            }
        }
        for (FinanceAaIndexDTO typeTree : treeDTOList) {
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
        return returnList.stream().map(FinanceAaIndexDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 递归列表
     * @param list
     * @param FinanceAaIndexDTO
     */
    private void recursionFn(List<FinanceAaIndexDTO> list, FinanceAaIndexDTO FinanceAaIndexDTO) {
        //得到子节点列表
        List<FinanceAaIndexDTO> childList = getChildList(list,FinanceAaIndexDTO);
        FinanceAaIndexDTO.setChildren(childList);
        for (FinanceAaIndexDTO orgChild : childList) {
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
    private List<FinanceAaIndexDTO> getChildList(List<FinanceAaIndexDTO> list, FinanceAaIndexDTO baseInfo) {
        List<FinanceAaIndexDTO> orgList = new ArrayList<>();
        //声明迭代器

        for (FinanceAaIndexDTO humanOrg : list) {
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
    private boolean hasChild(List<FinanceAaIndexDTO> list, FinanceAaIndexDTO baseInfo) {
        return getChildList(list,baseInfo).size() > 0;
    }

}

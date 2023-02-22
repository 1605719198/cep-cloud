package com.jlkj.finance.ac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.finance.ac.dto.FinanceCostDownTypeTreeDTO;
import com.jlkj.finance.ac.domain.FinanceCostCode;
import com.jlkj.finance.ac.domain.FinanceCostTree;

import com.jlkj.finance.ac.service.FinanceCostCodeService;
import com.jlkj.finance.ac.service.FinanceCostTreeService;
import com.jlkj.finance.ac.mapper.FinanceCostTreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author wang'mai
* @description 针对表【t_finance_cost_tree(成本科目阶层)】的数据库操作Service实现
*/
@Service
public class FinanceCostTreeServiceImpl extends ServiceImpl<FinanceCostTreeMapper, FinanceCostTree>
    implements FinanceCostTreeService{
    @Autowired
    private FinanceCostCodeService financeCostCodeService;

    /**
     * 成本科目树结构
     * @return  public List<FinanceCostDownTypeTreeDTO> getEquipmentTypeTreeList()
     */
    @Override
    public List<FinanceCostDownTypeTreeDTO> getEquipmentTypeTreeList() {
        List<FinanceCostDownTypeTreeDTO> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();

        //查询出所有成本科目树
        List<FinanceCostTree> equipmentDownTypeTrees = baseMapper.selectList(new QueryWrapper<FinanceCostTree>().orderByAsc("level_code").orderByAsc("level_name"));
        //成本科目树结构DTO
        List<FinanceCostDownTypeTreeDTO> treeDTOList = new ArrayList<>();
        //循环取出父节点编码
        if (StringUtils.isNotNull(equipmentDownTypeTrees)) {
            for (FinanceCostTree item : equipmentDownTypeTrees) {
                FinanceCostDownTypeTreeDTO treeDTO = new FinanceCostDownTypeTreeDTO();
                treeDTO.setId(item.getId());
                treeDTO.setLevelName(item.getLevelName());
                treeDTO.setLevelCode(item.getLevelCode());
                treeDTO.setNodeType(item.getNodeType());
                treeDTO.setParentId(item.getParentId());
                treeDTO.setCostCodeLevelCode(item.getCostCodeLevelCode());
                treeDTOList.add(treeDTO);
                tempList.add(item.getId());
            }
        }
        for (FinanceCostDownTypeTreeDTO typeTree : treeDTOList) {
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
        return returnList.stream().map(FinanceCostDownTypeTreeDTO::new).collect(Collectors.toList());
    }
    /**
     * 成本科目树结构
     * @return  public List<FinanceCostDownTypeTreeDTO> getEquipmentTypeAllTreeList()
     */
    @Override
    public List<FinanceCostDownTypeTreeDTO> getEquipmentTypeAllTreeList() {
        List<FinanceCostDownTypeTreeDTO> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();

        //查询出所有成本科目树
        List<FinanceCostTree> equipmentDownTypeTrees = baseMapper.selectList(new QueryWrapper<FinanceCostTree>().orderByAsc("level_code").orderByAsc("level_name"));
        //成本科目树结构DTO
        List<FinanceCostDownTypeTreeDTO> treeDTOList = new ArrayList<>();
        //循环取出父节点编码
        if (StringUtils.isNotNull(equipmentDownTypeTrees)) {
            for (FinanceCostTree item : equipmentDownTypeTrees) {
                FinanceCostDownTypeTreeDTO treeDTO = new FinanceCostDownTypeTreeDTO();
                treeDTO.setId(item.getId());
                treeDTO.setLevelName(item.getLevelName());
                treeDTO.setLevelCode(item.getLevelCode());
                treeDTO.setNodeType(item.getNodeType());
                treeDTO.setParentId(item.getParentId());
                treeDTO.setCostCodeLevelCode(item.getCostCodeLevelCode());
                String costCodeLevelCode = treeDTO.getCostCodeLevelCode();
                if  (StringUtils.isNotBlank(costCodeLevelCode)&& "DOC".equals(item.getNodeType())){
                    LambdaQueryWrapper<FinanceCostCode> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.likeRight(StringUtils.isNotBlank(costCodeLevelCode), FinanceCostCode::getCostCode, costCodeLevelCode);
                    List<FinanceCostCode> list = financeCostCodeService.list(queryWrapper);
                    if (list.size()!=0){
                        for ( FinanceCostCode financeCostCode :list){
                            FinanceCostDownTypeTreeDTO dto = new FinanceCostDownTypeTreeDTO();
                            dto.setId(financeCostCode.getId());
                            dto.setParentId(item.getId());
                            dto.setLevelName(financeCostCode.getCostDesc());
                            dto.setAccountCode(financeCostCode.getAccountCode());
                            dto.setCostDesc(financeCostCode.getCostDesc());
                            dto.setCostCode(financeCostCode.getCostCode());

                            treeDTOList.add(dto);
                            tempList.add(dto.getId());
                        }
                    }
                    tempList.add(item.getId());
                }
                treeDTOList.add(treeDTO);
                tempList.add(item.getId());
            }
        }
        for (FinanceCostDownTypeTreeDTO typeTree : treeDTOList) {
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
        return returnList.stream().map(FinanceCostDownTypeTreeDTO::new).collect(Collectors.toList());
    }
    /**
     * 递归列表
     * @param list 成本科目阶层DTO数组
     * @param typeTree 成本科目阶层DTO
     */
    private void recursionFn(List<FinanceCostDownTypeTreeDTO> list, FinanceCostDownTypeTreeDTO typeTree) {
        //得到子节点列表
        List<FinanceCostDownTypeTreeDTO> childList = getChildList(list,typeTree);
        typeTree.setChildren(childList);
        for (FinanceCostDownTypeTreeDTO orgChild : childList) {
            if (hasChild(list, orgChild)) {
                recursionFn(list, orgChild);
            }
        }
    }
    /**
     * 得到子节点列表
     * @param list 成本科目阶层DTO数组
     * @param typeTree 成本科目阶层DTO
     * @return  private List<FinanceCostDownTypeTreeDTO> getChildList(List<FinanceCostDownTypeTreeDTO> list, FinanceCostDownTypeTreeDTO typeTree)
     */
    private List<FinanceCostDownTypeTreeDTO> getChildList(List<FinanceCostDownTypeTreeDTO> list, FinanceCostDownTypeTreeDTO typeTree) {
        List<FinanceCostDownTypeTreeDTO> orgList = new ArrayList<>();
        //声明迭代器
        for (FinanceCostDownTypeTreeDTO humanOrg : list) {
            //判断当前迭代对象的夫ID是否等于传入的ID
            if (StringUtils.isNotNull(humanOrg.getParentId()) && humanOrg.getParentId().equals(typeTree.getId())) {
                orgList.add(humanOrg);
            }
        }
        return orgList;
    }

    /**
     * 判断是否有子节点
     * @param list 成本科目阶层DTO数组
     * @param typeTree 成本科目阶层DTO
     * @return  private boolean hasChild(List<FinanceCostDownTypeTreeDTO> list, FinanceCostDownTypeTreeDTO typeTree)
     */
    private boolean hasChild(List<FinanceCostDownTypeTreeDTO> list, FinanceCostDownTypeTreeDTO typeTree) {
        return getChildList(list,typeTree).size() > 0;
    }

}





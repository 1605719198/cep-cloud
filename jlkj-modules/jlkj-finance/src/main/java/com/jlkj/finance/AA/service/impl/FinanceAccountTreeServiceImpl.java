package com.jlkj.finance.AA.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.finance.AA.domain.FinanceAccountCode;
import com.jlkj.finance.AA.domain.FinanceAccountTree;
import com.jlkj.finance.AA.dto.FinanceAccountingTreeDTO;
import com.jlkj.finance.AA.mapper.FinanceAccountTreeMapper;
import com.jlkj.finance.AA.service.FinanceAccountCodeService;
import com.jlkj.finance.AA.service.FinanceAccountTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author wang'mai
* @description 针对表【t_finance_account_tree(会计科目树状资料)】的数据库操作Service实现
*/
@Service
public class FinanceAccountTreeServiceImpl extends ServiceImpl<FinanceAccountTreeMapper, FinanceAccountTree>
    implements FinanceAccountTreeService {
    @Autowired
    private FinanceAccountCodeService financeAccountCodeService;
    /**
     * 会计科目树结构
     * @return public List<FinanceAccountingTreeDTO> getEquipmentTypeTreeList()
     */
    @Override
    public List<FinanceAccountingTreeDTO> getEquipmentTypeTreeList() {
        List<FinanceAccountingTreeDTO> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();

        //查询出所有会计科目名称
        List<FinanceAccountTree> equipmentDownTypeTrees = baseMapper.selectList(new QueryWrapper<FinanceAccountTree>().orderByAsc("account_code").orderByAsc("account_name"));
        //会计科目树DTO
        List<FinanceAccountingTreeDTO> treeDTOList = new ArrayList<>();
        //循环取出父节点编码
        if (StringUtils.isNotNull(equipmentDownTypeTrees)) {
            for (FinanceAccountTree item : equipmentDownTypeTrees) {
                FinanceAccountingTreeDTO treeDTO = new FinanceAccountingTreeDTO();
                String accountCode1 = item.getAccountCode();
                if ( StringUtils.isEmpty(accountCode1)||accountCode1.length()<=4){
                    treeDTO.setId(item.getId());
                    treeDTO.setAccountName(item.getAccountName());
                    treeDTO.setParentId(item.getParentId());
                    treeDTO.setParentAccountCode(item.getParentAccountCode());
                    treeDTO.setFolderName(item.getFolderName());
                    treeDTO.setIsFolder(item.getIsFolder());
                    treeDTO.setAccountCode(item.getAccountCode());
                    String accountCode = treeDTO.getAccountCode();
                    if (StringUtils.isNoneBlank(accountCode)) {
                        LambdaQueryWrapper<FinanceAccountCode> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(StringUtils.isNotBlank(accountCode), FinanceAccountCode::getAccountCode, accountCode);
                        FinanceAccountCode financeAccountCode = financeAccountCodeService.getOne(queryWrapper);
                        if (financeAccountCode !=null){
                            treeDTO.setIdTypeStus(financeAccountCode.getIdTypeStus());
                            treeDTO.setRefTypeStus(financeAccountCode.getRefTypeStus());
                            treeDTO.setAccountName(financeAccountCode.getAccountCode()+"_"+financeAccountCode.getAccountChineseName());
                        }
                    }
                    treeDTOList.add(treeDTO);
                    tempList.add(item.getId());
                }
                }

        }
        for (FinanceAccountingTreeDTO typeTree : treeDTOList) {
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
        return returnList.stream().map(FinanceAccountingTreeDTO::new).collect(Collectors.toList());

    }
    /**
     * 会计科目树结构
     * @return public List<FinanceAccountingTreeDTO> getEquipmentTypeAllTreeList()
     */
    @Override
    public List<FinanceAccountingTreeDTO> getEquipmentTypeAllTreeList() {
        List<FinanceAccountingTreeDTO> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();

        //查询出所有会计科目名称
        List<FinanceAccountTree> equipmentDownTypeTrees = baseMapper.selectList(new QueryWrapper<FinanceAccountTree>().orderByAsc("account_code").orderByAsc("account_name"));
        //会计科目树DTO
        List<FinanceAccountingTreeDTO> treeDTOList = new ArrayList<>();
        //循环取出父节点编码
        if (StringUtils.isNotNull(equipmentDownTypeTrees)) {
            for (FinanceAccountTree item : equipmentDownTypeTrees) {
                FinanceAccountingTreeDTO treeDTO = new FinanceAccountingTreeDTO();
                    treeDTO.setId(item.getId());
                    treeDTO.setAccountName(item.getAccountName());
                    treeDTO.setParentId(item.getParentId());
                    treeDTO.setParentAccountCode(item.getParentAccountCode());
                    treeDTO.setFolderName(item.getFolderName());
                    treeDTO.setIsFolder(item.getIsFolder());
                    treeDTO.setAccountCode(item.getAccountCode());
                    String accountCode = treeDTO.getAccountCode();
                    if (StringUtils.isNoneBlank(accountCode)) {
                        LambdaQueryWrapper<FinanceAccountCode> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(StringUtils.isNotBlank(accountCode), FinanceAccountCode::getAccountCode, accountCode);
                        FinanceAccountCode financeAccountCode = financeAccountCodeService.getOne(queryWrapper);
                        if (financeAccountCode !=null){
                            treeDTO.setIdTypeStus(financeAccountCode.getIdTypeStus());
                            treeDTO.setRefTypeStus(financeAccountCode.getRefTypeStus());
                            treeDTO.setAccountName(financeAccountCode.getAccountCode()+"_"+financeAccountCode.getAccountChineseName());
                        }
                    }
                    treeDTOList.add(treeDTO);
                    tempList.add(item.getId());

            }

        }
        for (FinanceAccountingTreeDTO typeTree : treeDTOList) {
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
        return returnList.stream().map(FinanceAccountingTreeDTO::new).collect(Collectors.toList());

    }
    /**
     * 递归列表
     * @param list 会计科目树DTO数组
     * @param typeTree 会计科目树DTO
     */
    private void recursionFn(List<FinanceAccountingTreeDTO> list, FinanceAccountingTreeDTO typeTree) {
        //得到子节点列表
        List<FinanceAccountingTreeDTO> childList = getChildList(list,typeTree);
        typeTree.setChildren(childList);
        for (FinanceAccountingTreeDTO orgChild : childList) {
            if (hasChild(list, orgChild)) {
                recursionFn(list, orgChild);
            }
        }
    }
    /**
     * 得到子节点列表
     * @param list 会计科目树DTO数组
     * @param typeTree 会计科目树DTO
     * @return private List<FinanceAccountingTreeDTO> getChildList(List<FinanceAccountingTreeDTO> list, FinanceAccountingTreeDTO typeTree)
     */
    private List<FinanceAccountingTreeDTO> getChildList(List<FinanceAccountingTreeDTO> list, FinanceAccountingTreeDTO typeTree) {
        List<FinanceAccountingTreeDTO> orgList = new ArrayList<>();
        //声明迭代器
        for (FinanceAccountingTreeDTO humanOrg : list) {
            //判断当前迭代对象的夫ID是否等于传入的ID
            if (StringUtils.isNotNull(humanOrg.getParentId()) && humanOrg.getParentId().equals(typeTree.getId())) {
                orgList.add(humanOrg);
            }
        }
        return orgList;
    }

    /**
     * 判断是否有子节点
     * @param list 会计科目树DTO数组
     * @param typeTree 会计科目树DTO
     * @return  private boolean hasChild(List<FinanceAccountingTreeDTO> list, FinanceAccountingTreeDTO typeTree)
     */
    private boolean hasChild(List<FinanceAccountingTreeDTO> list, FinanceAccountingTreeDTO typeTree) {
        return getChildList(list,typeTree).size() > 0;
    }

}





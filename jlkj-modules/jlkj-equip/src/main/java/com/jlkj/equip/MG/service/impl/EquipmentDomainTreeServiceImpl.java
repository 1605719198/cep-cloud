package com.jlkj.equip.MG.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.equip.MG.dto.EquipmentDomainTreeDTO;
import com.jlkj.equip.MG.entity.EquipmentDomainTree;
import com.jlkj.equip.MG.mapper.EquipmentDomainTreeMapper;
import com.jlkj.equip.MG.service.IEquipmentDomainTreeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备域维护 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-06-09
 */
@Service
public class EquipmentDomainTreeServiceImpl extends ServiceImpl<EquipmentDomainTreeMapper, EquipmentDomainTree> implements IEquipmentDomainTreeService {
    /**
     * 获取设备域树结构资料
     */
    @Override
    public List<EquipmentDomainTreeDTO> getEquipmentDomainTreeList() {
        List<EquipmentDomainTreeDTO> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();

        // 查出所有设备域数据
        List<EquipmentDomainTree> domainTreeList = baseMapper.selectList(new QueryWrapper<EquipmentDomainTree>().orderByAsc("domain_no"));
        // 树结构DTO
        List<EquipmentDomainTreeDTO> domainDTOList = new ArrayList<>();
        // 循环取出父节点id
        if (StringUtils.isNotNull(domainTreeList)) {
            for (EquipmentDomainTree item : domainTreeList) {
                EquipmentDomainTreeDTO dto = new EquipmentDomainTreeDTO();
                BeanUtils.copyProperties(item,dto);
                domainDTOList.add(dto);
                tempList.add(item.getId());
            }
        }
        for (Iterator<EquipmentDomainTreeDTO> iterator = domainDTOList.iterator();iterator.hasNext();) {
            EquipmentDomainTreeDTO typeTree = (EquipmentDomainTreeDTO) iterator.next();
            //如果是Root节点，遍历该父节点的所有子节点
            if (!tempList.contains(typeTree.getParentId())) {
                //进行递归操作
                recursionFn(domainDTOList,typeTree);
                returnList.add(typeTree);
            }
        }
        if (returnList.isEmpty()) {
            returnList = domainDTOList;
        }
        return returnList.stream().map(EquipmentDomainTreeDTO::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<EquipmentDomainTreeDTO> list, EquipmentDomainTreeDTO typeTree) {
        //得到子节点列表
        List<EquipmentDomainTreeDTO> childList = getChildList(list,typeTree);
        typeTree.setChildren(childList);
        for (EquipmentDomainTreeDTO orgChild : childList) {
            if (hasChild(list, orgChild)) {
                recursionFn(list, orgChild);
            }
        }
    }
    /**
     * 得到子节点列表
     */
    private List<EquipmentDomainTreeDTO> getChildList(List<EquipmentDomainTreeDTO> list, EquipmentDomainTreeDTO typeTree) {
        List<EquipmentDomainTreeDTO> orgList = new ArrayList<>();
        //声明迭代器
        Iterator<EquipmentDomainTreeDTO> iterator = list.iterator();
        while (iterator.hasNext()) {
            EquipmentDomainTreeDTO humanOrg = (EquipmentDomainTreeDTO) iterator.next();
            //判断当前迭代对象的夫ID是否等于传入的ID
            if (StringUtils.isNotNull(humanOrg.getParentId()) && humanOrg.getParentId().equals(typeTree.getId())) {
                orgList.add(humanOrg);
            }
        }
        return orgList;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<EquipmentDomainTreeDTO> list, EquipmentDomainTreeDTO typeTree) {
        return getChildList(list,typeTree).size() > 0;
    }
}

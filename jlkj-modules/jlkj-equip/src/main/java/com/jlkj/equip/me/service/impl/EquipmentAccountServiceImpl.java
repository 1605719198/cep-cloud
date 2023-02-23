package com.jlkj.equip.me.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.equip.me.domain.EquipmentAccount;
import com.jlkj.equip.me.domain.EquipmentBasicProperties;
import com.jlkj.equip.me.mapper.EquipmentAccountMapper;
import com.jlkj.equip.me.service.IEquipmentAccountService;
import com.jlkj.equip.me.service.IEquipmentBasicPropertiesService;
import com.jlkj.equip.mg.domain.EquipmentDomainTree;
import com.jlkj.equip.mg.dto.EquipmentDomainTreeDTO;
import com.jlkj.equip.mg.service.IEquipmentDomainTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备台账 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-06-13
 */
@Service
public class EquipmentAccountServiceImpl extends ServiceImpl<EquipmentAccountMapper, EquipmentAccount> implements IEquipmentAccountService {

    @Autowired
    private IEquipmentBasicPropertiesService equipmentBasicPropertiesService;
    @Autowired
    private IEquipmentDomainTreeService equipmentDomainTreeService;

    /**
     * 获取设备树
     * @return
     */
    @Override
    public List<EquipmentBasicProperties> getEquipmentAccountTree() {
        List<EquipmentBasicProperties> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        // 查出所有设备数据
        List<EquipmentBasicProperties> baiscTreeList = equipmentBasicPropertiesService.list(new QueryWrapper<EquipmentBasicProperties>().orderByAsc("equipment_name"));
        // 循环取出父节点id
        if (StringUtils.isNotNull(baiscTreeList)) {
            for (EquipmentBasicProperties item : baiscTreeList) {
                tempList.add(item.getId());
            }
        }
        for (Iterator<EquipmentBasicProperties> iterator = baiscTreeList.iterator();iterator.hasNext();) {
            EquipmentBasicProperties basicProperties = (EquipmentBasicProperties) iterator.next();
            // 如果是Root节点，遍历该父节点的所有子节点
            if (!tempList.contains(basicProperties.getUpperLevel())) {
                // 进行递归操作
                recursionFn(baiscTreeList,basicProperties);
                returnList.add(basicProperties);
            }
        }
        if (returnList.isEmpty()) {
            returnList = baiscTreeList;
        }
        return returnList.stream().map(EquipmentBasicProperties::new).collect(Collectors.toList());
    }

    /**
     * 设备域、设备树结合
     * @return
     */
    @Override
    public List<EquipmentDomainTreeDTO> getEquipmentCombineTree() {
        List<EquipmentDomainTreeDTO> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        // 上级设备为null的数据
        List<EquipmentBasicProperties> upperList = equipmentBasicPropertiesService.list(new QueryWrapper<EquipmentBasicProperties>().orderByAsc("equipment_name"));
        // 处理upperList
        List<EquipmentDomainTreeDTO> upperCollect = new ArrayList<>();

        // 查出所有设备域数据
        List<EquipmentDomainTree> domainTreeList = equipmentDomainTreeService.list(new QueryWrapper<EquipmentDomainTree>().orderByAsc("domain_no"));
        // 处理domainTreeList
        List<EquipmentDomainTreeDTO> domainCollect = new ArrayList<>();

        // 合并树结构DTO
        List<EquipmentDomainTreeDTO> domainDTOList = new ArrayList<>();
        // 循环上级设备数据
        if (StringUtils.isNotNull(upperList)) {
            upperCollect = upperList.stream().map(item -> {
                EquipmentDomainTreeDTO dto = new EquipmentDomainTreeDTO();
                if (StringUtils.isNotNull(item.getUpperLevel())) {
                    dto.setId(item.getId());
                    dto.setParentId(item.getUpperLevel());
                    dto.setDomainName(item.getEquipmentName());
                    dto.setDomainNo(item.getEquipmentNumbert());
                } else {
                    dto.setId(item.getId());
                    dto.setParentId(item.getEquipmentLocation());
                    dto.setDomainName(item.getEquipmentName());
                    dto.setDomainNo(item.getEquipmentNumbert());
                }
                return dto;
            }).collect(Collectors.toList());
        }
        // 循环设备域数据
        if (StringUtils.isNotNull(domainTreeList)) {
            domainCollect = domainTreeList.stream().map(item -> {
                EquipmentDomainTreeDTO dto = new EquipmentDomainTreeDTO();
                dto.setId(item.getId());
                dto.setParentId(item.getParentId());
                dto.setDomainName(item.getDomainName());
                dto.setDomainNo(item.getDomainNo());
                return dto;
            }).collect(Collectors.toList());
        }
        domainDTOList.addAll(upperCollect);
        domainDTOList.addAll(domainCollect);
        // 循环取出父节点id
        if (StringUtils.isNotNull(domainDTOList)) {
            for (EquipmentDomainTreeDTO item : domainDTOList) {
                tempList.add(item.getId());
            }
        }
        for (Iterator<EquipmentDomainTreeDTO> iterator = domainDTOList.iterator();iterator.hasNext();) {
            EquipmentDomainTreeDTO typeTree = (EquipmentDomainTreeDTO) iterator.next();
            //如果是Root节点，遍历该父节点的所有子节点
            if (!tempList.contains(typeTree.getParentId())) {
                //进行递归操作
                recursionFn1(domainDTOList,typeTree);
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
     * @param list
     * @param typeTree
     */
    private void recursionFn(List<EquipmentBasicProperties> list, EquipmentBasicProperties typeTree) {
        //得到子节点列表
        List<EquipmentBasicProperties> childList = getChildList(list,typeTree);
        typeTree.setChildren(childList);
        for (EquipmentBasicProperties orgChild : childList) {
            if (hasChild(list, orgChild)) {
                recursionFn(list, orgChild);
            }
        }
    }
    /**
     * 得到子节点列表
     * @param list
     * @param typeTree
     * @return
     */
    private List<EquipmentBasicProperties> getChildList(List<EquipmentBasicProperties> list, EquipmentBasicProperties typeTree) {
        List<EquipmentBasicProperties> orgList = new ArrayList<EquipmentBasicProperties>();
        //声明迭代器
        Iterator<EquipmentBasicProperties> iterator = list.iterator();
        while (iterator.hasNext()) {
            EquipmentBasicProperties humanOrg = (EquipmentBasicProperties) iterator.next();
            //判断当前迭代对象的夫ID是否等于传入的ID
            if (StringUtils.isNotNull(humanOrg.getUpperLevel()) && humanOrg.getUpperLevel().equals(typeTree.getId())) {
                orgList.add(humanOrg);
            }
        }
        return orgList;
    }

    /**
     * 判断是否有子节点
     * @param list
     * @param typeTree
     * @return
     */
    private boolean hasChild(List<EquipmentBasicProperties> list, EquipmentBasicProperties typeTree) {
        return getChildList(list,typeTree).size() > 0;
    }

    /**
     * 递归列表
     * @param list
     * @param typeTree
     */
    private void recursionFn1(List<EquipmentDomainTreeDTO> list, EquipmentDomainTreeDTO typeTree) {
        //得到子节点列表
        List<EquipmentDomainTreeDTO> childList = getChildList1(list,typeTree);
        typeTree.setChildren(childList);
        for (EquipmentDomainTreeDTO orgChild : childList) {
            if (hasChild1(list, orgChild)) {
                recursionFn1(list, orgChild);
            }
        }
    }
    /**
     * 得到子节点列表
     * @param list
     * @param typeTree
     * @return
     */
    private List<EquipmentDomainTreeDTO> getChildList1(List<EquipmentDomainTreeDTO> list, EquipmentDomainTreeDTO typeTree) {
        List<EquipmentDomainTreeDTO> orgList = new ArrayList<EquipmentDomainTreeDTO>();
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
     * @param list
     * @param typeTree
     * @return
     */
    private boolean hasChild1(List<EquipmentDomainTreeDTO> list, EquipmentDomainTreeDTO typeTree) {
        return getChildList1(list,typeTree).size() > 0;
    }
}

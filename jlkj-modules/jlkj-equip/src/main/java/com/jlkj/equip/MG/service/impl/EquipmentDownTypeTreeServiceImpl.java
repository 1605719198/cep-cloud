package com.jlkj.equip.MG.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.equip.MG.dto.EquipmentDownTypeTreeDTO;
import com.jlkj.equip.MG.entity.EquipmentDownTypeTree;
import com.jlkj.equip.MG.mapper.EquipmentDownTypeTreeMapper;
import com.jlkj.equip.MG.service.IEquipmentDownTypeTreeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备停机类型维护  服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
@Service
public class EquipmentDownTypeTreeServiceImpl extends ServiceImpl<EquipmentDownTypeTreeMapper, EquipmentDownTypeTree> implements IEquipmentDownTypeTreeService {

    /**
     * 设备停机类型树结构
     * @return List<EquipmentDownTypeTreeDTO>
     */
    @Override
    public List<EquipmentDownTypeTreeDTO> getEquipmentTypeTreeList() {
        List<EquipmentDownTypeTreeDTO> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();

        //查询出所有停机类型数据
        List<EquipmentDownTypeTree> equipmentDownTypeTrees = baseMapper.selectList(new QueryWrapper<EquipmentDownTypeTree>().orderByAsc("equipment_down_type_code").orderByAsc("equipment_down_type_name"));
        //停机类型树结构DTO
        List<EquipmentDownTypeTreeDTO> treeDTOList = new ArrayList<>();
        //循环取出父节点编码
        if (StringUtils.isNotNull(equipmentDownTypeTrees)) {
            for (EquipmentDownTypeTree item : equipmentDownTypeTrees) {
                EquipmentDownTypeTreeDTO treeDTO = new EquipmentDownTypeTreeDTO();
                treeDTO.setId(item.getId());
                treeDTO.setLabel(item.getEquipmentDownTypeName());
                treeDTO.setLevel(item.getEquipmentDownTypeLevel());
                treeDTO.setCode(item.getEquipmentDownTypeCode());
                treeDTO.setParentId(item.getEquipmentDownTypeParentCode());
                treeDTOList.add(treeDTO);
                tempList.add(item.getId());
            }
        }
        for (Iterator<EquipmentDownTypeTreeDTO> iterator = treeDTOList.iterator();iterator.hasNext();) {
            EquipmentDownTypeTreeDTO typeTree = (EquipmentDownTypeTreeDTO) iterator.next();
            //如果是Root节点，遍历该父节点的所有子节点
            if (!tempList.contains(typeTree.getParentId())) {
                //进行递归操作
                recursionFn(treeDTOList,typeTree);
                returnList.add(typeTree);
            }
        }
        if (returnList.isEmpty()) {
            returnList = treeDTOList;
        }
        return returnList.stream().map(EquipmentDownTypeTreeDTO::new).collect(Collectors.toList());
    }
    /**
     * 递归列表
     */
    private void recursionFn(List<EquipmentDownTypeTreeDTO> list, EquipmentDownTypeTreeDTO typeTree) {
        //得到子节点列表
        List<EquipmentDownTypeTreeDTO> childList = getChildList(list,typeTree);
        typeTree.setChildren(childList);
        for (EquipmentDownTypeTreeDTO orgChild : childList) {
            if (hasChild(list, orgChild)) {
                recursionFn(list, orgChild);
            }
        }
    }
    /**
     * 得到子节点列表
     */
    private List<EquipmentDownTypeTreeDTO> getChildList(List<EquipmentDownTypeTreeDTO> list, EquipmentDownTypeTreeDTO typeTree) {
        List<EquipmentDownTypeTreeDTO> orgList = new ArrayList<>();
        //声明迭代器
        Iterator<EquipmentDownTypeTreeDTO> iterator = list.iterator();
        while (iterator.hasNext()) {
            EquipmentDownTypeTreeDTO humanOrg = (EquipmentDownTypeTreeDTO) iterator.next();
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
    private boolean hasChild(List<EquipmentDownTypeTreeDTO> list, EquipmentDownTypeTreeDTO typeTree) {
        return getChildList(list,typeTree).size() > 0;
    }
}

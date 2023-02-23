package com.jlkj.equip.me.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.equip.me.domain.EquipmentBasicProperties;
import com.jlkj.equip.me.dto.EquipmentBasicAccountDTO;
import com.jlkj.equip.me.dto.EquipmentQueryAccountDTO;
import com.jlkj.equip.me.mapper.EquipmentBasicPropertiesMapper;
import com.jlkj.equip.me.service.IEquipmentBasicPropertiesService;
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
public class EquipmentBasicPropertiesServiceImpl extends ServiceImpl<EquipmentBasicPropertiesMapper, EquipmentBasicProperties> implements IEquipmentBasicPropertiesService {

    @Autowired
    private EquipmentBasicPropertiesMapper equipmentBasicPropertiesMapper;

    /**
     * 查询设备台账信息
     */
    @Override
    public IPage<EquipmentBasicAccountDTO> queryEquipmentAccount(Integer current, Integer limit) {
        Page<EquipmentBasicAccountDTO> page = new Page<>(current,limit);
        return equipmentBasicPropertiesMapper.queryEquipmentAccount(page);
    }
    /**
     * 条件查询设备台账信息
     */
    @Override
    public IPage<EquipmentBasicAccountDTO> getEquipmentAccountByParams(EquipmentQueryAccountDTO queryAccountDTO) {
        Page<EquipmentBasicAccountDTO> page = new Page<>(queryAccountDTO.getPage(),queryAccountDTO.getLimit());
        IPage<EquipmentBasicAccountDTO> equipmentAccountByParams = getBaseMapper().getEquipmentAccountByParams(page, queryAccountDTO);
        return equipmentAccountByParams;
    }

    /**
     * 根据设备位置查询设备信息并组树
     */
    @Override
    public List<EquipmentBasicProperties> getEquipmentByLocationId(String locationId) {
        // 返回结果list
        List<EquipmentBasicProperties> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        // 根据设备位置查询设备基础资料信息
        List<EquipmentBasicProperties> equipmentList = baseMapper.selectList(new QueryWrapper<EquipmentBasicProperties>().eq(StringUtils.isNotEmpty(locationId), "equipment_location", locationId));
        // 循环取出父节点id
        if (StringUtils.isNotNull(equipmentList)) {
            for (EquipmentBasicProperties item : equipmentList) {
                tempList.add(item.getId());
            }
        }
        for (Iterator<EquipmentBasicProperties> iterator = equipmentList.iterator();iterator.hasNext();) {
            EquipmentBasicProperties basicProperties = iterator.next();
            // 如果是Root节点，遍历该父节点的所有子节点
            if (!tempList.contains(basicProperties.getUpperLevel())) {
                // 进行递归操作
                recursionFn(equipmentList,basicProperties);
                returnList.add(basicProperties);
            }
        }
        if (returnList.isEmpty()) {
            returnList = equipmentList;
        }
        return returnList.stream().map(EquipmentBasicProperties::new).collect(Collectors.toList());
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

}

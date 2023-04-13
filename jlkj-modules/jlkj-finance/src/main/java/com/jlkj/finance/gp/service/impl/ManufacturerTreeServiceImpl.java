package com.jlkj.finance.gp.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;

import com.jlkj.finance.gp.dto.ManufacturerTreeDTO;

import com.jlkj.finance.gp.domain.ManufacturerTree;
import com.jlkj.finance.gp.service.ManufacturerBaseService;
import com.jlkj.finance.gp.service.ManufacturerTreeService;
import com.jlkj.finance.gp.mapper.ManufacturerTreeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.jlkj.finance.gp.dto.ManufacturerBaseDTO;
import com.jlkj.finance.gp.domain.ManufacturerBase;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author wang'mai
* @description 针对表【t_manufacturer_tree(厂商树本资料)】的数据库操作Service实现
*/
@Service
public class ManufacturerTreeServiceImpl extends ServiceImpl<ManufacturerTreeMapper, ManufacturerTree>
    implements ManufacturerTreeService {
    @Autowired
    private ManufacturerBaseService manufacturerBaseService;
    /**
     * 成本科目树结构
     * @return public List<ManufacturerTreeDTO> getManufacturerTreeList()
     */
    @Override
    public List<ManufacturerTreeDTO> getManufacturerTreeList() {
        List<ManufacturerTreeDTO> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        //查询出所有节点编码数据
        List<ManufacturerTree> equipmentDownTypeTrees = baseMapper.selectList(new QueryWrapper<ManufacturerTree>().orderByAsc("node_no").orderByAsc("node_name"));
        //厂商树结构DTO
        List<ManufacturerTreeDTO> treeDTOList = new ArrayList<>();
        //循环取出父节点编码
        if (StringUtils.isNotNull(equipmentDownTypeTrees)) {
            for (ManufacturerTree item : equipmentDownTypeTrees) {
                ManufacturerTreeDTO treeDTO = new ManufacturerTreeDTO();
                treeDTO.setId(item.getId());
                treeDTO.setNodeNo(item.getNodeNo());
                treeDTO.setNodeName(item.getNodeName());
                treeDTO.setParentid(item.getParentid());

                treeDTOList.add(treeDTO);
                tempList.add(item.getId());
            }
        }
        for (ManufacturerTreeDTO typeTree : treeDTOList) {
            //如果是Root节点，遍历该父节点的所有子节点
            if (!tempList.contains(typeTree.getParentid())) {
                //进行递归操作
                recursionFn(treeDTOList, typeTree);
                returnList.add(typeTree);
            }
        }
        if (returnList.isEmpty()) {
            returnList = treeDTOList;
        }
        return returnList.stream().map(ManufacturerTreeDTO::new).collect(Collectors.toList());
    }
    /**
     * 递归列表
     * @param list 厂商树DTO数组
     * @param typeTree 厂商树DTO
     */
    private void recursionFn(List<ManufacturerTreeDTO> list, ManufacturerTreeDTO typeTree) {
        //得到子节点列表
        List<ManufacturerTreeDTO> childList = getChildList(list,typeTree);
        typeTree.setChildren(childList);
        for (ManufacturerTreeDTO orgChild : childList) {
            if (hasChild(list, orgChild)) {
                recursionFn(list, orgChild);
            }
        }
    }
    /**
     * 得到子节点列表
     * @param list 厂商树DTO数组
     * @param typeTree 厂商树DTO
     * @return  private List<ManufacturerTreeDTO> getChildList(List<ManufacturerTreeDTO> list, ManufacturerTreeDTO typeTree)
     */
    private List<ManufacturerTreeDTO> getChildList(List<ManufacturerTreeDTO> list, ManufacturerTreeDTO typeTree) {
        List<ManufacturerTreeDTO> orgList = new ArrayList<>();
        //声明迭代器
        for (ManufacturerTreeDTO humanOrg : list) {
            //判断当前迭代对象的夫ID是否等于传入的ID
            if (StringUtils.isNotNull(humanOrg.getParentid()) && humanOrg.getParentid().equals(typeTree.getId())) {
                orgList.add(humanOrg);
            }

        }
        return orgList;
    }

    /**
     * 判断是否有子节点
     * @param list 厂商树DTO数组
     * @param typeTree 厂商树DTO
     * @return  private boolean hasChild(List<ManufacturerTreeDTO> list, ManufacturerTreeDTO typeTree)
     */
    private boolean hasChild(List<ManufacturerTreeDTO> list, ManufacturerTreeDTO typeTree) {
        return getChildList(list,typeTree).size() > 0;
    }


}





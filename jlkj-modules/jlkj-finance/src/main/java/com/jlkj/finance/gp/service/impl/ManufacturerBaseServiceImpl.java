
package com.jlkj.finance.gp.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.dto.financeperdto.ManufacturerBaseDTO;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.finance.gp.domain.ManufacturerBase;
import com.jlkj.finance.gp.domain.ManufacturerTree;
import com.jlkj.finance.gp.mapper.ManufacturerTreeMapper;
import com.jlkj.finance.gp.service.ManufacturerBaseService;
import com.jlkj.finance.gp.mapper.ManufacturerBaseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author wang'mai
* @description 针对表【t_manufacturer_base(厂商基本资料)】的数据库操作Service实现
*/

@Service
public class ManufacturerBaseServiceImpl extends ServiceImpl<ManufacturerBaseMapper, ManufacturerBase>
    implements ManufacturerBaseService{
    @Autowired
    private ManufacturerTreeMapper manufacturerTreeMapper;

    @Override
    public ManufacturerBaseDTO createTreeData(ManufacturerBaseDTO manufacturerBaseDTO){
        ManufacturerBase manuFacturerBase = new ManufacturerBase();
        BeanUtils.copyProperties(manufacturerBaseDTO, manuFacturerBase);
        String countryId = manuFacturerBase.getCountryId();
        String mainAreaId= manuFacturerBase.getMainAreaId();
        String detailAreaId = manuFacturerBase.getDetailAreaId();
        String manufacturerChineseName = manufacturerBaseDTO.getManufacturerChineseName();
        String manufacturerId = manufacturerBaseDTO.getManufacturerId();
        String countryUuid;
        String mainAreaUuid;
        String detialAreaUuid;
        String manufacturerUuid;
        LambdaQueryWrapper<ManufacturerTree> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(countryId),ManufacturerTree::getNodeNo,countryId);
        ManufacturerTree tree = manufacturerTreeMapper.selectOne(queryWrapper);
        if(tree==null){
            ManufacturerTree manufacturerTree = new ManufacturerTree();
            manufacturerTree.setNodeNo(countryId);
            manufacturerTree.setNodeName(manufacturerBaseDTO.getCountryName());
            manufacturerTreeMapper.insert(manufacturerTree);
            countryUuid=manufacturerTree.getId();
        }else{
            countryUuid=tree.getId();
        }
        LambdaQueryWrapper<ManufacturerTree> queryWrapperMainAreaId = new LambdaQueryWrapper<>();
        queryWrapperMainAreaId.eq(StringUtils.isNotBlank(mainAreaId),ManufacturerTree::getNodeNo,mainAreaId)
                .eq(StringUtils.isNotBlank(countryUuid),ManufacturerTree::getParentid,countryUuid);
        ManufacturerTree tree2 = manufacturerTreeMapper.selectOne(queryWrapperMainAreaId);
        if(tree2==null){
            ManufacturerTree manufacturerTree = new ManufacturerTree();
            manufacturerTree.setCompId(manufacturerBaseDTO.getCompId());
            manufacturerTree.setNodeNo(mainAreaId);
            manufacturerTree.setNodeName(manufacturerBaseDTO.getMainAreaDesc());
            manufacturerTree.setParentid(countryUuid);
            manufacturerTreeMapper.insert(manufacturerTree);
            mainAreaUuid=manufacturerTree.getId();
        }else{
            mainAreaUuid=tree2.getId();
        }
        LambdaQueryWrapper<ManufacturerTree> queryWrapperDetailAreaId = new LambdaQueryWrapper<>();
        queryWrapperDetailAreaId.eq(StringUtils.isNotBlank(detailAreaId),ManufacturerTree::getNodeNo,detailAreaId)
                .eq(StringUtils.isNotBlank(mainAreaUuid),ManufacturerTree::getParentid,mainAreaUuid);
        ManufacturerTree tree3 = manufacturerTreeMapper.selectOne(queryWrapperDetailAreaId);
        if(tree3==null){
            ManufacturerTree manufacturerTree = new ManufacturerTree();
            manufacturerTree.setCompId(manufacturerBaseDTO.getCompId());
            manufacturerTree.setNodeNo(detailAreaId);
            manufacturerTree.setNodeName(manufacturerBaseDTO.getDetailAreaDesc());
            manufacturerTree.setParentid(mainAreaUuid);
            manufacturerTreeMapper.insert(manufacturerTree);
            detialAreaUuid = manufacturerTree.getId();
        }else{
            detialAreaUuid=tree3.getId();
        }
        LambdaQueryWrapper<ManufacturerTree> queryWrapperManufacturerId = new LambdaQueryWrapper<>();
        queryWrapperManufacturerId.eq(StringUtils.isNotBlank(manufacturerId),ManufacturerTree::getNodeNo,manufacturerId)
        .eq(StringUtils.isNotBlank(detialAreaUuid),ManufacturerTree::getParentid,detialAreaUuid);
        ManufacturerTree tree4 = manufacturerTreeMapper.selectOne(queryWrapperManufacturerId);
        if (tree4==null){
            ManufacturerTree manufacturerTree = new ManufacturerTree();
            manufacturerTree.setCompId(manufacturerBaseDTO.getCompId());
            manufacturerTree.setNodeNo(manufacturerId);
            manufacturerTree.setNodeName(manufacturerChineseName);
            manufacturerTree.setParentid(detialAreaUuid);
            manufacturerTreeMapper.insert(manufacturerTree);
            manufacturerUuid = manufacturerTree.getId();
        }else {
            manufacturerUuid= tree4.getId();
        }
        manuFacturerBase.setParentId(manufacturerUuid);
        this.updateById(manuFacturerBase);
        BeanUtils.copyProperties(manuFacturerBase,manufacturerBaseDTO);
        return manufacturerBaseDTO;
    }

}






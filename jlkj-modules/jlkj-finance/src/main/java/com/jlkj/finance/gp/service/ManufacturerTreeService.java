package com.jlkj.finance.gp.service;


import com.jlkj.finance.gp.dto.ManufacturerTreeDTO;

import com.jlkj.finance.gp.domain.ManufacturerTree;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author wang'mai
* @description 针对表【t_manufacturer_tree(厂商树本资料)】的数据库操作Service
* @createDate 2022-05-03 09:24:03
*/
public interface ManufacturerTreeService extends IService<ManufacturerTree> {
    /**
     * 获取厂商树结构
     * @return List<ManufacturerTreeDTO> getManufacturerTreeList()
     */
    List<ManufacturerTreeDTO> getManufacturerTreeList();

}

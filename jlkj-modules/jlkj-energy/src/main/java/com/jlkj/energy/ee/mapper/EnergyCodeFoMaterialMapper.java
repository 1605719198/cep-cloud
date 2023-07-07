package com.jlkj.energy.ee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.energy.ee.domain.EnergyCodeForMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 265675
* @description 针对表【t_energy_code_fo_material(固、液体能源代码、料号对照)】的数据库操作Mapper
* @createDate 2022-04-28 11:25:31
* @domain com.jlkj.energy.ee.domain.EnergyCodeFoMaterial
*/
@Repository
@Mapper
public interface EnergyCodeFoMaterialMapper extends BaseMapper<EnergyCodeForMaterial> {

}





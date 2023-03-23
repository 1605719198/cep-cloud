package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeSiWorkshopAcc;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车间明细台账 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-05-20
 */
public interface SafeWorkshopAccMapper extends BaseMapper<SafeSiWorkshopAcc> {

    /**
     * 车间明细台账-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,material_name,specification,amount,position,workshop_name,\n" +
            "create_user,DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%s') as create_time\n" +
            "from safe_si_workshop_acc\n" +
            "where workshop_name = #{condition.workshop_name} and (#{condition.material_name} = '' or material_name like ('%' + #{condition.material_name} +'%'))\n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeWorkshopAccPageList(Page<?> page, @Param("condition") Map<String, Object> condition);

    /**
     * 车间明细台账-工段列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,section_name\n" +
            "from safe_si_workshop_acc_section\n" +
            "where workshop_name = #{workshop_name}\n" +
            "order by section_name"
    )
    List<Map<String, Object>> getSafeworkshopAccSectionColList(Map<String, Object> condition);

    /**
     * 车间明细台账-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select workshop_name,material_name,specification ${condition.sql_section_max} from(\n" +
            "select workshop_name,material_name,specification ${condition.sql_section_col}\n" +
            "from safe_si_workshop_acc\n" +
            "where workshop_name = #{condition.workshop_name} and material_name like concat('%', #{condition.material_name}, '%')\n" +
            ") as a group by workshop_name,material_name,specification\n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeWorkshopAccPageListByProcedure(Page<?> page, @Param("condition") Map<String, Object> condition);

    /**
     * 车间明细台账-工段列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select section_name, id as prop\n" +
            "from safe_si_workshop_acc_section\n" +
            "where workshop_name = #{workshop_name}\n" +
            "order by id"
    )
    List<Map<String, Object>> getSafeworkshopAccSectionList(Map<String, Object> condition);

    /**
     * 车间明细台账-删除
     * @param condition 查询条件
     * @return 结果列表
     */
    @Delete(
            "delete from safe_si_workshop_acc where workshop_name=#{workshop_name} and material_name=#{material_name} and specification=#{specification}"
    )
    int deleteSafeWorkshopAcc(Map<String, Object> condition);

    /**
     * 车间明细台账-报文-列表
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            "select \n" +
            "id as uuid,\n" +
            "'WK00' as depNo,\n" +
            "type,\n" +
            "material_name as name,\n" +
            "amount as num,\n" +
            "concat(workshop_name,position) as location,\n" +
            "person_liable_no as personLiable,\n" +
            "person_liable_name as personLiableCh,\n" +
            "person_liable_tel as telNo,\n" +
            "person_liable_no as personLiable2,\n" +
            "person_liable_name as personLiableCh2,\n" +
            "person_liable_tel as telNo2,\n" +
            "create_user_no as createNo,\n" +
            "DATE_FORMAT(create_time,'%Y%m%d') as createDate,\n" +
            "DATE_FORMAT(create_time,'%H%i%s') as createTime,\n" +
            "modify_user_no as updateNo,\n" +
            "DATE_FORMAT(modify_time,'%Y%m%d') as updateDate,\n" +
            "DATE_FORMAT(modify_time,'%H%i%s') as updateTime\n" +
            "from safe_si_workshop_acc\n" +
            "where (#{id_list} = id) or (#{id_list} like concat('%,', id)) or (#{id_list} like concat(id, ',%')) or (#{id_list} like concat('%,', id, ',%'))\n"
    )
    List<Map<String, Object>> getErpSafeworkshopAccListByIds(Map<String, Object> condition);

    /**
     * 车间明细台账-报文-删除数据
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            "select GROUP_CONCAT(id) as id_list from safe_si_workshop_acc where workshop_name=#{workshop_name} and material_name=#{material_name} and specification=#{specification}"
    )
    Map<String, Object> getErpSafeworkshopAccDeleteList(Map<String, Object> condition);

    /**
     * 车间明细台账-详情
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            "select \n" +
            "workshop_name,\n" +
            "material_name,\n" +
            "type,\n" +
            "specification,\n" +
            "amount,\n" +
            "position,\n" +
            "person_liable_id,\n" +
            "person_liable_no,\n" +
            "person_liable_name,\n" +
            "person_liable_tel\n" +
            "from safe_si_workshop_acc\n" +
            "where id = #{id}"
    )
    Map<String, Object> getSafeworkshopAccInfo(Map<String, Object> condition);
}

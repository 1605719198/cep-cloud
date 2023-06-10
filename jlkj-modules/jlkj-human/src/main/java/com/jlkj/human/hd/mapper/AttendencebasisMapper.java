package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.Attendencebasis;
import com.jlkj.human.hd.dto.BasisOptionsDTO;

import java.util.List;

/**
 * 员工出勤基本资料维护Mapper接口
 * 
 * @author 266861
 * @date 2023-03-20
 */
public interface AttendencebasisMapper
{
    /**
     * 查询员工出勤基本资料维护
     * 
     * @param id 员工出勤基本资料维护主键
     * @return 员工出勤基本资料维护
     */
    public Attendencebasis selectAttendencebasisById(Long id);

    /**
     * 通过编码查询员工出勤基本资料维护
     *
     * @param attendencebasis 员工出勤基本资料维护
     * @return 员工出勤基本资料维护
     */
    public Attendencebasis selectAttendencebasisParentByCode(Attendencebasis  attendencebasis);

    /**
     * 查询是否有重复编码数据
     *
     * @param attendencebasis 员工出勤基本资料
     * @return 员工出勤基本资料维护
     */
    public int queryRepetitivedata(Attendencebasis attendencebasis);

    /**
     * 通过父节点id查询员工出勤基本资料维护
     *
     * @param parentid 员工出勤基本资料维护编码
     * @return 员工出勤基本资料维护
     */
    public List<BasisOptionsDTO> selectAttendencebasisByParentid(Long  parentid);

    /**
     * 查询员工出勤基本资料维护列表
     * 
     * @param attendencebasis 员工出勤基本资料维护
     * @return 员工出勤基本资料维护集合
     */
    public List<Attendencebasis> selectAttendencebasisList(Attendencebasis attendencebasis);

    /**
     * 新增员工出勤基本资料维护
     * 
     * @param attendencebasis 员工出勤基本资料维护
     * @return 结果
     */
    public Integer insertAttendencebasis(Attendencebasis attendencebasis);

    /**
     * 修改员工出勤基本资料维护
     * 
     * @param attendencebasis 员工出勤基本资料维护
     * @return 结果
     */
    public int updateAttendencebasis(Attendencebasis attendencebasis);

    /**
     * 删除员工出勤基本资料维护
     * 
     * @param id 员工出勤基本资料维护主键
     * @return 结果
     */
    public int deleteAttendencebasisById(Long id);

    /**
     * 批量删除员工出勤基本资料维护
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAttendencebasisByIds(Long[] ids);
}

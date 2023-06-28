package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.BeanCopyUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.human.hd.domain.ArrangeClass;
import com.jlkj.human.hd.domain.PersonClassDetail;
import com.jlkj.human.hd.domain.ShiftCode;
import com.jlkj.human.hd.mapper.PersonClassDetailMapper;
import com.jlkj.human.hd.service.IArrangeClassService;
import com.jlkj.human.hd.service.IPersonClassDetailService;
import com.jlkj.human.hd.service.IShiftCodeService;
import com.jlkj.human.hp.service.IHumanDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 人员排班明细Service业务层处理
 *
 * @author 266861
 * @date 2023-04-12
 */
@Service
public class PersonClassDetailServiceImpl implements IPersonClassDetailService
{
    @Autowired
    private PersonClassDetailMapper personClassDetailMapper;
    @Autowired
    private IArrangeClassService arrangeClassService;
    @Autowired
    private IHumanDeptService sysDeptService;
    @Autowired
    private IShiftCodeService shiftCodeService;

    /**
     * 查询人员排班明细
     *
     * @param id 人员排班明细主键
     * @return 人员排班明细
     */
    @Override
    public PersonClassDetail selectPersonClassDetailById(String id)
    {
        return personClassDetailMapper.selectPersonClassDetailById(id);
    }

    /**
     * 查询人员排班明细列表
     *
     * @param personClassDetail 人员排班明细
     * @return 人员排班明细
     */
    @Override
    public List<PersonClassDetail> selectPersonClassDetailList(PersonClassDetail personClassDetail)
    {
        return personClassDetailMapper.selectPersonClassDetailList(personClassDetail);
    }

    /**
     * 新增人员排班明细
     *
     * @param personClassDetail 人员排班明细
     * @return 结果
     */
    @Override
    public int insertPersonClassDetail(PersonClassDetail personClassDetail)
    {
        personClassDetail.setId(IdUtils.simpleUUID());
        return personClassDetailMapper.insertPersonClassDetail(personClassDetail);
    }

    /**
     * 调班
     *
     * @param list 人员排班明细列表
     * @return 结果
     */
    @Override
    public int changeDetail(ArrayList<PersonClassDetail> list)
    {
        int result =0;
        Iterator<PersonClassDetail> iterator = list.iterator();
        while (iterator.hasNext()){
            PersonClassDetail detail = iterator.next();
            if(detail.getSecShiftCode()!=null&&detail.getSecShiftId()==null){
                String secId = null;
                try {
                    ShiftCode shiftCode = new ShiftCode();
                    shiftCode.setShiftmodeId(detail.getShiftModeId());
                    shiftCode.setShiftCode(detail.getSecShiftCode());
                    secId = shiftCodeService.queryShiftCode(shiftCode).getId();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                detail.setSecShiftId(secId);
            }
            if(detail.getId()!=null){
                updatePersonClassDetail(detail);
            }else{
                insertPersonClassDetail(detail);
            }
            result++;
        }
        return result;
    }

    /**
     * 排班操作
     *
     * @param personClassDetail 排班操作
     * @return 结果
     */
    @Override
    public int setPersonClassDetail(PersonClassDetail personClassDetail)
    {
        int result = 0;
        long startTime = personClassDetail.getStartDate().getTime();
        long endTime = personClassDetail.getEndDate().getTime();
        long oneDay = 1000 * 60 * 60 * 24L;
        long time = startTime;
        int day = 0;
        while (time <= endTime) {
            time += oneDay;
            day++;
        }
        ArrangeClass param = new ArrangeClass();
        BeanCopyUtils.copy(personClassDetail,param);
        param.setShiftmodeId(personClassDetail.getShiftModeId());
        List<ArrangeClass> arrangeClassList = arrangeClassService.queryArrangeClass(param);
        if(arrangeClassList.size()!=day){
            return -1;
        }
        time = startTime;
        while (time <= endTime) {
            Date date = new Date(time);
            ArrangeClass type = new ArrangeClass();
            type.setCompId(personClassDetail.getCompId());
            type.setShiftmodeId(personClassDetail.getShiftModeId());
            type.setClassId(personClassDetail.getClassId());
            type.setArrShiDate(date);
            ArrangeClass arrangeClass =arrangeClassService.selectArrangeClassByClass(type);
            personClassDetail.setArrShiDate(date);
            personClassDetail.setFirShiftId(arrangeClass.getShiftId());
            personClassDetail.setFirShiftCode(arrangeClass.getShiftCode());
            insertPersonClassDetail(personClassDetail);
            time += oneDay;
            result++;
        }
        return result;
    }



    /**
     * 修改人员排班明细
     *
     * @param personClassDetail 人员排班明细
     * @return 结果
     */
    @Override
    public int updatePersonClassDetail(PersonClassDetail personClassDetail)
    {
        return personClassDetailMapper.updatePersonClassDetail(personClassDetail);
    }

    /**
     * 批量删除人员排班明细
     *
     * @param ids 需要删除的人员排班明细主键
     * @return 结果
     */
    @Override
    public int deletePersonClassDetailByIds(String[] ids)
    {
        return personClassDetailMapper.deletePersonClassDetailByIds(ids);
    }

    /**
     * 删除人员排班明细信息
     *
     * @param id 人员排班明细主键
     * @return 结果
     */
    @Override
    public int deletePersonClassDetailById(String id)
    {
        return personClassDetailMapper.deletePersonClassDetailById(id);
    }
}

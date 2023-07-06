package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.BeanCopyUtils;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.human.hd.domain.ArrangeClass;
import com.jlkj.human.hd.domain.ArrangeClassMaster;
import com.jlkj.human.hd.mapper.ArrangeClassMasterMapper;
import com.jlkj.human.hd.service.IArrangeClassMasterService;
import com.jlkj.human.hd.service.IArrangeClassService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 排班记录Service业务层处理
 *
 * @author 266861
 * @date 2023-04-10
 */
@Service
public class ArrangeClassMasterServiceImpl implements IArrangeClassMasterService
{
    @Autowired
    private ArrangeClassMasterMapper arrangeClassMasterMapper;
    @Autowired
    private IArrangeClassService arrangeClassService;

    /**
     * 查询排班记录
     *
     * @param id 排班记录主键
     * @return 排班记录
     */
    @Override
    public ArrangeClassMaster selectArrangeClassMasterById(String id)
    {
        return arrangeClassMasterMapper.selectArrangeClassMasterById(id);
    }

    /**
     * 查询排班记录列表
     *
     * @param arrangeClassMaster 排班记录
     * @return 排班记录
     */
    @Override
    public List<ArrangeClassMaster> selectArrangeClassMasterList(ArrangeClassMaster arrangeClassMaster)
    {
        return arrangeClassMasterMapper.selectArrangeClassMasterList(arrangeClassMaster);
    }

    /**
     * 新增排班记录
     *
     * @param arrangeClassMaster 排班记录
     * @return 结果
     */
    @Override
    public int insertArrangeClassMaster(ArrangeClassMaster arrangeClassMaster)
    {   arrangeClassMaster.setId(IdUtils.simpleUUID());
        for(ArrangeClass arrangeClass: arrangeClassMaster.getDetailList()){
            BeanUtils.copyProperties(arrangeClassMaster,arrangeClass);
            arrangeClass.setArrangeClassMasterId(arrangeClassMaster.getId());
            arrangeClass.setId(null);
            arrangeClassService.insertArrangeClass(arrangeClass);
        }
        return arrangeClassMasterMapper.insertArrangeClassMaster(arrangeClassMaster);
    }

    /**
     * 修改排班记录
     *
     * @param arrangeClassMaster 排班记录
     * @return 结果
     */
    @Override
    public int updateArrangeClassMaster(ArrangeClassMaster arrangeClassMaster)
    {
        return arrangeClassMasterMapper.updateArrangeClassMaster(arrangeClassMaster);
    }

    /**
     * 批量删除排班记录
     *
     * @param ids 需要删除的排班记录主键
     * @return 结果
     */
    @Override
    public int deleteArrangeClassMasterByIds(String[] ids)
    {
        return arrangeClassMasterMapper.deleteArrangeClassMasterByIds(ids);
    }

    /**
     * 删除排班记录信息
     *
     * @param id 排班记录主键
     * @return 结果
     */
    @Override
    public int deleteArrangeClassMasterById(String id)
    {
        return arrangeClassMasterMapper.deleteArrangeClassMasterById(id);
    }

    /**
     * 年底定时排班
     * @return 定时排班结果
     * @author 266861
     * @date 2023/7/6 8:46
     **/
    @Override
    public int scheduledShifts(){
        //查询当年最后一天排班明细
        ArrangeClass params = new ArrangeClass();
        Date lastDate = getLastOfYear();
        String lastDateStr = DateUtils.parseDateToStr("yyyy-MM-dd",lastDate);
        params.setArrShiDate(lastDate);
        List<ArrangeClass> arrangeClassList = arrangeClassService.queryArrangeClass(params);
        // 遍历排班明细找对应主档
        for(ArrangeClass arrangeClass : arrangeClassList){
          ArrangeClassMaster master = selectArrangeClassMasterById(arrangeClass.getArrangeClassMasterId());
            //记录排班周期个数
            int number = 0;
            if(!master.getRule().contains(",")&&master.getRule().length()!=0){
//                number = 0;
            }else{
                number = (master.getRule().length()- master.getRule().replaceAll(",","").length());
            }
            String [] ruleArray = new String[number+1];
            ruleArray[number--] = arrangeClass.getShiftCode();
            // 日期向前倒获取对应的班次编码
            for (int i = number; i >=0 ; i--) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(lastDate);
                cal.add(Calendar.DATE,i-number-1);
                BeanCopyUtils.copy(arrangeClass,params);
                params.setArrShiDate(cal.getTime());
                ArrangeClass arrange = arrangeClassService.selectArrangeClassByClass(params);
                ruleArray[i] = arrange.getShiftCode();
            }
            System.out.println(master.getRule());
            System.out.println("排班周期个数为"+(number+2));
            System.out.println("新规则为"+ Arrays.toString(ruleArray));
        }
        return arrangeClassList.size();
    }
    /**
    * @Description 获取当年最后一天
    * @return 当年最后一天时间
    * @author 266861
    * @date 2023/7/6 9:44
    **/
    public static Date getLastOfYear(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, currentYear);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

}

package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.BeanCopyUtils;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.domain.ArrangeClass;
import com.jlkj.human.hd.domain.ArrangeClassMaster;
import com.jlkj.human.hd.mapper.ArrangeClassMasterMapper;
import com.jlkj.human.hd.service.IArrangeClassMasterService;
import com.jlkj.human.hd.service.IArrangeClassService;
import com.jlkj.human.hd.service.IPersonClassMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 排班记录Service业务层处理
 *
 * @author 266861
 * @date 2023-04-10
 */
@Service
public class ArrangeClassMasterServiceImpl implements IArrangeClassMasterService {
    @Autowired
    private ArrangeClassMasterMapper arrangeClassMasterMapper;
    @Autowired
    private IArrangeClassService arrangeClassService;
    @Autowired
    private IPersonClassMasterService personClassMasterService;

    /**
     * 查询排班记录
     *
     * @param id 排班记录主键
     * @return 排班记录
     */
    @Override
    public ArrangeClassMaster selectArrangeClassMasterById(String id) {
        return arrangeClassMasterMapper.selectArrangeClassMasterById(id);
    }

    /**
     * 查询排班记录列表
     *
     * @param arrangeClassMaster 排班记录
     * @return 排班记录
     */
    @Override
    public List<ArrangeClassMaster> selectArrangeClassMasterList(ArrangeClassMaster arrangeClassMaster) {
        return arrangeClassMasterMapper.selectArrangeClassMasterList(arrangeClassMaster);
    }

    /**
     * 新增排班记录
     *
     * @param arrangeClassMaster 排班记录
     * @return 结果
     */
    @Override
    public int insertArrangeClassMaster(ArrangeClassMaster arrangeClassMaster) {
        if(StringUtils.isEmpty(SecurityUtils.getUsername())){
            arrangeClassMaster.setCreatorId(null);
            arrangeClassMaster.setCreator("定时启动");
        }else{
            arrangeClassMaster.setCreator(SecurityUtils.getNickName());
            arrangeClassMaster.setCreatorId(SecurityUtils.getUsername());
        }
        arrangeClassMaster.setId(IdUtils.simpleUUID());
        arrangeClassMaster.setCreateDate(new Date());
        for (ArrangeClass arrangeClass : arrangeClassMaster.getDetailList()) {
            BeanUtils.copyProperties(arrangeClassMaster, arrangeClass);
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
    public int updateArrangeClassMaster(ArrangeClassMaster arrangeClassMaster) {
        return arrangeClassMasterMapper.updateArrangeClassMaster(arrangeClassMaster);
    }

    /**
     * 批量删除排班记录
     *
     * @param ids 需要删除的排班记录主键
     * @return 结果
     */
    @Override
    public int deleteArrangeClassMasterByIds(String[] ids) {
        return arrangeClassMasterMapper.deleteArrangeClassMasterByIds(ids);
    }

    /**
     * 删除排班记录信息
     *
     * @param id 排班记录主键
     * @return 结果
     */
    @Override
    public int deleteArrangeClassMasterById(String id) {
        return arrangeClassMasterMapper.deleteArrangeClassMasterById(id);
    }

    /**
     * 年底定时排班
     *
     * @return 定时排班结果
     * @author 266861
     * @date 2023/7/6 8:46
     **/
    @Override
    public int scheduledShifts() {
        // 排班主档插入结果
        int result = 0;
        //查询当年最后一天排班明细
        ArrangeClass params = new ArrangeClass();
        Date lastDate = getLastOfYear(Calendar.getInstance().get(Calendar.YEAR));
        // 来年数据
        int nextYear = Calendar.getInstance().get(Calendar.YEAR)+1;
        Date startDate = getFirstOfYear(nextYear);
        Date endDate = getLastOfYear(nextYear);
        String lastDateStr = DateUtils.parseDateToStr("yyyy-MM-dd", lastDate);
        params.setArrShiDate(lastDate);
        List<ArrangeClass> arrangeClassList = arrangeClassService.queryArrangeClass(params);
        // 遍历排班明细找对应主档
        for (ArrangeClass arrangeClass : arrangeClassList) {
            ArrangeClassMaster master = selectArrangeClassMasterById(arrangeClass.getArrangeClassMasterId());
            //记录排班周期个数
            int number = 0;
            if(StringUtils.isNotNull(master)){
                if (!master.getRule().contains(",") && master.getRule().length() != 0) {
//                number = 0;
                } else {
                    number = (master.getRule().length() - master.getRule().replaceAll(",", "").length());
                }
                //排班编码规则数组，排班班次ID数组，排班班次描述数组
                String[] ruleArray = new String[number + 1];
                String[] codeIdArr = new String[number + 1];
                String[] descArr = new String[number + 1];
                ruleArray[number] = arrangeClass.getShiftCode();
                codeIdArr[number] = arrangeClass.getShiftId();
                descArr[number--] = arrangeClass.getShiftDesc();
                // 日期向前倒获取对应的班次编码
                for (int i = number; i >= 0; i--) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(lastDate);
                    cal.add(Calendar.DATE, i - number - 1);
                    BeanCopyUtils.copy(arrangeClass, params);
                    params.setArrShiDate(cal.getTime());
                    ArrangeClass arrange = arrangeClassService.selectArrangeClassByClass(params);
                    ruleArray[i] = arrange.getShiftCode();
                    codeIdArr[i] = arrange.getShiftId();
                    descArr[i] = arrange.getShiftDesc();
                }
                System.out.println(master.getRule());
                int ruleNumber = number + 2;
                System.out.println("排班周期个数为" + ruleNumber);
                System.out.println("新规则为" + Arrays.toString(ruleArray));
                //一天的时间戳
                long dayTc = 86400000;
                long startTc = startDate.getTime();
                long endTc = endDate.getTime();
                int day = 1;
                List<ArrangeClass> detailList = new ArrayList<>();
                for(;startTc<=endTc;startTc+=dayTc){
                    int index = (day%ruleNumber==0)? ruleNumber-1:(day%ruleNumber)-1;
                    ArrangeClass detail = new ArrangeClass();
                    detail.setArrShiDate(new Date(startTc));
                    detail.setShiftCode(ruleArray[index]);
                    detail.setShiftId(codeIdArr[index]);
                    detail.setShiftDesc(descArr[index]);
                    detailList.add(detail);
                    day++;
                }
                // 组合新排班主档数据
                ArrangeClassMaster masterData = new ArrangeClassMaster();
                BeanCopyUtils.copy(master,masterData);
                masterData.setRule(Arrays.toString(ruleArray));
                masterData.setStartDate(startDate);
                masterData.setEndDate(endDate);
                masterData.setDetailList(detailList);
                result+=insertArrangeClassMaster(masterData);
            }
        }
        personClassMasterService.scheduledPersonClass();
        return result;
    }

    /**
     * @param year 年份
     * @return 某年最后一天日期
     * @Description 获取某年最后一天日期
     * @author 266861
     * @date 2023/7/6 9:44
     **/
    public static Date getLastOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * @param year 年份
     * @return 某年第一天日期
     * @Description 获取某年第一天
     * @author 266861
     * @date 2023/7/6 9:44
     **/
    public static Date getFirstOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }
}

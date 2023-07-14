package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.BeanCopyUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.human.hd.domain.OvertimeWorkSetting;
import com.jlkj.human.hd.domain.SaveTime;
import com.jlkj.human.hd.mapper.SaveTimeMapper;
import com.jlkj.human.hd.service.IOvertimeWorkSettingService;
import com.jlkj.human.hd.service.ISaveTimeService;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.dto.HumanresourcePersonnelInfoDTO;
import com.jlkj.human.hm.service.IPersonnelService;
import com.jlkj.human.hp.domain.HumanDept;
import com.jlkj.human.hp.service.IHumanDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 存班记录Service业务层处理
 *
 * @author 266861
 * @date 2023-07-10
 */
@Service
public class SaveTimeServiceImpl implements ISaveTimeService
{
    @Autowired
    private SaveTimeMapper saveTimeMapper;
    @Autowired
    private IOvertimeWorkSettingService settingService;
    @Autowired
    private IHumanDeptService deptService;
    @Autowired
    private IPersonnelService iPersonnelService;

    /**
     * 查询存班记录
     *
     * @param id 存班记录主键
     * @return 存班记录
     */
    @Override
    public SaveTime selectSaveTimeById(String id)
    {
        return saveTimeMapper.selectSaveTimeById(id);
    }

    /**
     * 查询存班记录列表
     *
     * @param saveTime 存班记录
     * @return 存班记录
     */
    @Override
    public List<SaveTime> selectSaveTimeList(SaveTime saveTime)
    {
        return saveTimeMapper.selectSaveTimeList(saveTime);
    }

    /**
     * 新增存班记录
     *
     * @param saveTime 存班记录
     * @return 结果
     */
    @Override
    public int insertSaveTime(SaveTime saveTime)
    {
        return saveTimeMapper.insertSaveTime(saveTime);
    }

    /**
     * 修改存班记录
     *
     * @param saveTime 存班记录
     * @return 结果
     */
    @Override
    public int updateSaveTime(SaveTime saveTime)
    {
        return saveTimeMapper.updateSaveTime(saveTime);
    }

    /**
     * 批量删除存班记录
     *
     * @param ids 需要删除的存班记录主键
     * @return 结果
     */
    @Override
    public int deleteSaveTimeByIds(String[] ids)
    {
        return saveTimeMapper.deleteSaveTimeByIds(ids);
    }

    /**
     * 删除存班记录信息
     *
     * @param id 存班记录主键
     * @return 结果
     */
    @Override
    public int deleteSaveTimeById(String id)
    {
        return saveTimeMapper.deleteSaveTimeById(id);
    }

    /**
     * 加班存班时数定时清0
     *
     * @return 批次启动结果
     */
    @Override
    public int scheduledZeroing(){
        long [] quarter = {3,6,9,12};
        long lastMonth = 12;
        Date nowDate = new Date();
        long nowYear = Long.parseLong(String.format("%tY",nowDate));
        long nowMonth = Long.parseLong(String.format("%tm",nowDate));
        boolean twelveMonth  = (nowMonth - 1) == 0;
        long year = twelveMonth  ? nowYear-1:nowYear;
        long month = twelveMonth  ? 12:nowMonth-1;
        List<HumanDept> company= deptService.selectCompanyList();
        HashMap<String,OvertimeWorkSetting> workSettingmap = new HashMap<>(32);
        // 遍历查询所有公司加班补休参数
        for(HumanDept comp:company){
            OvertimeWorkSetting setting = new OvertimeWorkSetting();
            setting.setCompId(comp.getCompId());
            List<OvertimeWorkSetting> settingList= settingService.selectOvertimeWorkSettingList(setting);
            if(settingList.size()!=0){
                setting = settingService.selectOvertimeWorkSettingList(setting).get(0);
                workSettingmap.put(comp.getCompId(),setting);
            }
        }
        SaveTime empParam = new SaveTime();
        empParam.setYear(year);
        empParam.setMonth(month);
        List<SaveTime> empList =  saveTimeMapper.selectSchduleSaveTimeEmp(empParam);
        List<SaveTime> newDataList = new ArrayList<>();
        for(SaveTime emp:empList){
            if(workSettingmap.containsKey(emp.getCompId())){
                //是否月度继承
                String isTranMon = workSettingmap.get(emp.getCompId()).getIsTranMon();
                //是否季度继承
                String isTranQua = workSettingmap.get(emp.getCompId()).getIsTranQua();
                //是否年度继承
                String isTranYear = workSettingmap.get(emp.getCompId()).getIsTranYear();
                //执行月度结转
                SaveTime newMonthData = new SaveTime();
                BeanCopyUtils.copy(emp,newMonthData);
                newMonthData.setYear(year);
                newMonthData.setMonth(month);
                newMonthData = scheduleData(newMonthData,isTranMon,nowYear,nowMonth);
                newDataList.add(newMonthData);
                if(Arrays.binarySearch(quarter,month)>0){
                    long quaMonth ;
                    if(month<4){
                        quaMonth = 13;
                    }else if(month<7){
                        quaMonth = 14;
                    }else if(month<10){
                        quaMonth = 15;
                    }else{
                        quaMonth = 16;
                    }
                    //执行季度结转
                    SaveTime newQuarterData = new SaveTime();
                    BeanCopyUtils.copy(emp,newQuarterData);
                    newQuarterData.setYear(year);
                    newQuarterData.setMonth(quaMonth);
                    newQuarterData = scheduleData(newQuarterData,isTranQua,nowYear,quaMonth+1);
                    newDataList.add(newQuarterData);
                    if(month==lastMonth){
                        long yearMonth = 17;
                        //执行年度结转
                        SaveTime newYearData = new SaveTime();
                        BeanCopyUtils.copy(emp,newYearData);
                        newYearData.setYear(year);
                        newYearData.setMonth(yearMonth);
                        newYearData = scheduleData(newYearData,isTranYear,nowYear,yearMonth);
                        newDataList.add(newYearData);
                    }
                }
            }
        }
        if(newDataList.size()==0){
            return 0;
        }else{
            return saveTimeMapper.batchSaveTime(newDataList);
        }
    }

    /**
     * 员工定时存班数据设定
     *
     * @param newData 数据
     * @param isTran 是否结转上一月(季，年)数据
     * @param year 新数据年
     * @param month 新数据月
     * @return 员工数据结果
     * @author 266861
     * @Date 2023/7/12 9:35
     **/
    public SaveTime scheduleData(SaveTime newData, String isTran, long year, long month){
        String tran = "Y";
        if(tran.equals(isTran)){
            SaveTime preData = saveTimeMapper.selectSaveTimeList(newData).get(0);
            BeanCopyUtils.copy(preData,newData);
        }else{
            newData.setOvertimeDays("0");
            newData.setOvertimeHours("0");
            newData.setSaveDays("0");
            newData.setSaveHours("0");
            newData.setCompDays("0");
            newData.setCompHours("0");
        }
        newData.setCreator("定时结转");
        newData.setCreateDate(new Date());
        newData.setMonth(month);
        newData.setYear(year);
        newData.setId(IdUtils.simpleUUID());
        newData = setData(newData);
        return newData;
    }

    /**
     * 存班记录数据处理
     *
     * @param saveTime 存班记录数据
     * @return 存班记录数据
     */
    public SaveTime setData(SaveTime saveTime){
        HumanresourcePersonnelInfoDTO personnelInfoDTO = iPersonnelService.selectPersonnelInfo(saveTime.getEmpNo());
        List<Personnel> personnelList = personnelInfoDTO.getPersonnelList();
        if (personnelList.size() != 0) {
            Personnel personnel = personnelList.get(0);
            saveTime.setCompId(personnel.getCompId());
            saveTime.setEmpId(personnel.getId());
            saveTime.setEmpName(personnel.getFullName());
            if(personnel.getPostId()!=null){
                saveTime.setPostId(personnel.getPostId().toString());
            }
            saveTime.setPostName(personnel.getPostName());
        }
        return saveTime;
    }
}

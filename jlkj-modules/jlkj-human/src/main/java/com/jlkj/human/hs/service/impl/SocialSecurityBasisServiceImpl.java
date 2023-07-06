package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.hd.dto.OptionTypeDTO;
import com.jlkj.human.hm.domain.Contract;
import com.jlkj.human.hm.domain.Leave;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.dto.HumanresourcePersonnelInfoDTO;
import com.jlkj.human.hm.service.IPersonnelService;
import com.jlkj.human.hs.domain.*;
import com.jlkj.human.hs.dto.SocialSecurityBasisDTO;
import com.jlkj.human.hs.mapper.SocialSecurityBasisMapper;
import com.jlkj.human.hs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 社保公积金标准核定Service业务层处理
 *
 * @author 266861
 * @date 2023-05-30
 */
@Service
public class SocialSecurityBasisServiceImpl implements ISocialSecurityBasisService {
    @Autowired
    private SocialSecurityBasisMapper socialSecurityBasisMapper;
    @Autowired
    private ISocialSecurityBasisDetailService iSocialSecurityBasisDetailService;
    @Autowired
    protected Validator validator;
    @Autowired
    private ISalaryBasisService salaryBasisService;
    @Autowired
    private IImportErrorService iImportErrorService;
    @Autowired
    private IImportNoteService iImportNoteService;
    @Autowired
    private IProjectPayService projectPayService;
    @Autowired
    private ISocialSecurityService iSocialSecurityService;
    @Autowired
    private IPersonnelService iPersonnelService;

    /**
     * 查询社保公积金标准核定
     *
     * @param id 社保公积金标准核定主键
     * @return 社保公积金标准核定
     */
    @Override
    public SocialSecurityBasis selectSocialSecurityBasisById(String id) {
        SocialSecurityBasis socialSecurityBasis = socialSecurityBasisMapper.selectSocialSecurityBasisById(id);
        SocialSecurityBasisDetail detail = new SocialSecurityBasisDetail();
        detail.setSocialSecurityId(id);
        List<SocialSecurityBasisDetail> detailList = iSocialSecurityBasisDetailService.selectSocialSecurityBasisDetailList(detail);
        socialSecurityBasis.setDetailList(detailList);
        return socialSecurityBasis;
    }

    /**
     * 查询社保公积金标准核定列表
     *
     * @param socialSecurityBasis 社保公积金标准核定
     * @return 社保公积金标准核定
     */
    @Override
    public List<SocialSecurityBasis> selectSocialSecurityBasisList(SocialSecurityBasis socialSecurityBasis) {
        return socialSecurityBasisMapper.selectSocialSecurityBasisList(socialSecurityBasis);
    }

    /**
     * 新增社保公积金标准核定
     *
     * @param socialSecurityBasis 社保公积金标准核定
     * @return 结果
     */
    @Override
    public int insertSocialSecurityBasis(SocialSecurityBasis socialSecurityBasis) {
        List<SocialSecurityBasis> list = socialSecurityBasisMapper.selectSocialSecurityBasisByEmp(socialSecurityBasis);
        SocialSecurityBasis lastData = new SocialSecurityBasis();
        //最大生效日期与版本
        Date maxDate;
        long versionNo;
        if (list.size() == 0) {
            maxDate = DateUtils.parseDate(DateUtils.getDate());
            versionNo = 1L;
        } else {
            lastData = list.get(0);
            maxDate = lastData.getEffectDate();
            versionNo = lastData.getVersionNo() + 1;
        }
        //如果生效日期大于最大生效日期则新增
        if (socialSecurityBasis.getEffectDate().getTime() > maxDate.getTime()) {
            if (list.size() != 0) {
                SocialSecurityBasis newItem = new SocialSecurityBasis();
                newItem.setId(lastData.getId());
                newItem.setIsNew("0");
                socialSecurityBasisMapper.updateSocialSecurityBasis(newItem);
            }
            socialSecurityBasis.setVersionNo(versionNo);
            SocialSecurityBasis basis = setData(socialSecurityBasis);
            int number = socialSecurityBasisMapper.insertSocialSecurityBasis(basis);
            iSocialSecurityBasisDetailService.insertSocialSecurityBasisDetailByMain(basis);
            return number;
        } else {
            return -1;
        }
    }

    /**
     * 社保公积金标准核定主档数据处理
     *
     * @param socialSecurityBasis 社保公积金标准核定
     * @return 结果
     */
    public SocialSecurityBasis setData(SocialSecurityBasis socialSecurityBasis) {
        HumanresourcePersonnelInfoDTO personnelInfoDTO = iPersonnelService.selectPersonnelInfo(socialSecurityBasis.getEmpNo());
        List<Personnel> personnelList = personnelInfoDTO.getPersonnelList();
        List<Contract> contractList = personnelInfoDTO.getContractList();
        List<Leave> leaveList = personnelInfoDTO.getLeaveList();
        //定义转化为字符串的日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        if (personnelList.size() != 0) {
            Personnel personnel = personnelList.get(0);
            socialSecurityBasis.setEmpId(personnel.getId());
            socialSecurityBasis.setMainPostName(personnel.getPostName());
            socialSecurityBasis.setPosLevel(personnel.getPostLevelName());
            socialSecurityBasis.setBirthDate(personnel.getBirthDate());
            socialSecurityBasis.setJoinWorkDate(personnel.getJoinWorkDate());
            socialSecurityBasis.setLastExper(personnel.getLastRecordId());
            socialSecurityBasis.setNation(personnel.getNationId());
            socialSecurityBasis.setReg(personnel.getNativePlaceId());
            socialSecurityBasis.setRegFea(personnel.getNatureOfHouseholdRegistrationId());
            socialSecurityBasis.setCerNo(personnel.getCertificateNumber());
            socialSecurityBasis.setEntCorDate(dateFormat.format(personnel.getEntryDate()));
            socialSecurityBasis.setNowAddr(personnel.getHomeAddress());
            socialSecurityBasis.setMobPhone(personnel.getMyMobilePhone());
        }
        if (contractList.size() != 0) {
            Contract contract = contractList.get(0);
            socialSecurityBasis.setEmpIde(contract.getEmpIdeId());
            socialSecurityBasis.setWorkAddr(contract.getWorkAddrId());
            socialSecurityBasis.setEmpFea(contract.getEmpFeaId());
        }
        if (leaveList.size() != 0) {
            Leave leave = leaveList.get(0);
            socialSecurityBasis.setLeaveEffectDate(leave.getLeaveEffectDate());
        }
        socialSecurityBasis.setIsNew("1");
        socialSecurityBasis.setId(IdUtils.simpleUUID());
        socialSecurityBasis.setCreatorId(SecurityUtils.getUserId().toString());
        socialSecurityBasis.setCreator(SecurityUtils.getNickName());
        socialSecurityBasis.setCreatorNo(SecurityUtils.getUsername());
        socialSecurityBasis.setCreateDate(new Date());
        return socialSecurityBasis;
    }

    /**
     * 修改社保公积金标准核定
     *
     * @param socialSecurityBasis 社保公积金标准核定
     * @return 结果
     */
    @Override
    public int updateSocialSecurityBasis(SocialSecurityBasis socialSecurityBasis) {
        List<SocialSecurityBasis> list = socialSecurityBasisMapper.selectSocialSecurityBasisByEmp(socialSecurityBasis);
        SocialSecurityBasis lastData;
        SocialSecurityBasis lastData2;
        if (socialSecurityBasis.getId()!= null) {
            lastData = list.get(0);
            if ((socialSecurityBasis.getEffectDate().getTime() >= lastData.getEffectDate().getTime())) {
                SocialSecurityBasis preData = socialSecurityBasisMapper.selectSocialSecurityBasisById(socialSecurityBasis.getId());
                //如果生效日期不变则修改，反之新增一版本
                if (socialSecurityBasis.getEffectDate().getTime() == preData.getEffectDate().getTime()) {
                    socialSecurityBasis.setCreatorId(SecurityUtils.getUserId().toString());
                    socialSecurityBasis.setCreator(SecurityUtils.getNickName());
                    socialSecurityBasis.setCreatorNo(SecurityUtils.getUsername());
                    socialSecurityBasis.setCreateDate(new Date());
                    socialSecurityBasisMapper.updateSocialSecurityBasis(socialSecurityBasis);
                    return iSocialSecurityBasisDetailService.insertSocialSecurityBasisDetailByMain(socialSecurityBasis);
                } else {
                    return insertSocialSecurityBasis(socialSecurityBasis);
                }
            } else {
                return -1;
            }
        }else{
            Date nowDate = DateUtils.parseDate(DateUtils.getDate());
            if(list.size()==0){
                return insertSocialSecurityBasis(socialSecurityBasis);
            }else if (list.size()==1){
                lastData = list.get(0);
                if(nowDate.getTime()>=lastData.getEffectDate().getTime()){
                    return insertSocialSecurityBasis(socialSecurityBasis);
                }else{
                    if(socialSecurityBasis.getEffectDate().getTime()>nowDate.getTime()){
                        SocialSecurityBasis basis = setData(socialSecurityBasis);
                        basis.setId(lastData.getId());
                        basis.setVersionNo(lastData.getVersionNo());
                        socialSecurityBasisMapper.updateSocialSecurityBasis(basis);
                        iSocialSecurityBasisDetailService.insertSocialSecurityBasisDetailByMain(basis);
                        return iSocialSecurityBasisDetailService.insertSocialSecurityBasisDetailByMain(basis);
                    }else {
                        return -1;
                    }
                }
            }else{
                lastData = list.get(0);
                lastData2 = list.get(1);
                if(nowDate.getTime()>=lastData.getEffectDate().getTime()){
                    return insertSocialSecurityBasis(socialSecurityBasis);
                }else{
                    if(socialSecurityBasis.getEffectDate().getTime()>lastData2.getEffectDate().getTime()){
                        SocialSecurityBasis basis = setData(socialSecurityBasis);
                        basis.setId(lastData.getId());
                        basis.setVersionNo(lastData.getVersionNo());
                        socialSecurityBasisMapper.updateSocialSecurityBasis(basis);
                        return iSocialSecurityBasisDetailService.insertSocialSecurityBasisDetailByMain(basis);
                    }else {
                        return -1;
                    }
                }
            }

        }
    }

    /**
     * 批量删除社保公积金标准核定
     *
     * @param ids 需要删除的社保公积金标准核定主键
     * @return 结果
     */
    @Override
    public int deleteSocialSecurityBasisByIds(String[] ids) {
        return socialSecurityBasisMapper.deleteSocialSecurityBasisByIds(ids);
    }

    /**
     * 删除社保公积金标准核定信息
     *
     * @param id 社保公积金标准核定主键
     * @return 结果
     */
    @Override
    public int deleteSocialSecurityBasisById(String id) {
        return socialSecurityBasisMapper.deleteSocialSecurityBasisById(id);
    }


    /**
     * 导入社保公积金数据
     *
     * @param dtos       社保公积金数据列表
     * @param isUpdateSupport 是否新增
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SocialSecurityBasisDTO> dtos, Boolean isUpdateSupport, String operName, String compId) {
        if (StringUtils.isNull(dtos) || dtos.size() == 0) {
            throw new ServiceException("导入社保公积金数据不能为空！");
        }
        ImportNote note = new ImportNote();
        Calendar calendar = Calendar.getInstance();
        String randomId = IdUtils.simpleUUID();
        note.setId(randomId);
        note.setCompId(compId);
        note.setPayType("2");
        note.setStatus("1");
        note.setMonth(String.valueOf(calendar.get(Calendar.MONTH) + 1));
        int noteResult = iImportNoteService.insertImportNote(note);
        if (noteResult < 0) {
            throw new ServiceException("正在导入！请稍后再试");
        }
        //社保公积金核定原因
        String insureRea = "InsureRea";
        //缴费地区类型
        String payAreaType = "PayAreaType";
        //缴费地
        String socialSecurity = "SocialSecurity";
        OptionTypeDTO optionType = new OptionTypeDTO();
        optionType.setOptionsType(Arrays.asList(insureRea, payAreaType, socialSecurity));
        HashMap<String, List<BasisOptionsDTO>> map = salaryBasisService.getBasisOptions(optionType);
        HashMap<String, String[]> map1 = new HashMap<>(32);
        HashMap<String, String[]> map2 = new HashMap<>(32);
        HashMap<String, String[]> map3 = new HashMap<>(32);
        //选单编码ID值转换
        for (BasisOptionsDTO optionsDTO : map.get(insureRea)) {
            String[] info = {optionsDTO.getId().toString(), optionsDTO.getDicName()};
            map1.put(optionsDTO.getDicNo(), info);
        }
        for (BasisOptionsDTO optionsDTO : map.get(payAreaType)) {
            String[] info = {optionsDTO.getId().toString(), optionsDTO.getDicName()};
            map2.put(optionsDTO.getDicNo(), info);
        }
        for (BasisOptionsDTO optionsDTO : map.get(socialSecurity)) {
            String[] info = {optionsDTO.getId().toString(), optionsDTO.getDicName()};
            map3.put(optionsDTO.getDicNo(), info);
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        boolean firstError = true;
        String errorMsg = null;
        for (SocialSecurityBasisDTO basisDTO : dtos) {
            ImportError error = new ImportError();
            error.setPayType("2");
            error.setCompId(compId);
            error.setEmpNo(basisDTO.getEmpNo());
            BeanValidators.validateWithException(validator, basisDTO);
            SocialSecurityBasis basis = new SocialSecurityBasis();
            basis.setCompId(compId);
            basis.setEmpNo(basisDTO.getEmpNo());
            errorMsg = "社保核定原因编码不存在";
            try {
                basis.setInsureReaId(map1.get(basisDTO.getInsureReaCode())[0]);
                basis.setInsureRea(map1.get(basisDTO.getInsureReaCode())[1]);
                errorMsg = "社保缴费地类别编码不存在";
                basis.setPayAreaTypeId(map2.get(basisDTO.getPayAreaTypeCode())[0]);
                basis.setPayAreaType(map2.get(basisDTO.getPayAreaTypeCode())[1]);
                basis.setEffectDate(basisDTO.getEffectDate());
                basis.setInsureNo(basisDTO.getInsureNo());
                basis.setMedicalInsuranceNo(basisDTO.getMedicalInsuranceNo());
                basis.setCompInsureBookNo(basisDTO.getCompInsureBookNo());
                basis.setHouseAccount(basisDTO.getHouseAccount());
                List<SocialSecurityBasisDetail> detailList = new ArrayList<>();
                boolean stop = false;
                int excelNumber= 8;
                for (int i = 1; i < excelNumber; i++) {
                    if (!stop) {
                        SocialSecurityBasisDetail detail = new SocialSecurityBasisDetail();
                        String payProCode = getValue(basisDTO, "PayProCode" + i);
                        String thrIdSta = getValue(basisDTO, "ThrIdSta" + i);
                        String fivSta = getValue(basisDTO, "FivSta" + i);
                        String sevIdSta = getValue(basisDTO, "SevIdSta" + i);
                        errorMsg="缴费地编码不存在";
                        if (!StringUtils.isNull(sevIdSta) && !sevIdSta.isEmpty()) {
                            sevIdSta = map3.get(getValue(basisDTO, "SevIdSta" + i))[0];
                        }
                        if (!StringUtils.isNull(payProCode) && !payProCode.isEmpty()) {
                            detail.setPayProCode(payProCode);
                            ProjectPay projectPay = new ProjectPay();
                            projectPay.setPayProCode(payProCode);
                            projectPay.setPayType("2");
                            errorMsg="社保项目编码不存在";
                            projectPay = projectPayService.selectProjectPayByCode(projectPay);
                            detail.setPayProId(projectPay.getId().toString());
                            detail.setPayProName(projectPay.getPayProName());
                            List<SocialSecurity> socialList = iSocialSecurityService.selectSocialSecurityByempNo(basis.getCompId());
                            for (SocialSecurity item : socialList) {
                                if (item.getPayProCode().equals(payProCode)) {
                                    detail.setNinSta(item.getBaseLl());
                                    detail.setEleSta(item.getBaseUcl());
                                }
                            }
                            detail.setThrIdSta(thrIdSta);
                            detail.setFivSta(fivSta);
                            detail.setSevIdSta(sevIdSta);
                            detail.setTwoTitle("是否缴纳");
                            detail.setThrIsLov("0");
                            detail.setFourTitle(detail.getPayProName() + "基数");
                            detail.setFivIsLov("0");
                            detail.setSixTitle("缴费地");
                            detail.setSevIsLov("1");
                            detail.setEigTitle("该险种最低基数");
                            detail.setNinIsLov("0");
                            detail.setTenTitle("该险种最高基数");
                            detail.setEleIsLov("0");
                            detailList.add(detail);
                        } else {
                            stop = true;
                        }
                    }
                }
                basis.setDetailList(detailList);
                if (isUpdateSupport) {
                    int result = insertSocialSecurityBasis(basis);
                    if (result < 0) {
                        errorMsg = "生效日期不满足要求，新增失败";
                        throw new Exception(errorMsg);
                    }else{
                        successNum++;
                        successMsg.append("<br/>").append(successNum).append("、员工号 ").append(basisDTO.getEmpNo()).append(" 导入成功");
                    }
                }else{
                    int result = updateSocialSecurityBasis(basis);
                    if (result < 0) {
                        errorMsg = "生效日期不满足要求，更新失败";
                        throw new Exception(errorMsg);
                    }else{
                        successNum++;
                        successMsg.append("<br/>").append(successNum).append("、员工号 ").append(basisDTO.getEmpNo()).append(" 导入成功");
                    }
                }
            } catch (Exception e) {
                if (firstError) {
                    iImportErrorService.deleteImportError(error);
                    firstError = false;
                }
                error.setMark(errorMsg);
                iImportErrorService.insertImportError(error);
                failureNum++;
                String msg = "<br/>" + failureNum + "、员工号 " + basisDTO.getEmpNo() + ":" + errorMsg;
                failureMsg.append(msg);
            }

        }
        if (failureNum > 0) {
            ImportNote newNote = new ImportNote();
            newNote.setId(randomId);
            newNote.setStatus("2");
            iImportNoteService.updateImportNote(newNote);
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            ImportNote newNote = new ImportNote();
            newNote.setId(randomId);
            newNote.setStatus("3");
            iImportNoteService.updateImportNote(newNote);
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 动态调用实体的get方法(注意返回值)
     *
     * @param dto  实体
     * @param name 动态拼接字段
     * @throws Exception
     */
    private static String getValue(Object dto, String name) {
        try {
            Method m = (Method) dto.getClass().getMethod(("get" + name));
            // 调用getter方法获取属性值
            String val = null;
            try {
                val = (String) m.invoke(dto);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return val;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
    * @Description 社保核定基数年底核定下年初最新数据
    * @param
    * @return 新增数据结果
    * @author 266861
    * @date 2023/7/4 9:40
    **/
    @Override
    public int setNewYearSocialSecurity(){
        int result = 0;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR)+1;
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        //来年生效日期
        Date effectDate = calendar.getTime();
        List<SocialSecurityBasis> basisList = socialSecurityBasisMapper.selectSocialSecurityBasisList(new SocialSecurityBasis());
        for(SocialSecurityBasis basis : basisList){
            //修改生效日期
            basis.setEffectDate(effectDate);
            SocialSecurityBasisDetail detail = new SocialSecurityBasisDetail();
            detail.setSocialSecurityId(basis.getId());
            List<SocialSecurityBasisDetail> detailList = iSocialSecurityBasisDetailService.selectSocialSecurityBasisDetailList(detail);
            //配置明细列表
            basis.setDetailList(detailList);
            insertSocialSecurityBasis(basis);
            result++;
        }
        return result;
    }

}

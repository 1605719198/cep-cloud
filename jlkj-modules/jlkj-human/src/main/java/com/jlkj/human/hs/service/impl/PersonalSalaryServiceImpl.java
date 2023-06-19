package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.hd.dto.OptionTypeDTO;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.service.IPersonnelService;
import com.jlkj.human.hp.service.ISysDeptService;
import com.jlkj.human.hs.domain.*;
import com.jlkj.human.hs.dto.PersonalSalaryBankDTO;
import com.jlkj.human.hs.dto.PersonalSalaryDTO;
import com.jlkj.human.hs.dto.PersonalSalaryDetailDTO;
import com.jlkj.human.hs.mapper.PersonalSalaryMapper;
import com.jlkj.human.hs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 薪资核定Service业务层处理
 *
 * @author 266861
 * @date 2023-06-01
 */
@Service
public class PersonalSalaryServiceImpl implements IPersonalSalaryService {
    @Autowired
    private PersonalSalaryMapper personalSalaryMapper;
    @Autowired
    private IPersonnelService iPersonnelService;
    @Autowired
    protected Validator validator;
    @Autowired
    private ISalaryBasisService salaryBasisService;
    @Autowired
    private IPersonalSalaryDetailService detailService;
    @Autowired
    private IImportErrorService iImportErrorService;
    @Autowired
    private IImportNoteService iImportNoteService;
    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询薪资核定
     *
     * @param id 薪资核定主键
     * @return 薪资核定
     */
    @Override
    public PersonalSalary selectPersonalSalaryById(String id) {
        PersonalSalary personalSalary = personalSalaryMapper.selectPersonalSalaryById(id);
        PersonalSalaryDetail detail = new PersonalSalaryDetail();
        detail.setSalaryId(id);
        List<PersonalSalaryDetail> list = detailService.selectPersonalSalaryDetailList(detail);
        personalSalary.setDetailList(list);
        return personalSalary;
    }

    /**
     * 查询薪资核定列表
     *
     * @param personalSalary 薪资核定
     * @return 薪资核定
     */
    @Override
    public List<PersonalSalary> selectPersonalSalaryList(PersonalSalary personalSalary) {
        return personalSalaryMapper.selectPersonalSalaryList(personalSalary);
    }

    /**
     * 新增薪资核定
     *
     * @param personalSalary 薪资核定
     * @return 结果
     */
    @Override
    public int insertPersonalSalary(PersonalSalary personalSalary) {
        List<PersonalSalary> checkList = personalSalaryMapper.selectPersonalSalaryChecked(personalSalary);
        //查到未定薪列表数据，禁止新增
        if(checkList.size()!=0){
            return 0;
        }
        List<PersonalSalary> list = personalSalaryMapper.selectPersonalSalaryByEmp(personalSalary);
        PersonalSalary lastData = new PersonalSalary();
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
        if (personalSalary.getEffectDate().getTime() > maxDate.getTime()) {
            if (list.size() != 0) {
                PersonalSalary newItem = new PersonalSalary();
                newItem.setId(lastData.getId());
                newItem.setIsNew("0");
                personalSalaryMapper.updatePersonalSalary(newItem);
            }
            personalSalary.setVersionNo(versionNo);
            PersonalSalary salary = setData(personalSalary);
            int number = personalSalaryMapper.insertPersonalSalary(salary);
            detailService.insertPersonalSalaryDetailByMain(salary);
            return number;
        } else {
            return -1;
        }
    }

    /**
     * 薪资核定主档数据处理
     *
     * @param personalSalary 薪资核定
     * @return 结果
     */
    @SuppressWarnings("unchecked")
    public PersonalSalary setData(PersonalSalary personalSalary) {
        Object obj = iPersonnelService.selectPersonnelInfo(personalSalary.getEmpNo());
        AjaxResult result = (AjaxResult) obj;
        Object result2 = result.get("data");
        Map<String, Object> resultMap = (Map<String, Object>) result2;
        System.out.println(resultMap);
        List<Personnel> personnelList = (List<Personnel>) resultMap.get("personnelList");
        //定义转化为字符串的日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        if (personnelList.size() != 0) {
            Personnel personnel = personnelList.get(0);
            personalSalary.setEmpId(personnel.getId());
            personalSalary.setMainPostName(personnel.getPostName());
            personalSalary.setPostLevel(personnel.getPostLevelName());
        }
        personalSalary.setCompName(deptService.queryCompById(personalSalary.getCompId()).getCompanyName());
        personalSalary.setIsCheck("0");
        personalSalary.setIsNew("1");
        personalSalary.setId(UUID.randomUUID().toString().substring(0, 32));
        personalSalary.setCreatorId(SecurityUtils.getUserId().toString());
        personalSalary.setCreator(SecurityUtils.getNickName());
        personalSalary.setCreatorNo(SecurityUtils.getUsername());
        personalSalary.setCreateDate(new Date());
        return personalSalary;
    }

    /**
     * 修改薪资核定
     *
     * @param personalSalary 薪资核定
     * @param setEndPayDate  是否修改止薪日期
     * @return 结果
     */
    @Override
    public int updatePersonalSalary(PersonalSalary personalSalary, Boolean setEndPayDate) {
        if (setEndPayDate) {
            PersonalSalary salary = new PersonalSalary();
            salary.setEndPayDate(personalSalary.getEndPayDate());
            salary.setId(personalSalary.getId());
            return personalSalaryMapper.updatePersonalSalary(salary);
        } else {
            List<PersonalSalary> list = personalSalaryMapper.selectPersonalSalaryByEmp(personalSalary);
            PersonalSalary lastData, lastData2;
            if (personalSalary.getId() != null) {
                lastData = list.get(0);
                if ((personalSalary.getEffectDate().getTime() >= lastData.getEffectDate().getTime())) {
                    PersonalSalary preData = personalSalaryMapper.selectPersonalSalaryById(personalSalary.getId());
                    //如果生效日期不变则修改，反之新增一版本
                    if (personalSalary.getEffectDate().getTime() == preData.getEffectDate().getTime()) {
                        personalSalary.setCreatorId(SecurityUtils.getUserId().toString());
                        personalSalary.setCreator(SecurityUtils.getNickName());
                        personalSalary.setCreatorNo(SecurityUtils.getUsername());
                        personalSalary.setCreateDate(new Date());
                        personalSalaryMapper.updatePersonalSalary(personalSalary);
                        detailService.insertPersonalSalaryDetailByMain(personalSalary);
                        return 1;
                    } else {
                        return insertPersonalSalary(personalSalary);
                    }
                } else {
                    return -1;
                }
            } else {
                Date nowDate = DateUtils.parseDate(DateUtils.getDate());
                if (list.size() == 0) {
                    return insertPersonalSalary(personalSalary);
                } else if (list.size() == 1) {
                    lastData = list.get(0);
                    if (nowDate.getTime() >= lastData.getEffectDate().getTime()) {
                        return insertPersonalSalary(personalSalary);
                    } else {
                        if (personalSalary.getEffectDate().getTime() > nowDate.getTime()) {
                            PersonalSalary salary = setData(personalSalary);
                            salary.setId(lastData.getId());
                            salary.setVersionNo(lastData.getVersionNo());
                            personalSalaryMapper.updatePersonalSalary(personalSalary);
                            detailService.insertPersonalSalaryDetailByMain(salary);
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                } else {
                    lastData = list.get(0);
                    lastData2 = list.get(1);
                    if (nowDate.getTime() >= lastData.getEffectDate().getTime()) {
                        return insertPersonalSalary(personalSalary);
                    } else {
                        if (personalSalary.getEffectDate().getTime() > lastData2.getEffectDate().getTime()) {
                            PersonalSalary salary = setData(personalSalary);
                            salary.setId(lastData.getId());
                            salary.setVersionNo(lastData.getVersionNo());
                            personalSalaryMapper.updatePersonalSalary(salary);
                            detailService.insertPersonalSalaryDetailByMain(salary);
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
            }
        }

    }

    /**
     * 批量删除薪资核定
     *
     * @param ids 需要删除的薪资核定主键
     * @return 结果
     */
    @Override
    public int deletePersonalSalaryByIds(String[] ids) {
        return personalSalaryMapper.deletePersonalSalaryByIds(ids);
    }

    /**
     * 删除薪资核定信息
     *
     * @param id 薪资核定主键
     * @return 结果
     */
    @Override
    public int deletePersonalSalaryById(String id) {
        return personalSalaryMapper.deletePersonalSalaryById(id);
    }

    /**
     * 查询公司薪资支付银行编码，名称ID
     *
     * @param compId 公司别
     * @return 结果
     */
    @Override
    public List<BasisOptionsDTO> getSalaryBank(String compId) {
        return personalSalaryMapper.getSalaryBank(compId);
    }

    /**
     * 查询公司薪资薪酬项目
     *
     * @param compId 公司别
     * @return 结果
     */
    @Override
    public List<PersonalSalaryDetailDTO> getPayFormation(String compId) {
        List<PersonalSalaryDetailDTO> list = personalSalaryMapper.getPayFormation(compId);
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(32);
        for(PersonalSalaryDetailDTO dto:list ){
            Integer level = Integer.parseInt(dto.getParents());
            if(map.containsKey(level)){
                map.replace(level,map.get(level)+1);
                for(Integer i :map.keySet()){
                    if(i>level){
                        map.remove(i);
                    }
                }
                dto.setParents(getParents(map));
            }else{
                map.put(level,1);
                dto.setParents(getParents(map));
            }
        }
        System.out.println(list);
        return list;
    }
    /**
     * 重新获取祖级列表
     *
     * @param map 祖级列表数据map
     * @return 结果
     */
    public String getParents(HashMap<Integer, Integer> map){
        StringBuilder results = new StringBuilder();
        for (Integer i : map.keySet()) {
            if(map.get(i)!=0){
                if(results.length()==0){
                    results.append(map.get(i));
                }else{
                    results.append(".").append(map.get(i));
                }
            }
        }
        return results.toString();
    }

    public static void main(String[] args) {
        System.out.println("hello");
    }


    /**
     * 导入薪资核定数据
     *
     * @param dtos            薪资核定数据列表
     * @param isUpdateSupport 是否新增
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importSalary(List<PersonalSalaryDTO> dtos, Boolean isUpdateSupport, String operName, String compId) {
        if (StringUtils.isNull(dtos) || dtos.size() == 0) {
            throw new ServiceException("导入薪资核定数据不能为空！");
        }
        ImportNote note = new ImportNote();
        Calendar calendar = Calendar.getInstance();
        String randomId = UUID.randomUUID().toString().substring(0, 32);
        note.setId(randomId);
        note.setCompId(compId);
        note.setPayType("1");
        note.setStatus("1");
        note.setMonth(String.valueOf(calendar.get(Calendar.MONTH) + 1));
        int noteResult = iImportNoteService.insertImportNote(note);
        if (noteResult < 0) {
            throw new ServiceException("正在导入！请稍后再试");
        }
        //薪资核定原因
        String payCheckRea = "PayCheckRea";
        OptionTypeDTO optionType = new OptionTypeDTO();
        optionType.setOptionsType(Arrays.asList(payCheckRea));
        HashMap<String, List<BasisOptionsDTO>> map = salaryBasisService.getBasisOptions(optionType);
        HashMap<String, String[]> map1 = new HashMap<>(32);
        //选单编码ID值转换
        for (BasisOptionsDTO optionsDTO : map.get(payCheckRea)) {
            String[] info = {optionsDTO.getId().toString(), optionsDTO.getDicName()};
            map1.put(optionsDTO.getDicNo(), info);
        }
        //公司银行列表
        List<BasisOptionsDTO> bankList = personalSalaryMapper.getSalaryBank(compId);
        HashMap<String, String[]> map2 = new HashMap<>(32);
        //公司银行编码map
        for (BasisOptionsDTO bank : bankList) {
            String[] info = {bank.getUuid(), bank.getDicName()};
            map2.put(bank.getDicNo(), info);
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        //是否为本次导入第一条错误数据
        boolean firstError = true;
        //错误信息
        String errorMsg;
        for (PersonalSalaryDTO salaryDTO : dtos) {
            ImportError error = new ImportError();
            error.setPayType("1");
            error.setCompId(compId);
            error.setEmpNo(salaryDTO.getEmpNo());
            BeanValidators.validateWithException(validator, salaryDTO);
            PersonalSalary salary = new PersonalSalary();
            salary.setCompId(compId);
            salary.setEmpNo(salaryDTO.getEmpNo());
            errorMsg = "薪资核定原因编码不存在";
            try {
                salary.setPayCheckReaId(map1.get(salaryDTO.getPayCheckReaCode())[0]);
                salary.setPayCheckRea(map1.get(salaryDTO.getPayCheckReaCode())[1]);
                salary.setEffectDate(salaryDTO.getEffectDate());
                salary.setPayCheckDesc(salaryDTO.getPayCheckDesc());
                errorMsg = "工资支付银行编码不存在";
                salary.setSalBankId(map2.get(salaryDTO.getSalBankCode())[0]);
                salary.setSalBank(map2.get(salaryDTO.getSalBankCode())[1]);
                salary.setSalBankNo(salaryDTO.getSalBankNo());
                errorMsg = "奖金支付银行编码不存在";
                salary.setBonBankId(map2.get(salaryDTO.getSalBankCode())[0]);
                salary.setBonBank(map2.get(salaryDTO.getSalBankCode())[1]);
                salary.setBonBankNo(salaryDTO.getBonBankNo());
                salary.setIstax(salaryDTO.getIstax());

                List<PersonalSalaryDetail> detailList = new ArrayList<>();
                boolean stop = false;
//                int excelNumber= 8;
//                for (int i = 1; i < excelNumber; i++) {
//                    if (!stop) {
//                        SocialSecurityBasisDetail detail = new SocialSecurityBasisDetail();
//                        String payProCode = getValue(basisDTO, "PayProCode" + i);
//                        String thrIdSta = getValue(basisDTO, "ThrIdSta" + i);
//                        String fivSta = getValue(basisDTO, "FivSta" + i);
//                        String sevIdSta = getValue(basisDTO, "SevIdSta" + i);
//                        errorMsg="缴费地编码不存在";
//                        if (!StringUtils.isNull(sevIdSta) && !sevIdSta.isEmpty()) {
//                            sevIdSta = map3.get(getValue(basisDTO, "SevIdSta" + i))[0];
//                        }
//                        if (!StringUtils.isNull(payProCode) && !payProCode.isEmpty()) {
//                            detail.setPayProCode(payProCode);
//                            ProjectPay projectPay = new ProjectPay();
//                            projectPay.setPayProCode(payProCode);
//                            projectPay.setPayType("2");
//                            errorMsg="社保项目编码不存在";
//                            projectPay = projectPayService.selectProjectPayByCode(projectPay);
//                            detail.setPayProId(projectPay.getId().toString());
//                            detail.setPayProName(projectPay.getPayProName());
//                            List<SocialSecurity> socialList = iSocialSecurityService.selectSocialSecurityByempNo(basis.getCompId());
//                            for (SocialSecurity item : socialList) {
//                                if (item.getPayProCode().equals(payProCode)) {
//                                    detail.setNinSta(item.getBaseLl());
//                                    detail.setEleSta(item.getBaseUcl());
//                                }
//                            }
//                            detail.setThrIdSta(thrIdSta);
//                            detail.setFivSta(fivSta);
//                            detail.setSevIdSta(sevIdSta);
//                            detail.setTwoTitle("是否缴纳");
//                            detail.setThrIsLov("0");
//                            detail.setFourTitle(detail.getPayProName() + "基数");
//                            detail.setFivIsLov("0");
//                            detail.setSixTitle("缴费地");
//                            detail.setSevIsLov("1");
//                            detail.setEigTitle("该险种最低基数");
//                            detail.setNinIsLov("0");
//                            detail.setTenTitle("该险种最高基数");
//                            detail.setEleIsLov("0");
//                            detailList.add(detail);
//                        } else {
//                            stop = true;
//                        }
//                    }
//                }
                salary.setDetailList(detailList);
                if (isUpdateSupport) {
                    int result = insertPersonalSalary(salary);
                    if (result < 0) {
                        errorMsg = "生效日期不满足要求，新增失败";
                        throw new Exception(errorMsg);
                    } else {
                        successNum++;
                        successMsg.append("<br/>").append(successNum).append("、员工工号 ").append(salaryDTO.getEmpNo()).append(" 导入成功");
                    }
                } else {
                    int result = updatePersonalSalary(salary, false);
                    if (result < 0) {
                        errorMsg = "生效日期不满足要求，更新失败";
                        throw new Exception(errorMsg);
                    } else {
                        successNum++;
                        successMsg.append("<br/>").append(successNum).append("、员工号 ").append(salaryDTO.getEmpNo()).append(" 导入成功");
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
                String msg = "<br/>" + failureNum + "、员工号 " + salaryDTO.getEmpNo() + ":" + errorMsg;
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
        } catch (NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导入银行账号数据
     *
     * @param dtos            银行账号资料列表
     * @param isUpdateSupport 是否只修改最新一笔
     * @param compId          公司别
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importBankNo(List<PersonalSalaryBankDTO> dtos, Boolean isUpdateSupport, String operName, String compId) {
        if (StringUtils.isNull(dtos) || dtos.size() == 0) {
            throw new ServiceException("导入银行账号数据不能为空！");
        }
        ImportNote note = new ImportNote();
        Calendar calendar = Calendar.getInstance();
        String randomId = UUID.randomUUID().toString().substring(0, 32);
        note.setId(randomId);
        note.setCompId(compId);
        note.setPayType("3");
        note.setStatus("1");
        note.setMonth(String.valueOf(calendar.get(Calendar.MONTH) + 1));
        int noteResult = iImportNoteService.insertImportNote(note);
        if (noteResult < 0) {
            throw new ServiceException("正在导入！请稍后再试");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        //是否为本次导入第一条错误数据
        boolean firstError = true;
        //错误信息
        String errorMsg = null;
        for (PersonalSalaryBankDTO salaryBank : dtos) {
            ImportError error = new ImportError();
            error.setPayType("3");
            error.setCompId(compId);
            error.setEmpNo(salaryBank.getEmpNo());
            try {
                PersonalSalary emp = new PersonalSalary();
                emp.setEmpNo(salaryBank.getEmpNo());
                //查找用户薪资核定数据
                List<PersonalSalary> empSalary = personalSalaryMapper.selectPersonalSalaryList(emp);
                if (empSalary.size() == 0) {
                    errorMsg = "该员工无薪资核定数据";
                    throw new Exception(errorMsg);
                }
                BeanValidators.validateWithException(validator, salaryBank);
                int result = 0;
                //只修改最新一笔数据
                if (isUpdateSupport) {
                    result = updatePersonalSalaryBank(compId, empSalary.get(0).getId(), salaryBank);

                } else {
                    for (PersonalSalary salary : empSalary) {
                        if (result >= 0) {
                            result = updatePersonalSalaryBank(compId, salary.getId(), salaryBank);
                        }
                    }
                }
                if (result == 1) {
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、员工号 ").append(salaryBank.getEmpNo()).append(" 更新成功");
                } else if (result == -1) {
                    errorMsg = "工资支付银行编码不存在";
                    throw new Exception(errorMsg);
                } else {
                    errorMsg = "奖金支付银行编码不存在";
                    throw new Exception(errorMsg);
                }
            } catch (Exception e) {
                if (firstError) {
                    iImportErrorService.deleteImportError(error);
                    firstError = false;
                }
                error.setMark(errorMsg);
                iImportErrorService.insertImportError(error);
                failureNum++;
                String msg = "<br/>" + failureNum + "、员工号 " + salaryBank.getEmpNo() + ":" + errorMsg;
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
     * 修改薪资核定银行账号数据
     *
     * @param id      要改变的数据的id
     * @param bankDTO 银行账号数据
     * @param compId  公司别
     * @return 结果
     */
    @Override
    public int updatePersonalSalaryBank(String compId, String id, PersonalSalaryBankDTO bankDTO) {
        int result = 0;
        //公司银行列表
        List<BasisOptionsDTO> bankList = personalSalaryMapper.getSalaryBank(compId);
        HashMap<String, String[]> map = new HashMap<>(32);
        //公司银行编码map
        for (BasisOptionsDTO bank : bankList) {
            String[] info = {bank.getUuid(), bank.getDicName()};
            map.put(bank.getDicNo(), info);
        }
        PersonalSalary salary = new PersonalSalary();
        try {
            salary.setSalBankId(map.get(bankDTO.getSalBankCode())[0]);
            salary.setSalBank(map.get(bankDTO.getSalBankCode())[1]);
            salary.setSalBankNo(bankDTO.getSalBankNo());
            salary.setBonBankId(map.get(bankDTO.getBonBankCode())[0]);
            salary.setBonBank(map.get(bankDTO.getSalBankCode())[1]);
            salary.setBonBankNo(bankDTO.getBonBankNo());
            salary.setId(id);
            salary.setCreatorId(SecurityUtils.getUserId().toString());
            salary.setCreator(SecurityUtils.getNickName());
            salary.setCreatorNo(SecurityUtils.getUsername());
            salary.setCreateDate(new Date());
            result = personalSalaryMapper.updatePersonalSalary(salary);
        } catch (Exception e) {
            if (StringUtils.isEmpty(salary.getSalBankId())) {
                //工资支付银行编码错误
                result = -1;
            } else {
                //奖金支付银行编码错误
                result = -2;
            }
        }
        return result;
    }

}

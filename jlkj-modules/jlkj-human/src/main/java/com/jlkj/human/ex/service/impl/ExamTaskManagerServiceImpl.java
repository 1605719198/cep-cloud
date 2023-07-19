package com.jlkj.human.ex.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.core.domain.R;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.file.FileTypeUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.ex.domain.ExamTaskManager;
import com.jlkj.human.ex.domain.ExamTaskPicture;
import com.jlkj.human.ex.mapper.ExamTaskManagerMapper;
import com.jlkj.human.ex.mapper.ExamTaskPictureMapper;
import com.jlkj.human.ex.service.IExamTaskGroupService;
import com.jlkj.human.ex.service.IExamTaskManagerService;
import com.jlkj.human.ex.service.IExamTaskPersonService;
import com.jlkj.human.ex.service.IExamTaskQuestionsService;
import com.jlkj.system.api.RemoteFileService;
import com.jlkj.system.api.domain.SysFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建考试Service业务层处理
 *
 * @author qnsdt
 * @date 2023-01-06
 */
@Service
public class ExamTaskManagerServiceImpl implements IExamTaskManagerService {
    @Resource
    private ExamTaskManagerMapper examTaskManagerMapper;

    @Resource
    private IExamTaskGroupService examTaskGroupService;

    @Resource
    private IExamTaskQuestionsService examTaskQuestionsService;

    @Resource
    private IExamTaskPersonService examTaskPersonService;

    @Resource
    private RemoteFileService remoteFileService;

    @Resource
    private ExamTaskPictureMapper examTaskPictureMapper;

    /**
     * 查询创建考试
     *
     * @param examCode 创建考试ID
     * @return 创建考试
     */
    @Override
    public ExamTaskManager selectExamTaskManagerById(String examCode) {
        return examTaskManagerMapper.selectExamTaskManagerById(examCode);
    }

    /**
     * 查询创建考试列表
     *
     * @param examTaskManager 创建考试
     * @return 创建考试
     */
    @Override
    public List<ExamTaskManager> selectExamTaskManagerList(ExamTaskManager examTaskManager) {
        return examTaskManagerMapper.selectExamTaskManagerList(examTaskManager);
    }

    /**
     * 新增创建考试
     *
     * @param examTaskManager 创建考试
     * @return 结果
     */
    @Override
    public int insertExamTaskManager(ExamTaskManager examTaskManager) {
        examTaskManager.setCreateTime(DateUtils.getNowDate());
        return examTaskManagerMapper.insertExamTaskManager(examTaskManager);
    }

    /**
     * 修改创建考试
     *
     * @param examTaskManager 创建考试
     * @return 结果
     */
    @Override
    public int updateExamTaskManager(ExamTaskManager examTaskManager) {
        return examTaskManagerMapper.updateExamTaskManager(examTaskManager);
    }

    /**
     * 批量删除创建考试
     *
     * @param examCodes 需要删除的创建考试ID
     * @return 结果
     */
    @Override
    public int deleteExamTaskManagerByIds(String[] examCodes) {
        // 删除题目
        examTaskQuestionsService.deleteExamTaskQuestionsByIds(examCodes);
        // 删除人员分组
        examTaskGroupService.deleteExamTaskGroupByIds(examCodes);
        // 删除人员
        examTaskPersonService.deleteExamTaskPersonByIds(examCodes);

        return examTaskManagerMapper.deleteExamTaskManagerByIds(examCodes);
    }

    /**
     * 删除创建考试信息
     *
     * @param examCode 创建考试ID
     * @return 结果
     */
    @Override
    public int deleteExamTaskManagerById(String examCode) {
        return examTaskManagerMapper.deleteExamTaskManagerById(examCode);
    }

    /**
     * 考试题图片上传
     *
     * @param examCodes 考试题代码
     * @param file      图片文件
     * @return com.alibaba.fastjson.JSONObject 图片URL
     * @Author: 111191
     * @Date: 2023年7月19日, 0019 下午 02:33:14
     */
    @Override
    public JSONObject insertExamBankPicture(String examCodes, MultipartFile file) {
        // 取得文件扩展名
        String extension = FileTypeUtils.getExtension(file);
        // 取得原始文件名
        String originalFile = file.getOriginalFilename();

        R<SysFile> fileResult = remoteFileService.upload(file);
        if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData())) {
            new ServiceException("文件服务异常，请联系管理员");
        }
        ExamTaskPicture examTaskPicture = new ExamTaskPicture();
        examTaskPicture.setPhotoCode(examCodes);
        examTaskPicture.setPhotoPath(fileResult.getData().getUrl());
        examTaskPicture.setPhotoUrl(fileResult.getData().getUrl());
        examTaskPicture.setFileName(fileResult.getData().getName());
        examTaskPicture.setOriginalName(originalFile);
        examTaskPicture.setCreateBy(SecurityUtils.getLoginUser().getUserName());

        ExamTaskPicture examPicture = examTaskPictureMapper.selectExamTaskPictureById(examCodes);
        if (!ObjectUtil.isEmpty(examPicture)) {
            examTaskPictureMapper.deleteExamTaskPictureById(examCodes);
        }
        examTaskPictureMapper.insertExamTaskPicture(examTaskPicture);
        JSONObject json = new JSONObject();
        json.put("url", fileResult.getData().getUrl());

        return json;
    }
}

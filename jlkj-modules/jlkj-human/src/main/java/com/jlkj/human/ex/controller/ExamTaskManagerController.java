package com.jlkj.human.ex.controller;

import com.jlkj.common.core.domain.R;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.file.FileTypeUtils;
import com.jlkj.common.core.utils.file.MimeTypeUtils;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.service.TokenService;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.ex.domain.ExamTaskManager;
import com.jlkj.human.ex.domain.ExamTaskPicture;
import com.jlkj.human.ex.service.IExamTaskManagerService;
import com.jlkj.human.ex.service.IExamTaskPictureService;
import com.jlkj.system.api.RemoteFileService;
import com.jlkj.system.api.domain.SysFile;
import com.jlkj.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 创建考试Controller
 *
 * @author qnsdt
 * @date 2023-01-06
 */
@RestController
@RequestMapping("/exam/examtask")
public class ExamTaskManagerController extends BaseController {
    @Autowired
    private IExamTaskManagerService examTaskManagerService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IExamTaskPictureService examTaskPictureService;
    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * 查询创建考试列表
     */
    @RequiresPermissions("exam:examtask:list")
    @GetMapping("/list")
    public TableDataInfo list(ExamTaskManager examTaskManager) {

        if (examTaskManager.getStartTime() != null) {
            examTaskManager.setStartDateText(DateUtils.toShortDateString(examTaskManager.getStartTime()));
        }

        if (examTaskManager.getEndTime() != null) {
            examTaskManager.setEndDateText(DateUtils.toShortDateString(examTaskManager.getEndTime()));
        }

        startPage();
        List<ExamTaskManager> list = examTaskManagerService.selectExamTaskManagerList(examTaskManager);
        return getDataTable(list);
    }

    /**
     * 导出创建考试列表
     */
    @RequiresPermissions("exam:examtask:export")
    @Log(title = "创建考试", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, ExamTaskManager examTaskManager) {
        List<ExamTaskManager> list = examTaskManagerService.selectExamTaskManagerList(examTaskManager);
        ExcelUtil<ExamTaskManager> util = new ExcelUtil<ExamTaskManager>(ExamTaskManager.class);
        util.exportExcel(response, list, "创建考试数据");
    }

    /**
     * 获取创建考试详细信息
     */
    @RequiresPermissions("exam:examtask:query")
    @GetMapping(value = "/{examCode}")
    public AjaxResult getInfo(@PathVariable("examCode") String examCode) {
        return AjaxResult.success(examTaskManagerService.selectExamTaskManagerById(examCode));
    }

    /**
     * 新增创建考试
     */
    @RequiresPermissions("exam:examtask:add")
    @Log(title = "创建考试", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody ExamTaskManager examTaskManager) {
        return toAjax(examTaskManagerService.insertExamTaskManager(examTaskManager));
    }

    /**
     * 修改创建考试
     */
    @RequiresPermissions("exam:examtask:edit")
    @Log(title = "创建考试", businessType = BusinessType.UPDATE)
    // @PutMapping
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody ExamTaskManager examTaskManager) {
        return toAjax(examTaskManagerService.updateExamTaskManager(examTaskManager));
    }

    /**
     * 删除创建考试
     */
    @RequiresPermissions("exam:examtask:remove")
    @Log(title = "创建考试", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{examCodes}")
    public AjaxResult remove(@PathVariable String[] examCodes) {
        return toAjax(examTaskManagerService.deleteExamTaskManagerByIds(examCodes));
    }

    /**
     * 新增参数配置
     */
    @PostMapping("/uploadphoto/{examCodes}")
    public AjaxResult uploadPhoto(@PathVariable String examCodes, @RequestParam("file") MultipartFile file) {
        String extension = FileTypeUtils.getExtension(file);
        if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
            return error("文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
        }
        // 取得当前登录人员信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        R<SysFile> fileResult = remoteFileService.upload(file);
        if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData())) {
            return error("文件服务异常，请联系管理员");
        }
        String url = fileResult.getData().getUrl();
        ExamTaskPicture examTaskPicture =
                new ExamTaskPicture(examCodes, "", url, fileResult.getData().getName(),
                        "", loginUser.getUsername());
        try {
            examTaskPictureService.deleteExamTaskPictureById(examCodes);
            examTaskPictureService.insertExamTaskPicture(examTaskPicture);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success(examTaskPicture);
    }
}

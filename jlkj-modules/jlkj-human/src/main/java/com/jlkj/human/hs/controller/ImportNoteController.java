package com.jlkj.human.hs.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.ImportNote;
import com.jlkj.human.hs.service.IImportNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 薪资导入记录Controller
 *
 * @author 266861
 * @date 2023-06-05
 */
@RestController
@RequestMapping("/importNote")
public class ImportNoteController extends BaseController
{
    @Autowired
    private IImportNoteService importNoteService;

    /**
     * 查询薪资导入记录列表
     */
    @RequiresPermissions("human:importNote:query")
    @PostMapping("/query")
    public AjaxResult query(@RequestBody ImportNote importNote)
    {
        ImportNote note = importNoteService.selectImportNote(importNote);
        return success(note);
    }

}

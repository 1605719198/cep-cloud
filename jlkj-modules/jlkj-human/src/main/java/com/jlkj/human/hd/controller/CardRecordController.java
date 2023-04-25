package com.jlkj.human.hd.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.CardRecord;
import com.jlkj.human.hd.service.ICardRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 刷卡记录Controller
 *
 * @author jiangbingchen
 * @date 2023-04-03
 */
@RestController
@RequestMapping("/cardRecord")
public class CardRecordController extends BaseController
{
    @Autowired
    private ICardRecordService cardRecordService;

    /**
     * 查询刷卡记录列表
     */
    @RequiresPermissions("human:cardRecord:list")
    @GetMapping("/list")
    public TableDataInfo list(CardRecord cardRecord)
    {
        startPage();
        List<CardRecord> list = cardRecordService.selectCardRecordList(cardRecord);
        return getDataTable(list);
    }
}

package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.BoardDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.Board;
import com.jlkj.human.hm.service.IBoardService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 板块基础资料 前端控制器
 * </p>
 *
 * @author Xin
 * @since 2022-04-28
 */
@RestController
@RequestMapping("/humanresource/board")
public class BoardController {

    @Autowired
    private IBoardService boardService;
    /**
     * 新增板块资料
     */
    @Log(title = "新增板块资料",businessType = BusinessType.INSERT)
    @Operation(summary = "新增板块资料")
    @PostMapping("/addBoardData")
    public AjaxResult add(@RequestBody BoardDTO boardDTO, @RequestHeader("token") String token){
        Board board = new Board();
        BeanUtils.copyProperties(boardDTO, board);
        boolean save = boardService.save(board);
        return AjaxResult.success(save);
    }
    /**
     * 修改板块资料
     */
    @Log(title = "修改板块资料",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改板块资料")
    @PutMapping("/updateBoardData")
    public AjaxResult updateBoardData(@RequestBody BoardDTO boardDTO, @RequestHeader("token") String token){
        Board humanresourceBoard = new Board();
        BeanUtils.copyProperties(boardDTO,humanresourceBoard);
        Board board = boardService.getById(humanresourceBoard.getId());
        if (StringUtils.isNotNull(board)) {
            boolean update = boardService.updateById(humanresourceBoard);
            return AjaxResult.success(update);
        } else {
            boolean save = boardService.save(humanresourceBoard);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除板块资料
     */
    @Log(title = "删除板块资料",businessType = BusinessType.DELETE)
    @Operation(summary = "删除板块资料")
    @DeleteMapping("/deleteBoardData")
    public AjaxResult deleteBoardData(@RequestBody BoardDTO boardDTO,@RequestHeader("token") String token){
        Board board = new Board();
        BeanUtils.copyProperties(boardDTO, board);
        boolean delete = boardService.removeById(board);
        return AjaxResult.success(delete);
    }
}

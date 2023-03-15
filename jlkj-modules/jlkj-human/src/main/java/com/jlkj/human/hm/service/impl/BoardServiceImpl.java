package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hm.domain.Board;
import com.jlkj.human.hm.mapper.BoardMapper;
import com.jlkj.human.hm.service.IBoardService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 板块基础资料 服务实现类
 * </p>
 *
 * @author Xin
 * @since 2022-04-28
 */
@Service
public class BoardServiceImpl extends ServiceImpl<BoardMapper, Board> implements IBoardService {

}

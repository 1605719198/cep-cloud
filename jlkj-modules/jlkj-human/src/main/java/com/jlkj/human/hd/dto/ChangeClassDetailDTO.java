package com.jlkj.human.hd.dto;

import com.jlkj.common.core.web.domain.BaseEntity;
import com.jlkj.human.hd.domain.PersonClassDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *排班明细子表操作
 * @author 266861
 * @date 2023-03-08
 */


@Data
public class ChangeClassDetailDTO  {
    /**
     * 排班list
     */
    private ArrayList<PersonClassDetail> shiftChangeList;
}

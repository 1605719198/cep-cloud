package com.jlkj.human.hd.dto;

import com.jlkj.common.core.web.domain.BaseEntity;
import com.jlkj.human.hd.domain.PersonClassDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChangeClassDetailDTO  {
    private ArrayList<PersonClassDetail> shiftChangeList;
}

package com.jlkj.system.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * @author zyf
 * @Description
 * @create 2022-07-07 13:58
 */
@Data
public class GetUsersByIdsDTO {

    @NotNull
    private ArrayList<String> user_ids;
}

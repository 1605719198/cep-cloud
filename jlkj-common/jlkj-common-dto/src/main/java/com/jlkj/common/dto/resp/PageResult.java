package com.jlkj.common.dto.resp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zyf
 * @param <T>
 */
@Data
public class PageResult<T> implements Serializable {

    private String code;

    private Data data;

    private String msg;

    public static <T> PageResult<T> success(IPage<T> page) {
        PageResult<T> result = new PageResult<>();
        result.setCode(ResultCode.SUCCESS.getCode());

        Data<T> data = new Data<T>();
        data.setRecords(page.getRecords());
        data.setTotal(page.getTotal());
        data.setPage(page.getPages());
        data.setCurrent(page.getCurrent());
        data.setSize(page.getSize());

        result.setData(data);
        result.setMsg(ResultCode.SUCCESS.getMsg());
        return result;
    }

    @lombok.Data
    public static class Data<T> {

        private List<T> records;

        private long total;

        private long current;

        private long size;

        private long page;

    }

}

package com.jlkj.safety.si.service.impl;

import com.jlkj.safety.si.service.RedisService;
import com.jlkj.safety.si.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jlkj.safety.si.constants.Constant.ROW2;

/**
 * <p>
 * Redis 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-07
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Object getSafePointList(Map<String, Object> params) {
        try {
            Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("SEDATA_POINTVALUE_MAP");
            Map<String, Object> out = new HashMap<>(1);
            log.info("entries的长度为: " + entries.size());
            entries.forEach((key, value) -> {
                String[] array = String.valueOf(value).replaceAll("\"", "").split("\\|");
                if (array.length == ROW2) {
                    Map<String, Object> item = new HashMap<>(2);
                    item.put("id", String.valueOf(key));
                    item.put("value", array[0]);
                    item.put("time", array[1]);
                    out.put(String.valueOf(key), item);
                }
            });
            return ResponseUtil.toResult(params, out);
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "安全报警采集数据获取失败");
        }
    }

    @Override
    public Object getSafeDetailPointList(Map<String, Object> params) {
        try {
            List<Map<String, Object>> out = new ArrayList<>();
            List<String> values = stringRedisTemplate.opsForList().range("SE_AQ_WARN_DATA", 0, -1);
            log.info("list的长度为: " + values.size());
            for (String value: values) {
                String[] array = String.valueOf(value).replaceAll("\"", "").split("\\|");
                if (array.length == 7 && array[0].equals(params.get("safe_area_name"))) {
                    Map<String, Object> item = new HashMap<>(7);
                    item.put("area_name", array[1]);
                    item.put("name", array[2]);
                    item.put("id", array[3]);
                    item.put("position_name", array[4]);
                    item.put("value", array[5]);
                    item.put("warn", array[6]);
                    out.add(item);
                }
            }
            return ResponseUtil.toResult(params, out);
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "安全报警明细采集数据获取失败");
        }
    }

    @Override
    public Object getSafeTotalPointList(Map<String, Object> params) {
        try {
            List<Map<String, Object>> out = new ArrayList<>();
            List<String> values = stringRedisTemplate.opsForList().range("SE_AQ_WARN_TOTAL", 0, -1);
            log.info("list的长度为: " + values.size());
            for (String value: values) {
                String[] array = String.valueOf(value).replaceAll("\"", "").split("\\|");
                if (array.length == 2) {
                    Map<String, Object> item = new HashMap<>(2);
                    item.put("safe_area_name", array[0]);
                    item.put("count", array[1]);
                    out.add(item);
                }
            }
            return ResponseUtil.toResult(params, out);
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "安全报警汇总采集数据获取失败");
        }
    }

    @Override
    public Object getSafeEnvDetailPointList(Map<String, Object> params) {
        try {
            List<Map<String, Object>> out = new ArrayList<>();
            List<String> values = stringRedisTemplate.opsForList().range("SE_HB_WARN_DATA", 0, -1);
            log.info("list的长度为: " + values.size());
            for (String value: values) {
                String[] array = String.valueOf(value).replaceAll("\"", "").split("\\|");
                if (array.length == 4) {
                    Map<String, Object> item = new HashMap<>(4);
                    item.put("area_name", array[0]);
                    item.put("name", array[1]);
                    item.put("value", array[2]);
                    item.put("warn", array[3]);
                    out.add(item);
                }
            }
            return ResponseUtil.toResult(params, out);
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "环保明细采集数据获取失败");
        }
    }

    @Override
    public Object getSafeEnvTotalPointList(Map<String, Object> params) {
        try {
            List<Map<String, Object>> out = new ArrayList<>();
            List<String> values = stringRedisTemplate.opsForList().range("SE_HB_WARN_TOTAL", 0, -1);
            log.info("list的长度为: " + values.size());
            for (String value: values) {
                String[] array = String.valueOf(value).replaceAll("\"", "").split("\\|");
                if (array.length == 2) {
                    Map<String, Object> item = new HashMap<>(2);
                    item.put("safe_area_name", array[0]);
                    item.put("count", array[1]);
                    out.add(item);
                }
            }
            return ResponseUtil.toResult(params, out);
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "环保汇总采集数据获取失败");
        }
    }
}

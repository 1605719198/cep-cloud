package com.jlkj.safety.si.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.dto.safeindex.PageSafeIndexDTO;
import com.jlkj.safety.si.dto.safeindex.SafeAnalysisListDTO;
import com.jlkj.safety.si.entity.SafeIndex;
import com.jlkj.safety.si.mapper.SafeIndexMapper;
import com.jlkj.safety.si.service.SafeIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author yzl
 * @Description 环保指标项
 * @create 2022年7月19日 08:51:54
 */
@Service
@Slf4j
public class SafeIndexServiceImpl extends ServiceImpl<SafeIndexMapper, SafeIndex>
        implements SafeIndexService {
    @Override
    public IPage<Map<String, String>> getListPage(PageSafeIndexDTO pageSafeIndexDTO) {
        Page<Map<String, String>> page = new Page<>(pageSafeIndexDTO.getCurrent(), pageSafeIndexDTO.getSize());
        return getBaseMapper().getListPage(page, pageSafeIndexDTO);
    }


    public IPage<Map<String, String>> getAnalysisListPage(SafeAnalysisListDTO dto) {
        Page<Map<String, String>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return getBaseMapper().getAnalysisListPage(page, dto);
    }

    public List<Map<String, String>> getAnalysisPie(SafeAnalysisListDTO dto) {
        return getBaseMapper().getAnalysisPie(dto);
    }

    public List<Map<String, Object>> getAnalysisLine(SafeAnalysisListDTO dto) {
        List<Map<String, String>> list = getBaseMapper().getAnalysisLine(dto);
        ArrayList<Object> fire = new ArrayList<>();
        ArrayList<Object> high = new ArrayList<>();
        ArrayList<Object> hosting = new ArrayList<>();
        ArrayList<Object> space = new ArrayList<>();
        ArrayList<Object> soil = new ArrayList<>();
        ArrayList<Object> blind = new ArrayList<>();
        ArrayList<Object> cut = new ArrayList<>();
        ArrayList<Object> temp = new ArrayList<>();
        list.forEach(stringStringMap -> {
            fire.add(stringStringMap.get("fires"));
            high.add(stringStringMap.get("highs"));
            hosting.add(stringStringMap.get("hoistings"));
            space.add(stringStringMap.get("spaces"));
            soil.add(stringStringMap.get("soils"));
            blind.add(stringStringMap.get("blinds"));
            cut.add(stringStringMap.get("cuts"));
            temp.add(stringStringMap.get("temporarys"));
        });
        List<Map<String, Object>> res = new ArrayList<>();
        Map<String, Object> fireMap = new HashMap<>(1);
        fireMap.put("name", "动火作业票");
        fireMap.put("type", "line");
        fireMap.put("stack", "total");
        fireMap.put("data", fire);
        res.add(fireMap);

        Map<String, Object> highMap = new HashMap<>(1);
        highMap.put("name", "高处作业票");
        highMap.put("type", "line");
        highMap.put("stack", "total");
        highMap.put("data", high);
        res.add(highMap);

        Map<String, Object> hostingMap = new HashMap<>(1);
        hostingMap.put("name", "吊装作业票");
        hostingMap.put("type", "line");
        hostingMap.put("stack", "total");
        hostingMap.put("data", hosting);
        res.add(hostingMap);

        Map<String, Object> spaceMap = new HashMap<>(1);
        spaceMap.put("name", "有限空间作业票");
        spaceMap.put("type", "line");
        spaceMap.put("stack", "total");
        spaceMap.put("data", space);
        res.add(spaceMap);

        Map<String, Object> soilMap = new HashMap<>(1);
        soilMap.put("name", "动土作业票");
        soilMap.put("type", "line");
        soilMap.put("stack", "total");
        soilMap.put("data", soil);
        res.add(soilMap);

        Map<String, Object> blindMap = new HashMap<>(1);
        blindMap.put("name", "抽堵盲板作业票");
        blindMap.put("type", "line");
        blindMap.put("stack", "total");
        blindMap.put("data", blind);
        res.add(blindMap);

        Map<String, Object> cutMap = new HashMap<>(1);
        cutMap.put("name", "断路安全作业票");
        cutMap.put("type", "line");
        cutMap.put("stack", "total");
        cutMap.put("data", cut);
        res.add(cutMap);

        Map<String, Object> tempMap = new HashMap<>(1);
        tempMap.put("name", "临时用电作业票");
        tempMap.put("type", "line");
        tempMap.put("stack", "total");
        tempMap.put("data", temp);
        res.add(tempMap);
        return res;
    }

    public List<Map<String, String>> getAnalysisBoardTicketTotal() {
        return getBaseMapper().getAnalysisBoardTicketTotal();
    }
}


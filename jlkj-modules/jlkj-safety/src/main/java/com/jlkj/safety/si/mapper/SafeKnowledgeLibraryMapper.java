package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeSiKnowledgeLibrary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 知识库 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface SafeKnowledgeLibraryMapper extends BaseMapper<SafeSiKnowledgeLibrary> {

    /**
     * 知识库-查询
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select a.id,a.knowledge_category_id,b.knowledge_category_name,a.title,a.keywords,a.contents,a.create_user_name,DATE_FORMAT(a.ceate_time,'%Y-%m-%d %H:%i:%s') as ceate_time\n" +
            "from safe_si_knowledge_library a\n" +
            "INNER JOIN safe_si_knowledge_category b ON a.knowledge_category_id = b.id\n" +
            "where (#{condition.knowledge_category_id}='0' or a.knowledge_category_id=#{condition.knowledge_category_id}) and\n" +
            "(#{condition.title}='' or a.title like (concat('%', #{condition.title}, '%'))) and\n" +
            "(#{condition.keywords}='' or a.keywords like (concat('%', #{condition.keywords}, '%')))\n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeKnowledgeLibraryPageList(Page<?> page, @Param("condition") Map<String, Object> condition);

    /**
     * 知识库附件-删除
     * @param condition 删除条件
     * @return 结果
     */
    @Delete(
            "delete from safe_si_knowledge_library_file where knowledge_id=#{id}"
    )
    int deleteSafeKnowledgeLibraryFiles(Map<String, Object> condition);

    /**
     * 知识库-附件列表
     * @param condition 删除条件
     * @return 结果
     */
    @Select(
            "select id,file_name,disk_name,file_size,file_id from safe_si_knowledge_library_file\n" +
            "where knowledge_id=#{id}\n" +
            "order by id"
    )
    List<Map<String, Object>> getSafeKnowledgeLibraryFileList(Map<String, Object> condition);

}

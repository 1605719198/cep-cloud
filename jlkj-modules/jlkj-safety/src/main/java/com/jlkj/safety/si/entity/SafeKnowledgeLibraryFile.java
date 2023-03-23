package com.jlkj.safety.si.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 知识库-附件
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeKnowledgeLibraryFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 行标识
     */
    private String id;

    /**
     * 知识ID
     */
    private String knowledgeId;

    /**
     * 原始文件名
     */
    private String fileName;

    /**
     * 磁盘文件名
     */
    private String diskName;

    /**
     * 文件大小
     */
    private Integer fileSize;

    /**
     * 文件id
     */
    private String fileId;


}

package com.jlkj.safety.si.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 文件资源表
 * @author zyf
 * @TableName sys_fileresource
 */
@TableName(value ="sys_fileresource")
@Data
public class SysFileresource implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 转换文件名
     */
    private String targetName;

    /**
     * 文件位置
     */
    private String filePath;

    /**
     * 服务器相对路径
     */
    private String relativePath;

    /**
     * 原始文件名
     */
    private String fileName;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人id
     */
    private String modifyUserId;

    /**
     * 修改人
     */
    private String modifyUserName;

    /**
     * 修改时间
     */
    private Date modifyTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

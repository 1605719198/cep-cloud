package com.jlkj.flowable.domain.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlValue;
import java.io.Serializable;

/**
 * @author xin
 * @date 2021/3/28 19:48
 */
public class FlowSaveXmlVo implements Serializable {

    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程分类
     */
    private String category;

    /**
     * xml 文件
     */
    private String xml;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    @Override
    public String toString() {
        return "FlowSaveXmlVo{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", xml='" + xml + '\'' +
                '}';
    }


}

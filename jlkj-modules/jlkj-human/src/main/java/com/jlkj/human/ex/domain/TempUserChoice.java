package com.jlkj.human.ex.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 用户选择对象 temp_user_choice
 * 
 * @author qnsdt
 * @date 2023-01-31
 */
public class TempUserChoice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 选择代码 */
    @Excel(name = "选择代码")
    private String choiceCode;

    /** 用户代码 */
    @Excel(name = "用户代码")
    private String userCode;

    /** 内部排序 */
    @Excel(name = "内部排序")
    private Long orderId;

    public void setChoiceCode(String choiceCode) 
    {
        this.choiceCode = choiceCode;
    }

    public String getChoiceCode() 
    {
        return choiceCode;
    }
    public void setUserCode(String userCode) 
    {
        this.userCode = userCode;
    }

    public String getUserCode() 
    {
        return userCode;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("choiceCode", getChoiceCode())
            .append("userCode", getUserCode())
            .append("orderId", getOrderId())
            .toString();
    }
}

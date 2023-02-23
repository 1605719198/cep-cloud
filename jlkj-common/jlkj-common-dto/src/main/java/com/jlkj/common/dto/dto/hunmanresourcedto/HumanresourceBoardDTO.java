package com.jlkj.common.dto.dto.hunmanresourcedto;

/**
 * @description:
 * @author: Xin
 * @time: 2022/4/28 16:40
 */
public class HumanresourceBoardDTO {
    /**
     * 主键ID
     */
    private String id;
    /**
     * 板块代号
     */
    private String boardTypeCode;
    /**
     * 板块名称
     */
    private String boardTypeName;
    /**
     * 输入日期
     */
    private String createDate;
    /**
     * 输入人
     */
    private String creator;
    /**
     * 输入人ID
     */
    private String creatorId;
    /**
     * 状态
     */
    private String status;
    /**
     * 使用次数
     */
    private String usedTimes;

    public HumanresourceBoardDTO() {
    }

    public HumanresourceBoardDTO(String id, String boardTypeCode, String boardTypeName, String createDate, String creator, String creatorId, String status, String usedTimes) {
        this.id = id;
        this.boardTypeCode = boardTypeCode;
        this.boardTypeName = boardTypeName;
        this.createDate = createDate;
        this.creator = creator;
        this.creatorId = creatorId;
        this.status = status;
        this.usedTimes = usedTimes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoardTypeCode() {
        return boardTypeCode;
    }

    public void setBoardTypeCode(String boardTypeCode) {
        this.boardTypeCode = boardTypeCode;
    }

    public String getBoardTypeName() {
        return boardTypeName;
    }

    public void setBoardTypeName(String boardTypeName) {
        this.boardTypeName = boardTypeName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsedTimes() {
        return usedTimes;
    }

    public void setUsedTimes(String usedTimes) {
        this.usedTimes = usedTimes;
    }

    @Override
    public String toString() {
        return "HumanresourceBoardDTO{" +
                "id='" + id + '\'' +
                ", boardTypeCode='" + boardTypeCode + '\'' +
                ", boardTypeName='" + boardTypeName + '\'' +
                ", createDate='" + createDate + '\'' +
                ", creator='" + creator + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", status='" + status + '\'' +
                ", usedTimes='" + usedTimes + '\'' +
                '}';
    }
}

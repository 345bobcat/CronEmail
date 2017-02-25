package com.dark.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "template_config")
public class TemplateConfig implements Serializable {
    @Id
    @Column(name = "tc_id")
    private Integer tcId;

    private String name;

    @Column(name = "save_path")
    private String savePath;

    private String suffix;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "back_user")
    private String backUser;

    private String content;

    @Column(name = "sql_sentence")
    private String sqlSentence;

    @Column(name = "sql_source_id")
    private Integer sqlSourceId;

    /**
     * 记录创建时间
     */
    @Column(name = "gmt_created")
    private Date gmtCreated;

    /**
     * 记录修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 说明备注
     */
    private String comment;

    private static final long serialVersionUID = 1L;

    public TemplateConfig(Integer tcId, String name, String savePath, String suffix, String fileName, String backUser, String content, String sqlSentence, Integer sqlSourceId, Date gmtCreated, Date gmtModified) {
        this.tcId = tcId;
        this.name = name;
        this.savePath = savePath;
        this.suffix = suffix;
        this.fileName = fileName;
        this.backUser = backUser;
        this.content = content;
        this.sqlSentence = sqlSentence;
        this.sqlSourceId = sqlSourceId;
        this.gmtCreated = gmtCreated;
        this.gmtModified = gmtModified;
    }

    public TemplateConfig() {
        super();
    }

    /**
     * @return tc_id
     */
    public Integer getTcId() {
        return tcId;
    }

    /**
     * @param tcId
     */
    public void setTcId(Integer tcId) {
        this.tcId = tcId;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return save_path
     */
    public String getSavePath() {
        return savePath;
    }

    /**
     * @param savePath
     */
    public void setSavePath(String savePath) {
        this.savePath = savePath == null ? null : savePath.trim();
    }

    /**
     * @return suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    /**
     * @return file_name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * @return back_user
     */
    public String getBackUser() {
        return backUser;
    }

    /**
     * @param backUser
     */
    public void setBackUser(String backUser) {
        this.backUser = backUser == null ? null : backUser.trim();
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getSqlSentence() {
        return sqlSentence;
    }

    public void setSqlSentence(String sqlSentence) {
        this.sqlSentence = sqlSentence;
    }

    public Integer getSqlSourceId() {
        return sqlSourceId;
    }

    public void setSqlSourceId(Integer sqlSourceId) {
        this.sqlSourceId = sqlSourceId;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tcId=").append(tcId);
        sb.append(", name=").append(name);
        sb.append(", savePath=").append(savePath);
        sb.append(", suffix=").append(suffix);
        sb.append(", fileName=").append(fileName);
        sb.append(", backUser=").append(backUser);
        sb.append(", content=").append(content);
        sb.append(", sqlSentence=").append(sqlSentence);
        sb.append(", sqlSourceId=").append(sqlSourceId);
        sb.append(", comment=").append(comment);
        sb.append(", gmtCreated=").append(gmtCreated);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
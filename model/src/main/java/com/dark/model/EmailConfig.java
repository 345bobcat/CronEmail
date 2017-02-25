package com.dark.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "email_config")
public class EmailConfig implements Serializable {
    @Id
    @Column(name = "ec_id")
    private Integer ecId;

    @Column(name = "tc_id")
    private Integer tcId;

    private String sender;

    private String receivers;

    @Column(name = "email_cc")
    private String emailCc;

    @Column(name = "email_bcc")
    private String emailBcc;

    private String subject;

    @Column(name = "back_user")
    private String backUser;

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

    private static final long serialVersionUID = 1L;

    public EmailConfig(Integer ecId, Integer tcId, String sender, String receivers, String emailCc, String emailBcc, String subject, String backUser, Date gmtCreated, Date gmtModified) {
        this.ecId = ecId;
        this.tcId = tcId;
        this.sender = sender;
        this.receivers = receivers;
        this.emailCc = emailCc;
        this.emailBcc = emailBcc;
        this.subject = subject;
        this.backUser = backUser;
        this.gmtCreated = gmtCreated;
        this.gmtModified = gmtModified;
    }

    public EmailConfig() {
        super();
    }

    /**
     * @return ec_id
     */
    public Integer getEcId() {
        return ecId;
    }

    /**
     * @param ecId
     */
    public void setEcId(Integer ecId) {
        this.ecId = ecId;
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
     * @return sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender
     */
    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    /**
     * @return receivers
     */
    public String getReceivers() {
        return receivers;
    }

    /**
     * @param receivers
     */
    public void setReceivers(String receivers) {
        this.receivers = receivers == null ? null : receivers.trim();
    }

    /**
     * @return email_cc
     */
    public String getEmailCc() {
        return emailCc;
    }

    /**
     * @param emailCc
     */
    public void setEmailCc(String emailCc) {
        this.emailCc = emailCc == null ? null : emailCc.trim();
    }

    /**
     * @return email_bcc
     */
    public String getEmailBcc() {
        return emailBcc;
    }

    /**
     * @param emailBcc
     */
    public void setEmailBcc(String emailBcc) {
        this.emailBcc = emailBcc == null ? null : emailBcc.trim();
    }

    /**
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ecId=").append(ecId);
        sb.append(", tcId=").append(tcId);
        sb.append(", sender=").append(sender);
        sb.append(", receivers=").append(receivers);
        sb.append(", emailCc=").append(emailCc);
        sb.append(", emailBcc=").append(emailBcc);
        sb.append(", subject=").append(subject);
        sb.append(", backUser=").append(backUser);
        sb.append(", gmtCreated=").append(gmtCreated);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
package com.tamguo.modules.sys.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.modules.sys.model.enums.SysUserStatusEnum;


/**
 * The persistent class for the sys_user database table.
 * 
 */
@TableName(value="sys_user")
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private String userCode;
	private String officeCode;
	private String officeName;
	private String companyCode;
	private String companyName;
	private String avatar;
	private String corpCode;
	private String corpName;
	private String createBy;
	private Date createDate;
	private String email;
	private String freezeCause;
	private Date freezeDate;
	private Date lastLoginDate;
	private String lastLoginIp;
	private String loginCode;
	private String mgrType;
	private String mobile;
	private String mobileImei;
	private String password;
	private String phone;
	private Date pwdQuestUpdateDate;
	private String pwdQuestion;
	@TableField(value="pwd_question_2")
	private String pwdQuestion2;
	@TableField(value="pwd_question_3")
	private String pwdQuestion3;
	private String pwdQuestionAnswer;
	@TableField(value="pwd_question_answer_2")
	private String pwdQuestionAnswer2;
	@TableField(value="pwd_question_answer_3")
	private String pwdQuestionAnswer3;
	private BigDecimal pwdSecurityLevel;
	private Date pwdUpdateDate;
	private String pwdUpdateRecord;
	private String refCode;
	private String refName;
	private String remarks;
	private String sex;
	private String sign;
	private SysUserStatusEnum status;
	private String updateBy;
	private Date updateDate;
	private String userName;
	private String userType;
	private BigDecimal userWeight;
	private String wxOpenid;
	private String userNameEn;

	public SysUserEntity() {
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCorpCode() {
		return this.corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public String getCorpName() {
		return this.corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFreezeCause() {
		return this.freezeCause;
	}

	public void setFreezeCause(String freezeCause) {
		this.freezeCause = freezeCause;
	}

	public Date getFreezeDate() {
		return this.freezeDate;
	}

	public void setFreezeDate(Date freezeDate) {
		this.freezeDate = freezeDate;
	}

	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLoginCode() {
		return this.loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobileImei() {
		return this.mobileImei;
	}

	public void setMobileImei(String mobileImei) {
		this.mobileImei = mobileImei;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getPwdQuestUpdateDate() {
		return this.pwdQuestUpdateDate;
	}

	public void setPwdQuestUpdateDate(Date pwdQuestUpdateDate) {
		this.pwdQuestUpdateDate = pwdQuestUpdateDate;
	}

	public String getPwdQuestion() {
		return this.pwdQuestion;
	}

	public void setPwdQuestion(String pwdQuestion) {
		this.pwdQuestion = pwdQuestion;
	}

	public String getPwdQuestion2() {
		return this.pwdQuestion2;
	}

	public void setPwdQuestion2(String pwdQuestion2) {
		this.pwdQuestion2 = pwdQuestion2;
	}

	public String getPwdQuestion3() {
		return this.pwdQuestion3;
	}

	public void setPwdQuestion3(String pwdQuestion3) {
		this.pwdQuestion3 = pwdQuestion3;
	}

	public String getPwdQuestionAnswer() {
		return this.pwdQuestionAnswer;
	}

	public void setPwdQuestionAnswer(String pwdQuestionAnswer) {
		this.pwdQuestionAnswer = pwdQuestionAnswer;
	}

	public String getPwdQuestionAnswer2() {
		return this.pwdQuestionAnswer2;
	}

	public void setPwdQuestionAnswer2(String pwdQuestionAnswer2) {
		this.pwdQuestionAnswer2 = pwdQuestionAnswer2;
	}

	public String getPwdQuestionAnswer3() {
		return this.pwdQuestionAnswer3;
	}

	public void setPwdQuestionAnswer3(String pwdQuestionAnswer3) {
		this.pwdQuestionAnswer3 = pwdQuestionAnswer3;
	}

	public BigDecimal getPwdSecurityLevel() {
		return this.pwdSecurityLevel;
	}

	public void setPwdSecurityLevel(BigDecimal pwdSecurityLevel) {
		this.pwdSecurityLevel = pwdSecurityLevel;
	}

	public Date getPwdUpdateDate() {
		return this.pwdUpdateDate;
	}

	public void setPwdUpdateDate(Date pwdUpdateDate) {
		this.pwdUpdateDate = pwdUpdateDate;
	}

	public String getPwdUpdateRecord() {
		return this.pwdUpdateRecord;
	}

	public void setPwdUpdateRecord(String pwdUpdateRecord) {
		this.pwdUpdateRecord = pwdUpdateRecord;
	}

	public String getRefCode() {
		return this.refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public String getRefName() {
		return this.refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSign() {
		return this.sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public SysUserStatusEnum getStatus() {
		return this.status;
	}

	public void setStatus(SysUserStatusEnum status) {
		this.status = status;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public BigDecimal getUserWeight() {
		return this.userWeight;
	}

	public void setUserWeight(BigDecimal userWeight) {
		this.userWeight = userWeight;
	}

	public String getWxOpenid() {
		return this.wxOpenid;
	}

	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMgrType() {
		return mgrType;
	}

	public void setMgrType(String mgrType) {
		this.mgrType = mgrType;
	}

	public String getUserNameEn() {
		return userNameEn;
	}

	public void setUserNameEn(String userNameEn) {
		this.userNameEn = userNameEn;
	}


}
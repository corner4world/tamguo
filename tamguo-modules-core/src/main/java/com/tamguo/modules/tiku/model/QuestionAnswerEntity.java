package com.tamguo.modules.tiku.model;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;

@TableName(value="t_question_answer")
public class QuestionAnswerEntity extends SuperEntity<PaperEntity> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String questionId;
	private String memberId;
	private String memberAvatar;
	private String memberName;
	private String answer;
	private Integer agreeNum;
	private Integer disagreeNum;
	private Date createDate;
	
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getAgreeNum() {
		return agreeNum;
	}
	public void setAgreeNum(Integer agreeNum) {
		this.agreeNum = agreeNum;
	}
	public Integer getDisagreeNum() {
		return disagreeNum;
	}
	public void setDisagreeNum(Integer disagreeNum) {
		this.disagreeNum = disagreeNum;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getMemberAvatar() {
		return memberAvatar;
	}
	public void setMemberAvatar(String memberAvatar) {
		this.memberAvatar = memberAvatar;
	} 
	
}

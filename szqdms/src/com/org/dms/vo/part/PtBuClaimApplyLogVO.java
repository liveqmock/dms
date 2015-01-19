package com.org.dms.vo.part;

import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuClaimApplyLogVO extends BaseVO{
    public PtBuClaimApplyLogVO(){
    	//�����ֶ���Ϣ
    	this.addField("LOG_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CHECK_ORG_NAME",BaseVO.OP_STRING);
    	this.addField("CHECK_RESULT",BaseVO.OP_STRING);
    	this.addField("CHECK_REMARK",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("CHECK_ORG_ID",BaseVO.OP_STRING);
    	this.addField("CHECK_USER",BaseVO.OP_STRING);
    	this.addField("CHECK_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
    	this.addField("CREATE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_DATE", "yyyy-MM-dd");
    	this.addField("CHECK_ORG_CODE",BaseVO.OP_STRING);
    	this.addField("APPLY_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("LOG_ID","COMMON");
    	this.setVOTableName("PT_BU_CLAIM_APPLY_LOG");
}
	public void setLogId(String LogId){
		this.setInternal("LOG_ID" ,LogId );
	}


	public String getLogId(){
		return (String)this.getInternal("LOG_ID");
	}
	public void setCheckOrgName(String CheckOrgName){
		this.setInternal("CHECK_ORG_NAME" ,CheckOrgName );
	}


	public String getCheckOrgName(){
		return (String)this.getInternal("CHECK_ORG_NAME");
	}
	public void setCheckResult(String CheckResult){
		this.setInternal("CHECK_RESULT" ,CheckResult );
	}


	public String getCheckResult(){
		return (String)this.getInternal("CHECK_RESULT");
	}
	public void setCheckRemark(String CheckRemark){
		this.setInternal("CHECK_REMARK" ,CheckRemark );
	}


	public String getCheckRemark(){
		return (String)this.getInternal("CHECK_REMARK");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setCheckOrgId(String CheckOrgId){
		this.setInternal("CHECK_ORG_ID" ,CheckOrgId );
	}


	public String getCheckOrgId(){
		return (String)this.getInternal("CHECK_ORG_ID");
	}
	public void setCheckUser(String CheckUser){
		this.setInternal("CHECK_USER" ,CheckUser );
	}


	public String getCheckUser(){
		return (String)this.getInternal("CHECK_USER");
	}
	public void setCheckDate(Date CheckDate){
		this.setInternal("CHECK_DATE" ,CheckDate );
	}


	public Date getCheckDate(){
		return (Date)this.getInternal("CHECK_DATE");
	}
	public void setCreateDate(Date CreateDate){
		this.setInternal("CREATE_DATE" ,CreateDate );
	}


	public Date getCreateDate(){
		return (Date)this.getInternal("CREATE_DATE");
	}
	public void setCheckOrgCode(String CheckOrgCode){
		this.setInternal("CHECK_ORG_CODE" ,CheckOrgCode );
	}


	public String getCheckOrgCode(){
		return (String)this.getInternal("CHECK_ORG_CODE");
	}
	public void setApplyId(String ApplyId){
		this.setInternal("APPLY_ID" ,ApplyId );
	}


	public String getApplyId(){
		return (String)this.getInternal("APPLY_ID");
	}
}

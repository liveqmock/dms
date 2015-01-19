package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuProActiveDealCheckVO extends BaseVO{
    public PtBuProActiveDealCheckVO(){
    	//活动执行方案审核VO
    	this.addField("CHECK_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("DEAL_ID",BaseVO.OP_STRING);
    	this.addField("CHECK_RESULT",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("CHECK_CON",BaseVO.OP_STRING);
    	this.addField("CHECK_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
    	this.addField("CHECK_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("CHECK_ID","COMMON");
		//设置字典类型定义
		this.bindFieldToDic("STATUS","YXBS");//有效标识
    	this.setVOTableName("PT_BU_PRO_ACTIVE_DEAL_CHECK");
}
	public void setCheckId(String CheckId){
		this.setInternal("CHECK_ID" ,CheckId );
	}


	public String getCheckId(){
		return (String)this.getInternal("CHECK_ID");
	}
	public void setDealId(String DealId){
		this.setInternal("DEAL_ID" ,DealId );
	}


	public String getDealId(){
		return (String)this.getInternal("DEAL_ID");
	}
	public void setCheckResult(String CheckResult){
		this.setInternal("CHECK_RESULT" ,CheckResult );
	}


	public String getCheckResult(){
		return (String)this.getInternal("CHECK_RESULT");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setCheckCon(String CheckCon){
		this.setInternal("CHECK_CON" ,CheckCon );
	}


	public String getCheckCon(){
		return (String)this.getInternal("CHECK_CON");
	}
	public void setCheckDate(Date CheckDate){
		this.setInternal("CHECK_DATE" ,CheckDate );
	}


	public Date getCheckDate(){
		return (Date)this.getInternal("CHECK_DATE");
	}
	public void setCheckUser(String CheckUser){
		this.setInternal("CHECK_USER" ,CheckUser );
	}


	public String getCheckUser(){
		return (String)this.getInternal("CHECK_USER");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
}

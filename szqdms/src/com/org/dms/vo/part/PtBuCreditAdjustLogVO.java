package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuCreditAdjustLogVO extends BaseVO{
    public PtBuCreditAdjustLogVO(){
    	//�����ֶ���Ϣ
    	this.addField("AFT_LIMIT",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("BEF_LIMIT",BaseVO.OP_STRING);
    	this.addField("AJUST_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("AJUST_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("AJUST_USER",BaseVO.OP_STRING);
    	this.addField("LINE_ID",BaseVO.OP_STRING);
    	this.addField("AJUST_LIMIT",BaseVO.OP_STRING);
    	this.addField("AJUST_REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("LOG_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("LOG_ID","COMMON");
    	this.setVOTableName("PT_BU_CREDIT_ADJUST_LOG");
}
	public void setAftLimit(String AftLimit){
		this.setInternal("AFT_LIMIT" ,AftLimit );
	}


	public String getAftLimit(){
		return (String)this.getInternal("AFT_LIMIT");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setBefLimit(String BefLimit){
		this.setInternal("BEF_LIMIT" ,BefLimit );
	}


	public String getBefLimit(){
		return (String)this.getInternal("BEF_LIMIT");
	}
	public void setAjustDate(Date AjustDate){
		this.setInternal("AJUST_DATE" ,AjustDate );
	}


	public Date getAjustDate(){
		return (Date)this.getInternal("AJUST_DATE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setAjustUser(String AjustUser){
		this.setInternal("AJUST_USER" ,AjustUser );
	}


	public String getAjustUser(){
		return (String)this.getInternal("AJUST_USER");
	}
	public void setLineId(String LineId){
		this.setInternal("LINE_ID" ,LineId );
	}


	public String getLineId(){
		return (String)this.getInternal("LINE_ID");
	}
	public void setAjustLimit(String AjustLimit){
		this.setInternal("AJUST_LIMIT" ,AjustLimit );
	}


	public String getAjustLimit(){
		return (String)this.getInternal("AJUST_LIMIT");
	}
	public void setAjustRemarks(String AjustRemarks){
		this.setInternal("AJUST_REMARKS" ,AjustRemarks );
	}


	public String getAjustRemarks(){
		return (String)this.getInternal("AJUST_REMARKS");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setLogId(String LogId){
		this.setInternal("LOG_ID" ,LogId );
	}


	public String getLogId(){
		return (String)this.getInternal("LOG_ID");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
}

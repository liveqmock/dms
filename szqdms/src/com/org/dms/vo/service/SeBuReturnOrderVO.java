package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuReturnOrderVO extends BaseVO{
    public SeBuReturnOrderVO(){
    	//设置字段信息
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("ORDER_STATUS",BaseVO.OP_STRING);
    	this.addField("FOCUS_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("FOCUS_DATE", "yyyy-MM-dd");
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ORDER_NO",BaseVO.OP_STRING);
    	this.addField("APPLY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("AMOUNT",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("TRANS_TYPE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ORDER_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("RETURN_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("RETURN_DATE", "yyyy-MM-dd");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PRODUCE_DATE",BaseVO.OP_STRING);
    	this.addField("FINAL_CHECK_STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("ORDER_ID","COMMON");
    	this.setVOTableName("SE_BU_RETURN_ORDER");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setOrderStatus(String OrderStatus){
		this.setInternal("ORDER_STATUS" ,OrderStatus );
	}


	public String getOrderStatus(){
		return (String)this.getInternal("ORDER_STATUS");
	}
	public void setFocusDate(Date FocusDate){
		this.setInternal("FOCUS_DATE" ,FocusDate );
	}


	public Date getFocusDate(){
		return (Date)this.getInternal("FOCUS_DATE");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setOrderNo(String OrderNo){
		this.setInternal("ORDER_NO" ,OrderNo );
	}


	public String getOrderNo(){
		return (String)this.getInternal("ORDER_NO");
	}
	public void setApplyDate(Date ApplyDate){
		this.setInternal("APPLY_DATE" ,ApplyDate );
	}


	public Date getApplyDate(){
		return (Date)this.getInternal("APPLY_DATE");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setAmount(String Amount){
		this.setInternal("AMOUNT" ,Amount );
	}


	public String getAmount(){
		return (String)this.getInternal("AMOUNT");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setTransType(String TransType){
		this.setInternal("TRANS_TYPE" ,TransType );
	}


	public String getTransType(){
		return (String)this.getInternal("TRANS_TYPE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setOrderId(String OrderId){
		this.setInternal("ORDER_ID" ,OrderId );
	}


	public String getOrderId(){
		return (String)this.getInternal("ORDER_ID");
	}
	public void setReturnDate(Date ReturnDate){
		this.setInternal("RETURN_DATE" ,ReturnDate );
	}


	public Date getReturnDate(){
		return (Date)this.getInternal("RETURN_DATE");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setProduceDate(String ProduceDate){
		this.setInternal("PRODUCE_DATE" ,ProduceDate );
	}


	public String getProduceDate(){
		return (String)this.getInternal("PRODUCE_DATE");
	}
	public void setFinalCheckStatus(String FinalCheckStatus){
		this.setInternal("FINAL_CHECK_STATUS" ,FinalCheckStatus );
	}


	public String getFinalCheckStatus(){
		return (String)this.getInternal("FINAL_CHECK_STATUS");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
}


package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuTransCostsBalanceVO extends BaseVO{
    public PtBuTransCostsBalanceVO(){
    	//配件运费调整结算VO
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("CARRIER_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("CARRIER_CODE",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("ACTUL_COSTS",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("BALANCE_STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("BALANCE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("BALANCE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("PLAN_COSTS",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("TRANS_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("SHIP_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("CARRIER_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("TRANS_ID","COMMON");
		//设置字典类型定义
		this.bindFieldToDic("STATUS","YXBS");//有效标识  
		this.bindFieldToDic("BALANCE_STATUS","YSJSZT");//运费结算状态
    	this.setVOTableName("PT_BU_TRANS_COSTS_BALANCE");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setCarrierName(String CarrierName){
		this.setInternal("CARRIER_NAME" ,CarrierName );
	}


	public String getCarrierName(){
		return (String)this.getInternal("CARRIER_NAME");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setCarrierCode(String CarrierCode){
		this.setInternal("CARRIER_CODE" ,CarrierCode );
	}


	public String getCarrierCode(){
		return (String)this.getInternal("CARRIER_CODE");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setActulCosts(String ActulCosts){
		this.setInternal("ACTUL_COSTS" ,ActulCosts );
	}


	public String getActulCosts(){
		return (String)this.getInternal("ACTUL_COSTS");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setBalanceStatus(String BalanceStatus){
		this.setInternal("BALANCE_STATUS" ,BalanceStatus );
	}


	public String getBalanceStatus(){
		return (String)this.getInternal("BALANCE_STATUS");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setBalanceTime(Date BalanceTime){
		this.setInternal("BALANCE_TIME" ,BalanceTime );
	}


	public Date getBalanceTime(){
		return (Date)this.getInternal("BALANCE_TIME");
	}
	public void setPlanCosts(String PlanCosts){
		this.setInternal("PLAN_COSTS" ,PlanCosts );
	}


	public String getPlanCosts(){
		return (String)this.getInternal("PLAN_COSTS");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setTransId(String TransId){
		this.setInternal("TRANS_ID" ,TransId );
	}


	public String getTransId(){
		return (String)this.getInternal("TRANS_ID");
	}
	public void setShipId(String ShipId){
		this.setInternal("SHIP_ID" ,ShipId );
	}


	public String getShipId(){
		return (String)this.getInternal("SHIP_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setCarrierId(String CarrierId){
		this.setInternal("CARRIER_ID" ,CarrierId );
	}


	public String getCarrierId(){
		return (String)this.getInternal("CARRIER_ID");
	}
}

package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuClaimSettleVO extends BaseVO{
    public SeBuClaimSettleVO(){
    	//设置字段信息
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("SUMMARY",BaseVO.OP_STRING);
    	this.addField("LAST_TOTAL_COST",BaseVO.OP_STRING);
    	this.addField("RECEIVE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("RECEIVE_TIME", "yyyy-MM-dd");
    	this.addField("CAR_AWARD",BaseVO.OP_STRING);
    	this.addField("MATERIALCOST_REDUCE",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("INVOICE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("RECIPIENTS",BaseVO.OP_STRING);
    	this.addField("ADJUST_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("ADJUST_TIME", "yyyy-MM-dd");
    	this.addField("SETTLE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SETTLE_DATE", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("SETTLE_STATUS",BaseVO.OP_STRING);
    	this.addField("OTHERS_ADJUST_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("OTHERS_ADJUST_TIME", "yyyy-MM-dd");
    	this.addField("SETTLE_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("MANUALLY_COST",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("SETTLE_TYPE",BaseVO.OP_STRING);
    	this.addField("POLICY_ADJUST_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("POLICY_ADJUST_TIME", "yyyy-MM-dd");
    	this.addField("ADJUST_USER",BaseVO.OP_STRING);
    	this.addField("PAYMENT_AMOUT",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("TEL",BaseVO.OP_STRING);
    	this.addField("POLICY_ADJUST_USER",BaseVO.OP_STRING);
    	this.addField("PART_MATERIAL_COSTS",BaseVO.OP_STRING);
    	this.addField("CASH_GIFT",BaseVO.OP_STRING);
    	this.addField("EXPRESS_NO",BaseVO.OP_STRING);
    	this.addField("OTHERS_ADJUST_USER",BaseVO.OP_STRING);
    	this.addField("COSTS",BaseVO.OP_STRING);
    	this.addField("INVOICE_NO",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("POLICY_SUP",BaseVO.OP_STRING);
    	this.addField("ADDRESS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("AP_COSTS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("INVOICE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("INVOICE_DATE", "yyyy-MM-dd");
    	this.addField("ADJUST_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("ADJUST_DATE", "yyyy-MM-dd");
    	this.addField("SETTLE_NO",BaseVO.OP_STRING);
    	this.addField("COSTS_TYPE",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("RE_COSTS",BaseVO.OP_STRING);
    	this.addField("OTHERS",BaseVO.OP_STRING);
		this.bindFieldToSequence("SETTLE_ID","COMMON");
    	this.setVOTableName("SE_BU_CLAIM_SETTLE");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setSummary(String Summary){
		this.setInternal("SUMMARY" ,Summary );
	}


	public String getSummary(){
		return (String)this.getInternal("SUMMARY");
	}
	public void setLastTotalCost(String LastTotalCost){
		this.setInternal("LAST_TOTAL_COST" ,LastTotalCost );
	}


	public String getLastTotalCost(){
		return (String)this.getInternal("LAST_TOTAL_COST");
	}
	public void setReceiveTime(Date ReceiveTime){
		this.setInternal("RECEIVE_TIME" ,ReceiveTime );
	}


	public Date getReceiveTime(){
		return (Date)this.getInternal("RECEIVE_TIME");
	}
	public void setCarAward(String CarAward){
		this.setInternal("CAR_AWARD" ,CarAward );
	}


	public String getCarAward(){
		return (String)this.getInternal("CAR_AWARD");
	}
	public void setMaterialcostReduce(String MaterialcostReduce){
		this.setInternal("MATERIALCOST_REDUCE" ,MaterialcostReduce );
	}


	public String getMaterialcostReduce(){
		return (String)this.getInternal("MATERIALCOST_REDUCE");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setInvoiceAmount(String InvoiceAmount){
		this.setInternal("INVOICE_AMOUNT" ,InvoiceAmount );
	}


	public String getInvoiceAmount(){
		return (String)this.getInternal("INVOICE_AMOUNT");
	}
	public void setRecipients(String Recipients){
		this.setInternal("RECIPIENTS" ,Recipients );
	}


	public String getRecipients(){
		return (String)this.getInternal("RECIPIENTS");
	}
	public void setAdjustTime(Date AdjustTime){
		this.setInternal("ADJUST_TIME" ,AdjustTime );
	}


	public Date getAdjustTime(){
		return (Date)this.getInternal("ADJUST_TIME");
	}
	public void setSettleDate(Date SettleDate){
		this.setInternal("SETTLE_DATE" ,SettleDate );
	}


	public Date getSettleDate(){
		return (Date)this.getInternal("SETTLE_DATE");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setSettleStatus(String SettleStatus){
		this.setInternal("SETTLE_STATUS" ,SettleStatus );
	}


	public String getSettleStatus(){
		return (String)this.getInternal("SETTLE_STATUS");
	}
	public void setOthersAdjustTime(Date OthersAdjustTime){
		this.setInternal("OTHERS_ADJUST_TIME" ,OthersAdjustTime );
	}


	public Date getOthersAdjustTime(){
		return (Date)this.getInternal("OTHERS_ADJUST_TIME");
	}
	public void setSettleId(String SettleId){
		this.setInternal("SETTLE_ID" ,SettleId );
	}


	public String getSettleId(){
		return (String)this.getInternal("SETTLE_ID");
	}
	public void setManuallyCost(String ManuallyCost){
		this.setInternal("MANUALLY_COST" ,ManuallyCost );
	}


	public String getManuallyCost(){
		return (String)this.getInternal("MANUALLY_COST");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setSettleType(String SettleType){
		this.setInternal("SETTLE_TYPE" ,SettleType );
	}


	public String getSettleType(){
		return (String)this.getInternal("SETTLE_TYPE");
	}
	public void setPolicyAdjustTime(Date PolicyAdjustTime){
		this.setInternal("POLICY_ADJUST_TIME" ,PolicyAdjustTime );
	}


	public Date getPolicyAdjustTime(){
		return (Date)this.getInternal("POLICY_ADJUST_TIME");
	}
	public void setAdjustUser(String AdjustUser){
		this.setInternal("ADJUST_USER" ,AdjustUser );
	}


	public String getAdjustUser(){
		return (String)this.getInternal("ADJUST_USER");
	}
	public void setPaymentAmout(String PaymentAmout){
		this.setInternal("PAYMENT_AMOUT" ,PaymentAmout );
	}


	public String getPaymentAmout(){
		return (String)this.getInternal("PAYMENT_AMOUT");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setTel(String Tel){
		this.setInternal("TEL" ,Tel );
	}


	public String getTel(){
		return (String)this.getInternal("TEL");
	}
	public void setPolicyAdjustUser(String PolicyAdjustUser){
		this.setInternal("POLICY_ADJUST_USER" ,PolicyAdjustUser );
	}


	public String getPolicyAdjustUser(){
		return (String)this.getInternal("POLICY_ADJUST_USER");
	}
	public void setPartMaterialCosts(String PartMaterialCosts){
		this.setInternal("PART_MATERIAL_COSTS" ,PartMaterialCosts );
	}


	public String getPartMaterialCosts(){
		return (String)this.getInternal("PART_MATERIAL_COSTS");
	}
	public void setCashGift(String CashGift){
		this.setInternal("CASH_GIFT" ,CashGift );
	}


	public String getCashGift(){
		return (String)this.getInternal("CASH_GIFT");
	}
	public void setExpressNo(String ExpressNo){
		this.setInternal("EXPRESS_NO" ,ExpressNo );
	}


	public String getExpressNo(){
		return (String)this.getInternal("EXPRESS_NO");
	}
	public void setOthersAdjustUser(String OthersAdjustUser){
		this.setInternal("OTHERS_ADJUST_USER" ,OthersAdjustUser );
	}


	public String getOthersAdjustUser(){
		return (String)this.getInternal("OTHERS_ADJUST_USER");
	}
	public void setCosts(String Costs){
		this.setInternal("COSTS" ,Costs );
	}


	public String getCosts(){
		return (String)this.getInternal("COSTS");
	}
	public void setInvoiceNo(String InvoiceNo){
		this.setInternal("INVOICE_NO" ,InvoiceNo );
	}


	public String getInvoiceNo(){
		return (String)this.getInternal("INVOICE_NO");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setPolicySup(String PolicySup){
		this.setInternal("POLICY_SUP" ,PolicySup );
	}


	public String getPolicySup(){
		return (String)this.getInternal("POLICY_SUP");
	}
	public void setAddress(String Address){
		this.setInternal("ADDRESS" ,Address );
	}


	public String getAddress(){
		return (String)this.getInternal("ADDRESS");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setApCosts(String ApCosts){
		this.setInternal("AP_COSTS" ,ApCosts );
	}


	public String getApCosts(){
		return (String)this.getInternal("AP_COSTS");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setInvoiceDate(Date InvoiceDate){
		this.setInternal("INVOICE_DATE" ,InvoiceDate );
	}


	public Date getInvoiceDate(){
		return (Date)this.getInternal("INVOICE_DATE");
	}
	public void setAdjustDate(Date AdjustDate){
		this.setInternal("ADJUST_DATE" ,AdjustDate );
	}


	public Date getAdjustDate(){
		return (Date)this.getInternal("ADJUST_DATE");
	}
	public void setSettleNo(String SettleNo){
		this.setInternal("SETTLE_NO" ,SettleNo );
	}


	public String getSettleNo(){
		return (String)this.getInternal("SETTLE_NO");
	}
	public void setCostsType(String CostsType){
		this.setInternal("COSTS_TYPE" ,CostsType );
	}


	public String getCostsType(){
		return (String)this.getInternal("COSTS_TYPE");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setReCosts(String ReCosts){
		this.setInternal("RE_COSTS" ,ReCosts );
	}


	public String getReCosts(){
		return (String)this.getInternal("RE_COSTS");
	}
	public void setOthers(String Others){
		this.setInternal("OTHERS" ,Others );
	}


	public String getOthers(){
		return (String)this.getInternal("OTHERS");
	}
}


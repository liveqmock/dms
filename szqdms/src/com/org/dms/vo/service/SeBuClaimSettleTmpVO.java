package com.org.dms.vo.service;

import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuClaimSettleTmpVO extends BaseVO{
    public SeBuClaimSettleTmpVO(){
    	//设置字段信息
    	this.addField("SE_OTHERS",BaseVO.OP_STRING);
    	this.addField("MA_COSTS",BaseVO.OP_STRING);
    	this.addField("MA_CAR_AWARD",BaseVO.OP_STRING);
    	this.addField("MATERIALCOST_REDUCE",BaseVO.OP_STRING);
    	this.addField("SE_CASH_GIFT",BaseVO.OP_STRING);
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("SETTLE_DATE",BaseVO.OP_STRING);
    	this.addField("MA_POLICY_SUP",BaseVO.OP_STRING);
    	this.addField("SE_SUMMARY",BaseVO.OP_STRING);
    	this.addField("SE_CAR_AWARD",BaseVO.OP_STRING);
    	this.addField("MA_AP_COSTS",BaseVO.OP_STRING);
    	this.addField("MA_CASH_GIFT",BaseVO.OP_STRING);
    	this.addField("SETTLE_TYPE",BaseVO.OP_STRING);
    	this.addField("ROW_NUM",BaseVO.OP_STRING);
    	this.addField("MA_SUMMARY",BaseVO.OP_STRING);
    	this.addField("PART_MATERIAL_COSTS",BaseVO.OP_STRING);
    	this.addField("SE_COSTS",BaseVO.OP_STRING);
    	this.addField("SE_REMARKS",BaseVO.OP_STRING);
    	this.addField("INVOICE_NO",BaseVO.OP_STRING);
    	this.addField("MA_REMARKS",BaseVO.OP_STRING);
    	this.addField("SE_POLICY_SUP",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("INVOICE_DATE",BaseVO.OP_STRING);
    	this.addField("SETTLE_NO",BaseVO.OP_STRING);
    	this.addField("MA_OTHERS",BaseVO.OP_STRING);
    	this.addField("SE_AP_COSTS",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("SE_RE_COSTS",BaseVO.OP_STRING);
    	this.addField("MA_REBATE",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("SE_BU_CLAIM_SETTLE_TMP");
}
	public void setSeOthers(String SeOthers){
		this.setInternal("SE_OTHERS" ,SeOthers );
	}


	public String getSeOthers(){
		return (String)this.getInternal("SE_OTHERS");
	}
	public void setMaCosts(String MaCosts){
		this.setInternal("MA_COSTS" ,MaCosts );
	}


	public String getMaCosts(){
		return (String)this.getInternal("MA_COSTS");
	}
	public void setMaCarAward(String MaCarAward){
		this.setInternal("MA_CAR_AWARD" ,MaCarAward );
	}


	public String getMaCarAward(){
		return (String)this.getInternal("MA_CAR_AWARD");
	}
	public void setMaterialcostReduce(String MaterialcostReduce){
		this.setInternal("MATERIALCOST_REDUCE" ,MaterialcostReduce );
	}


	public String getMaterialcostReduce(){
		return (String)this.getInternal("MATERIALCOST_REDUCE");
	}
	public void setSeCashGift(String SeCashGift){
		this.setInternal("SE_CASH_GIFT" ,SeCashGift );
	}


	public String getSeCashGift(){
		return (String)this.getInternal("SE_CASH_GIFT");
	}
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
	}
	public void setSettleDate(String SettleDate){
		this.setInternal("SETTLE_DATE" ,SettleDate );
	}


	public String getSettleDate(){
		return (String)this.getInternal("SETTLE_DATE");
	}
	public void setMaPolicySup(String MaPolicySup){
		this.setInternal("MA_POLICY_SUP" ,MaPolicySup );
	}


	public String getMaPolicySup(){
		return (String)this.getInternal("MA_POLICY_SUP");
	}
	public void setSeSummary(String SeSummary){
		this.setInternal("SE_SUMMARY" ,SeSummary );
	}


	public String getSeSummary(){
		return (String)this.getInternal("SE_SUMMARY");
	}
	public void setSeCarAward(String SeCarAward){
		this.setInternal("SE_CAR_AWARD" ,SeCarAward );
	}


	public String getSeCarAward(){
		return (String)this.getInternal("SE_CAR_AWARD");
	}
	public void setMaApCosts(String MaApCosts){
		this.setInternal("MA_AP_COSTS" ,MaApCosts );
	}


	public String getMaApCosts(){
		return (String)this.getInternal("MA_AP_COSTS");
	}
	public void setMaCashGift(String MaCashGift){
		this.setInternal("MA_CASH_GIFT" ,MaCashGift );
	}


	public String getMaCashGift(){
		return (String)this.getInternal("MA_CASH_GIFT");
	}
	public void setSettleType(String SettleType){
		this.setInternal("SETTLE_TYPE" ,SettleType );
	}


	public String getSettleType(){
		return (String)this.getInternal("SETTLE_TYPE");
	}
	public void setRowNum(String RowNum){
		this.setInternal("ROW_NUM" ,RowNum );
	}


	public String getRowNum(){
		return (String)this.getInternal("ROW_NUM");
	}
	public void setMaSummary(String MaSummary){
		this.setInternal("MA_SUMMARY" ,MaSummary );
	}


	public String getMaSummary(){
		return (String)this.getInternal("MA_SUMMARY");
	}
	public void setPartMaterialCosts(String PartMaterialCosts){
		this.setInternal("PART_MATERIAL_COSTS" ,PartMaterialCosts );
	}


	public String getPartMaterialCosts(){
		return (String)this.getInternal("PART_MATERIAL_COSTS");
	}
	public void setSeCosts(String SeCosts){
		this.setInternal("SE_COSTS" ,SeCosts );
	}


	public String getSeCosts(){
		return (String)this.getInternal("SE_COSTS");
	}
	public void setSeRemarks(String SeRemarks){
		this.setInternal("SE_REMARKS" ,SeRemarks );
	}


	public String getSeRemarks(){
		return (String)this.getInternal("SE_REMARKS");
	}
	public void setInvoiceNo(String InvoiceNo){
		this.setInternal("INVOICE_NO" ,InvoiceNo );
	}


	public String getInvoiceNo(){
		return (String)this.getInternal("INVOICE_NO");
	}
	public void setMaRemarks(String MaRemarks){
		this.setInternal("MA_REMARKS" ,MaRemarks );
	}


	public String getMaRemarks(){
		return (String)this.getInternal("MA_REMARKS");
	}
	public void setSePolicySup(String SePolicySup){
		this.setInternal("SE_POLICY_SUP" ,SePolicySup );
	}


	public String getSePolicySup(){
		return (String)this.getInternal("SE_POLICY_SUP");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
	public void setInvoiceDate(String InvoiceDate){
		this.setInternal("INVOICE_DATE" ,InvoiceDate );
	}


	public String getInvoiceDate(){
		return (String)this.getInternal("INVOICE_DATE");
	}
	public void setSettleNo(String SettleNo){
		this.setInternal("SETTLE_NO" ,SettleNo );
	}


	public String getSettleNo(){
		return (String)this.getInternal("SETTLE_NO");
	}
	public void setMaOthers(String MaOthers){
		this.setInternal("MA_OTHERS" ,MaOthers );
	}


	public String getMaOthers(){
		return (String)this.getInternal("MA_OTHERS");
	}
	public void setSeApCosts(String SeApCosts){
		this.setInternal("SE_AP_COSTS" ,SeApCosts );
	}


	public String getSeApCosts(){
		return (String)this.getInternal("SE_AP_COSTS");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
	public void setSeReCosts(String SeReCosts){
		this.setInternal("SE_RE_COSTS" ,SeReCosts );
	}


	public String getSeReCosts(){
		return (String)this.getInternal("SE_RE_COSTS");
	}
	public void setMaRebate(String MaRebate){
		this.setInternal("MA_REBATE" ,MaRebate );
	}


	public String getMaRebate(){
		return (String)this.getInternal("MA_REBATE");
	}
}

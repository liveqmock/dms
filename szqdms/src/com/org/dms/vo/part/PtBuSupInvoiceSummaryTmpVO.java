package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuSupInvoiceSummaryTmpVO extends BaseVO{
    public PtBuSupInvoiceSummaryTmpVO(){
    	//设置字段信息
    	this.addField("TMP_NO",BaseVO.OP_STRING);
    	this.addField("ACCOUNT_TYPE",BaseVO.OP_STRING);
    	this.addField("SETTLE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("SELECT_MONTH",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("PT_BU_SUP_INVOICE_SUMMARY_TMP");
}
	public void setTmpNo(String TmpNo){
		this.setInternal("TMP_NO" ,TmpNo );
	}


	public String getTmpNo(){
		return (String)this.getInternal("TMP_NO");
	}
	public void setAccountType(String AccountType){
		this.setInternal("ACCOUNT_TYPE" ,AccountType );
	}


	public String getAccountType(){
		return (String)this.getInternal("ACCOUNT_TYPE");
	}
	public void setSettleAmount(String SettleAmount){
		this.setInternal("SETTLE_AMOUNT" ,SettleAmount );
	}


	public String getSettleAmount(){
		return (String)this.getInternal("SETTLE_AMOUNT");
	}
	public void setSelectMonth(String SelectMonth){
		this.setInternal("SELECT_MONTH" ,SelectMonth );
	}


	public String getSelectMonth(){
		return (String)this.getInternal("SELECT_MONTH");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
}

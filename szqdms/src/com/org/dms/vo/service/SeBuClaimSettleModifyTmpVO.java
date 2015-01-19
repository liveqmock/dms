package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuClaimSettleModifyTmpVO extends BaseVO{
    public SeBuClaimSettleModifyTmpVO(){
    	//设置字段信息
    	this.addField("ROW_NO",BaseVO.OP_STRING);
    	this.addField("SETTLE_NO",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("IF_PAY",BaseVO.OP_STRING);
    	this.addField("PAY_DATE",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("PAY_AMOUNT",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("SE_BU_CLAIM_SETTLE_MODIFY_TMP");
}
	public void setRowNo(String RowNo){
		this.setInternal("ROW_NO" ,RowNo );
	}


	public String getRowNo(){
		return (String)this.getInternal("ROW_NO");
	}
	public void setSettleNo(String SettleNo){
		this.setInternal("SETTLE_NO" ,SettleNo );
	}


	public String getSettleNo(){
		return (String)this.getInternal("SETTLE_NO");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setIfPay(String IfPay){
		this.setInternal("IF_PAY" ,IfPay );
	}


	public String getIfPay(){
		return (String)this.getInternal("IF_PAY");
	}
	public void setPayDate(String PayDate){
		this.setInternal("PAY_DATE" ,PayDate );
	}


	public String getPayDate(){
		return (String)this.getInternal("PAY_DATE");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setPayAmount(String PayAmount){
		this.setInternal("PAY_AMOUNT" ,PayAmount );
	}


	public String getPayAmount(){
		return (String)this.getInternal("PAY_AMOUNT");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
}
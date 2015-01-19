package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaClaimCycleSetTmpVO extends BaseVO{
    public PtBaClaimCycleSetTmpVO(){
    	//�����ֶ���Ϣ
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("TMP_NO",BaseVO.OP_STRING);
    	this.addField("CLAIM_MONTH",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("EXTENSION_MONTH",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("PT_BA_CLAIM_CYCLE_SET_TMP");
}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setTmpNo(String TmpNo){
		this.setInternal("TMP_NO" ,TmpNo );
	}


	public String getTmpNo(){
		return (String)this.getInternal("TMP_NO");
	}
	public void setClaimMonth(String ClaimMonth){
		this.setInternal("CLAIM_MONTH" ,ClaimMonth );
	}


	public String getClaimMonth(){
		return (String)this.getInternal("CLAIM_MONTH");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setExtensionMonth(String ExtensionMonth){
		this.setInternal("EXTENSION_MONTH" ,ExtensionMonth );
	}


	public String getExtensionMonth(){
		return (String)this.getInternal("EXTENSION_MONTH");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
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

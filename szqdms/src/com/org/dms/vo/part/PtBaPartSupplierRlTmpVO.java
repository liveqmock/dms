package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaPartSupplierRlTmpVO extends BaseVO{
    public PtBaPartSupplierRlTmpVO(){
    	//ÉèÖÃ×Ö¶ÎÐÅÏ¢
    	this.addField("TMP_NO",BaseVO.OP_STRING);
    	this.addField("PART_STATUS",BaseVO.OP_STRING);
    	this.addField("APPLY_CYCLE",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("PT_BA_PART_SUPPLIER_RL_TMP");
}
	public void setTmpNo(String TmpNo){
		this.setInternal("TMP_NO" ,TmpNo );
	}


	public String getTmpNo(){
		return (String)this.getInternal("TMP_NO");
	}
	public void setPartStatus(String PartStatus){
		this.setInternal("PART_STATUS" ,PartStatus );
	}


	public String getPartStatus(){
		return (String)this.getInternal("PART_STATUS");
	}
	public void setApplyCycle(String ApplyCycle){
		this.setInternal("APPLY_CYCLE" ,ApplyCycle );
	}


	public String getApplyCycle(){
		return (String)this.getInternal("APPLY_CYCLE");
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

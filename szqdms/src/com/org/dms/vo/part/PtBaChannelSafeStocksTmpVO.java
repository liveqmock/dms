package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;

public class PtBaChannelSafeStocksTmpVO extends BaseVO{
    public PtBaChannelSafeStocksTmpVO(){
    	//安全库存导入临时表VO
    	this.addField("ROW_NUM",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("UPPER_LIMIT",BaseVO.OP_STRING);
    	this.addField("LOWER_LIMIT",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("PT_BA_CHANNEL_SAFESTOCK_TMP");
}
    public void setrowNum(String rowNum){
		this.setInternal("ROW_NUM" ,rowNum );
	}

	public String getrowNum(){
		return (String)this.getInternal("ROW_NUM");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
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
	public void setUpperLimit(String UpperLimit){
		this.setInternal("UPPER_LIMIT" ,UpperLimit );
	}


	public String getUpperLimit(){
		return (String)this.getInternal("UPPER_LIMIT");
	}
	public void setLowerLimit(String LowerLimit){
		this.setInternal("LOWER_LIMIT" ,LowerLimit );
	}


	public String getLowerLimit(){
		return (String)this.getInternal("LOWER_LIMIT");
	}
}

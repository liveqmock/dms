package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaWarehouseKeeperTmpVO extends BaseVO{
    public PtBaWarehouseKeeperTmpVO(){
    	//�����ֶ���Ϣ
    	this.addField("TMP_NO",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("KEEP_MAN_NAME",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("KEEP_MAN_ACOUNT",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("WAREHOUSE_NAME",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_CODE",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("PT_BA_WAREHOUSE_KEEPER_TMP");
}
	public void setTmpNo(String TmpNo){
		this.setInternal("TMP_NO" ,TmpNo );
	}


	public String getTmpNo(){
		return (String)this.getInternal("TMP_NO");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setKeepManName(String KeepManName){
		this.setInternal("KEEP_MAN_NAME" ,KeepManName );
	}


	public String getKeepManName(){
		return (String)this.getInternal("KEEP_MAN_NAME");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setKeepManAcount(String KeepManAcount){
		this.setInternal("KEEP_MAN_ACOUNT" ,KeepManAcount );
	}


	public String getKeepManAcount(){
		return (String)this.getInternal("KEEP_MAN_ACOUNT");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
	public void setWarehouseName(String WarehouseName){
		this.setInternal("WAREHOUSE_NAME" ,WarehouseName );
	}


	public String getWarehouseName(){
		return (String)this.getInternal("WAREHOUSE_NAME");
	}
	public void setWarehouseCode(String WarehouseCode){
		this.setInternal("WAREHOUSE_CODE" ,WarehouseCode );
	}


	public String getWarehouseCode(){
		return (String)this.getInternal("WAREHOUSE_CODE");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
}

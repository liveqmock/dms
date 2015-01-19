package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaInfoTmpVO extends BaseVO{
    public PtBaInfoTmpVO(){
    	//�����ֶ���Ϣ
    	this.addField("IF_SCAN",BaseVO.OP_STRING);
    	this.addField("PART_TYPE",BaseVO.OP_STRING);
    	this.addField("REBATE_TYPE",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("ATTRIBUTE",BaseVO.OP_STRING);
    	this.addField("IF_OIL",BaseVO.OP_STRING);
    	this.addField("ROW_NUM",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PART_STATUS",BaseVO.OP_STRING);
    	this.addField("F_PART_NAME",BaseVO.OP_STRING);
    	this.addField("UNIT",BaseVO.OP_STRING);
    	this.addField("F_POSITION_NAME",BaseVO.OP_STRING);
    	this.addField("DIRECT_TYPE_NAME",BaseVO.OP_STRING);
    	this.addField("BELONG_ASSEMBLY",BaseVO.OP_STRING);
    	this.addField("POSITION_NAME",BaseVO.OP_STRING);
    	this.addField("MIN_UNIT",BaseVO.OP_STRING);
    	this.addField("PART_NO",BaseVO.OP_STRING);
    	this.addField("MIN_PACK",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("IF_OUT",BaseVO.OP_STRING);
    	this.addField("SPE_NAME",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("IF_ASSEMBLY",BaseVO.OP_STRING);
    	this.addField("F_PART_CODE",BaseVO.OP_STRING);
    	this.addField("IF_SUPLY",BaseVO.OP_STRING);
		this.bindFieldToSequence("PART_ID","COMMON");
    	this.setVOTableName("PT_BA_INFO_TMP");
}
	public void setIfScan(String IfScan){
		this.setInternal("IF_SCAN" ,IfScan );
	}


	public String getIfScan(){
		return (String)this.getInternal("IF_SCAN");
	}
	public void setPartType(String PartType){
		this.setInternal("PART_TYPE" ,PartType );
	}


	public String getPartType(){
		return (String)this.getInternal("PART_TYPE");
	}
	public void setRebateType(String RebateType){
		this.setInternal("REBATE_TYPE" ,RebateType );
	}


	public String getRebateType(){
		return (String)this.getInternal("REBATE_TYPE");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setAttribute(String Attribute){
		this.setInternal("ATTRIBUTE" ,Attribute );
	}


	public String getAttribute(){
		return (String)this.getInternal("ATTRIBUTE");
	}
	public void setIfOil(String IfOil){
		this.setInternal("IF_OIL" ,IfOil );
	}


	public String getIfOil(){
		return (String)this.getInternal("IF_OIL");
	}
	public void setRowNum(String RowNum){
		this.setInternal("ROW_NUM" ,RowNum );
	}


	public String getRowNum(){
		return (String)this.getInternal("ROW_NUM");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setPartStatus(String PartStatus){
		this.setInternal("PART_STATUS" ,PartStatus );
	}


	public String getPartStatus(){
		return (String)this.getInternal("PART_STATUS");
	}
	public void setFPartName(String FPartName){
		this.setInternal("F_PART_NAME" ,FPartName );
	}


	public String getFPartName(){
		return (String)this.getInternal("F_PART_NAME");
	}
	public void setUnit(String Unit){
		this.setInternal("UNIT" ,Unit );
	}


	public String getUnit(){
		return (String)this.getInternal("UNIT");
	}
	public void setFPositionName(String FPositionName){
		this.setInternal("F_POSITION_NAME" ,FPositionName );
	}


	public String getFPositionName(){
		return (String)this.getInternal("F_POSITION_NAME");
	}
	public void setDirectTypeName(String DirectTypeName){
		this.setInternal("DIRECT_TYPE_NAME" ,DirectTypeName );
	}


	public String getDirectTypeName(){
		return (String)this.getInternal("DIRECT_TYPE_NAME");
	}
	public void setBelongAssembly(String BelongAssembly){
		this.setInternal("BELONG_ASSEMBLY" ,BelongAssembly );
	}


	public String getBelongAssembly(){
		return (String)this.getInternal("BELONG_ASSEMBLY");
	}
	public void setPositionName(String PositionName){
		this.setInternal("POSITION_NAME" ,PositionName );
	}


	public String getPositionName(){
		return (String)this.getInternal("POSITION_NAME");
	}
	public void setMinUnit(String MinUnit){
		this.setInternal("MIN_UNIT" ,MinUnit );
	}


	public String getMinUnit(){
		return (String)this.getInternal("MIN_UNIT");
	}
	public void setPartNo(String PartNo){
		this.setInternal("PART_NO" ,PartNo );
	}


	public String getPartNo(){
		return (String)this.getInternal("PART_NO");
	}
	public void setMinPack(String MinPack){
		this.setInternal("MIN_PACK" ,MinPack );
	}


	public String getMinPack(){
		return (String)this.getInternal("MIN_PACK");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setIfOut(String IfOut){
		this.setInternal("IF_OUT" ,IfOut );
	}


	public String getIfOut(){
		return (String)this.getInternal("IF_OUT");
	}
	public void setSpeName(String SpeName){
		this.setInternal("SPE_NAME" ,SpeName );
	}


	public String getSpeName(){
		return (String)this.getInternal("SPE_NAME");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
	public void setIfAssembly(String IfAssembly){
		this.setInternal("IF_ASSEMBLY" ,IfAssembly );
	}


	public String getIfAssembly(){
		return (String)this.getInternal("IF_ASSEMBLY");
	}
	public void setFPartCode(String FPartCode){
		this.setInternal("F_PART_CODE" ,FPartCode );
	}


	public String getFPartCode(){
		return (String)this.getInternal("F_PART_CODE");
	}
	public void setIfSuply(String IfSuply){
		this.setInternal("IF_SUPLY" ,IfSuply );
	}


	public String getIfSuply(){
		return (String)this.getInternal("IF_SUPLY");
	}
}

package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuStockInContinualVO extends BaseVO{
    public PtBuStockInContinualVO(){
    	//�����ֶ���Ϣ
    	this.addField("IN_COUNT_TMP",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("IN_COUNT",BaseVO.OP_STRING);
    	this.addField("IN_ID",BaseVO.OP_STRING);
    	this.addField("PLAN_PRICE",BaseVO.OP_STRING);
    	this.addField("KEEPER_MAN",BaseVO.OP_STRING);
    	this.addField("CONTINUAL_NO",BaseVO.OP_STRING);
    	this.addField("IN_NO",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("IN_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("IN_DATE", "yyyy-MM-dd");
    	this.addField("CONTINUAL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CREATE_MAN",BaseVO.OP_STRING);
		this.bindFieldToSequence("CONTINUAL_ID","COMMON");
    	this.setVOTableName("PT_BU_STOCK_IN_CONTINUAL");
}
	public void setInCountTmp(String InCountTmp){
		this.setInternal("IN_COUNT_TMP" ,InCountTmp );
	}


	public String getInCountTmp(){
		return (String)this.getInternal("IN_COUNT_TMP");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setInCount(String InCount){
		this.setInternal("IN_COUNT" ,InCount );
	}


	public String getInCount(){
		return (String)this.getInternal("IN_COUNT");
	}
	public void setInId(String InId){
		this.setInternal("IN_ID" ,InId );
	}


	public String getInId(){
		return (String)this.getInternal("IN_ID");
	}
	public void setPlanPrice(String PlanPrice){
		this.setInternal("PLAN_PRICE" ,PlanPrice );
	}


	public String getPlanPrice(){
		return (String)this.getInternal("PLAN_PRICE");
	}
	public void setKeeperMan(String KeeperMan){
		this.setInternal("KEEPER_MAN" ,KeeperMan );
	}


	public String getKeeperMan(){
		return (String)this.getInternal("KEEPER_MAN");
	}
	public void setContinualNo(String ContinualNo){
		this.setInternal("CONTINUAL_NO" ,ContinualNo );
	}


	public String getContinualNo(){
		return (String)this.getInternal("CONTINUAL_NO");
	}
	public void setInNo(String InNo){
		this.setInternal("IN_NO" ,InNo );
	}


	public String getInNo(){
		return (String)this.getInternal("IN_NO");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setInDate(Date InDate){
		this.setInternal("IN_DATE" ,InDate );
	}


	public Date getInDate(){
		return (Date)this.getInternal("IN_DATE");
	}
	public void setContinualId(String ContinualId){
		this.setInternal("CONTINUAL_ID" ,ContinualId );
	}


	public String getContinualId(){
		return (String)this.getInternal("CONTINUAL_ID");
	}
	public void setCreateMan(String CreateMan){
		this.setInternal("CREATE_MAN" ,CreateMan );
	}


	public String getCreateMan(){
		return (String)this.getInternal("CREATE_MAN");
	}
}

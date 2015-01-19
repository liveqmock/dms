package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuStockOutContinualVO extends BaseVO{
    public PtBuStockOutContinualVO(){
    	//�����ֶ���Ϣ
    	this.addField("OUT_COUNT",BaseVO.OP_STRING);
    	this.addField("OUT_COUNT_TMP",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("OUT_ID",BaseVO.OP_STRING);
    	this.addField("KEEPER_MAN",BaseVO.OP_STRING);
    	this.addField("CREATE_MAN",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("CONTINUAL_NO",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PLAN_PRICE",BaseVO.OP_STRING);
    	this.addField("OUT_NO",BaseVO.OP_STRING);
    	this.addField("CONTINUAL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("OUT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd");
		this.bindFieldToSequence("CONTINUAL_ID","COMMON");
    	this.setVOTableName("PT_BU_STOCK_OUT_CONTINUAL");
}
	public void setOutCount(String OutCount){
		this.setInternal("OUT_COUNT" ,OutCount );
	}


	public String getOutCount(){
		return (String)this.getInternal("OUT_COUNT");
	}
	public void setOutCountTmp(String OutCountTmp){
		this.setInternal("OUT_COUNT_TMP" ,OutCountTmp );
	}


	public String getOutCountTmp(){
		return (String)this.getInternal("OUT_COUNT_TMP");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setOutId(String OutId){
		this.setInternal("OUT_ID" ,OutId );
	}


	public String getOutId(){
		return (String)this.getInternal("OUT_ID");
	}
	public void setKeeperMan(String KeeperMan){
		this.setInternal("KEEPER_MAN" ,KeeperMan );
	}


	public String getKeeperMan(){
		return (String)this.getInternal("KEEPER_MAN");
	}
	public void setCreateMan(String CreateMan){
		this.setInternal("CREATE_MAN" ,CreateMan );
	}


	public String getCreateMan(){
		return (String)this.getInternal("CREATE_MAN");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setContinualNo(String ContinualNo){
		this.setInternal("CONTINUAL_NO" ,ContinualNo );
	}


	public String getContinualNo(){
		return (String)this.getInternal("CONTINUAL_NO");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setPlanPrice(String PlanPrice){
		this.setInternal("PLAN_PRICE" ,PlanPrice );
	}


	public String getPlanPrice(){
		return (String)this.getInternal("PLAN_PRICE");
	}
	public void setOutNo(String OutNo){
		this.setInternal("OUT_NO" ,OutNo );
	}


	public String getOutNo(){
		return (String)this.getInternal("OUT_NO");
	}
	public void setContinualId(String ContinualId){
		this.setInternal("CONTINUAL_ID" ,ContinualId );
	}


	public String getContinualId(){
		return (String)this.getInternal("CONTINUAL_ID");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setOutDate(Date OutDate){
		this.setInternal("OUT_DATE" ,OutDate );
	}


	public Date getOutDate(){
		return (Date)this.getInternal("OUT_DATE");
	}
}

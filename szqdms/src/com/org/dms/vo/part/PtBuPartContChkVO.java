package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuPartContChkVO extends BaseVO{
    public PtBuPartContChkVO(){
    	//�����ֶ���Ϣ
    	this.addField("IF_IN",BaseVO.OP_STRING);
    	this.addField("CHECK_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("UNIT_PRICES",BaseVO.OP_STRING);
    	this.addField("SUP_NAMES",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
		this.bindFieldToSequence("CHECK_ID","COMMON");
    	this.setVOTableName("PT_BU_PART_CONT_CHK");
}
	public void setIfIn(String IfIn){
		this.setInternal("IF_IN" ,IfIn );
	}


	public String getIfIn(){
		return (String)this.getInternal("IF_IN");
	}
	public void setCheckId(String CheckId){
		this.setInternal("CHECK_ID" ,CheckId );
	}


	public String getCheckId(){
		return (String)this.getInternal("CHECK_ID");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setUnitPrices(String UnitPrices){
		this.setInternal("UNIT_PRICES" ,UnitPrices );
	}


	public String getUnitPrices(){
		return (String)this.getInternal("UNIT_PRICES");
	}
	public void setSupNames(String SupNames){
		this.setInternal("SUP_NAMES" ,SupNames );
	}


	public String getSupNames(){
		return (String)this.getInternal("SUP_NAMES");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
}

package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaStockSafeStocksTmpVO extends BaseVO{
    public PtBaStockSafeStocksTmpVO(){
    	//安全库存导入临时表VO
    	this.addField("ROW_NUM",BaseVO.OP_STRING);
    	//this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	//this.addField("PART_ID",BaseVO.OP_STRING);
    	//this.addField("STOCK_NAME",BaseVO.OP_STRING);
    	this.addField("STOCK_CODE",BaseVO.OP_STRING);
    	//this.addField("STOCK_ID",BaseVO.OP_STRING);
    	this.addField("UPPER_LIMIT",BaseVO.OP_STRING);
    	this.addField("LOWER_LIMIT",BaseVO.OP_STRING);
    	//this.addField("CREATE_USER",BaseVO.OP_STRING);
    	//this.addField("CREATE_TIME",BaseVO.OP_DATE);
		//this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("PT_BA_STOCK_SAFESTOCKS_TMP");
}
    public void setrowNum(String rowNum){
		this.setInternal("ROW_NUM" ,rowNum );
	}


	public String getrowNum(){
		return (String)this.getInternal("ROW_NUM");
	}
	/**
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}*/
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	/*public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setStockName(String StockName){
		this.setInternal("STOCK_NAME" ,StockName );
	}


	public String getStockName(){
		return (String)this.getInternal("STOCK_NAME");
	}*/
	public void setStockCode(String StockCode){
		this.setInternal("STOCK_CODE" ,StockCode );
	}


	public String getStockCode(){
		return (String)this.getInternal("STOCK_CODE");
	}
	/*public void setStockId(String StockId){
		this.setInternal("STOCK_ID" ,StockId );
	}

	public String getStockId(){
		return (String)this.getInternal("STOCK_ID");
	}*/
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
	/*public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}*/
}

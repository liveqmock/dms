package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaStockVO extends BaseVO{
    public PtBaStockVO(){
    	//�����ֶ���Ϣ
    	this.addField("STOCK_CODE",BaseVO.OP_STRING);
    	this.addField("STOCK_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("STOCK_NAME",BaseVO.OP_STRING);
    	this.addField("STOCK_STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("STOCK_TYPE",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("STOCK_ID","COMMON");
    	this.setVOTableName("PT_BA_STOCK");
}
	public void setStockCode(String StockCode){
		this.setInternal("STOCK_CODE" ,StockCode );
	}


	public String getStockCode(){
		return (String)this.getInternal("STOCK_CODE");
	}
	public void setStockId(String StockId){
		this.setInternal("STOCK_ID" ,StockId );
	}


	public String getStockId(){
		return (String)this.getInternal("STOCK_ID");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setStockName(String StockName){
		this.setInternal("STOCK_NAME" ,StockName );
	}


	public String getStockName(){
		return (String)this.getInternal("STOCK_NAME");
	}
	public void setStockStatus(String StockStatus){
		this.setInternal("STOCK_STATUS" ,StockStatus );
	}


	public String getStockStatus(){
		return (String)this.getInternal("STOCK_STATUS");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setStockType(String StockType){
		this.setInternal("STOCK_TYPE" ,StockType );
	}


	public String getStockType(){
		return (String)this.getInternal("STOCK_TYPE");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
}

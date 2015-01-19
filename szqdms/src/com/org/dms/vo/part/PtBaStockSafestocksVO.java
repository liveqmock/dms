package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaStockSafestocksVO extends BaseVO{
    public PtBaStockSafestocksVO(){
    	//安全库存管理VO
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("STOCK_CODE",BaseVO.OP_STRING);
    	this.addField("STOCK_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("UPPER_LIMIT",BaseVO.OP_STRING);
    	this.addField("LOWER_LIMIT",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("SAFTY_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("STOCK_NAME",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("SAFTY_ID","COMMON");
		//设置字典类型定义
		this.bindFieldToDic("STATUS","YXBS");//有效标识   
    	this.setVOTableName("PT_BA_STOCK_SAFESTOCKS");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
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
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
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
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setSaftyId(String SaftyId){
		this.setInternal("SAFTY_ID" ,SaftyId );
	}


	public String getSaftyId(){
		return (String)this.getInternal("SAFTY_ID");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setStockName(String StockName){
		this.setInternal("STOCK_NAME" ,StockName );
	}


	public String getStockName(){
		return (String)this.getInternal("STOCK_NAME");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
}
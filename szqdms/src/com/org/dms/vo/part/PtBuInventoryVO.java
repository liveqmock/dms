package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuInventoryVO extends BaseVO{
    public PtBuInventoryVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("INVENTORY_NO",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("WAREHOUSE_ID",BaseVO.OP_STRING);
    	this.addField("INVENTORY_USER",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("END_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	this.addField("WAREHOUSE_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("INVENTORY_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("INVENTORY_STATUS",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CHECK_REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("INVENTORY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("INVENTORY_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("INVENTORY_TYPE",BaseVO.OP_STRING);
		this.bindFieldToSequence("INVENTORY_ID","COMMON");
		
		//设置字典
		this.bindFieldToDic("INVENTORY_STATUS", "PDZT");
		this.bindFieldToDic("INVENTORY_TYPE", "PDLX");
		
    	this.setVOTableName("PT_BU_INVENTORY");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setInventoryNo(String InventoryNo){
		this.setInternal("INVENTORY_NO" ,InventoryNo );
	}


	public String getInventoryNo(){
		return (String)this.getInternal("INVENTORY_NO");
	}
	public void setWarehouseName(String WarehouseName){
		this.setInternal("WAREHOUSE_NAME" ,WarehouseName );
	}


	public String getWarehouseName(){
		return (String)this.getInternal("WAREHOUSE_NAME");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setWarehouseId(String WarehouseId){
		this.setInternal("WAREHOUSE_ID" ,WarehouseId );
	}


	public String getWarehouseId(){
		return (String)this.getInternal("WAREHOUSE_ID");
	}
	public void setInventoryUser(String InventoryUser){
		this.setInternal("INVENTORY_USER" ,InventoryUser );
	}


	public String getInventoryUser(){
		return (String)this.getInternal("INVENTORY_USER");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setEndDate(Date EndDate){
		this.setInternal("END_DATE" ,EndDate );
	}


	public Date getEndDate(){
		return (Date)this.getInternal("END_DATE");
	}
	public void setWarehouseCode(String WarehouseCode){
		this.setInternal("WAREHOUSE_CODE" ,WarehouseCode );
	}


	public String getWarehouseCode(){
		return (String)this.getInternal("WAREHOUSE_CODE");
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
	public void setInventoryId(String InventoryId){
		this.setInternal("INVENTORY_ID" ,InventoryId );
	}


	public String getInventoryId(){
		return (String)this.getInternal("INVENTORY_ID");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setInventoryStatus(String InventoryStatus){
		this.setInternal("INVENTORY_STATUS" ,InventoryStatus );
	}


	public String getInventoryStatus(){
		return (String)this.getInternal("INVENTORY_STATUS");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setCheckRemarks(String CheckRemarks){
		this.setInternal("CHECK_REMARKS" ,CheckRemarks );
	}


	public String getCheckRemarks(){
		return (String)this.getInternal("CHECK_REMARKS");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setInventoryDate(Date InventoryDate){
		this.setInternal("INVENTORY_DATE" ,InventoryDate );
	}


	public Date getInventoryDate(){
		return (Date)this.getInternal("INVENTORY_DATE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setInventoryType(String InventoryType){
		this.setInternal("INVENTORY_TYPE" ,InventoryType );
	}


	public String getInventoryType(){
		return (String)this.getInternal("INVENTORY_TYPE");
	}
}

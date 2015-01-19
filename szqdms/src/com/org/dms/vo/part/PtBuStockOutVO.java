package com.org.dms.vo.part;

import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuStockOutVO extends BaseVO{
    public PtBuStockOutVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("OTHER_OUT_TYPE",BaseVO.OP_STRING);
    	this.addField("OUT_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("WAREHOUSE_NAME",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_ID",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("PRINT_STATUS",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_CODE",BaseVO.OP_STRING);
    	this.addField("OUT_NO",BaseVO.OP_STRING);
    	this.addField("IF_IN_FLAG",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("RECEIVE_WAREHOUSE",BaseVO.OP_STRING);
    	this.addField("LINK_TEL",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("OUT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd");
    	this.addField("PRINT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("PRINT_DATE", "yyyy-MM-dd");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("OUT_STATUS",BaseVO.OP_STRING);
    	this.addField("OUT_TYPE",BaseVO.OP_STRING);
    	this.addField("MOVE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("MOVE_DATE", "yyyy-MM-dd");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ORDER_NO",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("LINK_MAN",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("MOVE_MAN",BaseVO.OP_STRING);
    	this.addField("ORDER_ID",BaseVO.OP_STRING);
    	this.addField("PRINT_MAN",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("OUT_ID","COMMON");
    	this.setVOTableName("PT_BU_STOCK_OUT");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setOtherOutType(String OtherOutType){
		this.setInternal("OTHER_OUT_TYPE" ,OtherOutType );
	}


	public String getOtherOutType(){
		return (String)this.getInternal("OTHER_OUT_TYPE");
	}
	public void setOutId(String OutId){
		this.setInternal("OUT_ID" ,OutId );
	}


	public String getOutId(){
		return (String)this.getInternal("OUT_ID");
	}
	public void setWarehouseName(String WarehouseName){
		this.setInternal("WAREHOUSE_NAME" ,WarehouseName );
	}


	public String getWarehouseName(){
		return (String)this.getInternal("WAREHOUSE_NAME");
	}
	public void setWarehouseId(String WarehouseId){
		this.setInternal("WAREHOUSE_ID" ,WarehouseId );
	}


	public String getWarehouseId(){
		return (String)this.getInternal("WAREHOUSE_ID");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setPrintStatus(String PrintStatus){
		this.setInternal("PRINT_STATUS" ,PrintStatus );
	}


	public String getPrintStatus(){
		return (String)this.getInternal("PRINT_STATUS");
	}
	public void setWarehouseCode(String WarehouseCode){
		this.setInternal("WAREHOUSE_CODE" ,WarehouseCode );
	}


	public String getWarehouseCode(){
		return (String)this.getInternal("WAREHOUSE_CODE");
	}
	public void setOutNo(String OutNo){
		this.setInternal("OUT_NO" ,OutNo );
	}


	public String getOutNo(){
		return (String)this.getInternal("OUT_NO");
	}
	public void setIfInFlag(String IfInFlag){
		this.setInternal("IF_IN_FLAG" ,IfInFlag );
	}


	public String getIfInFlag(){
		return (String)this.getInternal("IF_IN_FLAG");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setReceiveWarehouse(String ReceiveWarehouse){
		this.setInternal("RECEIVE_WAREHOUSE" ,ReceiveWarehouse );
	}


	public String getReceiveWarehouse(){
		return (String)this.getInternal("RECEIVE_WAREHOUSE");
	}
	public void setLinkTel(String LinkTel){
		this.setInternal("LINK_TEL" ,LinkTel );
	}


	public String getLinkTel(){
		return (String)this.getInternal("LINK_TEL");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setOutDate(Date OutDate){
		this.setInternal("OUT_DATE" ,OutDate );
	}


	public Date getOutDate(){
		return (Date)this.getInternal("OUT_DATE");
	}
	public void setPrintDate(Date PrintDate){
		this.setInternal("PRINT_DATE" ,PrintDate );
	}


	public Date getPrintDate(){
		return (Date)this.getInternal("PRINT_DATE");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setOutStatus(String OutStatus){
		this.setInternal("OUT_STATUS" ,OutStatus );
	}


	public String getOutStatus(){
		return (String)this.getInternal("OUT_STATUS");
	}
	public void setOutType(String OutType){
		this.setInternal("OUT_TYPE" ,OutType );
	}


	public String getOutType(){
		return (String)this.getInternal("OUT_TYPE");
	}
	public void setMoveDate(Date MoveDate){
		this.setInternal("MOVE_DATE" ,MoveDate );
	}


	public Date getMoveDate(){
		return (Date)this.getInternal("MOVE_DATE");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setOrderNo(String OrderNo){
		this.setInternal("ORDER_NO" ,OrderNo );
	}


	public String getOrderNo(){
		return (String)this.getInternal("ORDER_NO");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setLinkMan(String LinkMan){
		this.setInternal("LINK_MAN" ,LinkMan );
	}


	public String getLinkMan(){
		return (String)this.getInternal("LINK_MAN");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setMoveMan(String MoveMan){
		this.setInternal("MOVE_MAN" ,MoveMan );
	}


	public String getMoveMan(){
		return (String)this.getInternal("MOVE_MAN");
	}
	public void setOrderId(String OrderId){
		this.setInternal("ORDER_ID" ,OrderId );
	}


	public String getOrderId(){
		return (String)this.getInternal("ORDER_ID");
	}
	public void setPrintMan(String PrintMan){
		this.setInternal("PRINT_MAN" ,PrintMan );
	}


	public String getPrintMan(){
		return (String)this.getInternal("PRINT_MAN");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
}

package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuSaleOrderVO extends BaseVO{
    public PtBuSaleOrderVO(){
    	//�����ֶ���Ϣ
    	this.addField("SB_FAULT_ANALYSE_NAME",BaseVO.OP_STRING);
    	this.addField("PROM_ID",BaseVO.OP_STRING);
    	this.addField("ORDER_STATUS",BaseVO.OP_STRING);
    	this.addField("IF_TRANS",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_ID",BaseVO.OP_STRING);
    	this.addField("INVOICE_STATUS",BaseVO.OP_STRING);
    	this.addField("CUSTORM_NAME",BaseVO.OP_STRING);
    	this.addField("SB_BUY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SB_BUY_DATE", "yyyy-MM-dd");
    	this.addField("CLOSE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd");
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("SB_ENGINE_NO",BaseVO.OP_STRING);
    	this.addField("PROVINCE_CODE",BaseVO.OP_STRING);
    	this.addField("IF_CREDIT",BaseVO.OP_STRING);
    	this.addField("SB_MODELS_CODE",BaseVO.OP_STRING);
    	this.addField("SB_VIN",BaseVO.OP_STRING);
    	this.addField("PLAN_PRODUCE_NO",BaseVO.OP_STRING);
    	this.addField("ZIP_CODE",BaseVO.OP_STRING);
    	this.addField("DIRECT_TYPE_NAME",BaseVO.OP_STRING);
    	this.addField("BELONG_ASSEMBLY",BaseVO.OP_STRING);
    	this.addField("COUNTRY_CODE",BaseVO.OP_STRING);
    	this.addField("SB_MILEAGE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("SALEUSER_NAME",BaseVO.OP_STRING);
    	this.addField("RECEIPT",BaseVO.OP_STRING);
    	this.addField("PROVINCE_NAME",BaseVO.OP_STRING);
    	this.addField("SHIP_STATUS",BaseVO.OP_STRING);
    	this.addField("IF_DELAY_ORDER",BaseVO.OP_STRING);
    	this.addField("LINK_MAN",BaseVO.OP_STRING);
    	this.addField("IF_CHANEL_ORDER",BaseVO.OP_STRING);
    	this.addField("ORDER_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("DIRECT_TYPE_CODE",BaseVO.OP_STRING);
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("SB_FAULT_ANALYSE_CODE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("DIR_SOURCE_ORDER_NO",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("VIN",BaseVO.OP_STRING);
    	this.addField("PLAN_AMOUNT",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_CODE",BaseVO.OP_STRING);
    	this.addField("IF_OWN_EXPENCE",BaseVO.OP_STRING);
    	this.addField("SB_LINK_MAN",BaseVO.OP_STRING);
    	this.addField("DELIVERY_CITY",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("DELIVERY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("DELIVERY_DATE", "yyyy-MM-dd");
    	this.addField("APPLY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	this.addField("SOURCE_ORDER_NO",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("SB_USER_NAME",BaseVO.OP_STRING);
    	this.addField("ADDR_TYPE",BaseVO.OP_STRING);
    	this.addField("NEW_DELIVERY_ADDR",BaseVO.OP_STRING);
    	this.addField("TRANS_TYPE",BaseVO.OP_STRING);
    	this.addField("PHONE",BaseVO.OP_STRING);
    	this.addField("DELIVERY_ADDR",BaseVO.OP_STRING);
    	this.addField("SOURCE_ORDER_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("RECIVE_COMPANY",BaseVO.OP_STRING);
    	this.addField("SALEUSER_CODE",BaseVO.OP_STRING);
    	this.addField("PRINT_USER",BaseVO.OP_STRING);
    	this.addField("DIR_SOURCE_ORDER_ID",BaseVO.OP_STRING);
    	this.addField("COUNTRY_NAME",BaseVO.OP_STRING);
    	this.addField("ARMY_CONT_NO",BaseVO.OP_STRING);
    	this.addField("SB_PHONE",BaseVO.OP_STRING);
    	this.addField("SB_FAULT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SB_FAULT_DATE", "yyyy-MM-dd");
    	this.addField("SB_USER_ADDRESS",BaseVO.OP_STRING);
    	this.addField("ORDER_TYPE",BaseVO.OP_STRING);
    	this.addField("ORDER_AMOUNT",BaseVO.OP_STRING);
    	this.addField("CITY_CODE",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("IF_SUPER",BaseVO.OP_STRING);
    	this.addField("ORDER_NO",BaseVO.OP_STRING);
    	this.addField("EXPECT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
    	this.addField("TELEPHONE",BaseVO.OP_STRING);
    	this.addField("PALN_YMONTH",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("IF_TOTAIL",BaseVO.OP_STRING);
    	this.addField("CITY_NAME",BaseVO.OP_STRING);
    	this.addField("SPRINTDATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SPRINTDATE", "yyyy-MM-dd");
    	this.addField("DIRECT_TYPE_ID",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("REAL_AMOUNT",BaseVO.OP_STRING);
    	this.addField("FLAG",BaseVO.OP_STRING);
		this.bindFieldToSequence("ORDER_ID","COMMON");
    	this.setVOTableName("PT_BU_SALE_ORDER");
}
	public void setSbFaultAnalyseName(String SbFaultAnalyseName){
		this.setInternal("SB_FAULT_ANALYSE_NAME" ,SbFaultAnalyseName );
	}


	public String getSbFaultAnalyseName(){
		return (String)this.getInternal("SB_FAULT_ANALYSE_NAME");
	}
	public void setPromId(String PromId){
		this.setInternal("PROM_ID" ,PromId );
	}


	public String getPromId(){
		return (String)this.getInternal("PROM_ID");
	}
	
	public void setFlag(String flag){
		this.setInternal("FLAG" ,flag );
	}


	public String getFlag(){
		return (String)this.getInternal("FLAG");
	}
	public void setOrderStatus(String OrderStatus){
		this.setInternal("ORDER_STATUS" ,OrderStatus );
	}


	public String getOrderStatus(){
		return (String)this.getInternal("ORDER_STATUS");
	}
	public void setIfTrans(String IfTrans){
		this.setInternal("IF_TRANS" ,IfTrans );
	}


	public String getIfTrans(){
		return (String)this.getInternal("IF_TRANS");
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
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
	}
	public void setWarehouseId(String WarehouseId){
		this.setInternal("WAREHOUSE_ID" ,WarehouseId );
	}


	public String getWarehouseId(){
		return (String)this.getInternal("WAREHOUSE_ID");
	}
	public void setInvoiceStatus(String InvoiceStatus){
		this.setInternal("INVOICE_STATUS" ,InvoiceStatus );
	}


	public String getInvoiceStatus(){
		return (String)this.getInternal("INVOICE_STATUS");
	}
	public void setCustormName(String CustormName){
		this.setInternal("CUSTORM_NAME" ,CustormName );
	}


	public String getCustormName(){
		return (String)this.getInternal("CUSTORM_NAME");
	}
	public void setSbBuyDate(Date SbBuyDate){
		this.setInternal("SB_BUY_DATE" ,SbBuyDate );
	}


	public Date getSbBuyDate(){
		return (Date)this.getInternal("SB_BUY_DATE");
	}
	public void setCloseDate(Date CloseDate){
		this.setInternal("CLOSE_DATE" ,CloseDate );
	}


	public Date getCloseDate(){
		return (Date)this.getInternal("CLOSE_DATE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setSbEngineNo(String SbEngineNo){
		this.setInternal("SB_ENGINE_NO" ,SbEngineNo );
	}


	public String getSbEngineNo(){
		return (String)this.getInternal("SB_ENGINE_NO");
	}
	public void setProvinceCode(String ProvinceCode){
		this.setInternal("PROVINCE_CODE" ,ProvinceCode );
	}


	public String getProvinceCode(){
		return (String)this.getInternal("PROVINCE_CODE");
	}
	public void setIfCredit(String IfCredit){
		this.setInternal("IF_CREDIT" ,IfCredit );
	}


	public String getIfCredit(){
		return (String)this.getInternal("IF_CREDIT");
	}
	public void setSbModelsCode(String SbModelsCode){
		this.setInternal("SB_MODELS_CODE" ,SbModelsCode );
	}


	public String getSbModelsCode(){
		return (String)this.getInternal("SB_MODELS_CODE");
	}
	public void setSbVin(String SbVin){
		this.setInternal("SB_VIN" ,SbVin );
	}


	public String getSbVin(){
		return (String)this.getInternal("SB_VIN");
	}
	public void setPlanProduceNo(String PlanProduceNo){
		this.setInternal("PLAN_PRODUCE_NO" ,PlanProduceNo );
	}


	public String getPlanProduceNo(){
		return (String)this.getInternal("PLAN_PRODUCE_NO");
	}
	public void setZipCode(String ZipCode){
		this.setInternal("ZIP_CODE" ,ZipCode );
	}


	public String getZipCode(){
		return (String)this.getInternal("ZIP_CODE");
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
	public void setCountryCode(String CountryCode){
		this.setInternal("COUNTRY_CODE" ,CountryCode );
	}


	public String getCountryCode(){
		return (String)this.getInternal("COUNTRY_CODE");
	}
	public void setSbMileage(String SbMileage){
		this.setInternal("SB_MILEAGE" ,SbMileage );
	}


	public String getSbMileage(){
		return (String)this.getInternal("SB_MILEAGE");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setSaleuserName(String SaleuserName){
		this.setInternal("SALEUSER_NAME" ,SaleuserName );
	}


	public String getSaleuserName(){
		return (String)this.getInternal("SALEUSER_NAME");
	}
	public void setReceipt(String Receipt){
		this.setInternal("RECEIPT" ,Receipt );
	}


	public String getReceipt(){
		return (String)this.getInternal("RECEIPT");
	}
	public void setProvinceName(String ProvinceName){
		this.setInternal("PROVINCE_NAME" ,ProvinceName );
	}


	public String getProvinceName(){
		return (String)this.getInternal("PROVINCE_NAME");
	}
	public void setShipStatus(String ShipStatus){
		this.setInternal("SHIP_STATUS" ,ShipStatus );
	}


	public String getShipStatus(){
		return (String)this.getInternal("SHIP_STATUS");
	}
	public void setIfDelayOrder(String IfDelayOrder){
		this.setInternal("IF_DELAY_ORDER" ,IfDelayOrder );
	}


	public String getIfDelayOrder(){
		return (String)this.getInternal("IF_DELAY_ORDER");
	}
	public void setLinkMan(String LinkMan){
		this.setInternal("LINK_MAN" ,LinkMan );
	}


	public String getLinkMan(){
		return (String)this.getInternal("LINK_MAN");
	}
	public void setIfChanelOrder(String IfChanelOrder){
		this.setInternal("IF_CHANEL_ORDER" ,IfChanelOrder );
	}


	public String getIfChanelOrder(){
		return (String)this.getInternal("IF_CHANEL_ORDER");
	}
	public void setOrderId(String OrderId){
		this.setInternal("ORDER_ID" ,OrderId );
	}


	public String getOrderId(){
		return (String)this.getInternal("ORDER_ID");
	}
	public void setDirectTypeCode(String DirectTypeCode){
		this.setInternal("DIRECT_TYPE_CODE" ,DirectTypeCode );
	}


	public String getDirectTypeCode(){
		return (String)this.getInternal("DIRECT_TYPE_CODE");
	}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setSbFaultAnalyseCode(String SbFaultAnalyseCode){
		this.setInternal("SB_FAULT_ANALYSE_CODE" ,SbFaultAnalyseCode );
	}


	public String getSbFaultAnalyseCode(){
		return (String)this.getInternal("SB_FAULT_ANALYSE_CODE");
	}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setDirSourceOrderNo(String DirSourceOrderNo){
		this.setInternal("DIR_SOURCE_ORDER_NO" ,DirSourceOrderNo );
	}


	public String getDirSourceOrderNo(){
		return (String)this.getInternal("DIR_SOURCE_ORDER_NO");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setVin(String Vin){
		this.setInternal("VIN" ,Vin );
	}


	public String getVin(){
		return (String)this.getInternal("VIN");
	}
	public void setPlanAmount(String PlanAmount){
		this.setInternal("PLAN_AMOUNT" ,PlanAmount );
	}


	public String getPlanAmount(){
		return (String)this.getInternal("PLAN_AMOUNT");
	}
	public void setWarehouseCode(String WarehouseCode){
		this.setInternal("WAREHOUSE_CODE" ,WarehouseCode );
	}


	public String getWarehouseCode(){
		return (String)this.getInternal("WAREHOUSE_CODE");
	}
	public void setIfOwnExpence(String IfOwnExpence){
		this.setInternal("IF_OWN_EXPENCE" ,IfOwnExpence );
	}


	public String getIfOwnExpence(){
		return (String)this.getInternal("IF_OWN_EXPENCE");
	}
	public void setSbLinkMan(String SbLinkMan){
		this.setInternal("SB_LINK_MAN" ,SbLinkMan );
	}


	public String getSbLinkMan(){
		return (String)this.getInternal("SB_LINK_MAN");
	}
	public void setDeliveryCity(String DeliveryCity){
		this.setInternal("DELIVERY_CITY" ,DeliveryCity );
	}


	public String getDeliveryCity(){
		return (String)this.getInternal("DELIVERY_CITY");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setDeliveryDate(Date DeliveryDate){
		this.setInternal("DELIVERY_DATE" ,DeliveryDate );
	}


	public Date getDeliveryDate(){
		return (Date)this.getInternal("DELIVERY_DATE");
	}
	public void setApplyDate(Date ApplyDate){
		this.setInternal("APPLY_DATE" ,ApplyDate );
	}


	public Date getApplyDate(){
		return (Date)this.getInternal("APPLY_DATE");
	}
	public void setSourceOrderNo(String SourceOrderNo){
		this.setInternal("SOURCE_ORDER_NO" ,SourceOrderNo );
	}


	public String getSourceOrderNo(){
		return (String)this.getInternal("SOURCE_ORDER_NO");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setSbUserName(String SbUserName){
		this.setInternal("SB_USER_NAME" ,SbUserName );
	}


	public String getSbUserName(){
		return (String)this.getInternal("SB_USER_NAME");
	}
	public void setAddrType(String AddrType){
		this.setInternal("ADDR_TYPE" ,AddrType );
	}


	public String getAddrType(){
		return (String)this.getInternal("ADDR_TYPE");
	}
	public void setNewDeliveryAddr(String NewDeliveryAddr){
		this.setInternal("NEW_DELIVERY_ADDR" ,NewDeliveryAddr );
	}


	public String getNewDeliveryAddr(){
		return (String)this.getInternal("NEW_DELIVERY_ADDR");
	}
	public void setTransType(String TransType){
		this.setInternal("TRANS_TYPE" ,TransType );
	}


	public String getTransType(){
		return (String)this.getInternal("TRANS_TYPE");
	}
	public void setPhone(String Phone){
		this.setInternal("PHONE" ,Phone );
	}


	public String getPhone(){
		return (String)this.getInternal("PHONE");
	}
	public void setDeliveryAddr(String DeliveryAddr){
		this.setInternal("DELIVERY_ADDR" ,DeliveryAddr );
	}


	public String getDeliveryAddr(){
		return (String)this.getInternal("DELIVERY_ADDR");
	}
	public void setSourceOrderId(String SourceOrderId){
		this.setInternal("SOURCE_ORDER_ID" ,SourceOrderId );
	}


	public String getSourceOrderId(){
		return (String)this.getInternal("SOURCE_ORDER_ID");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setReciveCompany(String ReciveCompany){
		this.setInternal("RECIVE_COMPANY" ,ReciveCompany );
	}


	public String getReciveCompany(){
		return (String)this.getInternal("RECIVE_COMPANY");
	}
	public void setSaleuserCode(String SaleuserCode){
		this.setInternal("SALEUSER_CODE" ,SaleuserCode );
	}


	public String getSaleuserCode(){
		return (String)this.getInternal("SALEUSER_CODE");
	}
	public void setPrintUser(String PrintUser){
		this.setInternal("PRINT_USER" ,PrintUser );
	}


	public String getPrintUser(){
		return (String)this.getInternal("PRINT_USER");
	}
	public void setDirSourceOrderId(String DirSourceOrderId){
		this.setInternal("DIR_SOURCE_ORDER_ID" ,DirSourceOrderId );
	}


	public String getDirSourceOrderId(){
		return (String)this.getInternal("DIR_SOURCE_ORDER_ID");
	}
	public void setCountryName(String CountryName){
		this.setInternal("COUNTRY_NAME" ,CountryName );
	}


	public String getCountryName(){
		return (String)this.getInternal("COUNTRY_NAME");
	}
	public void setArmyContNo(String ArmyContNo){
		this.setInternal("ARMY_CONT_NO" ,ArmyContNo );
	}


	public String getArmyContNo(){
		return (String)this.getInternal("ARMY_CONT_NO");
	}
	public void setSbPhone(String SbPhone){
		this.setInternal("SB_PHONE" ,SbPhone );
	}


	public String getSbPhone(){
		return (String)this.getInternal("SB_PHONE");
	}
	public void setSbFaultDate(Date SbFaultDate){
		this.setInternal("SB_FAULT_DATE" ,SbFaultDate );
	}


	public Date getSbFaultDate(){
		return (Date)this.getInternal("SB_FAULT_DATE");
	}
	public void setSbUserAddress(String SbUserAddress){
		this.setInternal("SB_USER_ADDRESS" ,SbUserAddress );
	}


	public String getSbUserAddress(){
		return (String)this.getInternal("SB_USER_ADDRESS");
	}
	public void setOrderType(String OrderType){
		this.setInternal("ORDER_TYPE" ,OrderType );
	}


	public String getOrderType(){
		return (String)this.getInternal("ORDER_TYPE");
	}
	public void setOrderAmount(String OrderAmount){
		this.setInternal("ORDER_AMOUNT" ,OrderAmount );
	}


	public String getOrderAmount(){
		return (String)this.getInternal("ORDER_AMOUNT");
	}
	public void setCityCode(String CityCode){
		this.setInternal("CITY_CODE" ,CityCode );
	}


	public String getCityCode(){
		return (String)this.getInternal("CITY_CODE");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setIfSuper(String IfSuper){
		this.setInternal("IF_SUPER" ,IfSuper );
	}


	public String getIfSuper(){
		return (String)this.getInternal("IF_SUPER");
	}
	public void setOrderNo(String OrderNo){
		this.setInternal("ORDER_NO" ,OrderNo );
	}


	public String getOrderNo(){
		return (String)this.getInternal("ORDER_NO");
	}
	public void setExpectDate(Date ExpectDate){
		this.setInternal("EXPECT_DATE" ,ExpectDate );
	}


	public Date getExpectDate(){
		return (Date)this.getInternal("EXPECT_DATE");
	}
	public void setTelephone(String Telephone){
		this.setInternal("TELEPHONE" ,Telephone );
	}


	public String getTelephone(){
		return (String)this.getInternal("TELEPHONE");
	}
	public void setPalnYmonth(String PalnYmonth){
		this.setInternal("PALN_YMONTH" ,PalnYmonth );
	}


	public String getPalnYmonth(){
		return (String)this.getInternal("PALN_YMONTH");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setIfTotail(String IfTotail){
		this.setInternal("IF_TOTAIL" ,IfTotail );
	}


	public String getIfTotail(){
		return (String)this.getInternal("IF_TOTAIL");
	}
	public void setCityName(String CityName){
		this.setInternal("CITY_NAME" ,CityName );
	}


	public String getCityName(){
		return (String)this.getInternal("CITY_NAME");
	}
	public void setSprintdate(Date Sprintdate){
		this.setInternal("SPRINTDATE" ,Sprintdate );
	}


	public Date getSprintdate(){
		return (Date)this.getInternal("SPRINTDATE");
	}
	public void setDirectTypeId(String DirectTypeId){
		this.setInternal("DIRECT_TYPE_ID" ,DirectTypeId );
	}


	public String getDirectTypeId(){
		return (String)this.getInternal("DIRECT_TYPE_ID");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setRealAmount(String RealAmount){
		this.setInternal("REAL_AMOUNT" ,RealAmount );
	}


	public String getRealAmount(){
		return (String)this.getInternal("REAL_AMOUNT");
	}
}

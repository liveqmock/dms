package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaInfoVO extends BaseVO{
    public PtBaInfoVO(){
    	//�����ֶ���Ϣ
    	this.addField("PRICE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("PRICE_TIME", "yyyy-MM-dd");
    	this.addField("PART_TYPE",BaseVO.OP_STRING);
    	this.addField("SPE_TYPE",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
		this.addField("BRIDGE_STATUS",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PCH_PRICE",BaseVO.OP_STRING);
    	this.addField("F_POSITION_ID",BaseVO.OP_STRING);
    	this.addField("ATTRIBUTE",BaseVO.OP_STRING);
    	this.addField("VEHICLE_MAX",BaseVO.OP_STRING);
    	this.addField("IF_OIL",BaseVO.OP_STRING);
    	this.addField("P_SPECI",BaseVO.OP_STRING);
    	this.addField("OLD_MANAGE_FEE",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("SE_UNIT",BaseVO.OP_STRING);
    	this.addField("IF_WORK_HOURS_TIMES",BaseVO.OP_STRING);
    	this.addField("PART_STATUS",BaseVO.OP_STRING);
    	this.addField("DIRECT_TYPE_NAME",BaseVO.OP_STRING);
    	this.addField("POSITION_NAME",BaseVO.OP_STRING);
    	this.addField("BELONG_ASSEMBLY",BaseVO.OP_STRING);
    	this.addField("MIN_UNIT",BaseVO.OP_STRING);
    	this.addField("POSITION_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("RETAIL_PRICE",BaseVO.OP_STRING);
    	this.addField("SPE_NAME",BaseVO.OP_STRING);
    	this.addField("IF_ASSEMBLY",BaseVO.OP_STRING);
    	this.addField("F_PART_CODE",BaseVO.OP_STRING);
    	this.addField("PLAN_PRICE",BaseVO.OP_STRING);
    	this.addField("SE_STATUS",BaseVO.OP_STRING);
    	this.addField("POSITION_ID",BaseVO.OP_STRING);
    	this.addField("DIRECT_TYPE_CODE",BaseVO.OP_STRING);
    	this.addField("IF_SCAN",BaseVO.OP_STRING);
    	this.addField("SE_CLPRICE",BaseVO.OP_STRING);
    	this.addField("IF_BOOK",BaseVO.OP_STRING);
    	this.addField("REBATE_TYPE",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("SALE_PRICE",BaseVO.OP_STRING);
    	this.addField("ARMY_PRICE",BaseVO.OP_STRING);
    	this.addField("SE_REPRICE",BaseVO.OP_STRING);
    	this.addField("WEIGHT",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("IF_SELF",BaseVO.OP_STRING);
    	this.addField("IF_OUT_BUY",BaseVO.OP_STRING);
    	this.addField("PRICE_USER",BaseVO.OP_STRING);
    	this.addField("REBATE_PARAVALUE1",BaseVO.OP_STRING);
    	this.addField("F_PART_NAME",BaseVO.OP_STRING);
    	this.addField("F_PART_ID",BaseVO.OP_STRING);
    	this.addField("UNIT",BaseVO.OP_STRING);
    	this.addField("F_POSITION_NAME",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("PART_NO",BaseVO.OP_STRING);
    	this.addField("MIN_PACK",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("F_POSITION_CODE",BaseVO.OP_STRING);
    	this.addField("IF_OUT",BaseVO.OP_STRING);
    	this.addField("IF_RETURN",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("IF_STREAM",BaseVO.OP_STRING);
    	this.addField("DIRECT_TYPE_ID",BaseVO.OP_STRING);
    	this.addField("IF_WORK_MULTIPLE",BaseVO.OP_STRING);
    	this.addField("IF_SUPLY",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("IF_DIRECT",BaseVO.OP_STRING);
		this.bindFieldToSequence("PART_ID","COMMON");
		
		//设置字典
		this.bindFieldToDic("UNIT", "JLDW");
		this.bindFieldToDic("BELONG_ASSEMBLY", "PJZCLB");		
		this.bindFieldToDic("PART_TYPE", "PJLB"); 
		this.bindFieldToDic("ATTRIBUTE", "PJSX");  
		this.bindFieldToDic("MIN_UNIT", "JLDW");  
		this.bindFieldToDic("IF_DIRECT", "SF"); 		
		this.bindFieldToDic("IF_STREAM", "SF");   
		this.bindFieldToDic("IF_OUT", "SF");   
		this.bindFieldToDic("IF_BOOK", "SF");   
		this.bindFieldToDic("IF_RETURN", "SF"); 
		this.bindFieldToDic("IF_OIL", "SF"); 
		this.bindFieldToDic("IF_ASSEMBLY", "SF");
		this.bindFieldToDic("IF_SCAN", "SF");
		this.bindFieldToDic("IF_SUPLY", "SF");
		this.bindFieldToDic("PART_STATUS", "PJZT"); 
		this.bindFieldToDic("STATUS", "YXBS"); 
		this.bindFieldToDic("SE_STATUS", "YXBS"); 
		this.bindFieldToDic("IF_WORK_HOURS_TIMES", "SF"); 
		
    	this.setVOTableName("PT_BA_INFO");
}
    public void setBridgeStatus(String BridgeStatus){
		this.setInternal("BRIDGE_STATUS" ,BridgeStatus );
	}


	public String getBridgeStatus(){
		return (String)this.getInternal("BRIDGE_STATUS");
	}
	public void setPriceTime(Date PriceTime){
		this.setInternal("PRICE_TIME" ,PriceTime );
	}


	public Date getPriceTime(){
		return (Date)this.getInternal("PRICE_TIME");
	}
	public void setPartType(String PartType){
		this.setInternal("PART_TYPE" ,PartType );
	}


	public String getPartType(){
		return (String)this.getInternal("PART_TYPE");
	}
	public void setSpeType(String SpeType){
		this.setInternal("SPE_TYPE" ,SpeType );
	}


	public String getSpeType(){
		return (String)this.getInternal("SPE_TYPE");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setPchPrice(String PchPrice){
		this.setInternal("PCH_PRICE" ,PchPrice );
	}


	public String getPchPrice(){
		return (String)this.getInternal("PCH_PRICE");
	}
	public void setFPositionId(String FPositionId){
		this.setInternal("F_POSITION_ID" ,FPositionId );
	}


	public String getFPositionId(){
		return (String)this.getInternal("F_POSITION_ID");
	}
	public void setAttribute(String Attribute){
		this.setInternal("ATTRIBUTE" ,Attribute );
	}


	public String getAttribute(){
		return (String)this.getInternal("ATTRIBUTE");
	}
	public void setVehicleMax(String VehicleMax){
		this.setInternal("VEHICLE_MAX" ,VehicleMax );
	}


	public String getVehicleMax(){
		return (String)this.getInternal("VEHICLE_MAX");
	}
	public void setIfOil(String IfOil){
		this.setInternal("IF_OIL" ,IfOil );
	}


	public String getIfOil(){
		return (String)this.getInternal("IF_OIL");
	}
	public void setPSpeci(String PSpeci){
		this.setInternal("P_SPECI" ,PSpeci );
	}


	public String getPSpeci(){
		return (String)this.getInternal("P_SPECI");
	}
	public void setOldManageFee(String OldManageFee){
		this.setInternal("OLD_MANAGE_FEE" ,OldManageFee );
	}


	public String getOldManageFee(){
		return (String)this.getInternal("OLD_MANAGE_FEE");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setSeUnit(String SeUnit){
		this.setInternal("SE_UNIT" ,SeUnit );
	}


	public String getSeUnit(){
		return (String)this.getInternal("SE_UNIT");
	}
	public void setIfWorkHoursTimes(String IfWorkHoursTimes){
		this.setInternal("IF_WORK_HOURS_TIMES" ,IfWorkHoursTimes );
	}


	public String getIfWorkHoursTimes(){
		return (String)this.getInternal("IF_WORK_HOURS_TIMES");
	}
	public void setPartStatus(String PartStatus){
		this.setInternal("PART_STATUS" ,PartStatus );
	}


	public String getPartStatus(){
		return (String)this.getInternal("PART_STATUS");
	}
	public void setDirectTypeName(String DirectTypeName){
		this.setInternal("DIRECT_TYPE_NAME" ,DirectTypeName );
	}


	public String getDirectTypeName(){
		return (String)this.getInternal("DIRECT_TYPE_NAME");
	}
	public void setPositionName(String PositionName){
		this.setInternal("POSITION_NAME" ,PositionName );
	}


	public String getPositionName(){
		return (String)this.getInternal("POSITION_NAME");
	}
	public void setBelongAssembly(String BelongAssembly){
		this.setInternal("BELONG_ASSEMBLY" ,BelongAssembly );
	}


	public String getBelongAssembly(){
		return (String)this.getInternal("BELONG_ASSEMBLY");
	}
	public void setMinUnit(String MinUnit){
		this.setInternal("MIN_UNIT" ,MinUnit );
	}


	public String getMinUnit(){
		return (String)this.getInternal("MIN_UNIT");
	}
	public void setPositionCode(String PositionCode){
		this.setInternal("POSITION_CODE" ,PositionCode );
	}


	public String getPositionCode(){
		return (String)this.getInternal("POSITION_CODE");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setRetailPrice(String RetailPrice){
		this.setInternal("RETAIL_PRICE" ,RetailPrice );
	}


	public String getRetailPrice(){
		return (String)this.getInternal("RETAIL_PRICE");
	}
	public void setSpeName(String SpeName){
		this.setInternal("SPE_NAME" ,SpeName );
	}


	public String getSpeName(){
		return (String)this.getInternal("SPE_NAME");
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
	public void setPlanPrice(String PlanPrice){
		this.setInternal("PLAN_PRICE" ,PlanPrice );
	}


	public String getPlanPrice(){
		return (String)this.getInternal("PLAN_PRICE");
	}
	public void setSeStatus(String SeStatus){
		this.setInternal("SE_STATUS" ,SeStatus );
	}


	public String getSeStatus(){
		return (String)this.getInternal("SE_STATUS");
	}
	public void setPositionId(String PositionId){
		this.setInternal("POSITION_ID" ,PositionId );
	}


	public String getPositionId(){
		return (String)this.getInternal("POSITION_ID");
	}
	public void setDirectTypeCode(String DirectTypeCode){
		this.setInternal("DIRECT_TYPE_CODE" ,DirectTypeCode );
	}


	public String getDirectTypeCode(){
		return (String)this.getInternal("DIRECT_TYPE_CODE");
	}
	public void setIfScan(String IfScan){
		this.setInternal("IF_SCAN" ,IfScan );
	}


	public String getIfScan(){
		return (String)this.getInternal("IF_SCAN");
	}
	public void setSeClprice(String SeClprice){
		this.setInternal("SE_CLPRICE" ,SeClprice );
	}


	public String getSeClprice(){
		return (String)this.getInternal("SE_CLPRICE");
	}
	public void setIfBook(String IfBook){
		this.setInternal("IF_BOOK" ,IfBook );
	}


	public String getIfBook(){
		return (String)this.getInternal("IF_BOOK");
	}
	public void setRebateType(String RebateType){
		this.setInternal("REBATE_TYPE" ,RebateType );
	}


	public String getRebateType(){
		return (String)this.getInternal("REBATE_TYPE");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setSalePrice(String SalePrice){
		this.setInternal("SALE_PRICE" ,SalePrice );
	}


	public String getSalePrice(){
		return (String)this.getInternal("SALE_PRICE");
	}
	public void setArmyPrice(String ArmyPrice){
		this.setInternal("ARMY_PRICE" ,ArmyPrice );
	}


	public String getArmyPrice(){
		return (String)this.getInternal("ARMY_PRICE");
	}
	public void setSeReprice(String SeReprice){
		this.setInternal("SE_REPRICE" ,SeReprice );
	}


	public String getSeReprice(){
		return (String)this.getInternal("SE_REPRICE");
	}
	public void setWeight(String Weight){
		this.setInternal("WEIGHT" ,Weight );
	}


	public String getWeight(){
		return (String)this.getInternal("WEIGHT");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setIfSelf(String IfSelf){
		this.setInternal("IF_SELF" ,IfSelf );
	}


	public String getIfSelf(){
		return (String)this.getInternal("IF_SELF");
	}
	public void setIfOutBuy(String IfOutBuy){
		this.setInternal("IF_OUT_BUY" ,IfOutBuy );
	}


	public String getIfOutBuy(){
		return (String)this.getInternal("IF_OUT_BUY");
	}
	public void setPriceUser(String PriceUser){
		this.setInternal("PRICE_USER" ,PriceUser );
	}


	public String getPriceUser(){
		return (String)this.getInternal("PRICE_USER");
	}
	public void setRebateParavalue1(String RebateParavalue1){
		this.setInternal("REBATE_PARAVALUE1" ,RebateParavalue1 );
	}


	public String getRebateParavalue1(){
		return (String)this.getInternal("REBATE_PARAVALUE1");
	}
	public void setFPartName(String FPartName){
		this.setInternal("F_PART_NAME" ,FPartName );
	}


	public String getFPartName(){
		return (String)this.getInternal("F_PART_NAME");
	}
	public void setFPartId(String FPartId){
		this.setInternal("F_PART_ID" ,FPartId );
	}


	public String getFPartId(){
		return (String)this.getInternal("F_PART_ID");
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
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
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
	public void setFPositionCode(String FPositionCode){
		this.setInternal("F_POSITION_CODE" ,FPositionCode );
	}


	public String getFPositionCode(){
		return (String)this.getInternal("F_POSITION_CODE");
	}
	public void setIfOut(String IfOut){
		this.setInternal("IF_OUT" ,IfOut );
	}


	public String getIfOut(){
		return (String)this.getInternal("IF_OUT");
	}
	public void setIfReturn(String IfReturn){
		this.setInternal("IF_RETURN" ,IfReturn );
	}


	public String getIfReturn(){
		return (String)this.getInternal("IF_RETURN");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setIfStream(String IfStream){
		this.setInternal("IF_STREAM" ,IfStream );
	}


	public String getIfStream(){
		return (String)this.getInternal("IF_STREAM");
	}
	public void setDirectTypeId(String DirectTypeId){
		this.setInternal("DIRECT_TYPE_ID" ,DirectTypeId );
	}


	public String getDirectTypeId(){
		return (String)this.getInternal("DIRECT_TYPE_ID");
	}
	public void setIfWorkMultiple(String IfWorkMultiple){
		this.setInternal("IF_WORK_MULTIPLE" ,IfWorkMultiple );
	}


	public String getIfWorkMultiple(){
		return (String)this.getInternal("IF_WORK_MULTIPLE");
	}
	public void setIfSuply(String IfSuply){
		this.setInternal("IF_SUPLY" ,IfSuply );
	}


	public String getIfSuply(){
		return (String)this.getInternal("IF_SUPLY");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setIfDirect(String IfDirect){
		this.setInternal("IF_DIRECT" ,IfDirect );
	}


	public String getIfDirect(){
		return (String)this.getInternal("IF_DIRECT");
	}
}

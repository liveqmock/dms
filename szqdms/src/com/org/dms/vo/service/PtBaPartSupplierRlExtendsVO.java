package com.org.dms.vo.service;
import com.org.dms.vo.part.PtBaPartSupplierRlVO;
import com.org.framework.base.BaseVO;
public class PtBaPartSupplierRlExtendsVO extends PtBaPartSupplierRlVO{
    public PtBaPartSupplierRlExtendsVO(){
    	//设置字段信息
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING); 	
    	
		//设置字典类型定义
		this.bindFieldToDic("IF_STREAM","SF");//是否
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
	
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}
	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}
	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
}

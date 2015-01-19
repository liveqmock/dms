package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaPartSupplierRlVO_Ext extends PtBaPartSupplierRlVO{
    public PtBaPartSupplierRlVO_Ext(){
    	//配件
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	//供应商
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	//供货周期
    	this.addField("PARAKEY",BaseVO.OP_STRING);
    	this.addField("PARAVALUE1",BaseVO.OP_STRING);
    	this.addField("PARAVALUE2",BaseVO.OP_STRING);
    	this.bindFieldToDic("IF_STREAM", "SF");   //是否需要流水号
 	
}
  //供货周期
    public void setParakey(String Parakey){
    	this.setInternal("PARAKEY" ,Parakey );
    }
    public String getParakey(){
		return (String)this.getInternal("PARAKEY");
	}
    
    
    public void setParavalue1(String Paravalue1){
    	this.setInternal("PARAVALUE1" ,Paravalue1 );
    }
    public String getParavalue1(){
		return (String)this.getInternal("PARAVALUE1");
	}
    
    public void setParavalue2(String Paravalue2){
    	this.setInternal("PARAVALUE2" ,Paravalue2 );
    }
    public String getParavalue2(){
		return (String)this.getInternal("PARAVALUE2");
	}
  //供应商
    public void setSupplier_code(String Supplier_code){
    	this.setInternal("SUPPLIER_CODE" ,Supplier_code );
    }
    public String getSupplier_code(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
    
    
    public void setSupplier_name(String Supplier_name){
    	this.setInternal("SUPPLIER_NAME" ,Supplier_name );
    }
    public String getSupplier_name(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
 //配件   
    public void setPart_code(String Part_code){
    	this.setInternal("PART_CODE" ,Part_code );
    }
    public String getPart_code(){
		return (String)this.getInternal("PART_CODE");
	}
    
    
    public void setPart_name(String Part_name){
    	this.setInternal("PART_NAME" ,Part_name );
    }
    public String getPart_name(){
		return (String)this.getInternal("PART_NAME");
	}
    
    
    
}

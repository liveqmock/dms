package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaInfoVO_Ext extends PtBaInfoVO{
    public PtBaInfoVO_Ext(){
    	
  
    	this.addField("SN",BaseVO.OP_STRING);
    	this.addField("PARAKEY",BaseVO.OP_STRING);
    	this.addField("PARAVALUE1",BaseVO.OP_STRING);
 	
}
    
    public void setSn(String Sn){
    	this.setInternal("SN" ,Sn );
    }
    public String getSn(){
		return (String)this.getInternal("SN");
	}
    
    
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
  
   
}

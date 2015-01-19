package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
public class PtBaServiceDcExtendsVO extends PtBaServiceDcVO {
    public PtBaServiceDcExtendsVO(){
    	//配送关系管理(配送中心--经销商、服务站之间的关系)VO
    	//新增加的一些信息
		
		//由于业务需要  增加几个字段
		this.addField("DC_NAME",BaseVO.OP_STRING);
		this.addField("ORG_NAME",BaseVO.OP_STRING);
		
		//设置字典类型定义
		//this.bindFieldToDic("STATUS","YXBS");//有效标识 
    	//this.setVOTableName("PT_BA_SERVICE_DC");
    }
	
	//增加字段的get和set方法
	public void setDcName(String DcName){
		this.setInternal("DC_NAME" ,DcName );
	}


	public String getDcName(){
		return (String)this.getInternal("DC_NAME");
	}
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
	}
}

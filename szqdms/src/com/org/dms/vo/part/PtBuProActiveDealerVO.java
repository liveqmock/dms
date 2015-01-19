package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuProActiveDealerVO extends BaseVO{
    public PtBuProActiveDealerVO(){
    	//活动方案下发VO
    	this.addField("DTL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("ACTIVE_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("ACTIVE_NAME",BaseVO.OP_STRING);
    	this.addField("ACTIVE_ID",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("DTL_ID","COMMON");
		//设置字典类型定义
		this.bindFieldToDic("STATUS","YXBS");//有效标识
    	this.setVOTableName("PT_BU_PRO_ACTIVE_DEALER");
}
	public void setDtlId(String DtlId){
		this.setInternal("DTL_ID" ,DtlId );
	}


	public String getDtlId(){
		return (String)this.getInternal("DTL_ID");
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
	public void setActiveCode(String ActiveCode){
		this.setInternal("ACTIVE_CODE" ,ActiveCode );
	}


	public String getActiveCode(){
		return (String)this.getInternal("ACTIVE_CODE");
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
	public void setActiveName(String ActiveName){
		this.setInternal("ACTIVE_NAME" ,ActiveName );
	}


	public String getActiveName(){
		return (String)this.getInternal("ACTIVE_NAME");
	}
	public void setActiveId(String ActiveId){
		this.setInternal("ACTIVE_ID" ,ActiveId );
	}


	public String getActiveId(){
		return (String)this.getInternal("ACTIVE_ID");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
}

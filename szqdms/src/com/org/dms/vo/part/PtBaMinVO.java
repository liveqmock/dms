package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaMinVO extends BaseVO{
    public PtBaMinVO(){
    	//�����ֶ���Ϣ
    	this.addField("MIN_UNIT",BaseVO.OP_STRING);
    	this.addField("MIN_PACK",BaseVO.OP_STRING);
    	this.addField("MIN_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("STATUS",BaseVO.OP_STRING);
		this.bindFieldToSequence("MIN_ID","COMMON");
    	this.setVOTableName("PT_BA_MIN");
}
	public void setMinUnit(String MinUnit){
		this.setInternal("MIN_UNIT" ,MinUnit );
	}


	public String getMinUnit(){
		return (String)this.getInternal("MIN_UNIT");
	}
	public void setMinPack(String MinPack){
		this.setInternal("MIN_PACK" ,MinPack );
	}


	public String getMinPack(){
		return (String)this.getInternal("MIN_PACK");
	}
	public void setMinId(String MinId){
		this.setInternal("MIN_ID" ,MinId );
	}


	public String getMinId(){
		return (String)this.getInternal("MIN_ID");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
}

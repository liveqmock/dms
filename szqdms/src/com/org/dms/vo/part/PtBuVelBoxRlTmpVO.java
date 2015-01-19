package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuVelBoxRlTmpVO extends BaseVO{
    public PtBuVelBoxRlTmpVO(){
    	//�����ֶ���Ϣ
    	this.addField("TMP_NO",BaseVO.OP_STRING);
    	this.addField("USER_ACCONT",BaseVO.OP_STRING);
    	this.addField("BOX_NO",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("PT_BU_VEL_BOX_RL_TMP");
}
	public void setTmpNo(String TmpNo){
		this.setInternal("TMP_NO" ,TmpNo );
	}


	public String getTmpNo(){
		return (String)this.getInternal("TMP_NO");
	}
	public void setUserAccont(String UserAccont){
		this.setInternal("USER_ACCONT" ,UserAccont );
	}


	public String getUserAccont(){
		return (String)this.getInternal("USER_ACCONT");
	}
	public void setBoxNo(String BoxNo){
		this.setInternal("BOX_NO" ,BoxNo );
	}


	public String getBoxNo(){
		return (String)this.getInternal("BOX_NO");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
}

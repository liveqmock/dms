package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuScanCodeBoxnoVO extends BaseVO{
    public PtBuScanCodeBoxnoVO(){
    	//�����ֶ���Ϣ
    	this.addField("BOX_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("BOX_NO",BaseVO.OP_STRING);
		this.bindFieldToSequence("BOX_ID","COMMON");
    	this.setVOTableName("PT_BU_SCAN_CODE_BOXNO");
}
	public void setBoxId(String BoxId){
		this.setInternal("BOX_ID" ,BoxId );
	}


	public String getBoxId(){
		return (String)this.getInternal("BOX_ID");
	}
	public void setBoxNo(String BoxNo){
		this.setInternal("BOX_NO" ,BoxNo );
	}


	public String getBoxNo(){
		return (String)this.getInternal("BOX_NO");
	}
}

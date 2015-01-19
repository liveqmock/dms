package com.org.frameImpl.vo;
import com.org.framework.base.BaseVO;
public class DicTreeVO extends BaseVO{
	private static final long serialVersionUID = -7785729517138755733L;

	public DicTreeVO(){
    	//设置字段信息
    	this.addField("DIC_DMGL",BaseVO.OP_STRING);
    	this.addField("DIC_SYFW",BaseVO.OP_STRING);
    	this.addField("ORDER_NUM",BaseVO.OP_STRING);
    	this.addField("DIC_VALUE_ASPELL",BaseVO.OP_STRING);
    	this.addField("DIC_LAYER_TWO",BaseVO.OP_STRING);
    	this.addField("DIC_NODE",BaseVO.OP_STRING);
    	this.addField("DIC_NAME_VALUE",BaseVO.OP_STRING);
    	this.addField("DIC_LAYER",BaseVO.OP_STRING);
    	this.addField("DIC_NAME_CODE",BaseVO.OP_STRING);
    	this.addField("DIC_VALUE_EN",BaseVO.OP_STRING);
    	this.addField("DIC_CODE",BaseVO.OP_STRING);
    	this.addField("DIC_VALUE",BaseVO.OP_STRING);
    	this.addField("DIC_VALUE_SPELL",BaseVO.OP_STRING);
    	this.addField("DIC_YWLB",BaseVO.OP_STRING);
    	this.addField("IS_LEAF",BaseVO.OP_STRING);
    	this.addField("DIC_VALUE3",BaseVO.OP_STRING);
    	this.addField("ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("DIC_VALUE2",BaseVO.OP_STRING);
    	this.addField("DIC_BMFF",BaseVO.OP_STRING);
    	this.addField("PARENT_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("ID","COMMON");
    	this.setVOTableName("DIC_TREE");
}
	public void setDicDmgl(String DicDmgl){
		this.setInternal("DIC_DMGL" ,DicDmgl );
	}


	public String getDicDmgl(){
		return (String)this.getInternal("DIC_DMGL");
	}
	public void setDicSyfw(String DicSyfw){
		this.setInternal("DIC_SYFW" ,DicSyfw );
	}


	public String getDicSyfw(){
		return (String)this.getInternal("DIC_SYFW");
	}
	public void setOrderNum(String OrderNum){
		this.setInternal("ORDER_NUM" ,OrderNum );
	}


	public String getOrderNum(){
		return (String)this.getInternal("ORDER_NUM");
	}
	public void setDicValueAspell(String DicValueAspell){
		this.setInternal("DIC_VALUE_ASPELL" ,DicValueAspell );
	}


	public String getDicValueAspell(){
		return (String)this.getInternal("DIC_VALUE_ASPELL");
	}
	public void setDicLayerTwo(String DicLayerTwo){
		this.setInternal("DIC_LAYER_TWO" ,DicLayerTwo );
	}


	public String getDicLayerTwo(){
		return (String)this.getInternal("DIC_LAYER_TWO");
	}
	public void setDicNode(String DicNode){
		this.setInternal("DIC_NODE" ,DicNode );
	}


	public String getDicNode(){
		return (String)this.getInternal("DIC_NODE");
	}
	public void setDicNameValue(String DicNameValue){
		this.setInternal("DIC_NAME_VALUE" ,DicNameValue );
	}


	public String getDicNameValue(){
		return (String)this.getInternal("DIC_NAME_VALUE");
	}
	public void setDicLayer(String DicLayer){
		this.setInternal("DIC_LAYER" ,DicLayer );
	}


	public String getDicLayer(){
		return (String)this.getInternal("DIC_LAYER");
	}
	public void setDicNameCode(String DicNameCode){
		this.setInternal("DIC_NAME_CODE" ,DicNameCode );
	}


	public String getDicNameCode(){
		return (String)this.getInternal("DIC_NAME_CODE");
	}
	public void setDicValueEn(String DicValueEn){
		this.setInternal("DIC_VALUE_EN" ,DicValueEn );
	}


	public String getDicValueEn(){
		return (String)this.getInternal("DIC_VALUE_EN");
	}
	public void setDicCode(String DicCode){
		this.setInternal("DIC_CODE" ,DicCode );
	}


	public String getDicCode(){
		return (String)this.getInternal("DIC_CODE");
	}
	public void setDicValue(String DicValue){
		this.setInternal("DIC_VALUE" ,DicValue );
	}


	public String getDicValue(){
		return (String)this.getInternal("DIC_VALUE");
	}
	public void setDicValueSpell(String DicValueSpell){
		this.setInternal("DIC_VALUE_SPELL" ,DicValueSpell );
	}


	public String getDicValueSpell(){
		return (String)this.getInternal("DIC_VALUE_SPELL");
	}
	public void setDicYwlb(String DicYwlb){
		this.setInternal("DIC_YWLB" ,DicYwlb );
	}


	public String getDicYwlb(){
		return (String)this.getInternal("DIC_YWLB");
	}
	public void setIsLeaf(String IsLeaf){
		this.setInternal("IS_LEAF" ,IsLeaf );
	}


	public String getIsLeaf(){
		return (String)this.getInternal("IS_LEAF");
	}
	public void setDicValue3(String DicValue3){
		this.setInternal("DIC_VALUE3" ,DicValue3 );
	}


	public String getDicValue3(){
		return (String)this.getInternal("DIC_VALUE3");
	}
	public void setId(String Id){
		this.setInternal("ID" ,Id );
	}


	public String getId(){
		return (String)this.getInternal("ID");
	}
	public void setDicValue2(String DicValue2){
		this.setInternal("DIC_VALUE2" ,DicValue2 );
	}


	public String getDicValue2(){
		return (String)this.getInternal("DIC_VALUE2");
	}
	public void setDicBmff(String DicBmff){
		this.setInternal("DIC_BMFF" ,DicBmff );
	}


	public String getDicBmff(){
		return (String)this.getInternal("DIC_BMFF");
	}
	public void setParentId(String ParentId){
		this.setInternal("PARENT_ID" ,ParentId );
	}


	public String getParentId(){
		return (String)this.getInternal("PARENT_ID");
	}
}

package com.org.framework.base;

import java.io.Serializable;
import java.util.*;
import org.dom4j.*;
import com.ga.isl.util.StringUtil;
import com.org.framework.util.Pub;

import java.math.BigDecimal;

/**
 * @Description:基础vo类
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @author andy 
 */
public class BaseVO
    extends Hashtable<Object, Object> implements Serializable
{
	public static final long serialVersionUID = 1L;
	public static final int OP_STRING = 0x01;
	public static final int OP_DATE = 0x02;
	public static final int OP_INT = 0x04;
	public static final int OP_DOUBLE = 0x08;
	public static final int OP_BLOB = 0x10;
	public static final int OP_NCHAR = 0x20;

	public static final int OP_CLOB = 0x40;
	public static final int OP_NUMBER = 0x80;
	public static final int OP_FLOAT = 0x0100;
	public static final int OP_LONG = 0x0200;

	public static final int TP_PK = 0x010000;
    public static final int TP_SEQUENCE = 0x020000;
    public static final int TP_PARTITION = 0x040000;
    public static final int TP_NORMAL = 0x0;

    private static final String EXT_SEQUENCE = "_S_!@#";
    private static final String EXT_CHANGED = "_C_$%*";
    private static final String EXT_DATE = "_D_#^(";
    private static final String EXT_DIC = "_D_)(*";
    private static final String EXT_DIC_TYPE = "_D_&%#";

    public static final String SIMPLE_NAME = "1";
    public static final String FULL_NAME = "2";
    public static final String DIC_VALUE1 = "1";
    public static final String DIC_VALUE2 = "2";
    public static final String DIC_VALUE3 = "3";

    public static final String DIC_EXT_COMPANY = "EXT_COMPANY";
    public static final String DIC_EXT_DEPT = "EXT_DEPT";
    public static final String DIC_EXT_XZQH = "EXT_XZQH";
    public static final String DIC_EXT_ZZJG = "EXT_ZZJG";
    public static final String DIC_EXT_YHID = "EXT_YHID";
    public static final String DIC_EXT_PERSON = "EXT_PERSON";
    public static final String DIC_EXT_QDZ = "EXT_QDZ";
    public static final String DIC_EXT_AJBH = "EXT_AJBH";
    public static final String DIC_EXT_JLX = "EXT_JLX";

    public static final String DIC_EXT_ORG = "EXT_ORG";
    public static final String DIC_EXT_ROLE = "EXT_ROLE";
    public static final String DIC_EXT_SJBH = "EXT_SJBH";
    public static final String DIC_EXT_WRIT = "EXT_WRIT";

    public BaseVO()
    {
        super(10);
        fieldMap = new Hashtable<Object, Integer>(10);
        mapFieldMap = new Hashtable<Object, Object>(10);
        fieldExtern = new Hashtable<Object, Object>(10);
    }

    public BaseVO(int n)
    {
        super(n, 1);
        fieldMap = new Hashtable<Object, Integer>(n, 1);
        mapFieldMap = new Hashtable<Object, Object>(n, 1);
        fieldExtern = new Hashtable<Object, Object>(n);
    }

    protected Hashtable<Object, Integer> fieldMap;
    protected Hashtable<Object, Object> mapFieldMap;
    private String tableName;
    private String owner = "SZQDMS";
    // private String owner = "SZQTEST";
    protected Hashtable<Object, Object> fieldExtern;
    private String language = "1";
    public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public HashMap<Object, String> getHashMapData() throws Exception
    {
        HashMap<Object, String> res = new HashMap<Object, String>(this.values().size()+2);
        Enumeration<?> enum1 = this.keys();
        //enum1 = this.getFields();
        while(enum1.hasMoreElements())
        {
            Object obj = enum1.nextElement();
            if(this.isBlob((String)obj)){
            	String hexStr = StringUtil.BytesHexString((byte[])this.get(obj));
            	res.put(obj,hexStr);
            }else{
            	res.put(obj,(String) this.get(obj));
            }
        }
        //res.put(WSProperty.getInstance().get_MAP_TABLE_KEY(), this.getVOTableName());
        return res;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<?, String> getHashMapDataWithTableName() throws Exception
    {
        HashMap res = new HashMap(this.values().size()+2);
        Enumeration<?> enum1 = this.keys();
        //enum1 = this.getFields();
        while(enum1.hasMoreElements())
        {
            Object obj = enum1.nextElement();
            if(this.isBlob((String)obj)){
            	String hexStr = StringUtil.BytesHexString((byte[])this.get(obj));
            	res.put(this.getVOTableName()+"."+obj,hexStr);
            }
            else if(this.isDate((String)obj)){
            	//String dateFormat = this.getBindDateFormat((String)obj);
            	String dateStr = Pub.getDate("yyyyMMddHHmmss", (Date)this.get(obj));
            	res.put(this.getVOTableName()+"."+obj,dateStr);
            }
            else if(this.isClob((String)obj)){
            	String hexStr = StringUtil.BytesHexString(((String)this.get(obj)).getBytes());
            	res.put(this.getVOTableName()+"."+obj, hexStr);
            }
            else{
            	res.put(this.getVOTableName()+"."+obj,this.get(obj));
            }
        }
        //res.put(WSProperty.getInstance().get_MAP_TABLE_KEY(), this.getVOTableName());
        return res;
    } 

    public void setChanged(String fieldName)
    {
        if (fieldName != null)
            this.fieldExtern.put(fieldName.toUpperCase() + BaseVO.getExtChanged(),
                                 fieldName);
    }

    public void setChanged()
    {
        Enumeration<?> enum1 = this.fieldMap.keys();
        if (enum1 != null)
        {
            while (enum1.hasMoreElements())
            {
                String key = (String) enum1.nextElement();
                this.fieldExtern.put(key.toUpperCase() + BaseVO.getExtChanged(),
                                     key);
            }
        }
    }

    public void setUnChanged(String fieldName)
    {
        if (fieldName != null)
            this.fieldExtern.remove(fieldName.toUpperCase() + BaseVO.getExtChanged());
    }

    public void setUnChanged()
    {
        Enumeration<?> enum1 = this.fieldMap.keys();
        if (enum1 != null)
        {
            while (enum1.hasMoreElements())
            {
                String key = (String) enum1.nextElement();
                this.fieldExtern.remove(key + BaseVO.getExtChanged());
            }
        }
    }

    public Enumeration<?> getFields()
    {
        return this.fieldMap.keys();
    }

    public String[] getPartitionFields()
    {
        Enumeration<Object> enum1 = this.fieldMap.keys();
        ArrayList<String> list = new ArrayList<String>(3);
        while (enum1.hasMoreElements())
        {
            String key = (String) enum1.nextElement();
            if (this.isPartition(key))
                list.add(key);
        }
        String[] res = new String[list.size()];
        return (String[]) list.toArray(res);
    }

    public String[] getPkFields()
    {
        Enumeration<?> enum1 = this.fieldMap.keys();
        ArrayList<String> list = new ArrayList<String>(3);
        while (enum1.hasMoreElements())
        {
            String key = (String) enum1.nextElement();
            if (this.isPk(key))
                list.add(key);
        }
        String[] res = new String[list.size()];
        return (String[]) list.toArray(res);
    }

    public void setVOTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getVOTableName()
    {
        return this.tableName;
    }

    public void setVOOwner(String owner)
    {
        this.owner = owner;
    }

    public String getVOOwner()
    {
        return this.owner;
    }

    public void bindFieldToSequence(String fieldName, String sequenceName)
    {
        if (fieldExtern == null)
            fieldExtern = new Hashtable<Object, Object>(10);
        fieldExtern.put(fieldName.toUpperCase() + BaseVO.getExtSequence(),
                        sequenceName);
    }
    public void bindFieldToDic(String fieldName, String dicName, String type)
    {
        fieldExtern.put(fieldName.toUpperCase() + BaseVO.getExtDic(), dicName);
        if(type == null || type.trim().equals("")) type = BaseVO.getDicValue1();
        fieldExtern.put(fieldName.toUpperCase() + BaseVO.getExtDicType(),type);
    }

    public String getBindDicType(String fieldName)
    {
        return (String)fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDicType());
    }

    public void bindFieldToDic(String fieldName, String dicName)
    {
        bindFieldToDic(fieldName,dicName,BaseVO.getDicValue1());
    }

    public boolean isBindDic(String fieldName)
    {
        return this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic()) != null;
    }

    public String getBindDic(String fieldName)
    {
        return (String)this.fieldExtern.get(fieldName.toUpperCase() +
                                            BaseVO.getExtDic());
    }
    
    public boolean isBindOrgCompany(String fieldName)
    {
        return this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic()) != null
            && this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic())
            .equals(BaseVO.getDicExtCompany());
    }
    
    public boolean isBindOrgDept(String fieldName)
    {
        return this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic()) != null
            && this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic())
            .equals(BaseVO.getDicExtDept());
    }

    public boolean isBindOrgXzqh(String fieldName)
    {
        return this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic()) != null
            && this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic())
            .equals(BaseVO.getDicExtXzqh());
    }

    public boolean isBindJlx(String fieldName)
    {
        return this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic()) != null
            && this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic())
            .equals(BaseVO.getDicExtJlx());
    }

    public boolean isBindQdz(String fieldName)
    {
        return this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic()) != null
            && this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic())
            .equals(BaseVO.getDicExtQdz());
    }

    public boolean isBindOrgInfo(String fieldName)
    {
        return this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic()) != null
            && this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic())
            .equals(BaseVO.getDicExtXzqh());
    }

    public boolean isBindUserid(String fieldName)
    {
        return this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic()) != null
            && this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDic())
            .equals(BaseVO.getDicExtYhid());
    }

    public boolean isSetDateFormat(String fieldName)
    {
        return this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtDate()) != null;
    }

    public String getBindDateFormat(String fieldName)
    {
        return (String)this.fieldExtern.get(fieldName.toUpperCase() +
                                            BaseVO.getExtDate());
    }

    public void bindFieldToJlx(String fieldName)
    {

        this.bindFieldToDic(fieldName.toUpperCase(),BaseVO.getDicExtJlx(),BaseVO.getFullName());
    }

    public void bindFieldToQdz(String fieldName)
    {
        this.bindFieldToDic(fieldName.toUpperCase(),BaseVO.getDicExtQdz(),BaseVO.getFullName());
    }

    public void bindFieldToOrgCompany(String fieldName)
    {
        this.bindFieldToDic(fieldName.toUpperCase(),BaseVO.getDicExtCompany(),BaseVO.getFullName());
    }
    //type:1简称2全称
    public void bindFieldToOrgCompany(String fieldName, String type)
    {
        this.bindFieldToDic(fieldName.toUpperCase(),BaseVO.getDicExtCompany(),type);
    }
    
    public void bindFieldToSimpleOrgCompany(String fieldName)
    {
        this.bindFieldToDic(fieldName.toUpperCase(),BaseVO.getDicExtCompany(),BaseVO.getSimpleName());
    }
    
    public void bindFieldToOrgDept(String fieldName)
    {
        this.bindFieldToDic(fieldName.toUpperCase(),BaseVO.getDicExtDept(),BaseVO.getFullName());
    }
    
    public void bindFieldToSimpleOrgDept(String fieldName)
    {
        this.bindFieldToDic(fieldName.toUpperCase(),BaseVO.getDicExtDept(),BaseVO.getSimpleName());
    }

    public void bindFieldToOrgDept(String fieldName, String type)
    {
        this.bindFieldToDic(fieldName.toUpperCase(),BaseVO.getDicExtDept(),type);
    }

    public void bindFieldToOrgXzqh(String fieldName)
    {
    	this.bindFieldToDic(fieldName.toUpperCase(),BaseVO.getDicExtXzqh(),BaseVO.getSimpleName());
    }

    public void bindFieldToOrgXzjg(String fieldName, String type)
    {
        this.bindFieldToDic(fieldName.toUpperCase(),BaseVO.getDicExtXzqh(),type);
    }

    public void bindFieldToOrgInfo(String fieldName)
    {
        this.bindFieldToDic(fieldName.toUpperCase(),BaseVO.getDicExtXzqh(),BaseVO.getFullName());
    }

    public void bindFieldToOrgInfo(String fieldName, String type)
    {
        this.bindFieldToDic(fieldName.toUpperCase(),BaseVO.getDicExtXzqh(),type);
    }

    public void bindFieldToUserid(String fieldName)
    {
        this.bindFieldToDic(fieldName.toUpperCase(),BaseVO.getDicExtYhid(),BaseVO.getFullName());
    }

    public void setFieldDateFormat(String fieldName, String format)
    {
        fieldExtern.put(fieldName.toUpperCase() + BaseVO.getExtDate(), format);
    }

    public void addField(String fieldName, int style)
    {
        fieldMap.put(fieldName.toUpperCase(), new Integer(style));
    }
    
    public void addMapField(String fieldName, String mapFieldName)
    {
    	mapFieldMap.put(fieldName.toUpperCase(), mapFieldName.toUpperCase());
    }
    
    public String getMapField(String fieldName)
    {
    	return (String)this.mapFieldMap.get(fieldName.toUpperCase());
    }
    
    public void addField(String fieldName)
    {
        addField(fieldName.toUpperCase(), getTpNormal() | getOpString());
    }

    public void addFields(Element columns)
        throws Exception
    {
        if(columns == null) return;
        List<?> columnList = columns.selectNodes("COLUMN");
        if(columnList == null) return;
        for (int j = 0; j < columnList.size(); j++)
        {
            Element column = (Element) columnList.get(j);
            String fieldName = column.attribute("name").getText().toUpperCase().
                trim();
            String type = column.attribute("type") == null ? "string" :
                column.attribute("type").getText().toLowerCase().trim();
            String dic = column.attribute("dic") == null ? null :
                column.attribute("dic").getText().toUpperCase().trim();
            String dictype = column.attribute("dictype") == null ? null :
                column.attribute("dictype").getText().toUpperCase().trim();
            String pk = column.attribute("pk") == null ? null :
                column.attribute("pk").getText().toLowerCase().trim();
            String format = column.attribute("format") == null ? null :
                column.attribute("format").getText().trim();
            String generator = column.attribute("generator") == null ? null :
                column.attribute("generator").getText().toUpperCase().trim();
            String ispartition = column.attribute("partition") == null ? null :
                column.attribute("partition").getText().toLowerCase().trim();
			String mapFieldName = column.attribute("mapField") == null ? fieldName : 
				column.attribute("mapField").getText().toUpperCase().trim();
            if (Pub.empty(fieldName))
            {
                throw new Exception("Field name can not be empty!");
            }
            int style = 0;
            if (type.equals("string"))
            {
                style |= BaseVO.getOpString();
            }
            else if (type.equals("date"))
            {
                style |= BaseVO.getOpDate();
            }
            else if (type.equals("int"))
            {
                style |= BaseVO.getOpInt();
            }
            else if (type.equals("long"))
            {
                style |= BaseVO.getOpLong();
            }
            else if (type.equals("float"))
            {
                style |= BaseVO.getOpFloat();
            }
            else if (type.equals("double"))
            {
                style |= BaseVO.getOpDouble();
            }
            else if (type.equals("number") || type.equals("numeric"))
            {
                style |= BaseVO.getOpNumber();
            }
            else if (type.equals("blob"))
            {
                style |= BaseVO.getOpBlob();
            }
            else if (type.equals("clob"))
            {
                style |= BaseVO.getOpClob();
            }
            else if (type.equals("nchar"))
            {
                style |= BaseVO.getOpNchar();
            }
            if (pk != null && pk.equals("true"))
            {
                style |= BaseVO.getTpPk();
            }
            else
            {
                style |= BaseVO.getTpNormal();
            }
            if (ispartition != null && ispartition.equals("true"))
            {
                style |= BaseVO.getTpPartition();
            }
            this.addField(fieldName, style);
            this.addMapField(fieldName, mapFieldName);
            if (!Pub.empty(generator))
            {
                this.bindFieldToSequence(fieldName, generator);
            }
            if (!Pub.empty(format) && type.equals("date"))
            {
                this.setFieldDateFormat(fieldName, format);
            }
            //字典绑定
            if (!Pub.empty(dic))
            {
                this.bindFieldToDic(fieldName, dic, dictype);
            }
        }
    }

    public void setInternal(String fieldName, Object obj)
    {
        if (fieldMap.containsKey(fieldName.toUpperCase()))
        {
            this.fieldExtern.put(fieldName.toUpperCase() + BaseVO.getExtChanged(),
                                 fieldName);
            if (obj != null)
                this.put(fieldName.toUpperCase(), obj);
            else
                this.remove(fieldName.toUpperCase());
        }
    }

    public boolean isValidField(String fieldName)
    {
        return this.fieldMap.containsKey(fieldName);
    }

    public Object getInternal(String fieldName)
    {
        return this.get(fieldName.toUpperCase());
    }

    public int getFieldStyle(String fieldName)
    {
        return ( (Integer)this.fieldMap.get(fieldName.toUpperCase())).intValue() &
            0xffff0000;
    }

    public int getFieldType(String fieldName)
    {
        return ( (Integer)this.fieldMap.get(fieldName.toUpperCase())).intValue() &
            0x0000ffff;
    }

    public boolean isNumber(String fieldName)
    {
        return ( ( (Integer)this.fieldMap.get(fieldName.toUpperCase())).
                intValue() &
                BaseVO.getOpNumber()) > 0;
    }

    public boolean isBlob(String fieldName)
    {
        return ( ( (Integer)this.fieldMap.get(fieldName.toUpperCase())).
                intValue() & BaseVO.getOpBlob()) > 0;
    }

    //add by andy.ten@tom.com for clob field 20070905
    public boolean isClob(String fieldName)
    {
        return ( ( (Integer)this.fieldMap.get(fieldName.toUpperCase())).
                intValue() & BaseVO.getOpClob()) > 0;
    }

    public boolean isPartition(String fieldName)
    {
        return ( ( (Integer)this.fieldMap.get(fieldName.toUpperCase())).
                intValue() & BaseVO.getTpPartition()) > 0;
    }

    public boolean isNchar(String fieldName)
    {
        return ( ( (Integer)this.fieldMap.get(fieldName.toUpperCase())).
                intValue() & BaseVO.getOpNchar()) > 0;
    }

    public boolean isBindSequence(String fieldName)
    {
        return this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtSequence()) != null;
    }

    public String getBindSequence(String fieldName)
    {
        return (String)this.fieldExtern.get(fieldName.toUpperCase() +
                                            BaseVO.getExtSequence());
    }

    public boolean isChanged(String fieldName)
    {
        return this.fieldExtern.get(fieldName.toUpperCase() + BaseVO.getExtChanged()) != null;
    }

    public boolean isEmpty(String fieldName)
    {
        if (this.get(fieldName.toUpperCase()) instanceof java.lang.String)
        {
            return Pub.empty( (String)this.get(fieldName.toUpperCase()));
        }
        return this.get(fieldName.toUpperCase()) == null;
    }

    public boolean isDate(String fieldName)
    {
        return ( ( (Integer)this.fieldMap.get(fieldName.toUpperCase())).
                intValue() & BaseVO.getOpDate()) > 0;
    }

    public boolean isDouble(String fieldName)
    {
        return ( ( (Integer)this.fieldMap.get(fieldName.toUpperCase())).
                intValue() &
                BaseVO.getOpDouble()) > 0;
    }

    public boolean isFloat(String fieldName)
    {
        return ( ( (Integer)this.fieldMap.get(fieldName.toUpperCase())).
                intValue() &
                BaseVO.getOpFloat()) > 0;
    }

    public boolean isLong(String fieldName)
    {
        return ( ( (Integer)this.fieldMap.get(fieldName.toUpperCase())).
                intValue() &
                BaseVO.getOpLong()) > 0;
    }

    public boolean isString(String fieldName)
    {
        return ( ( (Integer)this.fieldMap.get(fieldName.toUpperCase())).
                intValue() & BaseVO.getOpString()) > 0;
    }

    public boolean isInt(String fieldName)
    {
        return ( ( (Integer)this.fieldMap.get(fieldName.toUpperCase())).
                intValue() & BaseVO.getOpInt()) > 0;
    }

    public boolean isPk(String fieldName)
    {
        return ( ( (Integer)this.fieldMap.get(fieldName.toUpperCase())).
                intValue() & BaseVO.getTpPk()) > 0;
    }

    public Document getDocument()
        throws Exception
    {
        return DocumentHelper.parseText(this.getXml());
    }

    public String getXml()
        throws Exception
    {
        return "<VO>" + this.getDefinitionXml() + this.getRowXml() + "</VO>";
    }

    public Document getDefinitionDocument()
        throws Exception
    {
        return DocumentHelper.parseText(this.getDefinitionXml());
    }

    public String getDefinitionXml()
        throws Exception
    {
        String res = "<TABLE name=\"" +
            Pub.catXml(this.getVOTableName() == null ? "" : this.getVOTableName())
            + "\" catalog=\"" +
            Pub.catXml(this.getVOOwner() == null ? "" : this.getVOOwner()) +
            "\""
            + " class=\"" + this.getClass().getName() + "\">";
        Enumeration<?> enum1 = this.getFields();
        while (enum1.hasMoreElements())
        {
            String key = (String) enum1.nextElement();
            res += "<COLUMN name=\"" + Pub.catXml(key) + "\" type=\"";
            if (this.isString(key))
                res += "string";
            else if (this.isInt(key))
                res += "int";
            else if (this.isDouble(key))
                res += "double";
            else if (this.isFloat(key))
                res += "float";
            else if (this.isLong(key))
                res += "long";
            else if (this.isNumber(key))
                res += "number";
            else if (this.isBlob(key))
                res += "blob";
            else if (this.isClob(key))
                res += "clob";
            else if (this.isDate(key))
                res += "date";
            else if (this.isNchar(key))
                res += "nchar";
            else
                res += "string";
            res += "\"";
            if (this.isPk(key))
                res += " pk=\"true\"";
            if (this.isBindSequence(key))
                res += " generator=\"" + Pub.catXml(this.getBindSequence(key)) +
                    "\"";
            if (this.isPartition(key))
                res += " partition=\"true\"";
            if (this.isSetDateFormat(key))
                res += " format=\"" + Pub.catXml(this.getBindDateFormat(key)) +
                    "\"";
            if (this.isBindDic(key))
            {
                res += " dic=\"" + Pub.catXml(this.getBindDic(key)) + "\"";
                res += " dictype=\"" + Pub.catXml(this.getBindDicType(key)) +
                    "\"";
            }
            res += "/>";
        }
        return res + "</TABLE>";
    }

    public Document getRowDocument()
        throws Exception
    {
        return DocumentHelper.parseText(this.getRowXml());
    }

    public String getRowXml()
        throws Exception
    {
        String xml = "<ROW>";
        Enumeration<?> enum1 = getFields();
        while (enum1.hasMoreElements())
        {
            String key = (String) enum1.nextElement();
            String value = "";
            xml += "<" + key;
            if (isDate(key))
            {
                Date date = (Date)this.getInternal(key);
                if (date != null)
                {
                    value = Pub.getDate("yyyyMMddHHmmss", date);
                    if (isSetDateFormat(key))
                    {
                        String sv = Pub.getDate(this.getBindDateFormat(key),
                                                date);
                        xml += " sv=\"" + sv + "\"";
                    }
                    else
                    {
                        xml += " sv=\"" + value + "\"";
                    }
                }
            }
            else if (isBlob(key))
            {
                byte[] barr = (byte[])this.getInternal(key);
                if (barr != null)
                {
                    value = Pub.toBase64(barr);
                }else value = "";
            }
            //add by andy.ten@tom.com for clob field 20070905
            else if (isClob(key))
            {
                value = (String)this.getInternal(key);
                if (value == null)
                    value = "";
            }
            else if (isFloat(key))
            {
                value = this.getInternal(key) == null ? "" :
                    ( (Float)this.getInternal(key)).toString();
            }
            else if (isDouble(key))
            {
                value = this.getInternal(key) == null ? "" :
                    ( (Double)this.getInternal(key)).toString();
            }
            else //if(isString(key) || isInt(key) || isNchar(key) || isNumber(key))
            {
                Object obj = this.getInternal(key);
                if (obj == null)
                    value = "";
                else
                {
                    if (obj instanceof java.lang.String)
                        value = (String) obj;
                    if (obj instanceof java.lang.Integer)
                        value = ( (Integer) obj).toString();
                    if (obj instanceof java.lang.Long)
                        value = ( (Long) obj).toString();
                    if (obj instanceof java.math.BigDecimal)
                        value = ( (BigDecimal) obj).toPlainString();
                    if (value == null)
                        value = "";
                    
                    if (isBindOrgCompany(key) &&
                            getBindDicType(key).equals(BaseVO.getFullName()))
                    {
                        String strval = Pub.getCompanyNameByID(value);
                        if (strval == null)
                            strval = "";
                        xml += " sv=\"" + Pub.catXml(strval) + "\"";
                    }else if (isBindOrgCompany(key) &&
                             getBindDicType(key).equals(BaseVO.getSimpleName()))
                    {
                        String strval = Pub.getCompanySimepleNameByID(value);
                        if (strval == null)
                            strval = "";
                        xml += " sv=\"" + Pub.catXml(strval) + "\"";
                    }else if (isBindOrgDept(key) && getBindDicType(key).equals(BaseVO.getFullName()))
                    {
                        String strval = Pub.getDeptNameByID(value);
                        if (strval == null)
                            strval = "";
                        xml += " sv=\"" + Pub.catXml(strval) + "\"";
                    }
                    else if (isBindOrgDept(key) &&
                             getBindDicType(key).equals(BaseVO.getSimpleName()))
                    {
                        String strval = Pub.getDeptSimepleNameByID(value);
                        if (strval == null)
                            strval = "";
                        xml += " sv=\"" + Pub.catXml(strval) + "\"";
                    }
                    else if (isBindOrgXzqh(key) &&
                             getBindDicType(key).equals(BaseVO.getFullName()))
                    {
                    	//核心库将XZQH作为字典处理
                    	//String strval = Pub.getDictValueByCode("XZQH", value);
                    	String strval = Pub.getXzqhFullNameByID(value);
                        if (strval == null)
                            strval = "";
                        xml += " sv=\"" + Pub.catXml(strval) + "\"";
                    }
                    else if (isBindOrgXzqh(key) &&
                             getBindDicType(key).equals(BaseVO.getSimpleName()))
                    {
                    	//核心库将XZJG作为字典处理
                    	//String strval = Pub.getDictValueByCode("XZQH", value);
                    	String strval = Pub.getXzqhSimepleNameByID(value);
                        if (strval == null)
                            strval = "";
                        xml += " sv=\"" + Pub.catXml(strval) + "\"";
                    }
                    else if (isBindUserid(key))
                    {
                        String[] dics = value.split(",");
                        if (dics.length == 1)
                        {
                            String strval = Pub.getUserNameByLoginId(value);
                            if (strval == null)
                                strval = "";
                            xml += " sv=\"" + Pub.catXml(strval) + "\"";
                        }
                        else
                        {
                            String sv = " sv=\"";
                            for (int i = 0; i < dics.length; i++)
                            {
                                String strval = Pub.getUserNameByLoginId(dics[i]);
                                sv = sv + Pub.catXml(strval);
                                if (i < dics.length - 1)
                                {
                                    sv = sv + ",";
                                }
                            }
                            xml += sv + "\"";
                        }
                    }else if (isBindDic(key))
                    {
                        String[] dics = value.split(",");
                        if (dics.length == 1)
                        {
                            String strval = Pub.getDictValueByCode(this. 
                                getBindDic(
                                    key), value, language);
                            if (strval == null)
                                strval = "";
                            xml += " sv=\"" + strval + "\"";
                        }
                        else
                        {
                            String sv = " sv=\"";
                            for (int i = 0; i < dics.length; i++)
                            {
                                String strval = Pub.getDictValueByCode(this.  
                                    getBindDic(
                                        key), dics[i], language);
                                sv = sv + Pub.catXml(strval);
                                if (i < dics.length - 1)
                                {
                                    sv = sv + ",";
                                }
                            }
                            xml += sv + "\"";
                        }
                    }
                }
                //added by andy 20081224 如果value 行首有空格，则增加节点属性记录空格数
                int n = 0;
                if (value.startsWith(" "))
                {
                    n++;
                    for (int j = 1; j < value.length(); j++)
                    {
                        if (" ".equals(String.valueOf(value.charAt(j))))
                            n++;
                        else
                            break;
                    }
                }
                if (n > 0)
                    xml += " spaces='" + n + "'";
            }
            xml += ">" + Pub.catXml(value);
            xml += "</" + key + ">";
        }
        //end;
        xml += "</ROW>";
        return xml;
    }

	public boolean setValue(Element node) throws Exception {
		// if(node == null) return false;
		List<?> list = node.elements();
		for (int i = 0; i < list.size(); i++) {
			Element field = (Element) list.get(i);
			String key = field.getName().toUpperCase();
			if (!this.fieldMap.containsKey(key))
				continue;
			this.setChanged(key);
			if (this.isBlob(key)) {
				if (!Pub.empty(field.getText()))
					this.setInternal(key, Pub.fromBase64(field.getText()));
			}
			// add by andy.ten@tom.com for clob field 20070905
			else if (this.isClob(key)) {
				if (!Pub.empty(field.getText()))
					// this.setInternal(key,Pub.fromBase64(field.getText()));
					this.setInternal(key, field.getText());
			} else if (this.isDate(key)) {
				if (!Pub.empty(field.getText())) {
					String val = field.getText().replaceAll("[\\s\\p{Punct}]",
							"").trim()
							+ "00000000000000";
					this.setInternal(key, Pub.toDate("yyyyMMddHHmmss", val
							.substring(0, 14)));
				}
			} else if (this.isInt(key)) {
				if (!Pub.empty(field.getText())) {
					this.setInternal(key, Integer.valueOf(field.getText()));
				}
			} else if (this.isLong(key)) {
				if (!Pub.empty(field.getText()))
					this.setInternal(key, Long.valueOf(field.getText()));
			} else if (this.isFloat(key)) {
				if (!Pub.empty(field.getText()))
					this.setInternal(key, Float.valueOf(field.getText()));
			} else if (this.isDouble(key)) {
				if (!Pub.empty(field.getText())) {
					this.setInternal(key, Double.valueOf(field.getText()));
				}
			} else if (this.isNumber(key)) {
				if (!Pub.empty(field.getText())) {
					this.setInternal(key, new BigDecimal(field.getText()));
				}
			} else
				this.setInternal(key, field.getText() == null ? "" : field
						.getText());
		}
		return true;
	}

	public boolean setMapValue(Element node) throws Exception {
		List<?> list = node.elements();
		String[][] mapArr = new String[this.mapFieldMap.size()][2];
		int x = 0;
		for (Enumeration<?> enums = this.mapFieldMap.keys(); enums.hasMoreElements();) {
			String tempKey = (String) enums.nextElement();
			mapArr[x][0] = tempKey ;
			mapArr[x][1] = new String((String) this.mapFieldMap.get(tempKey));
			x = x + 1 ;
		}				
		for (int i = 0; i < list.size(); i++) {
			Element field = (Element) list.get(i);
			String mapValue = field.getName().toUpperCase();
			// -----			
			if(!this.mapFieldMap.containsValue(mapValue)){
				continue;
			}
			ArrayList<String> keys = new ArrayList<String>();
			int k = 0 ;
			for(int j = 0 ; j < mapArr.length ; j++ ){
				if(mapArr[j][1].equals(mapValue)){
					keys.add(k, mapArr[j][0]);
					k = k + 1;
				}
			}
			if(keys.size() == 0)
				throw new Exception("Failed to map the fields.mapValue="+mapValue);
			// -----
			String key = null;
			for(int m = 0 ; m < keys.size() ; m++ ){
				key = keys.get(m);
				this.setChanged(key);
				if (this.isBlob(key)) {
					if (!Pub.empty(field.getText()))
						this.setInternal(key, Pub.fromBase64(field.getText()));
				}
				// add by andy.ten@tom.com for clob field 20070905
				else if (this.isClob(key)) {
					if (!Pub.empty(field.getText()))
						this.setInternal(key, field.getText());
				} else if (this.isDate(key)) {
					if (!Pub.empty(field.getText())) {
						String val = field.getText().replaceAll(
								"[\\s\\p{Punct}]", "").trim()
								+ "00000000000000";
						this.setInternal(key, Pub.toDate("yyyyMMddHHmmss", val
								.substring(0, 14)));
					}
				} else if (this.isInt(key)) {
					if (!Pub.empty(field.getText())) {
						this.setInternal(key, Integer.valueOf(field.getText()));
					}
				} else if (this.isLong(key)) {
					if (!Pub.empty(field.getText()))
						this.setInternal(key, Long.valueOf(field.getText()));
				} else if (this.isFloat(key)) {
					if (!Pub.empty(field.getText()))
						this.setInternal(key, Float.valueOf(field.getText()));
				} else if (this.isDouble(key)) {
					if (!Pub.empty(field.getText())) {
						this.setInternal(key, Double.valueOf(field.getText()));
					}
				} else if (this.isNumber(key)) {
					if (!Pub.empty(field.getText())) {
						this.setInternal(key, new BigDecimal(field.getText()));
					}
				} else
					this.setInternal(key, field.getText() == null ? "" : field
							.getText());
			}
		}
		return true;
	}    

	public void copyDefinition(BaseVO svo) {
		this.setVOOwner(svo.getVOOwner());
		this.setVOTableName(svo.getVOTableName());
		// this.clear();
		this.fieldExtern.clear();
		this.fieldMap.clear();
		this.mapFieldMap.clear();
		for (Enumeration<?> enums = svo.fieldExtern.keys(); enums
				.hasMoreElements();) {
			String key = (String) enums.nextElement();
			this.fieldExtern.put(key, new String((String) svo.fieldExtern
					.get(key)));
		}
		for (Enumeration<?> enums = svo.fieldMap.keys(); enums.hasMoreElements();) {
			String key = (String) enums.nextElement();
			this.fieldMap.put(key, new Integer(
					((Integer) svo.fieldMap.get(key)).intValue()));

		}
		for (Enumeration<?> mapenums = svo.mapFieldMap.keys(); mapenums
				.hasMoreElements();) {
			String mapKey = (String) mapenums.nextElement();
			this.mapFieldMap
					.put(mapKey, (String) (svo.mapFieldMap.get(mapKey)));
		}
	}

	public void setValueByChanged(BaseVO vo) {
		Enumeration<?> enum1 = vo.getFields();
		while (enum1.hasMoreElements()) {
			String key = (String) enum1.nextElement();
			if (vo.isChanged(key)) {
				if (this.isValidField(key)) {
					if (vo.get(key) != null)
						this.setInternal(key, vo.get(key));
					else
						this.setChanged(key);
				}
			}
		}
	}
	
	/**
	 * added by andy.ten@tom.com 2011-12-06
	 */
	public boolean setValue(HashMap<String, String> hm) throws Exception {

		if(hm.size() == 0) throw new Exception("Invalid parameter.");
		for (Map.Entry<String, String> entry : hm.entrySet())
        {
			String key = entry.getKey().toUpperCase();
			String text = entry.getValue();
			if (!this.fieldMap.containsKey(key))
				continue;
			this.setChanged(key);
			if (this.isBlob(key)) 
			{
				if (!Pub.empty(text))
					this.setInternal(key, Pub.fromBase64(text));
			}
			// add by andy.ten@tom.com for clob field 20070905
			else if (this.isClob(key)) 
			{
				if (!Pub.empty(text))
					// this.setInternal(key,Pub.fromBase64(field.getText()));
					this.setInternal(key, text);
			} else if (this.isDate(key)) 
			{
				if (!Pub.empty(text)) 
				{
					String val = text.replaceAll("[\\s\\p{Punct}]","").trim()+ "00000000000000";
					this.setInternal(key, Pub.toDate("yyyyMMddHHmmss", val.substring(0, 14)));
				}
			} else if (this.isInt(key)) 
			{
				if (!Pub.empty(text)) 
				{
					this.setInternal(key, Integer.valueOf(text));
				}
			} else if (this.isLong(key)) 
			{
				if (!Pub.empty(text))
					this.setInternal(key, Long.valueOf(text));
			} else if (this.isFloat(key)) 
			{
				if (!Pub.empty(text))
					this.setInternal(key, Float.valueOf(text));
			} else if (this.isDouble(key)) 
			{
				if (!Pub.empty(text)) 
				{
					this.setInternal(key, Double.valueOf(text));
				}
			} else if (this.isNumber(key)) 
			{
				if (!Pub.empty(text)) 
				{
					this.setInternal(key, new BigDecimal(text));
				}
			} else
				this.setInternal(key, text == null ? "" : text);
        }
		return true;
	}

	public static int getTpPartition() {
		return TP_PARTITION;
	}

	public static int getTpNormal() {
		return TP_NORMAL;
	}

	public static String getExtDate() {
		return EXT_DATE;
	}

	public static String getDicExtYhid() {
		return DIC_EXT_YHID;
	}

	public static String getDicExtXzqh() {
		return DIC_EXT_XZQH;
	}

	public static String getDicExtQdz() {
		return DIC_EXT_QDZ;
	}

	public static String getDicExtJlx() {
		return DIC_EXT_JLX;
	}

	public static String getDicExtDept() {
		return DIC_EXT_DEPT;
	}

	public static String getDicExtCompany() {
		return DIC_EXT_COMPANY;
	}

	public static String getExtDic() {
		return EXT_DIC;
	}

	public static String getDicValue1() {
		return DIC_VALUE1;
	}

	public static String getExtDicType() {
		return EXT_DIC_TYPE;
	}

	public static String getExtSequence() {
		return EXT_SEQUENCE;
	}

	public static String getExtChanged() {
		return EXT_CHANGED;
	}

	public static int getOpNchar() {
		return OP_NCHAR;
	}

	public static int getOpClob() {
		return OP_CLOB;
	}

	public static int getOpBlob() {
		return OP_BLOB;
	}

	public static int getOpNumber() {
		return OP_NUMBER;
	}

	public static int getOpString() {
		return OP_STRING;
	}

	public static int getOpDate() {
		return OP_DATE;
	}

	public static int getOpInt() {
		return OP_INT;
	}

	public static String getFullName() {
		return FULL_NAME;
	}

	public static int getOpDouble() {
		return OP_DOUBLE;
	}

	public static int getTpPk() {
		return TP_PK;
	}

	public static String getSimpleName() {
		return SIMPLE_NAME;
	}

	public static int getOpFloat() {
		return OP_FLOAT;
	}

	public static int getOpLong() {
		return OP_LONG;
	}
}
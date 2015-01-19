package com.org.frameImpl;

/**
 * @Description:数据生成xml
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @mail   andy.ten@tom.com
 */
public class DataToXmlImpl {

	/**
	 * 所有数据生成
	 */
	public static final String g_YW_GYL_KC_CK = "generateYwGylKcCk"; // 生成仓库
	
	
	/**
	 * 查询YW_GYL_KC_CK
	 */
	public static String generateYwGylKcCk() 
	{
		String sql = "";
		sql = " SELECT ORG_ID,CODE,SNAME,PID,BUS_TYPE,ORG_TYPE FROM TM_ORG WHERE STATUS ="+Constant.YXBS_01+" ORDER BY CDDE ASC";
		return sql;
	}

}

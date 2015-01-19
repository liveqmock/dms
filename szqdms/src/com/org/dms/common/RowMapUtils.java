package com.org.dms.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.org.framework.common.QuerySet;

/**
 * 
 * ClassName: RowMapUtils 
 * Function: 格式转换工具
 * date: 2014年10月23日 下午4:09:21
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public final class RowMapUtils {
	
	/**
	 * 
	 * toConvert: 将QuerySet转为Map
	 * key：固定为:"ROW_"+行号（行号从0开始）
	 * value:为Map，此Map中key为查询的字段(大写)+"_D"（D标示details的意思，加_D是为了区分主页控件，防止ID重复)，value为字段对应的值
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public static final Map<String,Map<String,String>> toConvert(QuerySet qs){
		Map<String, Map<String, String>> resultMap = new HashMap<String, Map<String, String>>();	// 用来保存拼接的数据，用来转换成JSON
		
		// 编辑查询结果集
		for (int i = 0; i < qs.getRowCount(); i++) {
			Map<String, String> rowMap = new HashMap<String, String>();
			for(Iterator<?> iter = qs.getAttrMap().keySet().iterator(); iter.hasNext();){
				String key = String.valueOf(iter.next());
				rowMap.put(key.toUpperCase()+"_D", qs.getString(i + 1, key));
			}
			resultMap.put("ROW_" + i, rowMap);
		}
		
		return resultMap;
	}
}

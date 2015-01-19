package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.vo.part.PtBaChannelSafestockVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class ChannelSafeStockDao extends BaseDAO
{
    //定义instance
    public  static final ChannelSafeStockDao getInstance(ActionContext atx)
    {
    	ChannelSafeStockDao dao = new ChannelSafeStockDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	/**
	 * 渠道安全库存新增
	 * 
     * 1、查询渠道库所有配件信息
     * 2、查询库存上下限的所有信息
     * 3、2中不存在1中的所有信息放到pt_ba_channel_safestocks表中(即增量新增)
     * @throws Exception
     * @Author suoxiuli 2014-07-25
     */
	public boolean insertChannelSafeStock(PtBaChannelSafestockVO vo) throws Exception
    {
    	return factory.insert(vo);
    }
	
    /**
	 * 渠道安全库存修改
	 * @throws Exception
     * @Author suoxiuli 2014-07-25
	 */
	public boolean updateChannelSafeStock(PtBaChannelSafestockVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 渠道安全库存查询
	 * @throws Exception
     * @Author suoxiuli 2014-07-25
	 */
    public BaseResultSet search(PageManager page, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND A.STATUS = 100201 ";
    	wheres += " AND A.ORG_ID = B.ORG_ID(+) ";
    	wheres += " AND A.PART_ID = B.PART_ID(+) ";
		wheres += " ORDER BY A.ORG_CODE, A.PART_CODE DESC ";
		page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DISTINCT A.ORG_ID as ORG_ID,\n" );
    	sql.append("                A.ORG_CODE as ORG_CODE,\n" );
    	sql.append("                A.ORG_NAME as ORG_NAME,\n" );
    	sql.append("                A.PART_ID as PART_ID,\n" );
    	sql.append("                A.PART_CODE as PART_CODE,\n" );
    	sql.append("                A.PART_NAME as PART_NAME,\n" );
    	sql.append("                B.SAFTY_ID as SAFTY_ID,\n" );
    	sql.append("                B.UPPER_LIMIT as UPPER_LIMIT,\n" );
    	sql.append("                B.LOWER_LIMIT as LOWER_LIMIT,\n" );
    	sql.append("                B.STATUS as STATUS　\n" );
    	sql.append("  FROM PT_BU_DEALER_STOCK A, PT_BA_CHANNEL_SAFESTOCK B\n" );

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }

    /**
     * 重置车厂安全库存的上下限
     * @throws Exception
     * @Author suoxiuli 2014-07-25
     */
    public boolean updateChannelSafeStockLowerUpper(String safeId, String updateUser) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update pt_ba_channel_safestock \n");
    	sql.append(" set lower_limit = '', \n");
    	sql.append(" upper_limit = '', \n");
    	sql.append(" update_user = '" + updateUser + "', \n");
    	sql.append(" update_time = sysdate \n");
    	sql.append(" where safty_id = '" + safeId + "' \n");
    	return factory.update(sql.toString(), null);
    }
    
    /**
     * 渠道安全库存临时表查询（校验临时表数据:1、空校验）
     * 点击"确定"按钮需要调用此方法
     * @throws Exception
     * @Author suoxiuli 2014-09-01
     */
    public QuerySet searchTmp(User user, String errorInfoRowNum)throws Exception{
		
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT \n" );
		sql.append(" TMP_ID,\n" );
		sql.append(" ROW_NUM,\n" );
		sql.append(" ORG_CODE,\n" );
		sql.append(" PART_CODE,\n" );
		sql.append(" LOWER_LIMIT,\n" );
		sql.append(" UPPER_LIMIT,\n" );
		sql.append(" USER_ACCOUNT\n" );
		sql.append(" FROM PT_BA_CHANNEL_SAFESTOCK_TMP\n" );
		sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		if (!errorInfoRowNum.equals("")) {
			sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
		}
		
		return factory.select(null, sql.toString());
	}

    /**
     * 判断渠道商CODE 和 配件CODE是否存在(校验临时表数据:2、是否存在检验)
     * @throws Exception
     * @Author suoxiuli 2014-09-03
     */
	public QuerySet checkOrgPartCode(String orgCode, String partCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM PT_BU_DEALER_STOCK \n");
    	sql.append(" WHERE ORG_CODE = '" + orgCode +"'");
    	sql.append(" AND PART_CODE = '" + partCode +"'");
    	sql.append(" AND STATUS = 100201 ");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
    /**
     * 渠道安全库存临时表查询（校验临时表数据:3、重复数据检验）
     * @throws Exception
     * @Author suoxiuli 2014-10-24
     */
    public QuerySet searchChanSafeStockTmpRepeatData(User user, String orgCode, String partCode)throws Exception{
		
    	StringBuffer sql= new StringBuffer();
    	if (!orgCode.equals("") && !partCode.equals("")) {
	    	sql.append("SELECT A.ROW_NUM, A.ORG_CODE, A.PART_CODE\n" );
	    	sql.append("  FROM PT_BA_CHANNEL_SAFESTOCK_TMP A,\n" );
	    	sql.append("       (");
    	}
	    sql.append("SELECT ORG_CODE, PART_CODE, COUNT(ORG_CODE)\n" );
    	sql.append("          FROM PT_BA_CHANNEL_SAFESTOCK_TMP\n" );
    	sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("         GROUP BY ORG_CODE, PART_CODE\n" );
    	sql.append("        HAVING COUNT(ORG_CODE) > 1");
    	if (!orgCode.equals("") && !partCode.equals("")) {
    		sql.append(")B\n" );
    		sql.append(" WHERE A.ORG_CODE = B.ORG_CODE\n" );
        	sql.append("   AND A.PART_CODE = B.PART_CODE\n" );
        	sql.append("   AND A.ORG_CODE = '"+orgCode+"'\n" );
	    	sql.append("   AND A.PART_CODE = '"+partCode+"'\n" );
        	sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        	sql.append("   ORDER BY A.ROW_NUM \n" );
    	}
    	
		return factory.select(null, sql.toString());
	}
    
    /**
	 * 渠道安全库存临时表查询（(导入数据正确，显示临时表数据）
	 * @throws Exception
     * @Author suoxiuli 2014-09-01
	 */
    public BaseResultSet searchTmp(PageManager page, String conditions, User user) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND USER_ACCOUNT = '"+user.getAccount()+"'";
		wheres += " ORDER BY TMP_ID DESC ";
		page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT \n" );
		sql.append(" TMP_ID,\n" );
		sql.append(" ROW_NUM,\n" );
		sql.append(" ORG_CODE,\n" );
		sql.append(" PART_CODE,\n" );
		sql.append(" LOWER_LIMIT,\n" );
		sql.append(" UPPER_LIMIT,\n" );
		sql.append(" USER_ACCOUNT\n" );
		sql.append(" FROM PT_BA_CHANNEL_SAFESTOCK_TMP\n" );

    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    
  //----------------------------下面是点击确定按钮，需要调用的方法----------------------------
    /**
     * 判断表PT_BA_CHANNEL_SAFESTOCK服务商CODE 和 配件CODE是否存在
     * @throws Exception
     * @Author suoxiuli 2014-10-27
     */
	public QuerySet checkChannelSafeStocksCode(String orgCode, String partCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT SAFTY_ID \n");
    	sql.append(" FROM PT_BA_CHANNEL_SAFESTOCK \n");
    	sql.append(" WHERE ORG_CODE = '" + orgCode +"'\n");
    	sql.append(" AND PART_CODE = '" + partCode +"'\n");
    	sql.append(" AND STATUS = 100201 \n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }

	/**
     * 导入新增：（临时表的数据有两种：安全库存不存在--则新增，否则--则更新）
     * @throws Exception
     * @Author suoxiuli 2014-08-30
     */
	public boolean insertImportChannSafeStockFromTmp(User user, String orgCode, String partCode) throws Exception{

		StringBuffer sql= new StringBuffer();
		sql.append("insert into PT_BA_CHANNEL_SAFESTOCK\n" );
		sql.append("   (SAFTY_ID,\n" );
		sql.append("    ORG_ID,\n" );
		sql.append("    ORG_CODE,\n" );
		sql.append("    ORG_NAME,\n" );
		sql.append("    PART_ID,\n" );
		sql.append("    PART_CODE,\n" );
		sql.append("    PART_NAME,\n" );
		sql.append("    LOWER_LIMIT,\n" );
		sql.append("    UPPER_LIMIT,\n" );
		sql.append("    CREATE_USER,\n" );
		sql.append("    UPDATE_TIME,\n" );
		sql.append("    STATUS)\n" );
		sql.append("   select f_getid(),\n" );
		sql.append("          A.ORG_ID,\n" );
		sql.append("          A.ORG_CODE,\n" );
		sql.append("          A.ORG_NAME,\n" );
		sql.append("          A.PART_ID,\n" );
		sql.append("          A.PART_CODE,\n" );
		sql.append("          A.PART_NAME,\n" );
		sql.append("          B.LOWER_LIMIT,\n" );
		sql.append("          B.UPPER_LIMIT,\n" );
		sql.append("          '"+ user.getAccount() +"',\n" );
		sql.append("          sysdate,\n" );
		sql.append("          100201\n" );
		sql.append("     from PT_BU_DEALER_STOCK A, PT_BA_CHANNEL_SAFESTOCK_TMP B\n" );
		sql.append("    WHERE A.ORG_CODE = B.ORG_CODE\n" );
		sql.append("      AND A.PART_CODE = B.PART_CODE\n" );
		sql.append("      AND B.ORG_CODE ='"+ orgCode +"'\n" );
		sql.append("      AND B.PART_CODE ='"+ partCode +"'");

    	return factory.update(sql.toString(), null);
	}
	
	/**
     * 导入更新：（临时表的数据有两种：安全库存不存在--则新增，否则--则更新）
     * @throws Exception
     * @Author suoxiuli 2014-10-27
     */
	public boolean updateImportChannSafeStockFromTmp(User user, String saftyId, String lowerLimit, String upperLimit) throws Exception{

		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BA_CHANNEL_SAFESTOCK\n" );
    	sql.append(" SET LOWER_LIMIT = "+ lowerLimit +", \n");
    	sql.append(" UPPER_LIMIT = "+ upperLimit +", \n");
    	sql.append(" UPDATE_USER = '" + user.getAccount() + "', \n");
    	sql.append(" UPDATE_TIME = SYSDATE \n");
    	sql.append(" WHERE SAFTY_ID = '" + saftyId + "' \n");

    	return factory.update(sql.toString(), null);
	}
//----------------------报表查询：配送中心库存预警明细查询--------------------------------------------------	
	/**
     * 配送中心库存预警明细查询
     * @throws Exception
     * @Author suoxiuli 2014-10-29
     */
	public BaseResultSet DCSafeStockWarnQuery(PageManager page,String conditions) throws Exception
    {
/*    	String wheres = conditions;  
    	wheres += " AND A.ORG_CODE = B.ORG_CODE(+)";
    	wheres += "   AND A.PART_CODE = B.PART_CODE(+)";
    	wheres += "   AND A.ORG_CODE = E.ORG_CODE(+)";
    	wheres += "   AND A.PART_CODE = E.PART_CODE(+)";
    	wheres += " ORDER BY A.ORG_CODE, A.PART_CODE DESC";
        page.setFilter(wheres);
        
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.ORG_ID,\n" );
    	sql.append("       A.ORG_CODE,\n" );
    	sql.append("       A.ORG_NAME,\n" );
    	sql.append("       A.PART_ID,\n" );
    	sql.append("       A.PART_CODE,\n" );
    	sql.append("       A.PART_NAME,\n" );
    	sql.append("	   NVL(A.AMOUNT, 0) as AMOUNT,\n" ); // --库存数量
    	sql.append("       A.AVAILABLE_AMOUNT as AVAILABLE_AMOUNT,\n" ); // --库存可用数量
    	sql.append("       NVL(B.LOWER_LIMIT, 0) as LOWER_LIMIT,\n" ); // --库存下限
    	sql.append("       NVL(E.DELIVERY_COUNT, 0) as DELIVERY_COUNT,\n" ); // --发运数量
    	sql.append("       NVL(A.AMOUNT, 0) - NVL(B.LOWER_LIMIT, 0) + NVL(E.DELIVERY_COUNT, 0) as DIFF_AMOUNT,\n" ); //差异数量
    	sql.append("       ((NVL(A.AMOUNT, 0) - NVL(B.LOWER_LIMIT, 0) + NVL(E.DELIVERY_COUNT, 0)) /\n" ); 
    	sql.append("       NVL(B.LOWER_LIMIT, 1))*100 || '%' as DIFF_RATE"); //差异率 NVL(B.LOWER_LIMIT, 1)库存下限若没值默认为1
    	sql.append("  FROM\n" );
    	
    	sql.append("       (SELECT ORG_ID,\n" );
    	sql.append("               ORG_CODE,\n" );
    	sql.append("               ORG_NAME,\n" );
    	sql.append("               PART_ID,\n" );
    	sql.append("               PART_CODE,\n" );
    	sql.append("               PART_NAME,\n" );
    	sql.append("               SUM(AMOUNT) as AMOUNT,\n" ); // --库存数量
    	sql.append("               SUM(AVAILABLE_AMOUNT) as AVAILABLE_AMOUNT\n" ); // --库存可用数量
    	sql.append("          FROM PT_BU_DEALER_STOCK\n" );
    	sql.append("         WHERE STATUS = 100201\n" );
    	sql.append("         GROUP BY ORG_ID, ORG_CODE, ORG_NAME, PART_ID, PART_CODE, PART_NAME) A,\n" );
    	
    	sql.append("       PT_BA_CHANNEL_SAFESTOCK B,\n" );
    	
    	sql.append("       (SELECT C.ORG_CODE,\n" );
    	sql.append("               C.ORG_NAME,\n" );
    	sql.append("               D.PART_ID,\n" );
    	sql.append("               D.PART_CODE,\n" );
    	sql.append("               D.PART_NAME,\n" );
    	sql.append("               SUM(NVL(D.DELIVERY_COUNT, 0)) as DELIVERY_COUNT\n" ); // --发运数量
    	sql.append("          FROM PT_BU_SALE_ORDER C, PT_BU_SALE_ORDER_DTL D\n" );
    	sql.append("         WHERE C.STATUS = 100201\n" );
    	sql.append("           AND C.SHIP_STATUS = 204806\n" ); // --已发运
    	sql.append("           AND C.ORDER_ID = D.ORDER_ID\n" );
    	sql.append("         GROUP BY C.ORG_CODE,\n" );
    	sql.append("                  C.ORG_NAME,\n" );
    	sql.append("                  D.PART_ID,\n" );
    	sql.append("                  D.PART_CODE,\n" );
    	sql.append("                  D.PART_NAME) E\n" );*/
		
		//定义返回结果集
    	BaseResultSet bs = null;
    	
		String sql = 
						"SELECT A.ORG_ID,\n" +
						"       A.ORG_CODE,\n" + 
						"       A.ORG_NAME,\n" + 
						"       A.PART_ID,\n" + 
						"       A.PART_CODE,\n" + 
						"       A.PART_NAME,\n" + 
						"       NVL(A.AMOUNT, 0) AS AMOUNT,\n" + // 库存数量
						"       A.AVAILABLE_AMOUNT AS AVAILABLE_AMOUNT,\n" + 
						"       NVL(A.LOWER_LIMIT, 0) AS LOWER_LIMIT,\n" + 	// 下限数量
						"       NVL(A.DELIVERY_COUNT, 0) AS DELIVERY_COUNT,\n" + // 在途数量
						"       NVL(A.AMOUNT, 0) + NVL(A.DELIVERY_COUNT, 0) - NVL(A.LOWER_LIMIT, 0) AS DIFF_AMOUNT,\n" + // 差异数量 = 库存 + 在途 - 下限数量
						"		CASE\n" +
						"  			WHEN NVL(LOWER_LIMIT, 0) = 0 THEN '100%'\n" + 
						"  			ELSE CONCAT((NVL(A.AMOUNT, 0) + NVL(A.LOWER_LIMIT, 0) - NVL(A.LOWER_LIMIT, 0)) / NVL(LOWER_LIMIT, 1), '%')\n" + 
						"		END AS DIFF_RATE"+  // 达标率=差异数量/下限数量
						"  FROM (SELECT ORG_ID,\n" + 
						"               ORG_CODE,\n" + 
						"               ORG_NAME,\n" + 
						"               PART_ID,\n" + 
						"               PART_CODE,\n" + 
						"               PART_NAME,\n" + 
						"               AMOUNT,\n" + 
						"               AVAILABLE_AMOUNT,\n" + 
						"               (\n" + 
						"                 SELECT B.LOWER_LIMIT FROM PT_BA_CHANNEL_SAFESTOCK B WHERE S.ORG_ID = B.ORG_ID\n" + 
						"                        AND S.PART_ID = B.PART_ID\n" + 
						"               ) LOWER_LIMIT,\n" + 
						"               (\n" + 
						"                 SELECT  SUM(D.DELIVERY_COUNT )\n" + 
						"                    FROM PT_BU_SALE_ORDER C, PT_BU_SALE_ORDER_DTL D\n" + 
						"                   WHERE C.STATUS = 100201\n" + 
						"                     AND C.SHIP_STATUS >= 204806\n" + 
						"                     AND C.ORDER_ID = D.ORDER_ID\n" + 
						"                     AND C.ORG_ID = S.ORG_ID\n" + 
						"                     AND D.PART_ID = S.PART_ID\n" + 
						"               ) DELIVERY_COUNT\n" + 
						"          FROM PT_BU_DEALER_STOCK S\n" + 
						"         WHERE STATUS = 100201\n" + 
						"				AND " + conditions +
						// " 			ORDER BY S.PART_ID DESC " + 
						"       ) A\n"; 

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql, page);
    	return bs;
    }
	
	/**
     * 配送中心库存预警明细导出:报表的导出按钮
     * @throws Exception
     * @Author suoxiuli 2014-10-29
     */
    public QuerySet reportExportExcel(PageManager page, String conditions) throws Exception {

    	String wheres = " WHERE "+ conditions; 
    	wheres += " AND A.ORG_CODE = B.ORG_CODE(+)";
    	wheres += "   AND A.PART_CODE = B.PART_CODE(+)";
    	wheres += "   AND A.ORG_CODE = E.ORG_CODE(+)";
    	wheres += "   AND A.PART_CODE = E.PART_CODE(+)";
    	wheres += " ORDER BY A.ORG_CODE, A.PART_CODE DESC";
        
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,\n" );
    	sql.append("       A.ORG_ID,\n" );
    	sql.append("       A.ORG_CODE,\n" );
    	sql.append("       A.ORG_NAME,\n" );
    	sql.append("       A.PART_ID,\n" );
    	sql.append("       A.PART_CODE,\n" );
    	sql.append("       A.PART_NAME||'%',\n" );
    	sql.append("	   NVL(A.AMOUNT, 0) as AMOUNT,\n" ); // --库存数量
    	sql.append("       A.AVAILABLE_AMOUNT as AVAILABLE_AMOUNT,\n" ); // --库存可用数量
    	sql.append("       NVL(B.LOWER_LIMIT, 0) as LOWER_LIMIT,\n" ); // --库存下限
    	sql.append("       NVL(E.DELIVERY_COUNT, 0) as DELIVERY_COUNT,\n" ); // --发运数量
    	sql.append("       NVL(A.AMOUNT, 0) - NVL(B.LOWER_LIMIT, 0) + NVL(E.DELIVERY_COUNT, 0) as DIFF_AMOUNT,\n" ); //差异数量
    	sql.append("       ((NVL(A.AMOUNT, 0) - NVL(B.LOWER_LIMIT, 0) + NVL(E.DELIVERY_COUNT, 0)) /\n" ); 
    	sql.append("       NVL(B.LOWER_LIMIT, 1))*100 || '%' as DIFF_RATE"); //差异率 NVL(B.LOWER_LIMIT, 0)库存下限若没值默认为1，因为它是除数
    	sql.append("  FROM\n" );
    	
    	sql.append("       (SELECT ORG_ID,\n" );
    	sql.append("               ORG_CODE,\n" );
    	sql.append("               ORG_NAME,\n" );
    	sql.append("               PART_ID,\n" );
    	sql.append("               PART_CODE,\n" );
    	sql.append("               PART_NAME,\n" );
    	sql.append("               SUM(AMOUNT) as AMOUNT,\n" ); // --库存数量
    	sql.append("               SUM(AVAILABLE_AMOUNT) as AVAILABLE_AMOUNT\n" ); // --库存可用数量
    	sql.append("          FROM PT_BU_DEALER_STOCK\n" );
    	sql.append("         WHERE STATUS = 100201\n" );
    	sql.append("         GROUP BY ORG_ID, ORG_CODE, ORG_NAME, PART_ID, PART_CODE, PART_NAME) A,\n" );
    	
    	sql.append("       PT_BA_CHANNEL_SAFESTOCK B,\n" );
    	
    	sql.append("       (SELECT C.ORG_CODE,\n" );
    	sql.append("               C.ORG_NAME,\n" );
    	sql.append("               D.PART_ID,\n" );
    	sql.append("               D.PART_CODE,\n" );
    	sql.append("               D.PART_NAME,\n" );
    	sql.append("               SUM(NVL(D.DELIVERY_COUNT, 0)) as DELIVERY_COUNT\n" ); // --发运数量
    	sql.append("          FROM PT_BU_SALE_ORDER C, PT_BU_SALE_ORDER_DTL D\n" );
    	sql.append("         WHERE C.STATUS = 100201\n" );
    	sql.append("           AND C.SHIP_STATUS = 204806\n" ); // --已发运
    	sql.append("           AND C.ORDER_ID = D.ORDER_ID\n" );
    	sql.append("         GROUP BY C.ORG_CODE,\n" );
    	sql.append("                  C.ORG_NAME,\n" );
    	sql.append("                  D.PART_ID,\n" );
    	sql.append("                  D.PART_CODE,\n" );
    	sql.append("                  D.PART_NAME) E\n" );

        //执行方法，不需要传递conn参数
    	return factory.select(null, sql.toString()+wheres);
    }
    
    /**
     * 导出错误数据按钮：把临时表的错误数据导出到EXCEL
     * @throws Exception
     * Author suoxiuli 2014-11-5
     */
    public QuerySet expSafeStockTmpErrorData(String pConditions,User user) throws Exception {

    	String wheres = " WHERE ROW_NUM IN ("+ pConditions + ") \n";
    	wheres += " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
    	wheres += " ORDER BY ROW_NUM \n";
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ROW_NUM, ORG_CODE, PART_CODE, LOWER_LIMIT, UPPER_LIMIT\n" );
    	sql.append("  FROM PT_BA_CHANNEL_SAFESTOCK_TMP");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }
}

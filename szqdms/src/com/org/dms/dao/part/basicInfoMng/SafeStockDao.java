package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.vo.part.PtBaStockSafestocksVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class SafeStockDao extends BaseDAO
{
    //定义instance
    public  static final SafeStockDao getInstance(ActionContext atx)
    {
    	SafeStockDao dao = new SafeStockDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

	/**
	 * 车厂安全库存新增
	 * 
     * 1、查询军品库和民品库下的所有配件信息
     * 2、查询库存上下限的所有信息
     * 3、2中不存在1中的所有信息放到pt_ba_stock_safestocks表中(即增量新增)
     * WAREHOUSE_TYPE 仓库类型：100101-民品库,100102-军品库
     * @throws Exception
     * @Author suoxiuli 2014-07-25
     */
	public boolean insertSafeStock(PtBaStockSafestocksVO vo) throws Exception
    {
		return factory.insert(vo);
    }
	
    /**
	 * 车厂安全库存修改
	 * @throws Exception
     * @Author suoxiuli 2014-07-25
	 */
	public boolean updateSafeStock(PtBaStockSafestocksVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 车厂安全库存查询
	 * @throws Exception
     * @Author suoxiuli 2014-07-25
	 */
    public BaseResultSet search(PageManager page, String conditions) throws Exception
    {
    	//安全库存表里面添加数据
    	//boolean b = insertSafeStock();
    	
    	String wheres = conditions;
    	wheres += " AND A.STATUS = 100201 ";
    	wheres += " AND A.WAREHOUSE_ID = B.STOCK_ID(+) ";
    	wheres += " AND A.PART_ID = B.PART_ID(+) ";
		wheres += " ORDER BY A.WAREHOUSE_CODE, A.PART_CODE DESC ";
		page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DISTINCT A.WAREHOUSE_ID as STOCK_ID,\n" );
    	sql.append("                A.WAREHOUSE_CODE as STOCK_CODE,\n" );
    	sql.append("                A.WAREHOUSE_NAME as STOCK_NAME,\n" );
    	sql.append("                A.PART_ID as PART_ID,\n" );
    	sql.append("                A.PART_CODE as PART_CODE,\n" );
    	sql.append("                A.PART_NAME as PART_NAME,\n" );
    	sql.append("                B.SAFTY_ID as SAFTY_ID,\n" );
    	sql.append("                B.UPPER_LIMIT as UPPER_LIMIT,\n" );
    	sql.append("                B.LOWER_LIMIT as LOWER_LIMIT,\n" );
    	sql.append("                B.STATUS as STATUS　\n" );
    	sql.append("  FROM PT_BU_STOCK A, PT_BA_STOCK_SAFESTOCKS B\n" );

    	bs = factory.select(sql.toString(), page);
    	return bs;
    }

    /**
     * 更新车厂安全库存的有效状态
     * @throws Exception
     * @Author suoxiuli 2014-07-25
     */
    public boolean updateSafeStockLowerUpper(String safeId, String updateUser) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update pt_ba_stock_safestocks \n");
    	sql.append(" set lower_limit = '', \n");
    	sql.append(" upper_limit = '', \n");
    	sql.append(" update_user = '" + updateUser + "', \n");
    	sql.append(" update_time = sysdate \n");
    	sql.append(" where safty_id = '" + safeId + "' \n");
    	return factory.update(sql.toString(), null);
    }
    
    /**
     * 查询安全库存临时表信息（校验临时表数据:1、空验证）
     * 点击"确定"按钮需要调用此方法
     * @throws Exception
     * @Author suoxiuli 2014-08-30
     */
    public QuerySet searchSafeStockTmpInfo(User user, String errorInfoRowNum)throws Exception{
		
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT \n" );
		sql.append(" TMP_ID,\n" );
		sql.append(" ROW_NUM,\n" );
		sql.append(" STOCK_CODE,\n" );
		sql.append(" PART_CODE,\n" );
		sql.append(" LOWER_LIMIT,\n" );
		sql.append(" UPPER_LIMIT,\n" );
		sql.append(" USER_ACCOUNT\n" );
		sql.append(" FROM PT_BA_STOCK_SAFESTOCKS_TMP\n" );
		sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		if (!errorInfoRowNum.equals("")) {
			sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
		}

		return factory.select(null, sql.toString());
	}

    /**
     * 判断仓库CODE 和 配件CODE是否存在（校验临时表数据:2、是否存在验证）
     * @throws Exception
     * @Author suoxiuli 2014-07-11
     */
	public QuerySet checkStockPartCode(String warehouseCode, String partCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM PT_BU_STOCK \n");
    	sql.append(" WHERE WAREHOUSE_CODE = '" + warehouseCode +"'");
    	sql.append(" AND PART_CODE = '" + partCode +"'");
    	sql.append(" AND STATUS = 100201 ");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
    /**
     * 查询安全库存临时表信息（校验临时表数据:3、重复数据校验）
     * @throws Exception
     * @Author suoxiuli 2014-10-24
     */
    public QuerySet searchSafeStockTmpRepeatData(User user, String stockCode, String partCode)throws Exception{
		
    	StringBuffer sql= new StringBuffer();
    	if (!stockCode.equals("") && !partCode.equals("")) {
	    	sql.append("SELECT A.ROW_NUM, A.STOCK_CODE, A.PART_CODE\n" );
	    	sql.append("  FROM PT_BA_STOCK_SAFESTOCKS_TMP A,\n" );
	    	sql.append("       (" );
    	}
    	sql.append("SELECT STOCK_CODE, PART_CODE, COUNT(STOCK_CODE)\n" );
    	sql.append("          FROM PT_BA_STOCK_SAFESTOCKS_TMP\n" );
    	sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("         GROUP BY STOCK_CODE, PART_CODE\n" );
    	sql.append("        HAVING COUNT(STOCK_CODE) > 1" );
    	if (!stockCode.equals("") && !partCode.equals("")) {
	    	sql.append(") B\n" );
	    	sql.append(" WHERE A.STOCK_CODE = B.STOCK_CODE\n" );
	    	sql.append("   AND A.PART_CODE = B.PART_CODE\n" );
	    	sql.append("   AND A.STOCK_CODE = '"+stockCode+"'\n" );
	    	sql.append("   AND A.PART_CODE = '"+partCode+"'\n" );
	    	sql.append("   AND USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	    	sql.append("   ORDER BY A.ROW_NUM\n" );
    	}
		return factory.select(null, sql.toString());
	}
    
    /**
     * 查询安全库存临时表信息（导入成功页面需要显示的信息）
     * @throws Exception
     * @Author suoxiuli 2014-08-30
     */
    public BaseResultSet searchSafeStockTmpInfo(PageManager page, String conditions, User user)throws Exception{
		
    	String wheres = conditions;
    	wheres += " AND USER_ACCOUNT = '"+user.getAccount()+"'";
		wheres += " order by TMP_ID desc ";
		page.setFilter(wheres);
    	BaseResultSet bs = null;
    	
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT \n" );
		sql.append(" TMP_ID,\n" );
		sql.append(" ROW_NUM,\n" );
		sql.append(" STOCK_CODE,\n" );
		sql.append(" PART_CODE,\n" );
		sql.append(" LOWER_LIMIT,\n" );
		sql.append(" UPPER_LIMIT,\n" );
		sql.append(" USER_ACCOUNT\n" );
		sql.append(" FROM PT_BA_STOCK_SAFESTOCKS_TMP\n" );
		
		bs = factory.select(sql.toString(), page);
		return bs;
	}
    
  //----------------------------下面是点击确定按钮，需要调用的方法----------------------------
    /**
     * 判断表PT_BA_STOCK_SAFESTOCKS仓库CODE 和 配件CODE是否存在
     * @throws Exception
     * @Author suoxiuli 2014-07-11
     */
	public QuerySet checkSafeStocksCode(String stockCode, String partCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT SAFTY_ID \n");
    	sql.append(" FROM PT_BA_STOCK_SAFESTOCKS \n");
    	sql.append(" WHERE STOCK_CODE = '" + stockCode +"'\n");
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
	public boolean insertImportSafeStockFromTmp(User user, String stockCode, String partCode) throws Exception{

		StringBuffer sql= new StringBuffer();
		sql.append("insert into PT_BA_STOCK_SAFESTOCKS\n" );
		sql.append("   (SAFTY_ID,\n" );
		sql.append("    STOCK_ID,\n" );
		sql.append("    STOCK_CODE,\n" );
		sql.append("    STOCK_NAME,\n" );
		sql.append("    PART_ID,\n" );
		sql.append("    PART_CODE,\n" );
		sql.append("    PART_NAME,\n" );
		sql.append("    LOWER_LIMIT,\n" );
		sql.append("    UPPER_LIMIT,\n" );
		sql.append("    CREATE_USER,\n" );
		sql.append("    UPDATE_TIME,\n" );
		sql.append("    STATUS)\n" );
		sql.append("   select f_getid(),\n" );
		sql.append("          A.WAREHOUSE_ID,\n" );
		sql.append("          A.WAREHOUSE_CODE,\n" );
		sql.append("          A.WAREHOUSE_NAME,\n" );
		sql.append("          A.PART_ID,\n" );
		sql.append("          A.PART_CODE,\n" );
		sql.append("          A.PART_NAME,\n" );
		sql.append("          B.LOWER_LIMIT,\n" );
		sql.append("          B.UPPER_LIMIT,\n" );
		sql.append("          '"+ user.getAccount() +"',\n" );
		sql.append("          sysdate,\n" );
		sql.append("          100201\n" );
		sql.append("     from PT_BU_STOCK A, PT_BA_STOCK_SAFESTOCKS_TMP B\n" );
		sql.append("    WHERE A.WAREHOUSE_CODE = B.STOCK_CODE\n" );
		sql.append("      AND A.PART_CODE = B.PART_CODE\n" );
		sql.append("      AND B.STOCK_CODE ='"+ stockCode +"'\n" );
		sql.append("      AND B.PART_CODE ='"+ partCode +"'");

    	return factory.update(sql.toString(), null);
	}
	
	/**
     * 导入更新：（临时表的数据有两种：安全库存不存在--则新增，否则--则更新）
     * @throws Exception
     * @Author suoxiuli 2014-10-27
     */
	public boolean updateImportSafeStockFromTmp(User user, String saftyId, String lowerLimit, String upperLimit) throws Exception{

		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BA_STOCK_SAFESTOCKS\n" );
    	sql.append(" SET LOWER_LIMIT = "+ lowerLimit +", \n");
    	sql.append(" UPPER_LIMIT = "+ upperLimit +", \n");
    	sql.append(" UPDATE_USER = '" + user.getAccount() + "', \n");
    	sql.append(" UPDATE_TIME = SYSDATE \n");
    	sql.append(" WHERE SAFTY_ID = '" + saftyId + "' \n");

    	return factory.update(sql.toString(), null);
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
    	sql.append("SELECT ROW_NUM, STOCK_CODE, PART_CODE, LOWER_LIMIT, UPPER_LIMIT\n" );
    	sql.append("  FROM PT_BA_STOCK_SAFESTOCKS_TMP");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }
}

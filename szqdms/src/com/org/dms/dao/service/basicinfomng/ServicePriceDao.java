package com.org.dms.dao.service.basicinfomng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaPartSupplierRlVO;
import com.org.dms.vo.part.PtBaPriceLogVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class ServicePriceDao extends BaseDAO
{
	//定义instance
    public  static final ServicePriceDao getInstance(ActionContext atx)
    {
    	ServicePriceDao dao = new ServicePriceDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    //获取服务索赔价格零时表数据
    public QuerySet searchSeBaClPriceTmp(User user, String errorInfoRowNum)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("ROW_NUM,\n" );
		sql.append("TMP_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("SE_CLPRICE,\n" );
		sql.append("USER_ACCOUNT\n" );
		sql.append("FROM  SE_BA_CL_PRICE_TMP");

		sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		if (!errorInfoRowNum.equals("")) {
			sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
		}
		return factory.select(null, sql.toString());
	}
  //获取服务追偿价价格零时表数据
    public QuerySet searchSeBaRePriceTmp(User user, String errorInfoRowNum)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("ROW_NUM,\n" );
		sql.append("TMP_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("SE_REPRICE,\n" );
		sql.append("USER_ACCOUNT\n" );
		sql.append("FROM  SE_BA_RE_PRICE_TMP");

		sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		if (!errorInfoRowNum.equals("")) {
			sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
		}
		return factory.select(null, sql.toString());
	}
    
    //服务索赔价格零时表重复数据校验
    public QuerySet searchSeBaClPriceTmpRepeatData(User user,String partCode)throws Exception{
		
    	StringBuffer sql= new StringBuffer();
    	if (!partCode.equals("")) {
	    	sql.append("SELECT A.ROW_NUM, A.PART_CODE\n" );
	    	sql.append("  FROM SE_BA_CL_PRICE_TMP A,\n" );
	    	sql.append("       (" );
    	}
    	sql.append("SELECT PART_CODE, COUNT(PART_CODE)\n" );
    	sql.append("          FROM SE_BA_CL_PRICE_TMP\n" );
    	sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("         GROUP BY PART_CODE\n" );
    	sql.append("        HAVING COUNT(PART_CODE) > 1" );
    	if (!partCode.equals("")) {
	    	sql.append(") B\n" );
	    	sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
	    	sql.append("   AND A.PART_CODE = '"+partCode+"'\n" );
	    	sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	    	sql.append("   ORDER BY A.ROW_NUM\n" );
    	}
		return factory.select(null, sql.toString());
	}
  //服务追偿价格零时表重复数据校验
    public QuerySet searchSeBaRePriceTmpRepeatData(User user,String partCode)throws Exception{
		
    	StringBuffer sql= new StringBuffer();
    	if (!partCode.equals("")) {
	    	sql.append("SELECT A.ROW_NUM, A.PART_CODE\n" );
	    	sql.append("  FROM SE_BA_RE_PRICE_TMP A,\n" );
	    	sql.append("       (" );
    	}
    	sql.append("SELECT PART_CODE, COUNT(PART_CODE)\n" );
    	sql.append("          FROM SE_BA_RE_PRICE_TMP\n" );
    	sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("         GROUP BY PART_CODE\n" );
    	sql.append("        HAVING COUNT(PART_CODE) > 1" );
    	if (!partCode.equals("")) {
	    	sql.append(") B\n" );
	    	sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
	    	sql.append("   AND A.PART_CODE = '"+partCode+"'\n" );
	    	sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	    	sql.append("   ORDER BY A.ROW_NUM\n" );
    	}
		return factory.select(null, sql.toString());
	}
    
  //将服务索赔价格零时表正确的数据显示到页面
	public BaseResultSet searchSeBaClPriceTmpImport(PageManager page, String conditions, User user)throws Exception{
		String wheres = conditions;
    	wheres += " AND USER_ACCOUNT = '"+user.getAccount()+"'";
    	wheres += " order by PART_CODE desc ";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
		
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("ROW_NUM,\n" );
		sql.append("TMP_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("SE_CLPRICE,\n" );
		sql.append("USER_ACCOUNT\n" );
		sql.append("FROM  SE_BA_CL_PRICE_TMP");

		bs=factory.select(sql.toString(), page);
		return bs;
	}
	//将服务追偿价格零时表正确的数据显示到页面
	public BaseResultSet searchSeBaRePriceTmpImport(PageManager page, String conditions, User user)throws Exception{
		String wheres = conditions;
    	wheres += " AND USER_ACCOUNT = '"+user.getAccount()+"'";
    	wheres += " order by PART_CODE desc ";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
		
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("ROW_NUM,\n" );
		sql.append("TMP_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("SE_REPRICE,\n" );
		sql.append("USER_ACCOUNT\n" );
		sql.append("FROM  SE_BA_RE_PRICE_TMP");

		bs=factory.select(sql.toString(), page);
		return bs;
	}
	//获得配件ID
	public QuerySet getPartInfo(String partCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT PART_ID,PART_NAME\n");
    	sql.append(" FROM  PT_BA_INFO\n");
    	sql.append(" WHERE PART_CODE = '" + partCode +"'\n");
    	sql.append(" AND SE_STATUS = '"+DicConstant.YXBS_01+"'\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	//盘点pt_ba_info表服务价格信息是否存在
	public QuerySet checkSePrice(String tableColumn, String part_id, String se_price) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT "+tableColumn+" \n");
    	sql.append(" FROM PT_BA_INFO \n");
    	sql.append(" WHERE PART_ID = '" + part_id +"'\n");
    	
    	if(se_price!=null && se_price != ""){
    		sql.append(" AND "+tableColumn+" = '" + se_price +"'\n");
    	}
    	sql.append(" AND SE_STATUS = '"+DicConstant.YXBS_01+"'\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    //更新服务价格信息
	public boolean updateSePrice(PtBaInfoVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	//插入服务价格轨迹
	public boolean insertSePriceLog(PtBaPriceLogVO vo) throws Exception {
		return factory.insert(vo);
	}
    
	//服务索赔价格错误信息导出
	public QuerySet expSeClPriceTmpErrorData(String pConditions,User user) throws Exception {
		String wheres = " WHERE ROW_NUM IN ("+ pConditions + ") \n";
    	wheres += " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
    	wheres += " ORDER BY ROW_NUM \n";
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ROW_NUM,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("SE_CLPRICE\n" );
    	sql.append("FROM SE_BA_CL_PRICE_TMP");

    	return factory.select(null, sql.toString()+wheres);
    }
	//服务追偿价格错误信息导出
	public QuerySet expSeRePriceTmpErrorData(String pConditions,User user) throws Exception {
		String wheres = " WHERE ROW_NUM IN ("+ pConditions + ") \n";
    	wheres += " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
    	wheres += " ORDER BY ROW_NUM \n";
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ROW_NUM,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("SE_REPRICE\n" );
    	sql.append("FROM SE_BA_RE_PRICE_TMP");

    	return factory.select(null, sql.toString()+wheres);
    }
	//服务索赔、追偿价格导出
	public QuerySet downloadServicePrice(String conditions) throws Exception {
    	  	
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("SE_CLPRICE,\n" );
    	sql.append("SE_REPRICE\n" );
    	sql.append("FROM PT_BA_INFO");
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append(" AND SE_STATUS = "+DicConstant.YXBS_01+"");

    	//执行方法，不需要传递conn参数
    	return factory.select(null, sql.toString());
    }
	
	//判断服务配件代码是否存在
	public QuerySet checkPart_code(String part_code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT PART_ID,PART_NAME \n");
    	sql.append(" FROM PT_BA_INFO \n");
    	sql.append(" WHERE PART_CODE = '" + part_code +"'");
    	sql.append(" AND SE_STATUS = '"+DicConstant.YXBS_01+"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    
    
    
    
    
    
    
    
    
    
    
}

package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.vo.part.PtBaTransportAddressTmpVO;
import com.org.dms.vo.part.PtBaTransportAddressVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class TransAddressDao extends BaseDAO
{
    //定义instance
    public  static final TransAddressDao getInstance(ActionContext atx)
    {
    	TransAddressDao dao = new TransAddressDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断渠道商CODE发运地址是否存在
     * @throws Exception
     * @Author suoxiuli 2014-10-25
     */
	public QuerySet checkOrgCodeAddress(String orgCode, String provinceCode,
			String cityCode, String countryCode,String address) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" from PT_BA_TRANSPORT_ADDRESS \n");
    	sql.append(" WHERE ORG_CODE = '" + orgCode +"'");
    	sql.append(" AND PROVINCE_CODE = '" + provinceCode +"'");
    	sql.append(" AND CITY_CODE = '" + cityCode +"'");
    	if (!countryCode.equals("")) {
    		sql.append(" AND COUNTRY_CODE = '" + countryCode +"'");
    	}
    	if (!address.equals("")) {
    		sql.append(" AND ADDRESS = '" + address +"'");
    	}
    	//sql.append(" AND STATUS = '100201'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
    /**
	 * 发运地址新增
	 * @throws Exception
     * @Author suoxiuli 2014-07-14
	 */
    public boolean insertTransAddr(PtBaTransportAddressVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
    /**
	 * 发运地址修改
	 * @throws Exception
     * @Author suoxiuli 2014-07-14
	 */
	public boolean updateTransAddr(PtBaTransportAddressVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 发运地址查询
	 * @throws Exception
     * @Author suoxiuli 2014-07-14
	 */
    public BaseResultSet search(PageManager page, String conditions) throws Exception
    {
    	String wheres = conditions;
    	//wheres += " AND O.STATUS = 100201 ";
    	//wheres += " AND A.ORG_ID = O.ORG_ID ";
		wheres += " ORDER BY A.CREATE_TIME, A.ADDRESS_ID DESC";
        page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.ADDRESS_ID,\n" );
    	sql.append("       A.ORG_NAME,\n" );
    	sql.append("       A.ORG_CODE,\n" );
    	sql.append("       A.ORG_ID,\n" );
    	sql.append("       A.PROVINCE_CODE,\n" );
    	sql.append("       A.PROVINCE_NAME,\n" );
    	sql.append("       A.CITY_CODE,\n" );
    	sql.append("       A.CITY_NAME,\n" );
    	sql.append("       A.COUNTRY_CODE,\n" );
    	sql.append("       A.COUNTRY_NAME,\n" );
    	sql.append("       A.ADDRESS,\n" );
    	sql.append("       A.LINK_MAN,\n" );
    	sql.append("       A.PHONE,\n" );
    	sql.append("       A.ZIP_CODE,\n" );
    	sql.append("	   A.MOBILE,\n" );
    	sql.append("       A.ADDR_TYPE,\n" );
    	sql.append("       A.FAX,\n" );
    	sql.append("       A.E_MAIL,");
    	sql.append("       A.CREATE_USER,\n" );
    	sql.append("       A.CREATE_TIME,\n" );
    	sql.append("       A.STATUS\n" );
    	//sql.append("       O.ORG_TYPE\n" );
    	sql.append("  FROM PT_BA_TRANSPORT_ADDRESS A" );
    	//sql.append("       TM_ORG O\n" );

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("ADDR_TYPE","JHDDLX");
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldDic("ORG_TYPE","ZZLB");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }

    /**
     * 更新发运地址的有效状态
     * @throws Exception
     * @Author suoxiuli 2014-07-14
     */
    public boolean updateTransAddrStatus(String addressId, String updateUser, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_TRANSPORT_ADDRESS \n");
    	sql.append(" SET STATUS = '" + status + "', \n");
    	sql.append(" UPDATE_USER = '" + updateUser + "', \n");
    	sql.append(" UPDATE_TIME = sysdate \n");
    	sql.append(" WHERE ADDRESS_ID = '" + addressId + "' \n");
    	
    	return factory.update(sql.toString(), null);
    }
    
    //-------------------------以下是导入、导出信息-------------------------------------------  
    /**
     * 渠道安全库存临时表查询（校验临时表数据:1、空校验）
     * 点击"确定"按钮需要调用此方法
     * @throws Exception
     * @Author suoxiuli 2014-10-25
     */
    public QuerySet searchTransAddressTmp(User user, String errorInfoRowNum)throws Exception{
		
    	StringBuffer sql= new StringBuffer();
    	sql.append("select TMP_ID,\n" );
    	sql.append("       ROW_NUM,\n" );
    	sql.append("       ORG_CODE,\n" );
    	sql.append("       LINK_MAN,\n" );
    	sql.append("       ORG_ID,\n" );
    	sql.append("       PHONE,\n" );
    	sql.append("       FAX,\n" );
    	sql.append("       E_MAIL,\n" );
    	sql.append("       ZIP_CODE,\n" );
    	sql.append("       ADDR_TYPE,\n" );
    	sql.append("       PROVINCE_CODE,\n" );
    	sql.append("       CITY_CODE,\n" );
    	sql.append("       COUNTRY_CODE,\n" );
    	sql.append("       ADDRESS,\n" );
    	sql.append("       USER_ACCOUNT\n" );
    	sql.append("  from PT_BA_TRANSPORT_ADDRESS_TMP\n" );
    	sql.append(" where USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	if (!errorInfoRowNum.equals("")) {
			sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
		}
    	sql.append(" ORDER BY ROW_NUM " );
		
		return factory.select(null, sql.toString());
	}
    
    /**
     * 发运地址临时表查询（校验临时表数据:3、重复数据检验）
     * @throws Exception
     * @Author suoxiuli 2014-10-24
     */
    public QuerySet searchTransAddrTmpRepeatData(User user, String orgCode)throws Exception{
		
    	StringBuffer sql= new StringBuffer();
    	if (!orgCode.equals("")) {
	    	sql.append("SELECT A.ROW_NUM, A.ORG_CODE, A.PART_CODE\n" );
	    	sql.append("  FROM PT_BA_CHANNEL_SAFESTOCK_TMP A,\n" );
	    	sql.append("       (");
    	}
    	
	    sql.append("SELECT ORG_CODE, COUNT(ORG_CODE)\n" );
    	sql.append("          FROM PT_BA_TRANSPORT_ADDRESS_TMP\n" );
    	sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("         GROUP BY ORG_CODE\n" );
    	sql.append("        HAVING COUNT(ORG_CODE) > 1");
    	
    	if (!orgCode.equals("")) {
    		sql.append(")B\n" );
    		sql.append(" WHERE A.ORG_CODE = B.ORG_CODE\n" );
        	sql.append("   AND A.ORG_CODE = '"+orgCode+"'\n" );
        	sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        	sql.append("   ORDER BY A.ROW_NUM \n" );
    	}
    	
		return factory.select(null, sql.toString());
	}
    
    /**
     * 查询发运地址临时表信息（导入成功页面需要显示的信息）
     * @throws Exception
     * @Author suoxiuli 2014-10-25
     */
    public BaseResultSet searchTransAddrTmpInfo(PageManager page, String conditions, User user)throws Exception{
		
    	String wheres = conditions;
    	wheres += " AND USER_ACCOUNT = '"+user.getAccount()+"'";
		wheres += " ORDER BY TMP_ID DESC ";
		page.setFilter(wheres);
    	BaseResultSet bs = null;
    	
		StringBuffer sql= new StringBuffer();
		sql.append("select TMP_ID,\n" );
    	sql.append("       ROW_NUM,\n" );
    	sql.append("       ORG_CODE,\n" );
    	sql.append("       LINK_MAN,\n" );
    	sql.append("       ORG_ID,\n" );
    	sql.append("       PHONE,\n" );
    	sql.append("       FAX,\n" );
    	sql.append("       E_MAIL,\n" );
    	sql.append("       ZIP_CODE,\n" );
    	sql.append("       ADDR_TYPE,\n" );
    	sql.append("       PROVINCE_CODE,\n" );
    	sql.append("       CITY_CODE,\n" );
    	sql.append("       COUNTRY_CODE,\n" );
    	sql.append("       ADDRESS,\n" );
    	sql.append("       USER_ACCOUNT\n" );
    	sql.append("  from PT_BA_TRANSPORT_ADDRESS_TMP\n" );
		
		bs = factory.select(sql.toString(), page);
		return bs;
	}
    
  //----------------------------下面是点击确定按钮，需要调用的方法----------------------------
    /**
     * 1、判断主表中服务商CODE发运地址是否存在
     * @throws Exception
     * @Author suoxiuli 2014-10-28
     */
    public QuerySet checkOrgCodeIsExists(String orgCode) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT ADDRESS_ID \n");
    	sql.append(" FROM PT_BA_TRANSPORT_ADDRESS \n");
    	sql.append(" WHERE ORG_CODE = '" + orgCode +"'");
    	sql.append(" AND STATUS = '100201'");
    	
    	return factory.select(null, sql.toString());
    }
    
    /**
     * 2、通过CODE得出NAME
     * @throws Exception
     * @Author suoxiuli 2014-10-28
     */
	public QuerySet getNameByCode(String orgCode, String userAccount) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT B.ORG_ID,\n" );
    	sql.append("       B.CODE,\n" );
    	sql.append("       B.ONAME,\n" );
    	sql.append("       C.DIC_CODE,\n" );
    	sql.append("       C.DIC_VALUE,\n" );
    	sql.append("       D1.DM as PROV_DM,\n" );
    	sql.append("       D1.JC as PROV_JC,\n" );
    	sql.append("       D2.DM as CITY_DM,\n" );
    	sql.append("       D2.JC as CITY_JC,\n" );
    	sql.append("       D3.DM as COUT_DM,\n" );
    	sql.append("       D3.JC as COUT_JC\n" );
    	sql.append("  FROM PT_BA_TRANSPORT_ADDRESS_TMP A,\n" );
    	sql.append("       TM_ORG                      B,\n" );
    	sql.append("       DIC_TREE                    C,\n" );
    	sql.append("       TM_DIVISION                 D1,\n" );
    	sql.append("       TM_DIVISION                 D2,\n" );
    	sql.append("       TM_DIVISION                 D3\n" );
    	sql.append(" WHERE A.ORG_CODE = B.CODE\n" );
    	sql.append("   AND A.ADDR_TYPE = C.DIC_VALUE\n" );
    	sql.append("   AND A.PROVINCE_CODE = D1.DM\n" );
    	sql.append("   AND A.CITY_CODE = D2.DM\n" );
    	sql.append("   AND A.COUNTRY_CODE = D3.DM\n" );
    	sql.append("   AND A.ORG_CODE = '"+ orgCode +"'\n" );
    	sql.append("   AND A.USER_ACCOUNT = '"+userAccount+"'\n" );

    	return factory.select(null, sql.toString());
    }
    
    /**
     * 判断表PT_BA_CHANNEL_SAFESTOCK服务商CODE 和 配件CODE是否存在
     * @throws Exception
     * @Author suoxiuli 2014-10-27
     */
	public QuerySet checkTransAddrRepeatData(String orgCode, String provinceCode,
			String cityCode, String countryCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT ADDRESS_ID \n");
    	sql.append(" from PT_BA_TRANSPORT_ADDRESS \n");
    	sql.append(" WHERE ORG_CODE = '" + orgCode +"'");
    	sql.append(" AND PROVINCE_CODE = '" + provinceCode +"'");
    	sql.append(" AND CITY_CODE = '" + cityCode +"'");

    	if (!countryCode.equals("")) {
    		sql.append(" AND COUNTRY_CODE = '" + countryCode +"'");
    	}
    	sql.append(" AND STATUS = '100201'");
    	
    	return factory.select(null, sql.toString());
    }
	
    /**
     * 发运地址导出
     *
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download() throws Exception {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT \n" );
        sql.append("       ROWNUM,\n" );
		//sql.append("       A.ADDRESS_ID,\n" );
        sql.append("       A.ORG_CODE,\n" );
    	sql.append("       A.ORG_NAME,\n" );
    	//sql.append("       A.PROVINCE_CODE,\n" );
    	sql.append("       A.PROVINCE_NAME,\n" );
    	//sql.append("       A.CITY_CODE,\n" );
    	sql.append("       A.CITY_NAME,\n" );
    	//sql.append("       A.COUNTRY_CODE,\n" );
    	sql.append("       A.COUNTRY_NAME,\n" );
    	sql.append("       A.ADDRESS,\n" );
    	sql.append("       A.LINK_MAN,\n" );
    	sql.append("       A.PHONE,\n" );
    	sql.append("       A.ZIP_CODE\n" );
    	//sql.append("       A.CREATE_USER,\n" );
    	//sql.append("       A.CREATE_TIME,\n" );
    	//sql.append("       A.STATUS,\n" );
    	sql.append("  FROM PT_BA_TRANSPORT_ADDRESS A, \n" );
    	sql.append("       TM_ORG O\n" );
    	sql.append("  WHERE A.ORG_ID=O.ORG_ID\n" );
    	sql.append("  AND O.STATUS = 100201\n" ); 
    	sql.append(" ORDER BY A.ORG_NAME, A.PROVINCE_NAME DESC " );

        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
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
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ORG_ID,\n" );
    	sql.append("       USER_ACCOUNT,\n" );
    	sql.append("       ADDR_TYPE,\n" );
    	sql.append("       MOBILE,\n" );
    	sql.append("       ROW_NUM,\n" );
    	sql.append("       TMP_ID,\n" );
    	sql.append("       ORG_CODE,\n" );
    	sql.append("       PROVINCE_CODE,\n" );
    	sql.append("       CITY_CODE,\n" );
    	sql.append("       COUNTRY_CODE,\n" );
    	sql.append("       ADDRESS,\n" );
    	sql.append("       LINK_MAN,\n" );
    	sql.append("       ZIP_CODE,\n" );
    	sql.append("       PHONE,\n" );
    	sql.append("       E_MAIL,\n" );
    	sql.append("       FAX\n" );
    	sql.append("  FROM PT_BA_TRANSPORT_ADDRESS_TMP");

        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }
}


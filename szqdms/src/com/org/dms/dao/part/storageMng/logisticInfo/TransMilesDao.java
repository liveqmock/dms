package com.org.dms.dao.part.storageMng.logisticInfo;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaTransMilesVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

public class TransMilesDao extends BaseDAO
{
    //定义instance
    public  static final TransMilesDao getInstance(ActionContext atx)
    {
    	TransMilesDao dao = new TransMilesDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断运费里程数是否存在
     * @throws Exception
     * @Author suoxiuli 2014-08-22
     */
	public QuerySet checkTransMiles(String birthlandCode, String provinceCode, String
			cityCode, String countryCode, String address) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select count(1) nums \n");
    	sql.append(" from pt_ba_trans_miles \n");
    	sql.append(" where birthland_code = '" + birthlandCode +"'");
    	sql.append(" and province_code = '" + provinceCode +"'");
    	sql.append(" and city_code = '" + cityCode +"'");
    	sql.append(" and country_code = '" + countryCode +"'");
    	sql.append(" and address = '" + address +"'");
    	sql.append(" and status = '" + DicConstant.YXBS_01 +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
    /**
	 * 运费里程数新增
	 * @throws Exception
     * @Author suoxiuli 2014-08-22
	 */
    public boolean insertTransMiles(PtBaTransMilesVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
    /**
	 * 运费里程数修改
	 * @throws Exception
     * @Author suoxiuli 2014-08-22
	 */
	public boolean updateTransMiles(PtBaTransMilesVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 运费里程数查询
	 * @throws Exception
     * @Author suoxiuli 2014-08-22
	 */
    public BaseResultSet search(PageManager page, String conditions) throws Exception
    {
    	String wheres = conditions;
		wheres += " ORDER BY CREATE_TIME, MILES_ID DESC";
        page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select BIRTHLAND_CODE,\n" );
    	sql.append("       BIRTHLAND_NAME,\n" );
    	sql.append("       PROVINCE_CODE,\n" );
    	sql.append("       PROVINCE_NAME,\n" );
    	sql.append("       CITY_CODE,\n" );
    	sql.append("       CITY_NAME,\n" );
    	sql.append("       COUNTRY_CODE,\n" );
    	sql.append("       COUNTRY_NAME,\n" );
    	sql.append("       ADDRESS,\n" );
    	sql.append("       MILES_ID,\n" );
    	sql.append("       TRANS_MILES,\n" );
    	sql.append("       CREATE_USER,\n" );
    	sql.append("       CREATE_TIME,\n" );
    	sql.append("       UPDATE_USER,\n" );
    	sql.append("       UPDATE_TIME,\n" );
    	sql.append("       STATUS,\n" );
    	sql.append("       UNIT_PRICE\n" );
    	sql.append("  from PT_BA_TRANS_MILES");


    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }

    /**
     * 更新运费里程数的有效状态
     * @throws Exception
     * @Author suoxiuli 2014-08-22
     */
    public boolean updateTransMilesStatus(String milesId, String updateUser, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update pt_ba_trans_miles \n");
    	sql.append(" set status = '" + status + "', \n");
    	sql.append(" update_user = '" + updateUser + "', \n");
    	sql.append(" update_time = sysdate \n");
    	sql.append(" where miles_id = '" + milesId + "' \n");
    	return factory.update(sql.toString(), null);
    }
}


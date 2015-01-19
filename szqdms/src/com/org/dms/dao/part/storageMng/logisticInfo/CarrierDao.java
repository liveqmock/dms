package com.org.dms.dao.part.storageMng.logisticInfo;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaCarrierVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

public class CarrierDao extends BaseDAO
{
    //定义instance
    public  static final CarrierDao getInstance(ActionContext atx)
    {
    	CarrierDao dao = new CarrierDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断承运商代码是否存在
     * @throws Exception
     * @Author suoxiuli 2014-08-21
     */
	public QuerySet checkCarrier(String carrierCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select count(1) nums \n");
    	sql.append(" from pt_ba_carrier \n");
    	sql.append(" where carrier_code = '" + carrierCode +"'");
    	sql.append(" and status = '" + DicConstant.YXBS_01 +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
    /**
	 * 承运商信息新增
	 * @throws Exception
     * @Author suoxiuli 2014-08-21
	 */
    public boolean insertCarrier(PtBaCarrierVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
    /**
	 * 承运商信息修改
	 * @throws Exception
     * @Author suoxiuli 2014-08-21
	 */
	public boolean updateCarrier(PtBaCarrierVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 承运商信息查询
	 * @throws Exception
     * @Author suoxiuli 2014-08-21
	 */
    public BaseResultSet search(PageManager page, String conditions) throws Exception
    {
    	String wheres = conditions;
		wheres += " ORDER BY CREATE_TIME, CARRIER_ID DESC";
        page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT FIXED_LINE,\n" );
        sql.append("       EMAIL,\n" );
        sql.append("       PROVINCE_CODE,\n" );
        sql.append("       PROVINCE_NAME,\n" );
        sql.append("       CITY_CODE,\n" );
        sql.append("       CITY_NAME,\n" );
        sql.append("       COUNTRY_CODE,\n" );
        sql.append("       COUNTRY_NAME,\n" );
        sql.append("       CARRIER_ID,\n" );
        sql.append("       CARRIER_NAME,\n" );
        sql.append("       CARRIER_CODE,\n" );
        sql.append("       LINK_MAN,\n" );
        sql.append("       PHONE,\n" );
        sql.append("       ADDRESS,\n" );
        sql.append("       REMARKS,\n" );
        sql.append("       IF_ARMY,\n" );
        sql.append("       COMPANY_ID,\n" );
        sql.append("       ORG_ID,\n" );
        sql.append("       CREATE_USER,\n" );
        sql.append("       CREATE_TIME,\n" );
        sql.append("       UPDATE_USER,\n" );
        sql.append("       UPDATE_TIME,\n" );
        sql.append("       STATUS,\n" );
        sql.append("       OEM_COMPANY_ID,\n" );
        sql.append("       SECRET_LEVEL\n" );
        sql.append("  FROM PT_BA_CARRIER");

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("IF_ARMY","SF");
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldUserID("CREATE_USER");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }

}


package com.org.dms.dao.part.storageMng.logisticInfo;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaDriverVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

public class DriverDao extends BaseDAO
{
    //定义instance
    public  static final DriverDao getInstance(ActionContext atx)
    {
    	DriverDao dao = new DriverDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断司机身份证号是否存在
     * @throws Exception
     * @Author suoxiuli 2014-08-21
     */
	public QuerySet checkDriverNO(String driverNO) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select count(1) nums \n");
    	sql.append(" from pt_ba_driver \n");
    	sql.append(" where driver_no = '" + driverNO +"'");
    	sql.append(" and status = '" + DicConstant.YXBS_01 +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	/**
	 * 
	 * checkDriverNOAndCarrierId: 根据身份证号码和承运商ID确定是否存在 true:存在，false不存在
	 * @author fuxiao
	 * Date:2014年11月26日
	 *
	 */
	public boolean checkDriverNOAndCarrierId(String driverNO, String carrierId) throws Exception
    {
    	return Integer.parseInt(this.factory.select("SELECT COUNT(1) NUMS\n" +
								    					"  FROM PT_BA_DRIVER\n" + 
								    					" WHERE DRIVER_NO = ?\n" + 
								    					"   AND CARRIER_ID = ?\n" + 
								    					"   AND STATUS = '100201'", new Object[]{driverNO, carrierId})[0][0]) > 0;
    }
	
    /**
	 * 司机信息新增
	 * @throws Exception
     * @Author suoxiuli 2014-08-21
	 */
    public boolean insertDriver(PtBaDriverVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
    /**
	 * 司机信息修改
	 * @throws Exception
     * @Author suoxiuli 2014-08-21
	 */
	public boolean updateDriver(PtBaDriverVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 司机信息查询
	 * @throws Exception
     * @Author suoxiuli 2014-08-21
	 */
    public BaseResultSet search(PageManager page, String conditions) throws Exception
    {
    	String wheres = conditions;
    	//wheres += " and status="+DicConstant.YXBS_01+"  ";
		wheres += " ORDER BY CREATE_TIME, DRIVER_ID DESC";
        page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select CARRIER_CODE,\n" );
    	sql.append("       FIXED_LINE,\n" );
    	sql.append("       SEX,\n" );
    	sql.append("       EMAIL,\n" );
    	sql.append("       CARRIER_ID,\n" );
    	sql.append("       CARRIER_NAME,\n" );
    	sql.append("       DRIVER_ID,\n" );
    	sql.append("       DRIVER_NAME,\n" );
    	sql.append("       DRIVER_NO,\n" );
    	sql.append("       PHONE,\n" );
    	sql.append("       ADDRESS,\n" );
    	sql.append("       COMPANY_ID,\n" );
    	sql.append("       ORG_ID,\n" );
    	sql.append("       CREATE_USER,\n" );
    	sql.append("       CREATE_TIME,\n" );
    	sql.append("       UPDATE_USER,\n" );
    	sql.append("       UPDATE_TIME,\n" );
    	sql.append("       STATUS,\n" );
    	sql.append("       OEM_COMPANY_ID,\n" );
    	sql.append("       SECRET_LEVEL\n" );
    	sql.append("  from PT_BA_DRIVER");

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("SEX","XB");
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldUserID("CREATE_USER");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }

    /**
     * 更新司机信息的有效状态
     * @throws Exception
     * @Author suoxiuli 2014-08-21
     */
    /*public boolean updateDriverStatus(String driverId, String updateUser, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update pt_ba_driver \n");
    	sql.append(" set status = '" + status + "', \n");
    	sql.append(" update_user = '" + updateUser + "', \n");
    	sql.append(" update_time = sysdate \n");
    	sql.append(" where driver_id = '" + driverId + "' \n");
    	return factory.update(sql.toString(), null);
    }*/
}


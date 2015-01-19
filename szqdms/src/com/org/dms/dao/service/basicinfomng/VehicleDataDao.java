package com.org.dms.dao.service.basicinfomng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.MainVehicleProductVO;
import com.org.dms.vo.service.MainVehicleVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 车辆数据查询
 * @author zts
 *
 */
public class VehicleDataDao extends BaseDAO
{
    //定义instance
    public  static final VehicleDataDao getInstance(ActionContext atx)
    {
    	VehicleDataDao dao = new VehicleDataDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    /***
     * 车辆查询
     * @return
     * @throws Exception
     */
    public BaseResultSet vehicleSearch(PageManager page,User user,String conditions)throws Exception{
    	BaseResultSet bs=null;
    	String wheres=conditions;
    		   wheres +=" ORDER BY T.VIN ";
    	page.setFilter(wheres);
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.VEHICLE_ID,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.SVIN,\n" );
    	sql.append("       T.ENGINE_NO,\n" );
    	sql.append("       T.MODELS_CODE,\n" );
    	sql.append("       T.CERTIFICATE,\n" );
    	sql.append("       T.ENGINE_TYPE,\n" );
    	sql.append("       T.USER_TYPE,\n" );
    	sql.append("       T.VEHICLE_USE,\n" );
    	sql.append("       T.DRIVE_FORM,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.GUARANTEE_NO,\n" );
    	sql.append("       T.FACTORY_DATE,\n" );
    	sql.append("       T.MAINTENANCE_DATE,\n" );
    	sql.append("       T.G_COUNT,\n" );
    	sql.append("       T.SALE_STATUS,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.LICENSE_PLATE,\n" );
    	sql.append("       T.USER_NAME,\n" );
    	sql.append("       T.USER_NO,\n" );
    	sql.append("       T.LINK_MAN,\n" );
    	sql.append("       T.PHONE,\n" );
    	sql.append("       T.USER_ADDRESS,\n" );
    	sql.append("       T.CERTIFICATEDATE,\n" );
    	sql.append("       T.BLACKLISTFLAG,\n" );
    	sql.append("       T.PRODUCTLINECODE,\n" );
    	sql.append("       T.INSIDECODE,\n" );
    	sql.append("       T.CONFIGURE,\n" );
    	sql.append("       T.CONTRACTAREANO,\n" );
    	sql.append("       T.REPAIR_DATE,\n" );
    	sql.append("       T.VEHICLE_SUPP\n" );
    	sql.append("  FROM MAIN_VEHICLE T");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDic("USER_TYPE","CLYHLX");
    	bs.setFieldDic("VEHICLE_USE","CLYT");
    	bs.setFieldDic("DRIVE_FORM", "QDXS");
    	bs.setFieldDic("SALE_STATUS", "CLXSZT");
    	bs.setFieldDic("BLACKLISTFLAG","SF");
    	bs.setFieldDic("STATUS", "YXBS");
    	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("CERTIFICATEDATE", "yyyy-MM-dd");
    	return bs;
    }
    
    /**
     * 车辆信息修改
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean vehicleUpdate(MainVehicleVO vo)throws Exception{
    	return factory.update(vo);
    }
    /**
     * 车辆信息新增
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean vehicleInsert(MainVehicleVO vo)throws Exception{
    	return factory.insert(vo);
    }
    
    /**
     * 查询导入数据
     * @return
     * @throws Exception
     */
    public BaseResultSet searchImportVehicle(PageManager page,User user,String conditions)throws Exception {
    	BaseResultSet bs=null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT * FROM MAIN_VEHICLE_TMP T WHERE "+conditions+" AND T.CREATE_USER = '"+user.getAccount()+"'");
    	bs=factory.select(sql.toString(), page);
    	return bs;
    }
    
    /**
     * 校验数据是否重复
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet checkList1(User user)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT COUNT(T.TMP_ID), T.VIN\n" );
    	sql.append("    FROM MAIN_VEHICLE_TMP T\n" );
    	sql.append("   GROUP BY T.VIN\n" );
    	sql.append("  HAVING(COUNT(T.TMP_ID)) > 1");
    	return factory.select(null, sql.toString());
    }
    
    /**
     * 校验数据是否在车辆表中存在
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet checkList2(User user)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ROW_NO, T.VIN\n" );
    	sql.append("  FROM MAIN_VEHICLE_TMP T\n" );
    	sql.append(" WHERE T.CREATE_USER = '"+user.getAccount()+"'\n" );
    	sql.append("   AND EXISTS (SELECT 1 FROM MAIN_VEHICLE V WHERE T.VIN = V.VIN)");
    	return factory.select(null, sql.toString());
    }
    
    /**
     * 插入车辆表
     * @param user
     * @return
     * @throws Exception
     */
    public boolean insertVehicle(User user,String rowNo)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("INSERT INTO MAIN_VEHICLE\n" );
    	sql.append("  (VEHICLE_ID,\n" );
    	sql.append("   VIN,\n" );
    	sql.append("   SVIN,\n" );
    	sql.append("   ENGINE_NO,\n" );
    	sql.append("   MODELS_ID,\n" );
    	sql.append("   MODELS_CODE,\n" );
    	sql.append("   CERTIFICATE,\n" );
    	sql.append("   ENGINE_TYPE,\n" );
    	sql.append("   USER_TYPE,\n" );
    	sql.append("   VEHICLE_USE,\n" );
    	sql.append("   DRIVE_FORM,\n" );
    	sql.append("   BUY_DATE,\n" );
    	sql.append("   GUARANTEE_NO,\n" );
    	sql.append("   FACTORY_DATE,\n" );
    	sql.append("   MAINTENANCE_DATE,\n" );
    	sql.append("   SALE_STATUS,\n" );
    	sql.append("   LICENSE_PLATE,\n" );
    	sql.append("   USER_NAME,\n" );
    	sql.append("   USER_NO,\n" );
    	sql.append("   LINK_MAN,\n" );
    	sql.append("   PHONE,\n" );
    	sql.append("   USER_ADDRESS,\n" );
    	sql.append("   ORG_ID,\n" );
    	sql.append("   CREATE_USER,\n" );
    	sql.append("   CREATE_TIME,\n" );
    	sql.append("   OEM_COMPANY_ID,\n" );
    	sql.append("   CERTIFICATEDATE,\n" );
    	sql.append("   STATUS,\n" );
    	sql.append("   BLACKLISTFLAG,\n" );
    	sql.append("   PRODUCTLINECODE,\n" );
    	sql.append("   INSIDECODE,\n" );
    	sql.append("   CONFIGURE,\n" );
    	sql.append("   CONTRACTAREANO)\n" );
    	sql.append("  SELECT F_GETID(),\n" );
    	sql.append("         T.VIN,\n" );
    	sql.append("         SUBSTR(T.VIN,LENGTH(T.VIN)-8+1,8),\n" );
    	sql.append("         T.ENGINE_NO,\n" );
    	sql.append("         (SELECT M.MODELS_ID FROM MAIN_MODELS M WHERE M.MODELS_CODE=T.MODELS_CODE) MODELS_ID,\n" );
    	sql.append("         T.MODELS_CODE,\n" );
    	sql.append("         T.CERTIFICATE,\n" );
    	sql.append("         T.ENGINE_TYPE,\n" );
    	sql.append("         T.USER_TYPE,\n" );
    	sql.append("         DECODE(SUBSTR(T.MODELS_CODE, 3, 1), '3', '300201', '300202') AS VEHICLE_USE,\n" );
    	sql.append("		(SELECT TR.DIC_CODE\n" );
    	sql.append("   		FROM DIC_TREE TR\n" );
    	sql.append("  		WHERE TR.DIC_VALUE IN\n" );
    	sql.append("        (SELECT A.DRIVE_TYPE_IMP\n" );
    	sql.append("           FROM SE_BA_DRIVE_VIN A\n" );
    	sql.append("          WHERE A.STATUS = "+DicConstant.YXBS_01+"\n" );
    	sql.append("            AND A.VIN = SUBSTR(T.VIN, 8, 1))) AS DRIVE_FORM,");
    	sql.append("         TO_DATE(T.BUY_DATE, 'yyyy-mm-dd') BUY_DATE,\n" );
    	sql.append("         T.GUARANTEE_NO,\n" );
    	sql.append("         TO_DATE(T.FACTORY_DATE, 'yyyy-mm-dd') FACTORY_DATE,\n" );
    	sql.append("         TO_DATE(T.MAINTENANCE_DATE, 'yyyy-mm-dd') MAINTENANCE_DATE,\n" );
    	sql.append("DECODE((SELECT A.DIC_CODE\n" );
    	sql.append("                 FROM DIC_TREE A\n" );
    	sql.append("                WHERE A.DIC_VALUE = T.SALE_STATUS\n" );
    	sql.append("                  AND A.PARENT_ID = 300800),\n" );
    	sql.append("               NULL,\n" );
    	sql.append("               300801,\n" );
    	sql.append("               (SELECT A.DIC_CODE\n" );
    	sql.append("                  FROM DIC_TREE A\n" );
    	sql.append("                 WHERE A.DIC_VALUE = T.SALE_STATUS\n" );
    	sql.append("                   AND A.PARENT_ID = 300800)) SALE_STATUS,");
    	sql.append("         T.LICENSE_PLATE,\n" );
    	sql.append("         T.USER_NAME,\n" );
    	sql.append("         T.USER_NO,\n" );
    	sql.append("         T.LINK_MAN,\n" );
    	sql.append("         T.PHONE,\n" );
    	sql.append("         T.USER_ADDRESS,\n" );
    	sql.append("         "+user.getOrgId()+",\n" );
    	sql.append("        '"+user.getAccount()+"',\n" );
    	sql.append("         SYSDATE,\n" );
    	sql.append("         "+user.getOemCompanyId()+",\n" );
    	sql.append("         TO_DATE(T.CERTIFICATEDATE, 'yyyy-mm-dd') CERTIFICATEDATE,\n" );
    	sql.append("         "+DicConstant.YXBS_01+",\n" );
    	sql.append("         (SELECT A.DIC_CODE\n" );
    	sql.append("            FROM DIC_TREE A\n" );
    	sql.append("           WHERE A.DIC_VALUE = T.BLACKLISTFLAG\n" );
    	sql.append("             AND A.PARENT_ID = 100100) BLACKLISTFLAG,\n" );
    	sql.append("         T.PRODUCTLINECODE,\n" );
    	sql.append("         T.INSIDECODE,\n" );
    	sql.append("         T.CONFIGURE,\n" );
    	sql.append("         T.CONTRACTAREANO\n" );
    	sql.append("    FROM MAIN_VEHICLE_TMP T\n" );
    	sql.append("   WHERE T.CREATE_USER = '"+user.getAccount()+"' "+rowNo+"");
    	return factory.update(sql.toString(), null);
    }
    
    /**
     * 车辆生产厂家规则查询
     * @return
     * @throws Exception
     */
    public BaseResultSet vehicleProductSearch(PageManager page,User user,String conditions)throws Exception {
    	String where = conditions;
    	page.setFilter(where);
    	BaseResultSet bs=null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.VEHICLE_PRO_ID, T.VIN_START, T.VIN_END, T.VEHICLE_PRODUCT\n" );
    	sql.append("  FROM MAIN_VEHICLE_PRODUCT T");
    	bs=factory.select(sql.toString(), page);
    	return bs;
    }    
    
    /**
     * 新增
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertProduct(MainVehicleProductVO vo)throws Exception{
    	return factory.insert(vo);
    }
    
    /**
     * 修改
     * * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateProduct(MainVehicleProductVO vo)throws Exception{
    	return factory.update(vo);
    }
    
    /**
     * 删除
     * @param vehicleProId
     * @return
     * @throws Exception
     */
    public boolean productDelete(String vehicleProId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM MAIN_VEHICLE_PRODUCT T WHERE T.VEHICLE_PRO_ID = "+vehicleProId+"");
    	return factory.update(sql.toString(), null);
    }
	public QuerySet expData(String conditions,User user) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT * FROM MAIN_VEHICLE_TMP T WHERE T.ROW_NO IN( "+conditions+") AND T.CREATE_USER = '"+user.getAccount()+"'");
		sql.append(" ORDER BY T.ROW_NO");
		return factory.select(null, sql.toString());
	}
	public QuerySet queryVin(String vin) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT * FROM MAIN_VEHICLE T WHERE T.SVIN ='"+vin+"' ");
		return factory.select(null, sql.toString());
	}
}

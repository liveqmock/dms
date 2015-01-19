package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.vo.part.PtBaDirectTypeVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

public class DirectTypeDao extends BaseDAO
{
    //定义instance
    public  static final DirectTypeDao getInstance(ActionContext atx)
    {
    	DirectTypeDao dao = new DirectTypeDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断直发类型代码是否存在
     * @throws Exception
     * @Author suoxiuli 2014-07-11
     */
	public QuerySet checkTypeCode(String typeCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select count(1) nums \n");
    	sql.append(" from pt_ba_direct_type \n");
    	sql.append(" where type_code = '" + typeCode +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
    /**
	 * 直发类型新增
	 * @throws Exception
     * @Author suoxiuli 2014-07-11
	 */
    public boolean insertDirectType(PtBaDirectTypeVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
    /**
	 * 直发类型修改
	 * @throws Exception
     * @Author suoxiuli 2014-07-11
	 */
	public boolean updateDirectType(PtBaDirectTypeVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 直发类型查询
	 * @throws Exception
     * @Author suoxiuli 2014-07-11
	 */
    public BaseResultSet search(PageManager page, String conditions) throws Exception
    {
    	String wheres = conditions;
        //wheres += " and status="+DicConstant.YXBS_01+"  ";
		wheres += " order by create_time desc ";
		page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" select type_id,type_name,type_code,remarks,org_id,status,secret_level,create_user,create_time ");
    	sql.append(" from pt_ba_direct_type ");
    	

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }

    /**
     * 更新直发类型的有效状态
     * @throws Exception
     * @Author suoxiuli 2014-07-12
     */
    /*public boolean updateDirectTypeStatus(String typeId, String updateUser, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update pt_ba_direct_type \n");
    	sql.append(" set status = '" + status + "', \n");
    	sql.append(" update_user = '" + updateUser + "', \n");
    	sql.append(" update_time = sysdate \n");
    	sql.append(" where type_id = '" + typeId + "' \n");
    	return factory.update(sql.toString(), null);
    }*/
}

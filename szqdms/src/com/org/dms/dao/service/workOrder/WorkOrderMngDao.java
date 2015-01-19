package com.org.dms.dao.service.workOrder;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuWorkOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.common.dataset.DataObjImpl;
import com.org.mvc.context.ActionContext;

public class WorkOrderMngDao extends BaseDAO{
	//定义instance
    public  static final WorkOrderMngDao getInstance(ActionContext atx)
    {
    	WorkOrderMngDao dao = new WorkOrderMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    /**
     * 生成工单号
     * @param vo
     * @return
     * @throws Exception
     */
    public String getNo()throws Exception
    {
    	QuerySet qs = null;
    	String date = getDateToString();
    	String num = "GD";
    	if(date!=null){
			date = date.replaceAll("-", "");
			num+=date;
		}
    	StringBuffer sql = new StringBuffer();
		sql.append("SELECT max(").append("WORK_NO").append(") as GDDM FROM ");
		sql.append( "SE_BU_WORK_ORDER").append(" t");
		sql.append(" where t.").append("WORK_NO").append(" like '%").append(num).append("%'");
		qs = factory.select(null, sql.toString());
		if(qs.getRowCount()==0){
			 num+="0001";
		}else{
			    DataObjImpl dateObj = (DataObjImpl) qs.getDataObjs().get(0);
			 	String tem  = dateObj.getString("GDDM");

			if(tem==null||"null".equals(tem)){
				num+="0001";	
			}else{
				int sz = Integer.parseInt(tem.substring(tem.length()-4, tem.length()))+1;
				//如果1位数
				if(String.valueOf(sz).length()==1){
					num=tem.substring(0, tem.length()-4)+"000"+String.valueOf(sz);
				}
				//如果2位数
				else if(String.valueOf(sz).length()==2){
					num=tem.substring(0, tem.length()-4)+"00"+String.valueOf(sz);
				}
				//如果3位数
				else if(String.valueOf(sz).length()==3){
					num=tem.substring(0, tem.length()-4)+"0"+String.valueOf(sz);
				}
				//如果4位数
				else{
				num=tem.substring(0, tem.length()-4)+String.valueOf(sz);
				}
			}
		 }
		return num;
    }
    public static String getDateToString(){
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = formatter.format(date);
		if(null!=d1&&!"".equals(d1))
		return d1;
		else return null;
	}	
    /**
     * 工单维护新增
     * @param vo
     * @throws Exception
     */
    public boolean insertWorkOrder(SeBuWorkOrderVO vo)throws Exception {
        return factory.insert(vo);
    }
    /**
     * @title: 删除工单
     * workOrderDelete
     * @throws Exception    设定文件 
     * @return boolean    返回类型 
     * @date 2014年7月3日11:17:22
     */
    public boolean workOrderDelete(String workId) throws Exception
    {
 	   StringBuffer sql = new StringBuffer();
 	   sql.append(" DELETE SE_BU_WORK_ORDER T WHERE T.WORK_ID ="+workId+"\n");
 	   return factory.delete(sql.toString(), null);
    }
    /**
     * 工单维护修改
     * @param vo
     * @throws Exception
     */
    public boolean updateWorkOrder(SeBuWorkOrderVO vo)throws Exception {
    	return factory.update(vo);
    }
    /**
	 * @title: search
	 * @date 2014.09.25
	 */
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.STATUS="+DicConstant.YXBS_01+""
    			+ " AND T.ORG_ID = "+user.getOrgId()+""
    			+ " AND T.WORK_STATUS IS NULL"
    			+ " ORDER BY  T.WORK_ID";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.WORK_ID,\n" );
    	sql.append("       T.WORK_NO,\n" );
    	sql.append("       T.REPAIR_USER,\n" );
    	sql.append("       T.REPAIR_DATE,\n" );
    	sql.append("       T.WORK_TYPE,\n" );
    	sql.append("       T.IF_OUT,\n" );
    	sql.append("       T.WORK_STATUS,\n" );
    	sql.append("       T.APPLY_USER,\n" );
    	sql.append("       T.APPLY_MOBIL,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.APPLY_ADDRESS,\n" );
    	sql.append("       T.APPLY_REMARKS,\n" );
    	sql.append("       T.REP_USER_TEL,\n" );
    	sql.append("       T.REJECTION_DATE,\n" );
    	sql.append("       T.GO_DATE,\n" );
    	sql.append("       T.ARRIVE_DATE,\n" );
    	sql.append("       T.COMPLETE_DATE,\n" );
    	sql.append("       T.COMPANY_ID,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.OEM_COMPANY_ID,\n" );
    	sql.append("       T.SECRET_LEVEL,\n" );
    	sql.append("       T.START_LONGITUDE,\n" );
    	sql.append("       T.END_LONGITUDE,\n" );
    	sql.append("       T.START_LATITUDE,\n" );
    	sql.append("       T.END_LATITUDE,\n" );
    	sql.append("       T.WORK_VIN\n" );
    	sql.append("  FROM SE_BU_WORK_ORDER T\n" );
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("REJECTION_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("GO_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDic("STATUS","YXBS");
		bs.setFieldDic("IF_OUT","SF");
		bs.setFieldDic("WORK_TYPE","PGLX");
		bs.setFieldDic("WORK_STATUS","PGDZT");
		bs.setFieldDic("IF_FIXCOSTS","SF");
		bs.setFieldDic("ACTIVITY_TYPE","HDLB");
		bs.setFieldDic("ACTIVITY_STATUS","HDZT");
		bs.setFieldDic("MANAGE_TYPE","CLFS");
    	return bs;
    }
}

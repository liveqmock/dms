package com.org.dms.dao.part.mainTenanceApplication;

import java.util.Map;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: MainTenanceApplicationDao 
 * Function: 配件信息维护申请Dao
 * date: 2014年10月11日 上午9:37:15
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class MainTenanceApplicationDao extends BaseDAO{

	public static final MainTenanceApplicationDao getInstance(ActionContext ac){
		MainTenanceApplicationDao dao = new MainTenanceApplicationDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * queryApplicationList:申请方的申请查询
	 * @author fuxiao
	 * Date:2014年10月11日上午9:49:38
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet queryApplicationList(PageManager page, String conditions, User user) throws Exception {
		BaseResultSet bs = null;
		String where = conditions;
		where += " AND STATUS = '" + DicConstant.YXBS_01 + "'";
		where += " AND ORG_ID = '" + user.getOrgId() + "'";
		where += " AND APPLICATION_STATUS NOT IN ('"+DicConstant.PJWHSQZT_02+"','"+DicConstant.PJWHSQZT_03+"','"+DicConstant.PJWHSQZT_05+"')";
		where += " ORDER BY APPLICATION_NO DESC";
		page.setFilter(where);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT APPLICATION_ID,\n" +
						"       APPLICATION_TYPE,\n" + 
						"       APPLICATION_NO,\n" + 
						"       APPLICATION_STATUS,\n" + 
						"       VIN,\n" + 
						"       APPLICATION_TIME,\n" + 
						"       APPLICATION_PERSON,\n" + 
						"       APPLICATION_WORK,\n" + 
						"       APPLICATION_INFOMATION,\n" + 
						"       ENGINEERING_DEPARTMENT,\n" + 
						"       ENGINEERING_DEPARTMENT_DATE,\n" + 
						"       ENGINEERING_DEPARTMENT_REMARK,\n" + 
						"       PURCHASING_DEPARTMENT,\n" + 
						"       PURCHASING_DEPARTMENT_DATE,\n" + 
						"       PURCHASING_DEPARTMENT_REMARK,\n" + 
						"       PLANNER,\n" + 
						"       PLANNER_DATE,\n" + 
						"       ORG_ID,\n" + 
						"       STATUS\n" + 
						"  FROM PT_BU_MAINTENANCE_APPLICATION"
				);
		bs = this.factory.select(sb.toString(), page);
		bs.setFieldDic("APPLICATION_TYPE","PJWHSQLX");
		bs.setFieldDic("APPLICATION_STATUS","PJWHSQZT");
    	bs.setFieldDateFormat("APPLICATION_TIME", "yyyy-MM-dd HH:mm:ss");
		return bs;
	}
	
	/**
	 * 
	 * queryApplicationList:配件申请单下载查询
	 * @author fuxiao
	 * Date:2014年10月11日上午9:49:38
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet queryPartInfoExport(PageManager page, String conditions, User user) throws Exception {
		BaseResultSet bs = null;
		String where = conditions;
		where += " AND STATUS = '" + DicConstant.YXBS_01 + "'";
		where += " AND APPLICATION_STATUS = '"+DicConstant.PJWHSQZT_05+"'";
		where += " ORDER BY APPLICATION_NO DESC";
		page.setFilter(where);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT APPLICATION_ID,\n" +
						"       APPLICATION_TYPE,\n" + 
						"       APPLICATION_NO,\n" + 
						"       APPLICATION_STATUS,\n" + 
						"       VIN,\n" + 
						"       APPLICATION_TIME,\n" + 
						"       APPLICATION_PERSON,\n" + 
						"       APPLICATION_WORK,\n" + 
						"       APPLICATION_INFOMATION,\n" + 
						"       ENGINEERING_DEPARTMENT,\n" + 
						"       ENGINEERING_DEPARTMENT_DATE,\n" + 
						"       ENGINEERING_DEPARTMENT_REMARK,\n" + 
						"       PURCHASING_DEPARTMENT,\n" + 
						"       PURCHASING_DEPARTMENT_DATE,\n" + 
						"       PURCHASING_DEPARTMENT_REMARK,\n" + 
						"       PLANNER,\n" + 
						"       PLANNER_DATE,\n" + 
						"       ORG_ID,\n" + 
						"       STATUS\n" + 
						"  FROM PT_BU_MAINTENANCE_APPLICATION"
				);
		bs = this.factory.select(sb.toString(), page);
		bs.setFieldDic("APPLICATION_TYPE","PJWHSQLX");
		bs.setFieldDic("APPLICATION_STATUS","PJWHSQZT");
    	bs.setFieldDateFormat("APPLICATION_TIME", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("PURCHASING_DEPARTMENT_DATE", "yyyy-MM-dd HH:mm:ss");
		return bs;
	}
	
	// 经销商申请单自身查询：需要挂ORG_ID
	public BaseResultSet queryApplicationListForDealer(PageManager page, String conditions, User user) throws Exception {
		BaseResultSet bs = null;
		String where = conditions;
		where += " AND STATUS = '" + DicConstant.YXBS_01 + "'";
		where += " AND ORG_ID = '" + user.getOrgId() + "'";
		where += " ORDER BY APPLICATION_NO DESC";
		page.setFilter(where);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT APPLICATION_ID,\n" +
						"       APPLICATION_TYPE,\n" + 
						"       APPLICATION_NO,\n" + 
						"       APPLICATION_STATUS,\n" + 
						"       VIN,\n" + 
						"       APPLICATION_TIME,\n" + 
						"       APPLICATION_PERSON,\n" + 
						"       APPLICATION_WORK,\n" + 
						"       APPLICATION_INFOMATION,\n" + 
						"       ENGINEERING_DEPARTMENT,\n" + 
						"       ENGINEERING_DEPARTMENT_DATE,\n" + 
						"       ENGINEERING_DEPARTMENT_REMARK,\n" + 
						"       PURCHASING_DEPARTMENT,\n" + 
						"       PURCHASING_DEPARTMENT_DATE,\n" + 
						"       PURCHASING_DEPARTMENT_REMARK,\n" + 
						"       PLANNER,\n" + 
						"       PLANNER_DATE,\n" + 
						"       ORG_ID,\n" + 
						"       STATUS\n" + 
						"  FROM PT_BU_MAINTENANCE_APPLICATION"
				);
		bs = this.factory.select(sb.toString(), page);
		bs.setFieldDic("APPLICATION_TYPE","PJWHSQLX");
		bs.setFieldDic("APPLICATION_STATUS","PJWHSQZT");
    	bs.setFieldDateFormat("APPLICATION_TIME", "yyyy-MM-dd HH:mm:ss");
		return bs;
	}
	
	// 审核方申请单查询
	public BaseResultSet queryApplicationListForAudit(PageManager page, String conditions, User user) throws Exception {
		conditions = this.filterCondition(conditions);
		BaseResultSet bs = null;
		String where = conditions;
		where += " AND STATUS = '" + DicConstant.YXBS_01 + "'";
		where += " AND APPLICATION_STATUS <> " + DicConstant.PJWHSQZT_01 ;
		where += " ORDER BY APPLICATION_STATUS DESC, PURCHASING_DEPARTMENT_DATE DESC, APPLICATION_ID DESC";
		page.setFilter(where);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT APPLICATION_ID,\n" +
						"       APPLICATION_TYPE,\n" + 
						"       APPLICATION_NO,\n" + 
						"       APPLICATION_STATUS,\n" + 
						"       VIN,\n" + 
						"       APPLICATION_TIME,\n" + 
						"       APPLICATION_PERSON,\n" + 
						"       APPLICATION_WORK,\n" + 
						"       APPLICATION_INFOMATION,\n" + 
						"       ENGINEERING_DEPARTMENT,\n" + 
						"       ENGINEERING_DEPARTMENT_DATE,\n" + 
						"       ENGINEERING_DEPARTMENT_REMARK ,\n" + 
						"       PURCHASING_DEPARTMENT,\n" + 
						"       PURCHASING_DEPARTMENT_DATE ,\n" + 
						"       PURCHASING_DEPARTMENT_REMARK ,\n" + 
						"       PLANNER,\n" + 
						"       PLANNER_DATE,\n" + 
						"       ORG_ID,\n" + 
						"       STATUS\n" + 
						"  FROM PT_BU_MAINTENANCE_APPLICATION T"
				);
		bs = this.factory.select(sb.toString(), page);
		bs.setFieldDic("APPLICATION_TYPE","PJWHSQLX");
		bs.setFieldDic("APPLICATION_STATUS","PJWHSQZT");
    	bs.setFieldDateFormat("APPLICATION_TIME", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("PURCHASING_DEPARTMENT_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("ENGINEERING_DEPARTMENT_DATE", "yyyy-MM-dd HH:mm:ss");
		return bs;
	}
	
	/**
	 * 
	 * @Title: filterCondition
	 * @Description: 对查询条件进行过滤转换
	 * @param conditions
	 * @return
	 * @return: String
	 */
	public String filterCondition(String conditions){
		if(conditions.indexOf("CODE") != -1){
			String tempStr = conditions.substring(conditions.indexOf("O.CODE"),
												  conditions.indexOf("'", conditions.indexOf("CODE") + 10) + 1);
			conditions = conditions.replace(" AND " + tempStr, "");
			conditions += " AND EXISTS (SELECT 1 FROM tm_org o WHERE o.org_id = t.org_id AND  " + tempStr + ")";
		}
		return conditions;
	}
	
	/**
	 * 
	 * queryApplicationListByAudit: 审核查询
	 * @author fuxiao
	 * Date:2014年10月13日下午2:50:56
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 
	 */
	public BaseResultSet queryApplicationListByAudit(PageManager page, String conditions, User user) throws Exception {
		conditions = this.filterCondition(conditions);
		BaseResultSet bs = null;
		String where = conditions;
		where += " AND STATUS = '" + DicConstant.YXBS_01 + "'";
		where += " ORDER BY APPLICATION_NO DESC";
		page.setFilter(where);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT APPLICATION_ID,\n" +
						"       APPLICATION_TYPE,\n" + 
						"       APPLICATION_NO,\n" + 
						"       APPLICATION_STATUS,\n" + 
						"       VIN,\n" + 
						"       APPLICATION_TIME,\n" + 
						"       APPLICATION_PERSON,\n" + 
						"       APPLICATION_WORK,\n" + 
						"       APPLICATION_INFOMATION,\n" + 
						"       ENGINEERING_DEPARTMENT,\n" + 
						"       ENGINEERING_DEPARTMENT_DATE,\n" + 
						"       ENGINEERING_DEPARTMENT_REMARK,\n" + 
						"       PURCHASING_DEPARTMENT,\n" + 
						"       PURCHASING_DEPARTMENT_DATE,\n" + 
						"       PURCHASING_DEPARTMENT_REMARK,\n" + 
						"       PLANNER,\n" + 
						"       PLANNER_DATE,\n" + 
						"       ORG_ID,\n" + 
						"       STATUS\n" + 
						"  FROM PT_BU_MAINTENANCE_APPLICATION T "
				);
		bs = this.factory.select(sb.toString(), page);
		bs.setFieldDic("APPLICATION_TYPE","PJWHSQLX");
		bs.setFieldDic("APPLICATION_STATUS","PJWHSQZT");
    	bs.setFieldDateFormat("APPLICATION_TIME", "yyyy-MM-dd HH:mm:ss");
		return bs;
	}
	
	// PD审核查询
	public BaseResultSet queryApplicationListByAuditForPD(PageManager page, String conditions, User user) throws Exception {
		conditions = this.filterCondition(conditions);
		BaseResultSet bs = null;
		String where = conditions;
		where += " AND STATUS = '" + DicConstant.YXBS_01 + "'";
		where += " AND APPLICATION_TYPE NOT IN ('"+DicConstant.PJWHSQLX_02+"','"+DicConstant.PJWHSQLX_03+"')";
		where += " ORDER BY APPLICATION_NO DESC";
		page.setFilter(where);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT APPLICATION_ID,\n" +
						"       APPLICATION_TYPE,\n" + 
						"       APPLICATION_NO,\n" + 
						"       APPLICATION_STATUS,\n" + 
						"       VIN,\n" + 
						"       APPLICATION_TIME,\n" + 
						"       APPLICATION_PERSON,\n" + 
						"       APPLICATION_WORK,\n" + 
						"       APPLICATION_INFOMATION,\n" + 
						"       ENGINEERING_DEPARTMENT,\n" + 
						"       ENGINEERING_DEPARTMENT_DATE,\n" + 
						"       ENGINEERING_DEPARTMENT_REMARK,\n" + 
						"       PURCHASING_DEPARTMENT,\n" + 
						"       PURCHASING_DEPARTMENT_DATE,\n" + 
						"       PURCHASING_DEPARTMENT_REMARK,\n" + 
						"       PLANNER,\n" + 
						"       PLANNER_DATE,\n" + 
						"       ORG_ID,\n" + 
						"       STATUS\n" + 
						"  FROM PT_BU_MAINTENANCE_APPLICATION T"
				);
		bs = this.factory.select(sb.toString(), page);
		bs.setFieldDic("APPLICATION_TYPE","PJWHSQLX");
		bs.setFieldDic("APPLICATION_STATUS","PJWHSQZT");
    	bs.setFieldDateFormat("APPLICATION_TIME", "yyyy-MM-dd HH:mm:ss");
		return bs;
	}
	
	/**
	 * 
	 * queryApplicationDetails: 根据申请单单号查询申请单详情
	 * @author fuxiao
	 * Date:2014年10月11日上午11:27:10
	 * @param applicationId
	 * @return
	 * @throws Exception
	 */
	public QuerySet queryApplicationDetails(String applicationId) throws Exception {
		return this.factory.select(new Object[]{applicationId}, 
									"SELECT APPLICATION_ID,\n" +
									"		APPLICATION_TYPE APPLICATION_TYPE_CODE,\n"+
									"       (SELECT DIC.DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = APPLICATION_TYPE) APPLICATION_TYPE,\n" + 
									"       APPLICATION_NO,\n" + 
									"       (SELECT DIC.DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = APPLICATION_STATUS) APPLICATION_STATUS,\n" + 
									"       VIN,\n" + 
									"       TO_CHAR(APPLICATION_TIME,'YYYY-MM-DD HH24:MI:SS') APPLICATION_TIME,\n" + 
									"       APPLICATION_PERSON,\n" + 
									"       APPLICATION_WORK,\n" + 
									"       APPLICATION_INFOMATION,\n" + 
									"       ENGINEERING_DEPARTMENT,\n" + 
									"       TO_CHAR(ENGINEERING_DEPARTMENT_DATE,'YYYY-MM-DD HH24:MI:SS') ENGINEERING_DEPARTMENT_DATE,\n" + 
									"       ENGINEERING_DEPARTMENT_REMARK,\n" + 
									"       PURCHASING_DEPARTMENT,\n" + 
									"       TO_CHAR(PURCHASING_DEPARTMENT_DATE,'YYYY-MM-DD HH24:MI:SS') PURCHASING_DEPARTMENT_DATE,\n" + 
									"       PURCHASING_DEPARTMENT_REMARK,\n" + 
									"       PLANNER,\n" + 
									"       TO_CHAR(PLANNER_DATE,'YYYY-MM-DD HH24:MI:SS') PLANNER_DATE,\n" + 
									"       ORG_ID,\n" + 
									"       STATUS\n" + 
									"  FROM PT_BU_MAINTENANCE_APPLICATION \n" +
									" WHERE APPLICATION_ID = ?"
				);
	}
	
	/**
	 * 
	 * queryApplicationDetailsForPartInfo: 配件信息录入查询详情
	 * @author fuxiao
	 * Date:2014年10月11日下午2:06:47
	 * @param page
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet queryApplicationDetailsForPartInfo(PageManager page, String conditions) throws Exception {
		BaseResultSet bs = null;
		String where = conditions;
		where += " ORDER BY PART_NO ASC";
		page.setFilter(where);
		StringBuilder sb = new StringBuilder();
		sb.append(
					"SELECT ENTRY_ID,\n" +
					"       APPLICATION_ID,\n" + 
					"       PART_NO,\n" + 
					"       PART_NAME,\n" + 
					"       VIN,\n" + 
					"       PROCESS_ROUTE,\n" + 
					"       OWN_FIRST_LEVEL,\n" + 
					"       OWN_SECOND_LEVEL,\n" + 
					"       PURCHASE_PRICE,\n" + 
					"       SUPPLIER_ID,\n" + 
					"       SUPPLIER_NAME,\n" + 
					"       ENGINEERING_DEPARTMENT,\n" + 
					"       ENGINEERING_DEPARTMENT_DATE,\n" + 
					"       ENGINEERING_DEPARTMENT_REMARK,\n" + 
					"       PURCHASING_DEPARTMENT,\n" + 
					"       PURCHASING_DEPARTMENT_DATE,\n" + 
					"       PURCHASING_DEPARTMENT_REMARK\n" + 
					"  FROM PT_BU_PART_INFO_ENTRY\n" 
				);
		bs = this.factory.select(sb.toString(), page);
		return bs;
	}
	
	// 根据申请单ID获取配件录入详细信息
	public QuerySet queryApplicationDetailsForPartInfo(String applicationId) throws Exception {
		return this.factory.select(new Object[]{applicationId}, 				
									"SELECT ENTRY_ID,\n" +
									"       APPLICATION_ID,\n" + 
									"       PART_NO,\n" + 
									"       PART_NAME,\n" + 
									"       VIN,\n" + 
									"       PROCESS_ROUTE,\n" + 
									"       OWN_FIRST_LEVEL,\n" + 
									"       OWN_SECOND_LEVEL,\n" + 
									"       PURCHASE_PRICE,\n" + 
									"       SUPPLIER_ID,\n" + 
									"       SUPPLIER_NAME,\n" + 
									"       ENGINEERING_DEPARTMENT,\n" + 
									"       ENGINEERING_DEPARTMENT_DATE,\n" + 
									"       ENGINEERING_DEPARTMENT_REMARK,\n" + 
									"       PURCHASING_DEPARTMENT,\n" + 
									"       PURCHASING_DEPARTMENT_DATE,\n" + 
									"       PURCHASING_DEPARTMENT_REMARK\n" + 
									"  FROM PT_BU_PART_INFO_ENTRY\n" +
									" WHERE APPLICATION_ID = ? \n" + 
									" ORDER BY PART_NO ASC");
	}
	
	/**
	 * 
	 * queryApplicationDetailsForCABAssembly: 查询驾驶室总成录入信息
	 * @author fuxiao
	 * Date:2014年10月12日下午5:24:41
	 * @param applicationId
	 * @return
	 * @throws Exception
	 */
	public QuerySet queryApplicationDetailsForCABAssembly(String applicationId) throws Exception {
		return this.factory.select(new Object[]{applicationId}, 
								"SELECT ENTRY_ID,\n" +
								"       APPLICATION_ID,\n" + 
								"       CAB_ASSEMBLY_NO,\n" + 
								"       CAR_BODY_COLOR,\n" + 
								"       VIN,\n" + 
								"       ENGINE_TYPE,\n" + 
								"       HILO_SHIFT,\n" + 
								"       WINDOW_TYPE,\n" + 
								"       AIR_TYPE,\n" + 
								"       IS_DEFLECTOR,\n" + 
								"       IS_RIGHT_MOUNTED\n" + 
								"  FROM PT_BU_CAB_ASSEMBLY_ENTRY\n" + 
								" WHERE APPLICATION_ID = ?");
	}
	
	/**
	 * 
	 * queryApplicationDetailsForCarBody: 查询车体描述信息
	 * @author fuxiao
	 * Date:2014年10月12日下午5:35:05
	 * @param applicationId
	 * @return
	 * @throws Exception
	 */
	public QuerySet queryApplicationDetailsForCarBody(String applicationId) throws Exception {
		return this.factory.select(new Object[]{applicationId}, 
													"SELECT BODY_ID,\n" +
													"       APPLICATION_ID,\n" + 
													"       BODY_CODE,\n" + 
													"       BODY_VALUE\n" + 
													"  FROM PT_BU_CAR_BODY_DESCRIPTION\n" + 
													" WHERE APPLICATION_ID = ?"
													);
	}
	
	/**
	 * 
	 * queryApplicationDetailsForCABInfo:驾驶室本体新编号录入审批表
	 * @author fuxiao
	 * Date:2014年10月11日下午3:29:12
	 * @param page
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet queryApplicationDetailsForCABInfo(PageManager page, String conditions) throws Exception {
		BaseResultSet bs = null;
		String where = conditions;
		where += " ORDER BY CAB_NO ASC";
		page.setFilter(where);
		StringBuilder sb = new StringBuilder();
		sb.append(
					"SELECT\n" +
					"   ENTRY_ID, APPLICATION_ID, CAB_NO, CAB_DESCRIPTION, VIN, REMARK\n" + 
					"  FROM PT_BU_CAB_INFO_ENTRY"
				);
		bs = this.factory.select(sb.toString(), page);
		return bs;
	}
	
	/**
	 * 
	 * queryApplicationDetailsForCABInfoEdit:驾驶室本体修改或审核时显示详细信息
	 * @author fuxiao
	 * Date:2014年10月13日上午9:56:25
	 * @param page
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public QuerySet queryApplicationDetailsForCABInfoEdit(String applicationId) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(
					"SELECT\n" +
					"   ENTRY_ID, APPLICATION_ID, CAB_NO, CAB_DESCRIPTION, VIN, REMARK\n" + 
					"  FROM PT_BU_CAB_INFO_ENTRY" +
					" WHERE APPLICATION_ID = ?" +
					" ORDER BY CAB_NO ASC"
				);
		return this.factory.select(new Object[]{applicationId}, sb.toString());
	}
	
	/**
	 * queryApplicationDetailsForPartInfoChange:零件编号更新（禁用）
	 * @author fuxiao
	 * Date:2014年10月11日下午3:33:42
	 * @param page
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet queryApplicationDetailsForPartInfoChange(PageManager page, String conditions) throws Exception {
		BaseResultSet bs = null;
		String where = conditions;
		where += " ORDER BY OLD_PART_NO ASC";
		page.setFilter(where);
		StringBuilder sb = new StringBuilder();
		sb.append(
					"SELECT ENTRY_ID,\n" +
					"       APPLICATION_ID,\n" + 
					"       OLD_PART_NO,\n" + 
					"       OLD_PART_NAME,\n" + 
					"       VIN,\n" + 
					"       NEW_PART_NO,\n" + 
					"       NEW_PART_NAME,\n" + 
					"       REMARK,\n" + 
					"       ENGINEERING_DEPARTMENT_REMARK,\n" + 
					"       PURCHASING_DEPARTMENT_REMARK\n" + 
					"  FROM PT_BU_PART_INFO_CHANGE_ENTRY"
				);
		bs = this.factory.select(sb.toString(), page);
		return bs;
	}
	
	/**
	 * 
	 * queryApplicationDetailsForPartInfoChangeForEdit:修改或审核时使用
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * @author fuxiao
	 * Date:2014年10月13日上午10:02:09
	 * @param applicationId
	 * @return
	 * @throws Exception
	 */
	public QuerySet queryApplicationDetailsForPartInfoChangeForEdit(String applicationId) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(
					"SELECT ENTRY_ID,\n" +
					"       APPLICATION_ID,\n" + 
					"       OLD_PART_NO,\n" + 
					"       OLD_PART_NAME,\n" + 
					"       VIN,\n" + 
					"       NEW_PART_NO,\n" + 
					"       NEW_PART_NAME,\n" + 
					"       REMARK,\n" + 
					"       ENGINEERING_DEPARTMENT_REMARK,\n" + 
					"       PURCHASING_DEPARTMENT_REMARK\n" + 
					"  FROM PT_BU_PART_INFO_CHANGE_ENTRY" +
					" WHERE APPLICATION_ID = ?" +
					" ORDER BY OLD_PART_NO ASC"					
				);
		return this.factory.select(new Object[]{applicationId}, sb.toString());
	}
	
	/**
	 * 
	 * queryApplicationDetailsForSupplierChange:供货商新增（变更）
	 * @author fuxiao
	 * Date:2014年10月11日下午3:39:01
	 * @param page
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet queryApplicationDetailsForSupplierChange(PageManager page, String conditions) throws Exception {
		BaseResultSet bs = null;
		String where = conditions;
		where += " ORDER BY PART_NO ASC";
		page.setFilter(where);
		StringBuilder sb = new StringBuilder();
		sb.append(
					"SELECT ENTRY_ID,\n" +
					"       APPLICATION_ID,\n" + 
					"       PART_NO,\n" + 
					"       PART_NAME,\n" + 
					"       OLD_SUPPLIER,\n" + 
					"       NEW_SUPPLIER,\n" + 
					"       REMARK,\n" + 
					"       OLD_SUPPLIER_REMARK,\n" + 
					"       NEW_SUPPLIER_REMARK,\n" + 
					"       PURCHASE_PRICE\n" + 
					"  FROM PT_BU_SUPPLIER_CHANGE_ENTRY"
				);
		bs = this.factory.select(sb.toString(), page);
		return bs;
	}
	
	/**
	 * 
	 * queryApplicationDetailsForSupplierChange: 修改或审核时使用
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * @author fuxiao
	 * Date:2014年10月13日上午10:04:45
	 * @param applicationId
	 * @return
	 * @throws Exception
	 
	 */
	public QuerySet queryApplicationDetailsForSupplierChangeForEdit(String applicationId) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(
					"SELECT ENTRY_ID,\n" +
					"       APPLICATION_ID,\n" + 
					"       PART_NO,\n" + 
					"       PART_NAME,\n" + 
					"       OLD_SUPPLIER,\n" + 
					"       NEW_SUPPLIER,\n" + 
					"       REMARK,\n" + 
					"       OLD_SUPPLIER_REMARK,\n" + 
					"       NEW_SUPPLIER_REMARK,\n" + 
					"       PURCHASE_PRICE\n" + 
					"  FROM PT_BU_SUPPLIER_CHANGE_ENTRY" +
					" WHERE APPLICATION_ID = ?" +
					" ORDER BY PART_NO ASC"
				);
		return this.factory.select(new Object[]{applicationId}, sb.toString());
	}
	
	public boolean submitApplication(Map<String, String> args) throws Exception {
		return this.factory.update(
							"UPDATE PT_BU_MAINTENANCE_APPLICATION T\n" +
							"   SET T.APPLICATION_STATUS = ?,\n" + 
							"       T.APPLICATION_TIME = SYSDATE,\n" + 
							"       T.UPDATE_USER = ?,\n" + 
							"       T.UPDATE_TIME = SYSDATE\n" + 
							" WHERE T.APPLICATION_ID = ?", new Object[]{
									DicConstant.PJWHSQZT_02,
									args.get("USER_ACCOUNT"),
									args.get("APPLICATION_ID_I")
							});
	}
	
	// 根据ORG_ID获取对应ORG_NAME
	public String getOrgName(String orgId) throws Exception{
		return this.factory.select("SELECT ONAME FROM TM_ORG WHERE ORG_ID = ?",new Object[]{orgId})[0][0];
	}
	
	
	// 查询经销商信息：控件使用
	public BaseResultSet querySupplierInfo(PageManager page, String conditions) throws Exception {
		BaseResultSet bs = null;
		String where = conditions;
		where += " AND PART_IDENTIFY = '" + DicConstant.YXBS_01 + "'";
		where += " ORDER BY SUPPLIER_CODE ASC ";
		page.setFilter(where);
		StringBuilder sb = new StringBuilder();
		sb.append(
					"SELECT SUPPLIER_ID,\n" +
					"       SUPPLIER_NAME,\n" + 
					"       SUPPLIER_CODE,\n" + 
					"       PART_ACCESS,\n" + 
					"       PART_ACCESS_TEL,\n" + 
					"       ADDRESS,\n" + 
					"       EXCLUSIVE_TYPE,\n" + 
					"       ERP_NO,\n" + 
					"       WARRANTY_MONEY,\n" + 
					"       WARRANTY_DAYS,\n" + 
					"       TAX_TYPE,\n" + 
					"       TAX_RATE,\n" + 
					"       OPEN_BANK,\n" + 
					"       ACCOUNT,\n" + 
					"       TAX_NO,\n" + 
					"       LEGAL_PERSON,\n" + 
					"       FAX,\n" + 
					"       SE_ACCESS,\n" + 
					"       SE_ACCESS_TEL,\n" + 
					"       IF_ARMY,\n" + 
					"       SUPPLIER_STATUS,\n" + 
					"       REMARKS,\n" + 
					"       COMPANY_ID,\n" + 
					"       ORG_ID,\n" + 
					"       OEM_COMPANY_ID,\n" + 
					"       SECRET_LEVEL,\n" + 
					"       ACTUL_SUPPLIER,\n" + 
					"       REAL_NO\n" + 
					"  FROM PT_BA_SUPPLIER"
				);
		bs = this.factory.select(sb.toString(), page);
		bs.setFieldDic("IF_ARMY","SF");
		return bs;
	}
	
	// 根据申请单ID查询配件申请明细
	public QuerySet queryPartInfoApplicationInfo(String applicationIds) throws Exception {
		return this.factory.select(new Object[0], "SELECT APPLICATION_ID, APPLICATION_NO,PART_NO ,PART_NAME, VIN, PROCESS_ROUTE, OWN_FIRST_LEVEL, OWN_SECOND_LEVEL, SUPPLIER_NAME,"
				+ " DECODE((SELECT COUNT(1) FROM PT_BA_INFO I WHERE I.PART_CODE = PART_NO), 0, '未维护', '已维护') IF_MAINTAIN "
				+ " FROM (SELECT APPLICATION_ID,\n" +
				"       (SELECT A.APPLICATION_NO FROM PT_BU_MAINTENANCE_APPLICATION A WHERE A.APPLICATION_ID = T.APPLICATION_ID) APPLICATION_NO,\n" + 
				"       PART_NO,\n" + 
				"       PART_NAME,\n" + 
				"       VIN,\n" + 
				"       PROCESS_ROUTE,\n" + 
				"       OWN_FIRST_LEVEL,\n" + 
				"       OWN_SECOND_LEVEL,\n" + 
				"       SUPPLIER_NAME\n" + 
				"  FROM PT_BU_PART_INFO_ENTRY T\n" + 
				" WHERE T.APPLICATION_ID IN ("+applicationIds+") ORDER BY PART_NO,PART_NO ASC) ORDER BY APPLICATION_NO DESC");
	}
	
	/**
	 * 
	 * @Title: checkPartIsExist
	 * @Description: 检测配件是否存在:配件状态为有效和待报废的配件
	 * @param partCode
	 * @return true 存在 false 不存在
	 * @throws Exception
	 * @return: boolean
	 */
	public boolean checkPartIsExist(String partCode) throws Exception {
		return Integer.parseInt(this.factory.select("SELECT COUNT(1) FROM PT_BA_INFO T WHERE T.PART_CODE = ? AND T.PART_STATUS IN (?,?)", new Object[]{partCode, DicConstant.PJZT_01, DicConstant.PJZT_03})[0][0]) > 0;
	}
}

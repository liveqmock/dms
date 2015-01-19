package com.org.dms.dao.part.mainTenanceApplication;

import java.util.Map;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: CABAssemblyType 
 * Function: 驾驶室总成新编号录入
 * date: 2014年10月12日 下午4:35:02
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 */
public class CABAssemblyType implements ApplicationType {
	private DBFactory factory;
	
	public void setFactory(DBFactory factory) {
		this.factory = factory;
	}
	public static final ApplicationType getInstance(ActionContext ac){
		CABAssemblyType dao = new CABAssemblyType();
		dao.setFactory(ac.getDBFactory());
		return dao;
	}

	@Override
	public void save(Map<String,String> hm) throws Exception {
		
		String APPLICATION_PERSON_A = hm.get("APPLICATION_PERSON_A");					// 申请人
		String APPLICATION_WORK_A = hm.get("APPLICATION_WORK_A");						// 申请单位
		String APPLICATION_INFOMATION_A = hm.get("APPLICATION_INFOMATION_A");			// 申请人联系方式
		String APPLICATION_TYPE_A = hm.get("APPLICATION_TYPE_A");						// 申请单类型
		String CAB_ASSEMBLY_NO_A = hm.get("CAB_ASSEMBLY_NO_A");							// 驾驶室总成编号
		String VIN_A = hm.get("VIN_A");													// VIN
		String CAR_BODY_DESCRIPTION_A = hm.get("CAR_BODY_DESCRIPTION_A");				// 车体描述 
		String CAR_BODY_DESCRIPTION_CODE_A = hm.get("CAR_BODY_DESCRIPTION_CODE_A");		// 车体描述Code
		String CAR_BODY_COLOR_A = hm.get("CAR_BODY_COLOR_A");							// 车体颜色
		String ENGINE_TYPE_A = hm.get("ENGINE_TYPE_A");									// 发动机型号
		String HILO_SHIFT_A = hm.get("HILO_SHIFT_A");									// 高低位换挡
		String WINDOW_TYPE_A = hm.get("WINDOW_TYPE_A");									// 窗机类型
		String AIR_TYPE_A = hm.get("AIR_TYPE_A");										// 空调类型
		String IS_DEFLECTOR_A = hm.get("IS_DEFLECTOR_A").trim();						// 是否带导流罩
		String IS_RIGHT_MOUNTED_A = hm.get("IS_RIGHT_MOUNTED_A").trim();				// 是否右置
		String USER_ACCOUNT = hm.get("USER_ACCOUNT");									// 操作人员账号
		String ORG_ID = hm.get("ORG_ID");												// 操作人员渠道ID
		String ORG_CODE = hm.get("ORG_CODE");										 	// 操作人员渠道CODE
		String editAction = hm.get("EDIT_ACTION") == null ? "" : hm.get("EDIT_ACTION"); // 获取修改动作参数
		String applicationId = "";
		if(editAction.equals("YES")){
			applicationId = hm.get("APPLICATION_ID_A") == null ? "" : hm.get("APPLICATION_ID_A");			          // 申请单ID
			String updateStr = 
					"UPDATE PT_BU_MAINTENANCE_APPLICATION\n" +
							"   SET " + 
							"       APPLICATION_PERSON = ?,\n" + 
							"       APPLICATION_WORK = ?,\n" + 
							"       APPLICATION_INFOMATION = ?,\n" + 
							"       UPDATE_USER = ?,\n" + 
							"       UPDATE_TIME = SYSDATE\n" + 
							" WHERE APPLICATION_ID = ?"; 															   // 修改申请单信息
			this.factory.update(updateStr, new Object[]{ APPLICATION_PERSON_A, APPLICATION_WORK_A, APPLICATION_INFOMATION_A, USER_ACCOUNT, applicationId});
			this.factory.update("DELETE PT_BU_CAB_ASSEMBLY_ENTRY WHERE APPLICATION_ID = ?", new Object[]{applicationId}); // 删除子表信息
			this.factory.update("DELETE PT_BU_CAR_BODY_DESCRIPTION WHERE APPLICATION_ID = ?", new Object[]{applicationId}); // 删除子表信息
			
		}else{
			applicationId = this.factory.select("SELECT F_GETID() FROM DUAL")[0][0];
			String appplicateInfoSql = 
										"INSERT INTO PT_BU_MAINTENANCE_APPLICATION\n" +
										"(\n" + 
										"    APPLICATION_ID,         APPLICATION_TYPE,           APPLICATION_NO,               APPLICATION_STATUS,\n" + 
										"    VIN,                    APPLICATION_PERSON,         APPLICATION_WORK,             APPLICATION_INFOMATION,\n" + 
										"    ORG_ID,                 CREATE_USER,                CREATE_TIME,                  STATUS\n" + 
										")\n" + 
										"VALUES\n" + 
										"(\n" + 
										"    ?,                      ?,                          f_get_application_no(?),      ?,\n" + 
										"    ?,                      ?,                          ?,    						   ?,\n" + 
										"    ?,                      ?,                          SYSDATE,					   ?\n" + 
										")";
			
			// 插入申请单主表内容
			this.factory.update(appplicateInfoSql, new Object[]{
					applicationId, APPLICATION_TYPE_A, ORG_CODE, DicConstant.PJWHSQZT_01,
					"", APPLICATION_PERSON_A, APPLICATION_WORK_A, APPLICATION_INFOMATION_A,
					ORG_ID, USER_ACCOUNT, DicConstant.YXBS_01
			});
		}
			
			// 驾驶室总成新编号录入审批表
			String cabAssemblySql = 
									"INSERT INTO PT_BU_CAB_ASSEMBLY_ENTRY\n" +
									"(\n" + 
									"       ENTRY_ID,     APPLICATION_ID,   CAB_ASSEMBLY_NO, CAR_BODY_COLOR, VIN,\n" + 
									"       ENGINE_TYPE,  HILO_SHIFT,       WINDOW_TYPE,     AIR_TYPE,\n" + 
									"       IS_DEFLECTOR, IS_RIGHT_MOUNTED, CREATE_USER,     CREATE_TIME\n" + 
									")\n" + 
									"VALUES\n" + 
									"(\n" + 
									"       F_GETID(),   ?,                 ?,               ?,		?,\n" + 
									"       ?,           ?,                 ?,               ?,\n" + 
									"       ?,           ?,                 ?,               SYSDATE\n" + 
									")";
	
			this.factory.update(cabAssemblySql, new String[]{
								   applicationId,      CAB_ASSEMBLY_NO_A, CAR_BODY_COLOR_A, VIN_A,
					ENGINE_TYPE_A, HILO_SHIFT_A,  	   WINDOW_TYPE_A,     AIR_TYPE_A,
					IS_DEFLECTOR_A,IS_RIGHT_MOUNTED_A, USER_ACCOUNT
			});
			
			// 车体描述子表
			String carBodySql = 
						"INSERT INTO PT_BU_CAR_BODY_DESCRIPTION\n" +
						"  (BODY_ID,   APPLICATION_ID, BODY_CODE, BODY_VALUE, CREATE_USER, CREATE_TIME)\n" + 
						"VALUES\n" + 
						"  (F_GETID(), ?,              ?,         ?,          ?,           SYSDATE)";
	
			this.factory.update(carBodySql, new String[]{
								   applicationId,      CAR_BODY_DESCRIPTION_CODE_A, CAR_BODY_DESCRIPTION_A, USER_ACCOUNT 
			});
		
	}

	/**
	 * 技术科审核
	 */
	@Override
	public void edAudit(Map<String, String> hm) throws Exception {
		String APPLICATION_ID_A = hm.get("APPLICATION_ID_A");								// 申请单ID
		String ENGINEERING_DEPARTMENT_A = hm.get("ENGINEERING_DEPARTMENT_A");				// 技术科审批人
		String APPLICATION_STATUS_A = hm.get("APPLICATION_STATUS_A");						// 审批状态
		String ENGINEERING_DEPARTMENT_REMARK_A = hm.get("ENGINEERING_DEPARTMENT_REMARK_A");	// 技术科审批备注
		String USER_ACCOUNT = hm.get("USER_ACCOUNT");										// 操作人员账号
		// 更新主表信息
		String updataStr = 
							"UPDATE PT_BU_MAINTENANCE_APPLICATION\n" +
									"   SET APPLICATION_STATUS = ?,\n" + 
									"       ENGINEERING_DEPARTMENT = ?,\n" + 
									"       ENGINEERING_DEPARTMENT_DATE = SYSDATE,\n" + 
									"       ENGINEERING_DEPARTMENT_REMARK = ?,\n" + 
									"       UPDATE_USER = ?,\n" + 
									"       UPDATE_TIME = SYSDATE\n" + 
									" WHERE APPLICATION_ID = ?";
		this.factory.update(updataStr, new Object[]{
				APPLICATION_STATUS_A, ENGINEERING_DEPARTMENT_A, ENGINEERING_DEPARTMENT_REMARK_A, USER_ACCOUNT, APPLICATION_ID_A
		});
		
	}

	/**
	 * 采供科审核
	 */
	@Override
	public void pdAudit(Map<String, String> hm) throws Exception {
		String APPLICATION_ID_A = hm.get("APPLICATION_ID_A");								// 申请单ID
		String PURCHASING_DEPARTMENT_A = hm.get("PURCHASING_DEPARTMENT_A");					// 采供科审核人
		String APPLICATION_STATUS_A = hm.get("APPLICATION_STATUS_A");						// 审批状态
		String PURCHASING_DEPARTMENT_REMARK_A = hm.get("PURCHASING_DEPARTMENT_REMARK_A");	// 采供科审批备注
		String USER_ACCOUNT = hm.get("USER_ACCOUNT");										// 操作人员账号
		
		// 更新主表信息
		String updataStr = 
							"UPDATE PT_BU_MAINTENANCE_APPLICATION\n" +
									"   SET APPLICATION_STATUS = ?,\n" + 
									"       PURCHASING_DEPARTMENT = ?,\n" + 
									"       PURCHASING_DEPARTMENT_DATE = SYSDATE,\n" + 
									"       PURCHASING_DEPARTMENT_REMARK = ?,\n" + 
									"       UPDATE_USER = ?,\n" + 
									"       UPDATE_TIME = SYSDATE\n" + 
									" WHERE APPLICATION_ID = ?";
		this.factory.update(updataStr, new Object[]{
				APPLICATION_STATUS_A, PURCHASING_DEPARTMENT_A, PURCHASING_DEPARTMENT_REMARK_A, USER_ACCOUNT, APPLICATION_ID_A
		});
	}
	
	@Override
	public void edTempSave(Map<String, String> hm) throws Exception {
		// 壳体暂不需要实现
		
	}
	@Override
	public void pdTempSave(Map<String, String> hm) throws Exception {
		// 壳体暂不需要实现
		
	}

	
}

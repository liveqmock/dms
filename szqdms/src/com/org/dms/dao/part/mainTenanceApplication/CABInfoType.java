package com.org.dms.dao.part.mainTenanceApplication;

import java.util.Map;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: PartInfoType 驾驶室本体录入
 * date: 2014年10月12日 上午11:53:45
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class CABInfoType implements ApplicationType {
	private DBFactory factory;
	
	public void setFactory(DBFactory factory) {
		this.factory = factory;
	}
	public static final ApplicationType getInstance(ActionContext ac){
		CABInfoType dao = new CABInfoType();
		dao.setFactory(ac.getDBFactory());
		return dao;
	}

	@Override
	public void save(Map<String,String> hm) throws Exception {
		String APPLICATION_PERSON_A = hm.get("APPLICATION_PERSON_A");						// 申请人
		String APPLICATION_WORK_A = hm.get("APPLICATION_WORK_A");							// 申请单位
		String APPLICATION_INFOMATION_A = hm.get("APPLICATION_INFOMATION_A");				// 申请人联系方式
		String APPLICATION_TYPE_A = hm.get("APPLICATION_TYPE_A");							// 申请单类型
		String[] CAB_NO_S_A = hm.get("CAB_NO_S_A").split(",");								// 驾驶本体编号
		String[] CAB_DESCRIPTION_S_A = hm.get("CAB_DESCRIPTION_S_A").split(",");			// 驾驶室本体状态描述
		String[] VIN_S = hm.get("VIN_S").split(",");										// VIN
		String USER_ACCOUNT = hm.get("USER_ACCOUNT");										// 操作人员账号
		String ORG_ID = hm.get("ORG_ID");													// 操作人员渠道ID
		String ORG_CODE = hm.get("ORG_CODE");										 		// 操作人员渠道CODE
		String editAction = hm.get("EDIT_ACTION") == null ? "" : hm.get("EDIT_ACTION"); 	// 获取修改动作参数
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
			this.factory.update("DELETE PT_BU_CAB_INFO_ENTRY WHERE APPLICATION_ID = ?", new Object[]{applicationId}); // 删除子表信息
			
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
		
		// 插入零件编号录入子表
		String partInfoSql = 
							"INSERT INTO PT_BU_CAB_INFO_ENTRY\n" +
							"(\n" + 
							"       ENTRY_ID,    APPLICATION_ID, CAB_NO,      CAB_DESCRIPTION,\n" + 
							"       VIN,         CREATE_USER,    CREATE_TIME\n" + 
							")\n" + 
							"VALUES\n" + 
							"(\n" + 
							"       F_GETID(),  ?,               ?,           ?,\n" + 
							"       ?,          ?,               SYSDATE\n" + 
							")";
		
		for(int i = 0; i < CAB_NO_S_A.length; i++){
			String cabNo = CAB_NO_S_A[i];
			String cabDesc = CAB_DESCRIPTION_S_A[i];
			String vin = VIN_S[i];
			this.factory.update(partInfoSql, new String[]{
					applicationId, cabNo, cabDesc,
				vin, USER_ACCOUNT
			});
		}
		
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
		String[] ENTRY_ID_S_A = hm.get("ENTRY_ID_S_A").split(",");							// 配件信息录入子表ID
		String[] REMARK_S_A = hm.get("REMARK_S_A").split(",");						// 技术科科长意见
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
		
		// 根据状态判断更新子表信息:只有审核通过了才更新子表信息
		if(DicConstant.PJWHSQZT_03.equals(APPLICATION_STATUS_A)){
			updataStr = "UPDATE PT_BU_CAB_INFO_ENTRY\n" +
							"   SET REMARK = ?,\n" + 
							"       UPDATE_USER = ?,\n" + 
							"       UPDATE_TIME = SYSDATE\n" + 
							" WHERE ENTRY_ID = ?";
			for(int i = 0 ; i < ENTRY_ID_S_A.length; i++){
				String entryId = ENTRY_ID_S_A[i];
				this.factory.update(updataStr, new Object[]{
						REMARK_S_A[i], USER_ACCOUNT, entryId
				});
			}
					
		}
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
		// 暂不需要实现
		
	}
	@Override
	public void pdTempSave(Map<String, String> hm) throws Exception {
		// 暂不需要实现
		
	}

}

package com.org.dms.dao.part.mainTenanceApplication;

import java.util.Map;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: PartInfoType 配件信息变更
 * date: 2014年10月12日 上午11:53:45
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class PartInfoChangeType implements ApplicationType {
	private DBFactory factory;
	
	public void setFactory(DBFactory factory) {
		this.factory = factory;
	}	
	public static final ApplicationType getInstance(ActionContext ac){
		PartInfoChangeType dao = new PartInfoChangeType();
		dao.setFactory(ac.getDBFactory());
		return dao;
	}

	@Override
	public void save(Map<String,String> hm) throws Exception {
		String APPLICATION_PERSON_A = hm.get("APPLICATION_PERSON_A");						// 申请人
		String APPLICATION_WORK_A = hm.get("APPLICATION_WORK_A");							// 申请单位
		String APPLICATION_INFOMATION_A = hm.get("APPLICATION_INFOMATION_A");				// 申请人联系方式
		String APPLICATION_TYPE_A = hm.get("APPLICATION_TYPE_A");							// 申请单类型
		String[] OLD_PART_NO_A = hm.get("OLD_PART_NO_A").split(",");					    // 原零件编号
		String[] OLD_PART_NAME_A = hm.get("OLD_PART_NAME_A").split(",");					// 原零件名称
		
		// 车辆识别码: 这里为String添加" "，目的是为了排除数组越界的问题，因为这些可能是选填项，如果最后一项为空则有可能数组长度与必填项的长度不一致
		String vinStr = hm.get("VIN_A") + " ";
		String[] VIN_A = vinStr.split(",");										// 车辆识别码
		
		// 更新编号
		String newPartNoStr = hm.get("NEW_PART_NO_A") + " ";
		String[] NEW_PART_NO_A = newPartNoStr.split(",");
		
		// 更新名称
		String newPartNameStr = hm.get("NEW_PART_NAME_A") + " ";
		String[] NEW_PART_NAME_A = newPartNameStr.split(",");
		String[] REMARK_A = hm.get("REMARK_A").split(",");									// 更新（禁用）原因
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
			this.factory.update("DELETE PT_BU_PART_INFO_CHANGE_ENTRY WHERE APPLICATION_ID = ?", new Object[]{applicationId}); // 删除子表信息
			
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
							"INSERT INTO PT_BU_PART_INFO_CHANGE_ENTRY\n" +
							"(\n" + 
							"       ENTRY_ID,    APPLICATION_ID, OLD_PART_NO, OLD_PART_NAME, VIN,\n" + 
							"       NEW_PART_NO, NEW_PART_NAME,  REMARK,      CREATE_USER,\n" + 
							"       CREATE_TIME\n" + 
							")\n" + 
							"VALUES\n" + 
							"(\n" + 
							"       F_GETID(),   ?,              ?,           ?,    ?,\n" + 
							"       ?,           ?,              ?,           ?,\n" + 
							"       SYSDATE\n" + 
							")";
		for(int i = 0; i < OLD_PART_NO_A.length; i++){
			this.factory.update(partInfoSql, new String[]{
					applicationId, OLD_PART_NO_A[i], OLD_PART_NAME_A[i], VIN_A[i] == null ? "" : VIN_A[i].trim(),
					NEW_PART_NO_A[i] == null ? "" : NEW_PART_NO_A[i].trim(), NEW_PART_NAME_A[i] == null ? "" : NEW_PART_NAME_A[i].trim(), REMARK_A[i], USER_ACCOUNT
			});
		}
		
	}

	/**
	 * 技术科审核
	 */
	@Override
	public void edAudit(Map<String, String> hm) throws Exception {
		String APPLICATION_ID_A = hm.get("APPLICATION_ID_A");												// 申请单ID
		String ENGINEERING_DEPARTMENT_A = hm.get("ENGINEERING_DEPARTMENT_A");								// 技术科审批人
		String APPLICATION_STATUS_A = hm.get("APPLICATION_STATUS_A");										// 审批状态
		String ENGINEERING_DEPARTMENT_REMARK_A = hm.get("ENGINEERING_DEPARTMENT_REMARK_A");					// 技术科审批备注
		String[] ENTRY_ID_S_A = hm.get("ENTRY_ID_S_A").split(",");											// 配件信息录入子表ID
		String[] ENGINEERING_DEPARTMENT_REMARK_S_A = hm.get("ENGINEERING_DEPARTMENT_REMARK_S_A").split(",");// 技术科意见
		String USER_ACCOUNT = hm.get("USER_ACCOUNT");														// 操作人员账号
		
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
			updataStr = "UPDATE PT_BU_PART_INFO_CHANGE_ENTRY\n" +
							"   SET ENGINEERING_DEPARTMENT_REMARK = ?,\n" + 
							"       UPDATE_USER = ?,\n" + 
							"       UPDATE_TIME = SYSDATE\n" + 
							" WHERE ENTRY_ID = ?";
			for(int i = 0 ; i < ENTRY_ID_S_A.length; i++){
				String entryId = ENTRY_ID_S_A[i];
				this.factory.update(updataStr, new Object[]{
						ENGINEERING_DEPARTMENT_REMARK_S_A[i], USER_ACCOUNT, entryId
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
		String[] ENTRY_ID_S_A = hm.get("ENTRY_ID_S_A").split(",");							// 配件信息录入子表ID
		String[] PURCHASING_DEPARTMENT_REMARK_S_A = hm.get("PURCHASING_DEPARTMENT_REMARK_S_A").split(",");				// 采购价（不含税）
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
		
		// 根据状态判断更新子表信息:只有审核通过了才更新子表信息
		if(DicConstant.PJWHSQZT_05.equals(APPLICATION_STATUS_A)){
			updataStr = "UPDATE PT_BU_PART_INFO_CHANGE_ENTRY\n" +
					"   SET PURCHASING_DEPARTMENT_REMARK = ?,\n" + 
					"       UPDATE_USER = ?,\n" + 
					"       UPDATE_TIME = SYSDATE\n" + 
					" WHERE ENTRY_ID = ?";
			for(int i = 0 ; i < ENTRY_ID_S_A.length; i++){
				String entryId = ENTRY_ID_S_A[i];
				this.factory.update(updataStr, new Object[]{
						PURCHASING_DEPARTMENT_REMARK_S_A[i], USER_ACCOUNT, entryId
				});
			}
		}
		
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

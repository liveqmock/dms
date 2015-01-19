package com.org.dms.dao.part.mainTenanceApplication;

import java.util.Map;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.mvc.context.ActionContext;

/**
 * 
 * @ClassName: SupplierChangeType
 * @Description: 供应商信息变更
 * @author: ALONY
 * @date: 2014年12月8日 下午3:30:57
 */
public class SupplierChangeType implements ApplicationType {
	private DBFactory factory;
	
	public void setFactory(DBFactory factory) {
		this.factory = factory;
	}
	public static final ApplicationType getInstance(ActionContext ac){
		SupplierChangeType dao = new SupplierChangeType();
		dao.setFactory(ac.getDBFactory());
		return dao;
	}

	@Override
	public void save(Map<String,String> hm) throws Exception {
		String APPLICATION_PERSON_A = hm.get("APPLICATION_PERSON_A");						// 申请人
		String APPLICATION_WORK_A = hm.get("APPLICATION_WORK_A");							// 申请单位
		String APPLICATION_INFOMATION_A = hm.get("APPLICATION_INFOMATION_A");				// 申请人联系方式
		String APPLICATION_TYPE_A = hm.get("APPLICATION_TYPE_A");							// 申请单类型
		
		
		String[] PART_NO_A = hm.get("PART_NO_A").split(",");					    		// 零件编号
		String[] PART_NAME_A = hm.get("PART_NAME_A").split(",");					    	// 零件名称
		String[] OLD_SUPPLIER_A = hm.get("OLD_SUPPLIER_A").split(",");					    // 原供应商
		String[] REMARK_A = hm.get("REMARK_A").split(",");					    			// 更新（禁用）原因
		String[] OLD_SUPPLIER_REMARK_A = hm.get("OLD_SUPPLIER_REMARK_A").split(",");		// 原厂家采购主管意见
		String[] NEW_SUPPLIER_REMARK_A = hm.get("NEW_SUPPLIER_REMARK_A").split(",");		// 新厂家采购主管意见
		
		// 新供应商
		String newSupplierStr = hm.get("NEW_SUPPLIER_A");
		if(newSupplierStr.indexOf(",") == newSupplierStr.length() - 1){
			newSupplierStr += " ";
		}
		String[] NEW_SUPPLIER_A = newSupplierStr.split(",");
		String USER_ACCOUNT = hm.get("USER_ACCOUNT");										// 操作人员账号
		String ORG_ID = hm.get("ORG_ID");													// 操作人员渠道ID
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
			this.factory.update("DELETE PT_BU_SUPPLIER_CHANGE_ENTRY WHERE APPLICATION_ID = ?", new Object[]{applicationId}); // 删除子表信息
			
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
		// 插入供应商信息录入子表
		String supplierChangeStr = 
							"INSERT INTO PT_BU_SUPPLIER_CHANGE_ENTRY\n" +
							"(\n" + 
							"       ENTRY_ID,            APPLICATION_ID, PART_NO,    PART_NAME,\n" + 
							"       OLD_SUPPLIER,        NEW_SUPPLIER,   REMARK,     OLD_SUPPLIER_REMARK,\n" + 
							"       NEW_SUPPLIER_REMARK, CREATE_USER,    CREATE_TIME\n" + 
							")\n" + 
							"VALUES\n" + 
							"(\n" + 
							"       F_GETID(),           ?,              ?,          ?,\n" + 
							"       ?,                   ?,              ?,          ?,\n" + 
							"       ?,                   ?,              SYSDATE\n" + 
							")";
		
		for(int i = 0; i < PART_NO_A.length; i++){
			String partNo = PART_NO_A[i];
			String partName = PART_NAME_A[i];
			String oldSupplier = OLD_SUPPLIER_A[i];
			String newSupplier = NEW_SUPPLIER_A[i] == null ? "" : NEW_SUPPLIER_A[i].trim();
			String remark = REMARK_A[i];
			String oldSupplierRemark = OLD_SUPPLIER_REMARK_A[i];
			String newSupplierRemark = NEW_SUPPLIER_REMARK_A[i];
			
			this.factory.update(supplierChangeStr, new String[]{
					applicationId, partNo, partName,
					oldSupplier, newSupplier, remark, oldSupplierRemark,
					newSupplierRemark, USER_ACCOUNT
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
		// 暂不需要实现
		
	}
	@Override
	public void pdTempSave(Map<String, String> hm) throws Exception {
		// 暂不需要实现
		
	}

}

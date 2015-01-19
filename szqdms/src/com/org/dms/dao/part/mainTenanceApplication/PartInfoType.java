package com.org.dms.dao.part.mainTenanceApplication;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: PartInfoType  配件编号录入审批单
 * date: 2014年10月12日 上午11:53:45
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class PartInfoType implements ApplicationType {
	private DBFactory factory;
	
	public void setFactory(DBFactory factory) {
		this.factory = factory;
	}

	public static final ApplicationType getInstance(ActionContext ac){
		PartInfoType dao = new PartInfoType();
		dao.setFactory(ac.getDBFactory());
		return dao;
	}
	
	@Override
	public void save(Map<String,String> hm) throws Exception {
		String APPLICATION_PERSON_A = hm.get("APPLICATION_PERSON_A");					// 申请人
		String APPLICATION_WORK_A = hm.get("APPLICATION_WORK_A");						// 申请单位
		String APPLICATION_INFOMATION_A = hm.get("APPLICATION_INFOMATION_A");			// 申请人联系方式
		String APPLICATION_TYPE_A = hm.get("APPLICATION_TYPE_A");						// 申请单类型
		String[] PART_NO_S_A = hm.get("PART_NO_S_A").split(",");						// 配件编号
		String[] PART_NAME_S_A = hm.get("PART_NAME_S_A").split(",");					// 配件名称
		String[] VIN_S_A = hm.get("VIN_S_A").split(",");								// VIN
		String USER_ACCOUNT = hm.get("USER_ACCOUNT");									// 操作人员账号
		String ORG_ID = hm.get("ORG_ID");										 		// 操作人员渠道ID
		String ORG_CODE = hm.get("ORG_CODE");										 	// 操作人员渠道CODE
		String editAction = hm.get("EDIT_ACTION") == null ? "" : hm.get("EDIT_ACTION"); // 获取修改动作参数
		String[] ENTRY_ID_S_A = (hm.get("ENTRY_ID_S_A") == null ? "" : hm.get("ENTRY_ID_S_A")).split(",");							// 配件信息录入子表ID
		String applicationId = "";
		if(editAction.equals("YES")){
			applicationId = hm.get("APPLICATION_ID_A") == null ? "" : hm.get("APPLICATION_ID_A");			          // 申请单ID
			String updateStr = 
					"UPDATE PT_BU_MAINTENANCE_APPLICATION\n" +
							"   SET APPLICATION_PERSON = ?,\n" + 
							"       APPLICATION_WORK = ?,\n" + 
							"       APPLICATION_INFOMATION = ?,\n" + 
							"       UPDATE_USER = ?,\n" + 
							"       UPDATE_TIME = SYSDATE\n" + 
							" WHERE APPLICATION_ID = ?"; 															   // 修改申请单信息
			this.factory.update(updateStr, new Object[]{ APPLICATION_PERSON_A, APPLICATION_WORK_A, APPLICATION_INFOMATION_A, USER_ACCOUNT, applicationId});
			// this.factory.update("UPDATE PT_BU_PART_INFO_ENTRY SET STATUS = ? WHERE APPLICATION_ID = ?", new Object[]{DicConstant.YXBS_02, applicationId}); // 将子表信息至为无效
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
			this.factory.update(appplicateInfoSql, new Object[]{
					applicationId, APPLICATION_TYPE_A, ORG_CODE, DicConstant.PJWHSQZT_01,
					"", APPLICATION_PERSON_A, APPLICATION_WORK_A, APPLICATION_INFOMATION_A,
					ORG_ID, USER_ACCOUNT, DicConstant.YXBS_01
			});																						// 插入申请单主表内容
		}
		String partInfoSql = 
					"INSERT INTO PT_BU_PART_INFO_ENTRY\n" +
					"(\n" + 
					"  ENTRY_ID, APPLICATION_ID, PART_NO, PART_NAME, VIN, CREATE_USER, CREATE_TIME\n" + 
					")\n" + 
					"VALUES\n" + 
					"(\n" + 
					"  F_GETID(), ?, ?, ?, ?, ?, SYSDATE\n" + 
					")";																			// 插入零件编号录入子表
		String updateStr = 
				"UPDATE PT_BU_PART_INFO_ENTRY T\n" +
						"   SET T.PART_NO     = ?,\n" + 
						"       T.PART_NAME   = ?,\n" + 
						"       T.VIN         = ?,\n" + 
						"       T.UPDATE_USER = ?,\n" + 
						"       T.UPDATE_TIME = SYSDATE WHERE T.ENTRY_ID = ?";						// 更新子表信息
		
		// 判断是更新还是新增
		if(editAction.equals("YES")){ // 更新
			
			// 获取原始的配件子信息并遍历，如果旧配件在新提报的配件信息中不存在，则删除
			String[][] oldApplicationPartNo = this.getPartInfoArray(applicationId);
			List<String> newPartList = Arrays.asList(PART_NO_S_A);
			for(String[] tempPartNo: oldApplicationPartNo){
				if(!newPartList.contains(tempPartNo[0])){
					this.deletePartInfo(applicationId, tempPartNo[0]);
				}
			}
			
			for(int i = 0; i < PART_NO_S_A.length; i++){
				
				// 如果entryid为空，则表示为新添加的配件
				if(ENTRY_ID_S_A[i].equals("null")){
					this.factory.update(partInfoSql, new String[]{
							applicationId, PART_NO_S_A[i], PART_NAME_S_A[i], ("null".equals(VIN_S_A[i]) ? "" : VIN_S_A[i]), USER_ACCOUNT
					});
				} else {
					this.factory.update(updateStr,new String[]{PART_NO_S_A[i], PART_NAME_S_A[i], ("null".equals(VIN_S_A[i]) ? "" : VIN_S_A[i]), USER_ACCOUNT, ENTRY_ID_S_A[i]});
				}
			}
		}else{
			for(int i = 0; i < PART_NO_S_A.length; i++){
				this.factory.update(partInfoSql, new String[]{
						applicationId, PART_NO_S_A[i], PART_NAME_S_A[i], ("null".equals(VIN_S_A[i]) ? "" : VIN_S_A[i]), USER_ACCOUNT
				});
			}
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
		String[] ENTRY_ID_S_A = hm.get("ENTRY_ID_S_A") != null ? hm.get("ENTRY_ID_S_A").split(",") : new String[0];							// 配件信息录入子表ID
		String[] PROCESS_ROUTE_S_A = hm.get("PROCESS_ROUTE_S_A") != null ? hm.get("PROCESS_ROUTE_S_A").split(",") : new String[0];					// 工艺路线
		String[] OWN_FIRST_LEVEL_S_A = hm.get("OWN_FIRST_LEVEL_S_A") != null ? hm.get("OWN_FIRST_LEVEL_S_A").split(",") : new String[0];				// 所属一级
		String[] OWN_SECOND_LEVEL_A = hm.get("OWN_SECOND_LEVEL_A") != null ? hm.get("OWN_SECOND_LEVEL_A").split(",") : new String[0];					// 所属二级
		String[] ENGINEERING_DEPARTMENT_REMARK_DA = (hm.get("ENGINEERING_DEPARTMENT_REMARK_DA") + " ").split(",");				// 技术科审核备注
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
			updataStr = "UPDATE PT_BU_PART_INFO_ENTRY\n" +
							"   SET PROCESS_ROUTE = ?,\n" + 
							"       OWN_FIRST_LEVEL = ?,\n" + 
							"       OWN_SECOND_LEVEL = ?,\n" + 
							"       ENGINEERING_DEPARTMENT = ?,\n" + 
							"       ENGINEERING_DEPARTMENT_DATE = SYSDATE,\n" + 
							"       ENGINEERING_DEPARTMENT_REMARK = ?,\n" + 
							"       UPDATE_USER = ?,\n" + 
							"       UPDATE_TIME = SYSDATE\n" + 
							" WHERE ENTRY_ID = ?";
			for(int i = 0 ; i < ENTRY_ID_S_A.length; i++){
				String entryId = ENTRY_ID_S_A[i];
				this.factory.update(updataStr, new Object[]{
						PROCESS_ROUTE_S_A[i], OWN_FIRST_LEVEL_S_A[i], "null".equals(OWN_SECOND_LEVEL_A[i]) ? "" : OWN_SECOND_LEVEL_A[i], 
						ENGINEERING_DEPARTMENT_A,
						ENGINEERING_DEPARTMENT_REMARK_DA[i],
						USER_ACCOUNT, entryId
				});
			}
					
		} else { // 审核未通过更新子表的审核信息
			updataStr = "UPDATE PT_BU_PART_INFO_ENTRY\n" +
					"   SET ENGINEERING_DEPARTMENT = ?,\n" + 
					"       ENGINEERING_DEPARTMENT_DATE = SYSDATE,\n" + 
					"       ENGINEERING_DEPARTMENT_REMARK = ?,\n" + 
					"       UPDATE_USER = ?,\n" + 
					"       UPDATE_TIME = SYSDATE\n" + 
					" WHERE ENTRY_ID = ?";
			for(int i = 0 ; i < ENTRY_ID_S_A.length; i++){
				String entryId = ENTRY_ID_S_A[i];
				this.factory.update(updataStr, new Object[]{
						ENGINEERING_DEPARTMENT_A,
						ENGINEERING_DEPARTMENT_REMARK_DA[i],
						USER_ACCOUNT, entryId
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
		String[] ENTRY_ID_S_A = hm.get("ENTRY_ID_S_A") != null ? hm.get("ENTRY_ID_S_A").split(",") : new String[0];								// 配件信息录入子表ID
		String[] SUPPLIER_ID_S_A = hm.get("SUPPLIER_ID_S_A") != null ? hm.get("SUPPLIER_ID_S_A").split(",") : new String[0];					// 供应商ID
		String[] SUPPLIER_NAME_S_A = hm.get("SUPPLIER_NAME_S_A") != null ? hm.get("SUPPLIER_NAME_S_A").split(",") : new String[0];				// 供应商名称
		String USER_ACCOUNT = hm.get("USER_ACCOUNT");										// 操作人员账号
		String[] PURCHASING_DEPARTMENT_REMARK_DA = (hm.get("PURCHASING_DEPARTMENT_REMARK_DA") + " ").split(",");				// 采供科审核备注
		
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
			updataStr = "UPDATE PT_BU_PART_INFO_ENTRY\n" +
							"   SET SUPPLIER_ID = ?,\n" + 
							"       SUPPLIER_NAME = ?,\n" + 
							"       PURCHASING_DEPARTMENT = ?,\n" + 
							"       PURCHASING_DEPARTMENT_DATE = SYSDATE,\n" + 
							"       PURCHASING_DEPARTMENT_REMARK = ?,\n" + 
							"       UPDATE_USER = ?,\n" + 
							"       UPDATE_TIME = SYSDATE\n" + 
							" WHERE ENTRY_ID = ?";
			for(int i = 0 ; i < ENTRY_ID_S_A.length; i++){
				String entryId = ENTRY_ID_S_A[i];
				this.factory.update(updataStr, new Object[]{
						SUPPLIER_ID_S_A[i], SUPPLIER_NAME_S_A[i], 
						PURCHASING_DEPARTMENT_A,
						PURCHASING_DEPARTMENT_REMARK_DA[i],
						USER_ACCOUNT, entryId
				});
			}
					
		} else { // 审核未通过，更新审核备注
			updataStr = "UPDATE PT_BU_PART_INFO_ENTRY\n" +
					"   SET PURCHASING_DEPARTMENT = ?,\n" + 
					"       PURCHASING_DEPARTMENT_DATE = SYSDATE,\n" + 
					"       PURCHASING_DEPARTMENT_REMARK = ?,\n" + 
					"       UPDATE_USER = ?,\n" + 
					"       UPDATE_TIME = SYSDATE\n" + 
					" WHERE ENTRY_ID = ?";
			for(int i = 0 ; i < ENTRY_ID_S_A.length; i++){
				String entryId = ENTRY_ID_S_A[i];
				this.factory.update(updataStr, new Object[]{
						PURCHASING_DEPARTMENT_A,
						PURCHASING_DEPARTMENT_REMARK_DA[i],
						USER_ACCOUNT, entryId
				});
			}
		}
	}

	/**
	 * 
	 * @Title: edTempSave
	 * @Description: 技术科审核信息暂存
	 * @param hm
	 * @throws Exception
	 * @see com.org.dms.dao.part.mainTenanceApplication.ApplicationType#edTempSave(java.util.Map)
	 */
	@Override
	public void edTempSave(Map<String, String> hm) throws Exception {
		String APPLICATION_ID_A = hm.get("APPLICATION_ID_A");								// 申请单ID
		String ENGINEERING_DEPARTMENT_A = hm.get("ENGINEERING_DEPARTMENT_A");				// 技术科审批人
		String ENGINEERING_DEPARTMENT_REMARK_A = hm.get("ENGINEERING_DEPARTMENT_REMARK_A");	// 技术科审批备注
		String[] ENTRY_ID_S_A = hm.get("ENTRY_ID_S_A").split(",");							// 配件信息录入子表ID
		String[] PROCESS_ROUTE_S_A = (hm.get("PROCESS_ROUTE_S_A") == null ? "" : hm.get("PROCESS_ROUTE_S_A") + " ").split(",");		// 工艺路线
		String[] OWN_FIRST_LEVEL_S_A = (hm.get("OWN_FIRST_LEVEL_S_A") == null ? "" : hm.get("OWN_FIRST_LEVEL_S_A") + " ").split(",");	// 所属一级
		String[] OWN_SECOND_LEVEL_A = (hm.get("OWN_SECOND_LEVEL_A") == null ? "" : hm.get("OWN_SECOND_LEVEL_A") + " ").split(",");		// 所属二级
		String[] ENGINEERING_DEPARTMENT_REMARK_DA = (hm.get("ENGINEERING_DEPARTMENT_REMARK_DA") == null ? "" : hm.get("ENGINEERING_DEPARTMENT_REMARK_DA") + " ").split(",");				// 技术科审核备注
		String USER_ACCOUNT = hm.get("USER_ACCOUNT");										// 操作人员账号
		

		// 更新主表信息
		String updataStr = 
							"UPDATE PT_BU_MAINTENANCE_APPLICATION\n" +
									"   SET ENGINEERING_DEPARTMENT = ?,\n" + 
									"       ENGINEERING_DEPARTMENT_DATE = SYSDATE,\n" + 
									"       ENGINEERING_DEPARTMENT_REMARK = ?,\n" + 
									"       UPDATE_USER = ?,\n" + 
									"       UPDATE_TIME = SYSDATE\n" + 
									" WHERE APPLICATION_ID = ?";
		this.factory.update(updataStr, new Object[]{
				ENGINEERING_DEPARTMENT_A, ENGINEERING_DEPARTMENT_REMARK_A, USER_ACCOUNT, APPLICATION_ID_A
		});
		
		// 根据状态判断更新子表信息:只有审核通过了才更新子表信息
		updataStr = "UPDATE PT_BU_PART_INFO_ENTRY\n" +
						"   SET PROCESS_ROUTE = ?,\n" + 
						"       OWN_FIRST_LEVEL = ?,\n" + 
						"       OWN_SECOND_LEVEL = ?,\n" + 
						"       ENGINEERING_DEPARTMENT = ?,\n" + 
						"       ENGINEERING_DEPARTMENT_DATE = SYSDATE,\n" + 
						"       ENGINEERING_DEPARTMENT_REMARK = ?,\n" + 
						"       UPDATE_USER = ?,\n" + 
						"       UPDATE_TIME = SYSDATE\n" + 
						" WHERE ENTRY_ID = ?";
		for(int i = 0 ; i < ENTRY_ID_S_A.length; i++){
			this.factory.update(updataStr, new Object[]{
					PROCESS_ROUTE_S_A[i].trim(), OWN_FIRST_LEVEL_S_A[i].trim(), 
					"null".equals(OWN_SECOND_LEVEL_A[i].trim()) ? "" : OWN_SECOND_LEVEL_A[i], 
					ENGINEERING_DEPARTMENT_A.trim(),
					"null".equals(ENGINEERING_DEPARTMENT_REMARK_DA[i].trim()) ? "" : ENGINEERING_DEPARTMENT_REMARK_DA[i].trim(),
					USER_ACCOUNT, ENTRY_ID_S_A[i]
			});
		}
					
	}

	/**
	 * 
	 * @Title: pdTempSave
	 * @Description: 采供科审核信息暂存
	 * @param hm
	 * @throws Exception
	 * @see com.org.dms.dao.part.mainTenanceApplication.ApplicationType#pdTempSave(java.util.Map)
	 */
	@Override
	public void pdTempSave(Map<String, String> hm) throws Exception {
		String APPLICATION_ID_A = hm.get("APPLICATION_ID_A");								// 申请单ID
		String PURCHASING_DEPARTMENT_A = hm.get("PURCHASING_DEPARTMENT_A");					// 采供科审核人
		String PURCHASING_DEPARTMENT_REMARK_A = hm.get("PURCHASING_DEPARTMENT_REMARK_A");	// 采供科审批备注
		String[] ENTRY_ID_S_A = hm.get("ENTRY_ID_S_A").split(",");							// 配件信息录入子表ID
		String[] SUPPLIER_ID_S_A = (hm.get("SUPPLIER_ID_S_A") == null ? "" : hm.get("SUPPLIER_ID_S_A") + " ").split(",");			// 供应商ID
		String[] SUPPLIER_NAME_S_A = (hm.get("SUPPLIER_NAME_S_A") == null ? "" : hm.get("SUPPLIER_NAME_S_A") + " ").split(",");		// 供应商名称
		String USER_ACCOUNT = hm.get("USER_ACCOUNT");										// 操作人员账号
		String[] PURCHASING_DEPARTMENT_REMARK_DA = (hm.get("PURCHASING_DEPARTMENT_REMARK_DA") == null ? "" : hm.get("SUPPLIER_NAME_S_A") + " ").split(",");				// 采供科审核备注
		
		// 更新主表信息
		String updataStr = 
							"UPDATE PT_BU_MAINTENANCE_APPLICATION\n" +
									"   SET PURCHASING_DEPARTMENT = ?,\n" + 
									"       PURCHASING_DEPARTMENT_DATE = SYSDATE,\n" + 
									"       PURCHASING_DEPARTMENT_REMARK = ?,\n" + 
									"       UPDATE_USER = ?,\n" + 
									"       UPDATE_TIME = SYSDATE\n" + 
									" WHERE APPLICATION_ID = ?";
		this.factory.update(updataStr, new Object[]{
				PURCHASING_DEPARTMENT_A, PURCHASING_DEPARTMENT_REMARK_A, USER_ACCOUNT, APPLICATION_ID_A
		});
		

		updataStr = "UPDATE PT_BU_PART_INFO_ENTRY\n" +
						"   SET SUPPLIER_ID = ?,\n" + 
						"       SUPPLIER_NAME = ?,\n" + 
						"       PURCHASING_DEPARTMENT = ?,\n" + 
						"       PURCHASING_DEPARTMENT_DATE = SYSDATE,\n" + 
						"       PURCHASING_DEPARTMENT_REMARK = ?,\n" + 
						"       UPDATE_USER = ?,\n" + 
						"       UPDATE_TIME = SYSDATE\n" + 
						" WHERE ENTRY_ID = ?";
		for(int i = 0 ; i < ENTRY_ID_S_A.length; i++){
			this.factory.update(updataStr, new Object[]{
					SUPPLIER_ID_S_A[i].trim(), 
					SUPPLIER_NAME_S_A[i].trim(), 
					PURCHASING_DEPARTMENT_A.trim(),
					"null".equals(PURCHASING_DEPARTMENT_REMARK_DA[i].trim()) ? "" : PURCHASING_DEPARTMENT_REMARK_DA[i].trim(),
					USER_ACCOUNT, 
					ENTRY_ID_S_A[i]
			});
		}
					
	
	}

	
	/**
	 * 
	 * @Title: getPartInfoArray
	 * @Description: 根据申请单ID获取申请单下的配件信息
	 * @param applicationId
	 * @return
	 * @throws Exception
	 * @return: String[][]
	 */
	public String[][] getPartInfoArray(String applicationId) throws Exception{
		return this.factory.select("SELECT PART_NO FROM PT_BU_PART_INFO_ENTRY t WHERE t.application_id = ?", new String[]{applicationId});
	}
	
	/**
	 * 
	 * @Title: deletePartInfo
	 * @Description: 根据申请单号，配件号删除一条子信息
	 * @param applicationId
	 * @param partNo
	 * @throws Exception
	 * @return: void
	 */
	public void deletePartInfo(String applicationId, String partNo) throws Exception{
		this.factory.update("DELETE PT_BU_PART_INFO_ENTRY T WHERE T.APPLICATION_ID = ? AND TRIM(T.PART_NO) = ?", new String[]{applicationId, partNo});
	}
}

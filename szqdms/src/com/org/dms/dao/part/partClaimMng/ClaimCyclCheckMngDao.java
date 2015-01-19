package com.org.dms.dao.part.partClaimMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuClaimApplyLogVO;
import com.org.dms.vo.part.PtBuClaimApplyVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 配件三包验证Dao
 *
 * 配件三包验证的增删改查
 * @author zhengyao
 * @date 2014-08-15
 * @version 1.0
 */
public class ClaimCyclCheckMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return ClaimCyclSetMngDao 配件三包验证Dao
     */
    public static final ClaimCyclCheckMngDao getInstance(ActionContext pActionContext) {

        ClaimCyclCheckMngDao claimCyclCheckMngDao = new ClaimCyclCheckMngDao();
        pActionContext.setDBFactory(claimCyclCheckMngDao.factory);
        return claimCyclCheckMngDao;
    }

    /**
     * 配件三包申请日志表(PT_BU_CLAIM_APPLY_LOG)添加
     *
     * @param pPtBuClaimApplyLogVO 配件三包期申请日志实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertClaimCyclApplyLog(PtBuClaimApplyLogVO pPtBuClaimApplyLogVO) throws Exception {

        return factory.insert(pPtBuClaimApplyLogVO);
    }

    /**
     * 配件三包申请表(PT_BU_CLAIM_APPLY)修改
     * @param pPtBuClaimApplyVO 配件三包申请实体
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean updateClaimCyclApply(PtBuClaimApplyVO pPtBuClaimApplyVO,User pUser) throws Exception {

    	String status = pPtBuClaimApplyVO.getApplyStatus();
    	if(status.equals(DicConstant.PJSBSPZT_04)){
    		 // 修改配件三包申请表sql
            String sql = " UPDATE \n"
                       + "     PT_BU_CLAIM_APPLY \n"
                       + " SET \n"
                       + "     APPLY_STATUS='" + pPtBuClaimApplyVO.getApplyStatus() + "',\n"
                       + "     SOURCE_IN_ID='" + pPtBuClaimApplyVO.getSourceInId() + "',\n"
                       + "     SOURCE_IN_NO='" + pPtBuClaimApplyVO.getSourceInNo() + "',\n"
                       + "     UPDATE_USER='" + pUser.getAccount() + "',"
                       + "     UPDATE_TIME=sysdate"
                       + " WHERE \n"
                       + "     APPLY_ID='" + pPtBuClaimApplyVO.getApplyId() + "'";
            			return factory.delete(sql, null);
    	}else{
    		String sql = " UPDATE \n"
                    + "     PT_BU_CLAIM_APPLY \n"
                    + " SET \n"
                    + "     APPLY_STATUS='" + pPtBuClaimApplyVO.getApplyStatus() + "',\n"
                    + "     UPDATE_USER='" + pUser.getAccount() + "',"
                    + "     UPDATE_TIME=sysdate"
                    + " WHERE \n"
                    + "     APPLY_ID='" + pPtBuClaimApplyVO.getApplyId() + "'";
         			return factory.delete(sql, null);
    	}
       
        
    }

    /**
     * 配件三包申请表(PT_BU_CLAIM_APPLY)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchClaimCyclApply(PageManager pPageManager, User pUser, String pConditions,String orgString) throws Exception {
    	String orgType = pUser.getOrgDept().getOrgType();
        String wheres = pConditions;
        // 按所属机构查询，并且按配件代码升序排列
        wheres += " AND  T.APPLY_STATUS=" + orgString +"  AND T.STATUS = " + DicConstant.YXBS_01 + " AND T.ORG_CODE = T1.CODE\n";
        if(DicConstant.ZZLB_09.equals(orgType)){
        	wheres +=" AND T.ORG_CODE = T1.CODE AND (SELECT WAREHOUSE_ID FROM PT_BU_SALE_ORDER WHERE ORDER_NO = IN_ORDER_NO) = "+pUser.getOrgId();
        }
        wheres += " ORDER BY T.PART_CODE";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT\n" );
        sql.append("     T.ORG_CODE,\n" );
        sql.append("     T.ORG_NAME,\n" );
        sql.append("     T.APPLY_ID,\n" );
        sql.append("     T.APPLY_NO,\n" );
        sql.append("     T.IN_ORDER_NO,\n" );
        sql.append("     T.OUT_ORDER_NO,\n" );
        sql.append("     T.OUT_DATE,\n" );
        sql.append("     T.PHONE,\n" );
        sql.append("     T.UNIT,\n" );
        sql.append("     T.SALE_PRICE,\n" );
        sql.append("     T.AMOUNT,\n" );
        sql.append("     T.OUT_COUNT,\n" );
        sql.append("     T.SUPPLIER_ID,\n" );
        sql.append("     T.SUPPLIER_CODE,\n" );
        sql.append("     T.SUPPLIER_NAME,\n" );
        sql.append("     T.CUSTOMER_NAME,\n" );
        sql.append("     T.PART_ID,\n" );
        sql.append("     T.PART_CODE,\n" );
        sql.append("     T.PART_NAME,\n" );
        sql.append("     T.CLAIM_COUNT,\n" );
        sql.append("     T.APPLY_DATE,\n" );
        sql.append("     T.APPLY_STATUS,\n" );
        sql.append("     T.FAULT_CONDITONS,\n" );
        sql.append("     T.REMARKS,\n" );
        sql.append("     T1.ORG_TYPE,\n" );
        sql.append("     T.SOURCE_IN_ID,\n" );
        sql.append("     T.SOURCE_IN_NO\n" );
        sql.append(" FROM\n" );
        sql.append("     PT_BU_CLAIM_APPLY T,TM_ORG T1");
        // 执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("APPLY_STATUS", DicConstant.PJSBSPZT);
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
        baseResultSet.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd");
        baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);
        return baseResultSet;
    }
    
    
    public BaseResultSet searchSourceOrder(PageManager pPageManager, User pUser, String pConditions,String PART_ID) throws Exception {

        String wheres = pConditions + " AND T.ORG_ID = '" + pUser.getOrgId() + "'"
        		+ " AND T.SHIP_STATUS = "+DicConstant.DDFYZT_07+""
        		+ " AND EXISTS(SELECT 1 FROM PT_BU_SALE_ORDER_DTL T1 WHERE T1.ORDER_ID = T.ORDER_ID"
        		+ " AND T1.PART_ID="+PART_ID+")";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuilder sql= new StringBuilder();
        sql.append(" SELECT \n" );
        sql.append("     T.ORDER_ID,\n" );
        sql.append("     T.ORDER_NO,\n" );
        sql.append("     T.ORDER_TYPE\n" );
        sql.append(" FROM \n" );
        sql.append("     PT_BU_SALE_ORDER T\n" );
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
        return baseResultSet;
    }
}
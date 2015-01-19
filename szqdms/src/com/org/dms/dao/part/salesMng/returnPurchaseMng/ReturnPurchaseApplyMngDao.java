package com.org.dms.dao.part.salesMng.returnPurchaseMng;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuReturnApplyDtlVO;
import com.org.dms.vo.part.PtBuReturnApplyVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 退件申请Dao
 *
 * 服务站和配送中心的退件申请
 * @author zhengyao
 * @date 2014-07-30
 * @version 1.0
 */
public class ReturnPurchaseApplyMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return returnPurchaseApplyMngDao 退件申请Dao
     */
    public static final ReturnPurchaseApplyMngDao getInstance(ActionContext pActionContext) {

        ReturnPurchaseApplyMngDao returnPurchaseApplyMngDao = new ReturnPurchaseApplyMngDao();
        pActionContext.setDBFactory(returnPurchaseApplyMngDao.factory);

        return returnPurchaseApplyMngDao;
    }

    /**
     * 退件申请验证
     *
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet returnPurchaseCheck(User pUser,String receiveOrgId) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT 1\n" );
        sql.append("  FROM PT_BU_RETURN_APPLY\n" );
        sql.append(" WHERE APPLY_ORG_ID = '" + pUser.getOrgId() + "'\n" );
        sql.append("   AND APPLY_SATUS = '" + DicConstant.TJSQDZT_01 + "' AND RECEIVE_ORG_ID='" + receiveOrgId + "'");

        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }

    /**
     * 订单查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet expData(String pConditions,User user) throws Exception {

    	String wheres = " WHERE TMP_NO IN ("+ pConditions + ") AND USER_ACCOUNT='"+user.getAccount()+"' \n";
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT PART_CODE, SUPPLIER_CODE, RETURN_COUNT\n" );
    	sql.append("  FROM PT_BU_RETURN_APPLY_DTL_TMP");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }

    /**
     * 退件申请明细临时表(pt_bu_return_apply_dtl_tmp)查询
     *
     * @param pPageManager 查询分页对象
     * @param pConditions sql条件(默认：1=1)
     * @return BaseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchImport(PageManager page, String conditions,User user) throws Exception {

		String wheres = conditions;
		wheres += " AND A.PART_CODE = B.PART_CODE AND A.USER_ACCOUNT ='"+user.getAccount()+"'\n";
		page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.PART_CODE,\n" );
        sql.append("       B.PART_NAME,\n" );
        sql.append("       B.IF_SUPLY,\n" );
        sql.append("       A.SUPPLIER_CODE,\n" );
        sql.append("       (SELECT C.SUPPLIER_NAME\n" );
        sql.append("          FROM PT_BA_SUPPLIER C\n" );
        sql.append("         WHERE A.SUPPLIER_CODE = C.SUPPLIER_CODE AND C.PART_IDENTIFY = "+DicConstant.YXBS_01+") SUPPLIER_NAME,\n" );
        sql.append("       A.RETURN_COUNT\n" );
        sql.append("  FROM PT_BU_RETURN_APPLY_DTL_TMP A, PT_BA_INFO B\n" );
		bs = factory.select(sql.toString(), page);
		return bs;
    }

    /**
     * 退件申请明细表(pt_bu_return_apply_dtl)导入更新
     *
     * @param pReturnId 退件订单ID
     * @param pUser
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateReturnApplyDtl(String pReturnId,User pUser,String tmpNo) throws Exception {

        // 修改退件申请明细表sql
        String stockString = " UPDATE \n"
                           + "     PT_BU_RETURN_APPLY_DTL A \n"
                           + " SET A.RETURN_COUNT ="
                           + "     (SELECT \n"
                           + "         B.RETURN_COUNT \n"
                           + "      FROM \n"
                           + "         PT_BU_RETURN_APPLY_DTL_TMP B \n"
                           + "      WHERE \n"
                           + "         B.USER_ACCOUNT='" + pUser.getAccount() + "'\n"
                           + "      AND A.PART_CODE=B.PART_CODE) \n"
                           + " WHERE \n"
                           + "    A.RETURN_ID='" + pReturnId + "'\n"
                           + " AND A.PART_CODE IN (SELECT \n"
                           + "                         PART_CODE \n"
                           + "                     FROM \n"
                           + "                         PT_BU_RETURN_APPLY_DTL_TMP\n"
                           + "                     WHERE USER_ACCOUNT = '" + pUser.getAccount() + "')"+tmpNo+"";

        // 新增退件申请明细表sql
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO PT_BU_RETURN_APPLY_DTL \n");
        sql.append("     (SELECT \n");
        sql.append("         F_GETID()," + pReturnId + ",\n");
        sql.append("         A.PART_CODE,\n");
        sql.append("         B.PART_NAME,\n");
        sql.append("         A.SUPPLIER_CODE, \n");
        sql.append("         C.SUPPLIER_NAME, \n");
        sql.append("         B.UNIT, \n");
        sql.append("         A.RETURN_COUNT,\n");
        sql.append("         B.SALE_PRICE, \n");
        sql.append("         A.RETURN_COUNT*B.SALE_PRICE, \n");
        sql.append("         '" + pUser.getAccount() + "', \n");
        sql.append("         SYSDATE, \n");
        sql.append("         B.PART_ID, \n");
        sql.append("         C.SUPPLIER_ID, \n");
        sql.append("         '0', \n");
        sql.append("         '0' \n");
        sql.append("     FROM \n");
        sql.append("         PT_BU_RETURN_APPLY_DTL_TMP A, PT_BA_INFO B, PT_BA_SUPPLIER C\n");
        sql.append("     WHERE \n");
        sql.append("         A.PART_CODE NOT IN(\n");
        sql.append("             SELECT \n");
        sql.append("                 PART_CODE \n");
        sql.append("             FROM \n");
        sql.append("                 PT_BU_RETURN_APPLY_DTL\n");
        sql.append("             WHERE \n");
        sql.append("                 RETURN_ID = '" + pReturnId + "')\n");
        sql.append("     AND A.PART_CODE=B.PART_CODE AND A.USER_ACCOUNT = '"+pUser.getAccount()+"' AND A.SUPPLIER_CODE = C.SUPPLIER_CODE AND C.PART_IDENTIFY = "+DicConstant.YXBS_01+") "+tmpNo+"");
        boolean insertFlag = factory.exec(sql.toString());
        boolean updateFlag =factory.update(stockString, null);

        return insertFlag&&updateFlag;
    }

    /**
     * 退件申请修改
     *
     * @param ptBuReturnApplyVO 退件申请实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateReturnApply(PtBuReturnApplyVO ptBuReturnApplyVO, User pUser) throws Exception {

        // 修改退件申请表sql
        String sql = " UPDATE \n"
                   + "     PT_BU_RETURN_APPLY \n"
                   + " SET \n"
                   + "     RECEIVE_ORG_ID='" + ptBuReturnApplyVO.getReceiveOrgId()+ "',\n"
                   + "     RECEIVE_ORG_CODE='" + ptBuReturnApplyVO.getReceiveOrgCode()+ "',\n"
                   + "     RECEIVE_ORG_NAME='" + ptBuReturnApplyVO.getReceiveOrgName()+ "',\n"
                   + "     SOURCE_ORDER_ID='" + ptBuReturnApplyVO.getSourceOrderId()+ "',\n"
                   + "     SOURCE_ORDER_NO='" + ptBuReturnApplyVO.getSourceOrderNo()+ "',\n"
                   + "     REMARKS='" + ptBuReturnApplyVO.getRemarks()+ "',\n"
                   + "     UPDATE_TIME=sysdate \n"
                   + " WHERE \n"
                   + "     RETURN_NO='" + ptBuReturnApplyVO.getReturnNo() + "'\n";

        return  factory.update(sql, null);
    }

    /**
     * 退件申请表(pt_bu_return_apply)添加
     *
     * @param ptBuForcastVO 退件申请实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertReturnPurchaseApply(PtBuReturnApplyVO pPtBuReturnApplyVO) throws Exception {

        return factory.insert(pPtBuReturnApplyVO);
    }

    /**
     * 退件申请明细表(pt_bu_return_apply_dtl)添加
     *
     * @param pPtBuReturnApplyDtlVO 退件申请明细实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertReturnApplyDtl(PtBuReturnApplyDtlVO pPtBuReturnApplyDtlVO) throws Exception {

        return factory.insert(pPtBuReturnApplyDtlVO);
    }

    /**
     * 退件申请表(pt_bu_return_apply)修改
     *
     * @param ptBuForcastVO 退件申请实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateReturnPurchaseApply(PtBuReturnApplyVO pPtBuReturnApplyVO, User pUser) throws Exception {

        // 修改配件预测表sql
        String forecastString = "UPDATE \n"
                              + "    PT_BU_RETURN_APPLY \n"
                              + "SET \n"
                              + "    APPLY_DATE=sysdate,\n"
                              + "    RETURN_COUNT=(SELECT sum(RETURN_COUNT) FROM PT_BU_RETURN_APPLY_DTL WHERE RETURN_ID = '" + pPtBuReturnApplyVO.getReturnId() + "'),\n"
                              + "    RETURN_AMOUNT=(SELECT sum(AMOUNT) FROM PT_BU_RETURN_APPLY_DTL WHERE RETURN_ID = '" + pPtBuReturnApplyVO.getReturnId() + "'),\n"
                              + "    APPLY_SATUS='" + pPtBuReturnApplyVO.getApplySatus() + "',\n"
                              + "    UPDATE_USER='" + pUser.getAccount()+ "',\n"
                              + "    UPDATE_TIME=sysdate \n"
                              + "WHERE \n"
                              + "    RETURN_ID='" + pPtBuReturnApplyVO.getReturnId() + "'\n";
        return factory.update(forecastString, null);
    }

    /**
     * 退件申请明细配件查询
     *
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet searchReturnPart(String pOrderId, User pUser) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT A.PART_ID,A.SUPPLIER_ID,B.RETURN_COUNT\n" );
        sql.append("  FROM PT_BU_DEALER_STOCK A, PT_BU_RETURN_APPLY_DTL B\n" );
        sql.append(" WHERE A.ORG_ID = '" + pUser.getOrgId() + "'\n" );
        sql.append("   AND A.PART_ID = B.PART_ID\n" );
        sql.append("   AND A.SUPPLIER_ID = B.SUPPLIER_ID\n" );
        sql.append("   AND B.RETURN_ID = '" + pOrderId + "'");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }

    /**
     * 修改库存占用
     *
     * @param partId 配件ID
     * @param supplierId 供应商ID
     * @param returnCount 退件数量
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateDealerStock(String partId,String supplierId,String returnCount, User pUser) throws Exception {

        // 修改渠道库存表sql
        StringBuilder sql= new StringBuilder();
        sql.append("UPDATE PT_BU_DEALER_STOCK\n" );
        sql.append("   SET OCCUPY_AMOUNT    = OCCUPY_AMOUNT + '" + returnCount + "',\n" );
        sql.append("       AVAILABLE_AMOUNT = AVAILABLE_AMOUNT - '" + returnCount + "'\n" );
        sql.append(" WHERE ORG_ID = '" + pUser.getOrgId() + "'\n" );
        sql.append("   AND PART_ID = '" + partId + "'\n" );
        sql.append("   AND SUPPLIER_ID = '" + supplierId + "'");

        return factory.update(sql.toString(), null);
    }

    /**
     * 退件申请明细表(pt_bu_return_apply_dtl)删除
     * @param pPtBuReturnApplyDtlVO 退件申请明细实体
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean deleteReturnPurchaseApplyDtl(String pWhere) throws Exception {

        // 删除配件预测明细表sql
        String stockString = "DELETE \n"
                           + "FROM \n"
                           + "    PT_BU_RETURN_APPLY_DTL \n"
                           + "WHERE \n"
                           + pWhere;
        return factory.delete(stockString, null);
    }

    /**
     * 退件申请表(pt_bu_return_apply)删除
     * @param pPtBuReturnApplyVO 退件申请实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean deleteReturnPurchaseApply(PtBuReturnApplyVO pPtBuReturnApplyVO, User pUser) throws Exception {

        // 删除退件申请表sql
        String stockString = "DELETE \n"
                           + "FROM \n"
                           + "    PT_BU_RETURN_APPLY \n"
                           + "WHERE \n"
                           + "    RETURN_ID='" + pPtBuReturnApplyVO.getReturnId() + "'";

        return factory.delete(stockString, null);
    }

    /**
     * 退件申请表(pt_bu_return_apply)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchReturnPurchaseApply1(PageManager page,String returnId) throws Exception {

        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     RETURN_ID,\n");
        sql.append("     RETURN_NO,\n");
        sql.append("     RECEIVE_ORG_ID,\n");
        sql.append("     RECEIVE_ORG_CODE,\n");
        sql.append("     RECEIVE_ORG_NAME,\n");
        sql.append("     SOURCE_ORDER_NO,\n");
        sql.append("     SOURCE_ORDER_ID,\n");
        sql.append("     APPLY_ORG_CODE,\n");
        sql.append("     APPLY_ORG_NAME,\n");
        sql.append("     REMARKS,\n");
        sql.append("     CHECK_REMARKS,\n");
        sql.append("     APPLY_SATUS \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_RETURN_APPLY WHERE RETURN_ID='"+returnId+"' \n");
        // 执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), page);
        baseResultSet.setFieldDic("APPLY_SATUS", DicConstant.TJSQDZT);

        return baseResultSet;
    }
    /**
     * 退件申请表(pt_bu_return_apply)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchReturnPurchaseApply(PageManager pPageManager, User pUser, String pConditions) throws Exception {

        String wheres = pConditions;
        // 查询未申请,审核驳回退件订单
        wheres += " AND APPLY_SATUS IN('" + DicConstant.TJSQDZT_01+ "','" + DicConstant.TJSQDZT_04 + "') \n"
                + " AND APPLY_ORG_ID = " + pUser.getOrgId() + "\n"
                + " ORDER BY RETURN_NO";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     RETURN_ID,\n");
        sql.append("     RETURN_NO,\n");
        sql.append("     RECEIVE_ORG_ID,\n");
        sql.append("     RECEIVE_ORG_CODE,\n");
        sql.append("     RECEIVE_ORG_NAME,\n");
        sql.append("     SOURCE_ORDER_NO,\n");
        sql.append("     SOURCE_ORDER_ID,\n");
        sql.append("     APPLY_ORG_CODE,\n");
        sql.append("     APPLY_ORG_NAME,\n");
        sql.append("     REMARKS,\n");
        sql.append("     CHECK_REMARKS,\n");
        sql.append("     APPLY_SATUS \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_RETURN_APPLY \n");
        // 执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("APPLY_SATUS", DicConstant.TJSQDZT);

        return baseResultSet;
    }
    
    /**
     * 
     * @Title: searchReturnPurchaseApplyForQuery
     * @Description: 退件申请查询
     * @param pPageManager
     * @param pConditions
     * @param user
     * @return
     * @throws Exception
     * @return: BaseResultSet
     */
    public BaseResultSet searchReturnPurchaseApplyForQuery(PageManager pPageManager, String pConditions, User user) throws Exception {
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        
        String sql = " SELECT \n" +
        "     RETURN_ID,\n"+
        "     RETURN_NO,\n"+
        "     RECEIVE_ORG_ID,\n"+
        "     RECEIVE_ORG_CODE,\n"+
        "     RECEIVE_ORG_NAME,\n"+
        "     SOURCE_ORDER_NO,\n"+
        "     SOURCE_ORDER_ID,\n"+
        "     APPLY_ORG_CODE,\n"+
        "     APPLY_ORG_NAME,\n"+
        "     APPLY_DATE,\n"+
        "     (SELECT I.IN_DATE FROM PT_BU_STOCK_IN I WHERE I.ORDER_ID = RETURN_ID) IN_DATE, \n" + 
        "     REMARKS,\n"+
        "     CHECK_REMARKS,\n"+
        "     APPLY_SATUS \n"+
        " FROM \n"+
        "     PT_BU_RETURN_APPLY \n"+
        " WHERE APPLY_SATUS <> '" + DicConstant.TJSQDZT_01 + "'";
       
        // 判断用户是否为审核员，如果是则只能看到自己所能审核的单子
        if(this.checkUserInCheckOrder(user)){
			sql += "AND EXISTS (SELECT 1\n" +
					"       FROM TM_ORG O, PT_BA_ORDER_CHECK C\n" + 
					"      WHERE O.ORG_ID = APPLY_ORG_ID\n" + 
					"        AND O.PID = C.ORG_ID\n" + 
					"        AND C.USER_ACCOUNT = '"+user.getAccount()+"')";
        }
        
       sql +=  " AND EXISTS (SELECT 1 \n" +
			   "       FROM PT_BA_WAREHOUSE W\n" + 
			   "      WHERE W.WAREHOUSE_ID = RECEIVE_ORG_ID) AND " + pConditions + " ORDER BY RETURN_NO";
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("APPLY_SATUS", DicConstant.TJSQDZT);
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        baseResultSet.setFieldDateFormat("IN_DATE", "yyyy-MM-dd HH:mm:ss");

        return baseResultSet;
    }
    
    /**
     * 
     * @Title: checkUserInCheckOrder
     * @Description: 检测用户是否为办事处对应的审核员，如果是返回true，否则返回false
     * @param user
     * @return
     * @throws Exception
     * @return: boolean
     */
    public boolean checkUserInCheckOrder(User user) throws Exception{
    	return Integer.parseInt(this.factory.select("SELECT COUNT(1)  FROM PT_BA_ORDER_CHECK c WHERE c.status = 100201 AND c.user_account = ?", new String[]{user.getAccount()})[0][0]) > 0;
    }
    
    /**
     * 
     * searchReturnPurchaseApply: 退件申请查询for经销商
     * @author fuxiao
     * Date:2014年11月15日
     *
     */
    public BaseResultSet searchReturnPurchaseApplyForDealerQuery(PageManager pPageManager, User pUser, String pConditions) throws Exception {
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        String sql = " SELECT \n" +
        "     RETURN_ID,\n"+
        "     RETURN_NO,\n"+
        "     RECEIVE_ORG_ID,\n"+
        "     RECEIVE_ORG_CODE,\n"+
        "     RECEIVE_ORG_NAME,\n"+
        "     SOURCE_ORDER_NO,\n"+
        "     SOURCE_ORDER_ID,\n"+
        "     APPLY_ORG_CODE,\n"+
        "     APPLY_ORG_NAME,\n"+
        "     REMARKS,\n"+
        "     CHECK_REMARKS,\n"+
        "     APPLY_SATUS \n"+
        " FROM \n"+
        "     PT_BU_RETURN_APPLY \n"+
        " WHERE APPLY_ORG_ID = " + pUser.getOrgId() + "\n" +
        " 		AND " + pConditions + " ORDER BY RETURN_NO";
       
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("APPLY_SATUS", DicConstant.TJSQDZT);

        return baseResultSet;
    }

    /**
     * 退件查询(配件销售订单明细(PT_BU_SALE_ORDER_DTL))
     *
     * @param pPageManager 查询分页对象
     * @param pConditions sql条件(默认：1=1)
     * @return BaseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchReturnPurchase(PageManager pPageManager, User pUser, String pConditions,String returnId) throws Exception {

        String wheres = pConditions;
        wheres += " AND B.PART_ID NOT IN (SELECT PART_ID FROM PT_BU_RETURN_APPLY_DTL WHERE RETURN_ID = '"+returnId+"') AND B.PART_STATUS <> '"+DicConstant.PJZT_02+"'\n";
        pPageManager.setFilter(wheres);

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT B.PART_ID,\n" );
        sql.append("       B.PART_CODE,\n" );
        sql.append("       B.PART_NAME,\n" );
        sql.append("       D.SUPPLIER_ID,\n" );
        sql.append("       D.SUPPLIER_CODE,\n" );
        sql.append("       D.SUPPLIER_NAME,\n" );
        sql.append("       B.SALE_PRICE,\n" );
        sql.append("       B.MIN_PACK,\n" );
        sql.append("       B.MIN_UNIT,\n" );
        sql.append("       B.UNIT,\n" );
        sql.append("       NVL(D.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT,\n" );
        sql.append("       CASE\n" );
        sql.append("         WHEN NVL(D.AVAILABLE_AMOUNT, 0) > 0 THEN\n" );
        sql.append("          '100101'\n" );
        sql.append("         ELSE\n" );
        sql.append("          '100102'\n" );
        sql.append("       END IF_RETURN\n" );
        sql.append("  FROM PT_BA_INFO B\n" );
        sql.append("  LEFT JOIN PT_BU_DEALER_STOCK D\n" );
        sql.append("    ON B.PART_ID = D.PART_ID\n" );
        sql.append("   AND D.ORG_ID = '"+pUser.getOrgId()+"'\n" );

        //执行方法，不需要传递conn参数
        return factory.select(sql.toString(),pPageManager);
    }

    /**
     * 配件预测明细表(pt_bu_forcast_dtl)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return QuerySet 结果集
     * @throws Exception
     */
    public BaseResultSet searchReturnPurchaseApplyDtl(PageManager pPageManager, String pConditions) throws Exception {

        String where = pConditions + " AND A.PART_ID=B.PART_ID";
        pPageManager.setFilter(where);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     A.DTL_ID,\n");
        sql.append("     A.RETURN_ID,\n");
        sql.append("     A.PART_ID,\n");
        sql.append("     A.PART_CODE,\n");
        sql.append("     A.PART_NAME,\n");
        sql.append("     A.SUPPLIER_ID,\n");
        sql.append("     A.SUPPLIER_CODE,\n");
        sql.append("     A.SUPPLIER_NAME,\n");
        sql.append("     A.UNIT,\n");
        sql.append("     A.RETURN_COUNT,\n");
        sql.append("     A.IN_COUNT,\n");
        sql.append("     A.SALE_PRICE,\n");
        sql.append("     NVL(A.IN_COUNT,0)*NVL(A.SALE_PRICE,0) AMOUNT,\n");
        sql.append("     B.MIN_PACK,\n");
        sql.append("     B.MIN_UNIT\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_RETURN_APPLY_DTL A,PT_BA_INFO B\n");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(),pPageManager);

        return baseResultSet;
    }

    /**
     * 查询销售订单
     *
     * @param pageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchSalesOrder(PageManager pageManager, User pUser, String pConditions) throws Exception {

        // 已关闭订单，已签收
        String wheres = pConditions + " AND STATUS = '" + DicConstant.YXBS_01 + "' \n"
                      + " AND ORDER_STATUS = '" + DicConstant.DDZT_06 + "'"
                      + " AND SHIP_STATUS = '" + DicConstant.DDFYZT_07+ "'"
                      + " AND ORDER_ID NOT IN(SELECT SOURCE_ORDER_ID FROM PT_BU_RETURN_APPLY)"
                      + " AND  ORG_ID='" + pUser.getOrgId() + "' ORDER BY ORDER_NO";
        pageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT \n");
        sql.append("     ORDER_ID,\n");
        sql.append("     ORDER_NO,\n");
        sql.append("     ORDER_TYPE \n");
        sql.append(" FROM PT_BU_SALE_ORDER \n");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pageManager);
        baseResultSet.setFieldDic("ORDER_TYPE", DicConstant.DDLX);

        return baseResultSet;
    }

    /**
     * 
     * @Title: queryDownInfo
     * @Description: 配件申请查询详细页面导出查询
     * @param conditions
     * @return
     * @return: QuerySet
     * @throws SQLException 
     */
	public QuerySet queryDownInfo(String conditions) throws SQLException {
		
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     A.DTL_ID,\n");
        sql.append("     A.RETURN_ID,\n");
        sql.append("     A.PART_ID,\n");
        sql.append("     A.PART_CODE,\n");
        sql.append("     A.PART_NAME,\n");
        sql.append("     A.SUPPLIER_ID,\n");
        sql.append("     A.SUPPLIER_CODE,\n");
        sql.append("     A.SUPPLIER_NAME,\n");
        sql.append("     (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = A.UNIT) UNIT,\n");
        sql.append("     A.RETURN_COUNT,\n");
        sql.append("     A.IN_COUNT,\n");
        sql.append("     A.SALE_PRICE,\n");
        sql.append("     NVL(A.IN_COUNT,0)*NVL(A.SALE_PRICE,0) AMOUNT,\n");
        sql.append("     B.MIN_PACK,\n");
        sql.append("     CONCAT(B.MIN_PACK || '/', (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = B.MIN_UNIT)) MIN_UNIT\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_RETURN_APPLY_DTL A,PT_BA_INFO B WHERE " + conditions + " AND A.PART_ID=B.PART_ID");
        
        return this.factory.select(null, sql.toString());
	}
	
	/**
	 * 
	 * @Title: compareApplyStatus
	 * @Description: 比较退件申请单的状态与DB中保存状态是否一致，true：一致，false：不一致
	 * @param returnId
	 * @param status
	 * @return
	 * @return: boolean
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 */
	public boolean compareApplyStatus(String returnId, String status) throws NumberFormatException, SQLException{
		return Integer.parseInt(this.factory.select("SELECT COUNT(1) FROM PT_BU_RETURN_APPLY A WHERE A.RETURN_ID = ? AND A.APPLY_SATUS = ?", new String[]{returnId, status})[0][0]) > 0;
	}
}
package com.org.dms.dao.part.partClaimMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaClaimCycleSetVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.DBFactory;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 配件三包期设置Dao
 *
 * 配件三包期设置的增删改查
 * @author zhengyao
 * @date 2014-08-08
 * @version 1.0
 */
public class ClaimCyclSetMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return ClaimCyclSetMngDao 配件三包期设置Dao
     */
    public static final ClaimCyclSetMngDao getInstance(ActionContext pActionContext) {

        ClaimCyclSetMngDao claimCyclSetMngDao = new ClaimCyclSetMngDao();
        pActionContext.setDBFactory(claimCyclSetMngDao.factory);
        return claimCyclSetMngDao;
    }

    /**
     * 配件三包期设置表(pt_ba_claim_cycle_set)导入更新
     *
     * @param pUser
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateClaimCycleSet(User pUser,String tmpNo) throws Exception {

        // 修改配件三包期设置表sql
        String claimCycleString = " UPDATE \n"
                           + "     PT_BA_CLAIM_CYCLE_SET B \n"
                           + " SET (CLAIM_MONTH,EXTENSION_MONTH)="
                           + "     (SELECT \n"
                           + "         A.CLAIM_MONTH, \n"
                           + "         A.EXTENSION_MONTH \n"
                           + "      FROM \n"
                           + "         PT_BA_CLAIM_CYCLE_SET_TMP A \n"
                           + "      WHERE \n"
                           + "         A.USER_ACCOUNT='" + pUser.getAccount() + "' "+tmpNo+"\n"
                           + "      AND A.PART_CODE=B.PART_CODE) \n"
                           + " WHERE \n"
                           + "      B.PART_CODE IN (SELECT \n"
                           + "                         PART_CODE \n"
                           + "                     FROM \n"
                           + "                         PT_BA_CLAIM_CYCLE_SET_TMP A\n"
                           + "                     WHERE A.USER_ACCOUNT = '" + pUser.getAccount() + "'"+tmpNo+")";

        // 新增配件三包期设置表sql
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO PT_BA_CLAIM_CYCLE_SET \n");
        sql.append("     (SELECT \n");
        sql.append("         F_GETID(),\n");
        sql.append("         B.PART_ID,\n");
        sql.append("         A.PART_CODE,\n");
        sql.append("         A.PART_NAME,\n");
        sql.append("         A.CLAIM_MONTH,\n");
        sql.append("         A.EXTENSION_MONTH,\n");
        sql.append("         '" + pUser.getCompanyId() + "',\n");
        sql.append("         '" + pUser.getOrgId() + "',\n");
        sql.append("         '" + pUser.getAccount() + "',\n");
        sql.append("         sysdate,\n");
        sql.append("         '',\n");
        sql.append("         '',\n");
        sql.append("         '" + DicConstant.YXBS_01 + "',\n");
        sql.append("         '" + pUser.getOemCompanyId() + "', \n");
        sql.append("         ''\n");
        sql.append("     FROM \n");
        sql.append("         PT_BA_CLAIM_CYCLE_SET_TMP A,PT_BA_INFO B\n");
        sql.append("     WHERE  \n");
        sql.append("         A.PART_CODE NOT IN(\n");
        sql.append("             SELECT \n");
        sql.append("                 PART_CODE \n");
        sql.append("             FROM \n");
        sql.append("                 PT_BA_CLAIM_CYCLE_SET) "+tmpNo+"\n");
        sql.append("     AND A.PART_CODE=B.PART_CODE AND USER_ACCOUNT='" + pUser.getAccount() + "')");
        boolean insertFlag = factory.exec(sql.toString());
        boolean updateFlag =factory.update(claimCycleString, null);
        return insertFlag&&updateFlag;
    }

    /**
     * 导入验证(配件数量)
     *
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet partCountCheck(User pUser,DBFactory factory1) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT \n" );
        sql.append("    TMP_ID,\n" );
        sql.append("    TMP_NO,\n" );
        sql.append("    PART_CODE,\n" );
        sql.append("    PART_NAME,\n" );
        sql.append("    CLAIM_MONTH,\n" );
        sql.append("    EXTENSION_MONTH,\n" );
        sql.append("    USER_ACCOUNT\n" );
        sql.append("    FROM PT_BA_CLAIM_CYCLE_SET_TMP\n" );
        sql.append(" WHERE USER_ACCOUNT = '" + pUser.getAccount() + "'");
        return factory1.select(null, sql.toString());
    }

    /**
     * 导入验证(配件是否存在)
     *
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet partCheck(User pUser,DBFactory factory1) throws Exception{

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT TMP_NO,PART_CODE, PART_NAME\n" );
        sql.append("  FROM PT_BA_CLAIM_CYCLE_SET_TMP\n" );
        sql.append(" WHERE PART_CODE || PART_NAME NOT IN\n" );
        sql.append("       (SELECT PBI.PART_CODE || PBI.PART_NAME\n" );
        sql.append("          FROM PT_BA_INFO PBI\n" );
        sql.append("         WHERE PBI.PART_ID NOT IN\n" );
        sql.append("               (SELECT PART_ID FROM PT_BA_CLAIM_CYCLE_SET))\n" );
        sql.append("   AND USER_ACCOUNT = '" + pUser.getAccount() + "'");

        return factory1.select(null, sql.toString());
    }
    /**
     * 配件三包期设置临时表(PT_BA_CLAIM_CYCLE_SET_TMP)查询
     *
     * @param pPageManager 查询分页对象
     * @param pConditions sql条件(默认：1=1)
     * @return BaseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchImport(PageManager pPageManager, String pConditions) throws Exception {

        pPageManager.setFilter(pConditions);
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     PART_CODE, \n");
        sql.append("     PART_NAME, \n");
        sql.append("     CLAIM_MONTH, \n");
        sql.append("     EXTENSION_MONTH \n");
        sql.append(" FROM \n");
        sql.append("     PT_BA_CLAIM_CYCLE_SET_TMP \n");
        //执行方法，不需要传递conn参数
        return factory.select(sql.toString(),pPageManager);
    }

    /**
     * 订单查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet expData(String pConditions,User user) throws Exception {

    	String wheres = " WHERE TMP_NO IN ("+ pConditions + ")  AND USER_ACCOUNT='"+user.getAccount()+"' \n";
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT PART_CODE, PART_NAME, CLAIM_MONTH, EXTENSION_MONTH\n" );
    	sql.append("  FROM PT_BA_CLAIM_CYCLE_SET_TMP");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }

    /**
     * 配件三包期设置(下载模板)
     *
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download() throws Exception {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT PBI.PART_CODE,\n");
        sql.append("     PBI.PART_NAME,\n");
        sql.append("     '' AS CLAIM_MONTH,\n");
        sql.append("     '' AS EXTENSION_MONTH\n");
        sql.append(" FROM PT_BA_INFO PBI\n");
        sql.append(" WHERE \n");
        sql.append("     PBI.PART_ID NOT IN (\n");
        sql.append("                         SELECT \n");
        sql.append("                             PART_ID \n");
        sql.append("                         FROM \n");
        sql.append("                         PT_BA_CLAIM_CYCLE_SET) \n");
        sql.append(" ORDER BY PBI.PART_CODE\n");
        //执行方法，不需要传递conn参数

        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }

    /**
     * 配件三包期设置表(PT_BA_CLAIM_CYCLE_SET)添加
     *
     * @param pPtBaClaimCycleSetVO 配件三包期设置实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertClaimCyclSet(PtBaClaimCycleSetVO pPtBaClaimCycleSetVO) throws Exception {

        return factory.insert(pPtBaClaimCycleSetVO);
    }

    /**
     * 配件三包期设置表(PT_BA_CLAIM_CYCLE_SET)修改
     *
     * @param pPtBaClaimCycleSetVO 配件三包期设置实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateClaimCyclSet(PtBaClaimCycleSetVO pPtBaClaimCycleSetVO, User pUser) throws Exception {

        String where = " AND OEM_COMPANY_ID=" + pUser.getOemCompanyId() + "\n";
        // 修改配件三包期设置表sql
        String sql = "UPDATE \n"
                   + "    PT_BA_CLAIM_CYCLE_SET \n"
                   + "SET \n"
                   + "    CLAIM_MONTH='" + pPtBaClaimCycleSetVO.getClaimMonth()+ "',\n"
                   + "    EXTENSION_MONTH='" + pPtBaClaimCycleSetVO.getExtensionMonth()+ "',\n"
                   + "    UPDATE_USER='" + pUser.getAccount()+ "',\n"
                   + "    UPDATE_TIME=sysdate \n"
                   + "WHERE \n"
                   + "    CYCLE_ID='" + pPtBaClaimCycleSetVO.getCycleId() + "'\n"
                   + where;
        return factory.update(sql, null);
    }

    /**
     * 配件三包期设置表(PT_BA_CLAIM_CYCLE_SET)删除
     * @param pPtBaClaimCycleSetVO 配件三包期设置实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean deleteClaimCyclSet(PtBaClaimCycleSetVO pPtBaClaimCycleSetVO, User pUser) throws Exception {

        // 按所属公司,所属机构删除
        String wheres = " AND OEM_COMPANY_ID=" + pUser.getOemCompanyId() + "\n";
        // 删除配件三包期设置表sql
        String sql = "DELETE \n"
                   + "FROM \n"
                   + "    PT_BA_CLAIM_CYCLE_SET \n"
                   + "WHERE \n"
                   + "    CYCLE_ID='" + pPtBaClaimCycleSetVO.getCycleId() + "'"
                   + wheres;
        return factory.delete(sql, null);
    }

    /**
     * 配件三包期设置表(PT_BA_CLAIM_CYCLE_SET)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchClaimCyclSet(PageManager pPageManager, User pUser, String pConditions,boolean pFlag) throws Exception {

        String wheres = pConditions;
        // 按所属机构查询，并且按配件代码升序排列
        wheres += " AND OEM_COMPANY_ID=" + pUser.getOemCompanyId() + "\n"
                + " AND STATUS = " + DicConstant.YXBS_01 + "\n"
                + " ORDER BY PART_CODE";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     CYCLE_ID,\n");
        sql.append("     PART_ID,\n");
        sql.append("     PART_CODE,\n");
        sql.append("     PART_NAME,\n");
        sql.append("     CLAIM_MONTH,\n");
        sql.append("     EXTENSION_MONTH\n");
        sql.append(" FROM \n");
        sql.append("     PT_BA_CLAIM_CYCLE_SET \n");
        // 执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        return baseResultSet;
    }

    /**
     * 查询配件
     *
     * @param pageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchPart(PageManager pageManager, User pUser, String pConditions) throws Exception {

        //过滤掉当前促销活动已存在的配件
        String wheres = pConditions + " AND PBI.PART_ID NOT IN (SELECT PART_ID FROM PT_BA_CLAIM_CYCLE_SET) ORDER BY PBI.PART_CODE\n";
        pageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT PBI.PART_ID,\n");
        sql.append("     PBI.PART_CODE,\n");
        sql.append("     PBI.PART_NAME\n");
        sql.append(" FROM PT_BA_INFO PBI\n");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pageManager);
        return baseResultSet;
    }
}
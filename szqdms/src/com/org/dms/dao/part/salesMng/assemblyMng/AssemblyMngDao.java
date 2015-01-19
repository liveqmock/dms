package com.org.dms.dao.part.salesMng.assemblyMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuAssemblyAnnexVO;
import com.org.dms.vo.part.PtBuAssemblyDtlVO;
import com.org.dms.vo.part.PtBuAssemblyVO;
import com.org.dms.vo.part.PtBuPchContractDtlVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 总成附件确认Dao
 *
 * 总成附件确认的增删改查
 * @author zhengyao
 * @date 2014-10-13
 * @version 1.0
 */
public class AssemblyMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return assemblyMngDao 总成附件确认Dao
     */
    public static final AssemblyMngDao getInstance(ActionContext pActionContext) {

        AssemblyMngDao assemblyMngDao = new AssemblyMngDao();
        pActionContext.setDBFactory(assemblyMngDao.factory);
        return assemblyMngDao;
    }

    /**
     * 总成附件确认表(PT_BU_ASSEMBLY)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchAssemblyCheck(PageManager pPageManager,String pConditions) throws Exception {

        String wheres = pConditions;
        // 按所属机构查询，并且按预测月份升序排列
        wheres += " AND CONFIG_STATUS = ("+DicConstant.ZCFJQRZT_02+")"
                + " ORDER BY APPLY_TIME";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     ASSEMBLY_ID,\n");
        sql.append("     ORG_CODE,\n");
        sql.append("     ORG_NAME,\n");
        sql.append("     APPLY_TIME,\n");
        sql.append("     CREATE_USER,\n");
        sql.append("     CONFIG_STATUS\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_ASSEMBLY \n");
        // 执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("CONFIG_STATUS", DicConstant.ZCFJQRZT);
        baseResultSet.setFieldDateFormat("APPLY_TIME", "yyyy-MM-dd HH:mm:ss");
        baseResultSet.setFieldUserID("CREATE_USER");
        return baseResultSet;
    }

    /**
     * 总成表(PT_BU_ASSEMBLY)添加
     *
     * @param ptBuAssemblyVO 总成附件确认实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertForecast(PtBuAssemblyVO ptBuAssemblyVO) throws Exception {

        return factory.insert(ptBuAssemblyVO);
    }

    /**
     * 总成附件确认明细表(PT_BU_ASSEMBLY_DTL)修改
     * @param ptBuAssemblyDtlVO 总成附件确认明细实体
     * @param pWhere 条件
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean updateForecastDetail(PtBuAssemblyDtlVO ptBuAssemblyDtlVO) throws Exception {

        // 删除配件预测明细表sql
        String stockString = " UPDATE \n"
                           + "    PT_BU_ASSEMBLY_DTL \n"
                           + " SET \n"
                           + "    REMARKS='" + ptBuAssemblyDtlVO.getRemarks() + "'"
                           + " WHERE \n"
                           + "    ASSEMBLY_ID='" + ptBuAssemblyDtlVO.getAssemblyId() + "' \n"
                           + " AND PART_ID='" + ptBuAssemblyDtlVO.getPartId() + "'\n";
        return factory.update(stockString, null);
    }

    /**
     * 总成附件确认明细表(PT_BU_ASSEMBLY_DTL)添加
     *
     * @param ptBuAssemblyDtlVO 总成附件确认明细实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertPartReportDetail(PtBuAssemblyDtlVO ptBuAssemblyDtlVO) throws Exception {

        return factory.insert(ptBuAssemblyDtlVO);
    }

    /**
     * 总成附件确认表(PT_BU_ASSEMBLY)修改
     *
     * @param ptBuForcastVO 总成附件确认实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateForecast(PtBuAssemblyVO ptBuAssemblyVO, User pUser) throws Exception {

        // 修改总成附件确认表sql
        String forecastString = "UPDATE \n"
                              + "    PT_BU_ASSEMBLY \n"
                              + "SET \n"
                              + "    CONFIG_STATUS='" + ptBuAssemblyVO.getConfigStatus()+ "',\n"
                              + "    UPDATE_USER='" + pUser.getAccount()+ "',\n"
                              + "    UPDATE_TIME=sysdate \n"
                              + "WHERE \n"
                              + "    ASSEMBLY_ID='" + ptBuAssemblyVO.getAssemblyId() + "'\n";
        return factory.update(forecastString, null);
    }

    /**
     * 总成附件确认明细表(PT_BU_ASSEMBLY_DTL)删除
     * @param ptBuAssemblyDtlVO 总成附件确认明细实体
     * @param pWhere 条件
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean deleteForecastDetail(PtBuAssemblyDtlVO ptBuAssemblyDtlVO, String pWhere) throws Exception {

        // 删除总成附件确认明细表sql
        String stockString = "DELETE \n"
                           + "FROM \n"
                           + "    PT_BU_ASSEMBLY_DTL \n"
                           + "WHERE \n"
                           + "    ASSEMBLY_ID='" + ptBuAssemblyDtlVO.getAssemblyId() + "'"
                           + pWhere;
        return factory.delete(stockString, null);
    }

    /**
     * 总成附件确认表(PT_BU_ASSEMBLY)删除
     * @param ptBuAssemblyVO 总成附件确认实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean deleteForecast(PtBuAssemblyVO ptBuAssemblyVO, User pUser) throws Exception {

        // 按所属公司,所属机构删除
        String wheres = " AND ORG_ID = " + pUser.getOrgId() + "\n";
        // 删除总成附件确认表sql
        String stockString = "DELETE \n"
                           + "FROM \n"
                           + "    PT_BU_ASSEMBLY \n"
                           + "WHERE \n"
                           + "    ASSEMBLY_ID='" + ptBuAssemblyVO.getAssemblyId() + "'"
                           + wheres;
        return factory.delete(stockString, null);
    }

    /**
     * 总成附件确认表(PT_BU_ASSEMBLY)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public Object searchForecast(PageManager pPageManager, User pUser, String pConditions,boolean pFlag) throws Exception {

        String wheres = pConditions;
        // 按所属机构查询，并且按预测月份升序排列
        wheres += " AND ORG_ID = " + pUser.getOrgId() + "\n"
        		+ " AND CONFIG_STATUS = "+DicConstant.ZCFJQRZT_01+" AND T.ASSEMBLY_ID=T1.ASSEMBLY_ID(+)"
                + " ORDER BY CREATE_TIME";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     T.ASSEMBLY_ID,\n");
        sql.append("     CREATE_USER,\n");
        sql.append("     CREATE_TIME,\n");
        sql.append("     APPLY_TIME,\n");
        sql.append("     COUNT,\n");
        sql.append("     CONFIG_STATUS\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_ASSEMBLY T,(SELECT COUNT(1) COUNT,ASSEMBLY_ID FROM PT_BU_ASSEMBLY_DTL GROUP BY ASSEMBLY_ID) T1 \n");
        // 执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("CONFIG_STATUS", DicConstant.ZCFJQRZT);
        baseResultSet.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
        baseResultSet.setFieldDateFormat("APPLY_TIME", "yyyy-MM-dd HH:mm:ss");
        baseResultSet.setFieldUserID("CREATE_USER");
        return baseResultSet;
    }

    /**
     * 总成附件确认表(PT_BU_ASSEMBLY)查询
     *
     * @param ptBuAssemblyDtlVO 总成附件确认实体
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet searchByForecastMonth(PtBuAssemblyDtlVO ptBuAssemblyDtlVO, User pUser) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     ASSEMBLY_ID \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_ASSEMBLY \n");
        sql.append(" WHERE \n");
        sql.append("     ASSEMBLY_ID='" + ptBuAssemblyDtlVO.getAssemblyId() + "' \n");
        sql.append(" AND ORG_ID = " + pUser.getOrgId() + "\n");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }

    /**
     * 总成附件确认明细表(PT_BU_ASSEMBLY_DTL)重复验证
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet searchByForecastMonth(PtBuAssemblyDtlVO ptBuAssemblyDtlVO) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     1 \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_ASSEMBLY_DTL \n");
        sql.append(" WHERE \n");
        sql.append("     ASSEMBLY_ID='" + ptBuAssemblyDtlVO.getAssemblyId() + "' \n");
        sql.append(" AND PART_CODE ='" + ptBuAssemblyDtlVO.getPartCode() + "'\n");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }

    

    /**
     * 总成附件确认明细表(PT_BU_ASSEMBLY_dtl)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return QuerySet 结果集
     * @throws Exception
     */
    public BaseResultSet searchForecastDetail(PageManager pPageManager, String pConditions) throws Exception {

        pPageManager.setFilter(pConditions);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     DTL_ID,\n");
        sql.append("     ASSEMBLY_ID,\n");
        sql.append("     PART_ID,\n");
        sql.append("     PART_CODE,\n");
        sql.append("     PART_NAME,\n");
        sql.append("     REMARKS\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_ASSEMBLY_DTL \n");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(),pPageManager);
        return baseResultSet;
    }
    public BaseResultSet searchForecastDetail1(PageManager pPageManager, String id) throws Exception {

        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     DTL_ID,\n");
        sql.append("     A_PART_ID,\n");
        sql.append("     PART_CODE,\n");
        sql.append("     PART_NAME,\n");
        sql.append("     REMARKS\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_ASSEMBLY_ANNEX WHERE A_PART_ID = "+id+" \n");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(),pPageManager);
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
    public BaseResultSet searchPart(PageManager pageManager, User pUser, String pConditions,String pAssemblyId) throws Exception {

        // 过滤掉已存在的,并且不是大总成的配件
        String wheres = pConditions + " AND PBI.IF_ASSEMBLY = '"+DicConstant.SF_01+"'"
                      + " AND PBI.PART_ID NOT IN (SELECT PART_ID FROM PT_BU_ASSEMBLY_DTL WHERE ASSEMBLY_ID='" + pAssemblyId + "')"
                      + " ORDER BY PBI.PART_CODE\n";
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
    public QuerySet checkUnique(String PART_CODE,String A_PART_ID) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT 1 FROM PT_BU_ASSEMBLY_ANNEX WHERE PART_CODE = '"+PART_CODE+"' AND A_PART_ID = "+A_PART_ID+"");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }
    public boolean partInsert(PtBuAssemblyAnnexVO vo)
            throws Exception {
        return factory.insert(vo);
    }
    public boolean partUpdate(PtBuAssemblyAnnexVO vo)
            throws Exception {
        return factory.update(vo);
    }
    public boolean updateParts(String DTL_ID ) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("DELETE FROM PT_BU_ASSEMBLY_ANNEX WHERE DTL_ID = "+DTL_ID+"");
    	return factory.update(sql.toString(), null);
    }
}
package com.org.dms.dao.part.salesMng.assemblyMng;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class AssemblySearchMngDao extends BaseDAO{
	public static final AssemblySearchMngDao getInstance(ActionContext pActionContext) {

        AssemblySearchMngDao AssemblySearchMngDao = new AssemblySearchMngDao();
        pActionContext.setDBFactory(AssemblySearchMngDao.factory);
        return AssemblySearchMngDao;
    }
	public Object searchForecast(PageManager pPageManager, User pUser, String pConditions,boolean pFlag) throws Exception {

        String wheres = pConditions;
        // 按所属机构查询，并且按预测月份升序排列
        wheres += " AND ORG_ID = " + pUser.getOrgId() + "\n"
        		+ " AND T.ASSEMBLY_ID=T1.ASSEMBLY_ID(+)"
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
	public BaseResultSet searchForecastDetail(PageManager pPageManager, String id) throws Exception {

        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.PART_ID,\n" );
        sql.append("       T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T.REMARKS,\n" );
        sql.append("       T1.PART_CODE A_PART_CODE,\n" );
        sql.append("       T1.PART_NAME A_PART_NAME,\n" );
        sql.append("       T1.REMARKS   A_REMARKS\n" );
        sql.append("  FROM PT_BU_ASSEMBLY_DTL T, PT_BU_ASSEMBLY_ANNEX T1\n" );
        sql.append(" WHERE 1 = 1\n" );
        sql.append("   AND T.DTL_ID = T1.A_PART_ID(+)\n" );
        sql.append("   AND ASSEMBLY_ID = "+id+"\n" );
        sql.append("   ORDER BY T1.PART_CODE");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(),pPageManager);
        return baseResultSet;
    }
	public Object OEMSearchForecast(PageManager pPageManager, User pUser, String pConditions,boolean pFlag) throws Exception {

        String wheres = pConditions;
        // 按所属机构查询，并且按预测月份升序排列
        wheres += " AND T.ASSEMBLY_ID=T1.ASSEMBLY_ID(+)"
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

}

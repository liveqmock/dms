package com.org.dms.dao.part.storageMng.warehouseMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaWarehousePartRlVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 配件与库位关系维护Dao
 *
 * @author zhengyao
 * @date 2014-07-14
 * @version 1.0
 */
public class PartRlHouseMngDao extends BaseDAO {

    /**
     * 定义instance
     * @param pActionContext
     * @return partRlHouseMngDao 配件与库位关系维护Dao
     */
    public static final PartRlHouseMngDao getInstance(ActionContext pActionContext) {

        PartRlHouseMngDao partRlHouseMngDao = new PartRlHouseMngDao();
        pActionContext.setDBFactory(partRlHouseMngDao.factory);
        return partRlHouseMngDao;
    }

    /**
     * 配件与库位关系维护表(pt_ba_warehouse_part_rl)添加
     *
     * @param pPtBaWarehousePartRlVO 配件与库位关系维护实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertPartRlHouse(PtBaWarehousePartRlVO pPtBaWarehousePartRlVO) throws Exception {

        return factory.insert(pPtBaWarehousePartRlVO);
    }

    /**
     * 配件与库位关系维护表(pt_ba_warehouse_part_rl)修改
     *
     * @param pPtBaWarehousePartRlVO 配件与库位关系维护实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updatePartRlHouse(PtBaWarehousePartRlVO pPtBaWarehousePartRlVO,User pUser) throws Exception {

//        String where = " AND COMPANY_ID=" + pUser.getCompanyId() + "\n"
//                     + " AND OEM_COMPANY_ID=" + pUser.getOemCompanyId() + "\n"
//                     + " AND ORG_ID = " + pUser.getOrgId() + "\n";
        // 修改库区表sql
        String areaString = "UPDATE \n"
                          + "PT_BA_WAREHOUSE_PART_RL \n"
                          + "SET \n"
                          + "POSITION_ID='" + pPtBaWarehousePartRlVO.getPositionId()+ "',\n"
                          + "POSITION_CODE='" + pPtBaWarehousePartRlVO.getPositionCode()+ "',\n"
                          + "POSITION_NAME='" + pPtBaWarehousePartRlVO.getPositionName()+ "',\n"
                          + "PART_ID='" + pPtBaWarehousePartRlVO.getPartId()+ "',\n"
                          + "PART_CODE='" + pPtBaWarehousePartRlVO.getPartCode()+ "',\n"
                          + "PART_NAME='" + pPtBaWarehousePartRlVO.getPartName()+ "',\n"
                          + "IF_DEFAULT='" + pPtBaWarehousePartRlVO.getIfDefault()+ "',\n"
                          + "STATUS='" + pPtBaWarehousePartRlVO.getStatus()+ "',\n"
                          + "UPDATE_USER='" + pUser.getAccount()+ "',\n"
                          + "UPDATE_TIME=sysdate \n"
                          + "WHERE \n"
                          + "RELATION_ID='" + pPtBaWarehousePartRlVO.getRelationId() + "'\n";
//                          + where;
        return factory.update(areaString,null);
    }

    /**
     * 配件与库位关系维护表(pt_ba_warehouse_part_rl)删除
     *
     * @param pPtBaWarehousePartRlVO 配件与库位关系维护实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean deletePartRlHouse(PtBaWarehousePartRlVO pPtBaWarehousePartRlVO,User pUser) throws Exception {

//        String where = " AND COMPANY_ID=" + pUser.getCompanyId() + "\n"
//                     + " AND OEM_COMPANY_ID=" + pUser.getOemCompanyId() + "\n"
//                     + " AND ORG_ID = " + pUser.getOrgId() + "\n";
        // 删除库位表sql
        String areaString = "DELETE \n"
                          + "FROM \n"
                          + "    PT_BA_WAREHOUSE_PART_RL \n"
                          + "WHERE \n"
                          + "    RELATION_ID='" + pPtBaWarehousePartRlVO.getRelationId() + "' \n";
//                          + where;
        return factory.delete(areaString,null);
    }

    /**
     * 配件与库位关系维护表(pt_ba_warehouse_part_rl)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return BaseResultSet 库位表的结果集
     * @throws Exception
     */
    public BaseResultSet searchPartRlHouse(PageManager pPageManager, User pUser, String pConditions) throws Exception {

        String wheres = pConditions;
        //增加按当前公司、当前组织过滤条件
        wheres += " AND PBWPR.POSITION_ID=PBWP.POSITION_ID "
                + " AND PBWP.AREA_ID=PBWA.AREA_ID "
                + " AND PBW.WAREHOUSE_ID = PBWA.WAREHOUSE_ID "
                //+ " AND PBWPR.COMPANY_ID=" + pUser.getCompanyId() 
                //+ " AND PBWPR.OEM_COMPANY_ID=" + pUser.getOemCompanyId() + "\n"
                //+ " AND PBWPR.ORG_ID = '" + pUser.getOrgId()+"' \n" 
                + " ORDER BY PBWPR.PART_CODE";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT PBWPR.RELATION_ID,\n");
        sql.append("        PBWPR.PART_ID,\n");
        sql.append("        PBWPR.PART_CODE,\n");
        sql.append("        PBWPR.PART_NAME,\n");
        sql.append("        PBWPR.POSITION_ID,\n");
        sql.append("        PBWPR.POSITION_CODE,\n");
        sql.append("        PBWPR.POSITION_NAME,\n");
        sql.append("        PBW.WAREHOUSE_ID,\n");
        sql.append("        PBW.WAREHOUSE_CODE,\n");
        sql.append("        PBW.WAREHOUSE_NAME,\n");
        sql.append("        PBWA.AREA_ID,\n");
        sql.append("        PBWA.AREA_CODE,\n");
        sql.append("        PBWA.AREA_NAME,\n");
        sql.append("        PBWPR.STATUS,\n");
        sql.append("        PBWPR.IF_DEFAULT\n");
        sql.append(" FROM \n");
        sql.append("        PT_BA_WAREHOUSE_PART_RL PBWPR,PT_BA_WAREHOUSE PBW,PT_BA_WAREHOUSE_AREA PBWA,PT_BA_WAREHOUSE_POSITION PBWP ");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("STATUS", "YXBS");
        baseResultSet.setFieldDic("IF_DEFAULT", "SF");
        return baseResultSet;
    }

    /**
     * 配件与库位关系维护表(pt_ba_warehouse_part_rl)查询
     *
     * @param pPtBaWarehousePartRlVO 配件与库位关系维护实体
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet searchByPartRlCode(PtBaWarehousePartRlVO pPtBaWarehousePartRlVO, User pUser) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     PART_CODE \n");
        sql.append(" FROM \n");
        sql.append("     PT_BA_WAREHOUSE_PART_RL \n");
        sql.append(" WHERE \n");
        sql.append("     POSITION_CODE='" + pPtBaWarehousePartRlVO.getPositionCode() + "' \n");
        sql.append(" AND PART_ID='" + pPtBaWarehousePartRlVO.getPartId() + "' \n");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }

    /**
     * 配件库存是否为0
     *
     * @param pPtBaWarehousePartRlVO 配件与库位关系维护实体
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet checkPartStock(PtBaWarehousePartRlVO pPtBaWarehousePartRlVO,String warehouseId) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT 1 FROM PT_BU_STOCK_DTL A,PT_BU_STOCK B WHERE A.STOCK_ID=B.STOCK_ID AND A.POSITION_ID='"+pPtBaWarehousePartRlVO.getPositionId()+"' AND A.AMOUNT>0 AND A.PART_ID='"+pPtBaWarehousePartRlVO.getPartId()+"' AND B.WAREHOUSE_ID='"+warehouseId+"' \n");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }
    /**
     * 配件存在默认库位验证
     *
     * @param pPtBaWarehousePartRlVO 配件与库位关系维护实体
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet checkByPartRlCode(PtBaWarehousePartRlVO pPtBaWarehousePartRlVO, User pUser,String partCode,String areaId) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     POSITION_CODE \n");
        sql.append(" FROM \n");
        sql.append("     PT_BA_WAREHOUSE_PART_RL \n");
        sql.append(" WHERE \n");
        sql.append("     IF_DEFAULT='" + DicConstant.SF_01 + "' \n");
        sql.append(" AND PART_ID='" + pPtBaWarehousePartRlVO.getPartId() + "' "+partCode+" \n");
        sql.append(" AND POSITION_CODE IN (SELECT POSITION_CODE FROM PT_BA_WAREHOUSE_POSITION WHERE AREA_ID = '" + areaId + "') \n");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }
    /**
     * 查询配件
     *
     * @param page 查询分页对象
     * @param user 当前登录user对象
     * @param conditions sql条件(默认：1=1)
     * @return
     * @throws Exception
     */
    public BaseResultSet searchPart(PageManager page, User pUser, String conditions) throws Exception {
        String wheres = conditions;
        //过滤掉当前促销活动已存在的配件
        wheres += " AND PBI.PART_STATUS<>'" + DicConstant.PJZT_02+ "' ORDER BY PBI.PART_CODE\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT PBI.PART_ID,\n");
        sql.append("     PBI.PART_CODE,\n");
        sql.append("     PBI.PART_NAME,\n");
        sql.append("     PBI.UNIT,\n");
        sql.append("     PBI.MIN_UNIT,\n");
        sql.append("     PBI.MIN_PACK\n");
        sql.append(" FROM PT_BA_INFO PBI\n");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
        return bs;
    }
    public BaseResultSet getArea(PageManager page, String PART_CODE,String WAREHOUSE_ID) throws Exception
    {
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T2.AREA_ID,T2.AREA_CODE,T2.AREA_NAME\n" );
        sql.append("  FROM PT_BA_INFO T, PT_BA_WAREHOUSE_KEEPER T1, PT_BA_WAREHOUSE_AREA T2\n" );
        sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
        sql.append("   AND T1.WAREHOUSE_ID = T2.WAREHOUSE_ID\n" );
        sql.append("   AND T2.KEEP_MAN = T1.USER_ACCOUNT\n" );
        sql.append("   AND T.PART_CODE = '"+PART_CODE+"'\n");
        sql.append("   AND T1.WAREHOUSE_ID = "+WAREHOUSE_ID+"\n" );
        bs = factory.select(sql.toString(), page);
        return bs;
    }
    public QuerySet checkArea(String PART_CODE,String AREA_ID,String WAREHOUSE_ID) throws Exception {
        //执行方法，不需要传递conn参数
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT 1\n" );
    	sql.append("  FROM PT_BA_WAREHOUSE_PART_RL\n" );
    	sql.append(" WHERE PART_CODE = '"+PART_CODE+"' AND STATUS = "+DicConstant.YXBS_01+"\n" );
    	sql.append("   AND POSITION_ID IN\n" );
    	sql.append("       (SELECT POSITION_ID FROM PT_BA_WAREHOUSE_AREA WHERE AREA_ID <> "+AREA_ID+" AND WAREHOUSE_ID ="+WAREHOUSE_ID+")");
        return factory.select(null,sql.toString());
    }
}
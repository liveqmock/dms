package com.org.dms.dao.part.storageMng.warehouseMng;

import com.org.dms.vo.part.PtBaWarehouseAreaVO;
import com.org.dms.vo.part.PtBaWarehouseVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 仓库管理Dao
 *
 * 仓库的增删改查
 * @author zhengyao
 * @date 2014-07-08
 * @version 1.0
 */
public class WarehouseMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return warehouseMngDao 仓库管理Dao
     */
    public static final WarehouseMngDao getInstance(ActionContext pActionContext) {

        WarehouseMngDao warehouseMngDao = new WarehouseMngDao();
        pActionContext.setDBFactory(warehouseMngDao.factory);
        return warehouseMngDao;
    }

    /**
     * 仓库表(pt_ba_warehouse)添加
     *
     * @param pPtBaWarehouseVO 仓库实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertWareHouse(PtBaWarehouseVO pPtBaWarehouseVO) throws Exception {

        return factory.insert(pPtBaWarehouseVO);
    }

    /**
     * 仓库表(pt_ba_warehouse),库区表(pt_ba_warehouse_area)修改
     *
     * @param pPtBaWarehouseVO 仓库实体
     * @param ptBaWarehouseAreaVO 库区实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateWareHouse(PtBaWarehouseVO pPtBaWarehouseVO, PtBaWarehouseAreaVO ptBaWarehouseAreaVO, User pUser) throws Exception {

        String where = " AND OEM_COMPANY_ID = " + pUser.getOemCompanyId() + "\n";
        // 修改仓库表sql
        String warehouseString = "UPDATE \n"
                               + "PT_BA_WAREHOUSE \n"
                               + "SET \n"
                               + "WAREHOUSE_CODE='" + pPtBaWarehouseVO.getWarehouseCode()+ "',\n"
                               + "WAREHOUSE_NAME='" + pPtBaWarehouseVO.getWarehouseName()+ "',\n"
                               + "WAREHOUSE_STATUS='" + pPtBaWarehouseVO.getWarehouseStatus()+ "',\n"
                               + "WAREHOUSE_TYPE='" + pPtBaWarehouseVO.getWarehouseType()+ "',\n"
                               + "ORG_ID='" + pPtBaWarehouseVO.getOrgId()+ "',\n"
                               + "UPDATE_USER='" + pUser.getAccount()+ "',\n"
                               + "UPDATE_TIME=sysdate \n"
                               + "WHERE \n"
                               + "WAREHOUSE_ID='" + pPtBaWarehouseVO.getWarehouseId() + "'\n"
                               + where;
        // 修改库区表sql
        String areaString = "UPDATE \n"
                          + "PT_BA_WAREHOUSE_AREA \n"
                          + "SET \n"
                          + "WAREHOUSE_CODE='" + ptBaWarehouseAreaVO.getWarehouseCode()+ "', \n"
                          + "WAREHOUSE_NAME='" + ptBaWarehouseAreaVO.getWarehouseName()+ "', \n"
                          + "UPDATE_USER='" + pUser.getAccount()+ "', \n"
                          + "UPDATE_TIME=sysdate \n"
                          + "WHERE \n"
                          + "WAREHOUSE_ID='" + ptBaWarehouseAreaVO.getWarehouseId() + "'\n"
                          + where;
        boolean warehouseFlag = factory.update(warehouseString, null);
        boolean areaFlag = factory.update(areaString, null);
        return areaFlag&&warehouseFlag;
    }

    /**
     * 仓库表(pt_ba_warehouse),库区表(pt_ba_warehouse_area),库位表(pt_ba_warehouse_position)删除
     * @param pPtBaWarehouseVO 仓库实体
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean deleteWareHouse(PtBaWarehouseVO pPtBaWarehouseVO, User pUser) throws Exception {

        String where = " AND OEM_COMPANY_ID = " + pUser.getOemCompanyId() + "\n";
        // 删除仓库表sql
        String warehouseString = "DELETE \n"
                               + "FROM \n"
                               + "    PT_BA_WAREHOUSE \n"
                               + "WHERE \n"
                               + "    WAREHOUSE_ID='" + pPtBaWarehouseVO.getWarehouseId() + "'"
                               + where;
        // 删除库区表sql
        String areaString = "DELETE \n"
                          + "FROM \n"
                          + "    PT_BA_WAREHOUSE_AREA \n"
                          + "WHERE WAREHOUSE_CODE ='" + pPtBaWarehouseVO.getWarehouseCode() + "' \n"
                          + where;
        // 删除库位表sql
        String positionString = "DELETE \n"
                              + "FROM \n"
                              + "    PT_BA_WAREHOUSE_POSITION \n"
                              + "WHERE \n"
                              + "    AREA_CODE IN ( \n"
                              + "        SELECT \n"
                              + "            AREA_CODE \n"
                              + "        FROM \n"
                              + "            PT_BA_WAREHOUSE_AREA \n"
                              + "       WHERE \n"
                              + "            WAREHOUSE_CODE='" + pPtBaWarehouseVO.getWarehouseCode() + "') \n"
                              + where;
        boolean positionFlag = factory.delete(positionString, null);
        boolean areaFlag = factory.delete(areaString, null);
        boolean warehouseFlag = factory.delete(warehouseString, null);
        return positionFlag&&areaFlag&&warehouseFlag;
    }

    /**
     * 仓库表(pt_ba_warehouse)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchWareHouse(PageManager pPageManager, User pUser, String pConditions) throws Exception {

        String wheres = pConditions;
        // 按所属公司,所属机构查询，并且按仓库代码升序排列
        wheres += " AND PBS.ORG_ID = TG.ORG_ID AND UPC.PARAKEY=to_char(PBS.WAREHOUSE_TYPE) AND PBS.OEM_COMPANY_ID = " + pUser.getOemCompanyId() + "\n"
                + " ORDER BY WAREHOUSE_CODE";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     PBS.WAREHOUSE_ID,\n");
        sql.append("     WAREHOUSE_CODE,\n");
        sql.append("     WAREHOUSE_NAME,\n");
        sql.append("     WAREHOUSE_STATUS,\n");
        sql.append("     WAREHOUSE_TYPE,\n");
        sql.append("     UPC.PARAVALUE1,\n");
        sql.append("     UPC.PARAKEY,\n");
        sql.append("     TG.ORG_ID,\n");
        sql.append("     TG.CODE,\n");
        sql.append("     TG.ONAME\n");
        sql.append(" FROM \n");
        sql.append("     PT_BA_WAREHOUSE PBS,USER_PARA_CONFIGURE UPC,TM_ORG TG \n");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("WAREHOUSE_STATUS", "YXBS");
        return baseResultSet;
    }

    /**
     * 仓库表(pt_ba_warehouse)查询
     *
     * @param pPtBaWarehouseVO 仓库实体
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet searchByWareHouseCode(PtBaWarehouseVO pPtBaWarehouseVO, User pUser) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     WAREHOUSE_CODE \n");
        sql.append(" FROM \n");
        sql.append("     PT_BA_WAREHOUSE \n");
        sql.append(" WHERE \n");
        sql.append("     WAREHOUSE_CODE='" + pPtBaWarehouseVO.getWarehouseCode() + "' \n");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }
}
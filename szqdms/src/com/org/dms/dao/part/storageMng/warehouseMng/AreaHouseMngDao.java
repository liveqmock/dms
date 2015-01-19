package com.org.dms.dao.part.storageMng.warehouseMng;

import com.org.dms.vo.part.PtBaWarehouseAreaVO;
import com.org.dms.vo.part.PtBaWarehousePositionVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 库区管理Dao
 * @author zhengyao
 * @date 2014-07-08
 * @version 1.0
 */
public class AreaHouseMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return AreaHouseMngDao
     */
    public static final AreaHouseMngDao getInstance(ActionContext pActionContext) {

        AreaHouseMngDao areaHouseMngDao = new AreaHouseMngDao();
        pActionContext.setDBFactory(areaHouseMngDao.factory);
        return areaHouseMngDao;
    }

    /**
     * 库区表(pt_ba_warehouse_area)添加
     *
     * @param pPtBaWarehouseAreaVO 库区实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertAreaHouse(PtBaWarehouseAreaVO pPtBaWarehouseAreaVO) throws Exception {

        return factory.insert(pPtBaWarehouseAreaVO);
    }

    /**
     * 库区表(pt_ba_warehouse_area),库位表(pt_ba_warehouse_position)修改
     *
     * @param pPtBaWarehouseAreaVO 库区实体
     * @param pPtBaWarehousePositionVO 库位实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateAreaHouse(PtBaWarehouseAreaVO pPtBaWarehouseAreaVO,PtBaWarehousePositionVO ptBaWarehousePositionVO,User pUser) throws Exception {

        String where = " AND OEM_COMPANY_ID = " + pUser.getOemCompanyId() + "\n";
        // 修改库区表sql
        String areaString = "UPDATE \n"
                          + "PT_BA_WAREHOUSE_AREA \n"
                          + "SET \n"
                          + "AREA_CODE='" + pPtBaWarehouseAreaVO.getAreaCode()+ "',\n"
                          + "AREA_NAME='" + pPtBaWarehouseAreaVO.getAreaName()+ "',\n"
                          + "AREA_STATUS='" + pPtBaWarehouseAreaVO.getAreaStatus()+ "',\n"
                          + "WAREHOUSE_ID='" + pPtBaWarehouseAreaVO.getWarehouseId()+ "',\n"
                          + "WAREHOUSE_CODE='" + pPtBaWarehouseAreaVO.getWarehouseCode()+ "',\n"
                          + "WAREHOUSE_NAME='" + pPtBaWarehouseAreaVO.getWarehouseName()+ "',\n"
                          + "AREA_ATTR='" + pPtBaWarehouseAreaVO.getAreaAttr()+ "',\n"
                          + "UPDATE_USER='" + pUser.getAccount()+ "',\n"
                          + "UPDATE_TIME=sysdate \n"
                          + "WHERE \n"
                          + "AREA_ID='" + pPtBaWarehouseAreaVO.getAreaId() + "'\n"
                          + where;
        // 修改库位表sql
        String positionString = "UPDATE \n"
                              + "PT_BA_WAREHOUSE_POSITION \n"
                              + "SET \n"
                              + "AREA_CODE='" + ptBaWarehousePositionVO.getAreaCode()+ "',\n"
                              + "AREA_NAME='" + ptBaWarehousePositionVO.getAreaName()+ "',\n"
                              + "UPDATE_USER='" + pUser.getAccount()+ "',\n"
                              + "UPDATE_TIME=sysdate \n"
                              + "WHERE \n"
                              + "AREA_ID='" + ptBaWarehousePositionVO.getAreaId() + "'\n"
                              + where;
        boolean positionFlag = factory.update(positionString, null);
        boolean areaFlag = factory.update(areaString, null);
        return areaFlag&&positionFlag;
    }

    /**
     * 库区表(pt_ba_warehouse_area),库位表(pt_ba_warehouse_position)删除
     *
     * @param pPtBaWarehouseAreaVO 库区实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean deleteAreaHouse(PtBaWarehouseAreaVO pPtBaWarehouseAreaVO,User pUser) throws Exception {

        String where = " AND OEM_COMPANY_ID = " + pUser.getOemCompanyId() + "\n";
        // 删除库区表sql
        String areaString = "DELETE \n"
                          + "FROM \n"
                          + "    PT_BA_WAREHOUSE_AREA \n"
                          + "WHERE \n"
                          + "    AREA_ID='" + pPtBaWarehouseAreaVO.getAreaId() + "' \n"
                          + where;
        // 删除库位表sql
        String positionString = "DELETE \n"
                              + "FROM \n"
                              + "    PT_BA_WAREHOUSE_POSITION \n"
                              + "WHERE \n"
                              + "    AREA_CODE='" + pPtBaWarehouseAreaVO.getAreaCode() + "' \n"
                                      + where;
        boolean positionFlag = factory.delete(positionString, null);
        boolean areaFlag = factory.delete(areaString, null);
        return positionFlag&&areaFlag;
    }

    /**
     * 库区表(pt_ba_warehouse_area)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pString  sql条件
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchAreaHouse(PageManager pPageManager, User pUser, String pString) throws Exception {

        String wheres = pString;
        //增加按当前公司、当前组织过滤条件
        wheres += " AND OEM_COMPANY_ID = " + pUser.getOemCompanyId() + "\n";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     PBSA.AREA_ID,\n");
        sql.append("     PBSA.AREA_CODE,\n");
        sql.append("     PBSA.AREA_NAME,\n");
        sql.append("     PBSA.AREA_STATUS,\n");
        sql.append("     PBSA.WAREHOUSE_ID,\n");
        sql.append("     PBSA.WAREHOUSE_CODE,\n");
        sql.append("     PBSA.WAREHOUSE_NAME,\n");
        sql.append("     PBSA.AREA_ATTR,\n");
        sql.append("     PBSA.KEEP_MAN\n");
        sql.append(" FROM \n");
        sql.append("     PT_BA_WAREHOUSE_AREA PBSA \n");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        return baseResultSet;
    }

    /**
     * 库区表(pt_ba_warehouse_area)查询
     *
     * @param ptBaWarehouseAreaVO 库区实体
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet searchByAreaCode(PtBaWarehouseAreaVO ptBaWarehouseAreaVO, User pUser) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     AREA_CODE \n");
        sql.append(" FROM \n");
        sql.append("     PT_BA_WAREHOUSE_AREA \n");
        sql.append(" WHERE \n");
        sql.append("     AREA_CODE='" + ptBaWarehouseAreaVO.getAreaCode() + "' \n");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }
}
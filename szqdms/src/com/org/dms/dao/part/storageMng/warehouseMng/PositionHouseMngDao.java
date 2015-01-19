package com.org.dms.dao.part.storageMng.warehouseMng;

import com.org.dms.vo.part.PtBaWarehousePositionVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 库位管理Dao
 *
 * @author zhengyao
 * @date 2014-07-08
 * @version 1.0
 */
public class PositionHouseMngDao extends BaseDAO {

    /**
     * 定义instance
     * @param pActionContext
     * @return positionHouseMngDao 库位管理Dao
     */
    public static final PositionHouseMngDao getInstance(ActionContext pActionContext) {

        PositionHouseMngDao positionHouseMngDao = new PositionHouseMngDao();
        pActionContext.setDBFactory(positionHouseMngDao.factory);
        return positionHouseMngDao;
    }

    /**
     * 库位表(pt_ba_wareahouse_position)添加
     *
     * @param pPtBaWarehousePositionVO 库位实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertPositionHouse(PtBaWarehousePositionVO pPtBaWarehousePositionVO) throws Exception {

        return factory.insert(pPtBaWarehousePositionVO);
    }

    /**
     * 库位表(pt_ba_wareahouse_position)修改
     *
     * @param pPtBaWarehousePositionVO 库位实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updatePositionHouse(PtBaWarehousePositionVO pPtBaWarehousePositionVO,User pUser) throws Exception {

        String where = " AND OEM_COMPANY_ID = " + pUser.getOemCompanyId() + "\n";
        // 修改库区表sql
        String areaString = "UPDATE \n"
                          + "PT_BA_WAREHOUSE_POSITION \n"
                          + "SET \n"
                          + "POSITION_CODE='" + pPtBaWarehousePositionVO.getPositionCode()+ "',\n"
                          + "POSITION_NAME='" + pPtBaWarehousePositionVO.getPositionName()+ "',\n"
                          + "POSITION_STATUS='" + pPtBaWarehousePositionVO.getPositionStatus()+ "',\n"
                          + "AREA_ID='" + pPtBaWarehousePositionVO.getAreaId()+ "',\n"
                          + "AREA_CODE='" + pPtBaWarehousePositionVO.getAreaCode()+ "',\n"
                          + "AREA_NAME='" + pPtBaWarehousePositionVO.getAreaName()+ "',\n"
                          + "UPDATE_USER='" + pUser.getAccount()+ "',\n"
                          + "UPDATE_TIME=sysdate \n"
                          + "WHERE \n"
                          + "POSITION_ID='" + pPtBaWarehousePositionVO.getPositionId() + "'\n"
                          + where;
        return factory.update(areaString,null);
    }

    /**
     * 库位表(pt_ba_wareahouse_position)删除
     *
     * @param pPtBaWarehousePositionVO 库位实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean deletePositionHouse(PtBaWarehousePositionVO pPtBaWarehousePositionVO,User pUser) throws Exception {

        String where = " AND OEM_COMPANY_ID = " + pUser.getOemCompanyId() + "\n";
        // 删除库位表sql
        String areaString = "DELETE \n"
                          + "FROM \n"
                          + "    PT_BA_WAREHOUSE_POSITION \n"
                          + "WHERE \n"
                          + "    POSITION_ID='" + pPtBaWarehousePositionVO.getPositionId() + "' \n"
                          + where;
        return factory.delete(areaString,null);
    }

    /**
     * 库位表(pt_ba_wareahouse_position)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return BaseResultSet 库位表的结果集
     * @throws Exception
     */
    public BaseResultSet searchPositionHouse(PageManager pPageManager, User pUser, String pConditions) throws Exception {

        String wheres = pConditions;
        //增加按当前公司、当前组织过滤条件
        wheres += " AND PBSP.COMPANY_ID=" + pUser.getCompanyId() 
                + " AND PBSP.AREA_CODE=PBSA.AREA_CODE "
                + " AND PBSP.OEM_COMPANY_ID = " + pUser.getOemCompanyId() + "\n"
                + " ORDER BY PBSP.POSITION_CODE";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT PBSP.POSITION_ID,\n");
        sql.append("        PBSP.POSITION_CODE,\n");
        sql.append("        PBSP.POSITION_NAME,\n");
        sql.append("        PBSP.POSITION_STATUS,\n");
        sql.append("        PBSP.AREA_ID,\n");
        sql.append("        PBSP.AREA_CODE,\n");
        sql.append("        PBSP.AREA_NAME,PBSA.WAREHOUSE_CODE,PBSA.WAREHOUSE_NAME \n");
        sql.append(" FROM \n");
        sql.append("        PT_BA_WAREHOUSE_POSITION PBSP,PT_BA_WAREHOUSE_AREA PBSA ");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("AREA_STATUS", "YXBS");
        return baseResultSet;
    }

    /**
     * 库位表(pt_ba_wareahouse_position)查询
     *
     * @param ptBaWarehousePositionVO 库位实体
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet searchByPositionCode(PtBaWarehousePositionVO ptBaWarehousePositionVO, User pUser) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     POSITION_CODE \n");
        sql.append(" FROM \n");
        sql.append("     PT_BA_WAREHOUSE_POSITION \n");
        sql.append(" WHERE \n");
        sql.append("     POSITION_CODE='" + ptBaWarehousePositionVO.getPositionCode() + "' \n");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }
}
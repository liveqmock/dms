package com.org.dms.dao.part.storageMng.stockMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuStockDtlVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

import java.util.Map;

/**
 * 移位管理Dao
 *
 * @user : lichuang
 * @date : 2014-07-14
 */
public class StockMoveDao extends BaseDAO {
    //定义instance
    public static final StockMoveDao getInstance(ActionContext atx) {
        StockMoveDao dao = new StockMoveDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 查询库存明细
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchStockDtl(PageManager page, User user, String conditions) throws Exception {
        String wheres = conditions;
        wheres += " AND PBS.STOCK_ID = PBSD.STOCK_ID\n";
        wheres += " AND PBS.PART_ID||PBS.WAREHOUSE_ID IN (SELECT PART_ID||WAREHOUSE_ID FROM PT_BA_WAREHOUSE_KEEPER WHERE USER_ACCOUNT='"+user.getAccount()+"')";
        wheres += " AND PBS.STOCK_STATUS = "+ DicConstant.KCZT_01+"\n";
        wheres += " AND PBS.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n";
        wheres += " ORDER BY PBS.PART_CODE ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT PBS.STOCK_ID,\n" );
        sql.append("       PBS.PART_ID,\n" );
        sql.append("       PBS.PART_CODE,\n" );
        sql.append("       PBS.PART_NAME,\n" );
        sql.append("       PBS.WAREHOUSE_ID,\n" );
        sql.append("       PBS.WAREHOUSE_CODE,\n" );
        sql.append("       PBS.WAREHOUSE_NAME,\n" );
        sql.append("       PBSD.DTL_ID,\n" );
        sql.append("       PBSD.AREA_ID,\n" );
        sql.append("       PBSD.AREA_CODE,\n" );
        sql.append("       PBSD.AREA_NAME,\n" );
        sql.append("       PBSD.POSITION_ID,\n" );
        sql.append("       PBSD.POSITION_CODE,\n" );
        sql.append("       PBSD.POSITION_NAME,\n" );
        sql.append("       PBSD.AMOUNT,\n" );
        sql.append("       PBSD.OCCUPY_AMOUNT,\n" );
        sql.append("       PBSD.AVAILABLE_AMOUNT,\n" );
        sql.append("       PBSD.SUPPLIER_ID,\n" );
        sql.append("       PBSD.SUPPLIER_CODE,\n" );
        sql.append("       PBSD.SUPPLIER_NAME\n" );
        sql.append("  FROM PT_BU_STOCK PBS,PT_BU_STOCK_DTL PBSD\n" );

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
//        bs.setFieldDic("STOCK_STATUS", "KCZT");
        return bs;
    }

    /**
     * 修改或新增库存明细(该配件的目标库位等信息在库存明细中存在则更新,否则新增)
     * @param user
     * @param map
     * @return
     * @throws Exception
     */
    public boolean insertOrUpdateStockDtl(User user,Map<String,String> map) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("MERGE INTO PT_BU_STOCK_DTL A\n" );
        sql.append("USING (SELECT COUNT(*) COUNT\n" );
        sql.append("         FROM PT_BU_STOCK_DTL B\n" );
        sql.append("        WHERE B.PART_ID = "+map.get("PART_ID")+"\n" );
        sql.append("          AND B.SUPPLIER_ID = "+map.get("SUPPLIER_ID")+"\n" );
        sql.append("          AND B.POSITION_ID = "+map.get("DES_POSITION_ID")+") C\n" );
        sql.append("ON (C.COUNT > 0)\n");
        sql.append("WHEN MATCHED THEN\n" );
        sql.append("  UPDATE SET\n" );
        sql.append("         AMOUNT = AMOUNT + "+map.get("MOVE_AMOUNT")+",\n" );
        sql.append("         AVAILABLE_AMOUNT = AVAILABLE_AMOUNT + "+map.get("MOVE_AMOUNT")+",\n" );
        sql.append("         UPDATE_USER = '"+user.getAccount()+"',\n" );
        sql.append("         UPDATE_TIME = SYSDATE\n" );
        sql.append("   WHERE PART_ID = "+map.get("PART_ID")+"\n" );
        sql.append("     AND SUPPLIER_ID = "+map.get("SUPPLIER_ID")+"\n" );
        sql.append("     AND POSITION_ID = "+map.get("DES_POSITION_ID")+"\n" );
        sql.append("WHEN NOT MATCHED THEN\n" );
        sql.append("  INSERT\n" );
        sql.append("    (DTL_ID,\n" );
        sql.append("     STOCK_ID,\n" );
        sql.append("     PART_ID,\n" );
        sql.append("     PART_CODE,\n" );
        sql.append("     PART_NAME,\n" );
        sql.append("     SUPPLIER_ID,\n" );
        sql.append("     SUPPLIER_CODE,\n" );
        sql.append("     SUPPLIER_NAME,\n" );
        sql.append("     AREA_ID,\n" );
        sql.append("     AREA_CODE,\n" );
        sql.append("     AREA_NAME,\n" );
        sql.append("     POSITION_ID,\n" );
        sql.append("     POSITION_CODE,\n" );
        sql.append("     POSITION_NAME,\n" );
        sql.append("     AMOUNT,\n" );
        sql.append("     OCCUPY_AMOUNT,\n" );
        sql.append("     AVAILABLE_AMOUNT,\n" );
        sql.append("     CREATE_USER,\n" );
        sql.append("     CREATE_TIME)\n" );
        sql.append("  VALUES\n" );
        sql.append("  (F_GETID(),\n" );
        sql.append("   "+map.get("STOCK_ID")+",\n" );
        sql.append("   "+map.get("PART_ID")+",\n" );
        sql.append("   '"+map.get("PART_CODE")+"',\n" );
        sql.append("   '"+map.get("PART_NAME")+"',\n" );
        sql.append("   '"+map.get("SUPPLIER_ID")+"',\n" );
        sql.append("   '"+map.get("SUPPLIER_CODE")+"',\n" );
        sql.append("   '"+map.get("SUPPLIER_NAME")+"',\n" );
        sql.append("   "+map.get("DES_AREA_ID")+",\n" );
        sql.append("   '"+map.get("DES_AREA_CODE")+"',\n" );
        sql.append("   '"+map.get("DES_AREA_NAME")+"',\n" );
        sql.append("   "+map.get("DES_POSITION_ID")+",\n" );
        sql.append("   '"+map.get("DES_POSITION_CODE")+"',\n" );
        sql.append("   '"+map.get("DES_POSITION_NAME")+"',\n" );
        sql.append("   "+map.get("MOVE_AMOUNT")+",\n" );
        sql.append("   0,\n" );
        sql.append("   "+map.get("MOVE_AMOUNT")+",\n" );
        sql.append("   '"+user.getAccount()+"',\n" );
        sql.append("   SYSDATE)\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 修改库存明细
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateStockDtl(PtBuStockDtlVO vo) throws Exception {
        return factory.update(vo);
    }
    
    public QuerySet checkLock1(String partIds,String STOCK_ID)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT to_char(wm_concat( PART_CODE)) CODES FROM PT_BU_STOCK WHERE PART_ID IN ("+partIds+") AND STOCK_STATUS = "+DicConstant.KCZT_02+" AND STOCK_ID = "+STOCK_ID+"\n" );
        return factory.select(null, sql.toString());
    }
    public QuerySet checkLock2(String partIds,String STOCK_ID)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT to_char(wm_concat( PART_CODE)) CODES FROM PT_BU_STOCK WHERE PART_ID IN ("+partIds+") AND STOCK_STATUS = "+DicConstant.KCZT_03+" AND STOCK_ID = "+STOCK_ID+"\n" );
        return factory.select(null, sql.toString());
    }
}
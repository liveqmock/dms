package com.org.dms.dao.part.storageMng.shipMng;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.vo.part.PtBuOrderShipDtlVO;
import com.org.dms.vo.part.PtBuOrderShipVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 发运单维护Dao
 *
 * @user : lichuang
 * @date : 2014-07-21
 */
public class ShipMngDao extends BaseDAO {
    //定义instance
    public static final ShipMngDao getInstance(ActionContext atx) {
        ShipMngDao dao = new ShipMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 查询发运单
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchShip(PageManager page, User user, String conditions,String status) throws Exception {
        String wheres = conditions;
        wheres += " AND A.SHIP_STATUS ="+status+"\n";
        wheres += " AND A.STATUS = " + DicConstant.YXBS_01 + "\n";
        wheres += " AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n";
        wheres += " ORDER BY A.CREATE_TIME DESC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT A.SHIP_ID,\n" );
        sql.append("       A.SHIP_NO,\n" );
        sql.append("       A.SHIP_STATUS,\n" );
        sql.append("       A.CARRIER_ID,\n" );
        sql.append("       A.CARRIER_CODE,\n" );
        sql.append("       A.CARRIER_NAME,\n" );
        sql.append("       A.LINK_MAN,\n" );
        sql.append("       A.PHONE,\n" );
        sql.append("       A.REMARKS,\n" );
        sql.append("       A.DRIVER_NAME,\n" );
        sql.append("       A.LICENSE_PLATE,\n" );
        sql.append("       A.CREATE_USER,\n" );
        sql.append("       A.CREATE_TIME,\n" );
        sql.append("       A.CONFIRM_USER,\n" );
        sql.append("       A.CONFIRM_TIME,\n" );
        sql.append("       A.STATUS,\n" );
        sql.append("       A.IF_ARMY\n" );
        sql.append("  FROM PT_BU_ORDER_SHIP A\n" );

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("STATUS","YXBS");
        bs.setFieldDic("SHIP_STATUS","FYDZT");
        bs.setFieldUserID("CREATE_USER");
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("CONFIRM_TIME", "yyyy-MM-dd");
        bs.setFieldDic("IF_ARMY", "SF");
        return bs;
    }
    
    public BaseResultSet searchShip1(PageManager page, User user, String conditions,String shipStatus) throws Exception {
        String wheres = conditions;
        wheres += " AND A.SHIP_ID = B.SHIP_ID AND A.SHIP_STATUS ="+shipStatus+"\n";
        wheres += " AND A.STATUS = " + DicConstant.YXBS_01 + "\n";
        wheres += " AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n";
        wheres += " ORDER BY A.CREATE_TIME DESC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.SHIP_ID,\n" );
        sql.append("       A.SHIP_NO,\n" );
        sql.append("       A.SHIP_STATUS,\n" );
        sql.append("       A.CARRIER_ID,\n" );
        sql.append("       A.CARRIER_CODE,\n" );
        sql.append("       A.CARRIER_NAME,\n" );
        sql.append("       A.LINK_MAN,\n" );
        sql.append("       A.PHONE,\n" );
        sql.append("       A.REMARKS,\n" );
        sql.append("       B.DRIVER_NAME,\n" );
        sql.append("       B.LICENSE_PLATE,\n" );
        sql.append("       A.CREATE_USER,\n" );
        sql.append("       A.CREATE_TIME,\n" );
        sql.append("       A.CONFIRM_USER,\n" );
        sql.append("       A.CONFIRM_TIME,\n" );
        sql.append("       A.STATUS,\n" );
        sql.append("       A.IF_ARMY,\n" );
        sql.append("       B.RECEIPT_NO\n" );
        sql.append("  FROM PT_BU_ORDER_SHIP A,PT_BU_ORDER_SHIP_CARRIER B");


        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("STATUS","YXBS");
        bs.setFieldDic("SHIP_STATUS","FYDZT");
        bs.setFieldUserID("CREATE_USER");
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("CONFIRM_TIME", "yyyy-MM-dd");
        bs.setFieldDic("IF_ARMY", "SF");
        return bs;
    }

    /**
     * 查询销售订单
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchSale(PageManager page, User user, String conditions,String ifArmy) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);

        wheres.append("   AND A.ORDER_ID = B.ORDER_ID\n" );
        if(ifArmy.equals(DicConstant.SF_01)){
        	wheres.append("   AND A.ORDER_TYPE = "+DicConstant.DDLX_08+"\n" );
        }else {
        	wheres.append("   AND A.ORDER_TYPE <> "+DicConstant.DDLX_08+"\n" );
		}
        wheres.append("   AND A.ORDER_ID = C.ORDER_ID\n" );
        wheres.append("   AND B.PART_ID = C.PART_ID\n" );
        wheres.append("   AND A.SHIP_STATUS = "+DicConstant.DDFYZT_04+"\n" );
        wheres.append("   AND NOT EXISTS\n" );
        wheres.append(" (SELECT 1 FROM PT_BU_ORDER_SHIP_DTL D WHERE D.ORDER_ID = A.ORDER_ID)\n" );
        wheres.append("   AND A.STATUS = " + DicConstant.YXBS_01 + "\n" );
        wheres.append("   AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n" );
        wheres.append(" GROUP BY A.ORDER_ID,\n" );
        wheres.append("          A.ORDER_NO,\n" );
        wheres.append("          A.ORDER_TYPE,\n" );
        wheres.append("          A.ORG_NAME,\n" );
        wheres.append("          A.DELIVERY_ADDR,\n" );
        wheres.append("          A.LINK_MAN,\n");
        wheres.append("          A.PHONE,\n");
        wheres.append("          A.CREATE_TIME\n");
        wheres.append(" ORDER BY A.CREATE_TIME DESC\n");

        page.setFilter(wheres.toString());
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT A.ORDER_ID,\n" );
        sql.append("       A.ORDER_NO,\n" );
        sql.append("       A.ORDER_TYPE,\n" );
        sql.append("       A.ORG_NAME,\n" );
        sql.append("       SUM(NVL(C.COUNT, 0)) COUNT,\n" );
        sql.append("       SUM(NVL(C.COUNT, 0) * B.UNIT_PRICE) AMOUNT,\n" );
        sql.append("       A.DELIVERY_ADDR,\n" );
        sql.append("       A.LINK_MAN,\n" );
        sql.append("       A.PHONE\n" );
        sql.append("  FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B, PT_BU_BOX_UP C\n" );

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("ORDER_TYPE",DicConstant.DDLX);
        return bs;
    }

    /**
     * 查询发运单明细
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchShipDtl(PageManager page, User user, String conditions, String shipId) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);

        wheres.append("   AND A.ORDER_ID = B.ORDER_ID\n" );
        wheres.append("   AND A.ORDER_ID = C.ORDER_ID\n" );
        wheres.append("   AND B.PART_ID = C.PART_ID\n" );
        wheres.append("   AND A.ORDER_ID = D.ORDER_ID\n" );
        wheres.append("   AND D.SHIP_ID = "+shipId+"\n" );
        wheres.append("   AND A.STATUS = " + DicConstant.YXBS_01 + "\n" );
        wheres.append("   AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n" );
        wheres.append(" GROUP BY A.ORDER_ID,\n" );
        wheres.append("          A.ORDER_NO,\n" );
        wheres.append("          A.ORDER_TYPE,\n" );
        wheres.append("          A.ORG_NAME,\n" );
        wheres.append("          A.DELIVERY_ADDR,\n" );
        wheres.append("          A.LINK_MAN,\n");
        wheres.append("          A.PHONE,\n");
        wheres.append("          A.CREATE_TIME,\n");
        wheres.append("          D.DTL_ID\n");
        wheres.append(" ORDER BY A.CREATE_TIME DESC\n");

        page.setFilter(wheres.toString());
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT A.ORDER_ID,\n" );
        sql.append("       A.ORDER_NO,\n" );
        sql.append("       A.ORDER_TYPE,\n" );
        sql.append("       A.ORG_NAME,\n" );
        sql.append("       SUM(NVL(C.COUNT, 0)) COUNT,\n" );
        sql.append("       SUM(NVL(C.COUNT, 0) * B.UNIT_PRICE) AMOUNT,\n" );
        sql.append("       A.DELIVERY_ADDR,\n" );
        sql.append("       A.LINK_MAN,\n" );
        sql.append("       A.PHONE,\n" );
        sql.append("       D.DTL_ID\n" );
        sql.append("  FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B, PT_BU_BOX_UP C, PT_BU_ORDER_SHIP_DTL D\n" );

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("ORDER_TYPE",DicConstant.DDLX);
        return bs;
    }

    /**
     * 新增发运单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertShip(PtBuOrderShipVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 修改发运单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateShip(PtBuOrderShipVO vo)
            throws Exception {
        return factory.update(vo);
    }

    /**
     * 删除发运单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean deleteShip(PtBuOrderShipVO vo)
            throws Exception {
        return factory.delete(vo);
    }

    /**
     * 删除发运单明细
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean deleteShipDtl(PtBuOrderShipDtlVO vo)
            throws Exception {
        return factory.delete(vo);
    }

    /**
     * 删除发运单明细
     *
     * @param shipId 发运单ID
     * @return
     * @throws Exception
     */
    public boolean deleteShipDtl(String shipId) throws Exception {
        StringBuilder sql= new StringBuilder();
        sql.append("DELETE FROM PT_BU_ORDER_SHIP_DTL WHERE SHIP_ID = "+shipId+"\n");
        return factory.delete(sql.toString(), null);
    }

    /**
     * 新增发运单明细
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertShipDtl(PtBuOrderShipDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 修改销售订单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateSaleOrder(PtBuSaleOrderVO vo)
            throws Exception {
        return factory.update(vo);
    }

    /**
     * 导出运输清单
     *
     * @param SHIP_ID
     * @return
     * @throws Exception
     */
    public QuerySet exportTransDtl(String SHIP_ID) throws Exception {

        //定义返回结果集
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT T.*,ROWNUM FROM(\n" );
        sql.append("SELECT A.PART_ID,\n" );
        sql.append("       A.PART_CODE,\n" );
        sql.append("       A.PART_NAME,\n" );
        sql.append("       D.DIC_VALUE UNIT,\n" );
        sql.append("       B.MIN_PACK,\n" );
        sql.append("       A.SUPPLIER_ID,\n" );
        sql.append("       A.SUPPLIER_CODE,\n" );
        sql.append("       DECODE(A.SUPPLIER_CODE,'9XXXXXX','',A.SUPPLIER_NAME) SUPPLIER_NAME,\n");
        sql.append("       A.ISSUE_ID,\n" );
        sql.append("       A.ISSUE_NO,\n" );
        sql.append("       A.SHOULD_COUNT,\n" );
        sql.append("       A.REAL_COUNT,\n" );
        sql.append("       C.BOX_NO,\n" );
        sql.append("       C.COUNT,\n" );
        sql.append("       C.REMARKS\n" );
        sql.append("  FROM (SELECT ORDER_ID,PART_ID,PART_CODE,PART_NAME,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,ISSUE_ID,ISSUE_NO,SUM(SHOULD_COUNT) SHOULD_COUNT,SUM(REAL_COUNT) REAL_COUNT FROM PT_BU_ISSUE_ORDER_DTL GROUP BY ORDER_ID,PART_ID,PART_CODE,PART_NAME,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,ISSUE_ID,ISSUE_NO) A,\n" );
        sql.append("       PT_BA_INFO B,\n" );
        sql.append("       PT_BU_BOX_UP C,\n" );
        sql.append("       DIC_TREE D\n" );
        sql.append("   WHERE 1=1\n" );
        sql.append("    AND A.ORDER_ID = C.ORDER_ID\n" );
        sql.append("    AND A.PART_ID = B.PART_ID\n" );
        sql.append("    AND A.PART_ID = C.PART_ID\n" );
        sql.append("    AND A.ISSUE_ID = C.ISSUE_ID\n" );
        sql.append("    AND B.UNIT = D.ID\n" );
        sql.append("    AND EXISTS(SELECT 1 FROM PT_BU_ORDER_SHIP_DTL D WHERE D.ORDER_ID = A.ORDER_ID AND D.SHIP_ID = "+SHIP_ID+")\n" );
        sql.append("   ORDER BY A.PART_CODE ASC) T\n");

        return factory.select(null,sql.toString());
    }

    /**
     * 校验发运单明细是否存在
     *
     * @param shipId  发运单ID
     * @return
     * @throws Exception
     */
    public Boolean checkShipDtlIsExist(String shipId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.DTL_ID\n");
        sql.append("  FROM PT_BU_ORDER_SHIP_DTL A\n");
        sql.append(" WHERE A.SHIP_ID = " + shipId + "\n");

        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 生成发运单号
     * @return
     * @throws Exception
     */
    public String createShipNo() throws Exception{
        return PartOddNumberUtil.getShipNo(factory);
    }
    
    /**
     * 发运单回执确认
     * @throws Exception
     * @Author suoxiuli 2014-09-13
     */
    public boolean recieptConfirmShip(String shipId, String confirmUser, String shipStatus) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update PT_BU_ORDER_SHIP \n");
    	sql.append(" set SHIP_STATUS = '" + shipStatus + "', \n");
    	sql.append(" CONFIRM_USER = '" + confirmUser + "', \n");
    	sql.append(" CONFIRM_time = sysdate \n");
    	sql.append(" where SHIP_ID = '" + shipId + "' \n");
    	return factory.update(sql.toString(), null);
    }
    
    public BaseResultSet getCarrier(PageManager page, User user, String conditions) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	BaseResultSet bs = null;
    	sql.append("SELECT T.CARRIER_ID, T.CARRIER_NAME, T.CARRIER_CODE, T.LINK_MAN, T.PHONE\n" );
    	sql.append("  FROM PT_BA_CARRIER T\n" );
    	sql.append(" WHERE T.IF_ARMY ="+DicConstant.SF_01+"\n");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    
}
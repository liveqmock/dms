package com.org.dms.dao.part.salesMng.shipMent;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuChangeShipAddressVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.frameImpl.Constant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class DeliveAddChageDao extends BaseDAO
{
    /**
     * 定义instance
     * 
     * @param atx
     * @return
     */
    public static final DeliveAddChageDao getInstance(ActionContext atx) {
        DeliveAddChageDao dao = new DeliveAddChageDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 配件收货地址变更表(PT_BU_CHANGE_SHIP_ADDRESS)
     *
     * @param ptBuForcastVO 配件预测实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean deliveryaddrApply(PtBuChangeShipAddressVO pPtBuChangeShipAddressVO, User pUser) throws Exception {

        String where = " AND OEM_COMPANY_ID=" + pUser.getOemCompanyId() + "\n";
        // 修改配件收货地址变更表sql
        String forecastString = "UPDATE \n"
                              + "    PT_BU_CHANGE_SHIP_ADDRESS \n"
                              + "SET \n"
                              + "    CHANGE_STATUS='" + pPtBuChangeShipAddressVO.getChangeStatus()+ "',\n"
                              + "    UPDATE_USER='" + pUser.getAccount()+ "',\n"
                              + "    UPDATE_TIME=sysdate \n"
                              + "WHERE \n"
                              + "    ADD_ID='" + pPtBuChangeShipAddressVO.getAddId() + "'\n"
                              + where;
        return factory.update(forecastString, null);
    }

    /**
     * 配件收货地址变更表(PT_BU_CHANGE_SHIP_ADDRESS)修改
     *
     * @param pPtBuChangeShipAddressVO 库区实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean deliveryaddrUpdate(PtBuChangeShipAddressVO pPtBuChangeShipAddressVO, User pUser) throws Exception {

        return factory.update(pPtBuChangeShipAddressVO);
    }
    /**
     * 配件收货地址变更表(PT_BU_CHANGE_SHIP_ADDRESS)删除
     *
     * @param pPtBuChangeShipAddressVO 配件收货地址变更实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean deleteChangeShipAddress(PtBuChangeShipAddressVO pPtBuChangeShipAddressVO, User pUser) throws Exception {

        // 按所属公司,所属机构删除
        String wheres = " AND ORG_ID = " + pUser.getOrgId() + "\n";
        // 配件收货地址变更表sql
        String stockString = "DELETE \n"
                           + "FROM \n"
                           + "    PT_BU_CHANGE_SHIP_ADDRESS \n"
                           + "WHERE \n"
                           + "    ADD_ID='" + pPtBuChangeShipAddressVO.getAddId() + "'"
                           + wheres;
        return factory.delete(stockString, null);
    }

    /**
     * 配件收货地址变更申请查询
     *
     * @param pPageManager
     * @param pUser 当前登录user对象
     * @param pConditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchDeliveryaddr(PageManager pPageManager, User pUser, String pConditions) throws Exception {

        String wheres = pConditions;
        // 查询
        wheres += " AND A.ORG_ID = '" + pUser.getOrgId() + "'\n"
                + " AND A.ORDER_ID=B.ORDER_ID\n"
                + " AND A.STATUS = '" + DicConstant.YXBS_01 + "'\n"
                + " AND A.CHANGE_STATUS IN ('" + DicConstant.JHDZBGZT_01+ "','"+DicConstant.JHDZBGZT_04+"')";
        pPageManager.setFilter(wheres);
        // 定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT A.ADD_ID,\n" );
        sql.append("       A.ADDRESS_ID,\n" );
        sql.append("       A.ADDRESS,\n" );
        sql.append("       A.LINK_MAN,\n" );
        sql.append("       A.ZIP_CODE,\n" );
        sql.append("       A.PHONE,\n" );
        sql.append("       A.ORDER_ID,\n" );
        sql.append("       A.ORDER_NO,\n" );
        sql.append("       A.CHANGE_STATUS,\n" );
        sql.append("       A.COMPANY_ID,\n" );
        sql.append("       A.ORG_ID,\n" );
        sql.append("       A.CREATE_USER,\n" );
        sql.append("       A.CREATE_TIME,\n" );
        sql.append("       A.UPDATE_USER,\n" );
        sql.append("       A.UPDATE_TIME,\n" );
        sql.append("       A.STATUS,\n" );
        sql.append("       A.OEM_COMPANY_ID,\n" );
        sql.append("       A.SECRET_LEVEL,\n" );
        sql.append("       A.PROVINCE_CODE,\n" );
        sql.append("       A.PROVINCE_NAME,\n" );
        sql.append("       A.CITY_CODE,\n" );
        sql.append("       A.CITY_NAME,\n" );
        sql.append("       A.COUNTRY_CODE,\n" );
        sql.append("       A.COUNTRY_NAME,\n" );
        sql.append("       A.TELEPHONE,\n" );
        sql.append("       B.APPLY_DATE,\n" );
        sql.append("       B.ORDER_TYPE,\n" );
        sql.append("       B.DELIVERY_ADDR\n" );
        sql.append("  FROM PT_BU_CHANGE_SHIP_ADDRESS A,PT_BU_SALE_ORDER B");
        baseResultSet= factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("CHANGE_STATUS", DicConstant.JHDZBGZT);
        baseResultSet.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
        return baseResultSet;
    }

    /**
     * 订单查询
     *
     * @param page
     * @param pUser 当前登录user对象
     * @param 查询为的sql
     * @return
     * @throws Exception
     */
    public BaseResultSet ordersearch(PageManager page, User pUser, String conditions) throws Exception {

        String wheres = conditions;
        // 查询订单状态不是(已发运,已签收)订单
        wheres += "\n AND NVL(PBO.SHIP_STATUS, 0) < "+DicConstant.DDFYZT_03+""
        	   +  "\n AND PBO.STATUS ="+ Constant.YXBS_01
        	   +  "\n AND PBO.IF_CHANEL_ORDER ="+ Constant.SF_02
        	   +  "\n AND PBO.IF_DELAY_ORDER ="+ Constant.SF_02
        	   +  "\n AND PBO.ORDER_STATUS IN("+DicConstant.DDZT_02+", "+DicConstant.DDZT_03+")"
               +  "\n AND PBO.ORG_ID ="+pUser.getOrgId()
               +  "\n AND NOT EXISTS (SELECT 1 FROM PT_BU_CHANGE_SHIP_ADDRESS T WHERE PBO.ORDER_ID = T.ORDER_ID AND T.CHANGE_STATUS != "+DicConstant.JHDZBGZT_03+")";
        page.setFilter(wheres);
        // 定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT PBO.ORDER_ID,\n" );
        sql.append("       PBO.ORDER_NO,\n" );
        sql.append("       PBO.COMPANY_ID,\n" );
        sql.append("       PBO.APPLY_DATE,\n" );
        sql.append("       PBO.DELIVERY_ADDR,\n" );
        sql.append("       PBO.ORDER_AMOUNT,\n" );
        sql.append("       PBO.ORDER_TYPE\n" );
        sql.append("  FROM PT_BU_SALE_ORDER PBO");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("order_type", "DDLX");
        bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
        return bs;
    }

    /**
     * 配件收货地址变更审核查询
     *
     * @param page
     * @param pUser 当前登录user对象
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet orderExasearch(PageManager page, User user, String conditions) throws Exception {
        String wheres = conditions;
        wheres += "\n AND A.ORDER_ID=B.ORDER_ID "
                + "\n AND B.SHIP_STATUS NOT IN ("+DicConstant.DDFYZT_06+", "+DicConstant.DDFYZT_07+")"
                + "\n AND A.CHANGE_STATUS='"+DicConstant.JHDZBGZT_02+"'"
                + "\n AND A.STATUS='"+DicConstant.YXBS_01+"'"
                + "\n AND B.STATUS='"+DicConstant.YXBS_01+"'"
                + "\n AND B.ORG_ID IN (SELECT ORG_ID FROM TM_ORG WHERE PID IN (SELECT ORG_ID FROM PT_BA_ORDER_CHECK WHERE USER_ACCOUNT='"+user.getAccount()+"'))"
                + "\n ORDER BY ORDER_NO";
        page.setFilter(wheres);
        // 定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT A.ADD_ID,\n" );
        sql.append("       A.ADDRESS,\n" );
        sql.append("       A.PROVINCE_CODE,\n" );
        sql.append("       A.PROVINCE_NAME,\n" );
        sql.append("       A.CITY_CODE,\n" );
        sql.append("       A.CITY_NAME,\n" );
        sql.append("       A.COUNTRY_CODE,\n" );
        sql.append("       A.COUNTRY_NAME,\n" );
        sql.append("       A.TELEPHONE,\n" );
        sql.append("       A.LINK_MAN,\n" );
        sql.append("       A.ZIP_CODE,\n" );
        sql.append("       A.PHONE,\n" );
        sql.append("       A.ORDER_ID,\n" );
        sql.append("       A.ORDER_NO,\n" );
        sql.append("       A.CHECK_USER,\n" );
        sql.append("       A.CHECK_TIME,\n" );
        sql.append("       A.CHANGE_STATUS,\n" );
        sql.append("       A.COMPANY_ID,\n" );
        sql.append("       A.ORG_ID,\n" );
        sql.append("       A.CREATE_USER,\n" );
        sql.append("       A.CREATE_TIME,\n" );
        sql.append("       A.UPDATE_USER,\n" );
        sql.append("       A.UPDATE_TIME,\n" );
        sql.append("       A.STATUS,\n" );
        sql.append("       A.OEM_COMPANY_ID,\n" );
        sql.append("       A.SECRET_LEVEL,\n" );
        sql.append("       B.DELIVERY_ADDR,\n" );
        sql.append("       B.ORDER_TYPE,\n" );
        sql.append("       B.APPLY_DATE,\n" );
        sql.append("       B.ORG_CODE,\n" );
        sql.append("       B.ORG_NAME,\n" );
        sql.append("       B.SHIP_STATUS\n" );
        sql.append("  FROM PT_BU_CHANGE_SHIP_ADDRESS A,PT_BU_SALE_ORDER B\n" );
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
        bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
        bs.setFieldDic("CHANGE_STATUS", DicConstant.JHDZBGZT);
        return bs;
    }
    
    /**
     * 配件收货地址变更表(变更状态修改为已通过)修改
     *
     * @param vo
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateChangeAddr(PtBuChangeShipAddressVO vo)throws Exception {

        return factory.update(vo);
    }

    /**
     * 销售订单表修改
     *
     * @param vo 销售订单表对应实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateSaleOrder(PtBuSaleOrderVO vo)throws Exception {

        return factory.update(vo);
    }

    /**
     * 配件收货地址变更表新增
     *
     * @param pPtBuChangeShipAddressVO 配件收货地址变更表对应实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean deliveryAddrInsert(PtBuChangeShipAddressVO pPtBuChangeShipAddressVO) throws Exception {

        return factory.insert(pPtBuChangeShipAddressVO);
    }
    public QuerySet checkStatus(String orderId)throws Exception
    {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT 1 FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+orderId+" AND NVL(SHIP_STATUS,0)>"+DicConstant.DDFYZT_03+"\n");
        qs = factory.select(null, sql.toString());
        return qs;
    }
    public QuerySet getNo(String orderId)throws Exception
    {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT ORDER_NO FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+orderId+"\n");
        qs = factory.select(null, sql.toString());
        return qs;
    }
    public QuerySet getDelay(String OLD_NO)throws Exception
    {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT ORDER_ID FROM PT_BU_SALE_ORDER WHERE ORDER_NO LIKE '%"+OLD_NO+"%' ORDER BY ORDER_ID\n");
        qs = factory.select(null, sql.toString());
        return qs;
    }

}

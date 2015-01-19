package com.org.dms.dao.part.salesMng.orderMng;

import java.sql.Connection;
import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 订单明细查询Dao
 *
 * 订单明细查询
 * @author zhengyao
 * @date 2014-10-23
 * @version 1.0
 */
public class PartOrderDtlQueryMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return warehouseMngDao 订单明细查询Dao
     */
    public static final PartOrderDtlQueryMngDao getInstance(ActionContext pActionContext) {

        PartOrderDtlQueryMngDao partOrderDtlQueryMngDao = new PartOrderDtlQueryMngDao();
        pActionContext.setDBFactory(partOrderDtlQueryMngDao.factory);
        return partOrderDtlQueryMngDao;
    }
    
    // 获取数据库连接
    public Connection getConnection(){
    	return this.factory.getConnection();
    }

    /**
     * 订单明细查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download(String pConditions,User user) throws Exception {

    	StringBuilder sql= new StringBuilder();
//        sql.append("SELECT T.PART_CODE,\n" );
//        sql.append("       T.PART_NAME,\n" );
//        sql.append("       T.UNIT,\n" );
//        sql.append("       T.SALE_PRICE,\n" );
//        sql.append("       T.ORDER_NO,\n" );
//        sql.append("       T.ORDER_ID,\n" );
//        sql.append("       T.ORG_NAME,\n" );
//        sql.append("       T.ORDER_COUNT,\n" );
//        sql.append("       T.DELIVERY_COUNT,\n" );
//        sql.append("       T.UNIT_PRICE,\n" );
//        sql.append("       T.DELIVERY_COUNT*T.UNIT_PRICE AMOUNT,\n" );
//        sql.append("       T.CHECK_USER,\n" );
//        sql.append("       T.CLOSE_DATE,\n" );
//        sql.append("       T.ORDER_STATUS,\n" );
//        sql.append("       T.ORG_ID\n" );
//        sql.append("  FROM (SELECT A.PART_CODE,\n" );
//        sql.append("               A.PART_NAME,\n" );
//        sql.append("               B.UNIT,\n" );
//        sql.append("               B.SALE_PRICE,\n" );
//        sql.append("               C.ORDER_NO,\n" );
//        sql.append("               C.ORDER_ID,\n" );
//        sql.append("               C.ORG_NAME,\n" );
//        sql.append("               A.ORDER_COUNT,\n" );
//        sql.append("               A.DELIVERY_COUNT,\n" );
//        sql.append("               A.UNIT_PRICE,\n" );
//        sql.append("               A.AMOUNT,\n" );
//        sql.append("               (SELECT U.PERSON_NAME FROM TM_USER U WHERE U.ACCOUNT = D.CHECK_USER) CHECK_USER,\n" );
//        sql.append("               C.CLOSE_DATE,\n" );
//        sql.append("               C.ORDER_STATUS,\n" );
//        sql.append("               C.ORG_ID\n" );
//        sql.append("          FROM PT_BU_SALE_ORDER_DTL   A,\n" );
//        sql.append("               PT_BA_INFO             B,\n" );
//        sql.append("               PT_BU_SALE_ORDER       C,\n" );
//        sql.append("               PT_BU_SALE_ORDER_CHECK D\n" );
//        sql.append("         WHERE " );
//        sql.append("           A.PART_ID = B.PART_ID\n" );
//        sql.append("           AND A.ORDER_ID = C.ORDER_ID(+)\n" );
//        sql.append("           AND A.ORDER_ID = D.ORDER_ID(+)\n" );
//        sql.append("           AND NVL(A.DELIVERY_COUNT,0) >0\n" );
//        sql.append("           AND C.ORDER_STATUS = '"+DicConstant.DDZT_06+"'\n" );
//        sql.append("           AND A.ORDER_ID NOT IN\n" );
//        sql.append("               (SELECT ORDER_ID\n" );
//        sql.append("                  FROM PT_BU_SALE_ORDER\n" );
//        sql.append("                 WHERE 1 = 1 AND ORG_ID NOT IN (SELECT T1.ORG_ID FROM TM_ORG T1 WHERE T1.ORG_TYPE IN ('"+DicConstant.ZZLB_10+"','"+DicConstant.ZZLB_11+"'))");
//        
//        // 判断用户是否属于军品
//        if(this.checkUserIsAM(user)){
//        	sql.append(" AND ORDER_TYPE = '"+DicConstant.DDLX_08+"' ");
//        } else {
//        	sql.append(" AND ORDER_TYPE <> '"+DicConstant.DDLX_08+"' ");
//        }
//        sql.append(" AND DIR_SOURCE_ORDER_ID IS NOT NULL)) T");
//        sql.append("	WHERE " + pConditions);
//        sql.append("    ORDER BY T.ORDER_ID DESC");
    	sql.append("SELECT T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T.UNIT) UNIT,\n" );
    	sql.append("       T.SALE_PRICE,\n" );
    	sql.append("       T.ORDER_NO,\n" );
    	sql.append("       T.ORDER_ID,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.ORDER_COUNT,\n" );
    	sql.append("       T.DELIVERY_COUNT,\n" );
    	sql.append("       T.UNIT_PRICE,\n" );
    	sql.append("       T.DELIVERY_COUNT * T.UNIT_PRICE AMOUNT,\n" );
    	sql.append("       T.CHECK_USER,\n" );
    	sql.append("       T.CLOSE_DATE,\n" );
    	sql.append("       T.ORDER_STATUS,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE\n" );
    	sql.append("  FROM (SELECT A.PART_CODE,\n" );
    	sql.append("               A.PART_NAME,\n" );
    	sql.append("               B.UNIT,\n" );
    	sql.append("               B.SALE_PRICE,\n" );
    	sql.append("               C.ORDER_NO,\n" );
    	sql.append("               C.ORDER_ID,\n" );
    	sql.append("               C.ORG_NAME,\n" );
    	sql.append("               A.ORDER_COUNT,\n" );
    	sql.append("               A.DELIVERY_COUNT,\n" );
    	sql.append("               A.UNIT_PRICE,\n" );
    	sql.append("               A.AMOUNT,\n" );
    	sql.append("               D.USER_NAME CHECK_USER,\n" );
    	sql.append("               C.CLOSE_DATE,\n" );
    	sql.append("               C.ORDER_STATUS,\n" );
    	sql.append("               C.SHIP_STATUS,\n" );
    	sql.append("               C.ORG_ID,\n" );
    	sql.append("               C.ORG_CODE\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER_DTL A,\n" );
    	sql.append("               PT_BA_INFO           B,\n" );
    	sql.append("               PT_BU_SALE_ORDER     C,\n" );
    	sql.append("               PT_BA_ORDER_CHECK    D,\n" );
    	sql.append("               TM_ORG               E\n" );
    	sql.append("         WHERE A.PART_ID = B.PART_ID\n" );
    	sql.append("           AND A.ORDER_ID = C.ORDER_ID\n" );
    	sql.append("           AND C.ORG_ID = E.ORG_ID\n" );
    	sql.append("           AND E.PID = D.ORG_ID\n" );
    	sql.append("           AND NVL(A.DELIVERY_COUNT, 0) > 0\n" );
    	sql.append("           AND C.ORDER_STATUS = "+DicConstant.DDZT_06+"\n" );
    	sql.append("           AND C.SHIP_STATUS IN ("+DicConstant.DDFYZT_04+", "+DicConstant.DDFYZT_05+", "+DicConstant.DDFYZT_06+", "+DicConstant.DDFYZT_07+")\n" );
    	if(this.checkUserIsAM(user)){
        	sql.append(" AND C.ORDER_TYPE = '"+DicConstant.DDLX_08+"' ");
        } else {
        	sql.append(" AND C.ORDER_TYPE <> '"+DicConstant.DDLX_08+"' ");
        }
    	
    	// 判断用户是否为订单审核人，如果是则只能查看自己名下的单子
    	if(this.checkUserIsCheckOrder(user)){
    		sql.append(" AND D.USER_ACCOUNT = '" + user.getAccount() + "'");
    	}
    	
    	sql.append("           AND E.ORG_TYPE = "+DicConstant.ZZLB_09+") T\n" );
    	sql.append("	WHERE " + pConditions);
        sql.append("    ORDER BY T.CLOSE_DATE DESC");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }

    /**
     * 销售订单明细表(PT_BU_SALE_ORDER_DTL)查询
     *
     * @param pConditions 月份
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public BaseResultSet searchOrderDtl(PageManager pPageManager, User pUser,String pConditions) throws Exception {

    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.UNIT,\n" );
    	sql.append("       T.SALE_PRICE,\n" );
    	sql.append("       T.ORDER_NO,\n" );
    	sql.append("       T.ORDER_ID,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.ORDER_COUNT,\n" );
    	sql.append("       T.DELIVERY_COUNT,\n" );
    	sql.append("       T.UNIT_PRICE,\n" );
    	sql.append("       T.DELIVERY_COUNT * T.UNIT_PRICE AMOUNT,\n" );
    	sql.append("       T.CHECK_USER,\n" );
    	sql.append("       T.CLOSE_DATE,\n" );
    	sql.append("       T.ORDER_STATUS,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE\n" );
    	sql.append("  FROM (SELECT A.PART_CODE,\n" );
    	sql.append("               A.PART_NAME,\n" );
    	sql.append("               B.UNIT,\n" );
    	sql.append("               B.SALE_PRICE,\n" );
    	sql.append("               C.ORDER_NO,\n" );
    	sql.append("               C.ORDER_ID,\n" );
    	sql.append("               C.ORG_NAME,\n" );
    	sql.append("               A.ORDER_COUNT,\n" );
    	sql.append("               A.DELIVERY_COUNT,\n" );
    	sql.append("               A.UNIT_PRICE,\n" );
    	sql.append("               A.AMOUNT,\n" );
    	sql.append("               D.USER_NAME CHECK_USER,\n" );
    	sql.append("               C.CLOSE_DATE,\n" );
    	sql.append("               C.ORDER_STATUS,\n" );
    	sql.append("               C.SHIP_STATUS,\n" );
    	sql.append("               C.ORG_ID,\n" );
    	sql.append("               C.ORG_CODE\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER_DTL A,\n" );
    	sql.append("               PT_BA_INFO           B,\n" );
    	sql.append("               PT_BU_SALE_ORDER     C,\n" );
    	sql.append("               PT_BA_ORDER_CHECK    D,\n" );
    	sql.append("               TM_ORG               E\n" );
    	sql.append("         WHERE A.PART_ID = B.PART_ID\n" );
    	sql.append("           AND A.ORDER_ID = C.ORDER_ID\n" );
    	sql.append("           AND C.ORG_ID = E.ORG_ID\n" );
    	sql.append("           AND E.PID = D.ORG_ID\n" );
    	sql.append("           AND NVL(A.DELIVERY_COUNT, 0) > 0\n" );
    	sql.append("           AND C.ORDER_STATUS = "+DicConstant.DDZT_06+"\n" );
    	sql.append("           AND C.SHIP_STATUS IN ("+DicConstant.DDFYZT_04+", "+DicConstant.DDFYZT_05+", "+DicConstant.DDFYZT_06+", "+DicConstant.DDFYZT_07+")\n" );
    	
    	// 判断用户是否为军品用户
    	if(this.checkUserIsAM(pUser)){
        	sql.append(" AND C.ORDER_TYPE = '"+DicConstant.DDLX_08+"' ");
        } else {
        	sql.append(" AND C.ORDER_TYPE <> '"+DicConstant.DDLX_08+"' ");
        }
    	
    	// 判断用户是否为订单审核人，如果是则只能查看自己名下的单子
    	if(this.checkUserIsCheckOrder(pUser)){
    		sql.append(" AND D.USER_ACCOUNT = '" + pUser.getAccount() + "'");
    	}
    	sql.append("           AND E.ORG_TYPE = "+DicConstant.ZZLB_09+") T\n" );
//        StringBuilder sql= new StringBuilder();
//        sql.append("SELECT T.PART_CODE,\n" );
//        sql.append("       T.PART_NAME,\n" );
//        sql.append("       T.UNIT,\n" );
//        sql.append("       T.SALE_PRICE,\n" );
//        sql.append("       T.ORDER_NO,\n" );
//        sql.append("       T.ORDER_ID,\n" );
//        sql.append("       T.ORG_NAME,\n" );
//        sql.append("       T.ORDER_COUNT,\n" );
//        sql.append("       T.DELIVERY_COUNT,\n" );
//        sql.append("       T.UNIT_PRICE,\n" );
//        sql.append("       T.DELIVERY_COUNT*T.UNIT_PRICE AMOUNT,\n" );
//        sql.append("       T.CHECK_USER,\n" );
//        sql.append("       T.CLOSE_DATE,\n" );
//        sql.append("       T.ORDER_STATUS,\n" );
//        sql.append("       T.ORG_ID\n" );
//        sql.append("  FROM (SELECT A.PART_CODE,\n" );
//        sql.append("               A.PART_NAME,\n" );
//        sql.append("               B.UNIT,\n" );
//        sql.append("               B.SALE_PRICE,\n" );
//        sql.append("               C.ORDER_NO,\n" );
//        sql.append("               C.ORDER_ID,\n" );
//        sql.append("               C.ORG_NAME,\n" );
//        sql.append("               A.ORDER_COUNT,\n" );
//        sql.append("               A.DELIVERY_COUNT,\n" );
//        sql.append("               A.UNIT_PRICE,\n" );
//        sql.append("               A.AMOUNT,\n" );
//        sql.append("               (SELECT U.PERSON_NAME FROM TM_USER U WHERE U.ACCOUNT = D.CHECK_USER) CHECK_USER,\n" );
//        sql.append("               C.CLOSE_DATE,\n" );
//        sql.append("               C.ORDER_STATUS,\n" );
//        sql.append("               C.ORG_ID\n" );
//        sql.append("          FROM PT_BU_SALE_ORDER_DTL   A,\n" );
//        sql.append("               PT_BA_INFO             B,\n" );
//        sql.append("               PT_BU_SALE_ORDER       C,\n" );
//        sql.append("               PT_BU_SALE_ORDER_CHECK D\n" );
//        sql.append("         WHERE " );
//        sql.append("           A.PART_ID = B.PART_ID\n" );
//        sql.append("           AND A.ORDER_ID = C.ORDER_ID(+)\n" );
//        sql.append("           AND A.ORDER_ID = D.ORDER_ID(+)\n" );
//        sql.append("           AND NVL(A.DELIVERY_COUNT,0) >0\n" );
//        sql.append("           AND C.ORDER_STATUS = "+DicConstant.DDZT_06+"\n" );
//        sql.append("           AND C.SHIP_STATUS IN ("+DicConstant.DDFYZT_04+","+DicConstant.DDFYZT_05+","+DicConstant.DDFYZT_06+","+DicConstant.DDFYZT_07+")\n" );
//        sql.append("           AND A.ORDER_ID NOT IN\n" );
//        sql.append("               (SELECT ORDER_ID\n" );
//        sql.append("                  FROM PT_BU_SALE_ORDER\n" );
//        sql.append("                 WHERE 1 = 1 AND ORG_ID NOT IN (SELECT T1.ORG_ID FROM TM_ORG T1 WHERE T1.ORG_TYPE IN ('"+DicConstant.ZZLB_10+"','"+DicConstant.ZZLB_11+"'))");
//        
        sql.append("	WHERE " + pConditions);
        sql.append("    ORDER BY T.CLOSE_DATE DESC");
        //执行方法，不需要传递conn参数
        return factory.select(sql.toString(),pPageManager);
    }

    /**
     * 
     * @Title: checkUserIsAM
     * @Description: 判断用户是否为军品用户
     * @param user
     * @return true 是 false 否
     * @throws SQLException
     * @return: boolean
     */
	public boolean checkUserIsAM(User user) throws SQLException{
		String res = this.factory.select("SELECT F_IS_AM("+user.getOrgId()+") FROM DUAL")[0][0];
		return res.equals("1");
	}
	
	/**
	 * 
	 * @Title: checkUserIsCheckOrder
	 * @Description: 判断用户是否为审核员
	 * @param user
	 * @return true 是 false 否
	 * @throws Exception
	 * @return: boolean
	 */
	public boolean checkUserIsCheckOrder(User user) throws Exception {
		return Integer.parseInt(this.factory.select("SELECT COUNT(1) FROM PT_BA_ORDER_CHECK C WHERE C.USER_ACCOUNT = ?", new Object[]{user.getAccount()})[0][0]) > 0;
	}
    
    /**
     * 订单明细表(PT_BU_SALE_ORDER_DTL)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return QuerySet 结果集
     * @throws Exception
     */
//    public BaseResultSet searchOrderDtl(PageManager pPageManager, String pConditions) throws Exception {
//
//        String wheres = pConditions;
//        wheres += " AND A.PART_ID = B.PART_ID ";
//        pPageManager.setFilter(wheres);
//        BaseResultSet bs = null;
//        StringBuffer sql= new StringBuffer();
//        sql.append("SELECT A.DTL_ID,\n" );
//        sql.append("       A.PART_ID,\n" );
//        sql.append("       A.PART_CODE,\n" );
//        sql.append("       A.PART_NAME,\n" );
//        sql.append("       A.PART_NO,\n" );
//        sql.append("       B.UNIT,\n" );
//        sql.append("       B.MIN_PACK,\n" );
//        sql.append("       B.MIN_UNIT,\n" );
//        sql.append("       A.UNIT_PRICE,\n" );
//        sql.append("       A.IF_SUPPLIER,\n" );
//        sql.append("       A.SUPPLIER_ID,\n" );
//        sql.append("       A.SUPPLIER_CODE,\n" );
//        sql.append("       A.SUPPLIER_NAME,\n" );
//        sql.append("       A.ORDER_COUNT,\n" );
//        sql.append("       A.REMARKS,\n" );
//        sql.append("       A.AMOUNT\n" );
//        sql.append("  FROM PT_BU_SALE_ORDER_DTL A, PT_BA_INFO B\n" );
//        bs = factory.select(sql.toString(), pPageManager);
//        //执行方法，不需要传递conn参数
//        bs = factory.select(sql.toString(),pPageManager);
//        return bs;
//    }
}

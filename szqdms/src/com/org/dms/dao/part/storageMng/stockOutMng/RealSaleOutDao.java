package com.org.dms.dao.part.storageMng.stockOutMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuDealerStockChangeVO;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.part.PtBuRealSaleDtlVO;
import com.org.dms.vo.part.PtBuRealSaleVO;
import com.org.frameImpl.Constant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 实销出库    dao
 *
 * @user : 王晶鑫
 */
public class RealSaleOutDao extends BaseDAO {

    //定义instance
    public static final RealSaleOutDao getInstance(ActionContext atx) {
        RealSaleOutDao dao = new RealSaleOutDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 查询库存ID
     */
    public QuerySet dealerStockSearch(String partId,User pUser) throws Exception {
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT\n" );
        sql.append("   STOCK_ID,\n" );
        sql.append("   PART_CODE,\n" );
        sql.append("   PART_NAME,\n" );
        sql.append("   SUPPLIER_ID,\n" );
        sql.append("   SUPPLIER_CODE,\n" );
        sql.append("   SUPPLIER_NAME\n" );
        sql.append("FROM\n" );
        sql.append("   PT_BU_DEALER_STOCK\n" );
        sql.append("WHERE\n" );
        sql.append("   ORG_ID='" + pUser.getOrgId() + "'\n" );
        sql.append("AND PART_ID='" + partId + "'");
        return factory.select(null, sql.toString());
    }

    /**
     * 查询实销订单明细
     * 
     */
    public QuerySet realSaleDtlSearch(String saleId) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT\n" );
        sql.append("    PART_ID,\n" );
        sql.append("    SALE_COUNT,\n" );
        sql.append("    AMOUNT\n" );
        sql.append("FROM\n" );
        sql.append("    PT_BU_REAL_SALE_DTL\n" );
        sql.append("WHERE\n" );
        sql.append("    SALE_ID = '"+saleId+"'");

        return factory.select(null, sql.toString());
    }

    /**
     * 查询实销订单明细(总金额，总数量)
     * 
     */
    public QuerySet realSaleDtlCountSearch(String saleId) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT\n" );
        sql.append("    SUM(SALE_COUNT) SUM_SALE_COUNT,\n" );
        sql.append("    SUM(AMOUNT) SUM_AMOUNT\n" );
        sql.append("FROM\n" );
        sql.append("    PT_BU_REAL_SALE_DTL\n" );
        sql.append("WHERE\n" );
        sql.append("    SALE_ID = '"+saleId+"'");

        return factory.select(null, sql.toString());
    }

    /**
     * 查询实销单
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean realSaleUpdate(PtBuRealSaleVO vo) throws Exception {

        return factory.update(vo);
    }
    /**
     * 查询实销单
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet realSalesearch(PageManager page, User user, String conditions,String partCode) throws Exception {
        String wheres = conditions;
        if(!"".equals(partCode)&&partCode!=null){
        wheres += "\n AND T.SALE_ID = T1.SALE_ID(+) "
        		+" AND EXISTS(SELECT 1 FROM PT_BU_REAL_SALE_DTL A WHERE A.SALE_ID = T.SALE_ID AND A.PART_CODE LIKE '%"+partCode+"%')"
                +" AND T.OEM_COMPANY_ID = '" + user.getOemCompanyId() + "'\n"
                +" AND T.STATUS = '" + DicConstant.YXBS_01 + "'\n"
                //+" AND T.OUT_TYPE = '" + DicConstant.SXLX_01 + "'\n"
                +" AND T.COMPANY_ID = '" + user.getCompanyId() + "'\n" 
                +" AND T.ORG_ID = '" + user.getOrgId() + "'\n" 
                +" AND T.SALE_STATUS = '" + DicConstant.SXDZT_01 + "' ORDER BY T.SALE_NO\n";
        }else{
        	wheres += "\n AND T.SALE_ID = T1.SALE_ID(+) "
        	        +" AND T.OEM_COMPANY_ID = '" + user.getOemCompanyId() + "'\n"
        	        +" AND T.STATUS = '" + DicConstant.YXBS_01 + "'\n"
        	        +" AND T.COMPANY_ID = '" + user.getCompanyId() + "'\n" 
        	        +" AND T.ORG_ID = '" + user.getOrgId() + "'\n" 
        	        //+" AND T.OUT_TYPE = '" + DicConstant.SXLX_01 + "'\n"
        	        +" AND T.SALE_STATUS = '" + DicConstant.SXDZT_01 + "' ORDER BY T.SALE_NO\n";
        }
        
        page.setFilter(wheres);
        // 定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.*,T1.COUNT FROM PT_BU_REAL_SALE T,\n");
        sql.append("(SELECT COUNT(1) COUNT,SALE_ID FROM PT_BU_REAL_SALE_DTL GROUP  BY SALE_ID)T1");
//        sql.append(" WHERE \n");
//        sql.append(" T.OEM_COMPANY_ID = '" + user.getOemCompanyId() + "'\n" );
//        sql.append("AND T.STATUS = '" + DicConstant.YXBS_01 + "'\n" );
//        sql.append("AND T.COMPANY_ID = '" + user.getCompanyId() + "'\n" );
//        sql.append("AND T.ORG_ID = '" + user.getOrgId() + "'\n" );
//        sql.append("AND T.SALE_STATUS = '" + DicConstant.SXDZT_01 + "'\n");
//        sql.append("");
//        sql.append("AND T.SALE_ID IN (SELECT A.SALE_ID FROM PT_BU_REAL_SALE A, PT_BU_REAL_SALE_DTL B");

        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("SALE_STATUS", "SXDZT");
        bs.setFieldDic("OUT_TYPE", "SXLX");
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        bs.setFieldDateFormat("SALE_DATE", "yyyy-MM-dd");
        return bs;
    }

    /**
     * 查询实销单明细
     * @param page
     * @param user
     * @param saleId
     * @return
     * @throws Exception
     */
    public BaseResultSet realSaleDtlsearch(PageManager page, User user, String saleId) throws Exception {
        String wheres ="";
        wheres += "\n 1=1 "
               +  "\n AND T.PART_ID = P.PART_ID"
               +  "\n AND T.SALE_ID = "+ saleId;
        page.setFilter(wheres);
        // 定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.DTL_ID,\n" );
        sql.append("       T.SALE_ID,\n" );
        sql.append("       T.PART_ID,\n" );
        sql.append("       P.PART_CODE,\n" );
        sql.append("       P.PART_NAME,\n" );
        sql.append("       P.UNIT,\n" );
        sql.append("       T.SALE_PRICE,\n" );
        sql.append("       T.SALE_COUNT,\n" );
        sql.append("       T.AMOUNT,\n" );
        sql.append("       P.PART_NO,\n" );
        sql.append("       T.SUPPLIER_ID,\n" );
        sql.append("       P.MIN_PACK\n" );
        sql.append("  FROM PT_BU_REAL_SALE_DTL T, PT_BA_INFO P");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        return bs;
    }

    /**
     * 查询配件库存
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet realSalePartsearch(PageManager page, User user, String conditions) throws Exception {
        String wheres =conditions;
        wheres += "\n AND P.PART_ID = T.PART_ID "
                + "\n AND T.STATUS ="+ Constant.YXBS_01
                + "\n And T.ORG_ID ="+user.getOrgId()
                + "\n And P.PART_STATUS <>"+DicConstant.PJZT_02
                + "\n And T.STORAGE_STATUS ="+DicConstant.KCZT_01
                + "\n ORDER BY T.PART_CODE";
        page.setFilter(wheres);
        // 定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT STOCK_ID,\n" );
        sql.append("       T.PART_CODE,\n" );
        sql.append("       T.PART_ID,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T.SUPPLIER_NAME,\n" );
        sql.append("       T.SUPPLIER_ID,\n" );
        sql.append("       T.SUPPLIER_CODE,\n" );
        sql.append("       T.AMOUNT,\n" );
        sql.append("       T.OCCUPY_AMOUNT,\n" );
        sql.append("       T.AVAILABLE_AMOUNT,\n" );
        sql.append("       P.UNIT,\n" );
        sql.append("       P.IF_SUPLY,\n" );
        sql.append("       P.RETAIL_PRICE SALE_PRICE\n" );
        sql.append("  FROM PT_BU_DEALER_STOCK T, PT_BA_INFO P");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
    
    /**
     * 新增实销单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertRealSale(PtBuRealSaleVO vo) throws Exception {

        return factory.insert(vo);
    }
    
    /**
     * 获取主键ID
     *
     * @return
     * @throws Exception
     */
    public String getId() throws Exception {

        return SequenceUtil.getCommonSerivalNumber(factory);
    }

    /**
     * 实销详单新增
     */
    public boolean realSaleDtlInsert(PtBuRealSaleDtlVO vo) throws Exception {

        String saledtl=SequenceUtil.getCommonSerivalNumber(factory);
        vo.setDtlId(saledtl);
        return factory.insert(vo);
    }
    /**
     * 查询此种配件是否存在
     */
    public QuerySet realSalePartIsHave(String partId,String saleId,User user) throws Exception {

        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.SALE_COUNT,T.AMOUNT,T.DTL_ID,T.PART_ID,S.STOCK_ID,S.OCCUPY_AMOUNT,S.AVAILABLE_AMOUNT,S.AMOUNT \n" );
        sql.append("FROM PT_BU_REAL_SALE_DTL T,PT_BA_INFO P, PT_BU_DEALER_STOCK S\n");
        sql.append("WHERE 1=1 \n");
        if(!partId.equals("") && partId != null){
            sql.append(" AND  T.PART_ID  = "+partId+"\n" );
        }
        sql.append(" AND  T.SALE_ID  = "+saleId+"\n" );
        sql.append(" AND P.PART_ID =  S.PART_ID \n" );
        sql.append("AND  T.PART_ID =P.PART_ID   \n" );
        sql.append("AND S.ORG_ID='"+user.getOrgId()+"' \n" );
        qs = factory.select(null, sql.toString());
        return qs;
    }
    /**
     * 删除时更改库存
     */
    public boolean dealerStock(String partId,String supplierId,String salecount,User user) throws Exception {

          StringBuffer sql= new StringBuffer();
          sql.append("UPDATE PT_BU_DEALER_STOCK \n" );
          sql.append("   SET OCCUPY_AMOUNT = OCCUPY_AMOUNT -"+salecount+",\n" );
          sql.append("       AVAILABLE_AMOUNT = AVAILABLE_AMOUNT +"+salecount+" \n");
          sql.append(" WHERE PART_ID ="+partId+" \n");
          sql.append("       AND ORG_ID ='"+user.getOrgId()+"' \n");
          sql.append("       AND SUPPLIER_ID ='"+supplierId+"' \n");
          return factory.update(sql.toString(), null);
    }
    /**
     * 出库时更新库存
     */
    public boolean updateStock(String partId,String salecount,String orgId) throws Exception {

        StringBuffer sql= new StringBuffer();
        sql.append("UPDATE PT_BU_DEALER_STOCK \n" );
        sql.append("   SET OCCUPY_AMOUNT = OCCUPY_AMOUNT -"+salecount+",\n" );
        sql.append("       AMOUNT = AMOUNT -"+salecount+" \n");
        sql.append(" WHERE PART_ID ="+partId+" \n");
        sql.append(" AND ORG_ID ="+orgId+" \n");
        return factory.update(sql.toString(), null);
    }

    /**
     * 配件出现重复时选择叠加
     */
    public boolean realSalePartUpdate(PtBuRealSaleDtlVO vo) throws Exception {

        return factory.update(vo);
    }

    /**
     * 库存锁定数量
     */
    public boolean DealerStockUpdate(PtBuDealerStockVO vo) throws Exception {

        return factory.update(vo);
    }
    
    /**
     * 实销单删除
     */
    public boolean realSaleDelete(PtBuRealSaleVO vo) throws Exception {

        return factory.delete(vo);
    }

    /**
     * 实销单删除单条
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean realSaleDtlOneDelete(PtBuRealSaleDtlVO vo) throws Exception {

        return factory.delete(vo);
    }

    /**
     * 实销单出库状态更新
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean realSaleOutUpdate(PtBuRealSaleVO vo) throws Exception {

        return factory.update(vo);
    }

    /**
     * 实销单详单删除
     */
     public boolean realSaleDtlDelete(PtBuRealSaleDtlVO vo) throws Exception {

        // 删除明细表sql
        String stockString = "DELETE \n"
                           + "FROM \n"
                           + "    PT_BU_REAL_SALE_DTL T\n"
                           + "WHERE \n"
                           + "    T.SALE_ID=" + vo.getSaleId() ;
        return factory.delete(stockString, null);
    }
     
    /**
     * 库存异动(轨迹)新增
     */
    public boolean StockChangeInsert(PtBuDealerStockChangeVO vo) throws Exception {

        return factory.insert(vo);
    }
    public void insetStockDtl(String saleCount,String aAmount,String SHOULD_COUNT,User user,String saleId,String url,String nPartId) throws Exception {
    	   StringBuffer sql= new StringBuffer();
    	   sql.append("INSERT INTO PT_BU_DEALER_STOCK_LOG\n" );
    	   sql.append("          (LOG_ID,\n" );
    	   sql.append("           YWZJ,\n" );
    	   sql.append("           ACTION_URL,\n" );
    	   sql.append("           OAMOUNT,\n" );
    	   sql.append("           AMOUNT,\n" );
    	   sql.append("           AAMOUNT,\n" );
    	   sql.append("           UPDATE_USER,\n" );
    	   sql.append("           UPDATE_TIME,\n" );
    	   sql.append("           PART_ID,\n" );
    	   sql.append("           ORG_ID)\n" );
    	   sql.append("        VALUES\n" );
    	   sql.append("          (F_GETID(),\n" );
    	   sql.append("           "+saleId+",\n" );
    	   sql.append("           '"+url+"',\n" );
    	   sql.append("           -"+SHOULD_COUNT+",\n" );
    	   sql.append("           -"+saleCount+",\n" );
    	   sql.append("           +"+aAmount+",\n" );
    	   sql.append("           '"+user.getAccount()+"',\n" );
    	   sql.append("           SYSDATE,\n" );
    	   sql.append("           "+nPartId+",\n" );
    	   sql.append("           "+user.getOrgId()+")");
    	   factory.update(sql.toString(), null);
       }

}
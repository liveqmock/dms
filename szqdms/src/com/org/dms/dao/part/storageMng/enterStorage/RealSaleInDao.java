package com.org.dms.dao.part.storageMng.enterStorage;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuDealerStockChangeVO;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.part.PtBuRealSaleDtlVO;
import com.org.dms.vo.part.PtBuRealSaleReturnVO;
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
public class RealSaleInDao extends BaseDAO {
    //定义instance
    public static final RealSaleInDao getInstance(ActionContext atx) {
        RealSaleInDao dao = new RealSaleInDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    /*
     * 查询实销单
     */
    public BaseResultSet realSalesearch(PageManager page, User user, String conditions) throws Exception {
        String wheres = conditions;
        wheres += "\n AND 1=1 "
               +  "\n AND t.STATUS ="+ Constant.YXBS_01
               +  "\n And t.org_id ="+user.getOrgId();
        page.setFilter(wheres);
        // 定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("select * from PT_BU_REAL_SALE t");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("SALE_STATUS", "SXDZT");
        return bs;
    }
    
    /*
     * 查询实销单明细
     */
    public BaseResultSet realSaleDtlsearch(PageManager page, User user, String saleId) throws Exception {
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
        sql.append("       (T.SALE_COUNT - NVL(T1.RETURN_COUNT, 0)) SALE_COUNT,\n" );
        sql.append("       T.AMOUNT,\n" );
        sql.append("       P.PART_NO,\n" );
        sql.append("       P.MIN_UNIT,\n" );
        sql.append("       P.MIN_PACK\n" );
        sql.append("  FROM PT_BU_REAL_SALE_DTL T,\n" );
        sql.append("       PT_BA_INFO P,\n" );
        sql.append("       (SELECT SUM(A.RETURN_COUNT) RETURN_COUNT, A.PART_ID\n" );
        sql.append("          FROM PT_BU_REAL_SALE_RETURN A\n" );
        sql.append("         WHERE A.SALE_ID = "+saleId+"\n" );
        sql.append("         GROUP BY A.PART_ID) T1\n" );
        sql.append(" WHERE 1 = 1\n" );
        sql.append("   AND T.PART_ID = P.PART_ID\n" );
        sql.append("   AND T.PART_ID = T1.PART_ID(+)\n" );
        sql.append("   AND T.SALE_ID = "+saleId+"");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
        return bs;
    }
    
    /*
     * 查询配件库存
     */
    public BaseResultSet realSalePartsearch(PageManager page, User user, String conditions) throws Exception {
        String wheres =conditions;
        wheres +=     "\n AND p.part_id = t.part_id "
                   //+  "\n AND t.OEM_COMPANY_ID = "+ user.getOemCompanyId() 
                   +  "\n AND t.STATUS ="+ Constant.YXBS_01
                   //+  "\n AND t.COMPANY_ID ="+user.getCompanyId()
                   +  "\n And t.org_id ="+user.getOrgId()
                   +  "\n And p.part_status <>"+DicConstant.PJZT_02
                   +  "\n And t.STORAGE_STATUS ="+DicConstant.KCZT_01;
        page.setFilter(wheres);
        // 定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("select STOCK_ID,\n" );
        sql.append("       t.part_code,\n" );
        sql.append("       t.part_id,\n" );
        sql.append("       t.part_name,\n" );
        sql.append("       t.supplier_name,\n" );
        sql.append("       t.supplier_id,\n" );
        sql.append("       t.supplier_code,\n" );
        sql.append("       t.amount,\n" );
        sql.append("       t.occupy_amount,\n" );
        sql.append("       t.available_amount,\n" );
        sql.append("       p.unit,\n" );
        sql.append("       p.sale_price\n" );
        sql.append("  from PT_BU_DEALER_STOCK t, pt_ba_info p");
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
    public boolean insertRealSale(PtBuRealSaleVO vo)
            throws Exception {
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
    public boolean realSaleDtlInsert(PtBuRealSaleDtlVO vo)
            throws Exception {
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
          sql.append(" ANd P.PART_ID =  S.PART_ID \n" );
          sql.append("AND  T.PART_ID =P.PART_ID   \n" );
          sql.append("AND S.ORG_ID='"+user.getOrgId()+"' \n" );
          qs = factory.select(null, sql.toString());
          return qs;
    }
    /**
     * 删除时更改库存
     */
//    public boolean dealerStock(String partId,String salecount,String occupy_amount,String available_amount) throws Exception {
//          QuerySet qs = null;
//          StringBuffer sql= new StringBuffer();
//          sql.append("UPDATE PT_BU_DEALER_STOCK T\n" );
//          sql.append("   SET T.OCCUPY_AMOUNT    = "+occupy_amount+" -"+salecount+",\n" );
//          sql.append("       T.AVAILABLE_AMOUNT = "+available_amount+" +"+salecount+" \n");
//          sql.append("        WHERE T.PART_ID ="+partId+" \n");
//          return factory.update(sql.toString(), null);
//    }

    /**
     * 查询退件期限
     */
    public QuerySet realSalePartCheck(String saleId,User user) throws Exception {

          QuerySet qs = null;
          StringBuilder sql= new StringBuilder();
          sql.append("SELECT ROUND(MONTHS_BETWEEN(SYSDATE, SALE_DATE) -\n" );
          sql.append("       (SELECT PARAVALUE1\n" );
          sql.append("          FROM USER_PARA_CONFIGURE\n" );
          sql.append("         WHERE PARAKEY = (SELECT CASE\n" );
          sql.append("                                   WHEN ORG_TYPE = '"+DicConstant.ZZLB_09+"' THEN\n" );
          sql.append("                                    '400101'\n" );
          sql.append("                                   WHEN ORG_TYPE IN ('"+DicConstant.ZZLB_10+"','"+DicConstant.ZZLB_11+"') THEN\n" );
          sql.append("                                    '400102'\n" );
          sql.append("                                 END \n" );
          sql.append("                            FROM TM_ORG\n" );
          sql.append("                           WHERE ORG_ID = '"+user.getOrgId()+"')),2) AS COUNT\n" );
          sql.append("  FROM PT_BU_REAL_SALE\n" );
          sql.append(" WHERE SALE_ID = '"+saleId+"'");

           qs = factory.select(null, sql.toString());
           return qs;
    }

    /**
     * 更新库存
     */
    public boolean updateStock(String partId,String avai_amount,String amount,User user)
            throws Exception {
          QuerySet qs = null;
          StringBuffer sql= new StringBuffer();
          sql.append("update pt_bu_dealer_stock t\n" );
          sql.append("   set t.available_amount    = "+avai_amount+",\n" );
          sql.append("       t.amount = "+amount+" \n");
          sql.append("        where t.part_id ="+partId+" and org_id = "+user.getOrgId()+"\n");
          sql.append("        and t.org_id ="+user.getOrgId()+" \n");
          return factory.update(sql.toString(), null);
    }
    
    /**
     * 库存异动(轨迹)新增
     */
    public boolean stockChangeInsert(PtBuDealerStockChangeVO vo)
            throws Exception {
        String ChangeId=SequenceUtil.getCommonSerivalNumber(factory);
        vo.setChangeId(ChangeId);;
        return factory.insert(vo);
    }
    
    /**
     * 配件出现重复时选择叠加
     */
    public boolean realSalePartUpdate(PtBuRealSaleDtlVO vo)
            throws Exception {
        return factory.update(vo);
    }
    /**
     * 库存锁定数量
     */
    public boolean DealerStockUpdate(PtBuDealerStockVO vo)
            throws Exception {
        return factory.update(vo);
    }
    
    /**
     * 实销单删除
     */
    public boolean realSaleDelete(PtBuRealSaleVO vo) throws Exception {
        return factory.delete(vo);
    }
    /*
     * 实销单删除单条
     */
    public boolean realSaleDtlOneDelete(PtBuRealSaleDtlVO vo) throws Exception {
        return factory.delete(vo);
    }
    /*
     * 实销单出库状态更新
     */
    public boolean realSaleOutUpdate(PtBuRealSaleVO vo)
            throws Exception {
        return factory.update(vo);
    }
    /**
     * 实销单详单删除
     */
     public boolean realSaleDtlDelete(PtBuRealSaleDtlVO vo) throws Exception {

            // 删除明细表sql
            String stockString = "DELETE \n"
                               + "FROM \n"
                               + "    pt_bu_real_sale_dtl t\n"
                               + "WHERE \n"
                               + "    t.sale_id=" + vo.getSaleId() ;
            return factory.delete(stockString, null);
        }
     public QuerySet getRealSale(String saleId,String partId) throws Exception {

         QuerySet qs = null;
         StringBuilder sql= new StringBuilder();
         sql.append("SELECT T.CUSTOMER_NAME,\n" );
         sql.append("       T.SALE_NO,\n" );
         sql.append("       T1.PART_ID,\n" );
         sql.append("       T1.PART_CODE,\n" );
         sql.append("       T1.PART_NAME,\n" );
         sql.append("       T1.SUPPLIER_CODE,\n" );
         sql.append("       T1.SUPPLIER_NAME,\n" );
         sql.append("       T1.SUPPLIER_ID,\n" );
         sql.append("       T1.UNIT,\n" );
         sql.append("       T1.SALE_PRICE\n" );
         sql.append("  FROM PT_BU_REAL_SALE T, PT_BU_REAL_SALE_DTL T1\n" );
         sql.append(" WHERE T.SALE_ID = T1.SALE_ID\n" );
         sql.append("   AND T1.PART_ID = "+partId+"\n" );
         sql.append("   AND T.SALE_ID = "+saleId+"");
          qs = factory.select(null, sql.toString());
          return qs;
   }
     public boolean insertRealSaleBack(PtBuRealSaleReturnVO vo)
             throws Exception {
         return factory.insert(vo);
     }

}
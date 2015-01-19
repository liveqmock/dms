package com.org.dms.dao.part.storageMng.stockInMng;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PchStockAcceptMngDao extends BaseDAO{
	public  static final PchStockAcceptMngDao getInstance(ActionContext atx)
    {
		PchStockAcceptMngDao dao = new PchStockAcceptMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

	public BaseResultSet orderSearch(PageManager page, User user, String conditions,String partCode,String partName,String warehouseType) throws Exception
    {
//		String sql1 = "SELECT WAREHOUSE_TYPE FROM PT_BA_WAREHOUSE WHERE ORG_ID = "+user.getOrgId()+"\n";
//		QuerySet qs  = factory.select(null, sql1);
//		String warehouseType = "";
//		if(qs.getRowCount()>0){
//			warehouseType = qs.getString(1, "WAREHOUSE_TYPE");
//		}
    	String wheres = conditions;
    	wheres +="AND T.SUPPLIER_ID = T1.SUPPLIER_ID\n" +
				"   AND T.SPLIT_ID = T2.SPLIT_ID \n" +
				"   AND T.COMPANY_ID = "+user.getCompanyId()+"\n" + 
				"   AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
				"   AND T.PURCHASE_TYPE <> "+DicConstant.CGDDLX_05+" AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n"; 
				if("100101".equals(warehouseType)){
					wheres +=" AND T.PURCHASE_TYPE IN("+DicConstant.CGDDLX_01+","+DicConstant.CGDDLX_02+","+DicConstant.CGDDLX_03+","+DicConstant.CGDDLX_06+")\n";
		    	}
		    	if("100102".equals(warehouseType)){
		    		wheres +=" AND T.PURCHASE_TYPE ="+DicConstant.CGDDLX_04+"\n";
		    	}
		    	if("100105".equals(warehouseType)){
		    		wheres +=" AND T.PURCHASE_TYPE ="+DicConstant.CGDDLX_07+"\n";
		    	}
		    	wheres +="   AND T.ORDER_STATUS = "+DicConstant.CGDDZT_04+"\n"+
				"   ORDER BY T.APPLY_DATE ";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DISTINCT T.SPLIT_ID,\n" );
    	sql.append("       T.PURCHASE_ID,\n" );
    	sql.append("       T.SPLIT_NO,\n" );
    	sql.append("       T.SELECT_MONTH,\n" );
    	sql.append("       T.PURCHASE_TYPE,\n" );
    	sql.append("       T.APPLY_USER,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );

    	//-- start 待验收数量
    	sql.append("(SELECT SUM(NVL(A.SHIP_COUNT,0)-NVL(A.ACCEPT_COUNT,0))\n" );
    	sql.append("   FROM PT_BU_PCH_ORDER_SPLIT_DTL A WHERE 1=1\n");
    	if(!"".equals(partCode)&&partCode!=null){
    		sql.append(" AND A.PART_CODE LIKE '%"+partCode+"%'\n");
    	}
    	if(!"".equals(partName)&&partName!=null){
    		sql.append(" AND A.PART_NAME LIKE '%"+partName+"%'\n");
    	}
    	sql.append(" AND T.SPLIT_ID = A.SPLIT_ID) WILL_ACCEPT_COUNT");
    	// -- end

    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T, PT_BA_SUPPLIER T1,");
    	sql.append("(SELECT A.SPLIT_ID, A.SHIP_COUNT, NVL(A.ACCEPT_COUNT, 0) ACCEPT_COUNT\n" );
    	sql.append("   FROM PT_BU_PCH_ORDER_SPLIT_DTL A WHERE 1=1\n");
    	if(!"".equals(partCode)&&partCode!=null){
    		sql.append(" AND A.PART_CODE LIKE '%"+partCode+"%'\n");
    	}
    	if(!"".equals(partName)&&partName!=null){
    		sql.append(" AND A.PART_NAME LIKE '%"+partName+"%'\n");
    	}
    	sql.append(" AND NVL(A.ACCEPT_COUNT,0) < NVL(A.SHIP_COUNT,0)) T2");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("PURCHASE_TYPE", DicConstant.CGDDLX);
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		bs.setFieldUserID("APPLY_USER");
    	return bs;
    }
	public boolean acceptPart(String detailId,String counts,String distributionNo,User user)
            throws Exception {
		StringBuffer sql = new StringBuffer();
		if(!"myNull".equals(distributionNo)){
			sql.append("UPDATE PT_BU_PCH_ORDER_SPLIT_DTL SET ACCEPT_COUNT = "+counts+", DISTRIBUTION_NO=DECODE(DISTRIBUTION_NO,NULL,'',DISTRIBUTION_NO||',')||'"+distributionNo+"' WHERE DETAIL_ID ='"+detailId+"'\n");
		}else{
			sql.append("UPDATE PT_BU_PCH_ORDER_SPLIT_DTL SET ACCEPT_COUNT = "+counts+"  WHERE DETAIL_ID ='"+detailId+"'\n");
		}
		return factory.update(sql.toString(), null);
    }
	
	/**
	 * 
	 * insertAcceptPartLog: 采购验收添加日志记录
	 * @author fuxiao
	 * Date:2014年11月16日
	 * @throws SQLException 
	 *
	 */
	public boolean insertAcceptPartLog(String[] args) throws SQLException{
		String sql = 
					"INSERT INTO PT_BU_IN_ACCEPT_LOG\n" +
					" (TID, PART_ID, PART_CODE, PART_NAME, SPLIT_ID, SPLIT_NO,\n" + 
					"  SUPPLIER_ID, SUPPLIER_NAME, SUPPLIER_CODE,\n" + 
					"  CHECK_COUNT, CHECK_TIME, CREATE_USER, CREATE_TIME)\n" + 
					"SELECT\n" + 
					" F_GETID(), D.PART_ID, D.PART_CODE, D.PART_NAME,D.SPLIT_ID, (SELECT S.SPLIT_NO FROM PT_BU_PCH_ORDER_SPLIT S WHERE S.SPLIT_ID = D.SPLIT_ID),\n" + 
					" D.SUPPLIER_ID, D.SUPPLIER_NAME, D.SUPPLIER_CODE,\n" + 
					" ?, SYSDATE, ?, SYSDATE\n" + 
					"FROM PT_BU_PCH_ORDER_SPLIT_DTL D WHERE D.DETAIL_ID = ?";
		return this.factory.update(sql, args);
	}
	
	public boolean updateAccept(String SPLIT_ID,int counts,User user)
            throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE PT_BU_PCH_ORDER_SPLIT SET ACCEPT_COUNT = NVL(ACCEPT_COUNT,0)+ "+counts+" WHERE SPLIT_ID ='"+SPLIT_ID+"'\n");
		return factory.update(sql.toString(), null);
    }
//	public boolean updateAccept(PtBuPchOrderSplitVO vo)
//            throws Exception {
//        return factory.update(vo);
//    }
	public BaseResultSet orderPartSearch(PageManager page, User user, String SPLIT_ID,String conditions,String warehouseType) throws Exception
    {
		String wheres = conditions;
		wheres += " AND T.PART_ID = T1.PART_ID\n";
		wheres += " AND T.SPLIT_ID = T2.SPLIT_ID\n";
		wheres += " AND T.PART_ID = T3.PART_ID(+)\n";
		wheres += " AND T3.WAREHOUSE_ID = T4.WAREHOUSE_ID\n";
		wheres += " AND T4.WAREHOUSE_TYPE = '"+warehouseType+"'\n";
		wheres += " AND T.SPLIT_ID = "+SPLIT_ID+"\n";
		wheres += " AND NVL(T.SHIP_COUNT,0) - NVL(T.ACCEPT_COUNT, 0) >0 ORDER BY T.PART_CODE\n";
		page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DETAIL_ID,\n" );
    	sql.append("       T.SPLIT_ID,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T1.UNIT,\n" );
    	sql.append("       T1.MIN_PACK,\n" );
    	sql.append("       NVL(T.SHIP_COUNT,0) SHIP_COUNT,\n" );
    	sql.append("       T.PCH_PRICE,\n" );
    	sql.append("       T.PCH_AMOUNT,\n" );
    	sql.append("       NVL(T.SHIP_COUNT,0) - NVL(T.ACCEPT_COUNT, 0) WILL_ACCEPT_COUNT,\n" );
    	sql.append("       NVL(T.ACCEPT_COUNT, 0) ACCEPT_COUNT,\n" );
    	sql.append("       T2.DELIVERY_CYCLE,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T3.USER_ACCOUNT\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL T, PT_BA_INFO T1, PT_BU_PCH_ORDER_SPLIT T2,PT_BA_WAREHOUSE_KEEPER T3,PT_BA_WAREHOUSE T4\n" );
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldUserID("USER_ACCOUNT");
    	return bs;
    }
	
	public QuerySet CheckPosi(String DETAIL_ID,String SPLIT_ID,User user)throws Exception
    {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT 1\n" );
        sql.append("  FROM PT_BA_WAREHOUSE_KEEPER T\n" );
        sql.append(" WHERE PART_ID = (SELECT PART_ID\n" );
        sql.append("                    FROM PT_BU_PCH_ORDER_SPLIT_DTL\n" );
        sql.append("                   WHERE DETAIL_ID = "+DETAIL_ID+"\n" );
        sql.append("                     AND SPLIT_ID = "+SPLIT_ID+")\n" );
        sql.append("   AND T.WAREHOUSE_ID =\n" );
        sql.append("       (SELECT WAREHOUSE_ID FROM PT_BA_WAREHOUSE WHERE ORG_ID = "+user.getOrgId()+")");
        qs = factory.select(null, sql.toString());
        return qs;
    }
	
	public QuerySet CheckPosi1(String DETAIL_ID,String SPLIT_ID,User user)throws Exception
    {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT 1\n" );
        sql.append("  FROM PT_BA_WAREHOUSE_PART_RL T\n" );
        sql.append(" WHERE PART_ID = (SELECT PART_ID\n" );
        sql.append("                    FROM PT_BU_PCH_ORDER_SPLIT_DTL\n" );
        sql.append("                   WHERE DETAIL_ID = "+DETAIL_ID+"\n" );
        sql.append("                     AND SPLIT_ID = "+SPLIT_ID+")\n" );
        sql.append("   AND T.WAREHOUSE_ID =\n" );
        sql.append("       (SELECT WAREHOUSE_ID FROM PT_BA_WAREHOUSE WHERE ORG_ID = "+user.getOrgId()+")");
        qs = factory.select(null, sql.toString());
        return qs;
    }
	
	public QuerySet getPartCode(String DETAIL_ID,String SPLIT_ID)throws Exception
    {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT PART_CODE\n" );
        sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL\n" );
        sql.append(" WHERE DETAIL_ID = "+DETAIL_ID+"\n" );
        sql.append("   AND PART_ID = "+SPLIT_ID+"");
        qs = factory.select(null, sql.toString());
        return qs;
    }
	public QuerySet checkOrg(String orgId)throws Exception
    {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT WAREHOUSE_TYPE FROM PT_BA_WAREHOUSE WHERE ORG_ID = "+orgId+"");
        qs = factory.select(null, sql.toString());
        return qs;
    }
}

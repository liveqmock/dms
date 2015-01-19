package com.org.dms.dao.part.basicInfoMng;


import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.vo.part.PtBuInventoryDtlVO;
import com.org.dms.vo.part.PtBuInventoryVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PtBuInventoryDtlDao extends BaseDAO {
	// 定义instance
	public static final PtBuInventoryDtlDao getInstance(ActionContext atx) {
		PtBuInventoryDtlDao dao = new PtBuInventoryDtlDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}
	//判断配件代码是否存在
	public QuerySet checkInventory_no(String inventory_no) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM PT_BU_INVENTORY \n");
    	sql.append(" WHERE INVENTORY_NO = '" + inventory_no +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	//插入信息
	public boolean insertPtBuInventoryDtl(PtBuInventoryDtlVO vo) throws Exception {
		return factory.insert(vo);
	}	
	
	//修改信息
	public boolean updatePtBuInventoryDtl(PtBuInventoryDtlVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	//删除信息
	public boolean updatePtBuInventoryDtlStatus(String dtl_id, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BU_INVENTORY_DTL \n");
    	sql.append(" SET STATUS = " + status + " \n");
    	sql.append(" WHERE dtl_id = " + dtl_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	
	//查询
	public BaseResultSet search(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	wheres += " ORDER BY CREATE_TIME DESC";
    	
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();

    	sql.append("SELECT\n" );
    	sql.append("DTL_ID,\n" );
    	sql.append("INVENTORY_ID,\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("SUPPLIER_ID,\n" );
    	sql.append("SUPPLIER_NAME,\n" );
    	sql.append("SUPPLIER_CODE,\n" );
    	sql.append("AREA_ID,\n" );
    	sql.append("AREA_CODE,\n" );
    	sql.append("AREA_NAME,\n" );
    	sql.append("POSITION_ID,\n" );
    	sql.append("POSITION_CODE,\n" );
    	sql.append("POSITION_NAME,\n" );
    	sql.append("WHOUSE_KEEPER,\n" );
    	sql.append("PAPER_COUNT,\n" );
    	sql.append("MATERIAL_COUNT,\n" );
    	sql.append("PLAN_PRICE,\n" );
    	sql.append("AMOUNT,\n" );
    	sql.append("INVENTORY_RESULT,\n" );
    	sql.append("REMARKS\n" );
    	sql.append("FROM PT_BU_INVENTORY_DTL\n" );


    	
	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，对于配件名称字典（PART_NAME），将字典代码翻译为字典名称	  
    	bs.setFieldDic("INVENTORY_TYPE", "PDLX");   		//盘点类型
        bs.setFieldDic("INVENTORY_STATUS", "PDZT");         //盘点状态
   
        bs.setFieldDateFormat("INVENTORY_DATE", "yyyy-MM-dd");		//盘点日期
    	return bs;
    }
	//查询配件
	public BaseResultSet partList(PageManager page,String conditions,String inventory_id) throws Exception {
    	String wheres = conditions;  
//    	wheres += " ORDER BY CREATE_TIME DESC";
    	wheres += " AND INVENTORY_ID='"+inventory_id+"'  ";
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("DTL_ID,\n" );
    	sql.append("INVENTORY_ID,\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("SUPPLIER_ID,\n" );
    	sql.append("SUPPLIER_NAME,\n" );
    	sql.append("SUPPLIER_CODE,\n" );
    	sql.append("AREA_ID,\n" );
    	sql.append("AREA_CODE,\n" );
    	sql.append("AREA_NAME,\n" );
    	sql.append("POSITION_ID,\n" );
    	sql.append("POSITION_CODE,\n" );
    	sql.append("POSITION_NAME,\n" );
    	sql.append("WHOUSE_KEEPER,\n" );
    	sql.append("PAPER_COUNT,\n" );
    	sql.append("MATERIAL_COUNT,\n" );
    	sql.append("PLAN_PRICE,\n" );
    	sql.append("AMOUNT,\n" );
    	sql.append("INVENTORY_RESULT,\n" );
    	sql.append("REMARKS\n" );
    	sql.append("FROM PT_BU_INVENTORY_DTL\n" );

    	bs = factory.select(sql.toString(), page); 
    	return bs;
    }
	
	//查询配件
	public BaseResultSet partSearch(PageManager page,String conditions,String warehouse_id) throws Exception {
    	String wheres = conditions;  
//    	wheres += " ORDER BY CREATE_TIME DESC";
    	wheres += " and  a.stock_id = b.stock_id ";
    	wheres += " and b.part_id = c.part_id ";
    	wheres += " and a.warehouse_id = '"+warehouse_id+"' ";
    	
    	
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select b.dtl_id,\n" );
    	sql.append("       b.part_id,\n" );
    	sql.append("       b.part_code,\n" );
    	sql.append("       b.part_name,\n" );
    	sql.append("       c.if_suply,\n" );
    	sql.append("       b.supplier_id,\n" );
    	sql.append("       b.supplier_code,\n" );
    	sql.append("       b.supplier_name,\n" );
    	sql.append("       b.area_id,\n" );
    	sql.append("       b.area_code,\n" );
    	sql.append("       b.area_name,\n" );
    	sql.append("       b.position_id,\n" );
//    	sql.append("       b.part_code,\n" );
    	sql.append("       b.position_name,\n" );
    	sql.append("       b.amount\n" );
    	sql.append("  from pt_bu_stock a, pt_bu_stock_dtl b, pt_ba_info c\n" );

    	bs = factory.select(sql.toString(), page); 
    	return bs;
    }
	//批量新增
	public boolean batchInsertPchAttribute(String partIds,String inventory_id)
			throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO PT_BU_INVENTORY_DTL(DTL_ID,INVENTORY_ID,PART_ID,PART_CODE,PART_NAME,PLAN_PRICE) \n");
		sql.append(" SELECT f_getid(),'"+inventory_id+"',PART_ID,PART_CODE,PART_NAME,PLAN_PRICE \n");
		sql.append(" from PT_BA_INFO  \n");
		sql.append(" WHERE PART_ID IN (" + partIds + ") \n");
		return factory.update(sql.toString(), null);
	}
	 /**
     * 生成盘点编号
     *
     * @param WAREHOUSE_CODE 盘点仓库代码
     * @return
     * @throws Exception
     */
    public String createInventory_No(String WAREHOUSE_CODE) throws Exception {
        return PartOddNumberUtil.getMoveAndOtherOutBillNo(factory, WAREHOUSE_CODE);
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
    //库存盘点表导出
//    public QuerySet download(String conditions,String inventory_id) throws Exception {
    public QuerySet download(String inventory_id) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,\n" );
//    	sql.append("SELECT \n" );
    	sql.append("PAPER_AMOUNT,\n" );					//账面金额
    	sql.append("MATERIAL_AMOUNT,\n" );				//实物金额
    	sql.append("INVENTORY_NO,\n" );
    	sql.append("STOCK_DTL_ID,\n" );
    	sql.append("DTL_ID,\n" );
    	sql.append("INVENTORY_ID,\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("SUPPLIER_ID,\n" );
    	sql.append("SUPPLIER_NAME,\n" );
    	sql.append("SUPPLIER_CODE,\n" );
    	sql.append("AREA_ID,\n" );
    	sql.append("AREA_CODE,\n" );
    	sql.append("AREA_NAME,\n" );
    	sql.append("POSITION_ID,\n" );
    	sql.append("POSITION_CODE,\n" );
    	sql.append("POSITION_NAME,\n" );
    	sql.append("WHOUSE_KEEPER,\n" );
    	sql.append("PAPER_COUNT,\n" );					//账面数量
    	sql.append("MATERIAL_COUNT,\n" );				//实物数量	
    	sql.append("PLAN_PRICE,\n" );
    	sql.append("AMOUNT,\n" );
    	sql.append("INVENTORY_RESULT,\n" );
    	sql.append("REMARKS\n" );
    	sql.append("FROM PT_BU_INVENTORY_DTL");
    	sql.append(" WHERE INVENTORY_ID ='"+inventory_id+"' \n");
//    	sql.append(" AND "+conditions+"\n" );
//    	sql.append(" ORDER BY PART_ID " );

        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }
  //库存明细错误信息导出
	public QuerySet expTmpErrorData(String pConditions,User user) throws Exception {
		String wheres = " WHERE ROW_NUM IN ("+ pConditions + ") \n";
    	wheres += " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
    	wheres += " ORDER BY ROW_NUM \n";
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ROW_NUM,\n" );
    	sql.append("DTL_ID,\n" );
    	sql.append("INVENTORY_NO,\n" );
    	sql.append("WHOUSE_KEEPER,\n" );
    	sql.append("AREA_CODE,\n" );
    	sql.append("POSITION_NAME,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("SUPPLIER_NAME,\n" );
    	sql.append("PLAN_PRICE,\n" );
    	sql.append("PAPER_COUNT,\n" );
    	sql.append("MATERIAL_COUNT,\n" );
    	sql.append("REMARKS\n" );
    	
    	sql.append("FROM PT_BU_INVENTORY_DTL_TMP");

    	return factory.select(null, sql.toString()+wheres);
    }
}

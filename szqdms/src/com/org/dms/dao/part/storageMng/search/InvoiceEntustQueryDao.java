package com.org.dms.dao.part.storageMng.search;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: ShipmentsRequirementDao 
 * Function: 发货满足率查询Dao
 * date: 2014年10月27日 上午10:04:10
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class InvoiceEntustQueryDao extends BaseDAO {
	
	public static final InvoiceEntustQueryDao getInstance(ActionContext ac){
		InvoiceEntustQueryDao dao = new InvoiceEntustQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
     * 订单查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download(String conditions,User user) throws Exception {
    	// 判断查询用户是否为审核员
    	if(this.checkUserIsCheck(user)){
	    	conditions += " AND E.USER_ACCOUNT = '" + user.getAccount() + "'";
    	}
    	conditions += " AND PRINT_STATUS ="+DicConstant.DYZT_02+" ORDER BY CREATE_TIME DESC\n";
		String sql = 
						"SELECT\n" +
						"       ENTRUST_ID,\n" + 
						"       ENTRUST_NO,\n" + 
						"       PRINT_DATE,\n" + 
						"       ORG_CODE,\n" + 
						"       (SELECT T.PERSON_NAME FROM TM_USER T WHERE T.ACCOUNT = USER_ACCOUNT) USER_ACCOUNT,\n" + 
						"       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = TARIFF_TYPE) TARIFF_TYPE,\n" + 
						"       ORG_NAME,\n" + 
						"       ADDRESS,\n" + 
						"       TELEPHONE,\n" + 
						"       TARIFF_NO,\n" + 
						"       OPEN_BANK,\n" + 
						"       BANK_ACCOUNT,\n" + 
						"       (SELECT SUM(ED.IN_INVOICE_AMOUNT) FROM PT_BU_INVOICE_ENTRUST_DTL ED  WHERE ED.ENTRUST_ID = E.ENTRUST_ID) IN_INVOICE_AMOUNT,\n" + 
						"       REMARKS,(SELECT DIC_VALUE FROM DIC_TREE WHERE ID = STATUS) STATUS\n" + 
						"  FROM PT_BU_INVOICE_ENTRUST E";

        return factory.select(null, sql + " WHERE " + conditions);
    }
    
    /**
     * 
     * @Title: queryList
     * @Description: 查询表格数据
     * @param pageManager
     * @param conditions
     * @param user
     * @return
     * @throws Exception
     * @return: BaseResultSet
     */
    public BaseResultSet queryList(PageManager pageManager, String conditions, User user) throws Exception{
    	
    	// 判断查询用户是否为审核员
    	if(this.checkUserIsCheck(user)){
	    	conditions += " AND E.USER_ACCOUNT = '" + user.getAccount() + "'";
    	}
    	return this.queryList(pageManager, conditions);
    }
    
    /**
     * 
     * @Title: checkUserIsCheck
     * @Description: 判断用户是否为审核员
     * @param user
     * @return true 是  false 不是
     * @throws Exception
     * @return: boolean
     */
    public boolean checkUserIsCheck(User user) throws Exception {
    	return Integer.parseInt(this.factory.select("SELECT COUNT(1) FROM PT_BA_ORDER_CHECK T WHERE T.USER_ACCOUNT = ?", new Object[]{user.getAccount()})[0][0]) > 0;
    }
	/**
	 * 
	 * queryList: 查询表单查询
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions) throws Exception{
		BaseResultSet rs = null;
		String condition = conditions+" AND PRINT_STATUS ="+DicConstant.DYZT_02+" AND STATUS = " +DicConstant.YXBS_01+ " ORDER BY CREATE_TIME DESC\n";
		pageManager.setFilter(condition);
		String sql = 
						"SELECT\n" +
						"       ENTRUST_ID,\n" + 
						"       ENTRUST_NO,\n" + 
						"       PRINT_DATE,\n" + 
						"       ORG_CODE,\n" + 
						"       (SELECT T.PERSON_NAME FROM TM_USER T WHERE T.ACCOUNT = USER_ACCOUNT) USER_ACCOUNT,\n" + 
						"       TARIFF_TYPE,\n" + 
						"       ORG_NAME,\n" + 
						"       ADDRESS,\n" + 
						"       TELEPHONE,\n" + 
						"       TARIFF_NO,\n" + 
						"       OPEN_BANK,\n" + 
						"       BANK_ACCOUNT,\n" + 
						"       (SELECT SUM(ED.IN_INVOICE_AMOUNT) FROM PT_BU_INVOICE_ENTRUST_DTL ED  WHERE ED.ENTRUST_ID = E.ENTRUST_ID) IN_INVOICE_AMOUNT,\n" + 
						"       REMARKS,STATUS\n" + 
						"  FROM PT_BU_INVOICE_ENTRUST E";
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("TARIFF_TYPE", "FPLX");
		rs.setFieldDic("STATUS", "YXBS");
		rs.setFieldDateFormat("PRINT_DATE", "yyyy-MM-dd HH:mm:ss");
		return rs;
	}
	
	public void deleteEntust(String entustId,User user)throws Exception{
		String sql="UPDATE PT_BU_INVOICE_ENTRUST SET STATUS="+DicConstant.YXBS_02+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE ENTRUST_ID ="+entustId+"\n";
		factory.update(sql, null);
	}
}

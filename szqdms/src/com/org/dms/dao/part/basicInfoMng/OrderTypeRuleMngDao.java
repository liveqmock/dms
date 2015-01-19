package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.vo.part.PtBaOrderTypeRuleVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * @Description:订单类型规则维护Dao
 * @Date: 2014-07-14
 * @Author gouwentan
 */
public class OrderTypeRuleMngDao extends BaseDAO{
	public static final OrderTypeRuleMngDao getInstance(ActionContext atx) {
		OrderTypeRuleMngDao dao = new OrderTypeRuleMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 订单类型规则查询
	 */
	public BaseResultSet orderTypeRuleSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.COMPANY_ID = "+user.getCompanyId();
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.TYPERULE_ID,\n" );
    	sql.append("       T.TYPE_CODE,\n" );
    	sql.append("       T.ORDER_TYPE,\n" );
    	sql.append("       T.FIRST_LETTER,\n" );
    	sql.append("       T.IF_CHANEL,\n" );
    	sql.append("       T.IF_SUMMARY,\n" );
    	sql.append("       T.IF_CHOOSEADDR,\n" );
    	sql.append("       T.IF_FUNDS,\n" );
    	sql.append("       T.IF_STORAGE,\n" );
    	sql.append("       T.IF_OWNPICK,\n" );
    	sql.append("       T.IF_FREE,\n" );
    	sql.append("       T.FREE_TIMES,\n" );
    	sql.append("       T.IF_APPLYTIMES,\n" );
    	sql.append("       T.APPLY_TIMES,\n" );
    	sql.append("       T.IF_APPLYDATE,\n" );
    	sql.append("       T.DC_STARTDATE,\n" );
    	sql.append("       T.DC_ENDDATE,\n" );
    	sql.append("       T.SE_STARTDATE,\n" );
    	sql.append("       T.SE_ENDDATE,\n" );
    	sql.append("       T.STATUS\n" );
    	sql.append("  FROM PT_BA_ORDER_TYPE_RULE T");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("ORDER_TYPE", "DDLX");
		bs.setFieldDic("IF_CHANEL", "SF");
		bs.setFieldDic("IF_SUMMARY", "SF");
		bs.setFieldDic("IF_FREE", "SF");
		bs.setFieldDic("IF_CHOOSEADDR", "SF");
		bs.setFieldDic("IF_APPLYDATE", "SF");
		bs.setFieldDic("IF_FUNDS", "SF");
		bs.setFieldDic("IF_APPLYTIMES", "SF");
		bs.setFieldDic("IF_STORAGE", "SF");
		bs.setFieldDic("IF_OWNPICK", "SF");
		bs.setFieldDic("STATUS", "YXBS");
    	return bs;
    }
	/**
	 * 类型代码重复验证
	 */
	public QuerySet orderTypeCheck(String typeCode) throws Exception {
		StringBuilder sql= new StringBuilder();
		sql.append("SELECT\n" );
		sql.append("    1\n" );
		sql.append("FROM\n" );
		sql.append("    PT_BA_ORDER_TYPE_RULE\n" );
		sql.append("WHERE\n" );
		sql.append("    TYPE_CODE = '" + typeCode + "'");
        return factory.select(null, sql.toString());
    }
	/**
	 * 订单类型规则新增
	 */
	public boolean orderTypeRuleInsert(PtBaOrderTypeRuleVO vo)
            throws Exception {
		String typeRuleId=SequenceUtil.getCommonSerivalNumber(factory);
		vo.setTyperuleId(typeRuleId);
        return factory.insert(vo);
    }
	/**
	 * 订单类型规则修改
	 */
	public boolean orderTypeRuleUpdate(PtBaOrderTypeRuleVO vo)
            throws Exception {
        return factory.update(vo);
    }
	/**
	 * 订单类型规则删除
	 */
	public boolean orderTypeRuleDelete(PtBaOrderTypeRuleVO vo) throws Exception {
        return factory.delete(vo);
    }
}

package com.org.dms.dao.part.salesMng.search;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.org.dms.BaseTest;
import com.org.framework.common.DBFactory;
import com.org.framework.message.mail.newSendMail;
import com.org.mvc.context.ActionContext;

public class AmountSummaryQueryDaoTest{

	private AmountSummaryQueryDao dao;
	private ActionContext ac;
	private Map<String, String> map;
	private DBFactory dbFactory;
	
	@Before
	public void init() throws SQLException{
		ac = ActionContext.getContext();
		dao = AmountSummaryQueryDao.getInstance(ac);
		map = new HashMap<String, String>();
		map.put("BEGINDATE", "2014-01-01");
		map.put("ENDDATE", "2014-01-31");
		map.put("CODE", "K000001");
		dbFactory = new DBFactory();
		this.dbFactory.delete("DELETE PT_PU_AMOUNT_SUMMARY S WHERE S.WAREHOUSE_CODE = ?", new String[]{"K000001"});
	}
	
	@Test
	public void inserByProcedure() throws Exception{
		try {
			dao.inserByProcedure(map);
		} catch (Exception e) {
			throw e;
		}
	}
		
}

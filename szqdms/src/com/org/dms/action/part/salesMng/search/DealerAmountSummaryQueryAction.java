package com.org.dms.action.part.salesMng.search;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.org.dms.common.RowMapUtils;
import com.org.dms.dao.part.salesMng.search.DealerAmountSummaryQueryDao;
import com.org.framework.common.QuerySet;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: DealerAmountSummaryQueryAction 
 * Function: 配送中心库存金额汇总表
 * date: 2014年11月7日 下午2:40:25
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class DealerAmountSummaryQueryAction {
	
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("DealerAmountSummaryQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private DealerAmountSummaryQueryDao dao = DealerAmountSummaryQueryDao.getInstance(atx);

	/**
	 * 
	 * queryListInfo: 表单查询  
	 * @author fuxiao Date:2014年10月23日上午10:36:58
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			Map<String,String> hm = RequestUtil.getValues(requestWrapper);
/*			if(!dao.queryDataCountByDate(hm)){
				dao.inserByProcedure(hm);
			}*/
			
			dao.deleteDateByDate(hm);
			dao.inserByProcedure(hm);
			QuerySet qs = dao.queryInfoByDate(hm);
			JSONObject jsonObject = JSONObject.fromObject(RowMapUtils.toConvert(qs));
			atx.setOutMsg(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

}

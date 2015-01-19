package com.org.dms.action.part.purchaseMng.purchaseOrder;

import java.sql.SQLException;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.org.dms.common.RowMapUtils;
import com.org.dms.dao.part.purchaseMng.purchaseOrder.PchReturnOrderQueryDao;
import com.org.framework.Globals;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: PchReturnOrderQueryAction 
 * Function: 采供退货单查询
 * date: 2014年11月22日 下午2:12:39
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class PchReturnOrderQueryAction {
	
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("PchReturnOrderQueryAction");
	
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	
	// 定义dao对象
	private PchReturnOrderQueryDao dao = PchReturnOrderQueryDao.getInstance(atx);

	/**
	 * 
	 * queryListInfo:表单查询
	 * @author fuxiao
	 * Date:2014年11月22日
	 *
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions, user, true));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * queryStockInfoById: 根据Id查询入库信息
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public void queryInfoById() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			String id = Pub.val(requestWrapper, "id");
			QuerySet qs = this.dao.queryInfoById(id); 								// 查询入库单主信息
			Map<String, Map<String, String>> resultMap = RowMapUtils.toConvert(qs);	// 用来保存拼接的数据，用来转换成JSON
			JSONObject jsonObj = JSONObject.fromObject(resultMap);
			atx.setOutMsg(jsonObj.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 
	 * queryStockDetailsById: 根据ID查询详细信息
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public void queryDetailsById() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryInfoDetailsById(pageManager, conditions));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

}

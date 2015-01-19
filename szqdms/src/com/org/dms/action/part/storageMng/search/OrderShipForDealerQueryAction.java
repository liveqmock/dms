package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.common.RowMapUtils;
import com.org.dms.dao.part.storageMng.search.OrderShipForDealerQueryDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 
 * @ClassName: OrderShipForDealerQueryAction
 * @Description: 承运信息
 * @author: ALONY
 * @date: 2014年12月4日 下午6:01:06
 */
public class OrderShipForDealerQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log
			.getLogger("OrderShipForDealerQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private OrderShipForDealerQueryDao dao = OrderShipForDealerQueryDao
			.getInstance(atx);

	/**
	 * 
	 *  queryListInfo: 表单查询    @author fuxiao
	 * 
	 * @Date:2014年10月23日上午10:36:58
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,
					pageManager);
			atx.setOutData("bs",
					this.dao.queryList(pageManager, conditions, user, true));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

	public void shipSearch() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		HashMap<String, String> hm = RequestUtil.getValues(request);
		try {
			String ORDER_NO = hm.get("ORDER_NO");
			BaseResultSet bs = dao.shipSearch(page, user, conditions, ORDER_NO);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	public void oemShipSearch() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		HashMap<String, String> hm = RequestUtil.getValues(request);
		try {
			String ORDER_NO = hm.get("ORDER_NO");
			String orgType = user.getOrgDept().getOrgType();
			if (DicConstant.ZZLB_13.equals(orgType)) {
				BaseResultSet bs = dao.shipSearchCar(page, user, conditions,
						ORDER_NO);
				atx.setOutData("bs", bs);
			} else {
				BaseResultSet bs = dao.shipSearchOem(page, user, conditions,
						ORDER_NO);
				atx.setOutData("bs", bs);
			}

		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 
	  * queryStockInfoById: 根据Id查询入库信息  * @author fuxiao Date:2014年10月23日
	 *
	 */
	public void queryInfoById() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			String id = Pub.val(requestWrapper, "id");
			QuerySet qs = this.dao.queryInfoById(id); // 查询入库单主信息
			Map<String, Map<String, String>> resultMap = RowMapUtils
					.toConvert(qs); // 用来保存拼接的数据，用来转换成JSON
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
	  * queryDriverInfoById: 查询司机信息  * @author fuxiao Date:2014年11月28日
	 *
	 */
	public void queryDriverInfoById() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,
					pageManager);
			atx.setOutData("bs",
					this.dao.queryDriverInfoById(pageManager, conditions));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 
	  * queryOrderInfoById: 查询订单信息  * @author fuxiao Date:2014年11月28日
	 *
	 */
	public void queryOrderInfoById() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,
					pageManager);
			atx.setOutData("bs",
					this.dao.queryOrderInfoById(pageManager, conditions));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 
	 * @Title: exportExcel
	 * @Description: 下载信息
	 * @throws Exception
	 * @return: void
	 */
	public void exportExcel() throws Exception {
		ResponseWrapper response = atx.getResponse();
		RequestWrapper requestWrapper = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil
				.getConditionsWhere(requestWrapper, page);
		OutputStream os = null;
		try {
			QuerySet qs = null;

			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("SHIP_NO");
			hBean.setTitle("发运单号");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("SHIP_STATUS");
			hBean.setTitle("发运状态");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("RECEIPT_NO");
			hBean.setTitle("回执单号");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("LICENSE_PLATE");
			hBean.setTitle("车牌号");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("SHIP_DATE");
			hBean.setTitle("发运日期");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("CREATE_USER");
			hBean.setTitle("制单人");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("CREATE_TIME");
			hBean.setTitle("制单日期");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("REMARKS");
			hBean.setTitle("备注");
			header.add(hBean);
			
			HashMap<String, String> hm = RequestUtil.getValues(requestWrapper);
			String orderNo = hm.get("ORDER_NO");
			String orgType = user.getOrgDept().getOrgType();
			if (DicConstant.ZZLB_13.equals(orgType)) {
				qs = dao.queryDownInfo1(conditions, user, orderNo);
				
			} else {
				qs = dao.queryDownInfo2(conditions, user, orderNo);
				
			}
			
			os = response.getHttpResponse().getOutputStream();
			response.getHttpResponse().reset();
			ExportManager.exportFile(response.getHttpResponse(), "发货详情下载",
					header, qs);
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

}

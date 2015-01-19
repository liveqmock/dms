package com.org.dms.action.part.salesMng.orderMng;

import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.org.dms.common.RowMapUtils;
import com.org.dms.dao.part.storageMng.search.BoxInfoDealerQueryDao;
import com.org.framework.Globals;
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
 * ClassName: BoxInfoQueryAction 
 * Function: 装箱清单查询
 * date: 2014年11月20日 上午12:30:29
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class BoxInfoDealerQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("BoxInfoQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private BoxInfoDealerQueryDao dao = BoxInfoDealerQueryDao.getInstance(atx);


	/**
	 * 
	 * queryListInfo: 主信息查询
	 * @author fuxiao
	 * Date:2014年11月20日
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
	 * queryBoxInfoById: 装箱详细信息
	 * @author fuxiao
	 * Date:2014年11月20日
	 *
	 */
	public void queryBoxInfoById() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			String id = Pub.val(requestWrapper, "id");
			QuerySet qs = this.dao.queryBoxInfoById(id); 									// 装箱详细信息		
			Map<String, Map<String, String>> resultMap = RowMapUtils.toConvert(qs);		    // 转换JSON
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
	 * queryDetailsListInfoById: 查询详细情况列表
	 * @author fuxiao
	 * Date:2014年11月20日
	 *
	 */
	public void queryDetailsListInfoById() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryBoxDetailsListById(pageManager, conditions));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	

	/**
	 * 
	 * exportExcel: 导出
	 * @author fuxiao
	 * Date:2014年11月20日
	 *
	 */
	public void exportExcel() throws Exception{
		ResponseWrapper response= atx.getResponse();
    	RequestWrapper requestWrapper = atx.getRequest();
    	PageManager pm = new PageManager();
    	String conditons = RequestUtil.getConditionsWhere(requestWrapper, pm);
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("ORDER_NO");
    		hBean.setTitle("订单号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ISSUE_NO");
    		hBean.setTitle("发料单号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("配件名称");
    		hBean.setWidth(50);
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("BOX_NO");
    		hBean.setTitle("装箱单号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("COUNT");
    		hBean.setTitle("装箱数");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DELIVERY_COUNT");
    		hBean.setTitle("实发数量");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("REMARKS");
    		hBean.setTitle("备注");
    		header.add(hBean);
    		QuerySet qs = dao.queryDownInfoById(conditons);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "装箱清单", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
        finally
        {
        	if (os != null)
            {
              os.close();
            }
        }
	}

}

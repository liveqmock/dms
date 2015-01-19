package com.org.dms.action.part.storageMng.search;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.org.dms.common.RowMapUtils;
import com.org.dms.dao.part.storageMng.search.MoveStocksOutQueryDao;
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
 * ClassName: MoveStockOutQueryAction 
 * Function: 移库查询
 * date: 2014年11月18日 下午10:17:17
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class MoveStockOutQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("StockInQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private MoveStocksOutQueryDao dao = MoveStocksOutQueryDao.getInstance(atx);
	// 定义reponse对象
    private ResponseWrapper responseWrapper = atx.getResponse();
    // 获取session中的user对象
    private User user = (User) atx.getSession().get(Globals.USER_KEY);

	/**
     * 订单查询导出
     * @throws Exception
     */
    public void download()throws Exception{

        try {
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
        	// 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("OUT_NO");
            hBean.setTitle("出库单号");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("OUT_WAREHOUSE_CODE");
            hBean.setTitle("出库仓库代码");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("OUT_WAREHOUSE_NAME");
            hBean.setTitle("出库仓库名称");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("IN_WAREHOUSE_CODE");
            hBean.setTitle("入库仓库代码");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("IN_WAREHOUSE_NAME");
            hBean.setTitle("入库仓库名称");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("PLAN_AMOUNT");
            hBean.setTitle("计划金额(元)");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("OUT_DATE");
            hBean.setTitle("出库日期");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("PRINT_STATUS");
            hBean.setTitle("是否打印");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("PRINT_DATE");
            hBean.setTitle("打印日期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PRINT_MAN");
            hBean.setTitle("打印人");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MOVE_MAN");
            hBean.setTitle("移库人");
            header.add(hBean);
            
            QuerySet querySet = dao.download(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "移库导出", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    
    
	/**
	 * 
	 * queryListInfo: 表单查询  
	 * @author fuxiao 
	 * @Date:2014年10月23日上午10:36:58
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
	public void queryStockInfoById() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			String outId = Pub.val(requestWrapper, "outId");
			QuerySet qs = this.dao.queryMoveStockInfoById(outId); 											// 查询入库单主信息
			Map<String, Map<String, String>> resultMap = RowMapUtils.toConvert(qs);						// 用来保存拼接的数据，用来转换成JSON
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
	public void queryStockDetailsById() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryStockInfoDetailsById(pageManager, conditions));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

}

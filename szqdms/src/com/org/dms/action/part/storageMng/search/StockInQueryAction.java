package com.org.dms.action.part.storageMng.search;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.org.dms.common.RowMapUtils;
import com.org.dms.dao.part.storageMng.search.StocksInQueryDao;
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
 * ClassName: StockInQueryAction 
 * Function: 入库单查询
 * date: 2014年10月23日 下午2:11:06
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class StockInQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("StockInQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private StocksInQueryDao dao = StocksInQueryDao.getInstance(atx);
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
			conditions = this.filterConditions(conditions, request);
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("IN_NO");
            hBean.setTitle("入库单号");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORDER_NO");
            hBean.setTitle("采购单号");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("PLAN_AMOUNT");
            hBean.setTitle("计划金额");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SALE_AMOUNT");
            hBean.setTitle("销售金额");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("WAREHOUSE_NAME");
            hBean.setTitle("仓库");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("IN_TYPE");
            hBean.setTitle("入库类型");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("RETURN_NAME");
            hBean.setTitle("退件单位名称");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("IN_DATE");
            hBean.setTitle("入库日期");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("供应商");
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
            
            QuerySet querySet = dao.download(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "入库单导出", header, querySet);
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
			conditions = this.filterConditions(conditions, requestWrapper);
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions, user, true));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	public String filterConditions(String conditions, RequestWrapper requestWrapper){
		Map<String, String> map = RequestUtil.getValues(requestWrapper);
		String orgCode = map.get("ORG_CODE") == null ? "" : map.get("ORG_CODE");
		
		if(!"".equals(orgCode)){
			conditions +=   " AND EXISTS (\n" +
							"    SELECT 1 FROM PT_BU_RETURN_APPLY A WHERE A.RETURN_ID = T.ORDER_ID AND T.ORG_CODE = '" + orgCode + "'\n" + 
							" ) ";
		}
		
		return conditions;
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
			String inId = Pub.val(requestWrapper, "inId");
			QuerySet qs = this.dao.queryStockInfoById(inId); 											// 查询入库单主信息
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

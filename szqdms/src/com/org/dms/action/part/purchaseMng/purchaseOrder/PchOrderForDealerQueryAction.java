package com.org.dms.action.part.purchaseMng.purchaseOrder;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.org.dms.common.RowMapUtils;
import com.org.dms.dao.part.purchaseMng.purchaseOrder.PchOrderForDealerQuerylDao;
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
 * ClassName: PchOrderForDealerQueryAction 
 * Function: 采购订单查询（经销商）
 * date: 2014年11月13日 下午2:18:49
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class PchOrderForDealerQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("PchOrderForDealerQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private PchOrderForDealerQuerylDao dao = PchOrderForDealerQuerylDao.getInstance(atx);
	private ResponseWrapper responseWrapper = atx.getResponse();
	

	/**
	 * 
	 * queryListInfo:表单查询
	 * @author fuxiao
	 * Date:2014年11月13日
	 *
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * queryInfoById: 根据ID查询详细信息
	 * @author fuxiao
	 * Date:2014年11月13日
	 *
	 */
	public void queryInfoById(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			String id = Pub.val(requestWrapper, "id");
			QuerySet qs = this.dao.queryInfoById(id);
			Map<String, Map<String, String>> resultMap = RowMapUtils.toConvert(qs);						// 用来保存拼接的数据，用来转换成JSON
			JSONObject jsonObj = JSONObject.fromObject(resultMap);
			atx.setOutMsg(jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * queryInfoDetailsById: 根据ID查询子表信息
	 * @author fuxiao
	 * Date:2014年11月13日
	 *
	 */
	public void queryInfoDetailsById(){
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


	/**
	 * 
	 * exportExcel:导出
	 * @author fuxiao
	 * Date:2014年11月13日
	 *
	 */
	public void exportExcel() throws Exception{
		ResponseWrapper response= atx.getResponse();
    	RequestWrapper requestWrapper = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
 		String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> list = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("SPLIT_NO");
    		hBean.setTitle("订单编号");
    		list.add(hBean);
    		
	    	hBean = new HeaderBean();
    		hBean.setName("PURCHASE_AMOUNT");
    		hBean.setTitle("订单金额");
    		list.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PLAN_DISTRIBUTION");
    		hBean.setTitle("计配号");
    		list.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("供应商名称");
    		list.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PURCHASE_TYPE");
    		hBean.setTitle("采购类型");
    		list.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SELECT_MONTH");
    		hBean.setTitle("所属月度");
    		list.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("APPLY_DATE");
    		hBean.setTitle("制单日期");
    		list.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("CLOSE_DATE");
    		hBean.setTitle("关单日期");
    		list.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ORDER_STATUS");
    		hBean.setTitle("采购单状态");
    		list.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("APPLY_USER");
    		hBean.setTitle("制单人");
    		list.add(hBean);
    		
    		QuerySet qs = dao.queryDownInfo(conditions, user);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "采购订单", list, qs);
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
	
	public void download() throws Exception {

        try {
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
            User user = (User) atx.getSession().get(Globals.USER_KEY);
            String SPLIT_ID = Pub.val(request, "SPLIT_ID");
			
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("SPLIT_NO");
            hBean.setTitle("订单编号");
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
            hBean.setName("UNIT");
            hBean.setTitle("计量单位");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("MIN_PACK");
            hBean.setTitle("最小包装");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("PCH_COUNT");
            hBean.setTitle("采购数量");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("PCH_PRICE");
            hBean.setTitle("采购价格");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("PCH_AMOUNT");
            hBean.setTitle("金额");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("DELIVERY_CYCLE");
            hBean.setTitle("供货周期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("REMARKS");
            hBean.setTitle("备注 ");
            header.add(hBean);
            
            QuerySet querySet = dao.download(user,SPLIT_ID);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "DDCXDC", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
	}
	
}

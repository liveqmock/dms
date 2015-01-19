package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.MoveStockDetailsQueryDao;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 
 * ClassName: MoveStockDetailsQueryAction 
 * Function: 移库数据统计Action
 * date: 2014年11月25日 下午2:58:23
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class MoveStockDetailsQueryAction {
	
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("SummaryDtlQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private MoveStockDetailsQueryDao dao = MoveStockDetailsQueryDao.getInstance(atx);

	/**
	 * 
	 * queryListInfo:表格查询
	 * @author fuxiao
	 * Date:2014年11月25日
	 *
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			String sql = dao.getString(requestWrapper, false);
			atx.setOutData("bs", this.dao.queryList(pageManager, sql));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 
	 * exportExcel: 表格DownInfo
	 * @author fuxiao
	 * Date:2014年11月25日
	 *
	 */
	public void exportExcel() throws Exception{
		ResponseWrapper response= atx.getResponse();
    	RequestWrapper requestWrapper = atx.getRequest();
    	String sql = dao.getString(requestWrapper, true);
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
    		hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("零件编号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("零件名称");
    		hBean.setWidth(50);
    		header.add(hBean);
	    	hBean = new HeaderBean();
    		hBean.setName("OUT_NO");
    		hBean.setTitle("移库单号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OUT_WAREHOUSE_NAME");
    		hBean.setTitle("出库仓库");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OUT_AMOUNT");
    		hBean.setTitle("移库数量");
    		header.add(hBean);
//    		hBean = new HeaderBean();
//    		hBean.setName("PLAN_PRICE");
//    		hBean.setTitle("计划价");
//    		header.add(hBean);
//    		hBean = new HeaderBean();
//    		hBean.setName("PLAN_AMOUNT");
//    		hBean.setTitle("计划金额");
//    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("IN_WAREHOUSE_NAME");
    		hBean.setTitle("目标仓库");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MOVE_DATE");
    		hBean.setTitle("移库时间");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("REMARKS");
    		hBean.setTitle("备注");
    		header.add(hBean);
    		QuerySet qs = dao.queryDownInfo(sql);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "移库数据统计", header, qs);
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

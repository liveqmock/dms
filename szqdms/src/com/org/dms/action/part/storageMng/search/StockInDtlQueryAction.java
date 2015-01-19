package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.StockInDtlQueryDao;
import com.org.framework.common.BaseResultSet;
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
 * @author zhengyao
 *
 */
public class StockInDtlQueryAction {
    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger("StockInDtlQueryAction");
    // 上下文对象
    private ActionContext atx = ActionContext.getContext();
    // 定义dao对象
    private StockInDtlQueryDao dao = StockInDtlQueryDao.getInstance(atx);

    /**
     * 
     * queryListInfo: 表单查询  
     * @author fuxiao Date:2014年10月23日上午10:36:58
     */
    public void queryListInfo() {
        try {
            RequestWrapper requestWrapper = atx.getRequest();
            PageManager pageManager = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
            atx.setOutData("bs", this.dao.queryList(pageManager, conditions));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 
     * exportExcel: 查询数据导出  
     * @author fuxiao Date:2014年10月23日
     *
     */
    public void exportExcel() throws Exception{
        ResponseWrapper response= atx.getResponse();
        RequestWrapper requestWrapper = atx.getRequest();
        PageManager page = new PageManager();
         String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
         OutputStream os = null;
        try
        {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
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
            hBean.setName("DISTRIBUTION_NO");
            hBean.setTitle("配送号");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IN_TYPE");
            hBean.setTitle("入库类型");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("WAREHOUSE_NAME");
            hBean.setTitle("入库仓库");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ORDER_NO");
            hBean.setTitle("订单号");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IN_NO");
            hBean.setTitle("入库单号");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IN_DATE");
            hBean.setTitle("入库日期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PRINT_DATE");
            hBean.setTitle("打印日期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IN_AMOUNT");
            hBean.setTitle("入库数量");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PLAN_PRICE");
            hBean.setTitle("入库计划价");
            header.add(hBean);

            
            hBean = new HeaderBean();
            hBean.setName("PLAN_AMOUNT");
            hBean.setTitle("入库金额");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PCH_PRICE");
            hBean.setTitle("采购价");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PCH_AMOUNT");
            hBean.setTitle("采购金额");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SALE_PRICE");
            hBean.setTitle("销售价");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SALE_AMOUNT");
            hBean.setTitle("销售金额");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_CODE");
            hBean.setTitle("发货方代码");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("发货方名称");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CHECK_USER");
            hBean.setTitle("审核员");
            header.add(hBean);
            
            QuerySet qs = dao.queryDownInfo(conditions);
            os = response.getHttpResponse().getOutputStream();
            response.getHttpResponse().reset();
            ExportManager.exportFile(response.getHttpResponse(), "入库明细", header, qs);
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
    public void getAmount() throws Exception {
        RequestWrapper request = atx.getRequest();
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.getAmount(page, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
}

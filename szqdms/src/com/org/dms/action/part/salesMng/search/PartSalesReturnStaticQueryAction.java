package com.org.dms.action.part.salesMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.org.dms.common.RowMapUtils;
import com.org.dms.dao.part.salesMng.search.PartSalesReturnStaticQueryDao;
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
 * ClassName: PartSalesReturnStaticQueryAction 
 * Function: 配件销售回款统计表
 * date: 2014-12-10 
 * @author suoxiuli
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class PartSalesReturnStaticQueryAction {
	
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("PartSalesReturnStaticQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private PartSalesReturnStaticQueryDao dao = PartSalesReturnStaticQueryDao.getInstance(atx);
	
	/**
     * 配件销售回款统计表查询
     * @throws Exception
     * Author suoxiuli 2014-07-14
     */
    public void queryListInfo() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		//String conditions = RequestUtil.getConditionsWhere(request,page);
		Map<String,String> hm = RequestUtil.getValues(request);	
		
		try
		{
			BaseResultSet bs = dao.queryInfoByDate(page, hm);
			//request.setAttribute("sum", 150+"");
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}

    /**
     * 某个配送中心的配件销售回款统计查询
     * @throws Exception
     * Author suoxiuli 2014-12-23
     */
	public String[] dataXml(String statisticId) {
		String strXMLTotal = "";//回款额和销售金额
		String strXMLDetail = "";//细分产品金额
		String[] strXML = new String[2];
		try {
			QuerySet qs = dao.queryDCInfoByDate(statisticId);
			if (qs.getRowCount()>0) {
				/*String statiId = qs.getString(1,"STATISTIC_ID");//统计ID
	    		String bscId = qs.getString(1,"BSC_ID");//办事处ID
	    		String bscCode = qs.getString(1,"BSC_CODE");//办事处代码
	    		String bscName = qs.getString(1,"BSC_NAME");//办事处名称	    		
	    		String dcId = qs.getString(1,"DC_ID");//配送中心ID
	    		String dcCode = qs.getString(1,"DC_CODE");//配送中心代码
	    		String dcName = qs.getString(1,"DC_NAME");//配送中心名称    */		
	    		String statiMonth = qs.getString(1,"STATISTIC_MONTH");//统计时间
	    		
	    		String outstandAcc = qs.getString(1,"OUTSTAND_ACCOUNT");//回款额
	    		String salesAcc = qs.getString(1,"SALES_ACCOUNT");//销售金额
	    		
	    		String oilAcc = qs.getString(1,"OIL_ACCOUT");//油品
	    		String outLxAcc = qs.getString(1,"OUT_LX_ACCOUT");//保外滤芯
	    		String outLhqAcc = qs.getString(1,"OUT_LHQ_ACCOUT");//保外离合器
	    		String cqjAcc = qs.getString(1,"CQJ_ACCOUT");//车桥件
	    		String qdzcAcc = qs.getString(1,"QDZC_ACCOUT");//七大总成
	    		String otherOutAcc = qs.getString(1,"OTHER_OUT_ACCOUT");//其他保外产品
	    		String otherAcc = qs.getString(1,"OTHER_ACCOUT");//其他
	    		
	    		String[] salesDate = statiMonth.split("-");
	    		strXMLTotal += "<graph caption='月销量' subCaption='"+salesDate[0]+"年"+salesDate[1]+"月'  baseFontSize='12'   decimalPrecision='0' formatNumberScale='0'>";
	    		strXMLTotal += "<set name='回款额' value='" +outstandAcc+ "' color='AFD8F8'/>";
	    		strXMLTotal += "<set name='销售金额' value='" +salesAcc+ "' color='F6BD0F'/>";
	    		strXMLTotal += "</graph>";
	    		strXML[0] = strXMLTotal;
	    		
	    		strXMLDetail += "<graph caption='月销量' subCaption='"+salesDate[0]+"年"+salesDate[1]+"月'  baseFontSize='12'   decimalPrecision='0' formatNumberScale='0'>";
	    		strXMLDetail += "<set name='油品' value='" +oilAcc+ "' color='AFD8F8'/>";
	    		strXMLDetail += "<set name='保外滤芯' value='" +outLxAcc+ "' color='F6BD0F'/>";
	    		strXMLDetail += "<set name='保外离合器' value='" +outLhqAcc+ "' color='8BBA00'/>";
	    		strXMLDetail += "<set name='车桥件' value='" +cqjAcc+ "' color='FF8E46'/>";
	    		strXMLDetail += "<set name='七大总成' value='" +qdzcAcc+ "' color='008E8E'/>";
	    		strXMLDetail += "<set name='其他保外产品' value='" +otherOutAcc+ "' color='D64646'/>";
	    		strXMLDetail += "<set name='其他' value='" +otherAcc+ "' color='D64646'/>";
	    		strXMLDetail += "</graph>";
	    		strXML[1] = strXMLDetail;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strXML; 
	}
	
	/**
     * 配件销售回款统计表查询导出：报表的导出按钮
     * @throws Exception
     * @Author suoxiuli 2014-12-30
     */
    public void reportExportExcel() throws Exception{

    	ResponseWrapper response= atx.getResponse();
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	page.setPageRows(99999);
 		//String conditions = RequestUtil.getConditionsWhere(request,page);
 		Map<String,String> hm = RequestUtil.getValues(request);	
 		OutputStream os = null;
        
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            
            hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
    		
            hBean = new HeaderBean();
            hBean.setName("BSC_NAME");
            hBean.setTitle("办事处");
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("DC_CODE");
            hBean.setTitle("渠道代码");
            header.add(2,hBean);
			
            hBean = new HeaderBean();
            hBean.setName("DC_NAME");
            hBean.setTitle("渠道名称");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("RETURN_ACCOUT_14");
            hBean.setTitle("回款额");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("RETURN_ACCOUT_YEAR_GROWTH");
            hBean.setTitle("同比");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SALES_ACCOUNT_14");
            hBean.setTitle("销售金额");
            header.add(6,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SALES_ACCOUNT_YEAR_GROWTH");
            hBean.setTitle("同比");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OIL_ACCOUT");
            hBean.setTitle("油品");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OUT_LX_ACCOUT");
            hBean.setTitle("保外滤芯");
            header.add(9,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OUT_LHQ_ACCOUT");
            hBean.setTitle("保外离合器");
            header.add(10,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CQJ_ACCOUT");
            hBean.setTitle("车桥件");
            header.add(11,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("QDZC_ACCOUT");
            hBean.setTitle("七大总成");
            header.add(12,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OTHER_OUT_ACCOUT");
            hBean.setTitle("其他保外产品");
            header.add(13,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OTHER_ACCOUT");
            hBean.setTitle("其他");
            header.add(14,hBean);

            QuerySet querySet = dao.reportExportExcel(page, hm);
            os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
            ExportManager.exportFile(response.getHttpResponse(), "配件销售回款统计表查询", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
}

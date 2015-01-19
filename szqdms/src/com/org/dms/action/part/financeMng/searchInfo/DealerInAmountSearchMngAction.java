package com.org.dms.action.part.financeMng.searchInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.financeMng.searchInfo.DealerInAmountSearchMngDao;
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

public class DealerInAmountSearchMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private DealerInAmountSearchMngDao dao = DealerInAmountSearchMngDao.getInstance(atx);
	    
	    public void inAmountSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			
			try
			{
				String conditions = RequestUtil.getConditionsWhere(request, page);
				Map<String, String> hm = RequestUtil.getValues(request);
				String jd = hm.get("JD");
				String jd_m = jd.substring(5);
				String jd_y = jd.substring(0,4);
				String beginDate = "";
				String endDate = "";
				if("1".endsWith(jd_m)){
					beginDate = jd_y+"-01-01";
					endDate = jd_y+"-03-31";
				}else if("2".endsWith(jd_m)){
					beginDate = jd_y+"-04-01";
					endDate = jd_y+"-06-30";
				}else if("3".endsWith(jd_m)){
					beginDate = jd_y+"-07-01";
					endDate = jd_y+"-09-30";
				}else if("4".equals(jd_m)){
					beginDate = jd_y+"-10-01";
					endDate = jd_y+"-12-31";
				}else {
					throw new Exception("请输入正确季度数据.");
				}
				BaseResultSet bs = dao.inAmountSearch(page,user,jd,beginDate,endDate,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    //download
	    public void download()throws Exception{
			//获取封装后的request对象
			RequestWrapper request = atx.getRequest();
			ResponseWrapper response= atx.getResponse();
			try
			{
				List<HeaderBean> header = new ArrayList<HeaderBean>();
				HeaderBean hBean = null;
				hBean = new HeaderBean();
				hBean.setName("CODE");
				hBean.setTitle("渠道商代码");
				header.add(0,hBean);
				hBean = new HeaderBean();
				hBean.setName("ONAME");
				hBean.setTitle("渠道商名称");
				header.add(1,hBean); 
				hBean = new HeaderBean();
				hBean.setName("JD");
				hBean.setTitle("季度");
				header.add(2,hBean);
				hBean = new HeaderBean();
				hBean.setName("ALCGJE");
				hBean.setTitle("A类配件金额(元)");
				header.add(3,hBean);
				hBean = new HeaderBean();
				hBean.setName("YPCGJE");
				hBean.setTitle("油品金额(元)");
				header.add(4,hBean);
				hBean = new HeaderBean();
				hBean.setName("NFCGJE");
				hBean.setTitle("尿素与防冻液金额(元)");
				header.add(5,hBean);
				hBean = new HeaderBean();
				hBean.setName("BLCGJE");
				hBean.setTitle("B类配件金额(元)");
				header.add(6,hBean);
				hBean = new HeaderBean();
				hBean.setName("CLCGJE");
				hBean.setTitle("C类配件金额(元)");
				header.add(7,hBean);
				hBean = new HeaderBean();
				hBean.setName("DLCGJE");
				hBean.setTitle("D类配件金额(元)");
				header.add(8,hBean);
				hBean = new HeaderBean();
				hBean.setName("ZCGJE");
				hBean.setTitle("合计金额(元)");
				header.add(9,hBean);
				String jd =Pub.val(request, "JD");
				String jd_m = jd.substring(5);
				String jd_y = jd.substring(0,4);
				String beginDate = "";
				String endDate = "";
				if("1".endsWith(jd_m)){
					beginDate = jd_y+"-01-01";
					endDate = jd_y+"-03-31";
				}else if("2".endsWith(jd_m)){
					beginDate = jd_y+"-04-01";
					endDate = jd_y+"-06-30";
				}else if("3".endsWith(jd_m)){
					beginDate = jd_y+"-07-01";
					endDate = jd_y+"-09-30";
				}else if("4".equals(jd_m)){
					beginDate = jd_y+"-10-01";
					endDate = jd_y+"-12-31";
				}else {
					throw new Exception("请输入正确季度数据.");
				}
				QuerySet qs = dao.download(jd,beginDate,endDate);
				ExportManager.exportFile(response.getHttpResponse(), "服务站入库记录汇总表", header, qs);
			}
			catch (Exception e)
			{
				atx.setException(e);
				logger.error(e);
			}
		}
	    
	    public void outAmountSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			
			try
			{
				String conditions = RequestUtil.getConditionsWhere(request, page);
				Map<String, String> hm = RequestUtil.getValues(request);
				String jd = hm.get("JD");
				String jd_m = jd.substring(5);
				String jd_y = jd.substring(0,4);
				String beginDate = "";
				String endDate = "";
				if("1".endsWith(jd_m)){
					beginDate = jd_y+"-01-01";
					endDate = jd_y+"-03-31";
				}else if("2".endsWith(jd_m)){
					beginDate = jd_y+"-04-01";
					endDate = jd_y+"-06-30";
				}else if("3".endsWith(jd_m)){
					beginDate = jd_y+"-07-01";
					endDate = jd_y+"-09-30";
				}else if("4".equals(jd_m)){
					beginDate = jd_y+"-10-01";
					endDate = jd_y+"-12-31";
				}else {
					throw new Exception("请输入正确季度数据.");
				}
				BaseResultSet bs = dao.outAmountSearch(page,user,jd,beginDate,endDate,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    public void outDownload()throws Exception{
			//获取封装后的request对象
			RequestWrapper request = atx.getRequest();
			ResponseWrapper response= atx.getResponse();
			try
			{
				List<HeaderBean> header = new ArrayList<HeaderBean>();
				HeaderBean hBean = null;
				hBean = new HeaderBean();
				hBean.setName("CODE");
				hBean.setTitle("渠道商代码");
				header.add(0,hBean);
				hBean = new HeaderBean();
				hBean.setName("ONAME");
				hBean.setTitle("渠道商名称");
				header.add(1,hBean); 
				hBean = new HeaderBean();
				hBean.setName("JD");
				hBean.setTitle("季度");
				header.add(2,hBean);
				hBean = new HeaderBean();
				hBean.setName("BWWXJE");
				hBean.setTitle("保外维修出库金额(元)");
				header.add(3,hBean);
				hBean = new HeaderBean();
				hBean.setName("ALCGJE");
				hBean.setTitle("A类配件金额(元)");
				header.add(4,hBean);
				hBean = new HeaderBean();
				hBean.setName("YPCGJE");
				hBean.setTitle("油品金额(元)");
				header.add(5,hBean);
				hBean = new HeaderBean();
				hBean.setName("NFCGJE");
				hBean.setTitle("尿素与防冻液金额(元)");
				header.add(6,hBean);
				hBean = new HeaderBean();
				hBean.setName("BLCGJE");
				hBean.setTitle("B类配件金额(元)");
				header.add(7,hBean);
				hBean = new HeaderBean();
				hBean.setName("CLCGJE");
				hBean.setTitle("C类配件金额(元)");
				header.add(8,hBean);
				hBean = new HeaderBean();
				hBean.setName("DLCGJE");
				hBean.setTitle("D类配件金额(元)");
				header.add(9,hBean);
				hBean = new HeaderBean();
				hBean.setName("ZCGJE");
				hBean.setTitle("合计金额(元)");
				header.add(10,hBean);
				String jd =Pub.val(request, "JD");
				String jd_m = jd.substring(5);
				String jd_y = jd.substring(0,4);
				String beginDate = "";
				String endDate = "";
				if("1".endsWith(jd_m)){
					beginDate = jd_y+"-01-01";
					endDate = jd_y+"-03-31";
				}else if("2".endsWith(jd_m)){
					beginDate = jd_y+"-04-01";
					endDate = jd_y+"-06-30";
				}else if("3".endsWith(jd_m)){
					beginDate = jd_y+"-07-01";
					endDate = jd_y+"-09-30";
				}else if("4".equals(jd_m)){
					beginDate = jd_y+"-10-01";
					endDate = jd_y+"-12-31";
				}else {
					throw new Exception("请输入正确季度数据.");
				}
				QuerySet qs = dao.outDownload(jd,beginDate,endDate);
				ExportManager.exportFile(response.getHttpResponse(), "服务站出库记录汇总表", header, qs);
			}
			catch (Exception e)
			{
				atx.setException(e);
				logger.error(e);
			}
		}
}

package com.org.dms.action.service.reportForms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.reportForms.ServiceCostReportDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class ServiceCostReportAction {

	private Logger logger = com.org.framework.log.log
			.getLogger("ServiceCostReportAction");
	private ActionContext atx = ActionContext.getContext();
	private ServiceCostReportDao dao = ServiceCostReportDao.getInstance(atx);
/**
 * 查询数据
 * 
 * @throws Exception
 */
	public void search() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		//conditions=conditions.replaceAll("T.SETTLE_DATE", " TO_char(t.SETTLE_DATE, 'YYYY-MM')");
		try {
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String aYear=hm.get("SETTLE_DATE");//当前月
			String year=aYear.substring(0, 4);
			String month=aYear.substring(5);
			String bYear="";
			String cYear="";
			if(!"".equals(month)&&month.equals("01")){
				bYear=String.valueOf(Integer.valueOf(year)-1)+"-12";//上个月是去年的12月
			}else if(!"".equals(month)&&month.equals("10")){

				bYear=year+"-0"+String.valueOf(Integer.valueOf(month)-1);//上个月是9月
			}else{

				bYear=year+"-"+String.valueOf(Integer.valueOf(month)-1);//上个月
			}
			cYear=String.valueOf(Integer.valueOf(year)-1)+"-"+month;//去年的当前月
			BaseResultSet bs =null;
			if(!aYear.equals("")&&!bYear.equals("")&&!cYear.equals("")){
				bs = dao.search(page, user, conditions,aYear,bYear,cYear);
			}
	
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		}
	}

	/**
	 * 导出表数据
	 * 
	 * @throws Exception
	 */
	public void download() throws Exception {

		// 定义request对象
		ResponseWrapper response = atx.getResponse();
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		conditions=conditions.replaceAll("T.SETTLE_DATE", " TO_char(t.SETTLE_DATE, 'YYYY-MM')");
		try {
			
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String aYear=hm.get("SETTLE_DATE");//当前月
			String year=aYear.substring(0, 4);
			String month=aYear.substring(5);
			String bYear="";
			String cYear="";
			if(!"".equals(month)&&month.equals("01")){
				bYear=String.valueOf(Integer.valueOf(year)-1)+"-12";//上个月是去年的12月
			}else if(!"".equals(month)&&month.equals("10")){

				bYear=year+"-0"+String.valueOf(Integer.valueOf(month)-1);//上个月是9月
			}else{

				bYear=year+"-"+String.valueOf(Integer.valueOf(month)-1);//上个月
			}
			cYear=String.valueOf(Integer.valueOf(year)-1)+"-"+month;//去年的当前月
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("ROWNUM");
			hBean.setTitle("序号");
			header.add(0, hBean);

			hBean = new HeaderBean();
			hBean.setName("NAME");
			hBean.setTitle("分类");
			header.add(1, hBean);
			hBean = new HeaderBean();
			hBean.setName("A1");
			hBean.setTitle("当前结算月");
			header.add(2, hBean);
			hBean = new HeaderBean();
			hBean.setName("A2");
			hBean.setTitle("上个结算月");
			header.add(3, hBean);

			hBean = new HeaderBean();
			hBean.setName("A3");
			hBean.setTitle("去年当前结算月");
			header.add(4, hBean);
			hBean = new HeaderBean();
			hBean.setName("A4");
			hBean.setTitle("同比%");
			header.add(5, hBean);
			hBean = new HeaderBean();
			hBean.setName("A5");
			hBean.setTitle("环比%");
			header.add(6, hBean);
			QuerySet querySet=null;
			if(!aYear.equals("")&&!bYear.equals("")&&!cYear.equals("")){
				 querySet = dao.download(conditions,aYear,bYear,cYear);
			}
			
			ExportManager.exportFile(response.getHttpResponse(), "网络终审同比环比",
					header, querySet);
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		}
	}

}

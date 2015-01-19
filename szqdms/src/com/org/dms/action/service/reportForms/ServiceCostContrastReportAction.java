package com.org.dms.action.service.reportForms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.reportForms.ServiceCostContrastReportDao;
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

/**
 * 报表查询ACTION
 * @author Administrator
 *
 */
public class ServiceCostContrastReportAction {

    private Logger logger = com.org.framework.log.log.getLogger("ClaimSearchAction");
    private ActionContext atx = ActionContext.getContext();
    private ServiceCostContrastReportDao dao = ServiceCostContrastReportDao.getInstance(atx);
    private ResponseWrapper response = atx.getResponse();

    /**
     * 服务费用对比																							
     * @throws Exception
     */
    public void search() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		try
		{
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String year=hm.get("APPLY_DATE");
			String bYear=String.valueOf(Integer.valueOf(year)-1);
			BaseResultSet bs = dao.search(page,user,year,bYear);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 服务费用对比导出
     * @throws Exception
     */
    public void download()throws Exception{

        try {
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String year=hm.get("APPLY_DATE");
			String bYear=String.valueOf(Integer.valueOf(year)-1);
        	// 定义查询分页对象
            // 将request流中的信息转化为where条件
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("OFFICE_NAME");
            hBean.setTitle("办事处");
            header.add(0,hBean);

            hBean = new HeaderBean();
            hBean.setName("ORG_CODE");
            hBean.setTitle("服务编码");
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("ORG_NAME");
            hBean.setTitle("服务站");
            header.add(2,hBean);

            hBean = new HeaderBean();
            hBean.setName("MATERIAL_COSTS1");
            hBean.setTitle("1月材料费");
            header.add(3,hBean);

            hBean = new HeaderBean();
            hBean.setName("MATERIAL_COSTS2");
            hBean.setTitle("2月材料费");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MATERIAL_COSTS3");
            hBean.setTitle("3月材料费");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MATERIAL_COSTS4");
            hBean.setTitle("4月材料费");
            header.add(6,hBean);

            hBean = new HeaderBean();
            hBean.setName("MATERIAL_COSTS5");
            hBean.setTitle("5月材料费");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MATERIAL_COSTS6");
            hBean.setTitle("6月材料费");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MATERIAL_COSTS7");
            hBean.setTitle("7月材料费");
            header.add(9,hBean);

            hBean = new HeaderBean();
            hBean.setName("MATERIAL_COSTS8");
            hBean.setTitle("8月材料费");
            header.add(10,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MATERIAL_COSTS9");
            hBean.setTitle("9月材料费");
            header.add(11,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MATERIAL_COSTS10");
            hBean.setTitle("10月材料费");
            header.add(12,hBean);

            hBean = new HeaderBean();
            hBean.setName("MATERIAL_COSTS11");
            hBean.setTitle("11月材料费");
            header.add(13,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MATERIAL_COSTS12");
            hBean.setTitle("12月材料费");
            header.add(14,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS1");
            hBean.setTitle("1月工时费");
            header.add(15,hBean);

            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS2");
            hBean.setTitle("2月工时费");
            header.add(16,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS3");
            hBean.setTitle("3月工时费");
            header.add(17,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS4");
            hBean.setTitle("4月工时费");
            header.add(18,hBean);

            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS5");
            hBean.setTitle("5月工时费");
            header.add(19,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS6");
            hBean.setTitle("6月工时费");
            header.add(20,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS7");
            hBean.setTitle("7月工时费");
            header.add(21,hBean);

            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS8");
            hBean.setTitle("8月工时费");
            header.add(22,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS9");
            hBean.setTitle("9月工时费");
            header.add(23,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS10");
            hBean.setTitle("10月工时费");
            header.add(24,hBean);

            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS11");
            hBean.setTitle("11月工时费");
            header.add(25,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS12");
            hBean.setTitle("12月工时费");
            header.add(26,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OUT_COSTS1");
            hBean.setTitle("1月外出费");
            header.add(27,hBean);

            hBean = new HeaderBean();
            hBean.setName("OUT_COSTS2");
            hBean.setTitle("2月外出费");
            header.add(28,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OUT_COSTS3");
            hBean.setTitle("3月外出费");
            header.add(29,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OUT_COSTS4");
            hBean.setTitle("4月外出费");
            header.add(30,hBean);

            hBean = new HeaderBean();
            hBean.setName("OUT_COSTS5");
            hBean.setTitle("5月外出费");
            header.add(31,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OUT_COSTS6");
            hBean.setTitle("6月外出费");
            header.add(32,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OUT_COSTS7");
            hBean.setTitle("7月外出费");
            header.add(33,hBean);

            hBean = new HeaderBean();
            hBean.setName("OUT_COSTS8");
            hBean.setTitle("8月外出费");
            header.add(34,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OUT_COSTS9");
            hBean.setTitle("9月外出费");
            header.add(35,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OUT_COSTS10");
            hBean.setTitle("10月外出费");
            header.add(36,hBean);

            hBean = new HeaderBean();
            hBean.setName("OUT_COSTS11");
            hBean.setTitle("11月外出费");
            header.add(37,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OUT_COSTS12");
            hBean.setTitle("12月外出费");
            header.add(38,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUMMARY1");
            hBean.setTitle("1月总费用");
            header.add(39,hBean);
            hBean = new HeaderBean();
            hBean.setName("F1");
            hBean.setTitle("同比增长率%");
            header.add(40,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("E1");
            hBean.setTitle("环比增长率%");
            header.add(41,hBean);
            hBean = new HeaderBean();
            hBean.setName("SUMMARY2");
            hBean.setTitle("2月总费用");
            header.add(42,hBean);
            hBean = new HeaderBean();
            hBean.setName("F2");
            hBean.setTitle("同比增长率%");
            header.add(43,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("E2");
            hBean.setTitle("环比增长率%");
            header.add(44,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUMMARY3");
            hBean.setTitle("3月总费用");
            header.add(45,hBean);
            hBean = new HeaderBean();
            hBean.setName("F3");
            hBean.setTitle("同比增长率%");
            header.add(46,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("E3");
            hBean.setTitle("环比增长率%");
            header.add(47,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUMMARY4");
            hBean.setTitle("4月总费用");
            header.add(48,hBean);
            hBean = new HeaderBean();
            hBean.setName("F4");
            hBean.setTitle("同比增长率%");
            header.add(49,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("E4");
            hBean.setTitle("环比增长率%");
            header.add(50,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUMMARY5");
            hBean.setTitle("5月总费用");
            header.add(51,hBean);
            hBean = new HeaderBean();
            hBean.setName("F5");
            hBean.setTitle("同比增长率%");
            header.add(52,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("E5");
            hBean.setTitle("环比增长率%");
            header.add(53,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUMMARY6");
            hBean.setTitle("6月总费用");
            header.add(54,hBean);
            hBean = new HeaderBean();
            hBean.setName("F6");
            hBean.setTitle("同比增长率%");
            header.add(55,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("E6");
            hBean.setTitle("环比增长率%");
            header.add(56,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUMMARY7");
            hBean.setTitle("7月总费用");
            header.add(57,hBean);
            hBean = new HeaderBean();
            hBean.setName("F7");
            hBean.setTitle("同比增长率%");
            header.add(58,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("E7");
            hBean.setTitle("环比增长率%");
            header.add(59,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUMMARY8");
            hBean.setTitle("8月总费用");
            header.add(60,hBean);
            hBean = new HeaderBean();
            hBean.setName("F8");
            hBean.setTitle("同比增长率%");
            header.add(61,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("E8");
            hBean.setTitle("环比增长率%");
            header.add(62,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUMMARY9");
            hBean.setTitle("9月总费用");
            header.add(63,hBean);
            hBean = new HeaderBean();
            hBean.setName("F9");
            hBean.setTitle("同比增长率%");
            header.add(64,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("E9");
            hBean.setTitle("环比增长率%");
            header.add(65,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUMMARY10");
            hBean.setTitle("10月总费用");
            header.add(66,hBean);
            hBean = new HeaderBean();
            hBean.setName("F10");
            hBean.setTitle("同比增长率%");
            header.add(67,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("E10");
            hBean.setTitle("环比增长率%");
            header.add(68,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUMMARY11");
            hBean.setTitle("11月总费用");
            header.add(69,hBean);
            hBean = new HeaderBean();
            hBean.setName("F11");
            hBean.setTitle("同比增长率%");
            header.add(70,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("E11");
            hBean.setTitle("环比增长率%");
            header.add(71,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUMMARY12");
            hBean.setTitle("12月总费用");
            header.add(72,hBean);
            hBean = new HeaderBean();
            hBean.setName("F12");
            hBean.setTitle("同比增长率%");
            header.add(73,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("E12");
            hBean.setTitle("环比增长率%");
            header.add(74,hBean);
            QuerySet querySet = dao.download(year,bYear);
            ExportManager.exportFile(response.getHttpResponse(), "FWFYDB", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
}
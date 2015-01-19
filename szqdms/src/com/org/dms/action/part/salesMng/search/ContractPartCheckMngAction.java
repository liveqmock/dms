package com.org.dms.action.part.salesMng.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.salesMng.search.ContractPartCheckMngDao;
import com.org.dms.vo.part.PtBuPartContChkVO;
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

public class ContractPartCheckMngAction {
	
	private Logger logger = com.org.framework.log.log.getLogger(
	        "ContractPartCheckMngAction");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private ContractPartCheckMngDao dao = ContractPartCheckMngDao.getInstance(atx);
	    
	    public void partSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.partSearch(page,user, conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    public void searchImport()throws Exception{
	 		PageManager page = new PageManager();
	 		User user = (User) atx.getSession().get(Globals.USER_KEY);
	 		RequestWrapper request = atx.getRequest();
	 		try
	 		{
	 			PageManager pageManager = new PageManager();
	            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
	 			BaseResultSet bs = dao.searchImport(page,user,conditions);
	 			atx.setOutData("bs", bs);
	 		}
	 		catch (Exception e)
	 		{
	 			logger.error(e);
	 			atx.setException(e);
	 		}
	    }
	    public void purchaseOrderPartImport()throws Exception{
	    	RequestWrapper request = atx.getRequest();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	 		try{
	 			QuerySet getPart = dao.getPart(user);
	 			for(int i=0;i<getPart.getRowCount();i++){
	 				String PART_CODE = getPart.getString(i+1, "PART_CODE");
	 				String PART_NAME = getPart.getString(i+1, "PART_NAME");
	 				PtBuPartContChkVO vo = new PtBuPartContChkVO();
	 				vo.setPartCode(PART_CODE);
	 				vo.setPartName(PART_NAME);
	 				QuerySet getCon = dao.getCon(PART_CODE);
	 				String supplierName = getCon.getString(1, "SUPPLIER_NAMES");
	 				String unitPrice = getCon.getString(1, "UNIT_PRICES");
	 				if(!"".equals(supplierName)&&!"".equals(unitPrice)&&supplierName!=null&&unitPrice!=null){
	 					vo.setIfIn(DicConstant.SF_01);
	 					vo.setSupNames(supplierName);
	 					vo.setUnitPrices(unitPrice);
	 				}else{
	 					vo.setIfIn(DicConstant.SF_02);
	 				}
	 				vo.setCreateUser(user.getAccount());
	 				vo.setCreateTime(Pub.getCurrentDate());
	 				dao.insertVo(vo);
	 			}
	 			atx.setOutMsg("", "导入成功！");
	 		}
	 		catch (Exception e)
	 		{
	 			logger.error(e);
	 			atx.setException(e);
	 		}
	    }
	    //download
	    public void download()throws Exception{

	        try {
	        	//获取封装后的request对象
	        	RequestWrapper request = atx.getRequest();
	        	// 定义查询分页对象
	            PageManager pageManager = new PageManager();
	            // 将request流中的信息转化为where条件
	            
	            List<HeaderBean> header = new ArrayList<HeaderBean>();
	            HeaderBean hBean = null;
	            hBean = new HeaderBean();
	            hBean.setName("PART_CODE");
	            hBean.setTitle("配件代码");
	            header.add(0,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("PART_NAME");
	            hBean.setTitle("配件名称");
	            hBean.setWidth(50);
	            header.add(1,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("IF_IN");
	            hBean.setTitle("是否在合同内");
	            header.add(2,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("SUP_NAMES");
	            hBean.setTitle("供应商名称");
	            header.add(3,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("UNIT_PRICES");
	            hBean.setTitle("供应商价格");
	            header.add(4,hBean);
	            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
	            QuerySet querySet = dao.download(conditions);
	            ExportManager.exportFile(atx.getResponse().getHttpResponse(), "待维护配件合同校验", header, querySet);
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }

}

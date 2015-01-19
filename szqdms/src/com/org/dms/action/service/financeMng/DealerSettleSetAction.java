package com.org.dms.action.service.financeMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.financeMng.DealerSettleSetDao;
import com.org.dms.vo.service.MainDealerVO;
import com.org.dms.vo.service.SeBuManuallyVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class DealerSettleSetAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "DealerSettleSetAction");
	private ActionContext atx = ActionContext.getContext();
	private DealerSettleSetDao dao = DealerSettleSetDao.getInstance(atx);
	
	/**
     * 渠道商查询
     * @throws Exception
     */
    public void mainDealerSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.mainDealerSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 手工帐查询
     * @throws Exception
     */
    public void manuallySearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.manuallySearch(page,conditions,user);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 手工帐申请查询
     * @throws Exception
     */
    public void manuallyApplySearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.manuallyApplySearch(page,conditions,user);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 手工帐服务站端查询
     * @throws Exception
     */
    public void manuallyDealerSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.manuallyApplySearch(page,conditions,user);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 手工帐审核查询
     * @throws Exception
     */
    public void manuallyCheckSearch1() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.manuallyCheckSearch1(page,conditions,user);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 手工帐查询
     * @throws Exception
     */
    public void manuallyCheckSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.manuallyCheckSearch(page,conditions,user);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    
    /**
     * 设置
     * @throws Exception
     */
    public void dealerUpdate()throws Exception{
    	RequestWrapper request = atx.getRequest();
       User user = (User) atx.getSession().get(Globals.USER_KEY);
       try
       {
       		MainDealerVO vo = new MainDealerVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			
			vo.setUpdateTime(Pub.getCurrentDate());//修改时间
			vo.setUpdateUser(user.getAccount());//修改人
			dao.dealerUpdate(vo);
			vo.bindFieldToDic("INVOICE_TYPE","FPLX");
			vo.bindFieldToDic("SETTLE_TYPE","JSLX");
			vo.bindFieldToDic("IF_MAKE_AMOUNT","SF");
			vo.setFieldDateFormat("SETTLE_START_DATE", "yyyy-MM-dd");
			vo.setFieldDateFormat("SETTLE_END_DATE", "yyyy-MM-dd");
			atx.setOutMsg(vo.getRowXml(),"设置成功！");
       }
       catch (Exception e)
       {
       		atx.setException(e);
           logger.error(e);
       }
    }
    /**
     * 手工帐 新增保存
     */
    public void manuallyInsert()throws Exception{
    	RequestWrapper request = atx.getRequest();
       User user = (User) atx.getSession().get(Globals.USER_KEY);
       try
       {
    	    SeBuManuallyVO vo = new SeBuManuallyVO();
    	    int flag =0;
		    HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String orgCode = hm.get("ORG_CODE");
			String claimNo = hm.get("CLAIM_NO");
			QuerySet qs = dao.queryOrg(orgCode);
			if(qs.getRowCount()>0){
				String orgId= qs.getString(1, "ORG_ID");
				String oname= qs.getString(1, "ONAME");
				if(claimNo.length()>0){
					QuerySet qs1 =dao.queryClaim(claimNo,orgId);
					if(qs1.getRowCount()>0){
						flag=1;
					}else{
						flag=2;
						atx.setOutMsg("","索赔单号填写不正确，请填写正确的索赔单号。");
					}
				}
				if(flag!=2){
					vo.setValue(hm);
					vo.setOrgId(orgId);
					vo.setOrgName(oname);
					vo.setStatus(DicConstant.YXBS_01);
					vo.setCreateUser(user.getAccount());
					vo.setCreateTime(Pub.getCurrentDate());
					vo.setManuallyStatus(DicConstant.SGZZT_01);
					dao.insertManually(vo);
					atx.setOutMsg(vo.getRowXml(),"手工帐新增成功。");
				}
			}else{
				atx.setOutMsg("","入账单位填写不正确，请填写正确的组织代码。");
			}
			
			
       }
       catch (Exception e)
       {
       		atx.setException(e);
           logger.error(e);
       }
    }
    /**
     * 手工帐 修改保存
     */
    public void manuallyUpdate()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		SeBuManuallyVO vo = new SeBuManuallyVO();
    		int flag =0;
    		HashMap<String,String> hm;
    		hm = RequestUtil.getValues(request);
    		String manuallyId= hm.get("MANUALLY_ID");
    		String orgCode = hm.get("ORG_CODE");
    		String claimNo = hm.get("CLAIM_NO");
    		QuerySet qs = dao.queryOrg(orgCode);
    		if(qs.getRowCount()>0){
    			String orgId= qs.getString(1, "ORG_ID");
    			String oname= qs.getString(1, "ONAME");
    			if(claimNo.length()>0){
    				QuerySet qs1 =dao.queryClaim(claimNo,orgId);
    				if(qs1.getRowCount()>0){
    					flag=1;
    				}else{
    					flag=2;
    					atx.setOutMsg("","索赔单号填写不正确，请填写正确的索赔单号。");
    				}
    			}
    			if(flag!=2){
    				vo.setManuallyId(manuallyId);
    				vo.setValue(hm);
    				vo.setOrgId(orgId);
    				vo.setOrgName(oname);
    				vo.setStatus(DicConstant.YXBS_01);
    				vo.setCreateUser(user.getAccount());
    				vo.setCreateTime(Pub.getCurrentDate());
    				vo.setManuallyStatus(DicConstant.SGZZT_01);
    				dao.updateManually(vo);
    				atx.setOutMsg(vo.getRowXml(),"手工帐修改成功。");
    			}
    		}else{
    			atx.setOutMsg("","入账单位填写不正确，请填写正确的组织代码。");
    		}
    		
    		
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 手工帐删除
     */
    public void manuallyDelete()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		SeBuManuallyVO vo = new SeBuManuallyVO();
    		String manuallyId= Pub.val(request, "manuallyId");
    		vo.setManuallyId(manuallyId);
    		vo.setStatus(DicConstant.YXBS_02);
    		vo.setUpdateUser(user.getAccount());
    		vo.setUpdateTime(Pub.getCurrentDate());
    		dao.updateManually(vo);
    		atx.setOutMsg(vo.getRowXml(),"手工删除改成功。");
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 手工帐删除
     */
    public void returnOldReport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		SeBuManuallyVO vo = new SeBuManuallyVO();
    		HashMap<String,String> hm;
    		hm = RequestUtil.getValues(request);
    		int aflag=0;
    		String flag= Pub.val(request, "flag");
    		String manuallyId= Pub.val(request, "manuallyId");
    		String orgCode=null;
    		String claimNo=null;
    		if(flag.equals("2")){
    			orgCode = hm.get("ORG_CODE");
        	    claimNo = hm.get("CLAIM_NO");
        	    QuerySet qs = dao.queryOrg(orgCode);
        	    if(qs.getRowCount()>0){
        	    	String orgId= qs.getString(1, "ORG_ID");
        			String oname= qs.getString(1, "ONAME");
        			if(claimNo.length()>0){
        				QuerySet qs1 =dao.queryClaim(claimNo,orgId);
        				if(qs1.getRowCount()>0){
        					aflag=1;
        				}else{
        					aflag=2;
        					atx.setOutMsg("","索赔单号填写不正确，请填写正确的索赔单号。");
        				}
        			}
        			if(aflag!=2){
    					vo.setManuallyId(manuallyId);
						vo.setValue(hm);
						vo.setOrgId(orgId);
						vo.setOrgName(oname);
						vo.setStatus(DicConstant.YXBS_01);
						vo.setManuallyStatus(DicConstant.SGZZT_02);
						vo.setUpdateUser(user.getAccount());
		    			vo.setUpdateTime(Pub.getCurrentDate());
    				}
        	    }else {
    				atx.setOutMsg("","入账单位填写不正确，请填写正确的组织代码。");
    			}	
    		}else if (flag.equals("1")){
    			vo.setManuallyId(manuallyId);
    			vo.setUpdateUser(user.getAccount());
    			vo.setUpdateTime(Pub.getCurrentDate());
				vo.setManuallyStatus(DicConstant.SGZZT_02);
    		}
    		dao.updateManually(vo);
    		atx.setOutMsg(vo.getRowXml(),"手工帐提报成功。");
		}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 审核通过
     */
    public void checkPass()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		SeBuManuallyVO vo = new SeBuManuallyVO() ;
    		String manuallyId= Pub.val(request, "manuallyId");
    		QuerySet qs =dao.querySettleType(manuallyId);
    		HashMap<String,String> hm;
    		hm = RequestUtil.getValues(request);
    		String remarks= hm.get("CHECK_REMARKS");
    		int flag = 0;
    		if(qs.getRowCount()>0){
    			String sType = qs.getString(1, "SETTLE_TYPE");
    			if(sType.length()>0){
    				QuerySet qs1 =dao.queryManuallyType(manuallyId,sType);
    				if(qs1.getRowCount()>0){
    						flag=1;
    					}else {
    						flag=2;
    					}
    				}
    			}else {
    				flag = 1;
    			}
    		if(flag == 1){
    			vo.setManuallyId(manuallyId);
        		vo.setManuallyStatus(DicConstant.SGZZT_03);
        		vo.setCheckRemarks(remarks);
        		vo.setPassDate(Pub.getCurrentDate());
        		vo.setUpdateUser(user.getAccount());
        		vo.setUpdateTime(Pub.getCurrentDate());
        		dao.updateManually(vo);
        		atx.setOutMsg(vo.getRowXml(),"手工帐审核通过。");
    		}else{
    			atx.setOutMsg("1","该服务站已做结算单类型设置，手工帐类型与设置不匹配，请驳回修改后重新提报。");
    		}
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 审核驳回
     */
    public void checkReject()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		HashMap<String,String> hm;
    		hm = RequestUtil.getValues(request);
    		String remarks= hm.get("CHECK_REMARKS");
    		SeBuManuallyVO vo = new SeBuManuallyVO();
    		String manuallyId= Pub.val(request, "manuallyId");
    		vo.setManuallyId(manuallyId);
    		vo.setCheckRemarks(remarks);
    		vo.setManuallyStatus(DicConstant.SGZZT_04);
    		vo.setUpdateUser(user.getAccount());
    		vo.setUpdateTime(Pub.getCurrentDate());
    		dao.updateManually(vo);
    		atx.setOutMsg(vo.getRowXml(),"手工帐审核驳回。");
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
}

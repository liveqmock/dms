package com.org.dms.action.service.oldpartMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.oldpartMng.OldPartNotReturnApplyDao;
import com.org.dms.vo.service.SeBuReturnorderNotDtlVO;
import com.org.dms.vo.service.SeBuReturnorderNotVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
/**
 * 旧件不回运申请ACTION
 * @author zts
 *
 */
public class OldPartNotReturnApplyAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "OldPartNotReturnApplyAction");
    private ActionContext atx = ActionContext.getContext();
    private OldPartNotReturnApplyDao dao = OldPartNotReturnApplyDao.getInstance(atx);
    
    /**
     * 查询
     * @throws Exception
     */
    public void oldPartNotReturnSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		HashMap<String, String> hm;
        //将request流转换为hashmap结构体
        hm = RequestUtil.getValues(request);
        String checkDate=hm.get("CHECK_DATE");
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.oldPartNotReturnSearch(page,conditions,user,checkDate);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }

    /**
     * 申请
     * @throws Exception
     */
    public void oldPartNotReturnApply()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	HashMap<String, String> hm;
        //将request流转换为hashmap结构体
        hm = RequestUtil.getValues(request);
        String checkDate=hm.get("CHECK_DATE");
        String orgId = user.getOrgId();
        String companyId= user.getCompanyId();
        
        try
        {
        	QuerySet qs0 =dao.queryClaimId(checkDate,user);
        	if(qs0.getRowCount()>0){
        		
        		for(int i=0;i<qs0.getRowCount();i++){
        			String notId=qs0.getString(i+1, "NOTBACK_ID");
        			SeBuReturnorderNotVO vo = new SeBuReturnorderNotVO();//不回运旧件主表Vo
        			String claimId = qs0.getString(i+1, "CLAIM_ID");
        			String claimNo = qs0.getString(i+1, "CLAIM_NO");
        			String claimType = qs0.getString(i+1, "CLAIM_TYPE");
        			if(null==notId||notId.equals("")){
            			String returnNo=dao.getReturnNo();
            			vo.setClaimId(claimId);
            			vo.setClaimNo(claimNo);
            			vo.setClaimType(claimType);
            			vo.setCreateTime(Pub.getCurrentDate());
            			vo.setCreateUser(user.getAccount());
            			vo.setReturnNo(returnNo);
            			vo.setApplyDate(Pub.getCurrentDate());
            			vo.setApplyUser(user.getAccount());
            			vo.setApplyStatus(DicConstant.BHYSQZT_01);//不回运状态申请
            			vo.setApplyMonth(checkDate);
            			vo.setStatus(DicConstant.YXBS_01);
            			vo.setOrgId(orgId);
            			vo.setCompanyId(companyId);
            			dao.insertOldPartNotReturn(vo);
            			String notbackId=vo.getNotbackId();
            			QuerySet qs1 =dao.searchOldPart1(claimId);
            			for(int j=0; j<qs1.getRowCount();j++){
            				SeBuReturnorderNotDtlVO vo1 =new SeBuReturnorderNotDtlVO();//不回运旧件明细表Vo
            				String oldPartId = qs1.getString(j+1, "OLD_PART_ID");
                			String oldPartCode = qs1.getString(j+1, "OLD_PART_CODE");
                			String oldPartName = qs1.getString(j+1, "OLD_PART_NAME");
                			String oldSupplierId = qs1.getString(j+1, "OLD_SUPPLIER_ID");
                			String faultCode = qs1.getString(j+1, "FAULT_CODE");
                			String sl = qs1.getString(j+1, "SL");
                			 vo1.setPartId(oldPartId);
                             vo1.setPartCode(oldPartCode);
                             vo1.setPartName(oldPartName);
                             vo1.setFaultCode(faultCode);
                             vo1.setDtlCount(sl);
                             vo1.setSupplierId(oldSupplierId);
                             vo1.setNotbackId(notbackId);
                             vo1.setCreateTime(Pub.getCurrentDate());
                             vo1.setCreateUser(user.getAccount());
                            dao.insertParts(vo1);
            			}
        			}else{
        			SeBuReturnorderNotVO vo1 = new SeBuReturnorderNotVO();//不回运旧件主表Vo
	        			vo1.setNotbackId(notId);
	        			vo1.setClaimId(claimId);
	        			vo1.setClaimNo(claimNo);
	        			vo1.setClaimType(claimType);
	        			vo1.setApplyDate(Pub.getCurrentDate());
	        			vo1.setApplyStatus(DicConstant.BHYSQZT_01);//不回运状态申请
	        			vo1.setApplyUser(user.getAccount());
	        			vo1.setOrgId(user.getOrgId());
	        			vo1.setCompanyId(user.getCompanyId());
	        			vo1.setUpdateTime(Pub.getCurrentDate());
	        			vo1.setUpdateUser(user.getAccount());
            			dao.updateNotReturn(vo1);
                        atx.setOutMsg("1","申请成功！");
        			}
        		}
        	}else{
        		atx.setOutMsg("2","缺少可申请旧件或本月已经申请不回运。");
        	}
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 查询
     * @throws Exception
     */
    public void searchParts() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String nId = Pub.val(request, "nId");//旧件不回运表ID
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.searchParts(page,conditions,user,nId);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 查询
     * @throws Exception
     */
    public void searchOldPart() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String nId = Pub.val(request, "nId");//旧件不回运表ID
    	String cId = Pub.val(request, "cId");//索赔单ID
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.searchOldPart(page,conditions,user,nId,cId);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 新增旧件
     *
     * @throws Exception
     */
    public void insertParts() throws Exception {
        //获取封装后的request对象                      
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String faultPartId=hm.get("FAULT_PART_ID");
			String oldPartCode=hm.get("OLD_PART_CODE");
			String oldPartName=hm.get("OLD_PART_NAME");
            String faultCode = hm.get("FAULT_CODE");
            String oldPartCount = hm.get("OLD_PART_COUNT");
            String oldPartId = hm.get("OLD_PART_ID");
            String oldSupplierId = hm.get("OLD_SUPPLIER_ID");
            String notbackId = hm.get("NOTBACK_ID");
            String[] faultPartIdArr = faultPartId.split(",");
            String[] oldPartCodeArr = oldPartCode.split(",");
            String[] oldPartNameArr = oldPartName.split(",");
            String[] faultCodeArr= faultCode.split(",");
            String[] oldPartIdArr= oldPartId.split(",");
            String[] oldPartCountArr= oldPartCount.split(",");
            String[] oldSupplierIdArr= oldSupplierId.split(",");
            for (int i = 0; i < faultPartIdArr.length; i++) {
               SeBuReturnorderNotDtlVO vo =new SeBuReturnorderNotDtlVO();
            	 vo.setPartId(oldPartIdArr[i]);
                 vo.setPartCode(oldPartCodeArr[i]);
                 vo.setPartName(oldPartNameArr[i]);
                 vo.setFaultCode(faultCodeArr[i]);
                 vo.setDtlCount(oldPartCountArr[i]);
                 vo.setSupplierId(oldSupplierIdArr[i]);
                 vo.setNotbackId(notbackId);
                 vo.setCreateTime(Pub.getCurrentDate());
                 vo.setCreateUser(user.getAccount());
                dao.insertParts(vo);
            }
            //返回插入结果和成功信息
            atx.setOutMsg("", "新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
	}
    /**
     * 删除不回运旧件
     * @throws Exception
     */
    public void deleteParts() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
       
        try
        {
        	String mxids=Pub.val(request, "mxid");
			 dao.deleteParts(mxids);
			 atx.setOutMsg("","不回运旧件删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
}

package com.org.dms.action.service.oldpartMng;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.oldpartMng.ClaimUpdateDao;
import com.org.dms.vo.service.SeBuClaimFaultPartVO;
import com.org.dms.vo.service.SeBuReturnorderDetailVO;
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
 * 索赔单修改ACTION
 * @author zts
 *
 */
public class ClaimUpdateAction {
	private Logger logger = com.org.framework.log.log.getLogger(
		        "ClaimUpdateAction");
    private ActionContext atx = ActionContext.getContext();
    private ClaimUpdateDao dao = ClaimUpdateDao.getInstance(atx);
	
    /**
     * 索赔单修改查询
     * @throws Exception
     */
   /* public void claimUpdateSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.claimUpdateSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }	
    */
    /**
     * 旧件查询
     * @throws Exception
     */
   /* public void oldPartSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		String claimId=Pub.val(request, "claimId");
 		RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.oldPartSearch(page,claimId);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }*/
    
    /**
     * 旧件选择查询
     * @throws Exception
     */
    public void claimUpdateOldPartSearch()throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.claimUpdateOldPartSearch(page,conditions);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 索赔单旧件修改保存
     * @throws Exception
     */
    /*public void claimOldPartUpdate()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	String isMain=Pub.val(request, "isMain");//是否主损
    	String claimDtlId=Pub.val(request, "claimDtlId");//故障ID
 		String claimId=Pub.val(request, "claimId");//索赔单ID
 		String detailId=Pub.val(request, "detailId");//旧件回运明细ID
 		try
 		{
 			HashMap<String, String> hm;
 			hm = RequestUtil.getValues(request);
 			//修改旧件回运明细表
 		    String partId=hm.get("PART_ID");
 		    String supplierId=hm.get("SUPPLIER_ID");
 		    String partCount=hm.get("PART_COUNT");
 		    String newPartId=hm.get("NEW_PART_ID");
 		    String newSupplierId=hm.get("NEW_SUPPLIER_ID");
 		    String newSalePrice;
 		    newSalePrice=hm.get("NEW_SALE_PRICE");
 		    //供应商相同，查询索赔单配件表里的索赔价格
 		    if(supplierId.equals(newSupplierId)){
 		    	QuerySet qs=dao.getPartPrice(claimId,claimDtlId,partId);
 		    	if(qs.getRowCount()>0){
 		    		newSalePrice = qs.getString(1, "CLAIM_COSTS"); 
 		    	}
 		    }
 		    //获取新配件信息（配件代码，配件名称）
 		    QuerySet qsPart=dao.getPartInfo(newPartId);
 		    //获取新供应商信息（供应商代码，供应商名称）
 		    QuerySet qsSupp=dao.getSuppInfo(newSupplierId);
 		    //通过索赔单ID、故障ID、旧件ID 获取索赔单故障零件主键
 		    QuerySet qsFp=dao.getFaultPartId(claimId,claimDtlId,partId);
 		    String partCode = null;
 		    String partName = null;
 		    if(qsPart.getRowCount()>0){
 		    	partCode=qsPart.getString(1,"PART_CODE");
 		    	partName=qsPart.getString(1,"PART_NAME");
 		    }
 		    String supplierCode =null;
		    String supplierName =null;
		    if(qsSupp.getRowCount()>0){
		    	supplierCode=qsSupp.getString(1,"SUPPLIER_CODE");
		    	supplierName=qsSupp.getString(1,"SUPPLIER_NAME");
 		    }
		    String faultPartId=null;
		    if(qsFp.getRowCount() > 0 ){
		    	faultPartId=qsFp.getString(1, "FAULT_PART_ID");
 		    }
		    //计算出配件总金额
 		    int partPrice=Integer.parseInt(newSalePrice);
 		    int partAmount=Integer.parseInt(partCount) * partPrice;//计算出配件金额
 		    
 		    Map<String,String> params=new HashMap<String,String>();
			params.put("faultPartId",faultPartId);//故障下的配件ID
			params.put("claimDtlId",claimDtlId);//故障ID
			params.put("newPartId",newPartId);//新配件ID
			params.put("partCode",partCode);//新配件代码
			params.put("partName",partName);//新配件名称
			params.put("newSupplierId",newSupplierId);//新供应商ID
			params.put("supplierCode", supplierCode);//新供应商代码
			params.put("supplierName",supplierName);//新供应商名称
			SeBuReturnorderDetailVO detailVO =new SeBuReturnorderDetailVO();
			SeBuClaimFaultPartVO fPartVO =new SeBuClaimFaultPartVO();
			//供应商修改不相同时 修改旧件信息
	    	if(!supplierId.equals(newSupplierId)){
	    		//旧件回运明细表
	    		detailVO.setPartId(newPartId);
		    	detailVO.setPartCode(partCode);
		    	detailVO.setPartName(partName);
		    	detailVO.setProsupplyId(newSupplierId);
		    	detailVO.setProsupplyCode(supplierCode);
		    	detailVO.setProsupplyName(supplierName);
		    	//故障配件表
		    	fPartVO.setOldPartId(newPartId);
		    	fPartVO.setOldPartCode(partCode);
		    	fPartVO.setOldPartName(partName);
		    	fPartVO.setOldSupplierId(newSupplierId);
		    	fPartVO.setClaimUprice(newSalePrice);//索赔单价
	    	}
	    	//修改旧件回运明细信息
	    	detailVO.setDetailId(detailId);
	    	detailVO.setOughtCount(partCount);
	    	detailVO.setPartAmount(String.valueOf(partAmount));
	    	dao.returnDetailUpdate(detailVO);
	    	//修改故障配件信息
	    	fPartVO.setFaultPartId(faultPartId);
	    	fPartVO.setOldPartCount(partCount);
	    	fPartVO.setClaimCosts(String.valueOf(partAmount));
	    	dao.claimFaultPartUpdate(fPartVO);
	    	
	    	//如果是主损件 更新该故障下的旧件回运明细表和故障配件表所有主损件信息和责任供应商 
	    	if(isMain.equals(DicConstant.GZLB_01)){
	    		dao.claimFaultPartAllUpdate(params);
	    		dao.returnDetailAllUpdate(params);
	    	}
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }*/
    
}

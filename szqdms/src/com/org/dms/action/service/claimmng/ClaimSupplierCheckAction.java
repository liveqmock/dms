package com.org.dms.action.service.claimmng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.claimmng.ClaimSupplierCheckDao;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.service.SeBuClaimCheckVO;
import com.org.dms.vo.service.SeBuClaimVO;
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
 * 供应商审核ACTION
 * @author zts
 *
 */
public class ClaimSupplierCheckAction {
	private Logger logger = com.org.framework.log.log.getLogger("ClaimSupplierCheckAction");
	private ActionContext atx = ActionContext.getContext();
	private ClaimSupplierCheckDao dao=ClaimSupplierCheckDao.getInstance(atx);
	
	/**
	 * 索赔单查询
	 * @throws Exception
	 */
	public void claimSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.claimSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
	}
	
	/**
	 * 查询故障信息
	 * @throws Exception
	 */
	public void searchClaimPattern()throws Exception{
		RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 	    RequestUtil.getConditionsWhere(request,page);
 	    String claimId=Pub.val(request, "claimId");
 		try
 		{
 			BaseResultSet bs = dao.searchClaimPattern(page,claimId,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
	}
	
	/**
	 * 配件查询
	 * @throws Exception
	 */
	public void searchPart()throws Exception{
		RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 	    RequestUtil.getConditionsWhere(request,page);
 	   // String claimId=Pub.val(request, "claimId");
 	    String claimDtlId=Pub.val(request, "claimDtlId");
 		try
 		{
 			BaseResultSet bs = dao.searchPart(page,claimDtlId,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
	}
	
	
	
	/**
	 * 供应商审核意见
	 * @throws Exception
	 */
	public void claimOptionUpdate()throws Exception{
		//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String claimId=hm.get("CLAIM_ID");
			String checkRemarks=hm.get("CHECK_REMARKS");
            //插入审核轨迹
            SeBuClaimCheckVO checkVo=new SeBuClaimCheckVO();
            checkVo.setClaimId(claimId);
            checkVo.setCheckUser(user.getAccount());
            checkVo.setCheckDate(Pub.getCurrentDate());
            checkVo.setCheckResult(DicConstant.SPDZT_03);
            checkVo.setCheckRemarks(checkRemarks);
            checkVo.setCreateTime(Pub.getCurrentDate());
            checkVo.setCreateUser(user.getAccount());
            checkVo.setOemCompanyId(user.getOemCompanyId());
            dao.insertCheck(checkVo);
            //更新索赔单故障配件表  供应商意见审核状态（意见）
            dao.updateFaultPart(user,claimId,DicConstant.GYSYJZT_02);
            atx.setOutMsg("1","审核完成！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
	}
	
	/**
	 * 供应商审核同意
	 * @throws Exception
	 */
	public void claimUpdate()throws Exception{
		//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String claimId=hm.get("CLAIM_ID");
			String checkRemarks=hm.get("CHECK_REMARKS");
            //更新索赔单故障配件表  供应商意见审核状态（同意）
			QuerySet qs1=dao.getSupplierCheck(user);
			String ifcheck="0";
			if(qs1.getRowCount() > 0 ){
				ifcheck =qs1.getString(1,"IF_CLAIM_CHECK");
			}
			String status=DicConstant.SPDZT_03;
			if(DicConstant.SF_01.toString().equals(ifcheck)){
				dao.updateFaultPart(user,claimId,DicConstant.GYSYJZT_01);
	            QuerySet qs=dao.getSl(claimId);
	            if(qs.getRowCount() > 0){
	            	String sl=qs.getString(1,"SL");//如果是0 表示所有主损件供应商都同意
	            	if("0".equals(sl)){
	            		SeBuClaimVO claimVO=new SeBuClaimVO();
	            		claimVO.setClaimId(claimId);
	            		claimVO.setClaimStatus(DicConstant.SPDZT_05);
	            		dao.updateClaim(claimVO);
	            		realSaleStock(claimId);
	            	    status=DicConstant.SPDZT_05;
	            	}else{
	            		status=DicConstant.SPDZT_03;
	            	}
	            }
			}
            //插入审核轨迹
            SeBuClaimCheckVO checkVo=new SeBuClaimCheckVO();
            checkVo.setClaimId(claimId);
            checkVo.setCheckUser(user.getAccount());
            checkVo.setCheckDate(Pub.getCurrentDate());
            checkVo.setCheckResult(status);
            checkVo.setCheckRemarks(checkRemarks);
            checkVo.setCreateTime(Pub.getCurrentDate());
            checkVo.setCreateUser(user.getAccount());
            checkVo.setOemCompanyId(user.getOemCompanyId());
            dao.insertCheck(checkVo);
            atx.setOutMsg("1","审核完成！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
	}
	/**
	 * //实销单出库，扣掉占用库存。总库存减少。
	 * 
	 * @param claimId
	 * @throws Exception
	 */
	public void realSaleStock(String claimId) throws Exception {
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		// 库存释放
		QuerySet qsp = dao.queryParts(claimId);// 查询索赔单下所有配件ID，供应商ID，配件数量。
		if (qsp.getRowCount() > 0) {
			String orgId = qsp.getString(1, "ORG_ID");// 服务站ID
			for (int i = 0; i < qsp.getRowCount(); i++) {// 遍历所有新件
				String amount = null;
				String oAmount = null;
				String stockId = null;

				String nPartId = qsp.getString(i + 1, "PART_ID");// 新件ID
				String nSupplierId = qsp.getString(i + 1, "SUPPLIER_ID");// 新件供应商ID
				String nPartCount = qsp.getString(i + 1, "SALE_COUNT");// 新件数量

				if (null == nPartId || nPartId.equals("")) {
				} else {
					QuerySet qsStock = dao.queryStock(nPartId, nSupplierId,
							orgId);
					if (qsStock.getRowCount() > 0) {
						amount = qsStock.getString(1, "AMOUNT");// 查询所有配件的可用库存
						oAmount = qsStock.getString(1, "OCCUPY_AMOUNT");// 查询所有配件的占用库存
						stockId = qsStock.getString(1, "STOCK_ID");// 查询所有配件的库存ID
					} else {
						amount = "0";
						oAmount = "0";
					}
					if (null == stockId || stockId.equals("")) {
					} else {
						PtBuDealerStockVO pbsVo = new PtBuDealerStockVO();// 库存表VO
						int saleCount = Integer.parseInt(nPartCount);
						// 对库存更新
						Double aamount = Double.parseDouble(amount.toString())- saleCount;// 通过时，将可用库存加上新件数量 ，占用减去新件数量。
						Double occupyamount = Double.parseDouble(oAmount.toString()) - saleCount;
						if(occupyamount<0||aamount<0){
                            throw new Exception("库存不足，无法操作。");
                        }else{	
						pbsVo.setAmount(aamount.toString());
						pbsVo.setOccupyAmount(occupyamount.toString());
						pbsVo.setUpdateTime(Pub.getCurrentDate());
						pbsVo.setUpdateUser(user.getAccount());
						pbsVo.setStockId(stockId);
						dao.DealerStockUpdate(pbsVo);
					}
					dao.updateRsSaleStatus(claimId);// 将实销单的实销状态改为已出库
					}
				}
			}
		}
	}
}

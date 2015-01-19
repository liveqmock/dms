package com.org.dms.action.service.claimmng;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.claimmng.ClaimOutBuyDao;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.part.PtBuOutBuyVO;
import com.org.dms.vo.service.SeBuClaimCheckVO;
import com.org.dms.vo.service.SeBuClaimVO;
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

/**
 * 外采单审核ACTION
 * @author Administrator
 *
 */
public class ClaimOutBuyMngAction {

    private Logger logger = com.org.framework.log.log.getLogger("ClaimSearchAction");
    private ActionContext atx = ActionContext.getContext();
    private ClaimOutBuyDao dao = ClaimOutBuyDao.getInstance(atx);

    /**
     * 外采单查询
     * @throws Exception
     */
    public void claimOutSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			String claimNo=Pub.val(request, "claimNo");
			BaseResultSet bs = dao.claimOutSearch(page,user,conditions,claimNo);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 外采单查询(办事处查询)
     * @throws Exception
     */
    public void claimOutSearchInfo() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			String claimNo=Pub.val(request, "claimNo");
			BaseResultSet bs = dao.claimOutSearchInfo(page,user,conditions,claimNo);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 外采单查询(配送中心端查询)
     * @throws Exception
     */
    public void claimOutSearchInfoPart() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		String claimNo=Pub.val(request, "claimNo");
    		String orgType = user.getOrgDept().getOrgType();
    		if(orgType.equals(DicConstant.ZZLB_09)){
    			BaseResultSet bs = dao.claimOutSearchInfoPart(page,user,conditions,claimNo);
    			atx.setOutData("bs", bs);
    		}else{
    			BaseResultSet bs = dao.claimOemOutSearchInfo(page,user,conditions,claimNo);
        		atx.setOutData("bs", bs);
    		}
    		
    		
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 外采单查询(服务商端查询)
     * @throws Exception
     */
    public void claimDealerOutSearchInfo() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		String claimNo=Pub.val(request, "claimNo");
    		BaseResultSet bs = dao.claimDealerOutSearchInfo(page,user,conditions,claimNo);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 外采单查询(厂端查询)
     * @throws Exception
     */
    public void claimOemOutSearchInfo() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		String claimNo=Pub.val(request, "claimNo");
    		BaseResultSet bs = dao.claimOemOutSearchInfo(page,user,conditions,claimNo);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 外采件明细
     * @throws Exception
     */
    public void searchOutParts() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		String buyId=Pub.val(request, "buyId");
    		BaseResultSet bs = dao.searchOutParts(page,user,conditions,buyId);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 外采单审核通过
     * @throws Exception
     */
    public void OutPartReport() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		String buyId=Pub.val(request, "buyId");
    		PtBuOutBuyVO vo=new PtBuOutBuyVO();
    		vo.setBuyId(buyId);
    		vo.setBuyStatus(DicConstant.SF_01);
    		vo.setUpdateTime(Pub.getCurrentDate());
    		vo.setUpdateUser(user.getAccount());
    		dao.updateBuyStatus(vo);
    		QuerySet qs =dao.queryClaimId(buyId);
    		String claimId=qs.getString(1, "CLAIM_ID");
    		QuerySet qs3 =dao.queryOpUser(claimId);
    		String opUser=null;
    		if (qs3.getRowCount()>0){
    			opUser=qs3.getString(1, "OPERATE_USER");
    		}
    		SeBuClaimCheckVO checkVo = new SeBuClaimCheckVO();
			checkVo.setClaimId(claimId);
			checkVo.setCheckUser(user.getAccount());
			checkVo.setCheckDate(Pub.getCurrentDate());
			checkVo.setCheckRemarks("办事处审核通过。");
			checkVo.setCreateTime(Pub.getCurrentDate());
			checkVo.setCreateUser(user.getAccount());
			checkVo.setOemCompanyId(user.getOemCompanyId());
			String status=DicConstant.SPDZT_03;
    		SeBuClaimVO vo1=new SeBuClaimVO();
    		vo1.setClaimId(claimId);
    		vo1.setOutBuypassDate(Pub.getCurrentDate());
    		vo1.setUpdateTime(Pub.getCurrentDate());
    		vo1.setUpdateUser(user.getAccount());
    		vo1.setStockMeet(DicConstant.SF_01);
    		QuerySet qs1 = dao.queryClaimType(claimId);
    		QuerySet qs2 = dao.queryClaimType1(claimId);
    		if(qs1.getRowCount()>0){//查询结果集大于0，说明索赔单中有旧件需要回运。
    			checkVo.setCheckRemarks("办事处审核通过，"+opUser+"正在审核中。");
    		}else{
    			String claimType = qs2.getString(1, "CLAIM_TYPE");
    			if(claimType.equals(DicConstant.SPDLX_02)||claimType.equals(DicConstant.SPDLX_04)||claimType.equals(DicConstant.SPDLX_05)||claimType.equals(DicConstant.SPDLX_06)||claimType.equals(DicConstant.SPDLX_07)){
    				vo1.setClaimStatus(DicConstant.SPDZT_15);//如果所有零件更换的旧件都不需要回运，则直接将索赔单更新为终审通过
    				vo1.setOldpartFinalDate(Pub.getCurrentDate());//更新终审通过时间。
    				vo1.setCheckpassDate(Pub.getCurrentDate());//更新初审通过时间
    				status=DicConstant.SPDZT_15;
    				dao.updateOught1(claimId);//更新维修和加装的审核数量
					dao.updateOught(claimId);//更新不需要回运旧件的审核通过数量
					realSaleStock(claimId);
    			}
    		}
    		checkVo.setCheckResult(status);
    		dao.updateClaim(vo1);
			dao.insertCheck(checkVo);
    		atx.setOutMsg("1", "审核通过成功");
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
	 * 实销单出库，扣掉占用库存。总库存减少。
	 * 
	 * @param claimId
	 * @throws Exception
	 */
	public void realSaleStock(String claimId) throws Exception {
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		// 库存释放
		QuerySet qsp = dao.queryParts(claimId);// 查询索赔单对应的实销单下所有配件ID，供应商ID，配件数量。
		if (qsp.getRowCount() > 0) {
			String orgId = qsp.getString(1, "ORG_ID");// 服务站ID
			for (int i = 0; i < qsp.getRowCount(); i++) {// 遍历所有新件
				String amount = null;
				String oAmount = null;
				String stockId = null;

				String nPartId = qsp.getString(i + 1, "PART_ID");// 新件ID
				String nSupplierId = String.valueOf("2014090300052510");// 新件供应商ID
				String nPartCount = qsp.getString(i + 1, "SALE_COUNT");// 新件数量

				if (null == nPartId || nPartId.equals("")) {
				} else {
					QuerySet qsStock = dao.queryStock(nPartId, nSupplierId,orgId);
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
						// 通过时，将可用库存加上新件数量 ，占用减去新件数量。
						Double aamount = Double.parseDouble(amount.toString())- saleCount;
						Double occupyamount = Double.parseDouble(oAmount.toString()) - saleCount;
						if(occupyamount<0||aamount<0){
                            throw new Exception("库存不足，无法操作。请将本单驳回重新提报");
                        }else{
						pbsVo.setAmount(aamount.toString());
						pbsVo.setOccupyAmount(occupyamount.toString());
						pbsVo.setUpdateTime(Pub.getCurrentDate());
						pbsVo.setUpdateUser(user.getAccount());
						pbsVo.setStockId(stockId);
						dao.DealerStockUpdate(pbsVo);
						String url="办事处通过:/action/service/claimmng/ClaimOutBuyMngAction/realSaleStock.ajax";
	                    dao.insetStockDtl(saleCount,user,orgId,claimId,url,nPartId);//插入库存变化明细。
					}
				}
				dao.updateRsSaleStatus(claimId);// 将实销单的实销状态改为已出库
				}
			}
		}
	}
    /**
     * 外采单审核驳回
     * @throws Exception
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public void OutPartReject() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			Map map = new HashMap();
			String claimType="";
			String vehicleId="";
			String checkRemarks=hm.get("CHECK_REMARKS");
    		String buyId=Pub.val(request, "buyId");
    		PtBuOutBuyVO vo=new PtBuOutBuyVO();
    		vo.setBuyId(buyId);
    		vo.setStatus(DicConstant.YXBS_02);
    		vo.setUpdateTime(Pub.getCurrentDate());
    		vo.setUpdateUser(user.getAccount());
    		dao.updateBuyStatus(vo);
    		QuerySet qs =dao.queryClaimId(buyId);
    		String claimId=qs.getString(1, "CLAIM_ID");
    		rejectClaim(claimId);
    		// 插入审核轨迹
    		SeBuClaimVO claimVo=new SeBuClaimVO();
    		claimVo.setClaimId(claimId);
    		claimVo.setStockMeet("");
    		claimVo.setOutBuyrejectDate(Pub.getCurrentDate());
    		claimVo.setRejectDate(Pub.getCurrentDate());
    		claimVo.setClaimStatus(DicConstant.SPDZT_06);
    		claimVo.setUpdateUser(user.getAccount());
    		claimVo.setUpdateTime(Pub.getCurrentDate());
    		dao.updateClaim(claimVo);
    		
    		QuerySet qs1 = dao.getClaimInfo(claimId);
			if(qs1.getRowCount() > 0 ){
				claimType=qs1.getString(1,"CLAIM_TYPE");
				vehicleId=qs1.getString(1,"VEHICLE_ID");
			}
    		Integer type = new Integer(claimType);
            switch (type.intValue()) {
			case 301402:// 首保
				map.put("sbxx","1");
				break;
			case 301403:// 服务活动
				//dao.updateActivity(activityId, user,vehicleId);// 更新服务活动未使用
				break;
			case 301405:// 定保次数
				map.put("dbcs", "1");// 更新车辆定保0次
				break;
			case 301406:// 售前培训检查
				map.put("sqjccs", "1");//更新售前培训检查0次
				break;
			case 301407:// 安全检查
				map.put("aqjccs", "1");// 更新安全检查次数0次
				break;
			case 301408:// 照顾性保修
				//dao.updatePreAuthor(preAuthorId, user);// 照顾性保修更新预售权未使用
				break;
			}
            dao.updateVehicle(user, vehicleId, map);
    		
			SeBuClaimCheckVO checkVo = new SeBuClaimCheckVO();
			checkVo.setClaimId(claimId);
			checkVo.setCheckUser(user.getAccount());
			checkVo.setCheckDate(Pub.getCurrentDate());
			checkVo.setCheckResult(DicConstant.SPDZT_06);
			checkVo.setCheckRemarks("办事处退回意见:"+checkRemarks);
			checkVo.setCreateTime(Pub.getCurrentDate());
			checkVo.setCreateUser(user.getAccount());
			checkVo.setOemCompanyId(user.getOemCompanyId());
			dao.insertCheck(checkVo);

    		atx.setOutMsg("1", "审核驳回成功");
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
	 * 索赔单驳回，与拒绝时，将服务站占用库存释放，并将实销单置为无效。
	 * 
	 * @param claimId
	 * @throws Exception
	 */
	public void rejectClaim(String claimId) throws Exception {
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		// 库存释放
		QuerySet qsp = dao.queryParts(claimId);// 查询索赔单下所有配件ID，供应商ID，配件数量。
		if (qsp.getRowCount() > 0) {
			String orgId = qsp.getString(1, "ORG_ID");// 服务站ID
			for (int i = 0; i < qsp.getRowCount(); i++) {// 遍历所有新件
				String aAmount = null;
				String oAmount = null;
				String stockId = null;

				String nPartId = qsp.getString(i + 1, "PART_ID");// 新件ID
				String nSupplierId = String.valueOf("2014090300052510");// 新件供应商ID
				String nPartCount = qsp.getString(i + 1, "SALE_COUNT");// 新件数量

				if (null == nPartId || nPartId.equals("")) {
				} else {
					QuerySet qsStock = dao.queryStock(nPartId, nSupplierId,orgId);
					if (qsStock.getRowCount() > 0) {
						aAmount = qsStock.getString(1, "AVAILABLE_AMOUNT");// 查询所有配件的可用库存
						oAmount = qsStock.getString(1, "OCCUPY_AMOUNT");// 查询所有配件的占用库存
						stockId = qsStock.getString(1, "STOCK_ID");// 查询所有配件的库存ID
					} else {
						aAmount = "0";
						oAmount = "0";
					}
					if (null == stockId || stockId.equals("")) {
					} else {
						PtBuDealerStockVO pbsVo = new PtBuDealerStockVO();// 库存表VO
						int saleCount = Integer.parseInt(nPartCount);
						// 对库存更新
						Double avaiamount = Double.parseDouble(aAmount.toString()) + saleCount;
						// 驳回，拒绝时，将可用库存加上新件数量，占用减去新件数量。
						Double occupyamount = Double.parseDouble(oAmount.toString()) - saleCount;
						if(occupyamount<0||avaiamount<0){
                            throw new Exception("库存不足，无法操作。");
                        }else{
						pbsVo.setAvailableAmount(avaiamount.toString());
						pbsVo.setOccupyAmount(occupyamount.toString());
						pbsVo.setUpdateTime(Pub.getCurrentDate());
						pbsVo.setUpdateUser(user.getAccount());
						pbsVo.setStockId(stockId);
						dao.DealerStockUpdate(pbsVo);
						
						String url="办事处驳回:/action/service/claimmng/ClaimOutBuyMngAction/rejectClaim.ajax";
	                    dao.insetStockDtl1(saleCount,user,orgId,claimId,url,nPartId);//插入库存变化明细。
                        }
					}	
				}
				dao.updateRsStatus(claimId);// 将索赔单对应的实销单置为无效。
				dao.updateObStatus(claimId);// 将索赔单对应的外采单置为无效。
			}
		}
	}
	public void download()throws Exception{
		//获取封装后的request对象
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response= atx.getResponse();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
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
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("BUY_NO");
			hBean.setTitle("外采单号");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("CUSTOMER_NAME");
			hBean.setTitle("客户名称");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("BUY_COUNT");
			hBean.setTitle("外购数量");
			header.add(5,hBean);
			hBean = new HeaderBean();
			hBean.setName("BUY_AMOUNT");
			hBean.setTitle("外购金额");
			header.add(6,hBean);
			hBean = new HeaderBean();
			hBean.setName("BUY_DATE");
			hBean.setTitle("外购日期");
			header.add(7,hBean);
			hBean = new HeaderBean();
			hBean.setName("STATUS");
			hBean.setTitle("外采单状态");
			header.add(8,hBean);
			hBean = new HeaderBean();
			hBean.setName("BUY_STATUS");
			hBean.setTitle("是否审核通过");
			header.add(9,hBean);
			String orgType = user.getOrgDept().getOrgType();
			String claimNo = Pub.val(request, "claimNo");
			
			if(orgType.equals(DicConstant.ZZLB_09)){
				QuerySet qs = dao.download(conditions,user,claimNo);
				ExportManager.exportFile(response.getHttpResponse(), "外采单导出", header, qs);
			}else{
				QuerySet qs = dao.download1(conditions,claimNo);
				ExportManager.exportFile(response.getHttpResponse(), "外采单导出", header, qs);
			}
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
	public void download1()throws Exception{
		//获取封装后的request对象
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response= atx.getResponse();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("BUY_NO");
			hBean.setTitle("外采单号");
			header.add(0,hBean); 
			hBean = new HeaderBean();
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(1,hBean);
			hBean = new HeaderBean();
			hBean.setName("PART_CODE");
			hBean.setTitle("配件代码");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("PART_NAME");
			hBean.setTitle("配件名称");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("UNIT");
			hBean.setTitle("单位");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("SUPPLIER_CODE");
			hBean.setTitle("供应商代码");
			header.add(5,hBean);
			hBean = new HeaderBean();
			hBean.setName("SUPPLIER_NAME");
			hBean.setTitle("供应商名称");
			header.add(6,hBean);
			hBean = new HeaderBean();
			hBean.setName("BUY_PRICE");
			hBean.setTitle("外采价格");
			header.add(7,hBean);
			hBean = new HeaderBean();
			hBean.setName("BUY_COUNT");
			hBean.setTitle("外采数量");
			header.add(8,hBean);
			hBean = new HeaderBean();
			hBean.setName("AMOUNT");
			hBean.setTitle("外采总金额");
			header.add(9,hBean);
			String orgType = user.getOrgDept().getOrgType();
			String claimNo = Pub.val(request, "claimNo");
			
			if(orgType.equals(DicConstant.ZZLB_09)){
				QuerySet qs = dao.download3(conditions,user,claimNo);
				ExportManager.exportFile(response.getHttpResponse(), "外采单明细导出", header, qs);
			}else{
				QuerySet qs = dao.download2(conditions,claimNo);
				ExportManager.exportFile(response.getHttpResponse(), "外采单明细导出", header, qs);
			}
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
}
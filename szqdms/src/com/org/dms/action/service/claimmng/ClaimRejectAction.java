package com.org.dms.action.service.claimmng;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.claimmng.ClaimRejectDao;
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
 * 索赔单驳回ACTION
 * @author zts
 *
 */
public class ClaimRejectAction {
	private Logger logger = com.org.framework.log.log.getLogger("ClaimRejectAction");
	private ActionContext atx = ActionContext.getContext();
	private ClaimRejectDao dao=ClaimRejectDao.getInstance(atx);
	 
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
	 * 索赔单驳回
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void claimRejectUpdate()throws Exception{
		//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuClaimVO vo=new SeBuClaimVO();
        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			Map map = new HashMap();
			String claimId=hm.get("CLAIM_ID");
			String claimType="";
			String vehicleId="";
			String activityId="";
			String preAuthorId="";
			QuerySet qs = dao.getClaimInfo(claimId);
			if(qs.getRowCount() > 0 ){
				claimType=qs.getString(1,"CLAIM_TYPE");
				vehicleId=qs.getString(1,"VEHICLE_ID");
				activityId=qs.getString(1,"ACTIVITY_ID");
				preAuthorId=qs.getString(1, "PRE_AUTHOR_ID");
			}
			String checkRemarks=hm.get("CHECK_REMARKS");
			vo.setClaimId(claimId);
			vo.setRejectDate(Pub.getCurrentDate());//审核驳回日期
			vo.setClaimStatus(DicConstant.SPDZT_06);//索赔单状态 (人工审核驳回)
			vo.setUpdateUser(user.getAccount());
			vo.setStockMeet("");
			vo.setUpdateTime(Pub.getCurrentDate());
            dao.claimRejectUpdae(vo);//修改索赔单
            dao.updateFinalDate(claimId);//逆流程  将终审时间更新为空（特殊情况）
            dao.updateOughtCount(claimId);//逆流程 将不需要回运的件 审核通过数量更新为0
            dao.updateOughtCount1(claimId);//逆流程 将处理措施是加装 和维修的 审核通过数量更新为0
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
            rejectClaim(claimId);
            //插入审核轨迹
            SeBuClaimCheckVO checkVo=new SeBuClaimCheckVO();
            checkVo.setClaimId(claimId);
            checkVo.setCheckUser(user.getAccount());
            checkVo.setCheckDate(Pub.getCurrentDate());
            checkVo.setCheckResult(DicConstant.SPDZT_06);
            checkVo.setCheckRemarks(checkRemarks);
            checkVo.setCreateTime(Pub.getCurrentDate());
            checkVo.setCreateUser(user.getAccount());
            checkVo.setOemCompanyId(user.getOemCompanyId());
            dao.insertCheck(checkVo);
            atx.setOutMsg("1","索赔单驳回！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
	}
	/**
	 * 索赔单驳回，与拒绝时，将服务站已经被实销出库扣掉的总数与可用加上实销出库数量
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
				String amount = null;

				String nPartId = qsp.getString(i + 1, "PART_ID");// 新件ID
				String nSupplierId = String.valueOf("2014090300052510");// 新件供应商ID
				String nPartCount = qsp.getString(i + 1, "SALE_COUNT");// 新件数量
				String dtlId = qsp.getString(i + 1, "DTL_ID");// 实销明细ID

				if (null == nPartId || nPartId.equals("")) {
				} else {
					QuerySet qsStock = dao.queryStock(nPartId, nSupplierId,orgId);
					if (qsStock.getRowCount() > 0) {
						aAmount = qsStock.getString(1, "AVAILABLE_AMOUNT");// 查询所有配件的可用库存
						oAmount = qsStock.getString(1, "OCCUPY_AMOUNT");// 查询所有配件的占用库存
						amount = qsStock.getString(1, "AMOUNT");// 查询所有配件的总库存
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
						Double zAmount = Double.parseDouble(amount.toString()) + saleCount;
						// 驳回，拒绝时，将可用库存加上新件数量，总数加上新件数量。
						Double avaAmount = Double.parseDouble(aAmount.toString()) + saleCount;
						pbsVo.setAmount(zAmount.toString());
						pbsVo.setAvailableAmount(avaAmount.toString());
						pbsVo.setUpdateTime(Pub.getCurrentDate());
						pbsVo.setUpdateUser(user.getAccount());
						pbsVo.setStockId(stockId);
						dao.DealerStockUpdate(pbsVo);
						
						String url="初审通过后驳回,+可用,+总数。/action/service/claimmng/ClaimRejectAction/rejectClaim.ajax";
	                    dao.insetStockDtl(saleCount,user,claimId,url,nPartId,orgId);//插入库存变化明细。
					}
					dao.insetStockChange(dtlId,stockId,orgId,user);//将实销驳回记录插入到库存异动明细表中。
				}
				dao.updateRsStatus(claimId);// 将索赔单对应的实销单置为无效。
				dao.updateObStatus(claimId);// 将索赔单对应的实销单置为无效。
			}
		}
	}

}

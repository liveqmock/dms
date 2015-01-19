package com.org.dms.action.service.claimmng;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.claimmng.ClaimCheckDao;
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
 * 索赔单审核
 * 
 * @author zts
 * 
 */
public class ClaimCheckAction {
	private Logger logger = com.org.framework.log.log
			.getLogger("ClaimCheckAction");
	private ActionContext atx = ActionContext.getContext();
	private ClaimCheckDao dao = ClaimCheckDao.getInstance(atx);

	/**
	 * 索赔单查询
	 * 
	 * @throws Exception
	 */
	public void claimSearch() throws Exception {
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.claimSearch(page, conditions, user);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 索赔单费用查询
	 * 
	 * @throws Exception
	 */
	public void claimCostsSearch() throws Exception {
		RequestWrapper request = atx.getRequest();
		//User user = (User) atx.getSession().get(Globals.USER_KEY);
		try {
			String claimId = request.getParamValue("claimId");
			/*SeBuClaimVO vo = new SeBuClaimVO();
			vo.setClaimId(claimId);
			vo.setOperateUser(user.getAccount());
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			dao.claimUpdate(vo);*/
			BaseResultSet bs = dao.claimCostsSearch(claimId);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 附件查询
	 * 
	 * @throws Exception
	 */
	public void fileSearch() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request, page);
		try {
			String workId = request.getParamValue("workId");
			BaseResultSet bs = dao.fileSearch(page, user, workId);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 审核轨迹查询
	 * 
	 * @throws Exception
	 */
	public void hisCheckSearch() throws Exception {
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request, page);
		try {
			String claimId = request.getParamValue("claimId");
			BaseResultSet bs = dao.hisCheckSearch(page, claimId);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 索赔单审核
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void claimCheckUpdate() throws Exception {
		// 获取封装后的request对象
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String claimStatus = Pub.val(request, "claimStatus");
		try {
			SeBuClaimVO vo = new SeBuClaimVO();
			HashMap<String, String> hm;
			hm = RequestUtil.getValues(request);
			Map map = new HashMap();
			String claimId = hm.get("CLAIM_ID");
			String checkRemarks = hm.get("CHECK_REMARKS");
			String adjustCosts =hm.get("ADJUST_COSTS");//调整费用
			String adjustRemarks =hm.get("ADJUST_REMARKS");//调整原因 
			String outAmount =hm.get("OUT_AMOUNT");//外出总费用
			String claimType =hm.get("CLAIM_TYPE");//索赔单类型
			String flagPre="0";
			// 人工审核驳回、审核拒绝
			if (claimStatus.equals(DicConstant.SPDZT_06.toString())|| claimStatus.equals(DicConstant.SPDZT_07.toString())) {
				vo.setRejectDate(Pub.getCurrentDate());// 审核驳回日期
				vo.setStockMeet("");
				rejectClaim(claimId);// 将索赔单对应的实销单置为无效。将库存释放
				//将车辆最新里程 更新成 上一次里程
				Integer type = new Integer(claimType);
				switch (type.intValue()) {
				case 301401:// 正常保修 驳回情况，要将更新预授权
					flagPre="1";
					break;
				case 301402:// 首保
					map.put("sbxx","1");
					break;
				case 301403: // 服务活动
					dao.updateActivityVehicle(claimId,user);// 更新服务活动中的车辆使用情况
					break;
				case 301405:// 定保次数
					map.put("dbcs", "1");// 更新车辆定保次数
					break;
				case 301406:// 售前培训检查
					map.put("sqjccs", "1");// 更新售前培训检查次数
					break;
				case 301407:// 安全检查
					map.put("aqjccs", "1");// 更新安全检查次数次数
					break;
				case 301408:// 照顾性保修
					flagPre="1"; 
					break;
				}
				dao.updateVehicle(claimId,map,user);
			}
			//审核拒绝（拒绝状态）
			if(claimStatus.equals(DicConstant.SPDZT_07.toString())){
				vo.setReapplyStatus(DicConstant.CXSQZT_02);//重新审核驳回
			}
			// 人工审核通过
			String sl="1";
			if (claimStatus.equals(DicConstant.SPDZT_05.toString())) {
				vo.setCheckpassDate(Pub.getCurrentDate());// 初审通过日期
				vo.setAdjustCosts(adjustCosts);//调整费用
				vo.setAdjustRemarks(adjustRemarks);//调整原因
				vo.setAdjustDate(Pub.getCurrentDate());//调整时间
				vo.setAdjustUser(user.getAccount());//调整人
				vo.setIfPersonCheck(DicConstant.SF_01);//人工点击初审通过(统计人工审核的数量)
				vo.setOutAmount(outAmount);//外出总费用
				QuerySet qSet=dao.getReturnOldPartCount(claimId);//获取回运个数（如果是0 更新索赔单终审时间）
				if(qSet.getRowCount() > 0){
					sl=qSet.getString(1,"SL");
				}
				realSaleStock(claimId);// 实销单出库，扣掉占用库存。总库存减少。		
			}
			//需回运数量是0 更新终审时间
			if("0".equals(sl)){
				vo.setOldpartFinalDate(Pub.getCurrentDate());//终审时间
				claimStatus=DicConstant.SPDZT_15.toString();//如果没有旧件回运的索赔单，直接终审
				dao.updateOught(claimId);//更新不需要回运旧件的数量
				dao.updateOught1(claimId);//更新维修和加装 的数量
			}
			if("1".equals(flagPre)){
				dao.updatePreAuthor(claimId, user);// 照顾性保修更新预售权未使用
			}
			vo.setClaimId(claimId);
			vo.setClaimStatus(claimStatus);// 索赔单状态
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			dao.claimCheckUpdae(vo);// 修改索赔单
			// 插入审核轨迹
			SeBuClaimCheckVO checkVo = new SeBuClaimCheckVO();
			checkVo.setClaimId(claimId);
			checkVo.setCheckUser(user.getAccount());
			checkVo.setCheckDate(Pub.getCurrentDate());
			checkVo.setCheckResult(claimStatus);
			checkVo.setCheckRemarks(checkRemarks);
			checkVo.setCreateTime(Pub.getCurrentDate());
			checkVo.setCreateUser(user.getAccount());
			checkVo.setOemCompanyId(user.getOemCompanyId());
			dao.insertCheck(checkVo);
			if (claimStatus.equals(DicConstant.SPDZT_05.toString()) ||claimStatus.equals(DicConstant.SPDZT_15.toString()) ) {
				// 人工审核通过 配件出库
				atx.setOutMsg("1", "审核通过.");
			} else if (claimStatus.equals(DicConstant.SPDZT_06.toString())) {
				atx.setOutMsg("1", "初审驳回.");
			} else if (claimStatus.equals(DicConstant.SPDZT_07.toString())) {
				atx.setOutMsg("1", "初审拒绝.");
			} else {
				atx.setOutMsg("1", "转领导审批.");
			}
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
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
                            throw new Exception("库存不足，无法操作。请将本单驳回重新提报。");
                        }else{
						pbsVo.setAvailableAmount(avaiamount.toString());
						pbsVo.setOccupyAmount(occupyamount.toString());
						pbsVo.setUpdateTime(Pub.getCurrentDate());
						pbsVo.setUpdateUser(user.getAccount());
						pbsVo.setStockId(stockId);
						dao.DealerStockUpdate(pbsVo);
						
						String url="初审驳回,实销单释放库存:/action/service/claimmng/ClaimCheckAction/rejectClaim.ajax";
	                    dao.insetStockDtl1(saleCount,user,claimId,url,nPartId,orgId);//插入库存变化明细。
                        }
                    }
				}
				dao.updateRsStatus(claimId);// 将索赔单对应的实销单置为无效。
				dao.updateObStatus(claimId);// 将索赔单对应的外采单置为无效。
			}
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
		QuerySet qsp = dao.queryParts(claimId);// 查询索赔单下所有配件ID，供应商ID，配件数量。
		if (qsp.getRowCount() > 0) {
			String orgId = qsp.getString(1, "ORG_ID");// 服务站ID
			for (int i = 0; i < qsp.getRowCount(); i++) {// 遍历所有新件
				String amount = null;
				String oAmount = null;
				String stockId = null;

				String nPartId = qsp.getString(i + 1, "PART_ID");// 新件ID
				//String nSupplierId = qsp.getString(i + 1, "SUPPLIER_ID");// 新件供应商ID
				String nSupplierId = String.valueOf("2014090300052510");// 新件供应商ID
				String nPartCount = qsp.getString(i + 1, "SALE_COUNT");// 新件数量
				String dtlId = qsp.getString(i + 1, "DTL_ID");//实销明细ID

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
                            throw new Exception("库存不足，无法操作。请将本单驳回重新提报。");
                        }else{
						pbsVo.setAmount(aamount.toString());
						pbsVo.setOccupyAmount(occupyamount.toString());
						pbsVo.setUpdateTime(Pub.getCurrentDate());
						pbsVo.setUpdateUser(user.getAccount());
						pbsVo.setStockId(stockId);
						dao.DealerStockUpdate(pbsVo);
						String url="初审通过,实销单出库,-总数,-占用。:/action/service/claimmng/ClaimCheckAction/realSaleStock.ajax";
	                    dao.insetStockDtl(saleCount,user,claimId,url,nPartId,orgId);//插入库存变化明细。
					}
					dao.insetStockChange(dtlId,stockId,orgId,user);//将出库记录插入到库存异动明细表中。
					dao.updateRsSaleStatus(claimId);// 将实销单的实销状态改为已出库
					}
				}
			}
		}
	}

	/**
	 * 查询索赔单信息
	 * 
	 * @throws Exception
	 */
	public void searchClaimInfo() throws Exception {
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String claimId = Pub.val(request, "claimId");
		RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.searchClaimInfo(page, claimId);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 外出信息
	 * 
	 * @throws Exception
	 */
	public void searchClaimOutInfo() throws Exception {
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String claimId = Pub.val(request, "claimId");
		RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.searchClaimOutInfo(page, claimId);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 故障信息
	 * 
	 * @throws Exception
	 */
	public void searchClaimPattern() throws Exception {
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request, page);
		String claimId = Pub.val(request, "claimId");
		try {
			// HashMap<String,String> hm;
			// hm = RequestUtil.getValues(request);
			// String claimId=hm.get("CLAIM_ID");
			BaseResultSet bs = dao.searchClaimPattern(page, claimId);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/***
	 * 索赔单费用调整查询
	 * @throws Exception
	 */
	public void claimCostAdjustSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.claimCostAdjustSearch(page, conditions, user);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 索赔单费用调整
	 * @throws Exception
	 */
	public void claimCostUpdate()throws Exception{
		// 获取封装后的request对象
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		try {
			SeBuClaimVO vo = new SeBuClaimVO();
			HashMap<String, String> hm;
			hm = RequestUtil.getValues(request);
			String claimId = hm.get("CLAIM_ID");
			String adjustCosts =hm.get("ADJUST_COSTS");//调整费用
			String adjustRemarks =hm.get("ADJUST_REMARKS");//调整原因 
			String outAmount =hm.get("OUT_AMOUNT");//外出总费用
			
			vo.setClaimId(claimId);
			vo.setAdjustCosts(adjustCosts);//费用调整
			vo.setAdjustDate(Pub.getCurrentDate());//调整日期
			vo.setAdjustUser(user.getAccount());//调整人
			vo.setAdjustRemarks(adjustRemarks);//调整原因
			vo.setOutAmount(outAmount);//外出总费用
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			dao.claimCheckUpdae(vo);
			atx.setOutMsg("1", "费用调整成功.");
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		}
	}
}

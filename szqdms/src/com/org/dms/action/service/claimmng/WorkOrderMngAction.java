package com.org.dms.action.service.claimmng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.service.claimmng.WorkOrderMngDao;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.part.PtBuOutBuyDtlVO;
import com.org.dms.vo.part.PtBuOutBuyVO;
import com.org.dms.vo.part.PtBuRealSaleDtlVO;
import com.org.dms.vo.part.PtBuRealSaleVO;
import com.org.dms.vo.service.SeBuClaimCheckVO;
import com.org.dms.vo.service.SeBuClaimFaultPartVO;
import com.org.dms.vo.service.SeBuClaimOutVO;
import com.org.dms.vo.service.SeBuClaimOutVO_Ext;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.dms.vo.service.SeBuClaimVO_Ext;
import com.org.dms.vo.service.SeBuWorkOrderVO;
import com.org.frameImpl.Constant;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.params.ParaManager;
import com.org.framework.params.UserPara.UserParaConfigureVO;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class WorkOrderMngAction {
	private Logger logger = com.org.framework.log.log
			.getLogger("WorkOrderMngAction");
	private ActionContext atx = ActionContext.getContext();
	private WorkOrderMngDao dao = WorkOrderMngDao.getInstance(atx);

	/**
	 * @title: searchWorkOrder
	 * @description: TODO(查询工单信息for工单转索赔单功能)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void searchWorkOrder() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.searchWorkOrder(page, user, conditions);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: queryWorkOrder
	 * @description: TODO(查询工单信息for工单查询)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void queryWorkOrder() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.queryWorkOrder(page, user, conditions);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * @title: bscQueryWorkOrder
	 * @description: TODO(查询工单信息for工单查询,办事处)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void bscQueryWorkOrder() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.bscQueryWorkOrder(page, user, conditions);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * 办事处导出
	 * @throws Exception
	 */
	public void bscDownload()throws Exception{
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
			hBean.setName("ORG_CODE");
			hBean.setTitle("渠道商代码");
			header.add(0,hBean);
			hBean = new HeaderBean();
			hBean.setName("ORG_NAME");
			hBean.setTitle("渠道商名称");
			header.add(1,hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_NO");
			hBean.setTitle("派工单号");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("LICENSE_PLATE");
			hBean.setTitle("车牌号");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_VIN");
			hBean.setTitle("工单VIN");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_STATUS");
			hBean.setTitle("工单状态");
			header.add(5,hBean);
			hBean = new HeaderBean();
			hBean.setName("REPAIR_USER");
			hBean.setTitle("维修人");
			header.add(6,hBean);
			hBean = new HeaderBean();
			hBean.setName("JOBORDER_TIME");
			hBean.setTitle("派工时间");
			header.add(7,hBean);
			hBean = new HeaderBean();
			hBean.setName("GO_DATE");
			hBean.setTitle("出发时间");
			header.add(8,hBean);
			hBean = new HeaderBean();
			hBean.setName("ARRIVE_DATE");
			hBean.setTitle("到达时间");
			header.add(9,hBean);
			hBean = new HeaderBean();
			hBean.setName("COMPLETE_DATE");
			hBean.setTitle("完成时间");
			header.add(10,hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_TYPE");
			hBean.setTitle("工单类型");
			header.add(11,hBean);
			hBean = new HeaderBean();
			hBean.setName("IF_OUT");
			hBean.setTitle("是否外出");
			header.add(12,hBean);
			QuerySet qs = dao.bscDownload(conditions,user);
			ExportManager.exportFile(response.getHttpResponse(), "办事处工单查询", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
	/**
	 * 厂端工单查询下载
	 * @throws Exception
	 */
	public void oemDownload()throws Exception{
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
			hBean.setName("ORG_CODE");
			hBean.setTitle("渠道商代码");
			header.add(0,hBean);
			hBean = new HeaderBean();
			hBean.setName("ORG_NAME");
			hBean.setTitle("渠道商名称");
			header.add(1,hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_NO");
			hBean.setTitle("派工单号");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("LICENSE_PLATE");
			hBean.setTitle("车牌号");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_VIN");
			hBean.setTitle("工单VIN");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_STATUS");
			hBean.setTitle("工单状态");
			header.add(5,hBean);
			hBean = new HeaderBean();
			hBean.setName("REPAIR_USER");
			hBean.setTitle("维修人");
			header.add(6,hBean);
			hBean = new HeaderBean();
			hBean.setName("JOBORDER_TIME");
			hBean.setTitle("派工时间");
			header.add(7,hBean);
			hBean = new HeaderBean();
			hBean.setName("GO_DATE");
			hBean.setTitle("出发时间");
			header.add(8,hBean);
			hBean = new HeaderBean();
			hBean.setName("ARRIVE_DATE");
			hBean.setTitle("到达时间");
			header.add(9,hBean);
			hBean = new HeaderBean();
			hBean.setName("COMPLETE_DATE");
			hBean.setTitle("完成时间");
			header.add(10,hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_TYPE");
			hBean.setTitle("工单类型");
			header.add(11,hBean);
			hBean = new HeaderBean();
			hBean.setName("IF_OUT");
			hBean.setTitle("是否外出");
			header.add(12,hBean);
			QuerySet qs = dao.oemDownload(conditions,user);
			ExportManager.exportFile(response.getHttpResponse(), "厂端工单查询", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
	
	/**
	 * @title: queryDealerWorkOrder
	 * @description: TODO(查询工单信息for工单查询)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void queryDealerWorkOrder() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.queryDealerWorkOrder(page, user, conditions);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * @title: queryWorkOrder
	 * @description: TODO(查询工单信息for工单查询)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void queryDtl() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		try {
			String workId=Pub.val(request, "workId");
			BaseResultSet bs = dao.queryDtl(page, user, workId);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: addClaimMaintis
	 * @description: TODO(添加索赔单主信息)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void addClaimMaintis() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String workId = Pub.val(request, "workId");
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			// 判断工单是否已生成了索赔单
			QuerySet qs = dao.checkWorkOrder(workId, user);
			if (qs.getRowCount() > 0) {
				String n = qs.getString(1, 1);
				if (Integer.parseInt(n) == 0)// 不存在索赔单，添加索赔单信息
				{
					dao.insertClaimMantis(workId, user);
				}
			}
			// 查询索赔单信息，传给前台
			BaseResultSet bs = dao.searchClaimMatin(page, user, workId,
					conditions);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: searchPattern
	 * @description: TODO(查询故障信息)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void searchPattern() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String cliamId = Pub.val(request, "cliamId");
		String cliamType = Pub.val(request, "claimType");
		String flag=null;
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			HashMap<String, String> hm;
			hm = RequestUtil.getValues(request);
			String partCode = hm.get("PART_CODE");
			String partName = hm.get("PART_NAME");
			if(DicConstant.SPDLX_03.equals(cliamType)){
				QuerySet qs= dao.queryPart(cliamId);
				QuerySet qs1= dao.queryWorkTimes(cliamId);
				if(qs.getRowCount()>0||qs1.getRowCount()>0){
					
				}else{
					 flag="1";
				}
			}
			BaseResultSet bs = dao.searchPattern(page, user, conditions,cliamId, cliamType, partCode, partName,flag);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: addClaimFattern
	 * @description: TODO(添加索赔单故障信息)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void addClaimFattern() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String claimId = Pub.val(request, "cliamId");
		String relationId = Pub.val(request, "relationId");
		String claimType = Pub.val(request, "claimType");
		String userType = Pub.val(request, "userType");
		try {

			// 插入索赔单故障信息
			dao.insertClaimPattern(claimId, relationId, user, claimType,userType);
			atx.setOutMsg("", "添加故障信息成功.");
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: searchPattern
	 * @description: TODO(查询索赔单故障信息)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void searchClaimPattern() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.searchClaimPattern(page, user, conditions);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 删除索赔单故障信息
	 * 
	 * @throws Exception
	 */
	public void deleteClaimPattern() throws Exception {
		RequestWrapper request = atx.getRequest();
		//User user = (User) atx.getSession().get(Globals.USER_KEY);
		String claimDtlId = Pub.val(request, "claimDtlId");
		try {
			// 先删除故障与零件关系表
			dao.deleteClaimPatternPart(claimDtlId);
			// 在删除故障信息表
			dao.deleteClaimPattern(claimDtlId);
			atx.setOutMsg("", "删除故障信息成功.");
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: searchOldPart
	 * @description: TODO(查询故障中的旧件，前提是在故障与零件关系表里存在并且不在已提报的索赔单中出现的)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void searchOldPart() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String claimDtlId = Pub.val(request, "claimDtlId");
		String selectType = Pub.val(request, "selectType");// 1代表查询的是旧件,2代表查询的是新件
		String claimType = Pub.val(request, "claimType");
		String claimId = Pub.val(request, "claimId");
		String faultType = Pub.val(request, "faultType");//是否主损件
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.searchOldPart(page, user, conditions,
					claimDtlId, selectType, claimType, claimId,faultType);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 新增故障配件
	 * @throws Exception
	 */
	public void insertFaultPart() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String ClaimId = Pub.val(request, "claimId");// 索赔单主ID
		String htmlId = Pub.val(request, "htmlId");// 页面动态生成的自动ID
		String ClaimDtlId = Pub.val(request, "claimDtlId");// 故障信息表ID
		try {
			SeBuClaimFaultPartVO vo = new SeBuClaimFaultPartVO();
			HashMap<String, String> hm;
			hm = RequestUtil.getValues(request);
			String FaultType = hm.get(htmlId + "_FAULTTYPE");// 故障类别
			String Measures = hm.get(htmlId + "_MEASURES");// 处理措施
			String PartType = hm.get(htmlId + "_PARTTYPE");// 配件类别
			String RepayUprice = hm.get(htmlId + "_REPAYUPRICE");// 追偿单价
			// String FirstPartCode=hm.get(htmlId+"_FIRSTPARTCODE");//祸首件代码
			// String FirstPartName=hm.get(htmlId+"_FIRSTPARTNAME");//祸首件名称
			// String FirstPartId=hm.get(htmlId+"_FIRSTPARTID");//祸首件ID
			String OldPartCode = hm.get(htmlId + "_OLDPARTCODE");// 旧件代码
			String OldPartName = hm.get(htmlId + "_OLDPARTNAME");// 旧件名称
			String OldPartId = hm.get(htmlId + "_OLDPARTID");// 旧件ID
			String OldSupplierId = hm.get(htmlId + "_OLDSUPPLIERID");// 旧件供应商ID
			String OldPartCount = hm.get(htmlId + "_OLDPARTCOUNT");// 旧件数量
			String OldPartStream = hm.get(htmlId + "_OLDPARTSTREAM");// 旧件零件流水号
			// String IfReturn=hm.get(htmlId+"_IFRETURN");//是否回运
			String NewPartCode = hm.get(htmlId + "_NEWPARTCODE");// 新件代码
			String NewPartName = hm.get(htmlId + "_NEWPARTNAME");// 新件名称
			String NewPartId = hm.get(htmlId + "_NEWPARTID");// 新件ID
			String NewSupplierId = hm.get(htmlId + "_NEWSUPPLIERID");// 新件供应商ID
			String NewPartCount = hm.get(htmlId + "_NEWPARTCOUNT");// 新件数量
			String NewPartStream = hm.get(htmlId + "_NEWPARTSTREAM");// 新件零件流水号
			String NewPartFrom = hm.get(htmlId + "_NEWPARTFROM");// 新件来源
			String ClaimUprice = hm.get(htmlId + "_CLAIMYPRICE");// 索赔单价
			String ClaimCosts = hm.get(htmlId + "_CLAIMCOSTS");// 索赔材料费
			String bridgeTypeId=hm.get(htmlId+"_BRIDGETYPE");//桥类别
			String BridgeCode=hm.get(htmlId+"_BRIDGECODE");////桥编码
			String FaultReason = hm.get(htmlId + "_FAULTREASON");// 故障原因
			// String Severity=hm.get(htmlId+"_SEVERITY");//严重程度
			String BridgeSupplierNo = hm.get(htmlId + "_BRIDGESUPPLIERNO");// 桥供应商号
			String ifWorkMultiple = hm.get(htmlId + "_IFWORKMULTIPLE");// 工时倍数
			vo.setFaultType(FaultType);
			vo.setClaimId(ClaimId);
			vo.setMeasures(Measures);
			vo.setPartType(PartType);
			vo.setRepayUprice(RepayUprice);
			vo.setClaimDtlId(ClaimDtlId);
			/*
			 * if(DicConstant.GZLB_02.equals(FaultType)) {
			 * vo.setFirstPartCode(FirstPartCode);
			 * vo.setFirstPartId(FirstPartId);
			 * vo.setFirstPartName(FirstPartName); }
			 */
			vo.setOldPartCode(OldPartCode);
			vo.setOldPartCount(OldPartCount);
			vo.setOldPartId(OldPartId);
			vo.setOldPartName(OldPartName);
			vo.setOldPartStream(OldPartStream);
			vo.setOldSupplierId(OldSupplierId);
			// vo.setIfReturn(IfReturn);
			if (DicConstant.CLFS_02.equals(Measures)
					|| DicConstant.CLFS_03.equals(Measures)) {
				vo.setNewPartCode(NewPartCode);
				vo.setNewPartCount(NewPartCount);
				vo.setNewPartFrom(NewPartFrom);
				vo.setNewPartId(NewPartId);
				vo.setNewPartName(NewPartName);
				vo.setNewPartStream(NewPartStream);
				vo.setNewSupplierId(NewSupplierId);
				vo.setClaimUprice(ClaimUprice);
				vo.setClaimCosts(ClaimCosts);
			}
			vo.setBridgeType(bridgeTypeId);
			vo.setBridgeCode(BridgeCode);
			vo.setFaultReason(FaultReason);
			vo.setIfReturn(DicConstant.SF_02);// 是否已回运，默认否
			// vo.setSeverity(Severity);
			vo.setBridgeSupplierNo(BridgeSupplierNo);
			vo.setStatus(Constant.YXBS_01);
			vo.setCreateTime(Pub.getCurrentDate());
			vo.setCreateUser(user.getAccount());
			//判断是否为主损件
			if(DicConstant.GZLB_01.toString().equals(FaultType) ){
				if(DicConstant.SF_01.toString().equals(ifWorkMultiple)){
					if(DicConstant.CLFS_03.toString().equals(Measures)){
						dao.updateClaimDtl(ClaimDtlId,NewPartCount);//更新工时费用
					}else{
						dao.updateClaimDtl(ClaimDtlId,OldPartCount);//更新工时费用
					}
				}
			}
			dao.insertFaultPart(vo);
			atx.setOutMsg(vo.getRowXml(), "新增故障零件成功！");
		} catch (Exception e) {
			// 设置失败异常处理
			atx.setException(e);
			logger.error(e);
		}
	}

	/**
	 * 修改故障配件
	 * @throws Exception
	 */
	public void updateFaultPart() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String FaultPartId = Pub.val(request, "faultPartId");// 故障与零件关系主键
		String htmlId = Pub.val(request, "htmlId");// 页面动态生成的自动ID
		String ClaimDtlId = Pub.val(request, "claimDtlId");// 故障信息表ID
		try {
			SeBuClaimFaultPartVO vo = new SeBuClaimFaultPartVO();
			HashMap<String, String> hm;
			hm = RequestUtil.getValues(request);
			String FaultType = hm.get(htmlId + "_FAULTTYPE");// 故障类别
			String Measures = hm.get(htmlId + "_MEASURES");// 处理措施
			String PartType = hm.get(htmlId + "_PARTTYPE");// 配件类别
			String RepayUprice = hm.get(htmlId + "_REPAYUPRICE");// 追偿单价
			// String FirstPartCode=hm.get(htmlId+"_FIRSTPARTCODE");//祸首件代码
			// String FirstPartName=hm.get(htmlId+"_FIRSTPARTNAME");//祸首件名称
			// String FirstPartId=hm.get(htmlId+"_FIRSTPARTID");//祸首件ID
			String OldPartCode = hm.get(htmlId + "_OLDPARTCODE");// 旧件代码
			String OldPartName = hm.get(htmlId + "_OLDPARTNAME");// 旧件名称
			String OldPartId = hm.get(htmlId + "_OLDPARTID");// 旧件ID
			String OldSupplierId = hm.get(htmlId + "_OLDSUPPLIERID");// 旧件供应商ID
			String OldPartCount = hm.get(htmlId + "_OLDPARTCOUNT");// 旧件数量
			String OldPartStream = hm.get(htmlId + "_OLDPARTSTREAM");// 旧件零件流水号
			//String IfReturn = hm.get(htmlId + "_IFRETURN");// 是否回运
			String NewPartCode = hm.get(htmlId + "_NEWPARTCODE");// 新件代码
			String NewPartName = hm.get(htmlId + "_NEWPARTNAME");// 新件名称
			String NewPartId = hm.get(htmlId + "_NEWPARTID");// 新件ID
			String NewSupplierId = hm.get(htmlId + "_NEWSUPPLIERID");// 新件供应商ID
			String NewPartCount = hm.get(htmlId + "_NEWPARTCOUNT");// 新件数量
			String NewPartStream = hm.get(htmlId + "_NEWPARTSTREAM");// 新件零件流水号
			String NewPartFrom = hm.get(htmlId + "_NEWPARTFROM");// 新件来源
			String ClaimUprice = hm.get(htmlId + "_CLAIMYPRICE");// 索赔单价
			String ClaimCosts = hm.get(htmlId + "_CLAIMCOSTS");// 索赔材料费
			String bridgeTypeId=hm.get(htmlId+"_BRIDGETYPE");//桥类别
			String BridgeCode=hm.get(htmlId+"_BRIDGECODE");////桥编码
			String FaultReason = hm.get(htmlId + "_FAULTREASON");// 故障原因
			// String Severity=hm.get(htmlId+"_SEVERITY");//严重程度
			String BridgeSupplierNo = hm.get(htmlId + "_BRIDGESUPPLIERNO");// 桥供应商号
			String ifWorkMultiple = hm.get(htmlId + "_IFWORKMULTIPLE");//工时倍数

			vo.setFaultType(FaultType);
			vo.setMeasures(Measures);
			vo.setPartType(PartType);
			vo.setRepayUprice(RepayUprice);
			vo.setFaultPartId(FaultPartId);
			/*
			 * if(DicConstant.GZLB_02.equals(FaultType)) {
			 * vo.setFirstPartCode(FirstPartCode);
			 * vo.setFirstPartId(FirstPartId);
			 * vo.setFirstPartName(FirstPartName); }else {
			 * vo.setFirstPartCode(""); vo.setFirstPartId("");
			 * vo.setFirstPartName(""); }
			 */
			vo.setOldPartCode(OldPartCode);
			vo.setOldPartCount(OldPartCount);
			vo.setOldPartId(OldPartId);
			vo.setOldPartName(OldPartName);
			vo.setOldPartStream(OldPartStream);
			vo.setOldSupplierId(OldSupplierId);
			//vo.setIfReturn(IfReturn);
			if (DicConstant.CLFS_02.equals(Measures)
					|| DicConstant.CLFS_03.equals(Measures)) {
				vo.setNewPartCode(NewPartCode);
				vo.setNewPartCount(NewPartCount);
				vo.setNewPartFrom(NewPartFrom);
				vo.setNewPartId(NewPartId);
				vo.setNewPartName(NewPartName);
				vo.setNewPartStream(NewPartStream);
				vo.setNewSupplierId(NewSupplierId);
				vo.setClaimUprice(ClaimUprice);
				vo.setClaimCosts(ClaimCosts);
			} else {
				vo.setNewPartCode("");
				vo.setNewPartCount("");
				vo.setNewPartFrom("");
				vo.setNewPartId("");
				vo.setNewPartName("");
				vo.setNewPartStream("");
				vo.setNewSupplierId("");
				vo.setClaimUprice("");
				vo.setClaimCosts("");
			}
			vo.setBridgeType(bridgeTypeId);
			vo.setBridgeCode(BridgeCode);
			vo.setFaultReason(FaultReason);
			// vo.setSeverity(Severity);
			vo.setBridgeSupplierNo(BridgeSupplierNo);
			vo.setUpdateTime(Pub.getCurrentDate());
			vo.setUpdateUser(user.getAccount());
			//判断是否为主损件
			if(DicConstant.GZLB_01.toString().equals(FaultType) ){
				if(DicConstant.SF_01.toString().equals(ifWorkMultiple)){
					if(DicConstant.CLFS_03.toString().equals(Measures)){
						dao.updateClaimDtl(ClaimDtlId,NewPartCount);//更新工时费用
					}else{
						dao.updateClaimDtl(ClaimDtlId,OldPartCount);//更新工时费用
					}
				}
			}
			dao.updateFaultPart(vo);
			atx.setOutMsg(vo.getRowXml(), "修改故障零件成功.");
		} catch (Exception e) {
			// 设置失败异常处理
			atx.setException(e);
			logger.error(e);
		}
	}

	/**
	 * @title: searchPartTabs
	 * @description: TODO(查询索赔单故障零件信息)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void searchPartTabs() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String claimDtlId = Pub.val(request, "claimDtlId");
		String maxPartCout = Pub.val(request, "maxPartCout");
		PageManager page = new PageManager();
		if (!"".equals(maxPartCout)) {
			page.setPageRows(new Long(maxPartCout).intValue());
		}
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.searchPartTabs(page, user, conditions,
					claimDtlId);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 车辆校验查询
	 * 
	 * @throws Exception
	 */
	public void vinCheckSearch() throws Exception {
		RequestWrapper request = atx.getRequest();
		try {
			String vin = request.getParamValue("diVinVal");
			String engineNo = request.getParamValue("diEngineNoVal");
			BaseResultSet bs = dao.vinCheckSearch(vin, engineNo);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 删除索赔单故障零件信息
	 * 
	 * @throws Exception
	 */
	public void deletePartTab() throws Exception {
		RequestWrapper request = atx.getRequest();
		//User user = (User) atx.getSession().get(Globals.USER_KEY);
		String faultPartId = Pub.val(request, "faultPartId");
		try {
			// 先删除故障与零件关系表
			dao.deleteClaimPart(faultPartId);
			atx.setOutMsg("", "删除故障信息成功.");
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: searchActivity
	 * @description: TODO(查询已登记的服务活动)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void searchActivity() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String vehicleId = Pub.val(request, "vehicleId");
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.searchActivity(page, user, conditions,
					vehicleId);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: searchPreauth
	 * @description: TODO(查询预授权)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void searchPreauth() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String vehicleId = Pub.val(request, "vehicleId");
		String claimType = Pub.val(request, "claimType");
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.searchPreauth(page, user, conditions,vehicleId,claimType);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: searchPartOrder
	 * @description: TODO(查询三包急件订单)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void searchPartOrder() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String vehicleId = Pub.val(request, "vehicleId");
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.searchPartOrder(page, user, conditions,
					vehicleId);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 更新索赔单
	 * 
	 * @throws Exception
	 */
	public void updateClaim() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String claimId = Pub.val(request, "claimId");
		String seType = Pub.val(request, "seType");
		String rejectFlag = Pub.val(request, "rejectFlag");
		try {
			String applyCount = null;// 提报次数
			String rejectDate = null;// 拒绝时间
			String applyDate = null;// 提报日期
			if (rejectFlag.equals("2")) {
				QuerySet qs = dao.getClaimInfo(claimId);
				if (qs.getRowCount() > 0) {
					applyCount = qs.getString(1, "APPLY_COUNT");
					rejectDate = qs.getString(1, "REJECT_DATE");
					applyDate = qs.getString(1, "APPLY_DATE");
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			HashMap<String, String> hm;
			hm = RequestUtil.getValues(request);
			String claimNo = hm.get("DIA_CLAIM_NO");
			String claimStatus = hm.get("DIA_CLAIM_STATUS");
			String workId = hm.get("DIA_WORK_ID");
			SeBuClaimVO vo = new SeBuClaimVO();
			SeBuClaimVO_Ext extVO = new SeBuClaimVO_Ext();
			vo.setClaimId(claimId);
			extVO.setClaimId(claimId);
			vo.setValue(hm);
			extVO.setValue(hm);
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			vo.setClaimNo(claimNo);
			vo.setClaimStatus(claimStatus);
			vo.setSeType(seType);
			extVO.setSeType(seType);
			extVO.setClaimNo(claimNo);
			extVO.setClaimStatus(claimStatus);
			extVO.setWorkId(workId);
			if (rejectFlag.equals("2")) {
				extVO.setApplyCount(applyCount);
				if(!(rejectDate==null || "".equals(rejectDate))){
					extVO.setRejectDate(sdf.parse(rejectDate));	
				}
				if(!(applyDate==null || "".equals(applyDate))){
					extVO.setApplyDate(sdf.parse(applyDate));
				}
			}
			dao.updateClaim(vo);
			extVO.bindFieldToDic("CLAIM_STATUS", "SPDZT");
			extVO.bindFieldToDic("SE_TYPE", "FWLX");
			extVO.bindFieldToDic("APPLY_USER_TYPE", "BXRLX");
			extVO.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd HH:mm");
			extVO.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd HH:mm");
			extVO.setFieldDateFormat("APPLY_REPAIR_DATE", "yyyy-MM-dd HH:mm");
			extVO.setFieldDateFormat("REJECT_DATE", "yyyy-MM-dd HH:mm");
			extVO.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm");
			extVO.bindFieldToOrgXzqh("PROVINCE_CODE");
			extVO.bindFieldToOrgXzqh("CITY_CODE");
			extVO.bindFieldToOrgXzqh("COUNTY_CODE");
			atx.setOutMsg(extVO.getRowXml(), "索赔单信息保存成功！");
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: searchDealerOut
	 * @description: TODO(查询服务站外出费用)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void searchDealerOut() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			page.setPageRows(100);
			BaseResultSet bs = dao.searchDealerOut(page, user, conditions);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: searchOther
	 * @description: TODO(查询其他费用)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void searchOther() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		String claimId = Pub.val(request, "claimId");
		try {
			page.setPageRows(100);
			BaseResultSet bs = dao.searchOther(page, user, conditions, claimId);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: insertOther
	 * @description: TODO(查询插入其他费用)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void insertOther() throws Exception {
		RequestWrapper request = atx.getRequest();
		String claimId = Pub.val(request, "claimId");
		String outId = Pub.val(request, "outId");
		HashMap<String, String> hm;
		hm = RequestUtil.getValues(request);
		String costIds = hm.get("COST_IDS");// 其他项目ID
		String instorAmounts = hm.get("INSTOR_AMOUNTS");// 项目金额
		String[] costIdArr = costIds.split(",");
		String[] instorAmountArr = instorAmounts.split(",");
		try {
			for (int i = 0; i < costIdArr.length; i++) {
				String costId = costIdArr[i];
				String amount = instorAmountArr[i];
				dao.insertOther(costId, claimId, amount);
			}
			String allAmount = "0";
			QuerySet qs = dao.countOther(claimId);
			if (qs != null && qs.getRowCount() > 0) {
				allAmount = qs.getString(1, 1);
			}
			SeBuClaimOutVO vo = new SeBuClaimOutVO();
			vo.setOutId(outId);
			vo.setOtherCosts(allAmount);
			dao.updateClaimOut(vo);
			atx.setOutMsg(vo.getRowXml(), "选择其他费用完成");
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: insertOut
	 * @description: TODO(插入外出费用)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void insertOut() throws Exception {
		RequestWrapper request = atx.getRequest();
		String claimId = Pub.val(request, "claimId");
		String seType = Pub.val(request, "seType");
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		HashMap<String, String> hm;
		hm = RequestUtil.getValues(request);
		try {
			String claimNo = "";
			String claimStatus = "";
			QuerySet qs = dao.getClaimInfo(claimId);
			if (qs.getRowCount() > 0) {
				claimNo = qs.getString(1, "CLAIM_NO");
				claimStatus = qs.getString(1, "CLAIM_STATUS");
			}
			
			QuerySet qsOut =dao.getClaimOutInfo(claimId);
			String outId ="";//标识该索赔单是否有外出信息 outId是否存在
			if(qsOut.getRowCount() > 0 ){
				outId = qsOut.getString(1, "OUT_ID");
			}else{
				outId = "0"; 
			}
			// 插入外出信息
			SeBuClaimOutVO vo = new SeBuClaimOutVO();
			SeBuClaimOutVO_Ext voExt = new SeBuClaimOutVO_Ext();
			vo.setValue(hm);
			vo.setClaimId(claimId);
			voExt.setValue(hm);
			voExt.setClaimId(claimId);
			voExt.setSeType(seType);
			voExt.setClaimNo(claimNo);
			voExt.setClaimStatus(claimStatus);
			//判断是否有外出  0是没有外出信息
			if("0".equals(outId)){
				//获取GPS 信息
				String  startPhoneLongitude=""; //手机出发经度
				String  endPhoneLongitude=""; //手机到达经度
				String  startPhonelatitude=""; //手机出发纬度
				String  endPhonelatitude=""; //手机到达纬度
				
				String  startVehicleLongitude=""; //车辆出发经度
				String  endVehicleLongitude=""; //车辆到达经度
				String  startVehiclelatitude=""; //车辆出发纬度
				String  endVehiclelatitude=""; //车辆到达纬度
				
				String vehiclePhoneMileage="";//出发车辆和手机距离
				String startPhoneLocation="";//出发手机位置
				String endPhoneLocation="";//到达手机位置
				String startVehicleLocation="";//出发车辆位置
				String endVehicleLocation="";//到达车辆位置
				String effectiveMileage="";//有效里程
				QuerySet qs1 =dao.getClaimOutGps(claimId);
				if(qs1.getRowCount() > 0 ){
					for (int i=0;i<qs1.getRowCount() ; i++){
						String operateStatus=qs1.getString(i+1, "OPERATE_STATUS");//1是出发 2是到达
						if("1".equals(operateStatus)){
							startPhoneLongitude=qs1.getString(i+1,"PHONE_LONGITUDE" );
							startPhonelatitude=qs1.getString(i+1,"PHONE_LATITUDE");
							startVehicleLongitude=qs1.getString(i+1, "VEHICLE_LONGITUDE");
							startVehiclelatitude=qs1.getString(i+1, "VEHICLE_LATITUDE");
							vehiclePhoneMileage=qs1.getString(i+1,"VEHICLE_PHONE_MILEAGE");
							startPhoneLocation=qs1.getString(i+1, "PHONE_LOCATION");
							startVehicleLocation=qs1.getString(i+1,"VEHICLE_LOCATION");
						}
						if("2".equals(operateStatus)){
							endPhoneLongitude=qs1.getString(i+1,"PHONE_LONGITUDE");
							endPhonelatitude=qs1.getString(i+1, "PHONE_LATITUDE");
							endVehicleLongitude=qs1.getString(i+1,"VEHICLE_LONGITUDE");
							endVehiclelatitude=qs1.getString(i+1,"VEHICLE_LATITUDE");
							endPhoneLocation=qs1.getString(i+1, "PHONE_LOCATION");
							endVehicleLocation=qs1.getString(i+1,"VEHICLE_LOCATION");
							effectiveMileage=qs1.getString(i+1,"EFFECTIVE_MILEAGE");
						}
					}
					//手机和车 出发经纬度 
					vo.setStartPhoneLatitude(startPhonelatitude);
					vo.setStartPhoneLocation(startPhoneLocation);
					vo.setStartPhoneLongitude(startPhoneLongitude);
					vo.setStartVehicleLatitude(startVehiclelatitude);
					vo.setStartVehicleLongitude(startVehicleLongitude);
					vo.setStartVehicleLocation(startVehicleLocation);
					vo.setVehiclePhoneMileage(vehiclePhoneMileage);
					
					//到达车辆的 经纬度
					vo.setEndPhoneLatitude(endPhonelatitude);
					vo.setEndPhoneLongitude(endPhoneLongitude);
					vo.setEndPhoneLocation(endPhoneLocation);
					
					vo.setEndVehicleLatitude(endVehiclelatitude);
					vo.setEndVehicleLongitude(endVehicleLongitude);
					vo.setEndVehicleLocation(endVehicleLocation);
					vo.setGpsMileage(effectiveMileage); //GPS返回有效里程
				}	
			}
			//outId等于0 新增 否则 修改
			if("0".equals(outId)){
				vo.setOutId("");
				vo.setCreateUser(user.getAccount());
				vo.setCreateTime(Pub.getCurrentDate());
				dao.insertClaimOut(vo);
			}else{
				vo.setUpdateUser(user.getAccount());
				vo.setUpdateTime(Pub.getCurrentDate());
				vo.setOutId(outId);
				dao.updateClaimOut(vo);
			}
			
			voExt.setOutId(vo.getOutId());
			// 更新索赔单外出费用
			String outAmount = hm.get("COST_AMOUNT");
			SeBuClaimVO claimVo = new SeBuClaimVO();
			claimVo.setClaimId(claimId);
			claimVo.setOutAmount(outAmount);
			claimVo.setUpdateUser(user.getAccount());
			claimVo.setUpdateTime(Pub.getCurrentDate());
			claimVo.setSeType(seType);
			dao.updateClaim(claimVo);
			voExt.bindFieldToDic("SE_TYPE", "FWLX");
			voExt.bindFieldToDic("CLAIM_STATUS", "SPDZT");
			voExt.setFieldDateFormat("GO_DATE", "yyyy-MM-dd HH:mm");
			voExt.setFieldDateFormat("ARRIVE_DATE", "yyyy-MM-dd HH:mm");
			voExt.setFieldDateFormat("LEAVE_DATE", "yyyy-MM-dd HH:mm");
			atx.setOutMsg(voExt.getRowXml(), "保存外出费用成功");
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: updateOut
	 * @description: TODO(更新外出费用)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	public void updateOut() throws Exception {
		RequestWrapper request = atx.getRequest();
		String claimId = Pub.val(request, "claimId");
		String seType = Pub.val(request, "seType");
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		HashMap<String, String> hm;
		hm = RequestUtil.getValues(request);
		try {
			String claimNo = "";
			String claimStatus = "";
			QuerySet qs = dao.getClaimInfo(claimId);
			if (qs.getRowCount() > 0) {
				claimNo = qs.getString(1, "CLAIM_NO");
				claimStatus = qs.getString(1, "CLAIM_STATUS");
			}
			// 插入外出信息
			SeBuClaimOutVO vo = new SeBuClaimOutVO();
			SeBuClaimOutVO_Ext voExt = new SeBuClaimOutVO_Ext();
			vo.setValue(hm);
			vo.setClaimId(claimId);
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			dao.updateClaimOut(vo);
			// 更新索赔单外出费用
			String outAmount = hm.get("COST_AMOUNT");
			SeBuClaimVO claimVo = new SeBuClaimVO();
			claimVo.setClaimId(claimId);
			claimVo.setOutAmount(outAmount);
			claimVo.setUpdateUser(user.getAccount());
			claimVo.setUpdateTime(Pub.getCurrentDate());
			claimVo.setSeType(seType);
			dao.updateClaim(claimVo);
			voExt.setValue(hm);
			voExt.setClaimId(claimId);
			voExt.setSeType(seType);
			voExt.setClaimNo(claimNo);
			voExt.setOutId(vo.getOutId());
			voExt.setClaimStatus(claimStatus);
			voExt.bindFieldToDic("SE_TYPE", "FWLX");
			voExt.bindFieldToDic("SE_TYPE", "FWLX");
			voExt.bindFieldToDic("CLAIM_STATUS", "SPDZT");
			voExt.setFieldDateFormat("GO_DATE", "yyyy-MM-dd HH:mm");
			voExt.setFieldDateFormat("ARRIVE_DATE", "yyyy-MM-dd HH:mm");
			voExt.setFieldDateFormat("LEAVE_DATE", "yyyy-MM-dd HH:mm");
			atx.setOutMsg(voExt.getRowXml(), "保存外出费用成功");

		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * @title: reportClaim
	 * @description: TODO(索赔单提报)
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void reportClaim() throws Exception {
		RequestWrapper request = atx.getRequest();
		String claimId = Pub.val(request, "claimId");
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		try {
			// 先判断是否有故障
			// 如果有故障，判断故障下有配件，没有配件不能提报
			QuerySet qsGz = dao.getClaimFault(claimId);
			if (qsGz.getRowCount() > 0) {
				int count = Integer.parseInt(qsGz.getString(1, "FAU_COUNT"));
				if (count > 0) {
					atx.setOutMsg("4", "请检查故障模式下必须有主损件.");
					return;
				}
			}
			UserParaConfigureVO userVo= (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("201801");
        	double costs = 30000;
        	if(userVo != null){
        		costs=Double.parseDouble(userVo.getParavalue1());// 业务参数表 单车最大材料费
        	}else{
        		costs= 30000;
        	}
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			// 提报前先判断一下索赔单的类型
			// 定保单 ，修改车辆表定保次数
			SeBuClaimVO vo = new SeBuClaimVO();
			vo.setClaimId(claimId);
			QuerySet qs1 = dao.queryClaimInfoById(claimId);
			Map map = new HashMap();
			String vehicleId = "";
			String preAuthorId="";//预授权ID
			String mileage = "";
			String workId = "";
			String userType="";
			int applyCount = 0;// 提报次数
			int flag = 0;// 标识 1是已经首保，2是已经安全检查，3是已经售前培训检查
			int gCount= 0;//定保次数
			double mainCost = 0;// 首保总费用
			double engineCost = 0;// 发动机费用
			double gearboxCost = 0;// 变速箱费用
			double bridgeCost = 0;// 桥费用
			String sgwxFlag = "0";// 类型 ：事故车维修 不走审核 直接审核通过
			//String flagCheck = "0";// 首保、定保、售前检查培训、安全检查,事故车维修不用转人工审核 flagCheck=1
									// 转人工审核flagCheck=0
			String flagMaterialCosts = "0";// 计算材料总费用
			String flagCosts="0";  //单车材料费标识，只有正常保修的 校验
			String operateUser = "0";// 是否绑定审核人 0是未绑定
			if (qs1 != null && qs1.getRowCount() > 0) {
				String claimType = qs1.getString(1, "CLAIM_TYPE"); //索赔单类型
				vehicleId = qs1.getString(1, "VEHICLE_ID"); //车辆ID
				String activityId = qs1.getString(1, "ACTIVITY_ID"); //活动ID
				String maintenanceDate = qs1.getString(1, "MAINTENANCE_DATE");// 首保日期
				mileage = qs1.getString(1, "MILEAGE");// 行驶里程
				preAuthorId = qs1.getString(1, "PRE_AUTHOR_ID");// 预授权ID
				workId = qs1.getString(1, "WORK_ID");// 工单ID
				Integer type = new Integer(claimType); 
				applyCount = Integer.parseInt(qs1.getString(1, "APPLY_COUNT"));// 提报次数
				String v_sbrq=qs1.getString(1, "V_SBRQ");//车辆表首保日期
				mainCost = Double.parseDouble(qs1.getString(1,"MAINTENANCE_COSTS"));// 首保总费用
				int safeCheckTimes = Integer.parseInt(qs1.getString(1,"SAFECHECKTIMES"));// 安全检查次数
				int trainTimes = Integer.parseInt(qs1.getString(1, "TRAINTIMES"));// 售前检查次数
				gCount = Integer.parseInt(qs1.getString(1, "G_COUNT"));// 定保次数
				operateUser = qs1.getString(1, "OPERATE_USER"); // 审单人
				userType  = qs1.getString(1, "USER_TYPE"); //用户类型
				switch (type.intValue()) {
				case 301401://正常保修 只有民用的车辆 单车材料费大于一定金额 选一个预授权
					if(DicConstant.CLYHLX_01.equals(userType)){
						flagCosts="1";
					}
					break;
				case 301402: // 首保
					map.put("maintenanceDate", maintenanceDate);// 将首保费用拆分成各个子项,更新车辆表首保日期
					map.put("fmaintainflag", "100101");
					map.put("deunkm", mileage);
					//如果报首保了，直接提示
					if(v_sbrq!=null &&!"".equals(v_sbrq)){ 
						flag=1; 
					}
					QuerySet qs2 = dao.getMianPara();
					if (qs2.getRowCount() > 0) {
						engineCost = Double.parseDouble(qs2.getString(1,"ENGINE"));
						gearboxCost = Double.parseDouble(qs2.getString(1,"GEARBOX"));
						bridgeCost = mainCost - engineCost - gearboxCost;
					}
					//flagCheck = "1";
					break;
				case 301403: // 服务活动
					dao.updateActivityVehicle(user, activityId, vehicleId);// 更新服务活动中的车辆使用情况
					break;
				case 301404: //售前维修
					//flagCheck = "1";
					break;
				case 301405: // 定保
					map.put("dbcs", "1");// 更新车辆定保次数
					//flagCheck = "1";
					gCount = gCount+1;
					vo.setGTimes(String.valueOf(gCount));
					break;
				case 301406: // 售前培训检查
					map.put("sqjccs", "1");// 更新售前培训检查
					if (trainTimes >= 1) {  //安全检查次数大于等于1不可以提报
						flag = 3;
					}
					//flagCheck = "1";
					break;
				case 301407: // 安全检查
					map.put("aqjccs", "1");// 更新安全检查次数
					if (safeCheckTimes >= 2) { //安全检查次数大于等于2不可以提报
						flag = 2;
					}
					//flagCheck = "1";
					break;
				case 301408: // 照顾性保修(预授权)
					dao.updatePreAuthorId(user, preAuthorId);// 照顾性保修更新预售权使用情况
					break;
				case 301409: // 事故车维修
					//flagCheck = "1";
					sgwxFlag = "1";
					flagMaterialCosts = "1";
					break;
				}

			}else{
				throw new Exception("请先保存维修信息,再提报.");
			}
			// 首保、售前培训检查 一次、安全检查每车只能做2次
			
			if(flag==1){ 
				atx.setOutMsg("1","该车已经做过首保.");
				return; 
			}else if (flag == 2) {
				atx.setOutMsg("2", "该车已经做过安全检查.");
				return;
			} else if (flag == 3) {
				atx.setOutMsg("3", "该车已经做过售前培训检查.");
				return;
			}
			applyCount = applyCount + 1;
			// 更新车辆表
			map.put("mileage", mileage);
			dao.updateVehicleMaintenanceDate(user, vehicleId, map);
			// 更新故障配件的祸首件信息
			dao.updateFaultFirstPart(claimId);
			// 更新索赔单表(事故车维修 ，提报上来直接人工审核通过)
			if (sgwxFlag.equals("1")) {
				vo.setClaimStatus(DicConstant.SPDZT_05);
			} else {
				vo.setClaimStatus(DicConstant.SPDZT_02);
			}
			// 计算总材料费
			String claimCosts = "0";
			if ("0".equals(flagMaterialCosts)) {
				QuerySet qsCost = dao.getMatericlCosts(claimId);
				if (qsCost.getRowCount() > 0) {
					claimCosts = qsCost.getString(1, 1);
				}
			}
			//正常保修单车材料费 大于一定金额，选择预授权
			if("1".equals(flagCosts)){
				double claimCostsD=Double.valueOf(claimCosts); //将总材料费转成 double型
				if(claimCostsD > costs){
					if(preAuthorId ==null || "".equals(preAuthorId)){
						atx.setOutMsg("7", "单车材料费大于"+costs+"请选择预授权.");
						return;
					}else{
						dao.updatePreAuthorId(user, preAuthorId);// 照顾性保修更新预售权使用情况
					}
				}else{
					vo.setIfPreAuthor("");//正常保修 ，没有预授权的将预授权清空
					vo.setPreAuthorId("");//预授权ID
				}
			}
			vo.setValue(hm);
			vo.setApplyDate(Pub.getCurrentDate());
			vo.setUpdateTime(Pub.getCurrentDate());
			vo.setUpdateUser(user.getAccount());
			vo.setMaterialCosts(claimCosts);// 材料总费用
			vo.setApplyCount(String.valueOf(applyCount));// 提报次数
			vo.setEngineCost(String.valueOf(engineCost));// 发动机费用
			vo.setGearboxCost(String.valueOf(gearboxCost));// 变速箱费用
			vo.setBridgeCost(String.valueOf(bridgeCost));// 桥费用
			//vo.setClaimIdentityStatus(DicConstant.SPDLCZT_02);//索赔单流程状态（）
			dao.updateClaim(vo);
			SeBuClaimCheckVO checkVo = new SeBuClaimCheckVO();
			checkVo.setClaimId(claimId);
			checkVo.setCheckUser(user.getAccount());
			checkVo.setCheckResult(DicConstant.SPDZT_02);
			checkVo.setCheckDate(Pub.getCurrentDate());
			checkVo.setCreateTime(Pub.getCurrentDate());
			checkVo.setCreateUser(user.getAccount());
			checkVo.setOemCompanyId(user.getOemCompanyId());
			dao.insertCheck(checkVo);
			// 更新工单状态
			SeBuWorkOrderVO workVo = new SeBuWorkOrderVO();
			workVo.setWorkId(workId);
			workVo.setUpdateTime(Pub.getCurrentDate());
			workVo.setUpdateUser(user.getAccount());
			workVo.setWorkStatus(DicConstant.PGDZT_04);
			dao.updateWorkOrder(workVo);

			// 索赔单类型为：首保、定保、售前检查培训、安全检查,事故车维修不用转人工审核 flagCheck=1 否则flagCheck=0
			//if (flagCheck.equals("0")) {
				// 判断该单是否有审核员 不等于0是有审核员
				if (operateUser.equals("0")) {
					SeBuClaimVO voClaimVO = new SeBuClaimVO();
					QuerySet dealerQs = dao.getDealerCheck(user);// 获取是否需要人工审核
					String ifAutoCheck = "0";//服务站是否需要审核
					int seqNoMax = 0;// 有效最大序号
					int seqNoCur = 0;// 当前序号
					int paras = 0;// 参数
					String account = "";
					String now = "";
					if (dealerQs.getRowCount() > 0) {
						ifAutoCheck = dealerQs.getString(1, "IF_AUTO_CHECK");
					}
					if (ifAutoCheck.equals(DicConstant.SF_02.toString())|| ifAutoCheck.equals("0")) {
						//军车绑定审核员
						if(userType.equals(DicConstant.CLYHLX_02.toString())){
							QuerySet userNowQs2 = dao.getUserMilitaryNow();// 获取当前军车序号，有效最大序号
							if (userNowQs2.getRowCount() > 0) {
								if("".equals(userNowQs2.getString(1,"SEQ_NO_MAX")) || userNowQs2.getString(1,"SEQ_NO_MAX") == null){
									atx.setOutMsg("5", "联系系统管理员，维护军车审核员.");
									return;
								}
								seqNoMax = Integer.parseInt(userNowQs2.getString(1,"SEQ_NO_MAX"));
								seqNoCur = Integer.parseInt(userNowQs2.getString(1,"SEQ_NO_CUR"));
							}
						} else {
							//民车绑定审核员
							QuerySet userNowQs = dao.getUserNow();// 获取当前民车序号，有效最大序号
							if (userNowQs.getRowCount() > 0) {
								if("".equals(userNowQs.getString(1,"SEQ_NO_MAX")) || userNowQs.getString(1,"SEQ_NO_MAX") == null){
									atx.setOutMsg("6", "联系系统管理员，维护民车审核员.");
									return;
								}
								seqNoMax = Integer.parseInt(userNowQs.getString(1,"SEQ_NO_MAX"));
								seqNoCur = Integer.parseInt(userNowQs.getString(1,"SEQ_NO_CUR"));
							}
						}
						if (seqNoCur >= seqNoMax) {
							paras = 0;
						} else {
							paras = seqNoCur;
						}
						QuerySet userCheckQs = dao.getUserAccount(paras,userType);//审核人序号和帐号
						//QuerySet userCheckQs = dao.getUserAccount(paras);//审核人序号和帐号
						if (userCheckQs.getRowCount() > 0) {
							account = userCheckQs.getString(1, "USER_ACCOUNT");
							now = userCheckQs.getString(1, "SEQ_NO");
						}	
						voClaimVO.setClaimId(claimId);
						voClaimVO.setOperateUser(account);
						dao.updateClaim(voClaimVO);
						//民用车需更新 当前序号
						if(userType.equals(DicConstant.CLYHLX_01.toString())){
							dao.updateNo(now);
						}
					}
				}
			//}
			saleStock(claimId);
			atx.setOutMsg(vo.getRowXml(), "索赔单提报成功.");
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * 生成实销出库单与外采申请单
	 */
	public void saleStock(String claimId) throws Exception{
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String flag="0";
		QuerySet qss= dao.queryIfStock(claimId);
		String saleId=null;
		QuerySet queryInfos= dao.queryUsersInfos(claimId);
		String userName=queryInfos.getString(1, "USER_NAME");
		String userAddress=queryInfos.getString(1, "USER_ADDRESS");
		String phone=queryInfos.getString(1, "PHONE");
		if(qss.getRowCount()>0){//如果查询结果大于0说明至少有一个件的可用库存是大于0的
			//生成实销单			    那么就生成实销单
			 PtBuRealSaleVO sVo =new PtBuRealSaleVO();//实销表VO
			 sVo.setCustomerName(userName);
			 sVo.setLinkPhone(phone);
			 sVo.setClaimId(claimId);
			 sVo.setLinkAddr(userAddress);
			 sVo.setSaleNo(PartOddNumberUtil.getRealSaleOutOrderNo(atx.getDBFactory(), user.getOrgDept().getOrgType(),user.getOrgCode()));
			 sVo.setCompanyId(user.getCompanyId());
			 sVo.setOrgId(user.getOrgId());
			 sVo.setOemCompanyId(user.getOemCompanyId());
			 sVo.setSaleDate(Pub.getCurrentDate());
			 sVo.setCreateUser(user.getAccount());
			 sVo.setCreateTime(Pub.getCurrentDate());
			 sVo.setSaleStatus(DicConstant.SXDZT_01);
			 sVo.setStatus(Constant.YXBS_01);
			 sVo.setOutType(DicConstant.SXLX_02);
			 dao.insertRealSale(sVo);//生成实销单
			 saleId=sVo.getSaleId();//通过VO方式取回刚新增的实销ID
		}
		 //新增外采申请单
		 PtBuOutBuyVO outVo=new PtBuOutBuyVO();
	 	 outVo.setCustomerName(userName);
	 	 outVo.setClaimId(claimId);
	 	 outVo.setLinkPhone(phone);
	 	 outVo.setLinkAddr(userAddress);
	  	 outVo.setBuyNo(PartOddNumberUtil.getBuyOrderNo(atx.getDBFactory(), user.getOrgDept().getOrgType(),user.getOrgCode()));
	 	 outVo.setCompanyId(user.getCompanyId());
	 	 outVo.setOrgId(user.getOrgId());
	 	 outVo.setOemCompanyId(user.getOemCompanyId());
	 	 outVo.setBuyDate(Pub.getCurrentDate());
	 	 outVo.setCreateUser(user.getAccount());
	 	 outVo.setCreateTime(Pub.getCurrentDate());
	 	 outVo.setStatus(Constant.YXBS_02);
		 dao.insertBuyOrder(outVo);//生成外采单
		 String buyId=outVo.getBuyId();//通过VO方式取回刚新增的外采单ID
		 QuerySet qsp = dao.queryParts(claimId);//查询索赔单下所有配件ID，供应商ID，配件数量。
		if(qsp.getRowCount()>0){
			for(int i=0; i<qsp.getRowCount();i++){//遍历所有新件
				String aAmount=null;
				String oAmount=null;
				String stockId=null;
				String unit=null;
				String retailPrice=null;
				String nSupplierCode=null;
				String nSupplierName=null;
				
				String nPartId=qsp.getString(i+1, "NEW_PART_ID");//新件ID
				String nPartCode=qsp.getString(i+1, "NEW_PART_CODE");//新件CODE
				String nPartName=qsp.getString(i+1, "NEW_PART_NAME");//新件NAME
				String nSupplierId=qsp.getString(i+1, "NEW_SUPPLIER_ID");//新件NAME
				//String nSupplierId = String.valueOf("2014090300052510");// 新件供应商ID
				String nPartCount=qsp.getString(i+1, "NEW_PART_COUNT");//新件数量
				
				if(null==nPartId||nPartId.equals("")){
					//没有产生新件的跳过生成外销单或者申请单步骤。
				}else{
					QuerySet qs =dao.queryIfOil(nPartId);
					if(qs.getRowCount()>0){
						//判断是否油品,如果是油品，不插入实销单明细。
					}else{
					QuerySet qsSup=dao.querySupplier(nSupplierId);
					if(qsSup.getRowCount()>0){
					    nSupplierCode=qsSup.getString(1, "SUPPLIER_CODE");//新件供应商CODE
						nSupplierName=qsSup.getString(1, "SUPPLIER_NAME");//新件供应商NAME
					}
					
					QuerySet qsStock= dao.queryStock(nPartId,user);
					if(qsStock.getRowCount()>0){
					    aAmount=qsStock.getString(1, "AVAILABLE_AMOUNT");//查询所有配件的可用库存
						oAmount=qsStock.getString(1, "OCCUPY_AMOUNT");//查询所有配件的占用库存
						stockId=qsStock.getString(1, "STOCK_ID");//查询所有配件的库存ID
					}else{
						aAmount="0";
						oAmount="0";
						stockId="anull";
					}
					//查询配件对应的 单位，零售价
					QuerySet qsje=dao.queryPartInfos(nPartId);
					if(qsje.getRowCount()>0){
						unit=qsje.getString(1, "UNIT");//旧件单位
						retailPrice=qsje.getString(1, "RETAIL_PRICE");//旧件零售价
					}
					//如果库存满足，生成实销单
					if(Integer.parseInt(aAmount)-Integer.parseInt(nPartCount)>=0)
					{
			            PtBuDealerStockVO pbsVo =new PtBuDealerStockVO();//库存表VO
			            PtBuRealSaleDtlVO dVo =new PtBuRealSaleDtlVO();//实销明细表VO
			            	//新增实销单明细
				            dVo.setSaleId(saleId);
				            dVo.setPartId(nPartId);
				            dVo.setPartCode(nPartCode);
				            dVo.setPartName(nPartName);
				            dVo.setSalePrice(retailPrice);
				            dVo.setSaleCount(nPartCount);
				            dVo.setUnit(unit);
				            dVo.setSupplierId(nSupplierId);
				            dVo.setSupplierCode(nSupplierCode);
				            dVo.setSupplierName(nSupplierName);
				            
	                        Double price =Double.parseDouble(retailPrice.toString());
	                        int saleCount =Integer.parseInt(nPartCount);
	                        Double amount =price*saleCount;
	                        dVo.setAmount(amount.toString());
	                        dVo.setCreateTime(Pub.getCurrentDate());
	                        dVo.setCreateUser(user.getAccount());
	                     
	                     	QuerySet qsCount=dao.queryCount(saleId);
				            int count=Integer.parseInt(qsCount.getString(1, "SALE_COUNT"))+Integer.parseInt(nPartCount);
				            Double saleAmount=Double.parseDouble(qsCount.getString(1, "SALE_AMOUNT"))+amount;
				           
				            PtBuRealSaleVO sVo1 =new PtBuRealSaleVO();//实销表VO
				            sVo1.setSaleId(saleId);
				            sVo1.setSaleCount(String.valueOf(count));
				            sVo1.setSaleAmount(String.valueOf(saleAmount));
				            dao.updateSale(sVo1);
		                     //对库存更新
		                    Double avaiamount =Double.parseDouble(aAmount.toString())-saleCount;
		                    Double occupyamount =Double.parseDouble(oAmount.toString())+saleCount;
		                    pbsVo.setAvailableAmount(avaiamount.toString());
		                    pbsVo.setOccupyAmount(occupyamount.toString());
		                    pbsVo.setUpdateTime(Pub.getCurrentDate());
		                    pbsVo.setUpdateUser(user.getAccount());
		                    pbsVo.setStockId(stockId);
		                    dao.DealerStockUpdate(pbsVo);
		                    dao.realSaleDtlInsert(dVo);
		                    String url="库存全部满足,将新件数量占用。/action/service/claimmng/WorkOrderMngAction/saleStock.ajax";
		                    dao.insetStockDtl(saleCount,user,saleId,url,nPartId);//插入库存变化明细。
						}else{
							 int syCount =	Integer.parseInt(nPartCount)-Integer.parseInt(aAmount);//不满足数量
							 //库存不满足，首先生成外采申请单，主表信息。
							
						 	 PtBuOutBuyDtlVO dtlVo=new PtBuOutBuyDtlVO();
			                     if(stockId=="anull"){//如果仓库ID为空，则这条配件数量全部生成外采单明细
			                    	 dtlVo.setBuyId(buyId);
									 dtlVo.setPartId(nPartId);
									 dtlVo.setPartCode(nPartCode);
									 dtlVo.setPartName(nPartName);
									 dtlVo.setBuyPrice(retailPrice);
									 dtlVo.setBuyCount(String.valueOf(syCount));
									 dtlVo.setUnit(unit);
									 dtlVo.setSupplierId(nSupplierId);
									 dtlVo.setSupplierCode(nSupplierCode);
									 dtlVo.setSupplierName(nSupplierName);
						             
									 Double price =Double.parseDouble(retailPrice.toString());
				                     Double syAmount =price*syCount;
				                     dtlVo.setAmount(syAmount.toString());
				                     dtlVo.setCreateTime(Pub.getCurrentDate());
				                     dtlVo.setCreateUser(user.getAccount());
				                     dao.insertBuyOrderDtl(dtlVo);
				                     
				                     QuerySet qsCount=dao.queryOutCount(buyId);
						             int count=Integer.parseInt(qsCount.getString(1, "BUY_COUNT"))+Integer.parseInt(nPartCount);
						             Double buyAmount=Double.parseDouble(qsCount.getString(1, "BUY_AMOUNT"))+syAmount;
						             PtBuOutBuyVO outVo1=new PtBuOutBuyVO();//外采单表VO
						             outVo1.setBuyId(buyId);
						             outVo1.setBuyCount(String.valueOf(count));
						             outVo1.setBuyAmount(String.valueOf(buyAmount));
						             outVo1.setBuyStatus(DicConstant.SF_02);//已保存。
						             outVo1.setStatus(Constant.YXBS_01);
						             dao.updateBuy(outVo1);
						             flag="1";
				                     //因库存ID不存在，不需要操作库存。
			                     }else{
			                    	 //仓库库存ID不为空
		                    	     PtBuRealSaleDtlVO dVo =new PtBuRealSaleDtlVO();//实销明细表VO
						             PtBuDealerStockVO pbsVo =new PtBuDealerStockVO();//库存表VO
						             //库存不满足的情况下，将满足的件新增到明细单中，剩余的生成申请单。
						             if(Integer.parseInt(aAmount)>0){
						            	 //新增实销单明细
							             dVo.setSaleId(saleId);
							             dVo.setPartId(nPartId);
							             dVo.setPartCode(nPartCode);
							             dVo.setPartName(nPartName);
							             dVo.setSalePrice(retailPrice);
							             dVo.setSaleCount(aAmount);
							             dVo.setUnit(unit);
							             dVo.setSupplierId(nSupplierId);
							             dVo.setSupplierCode(nSupplierCode);
							             dVo.setSupplierName(nSupplierName);
							             
					                     Double price =Double.parseDouble(retailPrice.toString());
					                     int aCount =Integer.parseInt(aAmount);
					                     Double aMount =price*aCount;
					                     dVo.setAmount(aMount.toString());
					                     dVo.setCreateTime(Pub.getCurrentDate());
					                     dVo.setCreateUser(user.getAccount());
					                     
				                        QuerySet qsCount=dao.queryCount(saleId);
							            int count=Integer.parseInt(qsCount.getString(1, "SALE_COUNT"))+aCount;
							            Double saleAmount=Double.parseDouble(qsCount.getString(1, "SALE_AMOUNT"))+aMount;
							            
							            PtBuRealSaleVO sVo1 =new PtBuRealSaleVO();//实销表VO
							            sVo1.setSaleId(saleId);
							            sVo1.setSaleCount(String.valueOf(count));
							            sVo1.setSaleAmount(String.valueOf(saleAmount));
							            dao.updateSale(sVo1);
					                     
					                     //对库存更新
					                    Double avaiamount =Double.parseDouble(aAmount.toString())-aCount;
					                    Double occupyamount =Double.parseDouble(oAmount.toString())+aCount;
					                    pbsVo.setAvailableAmount(avaiamount.toString());
					                    pbsVo.setOccupyAmount(occupyamount.toString());
					                    pbsVo.setUpdateTime(Pub.getCurrentDate());
					                    pbsVo.setUpdateUser(user.getAccount());
					                    pbsVo.setStockId(stockId);
					                    dao.DealerStockUpdate(pbsVo);
					                    dao.realSaleDtlInsert(dVo);//将满足的库存占用 
					                    
					                    String url="库存部分满足,将满足部分占用。/action/service/claimmng/WorkOrderMngAction/saleStock.ajax";
					                    dao.insetStockDtl(aCount,user,saleId,url,nPartId);//插入库存变化明细。
						             }
									 	//将不满足的部分生成外采申请单
				                     	dtlVo.setBuyId(buyId);
				                     	dtlVo.setPartId(nPartId);
				                     	dtlVo.setPartCode(nPartCode);
				                     	dtlVo.setPartName(nPartName);
				                     	dtlVo.setBuyPrice(retailPrice);
									 	dtlVo.setBuyCount(String.valueOf(syCount));
									 	dtlVo.setUnit(unit);
									 	dtlVo.setSupplierId(nSupplierId);
									 	dtlVo.setSupplierCode(nSupplierCode);
									 	dtlVo.setSupplierName(nSupplierName);
						           
									 	Double syprice =Double.parseDouble(retailPrice.toString());
									 	Double syAmount =syprice*syCount;//不满足的配件数量金额。
									 	dtlVo.setAmount(syAmount.toString());
									 	dtlVo.setCreateTime(Pub.getCurrentDate());
									 	dtlVo.setCreateUser(user.getAccount());
									 	dao.insertBuyOrderDtl(dtlVo);
				                     
									 	QuerySet qsCount=dao.queryOutCount(buyId);
									 	int count=Integer.parseInt(qsCount.getString(1, "BUY_COUNT"))+syCount;
									 	Double buyAmount=Double.parseDouble(qsCount.getString(1, "BUY_AMOUNT"))+syAmount;
									 	PtBuOutBuyVO outVo1=new PtBuOutBuyVO();//外采单表VO
									 	outVo1.setBuyId(buyId);
									 	outVo1.setBuyCount(String.valueOf(count));
									 	outVo1.setBuyAmount(String.valueOf(buyAmount));
									 	outVo1.setBuyStatus(DicConstant.SF_02);//已保存。
									 	outVo1.setStatus(Constant.YXBS_01);
									 	dao.updateBuy(outVo1);
									 	flag="1";
			                     }
							}
						}
					}
				}
			}
			if(flag=="1"){
				SeBuClaimVO vo =new SeBuClaimVO();
		        vo.setClaimId(claimId);
		        vo.setStockMeet(DicConstant.SF_02);
		        dao.updateClaim(vo);
		        atx.setOutMsg("1", "库存不足已生成外采申请单。");
			}else{
				dao.deleteOutBuy(buyId);
			}
	}
	
    public void outBuyPartSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		String claimId = Pub.val(request, "claimId");
		try
		{
			BaseResultSet bs = dao.outBuyPartSearch(page,user,claimId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * 查询附件
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
	 * 外出信息
	 * 
	 * @throws Exception
	 */
	public void searchClaimOut() throws Exception {
		RequestWrapper request = atx.getRequest();
		String claimId = Pub.val(request, "claimId");
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			page.setPageRows(100);
			BaseResultSet bs = dao.searchClaimOut(page, user, conditions,
					claimId);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 获取有效里程
	 * @throws Exception
	 */
	public void searchClaimOutMileage()throws Exception{
		RequestWrapper request = atx.getRequest();
		String workNo = Pub.val(request, "workNo");
		PageManager page = new PageManager();
		try {
			BaseResultSet bs = dao.searchClaimOutMileage(workNo,page);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 索赔单提报查询
	 * 
	 * @throws Exception
	 */
	public void searchClaimApply() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.searchClaimApply(page, user, conditions);
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
	 * 删除索赔单 索赔单类型 为照顾性保修、服务活动 将更新未使用,如果是 售前、安全检查 更新车辆表售前、安全检查次数 如果是定保 应该减一
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void deleteClaim() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String claimId = Pub.val(request, "claimId");// 索赔单ID
		try {
			// 查询索赔单信息
			QuerySet qs1 = dao.queryClaimInfoById(claimId);
			Map map = new HashMap();
			String vehicleId = "";
			String workId ="";
			if (qs1 != null && qs1.getRowCount() > 0) {
				String claimType = qs1.getString(1, "CLAIM_TYPE");
				vehicleId = qs1.getString(1, "VEHICLE_ID");
				String activityId = qs1.getString(1, "ACTIVITY_ID");// 活动ID
				String preAuthorId = qs1.getString(1, "PRE_AUTHOR_ID");// 预授权ID
				workId=qs1.getString(1,"WORK_ID");//工单ID
				Integer type = new Integer(claimType);
				switch (type.intValue()) {
				case 301402:// 首保
					map.put("sbxx","1");
					break;
				case 301403:// 服务活动
					dao.updateActivity(activityId, user,vehicleId);// 更新服务活动未使用
					break;
				case 301405:// 定保次数
					map.put("dbcs", "1");// 更新车辆定保0次
					break;
				case 301406:// 售前培训检查
					map.put("sqjccs", "1");
					;// 更新售前培训检查0次
					break;
				case 301407:// 安全检查
					map.put("aqjccs", "1");
					;// 更新安全检查次数0次
					break;
				case 301408:// 照顾性保修
					dao.updatePreAuthor(preAuthorId, user);// 照顾性保修更新预售权未使用
					break;
				}

			}
			// 更新车辆信息  
			//注释原因 ： 车辆表的数据都在索赔单驳回的时候更新完了
			//dao.updateVehicleCount(user, vehicleId, map);
			/*
			 * //删除外出费用 dao.deleteOut(claimId); //删除其它费用
			 * dao.deleteotherCost(claimId); //删除故障配件信息 dao.deletePart(claimId);
			 * //删除故障信息 dao.deleteFault(claimId);
			 */
			// 删除索赔单信息
			dao.deleteClaim(claimId, user);
			//放弃派工
			dao.updateWork(workId, user);
			atx.setOutMsg("", "删除成功.");
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		}
	}

	/**
	 * 作废
	 * 
	 * @throws Exception
	 */
	public void deleteWorkOrder() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String claimId = Pub.val(request, "claimId");// 索赔单ID
		String workId = Pub.val(request, "workId");// 工单ID
		try {
			// 更新索赔单无效
			SeBuClaimVO vo = new SeBuClaimVO();
			vo.setClaimId(claimId);
			vo.setWorkId("");
			vo.setStatus(DicConstant.YXBS_02);// 无效
			vo.setUpdateTime(Pub.getCurrentDate());
			vo.setUpdateUser(user.getAccount());
			dao.updateClaim(vo);
			// 更新工单
			SeBuWorkOrderVO orderVO = new SeBuWorkOrderVO();
			orderVO.setWorkId(workId);
			orderVO.setWorkStatus(DicConstant.PGDZT_03);// 工单完成
			orderVO.setUpdateTime(Pub.getCurrentDate());
			orderVO.setUpdateUser(user.getAccount());
			dao.updateWorkOrder(orderVO);
			atx.setOutMsg("", "作废成功.");
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		}
	}
	
	/***
	 * 保存预授权信息
	 * @throws Exception
	 */
	public void updateClaimPreauth()throws Exception{
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String claimId = Pub.val(request, "claimId");// 索赔单ID
		String preauthorId = Pub.val(request, "preauthorId");// 预授权ID
		try {
			//保存预授权信息
			SeBuClaimVO vo = new SeBuClaimVO();
			vo.setPreAuthorId(preauthorId);
			vo.setClaimId(claimId);
			vo.setIfPreAuthor(DicConstant.SF_01.toString());//是否预授权
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			dao.updateClaim(vo);
			atx.setOutMsg("", "");
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		}
	}
	/***
	 * 保存活动信息
	 * @throws Exception
	 */
	public void updateClaimActivity()throws Exception{
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String claimId = Pub.val(request, "claimId");// 索赔单ID
		String activityId = Pub.val(request, "activityId");// 活动ID
		String ifFixcosts = Pub.val(request, "ifFixcosts");// 是否固定费用
		String ifClaim = Pub.val(request, "ifClaim");// 是否索赔
		String activityCosts = Pub.val(request, "activityCosts");// 活动费用
		String manageType = Pub.val(request, "manageType");//处理方式
		try {
			//保存服务活动信息
			SeBuClaimVO vo = new SeBuClaimVO();
			vo.setClaimId(claimId);
			vo.setActivityId(activityId);
			vo.setIfFixed(ifFixcosts);
			vo.setServiceCost(activityCosts);
			vo.setIfRecovery(ifClaim);
			vo.setSeMethod(manageType);
			vo.setUpdateTime(Pub.getCurrentDate());
			vo.setUpdateUser(user.getAccount());
			dao.updateClaim(vo);
			atx.setOutMsg("", "");
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		}
	}
	
	/**
	 * 索赔单材料费
	 * @throws Exception
	 */
	public void searchClaimMaxCosts()throws Exception{
		RequestWrapper request = atx.getRequest();
		String claimId= Pub.val(request, "claimId");
		try {
			BaseResultSet bs = dao.searchClaimMaxCosts(claimId);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * 将工单置为无效
	 * @throws Exception
	 */
	public void nullityWorkOrder()throws Exception{
		RequestWrapper request = atx.getRequest();
		String workId= Pub.val(request, "workId");
		try {
			SeBuWorkOrderVO vo = new SeBuWorkOrderVO();
			vo.setWorkId(workId);
			vo.setStatus(DicConstant.YXBS_02);
			dao.updateWorkOrder(vo);
			atx.setOutMsg("", "已将工单置为无效。");
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * 将工单置为无效
	 * @throws Exception
	 */
	public void validityWorkOrder()throws Exception{
		RequestWrapper request = atx.getRequest();
		String workId= Pub.val(request, "workId");
		try {
			SeBuWorkOrderVO vo = new SeBuWorkOrderVO();
			vo.setWorkId(workId);
			vo.setStatus(DicConstant.YXBS_01);
			dao.updateWorkOrder(vo);
			atx.setOutMsg("", "已将工单置为有效。");
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}
}

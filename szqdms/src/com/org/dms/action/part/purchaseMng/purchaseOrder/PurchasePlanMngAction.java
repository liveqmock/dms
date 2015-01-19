package com.org.dms.action.part.purchaseMng.purchaseOrder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import oracle.net.aso.h;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.purchaseMng.purchaseOrder.PurchasePlanMngDao;
import com.org.dms.vo.part.PtBuPchOrderDtlVO;
import com.org.dms.vo.part.PtBuPchOrderVO;
import com.org.dms.vo.part.PtBuPurchaseplayTmpVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.message.mail.newSendMail;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class PurchasePlanMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PurchasePlanMngDao dao = PurchasePlanMngDao.getInstance(atx);
	    /**
	     * 
	     * @date()2014年8月25日下午5:48:43
	     * @author Administrator
	     * @to_do:采购计划查询
	     * @throws Exception
	     */
	    public void partSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			String type = Pub.val(request, "type");
			String account = Pub.val(request, "account");
			page.setPageRows(10000);
			try
			{
				BaseResultSet bs = dao.partSearch(page,user,conditions,type,account);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    
	    public void partReSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			String type = Pub.val(request, "type");
			String account = Pub.val(request, "account");
			page.setPageRows(10000);
			try
			{
				BaseResultSet bs = dao.partReSearch(page,account,conditions,type);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    
	    /**
	     * 
	     * @date()2014年8月25日下午5:48:28
	     * @author Administrator
	     * @to_do:下载采购计划
	     * @throws Exception
	     */
	    public void download()throws Exception{
	    	//获取封装后的request对象
	    	RequestWrapper request = atx.getRequest();
	    	ResponseWrapper response= atx.getResponse();
	    	PageManager page = new PageManager();
	 		User user = (User) atx.getSession().get(Globals.USER_KEY);
	 		String conditions = RequestUtil.getConditionsWhere(request,page);
	 		String type = Pub.val(request, "type");
	        try
	        {
	        	List<HeaderBean> header = new ArrayList<HeaderBean>();
	        	HeaderBean hBean = null;
	        	hBean = new HeaderBean();
	    		hBean.setName("ROWNUM");
	    		hBean.setTitle("序号");
	    		header.add(0,hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("PART_CODE");
	    		hBean.setTitle("配件代码");
	    		header.add(1,hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("PART_NAME");
	    		hBean.setTitle("配件名称");
	    		hBean.setWidth(50);
	    		header.add(2,hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("COUNT");
	    		hBean.setTitle("未满足数量");
	    		header.add(3,hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("USER_NAME");
	    		hBean.setTitle("采购员");
	    		header.add(4,hBean);
	    		
	    		QuerySet qs = dao.download(conditions,user,type);
	    		ExportManager.exportFile(response.getHttpResponse(), "采购计划", header, qs);
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    public void createPurchaseOrder() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try
	        {
	        	String type = Pub.val(request, "type");
	        	String account = Pub.val(request, "account");
	        	HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				String PART_IDS = hm.get("PARTIDS");
				String COUNTS = hm.get("COUNTS");
				String SUPPLIERS = hm.get("SUPPLIERS");
				String REMARKS = hm.get("REMARKS");
				String PART_ID[] =PART_IDS.split(",");
				String COUNT[] = COUNTS.split(",");
				String SUPPLIER[] = SUPPLIERS.split(",");
				String REMARK[] = REMARKS.split(",");
				
				
				QuerySet  getSup = dao.getSup(SUPPLIERS);
				for(int i=1;i<=getSup.getRowCount();i++){
					String SUPPLIER_ID = getSup.getString(i, "SUPPLIER_ID");
					String SUPPLIER_CODE = getSup.getString(i, "SUPPLIER_CODE");
					String SUPPLIER_NAME = getSup.getString(i, "SUPPLIER_NAME");
					QuerySet checkPch = dao.checkPch(type,SUPPLIER_CODE,user);
					if(checkPch.getRowCount()>0){
						QuerySet getDic  = dao.getDic(type);
						String code=  getDic.getString(1, "DIC_VALUE");
						throw new Exception("["+SUPPLIER_NAME+"]供应商有未提报的"+code+"采购订单，不能新增"+code+"采购订单.");
					}
					//根据采购类型与供应商生成采购订单号begin
					String ORDER_NO = PartOddNumberUtil.getPurchaseOrderNo(atx.getDBFactory(),type,SUPPLIER_CODE );
					Calendar calendar = Calendar.getInstance();
					int year = calendar.get(Calendar.YEAR);
					int month = calendar.get(Calendar.MONTH)+1; 
					StringBuffer selectMonths = new StringBuffer();
					selectMonths.append(year).append("-").append(month);
					//由采购计划生成采购订单主表信息BEGIN
					PtBuPchOrderVO vo = new PtBuPchOrderVO();
					vo.setOrderNo(ORDER_NO);
					vo.setSupplierId(SUPPLIER_ID);
					vo.setSupplierCode(SUPPLIER_CODE);
					vo.setSupplierName(SUPPLIER_NAME);
					vo.setPurchaseType(type);
					vo.setSelectMonth(selectMonths.toString());
					vo.setOrderStatus(DicConstant.CGDDZT_01);
					vo.setStatus(DicConstant.YXBS_01);
					vo.setCompanyId(user.getCompanyId());
					vo.setOemCompanyId(user.getOemCompanyId());
					vo.setCreateUser(user.getAccount());
					vo.setCreateTime(Pub.getCurrentDate());//
					vo.setOrgId(user.getOrgId());
					vo.setApplyUser(account);
					vo.setApplyDate(Pub.getCurrentDate());
					dao.insertPurchaseOrder(vo);
					//由采购计划生成采购订单主表信息END
					//将采购计划金额，采购总金额，采购总数量插入采购订单主表中
					float planAmount = 0;
		            float amount = 0;
		            int count = 0;
		            //将选中配件插入生成采购订单明细中
					for(int j = 0;j<PART_ID.length;j++){
						if(SUPPLIER[j].equals(SUPPLIER_ID)){
							QuerySet getPart = dao.getPart(PART_ID[j],SUPPLIER_ID);
							if(getPart.getRowCount()>0){
								String APPLY_CYCLE = getPart.getString(1, "APPLY_CYCLE");
								String PCH_PRICE = getPart.getString(1, "PCH_PRICE");
								String PLAN_PRICE = getPart.getString(1, "PLAN_PRICE");
								String PART_CODE = getPart.getString(1, "PART_CODE");
								String PART_NAME = getPart.getString(1, "PART_NAME");
								
								if(Float.parseFloat(PLAN_PRICE)==0){
									throw new Exception("配件"+PART_CODE+"尚未维护计划价格，请先维护计划价");
								}
								if(Float.parseFloat(PCH_PRICE)==0){
									throw new Exception("配件"+PART_CODE+"尚未维护采购价格，请先维护采购价");
								}
								
								float planPrice = Float.parseFloat(PLAN_PRICE);
				            	float plan = Integer.parseInt(COUNT[j])*planPrice;
				            	float pchAmount = Integer.parseInt(COUNT[j])*Float.parseFloat(PCH_PRICE);
								PtBuPchOrderDtlVO p_vo = new PtBuPchOrderDtlVO();
								p_vo.setPurchaseId(vo.getPurchaseId());
								p_vo.setPartId(PART_ID[j]);
								p_vo.setPartCode(PART_CODE);
								p_vo.setPartName(PART_NAME);
								p_vo.setPchCount(COUNT[j]);
								p_vo.setPchPrice(PCH_PRICE);
								p_vo.setPlanPrice(PLAN_PRICE);
								p_vo.setPchAmount(String.valueOf(pchAmount));
								p_vo.setPlanAmount(String.valueOf(pchAmount));
								p_vo.setDeliveryCycle(APPLY_CYCLE);
								if(!"anull".equals(REMARK[j])){
									p_vo.setRemarks(REMARK[j]);
								}
								p_vo.setCreateTime(Pub.getCurrentDate());
								p_vo.setCreateUser(user.getAccount());
								dao.inserPurchaseOrderDtl(p_vo);
								
								//配件采购总数，采购总金额，计划采购总金额
				                amount = amount+pchAmount;
				                count = count + Integer.parseInt(COUNT[j]);
				                planAmount = planAmount + plan;
							}
						}
						
					}
					//将配件采购总数，采购总金额，计划采购总金额插入主表中
		            PtBuPchOrderVO amoVo = new PtBuPchOrderVO();
		            amoVo.setPurchaseId(vo.getPurchaseId());
		            amoVo.setPlanAmount(String.valueOf(planAmount));
		            amoVo.setPurchaseAmount(String.valueOf(amount));
		            amoVo.setPurchaseCount(String.valueOf(count));
		            dao.updatePurchaseOrder(amoVo);
					
				}
				atx.setOutMsg("","订单转换成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    public void savePurchaseOrderTmp() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try
	        {
	        	String type = Pub.val(request, "type");
	        	String account = Pub.val(request, "account");
	        	HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				String PART_IDS = hm.get("PARTIDS");
				String COUNTS = hm.get("PCH_COUNTS");
				String SUPPLIERS = hm.get("SUPPLIERS");
				String REMARKS = hm.get("REMARKS");
				
				String PART_CODES = hm.get("PART_CODE");
				String PART_NAMES = hm.get("PART_NAME");
				String PART_NOS = hm.get("PART_NO");
				String UNITS = hm.get("UNIT");
				String MIN_PACKS = hm.get("MIN_PACK");
				String MIN_UNITS = hm.get("MIN_UNIT");
				String IF_SUPLYS = hm.get("IF_SUPLY");
				String IF_SBS = hm.get("IF_SB");
				String SUPPLIER_CODES = hm.get("SUPPLIER_CODE");
				String SUPPLIER_NAMES = hm.get("SUPPLIER_NAME");
				String ROWSPANS = hm.get("ROWSPAN");
				String PLAN_PRICES = hm.get("PLAN_PRICE");
				String ORDER_COUNTS = hm.get("ORDER_COUNT");
				String ALL_PRICES = hm.get("ALL_PRICE");
				String AVAILABLE_AMOUNTS = hm.get("AVAILABLE_AMOUNT");
				String UPPER_LIMITS = hm.get("UPPER_LIMIT");
				String LOWER_LIMITS = hm.get("LOWER_LIMIT");
				String POSITION_NAMES = hm.get("POSITION_NAME");
				String APPLY_CYCLES = hm.get("APPLY_CYCLE");
				
				String PART_ID[] =PART_IDS.split(",");
				String COUNT[] = COUNTS.split(",");
				String SUPPLIER[] = SUPPLIERS.split(",");
				String REMARK[] = REMARKS.split(",");
				
				String PART_CODE[] =PART_CODES.split(",");
				String PART_NAME[] = PART_NAMES.split(",");
				String PART_NO[] = PART_NOS.split(",");
				String UNIT[] = UNITS.split(",");
				String MIN_PACK[] =MIN_PACKS.split(",");
				String MIN_UNIT[] = MIN_UNITS.split(",");
				String IF_SB[] = IF_SBS.split(",");
				String IF_SUPLY[] = IF_SUPLYS.split(",");
				String SUPPLIER_CODE[] = SUPPLIER_CODES.split(",");
				String SUPPLIER_NAME[] =SUPPLIER_NAMES.split(",");
				String ROWSPAN[] = ROWSPANS.split(",");
				String PLAN_PRICE[] = PLAN_PRICES.split(",");
				String ORDER_COUNT[] = ORDER_COUNTS.split(",");
				String ALL_PRICE[] = ALL_PRICES.split(",");
				String AVAILABLE_AMOUNT[] =AVAILABLE_AMOUNTS.split(",");
				String UPPER_LIMIT[] = UPPER_LIMITS.split(",");
				String LOWER_LIMIT[] = LOWER_LIMITS.split(",");
				String POSITION_NAME[] = POSITION_NAMES.split(",");
				String APPLY_CYCLE[] =APPLY_CYCLES.split(",");
				if(PART_ID.length > 0)
					dao.deletePurchasePlanTmp(type, account);
				for (int i = 0; i < PART_ID.length; i++)
				{
					PtBuPurchaseplayTmpVO vo = new PtBuPurchaseplayTmpVO();
					vo.setPartId(PART_ID[i]);
					vo.setPartCode(PART_CODE[i]);
					vo.setPartName(PART_NAME[i]);
					String p = PART_NO[i];
					if(PART_NO[i].equals("anull")) p = "";
					vo.setPartNo(p);
					vo.setUnit(UNIT[i]);
					vo.setMinPack(MIN_PACK[i]);
					vo.setMinUnit(MIN_UNIT[i]);
					vo.setIfSb(IF_SB[i]);
					vo.setIfSuply(IF_SUPLY[i]);
					vo.setSupplierId(SUPPLIER[i]);
					vo.setSupplierCode(SUPPLIER_CODE[i]);
					vo.setSupplierName(SUPPLIER_NAME[i]);
					vo.setRowspan(ROWSPAN[i]);
					vo.setPlanPrice(PLAN_PRICE[i]);
					String c = COUNT[i];
					if(COUNT[i].equals("anull")) c = "";
					vo.setPchCount(c);
					vo.setAllPrice(ALL_PRICE[i]);
					String a = AVAILABLE_AMOUNT[i];
					if(AVAILABLE_AMOUNT[i].equals("anull")) a = "";
					vo.setAvailableAmount(a);
					String u = UPPER_LIMIT[i];
					if(UPPER_LIMIT[i].equals("anull")) u = "";
					vo.setUpperLimit(u);
					String l = LOWER_LIMIT[i];
					if(LOWER_LIMIT[i].equals("anull")) l = "";
					vo.setLowerLimit(l);
					vo.setUserAccount(account);
					String n = POSITION_NAME[i];
					if(POSITION_NAME[i].equals("anull")) n = "";
					vo.setPositionName(n);
					vo.setApplyCycle(APPLY_CYCLE[i]);
					String r = REMARK[i];
					if(REMARK[i].equals("anull")) r = "";
					vo.setRemark(r);
					vo.setPlanType(type);
					vo.setOrderCount(ORDER_COUNT[i]);
					dao.insertPurchasePlanTmp(vo);
				}
				atx.setOutMsg("","暂存采购计划成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }

}

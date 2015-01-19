package com.org.dms.action.part.purchaseMng.purchaseOrder;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.purchaseMng.purchaseOrder.PurchaseOrderMngDao;
import com.org.dms.vo.part.PtBuOrderShipCarrierVO;
import com.org.dms.vo.part.PtBuPchOrderDtlVO;
import com.org.dms.vo.part.PtBuPchOrderShippingVO;
import com.org.dms.vo.part.PtBuPchOrderSplitDtlVO;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchOrderVO;
import com.org.dms.vo.part.PtBuPchReceiveOrderDtlVO;
import com.org.dms.vo.part.PtBuPchReceiveOrderVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExcelErrors;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.message.mail.newSendMail;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class PurchaseOrderMngAction {
	private Logger logger = com.org.framework.log.log.getLogger("Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PurchaseOrderMngDao dao = PurchaseOrderMngDao.getInstance(atx);
	    // 定义reponse对象
	    private ResponseWrapper responseWrapper = atx.getResponse();
	    /**
	     * 
	     * @date()2014年7月11日下午4:29:48
	     * @author Administrator
	     * @to_do:采购订单查询(民品采购订单)
	     * @throws Exception
	     */
	    public void orderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.orderSearch(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    /**
	     * 订单查询导出
	     * @throws Exception
	     */
	    public void download()throws Exception{

	        try {
	        	//获取封装后的request对象
	        	RequestWrapper request = atx.getRequest();
	        	// 定义查询分页对象
	            PageManager pageManager = new PageManager();
	            String PURCHASE_ID = Pub.val(request, "PURCHASE_ID");
	            List<HeaderBean> header = new ArrayList<HeaderBean>();
	            HeaderBean hBean = null;
	            hBean = new HeaderBean();
	            hBean.setName("ORDER_NO");
	            hBean.setTitle("订单编号");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PART_CODE");
	            hBean.setTitle("配件代码");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PART_NAME");
	            hBean.setTitle("配件名称");
	            hBean.setWidth(50);
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("UNIT");
	            hBean.setTitle("计量单位");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("MIN_PACK");
	            hBean.setTitle("最小包装");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PCH_PRICE");
	            hBean.setTitle("采购价格");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PCH_COUNT");
	            hBean.setTitle("采购数量");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PCH_AMOUNT");
	            hBean.setTitle("金额");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("DELIVERY_CYCLE");
	            hBean.setTitle("供货周期");
	            header.add(hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("REMARKS");
	            hBean.setTitle("备注");
	            header.add(hBean);
	            
	            QuerySet querySet = dao.download(PURCHASE_ID);
	            ExportManager.exportFile(responseWrapper.getHttpResponse(), "DDCXDC", header, querySet);
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }

	    /**
	     * 
	     * @date()2014年7月14日下午12:04:27
	     * @author Administrator
	     * @to_do:订单已选择配件查询
	     * @throws Exception
	     */
	    public void orderPartSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			RequestUtil.getConditionsWhere(request,page);
			try
			{
				String PURCHASE_ID = Pub.val(request, "PURCHASE_ID");
				BaseResultSet bs = dao.orderPartSearch(page,user,PURCHASE_ID);
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
	     * @date()2014年7月16日下午9:04:37
	     * @author Administrator
	     * @to_do:待选择配件查询
	     * @throws Exception
	     */
	    public void searchPart() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PageManager page = new PageManager();
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        try {
	            String PURCHASE_ID = Pub.val(request, "PURCHASE_ID");
	            String SUPPLIER_ID = Pub.val(request, "SUPPLIER_ID");
	            String PURCHASE_TYPE = Pub.val(request, "PURCHASE_TYPE");
	            String USER_ACCOUNT = Pub.val(request, "ACCOUNT");
	            BaseResultSet bs = dao.searchPart(page, user, conditions,SUPPLIER_ID,PURCHASE_ID,PURCHASE_TYPE,USER_ACCOUNT);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月11日下午4:46:13
	     * @author Administrator
	     * @to_do:采购订单新增(民品采购订单)
	     * @throws Exception
	     */
	    public void purchaseOrderInsert() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try
	        {
	        	HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				
				//根据采购类型与供应商生成采购订单号begin
				String purchaseType = hm.get("PURCHASE_TYPE");
				String supplierCode = hm.get("SUPPLIER_CODE");
				String type = Pub.val(request, "type");
				QuerySet checkPch = dao.checkPch(purchaseType,supplierCode,user);
				if(checkPch.getRowCount()>0){
					QuerySet getDic  = dao.getDic(purchaseType);
					String code=  getDic.getString(1, "DIC_VALUE");
					throw new Exception("该供应商有未提报的"+code+"采购订单，不能新增"+code+"采购订单.");
				}else{
					String ORDER_NO = PartOddNumberUtil.getPurchaseOrderNo(atx.getDBFactory(),purchaseType,supplierCode );
		        	PtBuPchOrderVO vo = new PtBuPchOrderVO();
					vo.setValue(hm);
					vo.setOrderNo(ORDER_NO);
					vo.setOrderStatus(DicConstant.CGDDZT_01);
					vo.setStatus(DicConstant.YXBS_01);
					vo.setCompanyId(user.getCompanyId());
					vo.setOemCompanyId(user.getOemCompanyId());
					vo.setCreateUser(user.getAccount());
					vo.setPurchaseType(type);
					vo.setCreateTime(Pub.getCurrentDate());//
					vo.setOrgId(user.getOrgId());
					vo.setApplyDate(Pub.getCurrentDate());
					dao.insertPurchaseOrder(vo);
					atx.setOutMsg(vo.getRowXml(),"订单新增成功！");
				}
				
	        	
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月11日下午4:49:10
	     * @author Administrator
	     * @to_do:采购订单修改(民品采购订单)
	     * @throws Exception
	     */
	    public void purchaseOrderUpdate() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchOrderVO tempVO = new PtBuPchOrderVO();
	        try
	        {
	            HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				String type = Pub.val(request, "type");
				tempVO.setValue(hm);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setPurchaseType(type);
				tempVO.setUpdateTime(Pub.getCurrentDate());//
	            dao.updatePurchaseOrder(tempVO);
	            atx.setOutMsg(tempVO.getRowXml(),"订单修改成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月11日下午4:55:36
	     * @author Administrator
	     * @to_do:采购订单删除(删除主表以及配件信息)
	     * @throws Exception
	     */
	    public void purchaseOrderDelete() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        try
	        {
				String PURCHASE_ID = Pub.val(request, "PURCHASE_ID");
				QuerySet qs = dao.checkPart(PURCHASE_ID);
				if(qs.getRowCount()>0){
					dao.purchaseOrderPartDelete(PURCHASE_ID);
				}
	            dao.purchaseOrderDelete(PURCHASE_ID);
				atx.setOutMsg("","订单删除成功！");
	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月14日下午8:40:48
	     * @author Administrator
	     * @to_do:采购订单新增配件
	     * @throws Exception
	     */
	    public void insertOrderPart() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        try {
	            HashMap<String, String> hm;
	            hm = RequestUtil.getValues(request);
	            String PURCHASE_ID = hm.get("PURCHASID");//采购订单ID
	            String PART_IDS = hm.get("PARTIDS");//配件ID
	            String PART_CODES = hm.get("PARTCODES");//配件代码
	            String PART_NAMES = hm.get("PARTNAMES");//配件名称
	            String PCH_COUNTS = hm.get("PCHCOUNTS");//采购数量
	            String PCH_PRICES = hm.get("PCHPRICES");//采购价格
	            String PCH_AMOUNTS = hm.get("PCHAMOUNTS");//采购总价
	            String DELIVERYCYCLES = hm.get("DELIVERYCYCLES");//供货周期
	            String REMARKS = hm.get("REMARKS");//备注
	            String[] PART_ID = PART_IDS.split(",");
	            String[] PART_CODE = PART_CODES.split(",");
	            String[] PART_NAME = PART_NAMES.split(",");
	            String[] PCH_COUNT = PCH_COUNTS.split(",");
	            String[] PCH_PRICE = PCH_PRICES.split(",");
	            String[] PCH_AMOUNT = PCH_AMOUNTS.split(",");
	            String[] DELIVERYCYCLE = DELIVERYCYCLES.split(",");
	            String[] REMARK = REMARKS.split(",");
	            float planAmount = 0;
	            float amount = 0;
	            int count = 0;
	            
	            for (int i = 0; i < PART_ID.length; i++) {
	            	QuerySet querySet1 = dao.checkIn(PURCHASE_ID,PART_ID[i]);
	                if (querySet1.getRowCount() > 0) {
	                    throw new Exception("配件"+querySet1.getString(1, "PART_CODE")+"已添加.");
	                }
	            	QuerySet qs_price = dao.getPlanPrice(PART_ID[i],PURCHASE_ID);
	            	float planPrice = Float.parseFloat(qs_price.getString(1, 1));
	            	float plan = Integer.parseInt(PCH_COUNT[i])*planPrice;
	            	
	            	
	            	Pattern pattern = Pattern.compile("[0-9]*");  
                    Matcher matcher = pattern.matcher(DELIVERYCYCLE[i]);  
                    if (matcher.matches() == false) {
                        QuerySet qs1 = dao.getDeliveryCyclNum(DELIVERYCYCLE[i]);
                        if(qs1.getRowCount()>0){
                        	DELIVERYCYCLE[i] = qs1.getString(1, "PARAVALUE2");
                        }
                    }
	            	
	            	PtBuPchOrderDtlVO pVo = new PtBuPchOrderDtlVO();
	            	pVo.setPurchaseId(PURCHASE_ID);
	            	pVo.setPartId(PART_ID[i]);
	            	pVo.setPartCode(PART_CODE[i]);
	            	pVo.setPartName(PART_NAME[i]);
	            	pVo.setPchCount(PCH_COUNT[i]);
	            	pVo.setPchPrice(PCH_PRICE[i]);
	            	pVo.setPlanPrice(String.valueOf(planPrice));
	            	pVo.setPchAmount(PCH_AMOUNT[i]);
	            	pVo.setPlanAmount(String.valueOf(plan));
	            	pVo.setDeliveryCycle(DELIVERYCYCLE[i]);
	            	if(!"nu".equals(REMARK[i])){
	            		pVo.setRemarks(REMARK[i]);
	            	}
	                dao.insertPart(pVo);
	                //配件采购总数，采购总金额，计划采购总金额
	                amount = amount+Float.parseFloat(PCH_AMOUNT[i]);
	                count = count + Integer.parseInt(PCH_COUNT[i]);
	                planAmount = planAmount + plan;
	            }
	            //将配件采购总数，采购总金额，计划采购总金额插入主表中
	            PtBuPchOrderVO amoVo = new PtBuPchOrderVO();
	            amoVo.setPurchaseId(PURCHASE_ID);
	            amoVo.setPlanAmount(String.valueOf(planAmount));
	            amoVo.setPurchaseAmount(String.valueOf(amount));
	            amoVo.setPurchaseCount(String.valueOf(count));
	            dao.updatePurchaseOrder(amoVo);
	            //返回插入结果和成功信息
	            atx.setOutMsg("", "新增成功！");
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月17日上午10:40:21
	     * @author Administrator
	     * @to_do:采购订单配件删除
	     * @throws Exception
	     */
	    public void partDelete() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        try
	        {
				String DETAIL_ID = Pub.val(request, "DETAIL_ID");
				/**
				 * 更改采购总数采购金额计划总金额 BEGIN
				 */
				QuerySet prices = dao.geOldtPartAmount(DETAIL_ID);
				String PCH_COUNT = prices.getString(1, "PCH_COUNT");
				String PLAN_AMOUNT = prices.getString(1, "PLAN_AMOUNT");
				String PCH_AMOUNT = prices.getString(1, "PCH_AMOUNT");
				String PURCHASE_ID = prices.getString(1, "PURCHASE_ID");
				String P_PCH_AMOUNT = prices.getString(1, "P_PCH_AMOUNT");
				String P_PCH_COUNT = prices.getString(1, "P_PCH_COUNT");
				String P_PLAN_AMOUNT = prices.getString(1, "P_PLAN_AMOUNT");
				float new_planAmount = Float.parseFloat(P_PLAN_AMOUNT)-Float.parseFloat(PLAN_AMOUNT);
				float new_pchAmount = Float.parseFloat(P_PCH_AMOUNT)-Float.parseFloat(PCH_AMOUNT);
				int new_pchCount = Integer.parseInt(P_PCH_COUNT)-Integer.parseInt(PCH_COUNT);
				PtBuPchOrderVO tmpVO = new PtBuPchOrderVO();
				tmpVO.setPurchaseId(PURCHASE_ID);
				tmpVO.setPlanAmount(String.valueOf(new_planAmount));
				tmpVO.setPurchaseAmount(String.valueOf(new_pchAmount));
				tmpVO.setPurchaseCount(String.valueOf(new_pchCount));
				dao.updatePurchaseOrder(tmpVO);
				/**
				 * END
				 */
				
	            dao.delParts(DETAIL_ID);
				atx.setOutMsg("","配件删除成功！");
	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月11日下午5:00:44
	     * @author Administrator
	     * @to_do:采购订单提报(民品采购订单)
	     * @throws Exception
	     */
	    public void purchaseOrderReport() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchOrderVO tempVO = new PtBuPchOrderVO();
	        try
	        {
				String PURCHASE_ID = Pub.val(request, "PURCHASE_ID");
				/**
				 * 根据配件采购周期进行拆单begin
				 */
				//获取拆分单信息病插入拆分单主表
				/**
				 * 提交之前清除此采购单未提报的拆分单与拆分单明细信息
				 */
				dao.deleteSplitDtl(PURCHASE_ID);
				dao.deleteSplit(PURCHASE_ID);
				/*********************************/
				QuerySet qs = dao.getDelivery(PURCHASE_ID);
				for(int i =1;i<=qs.getRowCount();i++){
					String DELIVERY_CYCLE = qs.getString(i, "DELIVERY_CYCLE");
					String SELECT_MONTH = qs.getString(i, "SELECT_MONTH");
					String PURCHASE_TYPE = qs.getString(i, "PURCHASE_TYPE");
					String SUPPLIER_ID = qs.getString(i, "SUPPLIER_ID");
					String SUPPLIER_NAME = qs.getString(i, "SUPPLIER_NAME");
					String SUPPLIER_CODE = qs.getString(i, "SUPPLIER_CODE");
					Pattern pattern = Pattern.compile("[0-9]*");  
                    Matcher matcher = pattern.matcher(DELIVERY_CYCLE);  
                    if (matcher.matches() == false) {
                        QuerySet qs1 = dao.getDeliveryCyclNum(DELIVERY_CYCLE);
                        if(qs1.getRowCount()>0){
                        	DELIVERY_CYCLE = qs1.getString(1, "PARAVALUE2");
                        }
                    } 
					
//					Date date=new   Date();//取时间 
					Date date = Pub.getCurrentDate();
			        Calendar   calendar   =   new   GregorianCalendar(); 
			     	calendar.setTime(date); 
			     	calendar.add(calendar.DATE,Integer.parseInt(DELIVERY_CYCLE));//当前日期加采购周期
			     	date=calendar.getTime();   //要求完成日期
					
					//生成拆分单号
					String ORDER_NO = qs.getString(i, "ORDER_NO");
					String SPLIT_NO = PartOddNumberUtil.getSplitNo(atx.getDBFactory(),ORDER_NO );
					//插入拆分单主表
					PtBuPchOrderSplitVO s_po = new PtBuPchOrderSplitVO();
					s_po.setPurchaseId(PURCHASE_ID);
					s_po.setSplitNo(SPLIT_NO);
					s_po.setOrderNo(ORDER_NO);
					s_po.setSelectMonth(SELECT_MONTH);
					s_po.setPurchaseType(PURCHASE_TYPE);
					s_po.setSupplierId(SUPPLIER_ID);
					s_po.setSupplierCode(SUPPLIER_CODE);
					s_po.setSupplierName(SUPPLIER_NAME);
					s_po.setDeliveryCycle(DELIVERY_CYCLE);
					s_po.setOrderType(PURCHASE_TYPE);
					s_po.setOrderStatus(DicConstant.CGDDZT_01);
					//s_po.setApplyUser(user.getAccount());
					//s_po.setApplyDate(Pub.getCurrentDate());
					s_po.setStatus(DicConstant.YXBS_01);
					s_po.setSettleStatus(DicConstant.GYSJSZT_01);
					s_po.setCompanyId(user.getCompanyId());
					s_po.setOemCompanyId(user.getOemCompanyId());
					s_po.setCreateUser(user.getAccount());
					s_po.setCreateTime(Pub.getCurrentDate());
					s_po.setOrgId(user.getOrgId());
					s_po.setRepuirementTime(date);
					dao.insertSplitOrder(s_po);
					//循环将拆分单明细数据插入拆分单明细表,同时将拆分单总采购量，总采购金额，总计划金额放入拆分单表中
					float planAmount = 0;//计划总金额
		            float amount = 0;//采购总金额
		            int count = 0;//总采购数
					String SPLIT_ID = s_po.getSplitId();
					
					QuerySet checkIfSQ = dao.checkIfSQ(SUPPLIER_ID);//校验是否为陕汽制造供应商，如果为是 直接变为出库状态
					
					QuerySet qs_p = dao.getSplitPart(PURCHASE_ID,DELIVERY_CYCLE);
					for(int j =1;j<=qs_p.getRowCount();j++){
						String PART_ID = qs_p.getString(j, "PART_ID");
						String PART_NAME = qs_p.getString(j, "PART_NAME");
						String PART_CODE = qs_p.getString(j, "PART_CODE");
						String PCH_COUNT = qs_p.getString(j, "PCH_COUNT");
						String PCH_PRICE = qs_p.getString(j, "PCH_PRICE");
						String PCH_AMOUNT = qs_p.getString(j, "PCH_AMOUNT");
						String REMARKS = qs_p.getString(j, "REMARKS");
						String IF_SUPLY = qs_p.getString(j, "IF_SUPLY");
						//获取计划单价
						QuerySet qs_price = dao.getPlanPrice(PART_ID,PURCHASE_ID);
						float planPrice = Float.parseFloat(qs_price.getString(1, 1));
						float plan = Integer.parseInt(PCH_COUNT)*planPrice;
						
						PtBuPchOrderSplitDtlVO s_p_vo = new PtBuPchOrderSplitDtlVO();
						s_p_vo.setSplitId(SPLIT_ID);
						s_p_vo.setPartId(PART_ID);
						s_p_vo.setPartCode(PART_CODE);
						s_p_vo.setPartName(PART_NAME);
						if(DicConstant.SF_01.equals(IF_SUPLY)){
							s_p_vo.setSupplierId(SUPPLIER_ID);
							s_p_vo.setSupplierCode(SUPPLIER_CODE);
							s_p_vo.setSupplierName(SUPPLIER_NAME);
						}else{
							QuerySet tmp = dao.getTmpSup("9XXXXXX");
							s_p_vo.setSupplierId(tmp.getString(1, "SUPPLIER_ID"));
							s_p_vo.setSupplierCode(tmp.getString(1, "SUPPLIER_CODE"));
							s_p_vo.setSupplierName(tmp.getString(1, "SUPPLIER_NAME"));
						}
						s_p_vo.setPchCount(PCH_COUNT);
						s_p_vo.setPchPrice(PCH_PRICE);
						s_p_vo.setPchAmount(PCH_AMOUNT);
						if(checkIfSQ.getRowCount()>0){
							s_p_vo.setShipCount(PCH_COUNT);
						}
						s_p_vo.setRemarks(REMARKS);
						s_p_vo.setPlanPrice(String.valueOf(planPrice));
						s_p_vo.setPlanAmount(String.valueOf(plan));
						dao.insertSplitOrderPart(s_p_vo);
						
						if(checkIfSQ.getRowCount()>0){
			            	PtBuPchOrderShippingVO s_vo = new PtBuPchOrderShippingVO();
			            	s_vo.setDetailId(s_p_vo.getDetailId());
			            	s_vo.setPurchaseId(SPLIT_ID);
			            	s_vo.setShippingAmount(PCH_COUNT);
			            	s_vo.setShippingMoney(PCH_AMOUNT);
			            	s_vo.setShipDate(Pub.getCurrentDate());
			            	dao.inserShipLog(s_vo);
						}
						//获取计划总金额，采购总金额，总采购数
		                planAmount = planAmount + plan;//计划总金额
		                amount = amount+Float.parseFloat(PCH_AMOUNT);//采购总金额
		                count = count + Integer.parseInt(PCH_COUNT);//总采购数
					}
					//更新拆分单总采购量，拆分单总采购金额，拆分单总计划金额
					
					PtBuPchOrderSplitVO amVo = new PtBuPchOrderSplitVO();
					amVo.setSplitId(SPLIT_ID);
					amVo.setPlanAmount(String.valueOf(planAmount));//计划总金额
					amVo.setPurchaseAmount(String.valueOf(amount));//采购总金额
					amVo.setPurchaseCount(String.valueOf(count));//总采购数
					if(checkIfSQ.getRowCount()>0){
						amVo.setOrderStatus(DicConstant.CGDDZT_04);
						amVo.setIfOnTime(DicConstant.SF_01);
						amVo.setFirstShipDate(Pub.getCurrentDate());
						amVo.setLastShipDate(Pub.getCurrentDate());
						amVo.setShipTimes("1");
					}
					dao.updatePurchaseOrderSplit(amVo);
					
				}
	            /**
	             * 根据配件采购周期进行拆单end
	             */
				//主表状态修改
				tempVO.setPurchaseId(PURCHASE_ID);
				//tempVO.setOrderStatus(DicConstant.CGDDZT_02);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
	            dao.updatePurchaseOrder(tempVO);
				atx.setOutMsg("","订单提交成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月28日下午4:11:45
	     * @author Administrator
	     * @to_do:查询导入临时表数据
	     * @throws Exception
	     */
	    public void searchImport()throws Exception{
	 		User user = (User) atx.getSession().get(Globals.USER_KEY);
	 		RequestWrapper request = atx.getRequest();
	 		try
	 		{
	 			PageManager pageManager = new PageManager();
	            // 将request流中的信息转化为where条件
	            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
	 			BaseResultSet bs = dao.searchImport(pageManager,user,conditions);
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
	     * @date()2014年7月28日下午4:57:28
	     * @author Administrator
	     * @to_do:将临时表数据插入正式业务表中
	     * @throws Exception
	     */
	    public void purchaseOrderPartImport()throws Exception{
	    	RequestWrapper request = atx.getRequest();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	 		String PURCHASE_ID=Pub.val(request, "purchaseId");
	 		String tmpNo = Pub.val(request, "tmpNo");
	 		try
	 		{
	 			float amount = 0;
	 			float plan = 0;
	 			int count = 0;
	 			QuerySet sup = dao.getSup(PURCHASE_ID);
	 			String SUPPLIER_ID = sup.getString(1, "SUPPLIER_ID");
	 			QuerySet tmp = dao.getTmpInfo(user,SUPPLIER_ID,tmpNo);
	 			for(int i=1;i<=tmp.getRowCount();i++){
	 				String PART_ID = tmp.getString(i, "PART_ID");
	 				String PART_CODE = tmp.getString(i, "PART_CODE");
	 				String PART_NAME = tmp.getString(i, "PART_NAME");
	 				String PCH_COUNT = tmp.getString(i, "PCH_COUNT");
	 				String PCH_PRICE = tmp.getString(i, "PCH_PRICE");
	 				String PLAN_PRICE = tmp.getString(i, "PLAN_PRICE");
	 				String DELIVERYCYCLE = tmp.getString(i, "APPLY_CYCLE");
	 				String REMARK = tmp.getString(i, "REMARKS") == null ? "" : tmp.getString(i, "REMARKS");
	 				Float PchAmount = Integer.parseInt(PCH_COUNT)*Float.parseFloat(PCH_PRICE);
	 				Float PlanAount = Integer.parseInt(PCH_COUNT)*Float.parseFloat(PLAN_PRICE);
	 				PtBuPchOrderDtlVO vo = new PtBuPchOrderDtlVO();
	 				vo.setPurchaseId(PURCHASE_ID);
		 			vo.setPartId(PART_ID);
		 			vo.setPartCode(PART_CODE);
		 			vo.setPartName(PART_NAME);
		 			vo.setPchCount(PCH_COUNT);
		 			vo.setPchPrice(PCH_PRICE);
		 			vo.setPchAmount(String.valueOf(PchAmount));
		 			vo.setPlanAmount(String.valueOf(PlanAount));
		 			vo.setDeliveryCycle(DELIVERYCYCLE);
	            	
		 			vo.setRemarks(REMARK);
		 			// end
	            	
	            	dao.insertPart(vo);
	            	amount = amount+PchAmount;
	            	plan = plan+PlanAount;
	            	count = count+Integer.parseInt(PCH_COUNT);
	 			}
	 			PtBuPchOrderVO upVo = new PtBuPchOrderVO();
	 			upVo.setPurchaseId(PURCHASE_ID);
	 			upVo.setPlanAmount(String.valueOf(plan));
	 			upVo.setPurchaseAmount(String.valueOf(amount));
	 			upVo.setPurchaseCount(String.valueOf(count));
	 			dao.updatePurchaseOrder(upVo);
	 			atx.setOutMsg("", "导入成功！");
	 		}
	 		catch (Exception e)
	 		{
	 			logger.error(e);
	 			atx.setException(e);
	 		}
	    }
	    
	    public void expData()throws Exception{

	        try {
	        	//获取封装后的request对象
	        	RequestWrapper request = atx.getRequest();
		    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	            // 将request流中的信息转化为where条件
	            String conditions = Pub.val(request, "seqs");
	            List<HeaderBean> header = new ArrayList<HeaderBean>();
	            HeaderBean hBean = null;
	            hBean = new HeaderBean();
	            hBean.setName("PART_CODE");
	            hBean.setTitle("配件代码");
	            header.add(0,hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PCH_COUNT");
	            hBean.setTitle("采购数量");
	            header.add(1,hBean);
	            hBean = new HeaderBean();
	            hBean.setName("REMARKS");
	            hBean.setTitle("备注");
	            header.add(2,hBean);

	            QuerySet querySet = dao.expData(conditions,user);
	            ExportManager.exportFile(atx.getResponse().getHttpResponse(), "PchPartImp", header, querySet);
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年9月9日上午11:55:35
	     * @author Administrator
	     * @to_do:采购订单配件行编辑
	     * @throws Exception
	     */
	    public void orderPartCountSave() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        HashMap<String, String> hm = RequestUtil.getValues(request);
	        try {
	        	
				String PURCHASE_ID = hm.get("PCH_ID");
				String DETAIL_ID = hm.get("DTL_ID");
				String COUNT = hm.get("COUNT");
				String REMARKS = hm.get("RMK"); 
	            QuerySet getOld = dao.getOld(PURCHASE_ID,DETAIL_ID);
	            String PCH_COUNT = getOld.getString(1, "PCH_COUNT");
	            String PCH_PRICE = getOld.getString(1, "PCH_PRICE");
	            String PLAN_PRICE = getOld.getString(1, "PLAN_PRICE");
	            String PCH_AMOUNT = getOld.getString(1, "PCH_AMOUNT");
	            String PLAN_AMOUNT = getOld.getString(1, "PLAN_AMOUNT");
	            if(REMARKS==null){
	            	REMARKS = "";
	            }
	            dao.updateOrderPart(DETAIL_ID,PURCHASE_ID,COUNT,REMARKS,user);
	            /**
	             * 更改订单主表中的采购数量采购总金额计划总金额
	             */
	            String NEW_PLAN_AMOUNT = String.valueOf(Integer.parseInt(COUNT)*Float.parseFloat(PLAN_PRICE));
	            String NEW_PCH_AMOUNT = String.valueOf(Integer.parseInt(COUNT)*Float.parseFloat(PCH_PRICE));
	            dao.updatePurAmount(PURCHASE_ID,PLAN_AMOUNT,NEW_PLAN_AMOUNT,PCH_AMOUNT,NEW_PCH_AMOUNT,PCH_COUNT,COUNT,user);
	            atx.setOutMsg("", "保存成功！");
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    
	    public void receiveOrderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.receiveOrderSearch(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    public void purchaseSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.purchaseSearch(page,user,conditions);
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
	     * @date()2014年10月21日下午9:36:14
	     * @author Administrator
	     * @to_do:生成领料票
	     * @throws Exception
	     */
	    public void purchaseReceiveOrderInsert() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try
	        {
	        	HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				PtBuPchReceiveOrderVO vo = new PtBuPchReceiveOrderVO();
				String recNo =  PartOddNumberUtil.getReceiptNo(atx.getDBFactory());
				vo.setValue(hm);
				vo.setRecNo(recNo);
				vo.setPrintStatus(DicConstant.DYZT_01);
				vo.setStatus(DicConstant.YXBS_01);
				vo.setCompanyId(user.getCompanyId());
				vo.setOemCompanyId(user.getOemCompanyId());
				vo.setCreateUser(user.getAccount());
				vo.setCreateTime(Pub.getCurrentDate());//
				vo.setOrgId(user.getOrgId());
				dao.insertPurchaseReceiveOrder(vo);
				atx.setOutMsg(vo.getRowXml(),"领料票新增成功！");
	        	
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /***
	     * 
	     * @date()2014年10月21日下午9:36:29
	     * @author Administrator
	     * @to_do:领料票配件选择
	     * @throws Exception
	     */
	    public void receivePartSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			RequestUtil.getConditionsWhere(request,page);
			try
			{
				String REC_ID = Pub.val(request, "REC_ID");
				BaseResultSet bs = dao.receivePartSearch(page,user,REC_ID);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    public void receiveRearchPart() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PageManager page = new PageManager();
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        try {
	            String SPLIT_ID = Pub.val(request, "SPLIT_ID");
	            String REC_ID = Pub.val(request, "REC_ID");
	            BaseResultSet bs = dao.receiveRearchPart(page, user, conditions,SPLIT_ID,REC_ID);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    //insertReceiveOrderPart
	    public void insertReceiveOrderPart() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        try {
	            HashMap<String, String> hm;
	            hm = RequestUtil.getValues(request);
	            String REC_ID = hm.get("RECID");//采购订单ID
	            String PART_IDS = hm.get("PARTIDS");//配件ID
	            String PART_CODES = hm.get("PARTCODES");//配件代码
	            String PART_NAMES = hm.get("PARTNAMES");//配件名称
	            String PCH_COUNTS = hm.get("PCHCOUNTS");//采购数量
	            String PCH_PRICES = hm.get("PCHPRICES");//采购价格
	            String PCH_AMOUNTS = hm.get("PCHAMOUNTS");//采购总价
	            String PB_NOS = hm.get("PBNOS");//备注
	            String RMKS = hm.get("RMKS");//备注
	            String[] PART_ID = PART_IDS.split(",");
	            String[] PART_CODE = PART_CODES.split(",");
	            String[] PART_NAME = PART_NAMES.split(",");
	            String[] PCH_COUNT = PCH_COUNTS.split(",");
	            String[] PCH_PRICE = PCH_PRICES.split(",");
	            String[] PCH_AMOUNT = PCH_AMOUNTS.split(",");
	            String[] PB_NO = PB_NOS.split(",");
	            String[] REMARK = RMKS.split(",");
	            
	            
	            QuerySet getPchId = dao.getPchId(REC_ID);
	            String PURCHASE_ID = getPchId.getString(1, 1);
	            for (int i = 0; i < PART_ID.length; i++) {
	            	QuerySet qs_price = dao.getPlanPrice(PART_ID[i],PURCHASE_ID);
	            	float planPrice = Float.parseFloat(qs_price.getString(1, 1));
	            	float plan = Integer.parseInt(PCH_COUNT[i])*planPrice;
	            	PtBuPchReceiveOrderDtlVO pVo = new PtBuPchReceiveOrderDtlVO();
	            	pVo.setRecId(REC_ID);
	            	pVo.setPartId(PART_ID[i]);
	            	pVo.setPartCode(PART_CODE[i]);
	            	pVo.setPartName(PART_NAME[i]);
	            	pVo.setPchCount(PCH_COUNT[i]);
	            	pVo.setPchPrice(PCH_PRICE[i]);
	            	pVo.setPlanPrice(String.valueOf(planPrice));
	            	pVo.setPchAmount(PCH_AMOUNT[i]);
	            	pVo.setPlanAmount(String.valueOf(plan));
	            	if(!"myNull".equals(PB_NO[i])){
	            		pVo.setPbNo(PB_NO[i]);
	            	}
	            	if(!"myNull".equals(REMARK[i])){
	            		pVo.setRemarks(REMARK[i]);
	            	}
	                dao.insertReceivePart(pVo);
	            }
	            //返回插入结果和成功信息
	            atx.setOutMsg("", "新增成功！");
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    public void receivOerderPartCountSave() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        HashMap<String, String> hm = RequestUtil.getValues(request);
	        try {
	        	
				String REC_ID = hm.get("RE_ID");
				String DETAIL_ID = hm.get("DTL_ID");
				String COUNT = hm.get("COUNT");
				String REMARKS = hm.get("RMK"); 
//	            QuerySet getOld = dao.getOld(PURCHASE_ID,DETAIL_ID);
//	            String PCH_COUNT = getOld.getString(1, "PCH_COUNT");
//	            String PCH_PRICE = getOld.getString(1, "PCH_PRICE");
//	            String PLAN_PRICE = getOld.getString(1, "PLAN_PRICE");
//	            String PCH_AMOUNT = getOld.getString(1, "PCH_AMOUNT");
//	            String PLAN_AMOUNT = getOld.getString(1, "PLAN_AMOUNT");
	            if(REMARKS==null){
	            	REMARKS = "";
	            }
	            dao.updateReceiveOrderPart(DETAIL_ID,REC_ID,COUNT,REMARKS,user);
	            /**
	             * 更改订单主表中的采购数量采购总金额计划总金额
	             */
	            atx.setOutMsg("", "保存成功！");
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    public void receivePartDelete() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        try
	        {
				String DETAIL_ID = Pub.val(request, "DETAIL_ID");
	            dao.receivePartDelete(DETAIL_ID);
				atx.setOutMsg("","配件删除成功！");
	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    public void getPrintInfo() throws Exception {
	        //定义request对象
	        RequestWrapper request = atx.getRequest();
	        //获取session中的user对象
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        //定义查询分页对象
	        PageManager page = new PageManager();
	        //将request流中的信息转化为where条件
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        try {
	            String REC_ID = Pub.val(request, "REC_ID");
	            page.setPageRows(99999);
	            //BaseResultSet：结果集封装对象
	            BaseResultSet bs = dao.getPrintInfo(page, user, conditions, REC_ID);
	            //输出结果集，第一个参数”bs”为固定名称，不可修改
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    public void printReceive(String REC_ID) throws Exception {
	        //获取封装后的request对象
	        RequestWrapper request = atx.getRequest();
	        //获取封装后的response对象
	        //ResponseWrapper response = atx.getResponse();
	        //获取当前登录user对象
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try {
	            PtBuPchReceiveOrderVO sVO = new PtBuPchReceiveOrderVO();
	            sVO.setRecId(REC_ID);
	            sVO.setPrintStatus(DicConstant.DYZT_02);
	            sVO.setUpdateTime(Pub.getCurrentDate());
	            sVO.setUpdateUser(user.getAccount());
	            dao.updateReceive(sVO);
	            //返回插入结果和成功信息
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    /**
	     * 
	     * @date()2014年10月21日下午11:15:36
	     * @author Administrator
	     * @to_do: 领料单打印
	     * @throws Exception
	     */
	    public void printReceivePdf() throws Exception{
			RequestWrapper request = atx.getRequest();
			ResponseWrapper response =  atx.getResponse();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String REC_ID = Pub.val(request, "REC_ID");
			//设置字体
			String fontPath = "simsun.ttc";
			String url = request.getHttpRequest().getRealPath("css");
			BaseFont baseFont = BaseFont.createFont(url+"/"+fontPath+ ",0","Identity-H", false);
	        Font chineseFont = new Font(baseFont, 10, Font.NORMAL, Color.BLACK);
	        Font chineseFontBig = new Font(baseFont, 14, Font.BOLD, Color.BLACK);
	        //设置PDF页面大小、左、右、上和下页边距
			Document document = new Document(new Rectangle(637.79541F,340.33072F), 36.865997F, 38.849998F, 44.849998F, 36.849998F);
			ByteArrayOutputStream  ba = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, ba);
			document.open();
			//设置table中每个单元格列宽
			float[] widths = {35F, 35F, 55F,55F, 55F,55F, 45F,45F,38F,38F,38F,38F,38F};
			PdfPTable table = new PdfPTable(widths);
			table.setLockedWidth(true);
			table.setTotalWidth(widths);
			//查询领料票主信息
			QuerySet info = dao.queryRecOrderInfo(REC_ID,user);
			String PERSON_NAME = info.getString(1, "PERSON_NAME");
			String SPLIT_NO = info.getString(1, "SPLIT_NO");
			String REC_NO = info.getString(1, "REC_NO");
			String SUPPLIER_NAME = info.getString(1, "SUPPLIER_NAME");
			String printDate = info.getString(1, "PRINT_DATE");
			String [] printDates =printDate.split("-");
			//查询领料票明细信息
			QuerySet qs = dao.getPrintDtl(REC_ID);
			if(qs.getRowCount()>0){
				//按10条分页，取余算共几页
				int rows = qs.getRowCount();
				int pageRow = rows/10;
				int yu = rows%10;
				if(yu>0){
					pageRow = pageRow+1;
				}
				int pageNum = 1;
				for (int i = 0; i < qs.getRowCount(); i++) {
					//按10条分页，重新设置表头
					if(i%10==0){
						/***9+3*************************/
						PdfPCell cellA0 = new PdfPCell(new Paragraph("陕西重型汽车有限公司（"+SUPPLIER_NAME+"）材料领用单",chineseFontBig));
						//设置不换行
						cellA0.setNoWrap(true);
						cellA0.setFixedHeight(28.0F);
						//设置边框
						cellA0.setBorder(0);
						//设置文本水平位置
						cellA0.setHorizontalAlignment(Element.ALIGN_CENTER);
						//设置合并列
						cellA0.setColspan(10);
						table.addCell(cellA0);
						PdfPCell cellA1 = new PdfPCell(new Paragraph("NO："+REC_NO,chineseFontBig));
						cellA1.setNoWrap(true);
						cellA0.setFixedHeight(18.0F);
						cellA1.setBorder(0);
						cellA1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cellA1.setColspan(3);
						table.addCell(cellA1);
						/***6+3+3*************************/
						PdfPCell cellB0 = new PdfPCell(new Paragraph("领用单位：陕西重型汽车有限公司销售公司销售服务部",chineseFont));
						cellB0.setNoWrap(true);
						cellB0.setBorderWidth(0);
						cellB0.setHorizontalAlignment(Element.ALIGN_LEFT);
						cellB0.setColspan(7);
						table.addCell(cellB0);
						PdfPCell cellB1 = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日",chineseFont));
						cellB1.setNoWrap(true);
						cellB1.setBorderWidth(0);
						cellB1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cellB1.setColspan(3);
						table.addCell(cellB1);
						PdfPCell cellB2 = new PdfPCell(new Paragraph("第"+pageNum+"页，共"+pageRow+"页",chineseFont));
						cellB2.setNoWrap(true);
						cellB2.setBorderWidth(0);
						cellB2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cellB2.setColspan(3);
						table.addCell(cellB2);
						/***12***********/
						PdfPCell cellC1 = new PdfPCell(new Paragraph("采领计划号:"+SPLIT_NO, chineseFont));
						cellC1.setNoWrap(true);
						cellC1.setBorderWidth(0F);
						cellC1.setHorizontalAlignment(Element.ALIGN_LEFT);
						cellC1.setColspan(13);
						table.addCell(cellC1);
						
						
						PdfPCell cell0 = new PdfPCell(new Paragraph("序号",chineseFont));
						cell0.setFixedHeight(14.0F);
						cell0.setBorderWidth(0.5F);
						cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell0);
						PdfPCell cell1 = new PdfPCell(new Paragraph("计配号", chineseFont));
						cell1.setBorderWidth(0.5F);
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell1);
						PdfPCell cell2 = new PdfPCell(new Paragraph("配件编号", chineseFont));
						cell2.setColspan(2);
						cell2.setBorderWidth(0.5F);
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell2);
						PdfPCell cell3 = new PdfPCell(new Paragraph("配件名称", chineseFont));
						cell3.setColspan(3);
						cell3.setBorderWidth(0.5F);
						cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell3);
						PdfPCell cell4 = new PdfPCell(new Paragraph("单位", chineseFont));
						cell4.setBorderWidth(0.5F);
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell4);
						PdfPCell cell5 = new PdfPCell(new Paragraph("实发", chineseFont));
						cell5.setBorderWidth(0.5F);
						cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell5);
						PdfPCell cell6 = new PdfPCell(new Paragraph("实收", chineseFont));
						cell6.setBorderWidth(0.5F);
						cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell6);
						PdfPCell cell7 = new PdfPCell(new Paragraph("单价", chineseFont));
						cell7.setBorderWidth(0.5F);
						cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell7);
						PdfPCell cell8 = new PdfPCell(new Paragraph("金额", chineseFont));
						cell8.setBorderWidth(0.5F);
						cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell8);
						PdfPCell cell9 = new PdfPCell(new Paragraph("备注", chineseFont));
						cell9.setBorderWidth(0.5F);
						cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell9);
						pageNum=pageNum+1;
						
						
					}
					/**序号**/
					PdfPCell cella = new PdfPCell(new Paragraph(String.valueOf(i+1),chineseFont));
					cella.setFixedHeight(14.0F);
					cella.setBorderWidth(0.5F);
					cella.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cella);
					/**计配号**/
					PdfPCell cellb = new PdfPCell(new Paragraph(qs.getString(i+1, "PB_NO"), chineseFont));
					cellb.setBorderWidth(0.5F);
					table.addCell(cellb);
					/**配件代码**/
					PdfPCell cellc = new PdfPCell(new Paragraph(qs.getString(i+1, "PART_CODE"), chineseFont));
					cellc.setColspan(2);
					cellc.setBorderWidth(0.5F);
					table.addCell(cellc);
					/**配件名称**/
					PdfPCell celld = new PdfPCell(new Paragraph(qs.getString(i+1, "PART_NAME"), chineseFont));
					celld.setColspan(3);
					celld.setBorderWidth(0.5F);
					table.addCell(celld);
					/**单位**/
					PdfPCell celle = new PdfPCell(new Paragraph(qs.getString(i+1, "UNIT"), chineseFont));
					celle.setBorderWidth(0.5F);
					celle.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(celle);
					/**实发数量**/
					PdfPCell cellf = new PdfPCell(new Paragraph(qs.getString(i+1, "PCH_COUNT"), chineseFont));
					cellf.setBorderWidth(0.5F);
					table.addCell(cellf);
					/**实收**/
					PdfPCell cellg = new PdfPCell(new Paragraph("", chineseFont));
					cellg.setBorderWidth(0.5F);
					cellg.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellg);
					/**单价**/
					PdfPCell cellh = new PdfPCell(new Paragraph("", chineseFont));
					cellh.setBorderWidth(0.5F);
					cellh.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellh);
					/**金额**/
					PdfPCell celli = new PdfPCell(new Paragraph("", chineseFont));
					celli.setBorderWidth(0.5F);
					celli.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(celli);
					PdfPCell cellj = new PdfPCell(new Paragraph(qs.getString(i+1, "REMARKS"), chineseFont));
					cellj.setBorderWidth(0.5F);
					cellj.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellj);
					
					if((i+1)%10==0&&(i+1)>0||i==qs.getRowCount()-1){
						/**备注**/
						PdfPCell cellD0 = new PdfPCell(new Paragraph("备注:",chineseFont));
						cellD0.setNoWrap(true);
						cellD0.setBorderWidth(0.5F);
						cellD0.setFixedHeight(35.0f);
						cellD0.setHorizontalAlignment(Element.ALIGN_LEFT);
						cellD0.setColspan(13);
						table.addCell(cellD0);
						/**表尾**/
						PdfPCell cellE0 = new PdfPCell(new Paragraph("主管:",chineseFont));
						cellE0.setNoWrap(true);
						cellE0.setBorderWidth(0F);
						cellE0.setHorizontalAlignment(Element.ALIGN_LEFT);
						cellE0.setColspan(2);
						table.addCell(cellE0);
						PdfPCell cellE1 = new PdfPCell(new Paragraph("仓库记账:", chineseFont));
						cellE1.setNoWrap(true);
						cellE1.setBorderWidth(0F);
						cellE1.setHorizontalAlignment(Element.ALIGN_LEFT);
						cellE1.setColspan(2);
						table.addCell(cellE1);
						PdfPCell cellE2 = new PdfPCell(new Paragraph("发料人:", chineseFont));
						cellE2.setNoWrap(true);
						cellE2.setBorderWidth(0F);
						cellE2.setHorizontalAlignment(Element.ALIGN_LEFT);
						cellE2.setColspan(3);
						table.addCell(cellE2);
						PdfPCell cellE3 = new PdfPCell(new Paragraph("领料人:", chineseFont));
						cellE3.setNoWrap(true);
						cellE3.setBorderWidth(0F);
						cellE3.setHorizontalAlignment(Element.ALIGN_LEFT);
						cellE3.setColspan(3);
						table.addCell(cellE3);
						PdfPCell cellE4 = new PdfPCell(new Paragraph("制单人:"+PERSON_NAME, chineseFont));
						cellE4.setNoWrap(true);
						cellE4.setBorderWidth(0F);
						cellE4.setHorizontalAlignment(Element.ALIGN_LEFT);
						cellE4.setColspan(3);
						table.addCell(cellE4);
//						PdfPCell cellE5 = new PdfPCell(new Paragraph("接收员:", chineseFont));
//						cellE5.setNoWrap(true);
//						cellE5.setBorderWidth(0.5F);
//						cellE5.setColspan(3);
//						cellE5.setHorizontalAlignment(Element.ALIGN_LEFT);
//						table.addCell(cellE5);
					}
				}
				document.add(table);
				document.close();
				response.getHttpResponse().reset();
				response.setContentType("application/pdf");
				//设置保存的名称
				response.addHeader("Content-Disposition","inline;filename=ceshi.pdf"); 
				response.setContentLength(ba.size());
				try {
					ServletOutputStream out = response.getHttpResponse().getOutputStream();
					ba.writeTo(out);
					out.flush();
					out.close();
					printReceive(REC_ID);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	    public void supplierSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				String account = Pub.val(request, "ACCOUNT");
				BaseResultSet bs = dao.supplierSearch(page,user,conditions,account);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    public void armySupplierSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				String account = Pub.val(request, "ACCOUNT");
				BaseResultSet bs = dao.armySupplierSearch(page,user,conditions,account);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    //updateSplitNoChange
	    public void updateSplitNoChange() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try
	        {
				String PURCHASE_ID = Pub.val(request, "PURCHASE_ID");
				QuerySet checkSQ = dao.checkSQ(PURCHASE_ID);
				if(checkSQ.getRowCount()>0){
					dao.updateSplitNoSQGHChange(PURCHASE_ID,user);
				}else{
					dao.updateSplitNoChange(PURCHASE_ID,user);
				}
				
				PtBuPchOrderSplitVO pVo = new PtBuPchOrderSplitVO();
				atx.setOutMsg(pVo.getRowXml(),"订单提报成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    public void splitOrderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				String purchaseId = Pub.val(request, "PURCHASE_ID");
				BaseResultSet bs = dao.splitOrderSearch(page,user,conditions,purchaseId);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    //updateRepuirementTime
	    public void updateRepuirementTime() throws Exception {
	        //获取封装后的request对象
	        RequestWrapper request = atx.getRequest();
	        //获取当前登录user对象
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try {
	            HashMap<String, String> hm;
	            //将request流转换为hashmap结构体
	            hm = RequestUtil.getValues(request);
	            PtBuPchOrderSplitVO s_vo = new PtBuPchOrderSplitVO();
	            s_vo.setValue(hm);
	            dao.updatePurchaseOrderSplit(s_vo);
	            atx.setOutMsg("", "修改成功！");
	        } catch (Exception e) {
	            //设置失败异常处理
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
}

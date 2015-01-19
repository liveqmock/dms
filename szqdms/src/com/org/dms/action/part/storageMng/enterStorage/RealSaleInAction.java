package com.org.dms.action.part.storageMng.enterStorage;


import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.enterStorage.RealSaleInDao;
import com.org.dms.vo.part.PtBuDealerStockChangeVO;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.part.PtBuRealSaleDtlVO;
import com.org.dms.vo.part.PtBuRealSaleReturnVO;
import com.org.frameImpl.Constant;
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
 * 实销退件入库  
 *
 * @user : 王晶鑫
 */
public class RealSaleInAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "SaleStockOutMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private RealSaleInDao dao = RealSaleInDao.getInstance(atx);
    
    
	/*
	 * 查询实销单
	 * 
	 */
	public void realSalesearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.realSalesearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /*
     * 
     * 实销入库退件保存
     */
	 public void realSalebackInsert() throws Exception
	    {
	        //获取封装后的request对象
	        RequestWrapper request = atx.getRequest();
	        //获取封装后的response对象
	        //获取当前登录user对象
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PtBuDealerStockVO pbsVo =new PtBuDealerStockVO();
			PtBuRealSaleDtlVO vo =new PtBuRealSaleDtlVO();
	        try {
	            HashMap<String, String> hm;
	            hm = RequestUtil.getValues(request);
	            // 配件主键
				String saleId= hm.get("SALEID");
	            String partIds = hm.get("PARTIDS");
	            // 数量
	            String salecounts = hm.get("SALECOUNTS");
	            //金额
	            String amounts=hm.get("AMOUNTS");
	            QuerySet querySet = dao.realSalePartCheck(saleId,user);
	            if (querySet.getRowCount() > 0) {
	            	if (querySet.getString(1, "COUNT").contains("-") == false) {
	            		throw new Exception("销售日期超过退件期限.");
	            	}
	            }
	            String[] partIdArr = partIds.split(",");
	            String[] countArr = salecounts.split(",");
	            String[] amountArr = amounts.split(",");
	            for (int i = 0; i < partIdArr.length; i++) {
	            	 QuerySet qs =dao.realSalePartIsHave(partIdArr[i], saleId,user);
	            	 //配件已经存在时对其进行累加，更新数量
	            	 if(qs.getRowCount()>0){
//	            		//原实销数量
//	            		 String salecount=qs.getString(1, 1);
//	            		 //原实销金额
//	            		 String saleamout=qs.getString(1, 2);
	            		//库存有效数量
	            		 String avamount=qs.getString(1, 7);
	            		 //库存配件总数
	            		 String partamount =qs.getString(1, 8);
	            		 //实销明细ID
//	            		 String dtlId =qs.getString(1, 3);
//	            		 Double salecountup =Double.parseDouble(salecount)-Double.parseDouble(countArr[i].toString());
//	            		 Double saleamoutup =Double.parseDouble(saleamout)-Double.parseDouble(amountArr[i].toString());
//	            		 vo.setAmount(saleamoutup.toString());
//	            		 vo.setSaleCount(salecountup.toString());
//	            		 vo.setDtlId(dtlId);
	            		 //对库存进行变更
	                	 Double avaiamount =Double.parseDouble(avamount)+Double.parseDouble(countArr[i].toString());
	                	 Double partAllamount =Double.parseDouble(partamount)+Double.parseDouble(countArr[i].toString());
	                	 dao.updateStock(partIdArr[i],avaiamount.toString(),partAllamount.toString(),user);
//	            		 dao.realSalePartUpdate(vo);
	                	 QuerySet getRealSale = dao.getRealSale(saleId,partIdArr[i]);
	                	 PtBuRealSaleReturnVO r_vo = new PtBuRealSaleReturnVO();
	                	 r_vo.setSaleId(saleId);
	                	 r_vo.setCustomerName(getRealSale.getString(1, "CUSTOMER_NAME"));
	                	 r_vo.setPartId(partIdArr[i]);
	                	 r_vo.setReturnCount(countArr[i]);
	                	 r_vo.setPartCode(getRealSale.getString(1, "PART_CODE"));
	                	 r_vo.setPartName(getRealSale.getString(1, "PART_NAME"));
	                	 r_vo.setUnit(getRealSale.getString(1, "UNIT"));
	                	 r_vo.setSalePrice(getRealSale.getString(1, "SALE_PRICE"));
	                	 r_vo.setReturnAmount(String.valueOf(Integer.parseInt(countArr[i])*Float.parseFloat(getRealSale.getString(1, "SALE_PRICE"))));
	                	 dao.insertRealSaleBack(r_vo);
	            		 
	            		 
		  	              //插入轨迹表
	 	  	            PtBuDealerStockChangeVO pbdscVo =new PtBuDealerStockChangeVO();
	 	            	  pbdscVo.setOrgId(user.getOrgId());
	 	            	  pbdscVo.setOrgCode(user.getOrgCode());
	 	            	 pbdscVo.setOrgName(user.getOrgDept().getOName());
 	            	     pbdscVo.setStockId(qs.getString(1, "STOCK_ID"));
	 	            	  pbdscVo.setPartId(partIdArr[i]);
	 	            	  pbdscVo.setCount(countArr[i]);
	 	            	  pbdscVo.setPartCode(getRealSale.getString(1, "PART_CODE"));
	 	            	  pbdscVo.setPartName(getRealSale.getString(1, "PART_NAME"));
	 	            	  pbdscVo.setSupplierId(getRealSale.getString(1, "SUPPLIER_ID"));
	 	            	  pbdscVo.setSupplierCode(getRealSale.getString(1, "SUPPLIER_CODE"));
	 	            	  pbdscVo.setSupplierName(getRealSale.getString(1, "SUPPLIER_NAME"));
	 	            	  pbdscVo.setInNo(getRealSale.getString(1, "SALE_NO"));
	 	            	  pbdscVo.setApplyDate(Pub.getCurrentDate());
	 	            	  pbdscVo.setApplyType(DicConstant.CZLX_01);
	 	            	  pbdscVo.setStorageType(DicConstant.QDCRKLX_04);
	 	            	  pbdscVo.setCreateUser(user.getAccount());
	 	            	  pbdscVo.setCreateTime(Pub.getCurrentDate());
	 	            	  pbdscVo.setStatus(Constant.YXBS_01);
	 	  	               dao.stockChangeInsert(pbdscVo);
	 	            	  atx.setOutMsg(vo.getRowXml(),"实销退件入库完成");
	            	 }
	            
	            }
	          
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
		}
	/*
	 * 查询实销单
	 * 
	 */
	public void realSaleDtlsearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String saleId=request.getParamValue("saleId");
		RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.realSaleDtlsearch(page,user,saleId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/*
	 * 查询配件库存
	 * 
	 */
	public void realSalePartsearch() throws Exception
    {
		  RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.realSalePartsearch(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
	}
	
}
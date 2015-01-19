package com.org.dms.action.part.purchaseMng;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.purchaseMng.purchaseOrder.PurchaseOrderMngDao;
import com.org.dms.dao.part.purchaseMng.purchaseReturn.PurchaseOrderReturnMngDao;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExcelErrors;
import com.org.mvc.context.ActionContext;

public class PurchaseOrderImpCheckAction {
	
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
    //上下文对象
    private static ActionContext atx = ActionContext.getContext();
    //定义采购订单dao对象
    private static PurchaseOrderMngDao orderdao = PurchaseOrderMngDao.getInstance(atx);
    //定义采购退货单dao对象
    private static PurchaseOrderReturnMngDao checkdao = PurchaseOrderReturnMngDao.getInstance(atx);
	/**
	 * 
	 * @date()2014年7月28日下午10:38:01
	 * @author Administrator
	 * @to_do:民品采购订单导入校验方法
	 * @param user
	 * @param bParams
	 * @return
	 * @throws Exception
	 */
	public List<ExcelErrors> checkData(User user,String bParams) throws Exception {
		// TODO Auto-generated method stub
		ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet getId = orderdao.getSup(bParams);
		String SUPPLIER_ID = getId.getString(1, "SUPPLIER_ID");
		QuerySet check01 = orderdao.checkExist(user);//校验导入配件是否存在配件主信息表
		if(check01.getRowCount()>0){
			errors=new ExcelErrors();
			for(int i = 1;i<=check01.getRowCount();i++){
				String ROW_NUM = check01.getString(i, "NUM");
				errors.setRowNum(Integer.parseInt(ROW_NUM));
				errors.setErrorDesc("第"+ROW_NUM+"行配件代码不存在！");
			}
			errorList.add(errors);
		}
		QuerySet check02 = orderdao.checkAttribute(user);//校验配件的采购属性
		if(check02.getRowCount()>0){
			errors=new ExcelErrors();
			for(int i = 1;i<=check02.getRowCount();i++){
				String ROW_NUM = check02.getString(i, "NUM");
				String PART_CODE = check02.getString(i, "PART_CODE");
				errors.setRowNum(Integer.parseInt(ROW_NUM));
				errors.setErrorDesc("第"+ROW_NUM+"行配件"+PART_CODE+"采购属性未维护！");
			}
			errorList.add(errors);
		}
		QuerySet check03 = orderdao.checkPchCycle(user,SUPPLIER_ID);//校验配件的供货周期
		if(check03.getRowCount()>0){
			errors=new ExcelErrors();
			for(int i = 1;i<=check03.getRowCount();i++){
				String ROW_NUM = check03.getString(i, "NUM");
				String PART_CODE = check03.getString(i, "PART_CODE");
				errors.setRowNum(Integer.parseInt(ROW_NUM));
				errors.setErrorDesc("第"+ROW_NUM+"行配件"+PART_CODE+"采购周期未维护！");
			}
			errorList.add(errors);
		}
		QuerySet check04 = orderdao.checkPchPrice(user,SUPPLIER_ID);//校验配件的采购价格
		if(check04.getRowCount()>0){
			errors=new ExcelErrors();
			for(int i = 1;i<=check04.getRowCount();i++){
				String ROW_NUM = check04.getString(i, "NUM");
				String PART_CODE = check04.getString(i, "PART_CODE");
				errors.setRowNum(Integer.parseInt(ROW_NUM));
				errors.setErrorDesc("第"+ROW_NUM+"行配件"+PART_CODE+"采购价格未维护！");
			}
			errorList.add(errors);
		}
		QuerySet check05 = orderdao.checkPlanPrice(user,SUPPLIER_ID);//校验配件的计划价格
		if(check05.getRowCount()>0){
			errors=new ExcelErrors();
			for(int i = 1;i<=check05.getRowCount();i++){
				String ROW_NUM = check05.getString(i, "NUM");
				String PART_CODE = check05.getString(i, "PART_CODE");
				errors.setRowNum(Integer.parseInt(ROW_NUM));
				errors.setErrorDesc("第"+ROW_NUM+"行配件"+PART_CODE+"计划采购价格未维护！");
			}
			errorList.add(errors);
		}
		QuerySet check06 = orderdao.checkUnique(user,bParams);//校验配件的计划价格
		if(check06.getRowCount()>0){
			errors=new ExcelErrors();
			for(int i = 1;i<=check06.getRowCount();i++){
				String ROW_NUM = check06.getString(i, "NUM");
				String PART_CODE = check06.getString(i, "PART_CODE");
				errors.setRowNum(Integer.parseInt(ROW_NUM));
				errors.setErrorDesc("第"+ROW_NUM+"行配件"+PART_CODE+"已经在此订单中维护！");
			}
			errorList.add(errors);
		}
		if(errorList!=null&&errorList.size()>0){
			return errorList;
		}else{
			return null;
		}
	}
	/**
	 * 
	 * @date()2014年7月30日上午10:59:51
	 * @author Administrator
	 * @to_do:军品订单导入校验方法
	 * @param user
	 * @param bParams
	 * @return
	 * @throws Exception
	 */
	public static List<ExcelErrors> checkArmyData(User user,String bParams) throws Exception {
		// TODO Auto-generated method stub
		ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet getId = orderdao.getSup(bParams);
		String SUPPLIER_ID = getId.getString(1, "SUPPLIER_ID");
		QuerySet check01 = orderdao.checkExist(user);//校验导入配件是否存在配件主信息表
		if(check01.getRowCount()>0){
			errors=new ExcelErrors();
			for(int i = 1;i<=check01.getRowCount();i++){
				String ROW_NUM = check01.getString(i, "NUM");
				errors.setRowNum(Integer.parseInt(ROW_NUM));
				errors.setErrorDesc("第"+ROW_NUM+"行配件代码不存在！");
			}
			errorList.add(errors);
		}
		QuerySet check07 = orderdao.checkArmy(user);//校验导入配件是否存在配件主信息表
		if(check07.getRowCount()>0){
			errors=new ExcelErrors();
			for(int i = 1;i<=check07.getRowCount();i++){
				String ROW_NUM = check07.getString(i, "NUM");
				String PART_CODE = check07.getString(i, "PART_CODE");
				errors.setRowNum(Integer.parseInt(ROW_NUM));
				errors.setErrorDesc("第"+ROW_NUM+"行配件"+PART_CODE+"为民品配件！");
			}
			errorList.add(errors);
		}
		QuerySet check02 = orderdao.checkAttribute(user);//校验配件的采购属性
		if(check02.getRowCount()>0){
			errors=new ExcelErrors();
			for(int i = 1;i<=check02.getRowCount();i++){
				String ROW_NUM = check02.getString(i, "NUM");
				String PART_CODE = check02.getString(i, "PART_CODE");
				errors.setRowNum(Integer.parseInt(ROW_NUM));
				errors.setErrorDesc("第"+ROW_NUM+"行配件"+PART_CODE+"采购属性未维护！");
			}
			errorList.add(errors);
		}
		QuerySet check03 = orderdao.checkPchCycle(user,SUPPLIER_ID);//校验配件的供货周期
		if(check03.getRowCount()>0){
			errors=new ExcelErrors();
			for(int i = 1;i<=check03.getRowCount();i++){
				String ROW_NUM = check03.getString(i, "NUM");
				String PART_CODE = check03.getString(i, "PART_CODE");
				errors.setRowNum(Integer.parseInt(ROW_NUM));
				errors.setErrorDesc("第"+ROW_NUM+"行配件"+PART_CODE+"采购周期未维护！");
			}
			errorList.add(errors);
		}
		QuerySet check04 = orderdao.checkPchPrice(user,SUPPLIER_ID);//校验配件的采购价格
		if(check04.getRowCount()>0){
			errors=new ExcelErrors();
			for(int i = 1;i<=check04.getRowCount();i++){
				String ROW_NUM = check04.getString(i, "NUM");
				String PART_CODE = check04.getString(i, "PART_CODE");
				errors.setRowNum(Integer.parseInt(ROW_NUM));
				errors.setErrorDesc("第"+ROW_NUM+"行配件"+PART_CODE+"采购价格未维护！");
			}
			errorList.add(errors);
		}
		QuerySet check05 = orderdao.checkPlanPrice(user,SUPPLIER_ID);//校验配件的计划价格
		if(check05.getRowCount()>0){
			errors=new ExcelErrors();
			for(int i = 1;i<=check05.getRowCount();i++){
				String ROW_NUM = check05.getString(i, "NUM");
				String PART_CODE = check05.getString(i, "PART_CODE");
				errors.setRowNum(Integer.parseInt(ROW_NUM));
				errors.setErrorDesc("第"+ROW_NUM+"行配件"+PART_CODE+"计划采购价格未维护！");
			}
			errorList.add(errors);
		}
		QuerySet check06 = orderdao.checkUnique(user,bParams);//校验配件的计划价格
		if(check06.getRowCount()>0){
			errors=new ExcelErrors();
			for(int i = 1;i<=check06.getRowCount();i++){
				String ROW_NUM = check06.getString(i, "NUM");
				String PART_CODE = check06.getString(i, "PART_CODE");
				errors.setRowNum(Integer.parseInt(ROW_NUM));
				errors.setErrorDesc("第"+ROW_NUM+"行配件"+PART_CODE+"已经在此订单中维护！");
			}
			errorList.add(errors);
		}
		if(errorList!=null&&errorList.size()>0){
			return errorList;
		}else{
			return null;
		}
	}
	
	public List<ExcelErrors> checkReturnData(User user,String bParams) throws Exception {
		// TODO Auto-generated method stub
		ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		try
		{
			QuerySet querysId =checkdao.querySid(bParams);
			String sId = querysId.getString(1, "SUPPLIER_ID");
			QuerySet qs3 = checkdao.queryId();
			String wId = qs3.getString(1, "WAREHOUSE_ID");
			//校验填写的配件在本退货单中是否存在供货关系。
			QuerySet ifSuply=checkdao.queryIfSuply(user,bParams);
			if(ifSuply.getRowCount()>0){
				for(int i=0;i<ifSuply.getRowCount();i++){
					errors=new ExcelErrors();
					String rowNo=ifSuply.getString(i+1, "ROW_NO"); 
					String partCode=ifSuply.getString(i+1, "PART_CODE"); 
					errors.setRowNum(Integer.parseInt(rowNo));
					errors.setErrorDesc("配件代码:"+partCode+"在本退货单中不存在供货关系！");
					errorList.add(errors);
				}
			}
			//校验退货单填写是否正确
			QuerySet qs =checkdao.checkList1(user);
			if(qs.getRowCount()>0){
				for(int i=0;i<qs.getRowCount();i++){
					errors=new ExcelErrors();
					String rowNo=qs.getString(i+1, "ROW_NO"); 
					String returnNo=qs.getString(i+1, "RETURN_NO"); 
					errors.setRowNum(Integer.parseInt(rowNo));
					errors.setErrorDesc("退货单号:"+returnNo+"不存在。");
					errorList.add(errors);
				}
			}
			//校验配件代码填写是否正确
			QuerySet qs1=checkdao.checkPartCode(user);
			if(qs1.getRowCount()>0){
				if(qs1.getRowCount()>0){
					for(int i=0;i<qs1.getRowCount();i++){
						errors=new ExcelErrors();
						String rowNo=qs1.getString(i+1, "ROW_NO"); 
						String PART_CODE=qs1.getString(i+1, "PART_CODE"); 
						errors.setRowNum(Integer.parseInt(rowNo));
						errors.setErrorDesc("配件代码:"+PART_CODE+"不存在。");
						errorList.add(errors);
					}
				}
			}
			//校验配件代码与库位代码是否存在对应关系
			QuerySet qs2 =checkdao.checkPartPoCode(user);
			if(qs2.getRowCount()>0){
				if(qs2.getRowCount()>0){
					for(int i=0;i<qs2.getRowCount();i++){
						errors=new ExcelErrors();
						String rowNo=qs2.getString(i+1, "ROW_NO"); 
						String PART_CODE=qs2.getString(i+1, "PART_CODE"); 
						String POSITION_CODE=qs2.getString(i+1, "POSITION_CODE");
						errors.setRowNum(Integer.parseInt(rowNo));
						errors.setErrorDesc("配件代码:"+PART_CODE+"与库位代码:"+POSITION_CODE+"不存在对应关系！");
						errorList.add(errors);
					}
				}
			}
			//校验填写的退货数是否满足可退货数（已入库数-已退货数）的条件！
			QuerySet qs4=checkdao.checkKthAmount(user,sId,wId);
			if(qs4.getRowCount()>0){
				for(int i=0;i<qs4.getRowCount();i++){
					errors=new ExcelErrors();
					String rowNo=qs4.getString(i+1, "ROW_NO"); 
					String PART_CODE=qs4.getString(i+1, "PART_CODE"); 
					String POSITION_CODE=qs4.getString(i+1, "POSITION_CODE");
					String RETURN_COUNT=qs4.getString(i+1, "RETURN_COUNT");
					String SL=qs4.getString(i+1, "SL");
					errors.setRowNum(Integer.parseInt(rowNo));
					errors.setErrorDesc("配件代码:"+PART_CODE+"退货数量:"+RETURN_COUNT+"大于库位代码:"+POSITION_CODE+"中可退货数:"+SL+"！");
					errorList.add(errors);
				}	
			}
			//校验填写的退货数是否满足可用库存的条件！
			QuerySet qs5=checkdao.checkAvaAmount(user,sId,wId);
			if(qs5.getRowCount()>0){
				for(int i=0;i<qs5.getRowCount();i++){
					errors=new ExcelErrors();
					String rowNo=qs5.getString(i+1, "ROW_NO"); 
					String PART_CODE=qs5.getString(i+1, "PART_CODE"); 
					String POSITION_CODE=qs5.getString(i+1, "POSITION_CODE");
					String AVAILABLE_AMOUNT=qs5.getString(i+1, "AVAILABLE_AMOUNT");
					String RETURN_COUNT=qs5.getString(i+1, "RETURN_COUNT");
					errors.setRowNum(Integer.parseInt(rowNo));
					errors.setErrorDesc("配件代码:"+PART_CODE+"退货数:"+RETURN_COUNT+"大于库位代码:"+POSITION_CODE+"的可用库存数:"+AVAILABLE_AMOUNT+"!");
					errorList.add(errors);
				}	
			}
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
			if(errorList!=null&&errorList.size()>0){
				return errorList;
			}else{
				return null;
		}
	}
}

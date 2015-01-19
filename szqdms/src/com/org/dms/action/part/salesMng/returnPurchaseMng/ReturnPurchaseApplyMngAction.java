package com.org.dms.action.part.salesMng.returnPurchaseMng;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.salesMng.returnPurchaseMng.ReturnPurchaseApplyMngDao;
import com.org.dms.vo.part.PtBuReturnApplyDtlVO;
import com.org.dms.vo.part.PtBuReturnApplyVO;
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
 * 退件申请Action
 *
 * 服务站和配送中心退件申请
 * @author zhengyao
 * @date 2014-07-30
 * @version 1.0
 */
public class ReturnPurchaseApplyMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义仓库管理Dao对象
    private ReturnPurchaseApplyMngDao returnPurchaseApplyMngDao = ReturnPurchaseApplyMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 定义response对象
    ResponseWrapper responseWrapper= actionContext.getResponse();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 退件申请明细(错误数据)
     * @throws Exception
     */
    public void expData()throws Exception{

        try {
            String seqs = Pub.val(requestWrapper, "seqs");
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(0,hBean);

            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_CODE");
            hBean.setTitle("供应商代码");
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("RETURN_COUNT");
            hBean.setTitle("退货数量");
            header.add(2,hBean);

            QuerySet querySet = returnPurchaseApplyMngDao.expData(seqs,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "CWSJDC", header, querySet);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 退件申请明细更新(导入)
     * @throws Exception
     */
    public void insertImport() throws Exception {

        try {
            // 预测主键
            String returnId = Pub.val(requestWrapper, "returnId");
            String tmpNo = requestWrapper.getParamValue("tmpNo");
            String sql = "";
        	if (!"".equals(tmpNo)&&tmpNo!=null) {
        		sql = " AND A.TMP_NO NOT IN ("+tmpNo+") ";
        	}
            // 预测配件明细更新
            returnPurchaseApplyMngDao.updateReturnApplyDtl(returnId,user,sql);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 退件申请明细临时表查询(导入)
     * @throws Exception
     */
    public void searchImport() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = (BaseResultSet)returnPurchaseApplyMngDao.searchImport(pageManager, conditions,user);
            baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 修改退件申请
     * @throws Exception
     */
    public void updateReturnApply() throws Exception {

        try {
            // 退件申请表(pt_bu_return_apply)对应的实体
            PtBuReturnApplyVO ptBuReturnApplyVO = new PtBuReturnApplyVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBuReturnApplyVO.setValue(hashMap);
            // 通过Dao,执行更新
            returnPurchaseApplyMngDao.updateReturnApply(ptBuReturnApplyVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "修改成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 新增退件申请
     * @throws Exception
     */
    public void insertReturnPurchaseApply() throws Exception {

        try {
            // 退件申请表(pt_bu_return_apply)对应的实体
            PtBuReturnApplyVO ptBuReturnApplyVO = new PtBuReturnApplyVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBuReturnApplyVO.setValue(hashMap);
            if (returnPurchaseApplyMngDao.returnPurchaseCheck(user,ptBuReturnApplyVO.getReceiveOrgId()).getRowCount() > 0) {
                throw new Exception("请提报已保存的订单");
            }
            // 退件单号
            ptBuReturnApplyVO.setReturnNo(PartOddNumberUtil.getReturnPurchaseOrderNo(actionContext.getDBFactory(), user.getOrgCode()));
            // 申请方ID
            ptBuReturnApplyVO.setApplyOrgId(user.getOrgId());
            // 申请方代码
            ptBuReturnApplyVO.setApplyOrgCode(user.getOrgCode());
            // 申请方名称
            ptBuReturnApplyVO.setApplyOrgName(user.getPersonName());
            // 申请状态
            ptBuReturnApplyVO.setApplySatus(DicConstant.TJSQDZT_01);
            // 所属公司
            ptBuReturnApplyVO.setCompanyId(user.getCompanyId());
            // 创建时间
            ptBuReturnApplyVO.setCreateTime(Pub.getCurrentDate());
            // 创建人
            ptBuReturnApplyVO.setCreateUser(user.getAccount());
            // 状态
            ptBuReturnApplyVO.setStatus(DicConstant.YXBS_01);
            ptBuReturnApplyVO.setOrgId(user.getOrgId());
            ptBuReturnApplyVO.setOemCompanyId(user.getOemCompanyId());
            ptBuReturnApplyVO.setInvoiceStatus(DicConstant.KPZT_01);
            // 通过dao，执行插入
            returnPurchaseApplyMngDao.insertReturnPurchaseApply(ptBuReturnApplyVO);
            // 返回插入结果和成功信息
            actionContext.setOutMsg(ptBuReturnApplyVO.getRowXml(), "新增成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 查询销售订单
     * @throws Exception
     */
    public void searchInStockOrder() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = returnPurchaseApplyMngDao.searchSalesOrder(pageManager, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 退件申请查询
     * @throws Exception
     */
    public void searchReturnPurchaseApply1() throws Exception {

        try {
        	// 定义查询分页对象
            PageManager pageManager = new PageManager();
        	String returnId = Pub.val(requestWrapper, "RETURN_ID");
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = (BaseResultSet)returnPurchaseApplyMngDao.searchReturnPurchaseApply1(pageManager,returnId);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    
    /**
     * 退件申请查询
     * @throws Exception
     */
    public void searchReturnPurchaseApply() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = (BaseResultSet)returnPurchaseApplyMngDao.searchReturnPurchaseApply(pageManager, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    

    /**
     * 
     * searchReturnPurchaseApplyQuery:退件申请查询（车厂端功能）
     * @author fuxiao
     * Date:2014年11月16日
     *
     */
    public void searchReturnPurchaseApplyQuery() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = returnPurchaseApplyMngDao.searchReturnPurchaseApplyForQuery(pageManager, conditions, user);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    
    /**
     * 
     * searchReturnPurchaseApplyQueryForDealer:退件申请查询（经销商查询）
     * @author fuxiao
     * Date:2014年11月16日
     *
     */
    public void searchReturnPurchaseApplyQueryForDealer() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = returnPurchaseApplyMngDao.searchReturnPurchaseApplyForDealerQuery(pageManager, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }


    /**
     * 退件查询
     * @throws Exception
     */
    public void searchReturnPurchase() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            String returnId = Pub.val(requestWrapper, "returnId");
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = returnPurchaseApplyMngDao.searchReturnPurchase(pageManager, user, conditions,returnId);
            // 绑定配件预测状态
            baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);
            baseResultSet.setFieldDic("IF_RETURN", DicConstant.SF);
            baseResultSet.setFieldDic("MIN_UNIT", DicConstant.JLDW);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 新增退件申请明细
     * @throws Exception
     */
    public void insertReturnApplyDtl() throws Exception {

        try {
            // 退件申请单ID
            String returnId = hashMap.get("RETURNID");
            // 配件主键
            String partIds = hashMap.get("PARTIDS");
            // 配件代码
            String partCodes = hashMap.get("PARTCODES");
            // 配件名称
            String partNames = hashMap.get("PARTNAMES");
            // 供应商ID
            String supplierIds = hashMap.get("SUPPLIERIDS");
            // 供应商代码
            String supplierCodes = hashMap.get("SUPPLIERCODES");
            // 供应商名称
            String supplierNames = hashMap.get("SUPPLIERNAMES");
            // 单位
            String units = hashMap.get("UNITS");
            // 退货数量
            String returnCounts = hashMap.get("RETURNCOUNTS");
            // 经销商价
            String salePrices = hashMap.get("SALEPRICES");
            // 金额
            String amounts = hashMap.get("AMOUNTS");
            String[] partIdArr = partIds.split(",");
            String[] partCodeArr = partCodes.split(",");
            String[] partNameArr = partNames.split(",");
            String[] supplierIdArr = supplierIds.split(",");
            String[] supplierCodeArr = supplierCodes.split(",");
            String[] supplierNameArr = supplierNames.split(",");
            String[] unitArr = units.split(",");
            String[] returnCountArr = returnCounts.split(",");
            String[] salePriceArr = salePrices.split(",");
            String[] amountArr = amounts.split(",");
             for (int i = 0; i < partIdArr.length; i++) {
                 // 退件申请明细表(PT_BU_RETURN_APPLY_DTL)对应的实体
                 PtBuReturnApplyDtlVO ptBuReturnApplyDtlVO = new PtBuReturnApplyDtlVO();
                 ptBuReturnApplyDtlVO.setReturnId(returnId);
                 // 配件主键
                 ptBuReturnApplyDtlVO.setPartId(partIdArr[i]);
                 // 配件代码
                 ptBuReturnApplyDtlVO.setPartCode(partCodeArr[i]);
                 // 配件名称
                 ptBuReturnApplyDtlVO.setPartName(partNameArr[i]);
                 // 供应商ID
                 ptBuReturnApplyDtlVO.setSupplierId(supplierIdArr[i]);
                 // 供应商代码
                 ptBuReturnApplyDtlVO.setSupplierCode(supplierCodeArr[i]);
                 // 供应商名称
                 ptBuReturnApplyDtlVO.setSupplierName(supplierNameArr[i]);
                 // 单位
                 ptBuReturnApplyDtlVO.setUnit(unitArr[i]);
                 // 退货数量
                 ptBuReturnApplyDtlVO.setReturnCount(returnCountArr[i]);
                 // 经销商价
                 ptBuReturnApplyDtlVO.setSalePrice(salePriceArr[i]);
                 // 金额
                 ptBuReturnApplyDtlVO.setAmount(amountArr[i]);
                 // 创建人
                 ptBuReturnApplyDtlVO.setCreateUser(user.getAccount());
                 // 创建时间
                 ptBuReturnApplyDtlVO.setCreateTime(Pub.getCurrentDate());
                 // 更新退件申请明细
                 returnPurchaseApplyMngDao.insertReturnApplyDtl(ptBuReturnApplyDtlVO);
             }
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 配件预测明细查询
     * @throws Exception
     */
    public void searchReturnPurchaseApplyDtl() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = returnPurchaseApplyMngDao.searchReturnPurchaseApplyDtl(pageManager,conditions);
            // 单位
            baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);
            // 最小计量单位
            baseResultSet.setFieldDic("MIN_UNIT", DicConstant.JLDW);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    
    /**
     * 
     * @Title: exportExcel
     * @Description: 退货申请查询详细信息导出
     * @throws Exception
     * @return: void
     */
    public void exportExcel() throws Exception{
		ResponseWrapper response= actionContext.getResponse();
    	RequestWrapper requestWrapper = actionContext.getRequest();
    	PageManager page = new PageManager();
 		String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
        	
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
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("供应商");
    		header.add(hBean);
    		
	    	hBean = new HeaderBean();
    		hBean.setName("UNIT");
    		hBean.setTitle("计量单位");
    		header.add(hBean);
    		
	    	hBean = new HeaderBean();
    		hBean.setName("UNIT");
    		hBean.setTitle("计量单位");
    		header.add(hBean);
    		
	    	hBean = new HeaderBean();
    		hBean.setName("MIN_UNIT");
    		hBean.setTitle("最小包装");
    		header.add(hBean);
    		
	    	hBean = new HeaderBean();
    		hBean.setName("RETURN_COUNT");
    		hBean.setTitle("退货数");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("IN_COUNT");
    		hBean.setTitle("入库数");
    		header.add(hBean);
    		
	    	hBean = new HeaderBean();
    		hBean.setName("SALE_PRICE");
    		hBean.setTitle("经销商价格(元)");
    		header.add(hBean);
    		
	    	hBean = new HeaderBean();
    		hBean.setName("AMOUNT");
    		hBean.setTitle("金额(元)");
    		header.add(hBean);
    		
    		QuerySet qs = returnPurchaseApplyMngDao.queryDownInfo(conditions);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "退库明细下载", header, qs);
        }
        catch (Exception e)
        {
        	actionContext.setException(e);
            logger.error(e);
        }
        finally
        {
        	if (os != null)
            {
              os.close();
            }
        }
	}



    /**
     * 提报功能(修改退件申请)
     * @throws Exception
     */
    public void updateReturnPurchaseApply() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String returnId = Pub.val(requestWrapper, "returnId");
            // 退件申请表(pt_bu_return_apply)对应的实体
            PtBuReturnApplyVO ptBuReturnApplyVO = new PtBuReturnApplyVO();
            // 退件申请主键
            ptBuReturnApplyVO.setReturnId(returnId);
            // 退件状态(已申请)
            ptBuReturnApplyVO.setApplySatus(DicConstant.TJSQDZT_02);
            
            // begin 2015-01-14 by fuxiao : 提报添加校验，判断申请单是否已经提报，重复提报会导致库存占用减少多次的情况
            if(this.returnPurchaseApplyMngDao.compareApplyStatus(returnId, DicConstant.TJSQDZT_02)){
            	throw new Exception("此退件申请单已提报，不能重复提报");
            }
            // end
            
            // 通过Dao,执行更新
            returnPurchaseApplyMngDao.updateReturnPurchaseApply(ptBuReturnApplyVO, user);

            QuerySet querySet = returnPurchaseApplyMngDao.searchReturnPart(returnId,user);
            int count = querySet.getRowCount();
            if (count > 0) {
                for (int i=0;i<count;i++) {
                    // 占用库存
                    // 配件ID
                    String partId = querySet.getString(i+1, "PART_ID");
                    // 供应商ID
                    String supplierId = querySet.getString(i+1, "SUPPLIER_ID");
                    // 退件数量
                    String returnCount = querySet.getString(i+1, "RETURN_COUNT");
                    returnPurchaseApplyMngDao.updateDealerStock(partId,supplierId,returnCount,user);
                }
            }
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "提报成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }


    /**
     * 删除退件申请,退件申请明细
     * @throws Exception
     */
    public void deleteReturnPurchaseApply() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String returnId = Pub.val(requestWrapper, "returnId");
            // 退件申请表(pt_bu_return_apply)对应的实体
            PtBuReturnApplyVO ptBuReturnApplyVO = new PtBuReturnApplyVO();
            // 退件申请主键
            ptBuReturnApplyVO.setReturnId(returnId);
            String where = "    RETURN_ID='" + returnId + "'";
            // 通过Dao,执行删除退件申请
            returnPurchaseApplyMngDao.deleteReturnPurchaseApply(ptBuReturnApplyVO,user);
            // 通过Dao,执行删除退件申请明细
            returnPurchaseApplyMngDao.deleteReturnPurchaseApplyDtl(where);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除退件申请明细
     * @throws Exception
     */
    public void deleteReturnPurchaseApplyDtl() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String dtlId = Pub.val(requestWrapper, "dtlId");
            String where = "    DTL_ID='" + dtlId + "'";
            // 通过Dao,执行删除退件申请明细
            returnPurchaseApplyMngDao.deleteReturnPurchaseApplyDtl(where);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
}

package com.org.dms.action.part.salesMng.orderMng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.salesMng.orderMng.PartOrderReportDao;
import com.org.dms.vo.part.PtBuSaleOrderDtlExtendVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlVO;
import com.org.dms.vo.part.PtBuSaleOrderPayVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.Globals;
import com.org.framework.alertmsg.AlertInfoVO;
import com.org.framework.alertmsg.AlertManager;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExcelErrors;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * @Author: gouwentan
 * @Date: 2014-07-14
 * @Description:配件订单提报Action
 */
public class PartOrderReportAction {
    private Logger logger = com.org.framework.log.log.getLogger("PartOrderReportAction");
    private ActionContext atx = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) atx.getSession().get(Globals.USER_KEY);
    private PartOrderReportDao dao = PartOrderReportDao.getInstance(atx);
    // 定义response对象
    ResponseWrapper responseWrapper= atx.getResponse();

    /**
     * 错误数据导出
     * @throws Exception
     */
    public void expData()throws Exception{

        try {
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
            // 将request流中的信息转化为where条件
            String conditions = Pub.val(request, "seqs");
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
            hBean.setName("COUNT");
            hBean.setTitle("订购数量");
            header.add(2,hBean);

            QuerySet querySet = dao.expData(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "CWSJDC", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 订单查询
     */
    public void partOrderSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.partOrderSearch(page, user, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 资金账户可用余额查询
     */
    public void accountSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.accountSearch(page, user, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 订单类型规则查询
     */
    public void orderTypeRuleSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        String orderType = request.getParamValue("orderType");
        try {
            BaseResultSet bs = dao.orderTypeRuleSearch(page, user, conditions,orderType);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    /**
     * 订单主信息新增
     */
    public void orderInfoInsert() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String firstLetter = hm.get("FIRST_LETTER");
            String orderType = hm.get("ORDER_TYPE");
            String warehouseId = hm.get("WAREHOUSE_ID");
            
            // begin by fuxiao 20150114 : 只有配送中心才有额度，维修站及服务商没有额度
           /* String ifCredit = hm.get("IF_CREDIT");
            if("".equals(ifCredit)||ifCredit==null){
            	throw new Exception("请选择是否使用额度.");
            }*/
            if(user.getOrgDept().getOrgType().equals(DicConstant.ZZLB_09)){
	            String ifCredit = hm.get("IF_CREDIT");
	            if("".equals(ifCredit)||ifCredit==null){
	            	throw new Exception("请选择是否使用额度.");
	            }
            }
            // end
            
            String orgType = user.getOrgDept().getOrgType();
            QuerySet qs = dao.orderInfoSearch(user, orderType,warehouseId);
            if(qs.getRowCount()>0){
                throw new Exception("该订单类型有未提报的订单，不能新增订单.");
            }
            String transType =hm.get("TRANS_TYPE");
            if(DicConstant.FYFS_01.equals(transType)){
            	String countyCode =hm.get("COUNTY_CODE");
            	String cityCode =hm.get("CITY_CODE");
            	String provinceCode =hm.get("PROVINCE_CODE");
            	if("".equals(countyCode)||"".equals(cityCode)||"".equals(provinceCode)){
            		throw new Exception("收货地址不全，请联系029-86955803 淡勇捷完善信息.");
            	}
            }
            // 周度订单  START 10月24日
            QuerySet querySet = dao.orderReportRuleSearch(orderType);
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH)+1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            // 
            if(querySet.getRowCount()>0){
                String ifApplyDate = querySet.getString(1, "IF_APPLYDATE");
                String ifApplyTimes = querySet.getString(1, "IF_APPLYTIMES");
                if(DicConstant.SF_01.equals(ifApplyDate)){
                    if(DicConstant.ZZLB_09.equals(orgType)){
                        String startDate = querySet.getString(1, "DC_STARTDATE");
                        String endDate = querySet.getString(1, "DC_ENDDATE");
                        if(!(day>=Integer.parseInt(startDate)&&day<=Integer.parseInt(endDate))){
                            throw new Exception("提报日期不符,请在每月"+startDate+"~"+endDate+"日内提报.");
                        }
                    }
                    if(DicConstant.ZZLB_10.equals(orgType)||DicConstant.ZZLB_11.equals(orgType)){
                        String startDate = querySet.getString(1, "SE_STARTDATE");
                        String endDate = querySet.getString(1, "SE_ENDDATE");
                        if(!(day>=Integer.parseInt(startDate)&&day<=Integer.parseInt(endDate))){
                            throw new Exception("提报日期不符,请在每月"+startDate+"~"+endDate+"日内提报.");
                        }
                    }
                }
                if(DicConstant.SF_01.equals(ifApplyTimes)){
                    String applyTimes = querySet.getString(1, "APPLY_TIMES");
                    QuerySet timesqs = dao.orderReportTimesSearch(orderType,user,year,month);
                    if(Integer.parseInt(applyTimes)-Integer.parseInt(timesqs.getString(1, "COUNTS"))<=0){
                        throw new Exception("该类型订单已超出提报次数限制,每月最多只能提报"+applyTimes+"次.");
                    }
                }
            }
            // ------ END 
            
            String orderNo = PartOddNumberUtil.getSaleOrderNo(atx.getDBFactory(), firstLetter,user.getOrgCode());
            vo.setOrderNo(orderNo);
            vo.setValue(hm);
            if(DicConstant.ZZLB_10.equals(orgType)||DicConstant.ZZLB_11.equals(orgType)){
                if(!DicConstant.DDLX_07.equals(orderType)){
                    vo.setIfChanelOrder(DicConstant.SF_01);
                }else{
                    vo.setIfChanelOrder(DicConstant.SF_02);
                }
            }else{
                vo.setIfChanelOrder(DicConstant.SF_02);
            }
            vo.setIfDelayOrder(DicConstant.SF_02);
            vo.setOrgId(user.getOrgId());
            vo.setOrgCode(user.getOrgCode());
            vo.setOrgName(user.getOrgDept().getOName());
            vo.setOrderStatus(DicConstant.DDZT_01);
            vo.setCompanyId(user.getCompanyId());
            vo.setOemCompanyId(user.getOemCompanyId());
            vo.setCreateUser(user.getAccount());
            vo.setCreateTime(Pub.getCurrentDate());
            vo.setStatus(DicConstant.YXBS_01);
            dao.orderInfoInsert(vo);
            atx.setOutMsg(vo.getRowXml(), "新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 订单主信息修改
     */
    public void orderInfoUpdate() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            
	        // begin by fuxiao 20150114 : 只有配送中心才有额度，维修站及服务商没有额度
            /*
            String ifCredit = hm.get("IF_CREDIT");
            if("".equals(ifCredit)||ifCredit==null){
            	throw new Exception("请选择是否使用额度.");
            }
            */ 
            if(user.getOrgDept().getOrgType().equals(DicConstant.ZZLB_09)){
            	 String ifCredit = hm.get("IF_CREDIT");
                 if("".equals(ifCredit)||ifCredit==null){
                 	throw new Exception("请选择是否使用额度.");
                 }
            }
            // end
            
	        vo.setValue(hm);
            vo.setUpdateUser(user.getAccount());
            vo.setUpdateTime(Pub.getCurrentDate());
            dao.orderInfoUpdate(vo);
            atx.setOutMsg(vo.getRowXml(), "修改成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 订单删除
     */
    public void partOrderDelete() throws Exception {
        RequestWrapper request = atx.getRequest();
        try {
            String orderId = request.getParamValue("orderId");
            dao.orderPartInfoDelete(orderId);
            dao.orderPayInfoDelete(orderId);
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setOrderId(orderId);
            dao.orderInfoDelete(vo);
            atx.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 订单提报
     */
    public void partOrderReport() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH)+1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String orgType = user.getOrgDept().getOrgType();
            String orderId = request.getParamValue("orderId");
            String orderType = request.getParamValue("orderType");
            String orderAmount = request.getParamValue("orderAmount");
            QuerySet qs = dao.orderReportRuleSearch(orderType);
            
            // 提交订单顺序验证(驳回订单>日期MIN订单>未提报订单)
            QuerySet querySet = dao.partOrderReportCheck(orderId,orderType,user);
            if (querySet.getRowCount() <= 0) {
                throw new Exception("请优先提报同类型已驳回或日期最早的订单");
            }

            if(qs.getRowCount()>0){
                String ifApplyDate = qs.getString(1, "IF_APPLYDATE");
                String ifApplyTimes = qs.getString(1, "IF_APPLYTIMES");
                if(DicConstant.SF_01.equals(ifApplyDate)){
                    if(DicConstant.ZZLB_09.equals(orgType)){
                        String startDate = qs.getString(1, "DC_STARTDATE");
                        String endDate = qs.getString(1, "DC_ENDDATE");
                        if(!(day>=Integer.parseInt(startDate)&&day<=Integer.parseInt(endDate))){
                            throw new Exception("提报日期不符,请在每月"+startDate+"~"+endDate+"日内提报.");
                        }
                    }
                    if(DicConstant.ZZLB_10.equals(orgType)||DicConstant.ZZLB_11.equals(orgType)){
                        String startDate = qs.getString(1, "SE_STARTDATE");
                        String endDate = qs.getString(1, "SE_ENDDATE");
                        if(!(day>=Integer.parseInt(startDate)&&day<=Integer.parseInt(endDate))){
                            throw new Exception("提报日期不符,请在每月"+startDate+"~"+endDate+"日内提报.");
                        }
                    }
                }
                if(DicConstant.SF_01.equals(ifApplyTimes)){
                    String applyTimes = qs.getString(1, "APPLY_TIMES");
                    QuerySet timesqs = dao.orderReportTimesSearch(orderType,user,year,month);
                    if(Integer.parseInt(applyTimes)-Integer.parseInt(timesqs.getString(1, "COUNTS"))<=0){
                        throw new Exception("该类型订单已超出提报次数限制,每月最多只能提报"+applyTimes+"次.");
                    }
                }
            }
            if(Double.parseDouble(orderAmount)<=0){
                throw new Exception("订购金额为零,请维护所需订购的配件后进行提报.");
            }else{
                if(DicConstant.ZZLB_09.equals(orgType)){
                    QuerySet payqs = dao.orderPayAmountSearch(orderId);
                    if(Double.parseDouble(orderAmount)!=Double.parseDouble(payqs.getString(1, "PAY_AMOUNT"))){
                        throw new Exception("订购金额与账户使用金额不一致,请确认后再进行提报.");
                    }
                    QuerySet fundsqs = dao.orderFundsAmountSearch(orderId);
                    boolean flag = false;
                    if(fundsqs.getRowCount()>0){
                        for(int i=0;i<fundsqs.getRowCount();i++){
                            if(Double.parseDouble(fundsqs.getString(i+1, "PAY_AMOUNT"))-Double.parseDouble(fundsqs.getString(i+1, "AVAILABLE_AMOUNT"))>0){
                                flag = true;
                            }
                        }
                        if(flag){
                            throw new Exception("账户使用金额大于账户可用余额,不能进行提报.");
                        }else{
                            for(int i=0;i<fundsqs.getRowCount();i++){
                                String accountId  = fundsqs.getString(i+1, "ACCOUNT_ID");
                                String payAmount  = fundsqs.getString(i+1, "PAY_AMOUNT");
                                String accountType  = fundsqs.getString(i+1, "ACCOUNT_TYPE");
                                //更新资金账户
                                dao.accountUpdate(accountId, payAmount);
                                //插入订单资金占用
                                dao.orderFundsOccupy(orderId, accountId, accountType, payAmount, user);
                            }
                        }
                    }
                }
            }
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setOrderId(orderId);
            vo.setApplyDate(Pub.getCurrentDate());
            if(DicConstant.DDLX_07.equals(orderType)){
                vo.setOrderStatus(DicConstant.DDZT_07);
            }else{
                vo.setOrderStatus(DicConstant.DDZT_02);
            }
            vo.setUpdateUser(user.getAccount());
            vo.setUpdateTime(Pub.getCurrentDate());
            dao.orderInfoUpdate(vo);

            //代办提醒 start
            String title = "";
            if (DicConstant.ZZLB_09.equals(orgType)) {
                // 配送中心
            	title = "订单审核";
            }
//            if (DicConstant.ZZLB_10.equals(orgType)||DicConstant.ZZLB_11.equals(orgType)) {
//            	// 配件经销商,服务站
//            	title = "渠道商订单审核";
//            }
            QuerySet qsRole=dao.getRole(title);
            QuerySet querySet2 = dao.getOrderNo(orderId);
            String orderNo = "";
            if (querySet2.getRowCount() > 0) {
                orderNo = querySet2.getString(1, "ORDER_NO");
            }
            if(qsRole.getRowCount() > 0){
                for (int i=0;i<qsRole.getRowCount() ;i++){
                    String roleId=qsRole.getString(i+1,"ROLE_ID");
                    AlertInfoVO infoVO=new AlertInfoVO();
                    infoVO.setAlertTitle(user.getOrgDept().getOName()+"有一条销售订单申请！");//标题
                    infoVO.setDesr("订单号:"+orderNo);//提醒内容
                    infoVO.setOverrun("0");//提醒周期
                    infoVO.setAlertRole(roleId);//角色
                    infoVO.setBusType(DicConstant.YWLX_01);//业务类型（配件、售后）
                    infoVO.setBusPk(vo.getOrderId());//业务主键
                    infoVO.setAlertType(DicConstant.TXLX_01);//提醒类型
                    infoVO.setCreateUser(user.getAccount());//发送人（当前登录人）
                    infoVO.setCreateOrgid(user.getOrgId());//发送部门（当前登录部门）
                    AlertManager.alertInsert(atx.getDBFactory(),infoVO);
                }
            }
            //end
            atx.setOutMsg("", "操作成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 订单配件查询
     */
    public void orderPartSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String orderId = request.getParamValue("orderId");
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.orderPartSearch(page, user, conditions,orderId);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    /**
     * 
     * @Title: dealerOrderPartSearch
     * @Description: 订单配件查询：与上面的区别是查询时如果配件供应商Code为9XXXXXX时，则供应商名称为""
     * @throws Exception
     * @return: void
     */
    public void dealerOrderPartSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String orderId = request.getParamValue("orderId");
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.dealerOrderPartSearch(page, user, conditions,orderId);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    
    /**
     * 订单添加配件查询
     */
    public void orderPartAddSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String orgType = user.getOrgDept().getOrgType();
        String orderId = request.getParamValue("orderId");
        String warehouseId = request.getParamValue("warehouseId");
        String orderType = request.getParamValue("orderType");
        String promId = request.getParamValue("promId");
        //String supplierId = request.getParamValue("supplierId");
        String directTypeId = request.getParamValue("directTypeId");
        // 所属总成
        String belongAssembly = request.getParamValue("belongAssembly");
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
             BaseResultSet bs = dao.orderPartAddSearch(page, user, conditions,orgType,orderId,orderType,warehouseId,promId,directTypeId,belongAssembly);
             atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 添加配件
     */
    public void addPartSubmit() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String orderId = hm.get("ORDERID");
            String partIds = hm.get("PARTIDS");
            String partCodes = hm.get("PARTCODES");
            String partNames = hm.get("PARTNAMES");
            String supplierIds = hm.get("SUPPLIERIDS");
            String supplierCodes = hm.get("SUPPLIERCODES");
            String supplierNames = hm.get("SUPPLIERNAMES");
            String orderCounts = hm.get("ORDERCOUNTS");
            String salePrices = hm.get("SALEPRICES");
            String[] partIdArr = partIds.split(",");
            String[] partCodeArr = partCodes.split(",");
            String[] partNameArr = partNames.split(",");
            String[] supplierIdArr = supplierIds.split(",");
            String[] supplierCodeArr = supplierCodes.split(",");
            String[] supplierNameArr = supplierNames.split(",");
            String[] orderCountArr = orderCounts.split(",");
            String[] salePriceArr = salePrices.split(",");
            for (int i = 0; i < partIdArr.length; i++) {
                // 配件明细重复验证
                QuerySet querySet1 = dao.orderPartCheck(orderId,partIdArr[i], supplierIdArr[i]);
                if (querySet1.getRowCount() > 0) {
                    throw new Exception("配件"+querySet1.getString(1, "PART_CODE")+"已添加.");
                }
                PtBuSaleOrderDtlVO pbsod = new PtBuSaleOrderDtlVO();
                QuerySet querySet = dao.getPlanPrice(partIdArr[i]);
                pbsod.setPlanPrice(querySet.getString(1, "PLAN_PRICE"));
                pbsod.setOrderId(orderId);
                pbsod.setPartId(partIdArr[i]);
                pbsod.setPartCode(partCodeArr[i]);
                pbsod.setPartName(partNameArr[i]);
                if("9XXXXXX".equals(supplierCodeArr[i])){
                    pbsod.setIfSupplier(DicConstant.SF_02);
                    pbsod.setSupplierId(supplierIdArr[i]);
                    pbsod.setSupplierCode(supplierCodeArr[i]);
                    pbsod.setSupplierName(supplierNameArr[i]);
                }else{
                    pbsod.setIfSupplier(DicConstant.SF_01);
                    pbsod.setSupplierId(supplierIdArr[i]);
                    pbsod.setSupplierCode(supplierCodeArr[i]);
                    pbsod.setSupplierName(supplierNameArr[i]);
                }
                pbsod.setOrderCount(orderCountArr[i]);
                pbsod.setUnitPrice(salePriceArr[i]);
                pbsod.setAmount(String.valueOf(Integer.parseInt(orderCountArr[i])*Double.parseDouble(salePriceArr[i])));
                pbsod.setCreateUser(user.getAccount());
                pbsod.setCreateTime(Pub.getCurrentDate());
                dao.orderPartInfoInsert(pbsod);
            }
            QuerySet qs = dao.orderAmountSearch(orderId);
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setOrderId(orderId);
            vo.setOrderAmount(qs.getString(1, "AMOUNT"));
            dao.orderAmountUpdate(vo);
            atx.setOutMsg(vo.getXml(), "新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     *删除配件 
     */
    public void orderPartDelete() throws Exception {
        RequestWrapper request = atx.getRequest();
        try {
            String dtlId = request.getParamValue("dtlId");
            String orderId = request.getParamValue("orderId");
            PtBuSaleOrderDtlVO vo = new PtBuSaleOrderDtlVO();
            vo.setDtlId(dtlId);
            dao.deleteOrderPart(vo);
            QuerySet qs = dao.orderAmountSearch(orderId);
            PtBuSaleOrderVO svo = new PtBuSaleOrderVO();
            svo.setOrderId(orderId);
            svo.setOrderAmount(qs.getString(1, "AMOUNT"));
            dao.orderAmountUpdate(svo);
            atx.setOutMsg(svo.getXml(), "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     *修改配件订购数量 
     */
    public void orderPartCountSave() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        // 获取页面信息
        HashMap<String, String> hashMap = RequestUtil.getValues(request);
        try {
            String dtlId = hashMap.get("DTL_ID");
            String orderId = hashMap.get("ORDER_ID");
            String orderCount = hashMap.get("ORDER_COUNT");
            String remarks = hashMap.get("REMARKS");
            String unitPrice = hashMap.get("UNIT_PRICE");
            dao.updateOrderPart(dtlId,orderId,orderCount,remarks,user);
            QuerySet qs = dao.orderAmountSearch(orderId);
            PtBuSaleOrderVO svo = new PtBuSaleOrderVO();
            svo.setOrderId(orderId);
            svo.setRemarks(remarks);
            svo.setOrderAmount(qs.getString(1, "AMOUNT"));
            dao.orderAmountUpdate(svo);
            PtBuSaleOrderDtlExtendVO ptExtendVO = new PtBuSaleOrderDtlExtendVO();
            ptExtendVO.setDtlId(dtlId);
            ptExtendVO.setRemarks(remarks);
            ptExtendVO.setOrderCount(orderCount);
            ptExtendVO.setOrderAmount(qs.getString(1, "AMOUNT"));
            Double price = (Double.valueOf(unitPrice)*Double.valueOf(orderCount));
            ptExtendVO.setAmount(String.format("%.2f",price));
            ptExtendVO.setUnitPrice(String.format("%.2f",Double.valueOf(unitPrice)));
            atx.setOutMsg(ptExtendVO.getRowXml(), "");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 订单资金查询
     */
    public void orderFundsSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String orderId = request.getParamValue("orderId");
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.orderFundsSearch(page, user, conditions,orderId);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 订单付款明细删除
     */
    public void orderFundsDeleteOrderId() throws Exception {
    	RequestWrapper request = atx.getRequest();
        try {
            String orderId = request.getParamValue("orderId");
            dao.orderFundsDelete(orderId);
            atx.setOutMsg("", "修改成功!");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 订单免运费次数查询
     */
    public void transFreeTimesSearch()throws Exception {
         RequestWrapper request = atx.getRequest();
         User user = (User) atx.getSession().get(Globals.USER_KEY);
         PageManager page = new PageManager();
         String orderType = request.getParamValue("orderType");
         String conditions = RequestUtil.getConditionsWhere(request, page);
         try {
             BaseResultSet bs= dao.transFreeTimesSearch(page, user, conditions,orderType);
             atx.setOutData("bs", bs);
         } catch (Exception e) {
             logger.error(e);
             atx.setException(e);
         }
    }
    
    /**
     * 保存付款主信息
     */
    public void orderFundsInfoUpdate() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String orderId = request.getParamValue("orderId");
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setValue(hm);
            vo.setOrderId(orderId);
            vo.setUpdateUser(user.getAccount());
            vo.setUpdateTime(Pub.getCurrentDate());
            dao.orderAmountUpdate(vo);
            atx.setOutMsg("", "保存成功!");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 新增付款信息
     */
    public void orderFundsInsert() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            
            String orderId  = hm.get("ORDER_ID");
            String accountId= hm.get("ACCOUNT_ID");
            String accountType= hm.get("ACCOUNT_TYPE");
            boolean flag = dao.orderAccountPaySearch(orderId,accountId,accountType);
            if(flag){
                atx.setOutMsg("","该账户类型已使用，请重新选择!");
            }else{
                PtBuSaleOrderPayVO vo = new PtBuSaleOrderPayVO();
                vo.setValue(hm);
                vo.setCreateUser(user.getAccount());
                vo.setCreateTime(Pub.getCurrentDate());
                dao.orderFundsInsert(vo);
                atx.setOutMsg(vo.getXml(), "保存成功!");
            }
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 修改付款信息
     */
    public void orderFundsUpdate() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            PtBuSaleOrderPayVO vo = new PtBuSaleOrderPayVO();
            vo.setValue(hm);
            vo.setUpdateUser(user.getAccount());
            vo.setUpdateTime(Pub.getCurrentDate());
            dao.orderFundsUpdate(vo);
            atx.setOutMsg(vo.getXml(), "保存成功!");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 删除付款信息
     */
    public void orderFundsDelete()throws Exception {
        RequestWrapper request = atx.getRequest();
        try {
            String payId = request.getParamValue("payId");
            PtBuSaleOrderPayVO vo = new PtBuSaleOrderPayVO();
            vo.setPayId(payId);
            dao.orderFundsDelete(vo);
            atx.setOutMsg("", "删除成功!");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 批量清空配件订单明细
     */
    public void partOrderDtlDelete()throws Exception {
        RequestWrapper request = atx.getRequest();
        try {
            String orderId = request.getParamValue("orderId");
            dao.partOrderDtlDelete(orderId);
            atx.setOutMsg("", "删除成功!");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 配件订单明细导入校验
     */
    public List<ExcelErrors> checkData(String bParams)throws Exception{
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        ExcelErrors errors = null;
        List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
        String orgType = user.getOrgDept().getOrgType();
        QuerySet qs = dao.partOrderInfoSearch(bParams);
        if(qs.getRowCount()>0){
            String orderType = qs.getString(1,"ORDER_TYPE");
            String promId = "";
            String directTypeId = "";
            if(DicConstant.DDLX_06.equals(orderType)){
                promId = qs.getString(1, "PROM_ID");
            }
            if(DicConstant.DDLX_05.equals(orderType)){
                directTypeId = qs.getString(1, "DIRECT_TYPE_ID");
            }
            QuerySet qs1 = dao.partBaInfoSearch(user);
            if(qs1.getRowCount()>0){
                for(int i=0;i<qs1.getRowCount();i++){
                    errors=new ExcelErrors();
                    errors.setRowNum(Integer.parseInt(qs1.getString(i+1, "ROW_NO")));
                    String partCode = qs1.getString(i+1, "PART_CODE"); 
                    errors.setErrorDesc(partCode+"配件不存在.");
                    errorList.add(errors);
                }
            }
            QuerySet qs2 = dao.partInfoSearch(user,orderType,promId,directTypeId);
            if(qs2.getRowCount()>0){
                for(int i=0;i<qs2.getRowCount();i++){
                    errors=new ExcelErrors();
                    errors.setRowNum(Integer.parseInt(qs2.getString(i+1, "ROW_NO")));
                    String partCode = qs2.getString(i+1, "PART_CODE"); 
                    errors.setErrorDesc(partCode+"配件不符合订单要求.");
                    errorList.add(errors);
                }
            }
            QuerySet qs3 = dao.supplierBaInfoSearch(user);
            if(qs3.getRowCount()>0){
                for(int i=0;i<qs3.getRowCount();i++){
                    errors=new ExcelErrors();
                    errors.setRowNum(Integer.parseInt(qs3.getString(i+1, "ROW_NO")));
                    String supplierCode = qs3.getString(i+1, "SUPPLIER_CODE"); 
                    errors.setErrorDesc(supplierCode+"供应商不存在.");
                    errorList.add(errors);
                }
            }
            QuerySet qs4 = dao.partSupplierBaInfoSearch(user);
            if(qs4.getRowCount()>0){
                for(int i=0;i<qs4.getRowCount();i++){
                    errors=new ExcelErrors();
                    errors.setRowNum(Integer.parseInt(qs4.getString(i+1, "ROW_NO")));
                    String partCode = qs4.getString(i+1, "PART_CODE");
                    String supplierCode = qs4.getString(i+1, "SUPPLIER_CODE"); 
                    errors.setErrorDesc(partCode+"配件需指定的供应商，且与"+supplierCode+"供应商不存在供货关系.");
                    errorList.add(errors);
                }
            }
            QuerySet qs5 = dao.partOrderCountSearch(user);
            if(qs5.getRowCount()>0){
                for(int i=0;i<qs5.getRowCount();i++){
                    String partCode = qs5.getString(i+1, "PART_CODE");
                    String count = qs5.getString(i+1, "COUNT");
                    String minPack = qs5.getString(i+1, "MIN_PACK");
                    String reg = "^[1-9]d*$";
                    if(reg.matches(count)){
                        errors=new ExcelErrors();
                        errors.setRowNum(Integer.parseInt(qs5.getString(i+1, "ROW_NO")));
                        errors.setErrorDesc(partCode+"配件,订购数量不正确.");
                        errorList.add(errors);
                    }
                    if(DicConstant.ZZLB_09.equals(orgType)){
                        if(!(Integer.parseInt(count)%Integer.parseInt(minPack)==0)){
                            errors=new ExcelErrors();
                            errors.setRowNum(Integer.parseInt(qs5.getString(i+1, "ROW_NO")));
                            errors.setErrorDesc(partCode+"配件,订购数量应为最小包装数量"+minPack+"的倍数.");
                            errorList.add(errors);
                        }
                    }
                }
            }
//            QuerySet qs6 = dao.partOrderDetailSearch(user,orderId);
//            if(qs6.getRowCount()>0){
//                for(int i=0;i<qs6.getRowCount();i++){
//                    String partCode = qs6.getString(i+1, "PART_CODE");
//                    errors=new ExcelErrors();
//                    errors.setRowNum(Integer.parseInt(qs6.getString(i+1, "ROW_NO")));
//                    errors.setErrorDesc(partCode+"配件,在订单配件明细中已存在.");
//                    errorList.add(errors);
//                }
//            }
        }
        if(errorList!=null && errorList.size()>0){
            return errorList;
        }else{
            return null;
        }
    }
    
    /**
     * 校验成功查询
     */
    public void searchImportPart()throws Exception {
         RequestWrapper request = atx.getRequest();
         User user = (User) atx.getSession().get(Globals.USER_KEY);
         PageManager page = new PageManager();
         String conditions = RequestUtil.getConditionsWhere(request, page);
         try {
             BaseResultSet bs= dao.searchImportPart(page, user, conditions);
             atx.setOutData("bs", bs);
         } catch (Exception e) {
             logger.error(e);
             atx.setException(e);
         }
    }
    
    /**
     * 插入业务数据
     */
    public void insertImportPart()throws Exception {
         RequestWrapper request = atx.getRequest();
         User user = (User) atx.getSession().get(Globals.USER_KEY);
         String orderId = request.getParamValue("orderId");
         String tmpNo = request.getParamValue("tmpNo");
         String flag = request.getParamValue("flag");
         String sql = "";
         if (!"".equals(tmpNo)&&tmpNo!=null) {
     		sql = " AND A.ROW_NO NOT IN ("+tmpNo+") ";
     	}
         QuerySet qs = dao.partOrderInfoSearch(orderId);
         try {
             if(qs.getRowCount()>0){
                 String orderType = qs.getString(1,"ORDER_TYPE");
                 String promId = "";
                 if(DicConstant.DDLX_06.equals(orderType)){
                     promId = qs.getString(1, "PROM_ID");
                 }
                 dao.insertPartOrderDetail(user,orderId,promId,sql,flag);
             }


             // 修改订单总金额
             QuerySet qs1 = dao.orderAmountSearch(orderId);
             PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
             vo.setOrderAmount(qs1.getString(1, "AMOUNT"));

             if ("2".equals(flag)) {
            	 // 技术升级订单导入
            	 QuerySet querySet = dao.getserverOrderId(orderId);
            	 if (querySet.getRowCount() > 0) {
            		 String orderId1 = querySet.getString(1, "DIR_SOURCE_ORDER_ID");
            		 if (!orderId1.equals("0")) {
            			 vo.setOrderId(orderId1);
            			 dao.orderAmountUpdate(vo);
            		 }
            	 }
             }

             vo.setOrderId(orderId);
             dao.orderAmountUpdate(vo);
             atx.setOutMsg(vo.getXml(), "操作成功");
         } catch (Exception e) {
             logger.error(e);
             atx.setException(e);
         }
    }
    
    
    /**
     * 汇总渠道延期订单
     */
    public void totailDelayOrder()throws Exception {
         RequestWrapper request = atx.getRequest();
         User user = (User) atx.getSession().get(Globals.USER_KEY);
         String orderId = request.getParamValue("orderId");
         String orderType = request.getParamValue("orderType");
         String promId = request.getParamValue("promId");
         String directTypeId = request.getParamValue("directTypeId");
         try {
             dao.totailDelayPartOrder(user, orderId, orderType, promId, directTypeId);
             QuerySet qs = dao.orderAmountSearch(orderId);
             PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
             vo.setOrderId(orderId);
             vo.setOrderAmount(qs.getString(1, "AMOUNT"));
             dao.orderAmountUpdate(vo);
             atx.setOutMsg(vo.getRowXml(), "操作成功");
         } catch (Exception e) {
             logger.error(e);
             atx.setException(e);
         }
    }
    /**
     * 订单明细查询导出
     * @throws Exception
     */
    public void download()throws Exception{

        try {
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
            String orderId = Pub.val(request, "ORDER_ID");
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("ORDER_NO");
            hBean.setTitle("订单编号");
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
            hBean.setName("UNIT");
            hBean.setTitle("计量单位");
            header.add(3,hBean);

            hBean = new HeaderBean();
            hBean.setName("MINI");
            hBean.setTitle("最小包装");
            header.add(4,hBean);

            hBean = new HeaderBean();
            hBean.setName("UNIT_PRICE");
            hBean.setTitle("经销商价");
            header.add(5,hBean);

            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("供应商");
            header.add(6,hBean);

            hBean = new HeaderBean();
            hBean.setName("ORDER_COUNT");
            hBean.setTitle("订购数量");
            header.add(7,hBean);

            hBean = new HeaderBean();
            hBean.setName("AMOUNT");
            hBean.setTitle("小计");
            header.add(8,hBean);

            hBean = new HeaderBean();
            hBean.setName("REMARKS");
            hBean.setTitle("备注");
            header.add(9,hBean);

            hBean = new HeaderBean();
            hBean.setName("AVAILABLE_AMOUNT");
            hBean.setTitle("可用库存数量");
            header.add(10,hBean);
            
            
            QuerySet querySet = dao.download(orderId,user);
            QuerySet querySet2 = dao.getOrgName(orderId);
            String orgName = "订单明细";
            if (querySet2.getRowCount() > 0) {
	            if (!"0".equals(orgName)) {
	            	orgName = querySet2.getString(1, "ORG_NAME");
	            }
            }
            ExportManager.exportFile(responseWrapper.getHttpResponse(), orgName, header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
}

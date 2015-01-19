package com.org.dms.action.part.storageMng.boxUpMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.boxUpMng.BoxUpMngDao;
import com.org.dms.vo.part.PtBuBoxUpVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.Globals;
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
 * 装箱清单维护Action
 *
 * @user : lichuang
 * @date : 2014-07-21
 */
public class BoxUpMngAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "BoxUpMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private BoxUpMngDao dao = BoxUpMngDao.getInstance(atx);


    /**
     * 查询销售订单
     *
     * @throws Exception
     */
    public void searchSaleOrder() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchSaleOrder(page, user, conditions);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询销售订单配件
     *
     * @throws Exception
     */
    public void searchSaleOrderPart() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
        	page.setPageRows(10000);
            String ORDER_ID = Pub.val(request, "ORDER_ID");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchSaleOrderPart(page, user, conditions, ORDER_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 保存装箱单
     *
     * @throws Exception
     */
    public void saveBoxUp() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            insertOrUpdateBoxUp(hm, user);
            //返回插入结果和成功信息
            atx.setOutMsg("", "保存成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 装箱完成
     *
     * @throws Exception
     */
    public void completeBoxUp() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String orderId = hm.get("ORDER_ID");//销售订单ID
            if (!dao.checkBoxUpIsExist(orderId)) {
                throw new Exception("请维护装箱清单!");
            }
//            insertOrUpdateBoxUp(hm, user);
            PtBuSaleOrderVO saleOrderVO = new PtBuSaleOrderVO();
            saleOrderVO.setOrderId(orderId);
            saleOrderVO.setShipStatus(DicConstant.DDFYZT_04);
            
            /**------ 10-24 修改自提订单也可装箱  START------*/
            // 查询订单状态
            QuerySet transTypeQuerySet = dao.checkOrderType(orderId);
            String transType = transTypeQuerySet.getString(1, "TRANS_TYPE");
            // 发运方式为：自提
            if(DicConstant.FYFS_02.equals(transType)){
            	saleOrderVO.setShipStatus(DicConstant.DDFYZT_06);
            }
            /** ------ END ------*/
            
            saleOrderVO.setUpdateTime(Pub.getCurrentDate());
            saleOrderVO.setUpdateUser(user.getAccount());
            dao.updateSaleOrder(saleOrderVO);

            QuerySet check = dao.check(orderId);
            if(check.getRowCount()>0){
            	throw new Exception("装箱数量与出库数量不符 请验证");
            }
          //修改发运单对应的订单的明细的发运数量
          dao.updateSaleOrderDtl(orderId,user);
//          QuerySet getDevCount = dao.getDevCount(orderId);
//          //将实发金额更新至订单表中
          dao.updateSaleOrderAmount(orderId,user);

          // 技术升级,三包急件,直营订单.有原单时。
          QuerySet querySet = dao.getserverOrderId(orderId);
          if (querySet.getRowCount() > 0&&!"0".equals(querySet.getString(1, "DIR_SOURCE_ORDER_ID"))) {
              String orderId1 = querySet.getString(1, "DIR_SOURCE_ORDER_ID");
              //修改发运单对应的订单的明细的发运数量
//              dao.updateSaleOrderDtl(orderId1,user);
//              //将实发金额更新至订单表中
//              dao.updateSaleOrderAmount(orderId1,user);
        	  // 查询订单状态
              QuerySet transTypeQuerySet1 = dao.checkOrderType(orderId1);
              String transType1 = transTypeQuerySet1.getString(1, "TRANS_TYPE");
              // 发运方式为：自提
              if(DicConstant.FYFS_02.equals(transType1)){
              	saleOrderVO.setOrderId(orderId1);
              	dao.updateSaleOrder(saleOrderVO);
              }
          }
            //返回插入结果和成功信息
            atx.setOutMsg("", "装箱完成！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 新增或修改装箱单
     *
     * @param hm
     * @param user
     * @throws Exception
     */
    public void insertOrUpdateBoxUp(Map<String, String> hm, User user) throws Exception {
        String orderId = hm.get("ORDER_ID");//销售订单ID
        String orderNo = hm.get("ORDER_NO");//销售订单编号
        String partIds = hm.get("PARTIDS");//配件ID(逗号分隔)
        String partCodes = hm.get("PARTCODES");//配件代码(逗号分隔)
        String partNames = hm.get("PARTNAMES");//配件名称(逗号分隔)
        String bonNos = hm.get("BOXNOS");//箱号(逗号分隔)
        String counts = hm.get("COUNTS");//数量(逗号分隔)
        String remarks = hm.get("REMARKS");//备注(逗号分隔)
        String issueIds = hm.get("ISSUEIDS");//发料单ID(逗号分隔)
        String issueNos = hm.get("ISSUENOS");//发料单号(逗号分隔)
        String[] partIdArr = partIds.split("@");
        String[] partCodeArr = partCodes.split("@");
        String[] partNameArr = partNames.split("@");
        String[] boxNoArr = bonNos.split("@");
        String[] countArr = counts.split("@");
        String[] remarkArr = remarks.split("@");
        String[] issueIdArr = issueIds.split("@");
        String[] issueNoArr = issueNos.split("@");
        if (!"".equals(partIds)) {
            for (int i = 0; i < partIdArr.length; i++) {
            	QuerySet checkExists = dao.checkExists(partIdArr[i],issueIdArr[i],orderId);
            	if(checkExists.getRowCount()>0){
                    dao.deleteBox(partIdArr[i],issueIdArr[i],orderId);
            	}
	            if(!"myNull".equals(boxNoArr[i])){
	                if ("myNull".equals(remarkArr[i])) {
	                    remarkArr[i] = "";
	                }
	                /************************/
	                /**再次将传过来箱号信息分割**/
	                String []BOX_NO = boxNoArr[i].split(",");
	                String []COUNT = countArr[i].split(",");
	                
	                for(int j = 0;j<BOX_NO.length;j++){
	                	
	                		PtBuBoxUpVO boxUpVO = new PtBuBoxUpVO();
	                    	boxUpVO.setOrderId(orderId);
	                        boxUpVO.setOrderNo(orderNo);
	                        boxUpVO.setIssueId(issueIdArr[i]);
	                        boxUpVO.setIssueNo(issueNoArr[i]);
	                        boxUpVO.setPartId(partIdArr[i]);
	                        boxUpVO.setPartCode(partCodeArr[i]);
	                        boxUpVO.setPartName(partNameArr[i]);
	                        boxUpVO.setBoxNo(BOX_NO[j]);
	                        boxUpVO.setCount(COUNT[j]);
	                        
	                        if (!"myNull".equals(remarkArr[i])) {
	                        	boxUpVO.setRemarks(remarkArr[i]);
	                        }
	                        boxUpVO.setOrgId(user.getOrgId());
	                        boxUpVO.setCompanyId(user.getCompanyId());
	                        boxUpVO.setOemCompanyId(user.getOemCompanyId());
	                        boxUpVO.setStatus(DicConstant.YXBS_01);
	                        boxUpVO.setCreateTime(Pub.getCurrentDate());
	                        boxUpVO.setCreateUser(user.getAccount());
	                        dao.insertBoxUp(boxUpVO);
	                }
	            }
                //校验装箱清单是否存在
//                String upId = dao.checkBoxUpIsExist(issueIdArr[i], partIdArr[i], user);
//                if ("".equals(upId)) {//不存在 插入
//                    PtBuBoxUpVO boxUpVO = new PtBuBoxUpVO();
//                    boxUpVO.setOrderId(orderId);
//                    boxUpVO.setOrderNo(orderNo);
//                    boxUpVO.setIssueId(issueIdArr[i]);
//                    boxUpVO.setIssueNo(issueNoArr[i]);
//                    boxUpVO.setPartId(partIdArr[i]);
//                    boxUpVO.setPartCode(partCodeArr[i]);
//                    boxUpVO.setPartName(partNameArr[i]);
//                    boxUpVO.setBoxNo(boxNoArr[i].replaceAll("!", ","));
//                    boxUpVO.setCount(countArr[i]);
//                    boxUpVO.setRemarks(remarkArr[i]);
//                    boxUpVO.setOrgId(user.getOrgId());
//                    boxUpVO.setCompanyId(user.getCompanyId());
//                    boxUpVO.setOemCompanyId(user.getOemCompanyId());
//                    boxUpVO.setStatus(DicConstant.YXBS_01);
//                    boxUpVO.setCreateTime(Pub.getCurrentDate());
//                    boxUpVO.setCreateUser(user.getAccount());
//                    dao.insertBoxUp(boxUpVO);
//                } else {//存在 更新
//                    PtBuBoxUpVO boxUpVO = new PtBuBoxUpVO();
//                    boxUpVO.setUpId(upId);
//                    boxUpVO.setBoxNo(boxNoArr[i].replaceAll("!", ","));
//                    boxUpVO.setCount(countArr[i]);
//                    boxUpVO.setRemarks(remarkArr[i]);
//                    boxUpVO.setUpdateTime(Pub.getCurrentDate());
//                    boxUpVO.setUpdateUser(user.getAccount());
//                    dao.updateBoxUp(boxUpVO);
//                }
            }
        }
    }

    /**
     * 导出装箱清单
     *
     * @throws Exception
     */
    public void exportBoxUp() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        ResponseWrapper response = atx.getResponse();
        String ORDER_ID = Pub.val(request, "ORDER_ID");//销售订单ID
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("ROWNUM");
            hBean.setTitle("序号");
            header.add(0, hBean);
            hBean = new HeaderBean();
            hBean.setName("ISSUE_NO");
            hBean.setTitle("发料单号");
            header.add(1, hBean);
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(2, hBean);
            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(3, hBean);
            hBean = new HeaderBean();
            hBean.setName("UNIT");
            hBean.setTitle("单位");
            header.add(4, hBean);
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("供应商");
            header.add(5, hBean);
            hBean = new HeaderBean();
            hBean.setName("SHOULD_COUNT");
            hBean.setTitle("应发数量");
            header.add(6, hBean);
            hBean = new HeaderBean();
            hBean.setName("REAL_COUNT");
            hBean.setTitle("实发数量");
            header.add(7, hBean);
            hBean = new HeaderBean();
            hBean.setName("BOX_NO");
            hBean.setTitle("箱号");
            header.add(8, hBean);
            hBean = new HeaderBean();
            hBean.setName("COUNT");
            hBean.setTitle("数量");
            header.add(9, hBean);
            QuerySet qs = dao.exportBoxUp(ORDER_ID);
            QuerySet getOrgName = dao.getOrgName(ORDER_ID);
            String orgName = getOrgName.getString(1, "ORG_NAME");
            ExportManager.exportFile(response.getHttpResponse(), orgName, header, qs);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 导入数据校验
     *
     * @return
     * @throws Exception
     */
    public List<ExcelErrors> checkData(String bParams) throws Exception {
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        ExcelErrors errors = null;
        List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
        QuerySet qs1 = dao.checkList1(user);
        if (qs1.getRowCount() > 0) {
            for (int i = 0; i < qs1.getRowCount(); i++) {
                errors = new ExcelErrors();
                String ROW_NO = qs1.getString(i + 1, "ROW_NO");
                String ISSUE_NO = qs1.getString(i + 1, "ISSUE_NO");
                String PART_CODE = qs1.getString(i + 1, "PART_CODE");
                String BOX_NO = qs1.getString(i + 1, "BOX_NO");
                String COUNT = qs1.getString(i + 1, "COUNT");

                if (null == ISSUE_NO || "".equals(ISSUE_NO)) {
                    errors.setErrorDesc("第" + ROW_NO + "行,发料单号不能为空!");
                    errorList.add(errors);
                }
                if (null == PART_CODE || "".equals(PART_CODE)) {
                    errors.setErrorDesc("第" + ROW_NO + "行,配件代码不能为空");
                    errorList.add(errors);
                }
                if (null == BOX_NO || "".equals(BOX_NO)) {
                    errors.setErrorDesc("第" + ROW_NO + "行,箱号不能为空!");
                    errorList.add(errors);
                } else {
                    if (!BOX_NO.matches("^\\w{1,50}(,\\w{1,50})*$")) {
                        errors.setErrorDesc("第" + ROW_NO + "行,请输入正确的箱号!只能输入字母数字和下划线,长度不超过50个字符,多个请用英文逗号分隔!");
                        errorList.add(errors);
                    }
                }
                if (null == COUNT || "".equals(COUNT)) {
                    errors.setErrorDesc("第" + ROW_NO + "行,数量不能为空!");
                    errorList.add(errors);
                } else {
                    if (!COUNT.matches("^[1-9]\\d*$")) {
                        errors.setErrorDesc("第" + ROW_NO + "行,请输入正确的数量!");
                        errorList.add(errors);
                    }
                }
            }
        }
        if (errorList.size() == 0) {
            QuerySet qs2 = dao.checkList2(user, bParams);
            if (qs2.getRowCount() > 0) {
                for (int i = 0; i < qs2.getRowCount(); i++) {
                    errors = new ExcelErrors();
                    String ROW_NO = qs2.getString(i + 1, "ROW_NO");
                    errors.setErrorDesc("第" + ROW_NO + "行,发料单号与备件代码不属于此销售订单！");
                    errorList.add(errors);
                }
            }
        }

        if (errorList != null && errorList.size() > 0) {
            return errorList;
        } else {
            return null;
        }
    }

    /**
     * 导入验证成功查询
     *
     * @throws Exception
     */
    public void searchImport() throws Exception {
        RequestWrapper request = atx.getRequest();
        PageManager pageManager = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request, pageManager);
        PageManager page = new PageManager();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            BaseResultSet bs = dao.searchImport(page, user,conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 插入正式表
     *
     * @throws Exception
     */
    public void confirmImport() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String orderId = Pub.val(request, "orderId");
        String tmpNo = Pub.val(request, "tmpNo");
        try {
            //插入装箱清单表
        	dao.deleteBoxImpot(orderId);
        	QuerySet getTmp = dao.getTmp(tmpNo,user);
        	
        	for(int i=0;i<getTmp.getRowCount();i++){
        		String ORDER_ID = getTmp.getString(i+1, "ORDER_ID");
            	String ORDER_NO = getTmp.getString(i+1, "ORDER_NO");
            	String ISSUE_ID = getTmp.getString(i+1, "ISSUE_ID");
            	String ISSUE_NO = getTmp.getString(i+1, "ISSUE_NO");
            	String PART_ID = getTmp.getString(i+1, "PART_ID");
            	String PART_CODE = getTmp.getString(i+1, "PART_CODE");
            	String PART_NAME = getTmp.getString(i+1, "PART_NAME");
            	String COUNT = getTmp.getString(i+1, "COUNT");
            	String BOX_NO = getTmp.getString(i+1, "BOX_NO");
            	String REMARKS = getTmp.getString(i+1, "REMARKS");
            	String B_NO[] = BOX_NO.split(",");
            	String B_COUNT[] = COUNT.split(",");
            	for(int j=0;j<B_NO.length;j++){
            		PtBuBoxUpVO boxUpVO = new PtBuBoxUpVO();
                	boxUpVO.setOrderId(ORDER_ID);
                    boxUpVO.setOrderNo(ORDER_NO);
                    boxUpVO.setIssueId(ISSUE_ID);
                    boxUpVO.setIssueNo(ISSUE_NO);
                    boxUpVO.setPartId(PART_ID);
                    boxUpVO.setPartCode(PART_CODE);
                    boxUpVO.setPartName(PART_NAME);
                    boxUpVO.setBoxNo(B_NO[j]);
                    boxUpVO.setCount(B_COUNT[j]);
                    if(!"".equals(REMARKS)&&REMARKS!=null){
                    	boxUpVO.setRemarks(REMARKS);
                    }
                    boxUpVO.setOrgId(user.getOrgId());
                    boxUpVO.setCompanyId(user.getCompanyId());
                    boxUpVO.setOemCompanyId(user.getOemCompanyId());
                    boxUpVO.setStatus(DicConstant.YXBS_01);
                    boxUpVO.setCreateTime(Pub.getCurrentDate());
                    boxUpVO.setCreateUser(user.getAccount());
                    dao.insertBoxUp(boxUpVO);
            	}
            	
        	}
            atx.setOutMsg("", "导入成功！");
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 导出错误数据
     *
     * @throws Exception
     */
    public void expData()throws Exception{
    	RequestWrapper request = atx.getRequest();
        ResponseWrapper response = atx.getResponse();
        String orderId = Pub.val(request, "orderId");
        String tmpNo = Pub.val(request, "tmpNo");
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("ROWNUM");
            hBean.setTitle("序号");
            header.add(0, hBean);
            hBean = new HeaderBean();
            hBean.setName("ISSUE_NO");
            hBean.setTitle("发料单号");
            header.add(1, hBean);
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(2, hBean);
            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(3, hBean);
            hBean = new HeaderBean();
            hBean.setName("UNIT");
            hBean.setTitle("单位");
            header.add(4, hBean);
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("供应商");
            header.add(5, hBean);
            hBean = new HeaderBean();
            hBean.setName("SHOULD_COUNT");
            hBean.setTitle("应发数量");
            header.add(6, hBean);
            hBean = new HeaderBean();
            hBean.setName("REAL_COUNT");
            hBean.setTitle("实发数量");
            header.add(7, hBean);
            hBean = new HeaderBean();
            hBean.setName("BOX_NO");
            hBean.setTitle("箱号");
            header.add(8, hBean);
            hBean = new HeaderBean();
            hBean.setName("COUNT");
            hBean.setTitle("数量");
            header.add(9, hBean);
            hBean = new HeaderBean();
            hBean.setName("REMARKS");
            hBean.setTitle("备注");
            header.add(10, hBean);
            QuerySet qs = dao.exportData(orderId,tmpNo);
            QuerySet getOrgName = dao.getOrgName(orderId);
            String orgName = getOrgName.getString(1, "ORG_NAME");
            ExportManager.exportFile(response.getHttpResponse(), orgName, header, qs);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
}
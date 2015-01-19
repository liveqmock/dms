package com.org.dms.action.part.salesMng.shipMent;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.salesMng.shipMent.DeliveAddChageDao;
import com.org.dms.vo.part.PtBuChangeShipAddressVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class DeliveAddChageAction {
    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义仓库管理Dao对象
    private DeliveAddChageDao deliveAddChageDao = DeliveAddChageDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 定义response对象
    ResponseWrapper responseWrapper= actionContext.getResponse();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 提报功能(修改配件收货地址变更)
     * @throws Exception
     */
    public void deliveryaddrApply() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String addId = Pub.val(requestWrapper, "addId");
            // 配件收货地址变更表(PT_BU_CHANGE_SHIP_ADDRESS)对应的实体
            PtBuChangeShipAddressVO ptBuChangeShipAddressVO =new PtBuChangeShipAddressVO();
            // 变更地址主键
            ptBuChangeShipAddressVO.setAddId(addId);
            // 变更状态(已提报)
            ptBuChangeShipAddressVO.setChangeStatus(DicConstant.JHDZBGZT_02);
            // 更新人
            ptBuChangeShipAddressVO.setUpdateUser(user.getAccount());
            // 更新时间
            ptBuChangeShipAddressVO.setUpdateTime(Pub.getCurrentDate());
            // 通过Dao,执行更新
            deliveAddChageDao.deliveryaddrApply(ptBuChangeShipAddressVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg(ptBuChangeShipAddressVO.getRowXml(), "修改成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除配件收货地址变更
     * @throws Exception
     */
    public void deleteChangeShipAddress() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String addId = Pub.val(requestWrapper, "addId");
            // 配件收货地址变更表(PT_BU_CHANGE_SHIP_ADDRESS)对应的实体
            PtBuChangeShipAddressVO ptBuChangeShipAddressVO =new PtBuChangeShipAddressVO();
            // 变更地址主键
            ptBuChangeShipAddressVO.setAddId(addId);
            // 通过Dao,执行删除配件预测
            deliveAddChageDao.deleteChangeShipAddress(ptBuChangeShipAddressVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 查询已保存且未申请的变更地址
     * @throws Exception
     */
    public void searchDeliveryaddr() throws Exception {

        try {
            PageManager pageManager = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
            BaseResultSet baseResultSet = deliveAddChageDao.searchDeliveryaddr(pageManager,user,conditions);
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 查询销售订单状态不是(已发运,已签收)
     * @throws Exception
     **/
    public void ordersearch() throws Exception {

        try {
            PageManager page = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
            BaseResultSet bs = deliveAddChageDao.ordersearch(page,user,conditions);
            actionContext.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 收货地址变更审核查询
     * @throws Exception
     * 
     **/
    public void orderExasearch() throws Exception {

        try {
            PageManager page = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
            BaseResultSet bs = deliveAddChageDao.orderExasearch(page,user,conditions);
            actionContext.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

     /**
     * 交货地址变更添加
     * @throws Exception
     */
    public void deliveryaddrAdd() throws Exception {

        try {
            PtBuChangeShipAddressVO ptBuChangeShipAddressVO =new PtBuChangeShipAddressVO();
            ptBuChangeShipAddressVO.setValue(hashMap);
            // 变更状态(已保存)
            ptBuChangeShipAddressVO.setChangeStatus(DicConstant.JHDZBGZT_01);
            // 所属公司
            ptBuChangeShipAddressVO.setCompanyId(user.getCompanyId());
            // 所属机构
            ptBuChangeShipAddressVO.setOrgId(user.getOrgId());
            // 创建人
            ptBuChangeShipAddressVO.setCreateUser(user.getAccount());
            // 创建时间
            ptBuChangeShipAddressVO.setCreateTime(Pub.getCurrentDate());
            // 状态：有效
            ptBuChangeShipAddressVO.setStatus(DicConstant.YXBS_01);
            // 
            ptBuChangeShipAddressVO.setOemCompanyId(user.getOemCompanyId());
            deliveAddChageDao.deliveryAddrInsert(ptBuChangeShipAddressVO);
            actionContext.setOutMsg(ptBuChangeShipAddressVO.getRowXml(),"地址变更提交成功");
        } catch (Exception e) {
            //设置失败异常处理
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 审核通过
     * @throws Exception
     */
    public void deliveryaddrExaPassupdate() throws Exception{

        try {

        	String orderId = hashMap.get("ORDER_ID");
        	QuerySet checkStatus = deliveAddChageDao.checkStatus(orderId);
        	if(checkStatus.getRowCount()>0){
        		throw new Exception("此订单已经发料不能修改地址");
        	}
            // 交货地址变更表(PT_BU_CHANGE_SHIP_ADDRESS)对应的实体
            PtBuChangeShipAddressVO ptBuChangeShipAddressVO = new PtBuChangeShipAddressVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBuChangeShipAddressVO.setValue(hashMap);
            // 变更状态(已通过)
            ptBuChangeShipAddressVO.setChangeStatus(DicConstant.JHDZBGZT_03);
            // 审核人
            ptBuChangeShipAddressVO.setCheckUser(user.getAccount());
            // 审核时间
            ptBuChangeShipAddressVO.setCheckTime(Pub.getCurrentDate());
            // 更新人
            ptBuChangeShipAddressVO.setUpdateUser(user.getAccount());
            // 更新时间
            ptBuChangeShipAddressVO.setUpdateTime(Pub.getCurrentDate());
            // 修改收货地址
            deliveAddChageDao.updateChangeAddr(ptBuChangeShipAddressVO);
            QuerySet getNo = deliveAddChageDao.getNo(orderId);
            String orderNo = getNo.getString(1, "ORDER_NO");
            String oldNO = orderNo.substring(0,orderNo.length()-1);
            QuerySet getDelay = deliveAddChageDao.getDelay(oldNO);
            PtBuSaleOrderVO ptBuSaleOrderVO = new PtBuSaleOrderVO();
            for(int i = 0;i<getDelay.getRowCount(); i++){
	            ptBuSaleOrderVO.setValue(hashMap);
	            ptBuSaleOrderVO.setOrderId(getDelay.getString(i+1, "ORDER_ID"));
	  	          // 更新人
	  	        ptBuSaleOrderVO.setUpdateUser(user.getAccount());
	  	          // 更新时间
	  	        ptBuSaleOrderVO.setUpdateTime(Pub.getCurrentDate());
	  	        deliveAddChageDao.updateSaleOrder(ptBuSaleOrderVO);
            }
            // 修改销售订单表
	        
	          // 将hashmap映射到vo对象中,完成匹配赋值
	        
        } catch (Exception e) {
            //设置失败异常处理
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 审核驳回
     * @throws Exception
     */
    public void deliveryaddrExaNoPassupdate() throws Exception {

        try {
            // 交货地址变更表(PT_BU_CHANGE_SHIP_ADDRESS)对应的实体
            PtBuChangeShipAddressVO ptBuChangeShipAddressVO = new PtBuChangeShipAddressVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBuChangeShipAddressVO.setValue(hashMap);
            // 变更状态(审核驳回)
            ptBuChangeShipAddressVO.setChangeStatus(DicConstant.JHDZBGZT_04);
            // 审核人
            ptBuChangeShipAddressVO.setCheckUser(user.getAccount());
            // 审核时间
            ptBuChangeShipAddressVO.setCheckTime(Pub.getCurrentDate());
            // 更新人
            ptBuChangeShipAddressVO.setUpdateUser(user.getAccount());
            // 更新时间
            ptBuChangeShipAddressVO.setUpdateTime(Pub.getCurrentDate());
            deliveAddChageDao.updateChangeAddr(ptBuChangeShipAddressVO);
        } catch (Exception e) {
            //设置失败异常处理
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 交货地址变更修改
     * @throws Exception
     */
    public void deliveryaddrUpdate() throws Exception {

        try {
            // 交货地址变更表(PT_BU_CHANGE_SHIP_ADDRESS)对应的实体
            PtBuChangeShipAddressVO ptBuChangeShipAddressVO=new PtBuChangeShipAddressVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBuChangeShipAddressVO.setValue(hashMap);
            // 更新人
            ptBuChangeShipAddressVO.setUpdateUser(user.getAccount());
            // 更新时间
            ptBuChangeShipAddressVO.setUpdateTime(Pub.getCurrentDate());
            // 通过Dao,执行更新
            deliveAddChageDao.deliveryaddrUpdate(ptBuChangeShipAddressVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg(ptBuChangeShipAddressVO.getRowXml(), "修改成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
}

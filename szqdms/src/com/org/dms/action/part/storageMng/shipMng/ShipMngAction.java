package com.org.dms.action.part.storageMng.shipMng;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.shipMng.ShipMngDao;
import com.org.dms.vo.part.PtBuOrderShipDtlVO;
import com.org.dms.vo.part.PtBuOrderShipVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
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

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 发运单维护Action
 *
 * @user : lichuang
 * @date : 2014-07-21
 */
public class ShipMngAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "ShipMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private ShipMngDao dao = ShipMngDao.getInstance(atx);


    /**
     * 查询发运单
     *
     * @throws Exception
     */
    public void searchShip() throws Exception {
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
        	String status = Pub.val(request, "STATUS");
            BaseResultSet bs = dao.searchShip(page, user, conditions,status);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    /**
     * 查询配送中心到货确认的发运单
     * Author suoxiuli 2014-09-13
     * @throws Exception
     * remarks 可以跟searchShip合并为一个方法
     */
    public void searchShip1() throws Exception {
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
            BaseResultSet bs = dao.searchShip1(page, user, conditions, DicConstant.FYDZT_05);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 新增发运单
     *
     * @throws Exception
     */
    public void insertShip() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            PtBuOrderShipVO vo = new PtBuOrderShipVO();
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            //将hashmap映射到vo对象中,完成匹配赋值
            vo.setValue(hm);
            vo.setShipNo(dao.createShipNo());
            vo.setCompanyId(user.getCompanyId());
            vo.setOrgId(user.getOrgId());
            vo.setOemCompanyId(user.getOemCompanyId());
            vo.setCreateUser(user.getAccount());
            vo.setCreateTime(Pub.getCurrentDate());
            vo.setStatus(DicConstant.YXBS_01);
            //通过dao，执行插入
            dao.insertShip(vo);
            atx.setOutMsg(vo.getRowXml(), "新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 修改发运单
     *
     * @throws Exception
     */
    public void updateShip() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBuOrderShipVO tempVO = new PtBuOrderShipVO();
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            tempVO.setValue(hm);
            tempVO.setUpdateUser(user.getAccount());
            tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updateShip(tempVO);
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(), "修改成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 发运单回执确认
     * Author suoxiuli 2014-09-13
     * @throws Exception
     */
    public void recieptConfirmShip() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String shipId = Pub.val(request, "shipId");
        try
        {
            //更新司机信息为无效状态
            boolean b = dao.recieptConfirmShip(shipId, user.getAccount(), DicConstant.FYDZT_06);
            atx.setOutMsg("","司机信息删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 删除发运单
     *
     * @throws Exception
     */
    public void deleteShip() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String shipId = Pub.val(request, "shipId");
        try {
            PtBuOrderShipVO delVo = new PtBuOrderShipVO();
            delVo.setShipId(shipId);
            dao.deleteShip(delVo);

            dao.deleteShipDtl(shipId);
            atx.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除发运单明细
     *
     * @throws Exception
     */
    public void deleteShipDtl() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String dtlId = Pub.val(request, "dtlId");
        String orderId = Pub.val(request, "orderId");
        try {
            PtBuOrderShipDtlVO delVo = new PtBuOrderShipDtlVO();
            delVo.setDtlId(dtlId);
            dao.deleteShipDtl(delVo);

            //将销售订单更新为已装箱
            PtBuSaleOrderVO orderVO = new PtBuSaleOrderVO();
            orderVO.setOrderId(orderId);
            orderVO.setShipStatus(DicConstant.DDFYZT_04);
            orderVO.setUpdateTime(Pub.getCurrentDate());
            orderVO.setUpdateUser(user.getAccount());
            dao.updateSaleOrder(orderVO);

            atx.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 查询销售订单
     *
     * @throws Exception
     */
    public void searchSale() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        String ifArmy = Pub.val(request, "ifArmy");
        try {

            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchSale(page, user, conditions,ifArmy);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询发运单明细
     *
     * @throws Exception
     */
    public void searchShipDtl() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String shipId = Pub.val(request,"shipId");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchShipDtl(page, user, conditions, shipId);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 新增发运单明细
     *
     * @throws Exception
     */
    public void insertShipDtl() throws Exception {
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
            String shipId = hm.get("SHIPID");//发运单ID
            String orderIds = hm.get("ORDERIDS");//销售订单ID(逗号分隔)
            String[] orderIdArr = orderIds.split(",");
            for (int i = 0; i < orderIdArr.length; i++) {
                PtBuOrderShipDtlVO dtlVO = new PtBuOrderShipDtlVO();
                dtlVO.setShipId(shipId);
                dtlVO.setOrderId(orderIdArr[i]);
                dao.insertShipDtl(dtlVO);

                //将销售订单更新为未发运
                PtBuSaleOrderVO orderVO = new PtBuSaleOrderVO();
                orderVO.setOrderId(orderIdArr[i]);
                orderVO.setShipStatus(DicConstant.DDFYZT_05);
                orderVO.setUpdateTime(Pub.getCurrentDate());
                orderVO.setUpdateUser(user.getAccount());
                dao.updateSaleOrder(orderVO);
            }
            //返回插入结果和成功信息
            atx.setOutMsg("", "新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 导出运输清单
     * @throws Exception
     */
    public void exportTransDtl()throws Exception{
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        ResponseWrapper response= atx.getResponse();
        String SHIP_ID = Pub.val(request, "SHIP_ID");//发运单ID
        try
        {
            if(!dao.checkShipDtlIsExist(SHIP_ID)){
                throw new Exception("请维护发运单明细!");
            }
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("ROWNUM");
            hBean.setTitle("序号");
            header.add(0,hBean);
            hBean = new HeaderBean();
            hBean.setName("ISSUE_NO");
            hBean.setTitle("发料单号");
            header.add(1,hBean);
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(2,hBean);
            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(3,hBean);
            hBean = new HeaderBean();
            hBean.setName("UNIT");
            hBean.setTitle("单位");
            header.add(4,hBean);
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("供应商");
            header.add(5,hBean);
            hBean = new HeaderBean();
            hBean.setName("SHOULD_COUNT");
            hBean.setTitle("应发数量");
            header.add(6,hBean);
            hBean = new HeaderBean();
            hBean.setName("REAL_COUNT");
            hBean.setTitle("实发数量");
            header.add(7,hBean);
            hBean = new HeaderBean();
            hBean.setName("BOX_NO");
            hBean.setTitle("箱号");
            header.add(8,hBean);
            hBean = new HeaderBean();
            hBean.setName("COUNT");
            hBean.setTitle("数量");
            header.add(9,hBean);
            hBean = new HeaderBean();
            hBean.setName("REMARKS");
            hBean.setTitle("备注");
            header.add(10,hBean);
            QuerySet qs = dao.exportTransDtl(SHIP_ID);
            ExportManager.exportFile(response.getHttpResponse(), "运输清单", header, qs);
        }
        catch (Exception e)
        {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 提交发运单
     *
     * @throws Exception
     */
    public void submitShip() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            String shipId = Pub.val(request,"shipId");//发运单ID
            if(!dao.checkShipDtlIsExist(shipId)){
                throw new Exception("请维护发运单明细!");
            }

            PtBuOrderShipVO shipVO = new PtBuOrderShipVO();
            shipVO.setShipId(shipId);
            shipVO.setShipStatus(DicConstant.FYDZT_02);
            shipVO.setUpdateTime(Pub.getCurrentDate());
            shipVO.setUpdateUser(user.getAccount());
            dao.updateShip(shipVO);
            //返回插入结果和成功信息
            atx.setOutMsg("", "提交成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    public void getCarrier() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.getCarrier(page, user, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

}
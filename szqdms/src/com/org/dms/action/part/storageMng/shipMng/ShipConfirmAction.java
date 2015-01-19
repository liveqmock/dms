package com.org.dms.action.part.storageMng.shipMng;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.storageMng.shipMng.ShipConfirmDao;
import com.org.dms.vo.part.PtBuOrderShipCarrierVO;
import com.org.dms.vo.part.PtBuOrderShipVO;
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
 * 发运单确认Action
 *
 * @user : lichuang
 * @date : 2014-08-01
 */
public class ShipConfirmAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "ShipConfirmAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private ShipConfirmDao dao = ShipConfirmDao.getInstance(atx);


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
            String shipId = Pub.val(request, "shipId");
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
     * 导出运输清单
     *
     * @throws Exception
     */
    public void exportTransDtl() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        ResponseWrapper response = atx.getResponse();
        String SHIP_ID = Pub.val(request, "SHIP_ID");//发运单ID
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
            QuerySet qs = dao.exportTransDtl(SHIP_ID);
            ExportManager.exportFile(response.getHttpResponse(), "运输清单", header, qs);
        } catch (Exception e) {
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
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBuOrderShipVO tempVO = new PtBuOrderShipVO();
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            tempVO.setShipId(hm.get("SHIP_ID"));
            tempVO.setCarrierRemarks(hm.get("CARRIER_REMARKS"));
            tempVO.setShipStatus(DicConstant.FYDZT_03);
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
     * 新增承运信息
     *
     * @throws Exception
     */
    public void insertCarrier() throws Exception {
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            PtBuOrderShipCarrierVO vo = new PtBuOrderShipCarrierVO();
            
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String PLATE = hm.get("LICENSE_PLATE");
            QuerySet checkVel = dao.checkVel(PLATE);
            if(checkVel.getRowCount()==0){
            	throw new Exception("车辆："+PLATE+"基本信息尚未维护");
            }
            vo.setValue(hm);
            vo.setCreateUser(user.getAccount());// 设置创建人
            vo.setCreateTime(Pub.getCurrentDate());// 创建时间
            dao.insertCarrier(vo);
            atx.setOutMsg(vo.getRowXml(), "新增成功！");
        } catch (Exception e) {
            //设置失败异常处理
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 修改承运信息
     *
     * @throws Exception
     */
    public void updateCarrier() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            PtBuOrderShipCarrierVO vo = new PtBuOrderShipCarrierVO();
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            vo.setValue(hm);
            vo.setUpdateUser(user.getAccount());
            vo.setUpdateTime(Pub.getCurrentDate());
            dao.updateCarrier(vo);
            atx.setOutMsg(vo.getRowXml(), "修改成功！");
        } catch (Exception e) {
            //设置失败异常处理
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 查询承运信息
     *
     * @throws Exception
     */
    public void searchCarrier() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String shipId = Pub.val(request, "shipId");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchCarrier(page, user, conditions, shipId);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 删除预授权项目
     *
     * @throws Exception
     */
    public void deleteCarrier() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        String DEL_ID = Pub.val(request, "DEL_ID");
        try {
            PtBuOrderShipCarrierVO vo = new PtBuOrderShipCarrierVO();
            vo.setDelId(DEL_ID);
            dao.deleteCarrier(vo);
            //返回更新结果和成功信息
            atx.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
}
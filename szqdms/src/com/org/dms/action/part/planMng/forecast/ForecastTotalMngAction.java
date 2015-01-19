package com.org.dms.action.part.planMng.forecast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.planMng.forecast.ForecastTotalMngDao;
import com.org.dms.vo.part.PtBaWarehouseCommonVO;
import com.org.dms.vo.part.PtBuForcastDtlVO;
import com.org.dms.vo.part.PtBuForcastVO;
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
 * 预测汇总上报Action
 *
 * 配件预测的增删改查
 * @author zhengyao
 * @date 2014-07-18
 * @version 1.0
 */
public class ForecastTotalMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义仓库管理Dao对象
    private ForecastTotalMngDao forecastTotalMngDao = ForecastTotalMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 新增配件预测明细
     * @throws Exception
     */
    public void insertTotalPartReportDetail() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 预测主键
            String forcastMonth = hashMap.get("FORCASTMONTH");
            String wheres = " FORCAST_MONTH='" + forcastMonth + "'";
            QuerySet querySet = (QuerySet)forecastTotalMngDao.searchForecast(pageManager,user,wheres,false);
            String forcastId=querySet.getString(1,"FORCAST_ID");
            // 配件主键
            String partIds = hashMap.get("PARTIDS");
            // 配件代码
            String partCodes = hashMap.get("PARTCODES");
            // 配件名称
            String partNames = hashMap.get("PARTNAMES");
            // 预测数量
            String counts = hashMap.get("COUNTS");
            String[] partIdArr = partIds.split(",");
            String[] partCodeArr = partCodes.split(",");
            String[] partNameArr = partNames.split(",");
            String[] countArr = counts.split(",");
            // 配件预测表(pt_bu_forcast)对应的实体
            PtBuForcastDtlVO dptBuForcastDtlVO = new PtBuForcastDtlVO();
            dptBuForcastDtlVO.setForcastId(forcastId);
             for (int i = 0; i < partIdArr.length; i++) {
                 // 配件预测明细表(pt_bu_forcast_dtl)对应的实体
                 PtBuForcastDtlVO ptBuForcastDtlVO = new PtBuForcastDtlVO();
                 // 预测主键
                 ptBuForcastDtlVO.setForcastId(forcastId);
                 // 配件主键
                 ptBuForcastDtlVO.setPartId(partIdArr[i]);
                 // 配件代码
                 ptBuForcastDtlVO.setPartCode(partCodeArr[i]);
                 // 配件名称
                 ptBuForcastDtlVO.setPartName(partNameArr[i]);
                 // 预测数量
                 ptBuForcastDtlVO.setCount(countArr[i]);
                 // 预测配件验证
                 if (0 < forecastTotalMngDao.searchByForecastMonth(forcastMonth,ptBuForcastDtlVO,user).getRowCount()){
                     // ------ 预测配件存在
                     // 修改配件预测明细表(pt_bu_forcast_dtl)
                     forecastTotalMngDao.updateForecastDetail(ptBuForcastDtlVO);
                 } else {
                     // ------ 预测配件不存在
                     // 插入配件预测明细表(pt_bu_forcast_dtl)
                     forecastTotalMngDao.insertPartReportDetail(ptBuForcastDtlVO);
                 }
             }
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 提报状态验证
     * @throws Exception
     */
    public void reportCheck() throws Exception {

        try {
        	PageManager pageManager = new PageManager();
        	String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // 预测月份
            String forecastMonth = Pub.val(requestWrapper, "forcastMonth");
            // QuerySet：结果集封装对象
            BaseResultSet baseResultSet = forecastTotalMngDao.searchByForecastMonth1(pageManager,conditions,forecastMonth, user);
            // 返回更新结果和成功信息
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 配件预测查询
     * @throws Exception
     */
    public void searchForecast() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = (BaseResultSet)forecastTotalMngDao.searchForecast(pageManager, user, conditions,true);
            // 绑定配件预测状态
            baseResultSet.setFieldDic("FORCAST_STATUS", DicConstant.PJYCZT);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 服务商配件预测查询
     * @throws Exception
     */
    public void searchForecastByDealer() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = forecastTotalMngDao.searchForecastByDealer(pageManager, user, conditions);
            // 绑定配件预测状态
            baseResultSet.setFieldDic("FORCAST_STATUS", DicConstant.PJYCZT);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 配件预测明细查询
     * @throws Exception
     */
    public void searchForecastDetail() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = forecastTotalMngDao.searchForecastDetail(pageManager,conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 配件预测明细计算(相同配件代码,对应数量累加)
     * @throws Exception
     */
    public void searchForecastDetailByMonth() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            RequestUtil.getConditionsWhere(requestWrapper,pageManager);
            // 预测月份
            String conditions = hashMap.get("FORCAST_MONTH");
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = null;
            QuerySet querySet = forecastTotalMngDao.searchByForecastMonth(conditions, user);
            if (0 >= querySet.getRowCount()) {
                // ------ 预测汇总不存在
                // 配件预测实体
                PtBuForcastVO ptBuForcastVO = new PtBuForcastVO();
                // 所属公司
                ptBuForcastVO.setCompanyId(user.getCompanyId());
                // 所属机构
                ptBuForcastVO.setOrgId(user.getOrgId());
                // 预测组织主键
                ptBuForcastVO.setForcastOrgId(user.getOrgId());
                // 
                ptBuForcastVO.setOemCompanyId(user.getOemCompanyId());
                // 创建时间
                ptBuForcastVO.setCreateTime(Pub.getCurrentDate());
                // 创建人
                ptBuForcastVO.setCreateUser(user.getAccount());
                // 预测状态(未提报)
                ptBuForcastVO.setForcastStatus(DicConstant.PJYCZT_01);
                // 状态
                ptBuForcastVO.setStatus(DicConstant.YXBS_01);
                // 预测月份
                ptBuForcastVO.setForcastMonth(conditions);
                // Dao方法,预测配件添加
                forecastTotalMngDao.insertForecast(ptBuForcastVO);
                // Dao方法，预测配件明细添加
                forecastTotalMngDao.insertForecastDetail(user,conditions);
            } else {
                
            }
            String wheres = " FORCAST_ID IN( \n"
                          + " SELECT \n"
                          + "     FORCAST_ID \n"
                          + " FROM \n"
                          + "     PT_BU_FORCAST \n"
                          + " WHERE \n"
                          + "     FORCAST_MONTH='" + conditions + "' \n"
                          + " AND  ORG_ID='" + user.getOrgId() + "')";
            // 查询预测汇总明细
            baseResultSet = forecastTotalMngDao.searchForecastDetail(pageManager,wheres);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 提报功能(修改配件预测)
     * @throws Exception
     */
    public void updateForecast() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String forecastMonth = Pub.val(requestWrapper, "forecastMonth");
            // 配件预测表(pt_bu_forcast)对应的实体
            PtBuForcastVO ptBuForcastVO = new PtBuForcastVO();
            // 预测月份
            ptBuForcastVO.setForcastMonth(forecastMonth);
            // 配件状态(已提报)
            ptBuForcastVO.setForcastStatus(DicConstant.PJYCZT_02);
            PtBaWarehouseCommonVO ptBaWarehouseCommonVO = new PtBaWarehouseCommonVO();
            // 通过Dao,查询服务站提报起止日
            String[][] serviceDate = forecastTotalMngDao.searchByReportDay("100301");
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
            int day = Integer.valueOf(simpleDateFormat.format(date));
            // 服务站提报日验证
            if ((Integer.valueOf(serviceDate[0][0]) <= day && day<=Integer.valueOf(serviceDate[0][1]))==false) {
                // FLAG属性(FLAG:true不在提报日内;)
                ptBaWarehouseCommonVO.setFlag("true");
                // 返回更新结果和成功信息
                actionContext.setOutMsg(ptBaWarehouseCommonVO.getRowXml(), "今天不在提报日内！");
                return;
            }
            // 通过Dao,执行更新
            forecastTotalMngDao.updateForecast(ptBuForcastVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg(ptBuForcastVO.getRowXml(), "修改成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 汇总提报日验证
     * @throws Exception
     */
    public void searchByReportDay() throws Exception {

        try {
            // 通过Dao,查询服务站提报起止日
            String[][] serviceDate = forecastTotalMngDao.searchByReportDay("100301");
            PtBaWarehouseCommonVO ptBaWarehouseCommonVO = new PtBaWarehouseCommonVO();
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
            int day = Integer.valueOf(simpleDateFormat.format(date));
            // 服务站提报日验证
            if ((Integer.valueOf(serviceDate[0][0]) < day && day<Integer.valueOf(serviceDate[0][1]))==false) {
                // FLAG属性(FLAG:true不在提报日内;)
                ptBaWarehouseCommonVO.setFlag("true");
                // 返回更新结果和成功信息
                actionContext.setOutMsg(ptBaWarehouseCommonVO.getRowXml(), "今天不在提报日内！");
                return;
            }
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 删除配件预测
     * @throws Exception
     */
    public void deleteForecast() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String forcastId = Pub.val(requestWrapper, "forcastId");
            // 配件预测表(pt_bu_forcast)对应的实体
            PtBuForcastVO ptBuForcastVO = new PtBuForcastVO();
            // 预测主键
            ptBuForcastVO.setForcastId(forcastId);
            // 配件预测明细表(pt_bu_forcast_dtl)对应的实体
            PtBuForcastDtlVO ptBuForcastDtlVO = new PtBuForcastDtlVO();
           // 预测主键
            ptBuForcastDtlVO.setForcastId(forcastId);
            // 通过Dao,执行删除配件预测
            forecastTotalMngDao.deleteForecast(ptBuForcastVO,user);
            // 通过Dao,执行删除配件预测明细
            forecastTotalMngDao.deleteForecastDetail(ptBuForcastDtlVO,"");
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除配件预测明细
     * @throws Exception
     */
    public void deleteForecastDetail() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 预测月份
            String forcastMonth = Pub.val(requestWrapper, "forecastMonth");
            String wheres = " FORCAST_MONTH='" + forcastMonth + "'";
            QuerySet querySet = (QuerySet)forecastTotalMngDao.searchForecast(pageManager,user,wheres,false);
            String forcastId = querySet.getString(1,"FORCAST_ID");
            // 配件代码
            String partCode = Pub.val(requestWrapper, "partCode");
            // 配件预测明细表(pt_bu_forcast_dtl)对应的实体
            PtBuForcastDtlVO ptBuForcastDtlVO = new PtBuForcastDtlVO();
           // 预测主键赋值
            ptBuForcastDtlVO.setForcastId(forcastId);
            String where = " AND PART_CODE='" + partCode + "'";
            // 通过Dao,执行删除
            forecastTotalMngDao.deleteForecastDetail(ptBuForcastDtlVO,where);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

}

package com.org.dms.action.part.planMng.forecast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.planMng.forecast.ForecastMngDao;
import com.org.dms.vo.part.PtBaWarehouseCommonVO;
import com.org.dms.vo.part.PtBuForcastDtlVO;
import com.org.dms.vo.part.PtBuForcastVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.params.ParaManager;
import com.org.framework.params.UserPara.UserParaConfigureVO;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 预测上报Action
 *
 * 配件预测的增删改查
 * @author zhengyao
 * @date 2014-07-15
 * @version 1.0
 */
public class ForecastMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义仓库管理Dao对象
    private ForecastMngDao forecastMngDao = ForecastMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 定义response对象
    ResponseWrapper responseWrapper= actionContext.getResponse();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 错误数据导出
     * @throws Exception
     */
    public void expData()throws Exception{

        try {
        	//获取封装后的request对象
        	RequestWrapper request = actionContext.getRequest();
            // 将request流中的信息转化为where条件
            String conditions = Pub.val(request, "seqs");
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(0,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_COUNT");
            hBean.setTitle("配件数量");
            header.add(1,hBean);

            QuerySet querySet = forecastMngDao.expData(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "CWSJDC", header, querySet);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 预测配件明细导入
     * @throws Exception
     */
    public void insertImport() throws Exception {

        try {
            // 预测主键
            String forcastId = Pub.val(requestWrapper, "forcastMonth");
            String tmpNo = Pub.val(requestWrapper, "tmpNo");
        	String sql = "";
        	if (!"".equals(tmpNo)&&tmpNo!=null) {
        		sql = " AND B.TMP_NO NOT IN ("+tmpNo+") ";
        	}
            // 预测配件明细更新
            forecastMngDao.updateForecastDtl(forcastId,user,sql);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 预测配件明细临时表查询(导入)
     * @throws Exception
     */
    public void searchImport() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = (BaseResultSet)forecastMngDao.searchImport(pageManager, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
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
            BaseResultSet baseResultSet = (BaseResultSet)forecastMngDao.searchForecast(pageManager, user, conditions,true);
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
            BaseResultSet baseResultSet = forecastMngDao.searchForecastDetail(pageManager,conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }    

    /**
     * 查询配件
     * @throws Exception
     */
    public void searchPart() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // 将request流中的信息转化为where条件
            String forcastMonth = Pub.val(requestWrapper, "forcastMonth");
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = forecastMngDao.searchPart(pageManager, user, conditions,forcastMonth);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 新增配件预测
     * @throws Exception
     */
    public void insertForecast() throws Exception {

        try {
            // 配件预测表(pt_bu_forcast)对应的实体
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
            String forcastMonth = Pub.val(requestWrapper, "forcastMonth");
            // 预测月份
            ptBuForcastVO.setForcastMonth(forcastMonth);
            PtBaWarehouseCommonVO ptBaWarehouseCommonVO = new PtBaWarehouseCommonVO();
            // 重复验证
            if (0 < forecastMngDao.searchByForecastMonth(ptBuForcastVO, user).getRowCount()) {
                // FLAG属性(FLAG:true有重复数据;)
                ptBaWarehouseCommonVO.setFlag("true");
                // 返回更新结果和成功信息
                actionContext.setOutMsg(ptBaWarehouseCommonVO.getRowXml(), "数据已存在！");
                return;
            }
            // 通过dao，执行插入
            forecastMngDao.insertForecast(ptBuForcastVO);
            // 返回插入结果和成功信息
            actionContext.setOutMsg(ptBuForcastVO.getRowXml(), "新增成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 新增配件预测明细
     * @throws Exception
     */
    public void insertPartReportDetail() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 预测主键
            String forcastMonth = hashMap.get("FORCASTMONTH");
            String wheres = " FORCAST_MONTH='" + forcastMonth + "'";
            QuerySet querySet = (QuerySet)forecastMngDao.searchForecast(pageManager,user,wheres,false);
            // 预测主键
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
                 if (0 < forecastMngDao.searchByForecastMonth(ptBuForcastDtlVO).getRowCount()){
                     // ------ 预测配件存在
                     // 修改配件预测明细表(pt_bu_forcast_dtl)
                     forecastMngDao.updateForecastDetail(ptBuForcastDtlVO);
                 } else {
                     // ------ 预测配件不存在
                     //插入配件预测明细表(pt_bu_forcast_dtl)
                     forecastMngDao.insertPartReportDetail(ptBuForcastDtlVO);
                 }
             }
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 提报功能(修改配件预测)
     * @throws Exception
     */
    public void updateForecast() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String forcastId = Pub.val(requestWrapper, "forcastId");
            // 配件预测表(pt_bu_forcast)对应的实体
            PtBuForcastVO ptBuForcastVO = new PtBuForcastVO();
            // 预测主键
            ptBuForcastVO.setForcastId(forcastId);
            // 配件状态(已提报)
            ptBuForcastVO.setForcastStatus(DicConstant.PJYCZT_02);
            PtBaWarehouseCommonVO ptBaWarehouseCommonVO = new PtBaWarehouseCommonVO();
            // 通过Dao,查询服务站提报起止日
            UserParaConfigureVO userPara = (UserParaConfigureVO)ParaManager.getInstance().getUserParameter("100201");
            String paraValue1 = userPara.getParavalue1();
            String paraValue2 = userPara.getParavalue2();
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
            int day = Integer.valueOf(simpleDateFormat.format(date));
            // 服务站提报日验证
            if ((Integer.valueOf(paraValue1) <= day && day<=Integer.valueOf(paraValue2))==false) {
                // FLAG属性(FLAG:true不在提报日内;)
                ptBaWarehouseCommonVO.setFlag("true");
                // 返回更新结果和成功信息
                actionContext.setOutMsg(ptBaWarehouseCommonVO.getRowXml(), "今天不在提报日内！");
                return;
            }
            // 通过Dao,执行更新
            forecastMngDao.updateForecast(ptBuForcastVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg(ptBuForcastVO.getRowXml(), "修改成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
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
            forecastMngDao.deleteForecast(ptBuForcastVO,user);
            // 通过Dao,执行删除配件预测明细
            forecastMngDao.deleteForecastDetail(ptBuForcastDtlVO,"");
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
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            // 预测主键
            String forcastId = Pub.val(requestWrapper, "forcastId");
            // 配件代码
            String partCode = Pub.val(requestWrapper, "partCode");
            // 配件预测明细表(pt_bu_forcast_dtl)对应的实体
            PtBuForcastDtlVO ptBuForcastDtlVO = new PtBuForcastDtlVO();
           // 预测主键赋值
            ptBuForcastDtlVO.setForcastId(forcastId);
            String where = " AND PART_CODE='" + partCode + "'";
            // 通过Dao,执行删除
            forecastMngDao.deleteForecastDetail(ptBuForcastDtlVO,where);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
}
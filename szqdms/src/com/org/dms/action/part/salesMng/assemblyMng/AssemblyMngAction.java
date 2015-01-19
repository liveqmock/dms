package com.org.dms.action.part.salesMng.assemblyMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.salesMng.assemblyMng.AssemblyMngDao;
import com.org.dms.vo.part.PtBaPchContractCheckUniqueVO;
import com.org.dms.vo.part.PtBuAssemblyAnnexVO;
import com.org.dms.vo.part.PtBuAssemblyDtlVO;
import com.org.dms.vo.part.PtBuAssemblyVO;
import com.org.dms.vo.part.PtBuPchContractDtlVO;
import com.org.dms.vo.part.PtBuPchContractVO;
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
 * 总成附件确认Action
 *
 * 总成附件的增删改查
 * @author zhengyao
 * @date 2014-10-13
 * @version 1.0
 */
public class AssemblyMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义仓库管理Dao对象
    private AssemblyMngDao assemblyMngDao = AssemblyMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);


    /**
     * 直营订单提报查询
     *
     * @throws Exception
     */
    public void searchAssemblyCheck() throws Exception {

        try {
            PageManager pageManager = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            BaseResultSet bs = assemblyMngDao.searchAssemblyCheck(pageManager,conditions);
            actionContext.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 总成附件确认查询
     * @throws Exception
     */
    public void searchForecast() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = (BaseResultSet)assemblyMngDao.searchForecast(pageManager, user, conditions,true);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 总成附件确认明细查询
     * @throws Exception
     */
    public void searchForecastDetail() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = assemblyMngDao.searchForecastDetail(pageManager,conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    } 
    
    public void searchForecastDetail1() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            String id = Pub.val(requestWrapper, "ID");
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = assemblyMngDao.searchForecastDetail1(pageManager,id);
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
            String assemblyId = Pub.val(requestWrapper, "assemblyId");
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = assemblyMngDao.searchPart(pageManager, user, conditions,assemblyId);
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
            // 总成附件确认表(PT_BU_ASSEMBLY)对应的实体
            PtBuAssemblyVO ptBuAssemblyVO = new PtBuAssemblyVO();
            // 渠道ID
            ptBuAssemblyVO.setOrgId(user.getOrgId());
            // 渠道代码
            ptBuAssemblyVO.setOrgCode(user.getOrgCode());
            // 渠道名称
            ptBuAssemblyVO.setOrgName(user.getOrgDept().getOName());
            // 
            ptBuAssemblyVO.setOemCompanyId(user.getOemCompanyId());
            // 创建时间
            ptBuAssemblyVO.setCreateTime(Pub.getCurrentDate());
            // 创建人
            ptBuAssemblyVO.setCreateUser(user.getAccount());
            // 确认状态(已保存)
            ptBuAssemblyVO.setConfigStatus(DicConstant.ZCFJQRZT_01);
            // 状态
            ptBuAssemblyVO.setStatus(DicConstant.YXBS_01);
            // 通过dao，执行插入
            assemblyMngDao.insertForecast(ptBuAssemblyVO);
            // 返回插入结果和成功信息
            actionContext.setOutMsg(ptBuAssemblyVO.getRowXml(), "");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 修改总成附件确认明细(确认)
     * @throws Exception
     */
    public void updatePartReportDetail() throws Exception {

        try {
            // 总成附件确认主键
//            String assemblyId=hashMap.get("ASSEMBLYID");
        	String id = Pub.val(requestWrapper, "ID");
             // 总成附件确认明细表(PT_BU_ASSEMBLY)对应的实体
            PtBuAssemblyVO vo = new PtBuAssemblyVO();
            // 总成附件确认主键
               vo.setAssemblyId(id);
               // 更新状态
               vo.setConfigStatus(DicConstant.ZCFJQRZT_03);
               vo.setApplyTime(Pub.getCurrentDate());
               assemblyMngDao.updateForecast(vo,user);
//            // 配件主键
//            String partIds = hashMap.get("PARTIDS");
//            // 配件代码
//            String partCodes = hashMap.get("PARTCODES");
//            // 配件名称
//            String partNames = hashMap.get("PARTNAMES");
//            // 备注
//            String remarks = hashMap.get("REMARKS");
//            String[] partIdArr = partIds.split(",");
//            String[] partCodeArr = partCodes.split(",");
//            String[] partNameArr = partNames.split(",");
//            String[] remarkArr = remarks.split(",");
//             for (int i = 0; i < partIdArr.length; i++) {
//                 // 配件预测明细表(pt_bu_forcast_dtl)对应的实体
//                 PtBuAssemblyDtlVO ptBuAssemblyDtlVO = new PtBuAssemblyDtlVO();
//                 // 总成附件确认主键
//                 ptBuAssemblyDtlVO.setAssemblyId(assemblyId);
//                 // 配件主键
//                 ptBuAssemblyDtlVO.setPartId(partIdArr[i]);
//                 // 配件代码
//                 ptBuAssemblyDtlVO.setPartCode(partCodeArr[i]);
//                 // 配件名称
//                 ptBuAssemblyDtlVO.setPartName(partNameArr[i]);
//                 // 备注
//                 ptBuAssemblyDtlVO.setRemarks(remarkArr[i]);
//                 // 更新人
//                 ptBuAssemblyDtlVO.setUpdateUser(user.getAccount());
//                 // 更新时间
//                 ptBuAssemblyDtlVO.setCreateTime(Pub.getCurrentDate());
//                 //插入配件预测明细表(pt_bu_forcast_dtl)
//                 assemblyMngDao.updateForecastDetail(ptBuAssemblyDtlVO);
//             }
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 新增总成附件确认明细
     * @throws Exception
     */
    public void insertPartReportDetail() throws Exception {

        try {
            // 总成附件确认主键
            String assemblyId=hashMap.get("ASSEMBLYID");
            // 配件主键
            String partIds = hashMap.get("PARTIDS");
            // 配件代码
            String partCodes = hashMap.get("PARTCODES");
            // 配件名称
            String partNames = hashMap.get("PARTNAMES");
            String[] partIdArr = partIds.split(",");
            String[] partCodeArr = partCodes.split(",");
            String[] partNameArr = partNames.split(",");
             for (int i = 0; i < partIdArr.length; i++) {
                 // 配件预测明细表(pt_bu_forcast_dtl)对应的实体
                 PtBuAssemblyDtlVO ptBuAssemblyDtlVO = new PtBuAssemblyDtlVO();
                 // 总成附件确认主键
                 ptBuAssemblyDtlVO.setAssemblyId(assemblyId);
                 // 配件主键
                 ptBuAssemblyDtlVO.setPartId(partIdArr[i]);
                 // 配件代码
                 ptBuAssemblyDtlVO.setPartCode(partCodeArr[i]);
                 // 配件名称
                 ptBuAssemblyDtlVO.setPartName(partNameArr[i]);
                 // 创建人
                 ptBuAssemblyDtlVO.setCreateUser(user.getAccount());
                 // 创建时间
                 ptBuAssemblyDtlVO.setCreateTime(Pub.getCurrentDate());
                 //插入配件预测明细表(pt_bu_forcast_dtl)
                 assemblyMngDao.insertPartReportDetail(ptBuAssemblyDtlVO);
             }
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 提报功能(修改总成附件确认)
     * @throws Exception
     */
    public void updateForecast() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String assemblyId = Pub.val(requestWrapper, "assemblyId");
            // 总成附件确认表(pt_bu_forcast)对应的实体
            PtBuAssemblyVO ptBuAssemblyVO = new PtBuAssemblyVO();
            // 总成附件确认主键
            ptBuAssemblyVO.setAssemblyId(assemblyId);
            // 配件状态(已提交)
            ptBuAssemblyVO.setConfigStatus(DicConstant.ZCFJQRZT_02);
            // 提交时间
            ptBuAssemblyVO.setApplyTime(Pub.getCurrentDate());
            // 通过Dao,执行更新
            assemblyMngDao.updateForecast(ptBuAssemblyVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg(ptBuAssemblyVO.getRowXml(), "修改成功！");
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
            String assemblyId = Pub.val(requestWrapper, "assemblyId");
            // 总成附件确认表(pt_bu_forcast)对应的实体
            PtBuAssemblyVO ptBuAssemblyVO = new PtBuAssemblyVO();
            // 总成附件确认主键
            ptBuAssemblyVO.setAssemblyId(assemblyId);
            // 总成附件确认明细表(pt_bu_forcast_dtl)对应的实体
            PtBuAssemblyDtlVO ptBuAssemblyDtlVO = new PtBuAssemblyDtlVO();
            // 总成附件确认主键
            ptBuAssemblyDtlVO.setAssemblyId(assemblyId);
            // 通过Dao,执行删除配件预测
            assemblyMngDao.deleteForecast(ptBuAssemblyVO,user);
            // 通过Dao,执行删除配件预测明细
            assemblyMngDao.deleteForecastDetail(ptBuAssemblyDtlVO,"");
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
            // 总成附件确认主键
            String assemblyId = Pub.val(requestWrapper, "assemblyId");
            // 配件代码
            String partCode = Pub.val(requestWrapper, "partCode");
            // 总成附件确认明细表(pt_bu_assembly_dtl)对应的实体
            PtBuAssemblyDtlVO ptBuAssemblyDtlVO = new PtBuAssemblyDtlVO();
            // 总成附件确认主键赋值
            ptBuAssemblyDtlVO.setAssemblyId(assemblyId);
            String where = " AND PART_CODE='" + partCode + "'";
            // 通过Dao,执行删除
            assemblyMngDao.deleteForecastDetail(ptBuAssemblyDtlVO,where);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
    //partUpdate
    public void partInsert() throws Exception {
        RequestWrapper request = actionContext.getRequest();
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String PART_CODE = hm.get("PART_CODE");
            String A_PART_ID = hm.get("A_PART_ID");
            QuerySet check = assemblyMngDao.checkUnique(PART_CODE,A_PART_ID);
            PtBaPchContractCheckUniqueVO c_co = new PtBaPchContractCheckUniqueVO();
            if(check.getRowCount()>0){
            	c_co.setFlag("true");
            	actionContext.setOutMsg(c_co.getRowXml(), "数据已存在！");
                return;
            }else{
            	PtBuAssemblyAnnexVO p_vo = new PtBuAssemblyAnnexVO();
	            p_vo.setValue(hm);
	            assemblyMngDao.partInsert(p_vo);
	            //返回插入结果和成功信息
	            actionContext.setOutMsg(p_vo.getRowXml(), "新增成功！");
            }
        } catch (Exception e) {
        	actionContext.setException(e);
            logger.error(e);
        }
    }
    public void partUpdate() throws Exception {
        RequestWrapper request = actionContext.getRequest();
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            PtBuAssemblyAnnexVO p_vo = new PtBuAssemblyAnnexVO();
            p_vo.setValue(hm);
            assemblyMngDao.partUpdate(p_vo);
            //返回插入结果和成功信息
            actionContext.setOutMsg(p_vo.getRowXml(), "修改成功！");
        } catch (Exception e) {
        	actionContext.setException(e);
            logger.error(e);
        }
    }
    public void contractPartDelete() throws Exception
    {
    	RequestWrapper request = actionContext.getRequest();
        try
        {
			String DTL_ID = Pub.val(request, "DTL_ID");
			assemblyMngDao.updateParts(DTL_ID);
            actionContext.setOutMsg("","配件删除成功！");
            
        }
        catch (Exception e)
        {
        	actionContext.setException(e);
            logger.error(e);
        }
    }
    public void closeAss() throws Exception {

        try {
            // 总成附件确认主键
        	String id = Pub.val(requestWrapper, "assemblyId");
            PtBuAssemblyVO vo = new PtBuAssemblyVO();
            // 总成附件确认主键
               vo.setAssemblyId(id);
               vo.setConfigStatus(DicConstant.ZCFJQRZT_04);
               vo.setUpdateUser(user.getAccount());
               vo.setUpdateTime(Pub.getCurrentDate());
               vo.setApplyTime(Pub.getCurrentDate());
               assemblyMngDao.updateForecast(vo,user);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

}
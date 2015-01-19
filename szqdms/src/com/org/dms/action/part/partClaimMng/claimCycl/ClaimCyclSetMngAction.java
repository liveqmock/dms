package com.org.dms.action.part.partClaimMng.claimCycl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.partClaimMng.ClaimCyclSetMngDao;
import com.org.dms.vo.part.PtBaClaimCycleSetVO;
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
 * 配件三包期设置Action
 *
 * 配件三包期基本设置
 * @author zhengyao
 * @date 2014-08-08
 * @version 1.0
 */
public class ClaimCyclSetMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义配件三包期设置Dao对象
    private ClaimCyclSetMngDao claimCyclSetMngDao = ClaimCyclSetMngDao.getInstance(actionContext);
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
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("CLAIM_MONTH");
            hBean.setTitle("三包月份");
            header.add(2,hBean);

            hBean = new HeaderBean();
            hBean.setName("EXTENSION_MONTH");
            hBean.setTitle("延保月份");
            header.add(3,hBean);

            QuerySet querySet = claimCyclSetMngDao.expData(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "CWSJDC", header, querySet);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 配件三包期设置导入
     * @throws Exception
     */
    public void insertImport() throws Exception {

        try {
        	String tmpNo = Pub.val(requestWrapper, "tmpNo");
        	String sql = "";
        	if (!"".equals(tmpNo)&&tmpNo!=null) {
        		sql = " AND A.TMP_NO NOT IN ("+tmpNo+") ";
        	}
            // 配件三包期设置添加
            claimCyclSetMngDao.updateClaimCycleSet(user,sql);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 配件三包期设置临时表查询(导入)
     * @throws Exception
     */
    public void searchImport() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = claimCyclSetMngDao.searchImport(pageManager, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 配件三包期设置(下载模板)
     * @throws Exception
     */
    public void download()throws Exception{

        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(0,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("CLAIM_MONTH");
            hBean.setTitle("三包月份");
            header.add(2,hBean);

            hBean = new HeaderBean();
            hBean.setName("EXTENSION_MONTH");
            hBean.setTitle("延保月份");
            header.add(3,hBean);

            QuerySet querySet = claimCyclSetMngDao.download();
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "PJSBQSZDCMB", header, querySet);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 配件三包期设置查询
     * @throws Exception
     */
    public void searchClaimCyclSet() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = claimCyclSetMngDao.searchClaimCyclSet(pageManager, user, conditions,true);
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
     * 查询配件
     * @throws Exception
     */
    public void searchPart() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // 将request流中的信息转化为where条件
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = claimCyclSetMngDao.searchPart(pageManager, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 新增配件三包期设置
     * @throws Exception
     */
    public void insertClaimCyclSet() throws Exception {

        try {
            // 配件主键
            String partIds = hashMap.get("PARTIDS");
            // 配件代码
            String partCodes = hashMap.get("PARTCODES");
            // 配件名称
            String partNames = hashMap.get("PARTNAMES");
            // 三包月份
            String claimMonths = hashMap.get("CLAIMMONTHS");
            String[] partIdArr = partIds.split(",");
            String[] partCodeArr = partCodes.split(",");
            String[] partNameArr = partNames.split(",");
            String[] claimMonthArr = claimMonths.split(",");
            for (int i = 0; i < partIdArr.length; i++) {
                PtBaClaimCycleSetVO ptBaClaimCycleSetVO = new PtBaClaimCycleSetVO();
                ptBaClaimCycleSetVO.setCompanyId(user.getCompanyId());
                ptBaClaimCycleSetVO.setOrgId(user.getOrgId());
                 // 创建时间
                ptBaClaimCycleSetVO.setCreateTime(Pub.getCurrentDate());
                // 创建人
                ptBaClaimCycleSetVO.setCreateUser(user.getAccount());
                // 状态
                ptBaClaimCycleSetVO.setStatus(DicConstant.YXBS_01);
                ptBaClaimCycleSetVO.setOemCompanyId(user.getOemCompanyId());
                // 配件主键
                ptBaClaimCycleSetVO.setPartId(partIdArr[i]);
                // 配件代码
                ptBaClaimCycleSetVO.setPartCode(partCodeArr[i]);
                // 配件名称
                ptBaClaimCycleSetVO.setPartName(partNameArr[i]);
                // 三包月份
                ptBaClaimCycleSetVO.setClaimMonth(claimMonthArr[i]);
                claimCyclSetMngDao.insertClaimCyclSet(ptBaClaimCycleSetVO);
            }
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 修改配件三包期设置
     * @throws Exception
     */
    public void updateClaimCyclSet() throws Exception {

        try {
            PtBaClaimCycleSetVO ptBaClaimCycleSetVO = new PtBaClaimCycleSetVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBaClaimCycleSetVO.setValue(hashMap);
            // 通过Dao,执行更新
            claimCyclSetMngDao.updateClaimCyclSet(ptBaClaimCycleSetVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg(ptBaClaimCycleSetVO.getRowXml(), "修改成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除配件三包期设置
     * @throws Exception
     */
    public void deleteClaimCyclSet() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String cycleId = Pub.val(requestWrapper, "cycleId");
            PtBaClaimCycleSetVO ptBaClaimCycleSetVO = new PtBaClaimCycleSetVO();
            // 主键ID
            ptBaClaimCycleSetVO.setCycleId(cycleId);
            // 通过Dao,执行删除配件三包期设置
            claimCyclSetMngDao.deleteClaimCyclSet(ptBaClaimCycleSetVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
}
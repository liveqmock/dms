package com.org.dms.action.part.storageMng.warehouseMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.warehouseMng.WarehouseMngDao;
import com.org.dms.vo.part.PtBaWarehouseAreaVO;
import com.org.dms.vo.part.PtBaWarehouseCommonVO;
import com.org.dms.vo.part.PtBaWarehouseVO;
import com.org.dms.vo.part.PtBaWarehouseVO_Ext;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 仓库管理Action
 *
 * 仓库的增删改查
 * @author zhengyao
 * @date 2014-07-08
 * @version 1.0
 */
public class WarehouseMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义仓库管理Dao对象
    private WarehouseMngDao wareHouseMngDao = WarehouseMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 仓库查询
     * @throws Exception
     */
    public void searchWareHouse() throws Exception {

        try {
            // 定义查询分页对象
            PageManager page = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, page);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = wareHouseMngDao.searchWareHouse(page, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 新增仓库
     * @throws Exception
     */
    public void insertWareHouse() throws Exception {

        try {
            // 仓库表(PT_BA_WAREHOUSE)对应的实体
            PtBaWarehouseVO ptBaWarehouseVO = new PtBaWarehouseVO();
            // 页面回显VO
            PtBaWarehouseVO_Ext ptBaWarehouseVO_Ext = new PtBaWarehouseVO_Ext();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBaWarehouseVO.setValue(hashMap);
            // 所属公司
            ptBaWarehouseVO.setCompanyId(user.getCompanyId());
            // 所属机构
            ptBaWarehouseVO.setOemCompanyId(user.getOemCompanyId());
            // 创建时间
            ptBaWarehouseVO.setCreateTime(Pub.getCurrentDate());
            // 创建人
            ptBaWarehouseVO.setCreateUser(user.getAccount());
            // 状态
            ptBaWarehouseVO.setStatus(DicConstant.YXBS_01);
            PtBaWarehouseCommonVO ptBaWarehouseCommonVO = new PtBaWarehouseCommonVO();
            // 重复验证
            if (0 < wareHouseMngDao.searchByWareHouseCode(ptBaWarehouseVO, user).getRowCount()) {
                // FLAG属性(FLAG:true有重复数据;)
            	ptBaWarehouseCommonVO.setFlag("true");
                // 返回更新结果和成功信息
                actionContext.setOutMsg(ptBaWarehouseCommonVO.getRowXml(), "数据已存在！");
                return;
            }
            // 通过dao，执行插入
            wareHouseMngDao.insertWareHouse(ptBaWarehouseVO);
            // 将hashmap映射到vo对象中,用于页面回显
            ptBaWarehouseVO_Ext.setValue(hashMap);
            // 仓库主键
            ptBaWarehouseVO_Ext.setWarehouseId(ptBaWarehouseVO.getWarehouseId());
            // 仓库类型
            ptBaWarehouseVO_Ext.setParaKey(ptBaWarehouseVO.getWarehouseType());
            // 状态
            ptBaWarehouseVO_Ext.setStatus(DicConstant.YXBS_01);
            ptBaWarehouseVO_Ext.bindFieldToDic("WAREHOUSE_STATUS", "YXBS");
            ptBaWarehouseVO_Ext.bindFieldToOrgInfo("ORG_ID");
            // 返回插入结果和成功信息
            actionContext.setOutMsg(ptBaWarehouseVO_Ext.getRowXml(), "新增成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 修改仓库
     * @throws Exception
     */
    public void updateWareHouse() throws Exception {

        try {
            // 仓库表(PT_BA_WAREHOUSE)对应的实体
            PtBaWarehouseVO ptBaWarehouseVO = new PtBaWarehouseVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBaWarehouseVO.setValue(hashMap);
            // 库区表(PT_BA_WAREHOUSE_AREA)对应的实体
            PtBaWarehouseAreaVO ptBaWarehouseAreaVO = new PtBaWarehouseAreaVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBaWarehouseAreaVO.setValue(hashMap);
            // 通过Dao,执行更新
            wareHouseMngDao.updateWareHouse(ptBaWarehouseVO,ptBaWarehouseAreaVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg(ptBaWarehouseVO.getRowXml(), "修改成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除仓库
     * @throws Exception
     */
    public void deleteWareHouse() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String warehouseId = Pub.val(requestWrapper, "warehouseId");
            String warehousecode = Pub.val(requestWrapper, "warehousecode");
            // 仓库表(PT_BA_WAREHOUSE)对应的实体
            PtBaWarehouseVO ptBaWarehouseVO = new PtBaWarehouseVO();
            // 仓库主键
            ptBaWarehouseVO.setWarehouseId(warehouseId);
            // 仓库代码
            ptBaWarehouseVO.setWarehouseCode(warehousecode);
            // 通过Dao,执行删除
            wareHouseMngDao.deleteWareHouse(ptBaWarehouseVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
}
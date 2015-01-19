package com.org.dms.action.part.storageMng.warehouseMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.warehouseMng.PositionHouseMngDao;
import com.org.dms.vo.part.PtBaWarehouseCommonVO;
import com.org.dms.vo.part.PtBaWarehousePositionVO;
import com.org.dms.vo.part.PtBaWarehousePositionVO_Ext;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 库位管理
 *
 * 库位的增删改查
 * @author zhengyao
 * @date 2014-07-08
 * @version 1.0
 */
public class PositionHouseMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 定义dao对象
    private PositionHouseMngDao positionHouseMngDao = PositionHouseMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 查询库位
     * @throws Exception
     */
    public void searchPositionHouse() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // 查询库位表(pt_ba_stock_position)
            BaseResultSet baseResultSet = positionHouseMngDao.searchPositionHouse(pageManager, user, conditions);
            baseResultSet.setFieldDic("POSITION_STATUS", DicConstant.YXBS);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 新增库位
     * @throws Exception
     */
    public void insertPositionHouse() throws Exception {

        try {
            // 库位实体
            PtBaWarehousePositionVO ptBaWarehousePositionVO = new PtBaWarehousePositionVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBaWarehousePositionVO.setValue(hashMap);
            // 所属公司
            ptBaWarehousePositionVO.setCompanyId(user.getCompanyId());
            // 所属机构
            ptBaWarehousePositionVO.setOrgId(user.getOrgId());
            ptBaWarehousePositionVO.setOemCompanyId(user.getOemCompanyId());
            // 创建时间
            ptBaWarehousePositionVO.setCreateTime(Pub.getCurrentDate());
            // 创建人
            ptBaWarehousePositionVO.setCreateUser(user.getAccount());
            // 状态
            ptBaWarehousePositionVO.setStatus(DicConstant.YXBS_01);
            // 共通VO
            PtBaWarehouseCommonVO ptBaWarehouseCommonVO = new PtBaWarehouseCommonVO();
            // 重复验证
            if (0 < positionHouseMngDao.searchByPositionCode(ptBaWarehousePositionVO, user).getRowCount()) {
                // FLAG属性(FLAG:true有重复数据;)
                ptBaWarehouseCommonVO.setFlag("true");
                // 返回更新结果和成功信息
                actionContext.setOutMsg(ptBaWarehouseCommonVO.getRowXml(), "数据已存在！");
                return;
            }
            // 通过dao，执行插入
            positionHouseMngDao.insertPositionHouse(ptBaWarehousePositionVO);
            // 页面回显VO
            PtBaWarehousePositionVO_Ext ptBaWarehousePositionVO_Ext = new PtBaWarehousePositionVO_Ext();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBaWarehousePositionVO_Ext.setValue(hashMap);
            // 返回插入结果和成功信息
            actionContext.setOutMsg(ptBaWarehousePositionVO_Ext.getRowXml(), "新增成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 修改库位
     * @throws Exception
     */
    public void updatePositionHouse() throws Exception {

        try {
            // 库位实体
            PtBaWarehousePositionVO ptBaWarehousePositionVO = new PtBaWarehousePositionVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBaWarehousePositionVO.setValue(hashMap);
            // 执行修改库位表(pt_ba_stock_position)
            positionHouseMngDao.updatePositionHouse(ptBaWarehousePositionVO,user);
            // 页面回显VO
            PtBaWarehousePositionVO_Ext ptBaWarehousePositionVO_Ext = new PtBaWarehousePositionVO_Ext();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBaWarehousePositionVO_Ext.setValue(hashMap);
            // 返回更新结果和成功信息
            actionContext.setOutMsg(ptBaWarehousePositionVO_Ext.getRowXml(), "修改成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除库位
     * @throws Exception
     */
    public void deletePositionHouse() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String positionId = Pub.val(requestWrapper, "positionId");
            // 库位实体
            PtBaWarehousePositionVO ptBaWarehousePositionVO = new PtBaWarehousePositionVO();
            // 库位主键
            ptBaWarehousePositionVO.setPositionId(positionId);
            // 通过Dao,执行删除
            positionHouseMngDao.deletePositionHouse(ptBaWarehousePositionVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
}
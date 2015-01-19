package com.org.dms.action.part.storageMng.warehouseMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.warehouseMng.AreaHouseMngDao;
import com.org.dms.vo.part.PtBaWarehouseAreaVO;
import com.org.dms.vo.part.PtBaWarehouseCommonVO;
import com.org.dms.vo.part.PtBaWarehousePositionVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 库区管理Action
 *
 * 仓库的增删改查
 * @author zhengyao
 * @date 2014-07-08
 * @version 1.0
 */
public class AreaHouseMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 定义dao对象
    private AreaHouseMngDao areaHouseMngDao = AreaHouseMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 查询库区
     * @throws Exception
     */
    public void searchAreaHouse() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = areaHouseMngDao.searchAreaHouse(pageManager, user, conditions);
            baseResultSet.setFieldDic("AREA_ATTR", DicConstant.KQSX);
            baseResultSet.setFieldDic("AREA_STATUS", DicConstant.YXBS);
            baseResultSet.setFieldUserID("KEEP_MAN");
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 新增库区
     * @throws Exception
     */
    public void insertAreaHouse() throws Exception {

        try {
            // 库区实体
            PtBaWarehouseAreaVO ptBaWarehouseAreaVO = new PtBaWarehouseAreaVO();
            //将hashmap映射到vo对象中,完成匹配赋值
            ptBaWarehouseAreaVO.setValue(hashMap);
            // 所属公司
            ptBaWarehouseAreaVO.setCompanyId(user.getCompanyId());
            // 所属机构
            ptBaWarehouseAreaVO.setOrgId(user.getOrgId());
            ptBaWarehouseAreaVO.setOemCompanyId(user.getOemCompanyId());
            // 创建时间
            ptBaWarehouseAreaVO.setCreateTime(Pub.getCurrentDate());
            // 创建人
            ptBaWarehouseAreaVO.setCreateUser(user.getAccount());
            // 状态
            ptBaWarehouseAreaVO.setStatus(DicConstant.YXBS_01);
            // 共通VO
            PtBaWarehouseCommonVO ptBaWarehouseCommonVO = new PtBaWarehouseCommonVO();
            // 重复验证
            if (0 < areaHouseMngDao.searchByAreaCode(ptBaWarehouseAreaVO, user).getRowCount()) {
                // FLAG属性(FLAG:true有重复数据;)
                ptBaWarehouseCommonVO.setFlag("true");
                // 返回更新结果和成功信息
                actionContext.setOutMsg(ptBaWarehouseCommonVO.getRowXml(), "数据已存在！");
                return;
            }
            //通过dao，执行插入
            areaHouseMngDao.insertAreaHouse(ptBaWarehouseAreaVO);
            //返回插入结果和成功信息
            actionContext.setOutMsg(ptBaWarehouseAreaVO.getRowXml(), "新增成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 修改库区
     * @throws Exception
     */
    public void updateAreaHouse() throws Exception {

        try {
            // 库区表(PT_BA_WAREHOUSE_AREA)对应的实体
            PtBaWarehouseAreaVO ptBaWarehouseAreaVO = new PtBaWarehouseAreaVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBaWarehouseAreaVO.setValue(hashMap);
            // 库位表(PT_BA_WAREHOUSE_POSITION)对应的实体
            PtBaWarehousePositionVO ptBaWarehousePositionVO = new PtBaWarehousePositionVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBaWarehousePositionVO.setValue(hashMap);
            // 通过Dao,执行更新
            areaHouseMngDao.updateAreaHouse(ptBaWarehouseAreaVO,ptBaWarehousePositionVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg(ptBaWarehouseAreaVO.getRowXml(), "修改成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除库区
     * @throws Exception
     */
    public void deleteAreaHouse() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String areacode = Pub.val(requestWrapper, "areacode");
            String areaid = Pub.val(requestWrapper, "areaid");
            // 库区实体
            PtBaWarehouseAreaVO ptBaWarehouseAreaVO = new PtBaWarehouseAreaVO();
            // 库区主键
            ptBaWarehouseAreaVO.setAreaId(areaid);
            // 库区code
            ptBaWarehouseAreaVO.setAreaCode(areacode);
            // 通过Dao,执行删除
            areaHouseMngDao.deleteAreaHouse(ptBaWarehouseAreaVO,user);
            actionContext.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
}
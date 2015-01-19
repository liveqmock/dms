package com.org.dms.action.part.storageMng.warehouseMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.warehouseMng.PartRlHouseMngDao;
import com.org.dms.vo.part.PtBaWarehouseCommonVO;
import com.org.dms.vo.part.PtBaWarehousePartRlVO;
import com.org.dms.vo.part.PtBaWarehousePartRlVO_Ext;
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
 * 配件与库位关系维护
 *
 * 配件与库位关系维护的增删改查
 * @author zhengyao
 * @date 2014-07-14
 * @version 1.0
 */
public class PartRlHouseMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 定义dao对象
    private PartRlHouseMngDao partRlHouseMngDao = PartRlHouseMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 查询配件与库位关系维护
     * @throws Exception
     */
    public void searchPartRlHouse() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // 查询配件与库位关系维护表(pt_ba_warehouse_part_rl)
            BaseResultSet baseResultSet = partRlHouseMngDao.searchPartRlHouse(pageManager, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 查询配件
     *
     * @throws Exception
     */
    public void searchPart() throws Exception {

        try {
            //定义查询分页对象
            PageManager pageManager = new PageManager();
            //将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = partRlHouseMngDao.searchPart(pageManager, user, conditions);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 新增配件与库位关系维护
     * @throws Exception
     */
    public void insertPartRlHouse() throws Exception {

        try {
        	String areaId = hashMap.get("AREA_ID");
            // 配件与库位关系维护实体
            PtBaWarehousePartRlVO ptBaWarehousePartRlVO = new PtBaWarehousePartRlVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBaWarehousePartRlVO.setValue(hashMap);
            // 所属公司
            ptBaWarehousePartRlVO.setCompanyId(user.getCompanyId());
            // 所属机构
            ptBaWarehousePartRlVO.setOrgId(user.getOrgId());
            ptBaWarehousePartRlVO.setOemCompanyId(user.getOemCompanyId());
            // 创建时间
            ptBaWarehousePartRlVO.setCreateTime(Pub.getCurrentDate());
            // 创建人
            ptBaWarehousePartRlVO.setCreateUser(user.getAccount());
            // 状态
            ptBaWarehousePartRlVO.setStatus(DicConstant.YXBS_01);
            // 共通VO
            PtBaWarehouseCommonVO ptBaWarehouseCommonVO = new PtBaWarehouseCommonVO();
            // 重复验证
            if (0 < partRlHouseMngDao.searchByPartRlCode(ptBaWarehousePartRlVO, user).getRowCount()) {
                // FLAG属性(FLAG:true有重复数据;)
                ptBaWarehouseCommonVO.setFlag("true");
                // 返回更新结果和成功信息
                actionContext.setOutMsg(ptBaWarehouseCommonVO.getRowXml(), "数据已存在！");
                return;
            }
            // 是默认库位
            if (DicConstant.SF_01.equals(ptBaWarehousePartRlVO.getIfDefault())) {
                // 验证是否存在默认库位
                QuerySet qs = partRlHouseMngDao.checkByPartRlCode(ptBaWarehousePartRlVO, user,"",areaId);
                if (qs.getRowCount() > 0) {
                    throw new Exception("该配件已经存在默认库位："+qs.getString(1, "POSITION_CODE"));
                }
            }
/*            String PART_CODE = hashMap.get("PART_CODE");
            String WAREHOUSE_ID = hashMap.get("WAREHOUSE_ID");
            QuerySet qs = partRlHouseMngDao.checkArea(PART_CODE,areaId,WAREHOUSE_ID);
            if (qs.getRowCount() > 0) {
                throw new Exception("请维护库区信息");
            }*/
            // 通过dao，执行插入
            partRlHouseMngDao.insertPartRlHouse(ptBaWarehousePartRlVO);
            // 页面回显VO
            PtBaWarehousePartRlVO_Ext ptBaWarehousePartRlVO_Ext = new PtBaWarehousePartRlVO_Ext();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBaWarehousePartRlVO_Ext.setValue(hashMap);
            // 返回插入结果和成功信息
            actionContext.setOutMsg(ptBaWarehousePartRlVO_Ext.getRowXml(), "新增成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 修改配件与库位关系维护
     * @throws Exception
     */
    public void updatePartRlHouse() throws Exception {

        try {
        	String areaId = hashMap.get("AREA_ID");
            // 配件与库位关系维护实体
            PtBaWarehousePartRlVO ptBaWarehousePartRlVO = new PtBaWarehousePartRlVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBaWarehousePartRlVO.setValue(hashMap);
            if (DicConstant.YXBS_02.equals(hashMap.get("STATUS"))) {
            	// 配件关系：无效
            	String warehouseId = hashMap.get("WAREHOUSE_ID");
            	QuerySet qs1 = partRlHouseMngDao.checkPartStock(ptBaWarehousePartRlVO,warehouseId);
                if (qs1.getRowCount() > 0) {
                    throw new Exception("该配件对应的库位有库存,不能修改为无效状态.");
                }
            }
            // 是默认库位
            if (DicConstant.SF_01.equals(ptBaWarehousePartRlVO.getIfDefault())) {
            	String where = "AND POSITION_CODE NOT IN ('"+ptBaWarehousePartRlVO.getPositionCode()+"')";
                // 验证是否存在默认库位
                QuerySet qs = partRlHouseMngDao.checkByPartRlCode(ptBaWarehousePartRlVO, user,where,areaId);
                if (qs.getRowCount() > 0) {
                    throw new Exception("该配件已经存在默认库位："+qs.getString(1, "POSITION_CODE"));
                }
            }
            // 执行修改配件与库位关系维护表(pt_ba_warehouse_part_rl)
            partRlHouseMngDao.updatePartRlHouse(ptBaWarehousePartRlVO,user);
            // 页面回显VO
            PtBaWarehousePartRlVO_Ext ptBaWarehousePartRlVO_Ext = new PtBaWarehousePartRlVO_Ext();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBaWarehousePartRlVO_Ext.setValue(hashMap);
            // 返回更新结果和成功信息
            actionContext.setOutMsg(ptBaWarehousePartRlVO_Ext.getRowXml(), "修改成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除配件与库位关系维护
     * @throws Exception
     */
    public void deletePartRlHouse() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String relationId = Pub.val(requestWrapper, "relationId");
            // 库位实体
            PtBaWarehousePartRlVO ptBaWarehousePartRlVO = new PtBaWarehousePartRlVO();
            // 库位主键
            ptBaWarehousePartRlVO.setRelationId(relationId);
            // 通过Dao,执行删除
            partRlHouseMngDao.deletePartRlHouse(ptBaWarehousePartRlVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
    public void getArea() throws Exception {
        RequestWrapper request = actionContext.getRequest();
        PageManager page = new PageManager();
        String PART_CODE = Pub.val(request, "PART_CODE");
        String WAREHOUSE_ID = Pub.val(request, "WAREHOUSE_ID");
        try {
            BaseResultSet bs = partRlHouseMngDao.getArea(page,PART_CODE,WAREHOUSE_ID );
            actionContext.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
}
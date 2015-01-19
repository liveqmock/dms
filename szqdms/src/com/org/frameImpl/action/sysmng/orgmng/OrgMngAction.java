package com.org.frameImpl.action.sysmng.orgmng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaCarrierVO;
import com.org.dms.vo.part.PtBaSupplierVO;
import com.org.dms.vo.part.PtBuAccountVO;
import com.org.frameImpl.Constant;
import com.org.frameImpl.dao.sysmng.orgmng.OrgMngDao;
import com.org.frameImpl.vo.MainDealerVO;
import com.org.frameImpl.vo.TmOrgVO;
import com.org.framework.common.AppInit;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.framework.Globals;
import com.org.framework.common.User;
import com.org.framework.common.cache.CacheManager;
import com.org.framework.component.orgmanage.OrgDeptToXml;
import com.org.framework.log.LogManager;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 机构管理类
 * @author andy
 *
 */
public class OrgMngAction
{
    private Logger logger = com.org.framework.log.log.getLogger(
            "OrgMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private OrgMngDao dao = OrgMngDao.getInstance(atx);
    
    /**
     * 新增方法
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 9:38:01 AM
     */
    public void insert()
        throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) request.getSession().get(Globals.USER_KEY);
		try
		{
			TmOrgVO vo = new TmOrgVO();
			HashMap<String,String> hm = new HashMap<String, String>();
			hm = RequestUtil.getValues(request);
			vo.setValue(hm); 
			//判断机构代码是否已存在
			QuerySet qs = dao.checkOrgCode(vo.getCode());
			if(qs.getRowCount() > 0)
			{ 
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("机构代码已存在，保存失败！");
				}
			}
			//设置通用字段
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			vo.setOemCompanyId(user.getOemCompanyId());
			dao.insertOrg(vo);
			if(vo.getOrgType().equals(DicConstant.ZZLB_12))//如果是供应商，插入供应商表
			{
				PtBaSupplierVO svo = new PtBaSupplierVO();
				svo.setSupplierCode(vo.getCode());
				svo.setSupplierName(vo.getOname());
				svo.setStatus(vo.getStatus());
				svo.setOrgId(vo.getOrgId());
				//公共字段
				svo.setCreateUser(user.getAccount());
				svo.setCreateTime(Pub.getCurrentDate());
				svo.setOemCompanyId(user.getOemCompanyId());
				svo.setCompanyId(user.getOemCompanyId());
				dao.insertSupplier(svo);
			}else if(vo.getOrgType().equals(DicConstant.ZZLB_13))//如果是物流商，插入物流商表
			{
				PtBaCarrierVO cvo = new PtBaCarrierVO();
				cvo.setCarrierCode(vo.getCode());
				cvo.setCarrierName(vo.getOname());
				cvo.setStatus(vo.getStatus());
				cvo.setOrgId(vo.getOrgId());
				
				//公共字段
				cvo.setCreateUser(user.getAccount());
				cvo.setCreateTime(Pub.getCurrentDate());
				cvo.setOemCompanyId(user.getOemCompanyId());
				cvo.setCompanyId(user.getOemCompanyId());
				dao.insertCarrier(cvo);
			}else if(vo.getOrgType().equals(DicConstant.ZZLB_09))//判断是否是配件中心
			{
				//插入渠道信息表main_dealer
				MainDealerVO mVo = new MainDealerVO();
				mVo.setDealerCode(vo.getCode());//设置code
				mVo.setDealerName(vo.getOname());//设置名称
				mVo.setDealerShortname(vo.getSname());//设置简称
				mVo.setStatus(DicConstant.YXBS_01);
				mVo.setCreateUser(user.getAccount());
				mVo.setCreateTime(Pub.getCurrentDate());
				mVo.setOemCompanyId(user.getOemCompanyId());
				mVo.setCompanyId(vo.getCompanyId());
				mVo.setBelongOffice(vo.getPid()); //所属办事处
				mVo.setDealerType(DicConstant.ZZLB_09);//渠道类型
				mVo.setDealerStatus(vo.getBusStatus());//渠道状态
				mVo.setOrgId(vo.getOrgId());//所属机构,一对一关联
				dao.insertDealer(mVo);
				/**
				if(vo.getIsIc().equals(Constant.YXBS_01)) //如果是直营店，只插入现金账户
				{
					PtBuAccountVO cashAccountVO = new PtBuAccountVO();
					cashAccountVO.setAccountType(DicConstant.ZJZHLX_01);//现金
					cashAccountVO.setAvailableAmount("0.00");
					cashAccountVO.setBalanceAmount("0.00");
					cashAccountVO.setOccupyAmount("0.00");
					cashAccountVO.setOrgId(vo.getOrgId());
					cashAccountVO.setOrgCode(vo.getCode());
					cashAccountVO.setCompanyId(vo.getCompanyId());
					cashAccountVO.setOemCompanyId(vo.getOemCompanyId());
					cashAccountVO.setCreateUser(user.getAccount());
					cashAccountVO.setCreateTime(Pub.getCurrentDate());
					cashAccountVO.setStatus(vo.getStatus());
					dao.insertBuAccount(cashAccountVO);
				}else if(vo.getIsAm().equals(Constant.YXBS_01)) //如果是军品店，只插入额度账户
				{
					PtBuAccountVO cashAccountVO = new PtBuAccountVO();
					cashAccountVO.setAccountType(DicConstant.ZJZHLX_04);//额度
					cashAccountVO.setAvailableAmount("0.00");
					cashAccountVO.setBalanceAmount("0.00");
					cashAccountVO.setOccupyAmount("0.00");
					cashAccountVO.setOrgId(vo.getOrgId());
					cashAccountVO.setOrgCode(vo.getCode());
					cashAccountVO.setCompanyId(vo.getCompanyId());
					cashAccountVO.setOemCompanyId(vo.getOemCompanyId());
					cashAccountVO.setCreateUser(user.getAccount());
					cashAccountVO.setCreateTime(Pub.getCurrentDate());
					cashAccountVO.setStatus(vo.getStatus());
					dao.insertBuAccount(cashAccountVO);
				}else 
				{
				*/
				PtBuAccountVO cashAccountVO1 = new PtBuAccountVO();
				cashAccountVO1.setAccountType(DicConstant.ZJZHLX_01);//现金
				cashAccountVO1.setAvailableAmount("0.00");
				cashAccountVO1.setBalanceAmount("0.00");
				cashAccountVO1.setOccupyAmount("0.00");
				cashAccountVO1.setOrgId(vo.getOrgId());
				cashAccountVO1.setOrgCode(vo.getCode());
				cashAccountVO1.setCompanyId(vo.getCompanyId());
				cashAccountVO1.setOemCompanyId(vo.getOemCompanyId());
				cashAccountVO1.setCreateUser(user.getAccount());
				cashAccountVO1.setCreateTime(Pub.getCurrentDate());
				cashAccountVO1.setStatus(vo.getStatus());
				dao.insertBuAccount(cashAccountVO1);
				PtBuAccountVO cashAccountVO2 = new PtBuAccountVO();
				cashAccountVO2.setAccountType(DicConstant.ZJZHLX_02);//承兑
				cashAccountVO2.setAvailableAmount("0.00");
				cashAccountVO2.setBalanceAmount("0.00");
				cashAccountVO2.setOccupyAmount("0.00");
				cashAccountVO2.setOrgId(vo.getOrgId());
				cashAccountVO2.setOrgCode(vo.getCode());
				cashAccountVO2.setCompanyId(vo.getCompanyId());
				cashAccountVO2.setOemCompanyId(vo.getOemCompanyId());
				cashAccountVO2.setCreateUser(user.getAccount());
				cashAccountVO2.setCreateTime(Pub.getCurrentDate());
				cashAccountVO2.setStatus(vo.getStatus());
				dao.insertBuAccount(cashAccountVO2);
				PtBuAccountVO cashAccountVO3 = new PtBuAccountVO();
				cashAccountVO3.setAccountType(DicConstant.ZJZHLX_03);//材料费
				cashAccountVO3.setAvailableAmount("0.00");
				cashAccountVO3.setBalanceAmount("0.00");
				cashAccountVO3.setOccupyAmount("0.00");
				cashAccountVO3.setOrgId(vo.getOrgId());
				cashAccountVO3.setOrgCode(vo.getCode());
				cashAccountVO3.setCompanyId(vo.getCompanyId());
				cashAccountVO3.setOemCompanyId(vo.getOemCompanyId());
				cashAccountVO3.setCreateUser(user.getAccount());
				cashAccountVO3.setCreateTime(Pub.getCurrentDate());
				cashAccountVO3.setStatus(vo.getStatus());
				dao.insertBuAccount(cashAccountVO3);
				PtBuAccountVO cashAccountVO4 = new PtBuAccountVO();
				cashAccountVO4.setAccountType(DicConstant.ZJZHLX_04);//信用额度
				cashAccountVO4.setAvailableAmount("0.00");
				cashAccountVO4.setBalanceAmount("0.00");
				cashAccountVO4.setOccupyAmount("0.00");
				cashAccountVO4.setOrgId(vo.getOrgId());
				cashAccountVO4.setOrgCode(vo.getCode());
				cashAccountVO4.setCompanyId(vo.getCompanyId());
				cashAccountVO4.setOemCompanyId(vo.getOemCompanyId());
				cashAccountVO4.setCreateUser(user.getAccount());
				cashAccountVO4.setCreateTime(Pub.getCurrentDate());
				cashAccountVO4.setStatus(vo.getStatus());
				dao.insertBuAccount(cashAccountVO4);
				PtBuAccountVO cashAccountVO5 = new PtBuAccountVO();
				cashAccountVO5.setAccountType(DicConstant.ZJZHLX_05);//返利
				cashAccountVO5.setAvailableAmount("0.00");
				cashAccountVO5.setBalanceAmount("0.00");
				cashAccountVO5.setOccupyAmount("0.00");
				cashAccountVO5.setOrgId(vo.getOrgId());
				cashAccountVO5.setOrgCode(vo.getCode());
				cashAccountVO5.setCompanyId(vo.getCompanyId());
				cashAccountVO5.setOemCompanyId(vo.getOemCompanyId());
				cashAccountVO5.setCreateUser(user.getAccount());
				cashAccountVO5.setCreateTime(Pub.getCurrentDate());
				cashAccountVO5.setStatus(vo.getStatus());
				dao.insertBuAccount(cashAccountVO5);
				//}
			}else if(vo.getOrgType().equals(DicConstant.ZZLB_11))//判断是否是服务商
			{
				//插入渠道信息表main_dealer
				MainDealerVO mVo = new MainDealerVO();
				mVo.setDealerCode(vo.getCode());//设置code
				mVo.setDealerName(vo.getOname());//设置名称
				mVo.setDealerShortname(vo.getSname());//设置简称
				mVo.setStatus(DicConstant.YXBS_01);
				mVo.setCreateUser(user.getAccount());
				mVo.setCreateTime(Pub.getCurrentDate());
				mVo.setOemCompanyId(user.getOemCompanyId());
				mVo.setCompanyId(vo.getCompanyId());
				mVo.setBelongOffice(vo.getPid()); //所属办事处
				mVo.setDealerType(DicConstant.ZZLB_11);//渠道类型
				mVo.setDealerStatus(vo.getBusStatus());//渠道状态
				mVo.setOrgId(vo.getOrgId());//所属机构,一对一关联
				dao.insertDealer(mVo);
//				//插入资金账户
//				PtBuAccountVO cashAccountVO3 = new PtBuAccountVO();
//				cashAccountVO3.setAccountType(DicConstant.ZJZHLX_03);//材料费
//				cashAccountVO3.setAvailableAmount("0.00");
//				cashAccountVO3.setBalanceAmount("0.00");
//				cashAccountVO3.setOccupyAmount("0.00");
//				cashAccountVO3.setOrgId(vo.getOrgId());
//				cashAccountVO3.setOrgCode(vo.getCode());
//				cashAccountVO3.setCompanyId(vo.getCompanyId());
//				cashAccountVO3.setOemCompanyId(vo.getOemCompanyId());
//				cashAccountVO3.setCreateUser(user.getAccount());
//				cashAccountVO3.setCreateTime(Pub.getCurrentDate());
//				cashAccountVO3.setStatus(vo.getStatus());
//				dao.insertBuAccount(cashAccountVO3);
			}else if(vo.getOrgType().equals(DicConstant.ZZLB_10))//判断是否是配件经销商
			{
				//插入渠道信息表main_dealer
				MainDealerVO mVo = new MainDealerVO();
				mVo.setDealerCode(vo.getCode());//设置code
				mVo.setDealerName(vo.getOname());//设置名称
				mVo.setDealerShortname(vo.getSname());//设置简称
				mVo.setStatus(DicConstant.YXBS_01);
				mVo.setCreateUser(user.getAccount());
				mVo.setCreateTime(Pub.getCurrentDate());
				mVo.setOemCompanyId(user.getOemCompanyId());
				mVo.setCompanyId(vo.getCompanyId());
				mVo.setBelongOffice(vo.getPid()); //所属办事处
				mVo.setDealerType(DicConstant.ZZLB_10);//渠道类型
				mVo.setDealerStatus(vo.getBusStatus());//渠道状态
				mVo.setOrgId(vo.getOrgId());//所属机构,一对一关联
				dao.insertDealer(mVo);
//				PtBuAccountVO cashAccountVO1 = new PtBuAccountVO();
//				cashAccountVO1.setAccountType(DicConstant.ZJZHLX_01);//现金
//				cashAccountVO1.setAvailableAmount("0.00");
//				cashAccountVO1.setBalanceAmount("0.00");
//				cashAccountVO1.setOccupyAmount("0.00");
//				cashAccountVO1.setOrgId(vo.getOrgId());
//				cashAccountVO1.setOrgCode(vo.getCode());
//				cashAccountVO1.setCompanyId(vo.getCompanyId());
//				cashAccountVO1.setOemCompanyId(vo.getOemCompanyId());
//				cashAccountVO1.setCreateUser(user.getAccount());
//				cashAccountVO1.setCreateTime(Pub.getCurrentDate());
//				cashAccountVO1.setStatus(vo.getStatus());
//				dao.insertBuAccount(cashAccountVO1);
//				PtBuAccountVO cashAccountVO2 = new PtBuAccountVO();
//				cashAccountVO2.setAccountType(DicConstant.ZJZHLX_02);//承兑
//				cashAccountVO2.setAvailableAmount("0.00");
//				cashAccountVO2.setBalanceAmount("0.00");
//				cashAccountVO2.setOccupyAmount("0.00");
//				cashAccountVO2.setOrgId(vo.getOrgId());
//				cashAccountVO2.setOrgCode(vo.getCode());
//				cashAccountVO2.setCompanyId(vo.getCompanyId());
//				cashAccountVO2.setOemCompanyId(vo.getOemCompanyId());
//				cashAccountVO2.setCreateUser(user.getAccount());
//				cashAccountVO2.setCreateTime(Pub.getCurrentDate());
//				cashAccountVO2.setStatus(vo.getStatus());
//				dao.insertBuAccount(cashAccountVO2);
//				PtBuAccountVO cashAccountVO3 = new PtBuAccountVO();
//				cashAccountVO3.setAccountType(DicConstant.ZJZHLX_03);//材料费
//				cashAccountVO3.setAvailableAmount("0.00");
//				cashAccountVO3.setBalanceAmount("0.00");
//				cashAccountVO3.setOccupyAmount("0.00");
//				cashAccountVO3.setOrgId(vo.getOrgId());
//				cashAccountVO3.setOrgCode(vo.getCode());
//				cashAccountVO3.setCompanyId(vo.getCompanyId());
//				cashAccountVO3.setOemCompanyId(vo.getOemCompanyId());
//				cashAccountVO3.setCreateUser(user.getAccount());
//				cashAccountVO3.setCreateTime(Pub.getCurrentDate());
//				cashAccountVO3.setStatus(vo.getStatus());
//				dao.insertBuAccount(cashAccountVO3);
//				PtBuAccountVO cashAccountVO4 = new PtBuAccountVO();
//				cashAccountVO4.setAccountType(DicConstant.ZJZHLX_04);//信用额度
//				cashAccountVO4.setAvailableAmount("0.00");
//				cashAccountVO4.setBalanceAmount("0.00");
//				cashAccountVO4.setOccupyAmount("0.00");
//				cashAccountVO4.setOrgId(vo.getOrgId());
//				cashAccountVO4.setOrgCode(vo.getCode());
//				cashAccountVO4.setCompanyId(vo.getCompanyId());
//				cashAccountVO4.setOemCompanyId(vo.getOemCompanyId());
//				cashAccountVO4.setCreateUser(user.getAccount());
//				cashAccountVO4.setCreateTime(Pub.getCurrentDate());
//				cashAccountVO4.setStatus(vo.getStatus());
//				dao.insertBuAccount(cashAccountVO4);
//				PtBuAccountVO cashAccountVO5 = new PtBuAccountVO();
//				cashAccountVO5.setAccountType(DicConstant.ZJZHLX_05);//返利
//				cashAccountVO5.setAvailableAmount("0.00");
//				cashAccountVO5.setBalanceAmount("0.00");
//				cashAccountVO5.setOccupyAmount("0.00");
//				cashAccountVO5.setOrgId(vo.getOrgId());
//				cashAccountVO5.setOrgCode(vo.getCode());
//				cashAccountVO5.setCompanyId(vo.getCompanyId());
//				cashAccountVO5.setOemCompanyId(vo.getOemCompanyId());
//				cashAccountVO5.setCreateUser(user.getAccount());
//				cashAccountVO5.setCreateTime(Pub.getCurrentDate());
//				cashAccountVO5.setStatus(vo.getStatus());
//				dao.insertBuAccount(cashAccountVO5);
			}else if(vo.getOrgType().equals(DicConstant.ZZLB_08))//判断是否是办事处
			{
				//插入渠道信息表main_dealer
				MainDealerVO mVo = new MainDealerVO();
				mVo.setDealerCode(vo.getCode());//设置code
				mVo.setDealerName(vo.getOname());//设置名称
				mVo.setDealerShortname(vo.getSname());//设置简称
				mVo.setStatus(DicConstant.YXBS_01);
				mVo.setCreateUser(user.getAccount());
				mVo.setCreateTime(Pub.getCurrentDate());
				mVo.setOemCompanyId(user.getOemCompanyId());
				mVo.setCompanyId(vo.getCompanyId());
				//mVo.setBelongOffice(vo.getPid()); //所属办事处
				mVo.setDealerType(DicConstant.ZZLB_08);//渠道类型
				mVo.setDealerStatus(vo.getBusStatus());//渠道状态
				mVo.setOrgId(vo.getOrgId());//所属机构,一对一关联
				dao.insertDealer(mVo);
			}
            atx.setOutMsg(vo.getRowXml(), "操作成功！");
            
			// 写日志
			LogManager.writeUserLog("", "",Globals.OPERATION_TYPE_INSERT, LogManager.RESULT_SUCCESS,"新增组织机构："+vo.getCode(), user);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 更新
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 9, 2014 2:14:30 PM
     */
    public void update()
        throws Exception
    { 
    	RequestWrapper request = atx.getRequest();
        User user = (User) request.getSession().get(Globals.USER_KEY);
        TmOrgVO tempVO = new TmOrgVO();
        try
        {
            HashMap<String,String> hm = RequestUtil.getValues(request);
            String orgType = hm.get("ORG_TYPE");
			tempVO.setValue(hm);
			//设置更新人、更新时间
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updateOrg(tempVO);
            String oname=hm.get("ONAME");//组织名称(全称)
            String sname=hm.get("SNAME");//组织简称
            if(DicConstant.ZZLB_09.equals(orgType)||DicConstant.ZZLB_10.equals(orgType)||DicConstant.ZZLB_11.equals(orgType)){
            	dao.updateMainDealer(String.valueOf(hm.get("ORG_ID")),String.valueOf(hm.get("PID")),oname,sname);
            }
            //供应商
            if(DicConstant.ZZLB_12.equals(orgType)){
            	dao.updateSupp(String.valueOf(hm.get("ORG_ID")),oname,sname);
            }
            //运送公司
            if(DicConstant.ZZLB_13.equals(orgType)){
            	dao.updateCys(String.valueOf(hm.get("ORG_ID")),oname,sname);
            }
            atx.setOutMsg(tempVO.getRowXml(), "操作成功！");
            LogManager.writeUserLog("", "", Globals.OPERATION_TYPE_UPDATE,
                                    LogManager.RESULT_SUCCESS,
                                    "组织机构变更：" + tempVO.getCode(), user);
            
        } 
        catch (Exception e)
        {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    /**
     * 同步集群方法
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 1:35:17 PM
     */
    public void synchronize() 
        	throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	String orgId = Pub.val(request, "orgId");
    	String action = Pub.val(request, "type");
    	int type = 0;
    	if("1".equals(action))
    		type = 1;
    	else if("2".equals(action))
    		type = 2;
    	else if("3".equals(action))
    		type = 3;
    	//广播节点
        CacheManager.broadcastChanges(CacheManager.CACHE_ORG_DEPT,orgId, type);
    }
    /**
     * 删除方法
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 9:44:58 AM
     */
    public void delete()
        throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) request.getSession().get(Globals.USER_KEY);
        String str = "";
        try
        {
        	String orgId = Pub.val(request, "orgId");
        	if("".equals(orgId))
        		throw new Exception("无主键，删除失败！");
            
            String[][] strCount = dao.checkOrgUser(orgId);
            if (strCount != null && strCount.length > 0 &&
                Integer.parseInt(strCount[0][0]) > 0)
            {
                str = "删除失败：该组织机构下有人员，不能删除！";
                throw new Exception("删除失败：该组织机构下有人员，不能删除！");
            }
            else
            {
                strCount = dao.checkChildOrg(orgId);
                if (strCount != null && strCount.length > 0 &&
                    Integer.parseInt(strCount[0][0]) > 0)
                {
                    str = "该部门下有其他部门，不能删除！";
                    throw new Exception("该部门下有其他部门，不能删除！");
                }
                else
                {
                	boolean res = dao.delete(orgId);
                    if (res)
                    { 
                        logger.info("组织机构 [" + orgId + "] 删除...");
                        str = "操作成功！";
                        atx.setOutData("", str);
                        LogManager.writeUserLog("", "",
                                                Globals.OPERATION_TYPE_DELETE,
                                                LogManager.RESULT_SUCCESS,
                                                "组织机构删除：" + orgId,
                                                user);
                    }else
                    {
                    	atx.setOutMsg("", "删除失败！");
                    }
                    
                }
            }
        }
        catch (Exception e)
        {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    /**
     * 机构管理：查询机构信息
     * @throws Exception
     */
    public void search()
    		throws Exception
	{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) request.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page, user, conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
            atx.setException(e);
		}
	}
}

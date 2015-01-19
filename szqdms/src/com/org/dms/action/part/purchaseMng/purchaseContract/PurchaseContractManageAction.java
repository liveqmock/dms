package com.org.dms.action.part.purchaseMng.purchaseContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.purchaseMng.purchaseContract.PurchaseContractManageDao;
import com.org.dms.vo.part.PtBaPchContractCheckUniqueVO;
import com.org.dms.vo.part.PtBuPchContractCheckVO;
import com.org.dms.vo.part.PtBuPchContractDtlVO;
import com.org.dms.vo.part.PtBuPchContractVO;
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
 * @author WangChong
 * 
 */
public class PurchaseContractManageAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "PurchaseContractManageAction");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PurchaseContractManageDao dao = PurchaseContractManageDao.getInstance(atx);
	    
	    /**
	     * 错误数据导出
	     * @throws Exception
	     */
	    public void expData()throws Exception{

	        try {
	            // 获取session中的user对象
	            User user = (User) atx.getSession().get(Globals.USER_KEY);
	        	//获取封装后的request对象
	        	RequestWrapper request = atx.getRequest();
	            // 定义response对象
	            ResponseWrapper responseWrapper= atx.getResponse();
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
	            hBean.setName("UNIT_PRICE");
	            hBean.setTitle("采购价格");
	            header.add(2,hBean);

	            hBean = new HeaderBean();
	            hBean.setName("DELIVER_CYCLE");
	            hBean.setTitle("供货周期");
	            header.add(3,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("MIN_PACK_UNIT");
	            hBean.setTitle("最小包装单位");
	            header.add(4,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("MIN_PACK_COUNT");
	            hBean.setTitle("最小包装数");
	            header.add(5,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("REMARKS");
	            hBean.setTitle("备注");
	            header.add(6,hBean);
	            QuerySet querySet = dao.expData(conditions,user);
	            ExportManager.exportFile(responseWrapper.getHttpResponse(), "CWSJDC", header, querySet);
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    /**
	     * 合同明细导入
	     * @throws Exception
	     */
	    public void insertImport() throws Exception {

	        try {
	        	RequestWrapper request = atx.getRequest();
		        User user = (User) atx.getSession().get(Globals.USER_KEY);
	            // 合同明细主键
	            String forcastId = Pub.val(request, "forcastMonth");
	            String tmpNo = Pub.val(request, "tmpNo");
	        	String sql = "";
	        	if (!"".equals(tmpNo)&&tmpNo!=null) {
	        		sql = " AND TMP_NO NOT IN ("+tmpNo+") ";
	        	}
	            // 合同明细更新
	            dao.updateForecastDtl(forcastId,user,sql);
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    /**
	     * 合同明细临时表查询(导入)
	     * @throws Exception
	     */
	    public void searchImport() throws Exception {

	        try {
	        	RequestWrapper request = atx.getRequest();
		        User user = (User) atx.getSession().get(Globals.USER_KEY);
	            // 定义查询分页对象
	            PageManager pageManager = new PageManager();
	            // 将request流中的信息转化为where条件
	            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
	            // BaseResultSet：结果集封装对象
	            BaseResultSet baseResultSet = dao.searchImport(pageManager, conditions,user);
	            // 输出结果集，第一个参数”bs”为固定名称，不可修改
	            atx.setOutData("bs", baseResultSet);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }

	    /**
	     * 
	     * @date()2014年7月1日 下午4:01:34
	     * @user()WangChong
	     * TO_DO:合同查询
	     * @throws Exception
	     */
	    public void contractSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			HashMap<String, String> hm = RequestUtil.getValues(request);
			String partCode = hm.get("PART_CODE");
			String partName = hm.get("PART_NAME");
			try
			{
				BaseResultSet bs = dao.contractSearch(page,user,conditions,partCode,partName);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    /**
	     * 
	     * @date()2014年7月2日 上午11:35:36
	     * @user()WangChong
	     * TO_DO:查询配件
	     * @throws Exception
	     */
	    public void searchPart() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PageManager page = new PageManager();
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        try {
	            String CONTRACT_ID = Pub.val(request, "CONTRACT_ID");
	            BaseResultSet bs = dao.searchPart(page, user, conditions,CONTRACT_ID);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月3日 下午3:43:09
	     * @user()WangChong
	     * TO_DO:合同备件查询
	     * @throws Exception
	     */
	    public void contractPartSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			RequestUtil.getConditionsWhere(request,page);
			try
			{
				String CONTRACT_ID = Pub.val(request, "CONTRACT_ID");
				BaseResultSet bs = dao.contractPartSearch(page,user,CONTRACT_ID);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    /**
	     * 
	     * @date()2014年7月2日 上午11:35:36
	     * @user()WangChong
	     * TO_DO:新增合同
	     * @throws Exception
	     */
	    public void contractInsert() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try
	        {
	        	PtBuPchContractVO vo = new PtBuPchContractVO();
				HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				String supplierCode = hm.get("SUPPLIER_CODE");
				String supplierName = hm.get("SUPPLIER_NAME");
				String CONTRACT_NO = hm.get("CONTRACT_NO");
				QuerySet checkSup = dao.checkSup(supplierCode);
				if(checkSup.getRowCount()==0){
					QuerySet checkCNo = dao.checkCNo(CONTRACT_NO);
					if(checkCNo.getRowCount()>0){
						throw new Exception("非通汇采购合同合同号不能重复，请验证");
					}
				}
				QuerySet qs  = dao.checkCode(supplierCode);
				if(qs.getRowCount()>0){
					QuerySet checkName = dao.checkName(supplierCode,supplierName);
					if(checkName.getRowCount()==0){
						throw new Exception("此供应商代码与名称不符，请验证");
					}
				}
				vo.setValue(hm);
				vo.setContractStatus(DicConstant.CGHTZT_01);
				vo.setStatus(DicConstant.YXBS_01);
				vo.setCompanyId(user.getCompanyId());
				vo.setOemCompanyId(user.getOemCompanyId());
				vo.setCreateUser(user.getAccount());
				vo.setCreateTime(Pub.getCurrentDate());//
				vo.setOrgId(user.getOrgId());
				dao.insertContract(vo);
				atx.setOutMsg(vo.getRowXml(),"合同新增成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月2日 下午4:37:00
	     * @user()WangChong
	     * TO_DO:修改合同信息
	     * @throws Exception
	     */
	    public void contractUpdate() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchContractVO tempVO = new PtBuPchContractVO();
	        try
	        {
	            HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				tempVO.setValue(hm);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());//
	            dao.updateContract(tempVO);
	            atx.setOutMsg(tempVO.getRowXml(),"合同修改成功！");
	        }
	        catch (Exception e)
	        {
	        	//设置失败异常处理
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月2日 下午4:37:00
	     * @user()WangChong
	     * TO_DO:合同备件新增
	     * @throws Exception
	     */
	    public void partInsert() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        try {
	            HashMap<String, String> hm;
	            hm = RequestUtil.getValues(request);
	            String PART_CODE = hm.get("PART_CODE");
	            String CONTRACT_ID = hm.get("CONTRACT_ID");
	            QuerySet check = dao.checkUnique(PART_CODE,CONTRACT_ID);
	            QuerySet getSup = dao.getSup(CONTRACT_ID);
	            String supplierCode=  getSup.getString(1, "SUPPLIER_CODE");
	            QuerySet checkOther = dao.checkOther(PART_CODE,supplierCode);
	            PtBaPchContractCheckUniqueVO c_co = new PtBaPchContractCheckUniqueVO();
	            if(checkOther.getRowCount()>0){
	            	throw new Exception("该配件已经存在其他此供应商对应的有效合同内，无法重复添加，请核实!");
	            }
	            if(check.getRowCount()>0){
	            	c_co.setFlag("true");
	            	atx.setOutMsg(c_co.getRowXml(), "数据已存在！");
	                return;
	            }else{
		            PtBuPchContractDtlVO p_vo = new PtBuPchContractDtlVO();
		            p_vo.setValue(hm);
		            dao.partInsert(p_vo);
		            //返回插入结果和成功信息
		            atx.setOutMsg(p_vo.getRowXml(), "新增成功！");
	            }
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月10日 下午7:37:00
	     * @user()WangChong
	     * TO_DO:合同备件修改
	     * @throws Exception
	     */
	    public void partUpdate() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        try {
	            HashMap<String, String> hm;
	            hm = RequestUtil.getValues(request);
	            PtBuPchContractDtlVO p_vo = new PtBuPchContractDtlVO();
	            p_vo.setValue(hm);
	            dao.partUpdate(p_vo);
	            //返回插入结果和成功信息
	            atx.setOutMsg(p_vo.getRowXml(), "修改成功！");
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    /**
	     * 
	     * @date()2014年7月2日 下午4:37:00
	     * @user()WangChong
	     * TO_DO:合同配件删除
	     * @throws Exception
	     */
	    public void contractPartDelete() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchContractVO tempVO = new PtBuPchContractVO();
	        try
	        {
				String DETAIL_ID = Pub.val(request, "DETAIL_ID");
	            dao.updateParts(DETAIL_ID);
				atx.setOutMsg("","配件删除成功！");
	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月2日 下午5:47:02
	     * @user()WangChong
	     * TO_DO:提交合同
	     * @throws Exception
	     */
	    public void contractReport() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchContractVO tempVO = new PtBuPchContractVO();
	        try
	        {
				 String CONTRACT_ID = Pub.val(request, "CONTRACT_ID");
				tempVO.setContractId(CONTRACT_ID);
				tempVO.setContractStatus(DicConstant.CGHTZT_02);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
	            dao.updateContract(tempVO);
				atx.setOutMsg("","合同提交成功！");
				
				/**
				 * 插入合同生命轨迹
				 */
				PtBuPchContractCheckVO c_po = new PtBuPchContractCheckVO();
				c_po.setContractId(CONTRACT_ID);
				c_po.setCheckStatus(DicConstant.CGHTZT_02);
				c_po.setCheckUser(user.getAccount());
				c_po.setCheckDate(Pub.getCurrentDate());
				c_po.setCreateUser(user.getAccount());
				c_po.setCreateTime(Pub.getCurrentDate());
				c_po.setOrgId(user.getOrgId());
				c_po.setOemCompany(user.getOemCompanyId());
				c_po.setCompanyId(user.getCompanyId());
				c_po.setStatus(DicConstant.YXBS_01);
				dao.insertContractTrack(c_po);
	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    public void contractDelete() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchContractVO tempVO = new PtBuPchContractVO();
	        try
	        {
				 String CONTRACT_ID = Pub.val(request, "CONTRACT_ID");
				tempVO.setContractId(CONTRACT_ID);
				tempVO.setStatus(DicConstant.YXBS_02);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
	            dao.updateContract(tempVO);
				atx.setOutMsg("","合同删除成功！");
				
				/**
				 * 插入合同生命轨迹
				 */
				PtBuPchContractCheckVO c_po = new PtBuPchContractCheckVO();
				c_po.setContractId(CONTRACT_ID);
				c_po.setCheckStatus(DicConstant.CGHTZT_02);
				c_po.setCheckDate(Pub.getCurrentDate());
				c_po.setCreateUser(user.getAccount());
				c_po.setCreateTime(Pub.getCurrentDate());
				c_po.setOrgId(user.getOrgId());
				c_po.setCompanyId(user.getOemCompanyId());
				c_po.setStatus(DicConstant.YXBS_01);
				dao.insertContractTrack(c_po);
	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年9月13日上午10:01:54
	     * @author Administrator
	     * @to_do:
	     * @throws Exception
	     */
	    public void deleteAllParts() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        try
	        {
				String CONTRACT_ID = Pub.val(request, "CONTRACT_ID");
	            dao.deleteAllParts(CONTRACT_ID);
				atx.setOutMsg("","配件删除成功！");
	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    public void checkSupplier() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        PageManager page = new PageManager();
	        String supplierCode = request.getParamValue("supplierCode");
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        try {
	            BaseResultSet bs = dao.checkSupplier(page, supplierCode, conditions);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }

}

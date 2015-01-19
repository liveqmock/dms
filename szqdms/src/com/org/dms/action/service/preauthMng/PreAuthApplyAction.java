package com.org.dms.action.service.preauthMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.preauth.PreAuthApplyDao;
import com.org.dms.vo.service.SeBuPreAuthorPartVO;
import com.org.dms.vo.service.SeBuPreAuthorProjectVO;
import com.org.dms.vo.service.SeBuPreAuthorVO;
import com.org.dms.vo.service.SeBuPreAuthorVO_Ext;
import com.org.framework.Globals;
import com.org.framework.alertmsg.AlertInfoVO;
import com.org.framework.alertmsg.AlertManager;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 预授权提报ACTION
 * @author zts
 *
 */
public class PreAuthApplyAction {

	//日志类
    private Logger logger = com.org.framework.log.log.getLogger("PreAuthApplyAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PreAuthApplyDao dao = PreAuthApplyDao.getInstance(atx);

    /**
     * 预授权查询
     * @throws Exception
     */
    public void preAuthSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.preAuthsearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 车辆校验查询
     * @throws Exception
     */
    public void vinCheckSearch()  throws Exception{
    	RequestWrapper request = atx.getRequest();
    	try
		{
    		String vin=request.getParamValue("diVinVal");
    		String engineNo=request.getParamValue("diEngineNoVal");
    		BaseResultSet bs = dao.vinCheckSearch(vin,engineNo);
    		bs.setFieldDic("USER_TYPE","CLYHLX");
    		bs.setFieldDic("VEHICLE_USE", "CLYT");
    		bs.setFieldDic("DRIVE_FORM", "QDXS");//驱动行驶
    		bs.setFieldDateFormat("BUY_DATE","yyyy-MM-dd");
    		bs.setFieldDateFormat("FACTORY_DATE","yyyy-MM-dd");
    		bs.setFieldDateFormat("MAINTENANCE_DATE","yyyy-MM-dd");
    		atx.setOutData("bs" , bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    /**
     * 预授权新增保存
     * @throws Exception
     */
    public void preAuthInsert() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuPreAuthorVO vo = new SeBuPreAuthorVO();
        	SeBuPreAuthorVO_Ext voExt=new SeBuPreAuthorVO_Ext();
			HashMap<String,String> hm;
			String AuthorNo=dao.getPreAuthNo();
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			voExt.setValue(hm);
			vo.setAuthorStatus(DicConstant.YSQZT_01);//预授权状态(已保存)
			vo.setStatus(DicConstant.YXBS_01);//状态(有效)
			vo.setAuthorNo(AuthorNo);
			vo.setIfApplyclaim(DicConstant.SF_02);//是否已提报索赔单（否）
			vo.setCreateUser(user.getAccount());// 设置创建人
			vo.setCreateTime(Pub.getCurrentDate());// 创建时间
			vo.setOrgId(user.getOrgId());
			vo.setCompanyId(user.getCompanyId());
			vo.setOemCompanyId(user.getOemCompanyId());
			//通过dao，执行插入
			dao.insertPreAuth(vo);
			//返回插入结果和成功信息
			voExt.setAuthorId(vo.getAuthorId());
		    voExt.setAuthorNo(AuthorNo);
		    voExt.setAuthorStatus(DicConstant.YSQZT_01);
			voExt.setFieldDateFormat("REPORT_DATE", "yyyy-MM-dd");
			voExt.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
	        voExt.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
	        voExt.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
	        voExt.bindFieldToDic("AUTHOR_STATUS","YSQZT");
	        voExt.bindFieldToDic("AUTHOR_TYPE","YSQLX");
	        voExt.bindFieldToDic("VEHICLE_USE","CLYT");
	        voExt.bindFieldToDic("USER_TYPE","CLYHLX");
			atx.setOutMsg(voExt.getRowXml(),"预授权新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 预授权修改保存
     * @throws Exception
     */
    public void preAuthUpdate() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuPreAuthorVO vo = new SeBuPreAuthorVO();
        	SeBuPreAuthorVO_Ext voExt=new SeBuPreAuthorVO_Ext();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			voExt.setValue(hm);
			vo.setAuthorStatus(DicConstant.YSQZT_01);//预授权状态(已保存)
			vo.setUpdateUser(user.getAccount());// 设置创建人
			vo.setUpdateTime(Pub.getCurrentDate());// 创建时间
			vo.setOrgId(user.getOrgId());
			vo.setCompanyId(user.getCompanyId());
			//通过dao，执行插入
			dao.updatePreAuth(vo);
			//返回插入结果和成功信息
			//voExt.setAuthorId(vo.getAuthorId());
		    voExt.setAuthorStatus(DicConstant.YSQZT_01);
			voExt.setFieldDateFormat("REPORT_DATE", "yyyy-MM-dd");
			voExt.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
	        voExt.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
	        voExt.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
	        voExt.bindFieldToDic("AUTHOR_STATUS","YSQZT");
	        voExt.bindFieldToDic("AUTHOR_TYPE","YSQLX");
	        voExt.bindFieldToDic("VEHICLE_USE","CLYT");
	        voExt.bindFieldToDic("USER_TYPE","CLYHLX");
			atx.setOutMsg(voExt.getRowXml(),"预授权修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 查询项目
     * @throws Exception
     */
    public void itemSearch() throws Exception {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		try
		{
			String authorId=request.getParamValue("authorId");
			BaseResultSet bs = dao.itemSearch(page,user,authorId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    /**
     * 查询附件
     * @throws Exception
     */
    public void fileSearch() throws Exception {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		try
		{
			String authorId=request.getParamValue("authorId");
			BaseResultSet bs = dao.fileSearch(page,user,authorId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    /**
     * 查询审核轨迹
     * @throws Exception
     */
    public void authorHisSearch() throws Exception {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		try
		{
			String authorId=request.getParamValue("authorId");
			BaseResultSet bs = dao.authorHisSearch(page,user,authorId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 预授权删除
     * @throws Exception
     */
    public void preAuthDelete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        String authorId = Pub.val(request, "authorId");
        try
        {
        	//删除项目信息
        	dao.itemDelete(authorId);
        	//删除审核轨迹
        	dao.authorHisDelete(authorId);
        	//删除预授权
            dao.preAuthDelete(authorId);
            //返回更新结果和成功信息
            atx.setOutMsg("","删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 新增项目
     * @throws Exception
     */
    public void itemInsert() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	 //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuPreAuthorProjectVO vo = new SeBuPreAuthorProjectVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String amountId=hm.get("AMOUNT_ID");
			String authorId=hm.get("AUTHOR_ID");
			QuerySet qs=dao.getAmount(amountId,authorId);
			if(qs.getRowCount()>0){
				int count=Integer.parseInt(qs.getString(1, "COU"));
				if(count>0){
					atx.setOutMsg("1","该预授权已经维护该项目！");
					return;
				}
			}
			vo.setValue(hm);
			vo.setCreateUser(user.getAccount());// 设置创建人
			vo.setCreateTime(Pub.getCurrentDate());// 创建时间
			vo.setOrgId(user.getOrgId());
			vo.setCompanyId(user.getCompanyId());
			dao.insertItem(vo);
			vo.bindFieldToDic("PROJECT_TYPE","XMLX");
			atx.setOutMsg(vo.getRowXml(),"新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 项目信息修改
     * @throws Exception
     */
    public void itemUpdate() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        SeBuPreAuthorProjectVO vo = new SeBuPreAuthorProjectVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			String amountId=hm.get("AMOUNT_ID");
			String authorId=hm.get("AUTHOR_ID");
			String projectId=hm.get("PROJECT_ID");
			QuerySet qs=dao.getAmountUpdate(amountId,authorId,projectId);
			if(qs.getRowCount()>0){
				int count=Integer.parseInt(qs.getString(1, "COU"));
				if(count>0){
					atx.setOutMsg("1","该预授权已经维护该项目！");
					return;
				}
			}
			vo.setValue(hm);
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
            dao.updateItem(vo);
            vo.bindFieldToDic("PROJECT_TYPE","XMLX");
            atx.setOutMsg(vo.getRowXml(),"修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 删除预授权项目
     * @throws Exception
     */
    public void itemDelete() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        String projectId = Pub.val(request, "projectId");
        try
        {
        	//删除预授权项目
            dao.preAuthItemDelete(projectId);
            //返回更新结果和成功信息
            atx.setOutMsg("","删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 删除预授权附件
     * @throws Exception
     */
    public void attaDelete() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        String fjid = Pub.val(request, "fjid");
        try
        {
        	//删除预授权项目
            dao.preAuthAttaDelete(fjid);
            //返回更新结果和成功信息
            atx.setOutMsg("","删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 预授权提报
     * @throws Exception
     */
    public void preAuthReport() throws Exception {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        String authorId = Pub.val(request, "authorId");//预授权ID
        String authorNo = Pub.val(request, "authorNo");//预授权单号
        String authorType ="";//预授权类型
        String flag = Pub.val(request, "flag");//标示符  如果1直接提报，如果是2先保存再提报
        SeBuPreAuthorVO vo = new SeBuPreAuthorVO();
        String orgName=user.getOrgDept().getSname();
        try
        {
        	//判断预授权是否 有项目信息
			QuerySet qs = dao.checkReport(authorId);
			//QuerySet qs1=dao.checkReportAtta(authorId);
		    //&& qs1.getRowCount() > 0
			String amount="";
			if(qs.getRowCount() > 0 )
			{
				if(flag.equals("1")){
					vo.setAuthorId(authorId);
					vo.setAuthorNo(authorNo);
					amount =Pub.val(request, "amount");
					authorType= Pub.val(request, "authorType");
				}
				if(flag.equals("2")){
					HashMap<String,String> hm;
					hm = RequestUtil.getValues(request);
					vo.setValue(hm);
					amount = hm.get("AMOUNT");
					authorType=hm.get("AUTHOR_TYPE");//预授权类型
				}
				vo.setUpdateUser(user.getAccount());
				vo.setUpdateTime(Pub.getCurrentDate());
				vo.setAuthorStatus(DicConstant.YSQZT_02);
				vo.setReportDate(Pub.getCurrentDate());
				
				
	            //多级审核
	            double je = 0;
	            if (!(amount== null || "".equals(amount))){
	            	je=Double.parseDouble(amount);
	            }
	            Object[] result= this.checkPreAuth(authorType,je);
	            vo.setRuleId(result[0].toString());
	            vo.setLevelCode(result[1].toString());
	            //预授权提报
	            dao.updatePreAuth(vo);
	            //代办提醒 start
	            QuerySet qsRole=dao.getRole();
	            if(qsRole.getRowCount() > 0){
	            	for (int i=0;i<qsRole.getRowCount() ;i++){
		            	String roleId=qsRole.getString(i+1,"ROLE_ID");
		            	AlertInfoVO infoVO=new AlertInfoVO();
		            	infoVO.setAlertTitle(orgName+"有一条预授权申请！");//标题
		            	infoVO.setDesr("预授权单号:"+vo.getAuthorNo());//提醒内容
		            	infoVO.setOverrun("0");//提醒周期
		            	infoVO.setAlertRole(roleId);//角色
		            	infoVO.setBusType(DicConstant.YWLX_02);//业务类型（配件、售后）
		            	infoVO.setBusPk(vo.getAuthorId());//业务主键
		            	infoVO.setAlertType(DicConstant.TXLX_01);//提醒类型
		            	infoVO.setCreateUser(user.getAccount());//发送人（当前登录人）
		            	infoVO.setCreateOrgid(user.getOrgId());//发送部门（当前登录部门）
			            AlertManager.alertInsert(atx.getDBFactory(),infoVO);
	            	}
	            }
	            //end
	            //返回更新结果和成功信息
	            atx.setOutMsg("","提报成功.");
			}else{
				atx.setOutMsg("1","请先维护预授权项目，再提报.");
			}
        	
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 多级审核  确定审核规则，将规则中的审核人 获取第一位
     * @param authorType
     * @param je
     * @throws Exception
     */
	public Object[] checkPreAuth(String authorType,double je)throws Exception{
    	List<String> resultList = new ArrayList<String>();
    	try{
    		QuerySet qs1 = dao.getPreAuthRule(authorType);
    		String ruleId="";
    		String levelCodes="";
            if(qs1.getRowCount() > 0 ){
            	for(int i = 0; i < qs1.getRowCount() ; i++){
            		ruleId= qs1.getString(i+1,"RULE_ID");
            		levelCodes =qs1.getString(i+1, "LEVEL_CODE"); 
            		QuerySet qsDetail=dao.getPreAuthRuleDetail(ruleId,authorType);
            		int k=0;
            		if(qsDetail.getRowCount() > 0){
            			for (int j = 0; j < qsDetail.getRowCount() ; j++){
            				String comparCharacter= qsDetail.getString(j+1, "COMPAR_CHARACTER");
            				double  value= Double.parseDouble(qsDetail.getString(j+1, "VALUE"));
            				if(DicConstant.GXFH_05.equals(comparCharacter)){//100805  > 
            					 if(je > value){
            						 k++;
            					 }
            				}else  if(DicConstant.GXFH_06.equals(comparCharacter)){//100806  >=
            					 if(je >= value ){
            						 k++;
            					 }
            				}else if(DicConstant.GXFH_07.equals(comparCharacter)){//100807  < 
            					 if(je < value ){
            						 k++;
            					 }
            				}else if(DicConstant.GXFH_08.equals(comparCharacter)){//100808 <=
            					 if(je <= value){
            						 k++;
            					 }
            				}
            			}
            		}
            		
            		QuerySet qs3=dao.getIfRule(ruleId,authorType);
            		//存在记录个数  首先赋值无限大
            		int sl= Integer.MAX_VALUE;
            		if(qs3.getRowCount() > 0 ){
        				 sl=Integer.parseInt(qs3.getString(1,"SL"));
        			}
            		//如果 记录数和判断的个数一样，证明找到了规则
            		if(sl == k ){
            			break;
            		}
            	}
            }	
            String levelCode = null;
            if(levelCodes.indexOf(",") > 0){
            	levelCode= levelCodes.split(",")[0];
            }else{
            	levelCode=levelCodes;
            }
            resultList.add(ruleId);
            resultList.add(levelCode);
    	}  
    	catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    	return resultList.toArray();
    }
    
    /**
     * 预授权配件查询
     * @throws Exception
     */
    public void authPartSearch()throws Exception{
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String condition=RequestUtil.getConditionsWhere(request,page);
		String authorId=Pub.val(request, "authorId");
		try
		{
			BaseResultSet bs = dao.authPartSearch(page,user,authorId,condition);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 配件信息查询
     * @throws Exception
     */
    public void searchPart()throws Exception{
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String condition=RequestUtil.getConditionsWhere(request,page);
		String authorId=Pub.val(request, "authorId");
		try
		{
			BaseResultSet bs = dao.searchPart(page,user,authorId,condition);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 保存配件到预售权表
     * @throws Exception
     */
    public void authPartSave()throws Exception{
    	RequestWrapper request = atx.getRequest();
   	 	User user = (User) atx.getSession().get(Globals.USER_KEY);
   	 	String authorId=Pub.val(request, "authorId");
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String partIds = hm.get("PART_ID");//配件ID
            String partCounts =hm.get("PART_COUNT");//配件数量
            String partCodes =hm.get("PART_CODE");//配件代码
            String partNames =hm.get("PART_NAME");//配件名称
            String partPrices=hm.get("PART_PRICE");//配件价格
            String[] partIdArr = partIds.split(",");
            String[] partCountArr = partCounts.split(",");
            String[] partCodeArr = partCodes.split(",");
            String[] partNameArr = partNames.split(",");
            String[] partPriceArr = partPrices.split(",");
            //循环选择的配件信息
            for (int i = 0; i < partIdArr.length; i++) {
            	SeBuPreAuthorPartVO vo =new SeBuPreAuthorPartVO();
            	vo.setAuthorId(authorId);
            	vo.setPartId(partIdArr[i]);
            	vo.setPartCode(partCodeArr[i]);
            	vo.setPartName(partNameArr[i]);
            	vo.setQuantity(partCountArr[i]);
            	vo.setUnitPrice(partPriceArr[i]);
            	vo.setCreateUser(user.getAccount());
            	vo.setCreateTime(Pub.getCurrentDate());
            	dao.inertAuthPart(vo);
            }
           // dao.inertAuthPart(partIds,authorId,user);
            atx.setOutMsg("", "保存成功.");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 删除配件信息
     * @throws Exception
     */
    public void deleteParts()throws Exception{
    	RequestWrapper request = atx.getRequest();
   	 	//User user = (User) atx.getSession().get(Globals.USER_KEY);
   	 	String relIds=Pub.val(request, "relIds");
        try {
            dao.deleteAuthPart(relIds);
            atx.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 根据预授权ID查询 
     * @throws Exception
     */
    public void preAuthDetailSearch()throws Exception{
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		String authorId=Pub.val(request, "authorId");
		try
		{
			BaseResultSet bs = dao.preAuthDetailSearch(page,user,authorId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
}

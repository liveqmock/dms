package com.org.dms.action.service.claimmng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.claimmng.ChangeCheckUserDao;
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
 * 转让索赔单审核权
 * @author zts
 *
 */
public class ChangeCheckUserAction {
	 private Logger logger = com.org.framework.log.log.getLogger("ChangeCheckUserAction");
	 private ActionContext atx = ActionContext.getContext();
	 private ChangeCheckUserDao dao = ChangeCheckUserDao.getInstance(atx);

	 
	 /**
	  * 索赔单审核人数量
	  * @throws Exception
	  */
	 public void checkUserSearch()throws Exception{
		 RequestWrapper request = atx.getRequest();
 		 PageManager page = new PageManager();
 		 User user = (User) atx.getSession().get(Globals.USER_KEY);
 		 String conditions = RequestUtil.getConditionsWhere(request,page);
 		 try
 		 {
 			 BaseResultSet bs = dao.checkUserSearch(page,conditions,user);
 			 atx.setOutData("bs", bs);
 		 }
 		 catch (Exception e)
 		 {
 			 logger.error(e);
 			 atx.setException(e);
 		 }
	 }
	 
	 /**
	  * 转让审核权
	  * @throws Exception
	  */ 
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public void changeClaimCheck()throws Exception{
		RequestWrapper request = atx.getRequest();
	   	String account=Pub.val(request, "account");//审核员
        try {
        	QuerySet qsUser=dao.getCheckUserInfo(account);
        	int max=0;
        	Map map=new HashMap();
        	List list=new ArrayList();
        	if(qsUser.getRowCount() > 0 ){
        	    max=qsUser.getRowCount()-1;//设置有效审核人数
        		for(int i=0;i<qsUser.getRowCount() ;i++){
        			map.put(i, qsUser.getString(i+1,"USER_ACCOUNT"));
        			list.add(map);
        		}
        	}
        	Map map1=new HashMap();
        	int k=0;//获取审核人索引
        	QuerySet qsClaim=dao.getClaimInfo(account);
        	if (qsClaim.getRowCount() >0) {
        		//循环索赔单个数
				for(int j=0;j<qsClaim.getRowCount();j++){
					String claimId=qsClaim.getString(j+1,"CLAIM_ID");
					map1=(Map) list.get(k);
					String opertion=(String)map1.get(k);
					dao.updateClaim(claimId,opertion);
					if(k==max){
						k=0;
					}else{
						k++;
					}
				}
			}
            atx.setOutMsg("", "转让成功.");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
	}
	
}

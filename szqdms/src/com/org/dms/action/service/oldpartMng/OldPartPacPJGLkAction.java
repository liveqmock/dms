package com.org.dms.action.service.oldpartMng;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.oldpartMng.OldPartPackPJGLDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExcelErrors;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;
/**
 * 配件管理-旧件装箱
 * @author fuxiao
 *
 */
public class OldPartPacPJGLkAction {
    private Logger logger = com.org.framework.log.log.getLogger("OldPartPacPJGLkAction");
    private ActionContext atx = ActionContext.getContext();
    private OldPartPackPJGLDao dao = OldPartPackPJGLDao.getInstance(atx);
    
    
	/**
	 * 装箱页面查询
	 */
	public void searchOnBox(){
		try {
			RequestWrapper rw = atx.getRequest();
			PageManager pm = new PageManager();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(rw, pm);
			BaseResultSet bs = dao.searchPartOnBoxList(pm, conditions, user);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 异步请求保存boxNo
     */
    public void saveAsynBoxNo(){
    	RequestWrapper request = atx.getRequest();
		String applyId=Pub.val(request, "applyId");
		String boxNo = Pub.val(request, "boxNo");
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		try {
			this.dao.savaAsynnBoxNo(applyId, boxNo, user);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
    }
    
    
    /**
     * 批量下载
     * @throws Exception
     */
    public void download()throws Exception{
    	RequestWrapper rw = atx.getRequest();
		PageManager pm = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(rw, pm);
		ResponseWrapper response= atx.getResponse();
		// 预留分页时使用
    	// PageManager page = new PageManager();
        try
        {
        	// 声明整型变量，用来做添加Title的位置下标，并在主功能中屏蔽对政策支持费用，其他费用修改的列
        	int index = 0;
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
        	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("APPLY_ID");
    		hBean.setTitle("申请单ID");
    		header.add(index++,hBean);
    		
	    	hBean = new HeaderBean();
    		hBean.setName("APPLY_NO");
    		hBean.setTitle("申请单号");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("配件名称");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("BOX_NO");
    		hBean.setTitle("箱号");
    		header.add(index++,hBean);
    		
    		QuerySet qs = dao.downloadExcelData(conditions, user);
    		ExportManager.exportFile(response.getHttpResponse(), "箱号调整", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    
    /**
     * 导入验证成功查询
     * @throws Exception
     */
    public void searchImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.searchImportList(page, user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 保存导入的临时表中数据
     * @throws Exception
     */
    public void saveImportData() throws Exception{
    	try {
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			this.dao.updateImportSuccessData(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
 			atx.setException(e);
		}
    } 
}

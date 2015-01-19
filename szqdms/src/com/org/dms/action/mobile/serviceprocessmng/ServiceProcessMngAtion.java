package com.org.dms.action.mobile.serviceprocessmng;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.org.dms.dao.mobile.serviceprocessmng.ServiceProcessMngDao;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.params.ParaManager;
import com.org.framework.params.UserPara.UserParaConfigureVO;
import com.org.framework.util.Pub;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class ServiceProcessMngAtion {
	 private Logger logger = com.org.framework.log.log.getLogger("ServiceProcessMngAtion");
	    private ActionContext atx = ActionContext.getContext();
	    private ServiceProcessMngDao dao = new ServiceProcessMngDao();
    
	    
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	  public void searchVersionServiceProcess() throws Exception
	  {
		  RequestWrapper request = atx.getRequest();
		  ResponseWrapper response = atx.getResponse();
		  String repUserId=Pub.val(request, "repUserId");//手机号码	
		  
		  try
		  {
			  //QuerySet qs=dao.searchServiceProcess(repUserId);
			  List list = new ArrayList();
			  Map<String,String> whgdmap = new HashMap<String,String>();
			  UserParaConfigureVO userVo= (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("201601");
			  String version = "";
			  if(userVo != null){
				  version=userVo.getParavalue1();//手机版本号
			  }else{
				  version = "1.4";
			  }
			  whgdmap.put("Version", version);//需修改
			//if(qs.getColumnCount()>0)
			//{
			
			//}
			//atx.setOutData("bs", bs);
			  list.add(whgdmap);
			  outPrintInfo(request,response,list);
			  atx.setOutObject("noresponse", "true");
		  }
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
	  	 }    
   
	 /**
     * @title: searchServiceProcess
     * @description: TODO(为手机端查询工单信息)
     * @throws Exception    设定文件
     * @return void    返回类型
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void searchServiceProcess() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
	    ResponseWrapper response = atx.getResponse();
	    String repUserId=Pub.val(request, "repUserId");//手机号码
	    DBFactory factory = new DBFactory();
		try
		{
			QuerySet qs=dao.searchServiceProcess(factory,repUserId);
			List list = new ArrayList();

			Map<String,String> whgdmap = new HashMap<String,String>();
			if(qs.getColumnCount()>0)
			{
				for(int i=0;i<qs.getRowCount();i++)
				{
					whgdmap = new HashMap<String,String>();
					String workId=qs.getString(i+1, "WORK_ID");
					whgdmap.put("WORK_ID", qs.getString(i+1, "WORK_ID"));
					whgdmap.put("WORK_NO", qs.getString(i+1, "WORK_NO"));
					whgdmap.put("REPAIR_USER", qs.getString(i+1, "REPAIR_USER"));
					whgdmap.put("REPAIR_DATE", qs.getString(i+1, "REPAIR_DATE"));
					whgdmap.put("GO_DATE", qs.getString(i+1, "GO_DATE"));
					whgdmap.put("START_LONGITUDE", qs.getString(i+1, "START_LONGITUDE"));
					whgdmap.put("START_LATITUDE", qs.getString(i+1, "START_LATITUDE"));
					whgdmap.put("ARRIVE_DATE", qs.getString(i+1, "ARRIVE_DATE"));
					whgdmap.put("COMPLETE_DATE", qs.getString(i+1, "COMPLETE_DATE"));
					whgdmap.put("REP_USER_TEL", qs.getString(i+1, "REP_USER_TEL"));
					whgdmap.put("APPLY_USER", qs.getString(i+1, "APPLY_USER"));
					whgdmap.put("APPLY_MOBIL", qs.getString(i+1, "APPLY_MOBIL"));
					whgdmap.put("FJSL", qs.getString(i+1, "FJSL"));
					dao.updateWorkOrder(factory,workId,repUserId);
					list.add(whgdmap);
				}
			}
			//atx.setOutData("bs", bs);
			factory.getConnection().commit();
			outPrintInfo(request,response,list);
			atx.setOutObject("noresponse", "true");
		}
		catch (Exception e)
		{
			factory.getConnection().rollback();
			logger.error(e);
			atx.setException(e);
		}finally
		{
			if(factory != null)
				factory.getConnection().close();
			factory.setFactory(null);
			factory = null;
		}
	}
    /**
     * @title: updateServiceProcess
     * @description: TODO(更新出发、到达、完成时间)
     * @throws Exception    设定文件
     * @return void    返回类型
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateServiceProcess() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
  	    ResponseWrapper response = atx.getResponse();
  	    String workId=Pub.val(request, "workId");//工单ID
  	    String type=Pub.val(request, "type");//操作类型 1出发，2到达，3完成
  	    String longitude=Pub.val(request, "longitude");//经度
  	    String latitude=Pub.val(request, "latitude");//维度
  	    String ltime=Pub.val(request, "ltime");//时间
  	    System.out.println("");
  	    System.out.println("==================");
  	    System.out.println("经度=="+longitude);
  	    System.out.println("纬度=="+latitude);
  	    System.out.println("时间=="+ltime);

  	    System.out.println("==================");
  	    System.out.println("");
  		List list = new ArrayList();
  		Map<String,String> map = new HashMap<String,String>();
  		DBFactory factory = new DBFactory();
  		try
  		{
  			String date =dao.updateServiceProcess(factory,workId,longitude,latitude,ltime, type);
  			//System.out.println(map2);
  			map.put("status", "success");
  			map.put("date", date);
  			QuerySet qs=dao.searchServiceProcessByID(factory,workId);
  			if(qs.getColumnCount()>0)
  			{
				map.put("WORK_ID", qs.getString(1, "WORK_ID"));
  				map.put("WORK_NO", qs.getString(1, "WORK_NO"));
  				map.put("REPAIR_USER", qs.getString(1, "REPAIR_USER"));
  				map.put("REPAIR_DATE", qs.getString(1, "REPAIR_DATE"));
  				map.put("GO_DATE", qs.getString(1, "GO_DATE"));
  				map.put("START_LONGITUDE", qs.getString(1, "START_LONGITUDE"));
  				map.put("START_LATITUDE", qs.getString(1, "START_LATITUDE"));
  				map.put("ARRIVE_DATE", qs.getString(1, "ARRIVE_DATE"));
  				map.put("COMPLETE_DATE", qs.getString(1, "COMPLETE_DATE"));
  				map.put("REP_USER_TEL", qs.getString(1, "REP_USER_TEL"));
  				map.put("APPLY_USER", qs.getString(1, "APPLY_USER"));
  				map.put("APPLY_MOBIL", qs.getString(1, "APPLY_MOBIL"));
  				map.put("FJSL", qs.getString(1, "FJSL"));
  				
  				//获得车牌号 
  				QuerySet qs1=dao.searchLicensePlateByID(factory,workId);
  				String licensePlate="";
  				if(qs1.getRowCount() > 0){
  					licensePlate=qs1.getString(1, "LICENSE_PLATE");
  				}
  				//出发和到达 
  				//if("1".equals(type) || "2".equals(type)){
  				dao.updateWorkGps(factory,map,licensePlate,type,longitude,latitude,ltime);
  				//}
			}
  			factory.getConnection().commit();
  		}
  		catch (Exception e)
  		{
  			factory.getConnection().rollback();
  			logger.error(e);
  			atx.setException(e);
  			map.put("status", "error");
  			map.put("date", "");
  		}finally
		{
			if(factory != null)
				factory.getConnection().close();
			factory.setFactory(null);
			factory = null;
		}
  		
  		list.add(map);
  		outPrintInfo(request,response,list);
  		atx.setOutObject("noresponse", "true");
    }
    /**
     * @title: 跨域调用通用返回
     * @description: TODO
     * @throws Exception    
     * @return void    
     */
	@SuppressWarnings("rawtypes")
	public void outPrintInfo(RequestWrapper request, ResponseWrapper response,List list){
		String callback=request.getParamValue("callback");
		JSONArray jsonarray = JSONArray.fromObject(list);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			String retStr = jsonarray.toString();
			//retStr = retStr.substring(1, retStr.length()-1);
			if(callback != null){
				out.write(callback+"("+retStr+")");
			}else{
				out.write(retStr);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.FsFilestoreVO;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaPictureVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PtBaPictureDao extends BaseDAO {
	// 定义instance
	public static final PtBaPictureDao getInstance(ActionContext atx) {
		PtBaPictureDao dao = new PtBaPictureDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}
	//插入信息
	public boolean insertPtBaPicture(PtBaPictureVO vo) throws Exception {
		return factory.insert(vo);
	}
	//修改信息
	public boolean updatePtBaPicture(PtBaPictureVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	//删除图片信息
	public boolean deletePic(String fjid) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE FROM FS_FILESTORE \n");
    	sql.append(" WHERE FJID = " + fjid + " \n");
    	return factory.update(sql.toString(), null);
    }
	//删除信息
	public boolean updatePtBaPictureStatus(String PART_ID, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_PICTURE \n");
    	sql.append(" SET STATUS = " + status + " \n");
    	sql.append(" WHERE PART_ID = " + PART_ID + " \n");
    	return factory.update(sql.toString(), null);
    }
	
	//判断配件代码是否存在
	public QuerySet checkPart_code(String part_code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM PT_BA_PICTURE \n");
    	sql.append(" WHERE PART_CODE = '" + part_code +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	//查询
//	public BaseResultSet search(PageManager page,String conditions) throws Exception
	public BaseResultSet search(PageManager page,String partCode,String partName,String if_null) throws Exception
    {
    	//String wheres = conditions;  
    	//wheres += "ORDER BY CREATE_TIME DESC";
    	
    	
        //page.setFilter(wheres);
		page.setPageRows(200);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();    	
    	sql.append("SELECT\n" );
    	sql.append("T.IF_PICTURE,\n" );
    	sql.append("T.PART_STATUS,\n" );
    	sql.append("T.PICTURE_ID,\n" );
    	sql.append("T.PART_ID,\n" );
    	sql.append("T.PART_NAME,\n" );
    	sql.append("T.PART_CODE,\n" );
    	sql.append("T.PICTURE_URL,\n" );
    	sql.append("T.COMPANY_ID,\n" );
    	sql.append("T.ORG_ID,\n" );
    	sql.append("T.CREATE_USER,\n" );
    	sql.append("T.CREATE_TIME,\n" );
    	sql.append("T.UPDATE_USER,\n" );
    	sql.append("T.UPDATE_TIME,\n" );
    	sql.append("T.STATUS,\n" );
    	sql.append("       WM_CONCAT(S.FJID) FJID,\n" );
    	sql.append("       WM_CONCAT(S.FID) FID,\n" );
    	sql.append("       WM_CONCAT(S.FJMC) FJMC,\n" );
    	sql.append("       WM_CONCAT(S.WJJBS) WJJBS,\n" );
    	sql.append("       WM_CONCAT(S.BLWJM) BLWJM\n" );
    	sql.append("FROM PT_BA_PICTURE T,FS_FILESTORE S");
    	sql.append(" WHERE T.PART_ID = S.YWZJ(+)\n" );
    	
    	if(partCode !="" && partCode!=null){
    		sql.append(" AND T.PART_CODE LIKE '%"+partCode+"%'\n" );
    	}
    	if(partName!="" && partName!=null){
    		sql.append(" AND T.PART_NAME LIKE '%"+partName+"%'\n" );
    	}
    	
    	
    	//判断是否有照片
    	if(if_null.equals(DicConstant.SF_01)){
    		sql.append(" AND S.FJMC IS NOT NULL\n" );
    	}
    	if(if_null.equals(DicConstant.SF_02)){
    		sql.append(" AND S.FJMC IS NULL\n" );
    	}
    	
    	sql.append(" GROUP BY T.PART_ID,\n" );
    	sql.append("T.IF_PICTURE,\n" );
    	sql.append("T.PART_STATUS,\n" );
    	sql.append("T.PICTURE_ID,\n" );
    	sql.append("T.PART_ID,\n" );
    	sql.append("T.PART_NAME,\n" );
    	sql.append("T.PART_CODE,\n" );
    	sql.append("T.PICTURE_URL,\n" );
    	sql.append("T.COMPANY_ID,\n" );
    	sql.append("T.ORG_ID,\n" );
    	sql.append("T.CREATE_USER,\n" );
    	sql.append("T.CREATE_TIME,\n" );
    	sql.append("T.UPDATE_USER,\n" );
    	sql.append("T.UPDATE_TIME,\n" );
    	sql.append("T.STATUS\n" );
    	sql.append(" ORDER BY T.PART_ID\n" );


 
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，对于配件名称字典（PART_NAME），将字典代码翻译为字典名称	  
    	
    	
    	
//        bs.setFieldOrgDeptSimpleName("ORG_ID")//绑定组织机构
        bs.setFieldOrgCompanySimpleName("COMPANY_ID");
        bs.setFieldOrgDeptSimpleName("ORG_ID");
             
        bs.setFieldDic("STATUS", "YXBS");   //有效标识
        bs.setFieldDic("PART_STATUS", "PJZT");   //配件状态
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
         
    	return bs;
    }
	
	//导出配件图片信息
    public QuerySet download(String conditions) throws Exception {
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,\n" );
    	sql.append("A.PART_ID,\n" );
    	sql.append("A.PART_CODE,\n" );
    	sql.append("A.PART_NAME,\n" );
    	sql.append("B.FJMC\n" );
    	sql.append("FROM PT_BA_PICTURE A, FS_FILESTORE B\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append("AND  A.PART_ID = B.YWZJ");

        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }
    //查询配件图片零时表信息
    public QuerySet searchTmp(User user, String errorInfoRowNum)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("ROW_NUM,\n" );
		sql.append("TMP_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("URL,\n" );
		sql.append("PICTURE_NAME,\n" );
		sql.append("USER_ACCOUNT\n" );
		sql.append("FROM  PT_BA_PICTURE_TMP");

		sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		if (!errorInfoRowNum.equals("")) {
			sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
		}
		return factory.select(null, sql.toString());
	}
    //检验重复项
    public QuerySet searchTmpRepeatData(User user,String partCode, String pictureName)throws Exception{
		
    	StringBuffer sql= new StringBuffer();
    	if (!partCode.equals("")) {
	    	sql.append("SELECT A.ROW_NUM, A.PART_CODE, A.PICTURE_NAME\n" );
	    	sql.append("  FROM PT_BA_PICTURE_TMP A,\n" );
	    	sql.append("       (" );
    	}
    	sql.append("SELECT PART_CODE, PICTURE_NAME, COUNT(PART_CODE)\n" );
    	sql.append("          FROM PT_BA_PICTURE_TMP\n" );
    	sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("         GROUP BY PART_CODE, PICTURE_NAME\n" );
    	sql.append("        HAVING COUNT(PART_CODE) > 1" );
    	if (!partCode.equals("")) {
	    	sql.append(") B\n" );
	    	sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
	    	sql.append("   AND A.PICTURE_NAME = B.PICTURE_NAME\n" );
	    	sql.append("   AND A.PART_CODE = '"+partCode+"'\n" );
	    	sql.append("   AND A.PICTURE_NAME = '"+pictureName+"'\n" );
	    	sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	    	sql.append("   ORDER BY A.ROW_NUM\n" );
    	}
		return factory.select(null, sql.toString());
	}
  //将零时表正确的数据显示到页面
	public BaseResultSet searchPtBaInfoTmpImport(PageManager page, String conditions, User user)throws Exception{
		
		String wheres = conditions;
    	wheres += " AND USER_ACCOUNT = '"+user.getAccount()+"'";
    	wheres += " order by TMP_ID desc ";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
		
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("ROW_NUM,\n" );
		sql.append("TMP_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("URL,\n" );
		sql.append("PICTURE_NAME,\n" );
		sql.append("USER_ACCOUNT\n" );
		sql.append("FROM  PT_BA_PICTURE_TMP");
		
		bs=factory.select(sql.toString(), page);
		return bs;
	}
	public QuerySet getTableId(String tableId, String tableCode, String table, String code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT "+tableId+"\n");
    	sql.append(" FROM  "+table+"\n");
    	sql.append(" WHERE "+tableCode+" = '" + code +"'\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public boolean insertImportFileFromTmp(FsFilestoreVO vo) throws Exception
	{
		return factory.insert(vo);
	}
	public boolean insertImportPictureFromTmp(PtBaPictureVO vo) throws Exception
	{
		return factory.insert(vo);
	}
	 //配件图片错误信息导出
    public QuerySet expTmpErrorData(String pConditions,User user) throws Exception {

    	String wheres = " WHERE ROW_NUM IN ("+ pConditions + ") \n";
    	wheres += " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
    	wheres += " ORDER BY ROW_NUM \n";
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ROW_NUM,\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("PICTURE_NAME\n" );
    	sql.append("FROM PT_BA_PICTURE_TMP \n" );
		
    	
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }
}

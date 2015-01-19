package com.org.frameImpl.dao.sysmng.usermng;

import com.org.dms.common.DicConstant;
import com.org.frameImpl.Constant;
import com.org.frameImpl.vo.TmUserVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.framework.util.Pub;
import com.org.mvc.context.ActionContext;
/**
 * @Description:用户管理类
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @author andy.ten@tom.com 
 */
public class UserMngDao extends BaseDAO
{
    //定义instance
    public  static final UserMngDao getInstance(ActionContext atx)
    {
        UserMngDao dao = new UserMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断账号是否存在
     * @param account
     * @return
     * @throws Exception
     */
	public QuerySet checkAccount(String account) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM TM_USER \n");
    	sql.append(" WHERE ACCOUNT = '" + account +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public boolean insertUser(TmUserVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateUser(TmUserVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * @title: search
	 * @description: TODO(执行查询方法)
	 * @param page:分页对象
	 * @return
	 * @throws Exception    设定文件
	 * @return BaseResultSet    返回类型
	 * @auther andy.ten@tom.com
	 * @date 2014年6月25日 上午11:51:08
	 */
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	//增加按当前公司、当前组织过滤条件
    	boolean flag;
    	String account = user.getAccount();
		if(account.indexOf("ADMIN")==0||account.indexOf("SUPERMAN")==0)
			flag = false;
		else
			flag = true;
    	if(flag){
    		wheres +=" AND O.ORG_TYPE IN("+DicConstant.ZZLB_09+","+DicConstant.ZZLB_10+","+DicConstant.ZZLB_11+","+DicConstant.ZZLB_12+","+DicConstant.ZZLB_13+")\n";
    	}
    	wheres += " AND O.ORG_ID = U.ORG_ID AND O.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n";
    	wheres += " ORDER BY U.USER_ID DESC ";
    	//设置where过滤条件,将会带where关键字，所以在定义下面sql语句时，where的过滤条件应在此处增加，不能在定义sql语句时定义where条件
    	//如：wheres += " AND STATUS=1 ";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT U.USER_ID, U.ACCOUNT, U.PERSON_NAME, U.SEX, U.ORG_ID, U.ORG_CODE,O.SNAME ORG_NAME, U.PERSON_KIND,U.LEVEL_NAME, U.USER_SN, U.SECRET_LEVEL, U.IDCARD, U.MAIlFROM, U.DES, U.CONTACT_WAY, U.BIRTHDATE, U.PERSON_TYPE, U.STATUS,O.COMPANY_ID,O.OEM_COMPANY_ID,U.USER_AUTH,U.AUTH_MAC ");
    	sql.append(" FROM TM_USER U, TM_ORG O \n");
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，对于性别字典（sex），将字典代码翻译为字典名称
		bs.setFieldDic("SEX", "XB");
		//设置日期字段显示格式
		bs.setFieldDateFormat("BIRTHDATE", "yyyy-MM-dd");
		//绑定组织字典，将ORG_ID翻译为组织名称
		bs.setFieldOrgDeptSimpleName("ORG_ID");
		//绑定公司字典，将COMPANY_ID翻译为公司名称
		bs.setFieldOrgCompanySimpleName("COMPANY_ID");
		bs.setFieldOrgCompanySimpleName("OEM_COMPANY_ID");
		//bs.setFieldOrgDeptSimpleName("ORG_ID");
		bs.setFieldDic("STATUS",Constant.YXBS);
		bs.setFieldDic("PERSON_KIND",Constant.YHLX);
		bs.setFieldDic("SECRET_LEVEL",Constant.SJMJ);
		bs.setFieldDic("PERSON_TYPE",DicConstant.JGJB);
		bs.setFieldDic("USER_AUTH",DicConstant.YHQX);
    	return bs;
    }
    
    /**
	 * @title: search
	 * @description: TODO(执行查询方法)
	 * @param page:分页对象
	 * @return
	 * @throws Exception    设定文件
	 * @return BaseResultSet    返回类型
	 * @auther andy.ten@tom.com
	 * @date 2014年6月25日 上午11:51:08
	 */
    public BaseResultSet searchUser(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	//增加按当前公司、当前组织过滤条件
//    	boolean flag;
//    	String account = user.getAccount();
//		if(account.indexOf("ADMIN")==0||account.indexOf("SUPERMAN")==0)
//			flag = false;
//		else
//			flag = true;
//    	if(flag){
//    		wheres +=" AND O.ORG_TYPE IN("+DicConstant.ZZLB_09+","+DicConstant.ZZLB_10+","+DicConstant.ZZLB_11+","+DicConstant.ZZLB_12+","+DicConstant.ZZLB_13+")\n";
//    	}
    	wheres += " AND O.ORG_ID = U.ORG_ID AND O.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n";
    	wheres += " ORDER BY U.USER_ID DESC ";
    	//设置where过滤条件,将会带where关键字，所以在定义下面sql语句时，where的过滤条件应在此处增加，不能在定义sql语句时定义where条件
    	//如：wheres += " AND STATUS=1 ";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT U.USER_ID, U.ACCOUNT, U.PERSON_NAME, U.SEX, U.ORG_ID, U.ORG_CODE, U.PERSON_KIND,U.LEVEL_NAME, U.USER_SN, U.SECRET_LEVEL, U.IDCARD, U.MAIlFROM, U.DES, U.CONTACT_WAY, U.BIRTHDATE, U.PERSON_TYPE, U.STATUS,O.COMPANY_ID,O.OEM_COMPANY_ID,U.USER_AUTH,U.AUTH_MAC ");
    	sql.append(" FROM TM_USER U, TM_ORG O \n");
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，对于性别字典（sex），将字典代码翻译为字典名称
		bs.setFieldDic("SEX", "XB");
		//设置日期字段显示格式
		bs.setFieldDateFormat("BIRTHDATE", "yyyy-MM-dd");
		//绑定组织字典，将ORG_ID翻译为组织名称
		bs.setFieldOrgDeptSimpleName("ORG_ID");
		//绑定公司字典，将COMPANY_ID翻译为公司名称
		bs.setFieldOrgCompanySimpleName("COMPANY_ID");
		bs.setFieldOrgCompanySimpleName("OEM_COMPANY_ID");
		bs.setFieldOrgDeptSimpleName("ORG_ID");
		bs.setFieldDic("STATUS",Constant.YXBS);
		bs.setFieldDic("PERSON_KIND",Constant.YHLX);
		bs.setFieldDic("SECRET_LEVEL",Constant.SJMJ);
		bs.setFieldDic("PERSON_TYPE",DicConstant.JGJB);
		bs.setFieldDic("USER_AUTH",DicConstant.YHQX);
    	return bs;
    }
    /**
     * @title: selectUnGrantedRole 
     * @description: TODO(查询未授予角色的用户) 
     * @param page
     * @return
     * @throws Exception    设定文件 
     * @return BaseResultSet    返回类型 
     * @auther andy.ten@tom.com
     * @date 2014年6月25日 下午1:18:36
     */
    public BaseResultSet selectUnGrantedRole(PageManager page, String userId) throws Exception
    {
    	String wheres = "";
    	wheres = " ROLE_ID NOT IN (SELECT ROLE_ID FROM TR_ROLE_USER_MAP WHERE USER_ID="+userId+") AND STATUS='"+Constant.YXBS_01+"'";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT ROLE_ID, CODE, RNAME, ORG_ID \n");
    	sql.append(" FROM TM_ROLE \n");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldOrgDeptSimpleName("ORG_ID");
    	return bs;
    }
    /**
     * @title: selectUnGrantedRole
     * @description: TODO(查询未授予角色的用户，按用户过滤，对于用户名不是ADMIN开头的只查渠道角色) 
     * @param page
     * @return
     * @throws Exception    设定文件 
     * @return BaseResultSet    返回类型 
     * @auther gouwentan
     * @date 2014年11月21日 下午10:29:25
     */
    public BaseResultSet selectUnGrantedRoleNew(PageManager page, String userId, User user) throws Exception
    {
    	BaseResultSet bs = null;
		boolean flag;
		String account = user.getAccount();
		if(account.indexOf("ADMIN")==0||account.indexOf("SUPERMAN")==0)
			flag = false;
		else
			flag = true;
		String wheres = "";
    	wheres = " ROLE_ID NOT IN (SELECT ROLE_ID FROM TR_ROLE_USER_MAP WHERE USER_ID="+userId+") AND STATUS='"+Constant.YXBS_01+"'";
    	if(flag)
    		wheres+=" AND LEVEL_CODE  IN("+DicConstant.JGJB_05+","+DicConstant.JGJB_06+")";
        page.setFilter(wheres);
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT ROLE_ID, CODE, RNAME, ORG_ID \n");
    	sql.append(" FROM TM_ROLE \n");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldOrgDeptSimpleName("ORG_ID");
    	return bs;
    }

    /**
     * @title: selectGrantedRole
     * @description: TODO(查询已分配的角色)
     * @param page
     * @param userId
     * @return
     * @throws Exception    设定文件
     * @return BaseResultSet    返回类型
     * @auther andy.ten@tom.com
     * @date 2014年6月26日 下午4:55:41
     */
    public BaseResultSet selectGrantedRole(PageManager page, String userId) throws Exception
    {
    	String wheres;
    	wheres = " ROLE_ID IN (SELECT ROLE_ID FROM TR_ROLE_USER_MAP WHERE USER_ID="+userId+") AND STATUS='"+Constant.YXBS_01+"'";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT ROLE_ID, CODE, RNAME, ORG_ID \n");
    	sql.append(" FROM TM_ROLE \n");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldOrgDeptSimpleName("ORG_ID");
    	return bs;
    }
    
    /**
     * 删除用户
     * @param account
     * @param status
     * @return
     * @throws Exception
     */
    public boolean updateUserStatus(String account, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE TM_USER \n");
    	sql.append(" SET STATUS = " + status + " \n");
    	sql.append(" WHERE USER_ID = " + account + " \n");
    	return factory.update(sql.toString(), null);
    }
    
    /**
     * 删除用户角色对应关系
     * @param userId
     * @return
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 2, 2014 5:12:53 PM
     */
    public boolean deleteUserRolesByAccount(String userId) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE TR_ROLE_USER_MAP \n");
    	sql.append(" WHERE USER_ID = '" + userId + "' \n");
    	return factory.delete(sql.toString(), null);
    }
    
    /**
     * @title: deleteUserRolesByUserId 
     * @description: TODO(根据userId删除用户) 
     * @param userId
     * @return
     * @throws Exception    设定文件 
     * @return boolean    返回类型 
     * @auther andy.ten@tom.com
     * @date 2014年6月26日 下午5:01:39
     */
    public boolean deleteUserRolesByUserId(String userId) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE TR_ROLE_USER_MAP \n");
    	sql.append(" WHERE USER_ID = " + userId + " \n");
    	return factory.delete(sql.toString(), null);
    }
    
    /**
     * @title: saveRoleUser 
     * @description: TODO(保存角色信息，一个用户可能会对应多个角色) 
     * @param userId：用户id
     * @param account：用户账号
     * @param listId：角色id，可多个
     * @param listCode：角色code，可多个
     * @throws Exception    设定文件 
     * @return void    返回类型 
     * @auther andy.ten@tom.com
     * @date 2014年6月27日 上午10:34:04
     */
    public void saveRoleUser(String userId, String account, String[] listId, String[] listCode) 
    		throws Exception
    {
    	String deleteSql = "DELETE TR_ROLE_USER_MAP WHERE ACCOUNT = '" + account + "'";
    	factory.delete(deleteSql, null);
		String insertSql = "INSERT INTO TR_ROLE_USER_MAP(ROLE_ID,USER_ID,RCODE,ACCOUNT) values(?,?,?,?)";
		Object[] objs = new Object[4];
		for (int i = 0; i < listId.length; i++)
		{
			if (Pub.empty(listId[i]))
				continue;
			objs[0] = listId[i];
			objs[1] = userId;
			objs[2] = listCode[i];
			objs[3] = account;
			factory.update(insertSql, objs);
		}
    }

    /**
     * 重置密码
     * @param userId
     * @param newPass
     * @return
     * @throws Exception
     */
    public boolean resetPass(String userId ,String newPass) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE TM_USER \n");
    	sql.append(" SET PASSWORD = '" + newPass + "', \n");
    	sql.append(" UPDATEPWD_TIME= SYSDATE \n");
    	sql.append(" WHERE USER_ID = " + userId + " \n");
    	return factory.update(sql.toString(), null);
    }

    public String[][] searchPassword(String userId ) throws Exception
    {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT T.PASSWORD \n");
        sql.append(" FROM TM_USER T\n");
        sql.append(" WHERE USER_ID = " + userId + " \n");
        return factory.select(sql.toString());
    }

    public boolean updatePwd(String userId ,String newpass) throws Exception
    {
        StringBuffer sql = new StringBuffer();
        sql.append(" UPDATE TM_USER \n");
        sql.append(" SET PASSWORD='" +newpass+ "',\n");
        sql.append(" UPDATEPWD_TIME= SYSDATE \n");
        sql.append(" WHERE USER_ID = " + userId + " \n");
        return factory.update(sql.toString(),null);
    }
}
package com.org.frameImpl.dao.sysmng.rolemng;

import com.org.frameImpl.Constant;
import com.org.frameImpl.vo.TmRoleVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;
/**
 * @Description:角色管理类
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @author andy.ten@tom.com 
 */
public class RoleMngDao extends BaseDAO
{
    //定义instance
    public  static final RoleMngDao getInstance(ActionContext atx)
    {
        RoleMngDao dao = new RoleMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    /**
     * 校验角色代码是否已存在
     * @param account
     * @return
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 12:01:59 PM
     */
    public QuerySet checkRoleCode(String code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM TM_ROLE \n");
    	sql.append(" WHERE CODE = '" + code +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    
	public boolean insertRole(TmRoleVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateRole(TmRoleVO vo) throws Exception
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
        page.setFilter(wheres);
    	BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT CODE, RNAME, LEVEL_CODE, ROLE_ID, STATUS, ROLE_TYPE, ROLE_REMARK,ORG_ID \n");
    	sql.append(" FROM TM_ROLE \n");
    	bs = factory.select(sql.toString(), page);
		//绑定组织字典，将ORG_ID翻译为组织名称
		bs.setFieldOrgDeptSimpleName("ORG_ID");
		bs.setFieldDic("STATUS",Constant.YXBS);
		bs.setFieldDic("LEVEL_CODE",Constant.JGJB);
    	return bs;
    }
    
    /**
     * 删除角色和菜单对应关系
     * @param roleId
     * @param rootname
     * @return
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 2:40:08 PM
     */
    public boolean deleteRoleMenuMap(String roleId, String rootname) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE TR_ROLE_MENU_MAP \n");
    	sql.append(" WHERE ROLE_ID = " + roleId + " AND ROOTNAME='"+rootname+"'\n");
    	return factory.delete(sql.toString(), null);
    }
    /**
     * 插入角色和菜单对应关系
     * @param roleId
     * @param menuName
     * @param rootname
     * @return
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 3:02:03 PM
     */
    public boolean insertRoleMenuMap(String roleId, String menuName, String rootname) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" INSERT INTO TR_ROLE_MENU_MAP(ROLE_ID,MENU_NAME,ROOTNAME) \n");
    	sql.append(" VALUES(?,?,?) \n");
    	Object[] objs = new Object[3];
    	objs[0] = roleId;
    	objs[1] = menuName;
    	objs[2] = rootname;
    	return factory.delete(sql.toString(), objs);
    }
    /**
     * 删除角色
     * @param roleId
     * @return
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 3:02:35 PM
     */
    public boolean deleteRole(String roleId) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE TM_ROLE \n");
    	sql.append(" SET STATUS="+Constant.YXBS_02+"\n");
    	sql.append(" WHERE ROLE_ID = " + roleId + "\n");
    	return factory.update(sql.toString(), null);
    }
    /**
     * 删除角色和菜单关系表
     * @param roleId
     * @return
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 3:19:51 PM
     */
    public boolean deleteRoleMenuMapByRid(String roleId) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE TR_ROLE_MENU_MAP \n");
    	sql.append(" WHERE ROLE_ID = " + roleId + " \n");
    	return factory.delete(sql.toString(), null);
    }
    
    public boolean deleteRoleUserMapByRid(String roleId) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE TR_ROLE_USER_MAP \n");
    	sql.append(" WHERE ROLE_ID = " + roleId + " \n");
    	return factory.delete(sql.toString(), null);
    }
    
    public BaseResultSet selectUnGrantedPerson(PageManager page, User user, String conditions,String roleId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND p.STATUS="+Constant.YXBS_01+" AND p.USER_ID NOT IN (SELECT m.USER_ID FROM TR_ROLE_USER_MAP m WHERE m.ROLE_ID='"+roleId+"') ";
		
        page.setFilter(conditions);
        page.setFilter(wheres);
    	BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT p.USER_ID,p.ACCOUNT,p.PERSON_NAME,p.SEX,p.ORG_ID,p.PERSON_KIND \n");
        sql.append(" FROM TM_USER p\n");
    	bs = factory.select(sql.toString(), page);
		
		bs.setFieldDic("SEX", "XB");
		bs.setFieldOrgDeptSimpleName("ORG_ID");
		bs.setFieldDic("PERSON_KIND","YHLX");
    	return bs;
    }
    
    public BaseResultSet selectGrantedPerson(PageManager page, User user, String conditions,String roleId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND p.STATUS="+Constant.YXBS_01+" AND p.USER_ID IN (SELECT m.USER_ID FROM TR_ROLE_USER_MAP m WHERE m.ROLE_ID='"+roleId+"') ";
		
        page.setFilter(conditions);
        page.setFilter(wheres);
    	BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT p.USER_ID,p.ACCOUNT,p.PERSON_NAME,p.SEX,p.ORG_ID,p.PERSON_KIND \n");
        sql.append(" FROM TM_USER p\n");
    	bs = factory.select(sql.toString(), page);
		
		bs.setFieldDic("SEX", "XB");
		bs.setFieldOrgDeptSimpleName("ORG_ID");
		bs.setFieldDic("PERSON_KIND","YHLX");
    	return bs;
    }
    
    public boolean saveRoleUserMap(String roleId, String userId, String roleCode, String account) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" INSERT INTO TR_ROLE_USER_MAP(ROLE_ID,USER_ID,RCODE,ACCOUNT) \n");
    	sql.append(" VALUES(?,?,?,?) \n");
    	Object[] objs = new Object[4];
    	objs[0] = roleId;
    	objs[1] = userId;
    	objs[2] = roleCode;
    	objs[3] = account;
    	return factory.update(sql.toString(), objs);
    }
    
    public boolean removeRoleUserMap(String roleId, String userIds) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE FROM TR_ROLE_USER_MAP \n");
    	sql.append(" WHERE ROLE_ID = "+roleId+" AND USER_ID IN("+userIds+") \n");
    	
    	return factory.update(sql.toString(), null);
    }
}
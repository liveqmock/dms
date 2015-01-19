package com.org.dms.action.part.financeMng.cashAccountMng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.cashAccountMng.CashAccountMngDao;
import com.org.dms.vo.part.PtBuAccountLogVO;
import com.org.dms.vo.part.PtBuAccountVO_Ext;
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

public class CashAccountMngAction {

    private Logger logger = com.org.framework.log.log.getLogger("Logger");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    // 定义response对象
    ResponseWrapper responseWrapper= atx.getResponse();
    //定义dao对象
    private CashAccountMngDao dao = CashAccountMngDao.getInstance(atx);

    /**
     * 错误数据导出
     * @throws Exception
     */
    public void expData()throws Exception{

        try {
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
        	User user = (User) atx.getSession().get(Globals.USER_KEY);
            // 将request流中的信息转化为where条件
            String conditions = Pub.val(request, "seqs");
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("ORG_CODE");
            hBean.setTitle("渠道商代码");
            header.add(0,hBean);

            hBean = new HeaderBean();
            hBean.setName("AMOUNT");
            hBean.setTitle("金额");
            header.add(1,hBean);

            QuerySet querySet = dao.expData(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "CWSJDC", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 配件账户金额导入(材料费，返利)
     * @throws Exception
     */
    public void insertImport() throws Exception {

        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            // 账户类型(材料费,返利)
            String amountType = Pub.val(request, "amountType");
        	String tmpNo = Pub.val(request, "tmpNo");
        	String sql = "";
        	if (!"".equals(tmpNo)&&tmpNo!=null) {
        		sql = " AND A.TMP_NO NOT IN ("+tmpNo+") ";
        	}
            // 配件账户更新
            dao.updateAccount(amountType,user,sql);
            // 插入资金异动
            dao.insertAccountLog(amountType,user,sql);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 配件账户导入查询
     * @throws Exception
     */
    public void searchImport() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request,page);
        try {
            BaseResultSet bs = dao.searchImport(page,conditions,user);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 配件账户导入验证
     * 
     * @return
     * @throws Exception
     */
    public List<ExcelErrors> checkData(String accountType)throws Exception{

        User user = (User) atx.getSession().get(Globals.USER_KEY);
        ExcelErrors errors = new ExcelErrors();
        List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();

        // 查询导入的所有数据
        QuerySet querySet1 = dao.searchAmount(user);
        if (querySet1.getRowCount() > 0) {

            // 渠道商账户是否存在验证
            QuerySet querySet = dao.accountIdCheck(user,accountType);
            if (querySet.getRowCount() > 0) {
                for (int i =0;i<querySet.getRowCount();i++) {
                    String orgCode = querySet1.getString(i+1, "ORG_CODE");
                    // 渠道商账户不存在
                    errors.setRowNum(i+1);
                    errors.setErrorDesc("渠道商"+orgCode+"：不存在.");
                    errorList.add(errors);
                }
            }

            if (querySet1.getRowCount() > 0) {
                for (int j=0;j<querySet1.getRowCount();j++) {
                    String amount = querySet1.getString(1, "AMOUNT");
                    String orgCode = querySet1.getString(1, "ORG_CODE");
                    java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后一位的数字的正则表达式
                    java.util.regex.Matcher match=pattern.matcher(amount); 
                    if(match.matches()==false) {
                        // 金额不正确
                        errors.setRowNum(j+1);
                        errors.setErrorDesc("渠道商"+orgCode+"：金额格式错误.");
                        errorList.add(errors);
                    }
                }
            }
        }
        // 渠道商是否有账户(材料，返利)
        // 金额是否正确
        if(errorList!=null && errorList.size()>0){
            return errorList;
        } else {
            return null;
        }
    }

    public void cashSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request,page);
        try {
            BaseResultSet bs = dao.cashSearch(page,user,conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 
     * @throws Exception
     */
    public void searchDtl() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            String orgCode = Pub.val(request, "orgCode");
            QuerySet querySet = dao.searchDtl(orgCode,user);
            
            PtBuAccountVO_Ext vo = new PtBuAccountVO_Ext();
            if (querySet.getRowCount() > 0) {
                vo.setAcceptAvailableAmount(querySet.getString(1, "ACCEPT_AVAILABLE_AMOUNT"));
                vo.setCashAvailbleAmount(querySet.getString(1, "CASH_AVAILABLE_AMOUNT"));
                vo.setCreditAvailableAmount(querySet.getString(1, "CREDIT_AVAILABLE_AMOUNT"));
                vo.setMaterialAvailableAmount(querySet.getString(1, "MATERIAL_AVAILABLE_AMOUNT"));
                vo.setRebateAvailableAmount(querySet.getString(1, "REBATE_AVAILABLE_AMOUNT"));
                
                vo.setAcceptAccountId(querySet.getString(1, "ACCEPT_ACCOUNT_ID"));
                vo.setCashAccountId(querySet.getString(1, "CASH_ACCOUNT_ID"));
                vo.setCreditAccountId(querySet.getString(1, "CREDIT_ACCOUNT_ID"));
                vo.setMaterialAccountId(querySet.getString(1, "MATERIAL_ACCOUNT_ID"));
                vo.setRebateAccountId(querySet.getString(1, "REBATE_ACCOUNT_ID"));
            }
            
            // 返回更新结果和成功信息
            atx.setOutMsg(vo.getRowXml(), "");
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 
     * @date()2014年8月5日下午9:55:52
     * @author Administrator
     * @to_do:现金账户扣款
     * @throws Exception
     */
    public void cashUpdate() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String,String> hm;
            hm = RequestUtil.getValues(request);
            String LOG_TYPE = hm.get("LOG_TYPE");
            String ACCOUNT_ID = hm.get("ACCOUNT_ID");
            String amountType = hm.get("AMOUNT_TYPE");
            String IN_ACCOUNT_ID = hm.get("IN_ACCOUNT_ID");
            String AMOUNT = hm.get("AMOUNT");
            String IN_AMOUNT = hm.get("IN_AMOUNT");
            String REMARKS = hm.get("REMARKS");
            String IN_DATE = hm.get("IN_DATE");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(IN_DATE);
            
            // 判断操作类型
            if (DicConstant.ZJYDLX_04.equals(LOG_TYPE)||DicConstant.ZJYDLX_05.equals(LOG_TYPE)) {
                // ------ (扣款，罚款)

                //更改渠道账户余额
                dao.subAccount(ACCOUNT_ID,AMOUNT);
                
                //插入资金异动
                PtBuAccountLogVO vo = new PtBuAccountLogVO();
                vo.setAccountId(ACCOUNT_ID);
                vo.setAmount(AMOUNT);
                vo.setLogType(LOG_TYPE);
                if(REMARKS!=null&&!"".equals(REMARKS)){
                    vo.setRemarks(REMARKS);
                }
                vo.setSourceaccountType(amountType);
                //vo.setInDate(Pub.getCurrentDate());
                vo.setCreateUser(user.getAccount());
                vo.setCreateTime(Pub.getCurrentDate());//
                vo.setOrgId(user.getOrgId());
                vo.setInDate(date);
                dao.insertLog(vo);
            } else if (DicConstant.ZJYDLX_01.equals(LOG_TYPE)||DicConstant.ZJYDLX_06.equals(LOG_TYPE)||DicConstant.ZJYDLX_07.equals(LOG_TYPE)) {
                // ------ (打款，奖励，返利)
                //更改渠道账户余额
                dao.addAccount(ACCOUNT_ID,AMOUNT);
                //插入资金异动
                PtBuAccountLogVO vo = new PtBuAccountLogVO();
                vo.setAccountId(ACCOUNT_ID);
                vo.setAmount(AMOUNT);
                vo.setLogType(LOG_TYPE);
                if(REMARKS!=null&&!"".equals(REMARKS)){
                    vo.setRemarks(REMARKS);
                }
                vo.setSourceaccountType(amountType);
                //vo.setInDate(Pub.getCurrentDate());
                vo.setCreateUser(user.getAccount());
                vo.setCreateTime(Pub.getCurrentDate());//
                vo.setOrgId(user.getOrgId());
                vo.setInDate(date);
                dao.insertLog(vo);
            } else if (DicConstant.ZJYDLX_08.equals(LOG_TYPE)) {
                // ------ (调账)
                if ("".equals(IN_AMOUNT)) {
                    String orgCode = Pub.val(request, "orgCode");
                    QuerySet querySet = dao.searchDtl(orgCode,user);
                    
                    if (querySet.getRowCount() > 0) {
                        IN_AMOUNT = querySet.getString(1, "CASH_AVAILABLE_AMOUNT")+','+
                        querySet.getString(1, "ACCEPT_AVAILABLE_AMOUNT")+','+
                        querySet.getString(1, "MATERIAL_AVAILABLE_AMOUNT")+','+
                        querySet.getString(1, "REBATE_AVAILABLE_AMOUNT")+','+
                        querySet.getString(1, "CREDIT_AVAILABLE_AMOUNT");
                        
                        IN_ACCOUNT_ID = querySet.getString(1, "CASH_ACCOUNT_ID")+','+
                        querySet.getString(1, "ACCEPT_ACCOUNT_ID")+','+
                        querySet.getString(1, "MATERIAL_ACCOUNT_ID")+','+
                        querySet.getString(1, "REBATE_ACCOUNT_ID")+','+
                        querySet.getString(1, "CREDIT_ACCOUNT_ID");
                    }
                }
                // 调出账户(现金，承兑，材料费，返利，信用额度)
                String[] accountStr = ACCOUNT_ID.split(",");
                // 调出金额(现金，承兑，材料费，返利，信用额度)
                String[] amountStr = AMOUNT.split(",");
                // 调入账户(现金，承兑，材料费，返利，信用额度)
                String[] in_accountStr = IN_ACCOUNT_ID.split(",");
                // 调入金额(现金，承兑，材料费，返利，信用额度)
                String[] in_amountStr = IN_AMOUNT.split(",");
                for (int i = 0;i<accountStr.length;i++) {
                    //更改渠道账户余额(调出)
                    dao.subAccount(accountStr[i],amountStr[i]);
                    //插入资金异动(调出)
                    PtBuAccountLogVO vo = new PtBuAccountLogVO();
                    vo.setAccountId(accountStr[i]);
                    vo.setAmount(amountStr[i]);
                    
                    // 修改调出账户类型 by fuxiao 20141224
                    // vo.setLogType(LOG_TYPE);
                    vo.setLogType(DicConstant.ZJYDLX_09);
                    
                    if(REMARKS!=null&&!"".equals(REMARKS)){
                        vo.setRemarks(REMARKS);
                    }
                    vo.setCreateUser(user.getAccount());
                    //vo.setInDate(Pub.getCurrentDate());
                    vo.setCreateTime(Pub.getCurrentDate());
                    vo.setOrgId(user.getOrgId());
                    vo.setInDate(date);
                    dao.insertLog(vo);

                    // 不更新信用额度
                    if (i!=accountStr.length-1) {
                        //更改渠道账户余额(调入)
                        dao.addAccount(in_accountStr[i],in_amountStr[i]);
                        //插入资金异动(调入)
                        PtBuAccountLogVO in_vo = new PtBuAccountLogVO();
                        in_vo.setAccountId(in_accountStr[i]);
                        in_vo.setAmount(in_amountStr[i]);
                        
	                     // 修改调入账户类型 by fuxiao 20141224
//                         vo.setLogType(LOG_TYPE);
                        in_vo.setLogType(DicConstant.ZJYDLX_10);
	                    
                        if(REMARKS!=null&&!"".equals(REMARKS)){
                            in_vo.setRemarks(REMARKS);
                        }
                        //vo.setInDate(Pub.getCurrentDate());
                        in_vo.setCreateUser(user.getAccount());
                        in_vo.setCreateTime(Pub.getCurrentDate());//
                        in_vo.setOrgId(user.getOrgId());
                        in_vo.setInDate(date);
                        dao.insertLog(in_vo);
                    }
                }
            }
            
            atx.setOutMsg("","");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 
     * @date()2014年8月21日下午2:25:08
     * @author Administrator
     * @to_do:资金占用明细查询
     * @throws Exception
     */
    public void occupationDetail() throws Exception {

        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request,page);
        try {
            String ACCOUNT_ID = Pub.val(request, "ACCOUNT_ID");
            BaseResultSet bs = dao.occupationDetail(page,user,conditions,ACCOUNT_ID);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
}

package com.org.dms.action.part.financeMng.remitMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.remitMng.RemitCheckInMngDao;
import com.org.dms.vo.part.PtBuMoneyRemitVO;
import com.org.frameImpl.vo.TmOrgVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class RemitCheckInMngAction {
    private Logger logger = com.org.framework.log.log.getLogger(
            "Logger");
        //上下文对象
        private ActionContext atx = ActionContext.getContext();
        //定义dao对象
        private RemitCheckInMngDao dao = RemitCheckInMngDao.getInstance(atx);
        

        /**
         * 获取是否为直营店
         *
         * @throws Exception
         */
        public void getIsDs() throws Exception {

            try {
                User user = (User) atx.getSession().get(Globals.USER_KEY);
                QuerySet querySet = dao.getIsDs(user);
                String isDs = "";
                if (querySet.getRowCount() > 0) {
                    isDs = querySet.getString(1, "IS_DS");
                }
                TmOrgVO tmOrgVO = new TmOrgVO();
                tmOrgVO.setIsDs(isDs);
                atx.setOutMsg(tmOrgVO.getRowXml(), "");
            } catch (Exception e) {
                logger.error(e);
                atx.setException(e);
            }
        }

        /**
         * 
         * @date()2014年7月31日下午5:28:42
         * @author Administrator
         * @to_do:打款查询
         * @throws Exception
         */
        public void remitSearch() throws Exception
        {
            RequestWrapper request = atx.getRequest();
            User user = (User) atx.getSession().get(Globals.USER_KEY);
            PageManager page = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(request,page);
            try
            {
                BaseResultSet bs = dao.remitSearch(page,user,conditions);
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
         * @date()2014年7月31日下午5:28:57
         * @author Administrator
         * @to_do:打款新增
         * @throws Exception
         */
        public void remitInsert() throws Exception
        {
            RequestWrapper request = atx.getRequest();
            User user = (User) atx.getSession().get(Globals.USER_KEY);
            try
            {
                PtBuMoneyRemitVO vo = new PtBuMoneyRemitVO();
                HashMap<String,String> hm;
                hm = RequestUtil.getValues(request);
                vo.setValue(hm);
                vo.setFiliingDate(Pub.getCurrentDate());
                vo.setRemitStatus(DicConstant.DKZT_01);
                vo.setStatus(DicConstant.YXBS_01);
                vo.setCompanyId(user.getCompanyId());
                vo.setOemCompanyId(user.getOemCompanyId());
                vo.setCreateUser(user.getAccount());
                vo.setCreateTime(Pub.getCurrentDate());//
                vo.setOrgId(user.getOrgId());
                dao.insertRemit(vo);
                atx.setOutMsg(vo.getRowXml(),"打款新增成功！");
            }
            catch (Exception e)
            {
                atx.setException(e);
                logger.error(e);
            }
        }
        
        public void remitUpdate() throws Exception
        {
            RequestWrapper request = atx.getRequest();
            User user = (User) atx.getSession().get(Globals.USER_KEY);
            try
            {
                PtBuMoneyRemitVO vo = new PtBuMoneyRemitVO();
                HashMap<String,String> hm;
                hm = RequestUtil.getValues(request);
                vo.setValue(hm);
                vo.setUpdateUser(user.getAccount());
                vo.setUpdateTime(Pub.getCurrentDate());
                dao.updateRemit(vo);
                atx.setOutMsg(vo.getRowXml(),"打款修改成功！");
            }
            catch (Exception e)
            {
                atx.setException(e);
                logger.error(e);
            }
        }
        /**
         * 
         * @date()2014年7月31日下午5:36:55
         * @author Administrator
         * @to_do:提交打款信息
         * @throws Exception
         */
        public void remitReport() throws Exception
        {
            RequestWrapper request = atx.getRequest();
            User user = (User) atx.getSession().get(Globals.USER_KEY);
            PtBuMoneyRemitVO tempVO = new PtBuMoneyRemitVO();
            try
            {
                String REMIT_ID = Pub.val(request, "REMIT_ID");
                tempVO.setRemitId(REMIT_ID);
                tempVO.setRemitStatus(DicConstant.DKZT_02);
                tempVO.setUpdateUser(user.getAccount());
                tempVO.setUpdateTime(Pub.getCurrentDate());
                dao.updateRemit(tempVO);
                atx.setOutMsg("","打款提交成功！");
            }
            catch (Exception e)
            {
                atx.setException(e);
                logger.error(e);
            }
        }
        /**
         * 
         * @date()2014年7月31日下午5:36:24
         * @author Administrator
         * @to_do:删除打款信息
         * @throws Exception
         */
        public void remitDelete() throws Exception
        {
            RequestWrapper request = atx.getRequest();
            try
            {
                 String REMIT_ID = Pub.val(request, "REMIT_ID");
                dao.deletedRemit(REMIT_ID);
                atx.setOutMsg("","打款删除成功！");
                
            }
            catch (Exception e)
            {
                atx.setException(e);
                logger.error(e);
            }
        }

}

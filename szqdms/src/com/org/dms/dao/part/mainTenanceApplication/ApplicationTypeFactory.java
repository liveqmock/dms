package com.org.dms.dao.part.mainTenanceApplication;

import com.org.dms.common.DicConstant;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: ApplicationTypeFactory 
 * Function: 申请单工厂类：生成对应的申请单处理类
 * date: 2014年10月12日 上午11:57:57
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class ApplicationTypeFactory {
	
	/**
	 * 
	 * createApplicationType:生成对应的申请单实体类
	 * @author fuxiao
	 * Date:2014年10月12日下午12:02:20
	 * @param applicationType
	 * @param ac
	 * @return
	 */
	public static final ApplicationType createApplicationType(String applicationType , ActionContext ac){
		ApplicationType type = null;
		if(DicConstant.PJWHSQLX_01.equals(applicationType)){
			type = PartInfoType.getInstance(ac);
		}else if(DicConstant.PJWHSQLX_02.equals(applicationType)){
			type = CABAssemblyType.getInstance(ac);
		}else if(DicConstant.PJWHSQLX_03.equals(applicationType)){
			type = CABInfoType.getInstance(ac);
		}else if(DicConstant.PJWHSQLX_04.equals(applicationType)){
			type = PartInfoChangeType.getInstance(ac);
		}else if(DicConstant.PJWHSQLX_05.equals(applicationType)){
			type = SupplierChangeType.getInstance(ac);
		}
		return type;
	}
}

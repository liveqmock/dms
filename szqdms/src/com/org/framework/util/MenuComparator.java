package com.org.framework.util;
import java.util.*;
import com.org.framework.common.*;

/**
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @mail   andy.ten@tom.com
 */
public class MenuComparator
    implements Comparator<Object>{

     public int compare(Object o1,Object o2) {
    	 if(o1 == null) return 0;
    	 if(o2 == null) return 0;
    	 int res = 0;
    	 try
		 {
    		 Menu m1=(Menu)o1;
        	 Menu m2=(Menu)o2;
        	 if(Integer.parseInt(m1.getOrderNo())< Integer.parseInt(m2.getOrderNo()))
        		 res = 0;
        	 else
        		 res = 1;
		 }
		 catch (Exception e)
		 {
			 res = 0;
		 }
    	 return res;
     }

}


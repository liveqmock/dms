//添加金额格式化方法
//add by gouwentan 2014-07-15
function amountFormatNew(val){
	if(!val) return "";
	if(val==0)
	{
	   return "0.00";
	}
	if(val&&val!=0)
	{
		return zeroFormat(numberFormat('#,###.00',val));
	}
}
//去除金额格式化方法
//add by gouwentan 2014-07-15
function removeAmountFormat(val){
	if(val){
		return val.replace(/,/g,'');
	}
}
//================================================================================
function zeroFormat(val){
	if('0.00'==val){
		return '0';
	}else{
		return val;
	}
}
function numberFormat(pattern , val){
	var oldVal = val;
	if(val < 0){
		val = -val;
	} 
	var _nfPattern = "[0#]+[,]?[0#]*(\\.[0#]+)?";
	if ( !pattern || !(new RegExp(_nfPattern,'g').test(pattern)) ){
		alert('numberFormat() invalide pattern parameter : "'+pattern+'"');
		return ;
	}
	if ( !isNumber((new String(val)).replace(".",""))){
		alert('numberFormat() invalide number parameter : "'+val+'"');
		return ;
	}
	var strAry = pattern.match(new RegExp(_nfPattern,'g'));
	p1 = strAry[0].indexOf('.');
	if ( p1>0 ){
		len = strAry[0].length-p1-1;
		val = Math.round (val*Math.pow(10,len))/Math.pow(10,len);
	}else{
		val = Math.round(val);
	}	
	val = String(val);
	p2=val.indexOf('.');
	var ret='';
	if (p1>0){
		for(var i=p1+1;i<strAry[0].length;i++){
			ch = (p2>0)&&(p2+i-p1<val.length)?val.charAt(p2+i-p1):'';
			if ( strAry[0].charAt(i)=='0' ){
				ret = ret+(ch==''?'0':ch);
			}else{
				ret = ret+(ch==''?'':ch);				
			}
		}
		ret = ret==''?'':('.'+ret);
	}
	p3=strAry[0].indexOf(',');
	if( p3>0 ){
		len = p1>0?p1-p3-1:strAry[0].length-p3-1;
		len2 = p2>0?p2-1:val.length-1;
		for(var i=len2;i>=0;i--){
			ret = ((len2-i+1)%len==0?',':'')+val.charAt(i)+ret;
		}
		ret = ret.charAt(0)==','?ret.substring(1,ret.length):ret;
	}else{
		ret = val.substring(0,p2>0?p2:val.length)+ret;
	}
	if(oldVal < 0){
		ret = "-"+ret;
	}
	return pattern.replace(strAry[0],ret);
}
//================================================================================
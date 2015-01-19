/**
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2011-10
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
*/

window.onresize = resize_wm;
window.onerror = function(){};
var webAppsMsg = "/szqdms";
var divTop,divLeft,divWidth,divHeight,docHeight,docWidth,objTimer,mytm,mitm,i = 0;
var msgCounts = 0;
var alertMsgSearchUrl = webAppsMsg + "/AlertInfoAction/search.ajax";
var box,can;
var minFlag = false;
function doLinkMsg(link)
{
    //alert(1);
    var options = {width:600,height:340,max:false,mask:false,mixable:true,minable:true,resizable:true,drawable:true}; 
    var sYwzj = link.getAttribute("ref_sjbh");
    var sType = link.getAttribute("ref_type");
    var linkUrl = link.getAttribute("ref_link");
    var _op = $.extend(options,{ywzj:sYwzj,type:sType});
    var url = linkUrl || webAppsMsg +"/jsp/jQFrameWork/dialog/alertCommon.jsp?xh="+link.getAttribute("ref_xh");
    $.pdialog.open(url, "dia-alert-"+link.getAttribute("ref_xh"), link.getAttribute("ref_desc"), _op);
}
function doFinishMsg(link)
{
	var setDiaAlertFinish = webAppsMsg+"/AlertInfoAction/setAlertFinish.ajax?xh="+link.getAttribute("ref_xh");
	try
	{
	    $.ajax({
	        type: 'POST',
	        url:setDiaAlertFinish,
	        dataType:"xml",
	        data:{},
	        cache: false,
	        success: function(responseText){
						//删除当前行
						var tr = link;
						while(tr.tagName != "TR")
							tr = tr.parentElement;
						$(tr).remove();
	        		}
		});
	}catch(e){alert(e);}
}
function getMsg()
{
	try
	{
	    $.ajax({
            type: 'POST',
            url:alertMsgSearchUrl,
            dataType:"xml",
            data:{},
            cache: false,
            success: function(responseText){
                        var res = responseText.documentElement;
                        var rows = res.getElementsByTagName("ROW");
                        if(rows && rows.length > 0)
                        {
                            var s = "",ss = "";
                            var desrNode,typeNode,xhNode,sjbhNode,linkNode;
                            for(var i=0;i<rows.length;i++)
                            {
                                desrNode = rows[i].getElementsByTagName("ALERT_TITLE").item(0);
                                typeNode = rows[i].getElementsByTagName("ALERT_TYPE").item(0);
                                xhNode = rows[i].getElementsByTagName("XH").item(0);
                                sjbhNode = rows[i].getElementsByTagName("BUS_PK").item(0);
                                linkNode = rows[i].getElementsByTagName("LINKURL").item(0);
                                if(i%2 == 0)
                                    s += "<tr>";
                                else
                                    s += "<tr style='background-color:#F8F8F8'>";
                                s += "<td>" + (i+1) + "</td>";
                                s += "<td>" + (typeNode.getAttribute("sv")) + "</td>";
                                var t = desrNode.text?desrNode.text:desrNode.textContent;
                                var tt;
                                if(t && t.length > 13)
                                    tt = t.substr(0,13)+"...";
                                else
                                    tt = t;
                                s += "<td title='"+t+"'>" + tt + "</td>";
                                var xh = xhNode.text?xhNode.text:xhNode.textContent;
                                var sjbh = sjbhNode.text?sjbhNode.text:sjbhNode.textContent;
                                var linkUrl = linkNode.text?linkNode.text:linkNode.textContent;
                                var typeCode = typeNode.text?typeNode.text:typeNode.textContent;
                                var typeValue = typeNode.getAttribute("sv");
                                s += "<td style='color:red'><a href='javascript:void(0);' ref_desc='"+t+"' ref_typevalue='"+typeValue+"' ref_type='"+typeCode+"' ref_xh='"+xh+"' ref_sjbh='" + sjbh + "' ref_url='"+linkUrl+"' onclick='doLinkMsg(this);' class='btnLook' title='查看'></a><a href='javascript:void(0)' onclick='doFinishMsg(this);' ref_xh='"+xh+"' class='btnDel' title='不再提醒'></a></td>";
                                s += "</tr>";
                                ss += "<a href='javascript:void(0)' onclick='doLinkMsg(this)' ref_desc='"+t+"' ref_typevalue='"+typeValue+"' ref_type='"+typeCode+"' ref_xh='"+xh+"' ref_sjbh='" + sjbh + "' ref_url='"+linkUrl+"' class='rollings' title='"+t+"'><font color='red'>"+(typeNode.getAttribute("sv"))+"：</font>"+tt+"</a>";
                            }
                            var tab = $("table",$("#msgcontent"));
                            $(">tbody",tab).html("");
                            $(">tbody",tab).append(s);
                            msgCounts = res.getElementsByTagName("RESULT").item(0).getAttribute("countrows");
                            $("#newmsg2").html("<div id='div1' style='padding-left:5px;'>"+ss+"</div>");
                            box=document.getElementById("div1"),can=true;
                            box.innerHTML+=box.innerHTML;
                            box.onmouseover=function(){can=false;};
                            box.onmouseout=function(){can=true;};
                            //$("#newmsg2").html("&nbsp;系统消息&nbsp;<a style='color:red;cursor:hand;text-decoration: underline;'>["+msgCounts+"]</a>&nbsp;条");
                            //$("#newmsg1").html("&nbsp;您有最新的消息&nbsp;<span style='color:red;cursor:hand;text-decoration: nounderline;'>["+msgCounts+"]</span>&nbsp;条");
                            $("#newmsg1").html("&nbsp;您有最新的消息：&nbsp;");
                            $("#ctpop").show();
                            $('#msgcontent').show();
                            divTop = parseInt(document.getElementById("ctpop").style.top,10);
                            divLeft = parseInt(document.getElementById("ctpop").style.left,10);
                            divHeight = parseInt(document.getElementById("ctpop").offsetHeight,10);
                            divWidth = parseInt(document.getElementById("ctpop").offsetWidth,10);
                            docWidth = document.body.clientWidth;
                            docHeight = document.documentElement.clientHeight;
                            document.getElementById("ctpop").style.top = parseInt(document.body.scrollTop,10) + docHeight + 10 + "px";// divHeight
                            document.getElementById("ctpop").style.left = parseInt(document.body.scrollLeft,10) + docWidth - divWidth + "px";
                            document.getElementById("ctbody").style.visibility="visible";
                            objTimer = window.setInterval("move_wm()",12);
                            autocls();
                        }
                      },
            error: DWZ.ajaxError
        });
	}
	catch(e){}
}

function resize_wm()
{
	i+=1;
	try
	{
		divHeight = parseInt(document.getElementById("ctpop").offsetHeight,10);
		divWidth = parseInt(document.getElementById("ctpop").offsetWidth,10);
		docWidth = document.body.clientWidth;
		docHeight = document.documentElement.clientHeight;
		document.getElementById("ctpop").style.top = docHeight - divHeight + parseInt(document.body.scrollTop,10);
		document.getElementById("ctpop").style.left = docWidth - divWidth + parseInt(document.body.scrollLeft,10);
	}
	catch(e){}
}
function move_wm()
{
	try
	{
		if (parseInt(document.getElementById("ctpop").style.top,10) <= (docHeight - divHeight + parseInt(document.body.scrollTop,10)))
		{
			window.clearInterval(objTimer);
			objTimer = window.setInterval("resize_wm()",1);
		}
		divTop = parseInt(document.getElementById("ctpop").style.top,10);
		document.getElementById("ctpop").style.top = divTop - 5 +"px";
	}
	catch(e){}
}

function movedown_wm()
{
	try
	{
		if (parseInt(document.getElementById("ctpop").style.top,10) >= (docHeight + parseInt(document.body.scrollTop,10)))
		{
		    document.getElementById("opwm").style.visibility="hidden";
            document.getElementById('ctbody').style.visibility='visible';
            document.getElementById('msgcontent').style.display='';
			if(objTimer) window.clearInterval(objTimer);
		}else
		{
			documentdocument.getElementById("ctpop").style.top = document.getElementById("ctpop").style.top + "px";
			//alert(docHeight);
		}	
	}
	catch(e){}
}

function close_wm()
{
	document.getElementById('ctpop').style.visibility='hidden';
	document.getElementById('ctbody').style.visibility='hidden';
	document.getElementById('ctpop2').style.visibility='hidden';
	document.getElementById('opwm').style.visibility='hidden';
	document.getElementById('msgcontent').style.display='none';
	if(objTimer) window.clearInterval(objTimer);
	
}
function rolling(){
 var stop=box.scrollTop%21==0&&!can;
 if(!stop)box.scrollTop==(box.scrollHeight/2)?box.scrollTop=0:box.scrollTop++;
 mitm = setTimeout(arguments.callee,box.scrollTop%21?30:2500);
}
function mini_wm() 
{
	minFlag = true;
    $("#ctpop").fadeOut('slow');
    //$("#ctpop").height("0");
	//document.getElementById('ctbody').style.visibility='hidden';
    $("#ctpop2").show();
    $("#ctpop2").css("top",document.getElementById("ctpop").style.top);	
    $("#ctpop2").css("left",document.getElementById("ctpop").style.left); 
	document.getElementById("opwm").style.visibility="visible";
	document.getElementById("opwm").style.top = docHeight - 23 - divTop + "px";
	//document.getElementById('msgcontent').style.display='none';
	//$("#msgcontent").slideDown();
	rolling();
}

function reop_wm()
{
    $("#ctpop").fadeIn('slow');
    $("#ctpop2").hide();
	document.getElementById("opwm").style.visibility="hidden";
	clearInterval(mitm);
	//document.getElementById('ctbody').style.visibility='visible';
	//document.getElementById('msgcontent').style.display='';
} 

function hold_wm()
{
	if(mytm) {clearTimeout(mytm);minFlag == false;}
}

function autocls()
{
	if(minFlag == false)
		mytm=setTimeout("mini_wm()",1000*8);
}
document.write("<DIV id=ctpop2 style='display:none;LEFT: 0px; VISIBILITY: ; Z-INDEX: 1000;WIDTH: 306px;POSITION: absolute; TOP: 0px; HEIGHT: 21px;'>");
document.write("<div id=opwm style='position:relative; VISIBILITY: hidden;left:0px; top:208px; height:21px; width:100%;'>");
document.write("<table style='width: 100%; height: 21px;border: 1px solid #999999;' cellspacing='0' cellpadding='0'>");
document.write("    <tr>");
document.write("        <td style='height: 21px;background-image: url("+webAppsMsg+"/images/default/message/th_bg.gif);'><table width='100%' height='21px' border='0px' cellpadding='0' cellspacing='0'>");
document.write("    <tr>");
document.write("        <td><div id='newmsg2'></div>");
document.write("        </td>");
document.write("        <td width='16px' height='21px'></td>");
document.write("        <td width='50px' height='21px' style='text-align: center;'><table width='35px' border='0px' cellspacing='0' cellpadding='0'>");
document.write("              <tr>");
document.write("                <td><img src='"+webAppsMsg+"/images/default/message/open.gif' width='15px' height='15px' onClick='reop_wm()' style='cursor:hand;' alt='最大化'/></td>");
document.write("                <td width='5px'></td>");
document.write("                <td><img src='"+webAppsMsg+"/images/default/message/close.gif' width='15px' height='15px' onClick='close_wm()' style='cursor:hand;' alt='关闭'/></td>");
document.write("              </tr>");
document.write("            </table></td>");
document.write("    </tr>");
document.write("</table>");
document.write("</td>");
document.write("</tr>");
document.write("</table>");
document.write("</div>");
document.write("</div>");
document.write("<DIV id=ctpop style='display:none;LEFT: 0px; VISIBILITY: ; Z-INDEX: 1000;WIDTH: 306px;  ;POSITION: absolute; TOP: 0px; HEIGHT: 174px;' onmouseover='hold_wm()' onmouseout='autocls()' oncontextmenu='self.event.returnValue=false'>");
document.write("<DIV id=ctbody style='LEFT: 0px; VISIBILITY: hidden; WIDTH: 306px; POSITION: relative; TOP: 0px; HEIGHT:174px;'>");
document.write("<table style='width: 100%; height: 100%;border: 1px solid #999999;' cellspacing='0' cellpadding='0'>");
document.write("	<tr>");
document.write("		<td style='height: 21px;background-image: url("+webAppsMsg+"/images/default/message/th_bg.gif);'><table width='100%' height='21px' border='0px' cellpadding='0' cellspacing='0'>");
document.write("	<tr>");
document.write("		<td>");
document.write("			<div id='newmsg1'></div></td>");
document.write("		<td width='16px' height='21px'></td>");
document.write("		<td width='50px' height='21px' style='text-align: center;'><table width='35px' border='0' cellspacing='0' cellpadding='0'>");
document.write("              <tr>");
document.write("                <td><img src='"+webAppsMsg+"/images/default/message/mini.gif' width='15px' height='15px' onClick='mini_wm()' style='cursor:hand;' alt='最小化'/></td>");
document.write("                <td width='5px'></td>");
document.write("                <td><img src='"+webAppsMsg+"/images/default/message/close.gif' width='15px' height='15px' onClick='close_wm()' style='cursor:hand;' alt='关闭'/></td>");
document.write("              </tr>");
document.write("            </table></td>");
document.write("	</tr>");
document.write("</table>");
document.write("</td>");
document.write("	</tr>");
document.write("	<tr>");
document.write("		<td style='height: 180px;background-image: url("+webAppsMsg+"/images/default/message/main_bg.gif);' valign='top'><div id='msgcontent' style='display:none' style='overflow:hidden;height:100%;margin-top:0px;padding-top:0px;'>");
document.write("<table style='width:100%;height:auto;border:solid 0px #aaaaaa;' align='center' class='dlist' style='top:0px;'>");
document.write("<thead><tr>");
document.write("<th width='18px' algin='center'></th>");
document.write("<th width='30px' algin='center'>类型</th>");
document.write("<th >标题</th>");
document.write("<th width='50px'><font color='red'>操作</font></th>");
document.write("</tr></thead>");
document.write("<tbody></tbody>");
document.write("</table>");
document.write("</div></td>");
document.write("	</tr>");
document.write("</table>");
document.write("</DIV>");
document.write("</DIV>");
getMsg();
setInterval("getMsg();",1000*60*30);
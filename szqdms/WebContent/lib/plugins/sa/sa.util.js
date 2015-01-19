/**
	 Title:报表框架工程
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2013-08
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
*/
function doTableToXML(doc,objNode){
   var cell ;
   var curNode;
   var i,j,x = 0,y;
   for( i = 0,y = 0; i < document.getElementById("freetable").rows.length; i++)
   {
       x = 0;
       for(  j = 0; j < document.all.FreeTable.rows[i].cells.length; j++)
       {
       	  cell = document.all.FreeTable.rows[i].cells[j];

       	  curNode = doc.createElement("CELL");
       	  objNode.appendChild(curNode);
     	  
       	  curNode.setAttribute("X",x);
       	  curNode.setAttribute("Y",y);
       	  curNode.setAttribute("ROWSPAN",cell.rowSpan);
       	  curNode.setAttribute("COLSPAN",cell.colSpan);
       	  curNode.text = cell.innerText;
       	  x += cell.colSpan;
       }
       y++;
   }
   return x;	
}

function doSaveFreeSataResult(id)
{
    try
	{
//    processbar.style.display="";
    var postAction = "FreeTableAction.do?method=saveTableData";
//    var doc = new ActiveXObject("MSXML2.DOMDocument");    
    var doc = new ActiveXObject("msxml2.domdocument");
//var doc = new ActiveXObject("MSXML2.DOMDocument"); 
    doc.async = false;
    doc.loadXML("<RESULT ID='"+id+"'/>");
    var root = doc.documentElement;

    var infoNode = doc.createElement("INFO");
    var dataNode = doc.createElement("DATA");
    root.appendChild(infoNode);
    root.appendChild(dataNode);

//    infoNode.setAttribute("ID",id);
    
    var cols = doTableToXML(doc,dataNode);

    root.setAttribute("COLUMNS",cols);
   
    infoNode.text = document.all.FreeTable.innerHTML;
    var res = doGlobalPost(postAction,doc.xml);
//    processbar.style.display="none";
    }catch(e){
//    	processbar.style.display="none";
    	alert(e.message);
    }
    
}

/*
var p = 1;
var r = 20;
var count = 65;
var pages = 4;
*/
function doFirstPage()
{
	var pageform = document.getElementById('pageform');
	pageform.action = location.href;
	pageform.p.value = 1;
	pageform.r.value = r;
	pageform.c.value = count;
	pageform.submit();
}

function doNextPage()
{
	var pageform = document.getElementById('pageform');
	pageform.action = location.href;
	pageform.p.value = p+1;
	pageform.r.value = r;
	pageform.c.value = count;
	pageform.submit();
}

function doPrevPage()
{
	var pageform = document.getElementById('pageform');
	pageform.action = location.href;
	pageform.p.value = p-1;
	pageform.r.value = r;
	pageform.c.value = count;
	pageform.submit();
}

function doLastPage()
{
	var pageform = document.getElementById('pageform');
	pageform.action = location.href;
	pageform.p.value = pages;
	pageform.r.value = r;
	pageform.c.value = count;
	pageform.submit();
}

function doInitFreeTable()
{
	var freeTable = document.getElementById('freetable');
	if(!freeTable) return;
	for(var i=0;i<freeTable.rows.length;i++)
		for(var j=0;j<freeTable.rows[i].cells.length;j++)
		{
			try
			{
				var cell = freeTable.rows[i].cells[j];
				var val = cell.innerText;
				if(val && val.indexOf("id=")>=0)
				{
					val = val.substr(3);
					cell.setAttribute('id',val.substr(0,val.indexOf(':')));
					cell.setAttribute('line','true');
					cell.innerText = val.substr(val.indexOf(':')+1);
					doDrawLine(cell);
				}
			}catch(e){continue;}
		}
}

function doRefreshLine()
{
	var tjb = document.getElementById('freetable');
	if(!tjb) return;
	for(var i=0;i<tjb.rows.length;i++)
	 for(var j=0;j<tjb.rows[i].cells.length;j++)
	 	if(tjb.rows[i].cells[j].line == 'true')
	 	{
	 		var c = tjb.rows[i].cells[j];
	 		var t = document.getElementById(c.id+"_HC1").innerText+"////"
				+document.getElementById(c.id+"_HC2").innerText;
			c.innerHTML = t;
	 		doDrawLine(c);
	 	}
}

function doDrawLine(obj)
{
	if(!obj || obj.tagName != 'TD') return false;
	if(!obj.id)
	{
		var d = new Date();
		obj.id = d;
	}
	var v1 = obj.innerText;
	var v2 = "";
	if(obj.innerText && obj.innerText.indexOf('////') > 0)
	{
		v1 = obj.innerText.substring(0,obj.innerText.indexOf('/'));
		v2 = obj.innerText.substring(obj.innerText.indexOf('////')+4);
	}
	obj.innerHTML = "<!--[if gte vml 1]><v:line id='_x0000_s1025' style='"
   		+"z-index:0' from='0px,0px' to='"+obj.clientWidth+"px,"+obj.clientHeight+"px' coordsize='21600,21600'"
   		+" strokecolor='windowText [64]' o:insetmode='auto'/><![endif]-->"
   		+"<span style='mso-ignore:vglayout2'>"
  		+"<table cellpadding=0 cellspacing=0 width='100%'>"
   		+"<tr>"
    	+"<td type='HC' id='"+obj.id+"_HC1' height='"+(obj.clientHeight)+"' width='"+(obj.clientWidth/2)+"' align='left' valign='bottom'>"+v1+"</td>"
    	+"<td type='HC' id='"+obj.id+"_HC2' height='"+(obj.clientHeight)+"' width='"+(obj.clientWidth/2)+"' align='right' valign='top'>"+v2+"</td>"
   		+"</tr>"
  		+"</table>"
  		+"</span>";
  	return true;
}




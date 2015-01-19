var DIC_BLANK = 0;
var DIC_CODE = 1;
var DIC_TEXT = 2;
var DIC_STEXT = 3;
var DIC_SPELL = 4;
var DIC_TABLE = 5;
var DIC_ENUM = 6;
var DIC_ALLSPELL = 8;
var DIC_MIXSPELL = 16;
var DIC_SNSEL = 32;
var DIC_DIRSEL = 64;
var winColor = "#7BA2E7";
var winTitleColor = "navy";
var winCaretColor = "#FFCC33";
var dicButtonColor = "black";
var IsOpen = 0;
function new_xDic(id, left, top, width, height) {
	var h = this.pageSize * 16 + 74;
	this.id = id ? id: "SciSoftDicWin";
	this.width = (width && width > 200) ? parseInt(width, 10) : 200;
	this.height = (height && height > h) ? parseInt(height, 10) : h;
	this.left = left ? parseInt(left, 10) : ($(document.body).parent().get(0).clientWidth - this.width - 24);
	this.top = top ? parseInt(top, 10) : ($(document.body).parent().get(0).clientWidth > 360 ? 64 : 0);
	this.init();
	this.hidden()
}
new_xDic.prototype.CL_BTN_NORMAL = dicButtonColor;
new_xDic.prototype.CL_BTN_OVER = "coral";
new_xDic.prototype.items = 0;
new_xDic.prototype.pageSize = 10;
new_xDic.prototype.totalPage = 0;
new_xDic.prototype.currentPage = 1;
new_xDic.prototype.currentCode = "";
new_xDic.prototype.currentText = "";
new_xDic.prototype.disabledCode = false;
new_xDic.prototype.currentValue = "";
new_xDic.prototype.pageInfo = null;
new_xDic.prototype.spellMode = DIC_SPELL;
new_xDic.prototype.object = null;
new_xDic.prototype.tbDic = null;
new_xDic.prototype.pageInfo = null;
new_xDic.prototype.ctrlButton = [];
new_xDic.prototype.currentDic = createDOMDocument2();
new_xDic.prototype.xmlFiltered = createDOMDocument2();
new_xDic.prototype.xslDic = createDOMDocument2();
new_xDic.prototype.xslTree = createDOMDocument2();
new_xDic.prototype.docTree = createDOMDocument2();
new_xDic.prototype.xmlDics = [];
new_xDic.prototype.edit = null;
new_xDic.prototype.arrs = null;
new_xDic.prototype.currentIsLeaf = true;
new_xDic.prototype.queryArea = null;
new_xDic.prototype.filterArea = null;
new_xDic.prototype.dicClient = null;
new_xDic.prototype.itype = "";
new_xDic.prototype.first = function() {
	this.setPageNumber(1)
};
new_xDic.prototype.prev = function() {
	this.setPageNumber(--this.currentPage)
};
new_xDic.prototype.next = function() {
	this.setPageNumber(++this.currentPage)
};
new_xDic.prototype.last = function() {
	this.setPageNumber(this.totalPage)
};
new_xDic.prototype.getTreeDoc = function() {
	var doc = null;
	if (bOs == 1) {
		doc = this.docTree;
		try {
			doc.loadXML(this.currentDic.xml)
		} catch(e) {
			doc = this.currentDic;
			this.docTree = doc
		}
	} else {
		doc = this.currentDic;
		this.docTree = doc
	}
	var arr = new Object();
	var arr2 = new Object();
	var root = doc.documentElement;
	var list = doc.getElementsByTagName("R");
	for (var i = 0; i < list.length; i++) {
		var R = list[i];
		if (R.getAttribute('p') == 0) {
			arr['a' + R.getAttribute('c')] = R;
			continue
		} else {
			if (arr['a' + R.getAttribute('p')] != null) {
				arr['a' + R.getAttribute('p')].appendChild(R);
				arr['a' + R.getAttribute('c')] = R;
				if (arr2['a' + R.getAttribute('c')] != null) {
					var cNodes = arr2['a' + R.getAttribute('c')].childNodes;
					var len = cNodes.length;
					for (var j = 0; j < len; j++) {
						if (cNodes[j].nodeType == 1) R.appendChild(cNodes[j])
					}
					arr2['a' + R.getAttribute('c')] = R
				}
			} else {
				if (arr2['a' + R.getAttribute('p')] == null) {
					arr2['a' + R.getAttribute('p')] = doc.createElement('R');
					arr2['a' + R.getAttribute('p')].setAttribute('c', R.getAttribute('p'));
					arr2['a' + R.getAttribute('p')].setAttribute('isReal', 'false');
					arr2['a' + R.getAttribute('p')].appendChild(R)
				} else {
					arr2['a' + R.getAttribute('p')].appendChild(R)
				}
				if (arr2['a' + R.getAttribute('c')] != null) {
					var cNode = arr2['a' + R.getAttribute('c')].childNodes;
					var len = cNode.length;
					for (var j = 0; j < len; j++) {
						if (cNode.nodeType == 1) R.appendChild(cNode[j])
					}
					arr2['a' + R.getAttribute('c')] = R
				} else {
					arr2['a' + R.getAttribute('c')] = R
				}
			}
		}
	}
	if (bOs != 1) {
		var bArr = [];
		var cNode = root.childNodes;
		for (var i = 0; i < cNode.length; i++) {
			if (cNode[i].nodeType == 3) {
				bArr.push(cNode[i])
			}
		}
		var len = bArr.length;
		for (var j = 0; j < len; j++) {
			root.removeChild(bArr[j])
		}
	}
	for (var key in arr2) {
		var R = arr2[key];
		if (R.getAttribute('isReal') == 'false') {
			if (arr['a' + R.getAttribute('c')] != null) {
				var len = R.childNodes.length;
				for (var j = 0; j < len; j++) {
					arr['a' + R.getAttribute('c')].appendChild(R.childNodes[0])
				}
			} else {
				if (this.edit.attr("raise") == 'true') {
					while (R.childNodes.length > 0) {
						root.appendChild(R.childNodes[0])
					}
					root.removeChild(R)
				}
			}
		}
	}
	return doc
};
new_xDic.prototype.lineClick = function(obj) {
	if (this.edit.attr("hasImage")) this.imgClick(obj.nextSibling);
	else {
		var item = obj.parentElement;
		var child = $("#c" + item.id.substring(1)).get(0);
		if (child.fill != 'true') {
			var str = this.fillChild(item.id.substring(1));
			child.innerHTML = str;
			child.fill = 'true'
		}
		if (child.style.display == 'none') {
			child.style.display = '';
			var nexts = $(item).nextAll("[pid]");
			if (nexts.size() > 0) obj.src = webApps + "/images/trees/Tminus.gif";
			else obj.src = webApps + "/images/trees/Lminus.gif"
		} else {
			child.style.display = 'none';
			var nexts = $(item).nextAll("[pid]");
			if (nexts.size() > 0) {
				obj.src = webApps + "/images/trees/Tplus.gif"
			} else obj.src = webApps + "/images/trees/Lplus.gif"
		}
	}
};
new_xDic.prototype.imgClick = function(obj) {
	var item = obj.parentElement;
	var child = $("#c" + item.id.substring(1));
	if (child.size() == 0) {
		IsOpen = !IsOpen;
		if (IsOpen) obj.previousSibling.src = webApps + "/images/trees/Tminus.gif";
		else obj.previousSibling.src = webApps + "/images/trees/Lplus.gif";
		return
	}
	if (child.attr("fill") != 'true') {
		var str = this.fillChild(item.id.substring(1));
		child.html(str);
		child.attr("fill", 'true')
	}
	if (child.is(":hidden") == true) {
		if (child.next().size() > 0) obj.previousSibling.src = webApps + "/images/trees/Tminus.gif";
		else obj.previousSibling.src = webApps + "/images/trees/Lminus.gif";
		obj.src = webApps + "/images/trees/openfoldericon.gif";
		child.show()
	} else {
		if (child.next().size() > 0) obj.previousSibling.src = webApps + "/images/trees/Tplus.gif";
		else obj.previousSibling.src = webApps + "/images/trees/Lplus.gif";
		obj.src = webApps + "/images/trees/foldericon.gif";
		child.hide()
	}
};
new_xDic.prototype.fillChild = function(id) {
	var doc = createDOMDocument2();
	var node = this.docTree.selectSingleNode("//R[@c = '" + id + "']");
	if (node == null) return;
	var i = 0;
	var p = node.parentNode;
	while (true) {
		i++;
		if (p.tagName == 'DATA') break;
		p = p.parentNode
	}
	var str = node.xml.trim();
	var pn = $("#p" + id);
	var oChildNode = pn.children();
	var prefixstr = "";
	for (var n = 0; n < oChildNode.size() - (this.edit.attr("hasImage") ? 4 : 3); n++) {
		prefixstr += oChildNode.html()
	}
	if (pn.next().next().size() > 0) prefixstr += "<img src='" + webApps + "/images/trees/I.gif' align='absbottom' type='tree'/>";
	else prefixstr += "<img src='" + webApps + "/images/trees/blank.gif' align='absbottom' type='tree'/>";
	prefixstr = prefixstr.trim().replace(/\&/g, "&amp;").replace(/>/g, "&gt;").replace(/</g, "&lt;").replace(/\"/g, "&quot;").replace(/\'/g, "&apos;");
	doc.loadXML("<DATA prefix='" + i + "' prefixstr='" + prefixstr + "'>" + str.substring(3, str.length - 4) + "</DATA>");
	var paramNode = this.xslTree.selectSingleNode("//xsl:param[@name='Expand']");
	paramNode.text = 'true';
	var res = doc.transformNode(this.xslTree);
	doc = null;
	return res.replace(/&gt;/g, ">").replace(/&lt;/g, "<").replace(/&quot;/g, "\"").replace(/&apos;/g, "\'").replace(/&amp;/g, "\&")
};
new_xDic.prototype.itemClick = function(obj) {
	obj.previousSibling.checked = true;
	g_xDic.radioClick(obj.previousSibling);
	g_xDic.hidden()
};
new_xDic.prototype.processParentCheck = function(pid, n) {
	var p = $("#p" + pid).get(0);
	var oldexp = 0;
	if (p) {
		oldexp = p.exp - 0;
		p.csn = p.csn - 0 + n;
		if (p.csn - 0 < 0) p.csn = 0;
		if (p.csn - 0 > p.cn - 0) p.csn = p.cn;
		if (p.csn - 0 == p.cn - 0) {
			p.exp = 3;
			p.lastChild.previousSibling.src = webApps + "/images/trees/quanzhong.gif"
		} else if (p.csn - 0 == 0) {
			p.exp = 1;
			var c = $("#c" + pid).get(0);
			if (c) {
				var oChildNode = c.childNodes;
				if (!oChildNode[0].id) oChildNode = oChildNode[0].childNodes;
				for (var i = 0; i < oChildNode.length; i++) {
					var cc = oChildNode[i];
					if (cc.id.substring(0, 1) == 'p') {
						if (!cc.exp) cc.exp = 1;
						if (cc.exp - 0 != 1) {
							p.exp = 2;
							break
						}
					}
				}
			}
			p.lastChild.previousSibling.src = (p.exp == 1) ? webApps + "/images/trees/buzhong.gif": webApps + "/images/trees/jubuzhong.gif"
		} else {
			p.exp = 2;
			p.lastChild.previousSibling.src = webApps + "/images/trees/jubuzhong.gif"
		}
	}
	if (p && p.pid > 0) {
		if (p.exp != oldexp) {
			if (p.exp == 3) {
				this.processParentCheck(p.pid, 1)
			} else if (p.exp == 1) {
				if (oldexp != 2) this.processParentCheck(p.pid, -1);
				else {
					this.processParentCheck(p.pid, 0)
				}
			} else {
				if (oldexp == 3) this.processParentCheck(p.pid, -1);
				else {
					this.processParentCheck(p.pid, 0)
				}
			}
		}
	}
};
new_xDic.prototype.checkClick = function(obj) {
	var p = obj.parentElement;
	var id = p.id.substring(1);
	var pid = p.pid;
	var c = $("#c" + id).get(0);
	var exp = p.exp;
	if (p.leaf != "1") {
		switch (exp - 0) {
		case 2:
		case 3:
			p.exp = 1;
			p.csn = 0;
			p.lastChild.previousSibling.src = webApps + "/images/trees/buzhong.gif";
			if (pid > 0) {
				if (exp == 3) this.processParentCheck(pid, -1);
				else this.processParentCheck(pid, 0)
			}
			break;
		case 1:
		default:
			p.exp = 3;
			p.csn = p.cn;
			p.lastChild.previousSibling.src = webApps + "/images/trees/quanzhong.gif";
			if (pid > 0) {
				this.processParentCheck(pid, 1)
			}
			break
		}
		if (p.cn - 0 > 0) {
			if (c && c.fill == 'false') {
				c.innerHTML = this.fillChild(id);
				c.fill = 'true'
			}
			if (c && c.fill == 'true') {
				var oChildNode = c.getElementsByTagName("div");
				for (var i = 0; i < oChildNode.length; i++) {
					var cc = oChildNode[i];
					if (cc.id.substring(0, 1) == 'p') {
						switch (exp - 0) {
						case 2:
						case 3:
							cc.exp = 1;
							cc.csn = 0;
							cc.lastChild.previousSibling.src = webApps + "/images/trees/buzhong.gif";
							this.arrs['c' + cc.id.substring(1)] = null;
							break;
						case 1:
						default:
							cc.exp = 3;
							cc.csn = cc.cn;
							cc.lastChild.previousSibling.src = webApps + "/images/trees/quanzhong.gif";
							if ($("#c" + cc.id.substring(1)).size() > 0) continue;
							this.arrs['c' + cc.id.substring(1)] = cc.lastChild.innerText.replace(/\[.*\]/g, "").trim();
							break
						}
					}
				}
				this.setSelectedDics()
			}
		} else {
			return
		}
	} else {
		switch (exp - 0) {
		case 2:
		case 3:
			p.exp = 1;
			p.lastChild.previousSibling.src = webApps + "/images/trees/buzhong.gif";
			if (pid > 0) this.processParentCheck(pid, -1);
			this.arrs['c' + id] = null;
			this.setSelectedDics();
			break;
		case 1:
		default:
			p.exp = 3;
			p.lastChild.previousSibling.src = webApps + "/images/trees/quanzhong.gif";
			if (pid > 0) this.processParentCheck(pid, 1);
			this.arrs['c' + id] = p.lastChild.innerText.replace(/\[.*\]/g, "").trim();
			this.setSelectedDics();
			break
		}
	}
};
new_xDic.prototype.radioClick = function(obj) {
	obj.value = obj.nextSibling.innerText;
	var code = obj.parentElement.id;
	obj.code = code.substring(1);
	g_xDic.edit.val(obj.value);
	g_xDic.edit.attr("code", obj.code)
};
new_xDic.prototype.show = function($edit, Hcode, Hvalue) {
	var doc = null;
	this.edit = $edit;
	this.currentCode = Hcode;
	this.currentText = Hvalue;
	if ($edit.attr("multi") == 'true' && !isEmpty(Hcode) && !isEmpty(Hvalue)) {
		this.arrs = [];
		var carr = Hcode.split(',');
		var tarr = Hvalue.split(',');
		if (carr && tarr && carr.length <= tarr.length && carr.length > 0) for (var i = 0; i < carr.length; i++) {
			this.arrs['c' + carr[i]] = tarr[i]
		}
	} else if ($edit.attr("multi") == 'true') this.arrs = [];
	if ($edit.attr("partner")) this.queryArea.show();
	else this.queryArea.hide();
	if ($edit.attr("multi") == "true") this.filterArea.show();
	else this.filterArea.hide();
	if ($edit.attr("itype")) {
		this.itype = $edit.attr("itype")
	} else {
		this.itype = ""
	}
	if ($edit.attr("src")) {
		var b1 = false;
		if ($edit.attr("filtercode") && $edit.attr("filtervalue")) {
			b1 = this.loadDicByFilter($edit.attr("src"), $edit.attr("filtercode"), 'code', true);
			b1 = this.loadDicByFilter($edit.attr("src"), $edit.attr("filtervalue"), 'value', false)
		} else if ($edit.attr("filtercode")) {
			b1 = this.loadDicByFilter($edit.attr("src"), $edit.attr("filtercode"), 'code', true)
		} else if ($edit.attr("filtervalue")) {
			b1 = this.loadDicByFilter($edit.attr("src"), $edit.attr("filtervalue"), 'value', true)
		} else {
			b1 = this.loadDic($edit.attr("src"))
		}
		if (domToStr(this.currentDic) == "") {
			alert($edit.attr("src") + " 字典文件不存在");
			return false
		}
		if (b1) {
			if (bOs == 1) {
				try {
					this.xmlFiltered.loadXML(this.currentDic.xml)
				} catch(e) {
					this.xmlFiltered = this.currentDic
				}
			} else {
				this.xmlFiltered = this.currentDic
			}
			var rootNode = this.xmlFiltered.documentElement;
			var rowNodes;
			rowNodes = rootNode.getElementsByTagName("R");
			this.items = rowNodes.length;
			if (rowNodes.length % this.pageSize > 0) {
				this.totalPage = Math.round(rowNodes.length / this.pageSize + .5)
			} else {
				this.totalPage = Math.round(rowNodes.length / this.pageSize)
			}
			this.totalPage = this.totalPage == 0 ? 1 : this.totalPage;
			this.currentPage = 1;
			if (this.edit.attr("tree") == "true") {
				if (this.edit.attr("multi") && this.edit.attr("multi") == 'true') {
					if (!domToStr(this.xslTree)) {
						this.xslTree.load(webApps + "/css/tree.xsl")
					} else {
						if (this.edit.attr("isreload") && this.edit.attr("isreload") == "true") this.xslTree.load(webApps + "/css/tree.xsl")
					}
					doc = this.getTreeDoc();
					var paramNode = this.xslTree.selectSingleNode("//xsl:param[@name='Expand']");
					if (this.edit.attr("expand") == 'true') {
						paramNode.text = 'true'
					} else paramNode.text = 'false';
					if (this.edit.attr("hasCode") == 'true') {
						paramNode = this.xslTree.selectSingleNode("//xsl:param[@name='HasCode']");
						paramNode.text = 'true'
					}
					if (this.edit.attr("hasImage") == 'false') {
						paramNode = this.xslTree.selectSingleNode("//xsl:param[@name='HasImage']");
						paramNode.text = 'false'
					}
				} else {
					if (!domToStr(this.xslTree)) {
						if (bOs == 6 || bOs == 3) {
							var xmlhttp = new window.XMLHttpRequest();
							xmlhttp.open("GET", webApps + "/css/tree_single.xsl", false);
							xmlhttp.send(null);
							this.xslTree = xmlhttp.responseXML
						} else try {
							this.xslTree.load(webApps + "/css/tree_single.xsl")
						} catch(e) {
							var xmlhttp = new window.XMLHttpRequest();
							xmlhttp.open("GET", webApps + "/css/tree_single.xsl", false);
							xmlhttp.send(null);
							this.xslTree = xmlhttp.responseXML
						}
					} else {
						if (this.edit.attr("isreload") && this.edit.attr("isreload") == "true") {
							if (bOs == 6 || bOs == 3) {
								var xmlhttp = new window.XMLHttpRequest();
								xmlhttp.open("GET", webApps + "/css/tree_single.xsl", false);
								xmlhttp.send(null);
								this.xslTree = xmlhttp.responseXML
							} else try {
								this.xslTree.load(webApps + "/css/tree_single.xsl")
							} catch(e) {
								var xmlhttp = new window.XMLHttpRequest();
								xmlhttp.open("GET", webApps + "/css/tree_single.xsl", false);
								xmlhttp.send(null);
								this.xslTree = xmlhttp.responseXML
							}
						}
					}
					doc = this.getTreeDoc();
					var rootElement = this.xslTree.documentElement;
					var paramNode = null;
					if (bOs == 1) paramNode = rootElement.childNodes[4];
					else paramNode = rootElement.childNodes[9];
					if (this.edit.attr("expand") == 'true') {
						paramNode.text = 'true'
					} else paramNode.text = 'false';
					if (this.edit.attr("hasCode") == 'true') {
						if (bOs == 1) paramNode = rootElement.childNodes[3];
						else paramNode = rootElement.childNodes[7];
						paramNode.text = 'true'
					}
					if (this.edit.attr("hasImage") == 'false') {
						if (bOs == 1) paramNode = rootElement.childNodes[2];
						else paramNode = rootElement.childNodes[5];
						paramNode.text = 'false'
					}
				}
				var res = null;
				if (bOs == 1) try {
					res = doc.transformNode(this.xslTree)
				} catch(e) {
					var xsltProcessor = new XSLTProcessor();
					xsltProcessor.importStylesheet(this.xslTree);
					res = domToStr(xsltProcessor.transformToFragment(doc, document))
				} else {
					var xsltProcessor = new XSLTProcessor();
					xsltProcessor.importStylesheet(this.xslTree);
					res = domToStr(xsltProcessor.transformToFragment(doc, document))
				}
				res = res.replace(/&gt;/g, ">").replace(/&lt;/g, "<");
				this.dicClient = this.dicClient.detach();
				var treediv = $("<DIV></DIV>");
				treediv.width(this.width - 4);
				treediv.height(this.height - 24);
				treediv.css("background-color", "#f1f1f1");
				treediv.css("border", "1px inset silver");
				treediv.css("font-size", "9pt");
				treediv.css("color", "black");
				treediv.attr("id", "treediv");
				treediv.css("display", "block");
				treediv.css("overflow", "auto");
				this.object.setClientByObject(treediv);
				treediv.html(res);
				this.object.setRange(0, 0, $(document.body).parent().get(0).clientWidth, $(document.body).parent().get(0).clientHeight);
				this.object.setTitle("字&nbsp;&nbsp;典&nbsp;&nbsp;<span style='font:9pt'>[层级模式]")
			} else if (this.edit.get(0).tagName == "SELECT") {
				var rootNode = this.xmlFiltered.documentElement;
				var rowNodes;
				rowNodes = rootNode.getElementsByTagName("R");
				rowNodes = rowNodes.length == 0 ? this.currentDic.documentElement: rowNodes;
				var s = "";
				for (var i = 0; i < rowNodes.length; i++) {
					var sCode = rowNodes[i].getAttribute("c");
					var sText;
					if (syslanguage == "1") sText = rowNodes[i].getAttribute("t");
					else sText = rowNodes[i].getAttribute("e");
					if (this.edit.val() && this.edit.val() == sCode) s += "<OPTION value='" + sCode + "' selected>" + sText + "</OPTION>";
					else s += "<OPTION value='" + sCode + "'>" + sText + "</OPTION>"
				}
				var ss = "";
				try {
					if (this.edit.attr("datatype").split(",")[0] == "1") ss = "<OPTION value='-1'>--</OPTION>"
				} catch(e) {}
				this.edit.html(ss + s);
				return true
			} else {
				if (this.edit.attr("src").indexOf("T#") == -1) this.object.setTitle("字&nbsp;&nbsp;典&nbsp;&nbsp;<span style='font:9pt;'>[简拼模式]</span>");
				else if (this.edit.attr("src").indexOf("T#") == 0) this.object.setTitle("字&nbsp;&nbsp;典&nbsp;&nbsp;<span style='font:9pt;'>[表选模式]</span>");
				else if (this.edit.attr("src").indexOf("E#") == 0) this.object.setTitle("字&nbsp;&nbsp;典&nbsp;&nbsp;<span style='font:9pt;color:red;'>[枚举模式]</span>");
				this.dicClient = this.dicClient.detach();
				this.object.setClientByObject(this.xInitClient);
				var dw = this.width;
				var dh = 0;
				if (this.edit.attr("multi") == 'true') {
					if (this.edit.attr("dicstyle") == 'check') {
						dh = this.pageSize * 24 + 74
					} else {
						dh = this.pageSize * 18 + 74
					}
				} else {
					dh = this.pageSize * 16 + 74
				}
				var rootNode = this.xmlFiltered.documentElement;
				var dic_xml_width = rootNode.getAttribute("dicwidth");
				var dic_xml_height = rootNode.getAttribute("dicheigth");
				if (dic_xml_width) dw = dic_xml_width;
				if (dic_xml_height) dh = dic_xml_height;
				if (this.edit.attr("dicwidth") && this.edit.attr("dicwidth") - 0 > dw) dw = this.edit.attr("dicwidth");
				if (this.edit.attr("dicheight") && this.edit.attr("dicheight") - 0 > dh) dh = this.edit.attr("dicheight");
				this.object.setSize(dw, dh);
				this.object.setRange(0, 0, $(document.body).parent().get(0).clientWidth, document.documentElement.clientHeight);
				this.setStatus()
			}
			if (!this.object.isFix && !g_bDicFixed) {
				var atop = 0;
				var obj = this.edit.get(0);
				var aleft = 0;
				while (obj) {
					atop += $(obj).position().top - $(document.body).scrollTop();
					aleft += $(obj).position().left - $(document.body).scrollLeft();
					obj = $(obj).offsetParent().get(0);
					if (!obj || obj.tagName == 'BODY') {
						break
					}
					if (obj.tagName == 'TR') obj = $(obj).parent().get(0)
				}
				if ($(document.body).get(0).offsetWidth - aleft < this.width) aleft = aleft + ($(document.body).get(0).offsetWidth - aleft - this.width);
				if (document.documentElement.clientHeight - atop + 30 > this.height) {
					this.object.moveTo(aleft, atop + 22)
				} else {
					var n = atop + this.height - document.documentElement.clientHeight;
					if (n > 0) this.object.moveTo(aleft, atop - this.height + n);
					else this.object.moveTo(aleft, atop - this.height - 4)
				}
			}
			this.object.showWin()
		}
	}
	this.currentCode = "";
	this.currentText = ""
};
new_xDic.prototype.hidden = function() {
	if (this.edit) {
		if (myonblur(this.edit.attr("id"))) {}
	}
	this.edit = null;
	this.arrs = null;
	this.object.xWin.hide();
	this.object.close();
	this.items = 0
};
new_xDic.prototype.init = function() {
	var oThis = this;
	this.object = new new_xWin(this.id, this.left, this.top, this.width, this.height, "", "", winTitleColor, winColor, winCaretColor, "silver", true, false, false);
	this.object.setTitle("字&nbsp;&nbsp;典&nbsp;&nbsp;<span style='font:9pt'>[简拼模式]</span>");
	var client = $("<DIV style='overflow:hidden;text-align:center;padding:1px 0px 2px 2px;'></DIV>");
	client.width(this.width - 4);
	client.height(this.height - 24);
	client.css("background", "#f1f1f1");
	client.css("border-top", "1px inset silver");
	client.css("font-size", "9pt");
	client.css("color", "black");
	client.attr("id", "ceshi2");
	this.dicClient = client;
	this.object.setClientByObject(client);
	this.xInitClient = client;
	var dicQuery = $("<DIV style='background:#f4f4f4;position:relative;width:100%;height:20px;display:none' nowrap valign='top'></DIV>");
	dicQuery.html("<input type=text style='width:130px; color:#808080; background-color:white; border:1px solid silver;' id='dicQueryEdit'>&nbsp;&nbsp;<a href='#' onClick='getDicByCondition()'>查找&gt;&gt;</a>");
	this.queryArea = dicQuery;
	client.append(dicQuery);
	var dicQuery2 = $("<DIV style='display:none;background:#f4f4f4;position:relative;width:100%;height:20px;' nowrap valign='top'></DIV>");
	dicQuery2.html("<input type=text style='width:98%;color:#808080; background-color:white; border:1px solid silver;' id='dicFilterEdit'>");
	this.filterArea = dicQuery2;
	client.append(dicQuery2);
	$("#dicFilterEdit").bind("keyup",
	function() {
		doUserFilter(this)
	});
	var tbDic = $("<TABLE id='dicTab' style='width:100%;' cellpadding='0' cellspacing='0'></TABLE>");
	this.tbDic = tbDic;
	tbDic.css("border-left", "solid 1px #808080");
	tbDic.css("bordercolordark", "white");
	tbDic.css("bordercolorlight", "#808080");
	client.append(tbDic);
	var thead = $("<THEAD></THEAD>");
	tbDic.append(thead);
	var tr = $("<TR style='color:black;font-size:9pt;height:20px;'></TR>");
	tr.css("font-family", "Courier New");
	thead.append(tr);
	var th = $("<TH style='vertical-align:middle;background:#d8d8d8'></TH>");
	th.width(25);
	th.html(" ");
	th.css("font-weight", "bolder");
	th.css("text-align", "left");
	th.css("border-right", "1px inset #808080");
	th.css("border-bottom", "1px inset #808080");
	tr.append(th);
	th = $("<TH nowrap style='vertical-align:middle;background:#d8d8d8''></TH>");
	if (syslanguage == "1") th.html("<span>&nbsp;代&nbsp;&nbsp;码</span>");
	else th.html("<span>Code</span>");
	th.css("font-weight", "bolder");
	th.css("text-align", "left");
	th.css("border-right", "1px inset #808080");
	th.css("border-bottom", "1px inset #808080");
	tr.append(th);
	th = $("<TH nowrap style='vertical-align:middle;background:#d8d8d8''></TH>");
	if (syslanguage == "1") th.html("<span>&nbsp;内&nbsp;&nbsp;&nbsp;&nbsp;容</span>");
	else th.html("<span>Content</span>");
	th.css("font-weight", "bolder");
	th.css("text-align", "left");
	th.css("border-right", "1px inset #808080");
	th.css("border-bottom", "1px inset #808080");
	tr.append(th);
	if (bOs == 1) {
		var tbody = $("<TBODY></TBODY>");
		tbDic.append(tbody)
	}
	for (var i = 0; i < this.pageSize; i++) {
		var row = $("<TR style='height:17px;line-height:18px;' ></TR>");
		if (bOs == 1) $(">tbody", tbDic).append(row);
		else $(tbDic).append(row);
		for (var j = 1; j <= 3; j++) {
			var col = $("<TD style='color:black;font-size:9pt;vertical-align:middle;white-space:nowrap;' ></TD>");
			col.css("text-align", (j == 1 ? "center": "left"));
			if (j == 1) col.css("background", "#d8d8d8");
			col.css("border-right", "1px inset #808080");
			col.css("border-bottom", "1px inset #808080");
			col.html(j == 1 ? i + 1 : "$nbsp;");
			row.append(col)
		}
	}
	$("tr", this.tbDic).each(function() {
		var row = $(this);
		row.bind("click",
		function(event) {
			doClickDicItem($(this))
		});
		row.bind("mouseover",
		function(event) {
			var $this = $(this);
			if ($this.get(0).tagName == "TR") {
				if ($this.attr("isLeaf") != "1" && g_xDic.edit.attr("leafonly") == 'true') {
					$this.css("background-color", "#CCCCCC")
				} else {
					$this.css("background-color", "#FFDA46")
				}
			}
		});
		row.bind("mouseout",
		function(event) {
			var src = $(this);
			if (src.get(0).tagName == "TR") {
				if (src.attr("isLeaf") != "1" && g_xDic.edit && g_xDic.edit.attr("leafonly") == 'true') {
					src.css("background-color", "#CCCCCC")
				} else {
					src.css("background-color", "transparent")
				}
			}
		})
	});
	var dicBottom = $("<DIV style='float:left;position:relative;width:100%;height:15px;background:#f4f4f4;margin-top:2px;'></DIV>");
	client.append(dicBottom);
	var hintArea = $("<SPAN style='height:100%;'></SPAN>");
	hintArea.width(this.width - 100 - 8);
	dicBottom.append(hintArea);
	var hintInfo = $("<SPAN style='float:left;margin-left:8px;color:black;font-size:9pt;padding-right:4px;font-family:webdings'></SPAN>");
	if (bOs == 1) hintInfo.text("i");
	else hintInfo.text("ⓘ");
	hintInfo.attr("title", "提示： " + "\n\t⑴ 简拼模式：" + "\n\t 支持代码 + 内容的模糊检索;" + "\n\t 支持内容汉字简拼检索;" + "\n\t⑵ 表选模式：" + "\n\t 支持代码 + 内容的模糊检索;");
	hintArea.append(hintInfo);
	var pageInfo = $("<SPAN style='float:left;margin-left:20px;'></SPAN>");
	this.pageInfo = pageInfo;
	hintArea.append(pageInfo);
	var ctrlArea = $("<SPAN style='width:100%;height:15px;font-size:11pt;font-family:webdings'></SPAN>");
	ctrlArea.css("color", this.CL_BTN_NORMAL);
	ctrlArea.css("padding", "2px");
	ctrlArea.css("padding-left", "4px");
	dicBottom.append(ctrlArea);
	var btnSpellSwitch = $("<SPAN style='font-size:11pt;font-family:webdings;display:none;'></SPAN>");
	if (bOs == 1) btnSpellSwitch.text("`");
	else btnSpellSwitch.text("⇆");
	btnSpellSwitch.attr("title", "切换到全拼输入模式");
	btnSpellSwitch.width(15);
	btnSpellSwitch.height(15);
	btnSpellSwitch.css("color", "blue");
	btnSpellSwitch.css("padding-left", "10px");
	btnSpellSwitch.css("padding-right", "10px");
	ctrlArea.append(btnSpellSwitch);
	var btnFirst = $("<SPAN style='font-size:11pt;font-family:webdings'></SPAN>");
	btnFirst.text("9");
	if (syslanguage == "1") btnFirst.attr("title", "第一页");
	else btnFirst.attr("title", "First Page");
	btnFirst.width(15);
	btnFirst.height(15);
	ctrlArea.append(btnFirst);
	var btnPrev = $("<SPAN style='font-size:11pt;font-family:webdings'></SPAN>");
	btnPrev.text("3");
	if (syslanguage == "1") btnPrev.attr("title", "上一页");
	else btnPrev.attr("title", "Pre Page");
	btnPrev.width(15);
	btnPrev.height(15);
	ctrlArea.append(btnPrev);
	var btnNext = $("<SPAN style='font-size:11pt;font-family:webdings'></SPAN>");
	btnNext.text("4");
	if (syslanguage == "1") btnNext.attr("title", "下一页");
	else btnNext.attr("title", "Next Page");
	btnNext.width(15);
	btnNext.height(15);
	ctrlArea.append(btnNext);
	var btnLast = $("<SPAN style='font-size:11pt;font-family:webdings'></SPAN>");
	btnLast.text(":");
	if (syslanguage == "1") btnLast.attr("title", "最后一页");
	else btnLast.attr("title", "Last Page");
	btnLast.width(15);
	btnLast.height(15);
	ctrlArea.append(btnLast);
	this.ctrlButton = [btnSpellSwitch.get(0), btnFirst.get(0), btnPrev.get(0), btnNext.get(0), btnLast.get(0)];
	if (this.currentDic) {
		this.xslDic.async = false;
		this.currentDic.async = false;
		this.xmlFiltered.async = false;
		var sXSL = '<?xml version="1.0" encoding="UTF-8"?>' + '<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">' + '<xsl:output omit-xml-declaration="yes" indent="yes"/>' + '<xsl:template match="/">' + '<xsl:element name="DATA">' + '<xsl:for-each>' + '<xsl:element name="R">' + '<xsl:for-each select="@*">' + '<xsl:attribute name="{name()}">' + '<xsl:value-of select="."/>' + '</xsl:attribute>' + '</xsl:for-each>' + '</xsl:element>' + '</xsl:for-each>' + '</xsl:element>' + '</xsl:template>' + '</xsl:stylesheet>';
		if (bOs == 1) {
			try {
				this.xslDic.loadXML(sXSL)
			} catch(e) {
				this.xslDic = loadFromStr(sXSL)
			}
		} else {
			this.xslDic = loadFromStr(sXSL)
		}
	}
	btnSpellSwitch.bind("click", {},
	function(event) {
		var sTitle, sMode;
		if (oThis.spellMode == DIC_SPELL) {
			sMode = "全拼模式";
			sTitle = "切换到混拼输入模式";
			btnSpellSwitch.css("color", "green");
			oThis.spellMode = DIC_ALLSPELL
		} else if (oThis.spellMode == DIC_ALLSPELL) {
			sMode = "混拼模式";
			sTitle = "切换到简拼输入模式";
			btnSpellSwitch.css("color", "red");
			oThis.spellMode = DIC_MIXSPELL
		} else {
			sMode = "简拼模式";
			sTitle = "切换到全拼输入模式";
			btnSpellSwitch.css("color", "blue");
			oThis.spellMode = DIC_SPELL
		}
		btnSpellSwitch.attr("title", sTitle);
		oThis.object.setTitle("字&nbsp;&nbsp;典&nbsp;&nbsp;<span style='font:8pt'>[" + sMode + "]</span>");
		if (oThis.currentValue != "") oThis.getDic(oThis.currentValue)
	});
	hintInfo.bind("mouseover", {},
	function(event) {
		oThis.btnOver($(this), "mediumblue")
	});
	hintInfo.bind("mouseout", {},
	function(event) {
		oThis.btnOut($(this), "black")
	});
	btnFirst.bind("click", {},
	function(event) {
		oThis.first()
	});
	btnPrev.bind("click", {},
	function(event) {
		oThis.prev()
	});
	btnNext.bind("click", {},
	function(event) {
		oThis.next()
	});
	btnLast.bind("click", {},
	function(event) {
		oThis.last()
	});
	btnFirst.bind("mouseover", {},
	function(event) {
		oThis.btnOver($(this))
	});
	btnPrev.bind("mouseover", {},
	function(event) {
		oThis.btnOver($(this))
	});
	btnNext.bind("mouseover", {},
	function(event) {
		oThis.btnOver($(this))
	});
	btnLast.bind("mouseover", {},
	function(event) {
		oThis.btnOver($(this))
	});
	btnFirst.bind("mouseout", {},
	function(event) {
		oThis.btnOut($(this))
	});
	btnPrev.bind("mouseout", {},
	function(event) {
		oThis.btnOut($(this))
	});
	btnNext.bind("mouseout", {},
	function(event) {
		oThis.btnOut($(this))
	});
	btnLast.bind("mouseout", {},
	function(event) {
		oThis.btnOut($(this))
	})
};
new_xDic.prototype.setPageNumber = function(index) {
	this.currentPage = index < 1 ? 1 : (index > this.totalPage ? this.totalPage: index);
	this.setStatus()
};
new_xDic.prototype.loadDic = function(sDicFile) {
	try {
		var isreload = this.edit.attr("isreload");
		if (this.edit.attr("filtercode")) isreload = true;
		if (isreload == undefined && sDicFile.substr(0, 2).toUpperCase() == "T#") isreload = "false";
		if (isreload == undefined) isreload = "false";
		if (isreload == "false" && this.xmlDics[sDicFile]) {
			this.currentDic = this.xmlDics[sDicFile]
		} else {
			this.xmlDics[sDicFile] = createDOMDocument2();
			this.xmlDics[sDicFile].async = false;
			if (sDicFile.substr(0, 1) == "#") {
				var oXML = $("#" + sDicFile.substr(1));
				if (domToStr(oXML) == "") {
					throw 0
				}
				try {
					this.xmlDics[sDicFile].loadXML(domToStr(oXML))
				} catch(e) {
					this.xmlDics[sDicFile] = loadFromStr(domToStr(oXML))
				}
			} else if (sDicFile.substr(0, 2).toUpperCase() == "T#") {
				if (!this.edit.attr("partner")) {
					var vparam = sDicFile.substr(2);
					while (vparam.indexOf("%") > 0) {
						vparam = vparam.replace("%", "$")
					}
					var xmlhttp2 = createXmlHttp();
					var u = encodeURI(webApps + "/servlet/GetDicFromTable?param=" + vparam);
					xmlhttp2.open("POST", u, false);
					xmlhttp2.send("");
					if (bOs == 1) {
						try {
							this.xmlDics[sDicFile].loadXML(xmlhttp2.responseText)
						} catch(e) {
							this.xmlDics[sDicFile] = loadFromStr(xmlhttp2.responseText)
						}
					} else {
						this.xmlDics[sDicFile] = loadFromStr(xmlhttp2.responseText)
					}
					xmlhttp2 = null
				} else {
					if ($('#dicQueryEdit').val() != '') {
						var vparam = sDicFile.substr(2);
						while (vparam.indexOf("%") > 0) {
							vparam = vparam.replace("%", "$")
						}
						var xmlhttp2 = createXmlHttp();
						xmlhttp2.open("POST", webApps + "/servlet/GetDicFromTable?param=" + vparam + "&key=" + this.edit.attr("partner") + "&value=" + $('#dicQueryEdit').val() + "&operate=" + (this.edit.attr("partnerOperation") ? this.edit.attr("partnerOperation") : 'like'), false);
						xmlhttp2.send("");
						if (bOs == 1) {
							try {
								this.xmlDics[sDicFile].loadXML(xmlhttp2.responseText)
							} catch(e) {
								this.xmlDics[sDicFile] = loadFromStr(xmlhttp2.responseText)
							}
						} else {
							this.xmlDics[sDicFile] = loadFromStr(xmlhttp2.responseText)
						}
						xmlhttp2 = null
					} else {
						try {
							this.xmlDics[sDicFile].loadXML("<DATA></DATA>")
						} catch(e) {
							this.xmlDics[sDicFile] = loadFromStr("<DATA></DATA>")
						}
					}
				}
			} else if (sDicFile.substr(0, 2).toUpperCase() == "E#") {
				var vparam = sDicFile.substr(2);
				var list = vparam.split(':');
				var dstr = "<DATA>";
				for (var i = 0; i < list.length; i++) {
					var unit = list[i].split('=');
					if (unit.length == 2) {
						dstr += "<R c='" + unit[0] + "' t='" + unit[1] + "'/>"
					} else continue
				}
				dstr += "</DATA>";
				if (bOs == 1) {
					try {
						this.xmlDics[sDicFile].loadXML(dstr)
					} catch(e) {
						this.xmlDics[sDicFile] = loadFromStr(dstr)
					}
				} else {
					this.xmlDics[sDicFile] = loadFromStr(dstr)
				}
			} else {
				if (bOs == 6 || bOs == 3) {
					var xmlhttp = new window.XMLHttpRequest();
					xmlhttp.open("POST", g_sDicPath + sDicFile + ".xml", false);
					xmlhttp.send(null);
					this.xmlDics[sDicFile] = xmlhttp.responseXML
				} else try {
					this.xmlDics[sDicFile].load(g_sDicPath + sDicFile + ".xml")
				} catch(e) {
					var xmlhttp = new window.XMLHttpRequest();
					xmlhttp.open("POST", g_sDicPath + sDicFile + ".xml", false);
					xmlhttp.send(null);
					this.xmlDics[sDicFile] = xmlhttp.responseXML
				}
			}
			this.currentDic = this.xmlDics[sDicFile]
		}
		return true
	} catch(e) {
		alert("字典[" + sDicFile + "]加载错误！");
		return false
	}
};
function getFilterValueXml(cXml, sValue) {
	try {
		if (!cXml) {
			return null
		}
		var regexp = new RegExp(sValue, "");
		var sAttrib = "t";
		var xmlTemp = createDOMDocument2();
		xmlTemp.async = false;
		if (bOs == 1) {
			try {
				xmlTemp.loadXML(cXml.xml)
			} catch(e) {
				xmlTemp = cXml
			}
		} else xmlTemp = cXml;
		var nroot = xmlTemp.documentElement;
		var rows = nroot.getElementsByTagName("R");
		for (var i = 0; i < rows.length; i++) {
			if (!regexp.test(rows[i].getAttribute(sAttrib))) {
				nroot.removeChild(rows[i])
			}
		}
		return xmlTemp
	} catch(ee) {
		alert("getFilterValueXml:" + ee)
	}
	return null
}
new_xDic.prototype.loadDicByFilter = function(sDicFile, sFilterCode, kind, newXML) {
	try {
		var isreload = "true";
		if (isreload == "false" && this.xmlDics[sDicFile]) {
			if (newXML) {
				this.currentDic = this.xmlDics[sDicFile]
			}
			if (sFilterCode != null && sFilterCode.length > 0) {
				if (kind == 'code') {
					this.currentDic = getFilterXml(this.currentDic, sFilterCode)
				} else {
					this.currentDic = getFilterValueXml(this.currentDic, sFilterCode)
				}
			}
		} else {
			if (newXML) {
				this.currentDic = createDOMDocument2();
				this.currentDic.async = false;
				if (sDicFile.substr(0, 1) == "#") {
					var oXML = $("#" + sDicFile.substr(1));
					if (oXML.xml == "") {
						throw 0
					}
					this.currentDic.loadXML(domToStr(oXML))
				} else if (sDicFile.substr(0, 2).toUpperCase() == "T#") {
					if (!this.edit.attr("partner")) {
						var vparam = sDicFile.substr(2);
						var xmlhttp2 = createXmlHttp();
						xmlhttp2.open("POST", webApps + "/servlet/GetDicFromTable?param=" + vparam, false);
						xmlhttp2.send("");
						if (bOs == 1) {
							try {
								this.currentDic.loadXML(xmlhttp2.responseText)
							} catch(e) {
								this.currentDic = loadFromStr(xmlhttp2.responseText)
							}
						} else {
							this.currentDic = loadFromStr(xmlhttp2.responseText)
						}
						xmlhttp2 = null
					} else {
						if ($('#dicQueryEdit').val() != '') {
							var vparam = sDicFile.substr(2);
							var xmlhttp2 = createXmlHttp();
							xmlhttp2.open("POST", webApps + "/servlet/GetDicFromTable?param=" + vparam + "&key=" + this.edit.attr("partner") + "&value=" + $('#dicQueryEdit').val() + "&operate=" + (this.edit.attr("partnerOperation") ? this.edit.attr("partnerOperation") : 'like'), false);
							xmlhttp2.send("");
							if (bOs == 1) {
								try {
									this.currentDic.loadXML(xmlhttp2.responseText)
								} catch(e) {
									this.currentDic = loadFromStr(xmlhttp2.responseText)
								}
							} else {
								this.currentDic = loadFromStr(xmlhttp2.responseText)
							}
							xmlhttp2 = null
						} else {
							if (bOs == 1) {
								try {
									this.currentDic.loadXML("<DATA></DATA>")
								} catch(e) {
									this.currentDic = loadFromStr("<DATA></DATA>")
								}
							} else this.currentDic = loadFromStr("<DATA></DATA>")
						}
					}
				} else if (sDicFile.substr(0, 2).toUpperCase() == "E#") {
					var vparam = sDicFile.substr(2);
					var list = vparam.split(':');
					var dstr = "<DATA>";
					for (var i = 0; i < list.length; i++) {
						var unit = list[i].split('=');
						if (unit.length == 2) {
							dstr += "<R c='" + unit[0] + "' t='" + unit[1] + "'/>"
						} else continue
					}
					dstr += "</DATA>";
					if (bOs == 1) {
						try {
							this.currentDic.loadXML(dstr)
						} catch(e) {
							this.currentDic = loadFromStr(dstr)
						}
					} else this.currentDic = loadFromStr(dstr)
				} else {
					if (bOs == 6 || bOs == 3) {
						var xmlhttp = new window.XMLHttpRequest();
						xmlhttp.open("POST", g_sDicPath + sDicFile + ".xml", false);
						xmlhttp.send(null);
						this.currentDic = xmlhttp.responseXML
					} else {
						try {
							this.currentDic.load(g_sDicPath + sDicFile + ".xml")
						} catch(e) {
							var xmlhttp = new window.XMLHttpRequest();
							xmlhttp.open("POST", g_sDicPath + sDicFile + ".xml", false);
							xmlhttp.send(null);
							this.currentDic = xmlhttp.responseXML
						}
					}
				}
			}
			if (sFilterCode != null && sFilterCode.length > 0) {
				if (kind == 'code') {
					this.currentDic = getFilterXml(this.currentDic, sFilterCode)
				} else {
					this.currentDic = getFilterValueXml(this.currentDic, sFilterCode)
				}
			}
			this.xmlDics[sDicFile] = this.currentDic
		}
		return true
	} catch(e) {
		alert("字典加载错误！\n" + sDicFile + " 字典文件不存在");
		return false
	}
};
function getFilterXml(cXml, sValue) {
	try {
		if (!cXml) {
			return null
		}
		var regexp = new RegExp(sValue, "");
		var sAttrib = "c";
		var xmlTemp = createDOMDocument2();
		xmlTemp.async = false;
		if (bOs == 1) {
			try {
				xmlTemp.loadXML(cXml.xml)
			} catch(e) {
				xmlTemp = $.parseXML(domToStr(cXml))
			}
		} else {
			xmlTemp = $.parseXML(domToStr(cXml))
		}
		var nroot = xmlTemp.documentElement;
		var rows = nroot.getElementsByTagName("R");
		var r = [];
		var len = rows.length;
		for (var i = 0; i < len; i++) {
			if (!regexp.test(rows[i].getAttribute(sAttrib))) {
				r.push(rows[i])
			}
		}
		while (r.length > 0) {
			nroot.removeChild(r[0]);
			r.shift(0)
		}
		return xmlTemp
	} catch(ee) {
		alert("getFilterXml:" + ee)
	}
	return null
}
new_xDic.prototype.filter = function(sValue, iType) {
	try {
		if (!this.currentDic && !this.xslDic) return false;
		var sAttrib;
		switch (iType) {
		case DIC_CODE:
			sAttrib = "c";
			break;
		case DIC_SPELL:
			sAttrib = "s";
			sValue = sValue.toLowerCase();
			break;
		case DIC_ALLSPELL:
			sAttrib = "a";
			sValue = sValue.toLowerCase();
			break;
		case DIC_MIXSPELL:
			sAttrib = "DIC_MIXSPELL";
			sValue = sValue.toLowerCase();
			break;
		case DIC_TEXT:
		case DIC_STEXT:
			sAttrib = "t";
			break;
		default:
			sAttrib = ""
		}
		var rootNode = this.xslDic.documentElement;
		var node = rootNode.childNodes[1].firstChild.firstChild;
		if (iType == DIC_CODE || iType == DIC_SPELL || iType == DIC_ALLSPELL) {
			node.setAttribute("select", "//R[contains(@" + sAttrib + ",'" + sValue + "')]")
		} else if (iType == DIC_MIXSPELL) {
			node.setAttribute("select", "//R[starts-with(@s,'" + sValue + "') or " + "starts-with(@a,'" + sValue + "')]")
		} else if (iType == DIC_STEXT) {
			node.setAttribute("select", "//R[starts-with(@" + sAttrib + ",'" + sValue + "')]")
		} else if (iType == DIC_TEXT) {
			node.setAttribute("select", "//R[contains(@" + sAttrib + ",'" + sValue + "')]")
		} else if (iType == DIC_TABLE) {
			node.setAttribute("select", "//R[contains(@c,'" + sValue + "') or " + "contains(@t,'" + sValue + "')]")
		} else if (iType == DIC_ENUM) {
			node.setAttribute("select", "//R[contains(@c,'" + sValue + "') or " + "contains(@t,'" + sValue + "')]")
		} else {
			node.setAttribute("select", "//R")
		}
		if (bOs == 1) this.currentDic.transformNodeToObject(this.xslDic, this.xmlFiltered);
		else {
			var xsltProcessor = new XSLTProcessor();
			xsltProcessor.importStylesheet(this.xslDic);
			var result = xsltProcessor.transformToDocument(this.currentDic);
			this.xmlFiltered = result
		}
		rootNode = this.xmlFiltered.documentElement;
		var rowNodes = rootNode.getElementsByTagName("R");
		this.items = rowNodes.length;
		this.totalPage = Math.round(rowNodes.length / this.pageSize + .5);
		this.totalPage = this.totalPage == 0 ? 1 : this.totalPage;
		this.currentPage = 1;
		this.setStatus()
	} catch(e) {
		this.items = 0
	}
	return this.items
};
new_xDic.prototype.setStatus = function() {
	with(this) {
		ctrlButton[1].disabled = currentPage == 1 ? true: false;
		ctrlButton[2].disabled = currentPage == 1 ? true: false;
		ctrlButton[3].disabled = currentPage == totalPage ? true: false;
		ctrlButton[4].disabled = currentPage == totalPage ? true: false;
		for (var i = 1; i < ctrlButton.length; i++) {
			if (ctrlButton[i].disabled) {
				ctrlButton[i].style.color = this.CL_BTN_NORMAL;
				ctrlButton[i].style.cursor = "default"
			} else ctrlButton[i].style.cursor = "pointer"
		}
		if (syslanguage == "1") pageInfo.text("[" + currentPage + "/" + totalPage + "] 页");
		else pageInfo.text("[" + currentPage + "/" + totalPage + "] Page");
		fillTable()
	}
};
new_xDic.prototype.fillTable = function() {
	try {
		var tTable = 0;
		var tSrc = this.edit.attr("src");
		if (this.edit.attr("src").indexOf("T#") >= 0) tTable = 1;
		var count = 0;
		var rootNode = this.xmlFiltered.documentElement;
		var rowNodes = rootNode.getElementsByTagName("R");
		rowNodes = rowNodes.length == 0 ? this.currentDic.documentElement: rowNodes;
		var n = 0;
		var rows = this.tbDic.find("tr");
		for (var i = (this.currentPage - 1) * this.pageSize; i < rowNodes.length; i++) {
			n++;
			if (++count > this.pageSize) break;
			var sCode = rowNodes[i].getAttribute("c");
			var sText;
			if (syslanguage == "1") sText = rowNodes[i].getAttribute("t");
			else sText = rowNodes[i].getAttribute("e");
			var row = rows.eq(count);
			row.attr("isLeaf", rowNodes[i].getAttribute("l") ? rowNodes[i].getAttribute("l") : "1");
			$(">td", row).eq(0).html("<span>" + (i + 1) + "</span>");
			$(">td", row).eq(1).html("&nbsp;<span>" + sCode + "</span>");
			$(">td", row).eq(1).attr("title", sCode);
			if (tTable == 1 && sText.indexOf("{") >= 0) {
				var s = sText.split("{");
				$(">td", row).eq(2).attr("title", s[0])
			} else $(">td", row).eq(2).attr("title", sText);
			var offWidth = $(">td", row).eq(2).get(0).offsetWidth;
			var ssText = "";
			if (tTable == 1 && sText.indexOf("{") >= 0) {
				var so = sText.split("{");
				ssText = so[0];
				var s = so[1].split("}")[0];
				var ss = tSrc.split("{")[1];
				ss = ss.split("}")[0];
				var v = s.split(",");
				var vv = ss.split(",");
				for (var ii = 0; ii < v.length; ii++) {
					row.attr(vv[ii], v[ii])
				}
			} else ssText = sText;
			var iWidth = _getWidth(ssText, "9pt");
			if (iWidth > offWidth) {
				var len = Math.round(ssText.length * offWidth / iWidth) - 4;
				if (len != -4) ssText = _text(ssText, len)
			}
			$(">td", row).eq(2).html("&nbsp;<span>" + ssText + "</span>");
			if (row.attr("isLeaf") != "1" && this.edit.attr("leafonly") == 'true') {
				row.css("background-color", "#CCCCCC")
			} else {
				row.css("background-color", "")
			}
		}
		for (; n < this.pageSize; n++) {
			rows.eq(n).css("background-color", "")
		}
		if (count < this.pageSize) {
			for (var i = count + 1; i <= this.pageSize; i++) {
				var row2 = rows.eq(i);
				$(">td", row2).eq(0).text(" ");
				$(">td", row2).eq(1).text(" ");
				$(">td", row2).eq(1).attr("title", "");
				$(">td", row2).eq(2).text(" ");
				$(">td", row2).eq(2).attr("title", "")
			}
		}
		if (this.edit.attr("multi") == 'true' && this.edit.attr("dicstyle") == 'check') {
			var rows = this.tbDic.find("tr");
			for (var i = 0; i < rows.size(); i++) {
				var row = rows.eq(i);
				var firCell = $(">td:first", row);
				firCell.css("color", "black");
				if (i == 0) {
					var allbox = $("<INPUT type='checkbox' onClick='selectMultiDics(this)' id='allCheck'>");
					firCell.html("");
					firCell.append(allbox)
				} else {
					var allbox = $("<INPUT type='checkbox' onClick='selectMultiDics(this)' id='itemCheck' name='itemCheck'>");
					firCell.html("");
					firCell.append(allbox);
					if (this.arrs['c' + $(">td", row).eq(1).attr("title")] == $(">td", row).eq(2).attr("title")) allbox.attr("checked", true)
				}
			}
		} else if (this.edit.attr("multi") == 'true') {
			var rows = this.tbDic.find("tr");
			for (var i = 0; i < rows.size(); i++) {
				var row = rows.eq(i);
				var firCell = $(">td:first", row);
				firCell.css("color", "black");
				row.attr("selected2", "false");
				if (i == 0) {
					firCell.html("×");
					firCell.bind("click",
					function() {
						doClickDicItem($(this))
					})
				} else {
					if (this.arrs['c' + $(">td", row).eq(1).attr("title")] == $(">td", row).eq(2).attr("title")) {
						firCell.text("√");
						row.attr("selected2", "true");
						firCell.css("color", 'red')
					} else firCell.html("×")
				}
			}
		} else {}
	} catch(e) {}
};
new_xDic.prototype.btnOver = function($obj, color) {
	$obj.css("color", color ? color: this.CL_BTN_OVER)
};
new_xDic.prototype.btnOut = function($obj, color) {
	$obj.css("color", color ? color: this.CL_BTN_NORMAL)
};
new_xDic.prototype.free = function() {
	with(this) {
		id = null;
		width = null;
		height = null;
		left = null;
		top = null;
		items = null;
		pageSize = null;
		totalPage = null;
		currentPage = null;
		currentCode = null;
		currentText = null;
		currentValue = null;
		currentDic = null;
		pageInfo = null;
		spellMode = null;
		$("tbody>tr", tbDic).each(function() {
			var $row = $(this);
			$row.unbind("click");
			$row.unbind("mouseover");
			$row.unbind("mouseout")
		});
		tbDic = null;
		pageInfo = null;
		for (var btn in ctrlButton) {
			btn.onclick = null;
			btn.onmouseover = null;
			btn.onmouseout = null;
			btn = null
		}
		ctrlButton = null;
		btnSpellSwitch = null;
		btnFirst = null;
		btnPrev = null;
		btnNext = null;
		btnLast = null;
		object.free();
		object = null;
		currentDic = null;
		xmlFiltered = null;
		xslDic = null;
		for (var d in xmlDics) {
			xmlDics[d] = null
		}
		xmlDics = null;
		xslTree = null;
		docTree = null
	}
};
function createDOMDocument2() {
	try {
		var objXML;
		if (window.ActiveXObject) {
			objXML = new ActiveXObject('Msxml2.DOMDocument');
			objXML.async = false
		} else if (document.implementation && document.implementation.createDocument) {
			objXML = document.implementation.createDocument("", "", null);
			objXML.async = false
		} else {
			return null
		}
		return objXML
	} catch(e) {
		return null
	}
}
function doClickDicItem($row) {
	try {
		var $this = $row;
		var src = window.event.srcElement.parentElement;
		window.event.cancelBubble = true;
		if ($this.get(0).tagName == "TR") {
			g_xDic.currentCode = $(">td", $this).eq(1).attr("title");
			g_xDic.currentText = $(">td", $this).eq(2).attr("title");
			if (g_xDic.edit.attr("leafonly") == "true" && $this.attr("isLeaf") != "1") {
				g_xDic.currentIsLeaf = false;
				return
			} else g_xDic.currentIsLeaf = true;
			if (g_xDic.edit.attr("multi") != 'true') {
				if (g_xDic.currentCode != "") {
					SetContentBase(g_xDic.currentCode, g_xDic.currentText)
				}
				var t = $row.find("td:first").text();
				var selIndex = t ? t - 0 : -1;
				if (afterDicItemClick(g_xDic.edit.attr("id"), $this, selIndex)) nextFocus(g_xDic.edit)
			} else if (g_xDic.edit.attr("multi") == 'true') {
				if (g_xDic.edit.attr("dicstyle") == 'check') {
					var check = $(">td:first", $row).find("input[type='checkbox']:first");
					check.attr("checked", !check.attr("checked"));
					selectMultiDics(check)
				} else {
					if (!$row.attr("selected2") || $row.attr("selected2") == 'false') {
						$row.attr("selected2", 'true');
						$(">td:first", $row).text("√");
						$(">td:first", $row).css("color", "red")
					} else {
						$row.attr("selected2", 'false');
						$(">td:first", $row).text("×");
						$(">td:first", $row).css("color", "black")
					}
					var $rows = $("tr", g_xDic.tbDic);
					if (src.tagName == "TH") {
						for (var i = 1; i < $rows.size(); i++) {
							$rows.eq(i).find(">td:first").text($(">td:first", $row).text());
							$rows.eq(i).find(">td:first").css("color", $row.attr("selected2") == 'true' ? "red": "black");
							$rows.eq(i).attr("selected2", $row.attr("selected2"))
						}
					}
					for (var i = 0; i < $rows.size(); i++) {
						var trow = $rows.eq(i);
						if (trow.attr("selected2") == "true") {
							if (!isEmpty($(">td", trow).eq(1).attr("title"))) {
								g_xDic.arrs['c' + $(">td", trow).eq(1).attr("title")] = $(">td", trow).eq(2).attr("title")
							}
						} else {
							if (!isEmpty($(">td", trow).eq(1).attr("title"))) if (typeof(g_xDic.arrs['c' + $(">td", trow).eq(1).attr("title")]) != 'undefined') {
								g_xDic.arrs['c' + $(">td", trow).eq(1).attr("title")] = null
							}
						}
					}
					g_xDic.setSelectedDics()
				}
			}
		}
	} catch(e) {
		alert(e)
	}
}
function afterDicItemClick(id, $row, selIndex) {
	return true
}
function nextFocus($current) {
	var $next = $current.next();
	if ($next.size() == 0) {
		var n = $current.parent().next();
		if (n.size() == 0) $next = $current.parent().parent().next();
		else $next = n
	}
	while ($next.size() == 0) {
		$next = $current.parent().parent().parent()
	}
	while ($next.size() > 0) {
		if ($next.get(0).tagName == "TABLE") {
			g_xDic.hidden();
			break
		}
		if ($next.get(0).tagName == "DIV") {
			g_xDic.hidden();
			break
		}
		if ($next.get(0).tagName == "BODY") {
			g_xDic.hidden();
			break
		}
		if ($next.get(0).tagName == "TR") {
			if ($("input:not(:hidden)", $next).size() > 0) {
				var $input = $("input:not(:hidden)", $next).eq(0);
				if (!$input.attr("kind") || $input.attr("kind") != "dic") g_xDic.hidden();
				if ($input.is(":visible") == true) {
					$input.focus()
				}
				break
			} else {
				var n = $next.next();
				if (n.size() == 0) $next = $next.parent();
				else $next = n
			}
		} else if ($next.get(0).tagName == "TD") {
			if ($("input:not(:hidden)", $next).size() > 0) {
				var $input = $("input", $next).eq(0);
				if (!$input.attr("kind") || $input.attr("kind") != "dic") g_xDic.hidden();
				if ($input.is(":visible") == true) $input.focus();
				break
			} else if ($("select:not(:hidden)", $next).size() > 0) {
				var $input = $("select", $next).eq(0);
				if (!$input.attr("kind") || $input.attr("kind") != "dic") g_xDic.hidden();
				break
			} else {
				var n = $next.next();
				if (n.size() == 0) $next = $next.parent().next();
				else $next = n;
				if ($next.size() == 0) g_xDic.hidden()
			}
		} else if ($next.get(0).tagName == "INPUT") {
			if (!$next.attr("kind") || $next.attr("kind") != "dic") g_xDic.hidden();
			if ($next.is(":visible") == true) $next.focus();
			break
		} else if ($next.get(0).tagName == "SELECT") {
			if (!$next.attr("kind") || $next.attr("kind") != "dic") g_xDic.hidden();
			break
		} else {
			var n = $next.next();
			if (n.size() == 0) {
				var p = $next.parent();
				if (p.next().size() == 0) while (p.next().size() == 0) {
					if (p.parent().get(0) != null && p.parent().get(0).tagName == "TBODY") {
						break
					} else if (p.parent().get(0) == null) {
						g_xDic.hidden();
						break
					}
					p = p.parent().next()
				} else p = p.next();
				$next = p
			} else $next = n
		}
	}
}
function selectMultiDics(check) {
	if (check.id == 'allCheck') {
		var list = document.all('itemCheck');
		for (var i = 0; i < list.length; i++) {
			list[i].checked = check.checked
		}
	}
	for (var i = 1; i < g_xDic.tbDic.rows.length; i++) {
		var trow = g_xDic.tbDic.rows[i];
		if (trow.cells[0].firstChild.checked) {
			if (!isEmpty(trow.cells[1].title)) g_xDic.arrs['c' + trow.cells[1].title] = trow.cells[2].title
		} else {
			if (!isEmpty(trow.cells[1].title)) if (typeof(g_xDic.arrs['c' + trow.cells[1].title]) != 'undefined') g_xDic.arrs['c' + trow.cells[1].title] = null
		}
	}
	g_xDic.setSelectedDics()
}
function SetContentBase(sCode, sText) {
	if (g_xDic.edit.attr("leafonly") == 'true' && !g_xDic.currentIsLeaf) {
		sCode = "";
		sText = ""
	}
	if (g_xDic.edit.attr("kind").toLowerCase() == "dic" && !g_xDic.edit.get(0).readOnly) {
		g_bCheckChanged = false;
		g_xDic.edit.attr("code", sCode);
		g_xDic.edit.val(sText)
	}
}
new_xDic.prototype.setSelectedDics = function(obj) {
	if (!obj) obj = this.edit;
	if (obj) {
		var codes = '',
		texts = '';
		for (var prop in g_xDic.arrs) {
			if (prop == "removee") continue;
			if (g_xDic.arrs[prop]) {
				if (!codes) {
					codes = prop.substring(1);
					texts = g_xDic.arrs[prop]
				} else {
					codes += "," + prop.substring(1);
					texts += "," + g_xDic.arrs[prop]
				}
			}
		}
		g_bCheckChanged = false;
		obj.val(texts);
		obj.attr("code", codes);
		g_bCheckChanged = false
	}
};
new_xDic.prototype.clear = function() {
	this.arrs = [];
	this.currentCode = '';
	this.currentText = ''
};
new_xDic.prototype.getDic = function(sValue) {
	var iType;
	this.currentValue = sValue;
	var disabledCode = false;
	if (this.disabledCode) disabledCode = true;
	if (this.edit.attr("disabledCode") == "true") disabledCode = true;
	if (sValue == "") {
		iType = DIC_BLANK
	} else if (/^\d*$/.test(sValue)) {
		iType = disabledCode ? DIC_TEXT: DIC_CODE
	} else {
		var sLastChar = sValue.substr(sValue.length - 1);
		switch (sLastChar) {
		case ".":
		case " ":
			iType = DIC_DIRSEL;
			break;
		case "0":
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":
		case "9":
			if (this.edit.attr("src").indexOf("T#") >= 0) iType = DIC_TABLE;
			else if (this.edit.attr("src").indexOf("E#") >= 0) iType = DIC_ENUM;
			else iType = DIC_CODE;
			break;
		default:
			if (this.edit.attr("src").indexOf("T#") >= 0) {
				iType = DIC_TABLE
			} else if (this.edit.attr("src").indexOf("E#") >= 0) iType = DIC_ENUM;
			else iType = /^[A-Za-z]*$/.test(sValue) ? this.spellMode: DIC_TEXT;
			break
		}
	}
	if (this.itype != "") {
		iType = parseInt(this.itype)
	}
	if (iType != DIC_DIRSEL && iType != DIC_SNSEL) {
		this.filter(sValue, iType)
	}
	if (this.items > 0) {
		var $rows = $("tbody>tr", this.tbDic);
		this.currentIsLeaf = $rows.eq(0).attr("isLeaf") != "1" ? false: true;
		if (iType != DIC_SNSEL) {
			this.currentCode = $(">td", $rows.eq(0)).eq(1).attr("title");
			this.currentText = $(">td", $rows.eq(0)).eq(2).attr("title")
		} else {
			var sLastChar = sValue.substr(sValue.length - 1);
			var index = parseInt(sLastChar);
			if (index == 0) index = 10;
			var rows;
			if (bOs == 1) rows = $("thead>tr,tbody>tr", this.tbDic);
			else rows = $("thead>tr,>tr", this.tbDic);
			this.currentCode = $(">td", rows.eq(index)).eq(1).attr("title");
			this.currentText = $(">td", rows.eq(index)).eq(2).attr("title")
		}
	} else {
		this.currentCode = "";
		this.currentText = ""
	}
	return iType
};
new_xDic.prototype.checkMultiDic = function() {
	var tipid = getTip();
	if (tipid == null) {
		return false
	}
	if (!this.edit) return true;
	if (!this.edit.attr("code")) return true;
	var carr = this.edit.attr("code").split(',');
	var tarr = this.edit.val().split(',');
	if (! (carr && tarr && (carr.length == tarr.length))) {
		m_sErrorInfo = "字典值不存在";
		this.edit.attr("m_bFormatError", true);
		if (this.edit.attr("m_bFormatError")) {
			if (m_bFormatError) {
				hideTip(tipid);
				showTip(this.edit, m_sErrorInfo, tipid);
				issele == null ? (issele = this.edit.attr("id")) : "";
				rest = false
			}
		}
		this.edit.attr("m_bFormatError", false)
	}
};
new_xDic.prototype.getMultiDic = function(sValue) {
	var res = false;
	var iType;
	var sLastChar = sValue.substr(sValue.length - 1);
	switch (sLastChar) {
	case " ":
		iType = DIC_DIRSEL;
		res = true;
		break;
	case ",":
		iType = DIC_DIRSEL;
		res = false;
		break;
	case "0":
	case "1":
	case "2":
	case "3":
	case "4":
	case "5":
	case "6":
	case "7":
	case "8":
	case "9":
		iType = this.disabledCode ? DIC_STEXT: DIC_CODE;
		res = false;
		break;
	default:
		if (this.edit.attr("src").indexOf("T#") >= 0) iType = DIC_TABLE;
		else if (this.edit.attr("src").indexOf("E#") >= 0) iType = DIC_ENUM;
		else iType = /^[A-Za-z]*$/.test(sValue) ? this.spellMode: DIC_TEXT;
		res = false;
		break
	}
	if (iType == DIC_DIRSEL) {
		if (!res) {
			if (this.xmlFiltered) this.xmlFiltered = null;
			this.xmlFiltered = createDOMDocument2();
			this.xmlFiltered.async = false;
			try {
				this.xmlFiltered.loadXML(this.currentDic.xml)
			} catch(e) {
				this.xmlFiltered = this.currentDic
			}
			this.filter("", iType)
		}
		return res
	} else if (iType != DIC_STEXT && iType != DIC_TEXT) {
		var val = sValue.substr(sValue.lastIndexOf(",") + 1);
		this.filter(val, iType)
	} else {
		var carr = this.edit.attr("code").split(',');
		var tarr = this.edit.val().split(',');
		if (carr && tarr && carr.length > tarr.length) {
			for (var i = tarr.length; i < carr.length; i++) {
				this.arrs['c' + carr[i]] = null
			}
			var codes = '';
			for (var i = 0; i < tarr.length; i++) {
				if (!codes) {
					codes = carr[i]
				} else {
					codes += "," + carr[i]
				}
			}
			this.edit.attr("code", codes)
		}
		return res
	}
	if (this.items == 1) {
		this.currentIsLeaf = this.tbDic.rows[1].isLeaf != "1" ? false: true;
		if (this.currentIsLeaf) {
			var scode = this.tbDic.rows[1].cells[1].title;
			var stext = this.tbDic.rows[1].cells[2].title;
			this.arrs['c' + scode] = stext;
			if (this.edit.attr("dicstyle") == 'check') {
				var check = this.tbDic.rows[1].cells[0].firstChild;
				check.checked = true
			} else {
				this.tbDic.rows[1].selected2 = 'true';
				this.tbDic.rows[1].cells[0].innerText = "√";
				this.tbDic.rows[1].cells[0].style.color = "red"
			}
			this.setSelectedDics(this.edit)
		}
	}
	return res
};
function getDicByCondition() {
	if ($("#dicQueryEdit").val() != '') {
		g_xDic.xmlDics[g_xDic.edit.attr("src")] = null;
		g_xDic.show(g_xDic.edit, g_xDic.currentCode, g_xDic.currentText)
	} else alert('请输入查询条件')
}
function isEmpty(strInput) {
	if (isBlank(strInput)) {
		return true
	} else {
		if (!strInput) {
			return true
		} else {
			if (strInput == "null") {
				return true
			}
		}
	}
	return false
}
function isBlank(sValue) {
	return /^\s*$/.test(sValue)
}
function doUserFilter(obj) {
	var iType = "";
	var sValue = obj.value;
	if (sValue == "") {
		iType = DIC_BLANK
	} else if (/^\d*$/.test(sValue)) {
		iType = this.disabledCode ? DIC_STEXT: DIC_CODE
	} else {
		var sLastChar = sValue.substr(sValue.length - 1);
		switch (sLastChar) {
		case " ":
			iType = DIC_DIRSEL;
			break;
		case "0":
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":
			break;
		default:
			if (g_xDic.edit.attr("src").indexOf("T#") >= 0) iType = DIC_TABLE;
			else if (g_xDic.edit.attr("src").indexOf("E#") >= 0) iType = DIC_ENUM;
			else iType = /^[A-Za-z]*$/.test(sValue) ? DIC_SPELL: DIC_TEXT;
			break
		}
	}
	g_xDic.filter(obj.value, iType)
}
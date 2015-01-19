var webApps = "/szqdms";
var g_webAppImagePath = webApps + "/images";
var g_sDicPath = webApps + "/dic/";
var g_bCheckChanged = false;
var g_bDicFixed = false;
var g_xDic = null;
var alertWhenNoResult = false;
var showWhenNoResult = true;
var g_iSortIndex = -1;
var bOs = 1;
var debugMod = 1;
$(document).ready(function() {
	doLoadPage()
});
$(window).unload(function() {
	try {
		if (g_xDic) g_xDic.free();
		g_xDic = null;
		g_xTreeData = null;
		validateobjarr = null
	} catch(e) {}
});
function doLoadPage() {
	try {
		bOs = getOs();
		createTip();
		addListener();
		createExpandDiv();
		doInit();
		setStyle()
	} catch(e) {}
}
var validateConfig = {
	divCount: 1,
	isOnBlur: true,
	timeOut: 1500
};
var validateobjarr = new Array();
var tipid = "checkMsgDiv";
var divId = "temp";
function createTip() {
	var count = validateConfig.divCount;
	for (var n = 0; n < count; n++) {
		createTipDiv(tipid + "" + n);
		validateobjarr.push(tipid + "" + n)
	}
}
function createTipDiv(did) {
	var tip = $("<div id='" + did + "' style='display:none;position:relative;z-index:9999;' class='tipdiv'></div>");
	var nt = "";
	nt += "  <table width=\"120\" height=\"28\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  id=\"checkMsgTable\">";
	nt += "    <tr> ";
	nt += "      <td  valign=\"bottom\"><img src='" + webApps + "/lib/plugins/validate/alert_top.gif' width=\"120\" height=\"6\"></td>";
	nt += "    </tr>";
	nt += "    <tr> ";
	nt += "      <td  valign=\"top\">";
	nt += "          <table width=\"120\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  id=\"checkMsgTable\" style=\"font:9pt 微软雅黑;\" >";
	nt += "            <tr> ";
	nt += "                <td width=\"136\" valign=\"top\" id=\"" + did + "_msg\" background=" + webApps + "/lib/plugins/validate/alert_middle.gif  align=\"center\" style=\"font:9pt 微软雅黑;\">test </td>";
	nt += "            </tr>";
	nt += "          </table>";
	nt += "      </td>";
	nt += "    </tr>";
	nt += "    <tr>";
	nt += "      <td height=\"10\" valign=\"top\"><img src='" + webApps + "/lib/plugins/validate/alert_bottom.gif' width=\"120\" ></td>";
	nt += "    </tr>";
	nt += "  </table>";
	tip.html(nt);
	$(document.body).append(tip)
}
function createExpandDiv() {
	var newdiv = $("<DIV style='display:none;position:absolute;background:silver;border:0px solid black'></DIV>");
	newdiv.attr("id", "expandDiv");
	var newtextarea = $("<TEXTAREA nodblclick='true' style='background:silver;'></TEXTAREA>");
	newtextarea.attr("id", "expandTextarea");
	newtextarea.attr("overflow", "auto");
	newdiv.append(newtextarea);
	$(document.body).append(newdiv);
	$("#expandTextarea").bind("blur",
	function() {
		shrink_textarea($(this), $(this).attr("pID"))
	})
}
function setStyle(sObj, type) {
	var $d = null;
	if (sObj && typeof(sObj) == "object") {
		$d = sObj
	} else if (sObj) $d = $("#" + sObj);
	if ($d) {
		$("input,select,textarea", $d).each(function(index, element) {
			var $this = $(this);
			if ($this.val()) $this.attr("title", $this.val());
			var dataType = $this.attr("datatype");
			var must_s = false;
			if (type != "2") {
				$this.bind("dblclick",
				function(event) {
					if ($this.is(":hidden")) return true;
					if ($this.attr("kind")) return true;
					if ($this.get(0).tagName == "SELECT") return true;
					if ($this.hasClass("Wdate")) return true;
					if ($this.attr("type") && ($this.attr("type") == "checkbox" || $this.attr("type") == "radio")) return true;
					if ($this.attr("expand") && $this.attr("expand") == "false") return true;
					expand_textarea($this)
				})
			}
			if (dataType) {
				var arr = dataType.split(",");
				if (arr && arr.length > 0) {
					if (arr[0] == 0) must_s = true;
					if (arr[2]) {
						var maxLen = arr[2];
						if (!$this.attr("kind")) $this.attr("maxlength", maxLen)
					}
				}
				if (must_s == true) {
					var jSpan;
					if ($this.get(0).tagName == "INPUT") {
						if (type && type == "2") jSpan = $("<span style='font-size:12pt;color:red;width:10px; padding-left:2px;height:18px'>*</span>");
						else jSpan = $("<span style='position:relative;float:left;font-size:12pt;color:red;width:10px; left:-45px;height:18px'>*</span>")
					} else jSpan = $("<span style='font-size:12pt;color:red;width:10px; padding-left:2px;height:18px'>*</span>");
					$this.after(jSpan)
				}
			}
			if ($this.attr("kind") && $this.attr("kind") == "dic") {
				$this.bind("focus",
				function(event) {
					g_bCheckChanged = true;
					if (!g_xDic) {
						g_xDic = new new_xDic();
						g_xDic.object.size()
					}
					g_xDic.show($this, $this.attr("code"), $this.val())
				});
				if ($this.get(0).tagName == "SELECT") {
					$this.combox()
				} else $this.bind("keyup",
				function() {
					doPropChange($(this))
				})
			} else {
				$this.bind("focus",
				function(event) {
					if ($this.attr("id") == "dicFilterEdit") return;
					if (!$this.attr("kind") || $this.attr("kind") != "dic") try {
						if (g_xDic != null) g_xDic.hidden()
					} catch(e) {}
				})
			}
			if ($this.attr("hasBtn") && $this.attr("hasBtn") == "true") {
				var $div = $("<img></img>");
				$div.attr("src", g_webAppImagePath + "/default/search.png");
				$div.width(15);
				$div.height(15);
				$div.attr("title", "点击按钮");
				var $span;
				if (type && type == "2") $span = $("<span style='margin-left:-45px;'></span>");
				else $span = $("<span style='position:relative;float:left;left:-45px;'></span>");
				$span.append($div);
				if ($this.attr("callFunction")) {
					$span.bind("click",
					function() {
						eval($this.attr("callFunction"))
					})
				}
				$this.after($span)
			}
		});
		addListener($d)
	} else {
		$("input,select,textarea").each(function(index, element) {
			var $this = $(this);
			if ($this.val()) $this.attr("title", $this.val());
			var dataType = $this.attr("datatype");
			var must_s = false;
			$this.bind("dblclick",
			function(event) {
				if ($this.is(":hidden")) return true;
				if ($this.attr("kind")) return true;
				if ($this.get(0).tagName == "SELECT") return true;
				if ($this.hasClass("Wdate")) return true;
				if ($this.attr("expand") && $this.attr("expand") == "false") return true;
				expand_textarea($this)
			});
			if (dataType) {
				var arr = dataType.split(",");
				if (arr && arr.length > 0) {
					if (arr[0] == 0) must_s = true;
					if (arr[2]) {
						var maxLen = arr[2];
						$this.attr("maxlength", maxLen)
					}
				}
				if (must_s == true) {
					var jSpan;
					if ($this.get(0).tagName == "INPUT") {
						jSpan = $("<span style='position:relative;float:left;font-size:12pt;color:red;width:10px; left:-45px;height:18px'>*</span>")
					} else jSpan = $("<span style='font-size:12pt;color:red;width:10px; padding-left:2px;height:18px'>*</span>");
					$this.after(jSpan)
				}
			}
			if ($this.attr("kind") && $this.attr("kind") == "dic") {
				$this.bind("focus", {},
				function(event) {
					g_bCheckChanged = true;
					if (!g_xDic) {
						g_xDic = new new_xDic();
						g_xDic.object.size()
					}
					g_xDic.show($this, $this.attr("code"), $this.val())
				});
				if ($this.get(0).tagName == "SELECT") {
					$this.combox()
				} else $this.bind("keyup",
				function() {
					doPropChange($(this))
				})
			} else {
				$this.bind("focus",
				function(event) {
					if ($this.attr("id") == "dicFilterEdit") return;
					if (!$this.attr("kind") || $this.attr("kind") != "dic") {
						if (g_xDic != null) g_xDic.hidden()
					}
				})
			}
			if ($this.attr("hasBtn") && $this.attr("hasBtn") == "true") {
				var $div = $("<img></img>");
				$div.attr("src", g_webAppImagePath + "/default/search.png");
				$div.width(15);
				$div.height(15);
				$div.attr("title", "点击按钮");
				var $span = $("<span style='margin-left:-50px;'></span>");
				$span.append($div);
				if ($this.attr("callFunction")) {
					$span.bind("click",
					function() {
						eval($this.attr("callFunction"))
					})
				}
				$this.after($span)
			}
		})
	}
}
function setBtnStyle(arrInputs) {
	if (arrInputs && typeof(arrInputs) == "object" && arrInputs.length > 0) {
		for (var n = 0; n < arrInputs.length; n++) {
			var imgObj = document.createElement("IMG");
			imgObj.src = g_webAppImagePath + "/default/btn.gif";
			imgObj.id = "srtImg";
			imgObj.width = 15;
			imgObj.height = 15;
			imgObj.title = "点击按钮";
			var btn = document.createElement("SPAN");
			btn.appendChild(imgObj);
			if (arrInputs[n].callFunction) {
				btn.onclick = new Function(arrInputs[n].callFunction)
			}
			arrInputs[n].parentElement.insertBefore(btn, arrInputs[n].nextSibling)
		}
	} else {
		var inputs = document.getElementsByTagName("INPUT");
		if (inputs && inputs.length) {
			for (var i = 0; i < inputs.length; i++) {
				if (inputs[i].type != "hidden" && inputs[i].hasBtn == "true") {
					var imgObj = document.createElement("IMG");
					imgObj.src = g_webAppImagePath + "/default/btn.gif";
					imgObj.id = "srtImg";
					imgObj.width = 15;
					imgObj.height = 15;
					imgObj.title = "点击按钮";
					var btn = document.createElement("SPAN");
					btn.appendChild(imgObj);
					if (inputs[i].callFunction) {
						btn.onclick = new Function(inputs[i].callFunction)
					}
					inputs[i].parentElement.insertBefore(btn, inputs[i].nextSibling)
				}
			}
		}
	}
}
function setFixArea() {
	var div = document.createElement("DIV");
	div.id = "fixarea";
	div.name = "fixarea";
	div.style.display = "none";
	document.body.appendChild(div);
	var inputCond = document.createElement("INPUT");
	inputCond.id = "querycondition";
	inputCond.name = "querycondition";
	var inputAction = document.createElement("INPUT");
	inputAction.id = "queryaction";
	inputAction.name = "queryaction";
	div.appendChild(inputCond);
	div.appendChild(inputAction)
}
function doPropChange($input) {
	var m_kind = $input.attr("kind");
	var tree = $input.attr("tree");
	if (tree == true) return;
	if (m_kind == "dic" && g_xDic && g_bCheckChanged) {
		if (g_bCheckChanged && g_xDic) {
			g_bCheckChanged = false;
			var ret;
			if ($input.attr("multi") == "true") {
				if ($input.val() == "") {
					$input.attr("code", "");
					g_xDic.clear()
				} else if (g_xDic.getMultiDic($input.val())) {
					if (isEmpty($input.val().substr(0, $input.val().length - 1))) {
						$input.attr("code", "");
						$input.val("")
					} else {
						$input.val($input.val().substr(0, $input.val().length - 1))
					}
					g_xDic.clear();
					nextFocus()
				}
			} else {
				ret = g_xDic.getDic($input.val());
				switch (ret) {
				case DIC_SNSEL:
				case DIC_DIRSEL:
					var t = $input.val().length || 0;
					if (g_xDic.currentCode.length == t || g_xDic.currentText.length == t) SetContentBase(g_xDic.currentCode, g_xDic.currentText);
					break;
				case DIC_CODE:
					if (g_xDic.items > 0) {
						if (g_xDic.items == 1) {
							var t = $input.val().length || 0;
							if (g_xDic.currentCode.length == t || g_xDic.currentText.length == t) SetContentBase(g_xDic.currentCode, g_xDic.currentText)
						} else {
							$input.attr("code", "")
						}
					} else {
						$input.attr("code", "")
					}
					break;
				default:
					if (g_xDic.items > 0) {
						if (g_xDic.items == 1) {
							var t = $input.val().length || 0;
							if (g_xDic.currentCode.length == t || g_xDic.currentText.length == t) SetContentBase(g_xDic.currentCode, g_xDic.currentText)
						} else {
							$input.attr("code", "")
						}
						this.code = ""
					}
					break
				}
			}
			g_bCheckChanged = true
		} else {
			g_bCheckChanged = true
		}
	}
}
function doClosePage() {
	try {
		doClose()
	} catch(e) {}
}
function doInit() {}
function doClose() {}
function defaultCallBackEdit(res) {
	return true
}
function doPost(sUrl, eventUrlId, data, callback, dataType) {
	try {
		if (!sUrl) {
			alertMsg.error("invalid post path！");
			return false
		}
		var sData;
		var dType, re = true;
		if (dataType == "undefined" || dataType == undefined) dataType = "1";
		switch (dataType) {
		case "1":
			dType = "xml";
			break;
		case "2":
			dType = "json";
			break;
		default:
			dType = "xml";
			break
		}
		var sEventUrlId = "";
		if (eventUrlId) sEventUrlId = eventUrlId;
		if (sEventUrlId && $("#" + sEventUrlId).size() > 0) {
			$("#" + sEventUrlId).attr("disabled", "disabled")
		}
		if (!data) sData = {
			"data": ""
		};
		else sData = {
			"data": data
		};
		var $callback = callback || defaultCallBackEdit;
		if (!$.isFunction($callback)) {
			$callback = eval('(' + callback + ')')
		}
		$.ajax({
			type: 'POST',
			url: sUrl,
			dataType: dType,
			data: sData,
			cache: false,
			success: function(responseText) {
				try {
					if (!responseText) {
						alertMsg.error("No return response results!");
						return false
					}
					if (showResult(responseText, dType)) {
						re = true;
						if ($callback(responseText, sData)) {
							if (sEventUrlId && $("#" + sEventUrlId).size() > 0) {
								$("#" + sEventUrlId).attr("disabled", false)
							}
						}
					}
					return re
				} catch(ee) {
					if (debugMod) alertMsg.error(ee);
					return false
				}
			},
			error: DWZ.ajaxError
		})
	} catch(e) {
		if (debugMod) alertMsg.error(e);
		return false
	}
}
function cancelPostEvent(event) {
	return false
}
function doPostEvent(event) {
	try {
		var sUrl = event.data.a2;
		var eventUrlId = event.data.a3;
		var data = event.data.a4;
		var callback = event.data.a5;
		var dType = event.data.a6;
		if (!dType) dType = "xml";
		if (!sUrl) {
			alertMsg.error("Invalid post path！");
			return false
		}
		var sData;
		var sEventUrlId = "",
		re = true;
		if (eventUrlId) sEventUrlId = eventUrlId;
		if (sEventUrlId && $("#" + sEventUrlId).size() > 0) {
			$("#" + sEventUrlId).attr("disabled", "disabled")
		}
		if (!data) sData = {
			"data": ""
		};
		else sData = {
			"data": data
		};
		var $callback = callback || defaultCallBackEdit;
		if (!$.isFunction($callback)) $callback = eval('(' + callback + ')');
		$.ajax({
			type: 'POST',
			url: sUrl,
			dataType: dType,
			data: sData,
			cache: false,
			success: function(responseText) {
				try {
					if (!responseText) {
						alertMsg.error("No return response results!");
						return false
					}
					if (showResult(responseText, dType)) {
						if ($callback(responseText, sData)) {
							if (sEventUrlId && $("#" + sEventUrlId)) {
								$("#" + sEventUrlId).attr("disabled", false)
							}
						}
						re = true
					}
					return re
				} catch(ee) {
					if (debugMod == 1) alertMsg.error(ee);
					return false
				}
			},
			error: DWZ.ajaxError
		})
	} catch(e) {
		if (debugMod == 1) alertMsg.error(e);
		return false
	}
}
function sendAjax(sUrl, eventUrlId, data, callback, dataType) {
	if (!sUrl) {
		alertMsg.error("Invalid post path！");
		return false
	}
	var sData;
	var dType, re = true;
	if (dataType == "undefined" || dataType == undefined) dataType = "1";
	switch (dataType) {
	case "1":
		dType = "xml";
		break;
	case "2":
		dType = "json";
		break;
	default:
		dType = "xml";
		break
	}
	var sEventUrlId = "";
	if (eventUrlId) sEventUrlId = eventUrlId;
	if (sEventUrlId && $("#" + sEventUrlId).size() > 0) {
		$("#" + sEventUrlId).attr("disabled", "disabled")
	}
	if (!data) sData = {
		"data": ""
	};
	else sData = {
		"data": data
	};
	var $callback = callback || defaultCallBackEdit;
	if (!$.isFunction($callback)) {
		$callback = eval('(' + callback + ')')
	}
	$.ajax({
		type: 'POST',
		url: sUrl,
		dataType: dType,
		data: sData,
		cache: false,
		success: function(responseText) {
			try {
				if (!responseText) {
					alert("No return response results!");
					return false
				}
				var xmlDom = responseText.documentElement;
				var msg = xmlDom.childNodes[0].nodeName;
				var msgVal = getNodeText(xmlDom.childNodes[0]);
				if (msg == "ERRMESSAGE") {
					if (msg != null && msgVal != null) {
						alert(msgVal);
						xmlDom = null;
						re = false
					}
				} else if (msg == "MESSAGE") {
					if (msg != null && msgVal != null) {
						alert(msgVal);
						xmlDom = null
					}
				}
				$callback(responseText, sData);
				return re
			} catch(ee) {
				alert(ee);
				return false
			}
		},
		error: function(res) {
			alert("Error operation!");
			return false
		}
	})
}
function sendPost(sUrl, eventUrlId, data, callback, isConfirm, dType) {
	var sIsConfirm = isConfirm ? isConfirm: "false";
	sUrl = encodeURI(sUrl);
	if (sIsConfirm && sIsConfirm == "true") {
		var op = {
			okCall: null,
			cancelCall: null
		};
		$.extend(op, {
			okCall: doPostEvent,
			cancelCall: cancelPostEvent
		});
		var confirmText;
		if ($("#" + eventUrlId).attr("confirm")) confirmText = $("#" + eventUrlId).attr("confirm");
		else {
			if (syslanguage == "1") {
				confirmText = $("#" + eventUrlId).text() ? $("#" + eventUrlId).text() : "操作";
				confirmText = "是否确认 " + confirmText + "?"
			} else {
				confirmText = $("#" + eventUrlId).text() ? $("#" + eventUrlId).text() : "operating";
				confirmText = "If confirm " + confirmText + " ?"
			}
		}
		alertMsg.confirm(confirmText, op, $.extend({},
		{
			a2: sUrl,
			a3: eventUrlId,
			a4: data,
			a5: callback,
			a6: dType
		}))
	} else doPost(sUrl, eventUrlId, data, callback, dType)
}
function sendFormPost($f, sUrl, eventUrlId, data, callback) {
	try {
		if (!$f) {
			alertMsg.error("Invalid form!");
			return false
		};
		if (!sUrl) {
			alertMsg.error("Invalid post path！");
			return false
		}
		var sData;
		var sEventUrlId = "";
		if (eventUrlId) sEventUrlId = eventUrlId;
		if (sEventUrlId && $("#" + sEventUrlId).size() > 0) {}
		if (!data) sData = {
			"data": ""
		};
		else sData = {
			"data": data
		};
		var $callback = callback || defaultCallBackEdit;
		if (!$.isFunction($callback)) {
			$callback = eval('(' + callback + ')')
		}
		$.ajax({
			type: $f.attr("method") || 'POST',
			url: sUrl,
			dataType: "xml",
			data: sData,
			cache: false,
			success: function(responseText) {
				try {
					xmlDom = responseText.documentElement;
					if (!responseText) {
						alertMsg.error("Did not return the response results!");
						return false
					}
					var re = true;
					var msg = xmlDom.childNodes[0].nodeName;
					var msgVal = getNodeText(xmlDom.childNodes[0]);
					if (msg == "ERRMESSAGE") {
						if (msg != null && msgVal != null) {
							alertMsg.error(msgVal);
							xmlDom = null;
							re = false;
							return false
						}
					} else if (msg == "MESSAGE") {
						if (msg != null && msgVal != null) {
							alertMsg.correct(msgVal);
							xmlDom = null
						}
					}
					re = true;
					if ($callback(xmlDom)) {
						if (sEventUrlId && $("#" + sEventUrlId).size() > 0) {
							$("#" + sEventUrlId).attr("disabled", false)
						}
					}
					return re
				} catch(ee) {
					alertMsg.error(ee);
					return false
				}
			},
			error: DWZ.ajaxError
		})
	} catch(e) {
		alertMsg.error(e);
		return false
	}
}
function sendFormPostEvent(event) {
	try {
		var $f = event.data.a1;
		var sUrl = event.data.a2;
		var eventUrlId = event.data.a3;
		var data = event.data.a4;
		var callback = event.data.a5;
		if (!$f) {
			alertMsg.error("Invalid form!");
			return false
		};
		if (!sUrl) {
			alertMsg.error("Invalid post path！");
			return false
		}
		var sData;
		var sEventUrlId = "";
		if (eventUrlId) sEventUrlId = eventUrlId;
		if (sEventUrlId && $("#" + sEventUrlId).size() > 0) {
			$("#" + sEventUrlId).attr("disabled", "disabled")
		}
		if (!data) sData = {
			"data": ""
		};
		else sData = {
			"data": data
		};
		var $callback = callback || defaultCallBackEdit;
		if (!$.isFunction($callback)) $callback = eval('(' + callback + ')');
		$.ajax({
			type: $f.attr("method") || 'POST',
			url: sUrl,
			dataType: "xml",
			data: sData,
			cache: false,
			success: function(responseText) {
				try {
					var xmlDom = responseText.documentElement;
					if (!responseText) {
						alertMsg.error("Did not return the response results!");
						return false
					}
					var re = true;
					var errMessage = xmlDom.getElementsByTagName("ERRMESSAGE")[0];
					var ms;
					if (errMessage != null) {
						if (bOs == 1) ms = errMessage.text ? errMessage.text: errMessage.textContent;
						else ms = errMessage.textContent;
						alertMsg.error(ms);
						if (sEventUrlId && $("#" + sEventUrlId).size() > 0) {
							$("#" + sEventUrlId).attr("disabled", false)
						}
						re = false;
						xmlDom = null;
						return re
					}
					var message = xmlDom.getElementsByTagName("MESSAGE")[0];
					var ms;
					if (message != null) {
						if (bOs == 1) ms = message.text ? message.text: message.textContent;
						else ms = message.textContent;
						alertMsg.correct(ms);
						re = true
					}
					if ($callback(xmlDom)) {
						if (sEventUrlId && $("#" + sEventUrlId)) {
							$("#" + sEventUrlId).attr("disabled", false)
						}
					}
					xmlDom = null;
					return re
				} catch(ee) {
					alertMsg.error(ee);
					return false
				}
			},
			error: DWZ.ajaxError
		})
	} catch(e) {
		alertMsg.error(e);
		return false
	}
}
function doNormalSubmit($f, sUrl, eventUrlId, data, callback, isConfirm) {
	var sIsConfirm = isConfirm ? isConfirm: "true";
	$f.attr("method", "POST");
	if (sIsConfirm && sIsConfirm == "true") {
		if (eventUrlId) {
			var op = {
				okCall: null,
				cancelCall: null
			};
			$.extend(op, {
				okCall: sendFormPostEvent,
				cancelCall: cancelPostEvent
			});
			var confirmText;
			if ($("#" + eventUrlId).attr("confirm")) confirmText = $("#" + eventUrlId).attr("confirm");
			else {
				if (syslanguage == "1") {
					confirmText = $("#" + eventUrlId).text() ? $("#" + eventUrlId).text() : "操作";
					confirmText = "是否确认 " + confirmText + "?"
				} else {
					confirmText = $("#" + eventUrlId).text() ? $("#" + eventUrlId).text() : "operating";
					confirmText = "If confirm " + confirmText + " ?"
				}
			}
			alertMsg.confirm(confirmText, op, $.extend({},
			{
				a1: $f,
				a2: sUrl,
				a3: eventUrlId,
				a4: data,
				a5: callback
			}))
		}
	} else sendFormPost($f, sUrl, eventUrlId, data, callback)
}
function doFormSubmit($f, sUrl, eventUrlId, curPage, param, tabId, dataType) {
	try {
		if (!$f) {
			alertMsg.error("Invalid form!");
			return false
		};
		$f.attr("method", "POST");
		if (!sUrl) {
			alertMsg.error("Invalid post path！");
			return false
		}
		if (submitForm($f) == false) return false;
		var sParam;
		var sEventUrlId = "";
		if (eventUrlId) sEventUrlId = eventUrlId;
		if (sEventUrlId && $("#" + sEventUrlId).size() > 0) {
			$("#" + sEventUrlId).attr("disabled", "disabled")
		}
		if (!param) sParam = {
			"data": ""
		};
		else sParam = {
			"data": param
		};
		if (tabId) {
			var page_tab = $("#" + $("#" + tabId).attr("ref"));
			page_tab.attr("queryAction", sUrl);
			page_tab.attr("queryCondition", param)
		}
		var dType;
		if (dataType == "undefined" || dataType == undefined) dataType = "1";
		switch (dataType) {
		case "1":
			dType = "xml";
			break;
		case "2":
			dType = "json";
			break;
		default:
			dType = "xml";
			break
		}
		$.ajax({
			type: $f.attr("method") || 'POST',
			url: sUrl,
			dataType: dType,
			data: sParam,
			cache: false,
			success: function(responseText) {
				if (defaultCallbackSearch(responseText, tabId, dType)) {
					if (sEventUrlId && $("#" + sEventUrlId).size() > 0) {
						$("#" + sEventUrlId).attr("disabled", false)
					}
					try {
						callbackSearch(responseText, tabId)
					} catch(e) {}
				}
			},
			error: DWZ.ajaxError
		});
		return true
	} catch(e) {
		alertMsg.error(e);
		return false
	}
}
function defaultCallbackSearch(responseText, tabId, dType) {
	try {
		if (!responseText) {
			alertMsg.error("Did not return the response results!");
			return false
		}
		if (showResult(responseText, dType)) {
			if (tabId) return tabListShowResult(responseText, tabId, dType);
			else {
				responseText = null;
				return true
			}
		} else {
			responseText = null;
			return false
		}
	} catch(e) {
		alertMsg.error(e);
		return false
	}
}
function createDOMDocument() {
	try {
		var objXML;
		if (window.ActiveXObject) {
			objXML = new ActiveXObject('Microsoft.XMLDOM');
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
function createXmlHttp() {
	var XMLHttp = null;
	if (window.XMLHttpRequest) {
		XMLHttp = new XMLHttpRequest()
	} else if (window.ActiveXObject) {
		XMLHttp = new ActiveXObject("Msxml2.XMLHTTP")
	}
	return XMLHttp
}
function loadFromStr(str) {
	if (!str) return null;
	try {
		var oParser = new DOMParser();
		return oParser.parseFromString(str, "text/xml")
	} catch(e) {
		return null
	}
}
function domToStr(xmlDom) {
	try {
		if (bOs == 1) {
			var s = xmlDom.xml;
			if (!s) s = new XMLSerializer().serializeToString(xmlDom);
			return s
		} else return new XMLSerializer().serializeToString(xmlDom)
	} catch(e) {
		return ""
	}
}
function getOs() {
	var ua = navigator.userAgent.toLowerCase();
	if (ua.indexOf("msie") > 0 && ua.indexOf("compatible") > -1) return 1;
	if (ua.indexOf("firefox") > 0) return 2;
	if (ua.indexOf("safari") > 0 && ua.indexOf("chrome") < 1) return 3;
	if (ua.indexOf("camino") > 0) return 4;
	if (ua.indexOf("gecko/") > 0) return 5;
	if (ua.indexOf("chrome") > 0) return 6;
	if (ua.indexOf("ipad") > 0) return 11;
	if (ua.indexOf("iphone os") > 0) return 12;
	if (ua.indexOf("midp") > 0) return 13;
	if (ua.indexOf("rv:1.2.3.4") > 0) return 14;
	if (ua.indexOf("ucweb") > 0) return 15;
	if (ua.indexOf("android") > 0) return 16;
	if (ua.indexOf("windows ce") > 0) return 17;
	if (ua.indexOf("windows mobile") > 0) return 18;
	return 1
}
function showResult(resText, dType) {
	var re = true;
	var needfree = false;
	switch (dType) {
	case "xml":
		var xmlDom = resText.documentElement;
		var errMessage = xmlDom.getElementsByTagName("ERRMESSAGE")[0];
		var ms;
		if (errMessage != null) {
			ms = getNodeText(errMessage);
			alertMsg.error(ms);
			re = false;
			xmlDom = null;
			return re
		}
		var message = xmlDom.getElementsByTagName("MESSAGE")[0];
		var ms;
		if (message != null) {
			ms = getNodeText(message);
			alertMsg.correct(ms);
			re = true;
			xmlDom = null;
			return re
		}
		break;
	case "json":
		var res = resText;
		var errMessage = res.RESPONSE.ERRMESSAGE;
		if (errMessage != null && errMessage.text != null) {
			alertMsg.error(errMessage.text);
			if (needfree) res = null;
			re = false
		}
		var message = res.RESPONSE.MESSAGE;
		if (message != null && message.text != null) {
			alertMsg.correct(message.text);
			if (needfree) res = null
		}
		re = true;
		break
	}
	return re
}
function tabListShowResult(responseText, tabId, dType) {
	var tabList = $("#" + tabId);
	var tabId = tabList.attr("id");
	var refId = tabList.attr("ref");
	var layoutH = tabList.attr("layoutH");
	var refQuery = tabList.attr("refQuery");
	var total = tabList.attr("total");
	var shownum = tabList.attr("shownum");
	var isEditTab = tabList.attr("edit") || "false";
	var showpage = tabList.attr("showpage") || "true";
	var pageRows = tabList.attr("pageRows") || 10;
	var limitH = tabList.attr("limitH") || "true";
	var multivalsId = tabList.attr("multivals") || "";
	if (shownum == undefined) shownum = true;
	if (!total) total = false;
	var s;
	if (tabList.attr("init") && tabList.attr("init") != "undefined") {
		s = tabList.attr("init")
	} else {
		s = $("thead", tabList).html() + ""
	}
	var refer = tabList.attr("refer") ? true: false;
	$("#" + refId).html("");
	$("#" + refId).html("<table style='width:100%' init='" + s + "' class='normal' multivals='" + multivalsId + "' limitH='" + limitH + "' showpage='" + showpage + "' edit='" + isEditTab + "' shownum='" + shownum + "'total='" + total + "' layoutH='" + layoutH + "' refer='" + refer + "' refQuery='" + refQuery + "' pageRows='" + pageRows + "' ref='" + refId + "' id='" + tabId + "'><thead>" + s + "</thead></table>");
	tabList = $("#" + tabId);
	switch (dType) {
	case "xml":
		var rows = responseText.getElementsByTagName("ROW");
		if (!rows || rows.length == 0) {
			if (alertWhenNoResult) {
				alertMsg.info($.info.showNoResult())
			}
		}
		tabList.listResult(rows, dType);
		var nrecordsperpage = 0,
		ncurrentpagenum = 0,
		ntotalpagenum = 0,
		ncountrows = 0;
		var result = responseText.getElementsByTagName("RESULT")[0];
		if (result.getAttribute("recordsperpage")) nrecordsperpage = result.getAttribute("recordsperpage");
		if (result.getAttribute("currentpagenum")) ncurrentpagenum = result.getAttribute("currentpagenum");
		else if (!ncurrentpagenum) ncurrentpagenum = '1';
		if (result.getAttribute("totalpage")) ntotalpagenum = result.getAttribute("totalpage");
		else if (!ntotalpagenum) ntotalpagenum = '0';
		if (result.getAttribute("countrows")) ncountrows = result.getAttribute("countrows");
		else if (!ncountrows) ncountrows = '0';
		if (nrecordsperpage <= 100) {
			tabList.jTable();
			$("#" + tabId).width($("#" + tabId + "_content").width())
		} else {
			var lh = 250;
			var rowssize = $("tr", tabList).size();
			if (tabList.attr("layoutH") && tabList.attr("layoutH") != "undefined") {
				lh = tabList.attr("layoutH");
				if (rowssize * tabList.tabOptions.lineH < lh) {
					lh = rowssize * tabList.tabOptions.lineH
				}
			} else lh = rowssize * tabList.tabOptions.lineH;
			var clientHeight = document.documentElement.clientHeight;
			if ($("#" + tabId).attr("limitH") == "true" && $("#" + tabId).offset().top + parseInt(lh) + 70 > clientHeight) {
				lh = clientHeight - $("#" + tabId).offset().top - 70
			}
			tabList.addClass("dlist");
			tabList.parent().height(lh);
			tabList.parent().css("overflow-y", "auto")
		}
		if (ntotalpagenum > 1 || $("#pageinfo_" + tabId).is(":visible")) if (showpage == "true") tabList.setPageInfo(ncurrentpagenum, ntotalpagenum, nrecordsperpage, ncountrows, dType);
		break;
	case "json":
		var rows = responseText.RESPONSE.RESULT;
		if (!rows || rows.length == 0) {
			if (alertWhenNoResult) alertMsg.info($.info.showNoResult())
		}
		tabList.listResult(rows, dType);
		tabList.jTable();
		tabList.layoutH(tabList.parent());
		var nrecordsperpage = 0,
		ncurrentpagenum = 0,
		ntotalpagenum = 0,
		ncountrows = 0;
		if (responseText.RESPONSE.recordsperpage != undefined) nrecordsperpage = responseText.RESPONSE.recordsperpage;
		if (responseText.RESPONSE.currentpagenum != undefined) ncurrentpagenum = responseText.RESPONSE.currentpage;
		else if (!ncurrentpagenum) ncurrentpagenum = '1';
		if (responseText.RESPONSE.totalpage != undefined) ntotalpagenum = responseText.RESPONSE.totalpage;
		else if (!ntotalpagenum) ntotalpagenum = '0';
		if (responseText.RESPONSE.countrows != undefined) ncountrows = responseText.RESPONSE.countrows;
		else if (!ncountrows) ncountrows = '0';
		if (ntotalpagenum > 1 || $("#pageinfo_" + tabId).is(":visible")) tabList.setPageInfo(ncurrentpagenum, ntotalpagenum, nrecordsperpage, ncountrows, dType);
		break
	}
	var $multivalsArea;
	if (multivalsId) $multivalsArea = $("#" + multivalsId);
	var $p = ($multivalsArea && $multivalsArea.size() > 0) ? $multivalsArea: $(document);
	if ($("[name='multivals']", $p).size() > 0) {
		var column;
		if ($("[name='multivals']", $p).eq(0).attr("column")) column = $("[name='multivals']", $p).eq(0).attr("column");
		else column = 0;
		if ($("[name='multivals']", $p).eq(0).val()) {
			try {
				var mV = $("[name='multivals']", $p).eq(0).val();
				var mVList = mV.split(",");
				$("tr", $("#" + tabId + "_content")).each(function() {
					var $row = $(this);
					var checkbox = $("td", $row).eq(column).find("input[type='checkbox']:first");
					if (checkbox.size() > 0 && checkbox.is(":checked") == false) {
						for (var i = 0; i < mVList.length; i++) {
							if (checkbox.val() == mVList[i]) {
								checkbox.attr("checked", true);
								try {
									customOtherValue($row, checkbox.val())
								} catch(e) {}
								break
							}
						}
					}
				})
			} catch(e) {}
		}
	}
	xmlDom = null;
	return true
}
function doTabListCheck(obj) {
	var type = obj.type;
	if (type && type == 'checkbox') {
		$("#" + $(obj).attr("tableid") + "_content").find("tr>td").each(function() {
			var $this = $(this);
			if (obj.checked == true) {
				$("input[type='checkbox']", $this).each(function() {
					var check = $(this);
					check.attr("checked", true);
					try {
						doCheckbox(check.get(0))
					} catch(e) {}
				})
			} else {
				var check = $("input[type='checkbox']", $this);
				check.attr("checked", false);
				try {
					doCheckbox(check.get(0))
				} catch(e) {}
			}
		})
	} else if (type = 'radio') {
		obj.checked = true;
		$("tr>td:first-child input[type='radio']", $("#" + obj.tableId + "_content")).each(function() {
			var check = $(this);
			check.attr("checked", false)
		})
	}
}
function doRadio(obj) {}
function expand_textarea($obj) {
	if ($("#expandDiv").is(":hidden") == true) $("#expandDiv").show();
	var offsetWidth = $obj.width();
	var offsetHeight = $obj.height();
	var val = $obj.val();
	var oid = $obj.attr("id");
	var readonly = $obj.get(0).readOnly;
	var t = 0;
	var l = 0;
	while ($obj.get(0)) {
		t += $obj.position().top - $(document.body).scrollTop();
		l += $obj.position().left - $obj.scrollLeft();
		$obj = $obj.offsetParent();
		if (!$obj.get(0) || $obj.get(0).tagName == 'BODY') {
			break
		}
		if ($obj.get(0).tagName == 'TR') $obj = $obj.parent()
	}
	var newdiv = $("#expandDiv");
	var newtextarea = $("#expandTextarea");
	newtextarea.val(val);
	newtextarea.width(offsetWidth * 1.5);
	newtextarea.height(offsetHeight * 2);
	newtextarea.css("overflow", "auto");
	newtextarea.attr("pID", oid);
	if (readonly == true) {
		newtextarea.get(0).readOnly = true;
		newtextarea.css("background-color", "#F6F6F6")
	} else {
		newtextarea.get(0).readOnly = false;
		newtextarea.css("background-color", "#FFFFFF")
	}
	newdiv.height(offsetHeight * 2);
	newdiv.width(offsetWidth * 1.5);
	newdiv.css("top", t - 2);
	if ($(document.body).get(0).offsetWidth - l < offsetWidth * 1.5) l = l + ($(document.body).get(0).offsetWidth - l - offsetWidth * 1.5 - 20);
	newdiv.css("left", l);
	newdiv.css("position", "absolute");
	newdiv.css("z-index", 1001);
	newdiv.get(0).firstChild.focus()
}
function getNodeText(node) {
	var t = "";
	if (node == null) return t;
	switch (bOs) {
	case 1:
		t = node.text;
		if (t == undefined) t = node.textContent;
		break;
	case 2:
	case 3:
	case 4:
	case 5:
	case 6:
		t = node.textContent;
		break;
	default:
		t = node.textContent;
		break
	}
	return t
}
function getNodeValue(objXML, nodename, index) {
	try {
		var objNodeList = objXML.getElementsByTagName(nodename);
		if (objNodeList.length > 0) {
			if (index) {
				var childNode = objNodeList[index];
				if (childNode.getAttribute("spaces")) {
					var n = childNode.getAttribute("spaces");
					var s = "";
					while (n > 0) {
						s += " ";
						n--
					}
					return s + getNodeText(childNode)
				} else return getNodeText(childNode)
			} else {
				var childNode = objNodeList[0];
				if (childNode.getAttribute("spaces")) {
					var n = childNode.getAttribute("spaces");
					var s = "";
					while (n > 0) {
						s += " ";
						n--
					}
					return s + getNodeText(childNode)
				} else return getNodeText(childNode)
			}
		}
	} catch(e) {}
	return null
}
function getAttribValue(objXML, nodename, attribName, index) {
	try {
		var objNodeList = objXML.getElementsByTagName(nodename);
		if (objNodeList.length > 0) {
			if (index) {
				return objNodeList.item(index).getAttribute(attribName)
			} else {
				return objNodeList.item(0).getAttribute(attribName)
			}
		}
	} catch(e) {}
	return ""
}
function format(s) {
	var ss = s;
	try {
		if (!ss) return ss;
		ss = ss.replace(/>/g, "&gt;").replace(/</g, "&lt;").replace(/[\r\n]/g, "^^");
		ss = ss.replace(/\"/g, "@@");
		ss = ss.replace(/\'/g, "\\\\'");
		ss = ss.trim()
	} catch(e) {}
	return ss
}
function reFormat(s) {
	var ss = s;
	try {
		if (!ss) return ss;
		ss = s.replace(/&gt;/g, ">").replace(/&lt;/g, "<").replace(/\^\^/g, "\r\n");
		ss = ss.replace(/@@/g, '"');
		ss = ss.replace(/\\\'/g, "'");
		ss = ss.trim()
	} catch(e) {}
	return ss
}
function setEditValue(id, rowdata) {
	try {
		var oKind;
		var oTagName;
		var oType;
		var nodePath;
		var nodeValue;
		var attValue;
		var oDataSource = "";
		var initIndex = 0;
		var $obj = $("#" + id);
		if (!$obj) {
			alertMsg.error("Need to specify the ID of the assignment area");
			return false
		}
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		$("input,select,textarea", $obj).each(function() {
			var $objEdit = $(this);
			if ($objEdit.attr("datasource")) {
				oDataSource = $objEdit.attr("datasource").toUpperCase()
			} else return true;
			oTagName = $objEdit.get(0).tagName.toLowerCase();
			nodePath = oDataSource;
			nodeValue = getNodeValue(objXML, oDataSource, initIndex);
			nodeValue = nodeValue ? nodeValue: "";
			nodeValue = reFormat(nodeValue);
			attValue = getAttribValue(objXML, oDataSource, "sv", initIndex);
			attValue = attValue ? attValue: "";
			if (nodeValue) {
				$(">label.alt", $objEdit.parent()).each(function() {
					$(this).hide()
				})
			}
			oKind = "";
			if ($objEdit.attr("kind")) oKind = $objEdit.attr("kind").toLowerCase();
			if (!oKind && $objEdit.attr("datatype")) {
				var dataType = $objEdit.attr("datatype");
				if (dataType) {
					var arr = dataType.split(",");
					if (arr && arr.length > 0) {
						if (arr[1]) {
							switch (arr[1]) {
							case "is_date":
								oKind = "date";
								break;
							case "is_datetime":
								oKind = "datetime";
								break
							}
						}
					}
				}
			}
			switch (oKind) {
			case "dic":
				if ($objEdit.attr("multi") != 'true') {
					if ($objEdit.attr("isDefault") == "true") {} else {
						g_bCheckChanged = false;
						if (oTagName == "select") {
							$objEdit.find("option").remove();
							var $option = $("<OPTION></OPTION>");
							if (!nodeValue) nodeValue = "-1";
							if (!attValue) attValue = "--";
							$option.val(nodeValue);
							$option.text(attValue);
							$objEdit.append($option);
							$objEdit.attr("title", nodeValue);
							$objEdit.attr("code", nodeValue);
							var $a = $objEdit.parent().find("a:first");
							$a.attr("value", nodeValue);
							$a.text(attValue)
						} else {
							if (attValue == "") $objEdit.val(nodeValue);
							else $objEdit.val(attValue);
							$objEdit.attr("code", nodeValue)
						}
					}
				} else {
					if ($objEdit.attr("isDefault") == "true") {} else {
						g_bCheckChanged = false;
						if (attValue == "") $objEdit.val(nodeValue);
						else $objEdit.val(attValue);
						$objEdit.attr("code", nodeValue)
					}
				}
				break;
			case "query":
				if (nodeValue != "") {
					if (attValue != "") $objEdit.val(attValue);
					else $objEdit.val(nodeValue);
					$objEdit.attr("code", nodeValue)
				} else {
					$objEdit.val(">");
					$objEdit.attr("code", nodeValue)
				}
				break;
			case "datetime":
				$objEdit.val(attValue);
				var sTemp = attValue.replace(/[年月日时分秒]/g, "");
				sTemp = sTemp.replace(/[ :|-]/g, "");
				$objEdit.attr("code", sTemp);
				break;
			case "date":
				$objEdit.val(attValue);
				var sTemp = attValue.replace(/[年月日]/g, "");
				sTemp = sTemp.replace(/[ :|-]/g, "");
				$objEdit.attr("code", sTemp);
				break;
			case "time":
				$objEdit.val(attValue);
				var sTemp = attValue.replace(/[时分秒]/g, "");
				sTemp = sTemp.replace(/[ :|-]/g, "");
				$objEdit.attr("code", sTemp);
				break;
			case "more":
				var sMore = "";
				var morePath = "DATA";
				var moreNode = objXML.getElementsByTagName(nodePath).item(0);
				if (moreNode == null) $objEdit.val(">>");
				else {
					if (moreNode.hasChildNodes > 0) {
						$objEdit.val("[已录入]");
						var nodeList = moreNode.childNodes;
						for (var k = 0; k < nodeList.length; k++) sMore += nodeList.item(k).xml;
						sMore = "<" + morePath + ">" + sMore + "</" + morePath + ">"
					} else $objEdit.val(">>")
				}
				$objEdit.attr("code", sMore);
				break;
			case "":
				if (oTagName == "input") {
					oType = $objEdit.attr("type").toLowerCase();
					if (oType == "radio" || oType == "checkbox") {
						var curNode;
						var curAttr;
						var nodeList = objXML.getElementsByTagName(nodePath);
						var found = false;
						for (var n = 0; n < nodeList.length; n++) {
							curNode = nodeList.item(n);
							curAttr = getAttribValue(objXML, nodePath, "sv", n);
							if (curAttr == $objEdit.val() && curNode.text == $objEdit.attr("code")) {
								found = true;
								break
							}
						}
						$objEdit.attr("checked", found)
					} else {
						if (attValue) {
							$objEdit.val(attValue);
							$objEdit.attr("code", nodeValue)
						} else $objEdit.val(nodeValue)
					}
				} else $objEdit.val(nodeValue);
				break;
			default:
				$objEdit.val(nodeValue)
			}
		})
	} catch(e) {
		alertMsg.error(e);
		return false
	}
}
function resetEditValue(id) {
	try {
		var $obj = $("#" + id);
		if (!$obj) {
			alertMsg.error("Need to specify the ID of the emptied area");
			return false
		}
		var oKind;
		var oTagName;
		var oEditType;
		$("input,select,textarea", $obj).each(function() {
			var $objEdit = $(this);
			if ($objEdit.attr("datasource")) {
				if ($objEdit.attr("keep") && $objEdit.attr("keep") == "true") {
					return true
				}
				oKind = "";
				if ($objEdit.attr("kind")) oKind = $objEdit.attr("kind").toLowerCase();
				if (oKind == "more") {
					$objEdit.val(">>");
					$objEdit.attr("code", "")
				} else if (oKind == "query") {
					$objEdit.val(">");
					$objEdit.attr("code", "")
				} else {
					oTagName = $objEdit.get(0).tagName.toLowerCase();
					if (oTagName == "input") {
						oEditType = "";
						if ($objEdit.attr("type")) oEditType = $objEdit.attr("type").toLowerCase();
						if (oEditType == "radio" || oEditType == "checkbox") $objEdit.attr("checked", false);
						else {
							$objEdit.val("");
							if ($objEdit.attr("code")) $objEdit.attr("code", "")
						}
					} else if (oTagName == "select") {
						$objEdit.find("option").remove();
						var $option = $("<OPTION></OPTION>");
						nodeValue = "-1";
						attValue = "--";
						$option.val(nodeValue);
						$option.text(attValue);
						$objEdit.append($option);
						$objEdit.attr("title", nodeValue);
						$objEdit.attr("code", nodeValue);
						var $a = $objEdit.parent().find("a:first");
						$a.attr("value", nodeValue);
						$a.text(attValue)
					} else {
						$objEdit.val("");
						if ($objEdit.attr("code")) $objEdit.attr("code", "")
					}
				}
				$(">label.alt", $objEdit.parent()).each(function() {
					$(this).show()
				})
			}
		})
	} catch(e) {
		alertMsg.error(e);
		return false
	}
}
function shrink_textarea(obj, objName) {
	$("#" + objName).val(obj.val());
	$("#expandDiv").hide()
}
function onTitleClick(id) {
	var $obj = $("#" + id);
	if ($obj.is(":hidden")) {
		if (bOs == 1) $obj.show();
		else $obj.slideDown()
	} else {
		if (bOs == 1) $obj.hide();
		else $obj.slideUp()
	}
}
function getFilterDept(sJgdm) {
	var jgdm = sJgdm;
	var reg = "^" + jgdm;
	var len = 0;
	while (len <= jgdm.length - 2) {
		reg += "|^" + jgdm.substr(0, len + 2) + "$";
		len = len + 2
	}
	return reg
}
function multiSelected($obj, values, $dom) {
	if (typeof(values) != "object") return false;
	var $areas;
	if ($dom && $dom.size() > 0) $areas = $("[name='multivals']", $dom);
	else $areas = $("[name='multivals']");
	if ($obj.is(":checked") == true) {
		var unique = $obj.val();
		var firVal = $areas.eq(0).val();
		if (firVal.indexOf(unique) >= 0) {
			var a = firVal.split(",");
			var n = 0;
			for (var i = 0; i < a.length; i++) {
				if (a[i] == unique) {
					n = i;
					break
				}
			}
			$areas.each(function(index, element) {
				if (index > 0) {
					var $this = $(this);
					var t = $this.val();
					var vals = values[index];
					var start = 0,
					m = 0;
					var arr = t.split(",");
					while (m < n) {
						start += arr[m].length + 1;
						m++
					}
					$this.val(t.substr(0, start) + vals + t.substr(start + arr[n].length))
				}
			})
		} else {
			$areas.each(function(index, element) {
				var $this = $(this);
				if ($this.val()) {
					$this.val($this.val() + "," + values[index])
				} else $this.val(values[index])
			})
		}
	} else if ($obj.is(":checked") == false) {
		var unique = $obj.val();
		var firVal = $areas.eq(0).val();
		var a = firVal.split(",");
		var n = 0;
		for (var i = 0; i < a.length; i++) {
			if (a[i] == unique) {
				n = i;
				break
			}
		}
		$areas.each(function(index, element) {
			var $this = $(this);
			var t = $this.val();
			var start = 0,
			m = 0;
			var arr = t.split(",");
			while (m < n) {
				start += arr[m].length + 1;
				m++
			}
			if (start == 0 && n == 0) $this.val("" + t.substr(start + arr[n].length + 1));
			else if (start > 0 && n > 0 && (start + arr[n].length) == t.length) $this.val(t.substr(0, start - 1) + "");
			else $this.val(t.substr(0, start) + "" + t.substr(start + arr[n].length + 1))
		})
	}
}
function loadJS(url, charset) {
	if (!charset) {
		charset = "UTF-8"
	}
	var charsetProperty = " charset=\"" + charset + "\" ";
	document.write("<script type=\"text/javascript\" src=\"" + webApps + url + "\" onerror=\"alert('Error loading ' + this.src);\"" + charsetProperty + "></script>")
};
function loadCSS(url, charset) {
	if (!charset) {
		charset = "UTF-8"
	}
	var charsetProperty = " charset=\"" + charset + "\" ";
	document.write("<link href=\"" + webApps + url + "\" type=\"text/css\" rel=\"stylesheet\" onerror=\"alert('Error loading ' + this.src);\"" + charsetProperty + "/>")
};
function getAbsPoint(e) {
	var x = e.offsetLeft;
	var y = e.offsetTop;
	var arr = [];
	while (e = e.offsetParent) {
		x += e.offsetLeft;
		y += e.offsetTop
	}
	arr.push(x);
	arr.push(y);
	return arr
}
function doColLinkClick(func, obj) {
	try {
		if ($(obj).attr("isedit") == "true") {
			if (eval(func + "(obj)")) {}
		} else eval(func + "(obj)")
	} catch(e) {
		alertMsg.error(e)
	}
}
/**
 * 提交前验证:
 * submitForm(formID) 返回true通过验证，验证失败并在相应input上显示提示框
 * 标签多了一个datatype属性，该属性含三部分：
 * 1.是否可为空 1-允许 0-不允许
 * 2.数据类型，只能是下表中的值
 * 3.最大长度
 * 4.最小长度(可选)
 * 
 * 可用的数据类型：
 * is_vin 验证VIN
 * is_null 验证空
 * is_textarea 验证文本域
 * is_carno 验证车牌号
 * is_name 验证名称输入域
 * is_fdjh 发动机号
 * is_datetime 验证日期格式 YYYY-MM-DD hh:mm
 * is_date 日期类型，可匹配group使用。例：group="date_begin,date_end" 
 *         date_begin开始时间的ID，date_end结束时间的ID 加了is_date和group会检查开始时间不能大于结束时间
 * is_double 正浮点数 decimal 指定小数点后几位 decimal="2"
 * is_wan 验证万
 * is_yuan  验证圆
 * is_digit  全部由数字组成
 * is_digit_0  全部由数字组成,但是不能以0开头
 * is_letter_cn 由字母和中文组成
 * is_digit_letter_cn 由数字字母和中文组成
 * is_digit_letter 由数字和字母组成
 * is_email 是否Email
 * is_noquotation 不能输入 ',%
 * is_phone 电话
 × is_docNumber 文档编号
 * is_task_pri
 * is_labercode 验证工时
 * is_date_now 日期不能大于当前时间(今日)
 * is_digit_0 判断数字：0不能开头
 * is_idcard 判断身份证号码合法性
 * is_money  判断金额: 0不能开头，可以有小数但最多是两位 
 * is_money_4  判断税率: 0不能开头，可以有小数但最多是四位
 * is_bigmoney 金额格式可以为12,000.00
 * is_labercode 判断索赔工时代码
 * 添加以下校验方法，请勿覆盖
 * is_all_digit_2 校验正确的数字格式，可以为负数，实数保留2位有效数字
*/
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "")
};
String.prototype.getByteLength = function() {
	return this.replace(/[^\x00-\xff]/g, "00").length
};
Array.prototype.removee = function(s) {
	for (var i = 0; i < this.length; i++) {
		if (s == this[i]) this.splice(i, 1)
	}
};
function Map() {
	this.keys = new Array();
	this.data = new Object();
	this.put = function(key, value) {
		if (this.data[key] == null) {
			this.keys.push(key)
		}
		this.data[key] = value
	};
	this.get = function(key) {
		return this.data[key]
	};
	this.remove = function(key) {
		this.keys.removee(key);
		this.data[key] = null
	};
	this.isEmpty = function() {
		return this.keys.length == 0
	};
	this.size = function() {
		return this.keys.length
	}
}
var m = new Map();
function checkDate(sd, ed) {
	var startDate = sd.replace(/-/g, "/");
	var endDate = ed.replace(/-/g, "/");
	if (Date.parse(startDate) - Date.parse(endDate) > 0) {
		return false
	}
	return true
}
var ieVer = getIEVer();
function initPage() {
	createTip();
	addListener()
}
function getTip() {
	if (validateobjarr != null && validateobjarr.length > 0) {
		for (var i = 0; i < validateobjarr.length; i++) {
			if ($(validateobjarr[i]).is(":visible") == false) {
				return validateobjarr[i]
			}
		}
	}
	return null
}
function clearTip() {
	if (validateobjarr != null && validateobjarr.length > 0) {
		for (var i = 0; i < validateobjarr.length; i++) {
			$("#" + validateobjarr[i]).hide()
		}
	}
}
function getIEVer() {
	return parseFloat(navigator.appVersion.split("MSIE")[1])
}
function killErrors() {
	return true
}
window.onerror = killErrors;
var oldInputStyle;
var oldImgStyle;
function submitForm(form) {
	if (!checkFormInput(form)) {
		return false
	} else {
		var fjTab = $("#fileUploadTab");
		if (fjTab.length > 0) {
			var s = "";
			if (fjTab.rows.length > 1) {
				for (var i = 1; i < fjTab.rows.length; i++) {
					var fjid = fjTab.rows[i].getAttribute("FJID");
					if (s && s.length > 0) s += "," + fjid;
					else s = fjid
				}
			}
			if ($("#fjids")) $("#fjids").val(s)
		}
		return true
	}
}
function showTip(handle, msg, tipid) {
	handle.keydown(function(event) {
		if (event.keyCode != 9) {
			hideTip(tipid)
		}
	});
	var pos = getPosition(handle);
	var t = pos.top;
	var l = pos.left;
	mytop = t;
	if (msg.indexOf("CL") > 0) {
		mytop = mytop - msg.split("CL")[1];
		msg = msg.split("CL")[0]
	} else {
		if (msg.length > 0 && msg.length <= 10) {
			mytop = mytop - 28
		} else if (msg.length >= 11 && msg.length <= 16) {
			mytop = mytop - 30
		} else if (msg.length > 16) {
			mytop = mytop - 40
		}
	}
	$("#" + tipid).css("position", "absolute");
	$("#" + tipid).css("left", l + 60);
	$("#" + tipid).css("top", mytop);
	$("#" + tipid + "_msg").html("<font color='red'>&nbsp;" + msg + "</font>");
	$("#" + tipid).show();
	if (validateConfig.timeOut > 0) {
		var timer = setTimeout("hideTip('" + tipid + "','" + handle.attr("id") + "');", validateConfig.timeOut);
		m.put(tipid, timer)
	}
}
function addListener(sObj) {
	var $d = null;
	if (sObj && typeof(sObj) == "object") {
		$d = sObj
	} else if (sObj) $d = $("#" + sObj);
	if (validateConfig.isOnBlur) {
		var inputs;
		if (sObj && $d.size() > 0) {
			inputs = $("input", $d)
		} else inputs = $("input");
		inputs.each(function(index, element) {
			var $this = $(this);
			var datatype = $this.attr("datatype");
			if (datatype) {
				var temp = null;
				if ($this.attr("id")) temp = $this.attr("id");
				else {
					alertMsg.error("INPUT框[name=" + $this.attr("name") + "]中没有写明ID属性");
					return false
				}
				$this.blur(function() {
					if (myonblur(temp)) {
						if ($this.attr("blurback") && $this.attr("blurback") == "true") blurBack(temp);
						try {
							eval($this.attr("doblur"))
						} catch(e) {}
					}
				})
			}
		});
		var textareas;
		if (sObj && $d.size() > 0) textareas = $("textarea", $d);
		else textareas = $("textarea");
		textareas.each(function(index, element) {
			var $this = $(this);
			var datatype = $this.attr("datatype");
			if (datatype) {
				var temp = null;
				if ($this.attr("id")) temp = $this.attr("id");
				else {
					alertMsg.error("textarea框[name=" + $this.attr("name") + "]中没有写明ID属性");
					return false
				}
				$this.blur(function() {
					myonblur(temp);
					if ($this.attr("blurback") && $this.attr("blurback") == "true") blurBack(temp)
				})
			}
		})
	}
}
function blurBack(obj) {}
function myonblur(obj) {
	return checkInput(obj)
}
function showErrMsg(oid, errmsg, y) {
	var tipid = getTip();
	showTip($("#" + oid), errmsg + "CL" + y, tipid)
}
function checkInput(valid) {
	var tipid = getTip();
	if (tipid == null) {
		return false
	}
	var issele = null;
	var rest = true;
	var $input = $("#" + valid);
	$input.val($input.val().trim());
	tmptypeStr = $input.attr("datatype");
	str = tmptypeStr.split(",");
	nullAble = str[0];
	typeStr = str[1];
	maxLength = str[2];
	errm = $input.attr("errmsg");
	minLength = "0";
	if (str.length == 4) {
		minLength = str[3]
	}
	if ($input.val() == null || $input.val() == "") {
		if (nullAble == "0") {
			hideTip(tipid);
			if ($input.attr("hint") == null || $input.attr("hint") == "") {
				if ($input.attr("kind") != "dic") showTip($input, $.info.vMust(), tipid);
				issele == null ? issele = $input.attr("id") : ""
			}
		}
	} else {
		if (checkUnChar($input.val())) {
			hideTip(tipid);
			showTip($input, $.info.vUnChar(), tipid);
			issele == null ? (issele = $input.attr("id")) : "";
			return false
		}
		if (typeStr == "is_yuan" || typeStr == "is_wan") {
			maxLength = 1000
		}
		if (typeStr == "is_phone") {
			maxLength = 11
		}
		if (typeStr == "is_date") {
			if ($input.attr("group")) {
				var gr = $input.attr("group");
				var sdd = gr.split(",");
				var startd = $("#" + sdd[0]).val();
				var endd = $("#" + sdd[1]).val();
				if (startd != null && startd != "" && endd != null && endd != "") {
					if (!checkDate(startd, endd)) {
						hideTip(tipid);
						showTip($("#" + sdd[1]), $.info.vGroupDate(), tipid);
						issele == null ? (issele = $input.attr("id")) : "";
						rest = false
					}
				}
			}
		}
		if (typeStr == "is_date_now") {
			if ($input) {
				var valDate = $input.val();
				var nowDate = (new Date()).Format("yyyy-MM-dd");
				if (valDate != null && valDate != "" && nowDate != null && nowDate != "") {
					if (!checkDate(valDate, nowDate)) {
						hideTip(tipid);
						showTip($input, $.info.vDateNow(), tipid);
						issele == null ? (issele = $input.attr("id")) : "";
						rest = false
					}
				}
			}
			if ($input.attr("group")) {
				var gr = $input.attr("group");
				var sdd = gr.split(",");
				var startd = $("#" + sdd[0]).val();
				var endd = $("#" + sdd[1]).val();
				if (startd != null && startd != "" && endd != null && endd != "") {
					if (!checkDate(startd, endd)) {
						hideTip(tipid);
						showTip($("#" + sdd[0]), $.info.vGroupDate(), tipid);
						issele == null ? (issele = $input.attr("id")) : "";
						rest = false
					}
				}
			}
		}
		if (typeStr == "is_textarea") {
			if ($input.attr("length") > maxLength && maxLength > 0) {
				hideTip(tipid);
				showTip($input, $.info.vMaxLen(maxLength), tipid);
				issele == null ? (issele = $input.attr("id")) : "";
				rest = false
			}
		}
		if (typeStr == "is_double") {
			var controlObj = $input;
			if (controlObj.attr("decimal")) {
				var tempMsg = eval("" + typeStr + "(controlObj);");
				if (tempMsg == true) {
					var tt2 = checkDecimal(controlObj.val(), controlObj.attr("decimal"));
					if (tt2 != true) {
						hideTip(tipid);
						showTip($input, "" + tt2, tipid);
						issele == null ? (issele = $input.attr("id")) : "";
						rest = false
					}
				} else {
					hideTip(tipid);
					showTip($input, "" + tempMsg, tipid);
					issele == null ? (issele = $input.attr("id")) : "";
					rest = false
				}
			}
		}
		if (calculateLength($input.val()) < minLength) {
			hideTip(tipid);
			showTip($input, $.info.vMinLen(minLength), tipid);
			issele == null ? issele = $input.attr("id") : "";
			rest = false
		} else if (calculateLength($input.val()) > maxLength && maxLength > 0) {
			if ($input.attr("kind") && $input.attr("kind") == "dic") return true;
			hideTip(tipid);
			showTip($input, $.info.vMaxLen(maxLength), tipid);
			issele == null ? issele = $input.attr("id") : "";
			rest = false
		} else {
			var tempMsg = eval("" + typeStr + "($input);");
			if (tempMsg != true) {
				hideTip(tipid);
				if (errm != null && errm != "") {
					showTip($input, errm, tipid)
				} else {
					showTip($input, "" + tempMsg, tipid)
				}
				issele == null ? issele = $input.attr("id") : "";
				rest = false
			}
		}
		var kind = $input.attr("kind");
		if (kind && kind == "dic") {
			if (!$input.attr("code") && $input.val() != null && $input.val().length > 0 && $input.attr("multi") != 'true') {
				m_sErrorInfo = $.info.dCode();
				m_bFormatError = true;
				if (m_bFormatError) {
					hideTip(tipid);
					showTip($input, m_sErrorInfo, tipid);
					issele == null ? (issele = $input.attr("id")) : "";
					rest = false
				}
			} else if (this.multi == 'true') {
				if ($input.val()) {
					if ($input.val().trim().substr($input.val().length - 1) == ',') $input.val($input.val().trim().substr(0, $input.val().length - 1))
				}
				if ($input.val() != null && $input.val().length > 0 && (!$input.attr("code") || $input.attr("code").trim().length == 0)) {
					m_sErrorInfo = $.info.dCode();
					m_bFormatError = true;
					if (m_bFormatError) {
						hideTip(tipid);
						showTip($("#" + valid), m_sErrorInfo, tipid);
						issele == null ? (issele = $("#" + valid).attr("id")) : "";
						rest = false
					}
				}
				if ($input.attr("code") && $input.attr("code").trim().length > 0 && $input.attr("maxLength") && $input.attr("maxLength") > 0) {
					if ($input.attr("code").trim().length > $input.attr("maxLength")) {
						m_sErrorInfo = $.info.dCode();
						m_bFormatError = true;
						if (m_bFormatError) {
							hideTip(tipid);
							showTip($input, m_sErrorInfo, tipid);
							issele == null ? (issele = $input.attr("id")) : "";
							rest = false
						}
					}
				}
				if (g_xDic != null) g_xDic.checkMultiDic()
			}
		}
	}
	return rest
}
function checkFormInput(handle) {
	if (validateobjarr.length == 0) {
		return true
	}
	clearTip();
	var controlList;
	if (typeof(handle) == "object") {
		controlList = $("INPUT,SELECT,TEXTAREA", handle)
	} else {
		var form = $(handle);
		controlList = $("INPUT,SELECT,TEXTAREA", form)
	}
	var controlObj;
	var rest = true;
	var issele = null;
	controlList.each(function() {
		var tipid = getTip();
		if (tipid == null) {
			return false
		}
		controlObj = $(this);
		if (isControlVisible(controlObj)) {
			if (controlObj.isTag("select") || controlObj.attr("type") == 'text' || controlObj.attr("type") == 'password' || controlObj.attr("type") == 'textarea' || controlObj.attr("type") == 'select-one' || controlObj.attr("type") == 'file' || controlObj.attr("type") == 'checkbox') {
				var v = controlObj.get(0).value;
				controlObj.val(v.trim());
				if (controlObj.attr("datatype") != null) {
					if (checkUnChar(controlObj.val())) {
						hideTip(tipid);
						showTip(controlObj, $.info.vUnChar(), tipid);
						issele == null ? (issele = controlObj.attr("id")) : "";
						rest = false;
						return false
					}
					if (controlObj.attr("type") == 'checkbox' && controlObj.is(":checked") == false) {
						showTip(controlObj, $.info.vMust(), tipid);
						issele == null ? ($(controlObj).select(), issele = controlObj.attr("id")) : "";
						rest = false;
						return true
					} else if (controlObj.attr("type") == 'checkbox' && controlObj.is(":checked") == true) {
						rest = true;
						return true
					}
					tmptypeStr = controlObj.attr("datatype");
					str = tmptypeStr.split(",");
					nullAble = str[0];
					typeStr = str[1];
					maxLength = str[2];
					errm = controlObj.attr("errmsg");
					minLength = "0";
					if (str.length == 4) {
						minLength = str[3]
					}
					if (controlObj.isTag("select")) {
						if (controlObj.val() == "-1") if (nullAble == "0") {
							hideTip(tipid);
							if (controlObj.attr("hint") == null || controlObj.hint == "") {
								if (controlObj.attr("type") == 'select-one' && getIEVer() <= 6) {
									showTip(controlObj, $.info.vMust() + "CL30", tipid)
								} else {
									showTip(controlObj, $.info.vMust(), tipid)
								}
								issele == null ? ($(controlObj).select(), issele = controlObj.attr("id")) : "";
								rest = false;
								return false
							}
						}
					} else if (controlObj.val() == null || controlObj.val() == "") {
						if (nullAble == "0") {
							hideTip(tipid);
							if (controlObj.attr("hint") == null || controlObj.hint == "") {
								if (controlObj.attr("type") == 'select-one' && getIEVer() <= 6) {
									showTip(controlObj, $.info.vMust() + "CL30", tipid)
								} else {
									showTip(controlObj, $.info.vMust(), tipid)
								}
								issele == null ? ($(controlObj).select(), issele = controlObj.attr("id")) : "";
								rest = false;
								return false
							}
						}
					} else {
						if (typeStr == "is_yuan" || typeStr == "is_wan") {
							maxLength = 1000
						}
						if (typeStr == "is_phone") {
							maxLength = 11
						}
						if (typeStr == "is_date") {
							if (controlObj.attr("group")) {
								var gr = controlObj.attr("group");
								var sdd = gr.split(",");
								var startd = $("#" + sdd[0]).val();
								var endd = $("#" + sdd[1]).val();
								if (startd != null && startd != "" && endd != null && endd != "") {
									if (!checkDate(startd, endd)) {
										hideTip(tipid);
										showTip($("#" + sdd[1]), $.info.vGroupDate(), tipid);
										issele == null ? (controlObj.select(), issele = controlObj.attr("id")) : "";
										rest = false;
										return false
									}
								}
							}
						}
						if (typeStr == "is_date_now") {
							var valDate = controlObj.val();
							var nowDate = (new Date()).Format("yyyy-MM-dd");
							if (valDate != null && valDate != "" && nowDate != null && nowDate != "") {
								if (!checkDate(valDate, nowDate)) {
									hideTip(tipid);
									showTip(controlObj, $.info.vDateNow(), tipid);
									issele == null ? (controlObj.select(), issele = controlObj.attr("id")) : "";
									rest = false;
									return false
								}
							}
						}
						if (typeStr == "is_textarea") {
							if (calculateLength(controlObj.val()) > maxLength && maxLength > 0) {
								hideTip(tipid);
								showTip(controlObj, $.info.vMaxLen(maxLength), tipid);
								issele == null ? ($(controlObj).select(), issele = controlObj.attr("id")) : "";
								rest = false;
								return false
							}
						}
						if (typeStr == "is_double") {
							if (controlObj.attr("decimal")) {
								var tempMsg = eval("" + typeStr + "(controlObj);");
								if (tempMsg == true) {
									var tt2 = checkDecimal(controlObj.val(), controlObj.attr("decimal"));
									if (tt2 != true) {
										hideTip(tipid);
										showTip(controlObj, "" + tt2, tipid);
										issele == null ? ($(controlObj).select(), issele = controlObj.attr("id")) : "";
										rest = false;
										return false
									}
								} else {
									hideTip(tipid);
									showTip(controlObj, "" + tempMsg, tipid);
									issele == null ? ($(controlObj).select(), issele = controlObj.attr("id")) : "";
									rest = false;
									return false
								}
							}
						}
						if (calculateLength(controlObj.val()) < minLength) {
							hideTip(tipid);
							showTip(controlObj, $.info.vMinLen(minLength), tipid);
							issele == null ? ($(controlObj).select(), issele = controlObj.attr("id")) : "";
							rest = false;
							return true
						} else if (calculateLength(controlObj.val()) > maxLength && maxLength > 0) {
							hideTip(tipid);
							showTip(controlObj, $.info.vMaxLen(maxLength), tipid);
							issele == null ? ($(controlObj).select(), issele = controlObj.attr("id")) : "";
							rest = false;
							return false
						} else {
							var tempMsg = eval("" + typeStr + "(controlObj);");
							if (tempMsg != true) {
								hideTip(tipid);
								if (errm != null && errm != "") {
									showTip(controlObj, errm, tipid)
								} else {
									showTip(controlObj, "" + tempMsg, tipid)
								}
								issele == null ? (controlObj.select(), issele = controlObj.attr("id")) : "";
								rest = false;
								return false
							}
						}
					}
				}
			}
			var kind = controlObj.attr("kind");
			if (kind && kind == "dic") {
				if (controlObj.isTag("select") == false && !controlObj.attr("code") && controlObj.val() != null && controlObj.val().length > 0 && controlObj.attr("multi") != 'true') {
					m_sErrorInfo = $.info.dCode();
					m_bFormatError = true;
					if (m_bFormatError) {
						hideTip(tipid);
						showTip(controlObj, m_sErrorInfo, tipid);
						issele == null ? (issele = controlObj.attr("id")) : "";
						rest = false;
						return false
					}
				} else if (this.multi == 'true') {
					if (controlObj.val()) {
						if (controlObj.val().trim().substr(controlObj.val().length - 1) == ',') controlObj.val(controlObj.val().trim().substr(0, controlObj.val().length - 1))
					}
					if (controlObj.val() != null && controlObj.val().length > 0 && (!controlObj.attr("code") || controlObj.attr("code").trim().length == 0)) {
						m_sErrorInfo = $.info.dCode();
						m_bFormatError = true;
						if (m_bFormatError) {
							hideTip(tipid);
							showTip(controlObj, m_sErrorInfo, tipid);
							issele == null ? (issele = controlObj.attr("id")) : "";
							rest = false;
							return false
						}
					}
					if (controlObj.attr("code") && controlObj.attr("code").trim().length > 0 && controlObj.attr("maxLength") && controlObj.attr("maxLength") > 0) {
						if (controlObj.attr("code").trim().length > controlObj.attr("maxLength")) {
							m_sErrorInfo = $.info.dCode();
							m_bFormatError = true;
							if (m_bFormatError) {
								hideTip(tipid);
								showTip(controlObj, m_sErrorInfo, tipid);
								issele == null ? (issele = controlObj.attr("id")) : "";
								rest = false;
								return false
							}
						}
					}
					if (g_xDic != null) g_xDic.checkMultiDic()
				}
			}
		}
	});
	return rest
}
function handleKeyDown(event) {
	hideTip()
}
function hideTip(tipid, objId) {
	if (validateConfig.timeOut > 0) {
		var val = m.get(tipid);
		clearTimeout(val);
		m.remove(val)
	}
	$("#" + tipid).hide();
	if (ieVer <= 6) {
		ShowOverSels(tipid)
	}
}
function HideOverSels(objID) {
	var sels = document.getElementsByTagName('select');
	for (var i = 0; i < sels.length; i++) if (Obj1OverObj2(document.all[objID], sels[i])) {
		sels[i].style.visibility = 'hidden'
	}
}
function ShowOverSels(objID) {
	var sels = $('select');
	sels.each(function() {
		var $this = $(this);
		if (Obj1OverObj2($("#" + objId), $this)) $this.show()
	})
}
function Obj1OverObj2(obj1, obj2) {
	var pos1 = getPosition(obj1);
	var pos2 = getPosition(obj2);
	var result = true;
	var obj1Left = pos1.left - window.document.body.scrollLeft;
	var obj1Top = pos1.top - window.document.body.scrollTop;
	var obj1Right = obj1Left + obj1.offsetWidth;
	var obj1Bottom = obj1Top + obj1.offsetHeight;
	var obj2Left = pos2.left - window.document.body.scrollLeft;
	var obj2Top = pos2.top - window.document.body.scrollTop;
	var obj2Right = obj2Left + obj2.offsetWidth;
	var obj2Bottom = obj2Top + obj2.offsetHeight;
	if (obj1Right <= obj2Left || obj1Bottom <= obj2Top || obj1Left >= obj2Right || obj1Top >= obj2Bottom) result = false;
	return result
}
function getPosition(Obj) {
	var sumTop = 0;
	var sumLeft = 0;
	var obj = Obj.get(0);
	if (obj.tagName == "SELECT") {
		obj = $("#combox_" + obj.id).get(0);
		while (obj) {
			sumTop += $(obj).position().top - $(document.body).scrollTop();
			sumLeft += $(obj).position().left - $(obj).scrollLeft();
			obj = $(obj).offsetParent().get(0);
			if (!obj || obj.tagName == 'BODY') {
				break
			}
			if (obj.tagName == 'TR') obj = $(obj).parent().get(0)
		}
	} else {
		while (obj) {
			sumTop += $(obj).position().top - $(document.body).scrollTop();
			sumLeft += $(obj).position().left - $(obj).scrollLeft();
			obj = $(obj).offsetParent().get(0);
			if (!obj || obj.tagName == 'BODY') {
				break
			}
			if (obj.tagName == 'TR') obj = $(obj).parent().get(0)
		}
	}
	return {
		left: sumLeft,
		top: sumTop
	}
}
function isControlVisible(handle) {
	var retValue = true;
	if (handle.isTag("select")) return true;
	if (handle.is(":visible") == false) retValue = false;
	return retValue
}
function pdDate(st, ed) {
	var startDate = $(st).val();
	startDate = startDate.replace(/-/g, "/");
	var endDate = $(ed).val();
	endDate = endDate.replace(/-/g, "/");
	if (Date.parse(startDate) - Date.parse(endDate) > 0) {
		return false
	}
	return true
}
function is_digitC(Character) {
	if (! (Character >= '0' && Character <= '9')) {
		return false
	} else return true
}
function is_alfaC(Character) {
	if (! ((Character >= 'a' && Character <= 'z') || (Character >= 'A' && Character <= 'Z'))) {
		return false
	} else return true
}
function is_lineC(Character) {
	if (! ((Character == '-') || (Character == '_'))) {
		return false
	} else return true
}
function is_cnC(Character) {
	var pattern = /^[\u4E00-\u9FA5]{0,200}$/;
	if (!pattern.exec(Character)) {
		return false
	} else return true
}
function is_upper(handle) {
	var inputString = handle.val();
	for (var i = 0; i < inputString.length; i++) {
		checkCharacter = inputString.charAt(i);
		if (checkCharacter >= 'Z' || checkCharacter <= 'A') {
			return "不能输入字符\"" + checkCharacter + "\""
		}
	}
	return true
}
function is_all(handle) {
	var inputString = handle.val();
	for (var i = 0; i < inputString.length; i++) {
		checkCharacter = inputString.charAt(i);
		if ((checkCharacter == '\'') || (checkCharacter == '\\')) {
			return "不能输入字符\"" + checkCharacter + "\"."
		}
	}
	return true
}
function is_address(handle) {
	var pattern = /^[-#，。a-zA-Z0-9\u4E00-\u9FA5]{0,200}$/;
	if (!pattern.exec(handle.val())) return $.info.vAddress();
	return true
}
function is_phone(handle) {
	var inputString = handle.val();
	if (inputString.length != 11) {
		return "手机号码位数必须为11位."
	}
	var myreg = /^1+\d{10}$/;
	if (!myreg.exec(inputString)) {
		return $.info.vPhone()
	}
	return true
}
function inputNumber(handle, keyCode) {
	if (! ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 96 && keyCode <= 105))) {
		window.event.returnValue = false;
		return "必须输入数字!即如下字符:<br>1234567890"
	} else return true
}
function inputLetter(handle, keyCode) {
	if (! ((keyCode >= 97 && keyCode <= 122) || (keyCode >= 65 && keyCode <= 90))) {
		window.event.returnValue = false;
		return "必须输入大小写字母!即如下字符:<br>abcdefghijklmnopqrstuvwxyz<br>ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	} else return true
}
function inputVisible(handle, keyCode) {
	var pattern = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.@><,-[]{}?/+=|\\\'\":;~!#$%()`";
	var keyValue = String.fromCharCode(keyCode);
	if (keyCode == 190) keyValue = ".";
	if (keyCode == 189) keyValue = "-";
	if (keyCode == 188) keyValue = "<";
	if (keyCode == 219) keyValue = "[";
	if (keyCode == 221) keyValue = "]";
	if (keyCode == 191) keyValue = "?";
	if (keyCode == 187) keyValue = "+";
	if (keyCode == 220) keyValue = "|";
	if (keyCode == 222) keyValue = "'";
	if (keyCode == 186) keyValue = ";";
	if (keyCode == 192) keyValue = "~";
	if (pattern.indexOf(keyValue) != -1) {
		window.event.returnValue = true;
		return true
	} else {
		window.event.returnValue = false;
		return "必须输入可见字符!即如下字符:<br>ABCDEFGHIJKLMNOPQRSTUVWXYZ<br> abcdefghijklmnopqrstuvwxyz<br>0123456789.@><,-[]{}?/+=|\\\'\":<br>;~!#$%()`"
	}
}
function inputNormal(handle, keyCode) {
	var pattern = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	var keyValue = String.fromCharCode(keyCode);
	if (pattern.indexOf(keyValue) != -1) {
		window.event.returnValue = true;
		return true
	} else {
		window.event.returnValue = false;
		return "必须输入可见字符!即如下字符:<br>ABCDEFGHIJKLMNOPQRSTUVWXYZ<br>abcdefghijklmnopqrstuvwxyz<br>0123456789"
	}
}
function is_double(handle) {
	var pattern = /^\d+(\.\d+)?$/;
	if (!pattern.exec(handle.val())) return "请输入浮点数.";
	return true
}
function is_date(handle) {
	var pattern = /^\d{4}(-)(1[012]|0?[1-9]){1,2}(-)([12]\d|3[01]|0?[1-9]){1,2}$/;
	if (!pattern.exec(handle.val())) return "YYYY-MM-DD.";
	return true
}
function is_date_now(handle) {
	var pattern = /^\d{4}(-)(1[012]|0?[1-9]){1,2}(-)([12]\d|3[01]|0?[1-9]){1,2}$/;
	if (!pattern.exec(handle.val())) return "YYYY-MM-DD.";
	return true
}
function is_date3(handle) {
	var pattern = /^(-)(1[012]|0?[1-9]){1,2}(-)([12]\d|3[01]|0?[1-9]){1,2}$/;
	if (!pattern.exec(handle.val())) return "MM-DD.";
	return true
}
function is_vin(handle) {
	var pattern = /^[A-Z]|[0-9]$/;
	if (handle.val().length != 8) {
		if (handle.val().length != 17) return "必须为vin后8或17位大写字母和数字组合."
	}
	if (!pattern.exec(handle.val())) return "必须为vin后8或17位大写字母和数字组合";
	return true
}
function is_yuan(handle) {
	var pattern = /^[0-9]{1,8}(|\.[0-9]{1,2})$/;
	if (!pattern.exec(handle.val())) return "数据格式错误.";
	return true
}
function is_wan(handle) {
	var pattern = /^[0-9]{1,4}(|\.[0-9]{1,4})$/;
	if (!pattern.exec(handle.val())) return "数据格式错误.";
	return true
}
function is_fdjh(handle) {
	var pattern = /^[A-Z0-9*]{0,200}$/;
	if (!pattern.exec(handle.val())) return "数据格式错误.";
	return true
}
function is_carno(handle) {
	var pattern = /^[a-zA-Z0-9-·\u4E00-\u9FA5]{0,200}$/;
	if (!pattern.exec(handle.val())) return $.info.vCarNo();
	return true
}
function is_null(handle) {
	if (handle.val() == null || handle.val() == "") {
		return "此处不能为空."
	}
	return true
}
function is_textarea(handle) {
	return true
}
function is_datetime(handle) {
	var pattern = /^(\d{4})-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):*(\d{0,2})$/;
	if (!pattern.exec(handle.val())) return "YYYY-MM-DD HH:MM.CL27";
	return true
}
function is_task_pri(handle) {
	var pattern = /^\d+$/;
	if (!pattern.exec(handle.val())) return "YYYY-MM-DD HH:MM.CL27";
	return true
}
function is_money(handle) {
	var pattern = /^(([1-9]\d*)|0)(\.\d{1,2})?$/;
	if (!pattern.exec(handle.val().replace(/[,]/g, ""))) {
		return "只能输入数字,0不能开头,小数最多两位"
	} else {
		return true
	}
}
function is0_100(handle) {
	var pattern = /^(?:0|[1-9][0-9]?|100)$/;
	if (!pattern.exec(handle.val())) {
		return "只能输入0-100的整数"
	} else {
		return true
	}
}
function is_bigmoney(handle) {
	var pattern = /^[\-\+]?([0-9]\d*|0|[1-9]\d{0,2}(,\d{3})*)(\.\d+)?$/;
	if (!pattern.exec(handle.val())) {
		return "只能输入数字,0不能开头,小数最多两位"
	} else {
		return true
	}
}
function is_money_4(handle) {
	var pattern = /^(([1-9]\d*)|0)(\.\d{1,4})?$/;
	if (!pattern.exec(handle.val())) {
		return "只能输入数字,0不能开头,小数最多四位"
	} else {
		return true
	}
}
function is_digit(handle) {
	var pattern = /^\+?[1-9][0-9]*$/;
	if (!pattern.exec(handle.val())) return "只能输入非零的正整数，并且不能以0开头.";
	return true
}
function is_digit_0(handle) {
	var pattern = /^\+?[1-9][0-9]*$/;
	if (!pattern.exec(handle.val())) return "只能输入非零的正整数，并且不能以0开头.";
	return true
}
function is_digit_line(handle) {
	var pattern = /^(\d|[-]){0,100}$/;
	if (!pattern.exec(handle.val())) return "不能输入数字和'-'以外的字符.";
	return true
}
function is_letter_cn(handle) {
	var pattern = /^[a-zA-Z\u4E00-\u9FA5]{0,200}$/;
	if (!pattern.exec(handle.val())) return "不能输入字母和中文以外的字符.";
	return true
}
function is_digit_letter_cn(handle) {
	var pattern = /^[a-zA-Z0-9\u4E00-\u9FA5]{0,200}$/;
	if (!pattern.exec(handle.val())) return "不能输入数字、字母和中文以外的字符.";
	return true
}
function is_name(handle) {
	var pattern = /^[a-zA-Z0-9-_\u4E00-\u9FA5]{0,200}$/;
	if (!pattern.exec(handle.val())) return "不能输入数字、字母、-、_和中文以外的字符.";
	return true
}
function is_digit_letter(handle) {
	var pattern = /^([a-z]|[A-Z]|[-]|[_]|[*]|[.]|[0-9]){0,200}$/;
	if (!pattern.exec(handle.val())) return "不能输入数字和字母以外的字符.";
	return true
}
function is_digit_letter2(handle) {
	var pattern = /^([A-Z]|[0-9]){0,200}$/;
	if (!pattern.exec(handle.val())) return "只能输入大写字母和数字.";
	return true
}
function is_letter(handle) {
	var pattern = /^([a-z]|[A-Z]){0,200}$/;
	if (!pattern.exec(handle.val())) return "不能输入字母以外的字符.";
	return true
}
function is_labercode(handle) {
	var pattern = /^[A-Z]{2}\w{7}$/;
	if (!pattern.exec(handle.val())) return "工时代码定义不符合规则.";
	return true
}
function is_docNumber(handle) {
	var pattern = /^[a-zA-Z0-9_-]{0,200}$/;
	if (!pattern.exec(handle.val())) return "不能输入数字、字母、-、_以外的字符.";
	return true
}
function is_email(handle) {
	var patternn = /^[a-zA-Z0-9-_\.@]{0,200}$/;
	if (!patternn.exec(handle.val())) {
		return "邮件格式不正确."
	}
	var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
	if (!pattern.exec(handle.val())) return "邮件格式不正确.";
	return true
}
function getBirthday(sId) {
	var s = UpID(sId);
	return s ? s.substr(6, 8) : false
}
function getSex(sId) {
	var s = UpID(sId);
	return s ? (parseInt(s.substr(16, 1)) % 2 ? "1": "2") : "0"
}
function is_idcard(handle) {
	return UpID(handle.val()) ? true: "身份证格式不正确"
}
function isLeapYear(iYear) {
	return ((! (iYear % 4) && (iYear % 100)) || !(iYear % 400)) ? true: false
}
function y2k(iYear) {
	return (iYear < 1000) ? iYear + 1900 : iYear
}
function getNowDate() {
	var today = new Date();
	var y = today.getFullYear() + "";
	var m = today.getMonth() + 1 + "";
	m = m.length == 1 ? "0" + m: m;
	var d = today.getDate() + "";
	d = d.length == 1 ? "0" + d: d;
	return y + m + d
}
function getNowTime() {
	var today = new Date();
	var h = today.getHours() + "";
	h = h.length == 1 ? "0" + h: h;
	var m = today.getMinutes() + "";
	m = m.length == 1 ? "0" + m: m;
	var s = today.getSeconds() + "";
	s = s.length == 1 ? "0" + s: s;
	return h + m + s
}
function verifyDate(day, month, year) {
	if (!day) return false;
	var iToday = new Date();
	month = month ? month - 1 : iToday.getMonth();
	year = year ? y2k(parseInt(year)) : iToday.getFullYear();
	var iDate = new Date(year, month, day);
	if ((iDate.getFullYear() == year) && (iDate.getMonth() == month) && (iDate.getDate() == day)) {
		return true
	} else {
		return false
	}
}
function CalID_17to18(sId) {
	var aW = new Array(1, 2, 4, 8, 5, 10, 9, 7, 3, 6, 1, 2, 4, 8, 5, 10, 9, 7);
	var aA = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
	var aP = new Array(17);
	var aB = new Array(17);
	var i, iSum = 0;
	for (i = 1; i < 18; i++) aP[i] = sId.substr(17 - i, 1);
	for (i = 1; i < 18; i++) {
		aB[i] = parseInt(aP[i]) * parseInt(aW[i]);
		iSum += aB[i]
	}
	return sId + aA[iSum % 11]
}
function CalID_15to18(sId) {
	return CalID_17to18(sId.substr(0, 6) + "19" + sId.substr(6))
}
function UpID(sId) {
	var s = sId.trim();
	if (s.length == 15) {
		if (isNaN(sId)) return false;
		if (verifyDate(s.substr(10, 2), s.substr(8, 2), s.substr(6, 2))) return CalID_15to18(s);
		else return false
	} else if (s.length == 17) {
		if (isNaN(s)) return false;
		if (verifyDate(s.substr(12, 2), s.substr(10, 2), s.substr(6, 4))) return CalID_17to18(s);
		else return false
	} else if (s.length == 18) {
		if (isNaN(s.substr(0, 17))) return false;
		if (verifyDate(s.substr(12, 2), s.substr(10, 2), s.substr(6, 4))) {
			if (CalID_17to18(s.substr(0, 17)) == s) return s;
			else return false
		} else return false
	}
	return false
}
function is_pageurl(handle) {
	return true
}
function is_password(handle) {
	return true
}
function is_dirctoryurl(handle) {
	var pattern = /^[a-zA-Z0-9-_,.\\u4E00-\u9FA5]{0,200}$/;
	if (!pattern.exec(handle.val())) return "此路径无效.";
	return true
}
function is_noquotation(handle) {
	var pattern = /(['%])/;
	if (pattern.exec(handle.val())) return "不能输入'或%号.";
	return true
}
function checkDecimal(val, decimalLen) {
	if (val.indexOf('.') > 0) {
		val = val.substr(val.indexOf('.') + 1, val.length);
		if ((val.length) <= decimalLen) {
			return true
		}
		return $.info.vDecimal(decimalLen)
	} else {
		return true
	}
}
function is_special(handle) {
	var pattern = /^[\u4e00-\u9fa5\da-zA-Z\-\/,()]+$/;
	if (!pattern.exec(handle.val())) return "不能输入数字、字母、中文和-,()/以外的字符.";
	return true
}
function calculateLength(strTemp) {
	if (strTemp == null) return 0;
	var i, sum;
	sum = 0;
	for (i = 0; i < strTemp.length; i++) {
		if ((strTemp.charCodeAt(i) >= 032) && (strTemp.charCodeAt(i) <= 126)) sum = sum + 1;
		else sum = sum + 2
	}
	return sum
}
function calculateWidth(strTemp) {
	if (strTemp == null) return 0;
	var i, sum;
	sum = 0;
	for (i = 0; i < strTemp.length; i++) {
		if ((strTemp.charCodeAt(i) >= 032) && (strTemp.charCodeAt(i) <= 126)) sum = sum + 6;
		else sum = sum + 10
	}
	return sum
}
Date.prototype.Format = function(fmt) {
	var o = {
		"M+": this.getMonth() + 1,
		"d+": this.getDate(),
		"h+": this.getHours(),
		"m+": this.getMinutes(),
		"s+": this.getSeconds(),
		"q+": Math.floor((this.getMonth() + 3) / 3),
		"S": this.getMilliseconds()
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o) if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt
};
function isNumber(str) {
	var digits = "0123456789";
	var i = 0;
	if (str == null) return false;
	var sLength = str.length;
	if (sLength == 0) return false;
	while ((i < sLength)) {
		var c = str.charAt(i);
		if (digits.indexOf(c) == -1) return false;
		i++
	}
	return true
}
function amountFormat(val) {
	if (!val) return "";
	return zeroFormat(numberFormat('#,###.00', val))
}
function zeroFormat(val) {
	if ('0.00' == val) {
		return '0'
	} else {
		return val
	}
}
function checkUnChar(val) {
	if ((/[`&^\\]/).test(val)) {
		return true
	} else return false
}
function numberFormat(pattern, val) {
	var oldVal = val;
	if (val < 0) {
		val = -val
	}
	var _nfPattern = "[0#]+[,]?[0#]*(\\.[0#]+)?";
	if (!pattern || !(new RegExp(_nfPattern, 'g').test(pattern))) {
		alertMsg.error('numberFormat() invalide pattern parameter : "' + pattern + '"');
		return
	}
	if (!isNumber((new String(val)).replace(".", ""))) {
		alertMsg.error('numberFormat() invalide number parameter : "' + val + '"');
		return
	}
	var strAry = pattern.match(new RegExp(_nfPattern, 'g'));
	p1 = strAry[0].indexOf('.');
	if (p1 > 0) {
		len = strAry[0].length - p1 - 1;
		val = Math.round(val * Math.pow(10, len)) / Math.pow(10, len)
	} else {
		val = Math.round(val)
	}
	val = String(val);
	p2 = val.indexOf('.');
	var ret = '';
	if (p1 > 0) {
		for (var i = p1 + 1; i < strAry[0].length; i++) {
			ch = (p2 > 0) && (p2 + i - p1 < val.length) ? val.charAt(p2 + i - p1) : '';
			if (strAry[0].charAt(i) == '0') {
				ret = ret + (ch == '' ? '0': ch)
			} else {
				ret = ret + (ch == '' ? '': ch)
			}
		}
		ret = ret == '' ? '': ('.' + ret)
	}
	p3 = strAry[0].indexOf(',');
	if (p3 > 0) {
		len = p1 > 0 ? p1 - p3 - 1 : strAry[0].length - p3 - 1;
		len2 = p2 > 0 ? p2 - 1 : val.length - 1;
		for (var i = len2; i >= 0; i--) {
			ret = ((len2 - i + 1) % len == 0 ? ',': '') + val.charAt(i) + ret
		}
		ret = ret.charAt(0) == ',' ? ret.substring(1, ret.length) : ret
	} else {
		ret = val.substring(0, p2 > 0 ? p2: val.length) + ret
	}
	if (oldVal < 0) {
		ret = "-" + ret
	}
	return pattern.replace(strAry[0], ret)
}
function is_all_digit_2(handle) {
	var pattern = /^((\-[1-9]\d*)|(([1-9]\d*)|0))(\.\d{1,2})?$/;
	if (!pattern.exec(handle.val())) return "请输入正确的数字（包含负数）";
	return true
}
(function($) {
	$.fn.extend({
		tabOptions: {
			lineH: 25
		},
		combined: function(action) {
			var s = "";
			var $this = $(this);
			if ($this.get(0) && ($this.get(0).tagName.toUpperCase() == "INPUT" || $this.get(0).tagName.toUpperCase() == "TEXTAREA" || $this.get(0).tagName.toUpperCase() == "SELECT")) {} else {
				var sId, sDataSource, sValue, sCode, sOperation, sKind, sDateFormat = 'YYYY-MM-DD',
				sAction;
				$("input,textarea,select", $this).each(function() {
					var $this = $(this);
					var tagName = $this.get(0) ? $this.get(0).tagName: "";
					if (tagName == "SELECT") {
						if ($this.val() == -1 || $this.attr("code") == -1) {
							if (action) {
								$this.attr("value", "");
								$this.attr("code", "")
							} else return true
						}
					}
					if ($this.attr("type") == "radio" || $this.attr("type") == "checkbox") return true;
					if ($this.attr("action") && $this.attr("action").toLowerCase() == "ignore") return true;
					var len = 1;
					if (!action) len = $this.val().length;
					if ($this.attr("datasource") && len > 0) {
						if (tagName == "SELECT") sValue = format($this.attr("value"));
						else sValue = format($this.val());
						len = s.length;
						sId = $this.attr("id");
						sKind = $this.attr("kind") ? $this.attr("kind").toLowerCase() : "text";
						sDataSource = $this.attr("datasource").toUpperCase();
						sCode = "null";
						sAction = $this.attr("action") ? $this.attr("action").toLowerCase() : "null";
						var dataType = $this.attr("datatype");
						if ($this.hasClass("Wdate")) {
							sCode = $this.val();
							if ($this.attr("dateformat")) sDateFormat = $this.attr("dateformat");
							else sDateFormat = "YYYY-MM-DD"
						} else if (sKind == "dic") {
							if ($this.attr("code")) sCode = $this.attr("code")
						} else if ($this.attr("code")) sCode = $this.attr("code");
						sOperation = $this.attr("operation") ? $this.attr("operation") : "=";
						if (sKind == "dic" && $this.attr("src") == "ZZJG") sOperation = "left_like";
						if (sKind == "dic" && $this.attr("multi") == "true") {
							if (sValue.indexOf(",") > 0) {
								sOperation = "IN"
							} else sOperation = "="
						}
						sOperation = sOperation.replace(/</gi, "&lt;");
						sOperation = sOperation.replace(/>/, "&gt;");
						if (!sDateFormat) sDateFormat = "YYYY-MM-DD";
						if (!sOperation) sDateFormat = "=";
						$.fn.combined.defaultOptions.ID = sId;
						$.fn.combined.defaultOptions.DATASOURCE = sDataSource;
						$.fn.combined.defaultOptions.OPERATION = sOperation;
						$.fn.combined.defaultOptions.CODE = sCode;
						$.fn.combined.defaultOptions.VALUE = sValue;
						$.fn.combined.defaultOptions.KIND = sKind;
						$.fn.combined.defaultOptions.DATEFORMAT = sDateFormat;
						$.fn.combined.defaultOptions.ACTION = sAction;
						if (s == "") s += "\"" + sId + "\":" + makeCondition($.fn.combined.defaultOptions);
						else s += ",\"" + sId + "\":" + makeCondition($.fn.combined.defaultOptions)
					}
				})
			}
			if (action && action == 1) return "{" + s + "}";
			else {
				var conditions = appendPageInfo(s, $this, action);
				return "{" + conditions + "}"
			}
		},
		clearRows: function() {
			var $this = $(this);
			$("tr:not(:first)", $this).remove()
		},
		listResult: function(list, dType) {
			var $this = $(this);
			if (list != null && list.length > 0) {
				var totalColumnMap;
				if ($this.attr("total")) {
					totalColumnMap = new HashMap();
					$this.data("totalMap", totalColumnMap)
				}
				var fisCell = $(">thead>tr:last", $(this)).find("th:first");
				if (fisCell.attr("fieldname") != "ROWNUMS") {
					if ($this.attr("shownum") == "false") fisCell.before("<th style='display:none;' colwidth='28' align='center' fieldname='ROWNUMS'></th>");
					else fisCell.before("<th style='display:\"\";' colwidth='28' align='center' fieldname='ROWNUMS'></th>")
				}
				var $tr = null;
				if ($this.find(">tbody").size() > 0) $tr = $(">tbody", $this);
				else $tr = $this;
				var str = "";
				for (var i = 0; i < list.length; i++) {
					var newtr = [];
					newtr.push("");
					newtr.push("");
					newtr.push("");
					$this.setRowValue(dType, list[i], newtr, 1);
					if (i == 0) {
						newtr[0] = "<tr " + newtr[0] + " " + newtr[1] + ">" + newtr[2] + "</tr>";
						str += newtr[0]
					} else {
						if (i % 2 == 1) {
							newtr[0] = "<tr style='background-color:#F8F8F8'" + " " + newtr[1] + ">" + newtr[2] + "</tr>";
							str += newtr[0]
						} else {
							newtr[0] = "<tr " + newtr[0] + " " + newtr[1] + ">" + newtr[2] + "</tr>";
							str += newtr[0]
						}
					}
				}
				$tr.append(str);
				$("th", $this).each(function(index, element) {
					var $th = $(element);
					if (!$th.attr("refer")) return true;
					try {
						var $trs = null;
						if ($this.find(">tbody").size() > 0) $trs = $(">tbody>tr", $this);
						else $trs = $(">tr", $this);
						for (var r = 0; r < $trs.size(); r++) {
							var $refTd = $trs.eq(r).find("td").eq(index);
							var t = eval($th.attr("refer") + "($refTd)");
							if (t) $refTd.html(t)
						}
					} catch(e) {}
				});
				if ($this.attr("total") == "true") {
					var $tr = $("<tr class='totalSum'></tr>");
					if ($this.find(">tbody").size() > 0) $(">tbody>tr:last", $this).after($tr);
					else $("tr:last", $this).after($tr);
					var hcell = $(">thead>tr:last", $this);
					$(">th", hcell).each(function(index, element) {
						var $th = $(this);
						if ($th.is(":hidden")) {
							$tr.append("<td style='display:none'><div>0</div></td>");
							return true
						}
						if (index == 0) {
							$tr.append("<td><div >合计</div></td>")
						} else if ($th.attr("sum")) {
							var sum = $this.data("totalMap").get($th.attr("fieldname"));
							var fix = $th.attr("fix") ? $th.attr("fix") : 2;
							sum = new Number(sum).toFixed(fix);
							if ($th.attr("format")) {
								switch ($th.attr("format")) {
								case "money":
									sum = amountFormat(sum);
									break
								}
							}
							$tr.append("<td><div>" + sum + "</div></td>")
						} else $tr.append("<td><div>&nbsp;</div></td>")
					})
				}
			} else {
				if (showWhenNoResult) {
					var fisCell = $(">thead>tr:last", $this).find("th:first");
					if (fisCell.attr("fieldname") != "ROWNUMS") {
						if ($this.attr("shownum") == "false") fisCell.before("<th style='display:none;' colwidth='28' style='width:28px;' align='center' fieldname='ROWNUMS'></th>");
						else fisCell.before("<th style='display:\"\";' colwidth='28' style='width:28px;' align='center' fieldname='ROWNUMS'></th>")
					}
					var $tr = $("<tr style='vertical-align:middle;'></tr>");
					var $ths = $this.find("thead>tr:last-child").find("th:visible");
					var $td = $("<td colspan='" + $ths.size() + "'></td");
					$td.css("text-align", "center");
					$td.css("vertical-align", "middle");
					$td.html("<div style='color:red;' noresult='true'>" + $.info.showNoResult() + "</div>");
					$tr.append($td);
					if ($this.find(">tbody").size() > 0) $(">tbody:first", $this).append($tr);
					else $(">thead:last", $this).after($tr)
				}
			}
		},
		setRowValue: function(dType, node, newrow, action, isHidden) {
			var sDType = dType ? dType: "xml";
			var hrow = null;
			switch (action) {
			case 1:
				hrow = $(">thead>tr:last", $(this));
				break;
			case 2:
				if ($(this).attr("id").indexOf("_content") > 0) hrow = $("tr:first", $("#" + $(this).attr("id").replace("_content", "")));
				else hrow = $(">thead>tr:last", $(this));
				break;
			case 3:
				if ($(this).attr("id").indexOf("_content") > 0) hrow = $("tr:first", $("#" + $(this).attr("id").replace("_content", "")));
				else hrow = $("tr:first", $(this));
				break
			}
			switch (sDType) {
			case "xml":
				var xmls = "";
				var d = null,
				cNodes = null,
				clength = 0,
				editFlag = false;
				if (action == 3) {
					if (newrow && newrow.attr("rowdata")) editFlag = false;
					else editFlag = true
				}
				if (action == 3 && editFlag == false) {
					d = createDOMDocument();
					if (bOs == 1) try {
						d.loadXML(newrow.attr("rowdata"))
					} catch(e) {
						d = loadFromStr(newrow.attr("rowdata"))
					} else d = loadFromStr(newrow.attr("rowdata"));
					cNodes = d.childNodes[0].childNodes;
					clength = cNodes.length
				}
				var childNodes = node.childNodes;
				for (var j = 0; j < childNodes.length; j++) {
					var childNode = childNodes[j];
					if (childNode.childNodes.length > 1 && childNode.childNodes[0].nodeType == 1) {
						var childNodes2 = childNode.childNodes;
						for (var p = 0; p < childNodes2.length; p++) {
							var snode = childNodes2[p];
							var t = getNodeText(snode);
							if (snode.getAttribute("spaces")) {
								var n = snode.getAttribute("spaces");
								var s = "";
								while (n > 0) {
									s += " ";
									n--
								}
								t = s + t
							}
							if (action == 3) newrow.attr(childNode.nodeName + "_" + snode.nodeName, t);
							else newrow[1] += " " + childNode.nodeName + "_" + snode.nodeName + "=\"" + t + "\" ";
							var sv = snode.getAttribute("sv");
							if (sv) {
								if (action == 3) newrow.attr(childNode.nodeName + "_" + snode.nodeName + "_SV", sv);
								else newrow[1] += " " + childNode.nodeName + "_" + snode.nodeName + "_SV" + "=\"" + sv + "\" "
							}
							if (snode.getAttribute("sv")) xmls += "<" + snode.nodeName + " sv='" + snode.getAttribute("sv") + "'>" + t + "</" + snode.nodeName + ">";
							else xmls += "<" + snode.nodeName + ">" + t + "</" + snode.nodeName + ">"
						}
					} else {
						var snode = childNodes[j];
						var t = getNodeText(snode);
						if (snode.getAttribute("spaces")) {
							var n = snode.getAttribute("spaces");
							var s = "";
							while (n > 0) {
								s += " ";
								n--
							}
							t = s + t
						}
						if (action == 3) newrow.attr(snode.nodeName, t);
						else newrow[1] += " " + snode.nodeName + "=\"" + t + "\"";
						var sv = snode.getAttribute("sv");
						if (sv) {
							if (action == 3) newrow.attr(snode.nodeName + "_SV", sv);
							else newrow[1] += " " + snode.nodeName + "_SV" + "=\"" + sv + "\" "
						}
						if (action != 3) {
							if (snode.getAttribute("sv")) xmls += "<" + snode.nodeName + " sv='" + snode.getAttribute("sv") + "'>" + t + "</" + snode.nodeName + ">";
							else xmls += "<" + snode.nodeName + ">" + t + "</" + snode.nodeName + ">"
						} else {
							if (editFlag == true) {
								if (snode.getAttribute("sv")) xmls += "<" + snode.nodeName + " sv='" + snode.getAttribute("sv") + "'>" + t + "</" + snode.nodeName + ">";
								else xmls += "<" + snode.nodeName + ">" + t + "</" + snode.nodeName + ">"
							} else {
								for (var i = 0; i < clength; i++) {
									if (cNodes[i].nodeType == 1 && snode.nodeName == cNodes[i].nodeName) {
										if (snode.getAttribute("sv")) {
											cNodes[i].setAttribute("sv", snode.getAttribute("sv"));
											if (bOs == 1) try {
												cNodes[i].text = getNodeText(snode)
											} catch(e) {
												cNodes[i].textContent = getNodeText(snode)
											} else cNodes[i].textContent = getNodeText(snode)
										} else {
											if (bOs == 1) try {
												cNodes[i].text = getNodeText(snode)
											} catch(e) {
												cNodes[i].textContent = getNodeText(snode)
											} else cNodes[i].textContent = getNodeText(snode)
										}
									} else continue
								}
							}
						}
					}
				}
				if (action == 3) {
					if (editFlag == false) {
						if (bOs == 1) {
							var x = d.xml;
							if (x == "" || x == "undefined" || x == undefined) xmls += domToStr(d);
							else xmls += x
						} else xmls += domToStr(d)
					}
				}
				var $th = $(">th", hrow);
				var $td = null;
				if (action == 3) $td = $(">td", newrow);
				var cell = "";
				for (var j = 0,
				n = 0; j < $th.size(); j++) {
					var hcell = $th.eq(j);
					var fieldname = hcell.attr("fieldname");
					var cellAttr = "";
					var cellHTML = "";
					if (action == 3) {
						cell = $td.eq(j);
						if (!fieldname || "ROWNUMS" == fieldname.toUpperCase()) {
							n++;
							continue
						}
					}
					if (fieldname) if ("ROWNUMS" == fieldname.toUpperCase()) {
						if (action == 3) cell.css("background-color", "#E0ECFF");
						else {
							if (hcell.is(":hidden")) cellAttr += " style='display:none;border-left:solid 1px #99BBE8;background-color:#E0ECFF;text-align:center'";
							else cellAttr += " style='border-left:solid 1px #99BBE8;background-color:#E0ECFF;text-align:center;'"
						}
					}
					if (action == 2) {
						var w = hcell.attr("lwidth") ? hcell.attr("lwidth") : hcell.width();
						cellAttr += " width='" + w + "px' "
					}
					if (hcell.is(":hidden")) {
						if (action == 3) cell.hide();
						else cellAttr += " style='display:none'"
					}
					if (action != 3) {
						var m = processCell(dType, hcell, cell, action, node, isHidden);
						if (m) {
							if (hcell.is(":hidden")) cell += "<td style='display:none'>" + m + "</td>";
							else {
								if (action == 2) {
									switch (hcell.attr("type")) {
									case "single":
										cell += "<td width='20px;' style='text-align:center;'>" + m + "</td>";
										break;
									case "multi":
										cell += "<td width='20px;' style='text-align:center;'>" + m + "</td>";
										break;
									case "link":
										if (hcell.attr("colwidth")) cell += "<td width='" + hcell.attr("colwidth") + "px'>" + m + "</td>";
										else cell += "<td >" + m + "</td>";
										break
									}
								} else cell += "<td style='text-align:left;'>" + m + "</td>"
							}
							n++;
							continue
						}
					} else {
						if (hcell.attr("type") == "single" || hcell.attr("type") == "multi" || hcell.attr("type") == "link") {
							n++;
							continue
						}
					}
					var sv = "";
					var tx = "";
					if (fieldname) {
						var child = null;
						if (hcell.attr("prefix")) {
							child = node.getElementsByTagName(hcell.attr("prefix").toUpperCase() + "/" + fieldname.toUpperCase())[0]
						} else {
							child = node.getElementsByTagName(fieldname.toUpperCase())[0]
						}
						if (child) {
							sv = child.getAttribute("sv");
							tx = reFormat(getNodeText(child));
							if (!hcell.attr("fieldname")) {
								hcell.attr("fieldname", child.tagName)
							}
							n++
						} else {
							if (hcell.attr("fieldname") == "ROWNUMS") {
								sv = "0";
								cellAttr += " align='center' "
							} else {
								if (action == 3) {
									tx = cell.text();
									sv = cell.attr("sv")
								}
							}
							n++
						}
						if (hcell.attr("sum")) {
							var fieldName = hcell.attr("fieldname");
							var preTotalSum = $(this).data("totalMap").get(hcell.attr("fieldname"));
							preTotalSum = preTotalSum ? preTotalSum: 0;
							$(this).data("totalMap").put(fieldName, parseFloat(preTotalSum, 10) + parseFloat(tx, 10))
						}
					} else {
						if (hcell.attr("fieldname") == "ROWNUMS") {
							sv = "0";
							cellAttr += " align='center' "
						}
						n++
					}
					if (sv != null && sv != "undefined") if (action == 3) cell.attr("code", tx);
					else cellAttr += " code='" + tx + "'";
					sv = sv ? sv: tx;
					var maxlength = hcell.attr("maxlength");
					var len = calculateLength(sv);
					if (maxlength) {
						var w = hcell.width() - 0;
						if (w >= len * 5) {
							if (action == 3) cell.html(sv);
							else cellHTML = sv
						} else {
							if (action == 3) {
								if (sv && sv.length > maxlength) cell.html(sv.substr(0, maxlength - 0) + "...");
								else cell.html(sv.substr(0, maxlength - 0) + "")
							} else if (sv && sv.length > maxlength) cellHTML = (sv.substr(0, maxlength - 0) + "...");
							else cellHTML = (sv.substr(0, maxlength - 0) + "")
						}
						if (action == 3) {
							cell.attr("title", sv)
						} else cellAttr += " title='" + sv + "'"
					} else {
						if (action == 3) cell.html(sv);
						else cellHTML = sv;
						if (action == 3) cell.attr("title", sv);
						else cellAttr += " title='" + sv + "'"
					}
					if (action != 3) cell += "<td" + cellAttr + ">" + cellHTML + "</td>"
				}
				if (action != 3) {
					newrow[2] = cell;
					newrow[1] += " rowdata=\"" + "<ROW>" + xmls + "</ROW>" + "\" "
				} else {
					if (editFlag == false) newrow.attr("rowdata", "" + xmls + "");
					else newrow.attr("rowdata", "<ROW>" + xmls + "</ROW>")
				}
				d = null;
				break;
			case "json":
				var jsons = "";
				$.each(node,
				function(key, element) {
					if (typeof(element) != "object") return true;
					var t = element["va"];
					if (element["spaces"]) {
						var n = element["spaces"];
						var s = "";
						while (n > 0) {
							s += " ";
							n--
						}
						t = s + t
					}
					newrow.attr(key, t);
					var sv = element["sv"];
					if (sv) {
						newrow.attr(key + "_SV", sv)
					}
					if (jsons) {
						if (sv) jsons += ",{\"sv\":\"" + sv + "\",\"" + key + "\":\"" + t + "\"";
						else jsons += ",\"" + key + "\":\"" + t + "\""
					} else {
						if (sv) jsons += "{\"sv\":\"" + sv + "\",\"" + key + "\":\"" + t + "\"";
						else jsons += "\"" + key + "\":\"" + t + "\""
					}
					jsons = "{" + jsons + "}"
				});
				var $th = $(">th", hrow);
				for (var j = 0,
				n = 0; j < $th.size(); j++) {
					var hcell = $th.eq(j);
					var fieldname = hcell.attr("fieldname");
					var cell = $("<td></td");
					if (fieldname) if ("ROWNUMS" == fieldname.toUpperCase()) {
						cell.css("background-color", "#E0ECFF")
					}
					if (action == 2) cell.width(hcell.width());
					if (hcell.attr("visible") == "hidden") cell.hide();
					var m = processCell(dType, hcell, cell, action, node, isHidden);
					if (m) {
						newrow.append(cell);
						continue
					}
					newrow.append(cell);
					var sv = null;
					var tx = null;
					if (fieldname) {
						var child = null;
						if (hcell.attr("prefix")) {
							child = node[hcell.attr("prefix").toUpperCase() + "/" + fieldname.toUpperCase()]
						} else {
							child = node[fieldname.toUpperCase()]
						}
						if (child == undefined) {
							sv = "undefined";
							tx = "undefined"
						} else if (typeof(child) == "object") {
							sv = child["sv"];
							tx = child["va"]
						} else {
							tx = child
						}
						n++;
						if (hcell.attr("sum")) {
							var fieldName = hcell.attr("fieldname");
							var preTotalSum = $(this).data("totalMap").get(fieldname);
							preTotalSum = preTotalSum ? preTotalSum: 0;
							$(this).data("totalMap").put(fieldName, parseFloat(preTotalSum, 10) + parseFloat(tx, 10))
						}
					}
					sv = sv ? sv: tx;
					cell.attr("code", tx);
					if (hcell.attr("refer")) {
						var t = sv;
						try {
							cell.html(sv);
							t = eval(hcell.attr("refer") + "(cell)")
						} catch(e) {}
						if (t) cell.html(t)
					} else {
						var maxlength = hcell.attr("maxlength");
						var len = calculateLength(sv);
						if (maxlength && (maxlength * 2 - 0 < len)) {
							var w = hcell.width() - 0;
							if (w >= len * 5) cell.html(sv);
							else {
								cell.html(sv.substr(0, hcell.attr("maxlength") - 0) + "...")
							}
							cell.attr("title", sv)
						} else {
							if (sv.length > 20) cell.html(sv.substr(0, 19) + "...");
							else cell.html(sv);
							cell.attr("title", sv)
						}
					}
				}
				newrow.attr("rowdata", jsons);
				break
			}
		},
		doSort: function($cell) {
			var $table = $(this);
			var thead = $table.find("thead");
			var lastH = $(">tr:last-child", thead);
			var sIndex = -1;
			$(">th", lastH).each(function(index, element) {
				var $this = $(this);
				if ($this.text() != $cell.text() && ($this.hasClass("desc") || $this.hasClass("asc"))) {
					$this.attr("order", "none")
				} else if ($this.text() == $cell.text()) {
					sIndex = index
				}
			});
			if (sIndex > -1) g_iSortIndex = sIndex;
			else return false;
			if (!$cell.attr("ordertype")) $cell.attr("ordertype", "local");
			$cell.attr("ordertype", $cell.attr("ordertype").toLowerCase());
			if ($cell.attr("ordertype") == 'local') {
				var sortrows = [];
				var $rows = $("tr", $("#" + $table.attr("id") + "_content"));
				for (var i = $rows.size(); i > 0; i--) {
					sortrows[sortrows.length] = $rows[i - 1]
				}
				if ($cell.hasClass("asc")) {
					sortrows.sort(sortTabListRowAsc);
					$cell.removeClass("asc");
					$cell.addClass("desc")
				} else if ($cell.hasClass("desc")) {
					sortrows.sort(sortTabListRowDesc);
					$cell.removeClass("desc");
					$cell.addClass("asc")
				}
				var ths = $(">th:visible", lastH);
				$("td:visible", sortrows[0]).each(function(index, element) {
					var $cell = $(this);
					$cell.width(ths.eq(index).attr("lwidth"))
				});
				var $tableC = $("#" + $table.attr("id") + "_content");
				for (var i = 0; i < sortrows.length; i++) {
					if ($tableC.find(">tbody>tbody").size() > 0) $("tbody>tbody", $tableC).append(sortrows[i]);
					else $tableC.append(sortrows[i])
				}
			} else {}
		},
		setPageInfo: function(currentpagenum, totalpagenum, recordsperpage, countrows, dType) {
			var $this = $(this);
			if (parseInt(countrows, 10) >= parseInt(recordsperpage, 10) && $("#pageinfo_" + $this.attr("id")).size() == 0) {
				var p = $("#" + $this.attr("ref"));
				var panelBar = $("<div class='panelBar' id='panel_" + $this.attr("id") + "' style='display:'></div>");
				p.after(panelBar);
				var pages = $("<div class='pages'></div>");
				if ("1" == syslanguage) pages.append("<span>显示</span");
				else pages.append("<span>Show</span");
				var sel = $("<select class='combox' name='numPerPage' id='sel_" + $this.attr("id") + "'></select>");
				var options;
				switch (parseInt(recordsperpage, 10)) {
				case 10:
					options = "<option value='10' selected>10</option><option value='20'>20</option><option value='30'>30</option>";
					break;
				case 20:
					options = "<option value='10' >10</option><option value='20' selected>20</option><option value='30'>30</option>";
					break;
				case 50:
					options = "<option value='10' >10</option><option value='20' >20</option><option value='30' selected>30</option>";
					break;
				case 100:
					options = "<option value='10' >10</option><option value='20' >20</option><option value='30' >30</option>";
					break;
				default:
					options = "<option value='" + recordsperpage + "' selected>" + recordsperpage + "</option><option value='10'>10</option><option value='20' >20</option><option value='30' >30</option>";
					break
				}
				sel.html(options);
				pages.append(sel);
				if ($.fn.combox) sel.combox();
				var totalPages = 0;
				if (countrows % recordsperpage == 0) totalPages = countrows / recordsperpage;
				else totalPages = Math.floor(countrows / recordsperpage) + 1;
				if ("1" == syslanguage) pages.append("<span>条，共：" + countrows + "条&nbsp;|&nbsp;" + totalPages + "页</span>");
				else pages.append("<span>records，Total：" + countrows + "&nbsp;items&nbsp;/&nbsp;" + totalPages + "&nbsp;pages</span>");
				panelBar.append(pages);
				var pagination = $("<div class='pagination' dType='" + dType + "' ref_table='" + $this.attr("id") + "' id='pageinfo_" + $this.attr("id") + "' targetType='' totalCount=" + countrows + " totalPage=" + totalPages + " numPerPage=" + recordsperpage + " pageNumShown=10 currentPage=" + currentpagenum + "></div>");
				panelBar.append(pagination);
				$("div.pagination", panelBar).each(function() {
					var $this = $(this);
					$this.pagination({
						targetType: $this.attr("targetType"),
						rel: $this.attr("rel"),
						dType: $this.attr("dType"),
						totalCount: $this.attr("totalCount"),
						numPerPage: $this.attr("numPerPage"),
						pageNumShown: $this.attr("pageNumShown"),
						currentPage: $this.attr("currentPage")
					})
				});
				$("#sel_" + $this.attr("id")).bind("change", {
					"tabId": $this.attr("id")
				},
				function(event) {
					pagination.goNewPage($(this), pagination, 1, $(this).val())
				})
			} else if (parseInt(countrows, 10) > parseInt(recordsperpage, 10) && $("#pageinfo_" + $this.attr("id")).size() > 0) {
				var panelBar = $("#panel_" + $this.attr("id"));
				$("div.pagination", panelBar).each(function() {
					var $this = $(this);
					$this.attr("currentPage", currentpagenum);
					$this.attr("totalCount", countrows);
					$this.attr("numPerPage", recordsperpage);
					$this.attr("pageNumShown", 10);
					var pages = panelBar.find("div.pages").eq(0);
					var totalPages = 0;
					if (countrows % recordsperpage == 0) totalPages = countrows / recordsperpage;
					else totalPages = Math.floor(countrows / recordsperpage) + 1;
					pages.find("span:last").remove();
					if ("1" == syslanguage) pages.append("<span>条，共：" + countrows + "条&nbsp;|&nbsp;" + totalPages + "页</span>");
					else pages.append("<span>records , Total：" + countrows + "&nbsp;items&nbsp;/&nbsp;" + totalPages + "&nbsp;pages</span>");
					pages.attr("totalPage", totalPages);
					$this.pagination({
						targetType: $this.attr("targetType"),
						rel: $this.attr("rel"),
						dType: $this.attr("dType"),
						totalCount: countrows,
						numPerPage: recordsperpage,
						pageNumShown: 10,
						currentPage: currentpagenum
					})
				})
			} else if (parseInt(countrows, 10) <= parseInt(recordsperpage, 10) && $("#pageinfo_" + $this.attr("id")).size() > 0) {
				var panelBar = $("#panel_" + $this.attr("id"));
				panelBar.remove()
			}
		},
		insertResult: function(node1, index, dType) {
			var sDType = dType ? dType: "xml";
			var node = null;
			node = node1.getElementsByTagName("ROW")[0];
			var newtr = [];
			newtr.push("");
			newtr.push("");
			newtr.push("");
			if (index < 0 || index >= $("tr", $(this)).size()) index = 1;
			var $this = null;
			var f = "1";
			if ($(this).attr("id").indexOf("_content") > 0) $this = $(this);
			else {
				var id = $(this).attr("id");
				if ($(this).is(":visible") == false) {
					$this = $(this);
					f = "2"
				} else $this = $("#" + $(this).attr("id") + "_content")
			}
			try {
				var d = $this.is(":hidden");
				if (d) {
					$this.show()
				}
				var fisCell = $(">thead>tr:last", $this).find("th:first");
				if ($this.attr("id").indexOf("_content") == -1) {
					if ($this.attr("shownum") == "false") fisCell.before("<th style='display:none;text-align:center;' colwidth='28' style='width:28px;' align='center' fieldname='ROWNUMS'></th>");
					else fisCell.before("<th style='display:;text-align:center;' colwidth='28' style='width:28px;' align='center' fieldname='ROWNUMS'></th>")
				}
				$this.setRowValue(sDType, node, newtr, 2, d);
				var newrow = "<tr bgcolor='#E8E7E4' " + newtr[1] + ">" + newtr[2] + "</tr>";
				var rows = null;
				if ($this.find(">tbody>tbody").size() > 0) rows = $(">tbody>tbody>tr", $this);
				else rows = $("tbody>tr", $this);
				if (rows.size() > 0) {
					if ($this.find(">tbody>tbody").size() > 0) $(">tbody>tbody>tr", $this).eq(index).before(newrow);
					else $("tr", $this).eq(index).before(newrow)
				} else {
					if ($this.find(">tbody").size() > 0) $(">tbody", $this).append(newrow);
					else $this.append(newrow)
				}
				var lh = $this.tabOptions.lineH * 10;
				if ($this.attr("id").indexOf("_content") > 0) {
					var $grid = $this.parent().parent();
					var $layoutH = $("#" + $this.attr("id").replace("_content", "")).attr("layoutH");
					if ($layoutH && "undefined" != $layoutH) lh = $layoutH;
					else {
						var rowssize = rows.size();
						var lineH = $this.tabOptions.lineH;
						if ((rowssize + 1) * lineH <= lh) {
							lh = (rowssize + 1) * lineH
						} else {
							$this.width($this.width() + 18);
							$("#" + $this.attr("id").replace("_content", "")).width($this.width())
						}
					}
					$grid.height(lh)
				}
				if (f == "2") $this.jTable();
				return index
			} catch(e) {
				alertMsg.error(e);
				return - 1
			}
		},
		updateResult: function(node1, index, dType) {
			var sDType = dType ? dType: "xml";
			var node = null;
			var domdoc = null;
			if (typeof(node1) == 'string') {
				domdoc = createDOMDocument();
				if (bOs == 1) {
					try {
						domdoc.loadXML(node1)
					} catch(e) {
						domdoc = loadFromStr(node1)
					}
				} else domdoc = loadFromStr(node1);
				node = domdoc.getElementsByTagName("ROW").item(0)
			} else node = node1.getElementsByTagName("ROW").item(0);
			var $this = null;
			if ($(this).attr("id").indexOf("_content") > 0) $this = $(this);
			else $this = $("#" + $(this).attr("id") + "_content");
			var rows = $("tr", $this);
			if (index > -1 && index < rows.size()) {
				var row = rows.eq(index);
				$this.setRowValue(sDType, node, row, 3)
			} else {
				index = this.getSelectedIndex();
				if (syslanguage == "1") if (index < 0) return alertMsg.warn("\u8bf7\u9009\u62e9\u8981\u64cd\u4f5c\u7684\u6570\u636e\u884c\uff01");
				else if (index < 0) return alertMsg.warn("Please select rows of data to be operated！");
				var row = rows.eq(index);
				$this.setRowValue(sDType, node, row, 3)
			}
			domdoc = null
		},
		removeResult: function($rowobj) {
			var $this = null;
			if ($(this).attr("id").indexOf("_content") > 0) $this = $(this);
			else $this = $("#" + $(this).attr("id") + "_content");
			var rowIndex = $rowobj.attr("ROWNUMS");
			var $trs = $this.find("tr");
			var flag = 1;
			if ($trs.eq(0).attr("ROWNUMS") == rowIndex) flag = 1;
			else flag = 0;
			if ($rowobj) $rowobj.remove();
			if (flag == 1) {
				var $hrow = $("#" + $this.attr("id").replace("_content", "")).find("tr:last");
				var $ths = $hrow.find(">th");
				if ($this.find("tr").size() > 0) {
					$(">td", $this.find("tr:first")).each(function(i, e) {
						var $td = $(this);
						var $th = $ths.eq(i);
						$td.width($th.width())
					})
				}
			}
		},
		setSelect: function(index) {
			var ci = -1;
			var $this = $(this);
			var hrow = $("#" + $this.attr("id").replace("_content", "")).find("tr:last");
			var hcell = hrow.find("th");
			for (var i = 0; i < hcell.size(); i++) {
				if (hcell.eq(i).attr("type") && hcell.eq(i).attr("type").toLowerCase() == 'single') {
					ci = i;
					break
				}
			}
			if (ci == -1) {
				for (var i = 0; i < hcell.size(); i++) {
					if (hcell.eq(i).attr("type") && hcell.eq(i).attr("type").toLowerCase() == 'multi') {
						ci = i;
						break
					}
				}
			}
			var rows = $("tr", $this);
			if (index < rows.size() && index >= 0) {
				rows.eq(index).find(">td").get(ci).firstChild.checked = true;
				if ($this.attr("rowselect") && $this.attr("rowselect") == 'true') rows.eq(index).attr("selected", 'true');
				rows.eq(index).addClass("selected")
			} else alertMsg.error('Exceeds the maximum number of records !')
		},
		getSelectedRows: function() {
			var c = -1;
			var $this = null;
			if ($(this).attr("id").indexOf("_content") > 0) $this = $(this);
			else $this = $("#" + $(this).attr("id") + "_content");
			var $hTable = $("#" + $this.attr("id").replace("_content", ""));
			var hrow = $("tr:first", $hTable);
			$("th", hrow).each(function(index) {
				var $th = $(this);
				if ($th.attr("type")) {
					if ($th.attr("type") == "single") c = index;
					else if ($th.attr("type") == "multi") c = index;
					return false
				}
			});
			var srows = [];
			var j = 0;
			$("tr", $this).each(function() {
				var $tr = $(this);
				if (c > -1) {
					if ($tr.find("td").eq(c).find("input:first").is(":checked") == true) {
						srows[j++] = $tr
					} else if ($tr.attr("selected") && $tr.attr("selected") == "true" && $this.attr("rowselect") == 'true') {
						srows[j++] = $tr
					}
				} else if ($tr.attr("selected") && $tr.attr("selected") == 'true') srows[j++] = $tr
			});
			return srows
		},
		getSelectedIndex: function() {
			var $this = null;
			if ($(this).attr("id").indexOf("_content") > 0) $this = $(this);
			else $this = $("#" + $(this).attr("id") + "_content");
			var ci = -1;
			var hrow = null;
			if ($this.attr("id").indexOf("_content") > 0) hrow = $("tr:last-child", $("#" + $this.attr("id").replace("_content", "")));
			else hrow = $("tr:first", $this);
			var hcell = $(">th", hrow);
			for (var i = 0; i < hcell.size(); i++) {
				if (hcell.eq(i).attr("type") && hcell.eq(i).attr("type").toLowerCase() == 'single') {
					ci = i;
					break
				}
			}
			if (ci == -1) {
				for (var i = 0; i < hcell.size(); i++) {
					if (hcell.eq(i).attr("type") && hcell.eq(i).attr("type").toLowerCase() == 'multi') {
						ci = i;
						break
					}
				}
			}
			var rows = $("tr", $this);
			for (var i = 0; i < rows.size(); i++) {
				if (ci > -1) {
					if ($("input[type=radio],input[type=checkbox]", $(">td", rows.eq(i)).eq(ci)).attr("checked")) return i;
					else if (rows.eq(i).attr("selected") == 'true' && rows.eq(i).attr("rowselect") == 'true') return i
				} else {
					if (rows.eq(i).attr("selected") == 'true') return i
				}
			}
			return - 1
		},
		clearRowEdit: function($row, rowIndex) {
			$row.attr("isedit", "false");
			$row.css("background-color", "#fff");
			var $tab = null;
			if ($(this).attr("id").indexOf("_content") > 0) $tab = $(this);
			else $tab = $("#" + $(this).attr("id") + "_content");
			var $a = $row.find("td a");
			$a.each(function(index, el) {
				var $lc = $(this);
				var preT = $lc.attr("pretitle");
				$lc.attr("title", preT);
				$lc.text(preT)
			});
			$row.unbind("dblclick");
			$row.bind("dblclick",
			function(event) {
				if ($row.attr("isedit") == "false") $tab.setRowEdit($row, rowIndex);
				else {
					if (g_xDic && g_xDic.object.xWin.is(":visible")) g_xDic.hidden();
					$tab.updateResult($row.attr("rowdata"), rowIndex);
					$row.attr("isedit", "false");
					$row.css("background-color", "#fff");
					var $a = $row.find("td a");
					$a.each(function(index, el) {
						var $lc = $(this);
						var preT = $lc.attr("pretitle");
						$lc.attr("title", preT);
						$lc.text(preT)
					})
				}
			})
		},
		setRowEdit: function($row, rowIndex) {
			$row.css("background-color", "#dcdcdc");
			$row.attr("isedit", "true");
			$row.attr("erowIndex", rowIndex);
			var $tab = $(this);
			var $ths = $("thead>tr:last-child th", $("#" + $tab.attr("id").replace("_content", "")));
			formatRowHtml($tab, $ths, $row, 2);
			setStyle($row, 2)
		},
		createRow: function(op) {
			var $tab = null;
			if ($("#" + $(this).attr("id") + "_content").size() > 0 && $("#" + $(this).attr("id") + "_content").is(":visible")) $tab = $("#" + $(this).attr("id") + "_content");
			else $tab = $(this);
			var $tr = null;
			if ($tab.attr("id").indexOf("_content") > 0) {
				if ($tab.find("tbody").find("tr:first").find("td").size() == 1) {
					$tab.find("tbody").html("")
				}
				var $hTab = $("#" + $(this).attr("id").replace("_content", ""));
				var ths = $("thead>tr:last-child th", $hTab);
				$tr = $("<tr style='background-color:#dcdcdc'></tr>");
				$tr.attr("isedit", "true");
				formatRowHtml($tab, ths, $tr, 1);
				var iH = $tab.height();
				var clientHeight = document.documentElement.clientHeight;
				if ($tab.offset().top + parseInt(iH, 10) + 70 > clientHeight) {
					iH = clientHeight - $tab.offset().top - 70
				}
				$tab.parent().parent().height(iH + $tab.tabOptions.lineH);
				$hTab.width($tab.width());
				$tab.find("tbody").append($tr);
				setStyle($tr, 2)
			} else {
				var fisCell = $(">thead>tr:last", $tab).find("th:first");
				if (fisCell.attr("fieldname") != "ROWNUMS") {
					if ($tab.attr("shownum") == "false") fisCell.before("<th style='display:none;' colwidth='28' style='width:28px;' align='center' fieldname='ROWNUMS'></th>");
					else fisCell.before("<th style='display:\"\";' colwidth='28' style='width:28px;' align='center' fieldname='ROWNUMS'></th>")
				}
				var ths = $tab.find("thead>tr:last-child").find("th");
				$tr = $("<tr style='background-color:#dcdcdc'></tr>");
				formatRowHtml($tab, ths, $tr, 1);
				$tab.find("tbody").append($tr);
				$tab.jTable();
				var i = $tab.attr("id") + "_content";
				$("#" + i).find("tr:first").unbind("click");
				$("td", $("#" + i).find("tr:first")).each(function() {
					var $td = $(this);
					$("input", $td).each(function() {
						$input = $(this);
						var fdatatype = $input.attr("datatype");
						if (fdatatype && fdatatype.split(",")[0] == "0") $input.width($td.width() - 16);
						else $input.width($td.width() - 5)
					})
				});
				$("#" + i).find("tr:first").attr("isedit", "true");
				setStyle($("#" + i).find("tr:first"), 2)
			}
			return $tr
		}
	});
	function formatRowHtml($tab, $ths, $tr, iu) {
		var rowscount = $tab.find("tr").size();
		$ths.each(function(index, element) {
			var $th = $(this);
			var $td = null;
			if (iu == 1) $td = $("<td></td>");
			else {
				var tds = $tr.find("td");
				$td = tds.eq(index)
			}
			if ($th.is(":hidden")) $td.hide();
			var fType = $th.attr("type") || "";
			var fFieldname = $th.attr("fieldname") || "";
			if (fFieldname == "ROWNUMS") {
				if (iu == 2) return true;
				$td.width($th.width())
			}
			if (fType) {
				switch (fType.toLowerCase()) {
				case "single":
					var checkbox = "<input type='radio' style='width:20px;padding:2px 2px 4px 2px;' name='" + $th.attr("name") + "' onClick='doRadio(this);'/>";
					if (iu == 2) $td.html("");
					if ($tab.attr("id").indexOf("_content") > 0) {
						$td.html("<div>" + checkbox + "</div>")
					} else $td.html(checkbox);
					if (iu == 1) $td.width($th.width());
					break;
				case "multi":
					var checkbox = "<input type='checkbox' style='width:20px;padding:2px 2px 4px 2px' name='" + $th.attr("name") + "' onClick='doCheckbox(this);'/>";
					if (iu == 2) $td.html("");
					if ($tab.attr("id").indexOf("_content") > 0) {
						$td.html("<div>" + checkbox + "</div>")
					} else $td.html(checkbox);
					if (iu == 1) $td.width($th.width());
					break;
				case "link":
					var title = $th.attr("title");
					var action = $th.attr("action");
					var classes = $th.attr("class");
					var arr0 = null,
					arr1 = null,
					arr2 = null;
					if (title) arr0 = title.split("|");
					if (action) arr1 = action.split("|");
					if (classes) arr2 = classes.split("|");
					var s = "";
					for (var i = 0; i < arr0.length; i++) {
						if (s) s += "  ";
						var ss1 = "";
						var fTitle = arr0[i];
						if (i == 0) fTitle = "[\u4fdd\u5b58]";
						if (i == 0) ss1 = "if($(this.parentElement.parentElement.parentElement.parentElement.parentElement).attr('edit')=='true' && ($(this.parentElement.parentElement.parentElement).attr('isedit') == undefined || $(this.parentElement.parentElement.parentElement).attr('isedit') == 'false')){" + " $(this.parentElement.parentElement.parentElement).trigger('dblclick');} else eval(";
						var ss2 = "";
						if (i == 0) ss2 = "(this.parentElement.parentElement.parentElement))";
						else ss2 = "(this.parentElement.parentElement.parentElement)";
						if (arr2) s += "<a href='###' pretitle='" + arr0[i] + "' action='" + arr1[i] + "' title='" + fTitle + "' class='" + (arr2[i] || "op") + "' onClick=\"javascript:" + ss1 + arr1[i] + ss2 + "\">" + fTitle + "</a>";
						else s += "<a href='###' pretitle='" + arr0[i] + "' action='" + arr1[i] + "' title='" + fTitle + "' class='op' onClick=\"javascript:" + ss1 + arr1[i] + ss2 + "\">" + fTitle + "</a>"
					}
					if (iu == 2) $td.html("");
					if ($tab.attr("id").indexOf("_content") > 0) {
						$td.html("<div>" + s + "</div>");
						if (rowscount == 0) $td.width($th.width())
					} else $td.html(s);
					break
				}
			} else if (fFieldname) {
				var ftype = $th.attr("ftype") || "input";
				if (ftype == "input" && fFieldname != "ROWNUMS") {
					var fkind = $th.attr("fkind") || "";
					var fsrc = $th.attr("fsrc") || "";
					var fdatatype = $th.attr("fdatatype") || "1,is_null,30";
					var fvalue = $th.attr("fvalue") || "";
					var fonclick = $th.attr("fonclick") || "";
					var fonblur = $th.attr("fonblur") || "";
					var freadonly = $th.attr("freadonly") || "false";
					if (freadonly && freadonly == "true") {
						var s = "";
						if (iu == 2) {
							s = $td.attr("title") || ""
						} else {
							if (fvalue) s = fvalue
						}
						if ($tab.attr("id").indexOf("_content") > 0) {
							$td.html("<div>" + s + "</div>");
							if (rowscount == 0) $td.width($th.width())
						} else $td.html(s);
						$td.attr("title", s)
					} else {
						var fwidth;
						if (fdatatype && fdatatype.split(",")[0] == "0") fwidth = $th.width() - 16;
						else fwidth = $th.width() - 5;
						var $s = $("<input type='text' />");
						$s.attr("name", fFieldname);
						$s.attr("value", fvalue);
						if (fonclick) $s.attr("onclick", fonclick);
						if (fonblur) $s.attr("onblur", fonblur);
						if (iu == 1) {
							var rowsnum = $tab.find("tbody").find("tr").size();
							$s.attr("id", fFieldname + "-" + rowsnum)
						} else $s.attr("id", fFieldname + "-" + $tr.attr("erowIndex"));
						$s.attr("datasource", fFieldname);
						$s.attr("datatype", fdatatype);
						$s.width(fwidth);
						$s.height(20);
						if (fkind) $s.attr("kind", fkind);
						if (fsrc) $s.attr("src", fsrc);
						if (iu == 2) {
							$s.val($td.attr("title") || "");
							if (fkind) $s.attr("code", $td.attr("code") || $td.attr("title"))
						}
						if ($tab.attr("id").indexOf("_content") > 0) {
							if (iu == 2) $td.html("");
							$td.append($s);
							if (rowscount == 0) $td.width($th.width())
						} else {
							$td.attr("title", $s.val());
							$td.append($s)
						}
					}
				} else if (ftype == "select") {
					var fkind = $th.attr("fkind") || "dic";
					var fsrc = $th.attr("fsrc") || "";
					var fdatatype = $th.attr("fdatatype") || "1,is_null,30";
					var $sel = $("<select></select>");
					$sel.attr("type", "text");
					$sel.attr("name", fFieldname);
					if (iu == 1) {
						var rowsnum = $tab.find("tbody").find("tr").size();
						$sel.attr("id", fFieldname + "-" + rowsnum)
					} else $sel.attr("id", fFieldname + "-" + $tr.attr("erowIndex"));
					$sel.attr("datasource", fFieldname);
					$sel.attr("datatype", fdatatype);
					$sel.addClass("combox");
					if (fkind) $sel.attr("kind", fkind);
					if (fsrc) $sel.attr("src", fsrc);
					if (iu == 2) {
						if ($sel.attr("code") != null && $sel.attr("code") != "") {
							$sel.find("option").remove();
							var $option = $("<OPTION></OPTION>");
							$option.val($sel.attr("code"));
							$option.text($sel.val());
							$sel.append($option);
							$sel.attr("title", $sel.val());
							$sel.attr("code", $sel.attr("code"));
							var $a = $sel.parent().find("a:first");
							$a.attr("value", $sel.attr("code"));
							$a.text($sel.val())
						} else {
							$sel.find("option").remove();
							var $option = $("<OPTION></OPTION>");
							$option.val("-1");
							$option.text("--");
							$sel.append($option)
						}
					} else {
						var foption = $th.attr("foption");
						var fselCode = "",
						fselVal = "";
						if (foption) {
							fselCode = foption.split("|")[0];
							fselVal = foption.split("|")[1];
							$sel.val(fselVal);
							$sel.attr("title", fselVal);
							$sel.attr("code", fselCode)
						}
						if (fselCode != null && fselCode != "") {
							$sel.find("option").remove();
							var $option = $("<OPTION></OPTION>");
							$option.val(fselVal);
							$option.text(fselVal);
							$sel.append($option);
							$sel.attr("code", fselCode)
						}
					}
					if ($tab.attr("id").indexOf("_content") > 0) {
						if (iu == 2) $td.html("");
						$td.append($sel);
						if (rowscount == 0) $td.width($th.width())
					} else {
						$td.attr("title", $sel.val());
						$td.append($sel)
					}
				}
			}
			if (iu == 1) $tr.append($td)
		})
	}
	function sortTabListRowAsc(row1, row2) {
		var i = g_iSortIndex;
		if (!row1.cells[i].title || !row2.cells[i].title) return 1;
		return row1.cells[i].title > row2.cells[i].title ? 1 : row1.cells[i].title < row2.cells[i].title ? -1 : 0
	}
	function sortTabListRowDesc(row1, row2) {
		var i = g_iSortIndex;
		if (!row1.cells[i].title || !row2.cells[i].title) return - 1;
		return row1.cells[i].title > row2.cells[i].title ? -1 : row1.cells[i].title < row2.cells[i].title ? 1 : 0
	}
	function processCell(dType, cell, ncell, actions, node, isHidden) {
		var sDType = dType ? dType: "xml";
		if (cell.attr("type")) {
			switch (cell.attr("type").toLowerCase()) {
			case 'multi':
				var checkbox = "";
				if (node && cell.attr("unique")) {
					var s = "";
					switch (sDType) {
					case "xml":
						s = getNodeText(node.getElementsByTagName(cell.attr("unique"))[0]);
						break;
					case "json":
						s = node[cell.attr("unique")];
						break
					}
					checkbox = "<input type='checkbox'  style='width:20px;padding:2px 2px 4px 2px' name='" + cell.attr("name") + "' value='" + s + "' onClick='doCheckbox(this);'/>"
				} else {
					checkbox = "<input type='checkbox' style='width:20px;padding:2px 2px 4px 2px' name='" + cell.attr("name") + "' onClick='doCheckbox(this);'/>"
				}
				return checkbox;
				break;
			case 'single':
				var checkbox = "<input type='radio' style='width:20px;padding:2px 2px 4px 2px;' name='" + cell.attr("name") + "' onClick='doRadio(this);'/>";
				return checkbox;
				break;
			case 'link':
				var title = cell.attr("title");
				var action = cell.attr("action");
				var classes = cell.attr("class");
				var arr0 = null,
				arr1 = null,
				arr2 = null;
				if (title) arr0 = title.split("|");
				if (action) arr1 = action.split("|");
				if (classes) arr2 = classes.split("|");
				var s = "";
				for (var i = 0; i < arr0.length; i++) {
					if (s) s += "  ";
					var ss1 = "";
					if (i == 0) ss1 = "if($(this.parentElement.parentElement.parentElement.parentElement.parentElement).attr('edit')=='true' && ($(this.parentElement.parentElement.parentElement).attr('isedit') == undefined || $(this.parentElement.parentElement.parentElement).attr('isedit') == 'false')){" + " $(this.parentElement.parentElement.parentElement).trigger('dblclick');} else eval(";
					var ss2 = "";
					if (i == 0) ss2 = "(this.parentElement.parentElement.parentElement))";
					else ss2 = "(this.parentElement.parentElement.parentElement)";
					if (arr2) s += "<a href='###' action='" + arr1[i] + "' title='" + arr0[i] + "' class='" + (arr2[i] || "op") + "' onClick=\"javascript:" + ss1 + arr1[i] + ss2 + "\">" + arr0[i] + "</a>";
					else s += "<a href='###' action='" + arr1[i] + "' title='" + arr0[i] + "' class='op' onClick=\"javascript:" + ss1 + arr1[i] + ss2 + "\">" + arr0[i] + "</a>"
				}
				if (actions != 1 && isHidden == false) s = "<div>" + s + "</div>";
				return s;
				break;
			default:
				break
			}
		}
	}
	function appendPageInfo(sc, tab, action) {
		if (tab.isTag("form")) tab = tab.find("table:first");
		var queryTabId = $("table").filter("[refQuery='" + tab.attr("id") + "']:first").attr("id");
		var s = "#pageinfo_" + queryTabId;
		var currentpagenum, recordsperpage, countrows, totalpage;
		if (action && action == "1") currentpagenum = $(s).attr("currentPage") ? $(s).attr("currentPage") : 1;
		else currentpagenum = 1;
		var pageRows = 10;
		var p = $("#" + queryTabId).attr("pageRows");
		if (p == "undefined" || p == undefined) pageRows = 10;
		else pageRows = p;
		recordsperpage = 10;
		var n = $(s).attr("numPerPage");
		if (n == "undefined" || n == undefined) recordsperpage = pageRows;
		else recordsperpage = n;
		countrows = $(s).attr("totalCount") ? $(s).attr("totalCount") : 0;
		totalpage = $(s).attr("totalpage") ? $(s).attr("totalpage") : 0;
		var cs;
		if (sc) cs = sc + ",\"pageinfo\":{\"currentpagenum\":\"" + currentpagenum + "\",\"recordsperpage\":\"" + recordsperpage + "\",\"countrows\":\"" + countrows + "\",\"totalpage\":\"" + totalpage + "\"}";
		else cs = "\"pageinfo\":{\"currentpagenum\":\"" + currentpagenum + "\",\"recordsperpage\":\"" + recordsperpage + "\",\"countrows\":\"" + countrows + "\",\"totalpage\":\"" + totalpage + "\"}";
		return cs
	}
	$.fn.combined.defaultOptions = {
		ID: "null",
		DATASOURCE: "null",
		OPERATION: "=",
		VALUE: "null",
		KIND: "null",
		DATEFORMAT: "YYYY-MM-DD",
		ACTION: "null",
		CODE: "null"
	};
	function makeCondition(options) {
		try {
			var s = "";
			$.each(options,
			function(sName, sVal) {
				if (s == "") s += "\"" + sName + "\":\"" + sVal + "\"";
				else s += ",\"" + sName + "\":\"" + sVal + "\""
			});
			return "{" + s + "}"
		} catch(e) {
			return null
		}
	}
	function convertX(node) {
		var s = "";
		var childs = node.childNodes;
		if (childs) {
			for (var i = 0; i < childs.length; i++) {
				if (childs[i].getAttribute("sv")) s += "<" + childs[i].nodeName + " sv='" + childs[i].getAttribute("sv") + "'>" + getNodeText(childs[i]) + "</" + childs[i].nodeName + ">";
				else s += "<" + childs[i].nodeName + ">" + getNodeText(childs[i]) + "</" + childs[i].nodeName + ">"
			}
		}
		return s
	}
	function convertJ(o) {
		var r = [];
		if (typeof o == "string") return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
		if (typeof o == "undefined") return "undefined";
		if (typeof o == "object") {
			if (o === null) return "null";
			else if (!o.sort) {
				for (var i in o) r.push(i + ":" + obj2str(o[i]));
				r = "{" + r.join() + "}"
			} else {
				for (var i = 0; i < o.length; i++) r.push(obj2str(o[i]));
				r = "[" + r.join() + "]"
			}
			return r
		}
		return o.toString()
	}
})(jQuery);
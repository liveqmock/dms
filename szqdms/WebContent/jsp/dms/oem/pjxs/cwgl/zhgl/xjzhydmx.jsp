<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="xjzhydmx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<div id="xjzhydmxD">
			<table width="100%" id="xjzhydmxlb" name="xjzhydmxlb" style="display: none" >
				<thead>
					<tr>
						<th>异动日期</th>
						<th>账户类型</th>
						<th>操作类型</th>
						<th>异动金额</th>
						<th>凭证号码</th>
						<th>金税发票号</th>
						<th>外部单据号</th>
						<th>描述</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>2014-05-20</td>
						<td>定金账户</td>
						<td>打款</td>
						<td>10,000,000.00</td>
						<td>100A012233</td>
						<td>1953B012233</td>
						<td>1953C022231</td>
						<td></td>
					</tr>
					<tr>
						<td>2014-05-25</td>
						<td>定金账户</td>
						<td>扣款</td>
						<td>10,000,000.00</td>
						<td>100A012234</td>
						<td>1953B012234</td>
						<td>1953C022232</td>
						<td></td>
					</tr>
					<tr>
						<td>2014-05-28</td>
						<td>定金账户</td>
						<td>占用</td>
						<td>10,000,000.00</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>2014-05-20</td>
						<td>定金账户</td>
						<td>打款</td>
						<td>10,000,000.00</td>
						<td>100A012235</td>
						<td>1953B012235</td>
						<td>1953C022234</td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
$(function(){
	$("#xjzhydmxlb").show();
	$("#xjzhydmxlb").jTable();
});
//弹出窗体
var dialog = $("body").data("xjzhyd");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>
<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    <title>侦听控制台</title>
	<jsp:include flush="true" page="../common/crossDomainUrl.jsp"></jsp:include>
	<jsp:include flush="true" page="../common/constShare.jsp"></jsp:include>
	<!-- 公用自定义JS导入  -->
	<script type="text/javascript" src="../pub-js/third-lib/js-common-lib/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="../pub-js/third-lib/js-common-lib/json/json2.js"></script>
	<script type="text/javascript" src="../pub-js/third-lib/js-common-lib/utils/md5.js"></script>
	<script type="text/javascript" src="../xrainbow/serviceAxClient.js"></script>
	<script type="text/javascript" src="../pub-js/our-lib/common/common.js"></script>
	<script type="text/javascript">
		var areaId =11000;
	</script>	
	<script type="text/javascript" src="listenerConsole.js"></script>
	<link rel="stylesheet" href="../theme/css.css" type="text/css"></link>
	</head>
  <body topmargin="0" class="scrollbar" style="text-align:center;">
	<div class="current_path">
		<h3>客户关系管理(CRM) &gt;&gt; 侦听控制台</h3>
	</div>
	
	<div style="width:688px; *width:699px; _width:699px; height:440px;  background:url(../images/lis_bg.gif) no-repeat 0 32px; text-align:center; margin:0 auto; padding-left:10px; padding-right:11px; margin-top:30px;">    
	<img src="../images/lis_title.gif" style="float:left;"/>
	<table width="100%" align="center" height="235"
			cellpadding="1" cellspacing="0" style="border-collapse: collapse; margin:0 auto; clear:both;">
		<tbody>
		<tr>
			<td height="40" style="padding-top:8px;">
				<img border="0" id="all" align="left" src="../images/wtable_select_all.gif" alt="全选">
				<img border="0" id="cancel" align="left" src="../images/wtable_deselect_all.gif" alt="取消选择所有项">
				<div style="float:right; margin-right:10px; display:inline;">
				<input type="button" id="btnStart" name="btnStart" value="启动" class="button">
				<input type="button" id="btnStop" name="btnStop" value="停止" class="button">
				<%--<input type="button" id="btnSet" name="btnSet" value="设置" class="button">
				--%></div>
		  </td>
		</tr>
		<tr>
			<td valign="top">
				<div id="divtabListener" class="scrollDiv" style="width: 100%; height: 360px; overflow: auto">
					<table  id="tabListenerInfo"  width="100%" align="center"  height="100%"  cellpadding="0" cellspacing="0" style="border-collapse:collapse; border-top:1px solid #e1e1e1;">
					<tbody>
						<tr class="greytable" align="center">
							<td height="28">选择</td>
							<td height="28">名称</td>

							<td height="28">启动时间</td>
							<td height="28">成功消息</td>
							<td height="28">失败消息</td>
							<td height="28">命中率</td>
							<td height="28">线程数</td>
					
							<td height="28">状态<img id="initImg" border="0" src="../images/refresh.gif">					
							</td>
						</tr>
					</tbody>
					</table>
				</div>
			</td>
		</tr>
		</tbody>
	</table>
	</div>
	<div id="divCommitMask" style="z-index: 100003;height: 80px;width: 80px;position: absolute;"></div>
  </body>
</html>

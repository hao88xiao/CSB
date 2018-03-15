<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title>侦听数据监控台</title>
	<jsp:include flush="true" page="../common/crossDomainUrl.jsp"></jsp:include>
	<jsp:include flush="true" page="../common/constShare.jsp"></jsp:include>
	<!-- 公用自定义JS导入  -->
	<script type="text/javascript" src="../pub-js/third-lib/js-common-lib/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="../pub-js/third-lib/js-common-lib/json/json2.js"></script>
	<script type="text/javascript" src="../pub-js/third-lib/js-common-lib/utils/md5.js"></script>
	<script type="text/javascript" src="../xrainbow/serviceAxClient.js"></script>
	<script type="text/javascript" src="../pub-js/our-lib/common/common.js"></script>
	<script type="text/javascript" src="listenerDatamonitorConsole.js"></script>
	<link rel="stylesheet" href="../theme/css.css" type="text/css"></link>
	</head>
  <body topmargin="0" class="scrollbar" style="text-align:center;">
		<div class="current_path">
		<h3>客户关系管理(CRM) &gt;&gt; 侦听数据监控台</h3>
		</div>
		<div style="width:688px; *width:699px; _width:699px; height:440px;  background:url(../images/lis_bg.gif) no-repeat 0 32px; text-align:center; margin:0 auto; padding-left:10px; padding-right:11px; margin-top:30px;">
		<div style="height:40px;"><h3>侦听数据监控台</h3></div>
			<div id="divtabListener" style="position:relative;left:0px;width: 680px; height: 380px; overflow: auto">
			    <table  id="tabListenerInfo" width="100%" height="100%"  cellpadding="0" cellspacing="0" style="text-align:left;border-collapse:collapse; border-top:0px solid #e1e1e1;">
					<tbody>
						<tr class="greytable" height="10">
							<td height="28" align="center">侦听名称</td>
							<td height="28" align="center">表名</td>
							<td height="28" align="center">字段名</td>
							<td height="28" align="center">状态</td>
							<td height="28" align="center">数量</td>
							<td height="28" align="center">时间<img id="initImg" border="0" src="../images/refresh.gif">					
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div id="divCommitMask" style="z-index: 100003;height: 80px;width: 80px;position: absolute;"></div>
	</body>
</html>

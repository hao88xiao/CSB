<%@ page language="java" pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>营业侦听控制台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030" />
		<link rel="stylesheet" href="theme/css.css" type="text/css" />
		<link rel="stylesheet" href="./resources/css/style-all.css"
			type="text/css" title="styles1"></link>
		<link rel="alternate stylesheet" type="text/css"
			href="./resources/css/style-all-2.css" title="styles2" />
		<link rel="alternate stylesheet" type="text/css"
			href="./resources/css/style-all-3.css" title="styles3" />
		<link rel="stylesheet" href="theme/jquery-tab.css" type="text/css"></link>
		
		<jsp:include flush="true" page="./common/constShare.jsp"></jsp:include>

		<!-- 公用自定义JS导入  -->
		<script type="text/javascript" src="pub-js/our-lib/common/log.js"></script>
		<script type="text/javascript" src="pub-js/our-lib/common/Exception.js"></script>
		<script type="text/javascript" src="pub-js/third-lib/js-common-lib/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="pub-js/third-lib/js-common-lib/jquery/jquery.xmldom.js"></script>
		<script type="text/javascript" src="pub-js/third-lib/js-common-lib/jquery/jquery.alerts/jquery.alerts.js"></script>
		<script type="text/javascript" src="pub-js/third-lib/js-common-lib/json/json2.js"></script>
		<script type="text/javascript" src="pub-js/third-lib/js-common-lib/utils/md5.js"></script>
		<script type="text/javascript" src="xrainbow/serviceAxClient.js"></script>
		<script type="text/javascript" src="pub-js/third-lib/js-common-lib/jquery/jquery-tab.js"></script>
		<script type="text/javascript" src="pub-js/our-lib/common/common.js"></script>
			
		<script type="text/javascript">
			$(function() {
				//modify by xuwb 2013-4-11  不查询员工地区维度
				initTabsBak("listener/listenerConsole.jsp", 'Y');
			});		
		</script>
	</head>
	<body background="images/bg.gif">
		<div id="outer">
			<div id="tab_menu"></div>
			<div id="page"></div>
		</div>
	</body>
</html>

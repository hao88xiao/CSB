<%@ page language="java" pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>��������</title>
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030" />
		<jsp:include flush="true" page="../common/crossDomainUrl.jsp"></jsp:include>
		<jsp:include flush="true" page="../common/setSession.jsp"></jsp:include>
		<jsp:include flush="true" page="../common/setDomain.jsp"></jsp:include>	
		<jsp:include flush="true" page="../common/constShare.jsp"></jsp:include>			
		<script type="text/javascript" src="../pub-js/third-lib/js-common-lib/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="../xrainbow/serviceAxClient.js"></script>
		<script type="text/javascript" src="../pub-js/third-lib/js-common-lib/json/json2.js"></script>
		<script type="text/javascript" src="../pub-js/third-lib/js-common-lib/utils/md5.js"></script>
		<script type="text/javascript" src="../pub-js/our-lib/common/common.js"></script>
		<script type="text/javascript">
			var areaId = <%=request.getParameter("bssInnerAreaId")%>;
		</script>			
		<script type="text/javascript" src="listenerConfig.js"></script>
		<link rel="stylesheet" href="../theme/css.css" type="text/css"></link>
	</head>
	<body>
	    <table width="100%" border="1" align="center"   bordercolor="#BEBEBE"
				cellpadding="1" cellspacing="0" style="border-collapse:collapse">
		<tbody>
			<tr>
				<td>
					<TABLE width="100%" border="0" cellspacing="0"
					cellpadding="1">
						<tr>
							<td class="tb1">
								<div align="right">
									<input type="button" id="btnAdd"  class="button" onclick="insertRow()" value="���" />
									<input type="button" id="btnSave" class="button" onclick="submitRow()" value="����" />
									<input type="button" id="btnCannel" class="button" onclick="cannelRow()" value="ȡ��" />
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<table id="listenerSetUpInfo"  border="1" align="center"   cellpadding="1" cellspacing="0"  
						bordercolor="#BEBEBE" style="border-collapse:collapse" height="100%" width="100%">
						<tbody>
							<tr class="greytable" align="center">
								<td class="blue_5keepLine" width="10%">����</td>
								<td class="blue_5keepLine" width="100">����</td>								
								<td class="blue_5keepLine" width="100">��������</td>
								<td class="blue_5keepLine" width="100">��������</td>
								<td class="blue_5keepLine" width="100">ҵ��beanId</td>
								<td class="blue_5keepLine" width="100">��Ϣ����</td>
								<td class="blue_5keepLine" width="100">����߳���</td>
								<td class="blue_5keepLine" width="100">�߳��˯��(s)</td>
								<td class="blue_5keepLine" width="8%">����״̬</td>
								<td class="blue_5keepLine" width="100">����</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr class="list-white">
			<td>
				<div id="load" style="display:none">
					<div align="center">��ȴ�...</div>
				  	<p align="center"><img src="../images/load.gif" width="32" height="32"></p>
				</div>
			</td>
		</tr>
			</tbody>
		</table>
	</body>
</html>

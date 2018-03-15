/**
 * @description 前台js 异常类
 * @param {} jsObj
 * @return {}
 */
Exception = function(jsObj) {
	var errorDialog;
	var errorDialogTemplate = 
					"	<div class='titlebar'></div>" +
					"	<div style='padding:30px 0 0 20px'>" +
					"		<image src='{hostUrl}/resources/images/error.gif' style='float:left'/>" +
					"		<div style='float:left;height:110px;width:300px;' >"+
					"			<span style='padding-top:20px;font-size:24px;font-weight:bolder'>出..出错了..</span>" +
					"			<div id='errorDialog-message' style='padding-top:10px;padding-left:10px;margin:5px 10px 0 10px;'>" +
					"				<div >" +
					"					<div id='errorDialog-message-msg' class='scrollDiv'  style='max-height:100px;word-break:break-all;width:430px'>" +
					"						<span id='errorDialog-message-msg-title' style='font-size:14px;font-weight:bold'>异常信息:</span>" +
					"					</div>" +
					"				</div>"+
					"				<div id='errorDialog-message-code-wrap'>" +
					"					<div id='errorDialog-message-code'>" +
					"						<span id='errorDialog-message-code-title' style='font-size:14px;font-weight:bold'>异常编码:</span>" +
					"					</div>" +
					"				</div>"+
					"				<div>" +
					"					<span id='errorDialog-message-classname-title' style='font-size:14px;font-weight:bold'>类名:</span>" +
					"					<span id='errorDialog-message-classname'></span>" +
					"				</div>"+
					"			</div>"+
					"		</div>"+
					"		<div style='height:200px;clear:both'>"+
					"			<div id='errorDialog-message-more-wrap'>" +
					"				<input id='errorDialog-message-more-toggle' type='button' class='button_blue_order' value='更多信息'/>&nbsp;" +
					"				<input id='errorDialog-copy-to-clip' type='button' class='button_blue_order' value='复制'/><br/>" +
					"				<div id='errorDialog-message-more' class='scrollDiv' style='height:180px;width:600px;word-break:break-all'></div>" +
					"			</div>"+
					"		</div>"+
					"		<div style='clear:both;text-align:center;'>" +
					"			<input type='button' id='btnErrorDialogOk' class='button_blue_order' value='确定'/>"+
					"		</div>" +
					"</div>";
	/**
	 * 解析response.getErrorMessage()返回的xml
	 */
	var _analyseErrorMessage = function(errorMessageXml) {
		var errorObj = {};
		var errorMsgDom = $.xmlDOM(errorMessageXml);
		errorObj.msg = $("msg", errorMsgDom).text();
		errorObj.exceptionTrace = $("exceptionTrace", errorMsgDom).text();
		errorObj.code = $($("exception code", errorMsgDom)[0]).text();
		return errorObj;
	};
	var _showExcepDialog = function(clsName, message,msgXml){
		var url = window.location.href;
		var args = url.split("/");
		var hostUrl = args[0] + "//"+args[2]+"/"+args[3];
		var errorObj;
		var code = "";
		var exceptionTrace = "";
		if(msgXml){
			errorObj = _analyseErrorMessage(msgXml);
			code = errorObj.code;
			exceptionTrace = errorObj.exceptionTrace;
			exceptionTrace = errorObj.msg + exceptionTrace;
		}
		
		_saveExpLogToDb(message, code, exceptionTrace, clsName);
		//如果有遮罩层的话,先把遮罩层去掉
		$("#divCommitMask").hide();
		removeCover("divCommitMask");
		if(!document.getElementById('divErrorDialog')){
			errorDialog = document.createElement("div");
			errorDialog.id = "divErrorDialog";
			$(errorDialog).addClass("outerborder");
			$(errorDialog).hide();
			$(errorDialog).html(errorDialogTemplate.replace("{hostUrl}",hostUrl));
			document.body.appendChild(errorDialog);
			$("#errorDialog-message-classname",errorDialog).text(clsName);
			$("#errorDialog-message-msg",errorDialog).append(message);
			if(!code){
				$("#errorDialog-message-code-wrap",errorDialog).hide();
			}else{
				$("#errorDialog-message-code",errorDialog).append(code);
			}
			if(!exceptionTrace){
				$("#errorDialog-message-more-wrap",errorDialog).hide();
			}else{
				$("#errorDialog-message-more",errorDialog).append("<pre>"+exceptionTrace+"</pre>").css("overflow","scroll").hide();
				$("#errorDialog-message-more-toggle",errorDialog).toggle(function(){
					$("#errorDialog-message-more",errorDialog).show("slow");
				},function(){
					$("#errorDialog-message-more",errorDialog).hide("slow");
				});
				$("#errorDialog-copy-to-clip",errorDialog).click(function(){
					CommonUtils.copyClip($("#errorDialog-message-more",errorDialog).text());
				});
			}
			$("#btnErrorDialogOk",errorDialog).click(function(){
				$(errorDialog).remove();
				removeCover('divErrorDialog');
				
			});
			$(errorDialog).show();
			coverParent('divErrorDialog','indexModules',80);

		}
	};
	
	var _saveExpLogToDb = function(shortMsg, exceptionCode, exceptionMsg, fileClassName){
		var olId;
		try{
			if(order && order.CustOrder){
				var orderList = order.CustOrder.getOrderList();
				if(orderList && orderList.orderListInfo)
					olId = orderList.orderListInfo.olId;
			}
		}catch(e){}
		var param = {
			olId : olId ? olId : null,
			staffId : g_loginedStaffInfo.staffId,
			channelId : g_loginedStaffInfo.channelId,
			areaId : g_loginedStaffInfo.staffAreaId,
			exceptionCode : exceptionCode,
			exceptionMsg : exceptionMsg,
			shortMsg : shortMsg,
			fileClassName : fileClassName,
			state : 'A'
		};
//		var ajaxClient = new bss.serviceframework.ajax.client();
//		ajaxClient.callServiceAsJson("bss.bizHall.soFacade.saveLogSoException", param, function(response, scope){
//			if(response.getCode() != response.SUCCESS){
//				log.error("Exception.js 保存异常日志到后台出错:" + response.getErrorMessage());
//			}
//		});
	}
	return {
		showExcepDialog : function(clsName, message, msgXml){
			_showExcepDialog(clsName, message, msgXml);
		},
		saveExpLogToDb : _saveExpLogToDb
	}
}();
function initConsolePage()
{
	startProcess();
	var url = "listenerDatamonitor.action";
	$.get(url,createListenerDatamonitorInfo);
}

//初始化侦听数据监控页面
function createListenerDatamonitorInfo(dom)
{
	var src = $("#tabListenerInfo");
	//根据侦听数据量，如果侦听个数小于10个，重新计算表格的高度
	var listenerCount = $(dom).find('root').find('listenerData').size();
	if(listenerCount < 10){
		var lisTableHeight = 30 * ($(dom).find('root').find('listenerData').size() + 1);
		$("#tabListenerInfo").attr("height",lisTableHeight);
	}
	$("#tabListenerInfo .listener_list").remove();
	$(dom).find('root').find('listenerData').each(function(){
		var v_this = $(this);
		var listenerName = v_this.children('listenerName').text();
		var monitorTabelName = v_this.children('monitorTabelName').text();
		var monitorColumnName = v_this.children('monitorColumnName').text();
		if(monitorColumnName.indexOf("=") > -1 || monitorColumnName.indexOf("<>") > -1 || monitorColumnName == "1"){
			monitorColumnName = "state";
		}
		var monitorStatusName = v_this.children('monitorStatusName').text();
		var monitorDataStatus = v_this.children('monitorDataStatus').text();
		var monitorNum = v_this.children('monitorNum').text();
		var monitorDate = v_this.children('monitorDate').text();
		var threshold = v_this.children('threshold').text();
		var newrow = "";
		if(parseInt(monitorNum) > parseInt(threshold)){
			newrow = $("<tr class='listener_list' height='40px' bgcolor='yellow'/>");
		}else{
			newrow = $("<tr class='listener_list' height='40px'/>");
		}
		newrow.append($("<td width='30%' align='left'/>").text(listenerName));
		newrow.append($("<td width='22%' align='center'/>").text(monitorTabelName));
		newrow.append($("<td width='11%' align='right'/>").text(monitorColumnName));
		newrow.append($("<td width='10%' align='right'/>").text(monitorStatusName)).attr("title", monitorDataStatus);
		newrow.append($("<td width='7%' align='right'/>").text(monitorNum));
		newrow.append($("<td width='20%' align='right'/>").text(monitorDate));
		src.append(newrow);
	});
	endProcess();
}

//开始处理
function startProcess()
{
    $("#divCommitMask").show()
    coverParent("divCommitMask", "body");
}

//结束处理
function endProcess()
{
    $("#divCommitMask").hide();
    removeCover("divCommitMask");
}

$( function() {
	//DOM加载完成后注册事件
	$("input,img").slice(1,7).css({cursor:'pointer'});
	$("#initImg").bind("click",initConsolePage);
	//初始页面
	initConsolePage();
});


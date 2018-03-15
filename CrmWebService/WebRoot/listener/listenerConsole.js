function initConsolePage()
{
	startProcess();
	var url = "listenerConsole!checkListenerState.action"
			+ "?bssInnerAreaId="+areaId;
	$.get(url,createListenerListInfo);
}

function startListener()
{
	var selCount = 0;
	var objs = $("input:checked");
	if (objs.size() == 0)
	{
		alert("请选择要操作的侦听！");
		return;
	}
	startProcess();
	var areaCode = getAreaCode(areaId);
	var url = "listenerConsole!startListener.action"
			+ "?bssInnerAreaId="+areaId;
	objs.each(function(){
		$.get(url,{name:$(this).attr("name")});
	});
	//侦听N秒后刷新页面
	if(objs.size()>1){
		setTimeout(initConsolePage,8000);
	}
	else{
		setTimeout(initConsolePage,5000);
	}
}

function stopListener()
{
	var selCount = 0;
	var objs = $("input:checked");
	if (objs.size() == 0)
	{
		alert("请选择要操作的侦听！");
		return;
	}
	
	startProcess();
	var url = "listenerConsole!stopListener.action"
			+ "?bssInnerAreaId="+areaId;
	objs.each(function(){
		$.get(url,{name:$(this).attr("name")},stopListenerListInfo);
	});
	//侦听N秒后刷新页面
	if(objs.size()>1){
		setTimeout(initConsolePage,8000);
	}
	else{
		setTimeout(initConsolePage,5000);
	}	
}

//初始化侦听页面
function createListenerListInfo(dom)
{
	var src = $("#tabListenerInfo");
	//根据侦听数据量，如果侦听个数小于10个，重新计算表格的高度
	var listenerCount = $(dom).find('root').find('listener').size();
	if(listenerCount < 10){
		var lisTableHeight = 30 * ($(dom).find('root').find('listener').size() + 1);
		$("#tabListenerInfo").attr("height",lisTableHeight);
	}
	$("#tabListenerInfo .listener_list").remove();
	$(dom).find('root').find('listener').each(function(){
		var v_this = $(this);
		var listenerCode = v_this.children('listenerCode').text();
		var name = v_this.children('name').text();
		var description = v_this.children('description').text();
		var msgId = v_this.children('msgId').text();
		var msgContext = v_this.children('msgContext').text();
		
		var startDt = v_this.children('startDt').text();
		var msgSuccessCnt = v_this.children('msgSuccessCnt').text();
		var msgFailureCnt = v_this.children('msgFailureCnt').text();
		var msgHitRate = v_this.children('msgHitRate').text();
		var currentThreadCnt = v_this.children('currentThreadCnt').text();

		var imgSrc = "../images/unavail.gif";
		if (msgId == "0"){
			imgSrc = "../images/running.gif";
			
		}else if (msgId == "1"){
			imgSrc = "../images/stop.gif";
			
		}else if (msgId == "2"){
			imgSrc = "../images/unavail.gif";
			alert(msgContext);
			return;
		}
		var newrow = $("<tr class='listener_list'/>")
		newrow.append($("<td align='center'/>").append('<input type="checkbox" name="' + listenerCode + '" id="chk' + listenerCode + '">'));		
		newrow.append($("<td/>").text(name)).attr("title", description);;
		
		newrow.append($("<td/>").text(startDt));
		newrow.append($("<td/>").text(msgSuccessCnt));
		newrow.append($("<td/>").text(msgFailureCnt));
		newrow.append($("<td/>").text(msgHitRate));
		newrow.append($("<td/>").text(currentThreadCnt));
		
		newrow.append($("<td align='center'/>").append('<img id="img' + name + 'State" border="0" src="' + imgSrc + '" alt="状态">'));
		src.append(newrow);
	});
	endProcess();
}

//停止侦听
function stopListenerListInfo(dom)
{
	$(dom).find('root').find('listener').each(function(){
		var v_this = $(this);
		var name = v_this.children('name').text();
		var msgId = v_this.children('msgId').text();
		var msgContext = v_this.children('msgContext').text();
		var imgObj = $("#img" + name + "State");
		if (msgId == "0"){
			imgObj.src = "../images/stop.gif";
		}else if (msgId == "1"){
			alert(msgContext);
		}else if (msgId == "2"){
			alert(msgContext);
		}
	});
}

function setListener()
{
    var url = "listenerConfig.jsp" +
    "?bssInnerAreaId=" +
    areaId;
    +"&rand=" + Math.random();
    var iWidth = 790;
    var iHeight = 580;
    if (window.screen && window.screen.width == '800') {
        iWidth = window.screen.width * 0.9;
		iHeight =  window.screen.height * 0.8;
    }
    else 
        if (window.screen && window.screen.width > '1024') {
            iWidth = window.screen.width * 0.8;
			iHeight =  window.screen.height * 0.7;
        }
    var sFeathers = "dialogHeight:" + iHeight + "px;dialogWidth:" + iWidth + "px; center:true;help:off;resizable:yes;scroll:on;status:off";
    if ($.browser.msie) {
        window.showModalDialog(url, window, sFeathers);
    }
    else {
        var centerPos = getCenterPostition(iWidth, iHeight);
        sFeathers = "dialogWidth:" + iWidth + "px;dialogleft:" + centerPos.left + "px;dialogtop:" + centerPos.top + "px;dialogHeight:" + iHeight + "px;center:1;help:off;resizable:yes;scroll:on";
        window.showModalDialog(url, window, sFeathers);
    }
    initConsolePage();
}

//全选复选框
function allCheck()
{
	$("input[type=checkbox]").each(function() {
		this.checked = true; 
	});
}

//取消全选复选框
function cancelAllCheck()
{
	$("input[type=checkbox]").each(function() {
		this.checked = false; 
	});
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
	$("#all").bind("click",allCheck);
	$("#cancel").bind("click",cancelAllCheck);
	$("#btnStart").bind("click",startListener);
	$("#btnStop").bind("click",stopListener);
	$("#btnSet").bind("click",setListener);
	$("#initImg").bind("click",initConsolePage);
	//初始页面
	initConsolePage();
});


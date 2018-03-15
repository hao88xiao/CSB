window.onload = win_onload;
var dom;
var delData = new Array();
var delFlag = 1 ; //  0表示删除 ，1表示除删除其它操作
var delNum = 0;
var areaCode;
//页面加载时调用方法
function win_onload() {
	var url = "listenerConfig!checkListenerStateForConfig.action"
			+ "?bssInnerAreaId="+areaId;
	$.get(url, null, createListenerConfigInfo, null);
}

function formatTable(tableId) {
	$("#" + tableId + " tr:first").addClass("greytable");
	$("#" + tableId + " tr:not(:first)").addClass("list-white");
}

function createListenerConfigInfo(dom){
	if (dom != null)
	{
		var table = $("#listenerSetUpInfo");
		var listeners = $(dom).find("root").find("listener");
		listeners.each(function(i, domEle){
			v_domEle = $(domEle);
			var listenerCode = v_domEle.children('listenerCode').text();
			var name = v_domEle.children('name').text();
			var nameDesc = v_domEle.children('description').text();
			var className = v_domEle.children('className').text();
			var beanId = v_domEle.children('beanId').text();
			var processNumber = v_domEle.children('processNumber').text();
			var threadCount = v_domEle.children('threadCount').text();
			var maxSleepTime = v_domEle.children('maxSleepTime').text();
			var state = v_domEle.children('state').text();
			var msgId = v_domEle.children('msgId').text();
			table.append(insertRowAll(listenerCode, name, nameDesc, className, beanId, processNumber, threadCount, maxSleepTime, state, 1, msgId));
		});
		formatTable("listenerSetUpInfo");
		endProcess();
	}
}

// 创建侦听行中的指定列
function createListenerTd(name, nameDesc, type, tip) {
	var td = $("<td/>").attr("title", tip).attr("class", "blue_5keepLine");
	td.append($("<input type='hidden'/>").attr("name", name).attr("value", nameDesc));
	if(type == 1) {
		td.append($("<label>").attr("name", name).text(nameDesc));
		td.append($("<input type='text'style='border:2px inset;width:100%; display:none'/>").attr("name", name).attr("value", nameDesc).attr("title", tip));
	} else {
		td.append($("<label/>").text(nameDesc).css("display", "none"));
		td.append($("<input type='text'style='border:2px inset;width:100%;'/>").attr("name", name).attr("value", nameDesc).attr("title", tip));
	}
	return td;
}

//创建对配置进行操作的表格
function createOPTd(msgId, type){
	var modelBt = $("<input type='button' />").attr("disabled", msgId=="0" ? true : false).addClass(msgId=="0" ? "disable-button" : "button");
	var firstBt = modelBt.clone().attr("name", "modifyButton").attr("value", (type == 0 ? "确定" : "修改")).bind("click", modifyRow);
	var secondBt = modelBt.clone().attr("name", "deleButton").attr("value", "删除").bind("click", deleteRow);
	return $("<td/>").attr("class", "blue_5keepLine").append(firstBt).append(secondBt);	
}
//type指明是修改已有的行还是新增一行记录
//type=0新增；type=1修改
function insertRowAll(listenerCode, name, nameDesc, className, beanId, processNumber, threadCount, maxSleepTime, state, type, msgId) {
	var tr = $("<tr/>").attr("align", "center");
	tr.append(createListenerTd("listenerCode", listenerCode, type, "侦听编码"));
	tr.append(createListenerTd("name", name, type, "侦听的名称"));
	tr.append(createListenerTd("nameDesc", nameDesc, type, "侦听的名称描述"));
	tr.append(createListenerTd("className", className, type, "侦听主类，必须实现com.linkage.bss.commons.listener.ListenerThread抽象类"));
	tr.append(createListenerTd("beanId", beanId, type, "侦听线程需要用到的SMO在spring配置文件中的beanId"));
	tr.append(createListenerTd("processNumber", processNumber, type, "一次扫描处理多少条数据"));
	tr.append(createListenerTd("threadCount", threadCount, type, "线程的最大数量数"));
	tr.append(createListenerTd("maxSleepTime", maxSleepTime, type, "侦听最长休眠时间(s)"));
	tr.append(createListenerTd("state", state, type, "侦听状态，0 在用"));	
	tr.append(createOPTd(msgId, type));
	return tr;
}

// 为表格插入一个新空行
function insertRow() {
	var table = $("#listenerSetUpInfo");
	table.append(insertRowAll("", "", "", "", "", "", "", 0, 1));
	formatTable("listenerSetUpInfo");
}

// 编辑表格行
function modifyRow() {   	
	var v_this = $(this);
	var value;
	v_this.parent().parent().children().each(function(i, domEle){
        var name = $(domEle).find('label').attr('name');
        if (name != 'listenerCode') {
            var showCtrl;
            var hideCtrl;
            if (v_this.attr("value") == "修改") {
                value = "确定";
                hideCtrl = $(domEle).find("label");
                showCtrl = $(domEle).find("input[type='text']");
                showCtrl.attr("value", hideCtrl.text());
            }
            else {
                value = "修改";
                showCtrl = $(domEle).find("label");
                hideCtrl = $(domEle).find("input[type='text']");
                showCtrl.text(hideCtrl.attr("value"));
            }
            hideCtrl.css("display", "none");
            showCtrl.css("display", "block");
        }
	});
	v_this.attr("value", v_this.attr("value") == "修改" ? "确定" : "修改");		
}

// 删除表格行
function deleteRow() {
	var delTr = $(this).parent().parent();
	var listenerCode = delTr.find("input[name='listenerCode'][type='hidden']").attr("value");	
	var name = delTr.find("input[name='name'][type='hidden']").attr("value");
	var nameDesc = delTr.find("input[name='nameDesc'][type='hidden']").attr("value");
	var className = delTr.find("input[name='className'][type='hidden']").attr("value");
	var beanId = delTr.find("input[name='beanId'][type='hidden']").attr("value");
	var processNumber = delTr.find("input[name='processNumber'][type='hidden']").attr("value");
	var threadCount = delTr.find("input[name='threadCount'][type='hidden']").attr("value");
	var maxSleepTime = delTr.find("input[name='maxSleepTime'][type='hidden']").attr("value");
	var state = delTr.find("input[name='state'][type='hidden']").attr("value");
	
	if(listenerCode != "" && name != "" && nameDesc != "" && className != "" && beanId != "" && processNumber != "" && threadCount != "" && maxSleepTime != ""  && state != ""){
		delFlag = 0;
		delData[delNum] = +delFlag + "|" + nameDesc + "|" + className + "|" + beanId + "|" + processNumber + "|" + threadCount +"|" + maxSleepTime + "|" + listenerCode + "|"  + name + "|"+ state + "|";	
		delNum++;
	}
	$(this).parent().parent().remove();
	return;
	/*
	var deleButton = document.getElementsByName("deleButton");
	
	for(var i=0;i<deleButton.length;i++){
		if (deleButton[i] == this1){
			var tr = this1.parentNode.parentNode;
			var nameDesc = tr.cells[0].children(0).value;
			var listenerClass = tr.cells[1].children(0).value;
			var processNumber = tr.cells[2].children(0).value;
			var threadCount = tr.cells[3].children(0).value;
			var frequency = tr.cells[4].children(0).value;
			if(desc != "" && listenerClass != "" && processNumber != "" && threadCount != ""&& frequency != ""){
				delFlag = 0;
				delData[delNum] = +delFlag + "|" + desc + "|" + listenerClass + "|" + processNumber + threadCount +"|" + frequency +"|";	
				delNum++;
			}
			break;
		} 
	}
	*/
}

// 校验并保存修改结果
function submitRow() {
	var tb1 = document.getElementById("listenerSetUpInfo");
	var dataInfoArray = new Array();
	var i = 0;
	var j = 0;
	dataInfoArray = dataInfoArray.concat(delData);
	var modifyAddData = new Array();
	var hasExistError = false;
	$("#listenerSetUpInfo tr:not(:first)").each(function(i, domEle) {
		i++;
		if (!hasExistError) {
			var v_domEle = $(domEle);
			var newListenerCode = v_domEle.find("input[name='listenerCode'][type='text']").attr("value");
			var newName = v_domEle.find("input[name='name'][type='text']").attr("value");
			var newNameDesc = v_domEle.find("input[name='nameDesc'][type='text']").attr("value");
			var newClassName = v_domEle.find("input[name='className'][type='text']").attr("value");
			var newBeanId = v_domEle.find("input[name='beanId'][type='text']").attr("value");
			var newProcessNumber = v_domEle.find("input[name='processNumber'][type='text']").attr("value");
			var newThreadCount = v_domEle.find("input[name='threadCount'][type='text']").attr("value");
			var newMaxSleepTime = v_domEle.find("input[name='maxSleepTime'][type='text']").attr("value");
			var newState = v_domEle.find("input[name='state'][type='text']").attr("value");
			var oldListenerCode = v_domEle.find("input[name='listenerCode'][type='hidden']").attr("value");
			var oldName = v_domEle.find("input[name='name'][type='hidden']").attr("value");
			var oldNameDesc = v_domEle.find("input[name='nameDesc'][type='hidden']").attr("value");
			var oldClassName = v_domEle.find("input[name='className'][type='hidden']").attr("value");
			var oldBeanId = v_domEle.find("input[name='beanId'][type='hidden']").attr("value");
			var oldProcessNumber = v_domEle.find("input[name='processNumber'][type='hidden']").attr("value");
			var oldThreadCount = v_domEle.find("input[name='threadCount'][type='hidden']").attr("value");
			var oldMaxSleepTime = v_domEle.find("input[name='maxSleepTime'][type='hidden']").attr("value");
			var oldState = v_domEle.find("input[name='state'][type='hidden']").attr("value");
			if(newListenerCode == null || newListenerCode == "") {
				alert('第' + i + '个侦听的編碼描述为空，请注意填写正确的编码 ！');
				hasExistError = true;
				return;
			}
			if(newName == null || newName == "") {
				alert('第' + i + '个侦听的名称描述为空，请注意填写正确的名称！');
				hasExistError = true;
				return;
			}
			if(newNameDesc == null || newNameDesc == "") {
				alert('第' + i + '个侦听的名称描述为空，请注意填写正确的描述 ！');
				hasExistError = true;
				return;
			}
			if(newClassName == null || newClassName == "") {
				alert('第' + i + '个侦听的类为空，请注意填写正确的侦听类 ！');
				hasExistError = true;
				return;
			}
			if(newBeanId == null || newBeanId == "") {
				alert('第' + i + '个侦听的类为空，请注意填写正确的侦听需要的beanId ！');
				hasExistError = true;
				return;
			}
			if(newProcessNumber == null || newProcessNumber == "") {
				alert('第' + i + '个侦听的每次处理数量为空，请注意填写正确有效的每次处理数量——正整数 ！');
				hasExistError = true;
				return;
			}
			if(newThreadCount == null || newThreadCount == "") {
				alert('第' + i + '个侦听的起动线程数量为空，请注意填写正确有效的线程数量——正整数 ！');
				hasExistError = true;
				return;
			}
			if(newMaxSleepTime == null || newMaxSleepTime == "") {
				alert('第' + i + '个侦听的扫描频率为空，请注意填写正确有效的扫描频率——正整数 ！');
				hasExistError = true;
				return;
			}
			if(newState == null || newState == "") {
				alert('第' + i + '个侦听的状态为空，请注意填写正确的状态 ！');
				hasExistError = true;
				return;
			}			
			var patrn=/^[0-9]*[1-9][0-9]*/;
			if (!patrn.exec(newProcessNumber)) {
				alert('第' + i + '个侦听的每次处理量['+newProcessNumber +']' + '输入不合法，应为正整数！');
				hasExistError = true;
				return;
			}
			if (!patrn.exec(newThreadCount)) {
				alert('第' + i + '个侦听的线程数['+newThreadCount+']' + '输入不合法，应为正整数！');
				hasExistError = true;
				return;
			}
			if (!patrn.exec(newMaxSleepTime)) {
				alert('第' + i + '个侦听的扫描频率['+newFrequency+']' + '输入不合法，应为正整数！');
				hasExistError = true;
				return;
			}
			var patrnState=/[0-9]/;
			if (!patrnState.exec(newState)) {
				alert('第' + i + '个侦听的状态 ['+newState+']' + '输入不合法，应为一位正整数！');
				hasExistError = true;
				return;
			}
			//进一步校验类名称
			$.get('listenerConfig!checkListenerClass.action?bssInnerAreaId='+areaId, {newClassName : newClassName}, function(retMsg){
				if(retMsg != ""){
					alert(retMsg);
					return;
				}
			}, null);
			if(newListenerCode!= oldListenerCode || oldName != newName || oldNameDesc != newNameDesc  || oldClassName != newClassName || oldBeanId != newBeanId || oldProcessNumber != newProcessNumber || oldThreadCount != newThreadCount || oldMaxSleepTime != newMaxSleepTime || oldState != newState) {
				delFlag = 1;
				modifyAddData[j] = delFlag + "|" + encodeURIComponent(newNameDesc) + "|" + newClassName + "|" + newBeanId + "|" + newProcessNumber + "|" + newThreadCount +"|" + newMaxSleepTime + "|" + encodeURIComponent(newName) + "|" + newState +"|" + newListenerCode + "|";
				j++;
			}
		}
	});
	if (hasExistError) {
		return;
	}
	dataInfoArray = dataInfoArray.concat(modifyAddData);
	if(dataInfoArray.length == 0) {
		alert('没有任何修改的配置信息 ！');
		return;
	}
	url = "listenerConfig!saveListenerConfig.action"
			+ "?bssInnerAreaId="+areaId;
	$.get(url, {dataInfoArray : dataInfoArray.join(",")}, saveAndClose, "text");
	
}

// 保存并关闭弹出窗口
function saveAndClose(msg) {
	if(msg == 'success') {
		alert("侦听配置信息保存成功 ！");
		window.close();
	}
}

// 取消编辑
function cannelRow() {
	window.close();
}

// 显示动态处理图标
function startProcess() {
	load.style.display="";
}

// 隐藏动态处理图标
function endProcess() {
	load.style.display="none";
}
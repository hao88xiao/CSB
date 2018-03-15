/**
 * @description �����������[1,3,5]-[1,5,7]=[3]
 */
function arrayMinus(srcArr,tarArr){
	$.each(srcArr,function(i,n){
		srcArr[i]=n.toString();
	});
	$.each(tarArr,function(i,n){
		tarArr[i]=n.toString();
	});
	return $.grep(srcArr,function(n,i){
		return $.inArray(n,tarArr)==-1;
	});
}

/**
 * @description ��ȡURL�������鹦��
 */
var urlParam = (function (url){
	if(url==null)url = location.search;
    var qs = url.substring(url.indexOf("?")+1,url.length).split("&");
    var get = {};
    for (i=0; j=qs[i]; i++){ 
        get[j.substring(0,j.indexOf("="))] = j.substring(j.indexOf("=")+1,j.length); 
    }
    return get;
})();

/**
 * @description ģ��JAVA��StringBuffer,����JSƴ���ַ�����Ч��.
 */
function StringBuffer() {
    this.__strings__ = new Array;
}

StringBuffer.prototype.append = function (str) {
    this.__strings__.push(str);
};

StringBuffer.prototype.toString = function () {
    return this.__strings__.join("");
};
/**
 * @description <pre>ͨ��Ajax���ú�̨Action,�ж�Action�Ƿ��׳����쳣,
 * ����׳����쳣���쳣ҳ�����Ϣ�Ե���DIV����ʽ��ʾ����.�õ���Jquery����������DIV���
 * ���������������������ʾ�쳣ҳ���е�"�쳣����",һ������ֻ��ʾ�쳣��message.<br/>
 * ������������ʱҳ������simpleModal�������ļ�,һ����������jquery.alerts�������ļ�.</pre>
 * TODO �Ժ�Ҫɾ����
 */
function isThrowException(msg,isMoreMsg) {
	var sb = new StringBuffer();
	if(arguments.length == 2 && msg.indexOf("isErrorPage=\"true\"") > -1){
		//���쳣ҳ���е�"����"��ť�Ƴ�
		var re = /<input type=\"button\" id=\"retbutton\".+><\/input>/gi;
		msg = msg.replace(re, "");
		//�������쳣ҳ���Ե���DIV��ʾ����
		$.modal(msg,{containerCss:{width:document.body.clientWidth / 2 + 200,height:document.body.clientHeight-150}});
		return true;		
	}
	else if(arguments.length == 1 && msg.indexOf("isErrorPage=\"true\"") > -1){
		//��������ʽȡ��ҳ���е��쳣message
		var re = /<p.+>([\s\S]*)<\/p>/gi;
		re.test(msg,{containerCss:{}});
		sb.append("<font color='red'>��ѯ�����г����쳣:</font><br/>");
		sb.append("<textarea cols='30' rows='6'>");
		sb.append(RegExp.$1.trim() + "</textarea>");
		jAlert(sb.toString(),"�쳣");
		return true;
	}
	else{
		return false;
	}
}

/**
 * @description <pre>ͨ��Ajax���ú�̨Action,Action�׳��쳣ʱ,
 * ���쳣��Ϣ�Ե���DIV����ʽ��ʾ����.�õ���Jquery��simpleModal���
 * @param {} jsonObj ��̨���ص�JSON����
 */
function promptExcetionMsg(jsonObj){
		var sb = new StringBuffer();
		sb.append("<center><h2><font color='red'>��������г������쳣</font></h2></center>");
		sb.append("<br/><p class='red_long'>�쳣��Ϣ:" + jsonObj.exceptionDto.exceptionMsg + "</p>");
		sb.append("<br/><p class='red_long'> �쳣����: " + jsonObj.exceptionDto.exceptionCode + "</p>");
		sb
				.append('<br/><br/><input type="button" id="moreExceptionButton" class="button" value="��ϸ��Ϣ"/>');
		sb.append("<div id='moreExceptionDiv' style='color: red;'>");
		$.modal(sb.toString(), {
					containerCss : {
						width : document.body.clientWidth / 2,
						height : document.body.clientHeight - 150
					}
				});
		var divWidth = document.body.clientWidth / 2;
		var divHeight = document.body.clientHeight - 300;
		if($.browser.msie){
			 divWidth = document.body.clientWidth / 2 - 50;
			 divHeight = document.body.clientHeight - 350;
		}
		$("#moreExceptionDiv").hide().css({
					overflow : 'auto',
					width : divWidth,
					height : divHeight
				}).text(jsonObj.exceptionDto.exceptionStack);
		$("#moreExceptionButton").click(function() {
					$("#moreExceptionDiv").toggle();
				});
}
/**
 * ���ܣ�JS������
 * ���������������ռ䶨�壬��������
 * ���л�����IE 5.5+ only
 * ���ߣ�Kunee Hwang
 * ������2008-8-20
 */
bss = {
    'version': '0.1',
    'navigator':navigator.userAgent.toLowerCase(),
	/**
	 * ��ȡHTML BODY����
	 */
    getBody: function(){
        return document.body || document.documentElement
    },
	/**
	 * �պ���
	 */
    emptyFn: function(){
    },
    /**
     * �����ؼ�
     * @param {Object} tag
     */
    create: function(tag){
        return document.createElement(tag);
    },
    /**
     * ������������
     * @param {Object} o
     * @param {Object} c
     * @param {Object} override
     */
    apply: function(o, c, override){
        if (override == null) 
            override = true;
		else
			override = eval(override);
        if (o && c && typeof c == 'object') {
            for (var p in c) {
                if (override) 
                    o[p] = c[p];
                else 
                    if (o[p] == null) {
                        o[p] = c[p];
                    }
            }
        }
        return o;
    },
    /**
     * ���Ҷ���
     * ����1�������� docuemnt.getElementById;���Լ�Ϊ $('abc')
     * ����2�����б��в���
     * @param {Object} map
     * @param {Object} _key
     */
    findObj: function(map, _key){
        if (_key == null) {
            _key = map;
            return document.getElementById(_key);
        }
        for (var key in map) {
            key = key.toLowerCase();
            if (_key == key) 
                return map[_key];
        }
        return null;
    },
	/**
	 * ��ȡ XML DOM
	 * @param {Object} selectionLanguage
	 */
    getMsDom: function(selectionLanguage){
		{
			var dom = null;
			try {
				dom = new ActiveXObject('Microsoft.XMLDOM');
			} 
			catch (e) {
				dom = new ActiveXObject('Msxml2.DOMDocument.3.0');
			}
			if (dom == null) {
				alert('����xml�ؼ�ʧ��');
				return null;
			}
			dom.setProperty("SelectionLanguage", selectionLanguage ? selectionLanguage : "XPath");
			return dom;
		}
    },
	/**
	 * �̳�
	 * ԭ��������supperclass��Ϊ���࣬childʵ��������
	 * @param {Object} origin
	 * @param {Object} override
	 */
	extend:function(child,base){
		//childʵ��
		child.prototype = eval('new '+ base +'()');
		bss.apply(child.prototype,eval('new ' + child +'()'));
		child.prototype.constructor = base;
		child.prototype.supperclass = base;
	},
	/**
	 * �Ƿ���IE7
	 */
	isIE7:function(){
		return this.navigator.indexOf("msie 7") > -1;
	}
};


$$$ = bss.findObj;

bss.util = {
	/**
	 * ��ȡ��ǰҳ��·���Ĳ���
	 * @param {Object} obj
	 */
	getPara:function(paraName){
		var urlPara = window.location.href;
		var reg = new RegExp("[&|?]"+paraName+"=([^&$]*)", "gi"); 
		var a = reg.test(urlPara); 
		return a ? RegExp.$1 : ""; 
	}
};



/**
 * �ַ���
 * �˾�̬�����󶨵� String�������Ӻ�����ע�⴦�� this����
 */
bss.util.string = {
	/**
	 * ȥ���ַ�����β�ո�
	 * 
	 * var str = ' text ';
	 * str.trim();
	 * var str1 = ' text ';
	 * str1 = bss.util.String.trim(str1);
	 * 
	 * @param {Object} str
	 */
	trim: function(str){
		{
			var re = /^\s+|\s+$/g;
			
			if (str != null) {
				return str.replace(re, "");
			}
			else {
				//�������������򽫵�������Ϊ���ַ�������
				return this.replace(re, "");
			}
		}
	},
	/**
	 * ��ʽ���ַ���
	 * var str = '{0}{1},���';
	 * str = str.format('Kunee','����');
	 */
	format:function(){
		{
			var args = Array.prototype.slice.call(arguments, 0);
			return this.replace(/\{(\d+)\}/g, function(m, i){
				return args[i];
			});
		}
	},
	/**
	 * ��ʽ���ַ���(��̬����)
	 * var str = '{0}{1},���';
	 * str = bss.util.String.sFormat(str,'Kunee','����');
	 */
	sFormat:function(str){
		{
			var args = Array.prototype.slice.call(arguments, 1);
			return str.replace(/\{(\d+)\}/g, function(m, i){
				return args[i];
			});
		}
	},
    /**
     * �ַ���ת��ΪJSON����
     * @param {Object} str
     */
    decodeJson: function(str){
        if (str == null) 
            str = this;
        return eval("(" + str + ")");
    },
	/**
	 * �Ƿ���ĳ�ַ�����ʼ
	 * @param {Object} str
	 */
	startsWith:function(str){
		 return this.indexOf(str) == 0;
	},
	/**
	 * �Ƿ���ĳ�ַ�������
	 * @param {Object} str
	 */
	endsWith: function(str) {
    	var d = this.length - str.length;
    	return d >= 0 && this.lastIndexOf(str) == d;
	}
};
bss.apply(String.prototype,bss.util.string);


bss.ajax = {
    getXmlHttpRequest: function(){
	    var xhr = window.ActiveXObject ? new ActiveXObject("Microsoft.XMLHTTP") : new XMLHttpRequest();
        return xhr;
    },
	/**
	 * Զ�̻�ȡ
	 * @param {Object} url
	 * @param {Object} data
	 * @param {Object} callback
	 * @param {Object} domData
	 */
	post:function(url,data,callback,domData){
		var parameter = {
			'url':url,
			'urlpara':data,
			'p_method':'POST',
			'async':callback!=null,
			'dompara':domData
		};
		return this.load(parameter,callback);
	},
	/**
	 * Զ�̻�ȡ
	 * @param {Object} url
	 * @param {Object} data
	 * @param {Object} callback
	 */
	get:function(url,data,callback){
		var parameter = {
			'url':url,
			'urlpara':data,
			'p_method':'GET',
			'async':callback!=null
		};
		return this.load(parameter,callback);
	},
	/**
	 * Զ�̼���
	 * @param {Object} paramters ����
	 * @param {Object} callback �ص�����
	 */
    load: function(paramters, callback){
		//��������
        paramters = this.getDefaultSetting(paramters);
        callback = callback || bss.emptyFn;
        if(paramters.url.indexOf('?')>-1){
        	if(!paramters.url.endsWith('&')){
        		paramters.url = paramters.url + '&';
        	}
        	paramters.url = paramters.url + this.getUrlParamter(paramters['urlpara']);
        }else
	        paramters.url = paramters.url + '?' + this.getUrlParamter(paramters['urlpara']);

        var xhr = this.getXmlHttpRequest();
        if (paramters.async) {
			//�첽ģʽ������Ժ�ִ�лص�����
			xhr.onreadystatechange = function(){
				if(xhr.readyState == 4){
					if(xhr.status == 200){
						eval(callback).call(this,bss.ajax.getResult(xhr,paramters.dataType));
					}
				}
			}
        }
		xhr.open(paramters.p_method,paramters.url,paramters.async);
		xhr.setRequestHeader("Content-Type",paramters.contentType);
		xhr.setRequestHeader('charset',paramters.charset);
        xhr.send(paramters.data);
		if(paramters.async){
			//�첽ģʽ������XMLHTTP���
			return xhr;
		}else{
			//ͬ��ģʽ��ֱ�ӷ���ֵ
			return this.getResult(xhr,paramters.dataType);
		}
    },
	/**
	 * ��ȡ����ֵ
	 * @param {Object} xhr
	 * @param {Object} dataType
	 */
	getResult:function(xhr,dataType){
		var dom = bss.getMsDom();
		dom.load(xhr.responseXML);
		if(dom==null || dom.xml == '')
			return xhr.responseText;
		return dom;
	},
	/**
	 * ��������
	 * @param {Object} xhr
	 */
    abort: function(xhr){
        if (xhr)
            xhr.abort();
    },
	/**
	 * URL����
	 * @param {Object} obj
	 */
    getUrlParamter: function(obj){
        var str = '';
		if (obj != null) {
			for (var key in obj) {
				str += key + '=' + encodeURIComponent(encodeURIComponent(obj[key])) + '&';
			}
		}
        str += 'reconnectKeyCode=' + Math.random();
        return str;
    },
	/**
	 * Ĭ������
	 * @param {Object} paramters
	 */
    getDefaultSetting: function(paramters){
        paramters['url'] = paramters['url'];
        paramters['p_method'] = paramters['p_method'] ? paramters['p_method'] : 'GET';
        paramters['async'] = paramters['async'] ? eval(paramters['async']) : false;
        paramters['charset'] = paramters['charset'] ? paramters['charset'] : 'utf-8';
        paramters['data'] = paramters['dompara'] ? paramters['dompara'] : '';
        paramters['contentType'] = paramters['contentType'] ? paramters['contentType'] : "application/x-www-form-urlencoded";
        paramters['dataType'] = paramters['dataType'] ? paramters['dataType'] : 'text';
        return paramters;
    }
};
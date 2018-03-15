/**
 * @author 林志鹏
 * @description 对前台console的封装
 * @type 
 */
var log = {
	debug:function(){},
	warn:function(){},
	info:function(){},
	error:function(){},
	dir:function(){}
}
if (typeof console != "undefined" && typeof console.debug != "undefined") {
//	var _getCallerFuncName = function(func){
//		return func.substring(func.indexOf("function") + 8, func.indexOf("(")) || "anoynmous";
//	}
	log.debug=function(){
//		var callerFuncName = _getCallerFuncName(arguments.callee.caller.toString());
		console.debug(arguments[0]);
	}
	log.warn=function(){
//		var callerFuncName = _getCallerFuncName(arguments.callee.caller.toString());
		console.warn(arguments[0]);
	}
	log.error=function(){
//		var callerFuncName = _getCallerFuncName(arguments.callee.caller.toString());
		console.error(arguments[0]);
	}
	log.info=function(){
//		var callerFuncName = _getCallerFuncName(arguments.callee.caller.toString());
		console.info(arguments[0]);
	}
	log.dir=function(){
		console.dir(arguments[0]);
	}
}
/**
 * @author κ��ʤ
 * @description ��jQueryʵ�ֵ��۵������
 */
$.fn.panel = function(opt){
	var cfg={
			title:'panel',
			html:'',
			collapsible:true,
			direct:'south',
			callback:function(){},
			panelWidth:25,
			drag:false,
			width:400,
			height:350
	};
	cfg=$.extend(cfg,opt);
	var mask,panel,body,head,close,title;
	var _self=$(this);
	panel=$('<div class="panel"/>');
	head=$('<div class="panel-head" />').append(close=$('<div class="panel-top-right" title="չ�����۵�"></div>')).append(title=$('<div class="panel-top-left">'+cfg.title+'</div>'));
	body=$('<div class="panel-body" />').append(cfg.html).height(cfg.height);
	//south �۵����������·�
	if(cfg.direct=='south'){
		panel.append(head).append(body).width(cfg.width).appendTo(_self);
		//��ʼ��������
		//body.hide();
	}
	//north �۵����������Ϸ�
	if(cfg.direct=='north'){
		panel.append(body).append(head).width(cfg.width).appendTo(_self);
		//��ʼ��������
		//body.hide();		
	}
	if(cfg.collapsible){
		close.toggle(function(){
			close.removeClass().addClass('panel-top-right-left');
			body.fadeOut("slow");
		},function(){
			close.removeClass().addClass('panel-top-right');
			body.fadeIn("slow",function(){
				cfg.callback(_self);
			});
		});
	}
	else{
		close.remove();
	}
	return _self;
}

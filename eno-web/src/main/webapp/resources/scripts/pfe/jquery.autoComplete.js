// 专为客流做的
jQuery.AutoComplete = function(id, datalist) {
	var elt = $("#" + id);
	var strHtml = '<div class="AutoComplete" id="AutoComplete"><ul class="AutoComplete_ul"><li class="AutoComplete_title">请选择...</li>';
	for (var i = 0; i < datalist.length; i++) {
		strHtml += '<li hz="' + datalist[i] + '">' + datalist[i] + '</li>';
	}
	$('body').append(strHtml + "</ul></div>");
	var autoComplete = $('#AutoComplete');
	autoComplete.data('elt', elt);
	var autoLi = autoComplete.find('li:not(.AutoComplete_title)');
	autoLi.mouseover(function() {
				$(this).siblings().filter('.hover').removeClass('hover');
				$(this).addClass('hover');
			}).mouseout(function() {
				$(this).removeClass('hover');
			}).mousedown(function() {
				autoComplete.data('elt').val($(this).text()).change();
				findShopByCode($(this).text()); // 根据店铺编号，找店铺位置
				autoComplete.hide();
			});
	// 用户名补全+翻动
	elt.keyup(function(e) {
				if (/13|38|40|116/.test(e.keyCode) || this.value == '') {
					return false;
				}
				var username = this.value.toUpperCase();
				autoLi.each(function() {
							this.innerHTML = $(this).attr('hz').toUpperCase();
							if (this.innerHTML.indexOf(username) >= 0) {
								$(this).show();
							} else {
								$(this).hide();
							}
						}).filter('.hover').removeClass('hover');
				autoComplete.show().css({
							left : $(this).offset().left,
							top : $(this).offset().top
									+ $(this).outerHeight(true) - 1,
							position : 'absolute',
							zIndex : '99999'
						});
				if (autoLi.filter(':visible').length == 0) {
					autoComplete.hide();
				} else {
					autoLi.filter(':visible').eq(0).addClass('hover');
				}
			}).keydown(function(e) {
		if (e.keyCode == 38) { // 上
			autoLi.filter('.hover').prev().not('.AutoComplete_title')
					.addClass('hover').next().removeClass('hover');
		} else if (e.keyCode == 40) { // 下
			autoLi.filter('.hover').next().addClass('hover').prev()
					.removeClass('hover');
		} else if (e.keyCode == 13) { // Enter
			autoLi.filter('.hover').mousedown();
		}
	}).focus(function() {
				autoComplete.data('elt', $(this));
			}).blur(function() {
				autoComplete.hide();
			});
};
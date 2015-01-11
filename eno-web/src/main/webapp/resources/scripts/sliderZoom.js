/*页面载入后*/
var slide_num = 0;
var slide_nums = [1,2,3,4,5];
var slide_timesSize = [176,128,84,42,0];
var slide_times = 4;
window.onload = function() {
	var slideHeight = $('.drag-line').height();
	$('.draggable-button').draggable({
		axis : 'y',
		containment : 'parent'
	});
	$('.draggable-button').on('drag', function() {
		var position = $('.draggable-button').position();
		var marginTop = position.top;
		$('.line').css({
			'clip' : 'rect(' + marginTop + 'px,8px, 200px,0px)'
		});
		var currentNum = parseInt(slideHeight/(marginTop<=0?1:marginTop)) - 1;
		slide_num = currentNum > 4 ? 4 : currentNum;
		$('.draggable-button').text(slide_nums[slide_num]);
		$('#stru_id').css('transform', 'scale(' + slide_nums[slide_num] + ',' + slide_nums[slide_num] + ')');

	});
	// 放大平面图
	$('.fa-plus').on('click', function() {
		if(slide_num<4) {
			slideEvent(++slide_num);
		}
	});
	// 缩小平面图
	$('.fa-minus').on('click', function() {
		var position = $('.draggable-button').position();
		if (slide_num > 0) {
			slideEvent(--slide_num);
		}
	});

	// 平面图往上移动
	$(".BMap_panN").click(function() {
		var top = $("#stru_id").css('top');
		var slideLenght = parseInt(top)-50*parseInt(slide_nums[slide_num]);
		$('#stru_id').animate({'top':slideLenght}, 500);
	});
	// 平面图往右移动
	$(".BMap_panE").click(function() {
		var left = $("#stru_id").css('left');
		var slideLenght = parseInt(left)-200*parseInt(slide_nums[slide_num]);
		$("#stru_id").animate({'left':slideLenght},500);
	});
	// 平面图往下移动
	$(".BMap_panS").click(function() {
		var top = $("#stru_id").css('top');
		var slideLenght = parseInt(top)+100*parseInt(slide_nums[slide_num]);
		$('#stru_id').animate({'top':slideLenght}, 500);
	});
	// 平面图往左移动
	$(".BMap_panW").click(function() {
		var left = $("#stru_id").css('left');
		var slideLenght = parseInt(left)+200*parseInt(slide_nums[slide_num]);
		$("#stru_id").animate({'left':slideLenght},500);
	});
//	var oImg = document.getElementById('stru_id');
//	if (window.addEventListener) {
//		/** DOMMouseScroll is for mozilla. */
//		window.addEventListener('DOMMouseScroll', wheel, false);
//	}
//	/** IE/Opera. */
//	window.onmousewheel = document.onmousewheel = wheel;
//	(function() {
//		addEvent(
//				oImg,
//				'mousedown',
//				function(ev) {
//					var oEvent = prEvent(ev), oParent = oImg.parentNode, disX = oEvent.clientX
//							- oImg.offsetLeft, disY = oEvent.clientY
//							- oImg.offsetTop, startMove = function(ev) {
//						if (oParent.setCapture) {
//							oParent.setCapture();
//						}
//						var oEvent = ev || window.event, l = oEvent.clientX
//								- disX, t = oEvent.clientY - disY;
//						oImg.style.left = l + 'px';
//						oImg.style.top = t + 'px';
//						oParent.onselectstart = function() {
//							return false;
//						};
//						return;
//					}, endMove = function(ev) {
//						if (oParent.releaseCapture) {
//							oParent.releaseCapture();
//						}
//						oParent.onselectstart = null;
//						removeEvent(oParent, 'mousemove', startMove);
//						removeEvent(oParent, 'mouseup', endMove);
//						return;
//					};
//					addEvent(oParent, 'mousemove', startMove);
//					addEvent(oParent, 'mouseup', endMove);
//					return;
//				});
//	})();
};

function slideEvent(n) {
	$('.line').css({
		'clip' : 'rect(' + slide_timesSize[n] + 'px,8px, 200px,0px)'
	});
	$('.draggable-button').css({
		'top' : slide_timesSize[n]
	}).text(slide_nums[n]);
	var oImg = document.getElementById('stru_id');
	$(oImg).css('transform', 'scale(' + slide_nums[n] + ',' + slide_nums[n] + ')');
}



/*绑定事件*/
function addEvent(obj, sType, fn) {
	if (obj.addEventListener) {
		obj.addEventListener(sType, fn, false);
	} else {
		obj.attachEvent('on' + sType, fn);
	}
	return;
};
function removeEvent(obj, sType, fn) {
	if (obj.removeEventListener) {
		obj.removeEventListener(sType, fn, false);
	} else {
		obj.detachEvent('on' + sType, fn);
	}
};
function prEvent(ev) {
	var oEvent = ev || window.event;
	if (oEvent.preventDefault) {
		oEvent.preventDefault();
	}
	return oEvent;
}
var wheel = function(event) {
	var delta = 0;
	if (!event) /* For IE. */
		event = window.event;
	if (event.wheelDelta) { /* IE/Opera. */
		delta = event.wheelDelta / 120;
	} else if (event.detail) {
		/** Mozilla case. */
		/** In Mozilla, sign of delta is different than in IE. 
		 * Also, delta is multiple of 3. 
		 */
		delta = -event.detail / 3;
	}
	/** If delta is nonzero, handle it. 
	 * Basically, delta is now positive if wheel was scrolled up, 
	 * and negative, if wheel was scrolled down. 
	 */
	if (delta)
		handle(delta);
	/** Prevent default actions caused by mouse wheel. 
	 * That might be ugly, but we handle scrolls somehow 
	 * anyway, so don't bother here.. 
	 */
	if (event.preventDefault) {
		event.preventDefault();
	}
	event.returnValue = false;
};
var handle = function(delta) {
	
	if (delta < 0) {
		if (slide_num > 0) {
			slideEvent(--slide_num);
		}
		return;
	} else {
		if (slide_num < 4) {
			slideEvent(++slide_num);
		}
		return;
	}
};

/*
 * 本JS文件是为了扩展JQuery的方法：
 * 添加focusEnd()函数，当调用此函数时，
 * 1，当前控件可以聚焦
 * 2，聚焦后光标处于字符最后一位的后面。
 * 调用方法： $(element).focusEnd();
 * 注意：此js必须处理饮用的JQuery函数文件后面。
 */
$.fn.setCursorPosition = function(position) {
	if (this.lengh == 0)
		return this;
	return $(this).setSelection(position, position);
};

$.fn.setSelection = function(selectionStart, selectionEnd) {
	if (this.lengh == 0)
		return this;
	input = this[0];

	if (input.createTextRange) {
		var range = input.createTextRange();
		range.collapse(true);
		range.moveEnd('character', selectionEnd);
		range.moveStart('character', selectionStart);
		range.select();
	} else if (input.setSelectionRange) {
		input.focus();
		input.setSelectionRange(selectionStart, selectionEnd);
	}

	return this;
};

$.fn.focusEnd = function() {
	this.setCursorPosition(this.val().length);
};

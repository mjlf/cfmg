var count = 0;
var start = 0;
var display = 0;
var pageId = "";//页面显示id
var exe;
var everypagenum;

function page(info){
	count = info.count;
	start = info.start;
	display = info.display;
	data = info.data;
	address = info.address;
	dataId = info.dataId;
	pageId = info.pageId;
	exe = info.executed;
	everypagenum = info.everypagenum;
	createhtml();
}

function createhtml(){
	var html = "";
	var num = 0;
	if(start != 1){
		html += '<li><a href="#" id="first" onclick="first()">首页</a></li>';
		html += '<li><a href="#" id="pre" onclick="pre()">上一页</a></li>';
	}else{
		html += '<li><a href="#" id="first"  style="background-color:#81C0C0">首页</a></li>';
		html += '<li><a href="#" id="pre"  style="background-color:#81C0C0">上一页</a></li>';
	}
	var index = display/2;
	
	var i = 0
	if(start - index < 1){
		i = 1;
	}
	if(start + index > count){
		if(count - display >= 1){
			i = count - display;
		}else{
			i = 1;
		}
	}
	for(; i <= count; i++){
		if(i == start){
			html +='<li><a href="#" id="page'+i+'" style="background-color:#81C0C0">'+i+'</a></li>';
		}else{
			html +='<li><a href="#" id="page'+i+'" onclick="pageclick('+i+')">'+i+'</a></li>';
		}
	}
	if(start != count){
		html += '<li><a href="#" id="next" onclick="next()">下一页</a></li>';
		html += '<li><a href="#" id="last" onclick="last()">尾页</a></li>';
	}else{
		html += '<li><a href="#" id="next" style="background-color:#81C0C0">下一页</a></li>';
		html += '<li><a href="#" id="last"  style="background-color:#81C0C0">尾页</a></li>';
	}
	$("#"+pageId).empty().append(html);
}

function reload(){
	exe(start, everypagenum);
	createhtml();
}

function pageclick(num){
	start = num;
	exe(start, everypagenum);
	createhtml();
}
function first(){
	start = 1;
	exe(start, everypagenum);
	createhtml();
}
function pre(){
	start = start - 1;
	exe(start, everypagenum);
	createhtml();
}
function next(){
	start = start + 1;
	exe(start, everypagenum);
	createhtml();
}
function last(){
	start = count;
	exe(start, everypagenum);
	createhtml();
}
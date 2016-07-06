var indexpic = 0;
var allimgs;
function showApic(index){
	 $("#libshowpic").empty().append('<img src="'+allimgs[index].img+'">');
}
$("#showleftpic").live("click", function(){
	if(indexpic - 1 >= 0){
		indexpic -= 1;
		$("#libshowallpic").empty();
		for(var i = indexpic; i < allimgs.length; i++){
			 var imginfo = allimgs[i].img;
			 var html = '<img class="allpic" src='+imginfo+' width="100px" onClick="showApic('+i+')">';
			 $("#libshowallpic").append(html);
		}
	}
});
$("#showrightpic").live("click", function(){
	if(indexpic + 1 <= allimgs.length - 3){
		indexpic += 1;
		$("#libshowallpic").empty();
		for(var i = indexpic; i < allimgs.length; i++){
			 var imginfo = allimgs[i].img;
			 var html = '<img class="allpic" src='+imginfo+' width="100px" onClick="showApic('+i+')">';
			 $("#libshowallpic").append(html);
		}
	}
});

var clearInfo = function(){
	 $("#libshowinfoadmin").text("");
	 $("#libshowinfoisp").text("");
	 $("#libshowinfoisa").text("");
	 $("#libshowinfopeoplenum").text("");
	 $("#libshowinfostartam").text("");
	 $("#libshowinfoendam").text("");
	 $("#libshowinfostartpm").text("");
	 $("#libshowinfoendpm").text("");
	 $("#libshowinfoaddress").text("");
	 $("#libshowinfodisc").text("");
	 $("#libshowinfoknow").text("");
};
var showInfo = function (id) {
	clearInfo();
	$("#dolibpicinfo").empty();
	$.ajax({
		 type: "POST",
		 url: "./findALibrary.do",
		 data : {
			 "libraryId":id
		 },
		 success: function(msg){
			 msg = $.parseJSON(msg);
			 $("#libshowinfoadmin").text(msg.admin);
			 $("#libshowinfoisp").text(msg.isProjection);
			 $("#libshowinfoisa").text(msg.videoConferencing);
			 $("#libshowinfopeoplenum").text(msg.peoplenum);
			 $("#libshowinfostartam").text(msg.ams);
			 $("#libshowinfoendam").text(msg.ame);
			 $("#libshowinfostartpm").text(msg.pms);
			 $("#libshowinfoendpm").text(msg.pme);
			 $("#libshowinfoaddress").text(msg.address);
			 $("#libshowinfodisc").text(msg.disc);
			 $("#libshowinfoknow").text(msg.know);
			 var imgs = msg.imgs;
			 allimgs = imgs;
			 if(imgs.length > 0){
				 $("#dolibpicinfo").append('<div id="libshowpic"><img src="'+imgs[0].img+'"></div>');
				 if(imgs.length > 2){
					 $("#dolibpicinfo").append(
							 	'<div id="showleftpic"><img alt="left" src="/cfmg/admin/images/left.png"></div>'
								+'<div id="libshowallpic"></div>'
								+'<div id="showrightpic"><img alt="right" src="/cfmg/admin/images/right.png"></div>');
				 }else{
					 $("#dolibpicinfo").append('<div id="libshowallpic"></div>');
				 }
			 }
			 for(var i = 0; i < imgs.length; i++){
				 if(i > 2){
					 break;
				 }
				 var imginfo = imgs[i].img;
				 var html = '<img class="allpic" src='+imginfo+' width="100px" onClick="showApic('+i+')">';
				 $("#libshowallpic").append(html);
			 }
		 }
	});
	$("#detialedInfo").modal('show');
}
var execute = function (index, everypagenum){
	var datainfo = {};
	datainfo['index'] = index;
	datainfo['everypagenum'] = everypagenum;
	$.ajax({
		 type: "POST",
		 url: "./findallLibrary.do",
		 data : datainfo,
		 success: function(msg){
			msg = $.parseJSON(msg);
			var librarys = msg.list;
			var html = "";
			for(var i = 0; i < librarys.length; i++){
				var s = '<tr>'
					+'<td>'+(i+1)+'</td>'
					+'<td onclick="showchatforuser('+librarys[i].adminId+',\''+librarys[i].admin+'\')"><i class="icon-user" style="color: blue;"></i>'+librarys[i].admin+'</td>'
					+'<td>'+librarys[i].address+'</td>'
					+'<td>'+librarys[i].ams+'~'+librarys[i].ame+'</td>'
					+'<td>'+librarys[i].pms+'~'+librarys[i].pme+'</td>'
					+'<td>'+librarys[i].people+'</td>'
					+'<td>'+librarys[i].isProjection+'</td>'
					+'<td>'+librarys[i].videoConferencing+'</td>'
					+'<td onclick="showInfo('+librarys[i].id+')"><a href="#"><i class="icon-eye-open"></i></a></td>';
			s += '<td><a href="#" onclick="findBook('+librarys[i].id+')"><i class="icon-th-list"></i></a></td></tr>';
			html += s;
				$("#tblibrary").empty().append(html);
			}
		 }
	});
}
var conditionExecute = function (index, everypagenum){
	var datainfo ={
		 "isp":$('input[name="isp"]:checked').val(),
		 "isa":$('input[name="isa"]:checked').val(),
		 "admin":-1,
		 "startnum":$("#startnum").val(),
		 "endnum":$("#endnum").val(),
		 "starttime":$("#libararyliststarttime").val(),
		 "endtime":$("#libararylistendtime").val()
	 };
	datainfo['index'] = index;
	datainfo['everypagenum'] = everypagenum;
	$.ajax({
		 type: "POST",
		 url: "./findConditionLibrary.do",
		 data : datainfo,
		 success: function(msg){
			msg = $.parseJSON(msg);
			var librarys = msg.list;
			var html = "";
			for(var i = 0; i < librarys.length; i++){
				var s = '<tr>'
					+'<td>'+(i+1)+'</td>'
					+'<td onclick="showchatforuser('+librarys[i].adminId+',\''+librarys[i].admin+'\')"><i class="icon-user" style="color: blue;"></i>'+librarys[i].admin+'</td>'
					+'<td>'+librarys[i].ams+'~'+librarys[i].ame+'</td>'
					+'<td>'+librarys[i].pms+'~'+librarys[i].pme+'</td>'
					+'<td>'+librarys[i].people+'</td>'
					+'<td>'+librarys[i].isProjection+'</td>'
					+'<td>'+librarys[i].videoConferencing+'</td>'
					+'<td onclick="showInfo('+librarys[i].id+')"><a href="#"><i class="icon-eye-open"></i></a></td>';
			s += '<td><a href="#" onclick="findBook('+librarys[i].id+')"><i class="icon-th-list"></i></a></td></tr>';
			html += s;
				$("#tblibrary").empty().append(html);
			}
		 }
	});
}
$("#userLibrarys").live("click", function(){
	$.ajax({
		 type: "POST",
		 url: "./findallLibrary.do",
		 data:{
			 "index":1,
			 "everypagenum":10
		 },
		 success: function(msg){
			msg = $.parseJSON(msg);
			var librarys = msg.list;
			var html = "";
			for(var i = 0; i < librarys.length; i++){
				var s = '<tr>'
						+'<td>'+(i+1)+'</td>'
						+'<td onclick="showchatforuser('+librarys[i].adminId+',\''+librarys[i].admin+'\')"><i class="icon-user" style="color: blue;"></i>'+librarys[i].admin+'</td>'
						+'<td>'+librarys[i].address+'</td>'
						+'<td>'+librarys[i].ams+'~'+librarys[i].ame+'</td>'
						+'<td>'+librarys[i].pms+'~'+librarys[i].pme+'</td>'
						+'<td>'+librarys[i].people+'</td>'
						+'<td>'+librarys[i].isProjection+'</td>'
						+'<td>'+librarys[i].videoConferencing+'</td>'
						+'<td onclick="showInfo('+librarys[i].id+')"><a href="#"><i class="icon-eye-open"></i></a></td>';
				s += '<td><a href="#" onclick="findBook('+librarys[i].id+')"><i class="icon-th-list"></i></a></td></tr>';
				html += s;
			}
			$("#tblibrary").empty().append(html);
			
			var info = {
					"count":msg.pagecount,
					"start":msg.pageindex,
					"display":10,
					"pageId":"pageindex",
					"executed": execute,
					"everypagenum":10
					};
			page(info);
			
		 }
	});
	
	$.ajax({
		 type: "get",
		 url: "./toUserLibraryList.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("会议室列表");
		 }
	});
});

$("#doopcheck").live("click", function(){
	$.ajax({
		type: "POST",
		 url: "./findConditionLibrary.do",
		 data:{
			 "index":1,
			 "everypagenum":10,
			 "isp":$('input[name="isp"]:checked').val(),
			 "isa":$('input[name="isa"]:checked').val(),
			 "admin":-1,
			 "startnum":$("#startnum").val(),
			 "endnum":$("#endnum").val(),
			 "starttime":$("#libararyliststarttime").val(),
			 "endtime":$("#libararylistendtime").val()
		
		 },
		 success: function(msg){
			msg = $.parseJSON(msg);
			var librarys = msg.list;
			var html = "";
			for(var i = 0; i < librarys.length; i++){
				var s = '<tr>'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+librarys[i].admin+'</td>'
						+'<td>'+librarys[i].ams+'~'+librarys[i].ame+'</td>'
						+'<td>'+librarys[i].pms+'~'+librarys[i].pme+'</td>'
						+'<td>'+librarys[i].people+'</td>'
						+'<td>'+librarys[i].isProjection+'</td>'
						+'<td>'+librarys[i].videoConferencing+'</td>'
						+'<td onclick="showInfo('+librarys[i].id+')"><a href="#"><i class="icon-eye-open"></i></a></td>';
				s += '<td><a href="#" onclick="findBook('+librarys[i].id+')"><i class="icon-th-list"></i></a></td></tr>';
				html += s;
			}
			$("#tblibrary").empty().append(html);
			
			var info = {
					"count":msg.pagecount,
					"start":msg.pageindex,
					"display":10,
					"pageId":"pageindex",
					"executed": conditionExecute,
					"everypagenum":10
					};
			page(info);
		 }
	});
});

var addlibraryid = function(id){
	$("#deletelibid").val(id);
}

var findBook = function(id){
	$("#libraryId").val(id);
	$("#findDate").modal('show');
};

$("#dofind").live('click', function(){
	var libraryId = $("#libraryId").val();
	var date = $("#endDate").val();
	$.ajax({
		url:"./findSomedayCanBookLibrary.do",
		asnync:"true",
		type:"post",
		data:{
			"libraryId":libraryId,
			"findDate":date
		},
		success:function(msg){
			msg = $.parseJSON(msg);
			var library = msg.library;
			var user = msg.user;
			var libraryBook = msg.books;
			var html
			for(var i = 0; i <libraryBook.length; i++){
				var tr = '<tr>';
				tr += '<td>'+(i+1)+'</td>';
				tr += '<td>'+user.nickname+'</td>';
				tr += '<td id="libraryBookStartTimeStr'+i+'">'+libraryBook[i].startTimeStr+'</td>';
				tr += '<td id="libraryBookEndTimeStr'+i+'">'+libraryBook[i].endTimeStr+'</td>';
				tr += '<td>'+library.people+'</td>';
				tr += '<td>'+library.address+'</td>';
				tr += '<td>'+libraryBook[i].length+'</td>';
				tr += '<td ><button class="btn btn-danger" onclick="book('+libraryBook[i].libraryId+','+i+')">预定</button></td>';
				tr += '</tr>';
				html += tr;
			}
			$("#tblibraryBooks").empty().append(html);
			$("#libraryBooks").modal('show');
		}
	});
});


var user;
var showchatforuser = function(id, admin){
	$.ajax({
		type: "POST",
		url: "./getUserInfoById.do",
		async:'true',
		data:{
			"id":id
		},
		success:function(msg){
			user = $.parseJSON(msg).nickname;
			$("#tousernickname").val(user);
			$("#touserid").val($.parseJSON(msg).id);
		}
	});
	$.ajax({
		type: "GET",
		url: "./getUserInfo.do",
		async:"true",
		success:function(msg){
			user = $.parseJSON(msg).nickname;
			$("#fromusernickname").val(user);
			$("#fromuserid").val($.parseJSON(msg).id);
		}
	});
	$("#allmessage").empty();
	$("#usercharheadtitle").text(admin);
	$("#userid").val(id);
	$("#showchat").modal('show');
}

$("#findMessageInfo").live('click', function(){
	$.ajax({
		type: "POST",
		url: "./loadChatMessage.do",
		async:"true",
		data:{
			"fromUserId":$("#fromuserid").val(),
			"toUserId":$("#touserid").val()
		},
		success:function(msg){
			
			msg = $.parseJSON(msg);
			var html = "";
			for(var i = 0; i < msg.length; i++){
				if(msg[i].fromUserId == $("#fromuserid").val()){
					html +='<tr><td width=20%></td>'+
					'<td width=60% style="text-align:right">'+msg[i].data+'</td>'+
					'<td width=20%><i class="icon-user" style="color: orange;")">'+msg[i].messagetime+'</i></td></tr>';
				}else{
					html += '<tr><td width=20%><i class="icon-user" style="color: red;")">'+msg[i].messagetime+'</i></td>'+
					'<td width=60% style="text-align:left">'+msg[i].data+'</td>'+
					'<td width=20%></td></tr>';
				}
			}
			$("#allmessage").empty().append(html);
		}
	});
});

var book = function(id, i){
	$("#startTimeStrForBookAndCan").val($("#libraryBookStartTimeStr"+i).text());
	$("#endTimeStrForBookAndCan").val($("#libraryBookEndTimeStr"+i).text());
	$("#bookLibraryLibraryId").val(id);
	$.ajax({
		url:"./findALibraryNotImg.do",
		type:"post",
		async:"true",
		data:{
			"libraryId": id,
		},
		success : function(msg){
			msg = $.parseJSON(msg);
			$("#bookLibraryAdminId").val(msg.adminId);
		}
	});
	$("#bookLibraryWriteInfo").modal('show');
};


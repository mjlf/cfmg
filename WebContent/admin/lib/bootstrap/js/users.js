var admin;

var execute = function (index, everypagenum){
	var datainfo = {};
	datainfo['index'] = index;
	datainfo['everypagenum'] = everypagenum;
	
	$.ajax({
		 type: "POST",
		 url: "./findAllUser.do",
		 data:datainfo,
		 success: function(msg){
			dealUserinfo(msg);
		 }
	});
}
var executeisAdmin = function (index, everypagenum){
	var datainfo = {};
	datainfo['index'] = index;
	datainfo['everypagenum'] = everypagenum;
	
	$.ajax({
		 type: "POST",
		 url: "./findAllIsAdminUser.do",
		 data:datainfo,
		 success: function(msg){
			dealUserinfo(msg);
		 }
	});
}
var executeNotIsAdmin = function (index, everypagenum){
	var datainfo = {};
	datainfo['index'] = index;
	datainfo['everypagenum'] = everypagenum;
	
	$.ajax({
		 type: "POST",
		 url: "./findAllNotIsAdminUser.do",
		 data:datainfo,
		 success: function(msg){
			dealUserinfo(msg);
		 }
	});
}
$("#allUser").live("click", function(){
	$.ajax({
		 type: "GET",
		 url: "./getUserInfo.do",
		 success : function(message){
			 message = $.parseJSON(message);
			 admin = message.admin;
		 }
	});
	
	$.ajax({
		 type: "POST",
		 url: "./findAllUser.do",
		 data:{
			 "index":1,
			 "everypagenum":10
		 },
		 success: function(msg){
			dealUserinfo(msg);
			msg = $.parseJSON(msg);
			var info = {
					"count":msg.pageCount,
					"start":msg.pageIndex,
					"display":10,
					"pageId":"userspageindex",
					"executed": execute,
					"everypagenum":10
					};
			page(info);
		 }
	});
	
	$.ajax({
		 type: "get",
		 url: "./toAllUser.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("用户列表");
		 }
	});
});

$("#adminUser").live("click", function(){
	$.ajax({
		 type: "GET",
		 url: "./getUserInfo.do",
		 success : function(message){
			 message = $.parseJSON(message);
			 admin = message.admin;
		 }
	});
	
	$.ajax({
		 type: "POST",
		 url: "./findAllIsAdminUser.do",
		 data:{
			 "index":1,
			 "everypagenum":10
		 },
		 success: function(msg){
			dealUserinfo(msg);
			msg = $.parseJSON(msg);
			var info = {
					"count":msg.pageCount,
					"start":msg.pageIndex,
					"display":10,
					"pageId":"userspageindex",
					"executed": executeisAdmin,
					"everypagenum":10
					};
			page(info);
		 }
	});
	
	$.ajax({
		 type: "get",
		 url: "./toAllUser.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("用户列表");
		 }
	});
});

$("#commonUser").live("click", function(){
	$.ajax({
		 type: "GET",
		 url: "./getUserInfo.do",
		 success : function(message){
			 message = $.parseJSON(message);
			 admin = message.admin;
		 }
	});
	
	$.ajax({
		 type: "POST",
		 url: "./findAllNotIsAdminUser.do",
		 data:{
			 "index":1,
			 "everypagenum":10
		 },
		 success: function(msg){
			dealUserinfo(msg);
			msg = $.parseJSON(msg);
			var info = {
					"count":msg.pageCount,
					"start":msg.pageIndex,
					"display":10,
					"pageId":"userspageindex",
					"executed": executeNotIsAdmin,
					"everypagenum":10
					};
			page(info);
		 }
	});
	
	$.ajax({
		 type: "get",
		 url: "./toAllUser.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("用户列表");
		 }
	});
});

var dealUserinfo = function(msg){
	msg = $.parseJSON(msg);
	var users = msg.list[0];
	var html = "";
	for(var i = 0; i < users.length; i++){
		var s = '<tr id="user'+users[i].id+'">'
				+'<td>'+(i+1)+'</td>'
				+'<td>'+users[i].nickname+'</td>';
				if(users[i].admin == 2){
					s += '<td style="color:red;" id="useradmin'+users[i].id+'">'+'超级管理员'+'</td>';
				}else if(users[i].admin == 1){
					s += '<td style="color:blue;" id="useradmin'+users[i].id+'">'+'普通管理员'+'</td>';
				}else if(users[i].admin == 0){
					s += '<td style="color:#ccc;" id="useradmin'+users[i].id+'">'+'一般用户'+'</td>';
				}
				s += '<td>'+users[i].phone+'</td>'
				+'<td>'+users[i].email+'</td>';
				if(users[i].isOnline == 1){
					s +=  '<td><i class="icon-user" style="color: blue;" onclick="showchat('+users[i].id+')"></i></td>';
				}else{
					s +=  '<td><i class="icon-user" style="color: #CCC;" onclick="showchat('+users[i].id+')"></i></td>';
				}
				if(users[i].admin == '0'){
					s += '<td><a href="#myModal" role="button" data-toggle="modal"><i>-</i></a></td>';
				}else{
					s += '<td><a href="#myModal" role="button" data-toggle="modal"><i>'+users[i].count+'</i></a></td>';
				}
				if(admin == 2){
					if(users[i].admin == 2){
						s += '<td style="color:red;" id="usercontrol'+users[i].id+'">'+'-'+'</td>';
					}else if(users[i].admin == 1){
						s += '<td style="color:blue;"onclick="cancelAdmin('+users[i].id+');" id="functioncontrol'+users[i].id+'"><i class="icon-minus-sign" title="取消管理员身份" style="color: red;"  id="usercontrol'+users[i].id+'"></td>';
					}else if(users[i].admin == 0){
						s += '<td style="color:#ccc;" onclick="setAdmin('+users[i].id+');" id="functioncontrol'+users[i].id+'"><i class="icon-plus-sign" title="添加管理员身份" style="color: blue;"  id="usercontrol'+users[i].id+'"></td>';
					}
				}else{
					s += '<td style="color:red;" id="usercontrol'+users[i].id+'">'+'-'+'</td>';
				}
				
		html += s;
	}
	$("#tblibrary").empty().append(html);
}

var cancelAdmin = function(id){
	$.ajax({
		 type: "POST",
		 url: "./cancelAdmin.do",
		 data:{
			"id":id 
		 },
		 success: function(msg){
			$("#useradmin"+id).text("一般用户").css('color','#ccc');
			$("#usercontrol"+id).removeClass('icon-minus-sign').addClass('icon-plus-sign').attr('title','添加管理员').css('color','blue');
			$("#functioncontrol"+id).attr('onclick','setAdmin('+id+');');
		 }
	});
};
var setAdmin = function(id){
	$.ajax({
		type: "POST",
		 url: "./setAdmin.do",
		 data:{
			"id":id 
		 },
		 success: function(msg){
			$("#useradmin"+id).text("普通管理员").css('color','blue');
			$("#usercontrol"+id).removeClass('icon-plus-sign').addClass('icon-minus-sign').attr('title','取消管理员').css('color','red');
			$("#functioncontrol"+id).attr('onclick','cancelAdmin('+id+');');
		 }
	});
};

var user;
var showchat = function(id){
	$.ajax({
		type: "POST",
		url: "./getUserInfoById.do",
		data:{
			"id":id
		},
		success:function(msg){
			user = $.parseJSON(msg).nickname;
			$("#myModalLabeltitle").text(user);
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

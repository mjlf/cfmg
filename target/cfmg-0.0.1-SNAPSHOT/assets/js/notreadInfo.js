$("#adminnotreadinfo").live('click', function(){
	findinfo();
	$.ajax({
		 type: "get",
		 url: "./toReadInfoPage.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("消息列表");
		 }
	});
});

$("#userconuntchatmessagepage").live('click', function(){
	findinfoUser();
	$.ajax({
		 type: "get",
		 url: "./toReadInfoPage.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("消息列表");
		 }
	});
});

var findinfoUser = function(){
	$.ajax({
		url:"./getalluserforinfo.do",
		async:"true",
		type:"post",
		success:function(msg){
			msg = $.parseJSON(msg);
			var html = "";
			for(var i = 0; i < msg.length; i++){
				var div = '<div class="info" onclick="showmessageUser('+msg[i].id+')"><span>'+msg[i].nickname+'</span><div class="num" id="allinfospannum">'+msg[i].num+'</div></div>'
				html += div;
			}
			$("#allinfoUserdiv").empty().append(html);
		}
	});
}


var findinfo = function(){
	$.ajax({
		url:"./getalluserforinfo.do",
		async:"true",
		type:"post",
		success:function(msg){
			msg = $.parseJSON(msg);
			var html = "";
			for(var i = 0; i < msg.length; i++){
				var div = '<div class="info" onclick="showmessage('+msg[i].id+')"><span>'+msg[i].nickname+'</span><div class="num" id="allinfospannum">'+msg[i].num+'</div></div>'
				html += div;
			}
			$("#allinfoUserdiv").empty().append(html);
		}
	});
}

var showmessageUser = function(id){
	$.ajax({
		url:"./getUserInfoById.do",
		async:"false",
		type:"post",
		data:{
			"id":id
		},
		success:function(msg){
			msg = $.parseJSON(msg);
			$("#tousernickname").val(msg.nickname);
			$("#touserid").val(id);
			$("#usercharheadtitle").text(msg.nickname);
		}
	});
	$.ajax({
		url:"./getUserInfo.do",
		async:"false",
		type:"get",
		success:function(msg1){
			msg1 = $.parseJSON(msg1)
			$("#fromusernickname").val(msg1.nickname);
			$("#fromuserid").val(msg1.id);
			$("#findMessageInfo").click();
		}
	});
	$("#showchat").modal('show');
}

var showmessage = function(id){
	$.ajax({
		url:"./getUserInfoById.do",
		async:"false",
		type:"post",
		data:{
			"id":id
		},
		success:function(msg){
			msg = $.parseJSON(msg);
			$("#tousernickname").val(msg.nickname);
			$("#touserid").val(id);
			$("#myModalLabeltitle").text(msg.nickname);
		}
	});
	$.ajax({
		url:"./getUserInfo.do",
		async:"false",
		type:"get",
		success:function(msg1){
			msg1 = $.parseJSON(msg1)
			$("#fromusernickname").val(msg1.nickname);
			$("#fromuserid").val(msg1.id);
			$("#findMessageInfo").click();
		}
	});
	$("#showchat").modal('show');
}
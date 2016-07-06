var Chat = {};

Chat.socket = null;

Chat.connect = (function(host) {
	if ('WebSocket' in window) {
		Chat.socket = new WebSocket(host);
	} else if ('MozWebSocket' in window) {
		Chat.socket = new MozWebSocket(host);
	} else {
		return;
	}

	Chat.socket.onopen = function() {
		$.ajax({
			type: "GET",
			async:'true',
			url: "./getUserId.do",
			success:function(msg){
				Chat.socket.send("!!<<id>>!!:"+msg);
			}
		});
		
		$("#sendinfo").live('click', function(){
			var message = $("#editsendinfochat").val();
			if(message.trim() == ''){
				return;
			}
			$("#editsendinfochat").val("");
			var p = '<tr><td width=10%></td>'+
					'<td width=80% style="text-align:right">'+message+'</td>'+
					'<td width=10%><i class="icon-user" style="color: orange;")"></i></td></tr>';
			var fromuserid = $("#fromuserid").val();
			var touser = $("#tousernickname").val();
			$.ajax({
				type: "POST",
				async:'true',
				url: "./addChatMessage.do",
				data:{
					"fromUserId":$("#fromuserid").val(),
					"toUserId":$("#touserid").val(),
					"messageData":message
				},
				success: function(info){
					
				}
			});
			Chat.socket.send("!!<<id:msg>>!!:"+touser+":"+$("#touserid").val()+":"+$("#fromuserid").val()+":"+message);
			$("#allmessage").append(p);
			var console =  document.getElementById('messagediv');
			console.scrollTop = console.scrollHeight;
		});
	};

	Chat.socket.onclose = function() {
		alert("close");
	};
	
	Chat.socket.onerror = function() {
		alert("onerror");
	};
	
	Chat.socket.onmessage = function(message) {
		var messageData = message.data;
		try {
			if(messageData.indexOf('!!<<id:bookLibrary>>!!:') == 0){
				var userId = messageData.split(":")[2];
				$.ajax({
					type: "GET",
					url: "./getUserInfo.do",
					async:"true",
					success:function(msg){
						msg = $.parseJSON(msg);
						if(userId == msg.id){
							$("#adminBookNumNotDo").text(parseInt($("#adminBookNumNotDo").text())+1);
						}
					}
				});
			}else if(messageData.indexOf('!!<<id:bookLibraryAccept>>!!') == 0){
				var userId = messageData.split(":")[2];
				$.ajax({
					type: "GET",
					url: "./getUserInfo.do",
					async:"true",
					success:function(msg){
						msg = $.parseJSON(msg);
						if(userId == msg.id){
							$("#newBookLibraryAccept").text(parseInt($("#newBookLibraryAccept").text())+1);
						}
					}
				});
			}else{
				var userId = messageData.split(":")[0];
				var fromUserId = messageData.split(":")[1];
				var msgInfo = messageData.split(":")[2];
				
				var fromUserid = $("#fromuserid").val();
				if(userId == fromUserid && fromUserId == $("#touserid").val()){
					var p = '<tr><td width=10%><i class="icon-user" style="color: red;")"></i></td>'+
					'<td width=80% style="text-align:left">'+msgInfo+'</td>'+
					'<td width=10%></td></tr>';
					$("#allmessage").append(p);
				}
				var console = document.getElementById('messagediv');
				console.scrollTop = console.scrollHeight;
			}
		} catch (e) {
			
		}
	};
});


Chat.initialize = function() {
	if (window.location.protocol == 'http:') {
		Chat.connect('ws://' + window.location.host + '/cfmg/cfmg/websocket.do');
	} else {
		Chat.connect('wss://' + window.location.host + '/cfmg/cfmg/websocket.do');
	}
	
	$.ajax({
		type: "GET",
		url: "./getUserInfo.do",
		async:"true",
		success:function(msg){
			msg = $.parseJSON(msg);
			$("#indexAdminUserId").val(msg.id);
			$("#indexCommonUserId").val(msg.id);
		}
	});
};


Chat.initialize();

$("#doBookLibrary").live('click', function(){
	$.ajax({
		url:"./toBookLibrary.do",
		type:"post",
		async:'true',
		data:{
			"startTimeStr": $("#startTimeStrForBookAndCan").val(),
			"endTimeStr" : $("#endTimeStrForBookAndCan").val(),
			"bookStartTimeStr" : $("#bookLibraryInfoStartTime").val(),
			"bookEndTimeStr" : $("#bookLibraryInfoEndTime").val(),
			"peopleNum" : $("#bookLibraryPeopleNum").val(),
			"libraryId" :$("#bookLibraryLibraryId").val()
		},
		success : function(msg){
			if(msg == "Y"){
				$("#bookLibrarySuccessFromLibrarys").modal('show');
				//会议室预定信息发送成功
				Chat.socket.send("!!<<id:bookLibrary>>!!:"+$("#bookLibraryAdminId").val());
			}else{
				$("#bookLibraryFailedFromLibrarys").modal('show');
			}
		},
		error : function(){
			$("#bookLibraryExceptionFromLibrarys").modal('show');
		}
	});
});

$("#candoBookLibrary").live('click', function(){
	$.ajax({
		url:"./toBookLibrary.do",
		type:"post",
		async:'true',
		data:{
			"startTimeStr": $("#canstartTimeStrForBookAndCan").val(),
			"endTimeStr" : $("#canendTimeStrForBookAndCan").val(),
			"bookStartTimeStr" : $("#canbookLibraryInfoStartTime").val(),
			"bookEndTimeStr" : $("#canbookLibraryInfoEndTime").val(),
			"peopleNum" : $("#canbookLibraryPeopleNum").val(),
			"libraryId" :$("#canbookLibraryLibraryId").val()
		},
		success : function(msg){
			if(msg == "Y"){
				$("#canbookLibrarySuccessFromLibrarys").modal('show');
				//会议室预定信息发送成功
				Chat.socket.send("!!<<id:bookLibrary>>!!:"+$("#canbookLibraryAdminId").val());
			}else{
				$("#canbookLibraryFailedFromLibrarys").modal('show');
			}
		},
		error : function(){
			$("#canbookLibraryExceptionFromLibrarys").modal('show');
		}
	});
});
var accetpBook = function(id){
	Chat.socket.send("!!<<id:bookLibraryAccept>>!!:"+id);
}

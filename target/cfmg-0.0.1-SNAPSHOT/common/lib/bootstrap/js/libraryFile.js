$("#tolong").live('click', function(){
	$("#userLogin").modal('show');
});

$.ajax({
	url:"/cfmg/cfmg/getuserName.do",
	async:"true",
	type:"GET",
	success : function(msg){
		if(msg == 'N'){
			$("#userLoginfailed").modal('show');
		}else{
			msg = $.parseJSON(msg);
			$("#nameasdfasd").text(msg.name);
		}
	}
});

$("#loginto").live('click', function(){
	$.ajax({
		url:"/cfmg/cfmg/logintobook.do",
		async:"true",
		type:"post",
		data:{
			"name":$("#loginid").val(),
			"password":$("#password").val()
		},
		success : function(msg){
			if(msg == 'N'){
				$("#userLoginfailed").modal('show');
			}else{
				msg = $.parseJSON(msg);
				$("#nameasdfasd").text(msg.name);
			}
		}
	});
});

$("#addFile").live('click', function(){
	$("#uploadFile").modal('show');
});

$("#douploadFile").live('click', function(){
	$.ajaxFileUpload({
		url : '/cfmg/cfmg/uploadLibraryBookFile.do',
		fileElementId:'uploadFileuploadinfo',
		dataType: 'JSON',
		secureuri:false,
		success : function(data, status) {
			$("#successaddfile").modal('show');
		},
		error:function(data, status, e){
			alert(e);
		}
	});
});

$("#showAllFile").live('click', function(){
	$.ajax({
		url:"/cfmg/cfmg/findAllFileByLibraryBook.do",
		async:"true",
		type:"POST",
		data:{
			"index":1,
			"everypagenum":10
		},
		success:function(msg){
			msg = $.parseJSON(msg);
			files = msg.list[0];
			var html = "";
			for(var i = 0; i < files.length; i++){
				var s = '<tr id="libraryBookFile'+files[i].id+'">'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+files[i].fileName+'</td>'
						+'<td>'+files[i].fileType+'</td>'
						+'<td>'+files[i].fileLength+'</td>'
						+'<td>'+files[i].uploadUserName+'</td>'
						+'<td>'+files[i].uploadTimeStr+'</td>'
						+'<td><a href="'+files[i].downloadPath+'">下载</a></td>'
						+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="deletefile('+files[i].id+')">删除</button></td>'
						+'</tr>';
				html += s;
			}
			$("#allFileTbody").empty().append(html);
			
			var info = {
					"count":msg.pageCount,
					"start":msg.pageIndex,
					"display":10,
					"pageId":"userpagefile",
					"executed": executefindAllFile,
					"everypagenum":10
			};
			page(info);
		}
	});
});


var executefindAllFile = function(index, everyPageNum){
	$.ajax({
		url:"/cfmg/cfmg/findAllFileByLibraryBook.do",
		async:"true",
		type:"POST",
		data:{
			"index":index,
			"everypagenum":everyPageNum
		},
		success:function(msg){
			msg = $.parseJSON(msg);
			var files = msg.list[0];
			var html = "";
			for(var i = 0; i < files.length; i++){
				var s = '<tr id="libraryBookFile'+files[i].id+'">'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+files[i].fileName+'</td>'
						+'<td>'+files[i].fileType+'</td>'
						+'<td>'+files[i].fileLength+'</td>'
						+'<td>'+files[i].uploadUserName+'</td>'
						+'<td>'+files[i].uploadTimeStr+'</td>'
						+'<td><a href="'+files[i].downloadPath+'">下载</a></td>'
						+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="deletefile('+files[i].id+')">删除</button></td>'
						+'</tr>';
				html += s;
			}
			$("#allFileTbody").empty().append(html);
		}
	});
};

var deletefile = function(id){
	$.ajax({
		url:"/cfmg/cfmg/deleteLibraryBookFile.do",
		asnyc:"true",
		type:"post",
		data:{
			"fileId":id
		},
		success:function(msg){
			if(msg == 'Y'){
				$("#libraryBookFile"+id).remove();
			}else{
				$("#userLoginfailed").modal('show');
			}
		}
	});
}
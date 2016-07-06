$("#acceptedBookLibraryList").live("click", function(){
	$.ajax({
		url:"./findLibraryBookDoingPageValue.do",
		async:"true",
		type:"POST",
		data:{
			"index":1,
			"everypagenum":10,
			"state":1
		},
		success:function(msg){
			msg = $.parseJSON(msg);
			books = msg.list[0];
			var html = "";
			for(var i = 0; i < books.length; i++){
				var s = '<tr id="libraryBookDid'+books[i].id+'">'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+books[i].id+'</td>'
						+'<td>'+books[i].nameBookUser+'</td>'
						+'<td>'+books[i].peopleNum+'</td>'
						+'<td>'+books[i].libraryPeopleNum+'</td>'
						+'<td>'+books[i].startTimeStr+'</td>'
						+'<td>'+books[i].endTimeStr+'</td>'
						+'<td>'+books[i].address+'</td>'
						+'<td>Y</td>'
						+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="managePeople('+books[i].id+')">人员</button></td>'
						+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="manageFile('+books[i].id+')">文件</button></td>'
						+'</tr>';
				html += s;
			}
			$("#tblibraryBookDid").empty().append(html);
			
			var info = {
					"count":msg.pageCount,
					"start":msg.pageIndex,
					"display":10,
					"pageId":"pageIndexBookLibararyDid",
					"executed": executeAdminBookLibraryDid,
					"everypagenum":10
			};
			page(info);
		}
	});
	$.ajax({
		 type: "get",
		 url: "./toBookLibraryCommonPagedid.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("待处理预定列表");
		 }
	});
});

var executeAdminBookLibraryDid =  function (index, everypagenum){
	var datainfo = {};
	datainfo['index'] = index;
	datainfo['everypagenum'] = everypagenum;
	datainfo['state'] = 0;
	$.ajax({
		url:"./findLibraryBookDoingPageValue.do",
		async:"true",
		type:"POST",
		data:datainfo,
		success:function(msg){
			msg = $.parseJSON(msg);
			books = msg.list;
			var html = "";
			for(var i = 0; i < msg.length; i++){
				var s = '<tr id="libraryBookDid'+books[i].id+'">'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+books[i].id+'</td>'
						+'<td>'+books[i].nameBookUser+'</td>'
						+'<td>'+books[i].peopleNum+'</td>'
						+'<td>'+books[i].libraryPeopleNum+'</td>'
						+'<td>'+books[i].startTimeStr+'</td>'
						+'<td>'+books[i].endTimeStr+'</td>'
						+'<td>'+books[i].address+'</td>'
						+'<td>Y</td>'
						+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="managePeople('+books[i].id+')">人员</button></td>'
						+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="manageFile('+books[i].id+')">文件</button></td>'
						+'</tr>';
				html += s;
			}
			$("#tblibraryBookDid").empty().append(html);
		}
	});
}

var executeAdminBookLibraryPeople = function(index, everypagenum){
	$.ajax({
		url:"./findALibraryBookPeople.do",
		async:"true",
		type:"POST",
		data:{
			"index":index,
			"everypagenum":everypagenum,
			"libraryBookId":$("#uploadPeopleFilebookLibraryId").val()
		},
		success:function(msg){
			msg = $.parseJSON(msg);
			peoples = msg.list[0];
			var html = "";
			for(var i = 0; i < peoples.length; i++){
				var s = '<tr id="libraryBookPeople'+peoples[i].id+'">'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+peoples[i].name+'</td>'
						+'<td>'+peoples[i].phone+'</td>'
						+'<td>'+peoples[i].email+'</td>'
						+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="updatePeople('+peoples[i].id+')">修改</button></td>'
						+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="deletePeople('+peoples[i].id+')">删除</button></td>'
						+'</tr>';
				html += s;
			}
			$("#tblibraryBookPeople").empty().append(html);
		}
	});
};

var managePeople = function(id){
	$.ajax({
		url:"./findALibraryBookPeople.do",
		async:"true",
		type:"POST",
		data:{
			"index":1,
			"everypagenum":10,
			"libraryBookId":id
		},
		success:function(msg){
			msg = $.parseJSON(msg);
			peoples = msg.list[0];
			var html = "";
			for(var i = 0; i < peoples.length; i++){
				var s = '<tr id="libraryBookPeople'+peoples[i].id+'">'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+peoples[i].name+'</td>'
						+'<td>'+peoples[i].phone+'</td>'
						+'<td>'+peoples[i].email+'</td>';
					if(peoples[i].isSign_in == '1'){
						s += '<td>'+'已签到'+'</td>';
					}else{
						s += '<td>'+'未签到'+'</td>';
					}
						
						s += '<td><button class="btn btn-danger" data-dismiss="modal" onclick="updatePeople('+peoples[i].id+')">修改</button></td>'
						+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="deletePeople('+peoples[i].id+')">删除</button></td>'
						+'</tr>';
				html += s;
			}
			$("#tblibraryBookPeople").empty().append(html);
			
			var info = {
					"count":msg.pageCount,
					"start":msg.pageIndex,
					"display":10,
					"pageId":"pageIndexBookLibararyPeople",
					"executed": executeAdminBookLibraryPeople,
					"everypagenum":10
			};
			page(info);
		}
	});
	$.ajax({
		 type: "get",
		 url: "./toLibraryBookPeoplepage.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("参会人员管理");
			 $("#uploadPeopleFilebookLibraryId").val(id);
		 }
	});
};

var deletePeople = function(id){
	$.ajax({
		url:"./deleteAPeople.do",
		type:"post",
		async:"true",
		data:{
			"peopleId":id
		},
		success:function(msg){
			if(msg == 'Y'){
				$("#libraryBookPeople"+id).remove();
			}else{
				$("#deletepleFilefailed").modal('show');
			}
		}
	});
}

var updatePeople = function(id){
	$("#updatePeopleId").val(id);
	$.ajax({
		url:"./findApeopleforupdate.do",
		type:"post",
		async:"true",
		data:{
			"peopleId":id
		},
		success:function(msg){
			msg = $.parseJSON(msg);
			$("#updatePeopleName").val(msg.name);
			$("#updatePeoplePhone").val(msg.phone);
			$("#updatePeopleEmail").val(msg.email);
			$("#updatePeopleWriteInfo").modal('show');
		}
	});
};

$("#updatePeopleforone").live('click', function(){
	$.ajax({
		url:"./updatePoepleAtOne.do",
		type:"post",
		async:"true",
		data:{
			"peopleId":$("#updatePeopleId").val(),
			"name":$("#updatePeopleName").val(),
			"phone":$("#updatePeoplePhone").val(),
			"email":$("#updatePeopleEmail").val()
		},
		success : function(msg){
			if(msg == 'Y'){
				var children = $("#libraryBookPeople"+$("#updatePeopleId").val()).children();
				children.eq(1).text($("#updatePeopleName").val());
				children.eq(2).text($("#updatePeoplePhone").val());
				children.eq(3).text($("#updatePeopleEmail").val());
			}else{
				$("#deletepleFilefailed").modal('show');
			}
		}
	});
});

$("#doaddPeopleforone").live('click', function(){
	$.ajax({
		url:"./addPoepleAtOne.do",
		type:"post",
		async:"true",
		data:{
			"librarybookid":$("#uploadPeopleFilebookLibraryId").val(),
			"name":$("#addPeopleName").val(),
			"phone":$("#addPeoplePhone").val(),
			"email":$("#addPeopleEmail").val()
		},
		success : function(msg){
			if(msg == 'N'){
				$("#deletepleFilefailed").modal('show');
			}else{
				msg = $.parseJSON(msg);
				var s = '<tr id="libraryBookPeople'+msg.id+'">'
				+'<td>-</td>'
				+'<td>'+msg.name+'</td>'
				+'<td>'+msg.phone+'</td>'
				+'<td>'+msg.email+'</td>';
				if(peoples[i].isSign_in == '1'){
					s += '<td>'+'已签到'+'</td>';
				}else{
					s += '<td>'+'未签到'+'</td>';
				}
				+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="updatePeople('+msg.id+')">修改</button></td>'
				+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="deletePeople('+msg.id+')">删除</button></td>'
				+'</tr>';
				$("#tblibraryBookPeople").append(s);
			}
		}
	});
});

var manageFile = function(id){
	$.ajax({
		url:"./loadLibraryBookFile.do",
		async:"true",
		type:"POST",
		data:{
			"index":1,
			"everyPageNum":10,
			"bookLibraryId":id
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
						+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="deleteFile('+files[i].id+')">删除</button></td>'
						+'</tr>';
				html += s;
			}
			$("#tblibraryBookFile").empty().append(html);
			
			var info = {
					"count":msg.pageCount,
					"start":msg.pageIndex,
					"display":10,
					"pageId":"pageIndexBookLibararyFile",
					"executed": executeAdminBookLibraryPeople,
					"everypagenum":10
			};
			page(info);
		}
	});
	$.ajax({
		 type: "get",
		 url: "./toLibraryFileList.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("文件浏览");
			 $("#libraryFileId").val(id);
		 }
	});
};

var executeFileLoad = function(index, everypagenum){
	$.ajax({
		url:"./loadLibraryBookFile.do",
		async:"true",
		type:"POST",
		data:{
			"index":index,
			"everypagenum":everypagenum,
			"bookLibraryId": $("#libraryFileId").val()
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
						+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="deleteFile('+files[i].id+')">删除</button></td>'
						+'</tr>';
				html += s;
			}
			$("#tblibraryBookFile").empty().append(html);
		}
	});
}

var deleteFile = function(id){
	$.ajax({
		url:"./deleteLibraryBookFile.do",
		async:"true",
		type:"post",
		data:{
			"fileId":id
		},
		success: function(msg){
			if(msg == 'Y'){
				$("#libraryBookFile"+id).remove();
			}
		}
	});
}
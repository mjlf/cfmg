$("#libraryBook, #adminBookNumNotDo").live("click", function(){
	$.ajax({
		url:"./findLibraryBookDoingPageValue.do",
		async:"true",
		type:"POST",
		data:{
			"index":1,
			"everypagenum":10,
			"state":0
		},
		success:function(msg){
			msg = $.parseJSON(msg);
			books = msg.list[0];
			var html = "";
			for(var i = 0; i < books.length; i++){
				var s = '<tr id="libraryBookDoingtr'+books[i].id+'">'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+books[i].nameBookUser+'</td>'
						+'<td>'+books[i].peopleNum+'</td>'
						+'<td>'+books[i].libraryPeopleNum+'</td>'
						+'<td>'+books[i].startTimeStr+'</td>'
						+'<td>'+books[i].endTimeStr+'</td>'
						+'<td>'+books[i].address+'</td>'
						+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="accept('+books[i].id+','+books[i].bookUserId+')">同意</button></td>';
						+'</tr>';
				html += s;
			}
			$("#tblibraryBookDing").empty().append(html);
			
			var info = {
					"count":msg.pageCount,
					"start":msg.pageIndex,
					"display":10,
					"pageId":"pageIndexBookLibararyDoing",
					"executed": executeAdminBookLibraryDoing,
					"everypagenum":10
			};
			page(info);
		}
	});
	$.ajax({
		 type: "get",
		 url: "./toBookLibraryPage.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("待处理预定列表");
		 }
	});
});

var executeAdminBookLibraryDoing =  function (index, everypagenum){
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
				var s = '<tr id="libraryBookDoingtr'+msg[i].id+'">'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+books[i].nameBookUser+'</td>'
						+'<td>'+books[i].peopleNum+'</td>'
						+'<td>'+books[i].libraryPeopleNum+'</td>'
						+'<td>'+books[i].startTimeStr+'</td>'
						+'<td>'+books[i].endTimeStr+'</td>'
						+'<td>'+books[i].address+'</td>'
						+'<td><button class="btn btn-danger" data-dismiss="modal" onclick="accept('+books[i].id+','+books[i].bookUserId+')">同意</button></td>';
						+'</tr>';
				html += s;
			}
			$("#tblibraryBookDing").empty().append(html);
		}
	});
}

var accept = function(id, bookUserId){
	$.ajax({
		url:"./updataLibraryBookDoing.do",
		asnyc:"true",
		type:"POST",
		data:{
			"libraryBookId":id
		},
		success:function(msg){
			if(msg == 'Y'){
				accetpBook(bookUserId);
				$("#adminBookNumNotDo").text(parseInt($("#adminBookNumNotDo").text())-1);
				$("#libraryBookDoingtr"+id).remove();
			}
		}
	});
}
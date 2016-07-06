$("#libraryBookList").live("click", function(){
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
				var s = '<tr id="libraryBookDoingtr'+books[i].id+'">'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+books[i].nameBookUser+'</td>'
						+'<td>'+books[i].peopleNum+'</td>'
						+'<td>'+books[i].libraryPeopleNum+'</td>'
						+'<td>'+books[i].startTimeStr+'</td>'
						+'<td>'+books[i].endTimeStr+'</td>'
						+'<td>'+books[i].address+'</td>'
						+'<td>已审核</td>';
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
		 url: "./toBookLibraryPagedid.do",
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
				var s = '<tr id="libraryBookDoingtr'+msg[i].id+'">'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+books[i].nameBookUser+'</td>'
						+'<td>'+books[i].peopleNum+'</td>'
						+'<td>'+books[i].libraryPeopleNum+'</td>'
						+'<td>'+books[i].startTimeStr+'</td>'
						+'<td>'+books[i].endTimeStr+'</td>'
						+'<td>'+books[i].address+'</td>'
						+'<td>已审核</td>';
						+'</tr>';
				html += s;
			}
			$("#tblibraryBookDid").empty().append(html);
		}
	});
}
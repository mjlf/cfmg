$("#waitAcceptBookLibraryList").live("click", function(){
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
						+'<td>'+books[i].address+'</td>';
						if(books[i].state == 0){
							s += '<td>等待审核</td>'
							+'</tr>';
						}else{
							s += '<td>审核通过</td>'
							+'</tr>';
						}
				html += s;
			}
			$("#tblibraryBookDingUserWait").empty().append(html);
			
			var info = {
					"count":msg.pageCount,
					"start":msg.pageIndex,
					"display":10,
					"pageId":"pageIndexBookLibararyDoingUserWait",
					"executed": executeAdminBookLibraryDoingWait,
					"everypagenum":10
			};
			page(info);
		}
	});
	$.ajax({
		 type: "get",
		 url: "./toBookLibraryPageUserWait.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("待审核列表");
		 }
	});
});

var executeAdminBookLibraryDoingWait =  function (index, everypagenum){
	var datainfo = {};
	datainfo['index'] = index;
	datainfo['everypagenum'] = everypagenum;
	datainfo['state'] = 0;
	$.ajax({
		url:"findLibraryBookDoingPageValue.do",
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
						if(books[i].state == 0){
							s += '<td>等待审核</td>'
							+'</tr>';
						}else{
							s += '<td>审核通过</td>'
							+'</tr>';
						}
				html += s;
			}
			$("#tblibraryBookDingUserWait").empty().append(html);
		}
	});
}
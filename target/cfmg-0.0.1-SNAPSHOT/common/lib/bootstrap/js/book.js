$("#tobookLibrary").live('click', function(){
	$.ajax({
		 type: "get",
		 url: "./tobookPagewriteinfo.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("预定");
		 }
	});
});
$("#doBookLibraryBook").live('click', function(){
	$.ajax({
		url:"./findSomedayCanBookLibraryBook.do",
		async:"true",
		type:"post",
		data:{
			"peopleNum":$("#bookLibraryAddressBook").val(),
			"address":$("#bookLibraryPeopleNumBook").val(),
			"time":$("#bookLibraryTimeBook").val()
		},
		success:function(msg){
			var libraryBook = $.parseJSON(msg);
			var html
			for(var i = 0; i <libraryBook.length; i++){
				var user = libraryBook[i].user;
				var library = libraryBook[i].library;
				var tr = '<tr>';
				tr += '<td>'+(i+1)+'</td>';
				tr += '<td>'+user.nickname+'</td>';
				tr += '<td id="canlibraryBookStartTimeStr'+i+'">'+libraryBook[i].startTimeStr+'</td>';
				tr += '<td id="canlibraryBookEndTimeStr'+i+'">'+libraryBook[i].endTimeStr+'</td>';
				tr += '<td>'+library.people+'</td>';
				tr += '<td>'+library.address+'</td>';
				tr += '<td>'+libraryBook[i].length+'</td>';
				tr += '<td ><button class="btn btn-danger" onclick="canbook('+libraryBook[i].libraryId+','+i+')">预定</button></td>';
				tr += '</tr>';
				html += tr;
			}
			$("#cantblibraryBooks").empty().append(html);
			$("#canbooklibrary").modal('show');
		}
	});
});

var canbook = function(id, i){
	$("#canstartTimeStrForBookAndCan").val($("#canlibraryBookStartTimeStr"+i).text());
	$("#canendTimeStrForBookAndCan").val($("#canlibraryBookEndTimeStr"+i).text());
	$("#canbookLibraryLibraryId").val(id);
	$.ajax({
		url:"./findALibraryNotImg.do",
		type:"post",
		async:"true",
		data:{
			"libraryId": id,
		},
		success : function(msg){
			msg = $.parseJSON(msg);
			$("#canbookLibraryAdminId").val(msg.adminId);
		}
	});
	$("#canbookLibraryWriteInfo").modal('show');
}
$(document).ready(function(){
	$.ajax({
		url:"./findLibraryBookByLibraryIdAndState.do",
		type:"POST",
		async:"true",
		data:{
			"state":"0"
		},
		success:function(msg){
			msg = $.parseJSON(msg);
			$("#adminBookNumNotDo").text(msg.length);
		}
	});
});
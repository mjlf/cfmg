$("#toknow").live('click', function(){
	$.ajax({
		 type: "get",
		 url: "./toknow.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("预定流程");
			 $("#usernamedan").attr('href', "/cfmg/upload/user1234567890.xlsx");
		 }
	});
});
$("#toknowUser").live('click', function(){
	$.ajax({
		 type: "get",
		 url: "./toknow.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("预定流程");
			 $("#usernamedan").attr('href', "/cfmg/upload/user1234567890.xlsx");
		 }
	});
});


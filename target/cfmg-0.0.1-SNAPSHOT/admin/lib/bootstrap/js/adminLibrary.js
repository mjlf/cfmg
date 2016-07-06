
$("#libraryAdd").live("click", function(){
	$.ajax({
		 type: "get",
		 url: "./toAddLibrary.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("添加会议室");
		 }
	});
});

$("#addLibraryBtn").live("click", function(){
	$.ajax({
		type:"post",
		async:"false", 
		url:"./addLibrary.do",
	    data:{
	    	"people": $("#libraryPeopleNum").val(),
			"address" : $("#libraryAddressText").val(),
			"amhourstart" : $("#amhourstart").val(),
			"amminstart" : $("#amminstart").val(),
			"amsecstart" : $("#amsecstart").val(),
			"amhourend" : $("#amhourend").val(),
			"amminend" : $("#amminend").val(),
			"amsecend" : $("#amsecend").val(),
			"pmhourstart" : $("#pmhourstart").val(),
			"pmminstart" : $("#pmminstart").val(),
			"pmsecstart" : $("#pmsecstart").val(),
			"pmhourend" : $("#pmhourend").val(),
			"pmminend" : $("#pmminend").val(),
			"pmsecend" : $("#pmsecend").val(),
			"isProjection" : $("#isProjection").val(),
			"videoConferencing" : $("#videoConferencing").val(),
			"disc" : $("#disc").val(),
			"bookAndKnow" : $("#bookAndKnow").val()
	    },
	    success : function(result){
	    	if(result == 'H' || result == 'N'){
	    		$("#addlibraryinfo").text("添加失败").css("color", "red");
	    	}else{
	    		var img = $("#showpicture img");
	    		for(var i = 0; i < img.length; i++){
	    			var imgi = img.eq(i)[0].src;
	    			$.ajax({
	    				type:"post",
	    				async:"false",
	    				url:"./addicon.do",
	    				data:{
	    					"libraryId": result,
	    					"pictureName":imgi,
	    					"type":"1000"
	    				},
	    				success : function(result){
	    					if(result == "N"){
	    						$("#addlibraryinfo").text("有图片添加失败").css("color", "red");
	    					}else{
	    						$("#addlibraryinfo").text("图片添加成功").css("color", "green");
	    					}
	    				}
	    			});
	    		}
	    		$("#libraryPeopleNum").val("");
	    		$("#disc").val("");
				$("#bookAndKnow").val("");
	    		$("#successadd").click();
	    		//显示添加成功
	    		$("#successadd").modal('show');
	    	}
	    }
	});
});

var nowid;

var showthis = function(id){
	var img1 = $("#"+id);
	var img = new Image();
	img.src = img1.attr("src");
	$(img).width('auto');
	$(img).height(400);
	$("#picinfo").empty().append(img);
	$("#showthispic").modal('show');
	nowid = id;
}

$("#deletepicthis").live('click', function(){
	$("#"+nowid).remove();
});


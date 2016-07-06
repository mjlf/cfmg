
var editlibrary = function(id){
	 $.ajax({
		 type: "get",
		 url: "./toEditLibrary.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("会议室编辑");
		 }
	});
	$.ajax({
		 type: "POST",
		 url: "./editFindALibrary.do",
		 data : {
			 "libraryId":id
		 },
		 success: function(msg){
			 msg = $.parseJSON(msg);
			 var imgs = msg.imgs;
			 $("#editlibraryPeopleId").val(msg.id);
			 $("#editlibraryPeopleNum").val(msg.peoplenum);
			 $("#editlibraryAddressText").val(msg.address);
			 $("#editamhourstart"+msg.amshour).attr("selected","selected");
			 $("#editamminstart"+msg.amsmin).attr("selected","selected");
			 $("#editamsecstart"+msg.amssec).attr("selected","selected");
			 $("#editamhourend"+msg.amehour).attr("selected","selected");
			 $("#editamminend"+msg.amemin).attr("selected","selected");
			 $("#editamsecend"+msg.amesec).attr("selected","selected");
			 $("#editpmhourstart"+msg.pmshour).attr("selected","selected");
			 $("#editpmminstart"+msg.pmsmin).attr("selected","selected");
			 $("#editpmsecstart"+msg.pmssec).attr("selected","selected");
			 $("#editpmhourend"+msg.pmehour).attr("selected","selected");
			 $("#eidtpmminend"+msg.pmemin).attr("selected","selected");
			 $("#editpmsecend"+msg.pmesec).attr("selected","selected");
			 
			 $("#isp"+msg.isp).attr("selected","selected");
			 $("#isa"+msg.isa).attr("selected","selected");
			 
			 $("#editdisc").text(msg.disc);
			 $("#showedpicture").empty();
			 $("#editbookAndKnow").text(msg.know);
			 for(var i = 0; i < imgs.length; i++){
				 var html = '<img class="allpic" alt="'+imgs[i].picName+'" id="'+imgs[i].id+'" onclick="showimage(\''+imgs[i].id+'\');" src='+imgs[i].img+' width="100px" >';
				 $("#showedpicture").append(html);
			 }
			 
		 }
	});
}
var addnewpic = function(){
	$("#findpicture").click();
}
var showimage = function(id){
	var img1 = $("#"+id);
	var img = new Image();
	img.src = img1.attr("src");
	$(img).width('auto');
	$(img).height(400);
	$(img).attr('alt', id);
	$("#editshowpicinfo").empty().append(img);
	$("#showeditpic").modal('show');
}

$("#deleteeditpicthis").click(function(){
	var id = $("#editshowpicinfo img").attr('alt');
	$.ajax({
		 type: "POST",
		 url: "./deleteIcon.do",
		 data : {
			 "id":id,
			 "picName":$("#"+id).attr('alt')
		 },
		 success : function(msg){
			 if(msg == 'Y'){
				 $("#"+id).remove();
			 }else{
				 $("#deleerror").modal('show');
			 }
		 },
		 error:function(){
			 $("#deleerror").modal('show');
		 }
	});
});

$("#editLibraryButton").click(function(){
	$.ajax({
		type:"post",
		async:"false", 
		url:"./editLibrary.do",
	    data:{
	    	"id":$("#editlibraryPeopleId").val(),
	    	"people": $("#editlibraryPeopleNum").val(),
			"address" : $("#editlibraryAddressText").val(),
			"amhourstart" : $("#editamhourstart").val(),
			"amminstart" : $("#editamminstart").val(),
			"amsecstart" : $("#editamsecstart").val(),
			"amhourend" : $("#editamhourend").val(),
			"amminend" : $("#editamminend").val(),
			"amsecend" : $("#editamsecend").val(),
			"pmhourstart" : $("#editpmhourstart").val(),
			"pmminstart" : $("#editpmminstart").val(),
			"pmsecstart" : $("#editpmsecstart").val(),
			"pmhourend" : $("#editpmhourend").val(),
			"pmminend" : $("#eidtpmminend").val(),
			"pmsecend" : $("#editpmsecend").val(),
			"isProjection" : $("#editisProjection").val(),
			"videoConferencing" : $("#editvideoConferencing").val(),
			"disc" : $("#editdisc").val(),
			"bookAndKnow" : $("#editbookAndKnow").val()
	    },
	    success : function(result){
	    	if(result == 'Y'){
	    		$("#successeditinfo").modal('show');
	    	}else{
	    		$("#editfailedinfo").modal('show');
	    	}
	    }
	});
	
	var img = $("#showpicture img");
	for(var i = 0; i < img.length; i++){
		var imgi = img.eq(i)[0].src;
		$.ajax({
			type:"post",
			async:false,
			url:"./addicon.do",
			data:{
				"libraryId":$("#editlibraryPeopleId").val(),
				"pictureName":imgi,
				"type":"1000"
			},
			success : function(result){
				if(result == "N"){
					$("#erroraddpic").modal('show');
				}else{
					result = $.parseJSON(result);
					 var html = '<img class="allpic" alt="'+result.pictrue+'" id="'+result.id+'" onclick="showimage(\''+result.id+'\');" src='+imgi+' width="100px" >';
					 $("#showedpicture").append(html);
				}
			}
		});
	}
});

$("#truedeleteLibrary").click(function(){
	var id = $("#deletelibid").val();
	$.ajax({
		type:"post",
		async:true,
		url:"./deleteLibrary.do",
		data:{
			"id": id
		},
		success : function(msg){
			if(msg == 'Y'){
				$("#ok").modal('show');
				reload();
			}else{
				$("#failedel").modal('show');
			}
		}
	});
	reload();
	$("#deletelibid").val("");
	$("#myModal").modal('hide')
});
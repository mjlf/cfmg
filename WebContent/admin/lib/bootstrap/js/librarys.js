
var indexpic = 0;
var allimgs;
function showApic(index){
	 $("#libshowpic").empty().append('<img src="'+allimgs[index].img+'">');
}
$("#showleftpic").live("click", function(){
	if(indexpic - 1 >= 0){
		indexpic -= 1;
		$("#libshowallpic").empty();
		for(var i = indexpic; i < allimgs.length; i++){
			 var imginfo = allimgs[i].img;
			 var html = '<img class="allpic" src='+imginfo+' width="100px" onClick="showApic('+i+')">';
			 $("#libshowallpic").append(html);
		}
	}
});
$("#showrightpic").live("click", function(){
	if(indexpic + 1 <= allimgs.length - 3){
		indexpic += 1;
		$("#libshowallpic").empty();
		for(var i = indexpic; i < allimgs.length; i++){
			 var imginfo = allimgs[i].img;
			 var html = '<img class="allpic" src='+imginfo+' width="100px" onClick="showApic('+i+')">';
			 $("#libshowallpic").append(html);
		}
	}
});

var showInfo = function (id) {
	$.ajax({
		 type: "POST",
		 url: "./findALibrary.do",
		 data : {
			 "libraryId":id
		 },
		 success: function(msg){
			 msg = $.parseJSON(msg);
			 $("#libshowinfoadmin").text(msg.admin);
			 $("#libshowinfoisp").text(msg.isProjection);
			 $("#libshowinfoisa").text(msg.videoConferencing);
			 $("#libshowinfopeoplenum").text(msg.peoplenum);
			 $("#libshowinfostartam").text(msg.ams);
			 $("#libshowinfoendam").text(msg.ame);
			 $("#libshowinfostartpm").text(msg.pms);
			 $("#libshowinfoendpm").text(msg.pme);
			 $("#libshowinfoaddress").text(msg.address);
			 $("#libshowinfodisc").text(msg.disc);
			 $("#libshowinfoknow").text(msg.know);
			 var imgs = msg.imgs;
			 allimgs = imgs;
			 $("#dolibpicinfo").empty();
			 if(imgs.length > 0){
				 $("#dolibpicinfo").append('<div id="libshowpic"><img src="'+imgs[0].img+'"></div>');
				 if(imgs.length > 2){
					 $("#dolibpicinfo").append(
							 	'<div id="showleftpic"><img alt="left" src="/cfmg/admin/images/left.png"></div>'
								+'<div id="libshowallpic"></div>'
								+'<div id="showrightpic"><img alt="right" src="/cfmg/admin/images/right.png"></div>');
				 }else{
					 $("#dolibpicinfo").append('<div id="libshowallpic"></div>');
				 }
			 }
			 for(var i = 0; i < imgs.length; i++){
				 if(i > 2){
					 break;
				 }
				 var imginfo = imgs[i].img;
				 var html = '<img class="allpic" src='+imginfo+' width="100px" onClick="showApic('+i+')">';
				 $("#libshowallpic").append(html);
			 }
		 }
	});
	$("#detialedInfo").modal('show');
}
var executeAdmin = function (index, everypagenum){
	var datainfo = {};
	datainfo['index'] = index;
	datainfo['everypagenum'] = everypagenum;
	$.ajax({
		 type: "POST",
		 url: "./findallLibrary.do",
		 data : datainfo,
		 success: function(msg){
			msg = $.parseJSON(msg);
			var librarys = msg.list;
			var html = "";
			for(var i = 0; i < librarys.length; i++){
				var s = '<tr>'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+librarys[i].admin+'</td>'
						+'<td>'+librarys[i].ams+'~'+librarys[i].ame+'</td>'
						+'<td>'+librarys[i].pms+'~'+librarys[i].pme+'</td>'
						+'<td>'+librarys[i].people+'</td>'
						+'<td>'+librarys[i].isProjection+'</td>'
						+'<td>'+librarys[i].videoConferencing+'</td>'
						+'<td onclick="showInfo('+librarys[i].id+')"><a href="#"><i class="icon-eye-open"></i></a></td>';
				if(librarys[i].isControl == 1){
					s += '<td><a href="#" onclick="editlibrary('+librarys[i].id+')"><i class="icon-pencil"></i></a>'
						+'&nbsp;&nbsp;<a href="#myModal" role="button" data-toggle="modal" onclick="addlibraryid('+librarys[i].id+')"><i class="icon-remove"></i></a></td>'
						+'</tr>';
				}else{
					s += '<td><a href="#nopower" role="button" data-toggle="modal" ><i class="icon-pencil"></i></a>'
						+'&nbsp;&nbsp;<a href="#nopower" role="button" data-toggle="modal"><i class="icon-remove"></i></a></td>'
						+'</tr>';
				}
				html += s;
				$("#tblibrary").empty().append(html);
			}
		 }
	});
}
var conditionExecuteAdmin = function (index, everypagenum){
	var datainfo ={
		 "isp":$('input[name="isp"]:checked').val(),
		 "isa":$('input[name="isa"]:checked').val(),
		 "admin":$('input[name="isme"]:checked').val(),
		 "startnum":$("#startnum").val(),
		 "endnum":$("#endnum").val(),
		 "starttime":$("#libararyliststarttime").val(),
		 "endtime":$("#libararylistendtime").val()
	 };
	datainfo['index'] = index;
	datainfo['everypagenum'] = everypagenum;
	$.ajax({
		 type: "POST",
		 url: "./findConditionLibrary.do",
		 data : datainfo,
		 success: function(msg){
			msg = $.parseJSON(msg);
			var librarys = msg.list;
			var html = "";
			for(var i = 0; i < librarys.length; i++){
				var s = '<tr>'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+librarys[i].admin+'</td>'
						+'<td>'+librarys[i].ams+'~'+librarys[i].ame+'</td>'
						+'<td>'+librarys[i].pms+'~'+librarys[i].pme+'</td>'
						+'<td>'+librarys[i].people+'</td>'
						+'<td>'+librarys[i].isProjection+'</td>'
						+'<td>'+librarys[i].videoConferencing+'</td>'
						+'<td onclick="showInfo('+librarys[i].id+')"><a href="#"><i class="icon-eye-open"></i></a></td>';
				if(librarys[i].isControl == 1){
					s += '<td><a href="#" onclick="editlibrary('+librarys[i].id+')"><i class="icon-pencil"></i></a>'
						+'&nbsp;&nbsp;<a href="#myModal" role="button" data-toggle="modal" onclick="addlibraryid('+librarys[i].id+')"><i class="icon-remove"></i></a></td>'
						+'</tr>';
				}else{
					s += '<td><a href="#nopower" role="button" data-toggle="modal" ><i class="icon-pencil"></i></a>'
						+'&nbsp;&nbsp;<a href="#nopower" role="button" data-toggle="modal"><i class="icon-remove"></i></a></td>'
						+'</tr>';
				}
				html += s;
				$("#tblibrary").empty().append(html);
			}
		 }
	});
}
$("#libraryList").live("click", function(){
	$.ajax({
		 type: "POST",
		 url: "./findallLibrary.do",
		 data:{
			 "index":1,
			 "everypagenum":10
		 },
		 success: function(msg){
			msg = $.parseJSON(msg);
			var librarys = msg.list;
			var html = "";
			for(var i = 0; i < librarys.length; i++){
				var s = '<tr>'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+librarys[i].admin+'</td>'
						+'<td>'+librarys[i].ams+'~'+librarys[i].ame+'</td>'
						+'<td>'+librarys[i].pms+'~'+librarys[i].pme+'</td>'
						+'<td>'+librarys[i].people+'</td>'
						+'<td>'+librarys[i].isProjection+'</td>'
						+'<td>'+librarys[i].videoConferencing+'</td>'
						+'<td onclick="showInfo('+librarys[i].id+')"><a href="#"><i class="icon-eye-open"></i></a></td>';
				if(librarys[i].isControl == 1){
					s += '<td><a href="#" onclick="editlibrary('+librarys[i].id+')"><i class="icon-pencil"></i></a>'
						+'&nbsp;&nbsp;<a href="#myModal" role="button" data-toggle="modal" onclick="addlibraryid('+librarys[i].id+')"><i class="icon-remove"></i></a></td>'
						+'</tr>';
				}else{
					s += '<td><a href="#nopower" role="button" data-toggle="modal" ><i class="icon-pencil"></i></a>'
						+'&nbsp;&nbsp;<a href="#nopower" role="button" data-toggle="modal"><i class="icon-remove"></i></a></td>'
						+'</tr>';
				}
				html += s;
			}
			$("#tblibrary").empty().append(html);
			
			var info = {
					"count":msg.pagecount,
					"start":msg.pageindex,
					"display":10,
					"pageId":"pageindex",
					"executed": executeAdmin,
					"everypagenum":10
					};
			page(info);
			
		 }
	});
	
	$.ajax({
		 type: "get",
		 url: "./toLibraryList.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("会议室列表");
		 }
	});
});

$("#doopcheck").live("click", function(){
	$.ajax({
		type: "POST",
		 url: "./findConditionLibrary.do",
		 data:{
			 "index":1,
			 "everypagenum":10,
			 "isp":$('input[name="isp"]:checked').val(),
			 "isa":$('input[name="isa"]:checked').val(),
			 "admin":$('input[name="isme"]:checked').val(),
			 "startnum":$("#startnum").val(),
			 "endnum":$("#endnum").val(),
			 "starttime":$("#libararyliststarttime").val(),
			 "endtime":$("#libararylistendtime").val()
		
		 },
		 success: function(msg){
			msg = $.parseJSON(msg);
			var librarys = msg.list;
			var html = "";
			for(var i = 0; i < librarys.length; i++){
				var s = '<tr>'
						+'<td>'+(i+1)+'</td>'
						+'<td>'+librarys[i].admin+'</td>'
						+'<td>'+librarys[i].ams+'~'+librarys[i].ame+'</td>'
						+'<td>'+librarys[i].pms+'~'+librarys[i].pme+'</td>'
						+'<td>'+librarys[i].people+'</td>'
						+'<td>'+librarys[i].isProjection+'</td>'
						+'<td>'+librarys[i].videoConferencing+'</td>'
						+'<td onclick="showInfo('+librarys[i].id+')"><a href="#"><i class="icon-eye-open"></i></a></td>';
				if(librarys[i].isControl == 1){
					s += '<td><a href="#" onclick="editlibrary('+librarys[i].id+')"><i class="icon-pencil"></i></a>'
						+'&nbsp;&nbsp;<a href="#myModal" role="button" data-toggle="modal" onclick="addlibraryid('+librarys[i].id+')"><i class="icon-remove"></i></a></td>'
						+'</tr>';
				}else{
					s += '<td><a href="#nopower" role="button" data-toggle="modal" ><i class="icon-pencil"></i></a>'
						+'&nbsp;&nbsp;<a href="#nopower" role="button" data-toggle="modal"><i class="icon-remove"></i></a></td>'
						+'</tr>';
				}
				html += s;
			}
			$("#tblibrary").empty().append(html);
			
			var info = {
					"count":msg.pagecount,
					"start":msg.pageindex,
					"display":10,
					"pageId":"pageindex",
					"executed": conditionExecuteAdmin,
					"everypagenum":10
					};
			page(info);
		 }
	});
});

var addlibraryid = function(id){
	$("#deletelibid").val(id);
}


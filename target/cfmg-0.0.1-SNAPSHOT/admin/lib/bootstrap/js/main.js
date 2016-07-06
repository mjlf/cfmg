$.ajax({
	type : "post",
	async : false, //同步执行
	url : "./showuser.do",
	success:function(result){
		$("#username").text(result);
	}
});

$(document).ready(function(){
	$("#opeatelogout").live("click", function(){
		$.ajax({
			type : "post",
			async : false,
			url : "./logout.do",
			success : function(){
				window.location.href="./index.do";
			}
		})
	});
	
	$("#opeatesetting").live('click', function(){
		$.ajax({
			 type: "get",
			 url: "./adminsetting.do",
			 success: function(msg){
				 $("#toControlpage").html(msg);
				 $("#controlSubject").text("修改个人信息");
			 }
		});
		$.ajax({
			type:"get",
			url:"./userinfo.do",
			success: function(result){
				result = $.parseJSON(result);
				$("#namechange").text(result.username);
				$("#nickchange").val(result.nickname);
				$("#emailchange").val(result.email);
				$("#phonechange").val(result.phone);
			}
		});
	});
});

function checkNickName(){
	if($("#nickchange").val().length == 0){
		$("#info").text('昵称不能为空').css('color', 'red');
		return false;
	}else{
		return true;
	}
}

function checkEmail(){
	var re = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if($("#emailchange").val().length == 0){
		$("#info").text('邮箱不能为空').css('color', 'red');
		return false
	}else{
		if(re.test($("#emailchange").val())){
			$.ajax({
				type : "post",
				async : false, //同步执行
				url : "./checkEmail.do",
				data : {
					"email":$("#emailchange").val()
				},
				success:function(result){
					if(result == 'Y'){
						$("#info").text('该邮箱已被使用，请重新输入').css('color', '#D94600');
						return false;
					}
				},
				error:function(){
					$("#info").text('未知情况， 请稍后再试').css('color', '	#FF8000');
				}
			});
		}else{
			$("#info").text('不是合法邮箱账号').css('color', '#D94600');
			return false;
		}
	}
	return true;
}

function checkPhone(){
	if($("#phonechange").val().length == 0){
		$("#info").text('手机号不能为空').css('color', 'red');
		return false;
	}else{
		var re = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/;
		if(re.test($("#phonechange").val())){
			$.ajax({
				type : "post",
				async : false, //同步执行
				url : "./checkPhone.do",
				data : {
					"phone":$("#phonechange").val()
				},
				success:function(result){
					if(result == 'Y'){
						$("#info").text('该手机已被使用，请重新输入').css('color', '#D94600');
						return false;
					}
				},
				error:function(){
					$("#info").text('未知情况， 请稍后再试').css('color', '	#FF8000');
					return false;
				}
			});
		}else{
			$("#info").text('手机号不合法').css('color', '#D94600');
			return false;
		}
	}
	return true;
}


$(document).ready(function(){
	
	
	$("#showdate").live("click", function(){
		var html = '<iframe src="./date.do" width="100%" height="350px"></iframe>';
		 $("#toControlpage").html(html);
		 $("#controlSubject").text("日历");
	});
	
	$("#changeinformation").live('click',function(){
		if(checkNickName() && checkEmail() && checkPhone()){
			$.ajax({
				type:"post",
				url:"./changeInfo.do",
				data:{
					"username":$("#namechange").text(),
					"nickname":$("#nickchange").val(),
					"email":$("#emailchange").val(),
					"phone":$("#phonechange").val()
				},
				success:function(result){
					$("#info").text("修改成功").css("background-color:red");
				},
				error:function(){
					$("#info").text("未知错误， 稍后再试").css("backgroun-color:red");
				}
			});
		}else{
			$("#info").text("信息有误").css("backgroun-color:red");
		}
	});
	
	$("#opeatepass").live("click", function(){
		$.ajax({
			 type: "get",
			 url: "./repass.do",
			 success: function(msg){
				 $("#toControlpage").html(msg);
				 $("#controlSubject").text("修改密码");
			 }
		});
	});
	$("#changepassword").live("click", function(){
		if(checkChangePass()){
			$.ajax({
				 type: "post",
				 url: "./chagepassword.do",
				 data : {
					 "oldpassword":$("#oldpassword").val(),
					 "newpassword":$("#newpassword").val()
				 },
				 success: function(msg){
					if(msg == "Y"){
						$("#repassinfo").text("修改成功").css("background-color", "green");
					}else{
						$("#repassinfo").text("修改失败").css("background-color", "red");
					}
				 },
				 error : function(msg){
					 $("#repassinfo").text("未知错误， 稍后再试").css("background-color", "red");
				 }
			});
		}
	});
});



function checkPass(){
	var pass = $("#newpassword").val();
	if(pass.length == 0){
		$("#repassinfo").text('输入密码').css("color","red");
		return false;
	}else{
		var re, is = 0; 
		re = /[(\*)(\.)(\\)(\?)]/;
		if(pass.match(re) != null){
			$("#repassinfo").text('密码中不能有"*,.\\,?"').css('color', '#D94600');
		}
		re = /[(\ )(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)(\)]+/;
		if(pass.match(re) != null){
			is = is + 1;
		}
		re = /\d+/ig;
		if(pass.match(re) != null){
			is = is + 1;
		}
		re = /[a-zA-Z]/g;
		if(pass.match(re) != null){
			is = is + 1;
		}
		if(is < 2 || pass.length < 6){
			$("#repassinfo").text('输入密码不够安全，请重输入').css('color', '#D94600');
			return false;
		}else{
			$("#repassinfo").text('');
			return true;
		}
	}
	return true;
}

function checkChangePass(){
	var oldpassword = $("#oldpassword").val();
	var newpassword = $("#newpassword").val();
	var passwordagain = $("#passwordagain").val();
	if(oldpassword.length == 0){
		$("#repassinfo").text('输入密码旧密码').css('color', '#D94600');
		return false;
	}
	if(checkPass()){
		if(newpassword == passwordagain){
			$("#repassinfo").text("");
			return true;	
		}else{
			$("#repassinfo").text("两次输入密码不一致").css('color', '#D94600');
			return false;
		}
	}else{
		return false;
	}
	return true;
}

$("#forgetpass").live("click", function(){
	$.ajax({
		 type: "get",
		 url: "./forgetrepass.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("忘记密码");
		 }
	});
});

$("#sendpassword").live("click", function(){
	$.ajax({
		type:"post",
		async : false,
		url:"./sendmailtoresetpass.do",
		data:{
			"username":$("#passresetusername").val(),
			"email":$("#passresetemail").val()
		},
		success : function(msg){
			if(msg == 'Y'){
				$("#forgetpasswordinfo").text("密码已重置，请查收邮件").css("color", "green");
			}else if(msg == "N"){
				$("#forgetpasswordinfo").text("邮箱不对").css("color", "red");
			}else if(msg == 'H'){
				$("#forgetpasswordinfo").text("未知错误").css("color", "orange");
			}
		},
		erroro : function(msg){
			$("#forgetpasswordinfo").text("未知错误").css("color", "orange");
		}
	});
});

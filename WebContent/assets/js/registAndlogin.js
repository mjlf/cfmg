$("#reusername").live("blur", function(){
	var str = $("#reusername").val();
	if(str.length == 0){
		$("#infousername").text('输入用户名').css("color","red");
	}else if(str.length < 3){
		$("#infousername").text('用户名长度必须大于3').css('color', '#D94600');
	}else{
		//验证该账号是否被使用过
		$.ajax({
			type : "post",
			async : false, //同步执行
			url : "./findusername.do",
			data : {
				"username":str
			},
			success:function(result){
				if(result == 'Y'){
					$("#infousername").text('该账户已被使用，请重新输入').css('color', '#D94600');
				}else{
					$("#infousername").text('');
				}
			},
			error:function(){
				$("#infousername").text('未知情况， 请稍后再试').css('color', '	#FF8000');
			}
		});
	}
});
$("#repass").live("blur", function(){
	var pass = $("#repass").val();
	if(pass.length == 0){
		$("#infopass").text('输入密码').css("color","red");
	}else{
		
		var re, is = 0; 
		re = /[(\*)(\.)(\\)(\?)]/;
		if(pass.match(re) != null){
			$("#infopass").text('密码中不能有"*,.\\,?"').css('color', '#D94600');
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
			$("#infopass").text('输入密码不够安全，请重输入').css('color', '#D94600');
		}else{
			$("#infopass").text('');
		}
	}
});
$("#renickname").live('blur', function(){
	if($("#renickname").val().length == 0){
		$("#infonickname").text('昵称不能为空').css('color', 'red');
	}else{
		$("#infonickname").text('');
	}
});
$("#reemail").live('blur', function(){
	var re = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if($("#reemail").val().length == 0){
		$("#infoemail").text('邮箱不能为空').css('color', 'red');
	}else{
		if(re.test($("#reemail").val())){
			$.ajax({
				type : "post",
				async : false, //同步执行
				url : "./findemail.do",
				data : {
					"email":$("#reemail").val()
				},
				success:function(result){
					if(result == 'Y'){
						$("#infoemail").text('该邮箱已被使用，请重新输入').css('color', '#D94600');
					}else{
						$("#infoemail").text('');
					}
				},
				error:function(){
					$("#infoemail").text('未知情况， 请稍后再试').css('color', '	#FF8000');
				}
			});
		}else{
			$("#infoemail").text('不是合法邮箱账号').css('color', '#D94600');
		}
	}
});
$("#rephone").live('blur', function(){
	if($("#rephone").val().length == 0){
		$("#infophone").text('手机号不能为空').css('color', 'red');
	}else{
		var re = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/;
		if(re.test($("#rephone").val())){
			$.ajax({
				type : "post",
				async : false, //同步执行
				url : "./findphone.do",
				data : {
					"phone":$("#rephone").val()
				},
				success:function(result){
					if(result == 'Y'){
						$("#infophone").text('该手机已被使用，请重新输入').css('color', '#D94600');
					}else{
						$("#infophone").text('');
					}
				},
				error:function(){
					$("#infophone").text('未知情况， 请稍后再试').css('color', '	#FF8000');
				}
			});
		}else{
			$("#infophone").text('手机号不合法').css('color', '#D94600');
		}
	}
});

$("#rebutton").live('click', function(){
	$("#reusername").blur();
	$("#repass").blur();
	$("#renickname").blur();
	$("#reemail").blur();
	$("#rephone").blur();
	
	var info = 0;
	if($("#infousername").text()){
		info ++;
	}
	if($("#infopass").text()){
		info ++;
	}
	if($("#infonickname").text()){
		info ++;
	}
	if($("#infoemail").text()){
		info ++;
	}
	if($("#infophone").text()){
		info ++;
	}
	if(info == 0){
		$.ajax({
			type : "post",
			async : false, //同步执行
			url : "./regist.do",
			data : {
				"username":$("#reusername").val(),
				"password":$("#repass").val(),
				"nickname":$("#renickname").val(),
				"email":$("#reemail").val(),
				"phone":$("#rephone").val()
			},
			success:function(result){
				if(result == 'Y'){
					$("#inforeg").text('注册成功').css('color', '#00BB00');
					setTimeout('recal()', 1000);
					$("#inforeg").text('');
				}else{
					$("#inforeg").text('注册失败').css('color', 'red');
				}
			},
			error:function(){
				$("#inforeg").text('未知情况， 请稍后再试').css('color', '#FF8000');
			}
		});
	}
});
function recal(){
	$("#recal").click();
}

$("#dologin").live('click', function(){
	$("#infologinlog").text("");
	$.ajax({
		type : "post",
		async : false, //同步执行
		url : "./login.do",
		data : {
			"username":$("#username").val(),
			"password":$("#password").val()
		},
		success:function(result){
			if(result == "N"){
				$("#infologinlog").text("用户名或密码不对").css("color", "red");
				$("#toforget").attr("hidden", false);
				
			}else if(result == "Y"){
				 window.location.href = "./main.do";
			}
		},
		error:function(){
			$("#infologinlog").text("登录异常，稍后再试").css("color", "red");
		}
	});
});

$(document).ready(function(){
	onkeydown = function(event){
		  if(event.keyCode == 13)
		  {
		     $("#dologin").click();
		  }
	}
});

$("#passSend").live("click", function(){
	$.ajax({
		type:"post",
		async : false,
		url:"./sendmailtoresetpass.do",
		data:{
			"username":$("#forusername").val(),
			"email":$("#foremail").val()
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























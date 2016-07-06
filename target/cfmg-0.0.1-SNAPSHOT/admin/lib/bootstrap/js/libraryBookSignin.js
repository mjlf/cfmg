$("#LibraryBookSigin").live('click', function(){
	$.ajax({
		 type: "get",
		 url: "./toLibraryBookSigininqrcode.do",
		 async:"false",
		 success: function(msg){
			 
			 var qrcode = new QRCode(document.getElementById("libraryBookLibrarySigntowCoder"), {
		            width : 120,//设置宽高
		            height : 120
		        });
		     qrcode.makeCode(msg);
//		        document.getElementById("send").onclick =function(){
//		            qrcode.makeCode(document.getElementById("getval").value);
//		        }
//			 
//			 $("#libraryBookLibrarySigntowCoder").qrcode({ 
//				    render: "table", //table方式 
//				    width: 160, //宽度 
//				    height:160, //高度 
//				    text: msg//任意内容 
//			}); 
		 }
	});
	$.ajax({
		 type: "get",
		 url: "./toLibraryBookSigininpage.do",
		 success: function(msg){
			 $("#toControlpage").html(msg);
			 $("#controlSubject").text("会议签到");
		 }
	});
});

function toUtf8(str) {    
    var out, i, len, c;    
    out = "";    
    len = str.length;    
    for(i = 0; i < len; i++) {    
        c = str.charCodeAt(i);    
        if ((c >= 0x0001) && (c <= 0x007F)) {    
            out += str.charAt(i);    
        } else if (c > 0x07FF) {    
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));    
            out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));    
            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
        } else {    
            out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));    
            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
        }    
    }    
    return out;    
} 

$("#sign_intolibrayrbook").live('click', function(){
	$.ajax({
		url:"./signIncodepass.do",
		async:"true",
		type:"POST",
		data:{
			"signcode":$("#libraryBooKSigninPhone").val(),
			"signpassword":$("#libraryBooKSigninPassword").val()
		},
		success:function(msg){
			if(msg == 'Y'){
				$("#successSigin").modal('show');
				$("#libraryBooKSigninPhone").val("");
				$("#libraryBooKSigninPassword").val("");
			}else if(msg == "N"){
				$("#successfalied").modal('show');
			}
		}
	});
});

$("#sign_intolibrayrbook_1").live('click', function(){
	$.ajax({
		url:"/cfmg/cfmg/signIncodepass.do",
		async:"true",
		type:"POST",
		data:{
			"signcode":$("#libraryBooKSigninPhone_1").val(),
			"signpassword":$("#libraryBooKSigninPassword_1").val()
		},
		success:function(msg){
			if(msg == 'Y'){
				$("#successSigin_1").modal('show');
				$("#libraryBooKSigninPhone_1").val("");
				$("#libraryBooKSigninPassword_1").val("");
			}else if(msg == "N"){
				$("#successfalied_1").modal('show');
			}
		}
	});
});
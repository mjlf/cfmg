$("#douploadPeopleFile").live('click', function() {
	$.ajaxFileUpload({
		url : './uploadPeopleFile.do',
		fileElementId : 'uploadPeopleFile',
		type: 'post',
		dataType: 'json',
		secureuri:false,
		data:data,
		success : function(data, status) {
			managePeople($("#uploadPeopleFilebookLibraryId").val());
		},
		error:function(){
			alert('yes');
		}
	});
});
KindEditor.DEBUG = true;
KindEditor.options.filterMode = false;
KindEditor.options.autoHeightMode = true;
KindEditor.options.fixToolBar = true;
//document.domain = 'domain.com';
KindEditor.basePath = '../';
KindEditor.ready(function(K) {
	var editor1 = K.create('textarea[name=content1]', {
		basePath : '../',
		pluginsPath : '../plugins/',
		urlType : 'absolute',
		themeType : 'simple',
		allowImageUpload : true,
		allowImageRemote : true,
		allowFileUpload : true,
		designMode : true,
		allowFileManager : true,
		fullscreenMode : false,
		formatUploadUrl : false,
		fullscreenShortcut : false,
		newlineTag : 'p',
		pasteType : 1,
		extraFileUploadParams : {
			'testParam1' : 'a',
			'testParam2' : 'b'
		},
		//afterTab : function() {
			//editor2.focus();
		//},
		afterChange : function() {
			K('#J_count1').html(this.count());
			//console.log('1');
		},
		afterUpload : function(url, data, name) {
			var select = K('#uploadFile')[0];
			select.options.add(new Option(url, url));
			console.log(data);
			console.log(name);
		},
		afterSelectFile : function(url) {
			var select = K('#uploadFile')[0];
			select.options.add(new Option(url, url));
		},
		afterCreate : function() {
			//this.loadPlugin('autoheight');
		}
	});
	K('#create1').click(function(e) {
		editor1.create();
	});
	K('#remove1').click(function(e) {
		editor1.remove();
	});
	K('#getHtml1').click(function(e) {
		alert(editor1.html());
	});
	K('#fullHtml1').click(function(e) {
		alert(editor1.fullHtml());
	});
	K('#setHtml1').click(function(e) {
		editor1.html(K('#insertHtml').val());
	});
	K('#empty1').click(function(e) {
		editor1.html('');
	});
	K('#getText1').click(function(e) {
		alert(editor1.text());
	});
	K('#setText1').click(function(e) {
		editor1.text(K('#insertHtml').val());
	});
	K('#selectedHtml1').click(function(e) {
		alert(editor1.selectedHtml());
	});
	K('#insertHtml1').click(function(e) {
		editor1.insertHtml(K('#insertHtml').val());
	});
	K('#appendHtml1').click(function(e) {
		editor1.appendHtml(K('#insertHtml').val());
	});
	K('#sync1').click(function(e) {
		editor1.sync();
		alert(K('textarea[name=content1]').val());
	});
	K('#focus1').click(function(e) {
		editor1.focus();
	});
	K('#blur1').click(function(e) {
		editor1.blur();
	});
	K('#readonly1').click(function(e) {
		editor1.readonly();
	});
	K('#cancelReadonly1').click(function(e) {
		editor1.readonly(false);
	});
	K('#isEmpty1').click(function(e) {
		alert(editor1.isEmpty());
	});
	K('#isDirty1').click(function(e) {
		alert(editor1.isDirty());
	});
	K('#loadPlugin1').click(function(e) {
		editor1.loadPlugin('template', function() {

		});
		editor1.loadPlugin('template', function() {

		});
	});
	var editor2 = K.create(K('textarea[name=content2]').get(), {
		basePath : '../',
		langType : 'en',
		formatUploadUrl : false,
		afterTab : function() {
			K('#uploadFile')[0].focus();
		},
		afterCreate : function() {
			//this.loadPlugin('autoheight');
		}
	});
	K('#create2').click(function(e) {
		editor2.create();

	});
	K('#remove2').click(function(e) {
		editor2.remove();
	});

	var editor3 = K.editor({
		basePath : '../',
		themesPath : '../themes/',
		pluginsPath : '../plugins/',
		langPath : '../lang/',
		allowFileManager : true
	});
	K('#image').click(function() {
		editor3.loadPlugin('image', function() {
			editor3.plugin.imageDialog({
				imageUrl : K('#url').val(),
				clickFn : function(url, title, width, height, border, align) {
					K('#url').val(url);
					editor3.hideDialog();
				}
			});
		});
	});
	K.create('textarea[name=content1]');

});
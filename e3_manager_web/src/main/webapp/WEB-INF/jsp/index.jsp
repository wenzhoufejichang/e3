<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>易商城后台管理系统</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/themes/gray/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/e3.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	

	Date.prototype.format = function(format){ 
	    var o =  { 
	    "M+" : this.getMonth()+1, // month
	    "d+" : this.getDate(), // day
	    "h+" : this.getHours(), // hour
	    "m+" : this.getMinutes(), // minute
	    "s+" : this.getSeconds(), // second
	    "q+" : Math.floor((this.getMonth()+3)/3),
	    "S" : this.getMilliseconds() // millisecond
	    };
	    if(/(y+)/.test(format)){ 
	    	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	    }
	    for(var k in o)  { 
		    if(new RegExp("("+ k +")").test(format)){ 
		    	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		    } 
	    } 
	    return format; 
	};

	var E3 = {
		// 编辑器参数
		kingEditorParams : {
			// 指定上传文件参数名称
			filePostName  : "uploadFile",
			// 指定上传文件请求的url。
			uploadJson : '${pageContext.request.contextPath}/pic/upload',
			// 上传类型，分别为image、flash、media、file
			dir : "image"
		},
		// 格式化时间
		formatDateTime : function(val,row){
			var now = new Date(val);
	    	return now.format("yyyy-MM-dd hh:mm:ss");
		},
		// 格式化连接
		formatUrl : function(val,row){
			if(val){
				return "<a href='"+val+"' target='_blank'>查看</a>";			
			}
			return "";
		},
		// 格式化价格
		formatPrice : function(val,row){
			return (val/1000).toFixed(2);
		},
		// 格式化商品的状态
		formatItemStatus : function formatStatus(val,row){
	        if (val == 1){
	            return '正常';
	        } else if(val == 2){
	        	return '<span style="color:red;">下架</span>';
	        } else {
	        	return '未知';
	        }
	    },
	    
	    init : function(data){
	    	// 初始化图片上传组件
	    	this.initPicUpload(data);
	    	// 初始化选择类目组件
	    	this.initItemCat(data);
	    },
	    // 初始化图片上传组件
	    initPicUpload : function(data){
	    	$(".picFileUpload").each(function(i,e){
	    		var _ele = $(e);
	    		_ele.siblings("div.pics").remove();
	    		_ele.after('\
	    			<div class="pics">\
	        			<ul></ul>\
	        		</div>');
	    		// 回显图片
	        	if(data && data.pics){
	        		var imgs = data.pics.split(",");
	        		for(var i in imgs){
	        			if($.trim(imgs[i]).length > 0){
	        				
	        				_ele.siblings(".pics").find("ul").append("<li><a href='"+imgs[i]+"' target='_blank'><img src='"+imgs[i]+"' width='80' height='50' /></a></li>");
	        			}
	        		}
	        	}
	        	// 给“上传图片按钮”绑定click事件
	        	$(e).click(function(){
	        		var form = $(this).parentsUntil("form").parent("form");
	        		// 打开图片上传窗口
	        		KindEditor.editor(E3.kingEditorParams).loadPlugin('multiimage',function(){
	        			var editor = this;
	        			editor.plugin.multiImageDialog({
							clickFn : function(urlList) {
								var imgArray = [];
								KindEditor.each(urlList, function(i, data) {
									imgArray.push(data.url);
									form.find(".pics ul").append("<li><a href='"+data.url+"' target='_blank'><img src='"+data.url+"' width='80' height='50' /></a></li>");
								});
								form.find("[name=image]").val(imgArray.join(","));
								editor.hideDialog();
							}
						});
	        		});
	        	});
	    	});
	    },
	    
	    // 初始化选择类目组件
	    initItemCat : function(data){
	    	$(".selectItemCat").each(function(i,e){
	    		var _ele = $(e);
	    		if(data && data.cid){
	    			_ele.after("<span style='margin-left:10px;'>"+data.cid+"</span>");
	    		}else{
	    			_ele.after("<span style='margin-left:10px;'></span>");
	    		}
	    		_ele.unbind('click').click(function(){
	    			$("<div>").css({padding:"5px"}).html("<ul>")
	    			.window({
	    				width:'500',
	    			    height:"450",
	    			    modal:true,
	    			    closed:true,
	    			    iconCls:'icon-save',
	    			    title:'选择类目',
	    			    onOpen : function(){
	    			    	var _win = this;
	    			    	$("ul",_win).tree({
	    			    		url:'${pageContext.request.contextPath }/item/cat/lists',
	    			    		animate:true,
	    			    		onClick : function(node){
	    			    			if($(this).tree("isLeaf",node.target)){
	    			    				// 填写到cid中
	    			    				_ele.parent().find("[name=cid]").val(node.id);
	    			    				_ele.next().text(node.text).attr("cid",node.id);
	    			    				$(_win).window('close');
	    			    				if(data && data.fun){
	    			    					data.fun.call(this,node);
	    			    				}
	    			    			}
	    			    		}
	    			    	});
	    			    },
	    			    onClose : function(){
	    			    	$(this).window("destroy");
	    			    }
	    			}).window('open');
	    		});
	    	});
	    },
	    
	    createEditor : function(select){
	    	return KindEditor.create(select, E3.kingEditorParams);
	    },
	    
	    /**
		 * 创建一个窗口，关闭窗口后销毁该窗口对象。<br/>
		 * 
		 * 默认：<br/> width : 80% <br/> height : 80% <br/> title : (空字符串) <br/>
		 * 
		 * 参数：<br/> width : <br/> height : <br/> title : <br/> url : 必填参数 <br/>
		 * onLoad : function 加载完窗口内容后执行<br/>
		 * 
		 * 
		 */
	    createWindow : function(params){
	    	$("<div>").css({padding:"5px"}).window({
	    		width : params.width?params.width:"80%",
	    		height : params.height?params.height:"80%",
	    		modal:true,
	    		title : params.title?params.title:" ",
	    		href : params.url,
			    onClose : function(){
			    	$(this).window("destroy");
			    },
			    onLoad : function(){
			    	if(params.onLoad){
			    		params.onLoad.call(this);
			    	}
			    }
	    	}).window("open");
	    },
	    
	    closeCurrentWindow : function(){
	    	$(".panel-tool-close").click();
	    },
	    
	    changeItemParam : function(node,formId){
	    	$.getJSON("/item/param/query/itemcatid/" + node.id,function(data){
				  if(data.status == 200 && data.data){
					 $("#"+formId+" .params").show();
					 var paramData = JSON.parse(data.data.paramData);
					 var html = "<ul>";
					 for(var i in paramData){
						 var pd = paramData[i];
						 html+="<li><table>";
						 html+="<tr><td colspan=\"2\" class=\"group\">"+pd.group+"</td></tr>";
						 
						 for(var j in pd.params){
							 var ps = pd.params[j];
							 html+="<tr><td class=\"param\"><span>"+ps+"</span>: </td><td><input autocomplete=\"off\" type=\"text\"/></td></tr>";
						 }
						 
						 html+="</li></table>";
					 }
					 html+= "</ul>";
					 $("#"+formId+" .params td").eq(1).html(html);
				  }else{
					 $("#"+formId+" .params").hide();
					 $("#"+formId+" .params td").eq(1).empty();
				  }
			  });
	    },
	    getSelectionsIds : function (select){
	    	var list = $(select);
	    	var sels = list.datagrid("getSelections");
	    	var ids = [];
	    	for(var i in sels){
	    		ids.push(sels[i].id);
	    	}
	    	ids = ids.join(",");
	    	return ids;
	    },
	    
	    /**
		 * 初始化单图片上传组件 <br/> 选择器为：.onePicUpload <br/> 上传完成后会设置input内容以及在input后面追加<img>
		 */
	    initOnePicUpload : function(){
	    	$(".onePicUpload").click(function(){
				var _self = $(this);
				KindEditor.editor(E3.kingEditorParams).loadPlugin('image', function() {
					this.plugin.imageDialog({
						showRemote : false,
						clickFn : function(url, title, width, height, border, align) {
							var input = _self.siblings("input");
							input.parent().find("img").remove();
							input.val(url);
							input.after("<a href='"+url+"' target='_blank'><img src='"+url+"' width='80' height='50'/></a>");
							this.hideDialog();
						}
					});
				});
			});
	    }
	};

	
	
	
	
	
	
	
	
	
	
</script>
<style type="text/css">
.content {
	padding: 10px 10px 10px 10px;
}
</style>
</head>
<body class="easyui-layout">
	<!-- 头部标题 -->
	<div data-options="region:'north',border:false"
		style="height: 60px; padding: 5px; background: #F3F3F3">
		<span class="northTitle">易商城后台管理系统</span> <span class="loginInfo">登录用户：admin&nbsp;&nbsp;姓名：管理员&nbsp;&nbsp;角色：系统管理员</span>
	</div>
	<div data-options="region:'west',title:'菜单',split:true"
		style="width: 180px;">
		<ul id="menu" class="easyui-tree"
			style="margin-top: 10px; margin-left: 5px;">
			<li><span>商品管理</span>
				<ul>
					<li
						data-options="attributes:{'url':'${pageContext.request.contextPath }/item/item-add'}">新增商品</li>
					<li
						data-options="attributes:{'url':'${pageContext.request.contextPath }/item/item-list'}">查询商品</li>
					<li
						data-options="attributes:{'url':'${pageContext.request.contextPath }/item/item-param-list'}">规格参数</li>
				</ul></li>
			<li><span>网站内容管理</span>
				<ul>
					<li data-options="attributes:{'url':'${pageContext.request.contextPath }/item/content-category'}">内容分类管理</li>
					<li data-options="attributes:{'url':'${pageContext.request.contextPath }/item/content'}">内容管理</li>
				</ul></li>
			<li><span>索引库管理</span>
				<ul>
					<li data-options="attributes:{'url':'${pageContext.request.contextPath }/item/index-item'}">solr索引库维护</li>
				</ul></li>
		</ul>
	</div>
	<div data-options="region:'center',title:''">
		<div id="tabs" class="easyui-tabs">
			<div title="首页" style="padding: 20px;"></div>
		</div>
	</div>
	<!-- 页脚信息 -->
	<div data-options="region:'south',border:false"
		style="height: 20px; background: #F3F3F3; padding: 2px; vertical-align: middle;">
		<span id="sysVersion">系统版本：V1.0</span> <span id="nowTime"></span>
	</div>
	<script type="text/javascript">
		$(function() {
			$('#menu').tree({
				onClick : function(node) {
					if ($('#menu').tree("isLeaf", node.target)) {
						var tabs = $("#tabs");
						var tab = tabs.tabs("getTab", node.text);
						if (tab) {
							tabs.tabs("select", node.text);
						} else {
							tabs.tabs('add', {
								title : node.text,
								href : node.attributes.url,
								closable : true,
								bodyCls : "content"
							});
						}
					}
				}
			});
		});
		setInterval(
				"document.getElementById('nowTime').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",
				1000);
	</script>
</body>
</html>
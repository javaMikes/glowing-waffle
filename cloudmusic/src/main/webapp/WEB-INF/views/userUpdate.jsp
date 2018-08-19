<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<title>用户个人中心</title>
	<link href="static/template/css/bootstrap.css" rel="stylesheet">
	<link href="static/template/css/responsive.bootstrap.css" rel="stylesheet">
	<link href="static/template/css/screen.css" rel="stylesheet">    
	<link href="static/head/cropper.min.css" rel="stylesheet">
	<link href="static/head/sitelogo.css" rel="stylesheet">
	<link href="static/head/font-awesome.4.6.0.css" rel="stylesheet" type="text/css">
	<link href="static/css/city.css" rel="stylesheet"> 
	<link href="static/css/user-update.css" rel="stylesheet">
	<link href="static/css/register.css" rel="stylesheet">
	<script src="static/mplayer-master/js/jquery.js"></script>
</head>
<body>
	<!-- 公共header部分 -->
	<%-- <%@ include file="header.jsp" %> --%>
	
	<div class="container-fluid topImage">
		<div class="container">
			
		</div><!-- /.container -->
	</div><!-- /.container-fluid [topImage] -->

	<div class="container">
		<div class="row pageStyle">
			<div class="span4">
				<div class="sidebarMenu boxModel" id="user_setting_nav">
					<h1 class="colored">个人设置</h1>
					<ul>
						<li class="active" id="basic_setting_btn"><a href="javacript:void(0);"><b>&#8594;</b>基本设置</a></li>
						<li><a href="upload_head"><b>&#8594;</b>修改头像</a></li>
					</ul>
				</div>
			</div><!-- /.span4-->
			<div class="span8">
				<div id="basic_setting_content" class="setting_content active">					
					<form action="#" id="user_update_form" class="contactForm coloredGray">
						<p class="row-fluid">
							<label for="birthday_update" class="span2">生日<sup>*</sup></label>
							<input type="date" name="birthday" class="span10" id="birthday_update" value="<fmt:formatDate value="${userInfo.birthday}" pattern="yyyy-MM-dd" />">
							<b class="colored span10 offset2 contact_page_form_result"></b>
						</p>
						<p class="row-fluid">
							<label for="" class="span2">性别<sup>*</sup></label>
							<c:if test="${userInfo.sex == false}">
								<span class="span10">
									<label class="checkbox-inline"> <input type="radio" name="sex" id="male_update" value="0" checked="checked">&nbsp;&nbsp;&nbsp;男 </label>
									&nbsp;&nbsp;&nbsp; 
									<label class="checkbox-inline"> <input type="radio" name="sex" id="female_update" value="1">&nbsp;&nbsp;&nbsp;女 </label>
								</span>		
							</c:if>	
							<c:if test="${userInfo.sex == true}">
								<span class="span10">
									<label class="checkbox-inline"> <input type="radio" name="sex" id="male_update" value="0" >&nbsp;&nbsp;&nbsp;男 </label>
									&nbsp;&nbsp;&nbsp; 
									<label class="checkbox-inline"> <input type="radio" name="sex" id="female_update" value="1" checked="checked">&nbsp;&nbsp;&nbsp;女 </label>
								</span>		
							</c:if>					
						</p>
						<p class="row-fluid">
							<label for="region_update" class="span2">地区<sup></sup></label>
							<input type="text" name="region" class="span10" id="region_update" placeholder="地区" value="${userInfo.region}">
							<input type="hidden" name="harea" data-id="440607" id="harea" disabled="disabled">
							<input type="hidden" name="hproper" data-id="440600" id="hproper" disabled="disabled">
							<input type="hidden" name="hcity" data-id="440000" id="hcity" disabled="disabled">
							<b class="colored span10 offset2 contact_page_form_result"></b>
						</p>
						<p class="row-fluid">
							<label for="nickname_update" class="span2">昵称<sup></sup></label>
							<input type="text" name="nickname" class="span10" id="nickname_update" placeholder="昵称" value="${userInfo.nickname}">
							<b class="colored span10 offset2 contact_page_form_result"></b>
						</p>
						<p class="row-fluid">
							<label for="person_signature_update" class="span2">个性签名<sup></sup></label>
							<textarea name="personSignature" class="span10" id="person_signature_update" rows="3" placeholder="个性签名">${userInfo.personSignature}</textarea>
							<b class="colored span10 offset2 contact_page_form_result"></b>
						</p>				
						<p class="row-fluid">
							<button type="button" class="btn btn-color offset2" id="user_update_btn">保存</button>
						</p>
					</form>
					
				</div>
				<div id="change_image_content" class="setting_content">
					<div class="user_pic">
						<img alt="上传头像" src="seekExperts?userId=${userInfo.id}"/>
					</div>
					<button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#avatar-modal">
							修改头像
					</button>
					<div class="modal fade" id="avatar-modal" aria-hidden="true"aria-labelledby="avatar-modal-label"  role="dialog" tabindex="-1">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
								<!--<form class="avatar-form" action="upload-logo.php" enctype="multipart/form-data" method="post">-->
								<form class="avatar-form" id="uploadImgForm">
									<div class="modal-header">
										<button class="close" data-dismiss="modal" type="button">&times;</button>
										<h4 class="modal-title" id="avatar-modal-label">上传图片</h4>
									</div>
									<div class="modal-body">
										<div class="avatar-body">
											<div class="avatar-upload">
												<input class="avatar-src" name="avatar_src" type="hidden">
												<input class="avatar-data" name="avatar_data" type="hidden">
												<label for="avatarInput" style="line-height: 35px;">图片上传</label>
												<button class="btn btn-danger"  type="button" style="height: 35px;" onclick="$('input[id=avatarInput]').click();">请选择图片</button>
												<span id="avatar-name"></span>
												<input class="avatar-input hide" id="avatarInput" name="avatar_file" type="file"></div>
											<div class="row-fluid">
												<div class="span9">
													<div class="avatar-wrapper"></div>
												</div>
												<div class="span3">
													<div class="avatar-preview preview-lg" id="imageHead"></div>
													<!--<div class="avatar-preview preview-md"></div>
											<div class="avatar-preview preview-sm"></div>-->
												</div>
											</div>
											<div class="row-fluid avatar-btns">
												<div class="span4">
													<div class="btn-group">
														<button class="btn btn-danger fa fa-undo" data-method="rotate" data-option="-90" type="button" title="Rotate -90 degrees"> 向左旋转</button>
													</div>
													<div class="btn-group">
														<button class="btn  btn-danger fa fa-repeat" data-method="rotate" data-option="90" type="button" title="Rotate 90 degrees"> 向右旋转</button>
													</div>
												</div>
												<div class="span5" style="text-align: right;">								
													<button class="btn btn-danger fa fa-arrows" data-method="setDragMode" data-option="move" type="button" title="移动">
										            <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;setDragMode&quot;, &quot;move&quot;)">
										            </span>
										          </button>
										          <button type="button" class="btn btn-danger fa fa-search-plus" data-method="zoom" data-option="0.1" title="放大图片">
										            <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;zoom&quot;, 0.1)">
										              <!--<span class="fa fa-search-plus"></span>-->
										            </span>
										          </button>
										          <button type="button" class="btn btn-danger fa fa-search-minus" data-method="zoom" data-option="-0.1" title="缩小图片">
										            <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;zoom&quot;, -0.1)">
										              <!--<span class="fa fa-search-minus"></span>-->
										            </span>
										          </button>
										          <button type="button" class="btn btn-danger fa fa-refresh" data-method="reset" title="重置图片">
											            <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;reset&quot;)" aria-describedby="tooltip866214">
											       </button>
										        </div>
												<div class="span3">
													<button class="btn btn-danger btn-block avatar-save fa fa-save" type="button" data-dismiss="modal"> 保存修改</button>
												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
					<div class="loading" aria-label="Loading" role="img" tabindex="-1"></div>	
				</div>
				
			</div><!-- /.span8 -->
		</div><!-- /.row-->
	</div><!-- /.container -->		

	<div id="register_msg_box">
		<div class="box_head">
			<a href="showUserUpdate?id=${userInfo.id}"><button type="button" class="box_close">&times;</button></a>
		</div>	
		<div class="box_body"></div>
		<div class="box_foot">
			<a href="showUserUpdate?id=${userInfo.id}"><button type="button" class="btn btn-color" id="update_confirm">确认</button></a>
		</div>
	</div>

	<!-- 公共footer部分 -->
	<%-- <%@ include file="footer.jsp" %> --%>		

<script src="static/template/js/jquery-ui.js"></script>
<script src="static/template/js/bootstrap.min.js"></script>
<script src="static/template/js/settingsbox.js"></script>
<script src="static/template/js/farbtastic/farbtastic.js"></script>
<script src="static/template/js/jquery.prettyPhoto.js"></script>
<script src="static/template/js/izotope.js"></script>
<script src="static/template/js/option.js"></script>
<script src="static/head/cropper.js"></script>
<script src="static/head/sitelogo.js"></script>
<script src="static/head/html2canvas.min.js" type="text/javascript" charset="utf-8"></script>
<script src="static/js/city.js"></script>
<script src="static/js/cityJson.js"></script>	
<script src="static/js/user-update.js"></script>
<script type="text/javascript">
/*=========================== 上传头像 ===========================*/
//做个下简易的验证  大小 格式 
	$('#avatarInput').on('change', function(e) {
		var filemaxsize = 1024 * 5;//5M
		var target = $(e.target);
		var Size = target[0].files[0].size / 1024;
		if(Size > filemaxsize) {
			alert('图片过大，请重新选择!');
			$(".avatar-wrapper").childre().remove;
			return false;
		}
		if(!this.files[0].type.match(/image.*/)) {
			alert('请选择正确的图片!')
		} else {
			var filename = document.querySelector("#avatar-name");
			var texts = document.querySelector("#avatarInput").value;
			var teststr = texts; //你这里的路径写错了
			testend = teststr.match(/[^\\]+\.[^\(]+/i); //直接完整文件名的
			filename.innerHTML = testend;
		}
	
	});

	$(".avatar-save").on("click", function() {
		var img_lg = document.getElementById('imageHead');
		// 截图小的显示框内的内容
		html2canvas(img_lg, {
			allowTaint: true,
			taintTest: false,
			onrendered: function(canvas) {
				canvas.id = "mycanvas";
				//生成base64图片数据
				var dataUrl = canvas.toDataURL("img/jpeg");
				var newImg = document.createElement("img");
				newImg.src = dataUrl;
				imagesAjax(dataUrl)
			}
		});
	})
	
	function imagesAjax(src) {
		//var data = {};
		//var data = {};
		//data.img = src;
		//data.jid = $('#jid').val();
		var data = new FormData($("#uploadImgForm")[0]);  
		data.append("imgBase64",src);//  
		console.log(src);
		$.ajax({  
            type : 'post',//请求方式为post  
            datatype : 'json',//服务端返回的数据类型  
            url : "upload_head_img",//接收数据的映射接口  
            data : data,  
            /**    
             * 必须false才会避开jQuery对 formdata 的默认处理    
             * XMLHttpRequest会对 formdata 进行正确的处理    
             */    
            processData : false,  
            contentType : false,//"application/x-www-form-urlencoded",//直接用对象接收时，必须这么写；若要发送json格式数据，可写成“application/json”，用注解@RequestBody String str接收
            async : false,
            success : function(data) { 
                alert("上传成功");  
                $('.user_pic img').attr('src',src );
                  
            },//ajax请求成功后调用该方法  
            error : function(data) { 
            	alert("上传失败"); 
            }  

        });
	}	

</script>
</body>
</html>
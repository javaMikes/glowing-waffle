<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css">

<script type="text/javaScript" src="static/js/jquery-3.2.1.min.js"></script>

<script src="static/bootstrap/js/bootstrap.min.js"></script>

<style type="text/css">

#tag_box dl {
	margin: 0px;
	clear: left;
}

#tag_box dt {
	float: left;
	padding-top: 10px;
	width: 15%;
	text-align: center;
}

#tag_box dd {
	float: left;
	border-left: 1px solid #e6e6e6;
	padding-left: 15px;
	width: 80%;
	line-height: 23px;
}

#tag_box dd a{
	display: inline-block;
    padding: 6px 12px;
    margin-bottom: 0;
    font-size: 14px;
    font-weight: 400;
    line-height: 1.42857143;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -ms-touch-action: manipulation;
    touch-action: manipulation;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px;
}


#tag_box dd span{
	font-size: 0.9em;
	color: #e6e6e6;
}

</style>

<title>后台主页面</title>
</head>
<body>

<!-- 添加静态框 -->
<!-- 修改歌曲信息模态框 -->
<!-- Modal -->
<div class="modal fade" id="update_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
            <div class="modal-content">
                  <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">歌曲信息修改</h4>
                  </div>
                  <div class="modal-body">
                       <!-- 添加一个表单 -->
                       <!-- <form class="form-horizontal">
                          <div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label">账号</label>
                            <div class="col-sm-10">
                              <p class="form-control-static" id="update_name"></p>
                              <span class="help-block"></span>
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
                            <div class="col-sm-10">
                              <input type="password" class="form-control" id="update_password" name="password" placeholder="请输入密码">
                              <span class="help-block"></span>
                            </div>
                          </div>
                    </form> -->

                    <table class="table table-hover table-condensed">
                        <tbody>
                            <tr>
                                <td colspan="2">歌曲名称</td>
                                <td ><label id="song_name"></label></td>
                                <td colspan="2">歌手</td>
                                <td ><input type="text" value="" id="singer_name"></td>
                            </tr>
                            <tr>
                                <!-- <td colspan="2">作词者</td>
                                <td ><input type="text" value="" id="lyrics_name"></td> -->
                                <td colspan="2">作曲者</td>
                                <td ><input type="text" value="" id="composition_name"></td>
                            </tr>
                            <tr>
                            	<td colspan="4">歌曲介绍</td>
                            </tr>
                            	
                        </tbody>
                    </table>
					<textarea class="form-control" rows="3" id="song_introduction"></textarea>
					
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="song_update_btn">修改</button>
                  </div>
            </div>
      </div>
</div>

<!-- 修改歌手信息模态框 -->
<!-- Modal -->
<div class="modal fade" id="singer_update_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
            <div class="modal-content">
                  <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">歌手信息修改</h4>
                  </div>
                  <div class="modal-body">
                      
                    <table class="table table-hover table-condensed">
                        <tbody>
                            <tr>
                                <td colspan="2">歌手ID</td>
                                <td ><label id="singerId"></label></td>
                                <td colspan="2">歌手名字</td>
                                <td ><input type="text" value="" id="singerName"></td>
                            </tr>
                            <tr>
                                <td colspan="2">入驻时间</td>
                                <td ><input type="date" value="" id="settledTime"></td>
                            </tr>
                            <tr>
                            	<td colspan="4">歌手介绍</td>
                            </tr>
                            	
                        </tbody>
                    </table>
					<textarea class="form-control" rows="3" id="singerIntroduction"></textarea>
					
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="singer_update_btn">修改</button>
                  </div>
            </div>
      </div>
</div>

<!-- 添加歌手模态框 -->
<!-- Modal -->
<div class="modal fade" id="singer_add_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
            <div class="modal-content">
                  <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">歌手添加</h4>
                  </div>
                  <div class="modal-body">
                      
                    <table class="table table-hover table-condensed">
                        <tbody>
                            <tr>
                                <td colspan="2">歌手名字</td>
                                <td ><input type="text" id="add_singerName"></td>
                                <td colspan="2">入驻时间</td>
                                <td ><input type="date" id="add_settledTime"></td>
                            </tr>
                            <tr>
                                <td colspan="2">歌手介绍</td>
                            </tr>
                            	
                        </tbody>
                    </table>
					<textarea class="form-control" rows="3" id="add_introduction"></textarea>
					
					<form id="uploadSingerImgForm" method="post">
                   		<tr>
                           	<td colspan="2">歌手头像上传</td>
                            <td ><input type="file" name="singerImg_file" id="singerImg_file"></td>
                        </tr>
					</form>
					
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="singer_add_btn">添加</button>
                  </div>
            </div>
      </div>
</div>

<!-- 查看歌曲评论详细信息模态框 -->
<!-- Modal -->
<div class="modal fade" id="song_comment_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
            <div class="modal-content">
                  <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">歌曲评论详情</h4>
                  </div>
                  <div class="modal-body">
                      
                    <table class="table table-hover table-condensed">
                        <tbody>
                            <tr>
                                <td colspan="2">评论用户</td>
                                <td ><label id="songcomment_user"></label></td>
                                <td colspan="2">评论歌曲</td>
                                <td ><label id="songcomment_song"></label></td>
                            </tr>
                            <tr>
                                <td colspan="2">评论时间</td>
                                <td ><label id="songcomment_time"></label></td>
                                <td colspan="2">评论内容</td>
                                <td ><label id="songcomment_content"></label></td>
                            </tr>
                            	
                        </tbody>
                    </table>
					
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-danger" id="songcomment_del_btn">删除评论</button>
                    <button type="button" class="btn btn-primary" id="songcomment_pass_btn">审核通过</button>
                  </div>
            </div>
      </div>
</div>

<!-- 查看歌单评论详细信息模态框 -->
<!-- Modal -->
<div class="modal fade" id="playlist_comment_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
            <div class="modal-content">
                  <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">歌单评论详情</h4>
                  </div>
                  <div class="modal-body">
                      
                    <table class="table table-hover table-condensed">
                        <tbody>
                            <tr>
                                <td colspan="2">评论用户</td>
                                <td ><label id="playlist_comment_user"></label></td>
                                <td colspan="2">评论歌单</td>
                                <td ><label id="playlist_comment_song"></label></td>
                            </tr>
                            <tr>
                                <td colspan="2">评论时间</td>
                                <td ><label id="playlist_comment_time"></label></td>
                                <td colspan="2">评论内容</td>
                                <td ><label id="playlist_comment_content"></label></td>
                            </tr>
                            	
                        </tbody>
                    </table>
					
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-danger" id="playlist_comment_del_btn">删除评论</button>
                    <button type="button" class="btn btn-primary" id="playlist_comment_pass_btn">审核通过</button>
                  </div>
            </div>
      </div>
</div>

<!-- 添加后台管理员模态框 -->
<!-- Modal -->
<div class="modal fade" id="superuser_add_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
            <div class="modal-content">
                  <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">管理员添加</h4>
                  </div>
                  <div class="modal-body">
                      
                    <table class="table table-hover table-condensed">
                        <tbody>
                            <tr>
                                <td colspan="2">昵称</td>
                                <td ><input type="text" id="add_superUserName"></td>
                                <td colspan="2">密码</td>
                                <td ><input type="password" id="add_superUserPwd"></td>
                            </tr>
                            <tr>
                                <td colspan="2">性别</td>
                                <td><input type="radio" name="sex" id="male_add" value="0" checked="checked">&nbsp;&nbsp;&nbsp;男 </td>
                                <td><input type="radio" name="sex" id="female_add" value="1">&nbsp;&nbsp;&nbsp;女</td>
                            </tr>
                            <tr>
                            	<td colspan="2">生日</td>
                                <td ><input type="date" id="add_birthday"></td>
                                <td colspan="2">邮箱</td>
                                <td ><input type="text" id="add_email"></td>
                            </tr>
                            <tr>
                            	<td colspan="2">地区</td>
                                <td ><input type="text" id="add_region"></td>
                            	<td colspan="2">个性签名</td>
                                <td ><input type="text" id="add_personsignature"></td>
                            </tr>
                        </tbody>
                    </table>
                    <form id="uploadUserImgForm" method="post">
                   		<tr>
                           	<td colspan="2">头像上传</td>
                            <td ><input type="file" name="img_file" id="img_file"></td>
                        </tr>
					</form>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="superuser_add_btn">添加</button>
                  </div>
            </div>
      </div>
</div>

<!-- 修改管理员信息模态框 -->
<!-- Modal -->
<div class="modal fade" id="superuser_update_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
            <div class="modal-content">
                  <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">管理员信息修改</h4>
                  </div>
                  <div class="modal-body">
                      
                    <table class="table table-hover table-condensed">
                        <tbody>
                            <tr>
                                <td colspan="2">歌手ID</td>
                                <td ><label id="singerId"></label></td>
                                <td colspan="2">歌手名字</td>
                                <td ><input type="text" value="" id="singerName"></td>
                            </tr>
                            <tr>
                                <td colspan="2">入驻时间</td>
                                <td ><input type="date" value="" id="settledTime"></td>
                            </tr>
                            <tr>
                            	<td colspan="4">歌手介绍</td>
                            </tr>
                            	
                        </tbody>
                    </table>
					<textarea class="form-control" rows="3" id="singerIntroduction"></textarea>
					
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="singer_update_btn">修改</button>
                  </div>
            </div>
      </div>
</div>


<div class="container-fluid">
		<div class="row">
		<c:if test="${userInfo.isSuper == false || userInfo.isSuper == null}">
			<c:redirect url="adminLogin.jsp"/>
		</c:if>
		<c:if test="${userInfo.isSuper == true}">
			<div class="col-sm-2">
				<!-- 父导航，控制sub_nav -->
				<ul class="nav nav-pills nav-stacked" role="tablist" id="nav">
					<li><span class="glyphicon glyphicon-user" style="color: rgb(234, 70, 216); font-size: 20px; text-shadow: rgb(0, 0, 0) -0.3px -0.3px 0.5px;margin-top:2%">${userInfo.nickname }</span></li>
					<li role="presentation"><a href="#singer_manager" aria-controls="singer_manager" role="tab" data-toggle="tab" style="margin-top:3%">歌手管理</a></li>
					<li role="presentation"><a href="#song_manager" aria-controls="song_manager" role="tab" data-toggle="tab">歌曲管理</a></li>
					<!-- <li role="presentation"><a href="#playlist_manager" aria-controls="playlist_manager" role="tab" data-toggle="tab">歌单管理</a></li> -->
					<!-- <li role="presentation"><a href="#mv_manager" aria-controls="mv_manager" role="tab" data-toggle="tab">MV管理</a></li> -->
					<li role="presentation"><a href="#comment_manager" aria-controls="comment_manager" role="tab" data-toggle="tab">评论管理</a></li>
					<li role="presentation"><a href="#user_manager" aria-controls="user_manager" role="tab" data-toggle="tab">用户管理</a></li>
					<!-- <li role="presentation" onClick="showUserInfo()"><a href="#system_management" aria-controls="system_management" role="tab" data-toggle="tab">系统管理</a></li> -->
					<li role="presentation" ><a href="javascript:void(0)" id="adminlogout" role="tab" data-toggle="tab">注销</a></li> 
				</ul>
			</div>
		</c:if>
			<div class="col-sm-10">
				<!-- 子导航，控制tab_content -->
				<div class="tab-content" id="sub_nav">
					
					<!-- 歌手管理子导航 -->
					<div role="tabpanel" class="tab-pane" id="singer_manager">
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation"><a href="#singer_crud" aria-controls="singer_crud" role="tab" data-toggle="tab">歌手增删改查</a></li>
							<!-- <li role="presentation"><a href="#singer_addggg" aria-controls="singer_addggg" role="tab" data-toggle="tab">XXXX</a></li> -->
						</ul>
					</div>
					<!--歌曲管理子导航  -->
					<div role="tabpanel" class="tab-pane" id="song_manager">
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation"><a href="#upload_song" aria-controls="upload_song" role="tab" data-toggle="tab">歌曲上传</a></li>
							<li role="presentation"><a href="#update_song" aria-controls="update_song" role="tab" data-toggle="tab">歌曲修改</a></li>
						</ul>
					</div>
					<!--评论管理子导航  -->
					<div role="tabpanel" class="tab-pane" id="comment_manager">
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation"><a href="#song_comment_check" aria-controls="song_comment_check" role="tab" data-toggle="tab">歌曲评论审核</a></li>
							<li role="presentation"><a href="#playlist_comment_check" aria-controls="playlist_comment_check" role="tab" data-toggle="tab">歌单评论审核</a></li>
							<!-- <li role="presentation"><a href="#playlist_comment_check" aria-controls="playlist_comment_check" role="tab" data-toggle="tab">歌单评论审核</a></li> -->
						</ul>
					</div>
					
					<!--用户管理子导航  -->
					<div role="tabpanel" class="tab-pane" id="user_manager">
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation"><a href="#show_super_user" aria-controls="show_super_user" role="tab" data-toggle="tab">管理员列表</a></li>
							<!-- <li role="presentation"><a href="#song_comment_report_check" aria-controls="song_comment_report_check" role="tab" data-toggle="tab">被举报歌曲评论审核</a></li>
							<li role="presentation"><a href="#playlist_comment_check" aria-controls="playlist_comment_check" role="tab" data-toggle="tab">歌单评论审核</a></li> -->
							<!-- <li role="presentation"><a href="#user_xxx" aria-controls="user_xxx" role="tab" data-toggle="tab">XXXX</a></li> -->
						</ul>
					</div>
					
				</div>
				
				<!-- 标签页内容 -->
				<div class="tab-content" id="tab_content">
					<!--歌曲上传 -->
					<div role="tabpanel" class="tab-pane" id="upload_song">
						<!-- <label for="avatarInput" style="line-height: 35px;">歌曲上传</label>
						<button class="btn btn-primary"  type="button" style="height: 35px;" onclick="$('input[id=avatarInput]').click();">请选择歌曲</button>
						<span id="avatar-name"></span>
						<input class="avatar-input hide" id="avatarInput" name="avatar_file" type="file">
						
						<button class="btn btn-primary" type="button" id="upload_song_btn"> 上传</button> -->
						
						<table class="table table-hover table-condensed">
                        <tbody>
                            <tr>
                                <td colspan="2">歌曲名称</td>
                                <td ><input type="text" value="" id="upload_song_name"></td>
                                <td colspan="2">歌手</td>
                                <td ><input type="text" value="" id="upload_singer_name"></td>
                            </tr>
                            <tr>
                                <td colspan="2">作曲者</td>
                                <td ><input type="text" value="" id="upload_composition_name"></td>
                                <td colspan="2">所需积分</td>
                                <td ><input type="text" value="" id="upload_integral"></td>
                            </tr>
                            <tr>
                            	<td colspan="4">歌曲介绍</td>
                            </tr>
                            
                            
                        </tbody>
                    </table>
                        <textarea class="form-control" rows="3" id="upload_song_introduction"></textarea>
                        
                        <div class="panel panel-default">
						 	<div class="panel-heading">选择标签</div>
							<div class="panel-body">
						  		<div id="tag_box">
									<dl>
										<dt>语种</dt>
										<dd>
									   		<a href="javascript:void(0)" data-tag="华语">华语</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="欧美">欧美</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="日语">日语</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="韩语">韩语</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="粤语">粤语</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="小语种">小语种</a>
									   		<span>|</span>
									   	</dd>
									</dl>						
									<dl>
										<dt>风格</dt>
										<dd>
									   		<a href="javascript:void(0)" data-tag="流行">流行</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="摇滚">摇滚</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="民谣">民谣</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="电子">电子</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="舞曲">舞曲</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="说唱">说唱</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="轻音乐">轻音乐</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="爵士">爵士</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="乡村">乡村</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="拉丁">拉丁</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="古典">古典</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="民族">民族</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="英伦">英伦</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="金属">金属</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="朋克">朋克</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="蓝调">蓝调</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="雷鬼">雷鬼</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="世界音乐">世界音乐</a>
									   		<span>|</span>
									   	</dd>
									</dl>						
									<dl>
										<dt>场景</dt>
										<dd>
									   		<a href="javascript:void(0)" data-tag="清晨">清晨</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="夜晚">夜晚</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="学习">学习</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="工作">工作</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="午休">午休</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="下午茶">下午茶</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="地铁">地铁</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="驾车">驾车</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="运动">运动</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="旅行">旅行</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="散步">散步</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="酒吧">酒吧</a>
									   		<span>|</span>
									   	</dd>
									</dl>
									<dl>
										<dt>情感</dt>
										<dd>
									   		<a href="javascript:void(0)" data-tag="怀旧">怀旧</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="清新">清新</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="浪漫">浪漫</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="性感">性感</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="伤感">伤感</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="治愈">治愈</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="放松">放松</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="孤独">孤独</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="感动">感动</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="兴奋">兴奋</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="快乐">快乐</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="安静">安静</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="思念">思念</a>
									   		<span>|</span>
									   	</dd>
									</dl>
									<dl>
										<dt>主题</dt>
										<dd>
									   		<a href="javascript:void(0)" data-tag="影视原声">影视原声</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="ACG">ACG</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="校园">校园</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="游戏">游戏</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="70后">70后</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="80后">80后</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="90后">90后</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="网络歌曲">网络歌曲</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="KTV">KTV</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="经典">经典</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="吉他">吉他</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="钢琴">钢琴</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="器乐">器乐</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="儿童">儿童</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="榜单">榜单</a>
									   		<span>|</span>
									   		<a href="javascript:void(0)" data-tag="00后">00后</a>
									   		<span>|</span>
									   	</dd>
									</dl>
								</div>	
							</div>
						</div>
                        
						<form id="uploadForm" method="post">
							<tr>
								<td colspan="2">歌曲文件上传</td>
								<td><input type="file" name="song_file" id="song_file"></td>
								
							</tr>
						
						
					
							<tr>
								<td colspan="2">歌曲歌词文件上传</td>
								<td><input type="file" name="songLyrics_file" id="songLyrics_file"></td>
							</tr>
						
							<tr>
								<td colspan="2">歌曲图像上传</td>
								<td><input type="file" name="songImg_file" id="songImg_file"></td>
							</tr>
						</form>
						<button class="btn btn-primary" value="upload_song_btn" onclick="upload_song_btn()">上传</button>
					</div>
					
					<!-- 歌曲修改删除 -->
					<div role="tabpanel" class="tab-pane" id="update_song">
						<div class="row">
							<div class="col-md-4 col-md-offset-10">
								<button class="btn btn-danger" id="song_delete_all">删除全部</button>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-12">
								<table class="table table-hover" id="updatetable">
									<thead>
										<tr>
											<!-- <th style="width:25%">歌曲名</th>
											<th style="width:25%">歌手</th>
											<th style="width:25%">作曲者</th>
											<th style="width:25%">操作</th> -->
											<td><input type="checkbox" id="check_all"/></td>
											<td>歌曲名</td>
											<td>歌手</td>
											<td>作曲者</td>
											<td>操作</td>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>	
							<div class="row">
								<div class="col-md-6 text-center" id="page_info">
								</div>
								
								<div class="col-md-6 text-center" id="page_nav">
								</div>
							</div>
						</div>
						
						<!-- 歌手增删改查 -->
						<div role="tabpanel" class="tab-pane" id="singer_crud">
							<div class="row">
								<div class="col-md-4 col-md-offset-8">
									<button class="btn btn-primary" id="singer_add">歌手添加</button>
									<button class="btn btn-danger" id="singer_delete_all">删除全部</button>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-12">
									<table class="table table-hover" id="singertable">
										<thead>
											<tr>
												<!-- <th style="width:25%">歌曲名</th>
												<th style="width:25%">歌手</th>
												<th style="width:25%">作曲者</th>
												<th style="width:25%">操作</th> -->
												<td><input type="checkbox" id="singer_check_all"/></td>
												<td>歌曲ID</td>
												<td>歌手</td>
												<td>入驻时间</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>	
								<div class="row">
									<div class="col-md-6 text-center" id="singer_page_info">
									</div>
									
									<div class="col-md-6 text-center" id="singer_page_nav">
									</div>
								</div>
						</div>
					
						<!-- 歌曲评论审核 -->
						<div role="tabpanel" class="tab-pane" id="song_comment_check">
							<div class="row">
								<div class="col-md-4 col-md-offset-10">
									<button class="btn btn-danger" id="comment_delete_all">删除全部</button>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-12">
									<table class="table table-hover" id="songCommentTable">
										<thead>
											<tr>
												<!-- <th style="width:25%">歌曲名</th>
												<th style="width:25%">歌手</th>
												<th style="width:25%">作曲者</th>
												<th style="width:25%">操作</th> -->
												<td><input type="checkbox" id="songcomment_check_all"/></td>
												<td>评论ID</td>
												<td>评论用户</td>
												<td>评论歌曲</td>
												<td>评论时间</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>	
								<div class="row">
									<div class="col-md-6 text-center" id="song_comment_page_info">
									</div>
									
									<div class="col-md-6 text-center" id="song_comment_page_nav">
									</div>
								</div>
						</div>
						
						<!-- 歌单评论审核 -->
						<div role="tabpanel" class="tab-pane" id="playlist_comment_check">
							<div class="row">
								<div class="col-md-4 col-md-offset-10">
									<button class="btn btn-danger" id="playlist_comment_delete_all">删除全部</button>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-12">
									<table class="table table-hover" id="playlistCommentTable">
										<thead>
											<tr>
												<!-- <th style="width:25%">歌曲名</th>
												<th style="width:25%">歌手</th>
												<th style="width:25%">作曲者</th>
												<th style="width:25%">操作</th> -->
												<td><input type="checkbox" id="playlist_comment_check_all"/></td>
												<td>评论ID</td>
												<td>评论用户</td>
												<td>评论歌单</td>
												<td>评论时间</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>	
								<div class="row">
									<div class="col-md-6 text-center" id="playlist_comment_page_info">
									</div>
									
									<div class="col-md-6 text-center" id="playlist_comment_page_nav">
									</div>
								</div>
						</div>
						
						<!-- 管理员增删改查页面 -->
						<div role="tabpanel" class="tab-pane" id="show_super_user">
							<div class="row">
								<div class="col-md-4 col-md-offset-8">
									<button class="btn btn-primary" id="superuser_add">管理员添加</button>
									<!-- <button class="btn btn-danger" id="superuser_delete_all">删除全部</button> -->
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-12">
									<table class="table table-hover" id="superUserTable">
										<thead>
											<tr>
												<!-- <th style="width:25%">歌曲名</th>
												<th style="width:25%">歌手</th>
												<th style="width:25%">作曲者</th>
												<th style="width:25%">操作</th> -->
												<!-- <td><input type="checkbox" id="superuser_check_all"/></td> -->
												<td>管理员ID</td>
												<td>昵称</td>
												<td>地区</td>
												<td>个性签名</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
									
								</div>
							</div>	
								<div class="row">
									<div class="col-md-6 text-center" id="superuser_page_info">
									</div>
									
									<div class="col-md-6 text-center" id="superuser_page_nav">
									</div>
								</div>
						</div>
						
				</div>
				
			</div>
				
		</div><!-- End:<div class="row"> -->
</div>
</body>

<script type="text/javascript">
$("#tag_box dd a").click(function(){
	$(this).toggleClass("btn btn-info");
	if ($("#tag_box dd .btn-info").length > 3) {
		$(this).toggleClass("btn btn-info");
		alert("最多只能选择3个标签 : (");
		return ;
	} 
});	
	
	//定义全局变量
	var total,pageNum;
	
	$(function(){
		to_page(1);
		to_singer_page(1);
		to_song_comment_page(1);
		to_playlist_comment_page(1);
		to_superuser_page(1);
	});
	
	function to_page(pn){
		$.ajax({
			url : "songSelect",
			data : "pn=" + pn,
			type : "post",
			success : function(msg){
				// 解析并显示歌曲数据
				build_song_table(msg);
				//解析并显示分页信息
				build_song_info(msg);
				//解析并显示分页条信息
				build_song_nav(msg);
				
			},
			error : function(){
				
			}
		});
	}	
	
	function to_singer_page(pn){
		$.ajax({
			url : "singerSelect",
			data : "pn=" + pn,
			type : "post",
			success : function(msg){
				// 解析并显示歌曲数据
				build_singer_table(msg);
				//解析并显示分页信息
				build_singer_info(msg);
				//解析并显示分页条信息
				build_singer_nav(msg);
			},
			error : function(){
				
			}
		});
	}
	
	function to_song_comment_page(pn){
		$.ajax({
			url : "songCommentReportSelect",
			data : "pn=" + pn,
			type : "post",
			success : function(msg){
				// 解析并显示歌曲评论数据
				build_song_comment_table(msg);
				//解析并显示歌曲评论分页信息
				build_song_comment_info(msg);
				//解析并显示歌曲评论分页条信息
				build_song_comment_nav(msg);
			},
			error : function(){
				
			}
		});
	}
	
	function to_playlist_comment_page(pn){
		$.ajax({
			url : "playlistCommentSelect",
			data : "pn=" + pn,
			type : "post",
			success : function(msg){
				// 解析并显示歌单评论数据
				build_playlist_comment_table(msg);
				// 解析并显示歌单评论分页信息
				build_playlist_comment_info(msg);
				// 解析并显示歌单评论分页条信息
				build_playlist_comment_nav(msg);
			},
			error : function(){
				
			}
		});
	}
	
	function to_superuser_page(pn){
		$.ajax({
			url : "superUserSelect",
			data : "pn=" + pn,
			type : "post",
			success : function(msg){
				//解析并显示管理员数据
				build_superuser_table(msg);
				//解析并显示管理员评论分页信息
				build_superuser_info(msg);
				//解析并显示歌曲评论分页条信息
				build_superuser_nav(msg);
			},
			error : function(){
				
			}
		});
	}
	
	
	function upload_song_btn(){
		var upload_song_name = $("#upload_song_name").val();
		var upload_singer_name = $("#upload_singer_name").val();
		var upload_composition_name = $("#upload_composition_name").val();
		var upload_integral = $("#upload_integral").val();
		var upload_song_introduction = $("#upload_song_introduction").val();
		var tagStr = "";
		$.each($("#tag_box dd .btn-info"), function(index, element){
			tagStr += $(this).attr("data-tag") + ",";
		});
		tagStr = tagStr.slice(0, -1);
		
		var formData = new FormData($("#uploadForm")[0]);
		
		formData.append("upload_song_name", upload_song_name);
		formData.append("upload_singer_name", upload_singer_name);
		formData.append("upload_composition_name", upload_composition_name);
		formData.append("upload_integral", upload_integral);
		formData.append("upload_song_introduction", upload_song_introduction);
		formData.append("tagName", tagStr);
		
		$.ajax({
			url : 'songUpload',
			type : 'POST', 
			data : formData,
			async : false,
			contentType : false,
			processData : false,
			success : function(returndata){
				alert("上传成功");
			},
			error : function(returndata){
				alert("上传失败");
			}
		}); 
	} 
	
	var val = $("#checkbox_id").val();
	
	// 解析并显示歌曲数据
	function build_song_table(msg){
		//清空table
		$("#updatetable tbody").empty();
		var songList = msg.map.pageInfo.list;
		$.each(songList, function(index, song){
			var checkBoxTd = $("<td><input type='checkbox' class='check_item' value='"+song.id+"'/></td>");
			var song_name = $("<td></td>").append(song.songName);
			var singer = $("<td></td>").append(song.singer);
			var composition = $("<td></td>").append(song.composition);
			//修改按钮
            var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn").append("<span></span>").addClass("glyphicon glyphicon-pencil").append("修改");
			//给修改按钮添加一个自定义属性，用来存取歌曲ID
			editBtn.attr("edit_id", song.id);
			
			//删除按钮
			var deleteBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn").append("<span></span>").addClass("glyphicon glyphicon-trash").append("删除");
			deleteBtn.click(function(){
				song_delete(song.id);
			});
			
            var tr = $("<tr></tr>");
            tr.append(checkBoxTd).append(song_name).append(singer).append(composition).append(editBtn).append(deleteBtn).appendTo("#updatetable tbody");
		});
	}
	
	// 解析并显示歌手数据
	function build_singer_table(msg){
		//清空table
		$("#singertable tbody").empty();
		var singerList = msg.map.singerPageInfo.list;
		$.each(singerList, function(index, singer){
			var checkBoxTd = $("<td><input type='checkbox' class='singer_check_item' value='"+singer.id+"'/></td>");
			var id = $("<td></td>").append(singer.id);
			var singer_name = $("<td></td>").append(singer.singerName);
			/* var settled_time_milliseconds = parseInt(singer.settledTime.replace(/\D/igm,""));
			alert("settled_time_milliseconds" + settled_time_milliseconds); 
			var settled_time = $("<td></td>").append(new Date(settled_time_milliseconds));*/
			var settled_time = $("<td></td>").append(new Date(singer.settledTime).toLocaleString());
			//var settled_time = $("<td></td>").append((new Date(singer.settledTime)).Format("yyyy-MM-dd"));
			//修改按钮
            var singerEditBtn = $("<button></button>").addClass("btn btn-primary btn-sm singer_edit_btn").append("<span></span>").addClass("glyphicon glyphicon-pencil").append("修改");
			//给修改按钮添加一个自定义属性，用来存取歌曲ID
			singerEditBtn.attr("singer_edit_id", singer.id);
			
			
			//删除按钮
			var singerDeleteBtn = $("<button></button>").addClass("btn btn-danger btn-sm singer_delete_btn").append("<span></span>").addClass("glyphicon glyphicon-trash").append("删除");
			singerDeleteBtn.click(function(){
				singer_delete(singer.id);
			}); 
			
            var tr = $("<tr></tr>");
			
            tr.append(checkBoxTd).append(id).append(singer_name).append(settled_time).append(singerEditBtn).append(singerDeleteBtn).appendTo("#singertable tbody");
		});
	}
	
	// 解析并显示歌曲评论数据
	function build_song_comment_table(msg){
		//清空table
		$("#songCommentTable tbody").empty();
		var songCommentList = msg.map.songCommentReportPageInfo.list;
		$.each(songCommentList, function(index, songComment){
			var checkBoxTd = $("<td><input type='checkbox' class='song_comment_check_item' value='"+songComment.id+"'/></td>");
			var id = $("<td></td>").append(songComment.id);
			var userId = $("<td></td>").append(songComment.userId);
			var songId = $("<td></td>").append(songComment.songId);
			var commentTime = $("<td></td>").append(new Date(songComment.commentTime).toLocaleString());
			//查看详情按钮
            var songCommentEditBtn = $("<button></button>").addClass("btn btn-primary btn-sm song_comment_check_btn").append("<span></span>").addClass("glyphicon glyphicon-pencil").append("查看详情");
			//给修改按钮添加一个自定义属性，用来存取歌曲ID
			songCommentEditBtn.attr("song_comment_check_id", songComment.id);
			
			//删除按钮
			var songCommentDeleteBtn = $("<button></button>").addClass("btn btn-danger btn-sm song_comment_delete_btn").append("<span></span>").addClass("glyphicon glyphicon-trash").append("删除");
			songCommentDeleteBtn.click(function(){
				songComment_delete(songComment.id);
			}); 
			
            var tr = $("<tr></tr>");
			
            tr.append(checkBoxTd).append(id).append(userId).append(songId).append(commentTime ).append(songCommentEditBtn).append(songCommentDeleteBtn).appendTo("#songCommentTable tbody");
		});
	}
	
	// 解析并显示被举报歌曲评论数据
	function build_songcomment_report_table(msg){
		//清空table
		$("#songCommentReportTable tbody").empty();
		var songCommentList = msg.map.songCommentReportPageInfo.list;
		$.each(songCommentList, function(index, songComment){
			var checkBoxTd = $("<td><input type='checkbox' class='songcomment_report_check_item' value='"+songComment.id+"'/></td>");
			var id = $("<td></td>").append(songComment.id);
			var userId = $("<td></td>").append(songComment.userId);
			var songId = $("<td></td>").append(songComment.songId);
			var commentTime = $("<td></td>").append(new Date(songComment.commentTime).toLocaleString());
			//查看审核按钮
            var songCommentCheckBtn = $("<button></button>").addClass("btn btn-primary btn-sm song_comment_check_btn").append("<span></span>").addClass("glyphicon glyphicon-pencil").append("查看详情");
			//给审核按钮添加一个自定义属性，用来存取歌曲ID
			songCommentCheckBtn.attr("song_comment_check_id", songComment.id);
			
			//删除按钮
			var songCommentDeleteBtn = $("<button></button>").addClass("btn btn-danger btn-sm song_comment_delete_btn").append("<span></span>").addClass("glyphicon glyphicon-trash").append("删除");
			songCommentDeleteBtn.click(function(){
				songComment_delete(songComment.id);
				to_songcomment_report_page(pageNum);
			}); 
			
            var tr = $("<tr></tr>");
			
            tr.append(checkBoxTd).append(id).append(userId).append(songId).append(commentTime).append(songCommentCheckBtn).append(songCommentDeleteBtn).appendTo("#songCommentReportTable tbody");
		});
	}
	
	// 解析并显示管理员数据
	function build_superuser_table(msg){
		//清空table
		$("#superUserTable tbody").empty();
		var superUserList = msg.map.superUserPageInfo.list;
		$.each(superUserList, function(index, superUser){
			//var checkBoxTd = $("<td><input type='checkbox' class='super_user_check_item' value='"+superUser.id+"'/></td>");
			var id = $("<td></td>").append(superUser.id);
			var nickName = $("<td></td>").append(superUser.nickname);
			var region = $("<td></td>").append(superUser.region);
			var personSignature = $("<td></td>").append(superUser.personSignature);
			/* //修改按钮
            var superUserEditBtn = $("<button></button>").addClass("btn btn-primary btn-sm superuser_edit_btn").append("<span></span>").addClass("glyphicon glyphicon-pencil").append("修改");
			superUserEditBtn.attr("superuser_edit_id", superUser.id); */
			
			//删除按钮
			var superUserDeleteBtn = $("<button></button>").addClass("btn btn-danger btn-sm superuser_delete_btn").append("<span></span>").addClass("glyphicon glyphicon-trash").append("删除");
			superUserDeleteBtn.click(function(){
				superuser_delete(superUser.id);
				//to_songcomment_report_page(pageNum);
			}); 
			
            var tr = $("<tr></tr>");
			
            tr.append(id).append(nickName).append(region).append(personSignature).append(superUserDeleteBtn).appendTo("#superUserTable tbody");
		});
	}
	
	// 解析并显示歌单评论数据
	function build_playlist_comment_table(msg){
		//清空table
		$("#playlistCommentTable tbody").empty();
		var playlistCommentList = msg.map.playlistCommentPageInfo.list;
		$.each(playlistCommentList, function(index, playlistComment){
			var checkBoxTd = $("<td><input type='checkbox' class='playlist_comment_check_item' value='"+playlistComment.id+"'/></td>");
			var id = $("<td></td>").append(playlistComment.id);
			var userId = $("<td></td>").append(playlistComment.userId);
			var playlistId = $("<td></td>").append(playlistComment.playlistId);
			var commentTime = $("<td></td>").append(new Date(playlistComment.commentTime).toLocaleString());
			//查看审核按钮
            var playlistCommentCheckBtn = $("<button></button>").addClass("btn btn-primary btn-sm playlist_comment_check_btn").append("<span></span>").addClass("glyphicon glyphicon-pencil").append("查看详情");
			//给审核按钮添加一个自定义属性，用来存取歌单ID
			playlistCommentCheckBtn.attr("playlist_comment_check_id", playlistComment.id);
			
			//删除按钮
			var playlistCommentDeleteBtn = $("<button></button>").addClass("btn btn-danger btn-sm playlist_comment_delete_btn").append("<span></span>").addClass("glyphicon glyphicon-trash").append("删除");
			playlistCommentDeleteBtn.click(function(){
				playlistComment_delete(playlistComment.id);
				to_playlist_comment_page(pageNum);
			}); 
			
            var tr = $("<tr></tr>");
			
            tr.append(checkBoxTd).append(id).append(userId).append(playlistId).append(commentTime).append(playlistCommentCheckBtn).append(playlistCommentDeleteBtn).appendTo("#playlistCommentTable tbody");
			
		});
	}
	
	//解析并显示歌曲分页信息
	function build_song_info(msg){
		//情况page_info
		$("#page_info").empty();
		
		$("#page_info").append("当前第" + msg.map.pageInfo.pageNum+"页,一共有"+msg.map.pageInfo.pages+"页,一共有"+msg.map.pageInfo.total+"条数据");
		
		total = msg.map.pageInfo.total;
		pageNum = msg.map.pageInfo.pageNum;
	}
	
	//解析并显示歌手分页信息
	function build_singer_info(msg){ 
		$("#singer_page_info").empty();
		
		$("#singer_page_info").append("当前第" + msg.map.singerPageInfo.pageNum+"页,一共有"+msg.map.singerPageInfo.pages+"页,一共有"+msg.map.singerPageInfo.total+"条数据");
		
		total = msg.map.singerPageInfo.total;
		pageNum = msg.map.singerPageInfo.pageNum;
	}
	
	//解析并显示歌曲评论分页信息
	function build_song_comment_info(msg){
		$("#song_comment_page_info").empty();
		
		$("#song_comment_page_info").append("当前第" + msg.map.songCommentReportPageInfo.pageNum+"页,一共有"+msg.map.songCommentReportPageInfo.pages+"页,一共有"+msg.map.songCommentReportPageInfo.total+"条数据");
		
		total = msg.map.songCommentReportPageInfo.total;
		pageNum = msg.map.songCommentReportPageInfo.pageNum;
	}
	
	//解析并显示歌单评论分页信息
	function build_playlist_comment_info(msg){
		$("#playlist_comment_page_info").empty();
		
		$("#playlist_comment_page_info").append("当前第" + msg.map.playlistCommentPageInfo.pageNum+"页,一共有"+msg.map.playlistCommentPageInfo.pages+"页,一共有"+msg.map.playlistCommentPageInfo.total+"条数据");
		
		total = msg.map.playlistCommentPageInfo.total;
		pageNum = msg.map.playlistCommentPageInfo.pageNum;
	}
	
	//解析并显示管理员评论分页信息
	function build_superuser_info(msg){
		$("#superuser_page_info").empty();
		
		$("#superuser_page_info").append("当前第" + msg.map.superUserPageInfo.pageNum+"页,一共有"+msg.map.superUserPageInfo.pages+"页,一共有"+msg.map.superUserPageInfo.total+"条数据");
		
		total = msg.map.superUserPageInfo.total;
		pageNum = msg.map.superUserPageInfo.pageNum;
	}
	
	//解析并显示分页条信息
	function build_song_nav(msg){
		//清空nav
		$("#page_nav").empty();
		//nav
		var nav = $("<nav></nav>");
		//ul
		var ul = $("<ul></ul>").addClass("pagination");
		
		//首页
		var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
		//上一页
		var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;").attr("href","#"));
		//判断是否为首页
		if(msg.map.pageInfo.hasPreviousPage==false){
			//禁用li
			firstPageLi.addClass("disabled");
			prePageLi.addClass("disabled");
		}else{
			firstPageLi.click(function(){
				to_page(1);
			});
			prePageLi.click(function(){
				to_page(msg.map.pageInfo.pageNum-1);
			});
		}
		ul.append(firstPageLi);
		ul.append(prePageLi);
		//页码
		$.each(msg.map.pageInfo.navigatepageNums, function(index, item){
			var numLi = $("<li></li>").append($("<a></a>").append(item).attr("href","#"));
			if(msg.map.pageInfo.pageNum==item){
				numLi.addClass("active");
			}
			numLi.click(function(){
				to_page(item);
			});
			ul.append(numLi);
		});
		//下一页
		var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;").attr("href","#"));
		//末页
		var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
		//判断是否为末页
		if(msg.map.pageInfo.hasNextPage==false){
			nextPageLi.addClass("disabled");
			lastPageLi.addClass("disabled");
		}else{
			nextPageLi.click(function(){
				to_page(msg.map.pageInfo.pageNum+1);
			});
			lastPageLi.click(function(){
				to_page(msg.map.pageInfo.pages);
			});
		}
		
		ul.append(nextPageLi);
		ul.append(lastPageLi);
		//
		nav.append(ul).appendTo("#page_nav");
	}
	
	//解析并显示歌手分页条信息
	function build_singer_nav(msg){
		//清空nav
		$("#singer_page_nav").empty();
		//nav
		var nav = $("<nav></nav>");
		//ul
		var ul = $("<ul></ul>").addClass("pagination");
		
		//首页
		var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
		//上一页
		var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;").attr("href","#"));
		//判断是否为首页
		if(msg.map.singerPageInfo.hasPreviousPage==false){
			//禁用li
			firstPageLi.addClass("disabled");
			prePageLi.addClass("disabled");
		}else{
			firstPageLi.click(function(){
				to_singer_page(1);
			});
			prePageLi.click(function(){
				to_singer_page(msg.map.singerPageInfo.pageNum-1);
			});
		}
		ul.append(firstPageLi);
		ul.append(prePageLi);
		//页码
		$.each(msg.map.singerPageInfo.navigatepageNums, function(index, item){
			var numLi = $("<li></li>").append($("<a></a>").append(item).attr("href","#"));
			if(msg.map.singerPageInfo.pageNum==item){
				numLi.addClass("active");
			}
			numLi.click(function(){
				to_singer_page(item);
			});
			ul.append(numLi);
		});
		//下一页
		var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;").attr("href","#"));
		//末页
		var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
		//判断是否为末页
		if(msg.map.singerPageInfo.hasNextPage==false){
			nextPageLi.addClass("disabled");
			lastPageLi.addClass("disabled");
		}else{
			nextPageLi.click(function(){
				to_singer_page(msg.map.singerPageInfo.pageNum+1);
			});
			lastPageLi.click(function(){
				to_singer_page(msg.map.singerPageInfo.pages);
			});
		}
		
		ul.append(nextPageLi);
		ul.append(lastPageLi);
		//
		nav.append(ul).appendTo("#singer_page_nav");
	}
	
	//解析并显示歌曲评论分页条信息
	function build_song_comment_nav(msg){
		//清空nav
		$("#song_comment_page_nav").empty();
		//nav
		var nav = $("<nav></nav>");
		//ul
		var ul = $("<ul></ul>").addClass("pagination");
		
		//首页
		var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
		//上一页
		var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;").attr("href","#"));
		//判断是否为首页
		if(msg.map.songCommentReportPageInfo.hasPreviousPage==false){
			//禁用li
			firstPageLi.addClass("disabled");
			prePageLi.addClass("disabled");
		}else{
			firstPageLi.click(function(){
				to_song_comment_page(1);
			});
			prePageLi.click(function(){
				to_song_comment_page(msg.map.songCommentReportPageInfo.pageNum-1);
			});
		}
		ul.append(firstPageLi);
		ul.append(prePageLi);
		//页码
		$.each(msg.map.songCommentReportPageInfo.navigatepageNums, function(index, item){
			var numLi = $("<li></li>").append($("<a></a>").append(item).attr("href","#"));
			if(msg.map.songCommentReportPageInfo.pageNum==item){
				numLi.addClass("active");
			}
			numLi.click(function(){
				to_song_comment_page(item);
			});
			ul.append(numLi);
		});
		//下一页
		var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;").attr("href","#"));
		//末页
		var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
		//判断是否为末页
		if(msg.map.songCommentReportPageInfo.hasNextPage==false){
			nextPageLi.addClass("disabled");
			lastPageLi.addClass("disabled");
		}else{
			nextPageLi.click(function(){
				to_song_comment_page(msg.map.songCommentReportPageInfo.pageNum+1);
			});
			lastPageLi.click(function(){
				to_song_comment_page(msg.map.songCommentReportPageInfo.pages);
			});
		}
		
		ul.append(nextPageLi);
		ul.append(lastPageLi);
		//
		nav.append(ul).appendTo("#song_comment_page_nav");
	}
	
	//解析并显示歌单评论分页条信息
	function build_playlist_comment_nav(msg){
		//清空nav
		$("#playlist_comment_page_nav").empty();
		//nav
		var nav = $("<nav></nav>");
		//ul
		var ul = $("<ul></ul>").addClass("pagination");
		
		//首页
		var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
		//上一页
		var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;").attr("href","#"));
		//判断是否为首页
		if(msg.map.playlistCommentPageInfo.hasPreviousPage==false){
			//禁用li
			firstPageLi.addClass("disabled");
			prePageLi.addClass("disabled");
		}else{
			firstPageLi.click(function(){
				to_playlist_comment_page(1);
			});
			prePageLi.click(function(){
				to_playlist_comment_page(msg.map.playlistCommentPageInfo.pageNum-1);
			});
		}
		ul.append(firstPageLi);
		ul.append(prePageLi);
		//页码
		$.each(msg.map.playlistCommentPageInfo.navigatepageNums, function(index, item){
			var numLi = $("<li></li>").append($("<a></a>").append(item).attr("href","#"));
			if(msg.map.playlistCommentPageInfo.pageNum==item){
				numLi.addClass("active");
			}
			numLi.click(function(){
				to_playlist_comment_page(item);
			});
			ul.append(numLi);
		});
		//下一页
		var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;").attr("href","#"));
		//末页
		var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
		//判断是否为末页
		if(msg.map.playlistCommentPageInfo.hasNextPage==false){
			nextPageLi.addClass("disabled");
			lastPageLi.addClass("disabled");
		}else{
			nextPageLi.click(function(){
				to_playlist_comment_page(msg.map.playlistCommentPageInfo.pageNum+1);
			});
			lastPageLi.click(function(){
				to_playlist_comment_page(msg.map.playlistCommentPageInfo.pages);
			});
		}
		
		ul.append(nextPageLi);
		ul.append(lastPageLi);
		//
		nav.append(ul).appendTo("#playlist_comment_page_nav");
	}
	
	
	//解析并显示管理员分页条信息
	function build_superuser_nav(msg){
		//清空nav
		$("#superuser_page_nav").empty();
		//nav
		var nav = $("<nav></nav>");
		//ul
		var ul = $("<ul></ul>").addClass("pagination");
		
		//首页
		var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
		//上一页
		var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;").attr("href","#"));
		//判断是否为首页
		if(msg.map.superUserPageInfo.hasPreviousPage==false){
			//禁用li
			firstPageLi.addClass("disabled");
			prePageLi.addClass("disabled");
		}else{
			firstPageLi.click(function(){
				to_superuser_page(1);
			});
			prePageLi.click(function(){
				to_superuser_page(msg.map.superUserPageInfo.pageNum-1);
			});
		}
		ul.append(firstPageLi);
		ul.append(prePageLi);
		//页码
		$.each(msg.map.superUserPageInfo.navigatepageNums, function(index, item){
			var numLi = $("<li></li>").append($("<a></a>").append(item).attr("href","#"));
			if(msg.map.superUserPageInfo.pageNum==item){
				numLi.addClass("active");
			}
			numLi.click(function(){
				to_superuser_page(item);
			});
			ul.append(numLi);
		});
		//下一页
		var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;").attr("href","#"));
		//末页
		var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
		//判断是否为末页
		if(msg.map.superUserPageInfo.hasNextPage==false){
			nextPageLi.addClass("disabled");
			lastPageLi.addClass("disabled");
		}else{
			nextPageLi.click(function(){
				to_superuser_page(msg.map.superUserPageInfo.pageNum+1);
			});
			lastPageLi.click(function(){
				to_superuser_page(msg.map.superUserPageInfo.pages);
			});
		}
		
		ul.append(nextPageLi);
		ul.append(lastPageLi);
		//
		nav.append(ul).appendTo("#superuser_page_nav");
	}
	
	//使用on绑定事件：根据歌曲ID获取歌曲信息
	$(document).on("click", ".edit_btn", function(){
		var id = $(this).attr("edit_id");
		$.ajax({
			url : "songSelectById",
			data : "id=" + id,
			type : "post",
			success : function(msg){
				$("#song_name").text(msg.map.pageInfo.songName);
				$("#singer_name").val(msg.map.pageInfo.singer);
				//$("#lyrics_name").val(msg.map.pageInfo.lyrics);
				$("#composition_name").val(msg.map.pageInfo.composition);
				$("#song_introduction").val(msg.map.pageInfo.introduction);
				
				//将id进行传递
				$("#song_update_btn").attr("edit_id",id);
				
				
				//打开修改模态框,把返回值赋值给模态框控件
				$("#update_modal").modal({
					backdrop : "static"
				});
			},
			error : function(){
				
			}
		});
	});
	
	//使用on绑定事件：根据歌手ID获取歌曲信息
	$(document).on("click", ".singer_edit_btn", function(){
		var id = $(this).attr("singer_edit_id");
		$.ajax({
			url : "singerSelectById",
			data : "id=" + id,
			type : "post",
			success : function(msg){
				$("#singerId").text(msg.map.singerPageInfo.id);
				$("#singerName").val(msg.map.singerPageInfo.singerName);
				$("#settledTime").val(msg.map.singerPageInfo.settledTime);
				$("#singerIntroduction").val(msg.map.singerPageInfo.introduction);
				
				//将id进行传递
				$("#singer_update_btn").attr("singer_edit_id",id);
				
				//打开修改模态框,把返回值赋值给模态框控件
				$("#singer_update_modal").modal({
					backdrop : "static"
				});
			},
			error : function(){
				
			}
		});
	});
	
	//使用on绑定事件：根据歌曲评论ID获取评论详细信息
	$(document).on("click", ".song_comment_check_btn", function(){
		var id = $(this).attr("song_comment_check_id");
		$.ajax({
			url : "songCommentSelectById",
			data : "id=" + id,
			type : "post",
			success : function(msg){
				
				$("#songcomment_user").text(msg.map.userName);
				$("#songcomment_song").text(msg.map.songName);
				$("#songcomment_time").text(new Date(msg.map.songComment.commentTime).toLocaleString());
				$("#songcomment_content").text(msg.map.songComment.content);
				
				//将id进行传递
				$("#songcomment_pass_btn").attr("songcomment_pass_id", id);
				$("#songcomment_del_btn").attr("songcomment_del_id", id);
				
				
				//打开歌曲详细信息模态框,把返回值赋值给模态框控件
				$("#song_comment_modal").modal({
					backdrop : "static"
				}); 
			},
			error : function(){
				
			}
		});
	});
	
	//使用on绑定事件：根据歌单评论ID获取评论详细信息
	$(document).on("click", ".playlist_comment_check_btn", function(){
		var id = $(this).attr("playlist_comment_check_id");
		$.ajax({
			url : "playlistCommentSelectById",
			data : "id=" + id,
			type : "post",
			success : function(msg){
				
				$("#playlist_comment_user").text(msg.map.userName);
				$("#playlist_comment_song").text(msg.map.playlistName);
				$("#playlist_comment_time").text(new Date(msg.map.playlistComment.commentTime).toLocaleString());
				$("#playlist_comment_content").text(msg.map.playlistComment.content);
				
				//将id进行传递
				$("#playlist_comment_pass_btn").attr("playlist_comment_pass_id", id);
				$("#playlist_comment_del_btn").attr("playlist_comment_del_id", id);
				
				
				//打开歌曲详细信息模态框,把返回值赋值给模态框控件
				$("#playlist_comment_modal").modal({
					backdrop : "static"
				}); 
			},
			error : function(){
				
			}
		});
	});
	
	// 查看修改管理员信息
	$(document).on("click", ".superuser_edit_btn", function(){
		alert("ooo");
	});
	
	$("#song_update_btn").click(function(){
		var singer_name = $("#singer_name").val();
		var lyrics_name = $("#lyrics_name").val();
		var composition_name = $("#composition_name").val();
		var song_introduction = $("#song_introduction").val();
		
		var id = $(this).attr("edit_id");
		//发送ajax请求修改歌曲信息
		$.ajax({
			url : "songUpdateById",
			data : "id=" + id + "&singer=" + singer_name + "&lyrics=" + lyrics_name + "&composition=" + composition_name + "&introduction=" + song_introduction,
			type : "post",
			success : function(msg){
				//关闭模态框
				$("#update_modal").modal("hide");
				//回到当前页
				to_page(pageNum);
			},
			error : function(){
				
			}
		});
	});
	
	//修改歌手信息
	$("#singer_update_btn").click(function(){
		var singerName = $("#singerName").val();
		var settledTime = $("#settledTime").val();
		var singerIntroduction = $("#singerIntroduction").val();
		
		var id = $(this).attr("singer_edit_id");
		
		//发送ajax请求修改歌手信息
		$.ajax({
			url : "singerUpdateById",
			//data : "id=" + id + "&singerName=" + singerName + "&settledTime_=" + settledTime + "&introduction=" + singerIntroduction,
			type : "post",
			dataType : "json",
			data : {
				id : id,
				singerName : singerName,
				settledTime_ : settledTime,
				introduction : singerIntroduction,
			}, 
			success : function(data, status){
				//关闭模态框
				$("#singer_update_modal").modal("hide");
				//回到当前页
				to_singer_page(pageNum);
			},
			error : function(){
				
			}
		});
	});
	
	//歌手管理的添加歌手按钮，用于弹出模态框
	$("#singer_add").click(function(){
		//打开修改模态框,把返回值赋值给模态框控件
		$("#singer_add_modal").modal({
			backdrop : "static"
		});
	});
	
	//用户管理的添加管理员按钮，用于弹出模态框
	$("#superuser_add").click(function(){
		$("#superuser_add_modal").modal({
			backdrop : "static"
		});
	});
	
	
	//向数据库插入歌手信息
	$("#singer_add_btn").click(function(){
		
		var formData = new FormData($("#uploadSingerImgForm")[0]);
		
		var singerName = $("#add_singerName").val();
		var settledTime_ = $("#add_settledTime").val();
		var singerIntroduction = $("#add_introduction").val();
		alert("singName"+singerName + "add_settledTime" + settledTime_ + "singerIntroduction" + singerIntroduction);
		
		formData.append("add_singerName", singerName);
		formData.append("add_settledTime", settledTime_);
		formData.append("add_introduction", singerIntroduction);
		
		// 发送ajax请求插入歌手信息
		$.ajax({
			url : "addSinger",
			//日期格式问题
			//data : "singerName=" + singerName + "&settledTime_=" + settledTime_ + "&introduction=" + singerIntroduction,
			type : "post",
			data : formData,
			async : false,
			contentType : false,
			processData : false,
			success : function(msg){
				alert("成功添加");
				//关闭模态框
				$("#singer_add_modal").modal("hide");
				//回到当前页
				to_singer_page(pageNum);
			},
			error : function(){
			}
		}); 
	});
	
	// 歌曲评论审核通过按钮控件,将评论置为已处理状态
	$("#songcomment_pass_btn").click(function(){
		var id = $(this).attr("songcomment_pass_id");
		$.ajax({
			url : "updateReportStatusById",
			data : "id=" + id,
			type : "post",
			success : function(){
				//关闭模态框
				$("#song_comment_modal").modal("hide");
				to_song_comment_page(pageNum);
			},
			error : function(){
				
			}
		});
	})
	
	// 歌单评论审核通过按钮控件,将歌单评论置为已处理状态
	$("#playlist_comment_pass_btn").click(function(){
		var id = $(this).attr("playlist_comment_pass_id");
		$.ajax({
			url : "updatePlaylistCommentStatusById",
			data : "id=" + id,
			type : "post",
			success : function(){
				//关闭模态框
				$("#playlist_comment_modal").modal("hide");
				to_playlist_comment_page(pageNum);
			},
			error : function(){
				
			}
		});
	})
	
	$("#songcomment_del_btn").click(function(){
		var id = $(this).attr("songcomment_del_id");
		songComment_delete(id);
		//关闭模态框
		$("#song_comment_modal").modal("hide");
	});
	
	$("#playlist_comment_del_btn").click(function(){
		var id = $(this).attr("playlist_comment_del_id");
		playlistComment_delete(id);
		//关闭模态框
		$("#playlist_comment_modal").modal("hide");
	});
	
	//增加管理员按钮点击事件
	$("#superuser_add_btn").click(function(){
		var add_superUserName = $("#add_superUserName").val();
		var add_superUserPwd = $("#add_superUserPwd").val();
		var add_superUserSex = $("input[name='sex']:checked").val();
		alert("SEX=" + add_superUserSex);
		var add_birthday = $("#add_birthday").val(); 
		alert("Birthday=" + add_birthday);
		var add_email = $("#add_email").val();
		var add_region = $("#add_region").val();
		var add_personsignature = $("#add_personsignature").val();
		
		var formData = new FormData($("#uploadUserImgForm")[0]);
		
		formData.append("add_superUserName", add_superUserName);
		formData.append("add_superUserPwd", add_superUserPwd);
		formData.append("add_superUserSex", add_superUserSex);
		formData.append("add_birthday", add_birthday); 
		formData.append("add_email", add_email);
		formData.append("add_region", add_region);
		formData.append("add_personsignature", add_personsignature);
		
		$.ajax({
			url : 'superUserInsert',
			type : 'POST', 
			data : formData,
			async : false,
			contentType : false,
			processData : false,
			success : function(returndata){
				// 关闭模态框
				$("#superuser_add_modal").modal("hide");
				to_superuser_page(pageNum);
			},
			error : function(returndata){
				alert("error");
			}
		}); 
	});
	
	//全选与全不选功能
	$("#check_all").click(function(){
		//使用prop获取dom原生属性的值
		$(".check_item").prop("checked", $(this).prop("checked"));
	});
	
	//列表checkbox选满以后,全选按钮要变为选中状态,未选满全选按钮为未选中状态
	$(document).on("click", ".check_item", function(){
		var flag = $(".check_item:checked").length == $(".check_item").length;
		$("#check_all").prop("checked", flag);
	});
	
	//歌手管理页面全选与全不选功能
	$("#singer_check_all").click(function(){
		//使用prop获取dom原生属性的值
		$(".singer_check_item").prop("checked", $(this).prop("checked"));
	});
	
	//歌手页面列表checkbox选满以后,全选按钮要变为选中状态,未选满全选按钮为未选中状态
	$(document).on("click", ".singer_check_item", function(){
		var flag = $(".singer_check_item:checked").length == $(".singer_check_item").length;
		$("#singer_check_all").prop("checked", flag);
	});
	
	//歌曲评论页面全选与全不选功能
	$("#songcomment_check_all").click(function(){
		//使用prop获取dom原生属性的值
		$(".song_comment_check_item").prop("checked", $(this).prop("checked"));
	});
	
	//歌曲评论页面列表checkbox选满以后,全选按钮要变为选中状态,未选满全选按钮为未选中状态
	$(document).on("click", ".song_comment_check_item", function(){
		var flag = $(".song_comment_check_item:checked").length == $(".song_comment_check_item").length;
		$("#songcomment_check_all").prop("checked", flag);
	});
	
	//歌曲被举报评论页面全选与全不选功能
	$("#songcomment_report_check_all").click(function(){
		//使用prop获取dom原生属性的值
		$(".songcomment_report_check_item").prop("checked", $(this).prop("checked"));
	});
	
	//歌曲评论页面列表checkbox选满以后,全选按钮要变为选中状态,未选满全选按钮为未选中状态
	$(document).on("click", ".songcomment_report_check_item", function(){
		var flag = $(".songcomment_report_check_item:checked").length == $(".songcomment_report_check_item").length;
		$("#songcomment_report_check_all").prop("checked", flag);
	});
	
	//歌单评论页面全选与全不选功能
	$("#playlist_comment_check_all").click(function(){
		//使用prop获取dom原生属性的值
		$(".playlist_comment_check_item").prop("checked", $(this).prop("checked"));
	});
	
	//歌单评论页面列表checkbox选满以后,全选按钮要变为选中状态,未选满全选按钮为未选中状态
	$(document).on("click", ".playlist_comment_check_item", function(){
		var flag = $(".playlist_comment_check_item:checked").length == $(".playlist_comment_check_item").length;
		$("#playlist_comment_check_all").prop("checked", flag);
	});
	
	// 歌曲评论批量删除
	$("#comment_delete_all").click(function(){
		var ids = "";
		//循环遍历选中的checkbox取出其id
		$.each($(".song_comment_check_item:checked"), function(index, item){
			ids += $(this).val() + "-";
		});
		
		//去掉ids后面多余的那个杠
		ids = ids.substring(0, ids.length-1);
		alert(ids);
		if(confirm("你确定要批量删除吗？")){
			$.ajax({
				url : "deleteSongCommentByCheckbox",
				data : "ids=" + ids,
				type : "post",
				success : function(msg){
					to_song_comment_page(pageNum);
				},
				error : function(){
					
				}
			});
		}
	});
	
	// 歌单评论批量删除
	$("#playlist_comment_delete_all").click(function(){
		var ids = "";
		//循环遍历选中的checkbox取出其id
		$.each($(".playlist_comment_check_item:checked"), function(index, item){
			ids += $(this).val() + "-";
		});
		
		//去掉ids后面多余的那个杠
		ids = ids.substring(0, ids.length-1);
		alert(ids);
		if(confirm("你确定要批量删除吗？")){
			$.ajax({
				url : "deletePlaylistCommentByCheckbox",
				data : "ids=" + ids,
				type : "post",
				success : function(msg){
					to_playlist_comment_page(pageNum);
				},
				error : function(){
					
				}
			});
		}
	});
	
	// 被举报歌曲评论批量删除
	$("#songcomment_report_delete_all").click(function(){
		var ids = "";
		//循环遍历选中的checkbox取出其id
		$.each($(".songcomment_report_check_item:checked"), function(index, item){
			ids += $(this).val() + "-";
		});
		
		//去掉ids后面多余的那个杠
		ids = ids.substring(0, ids.length-1);
		alert(ids);
		if(confirm("你确定要批量删除吗？")){
			$.ajax({
				url : "deleteSongCommentByCheckbox",
				data : "ids=" + ids,
				type : "post",
				success : function(msg){
					to_songcomment_report_page(pageNum);
				},
				error : function(){
					
				}
			});
		}
	});
	
	
	
	//歌曲批量删除
	$("#song_delete_all").click(function(){
		var ids = "";
		//循环遍历选中的checkbox取出其id
		$.each($(".check_item:checked"), function(index, item){
			ids += $(this).val() + "-";
		});
		
		//去掉ids后面多余的那个杠
		ids = ids.substring(0, ids.length-1);
		if(confirm("你确定要批量删除吗？")){
			$.ajax({
				url : "deleteSongByCheckbox",
				data : "ids=" + ids,
				type : "post",
				success : function(msg){
					to_page(pageNum);
				},
				error : function(){
					
				}
			});
		}
	});
	
	//歌手批量删除
	$("#singer_delete_all").click(function(){
		var ids = "";
		//循环遍历选中的checkbox取出其id
		$.each($(".singer_check_item:checked"), function(index, item){
			ids += $(this).val() + "-";
		});
		
		//去掉ids后面多余的那个杠
		ids = ids.substring(0, ids.length-1);
		
		alert(ids);
		if(confirm("你确定要批量删除吗？")){
			$.ajax({
				url : "deleteSingerByCheckbox",
				data : "ids=" + ids,
				type : "post",
				success : function(msg){
					to_singer_page(pageNum);
				},
				error : function(){
					
				}
			});
		}
	});
	
	//删除歌曲
	function song_delete(id){
		$.ajax({
			url : "songDeleteById",
			data : "id=" + id,
			type : "post",
			success : function(msg){
				if(msg.code == 100)
					to_page(pageNum);
				else
					alert("删除失败");
			},
			error : function(){
				
			}
		});
	}
	
	//删除歌手
	function singer_delete(id){
		$.ajax({
			url : "singerDeleteById",
			data : "id=" + id,
			type : "post",
			success : function(msg){
				if(msg.code == 100)
					to_singer_page(pageNum);
				else
					alert("删除失败");
			},
			error : function(){
				
			}
		});
	}
	
	//删除歌曲评论
	function songComment_delete(id){
		$.ajax({
			url : "songCommentDeleteById",
			data : "id=" + id,
			type : "post",
			success : function(msg){
				to_song_comment_page(pageNum);
			},
			error : function(){
			}
		});
	}
	
	//删除歌单评论
	function playlistComment_delete(id){
		$.ajax({
			url : "playlistCommentDeleteById",
			data : "id=" + id,
			type : "post",
			success : function(msg){
				to_playlist_comment_page(pageNum);
			},
			error : function(){
			}
		});
	}
	
	// 管理员删除
	function superuser_delete(id){
		$.ajax({
			url : "superUserDeleteById",
			data : "id=" + id,
			type : "post",
			success : function(msg){
				to_superuser_page(pageNum);
			},
			error : function(){
			}
		});
	}
	
	$("#adminlogout").click(function(){
		$.ajax({
			url : "adminlogout",
			data : "",
			type : "post",
			success : function(msg){
				window.location.href="adminLogin.jsp";
			},
			error : function(){
			}
		});
	})
	
	$("#nav li").bind({
		click : function() {
			$("#sub_nav .active").removeClass("active");
			$("#tab_content .active").removeClass("active");
		}
	});
	
</script>

</html>
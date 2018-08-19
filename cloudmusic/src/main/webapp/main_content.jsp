<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>克劳德音乐</title>
    <link href="static/template/css/bootstrap.css" rel="stylesheet">
    <link href="static/template/css/responsive.bootstrap.css" rel="stylesheet">
    <link href="static/template/css/screen.css" rel="stylesheet">
    <link href="static/css/main.css" rel="stylesheet">
	<script src="static/mplayer-master/js/jquery.js"></script>
</head>
<body>
	<!-- 公共header部分 -->
	<%-- <%@ include file="WEB-INF/views/header.jsp" %> --%>
	
	<div class="container-fluid bigSlider">
		<div class="rs_mainslider">
			<ul class="rs_mainslider_items unstyled">
				<li class="rs_mainslider_items_active">
					<img src="static/img/s1.jpg" alt="slide1" class="rs_mainslider_items_image" id="img1">
					<!-- <div class="rs_mainslider_items_text container">
						<h2>Lorem ipsum dolor sit amet, consectetur</h2>
						<h2>Donec mattis, magna nec lacinia </h2>
					</div> -->
				</li>
				<li class="rs_mainslider_items_active">
					<img src="static/img/s2.jpg" alt="slide2" class="rs_mainslider_items_image" id="img2">
				</li>
				<li class="rs_mainslider_items_active">
					<img src="static/img/s3.jpg" alt="slide3" class="rs_mainslider_items_image" id="img3">
				</li>
				<!-- <li class="rs_mainslider_items_active">
					<img src="static/img/s4.jpg" alt="slide4" class="rs_mainslider_items_image" >
					
				</li> -->
			</ul>
		    <div class="rs_mainslider_left_container rs_center_vertical_container">
		    	<div class="rs_mainslider_left rs_center_vertical"></div>
		    </div>

		    <div class="rs_mainslider_right_container rs_center_vertical_container">
		    	<div class="rs_mainslider_right rs_center_vertical"></div>
		    </div>

		    <div class="rs_mainslider_dots_container rs_center_horizontal_container">
		      <ul class="rs_mainslider_dots rs_center_horizontal clearfix"></ul>
		    </div>
		</div><!-- / -->
	</div><!-- /.container-fluid [bigSlider] -->

	<div class="container" id="song_singer_container">
		<div class="row">
			<div class="span8" id="song_list_container">
				<!-- 热门推荐 -->
				<div class="hot title_box">
					<span class="colored color_dot">◎&nbsp;</span><span class="title"><a href="javascript:void(0)">热门推荐</a></span>
					<ul>
						<li><a href="showPlaylist?tagName=华语">华语</a></li>
						<li>|</li>
						<li><a href="showPlaylist?tagName=流行">流行</a></li>
						<li>|</li>
						<li><a href="showPlaylist?tagName=摇滚">摇滚</a></li>
						<li>|</li>
						<li><a href="showPlaylist?tagName=民谣">民谣</a></li>
						<li>|</li>
						<li><a href="showPlaylist?tagName=电子">电子</a></li>
					</ul>
					<span class="more"><a href="showPlaylist">更多 >></a></span>
				</div>
				<ul class="row portfolioItems" id="hot_list">
					<li class="span2">
						<figure>
							<a href="#" class="thumbnail">
								<img src="static/img/defaultPlaylist.jpg" alt="project">
							</a>
							<figcaption>
								<h3><a href="#">......</a></h3>
								<p>
									......
								</p>
							</figcaption>
						</figure>
					</li>
					<li class="span2">
						<figure>
							<a href="#" class="thumbnail">
								<img src="static/img/defaultPlaylist.jpg" alt="project">
							</a>
							<figcaption>
								<h3><a href="#">......</a></h3>
								<p>
									......
								</p>
							</figcaption>
						</figure>
					</li>
					<li class="span2">
						<figure>
							<a href="#" class="thumbnail">
								<img src="static/img/defaultPlaylist.jpg" alt="project">
							</a>
							<figcaption>
								<h3><a href="#">......</a></h3>
								<p>
									......
								</p>
							</figcaption>
						</figure>
					</li>
					<li class="span2">
						<figure>
							<a href="#" class="thumbnail">
								<img src="static/img/defaultPlaylist.jpg" alt="project">
							</a>
							<figcaption>
								<h3><a href="#">......</a></h3>
								<p>
									......
								</p>
							</figcaption>
						</figure>
					</li>
					<li class="span2">
						<figure>
							<a href="#" class="thumbnail">
								<img src="static/img/defaultPlaylist.jpg" alt="project">
							</a>
							<figcaption>
								<h3><a href="#">......</a></h3>
								<p>
									......
								</p>
							</figcaption>
						</figure>
					</li>
					<li class="span2">
						<figure>
							<a href="#" class="thumbnail">
								<img src="static/img/defaultPlaylist.jpg" alt="project">
							</a>
							<figcaption>
								<h3><a href="#">......</a></h3>
								<p>
									......
								</p>
							</figcaption>
						</figure>
					</li>
					<li class="span2">
						<figure>
							<a href="#" class="thumbnail">
								<img src="static/img/defaultPlaylist.jpg" alt="project">
							</a>
							<figcaption>
								<h3><a href="#">......</a></h3>
								<p>
									......
								</p>
							</figcaption>
						</figure>
					</li>
					<li class="span2">
						<figure>
							<a href="#" class="thumbnail">
								<img src="static/img/defaultPlaylist.jpg" alt="project">
							</a>
							<figcaption>
								<h3><a href="#">......</a></h3>
								<p>
									......
								</p>
							</figcaption>
						</figure>
					</li>
				</ul><!-- /.portfolioItems -->
				
				<!-- 个性推荐 -->
				<c:if test="${userInfo.id != null}">
					<div class="favourite title_box">
						<span class="colored color_dot">◎&nbsp;</span><span class="title"><a href="javascript:void(0)">个性推荐</a></span>
						<ul>
							
						</ul>
						<!-- <span class="more"><a href="javascript:void(0)">更多 >></a></span> -->
					</div>
					<ul class="row portfolioItems" id="favourite_list" data-user-id="${userInfo.id}">
						<li class="span2">
							<figure>
								<a href="showRecommendation" class="thumbnail">
									<img src="static/img/date.png" alt="project">
								</a>
								<figcaption>
									<h3><a href="showRecommendation">每日歌曲推荐</a></h3>
									<p>
										
									</p>
								</figcaption>
							</figure>
						</li>
						<li class="span2">
							<figure>
								<a href="#" class="thumbnail">
									<img src="static/img/defaultPlaylist.jpg" alt="project">
								</a>
								<figcaption>
									<h3><a href="#">......</a></h3>
									<p>
										......
									</p>
								</figcaption>
							</figure>
						</li>
						<li class="span2">
							<figure>
								<a href="#" class="thumbnail">
									<img src="static/img/defaultPlaylist.jpg" alt="project">
								</a>
								<figcaption>
									<h3><a href="#">......</a></h3>
									<p>
										......
									</p>
								</figcaption>
							</figure>
						</li>
						<li class="span2">
							<figure>
								<a href="#" class="thumbnail">
									<img src="static/img/defaultPlaylist.jpg" alt="project">
								</a>
								<figcaption>
									<h3><a href="#">......</a></h3>
									<p>
										......
									</p>
								</figcaption>
							</figure>
						</li>
					</ul><!-- /.portfolioItems -->
				</c:if>
				
				<!-- 榜单 -->
				<div class="billboard title_box">
					<span class="colored color_dot">◎&nbsp;</span><span class="title"><a href="javascript:void(0)">榜单</a></span>
					<ul>
						
					</ul>
					<!-- <span class="more"><a href="javascript:void(0)">更多 >></a></span> -->
				</div>
				<table id="billboard_table">
					<thead>
						<th>歌曲收听榜</th>
						<th>歌曲收藏榜</th>
						<th>热门歌手榜</th>
					</thead>
					<tbody>
						<tr>
							<td><span class="billboard_num">1</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">1</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">1</span><a href="" class="td_text"></a></td>
						</tr>
						<tr>
							<td><span class="billboard_num">2</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">2</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">2</span><a href="" class="td_text"></a></td>
						</tr>
						<tr>
							<td><span class="billboard_num">3</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">3</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">3</span><a href="" class="td_text"></a></td>
						</tr>
						<tr>
							<td><span class="billboard_num">4</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">4</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">4</span><a href="" class="td_text"></a></td>
						</tr>
						<tr>
							<td><span class="billboard_num">5</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">5</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">5</span><a href="" class="td_text"></a></td>
						</tr>
						<tr>
							<td><span class="billboard_num">6</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">6</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">6</span><a href="" class="td_text"></a></td>
						</tr>
						<tr>
							<td><span class="billboard_num">7</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">7</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">7</span><a href="" class="td_text"></a></td>
						</tr>
						<tr>
							<td><span class="billboard_num">8</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">8</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">8</span><a href="" class="td_text"></a></td>
						</tr>
						<tr>
							<td><span class="billboard_num">9</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">9</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">9</span><a href="" class="td_text"></a></td>
						</tr>
						<tr>
							<td><span class="billboard_num">10</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">10</span><a href="" class="td_text"></a></td>
							<td><span class="billboard_num">10</span><a href="" class="td_text"></a></td>
						</tr>
					</tbody>
				</table>
				
			</div><!-- /.span8 -->
			<c:if test="${userInfo != null}">
				<!-- 用户模块 -->
				<div class="span4">
					<div class="boxModel recentPostsSidebar">
						<div id="user_nav_content">用户名：${ userInfo.nickname }
						<c:if test="${userInfo.isSignin == false}">
							<a href="javascript:signIn(${userInfo.id})"><button id="signInButton">签到</button></a>
						</c:if>
						<c:if test="${userInfo.isSignin == true}">
							<button disabled>已签到</button>
						</c:if>
						</div>	
						<%-- <div>粉丝：${fans}</div>
						<div>关注：${concerned}</div> --%>
						<c:if test="${userInfo.sex == false}">
							<div>性别：男</div>
						</c:if>
						<c:if test="${userInfo.sex == true}">
							<div>性别：女</div>
						</c:if>
						<div>地区：${userInfo.region}</div>
						<%-- <div>关注：${concerned}</div> --%> 
						<div id="integral">积分：${userInfo.integral}</div>
					</div>			
				</div><!-- /.span4 -->
			</c:if>
			
			<div class="span4"  id="singer_list_container">
				<div class="boxModel recentPostsSidebar">
					<div class="hot_singer">
						<h1 class="colored">新晋歌手</h1><!-- <a href="javascript:void(0)" class="readMore">更多 >></a> --><hr>
					</div>						
					<ul class="tabPost unstyled">
					  	<!-- <li>
					  		<a href="javascript:void(0)">
						  		<figure>
						  			<img src="static/template/img/flickr/flickr3.jpg" alt="photo">
						  		</figure>
						  		<p>
					  				<b>啊杰</b><br>不好看不好听07308灵魂歌手
						  		</p>
					  		</a> 
					  	</li> -->
					</ul>	
				</div>			
			</div><!-- /.span4 -->
			
		</div><!-- /.row -->
	</div><!-- /.container [] -->

	<!-- 公共footer部分 -->
	<%-- <%@ include file="WEB-INF/views/footer.jsp" %> --%>		

	<!-- 公共player部分 -->
	<%-- <%@ include file="WEB-INF/views/player.jsp" %> --%>
	
<script src="static/template/js/jquery-ui.js"></script>
<script src="static/template/js/bootstrap.min.js"></script>
<script src="static/template/js/settingsbox.js"></script>
<script src="static/template/js/farbtastic/farbtastic.js"></script>
<script src="static/template/js/jquery.prettyPhoto.js"></script>
<script src="static/template/js/izotope.js"></script>
<script src="static/template/js/option.js"></script>
<script src="static/js/main.js"></script>
</body>
</html>
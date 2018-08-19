package com.cloudmusic.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cloudmusic.bean.User;

/**
 * 登录认证的拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

	/**
	 * Handler执行完成之后调用这个方法
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exc) throws Exception {

	}

	/**
	 * Handler执行之后，ModelAndView返回之前调用这个方法
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	/**
	 * Handler执行之前调用这个方法
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 获取请求的URL
		String url = request.getRequestURI();
		// URL:login.jsp是公开的;这个demo是除了login.jsp是可以公开访问的，其它的URL都进行拦截控制

		// 游客可以访问的url
		if (url.contains("static")) {
			return true;
		}

		if (url.contains("showMyMusicPage")) {
			return true;
		}

		if (url.contains("showCommentByPlaylistId")) {
			return true;
		}

		if (url.contains("findHotPlaylistTop")) {
			return true;
		}

		if (url.contains("findSongByPlayListId")) {
			return true;
		}

		if (url.contains("showPlaylist")) {
			return true;
		}

		if (url.contains("showPlaylistImg")) {
			return true;
		}

		if (url.contains("showPlaylistPage")) {
			return true;
		}

		if (url.contains("selectSingerTopNum")) {
			return true;
		}

		if (url.contains("findPlaylistById")) {
			return true;
		}

		if (url.contains("showSingerInfoPage")) {
			return true;
		}

		if (url.contains("findSongBySingerId")) {
			return true;
		}

		if (url.contains("findHotSongBySingerId")) {
			return true;
		}

		if (url.contains("showCommentBySongId")) {
			return true;
		}

		if (url.contains("replySongComment")) {
			return true;
		}

		if (url.contains("playMusic")) {
			return true;
		}

		if (url.contains("findLocalImg")) {
			return true;
		}

		if (url.contains("selectSongTopTenByPlayTimes")) {
			return true;
		}

		if (url.contains("selectSongTopTenByCollection")) {
			return true;
		}

		if (url.contains("selectSongDesByCollection")) {
			return true;
		}

		if (url.contains("showSongPage")) {
			return true;
		}

		if (url.contains("showSongLrc")) {
			return true;
		}

		if (url.contains("addOnePlaytimeOfSong")) {
			return true;
		}

		if (url.contains("searchAll")) {
			return true;
		}

		if (url.contains("login")) {
			return true;
		}

		if (url.contains("register")) {
			return true;
		}

		if (url.contains("forgotPsw")) {
			return true;
		}

		if (url.contains("sendCode")) {
			return true;
		}

		if (url.contains("delete")) {
			return true;
		}

		if (url.contains("seekExperts")) {
			return true;
		}

		if (url.contains("findUserNameById")) {
			return true;
		}

		if (url.contains("showPersonPage")) {
			return true;
		}

		if (url.contains("findRankSong")) {
			return true;
		}

		if (url.contains("showSingerImg")) {
			return true;
		}

		if (url.contains("selectNewestSinger")) {
			return true;
		}
		
		if(url.contains("adminLogin")){
			return true;
		}

		if(url.contains("validateEmail")){
			return true;
		}
		
		// 获取Session
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userInfo");

		if (user != null) {
			return true;
		}
		// 不符合条件的，跳转到主页面
		request.getRequestDispatcher("main.jsp").forward(request, response);

		return false;
	}

}

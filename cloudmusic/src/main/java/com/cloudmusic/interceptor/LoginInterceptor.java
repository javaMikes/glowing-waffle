package com.cloudmusic.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cloudmusic.bean.User;

/**
 * ��¼��֤��������
 */
public class LoginInterceptor implements HandlerInterceptor {

	/**
	 * Handlerִ�����֮������������
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exc) throws Exception {

	}

	/**
	 * Handlerִ��֮��ModelAndView����֮ǰ�����������
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	/**
	 * Handlerִ��֮ǰ�����������
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// ��ȡ�����URL
		String url = request.getRequestURI();
		// URL:login.jsp�ǹ�����;���demo�ǳ���login.jsp�ǿ��Թ������ʵģ�������URL���������ؿ���

		// �οͿ��Է��ʵ�url
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
		
		// ��ȡSession
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userInfo");

		if (user != null) {
			return true;
		}
		// �����������ģ���ת����ҳ��
		request.getRequestDispatcher("main.jsp").forward(request, response);

		return false;
	}

}

package com.cloudmusic.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OverallExceptionResolver implements HandlerExceptionResolver {
	/**
	 * ����ȫ���쳣�Ĺ��˺ʹ���
	 */
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		// handlerΪ��ǰ������������ִ�еĶ���
		String message = null;
		// �ж��Ƿ�Ϊϵͳ�Զ����쳣��
		if (ex instanceof CustomException) {
			message = ((CustomException) ex).getMessage();
		}

		else {
			message = "ϵͳ���������Ժ������ԣ�";
		}

		ModelAndView modelAndView = new ModelAndView();
		// ��ת����Ӧ�Ĵ���ҳ��
		modelAndView.addObject("message", message);
		modelAndView.setViewName("error");

		return modelAndView;
	}
}

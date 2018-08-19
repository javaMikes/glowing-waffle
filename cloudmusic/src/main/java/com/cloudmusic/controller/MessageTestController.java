package com.cloudmusic.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudmusic.bean.User;
import com.cloudmusic.service.IUserService;
import com.cloudmusic.util.Msg;

@Controller
public class MessageTestController {

	@Autowired
	IUserService userService;

	@RequestMapping("messageTest")
	public void messageTest(HttpServletResponse resp) throws ServletException, IOException {
		// ContentType 必须指定为 text/event-stream
		resp.setContentType("text/event-stream");
		// CharacterEncoding 必须指定为 UTF-8
		resp.setCharacterEncoding("UTF-8");
		PrintWriter pw = resp.getWriter();
		// for (int i = 0; i < 10; i++) {
		// 每次发送的消息必须以\n\n结束
		// System.out.println(System.currentTimeMillis());
		// pw.write("data: " + System.currentTimeMillis() + "\n\n");
		// }
		Msg msg = userService.selectUserById(72);
		User user = (User) msg.getMap().get("user");
		System.out.println(user.getIsSignin());
		pw.write("data: " + user.getIsSignin() + "\n\n");
		pw.close();
	}
}

package com.cloudmusic.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudmusic.util.Msg;

//判断该服务是否启动成功
@Controller
public class ConnectTest {

	@RequestMapping("connectTest")
	@ResponseBody
	public String Connect() {
		return "connect success!";
	}

	//eee
	
}

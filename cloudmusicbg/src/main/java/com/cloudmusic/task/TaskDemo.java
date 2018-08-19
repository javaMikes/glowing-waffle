package com.cloudmusic.task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cloudmusic.service.IUserService;
import com.cloudmusic.util.Msg;

@Component
public class TaskDemo {

	@Autowired
	IUserService userService;

	@Scheduled(cron = "0 0 0 * * ?") // 每天零点执行
	void doSomethingWith() {
		System.out.println("定时任务开始......");
		long begin = System.currentTimeMillis();

		// 执行数据库操作了哦...

		// (1)将所有用户的签到状态设置为false
		Msg msg = userService.allUserSignOut();

		// 执行完毕了哦..

		long end = System.currentTimeMillis();
		System.out.println("定时任务结束，共耗时：[" + (end - begin) / 1000 + "]秒");
	}

	@Scheduled(cron = "0 0/120 7-23 * * ?") // 每天7点到晚上23点，每小时执行一次
	void scheduledTest() {
		System.out.println("定时任务开始......");
		long begin = System.currentTimeMillis();

		// 执行数据库操作了哦...
		System.out.println("开始执行，检验错误并转化lrc文件");
		
		try {
			transferLrc();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("执行完毕");
		// 执行完毕了哦..

		long end = System.currentTimeMillis();
		System.out.println("定时任务结束，共耗时：[" + (end - begin) / 1000 + "]秒");
	}
	
	public void transferLrc() throws Exception
	{
		File f = new File("D:/upload/cloudmusic/lrc");
		File[] files = f.listFiles();
		
		int count = 0;
		for(File file : files)
		{
			System.out.println("----------------------------------------------");
			System.out.println("现在进行的是："+file.getName());
			boolean needTransfer = false;
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			
			String line = "";
			String lrcFileStr = "";
			
			while((line = br.readLine()) != null)
			{
				//读一行
				
				int num = line.indexOf("]");
				if(num == 10)
				{
					needTransfer = true;
					String str_1 = line.substring(0, 9);
					String str_2 = line.substring(num, line.length());
					String new_line = str_1 + str_2;
					lrcFileStr = lrcFileStr + new_line + "\n";
				}
				else
					lrcFileStr = lrcFileStr + line + "\n";
			}
			br.close();
			isr.close();
			fis.close();
			
			//开始覆盖写入文件
			FileOutputStream fos=new FileOutputStream(file);
	        OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
	        BufferedWriter  bw=new BufferedWriter(osw);
			
	        bw.write(lrcFileStr);
	        
	        bw.close();
	        osw.close();
	        fos.close();
	        if(needTransfer)
	        {
	        	count++;
	        	System.out.println("该文件需要转化");
	        }
	        System.out.println("----------------------------------------------");
		}
		System.out.println("============= 一共有"+files.length+"个文件，成功转化了"+count+"个文件");
	}
}

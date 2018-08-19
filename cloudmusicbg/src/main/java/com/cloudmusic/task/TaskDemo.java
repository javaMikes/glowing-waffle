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

	@Scheduled(cron = "0 0 0 * * ?") // ÿ�����ִ��
	void doSomethingWith() {
		System.out.println("��ʱ����ʼ......");
		long begin = System.currentTimeMillis();

		// ִ�����ݿ������Ŷ...

		// (1)�������û���ǩ��״̬����Ϊfalse
		Msg msg = userService.allUserSignOut();

		// ִ�������Ŷ..

		long end = System.currentTimeMillis();
		System.out.println("��ʱ�������������ʱ��[" + (end - begin) / 1000 + "]��");
	}

	@Scheduled(cron = "0 0/120 7-23 * * ?") // ÿ��7�㵽����23�㣬ÿСʱִ��һ��
	void scheduledTest() {
		System.out.println("��ʱ����ʼ......");
		long begin = System.currentTimeMillis();

		// ִ�����ݿ������Ŷ...
		System.out.println("��ʼִ�У��������ת��lrc�ļ�");
		
		try {
			transferLrc();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ִ�����");
		// ִ�������Ŷ..

		long end = System.currentTimeMillis();
		System.out.println("��ʱ�������������ʱ��[" + (end - begin) / 1000 + "]��");
	}
	
	public void transferLrc() throws Exception
	{
		File f = new File("D:/upload/cloudmusic/lrc");
		File[] files = f.listFiles();
		
		int count = 0;
		for(File file : files)
		{
			System.out.println("----------------------------------------------");
			System.out.println("���ڽ��е��ǣ�"+file.getName());
			boolean needTransfer = false;
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			
			String line = "";
			String lrcFileStr = "";
			
			while((line = br.readLine()) != null)
			{
				//��һ��
				
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
			
			//��ʼ����д���ļ�
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
	        	System.out.println("���ļ���Ҫת��");
	        }
	        System.out.println("----------------------------------------------");
		}
		System.out.println("============= һ����"+files.length+"���ļ����ɹ�ת����"+count+"���ļ�");
	}
}

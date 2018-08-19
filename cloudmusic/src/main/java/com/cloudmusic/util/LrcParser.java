package com.cloudmusic.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据正则表达式解析歌词
 * 
 * @author Mike
 *
 */
public class LrcParser {

	String lrcStr = new String();
	// private String currentContent = null;// 存放临时歌词

	/**
	 * 根据文件路径，读取文件，返回一个输入流
	 * 
	 * @param path
	 *            路径
	 * @return 输入流
	 * @throws FileNotFoundException
	 */
	private InputStream readLrcFile(String path) throws FileNotFoundException {
		File f = new File(path);
		InputStream ins = new FileInputStream(f);
		return ins;
	}

	public String parser(String path) throws Exception {
		InputStream in = readLrcFile(path);
		return parser(in);

	}

	public String parserWithoutTime(String path) throws Exception {
		InputStream in = readLrcFile(path);
		return parserWithoutTime(in);

	}

	/**
	 * 将输入流中的信息解析，返回一个LrcInfo对象(包括歌词的时间)
	 * 
	 * @param inputStream
	 *            输入流
	 * @return 解析好的LrcInfo对象
	 * @throws IOException
	 */
	public String parser(InputStream inputStream) throws IOException {
		// 三层包装
		InputStreamReader inr = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader reader = new BufferedReader(inr);
		// 一行一行的读，每读一行，解析一行
		String line = null;
		while ((line = reader.readLine()) != null) {
			parserLine(line);
		}
		return lrcStr;
	}

	/**
	 * 将输入流中的信息解析，返回一个LrcInfo对象(不包括歌词的时间)
	 * 
	 * @param inputStream
	 *            输入流
	 * @return 解析好的LrcInfo对象
	 * @throws IOException
	 */
	public String parserWithoutTime(InputStream inputStream) throws IOException {
		// 三层包装
		InputStreamReader inr = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader reader = new BufferedReader(inr);
		// 一行一行的读，每读一行，解析一行
		String line = null;
		while ((line = reader.readLine()) != null) {
			parserLineWithoutTime(line);
		}
		return lrcStr;
	}

	/**
	 * 利用正则表达式解析每行具体语句 并在解析完该语句后，将解析出来的信息设置在LrcInfo对象中(包括时间)
	 * 
	 * @param str
	 */
	private void parserLine(String str) {
		// 通过正则取得每句歌词信息
		if (!str.contains("[ti:") && !str.startsWith("[ar:") && !str.startsWith("[al:")) {
			// 设置正则规则
			String reg = "\\[(\\d{2}:\\d{2}\\.\\d{2})\\]";
			// 编译
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);

			// 如果存在匹配项，则执行以下操作
			while (matcher.find()) {
				lrcStr += str + "\\n";
			}
		}
	}

	/**
	 * 利用正则表达式解析每行具体语句 并在解析完该语句后，将解析出来的信息设置在LrcInfo对象中(不包括时间)
	 * 
	 * @param str
	 */
	private void parserLineWithoutTime(String str) {
		// 通过正则取得每句歌词信息
		if (!str.contains("[ti:") && !str.startsWith("[ar:") && !str.startsWith("[al:")) {
			// 设置正则规则
			String reg = "\\[(\\d{2}:\\d{2}\\.\\d{2})\\]";
			// 编译
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);

			// 如果存在匹配项，则执行以下操作
			while (matcher.find()) {

				// 得到时间点后的内容
				String[] content = pattern.split(str);

				// 输出数组内容
				for (int i = 0; i < content.length; i++) {
					if (i == content.length - 1) {
						// 将内容设置为当前内容
						lrcStr += content[i] + "\\n";
					}
				}

			}
		}
	}

	// 解析歌词测试
	public static void main(String[] args) {
		LrcParser lp = new LrcParser();
		try {
			// System.out.println(lp.parser("D://upload/cloudmusic/lrc/11.lrc"));
			System.out.println(lp.parser("D://upload/cloudmusic/lrc/网络歌手-北京东路的日子.lrc"));
		} catch (Exception e) {
			System.out.println("parser error");
			e.printStackTrace();
		}

	}
}

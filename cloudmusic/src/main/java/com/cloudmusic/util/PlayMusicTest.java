// package com.cloudmusic.util;
//
// import java.io.File;
// import java.io.IOException;
//
// import javax.media.CannotRealizeException;
// import javax.media.Manager;
// import javax.media.MediaLocator;
// import javax.media.NoPlayerException;
//
// public class PlayMusicTest {
// public static void main(String[] args) {
// File file = new File("D://upload/cloudmusic/song/ABC.mp3");
// playMusic(file);
// }
//
// public static void playMusic(File file) {
// try {
// javax.media.Player player = null;
// if (file.exists()) {
// MediaLocator locator = new MediaLocator("file:" + file.getAbsolutePath());
// System.out.println(file.getAbsolutePath());
// player = Manager.createRealizedPlayer(locator);
// player.prefetch();// Ԥ准备读取
// player.start();// 开始读取
// } else {
// System.out.println("没找到文件");
// }
// } catch (CannotRealizeException ex) {
// ex.printStackTrace();
// } catch (NoPlayerException ex) {
// ex.printStackTrace();
// } catch (IOException ex) {
// ex.printStackTrace();
// }
// }
// }

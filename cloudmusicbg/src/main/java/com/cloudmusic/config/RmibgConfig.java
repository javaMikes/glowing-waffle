package com.cloudmusic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import com.cloudmusic.adminservice.IAdminService;
import com.cloudmusic.service.IPlaylistCommentService;
import com.cloudmusic.service.IPlaylistService;
import com.cloudmusic.service.ISingerService;
import com.cloudmusic.service.ISongCommentService;
import com.cloudmusic.service.ISongService;
import com.cloudmusic.service.ITagService;
import com.cloudmusic.service.IUserService;

//rmi≈‰÷√
@Configuration
public class RmibgConfig {

	@Bean
	public RmiServiceExporter rmiExporter(IUserService userService) {
		RmiServiceExporter rmiExporter = new RmiServiceExporter();
		rmiExporter.setService(userService);
		rmiExporter.setServiceName("IUserService");
		rmiExporter.setServiceInterface(IUserService.class);
		return rmiExporter;
	}

	@Bean
	public RmiServiceExporter rmiExporter_playlist(IPlaylistService playlistService) {
		RmiServiceExporter rmiExporter_playList = new RmiServiceExporter();
		rmiExporter_playList.setService(playlistService);
		rmiExporter_playList.setServiceName("IPlaylistService");
		rmiExporter_playList.setServiceInterface(IPlaylistService.class);
		return rmiExporter_playList;
	}

	@Bean
	public RmiServiceExporter rmiExporter_song(ISongService songService) {
		RmiServiceExporter rmiExporter_song = new RmiServiceExporter();
		rmiExporter_song.setService(songService);
		rmiExporter_song.setServiceName("ISongService");
		rmiExporter_song.setServiceInterface(ISongService.class);
		return rmiExporter_song;
	}

	@Bean
	public RmiServiceExporter rmiExporter_tag(ITagService iTagService) {
		RmiServiceExporter rmiExporter_tag = new RmiServiceExporter();
		rmiExporter_tag.setService(iTagService);
		rmiExporter_tag.setServiceName("ITagService");
		rmiExporter_tag.setServiceInterface(ITagService.class);
		return rmiExporter_tag;
	}

	@Bean
	public RmiServiceExporter rmiExporter_playlistComment(IPlaylistCommentService iPlaylistComment) {
		RmiServiceExporter rmiExporter_playlistComment = new RmiServiceExporter();
		rmiExporter_playlistComment.setService(iPlaylistComment);
		rmiExporter_playlistComment.setServiceName("IPlaylistCommentService");
		rmiExporter_playlistComment.setServiceInterface(IPlaylistCommentService.class);
		return rmiExporter_playlistComment;
	}

	@Bean
	public RmiServiceExporter rmiExporter_singer(ISingerService iSingerService) {
		RmiServiceExporter rmiExporter_singer = new RmiServiceExporter();
		rmiExporter_singer.setService(iSingerService);
		rmiExporter_singer.setServiceName("ISingerService");
		rmiExporter_singer.setServiceInterface(ISingerService.class);
		return rmiExporter_singer;
	}
	
	@Bean
	public RmiServiceExporter rmiExporter_songComment(ISongCommentService isongCommentService) {
		RmiServiceExporter rmiExporter_songComment = new RmiServiceExporter();
		rmiExporter_songComment.setService(isongCommentService);
		rmiExporter_songComment.setServiceName("ISongCommentService");
		rmiExporter_songComment.setServiceInterface(ISongCommentService.class);
		return rmiExporter_songComment;
	}
	
	@Bean
	public RmiServiceExporter rmiExporter_admin(IAdminService iAdminService) {
		RmiServiceExporter rmiExporter_admin = new RmiServiceExporter();
		rmiExporter_admin.setService(iAdminService);
		rmiExporter_admin.setServiceName("IAdminService");
		rmiExporter_admin.setServiceInterface(IAdminService.class);
		return rmiExporter_admin;
	}
}

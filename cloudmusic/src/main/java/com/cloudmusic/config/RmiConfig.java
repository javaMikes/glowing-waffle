package com.cloudmusic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.cloudmusic.adminservice.IAdminService;
import com.cloudmusic.service.IPlaylistCommentService;
import com.cloudmusic.service.IPlaylistService;
import com.cloudmusic.service.ISingerService;
import com.cloudmusic.service.ISongCommentService;
import com.cloudmusic.service.ISongService;
import com.cloudmusic.service.ITagService;
import com.cloudmusic.service.IUserService;

@Configuration
public class RmiConfig {

	@Bean
	public RmiProxyFactoryBean spitterService() {
		RmiProxyFactoryBean rmiProxy = new RmiProxyFactoryBean();
		// rmiProxy.setServiceUrl("rmi://localhost/IUserService");
		// rmiProxy.setServiceUrl("rmi://172.27.33.24/IUserService");
		rmiProxy.setServiceUrl("rmi://localhost/IUserService");
		rmiProxy.setServiceInterface(IUserService.class);
		return rmiProxy;
	}

	@Bean
	public RmiProxyFactoryBean playlistService() {
		RmiProxyFactoryBean playlistService_rmiProxy = new RmiProxyFactoryBean();
		playlistService_rmiProxy.setServiceUrl("rmi://localhost/IPlaylistService");
		playlistService_rmiProxy.setServiceInterface(IPlaylistService.class);
		return playlistService_rmiProxy;
	}

	@Bean
	public RmiProxyFactoryBean songService() {
		RmiProxyFactoryBean songService_rmiProxy = new RmiProxyFactoryBean();
		songService_rmiProxy.setServiceUrl("rmi://localhost/ISongService");
		songService_rmiProxy.setServiceInterface(ISongService.class);
		return songService_rmiProxy;
	}

	@Bean
	public RmiProxyFactoryBean tagService() {
		RmiProxyFactoryBean tagService_rmiProxy = new RmiProxyFactoryBean();
		tagService_rmiProxy.setServiceUrl("rmi://localhost/ITagService");
		tagService_rmiProxy.setServiceInterface(ITagService.class);
		return tagService_rmiProxy;
	}

	@Bean
	public RmiProxyFactoryBean playlistCommentService() {
		RmiProxyFactoryBean playlistCommentService_rmiProxy = new RmiProxyFactoryBean();
		playlistCommentService_rmiProxy.setServiceUrl("rmi://localhost/IPlaylistCommentService");
		playlistCommentService_rmiProxy.setServiceInterface(IPlaylistCommentService.class);
		return playlistCommentService_rmiProxy;
	}

	@Bean
	public RmiProxyFactoryBean singerService() {
		RmiProxyFactoryBean singerService_rmiProxy = new RmiProxyFactoryBean();
		singerService_rmiProxy.setServiceUrl("rmi://localhost/ISingerService");
		singerService_rmiProxy.setServiceInterface(ISingerService.class);
		return singerService_rmiProxy;
	}

	@Bean
	public RmiProxyFactoryBean songCommentService() {
		RmiProxyFactoryBean songCommentService_rmiProxy = new RmiProxyFactoryBean();
		songCommentService_rmiProxy.setServiceUrl("rmi://localhost/ISongCommentService");
		songCommentService_rmiProxy.setServiceInterface(ISongCommentService.class);
		return songCommentService_rmiProxy;
	}

	@Bean
	public RmiProxyFactoryBean adminService() {
		RmiProxyFactoryBean adminService_rmiProxy = new RmiProxyFactoryBean();
		adminService_rmiProxy.setServiceUrl("rmi://localhost/IAdminService");
		adminService_rmiProxy.setServiceInterface(IAdminService.class);
		return adminService_rmiProxy;
	}
}

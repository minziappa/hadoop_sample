package io.sample.bean;

import io.sample.bean.model.HadoopAdminModel;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class ExtendUser extends User {

	private static final long serialVersionUID = 2086202300685821979L;

	HadoopAdminModel hadoopAdmin;

	public ExtendUser(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, HadoopAdminModel hadoopAdmin) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);

		// GameId
		this.hadoopAdmin = hadoopAdmin;

	}

	public HadoopAdminModel getHadoopAdmin() {
		return hadoopAdmin;
	}

	public void setHadoopAdmin(HadoopAdminModel hadoopAdmin) {
		this.hadoopAdmin = hadoopAdmin;
	}


}

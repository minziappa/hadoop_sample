package io.sample.service.impl;

import io.sample.bean.ExtendUser;
import io.sample.bean.model.HadoopAdminModel;
import io.sample.dao.SlaveDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private SqlSession slaveDao;
	@Autowired
	private Md5PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		ExtendUser user = null;
		HadoopAdminModel hadoopAdmin = null;

		try {
			logger.info("UserDetailsService >> userId >> " + userName);

			// Get a user information form DB.
			Map<String, Object> mapSelect = new HashMap<String, Object>();
			mapSelect.put("userName", userName);

			try {
				hadoopAdmin = slaveDao.getMapper(SlaveDao.class).selectAdmin(mapSelect);
			} catch (Exception e) {
				logger.error("Exception error", e);
			}

			if(hadoopAdmin == null) {
				throw new UsernameNotFoundException( userName + " is not found." );
			}

	        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        // For java1.6
	        switch(Integer.valueOf(hadoopAdmin.getAdminStatusFlag())) {
	            case 1:
	                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
	                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	            break;
	            default:
	                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	            break;
	        }

	        boolean enabled = true;
	        boolean accountNonExpired = true;
	        boolean credentialsNonExpired = true;
	        boolean accountNonLocked = true;

	        user = new ExtendUser(hadoopAdmin.getAdminName(), passwordEncoder.encodePassword("test", null), enabled, 
	        		accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, hadoopAdmin);

		} catch (Exception e) {
			logger.error("Select error, userName={}, userStatus={}", hadoopAdmin.getAdminName(), hadoopAdmin.getAdminStatusFlag());
			logger.error("Exception >> ", e);
		}

		return user;
	}

}

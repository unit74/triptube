package com.ssafy.triptube.configures.security.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.ssafy.triptube.configures.security.interfaces.CustomUserDetails;

public class SecurityUtil {
	public static String getLoginUsername() {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return user.getUsername();
	}

	public static Long getLoginUserId() {
		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		return user.getUserId();
	}
}

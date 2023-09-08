package com.aduilio.bdd;

import com.aduilio.bdd.model.User;

public class TestUtil {

	public static final String USER1_NAME = "User 1";

	public static User getUser1() {
		return User	.builder()
					.name(USER1_NAME)
					.email("user1@email.com")
					.password("pass")
					.role("USER")
					.enabled(true)
					.build();
	}
}

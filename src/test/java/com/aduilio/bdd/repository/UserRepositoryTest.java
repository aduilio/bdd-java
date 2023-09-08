package com.aduilio.bdd.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.aduilio.bdd.TestUtil;
import com.aduilio.bdd.model.User;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

	@Autowired
	private UserRepository repository;

	@BeforeEach
	public void setup() {
		repository.saveAndFlush(TestUtil.getUser1());
	}

	@Test
	public void getUserByUsername_shouldReturnTheUser() {
		final User user = repository.getUserByUsername(TestUtil.USER1_NAME);
		Assertions.assertEquals(TestUtil.getUser1(), user);
	}
}

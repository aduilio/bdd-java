package com.aduilio.bdd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "users")
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@NotNull
	@NotBlank
	@Column(name = "username")
	@EqualsAndHashCode.Include
	private final String name;

	@NotNull
	@NotBlank
	@Column(name = "password")
	private String password;

	@NotNull
	@NotBlank
	@Email
	private String email;

	// Spring Security
	private String role;
	private boolean enabled;

	public void activate() {
		this.enabled = true;
	}

	public void deactivate() {
		this.enabled = false;
	}
}
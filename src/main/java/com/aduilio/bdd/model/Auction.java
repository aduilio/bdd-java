package com.aduilio.bdd.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Auction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	private final String nome;

	@NotNull
	@DecimalMin(value = "0.1")
	private BigDecimal initialValue;

	@OneToOne
	@JoinColumn(nullable = false)
	private User usuario;

	@NotNull
	private final LocalDate openingDate = LocalDate.now();

	@OneToMany(mappedBy = "auction")
	private final List<Bid> bids = new ArrayList<>();

}
package com.aduilio.bdd.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.aduilio.bdd.model.Auction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewAuctionDto {

	private static DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private Long id;

	@NotNull
	@NotBlank
	@Size(min = 3, message = "minimo 3 caracteres")
	private String name;

	@NotNull(message = "deve ser um valor maior de 0.1")
	@DecimalMin(value = "0.1", message = "must be greater than 0.1")
	private BigDecimal initialValue;

	@NotNull(message = "must use the format dd/MM/yyyy")
	@Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "must use the format dd/MM/yyyy")
	private String openingDate;

	public Auction toAuction() {
		return Auction.builder()
				.name(name)
				.initialValue(initialValue)
				.openingDate(LocalDate.parse(openingDate, ofPattern))
				.build();
	}
}
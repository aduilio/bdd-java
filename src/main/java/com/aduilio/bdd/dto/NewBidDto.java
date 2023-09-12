package com.aduilio.bdd.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.aduilio.bdd.model.Auction;
import com.aduilio.bdd.model.Bid;
import com.aduilio.bdd.model.User;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewBidDto {

	@NotNull
	@DecimalMin(value = "0.1")
	private BigDecimal value;

	private Long auctionId;

	public Bid toBid(final User user, final Auction auction) {
		return Bid.builder()
				.bidUser(user)
				.bidValue(value)
				.bidDate(LocalDate.now())
				.auction(auction)
				.build();
	}

}
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

	private static final Integer MAX_BID_BY_USER = 5;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	private final String name;

	@NotNull
	@DecimalMin(value = "0.1")
	private BigDecimal initialValue;

	@OneToOne
	@JoinColumn(nullable = false)
	private User user;

	@NotNull
	@Builder.Default
	private LocalDate openingDate = LocalDate.now();

	@OneToMany(mappedBy = "auction")
	private final List<Bid> bids = new ArrayList<>();

	public boolean newBid(final Bid bid) {

		if (!hasBids() || isValidBid(bid)) {
			bids.add(bid);
			return true;
		}
		return false;
	}

	private boolean hasBids() {
		return !bids.isEmpty();
	}

	private boolean isValidBid(final Bid bid) {
		return isHigherValue(bid) && !isSameUser(bid) && isUserBidAcceptable(user);
	}

	private boolean isHigherValue(final Bid bid) {
		final var lastBid = getLastBid();

		return bid.getBidValue()
				.compareTo(lastBid.getBidValue()) > 0;
	}

	private Bid getLastBid() {
		return bids.get(bids.size() - 1);
	}

	private boolean isSameUser(final Bid bid) {
		final var lastBidUser = getLastBid().getBidUser();
		return !lastBidUser.equals(bid.getBidUser());
	}

	private boolean isUserBidAcceptable(final User user) {
		final int bids = bidsPerUser(user);
		return bids < MAX_BID_BY_USER;
	}

	private int bidsPerUser(final User user) {
		int total = 0;
		for (final Bid bid : bids) {
			if (bid.getBidUser()
					.equals(user))
				total++;
		}

		return total;
	}

}
package com.aduilio.bdd.service;

import org.springframework.stereotype.Service;

import com.aduilio.bdd.dto.NewBidDto;
import com.aduilio.bdd.model.Auction;
import com.aduilio.bdd.model.Bid;
import com.aduilio.bdd.model.User;
import com.aduilio.bdd.repository.AuctionRepository;
import com.aduilio.bdd.repository.BidRepository;
import com.aduilio.bdd.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidService {

	public final BidRepository bidRepository;
	public final UserRepository userRepository;
	public final AuctionRepository auctionRepository;

	public boolean newBid(final NewBidDto newBid, final Auction auction, final String userName) {

		final User user = userRepository.getUserByUsername(userName);
		final Bid bid = newBid.toBid(user, auction);

		if (auction.newBid(bid)) {
			bidRepository.save(bid);
			return true;
		}

		return false;
	}

	public Auction getAuction(final Long auctionId) {
		return auctionRepository.findById(auctionId)
				.orElseThrow(() -> new RuntimeException("Auction not found"));
	}
}
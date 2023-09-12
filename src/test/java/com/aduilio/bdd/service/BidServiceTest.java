package com.aduilio.bdd.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aduilio.bdd.dto.NewBidDto;
import com.aduilio.bdd.model.Auction;
import com.aduilio.bdd.model.Bid;
import com.aduilio.bdd.model.User;
import com.aduilio.bdd.repository.AuctionRepository;
import com.aduilio.bdd.repository.BidRepository;
import com.aduilio.bdd.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class BidServiceTest {

	private static final String USER_NAME = "User Name";

	@Mock
	public BidRepository bidRepository;

	@Mock
	public UserRepository userRepository;

	@Mock
	public AuctionRepository auctionRepository;

	@InjectMocks
	private BidService service;

	private NewBidDto newBidDto;
	private Auction auction;
	private User user;
	private Bid bid;

	@BeforeEach
	public void setUp() {
		final long auctionId = 1l;

		newBidDto = NewBidDto.builder()
				.auctionId(auctionId)
				.value(BigDecimal.TEN)
				.build();

		auction = Auction.builder()
				.name("Product 1")
				.id(auctionId)
				.build();

		user = User.builder()
				.name(USER_NAME)
				.build();

		bid = newBidDto.toBid(user, auction);
	}

	@Test
	public void newBidShouldAcceptBid() {
		Mockito.when(bidRepository.save(bid))
				.thenReturn(bid);
		Mockito.when(userRepository.getUserByUsername(USER_NAME))
				.thenReturn(user);

		final boolean acceptBid = service.newBid(newBidDto, auction, USER_NAME);

		Assertions.assertTrue(acceptBid);
	}

	@Test
	public void newBidShouldNotAcceptDuplicated() {
		Mockito.when(bidRepository.save(bid))
				.thenReturn(bid);
		Mockito.when(userRepository.getUserByUsername(USER_NAME))
				.thenReturn(user);

		service.newBid(newBidDto, auction, USER_NAME);

		final boolean acceptBid = service.newBid(newBidDto, auction, USER_NAME);

		Assertions.assertFalse(acceptBid);
	}
}
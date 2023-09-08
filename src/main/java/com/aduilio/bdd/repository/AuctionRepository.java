package com.aduilio.bdd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aduilio.bdd.model.Auction;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

}

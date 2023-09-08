package com.aduilio.bdd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aduilio.bdd.model.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {

}

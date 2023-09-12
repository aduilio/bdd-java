package com.aduilio.bdd.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aduilio.bdd.dto.NewBidDto;
import com.aduilio.bdd.model.Auction;
import com.aduilio.bdd.service.BidService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/bids")
public class BidController {

	@Autowired
	private BidService bidService;

	@PostMapping
	public ModelAndView newBid(@Valid @ModelAttribute("bid") final NewBidDto bidDto, final Errors erros,
			final Principal principal, final RedirectAttributes redirectAttributes) {

		final Auction auction = bidService.getAuction(bidDto.getAuctionId());

		if (erros.hasErrors()) {
			final ModelAndView mv = new ModelAndView("/auction/show");
			mv.addObject("bid", bidDto);
			mv.addObject("auction", auction);
			return mv;
		}

		if (bidService.newBid(bidDto, auction, principal.getName())) {
			redirectAttributes.addFlashAttribute("message", "New bid saved!");
		} else {
			redirectAttributes.addFlashAttribute("error", "New bid invalid!");
		}

		final String redirectURL = "redirect:/auctions/" + bidDto.getAuctionId();
		return new ModelAndView(redirectURL);
	}

}
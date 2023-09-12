package com.aduilio.bdd.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aduilio.bdd.dto.NewAuctionDto;
import com.aduilio.bdd.dto.NewBidDto;
import com.aduilio.bdd.model.Auction;
import com.aduilio.bdd.model.User;
import com.aduilio.bdd.repository.AuctionRepository;
import com.aduilio.bdd.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auctions")
@RequiredArgsConstructor
public class AuctionController {

	private final AuctionRepository auctionRepository;
	private final UserRepository userRepository;

	@GetMapping
	public ModelAndView index(final Principal principal) {
		final ModelAndView mv = new ModelAndView("auction/index");
		mv.addObject("auctions", auctionRepository.findAll());
		mv.addObject("user", principal);
		return mv;
	}

	@GetMapping("/{id}/form")
	public ModelAndView form(@PathVariable("id") final Long id, final Principal principal) {

		final Auction auction = auctionRepository.findById(id)
				.get();

		final ModelAndView mv = new ModelAndView("auction/form");
		mv.addObject("username", principal.getName());
		mv.addObject("auction", auction);
		return mv;
	}

	@PostMapping
	public ModelAndView saveOrUpdate(@Valid @ModelAttribute("auction") final NewAuctionDto auctionForm,
			final Errors errors, final RedirectAttributes attr, final Principal principal) {

		if (errors.hasErrors()) {
			final ModelAndView mv = new ModelAndView("/auction/form");
			mv.addObject("auction", auctionForm);
			mv.addObject("username", principal.getName());
			return mv;
		}

		final User user = userRepository.getUserByUsername(principal.getName());
		final Auction auction = auctionForm.toAuction();
		auction.setUser(user);

		auctionRepository.save(auction);

		attr.addFlashAttribute("message", "Auction saved");

		return new ModelAndView("redirect:/auctions");
	}

	@GetMapping("/new")
	public ModelAndView newAuction(final Principal principal) {
		final ModelAndView mv = new ModelAndView("auction/form");
		mv.addObject("username", principal.getName());
		mv.addObject("auction", NewAuctionDto.builder()
				.build());
		return mv;
	}

	@GetMapping("/{id}")
	public ModelAndView show(@PathVariable final Long id, final Principal principal) {
		final ModelAndView mv = new ModelAndView("auction/show");
		mv.addObject("username", principal.getName());
		mv.addObject("auction", auctionRepository.findById(id)
				.get());
		mv.addObject("bid", NewBidDto.builder()
				.build());
		return mv;
	}
}

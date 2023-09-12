package com.aduilio.bdd.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class AuctionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void loadAuctionsPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/auctions"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Auctions")));
	}
}
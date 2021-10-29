package com.currencyfair.mkttrade;

import com.currencyfair.mkttrade.data.models.CurrencyTrade;
import com.currencyfair.mkttrade.data.payloads.request.CurrencyTradeRequest;
import com.currencyfair.mkttrade.data.payloads.response.MessageResponse;
import com.currencyfair.mkttrade.service.CurrencyTradeService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MktTradeProcessorApplicationTests {

    @Autowired
    CurrencyTradeService currencyTradeService;

    @Autowired
    ApplicationContext context;

    private CurrencyTradeRequest getTradeRequest() throws ParseException {
        CurrencyTradeRequest newTrade = new CurrencyTradeRequest();
        newTrade.setUserId(123456L);
        newTrade.setCurrencyFrom("EUR");
        newTrade.setCurrencyFrom("GBP");
        newTrade.setAmountSell(new BigDecimal("1000"));
        newTrade.setAmountBuy(new BigDecimal("800"));
        newTrade.setRate(new BigDecimal("0.7471"));
        Date inDate = new SimpleDateFormat("dd-MMM-yy HH:mm:ss").parse("24-JAN-18 10:27:44");
        newTrade.setTimePlaced(inDate);
        return newTrade;
    }

    @Test
    @Order(1)
    public void testProcessCurrencyTrade() throws Exception {
        // Given
        MessageResponse response;
        Long id = 1L;    // Unique record ID
        CurrencyTradeRequest newTrade = this.getTradeRequest();
        currencyTradeService.createCurrencyTrade(newTrade);
        // When
        response = currencyTradeService.processCurrencyTrade(id);
        // Then
        assertThat(response.getMessage(), is("Trade with id "+ id +" updated successfully"));
    }

    @Test
    @Order(2)
    public void testCreateCurrencyTrade() throws Exception {
        // Given
        MessageResponse response;
        CurrencyTradeRequest newTrade = this.getTradeRequest();
        // When
        response = currencyTradeService.createCurrencyTrade(newTrade);
        // Then
        assertThat(response.getMessage(), is("Trade for user "+newTrade.getUserId().toString()+" inserted successfully"));
    }

    @Test
    @Order(3)
    public void testDeleteUserCurrencyTrade() throws Exception {
        // Given
        MessageResponse response;
        Long userId = 123456L;    // Unique record ID
        CurrencyTradeRequest newTrade = this.getTradeRequest();
        currencyTradeService.createCurrencyTrade(newTrade);
        // When
        response = currencyTradeService.deleteUserCurrencyTrades(userId);
        // Then
        assertThat(response.getMessage(), is("Trade for user "+ userId +" deleted successfully"));
    }

}

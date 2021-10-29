package com.currencyfair.mkttrade.service;

import com.currencyfair.mkttrade.data.models.CurrencyTrade;
import com.currencyfair.mkttrade.data.payloads.request.CurrencyTradeRequest;
import com.currencyfair.mkttrade.data.payloads.response.MessageResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CurrencyTradeService {
    // Consumer
    MessageResponse createCurrencyTrade(CurrencyTradeRequest ccyTradesRequest);
    List<CurrencyTrade> getAllCurrencyTrades();
    List<CurrencyTrade> getAllUserCurrencyTrades(Long userId);
    CurrencyTrade getCurrencyTradeById(Long id);
    // Processor
    MessageResponse deleteUserCurrencyTrades(Long userId);
    // Update: amountBuy = amountSell * rate
    MessageResponse processCurrencyTrade(Long id);
}

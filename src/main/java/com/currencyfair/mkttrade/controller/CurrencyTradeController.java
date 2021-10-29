package com.currencyfair.mkttrade.controller;

import com.currencyfair.mkttrade.data.models.CurrencyTrade;
import com.currencyfair.mkttrade.data.payloads.request.CurrencyTradeRequest;
import com.currencyfair.mkttrade.data.payloads.response.MessageResponse;
import com.currencyfair.mkttrade.exception.ResourceNotFoundException;
import com.currencyfair.mkttrade.service.CurrencyTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currencytrade")
public class CurrencyTradeController {

    private final CurrencyTradeService currencyTradeService;

    public CurrencyTradeController(CurrencyTradeService currencyTradeService) {
        this.currencyTradeService = currencyTradeService;
    }

    @PostMapping("/add")
    public ResponseEntity<MessageResponse> addCurrencyTrade(@RequestBody CurrencyTradeRequest request) {
        MessageResponse newCurrencyTrade;
        try {
            newCurrencyTrade = currencyTradeService.createCurrencyTrade(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(newCurrencyTrade, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CurrencyTrade>> getAllTrades() {
        try {
            List<CurrencyTrade> trades = currencyTradeService.getAllCurrencyTrades();
            if (trades == null || trades.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(trades, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{userId}")
    public ResponseEntity<List<CurrencyTrade>> getCurrencyTradesByUserId(@PathVariable("userId") Long userId) {
        try {
            List<CurrencyTrade> trades = currencyTradeService.getAllUserCurrencyTrades(userId);
            if (trades == null || trades.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(trades, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<HttpStatus> deleteUserTrades(@PathVariable("userId") Long userId) {
        try {
            currencyTradeService.deleteUserCurrencyTrades(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/process/{id}")
    public ResponseEntity<HttpStatus> processTrades(@PathVariable("id") Long id) {
        try {
            currencyTradeService.processCurrencyTrade(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.currencyfair.mkttrade.data.payloads.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CurrencyTradeRequest {
    @NotBlank
    @NotNull
    private Long userId;
    @NotBlank
    @NotNull
    private String currencyFrom;
    @NotBlank
    @NotNull
    private String currencyTo;
    private BigDecimal amountSell;
    private BigDecimal amountBuy;
    @NotBlank
    @NotNull
    private BigDecimal rate;
    private String originatingCountry;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-MMM-yy HH:mm:ss", timezone = "GMT+08:00")
    private Date timePlaced;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-MMM-yy HH:mm:ss", timezone = "GMT+08:00")
    private Date updatedAt;
}

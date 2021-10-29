package com.currencyfair.mkttrade.data.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Table(name="currency_trade")
@Entity
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CurrencyTrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_id")
    private Long userId;
    @Column(name="currency_from")
    private String currencyFrom;
    @Column(name="currency_to")
    private String currencyTo;
    @Column(name="amount_sell")
    private BigDecimal amountSell;
    @Column(name="amount_buy")
    private BigDecimal amountBuy;
    private BigDecimal rate;
    @Column(name="originating_country")
    private String originatingCountry;
    @Column(name="time_placed")
    private Date timePlaced;
    @UpdateTimestamp
    @Column(name="updated_at")
    private Date updatedAt;

    // Formatted Date columns for display on web
    private static final String pattern = "dd-MMM-yy HH:mm:ss";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    public String getTimePlacedStr() {
        if (this.timePlaced != null) {
            return simpleDateFormat.format(this.timePlaced);
        } else {
            return "";
        }
    }

    public String getUpdatedAtStr() {
        if (this.updatedAt != null) {
            return simpleDateFormat.format(this.updatedAt);
        } else {
            return "";
        }
    }
}

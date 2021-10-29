package com.currencyfair.mkttrade.data.repository;

import com.currencyfair.mkttrade.data.models.CurrencyTrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyTradeRepository extends JpaRepository<CurrencyTrade, Long> {

}

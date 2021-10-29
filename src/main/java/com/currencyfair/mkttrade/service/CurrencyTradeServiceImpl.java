package com.currencyfair.mkttrade.service;

import com.currencyfair.mkttrade.data.models.CurrencyTrade;
import com.currencyfair.mkttrade.data.payloads.request.CurrencyTradeRequest;
import com.currencyfair.mkttrade.data.payloads.response.MessageResponse;
import com.currencyfair.mkttrade.data.repository.CurrencyTradeRepository;
import com.currencyfair.mkttrade.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CurrencyTradeServiceImpl implements CurrencyTradeService {

    final
    CurrencyTradeRepository ccyTradeRepository;

    @PersistenceContext
    private EntityManager em;

    public CurrencyTradeServiceImpl(CurrencyTradeRepository ccyTradeRepository) {
        this.ccyTradeRepository = ccyTradeRepository;
    }

    @Override
    @Transactional
    public MessageResponse createCurrencyTrade(CurrencyTradeRequest ccyTradesRequest) {
        CurrencyTrade newTrade = new CurrencyTrade();
        newTrade.setUserId(ccyTradesRequest.getUserId());
        newTrade.setCurrencyFrom(ccyTradesRequest.getCurrencyFrom());
        newTrade.setCurrencyTo(ccyTradesRequest.getCurrencyTo());
        newTrade.setAmountBuy(ccyTradesRequest.getAmountBuy());
        newTrade.setAmountSell(ccyTradesRequest.getAmountSell());
        newTrade.setRate(ccyTradesRequest.getRate());
        newTrade.setTimePlaced(ccyTradesRequest.getTimePlaced());
        newTrade.setUpdatedAt(ccyTradesRequest.getUpdatedAt());
        newTrade.setOriginatingCountry(ccyTradesRequest.getOriginatingCountry());

        ccyTradeRepository.save(newTrade);
        return new MessageResponse("Trade for user "+newTrade.getUserId()+" inserted successfully");
    }

    @Override
    public List<CurrencyTrade> getAllCurrencyTrades() {
        return ccyTradeRepository.findAll();
    }

    @Override
    public List<CurrencyTrade> getAllUserCurrencyTrades(Long userId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<CurrencyTrade> criteriaQuery = criteriaBuilder.createQuery(CurrencyTrade.class);
        Root<CurrencyTrade> tradeRoot = criteriaQuery.from(CurrencyTrade.class);
        criteriaQuery.select(tradeRoot);
        Predicate predicate = criteriaBuilder.equal(tradeRoot.get("userId"), userId);
        criteriaQuery.where(predicate);
        TypedQuery<CurrencyTrade> typedQuery = em.createQuery(criteriaQuery);
        List<CurrencyTrade> currencyTradeList = typedQuery.getResultList();

        return currencyTradeList;
    }

    @Override
    public CurrencyTrade getCurrencyTradeById(Long id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<CurrencyTrade> criteriaQuery = criteriaBuilder.createQuery(CurrencyTrade.class);
        Root<CurrencyTrade> tradeRoot = criteriaQuery.from(CurrencyTrade.class);
        criteriaQuery.select(tradeRoot);
        Predicate predicate = criteriaBuilder.equal(tradeRoot.get("id"), id);
        criteriaQuery.where(predicate);
        TypedQuery<CurrencyTrade> typedQuery = em.createQuery(criteriaQuery);
        CurrencyTrade currencyTrade = typedQuery.getSingleResult();

        return currencyTrade;
    }

    @Override
    @Transactional
    public MessageResponse deleteUserCurrencyTrades(Long userId) throws ResourceNotFoundException {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaDelete<CurrencyTrade> criteriaDelete = criteriaBuilder.createCriteriaDelete(CurrencyTrade.class);
        Root<CurrencyTrade> tradeRoot = criteriaDelete.from(CurrencyTrade.class);
        Predicate predicate = criteriaBuilder.equal(tradeRoot.get("userId"), userId);
        criteriaDelete.where(predicate);

        int rowsDeleted = em.createQuery(criteriaDelete).executeUpdate();
        if (rowsDeleted > 0) {
            return new MessageResponse("Trade for user "+userId+" deleted successfully");
        } else {
            throw new ResourceNotFoundException("CurrencyTrade", "userId", userId.toString());
        }
    }

    @Override
    @Transactional
    public MessageResponse processCurrencyTrade(Long id) throws ResourceNotFoundException {
        // Get trade by id
        CurrencyTrade trade = this.getCurrencyTradeById(id);
        BigDecimal amountSell = (trade.getAmountSell() != null) ? trade.getAmountSell() : BigDecimal.ZERO;
        BigDecimal rate = (trade.getRate() != null) ? trade.getRate() : BigDecimal.ZERO;
        BigDecimal newAmountBuy = amountSell.multiply(rate);

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaUpdate<CurrencyTrade> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(CurrencyTrade.class);
        Root<CurrencyTrade> tradeRoot = criteriaUpdate.from(CurrencyTrade.class);
        // Define update
        criteriaUpdate.set(tradeRoot.get("amountBuy"), newAmountBuy);
        Predicate predicate = criteriaBuilder.equal(tradeRoot.get("id"), id);
        criteriaUpdate.where(predicate);

        int rowsUpdated = em.createQuery(criteriaUpdate).executeUpdate();
        if (rowsUpdated > 0) {
            return new MessageResponse("Trade with id "+id+" updated successfully");
        } else {
            throw new ResourceNotFoundException("CurrencyTrade", "id", id.toString());
        }
    }

}

package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Bank;
import com.himedia.rentmon_back.entity.Card;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.BankRepository;
import com.himedia.rentmon_back.repository.CardRepository;
import com.himedia.rentmon_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BankService {
    private final BankRepository br;
    private final UserRepository ur;
    private final CardRepository cr;

    public List<Bank> getBankList() {
        return  br.findAll();
    }

    public void saveCard(Card card, String userid) {
        User user = ur.findByUserid(userid);
        String[] cardMonthyear = card.getMonthyear().split("/");
        card.setMonthyear(cardMonthyear[0]+cardMonthyear[1]);
        Card setCard = null;
        if(user.getCseq() == null ) {
            setCard = cr.save(card);
            System.out.println(setCard);
        }else{
            Optional<Card> optionCard = cr.findById(card.getCseq());
            if(optionCard.isPresent()) {
                setCard = optionCard.get();
                setCard.setBank(card.getBank());
                setCard.setCvc(card.getCvc());
                setCard.setMonthyear(card.getMonthyear());
                setCard.setCardnum(card.getCardnum());
                cr.save(setCard);
            }
        }
        user.setCseq(setCard);
        ur.save(user);
    }
}

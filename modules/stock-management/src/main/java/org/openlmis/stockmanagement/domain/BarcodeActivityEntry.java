package org.openlmis.stockmanagement.domain;

import org.openlmis.core.domain.BaseModel;
import org.openlmis.stockmanagement.repository.StockCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Martha Shaka on 3/30/17.
 */
public class BarcodeActivityEntry extends BaseModel {
    private Integer stockCardEntryId=0;

    @Autowired
    StockCardRepository repository;



    public BarcodeActivityEntry(Integer stockCardEntryId){
        this.stockCardEntryId = stockCardEntryId;
    }

    @Transactional
    public void addBarcodeActivityEntry(BarcodeActivityEntry entry) {
        StockCard card = getOrCreateStockCard(entry.getStockCard().getFacility().getId(), entry.getStockCard().getProduct().getCode());//entry.getStockCard();

        card.addToTotalQuantityOnHand(entry.getQuantity());
        repository.persistStockCardEntry(entry);
        repository.updateStockCard(card);

        LotOnHand lotOnHand = entry.getLotOnHand();
        if (null != lotOnHand) {
            lotOnHand.addToQuantityOnHand(entry.getQuantity());
            lotRepository.saveLotOnHand(lotOnHand);
        }
    }

}

package org.openlmis.stockmanagement.domain;

import org.openlmis.core.domain.BaseModel;
import org.openlmis.stockmanagement.repository.BarcodeActivityRepository;
import org.openlmis.stockmanagement.repository.StockCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Martha Shaka on 3/30/17.
 */
public class BarcodeActivityEntry extends BaseModel {
    private Integer stockCardEntryId=0;
    public BarcodeActivityEntry(Integer stockCardEntryId){
        this.stockCardEntryId = stockCardEntryId;
    }


}

package org.openlmis.stockmanagement.repository;

import lombok.NoArgsConstructor;
import org.openlmis.stockmanagement.domain.BarcodeActivityEntry;
import org.openlmis.stockmanagement.domain.StockCard;
import org.openlmis.stockmanagement.repository.mapper.BarcodeActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Created by Martha Shaka on 3/30/17.
 */

@Component
@NoArgsConstructor
public class BarcodeActivityRepository {
    @Autowired
    BarcodeActivityMapper mapper;

    public void updateBarcodeActivityEntry(BarcodeActivityEntry entry) {
        Objects.requireNonNull(entry);
        mapper.insert(entry);
    }
}


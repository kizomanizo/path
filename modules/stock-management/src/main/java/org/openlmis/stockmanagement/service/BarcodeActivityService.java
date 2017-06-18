/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.stockmanagement.service;

import lombok.NoArgsConstructor;
import org.openlmis.core.repository.ProductRepository;
import org.openlmis.core.service.FacilityService;
import org.openlmis.stockmanagement.domain.*;
import org.openlmis.stockmanagement.repository.BarcodeActivityRepository;
import org.openlmis.stockmanagement.repository.LotRepository;
import org.openlmis.stockmanagement.repository.StockCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


/**
 * created by Martha Shaka
 * 3/30/2017
 * Exposes the services for handling barcode scanning activities.
 */

@Service
@NoArgsConstructor
public class BarcodeActivityService {

  @Autowired
  BarcodeActivityRepository repository;

  BarcodeActivityService(BarcodeActivityRepository repository) {
    this.repository = Objects.requireNonNull(repository);
  }

  @Transactional
  public void addBarcodeActivityEntry(BarcodeActivityEntry entry) {
    repository.updateBarcodeActivityEntry(entry);
  }

  @Transactional
  public void addStockCardEntries(List<BarcodeActivityEntry> entries) {
    for (BarcodeActivityEntry entry : entries) {
      addBarcodeActivityEntry(entry);
    }
  }
}

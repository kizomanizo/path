package org.openlmis.stockmanagement.controller;

/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.openlmis.core.domain.Product;
import org.openlmis.core.domain.Right;
import org.openlmis.core.domain.StockAdjustmentReason;
import org.openlmis.core.repository.FacilityRepository;
import org.openlmis.core.repository.StockAdjustmentReasonRepository;
import org.openlmis.core.service.*;
import org.openlmis.core.web.OpenLmisResponse;
import org.openlmis.core.web.controller.BaseController;
import org.openlmis.stockmanagement.domain.*;
import org.openlmis.stockmanagement.dto.StockEvent;
import org.openlmis.stockmanagement.dto.StockEventType;
import org.openlmis.stockmanagement.repository.LotRepository;
import org.openlmis.stockmanagement.repository.StockCardRepository;
import org.openlmis.stockmanagement.service.BarcodeActivityService;
import org.openlmis.stockmanagement.service.StockCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.google.common.collect.Iterables.any;
import static org.openlmis.core.utils.RightUtil.with;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * This controller provides GET, POST, and DELETE endpoints related to barcode activities.
 */

@Controller
@RequestMapping(value = "/api/barcode/")
@NoArgsConstructor
public class BarcodeActivityController extends BaseController
{
    private static Logger logger = Logger.getLogger(BarcodeActivityController.class);
    /**
     * Added by Martha Shaka 30th March 2017
     * barcodeActivityService used to manage barcode activity services.
     */
    @Autowired
    private BarcodeActivityService barcodeActivityService;

    BarcodeActivityController(MessageService messageService, BarcodeActivityService barcodeActivityService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.barcodeActivityService = Objects.requireNonNull(barcodeActivityService);
    }



    /**
     * Added by Martha Shaka 26th March 2017
     * For storing tracked barcode activities
     * @param facilityId
     * @param stockCardIds
     * @param request
     * @return
     */
    @RequestMapping(value = "barcodeActivities", method = POST, headers = ACCEPT_JSON)
    @Transactional
    public ResponseEntity processBarcodeActivities(
                                       @RequestBody(required = true) List<Integer> stockCardIds,
                                       HttpServletRequest request) {

        // verify we have something to do and facility exists
        if(null == stockCardIds || 0 >= stockCardIds.size()) return OpenLmisResponse.success(messageService.message("success.stock.event.none"));

        // convert events to entries
        Long userId = loggedInUserId(request);
        List<BarcodeActivityEntry> entries = new ArrayList<>();
        for(int stockCardId : stockCardIds) {
            logger.debug("Processing barcode activity with eventId: " + stockCardId);

            //TODO CHECK IF THERE IS A NEED FOR CHECKING IF THE STOCK CARD ID EXISTS.

            BarcodeActivityEntry entry = new BarcodeActivityEntry(stockCardId);
            entry.setCreatedBy(userId);
            entry.setModifiedBy(userId);
            entries.add(entry);
        }

        barcodeActivityService.addStockCardEntries(entries);
        return OpenLmisResponse.success("barcodeActivitySavedSuccessfully");

    }

}
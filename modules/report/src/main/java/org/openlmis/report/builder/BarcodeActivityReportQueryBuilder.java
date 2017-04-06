/*
 * Electronic Logistics Management Information System (eLMIS) is a supply chain management system for health commodities in a developing country setting.
 *
 * Copyright (C) 2015 Clinton Health Access Initiative (CHAI). This program was produced for the U.S. Agency for International Development (USAID). It was prepared under the USAID | DELIVER PROJECT, Task Order 4.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * created by Martha Shaka
 * 31/03/2017
 */
package org.openlmis.report.builder;

import org.openlmis.report.model.params.BarcodeActivityReportParam;

import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;

public class BarcodeActivityReportQueryBuilder {

    public static String SelectBarcodeActivityByGeographicalSql(Map params) {

        BarcodeActivityReportParam filter = (BarcodeActivityReportParam) params.get("filterCriteria");
        Long userId = (Long) params.get("userId");
        String sql = "";
        String facilityFilter=null;
        BEGIN();
        String aggregateOpen = "SELECT  "+
                "SUM(agg.credit) AS credit " +
                ",SUM(agg.debit) AS debit " +
                ",SUM(agg.adjustment) AS adjustment " +
                "FROM(";


        String aggregateClose = " ) AS agg ";

        if (filter.getProgramId() != 0) {
            if (!filter.getFacilityLevel().isEmpty()) {
                String facilityLevel = filter.getFacilityLevel();

                if(facilityLevel.equalsIgnoreCase("cvs") || facilityLevel.equalsIgnoreCase("rvs") || facilityLevel.equalsIgnoreCase("dvs")) {
                    facilityFilter = " WHERE facilities.id IN(" + filter.getFacilityIds() + ") AND facility_types.code ='" + facilityLevel + "' ";
                }
                else{
                    facilityFilter = " WHERE facilities.id IN("+filter.getFacilityIds()+") AND facility_types.code NOT IN ('cvs','rvs','dvs')";
                }

            } else {
                 facilityFilter = " WHERE facilities.id IN("+filter.getFacilityIds()+")";
            }
            String barcodeActivity = "select T2.Id, T2.credit, T2.debit, T2.adjustment, facilities.name, facility_types.name AS type_name from (\n" +
                    "select * from crosstab($$ select id,type,count(type) from \n" +
                    "            (select facilities.id, type from barcode_activity\n" +
                    "\t         inner join stock_card_entries on barcode_activity.activitydetailsId = stock_card_entries.id\n" +
                    "\t         inner join stock_cards on stock_card_entries.stockcardid = stock_cards.id\n" +
                    "\t         inner join facilities on stock_cards.facilityid = facilities.id\n" +
                    "\t         inner join facility_types on facilities.typeid = facility_types.id\n" +
                    "\t         inner join geographic_zones on facilities.geographiczoneid = geographic_zones.id\n" +facilityFilter+")\n" +
                    "             AS T1 group by T1.id,T1.type order by T1.id,T1.type $$)\n" +
                    "\n" +
                    "AS final_result(Id integer, adjustment bigint, credit bigint, debit bigint) \n" +
                    "\n" +
                    ") AS T2\n" +
                    "inner join facilities on T2.Id = facilities.id\n" +
                    "inner join facility_types on facilities.typeid = facility_types.id";
            if (filter.getAggregate()) {
                sql = aggregateOpen + barcodeActivity  + aggregateClose;
            } else {
                sql = barcodeActivity ;
            }

        } else {
            sql="";
        }
        return sql;
    }



}

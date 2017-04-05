/*
 * Electronic Logistics Management Information System (eLMIS) is a supply chain management system for health commodities in a developing country setting.
 *
 * Copyright (C) 2015  John Snow, Inc (JSI). This program was produced for the U.S. Agency for International Development (USAID). It was prepared under the USAID | DELIVER PROJECT, Task Order 4.
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
package org.openlmis.report.model.report;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openlmis.report.model.ResultRow;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class BarcodeActivityReport implements ResultRow {

    @Column(name = "credit")
    private  int credit;

    @Column(name = "debit")
    private int debit;

    @Column(name = "adjustment")
    private int adjustment;

    @Column(name = "Id")
    private int Id;

    @Column(name = "name")
    private String name;

    @Column(name = "type_name")
    private String type_name;





}

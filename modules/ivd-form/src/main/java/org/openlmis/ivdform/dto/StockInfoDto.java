package org.openlmis.ivdform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockInfoDto {

  private String facilityCode;

  private String programCode;

  private String productCode;

  private String status;

  private Long periodId;

  private Long stockStatus;

  private Long daysOutOfStock;

  private Long amc;

}

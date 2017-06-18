package org.openlmis.stockmanagement.repository.mapper;

import org.apache.ibatis.annotations.*;
import org.openlmis.core.domain.Facility;
import org.openlmis.core.domain.Product;
import org.openlmis.stockmanagement.domain.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * created by Martha Shaka
 * on 3/30/2017
 */
@Repository
public interface BarcodeActivityMapper {

  @Select("SELECT *" +
      " FROM stock_cards" +
      " WHERE facilityid = #{facilityId}" +
      "   AND productid = (SELECT id FROM products WHERE code = #{productCode})")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "facility", column = "facilityId", javaType = Facility.class,
          one = @One(select = "org.openlmis.core.repository.mapper.FacilityMapper.getById")),
      @Result(property = "product", column = "productId", javaType = Product.class,
          one = @One(select = "org.openlmis.core.repository.mapper.ProductMapper.getById")),
      @Result(property = "keyValues", column = "id", javaType = List.class,
          one = @One(select = "getStockCardKeyValues")),
      @Result(property = "entries", column = "id", javaType = List.class,
          many = @Many(select = "getEntries")),
      @Result(property = "lotsOnHand", column = "id", javaType = List.class,
      many = @Many(select = "getLotsOnHand"))
  })
  StockCard getByFacilityAndProduct(@Param("facilityId") Long facilityId, @Param("productCode") String productCode);

  @Select("SELECT *" +
      " FROM stock_cards" +
      " WHERE facilityid = #{facilityId}" +
      "   AND id = #{id}")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "facility", column = "facilityId", javaType = Facility.class,
          one = @One(select = "org.openlmis.core.repository.mapper.FacilityMapper.getById")),
      @Result(property = "product", column = "productId", javaType = Product.class,
          one = @One(select = "org.openlmis.core.repository.mapper.ProductMapper.getById")),
      @Result(property = "keyValues", column = "id", javaType = List.class,
          one = @One(select = "getStockCardKeyValues")),
      @Result(property = "entries", column = "id", javaType = List.class,
          many = @Many(select = "getEntries")),
      @Result(property = "lotsOnHand", column = "id", javaType = List.class,
          many = @Many(select = "getLotsOnHand"))
  })
  StockCard getByFacilityAndId(@Param("facilityId") Long facilityId, @Param("id") Long id);

  @Select("SELECT *" +
      " FROM stock_cards" +
          " WHERE facilityid = #{facilityId} order by productId")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "product", column = "productId", javaType = Product.class,
          one = @One(select = "org.openlmis.core.repository.mapper.ProductMapper.getById")),
      @Result(property = "keyValues", column = "id", javaType = List.class,
          one = @One(select = "getStockCardKeyValues")),
      @Result(property = "entries", column = "id", javaType = List.class,
          many = @Many(select = "getEntries")),
      @Result(property = "lotsOnHand", column = "id", javaType = List.class,
          many = @Many(select = "getLotsOnHand"))
  })
  List<StockCard> getAllByFacility(@Param("facilityId") Long facilityId);

  @Select("SELECT scekv.keycolumn" +
          ", scekv.valuecolumn" +
          ", scekv.modifieddate AS synceddate" +
          " FROM stock_card_entries sce" +
          "   JOIN stock_card_entry_key_values scekv ON scekv.stockcardentryid = sce.id" +
          " WHERE stockcardid = #{stockCardId}")
  List<StockCardEntryKV> getStockCardKeyValues(@Param("stockCardId") Long stockCardId);

  @Select("SELECT *" +
      " FROM stock_card_entries" +
      " WHERE stockcardid = #{stockCardId}" +
      " ORDER BY createddate DESC")
  @Results({
      @Result(property = "keyValues", column = "id", javaType = List.class,
          many = @Many(select = "getEntryKeyValues"))
  })
  List<StockCardEntry> getEntries(@Param("stockCardId") Long stockCardId);

  @Select("SELECT keycolumn" +
          ", valuecolumn" +
          ", modifieddate AS synceddate" +
          " FROM stock_card_entry_key_values" +
          " WHERE stockcardentryid = #{stockCardEntryId}")
  List<StockCardEntryKV> getEntryKeyValues(@Param("stockCardEntryId") Long stockCardEntryId);

  @Select("SELECT loh.*" +
          " FROM lots_on_hand loh" +
          " WHERE loh.stockcardid = #{stockCardId}")
  @Results({@Result(property = "lotId", column = "lotId"),
      @Result(
          property = "keyValues", column = "id", javaType = List.class,
          one = @One(select = "getLotOnHandKeyValues")),
      @Result(
          property = "lot", column = "lotId", javaType = Lot.class,
          one = @One(select = "org.openlmis.stockmanagement.repository.mapper.LotMapper.getById"))
  })
  List<LotOnHand> getLotsOnHand(@Param("stockCardId") Long stockCardId);

  @Select("SELECT scekv.keycolumn" +
          ", scekv.valuecolumn" +
          ", scekv.modifieddate AS synceddate" +
          " FROM stock_card_entries sce" +
          "   JOIN stock_card_entry_key_values scekv ON scekv.stockcardentryid = sce.id" +
          " WHERE lotonhandid = #{lotOnHandId}")
  List<StockCardEntryKV> getLotOnHandKeyValues(@Param("lotOnHandId") Long lotOnHandId);

  @Select("SELECT p.*" +
          " FROM products p" +
          "   JOIN stock_cards sc ON sc.productid = p.id" +
          " WHERE sc.id = #{stockCardId}")
  Product getProductByStockCardId(Long stockCardId);

  @Insert("INSERT INTO barcode_activity (activitydetailsId" +
      ", createdBy" +
      ", createdDate" +
      ", modifiedBy" +
      ", modifiedDate" +
      ") VALUES ( #{stockCardEntryId}" +
      ", #{createdBy}" +
      ", NOW()" +
      ", #{modifiedBy}" +
      ", NOW() )" )
  @Options(useGeneratedKeys = true)
  int insert(BarcodeActivityEntry entry);


  @Select("SELECT modifieddate FROM stock_cards " +
      "WHERE facilityid = #{facilityId} " +
      "ORDER BY modifieddate DESC LIMIT 1"
  )
  Date getLastUpdatedTimeforStockDataByFacility(Long facilityId);
}

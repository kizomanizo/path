DROP TABLE IF EXISTS product;
CREATE TABLE product (
  id SERIAL PRIMARY KEY,
  code CHAR(20) NOT NULL UNIQUE ,
  alternate_item_code CHAR(20),
  manufacturer  VARCHAR(100),
  manufacturer_code CHAR(30),
  manufacturer_barcode CHAR(20),
  moh_barcode CHAR(20),
  gtin CHAR(20),
  type VARCHAR(100),
  primary_name VARCHAR(150) NOT NULL,
  full_name VARCHAR(250),
  generic_name VARCHAR(100),
  alternate_name VARCHAR(100),
  description VARCHAR(250),
  strength VARCHAR(14),
  form INTEGER,
  dosage_unit INTEGER,
  dispensing_unit VARCHAR(20),
  doses_per_dispensing_unit SMALLINT,
  doses_per_day SMALLINT,
  pack_size SMALLINT NOT NULL,
  alternate_pack_size SMALLINT,
  store_refrigerated  BOOLEAN,
  store_room_temperature BOOLEAN,
  hazardous BOOLEAN,
  flammable BOOLEAN,
  controlled_substance BOOLEAN,
  light_sensitive BOOLEAN,
  approved_by_who BOOLEAN,
  contraceptive_cyp NUMERIC(8,4),
  pack_length NUMERIC(8,4),
  pack_width NUMERIC(8,4),
  pack_height NUMERIC(8,4),
  pack_weight NUMERIC(8,4),
  packs_per_carton SMALLINT,
  carton_length NUMERIC(8,4),
  carton_width NUMERIC(8,4),
  carton_height NUMERIC(8,4),
  cartons_per_pallet SMALLINT,
  expected_shelf_life  SMALLINT,
  special_storage_instructions TEXT,
  special_transport_instructions TEXT,
  active BOOLEAN NOT NULL,
  full_supply BOOLEAN NOT NULL,
  tracer BOOLEAN NOT NULL,
  round_to_zero BOOLEAN NOT NULL,
  archived BOOLEAN,
  pack_rounding_threshold SMALLINT NOT NULL,
  last_modified_date TIMESTAMP,
  last_modified_by INTEGER);
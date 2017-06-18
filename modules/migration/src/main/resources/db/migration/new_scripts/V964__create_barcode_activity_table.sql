DROP TABLE IF EXISTS barcode_activity;
CREATE TABLE barcode_activity
(
  id serial PRIMARY KEY,
  activitydetailsid integer,
  createdby integer,
  createddate timestamp with time zone DEFAULT now(),
  modifiedby integer,
  modifieddate timestamp with time zone DEFAULT now(),
  CONSTRAINT barcode_activityId_fkey FOREIGN KEY (activitydetailsid)
      REFERENCES processing_periods (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)

ALTER TABLE barcode_activity
  OWNER TO postgres;


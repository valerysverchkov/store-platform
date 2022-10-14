CREATE SEQUENCE products_id_seq;

CREATE TABLE product(
    id BIGINT NOT NULL DEFAULT nextval('products_id_seq'),
    catalog_id UUID NOT NULL,
    quantity INTEGER NOT NULL,
    status VARCHAR(255) NOT NULL,
    PRIMARY KEY (catalog_id, status, id)
) PARTITION BY HASH (catalog_id);

CREATE TABLE product_part_1 PARTITION OF product FOR VALUES WITH (modulus 3, remainder 0) PARTITION BY LIST(status);
CREATE TABLE product_part_1_active PARTITION OF product_part_1 FOR VALUES IN ('AVAILABLE', 'OUT_OF_STOCK');
CREATE TABLE product_part_1_inactive PARTITION OF product_part_1 FOR VALUES IN ('DISABLED');

CREATE TABLE product_part_2 PARTITION OF product FOR VALUES WITH (modulus 3, remainder 1) PARTITION BY LIST(status);
CREATE TABLE product_part_2_active PARTITION OF product_part_2 FOR VALUES IN ('AVAILABLE', 'OUT_OF_STOCK');
CREATE TABLE product_part_2_inactive PARTITION OF product_part_2 FOR VALUES IN ('DISABLED');

CREATE TABLE product_part_3 PARTITION OF product FOR VALUES WITH (modulus 3, remainder 2) PARTITION BY LIST(status);
CREATE TABLE product_part_3_active PARTITION OF product_part_3 FOR VALUES IN ('AVAILABLE', 'OUT_OF_STOCK');
CREATE TABLE product_part_3_inactive PARTITION OF product_part_3 FOR VALUES IN ('DISABLED');
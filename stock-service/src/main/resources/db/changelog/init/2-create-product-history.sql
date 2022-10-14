CREATE SEQUENCE products_history_id_seq;

CREATE TABLE product_history(
    id BIGINT NOT NULL DEFAULT nextval('products_history_id_seq'),
    catalog_id UUID NOT NULL,
    user_id UUID NOT NULL,
    quantity INTEGER NOT NULL,
    status VARCHAR(255) NOT NULL,
    PRIMARY KEY (catalog_id, user_id, id)
) PARTITION BY HASH (catalog_id);

CREATE TABLE product_part_1 PARTITION OF product_history FOR VALUES WITH (modulus 3, remainder 0);
CREATE TABLE product_part_2 PARTITION OF product_history FOR VALUES WITH (modulus 3, remainder 1);
CREATE TABLE product_part_3 PARTITION OF product_history FOR VALUES WITH (modulus 3, remainder 2);
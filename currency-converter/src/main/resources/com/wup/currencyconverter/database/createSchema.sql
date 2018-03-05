CREATE TABLE currency (
    id      NUMBER(3)   NOT NULL,
    name    VARCHAR2(3) NOT NULL
)
/

ALTER TABLE currency
    ADD (
        CONSTRAINT currency_pk PRIMARY KEY (id)
    )
/

CREATE UNIQUE INDEX currency_unique_name ON currency (NAME)
/

CREATE SEQUENCE currency_sequence
/

CREATE OR REPLACE TRIGGER currency_on_insert
  BEFORE INSERT ON currency
  FOR EACH ROW
  BEGIN
    IF :new.id is null
    THEN
        SELECT currency_sequence.nextval
        INTO :new.id
        FROM dual;
  END IF;

END;
/

CREATE TABLE exchange_rate (
  id NUMBER(*,0),
  currency_from NUMBER(*,0),
  currency_to NUMBER(*,0),
  buy_rate NUMBER,
  sell_rate NUMBER
)
/

CREATE SEQUENCE exchange_rate_sequence
/

CREATE OR REPLACE TRIGGER exchange_rate_on_insert
  BEFORE INSERT ON exchange_rate
  FOR EACH ROW
BEGIN
  SELECT exchange_rate_sequence.nextval
  INTO :new.id
  FROM dual;
END;
/

CREATE UNIQUE INDEX exchange_rate_unique_curr ON exchange_rate (currency_from, currency_to)
/

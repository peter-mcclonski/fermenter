drop table VALIDATION_EXAMPLE if exists

create table VALIDATION_EXAMPLE (VALIDATION_EXAMPLE_ID varchar(255) not null, BIG_DECIMAL_EXAMPLE decimal(19,2), BIG_DECIMAL_SCALE_LARGE decimal(19,10), BIG_DECIMAL_SCALE_LARGE_INTEGER decimal(19,2), BIG_DECIMAL_SCALE decimal(19,3), CHAR_EXAMPLE varchar(255), DATE_EXAMPLE timestamp, INTEGER_EXAMPLE integer, LONG_EXAMPLE bigint, REGEX_EXAMPLE varchar(255), REGEX_ZIPCODE_EXAMPLE varchar(255), REQUIRED_FIELD varchar(255) not null, STRING_EXAMPLE varchar(255), primary key (VALIDATION_EXAMPLE_ID))

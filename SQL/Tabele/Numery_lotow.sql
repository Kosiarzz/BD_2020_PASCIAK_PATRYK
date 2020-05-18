--------------------------------------------------------
--  File created - poniedzia³ek-maja-18-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table NUMERY_LOTOW
--------------------------------------------------------

  CREATE TABLE "C##PATRYK"."NUMERY_LOTOW" 
   (	"ID_NR_LOTOW" VARCHAR2(20 BYTE), 
	"NUMER_LOTU" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into C##PATRYK.NUMERY_LOTOW
SET DEFINE OFF;
Insert into C##PATRYK.NUMERY_LOTOW (ID_NR_LOTOW,NUMER_LOTU) values ('1','18');
--------------------------------------------------------
--  Constraints for Table NUMERY_LOTOW
--------------------------------------------------------

  ALTER TABLE "C##PATRYK"."NUMERY_LOTOW" MODIFY ("ID_NR_LOTOW" NOT NULL ENABLE);

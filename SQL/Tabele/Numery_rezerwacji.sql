--------------------------------------------------------
--  File created - œroda-maja-06-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table NUMERY_REZERWACJI
--------------------------------------------------------

  CREATE TABLE "C##PATRYK"."NUMERY_REZERWACJI" 
   (	"ID_NR_REZERWACJI" NUMBER(*,0), 
	"NUMER_REZERWACJI" NUMBER(*,0), 
	"STATUS" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into C##PATRYK.NUMERY_REZERWACJI
SET DEFINE OFF;
Insert into C##PATRYK.NUMERY_REZERWACJI (ID_NR_REZERWACJI,NUMER_REZERWACJI,STATUS) values ('1','1','1');
--------------------------------------------------------
--  Constraints for Table NUMERY_REZERWACJI
--------------------------------------------------------

  ALTER TABLE "C##PATRYK"."NUMERY_REZERWACJI" MODIFY ("ID_NR_REZERWACJI" NOT NULL ENABLE);

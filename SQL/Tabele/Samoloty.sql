--------------------------------------------------------
--  File created - œroda-maja-06-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table SAMOLOTY
--------------------------------------------------------

  CREATE TABLE "C##PATRYK"."SAMOLOTY" 
   (	"ID_SAMOLOT" NUMBER(*,0), 
	"MODEL" VARCHAR2(64 BYTE), 
	"ILOSC_MIEJSC" NUMBER(*,0), 
	"RODZAJ" VARCHAR2(10 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into C##PATRYK.SAMOLOTY
SET DEFINE OFF;
Insert into C##PATRYK.SAMOLOTY (ID_SAMOLOT,MODEL,ILOSC_MIEJSC,RODZAJ) values ('1','AIR','24','P');
--------------------------------------------------------
--  Constraints for Table SAMOLOTY
--------------------------------------------------------

  ALTER TABLE "C##PATRYK"."SAMOLOTY" MODIFY ("ID_SAMOLOT" NOT NULL ENABLE);

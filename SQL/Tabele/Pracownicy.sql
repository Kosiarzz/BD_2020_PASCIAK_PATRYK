--------------------------------------------------------
--  File created - œroda-maja-06-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table PRACOWNICY
--------------------------------------------------------

  CREATE TABLE "C##PATRYK"."PRACOWNICY" 
   (	"ID_PRACOWNIK" NUMBER(*,0), 
	"IMIE" VARCHAR2(20 BYTE), 
	"NAZWISKO" VARCHAR2(32 BYTE), 
	"ADRES" VARCHAR2(128 BYTE), 
	"PESEL" VARCHAR2(11 BYTE), 
	"TELEFON" VARCHAR2(9 BYTE), 
	"LOGIN" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
REM INSERTING into C##PATRYK.PRACOWNICY
SET DEFINE OFF;
--------------------------------------------------------
--  Constraints for Table PRACOWNICY
--------------------------------------------------------

  ALTER TABLE "C##PATRYK"."PRACOWNICY" MODIFY ("ID_PRACOWNIK" NOT NULL ENABLE);

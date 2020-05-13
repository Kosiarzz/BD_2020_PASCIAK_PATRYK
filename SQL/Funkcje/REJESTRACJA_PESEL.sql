--------------------------------------------------------
--  File created - œroda-maja-13-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function REJESTRACJA_PESEL
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."REJESTRACJA_PESEL" (
imie in VARCHAR2,
nazwisko in VARCHAR2,
adres in VARCHAR2,
pesel in VARCHAR2,
telefon in VARCHAR2,
login in VARCHAR2)
RETURN VARCHAR2 
IS
BEGIN
INSERT INTO KLIENT (IMIE,NAZWISKO,ADRES,PESEL,TELEFON,LOGIN) VALUES(imie,nazwisko,adres,pesel,telefon,login);

exception
when DUP_VAL_ON_INDEX then RAISE_APPLICATION_ERROR(-20001, 'Taki pesel juz istnieje istnieje !');
  return NULL;
END REJESTRACJA_PESEL;

/

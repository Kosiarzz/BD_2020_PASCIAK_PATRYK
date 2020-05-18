--------------------------------------------------------
--  File created - poniedzia³ek-maja-18-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function SPRAWDZENIE_PESEL_BAZA
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."SPRAWDZENIE_PESEL_BAZA" 
(pesel in varchar2)
RETURN VARCHAR2 AS 
zmienna VARCHAR2(1);
BEGIN
    SELECT PESEL
      into zmienna
    FROM KLIENCI_LOGOWANIE
    WHERE PESEL = pesel;

  exception
  when NO_DATA_FOUND then RAISE_APPLICATION_ERROR(-20001, 'Taki pesel nie istnieje!');
  return 'FALSE';
END SPRAWDZENIE_PESEL_BAZA;

/

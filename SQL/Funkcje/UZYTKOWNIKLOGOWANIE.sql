--------------------------------------------------------
--  File created - poniedzia³ek-maja-18-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function UZYTKOWNIKLOGOWANIE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."UZYTKOWNIKLOGOWANIE" 
(login in VARCHAR2,
haslo in VARCHAR2,
phaslo in VARCHAR2)
RETURN VARCHAR2
IS
ilosc NUMBER;
BEGIN
IF (length(login)>0 and length(haslo)>0 and length(phaslo)>0) then
  RETURN 'TRUE';
ELSE
  RETURN 'FALSE';
  END IF;
END UZYTKOWNIKLOGOWANIE;

/

--------------------------------------------------------
--  File created - poniedzia³ek-maja-18-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function WYSZUKAJ_LOT
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."WYSZUKAJ_LOT" 
(jeden in VARCHAR2,
dwa in VARCHAR2)
RETURN VARCHAR2
IS
blad exception;
BEGIN
IF (length(jeden)>0 or length(dwa)>0) then
  RETURN 'TRUE';
ELSE
  raise blad;
  END IF;
  exception
when blad then RAISE_APPLICATION_ERROR(-20000, 'Uzupelij pola!');
END WYSZUKAJ_LOT;

/

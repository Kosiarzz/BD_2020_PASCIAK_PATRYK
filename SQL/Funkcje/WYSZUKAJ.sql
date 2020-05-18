--------------------------------------------------------
--  File created - poniedzia³ek-maja-18-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function WYSZUKAJ
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."WYSZUKAJ" 
(jeden in VARCHAR2,
dwa in VARCHAR2,
trzy in VARCHAR2)
RETURN VARCHAR2
IS
blad exception;
BEGIN
IF (length(jeden)>0 or length(dwa)>0 or length(trzy)>0) then
  RETURN 'TRUE';
ELSE
  raise blad;
  END IF;
   exception
when blad then RAISE_APPLICATION_ERROR(-20000, 'Uzupelij pola!');
END WYSZUKAJ;

/

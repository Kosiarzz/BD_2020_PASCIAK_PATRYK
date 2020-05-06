--------------------------------------------------------
--  File created - œroda-maja-06-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function HASLO
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."HASLO" --tworzenie funkcji o nazwwie FUNCTION1
---MIEJSCE NA ZMIENNE WEJŒCIOWE
(haslo in VARCHAR2,
phaslo in VARCHAR2)
RETURN VARCHAR2 --co zwraca funkcja (typ danych)
IS
--zmienne lokalne funkcji

BEGIN
--co chcemy aby funkcja wykonwywala
IF (haslo=phaslo) then
  RETURN 'TRUE'; --zwracana wartosc przez funkcje
ELSE
  RETURN 'FALSE'; --zwracana wartoœæ przez funkcje
  END IF;
END HASLO; --koniec funkcji

/

--------------------------------------------------------
--  File created - œroda-maja-06-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function LOGOWANIE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."LOGOWANIE" --tworzenie funkcji o nazwwie FUNCTION1
---MIEJSCE NA ZMIENNE WEJŒCIOWE
(znak in VARCHAR2,
znak2 in VARCHAR2)
RETURN VARCHAR2 --co zwraca funkcja (typ danych)
IS
--zmienne lokalne funkcji
ilosc NUMBER;
BEGIN
--co chcemy aby funkcja wykonwywala
IF (length(znak)>0 and length(znak2)>0) then
  RETURN 'TRUE'; --zwracana wartosc przez funkcje
ELSE
  RETURN 'FALSE'; --zwracana wartoœæ przez funkcje
  END IF;
END LOGOWANIE; --koniec funkcji

/

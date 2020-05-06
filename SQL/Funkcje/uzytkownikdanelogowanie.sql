--------------------------------------------------------
--  File created - œroda-maja-06-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function UZYTKOWNIKLOGOWANIE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."UZYTKOWNIKLOGOWANIE" --tworzenie funkcji o nazwwie FUNCTION1
---MIEJSCE NA ZMIENNE WEJŒCIOWE
(login in VARCHAR2,
haslo in VARCHAR2,
phaslo in VARCHAR2)
RETURN VARCHAR2 --co zwraca funkcja (typ danych)
IS
--zmienne lokalne funkcji
ilosc NUMBER;
BEGIN
--co chcemy aby funkcja wykonwywala
IF (length(login)>0 and length(haslo)>0 and length(phaslo)>0) then
  RETURN 'TRUE'; --zwracana wartosc przez funkcje
ELSE
  RETURN 'FALSE'; --zwracana wartoœæ przez funkcje
  END IF;
END UZYTKOWNIKLOGOWANIE; --koniec funkcji

/

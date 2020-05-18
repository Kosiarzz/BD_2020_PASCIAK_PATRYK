--------------------------------------------------------
--  File created - poniedzia³ek-maja-18-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function EDYTUJLOTDANE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."EDYTUJLOTDANE" --tworzenie funkcji o nazwwie FUNCTION1
---MIEJSCE NA ZMIENNE WEJŒCIOWE
(starts in VARCHAR2,
ladowanie in VARCHAR2,
powrot in VARCHAR2,
miejscowosc in VARCHAR2,
status in VARCHAR2,
miejsc in VARCHAR2,
samolot in VARCHAR2)
RETURN VARCHAR2 --co zwraca funkcja (typ danych)
IS
--zmienne lokalne funkcji
ilosc NUMBER;
BEGIN
--co chcemy aby funkcja wykonwywala
IF (length(starts)>0 and length(ladowanie)>0 and length(powrot)>0 and length(miejscowosc)>0 and length(status)>0 and length(miejsc)>0 and length(samolot)>0) then
  RETURN 'TRUE'; --zwracana wartosc przez funkcje
ELSE
  RETURN 'FALSE'; --zwracana wartoœæ przez funkcje
  END IF;
END EDYTUJLOTDANE; --koniec funkcji

/

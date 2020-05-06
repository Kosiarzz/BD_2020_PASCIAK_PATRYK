--------------------------------------------------------
--  File created - �roda-maja-06-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function PESEL
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."PESEL" --tworzenie funkcji o nazwwie FUNCTION1
---MIEJSCE NA ZMIENNE WEJ�CIOWE
(pesel in VARCHAR2,
telefon in VARCHAR2
)
RETURN VARCHAR2 --co zwraca funkcja (typ danych)
IS
--zmienne lokalne funkcji

BEGIN
--co chcemy aby funkcja wykonwywala
IF (length(pesel)=11 and length(telefon)=9) then
  RETURN 'TRUE'; --zwracana wartosc przez funkcje
ELSE
  RETURN 'FALSE'; --zwracana warto�� przez funkcje
  END IF;
END PESEL; --koniec funkcji

/

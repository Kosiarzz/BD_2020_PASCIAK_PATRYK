--------------------------------------------------------
--  File created - œroda-maja-06-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function UZYTKOWNIKDANE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."UZYTKOWNIKDANE" --tworzenie funkcji o nazwwie FUNCTION1
---MIEJSCE NA ZMIENNE WEJŒCIOWE
(imie in VARCHAR2,
nazwisko in VARCHAR2,
adres in VARCHAR2,
pesel in VARCHAR2,
telefon in VARCHAR2)
RETURN VARCHAR2 --co zwraca funkcja (typ danych)
IS
--zmienne lokalne funkcji
ilosc NUMBER;
BEGIN
--co chcemy aby funkcja wykonwywala
IF (length(imie)>0 and length(nazwisko)>0 and length(adres)>0) and length(pesel)>0 and length(telefon)>0 then
  RETURN 'TRUE'; --zwracana wartosc przez funkcje
ELSE
  RETURN 'FALSE'; --zwracana wartoœæ przez funkcje
  END IF;
END UZYTKOWNIKDANE; --koniec funkcji

/

--------------------------------------------------------
--  File created - �roda-maja-13-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function LOGOWANIE_WALIDACJA_DANYCH
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."LOGOWANIE_WALIDACJA_DANYCH" 
---MIEJSCE NA ZMIENNE WEJ�CIOWE
(login in VARCHAR2,
haslo in VARCHAR2)
RETURN VARCHAR2 --co zwraca funkcja (typ danych)
IS
--zmienne lokalne funkcji
blad exception;
BEGIN
--co chcemy aby funkcja wykonwywala
IF (length(login)>0 and length(haslo)>0) then
  RETURN 'TRUE'; --zwracana wartosc przez funkcje
ELSE
  raise blad; --zwracana warto�� przez funkcje
  END IF;
exception
when blad then RAISE_APPLICATION_ERROR(-20001, 'Uzupelnij wszystkie pola');
END LOGOWANIE_WALIDACJA_DANYCH; --koniec funkcji

/

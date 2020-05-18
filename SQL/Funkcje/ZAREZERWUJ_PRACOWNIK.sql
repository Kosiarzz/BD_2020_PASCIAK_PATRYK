--------------------------------------------------------
--  File created - poniedzia³ek-maja-18-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function ZAREZERWUJ_PRACOWNIK
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."ZAREZERWUJ_PRACOWNIK" 
(jeden in VARCHAR2, --numer miejsca
dwa in VARCHAR2) --pesel
RETURN VARCHAR2
IS
zmienna varchar2(1);
blad exception;
blad2 exception;
BEGIN
IF (length(jeden)>0 or length(dwa)>0) then
  IF(length(dwa)=11) then
    return 'TRUE';
    ELSE
    raise blad2;
    END IF;
ELSE
  raise blad;
  END IF;
  exception
when blad then RAISE_APPLICATION_ERROR(-20000, 'Uzupelij pola!');
when blad2 then RAISE_APPLICATION_ERROR(-20001, 'Pesel jest nieprawidlowy!');
END ZAREZERWUJ_PRACOWNIK;

/

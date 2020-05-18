--------------------------------------------------------
--  File created - poniedzia³ek-maja-18-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function REJESTRACJA_LOGIN
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."REJESTRACJA_LOGIN" (
login in VARCHAR2,
haslo in VARCHAR2,
phaslo in VARCHAR2)
RETURN VARCHAR2 
IS

BEGIN

INSERT INTO KLIENCI_LOGOWANIE (LOGIN, HASLO)  VALUES (login,haslo);

exception
when DUP_VAL_ON_INDEX then RAISE_APPLICATION_ERROR(-20001, 'Taki login istnieje !');
  return NULL;
END REJESTRACJA_LOGIN;

/

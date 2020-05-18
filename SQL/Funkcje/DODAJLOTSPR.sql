--------------------------------------------------------
--  File created - poniedzia³ek-maja-18-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function DODAJLOTSPR
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."DODAJLOTSPR" 
(startd in VARCHAR2,
startg in VARCHAR2,
ladowanied in VARCHAR2,
ladowanieg in VARCHAR2,
powrotd in VARCHAR2,
powrotg in VARCHAR2,
miejscowosc in VARCHAR2,
samolot in VARCHAR2)
RETURN VARCHAR2 
IS
ilosc NUMBER;
BEGIN
IF (length(startd)>0 and length(startg)>0 and length(ladowanied)>0 and length(ladowanieg)>0 and length(powrotd)>0 and length(powrotg)>0 and length(miejscowosc)>0 and length(samolot)>0) then
  RETURN 'TRUE';
ELSE
  RETURN 'FALSE';
  END IF;
END DODAJLOTSPR;

/

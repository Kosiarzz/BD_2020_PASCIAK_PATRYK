--------------------------------------------------------
--  File created - poniedzia³ek-maja-18-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function NUMERLOTU
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."NUMERLOTU" 
(nrlotu in VARCHAR2)
RETURN VARCHAR2
IS
BEGIN
IF (length(nrlotu)>0 ) then
  RETURN 'TRUE';
ELSE
  RETURN 'FALSE';
  END IF;
END NUMERLOTU;

/

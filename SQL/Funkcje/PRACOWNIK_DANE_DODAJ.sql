--------------------------------------------------------
--  File created - poniedzia³ek-maja-18-2020   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function PRACOWNIK_DANE_DODAJ
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE FUNCTION "C##PATRYK"."PRACOWNIK_DANE_DODAJ" 
(imie in VARCHAR2,
nazwisko in VARCHAR2,
adres in VARCHAR2,
pesel in VARCHAR2,
telefon in VARCHAR2)
RETURN VARCHAR2
IS
ilosc_znakow exception;	
bpesel exception;		
btelefon exception;
BEGIN
IF (length(imie)>0 and length(nazwisko)>0 and length(adres)>0 and length(pesel)>0 and length(telefon)>0) then
            IF(length(telefon)=9) then
                IF(length(pesel)=11) then
                    return 'TRUE';
                else
                    raise bpesel;
                END IF;
            else
                raise btelefon;
            END IF;
        else
    raise ilosc_znakow;
END IF;
  
  exception
  when ilosc_znakow then RAISE_APPLICATION_ERROR(-20000, 'Uzupelnij wszytskie dane!');
  when btelefon then RAISE_APPLICATION_ERROR(-20002, 'Podaj poprawny numer telefonu!');
  when bpesel then RAISE_APPLICATION_ERROR(-20003, 'Podaj poprawny pesel!');
  when OTHERS then RAISE_APPLICATION_ERROR(-20004,'Blad!');
END PRACOWNIK_DANE_DODAJ; --koniec funkcji

/

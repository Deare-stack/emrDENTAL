-- please make sure run these sqls before run Java code
-- 1. create database named dentalemr
-- 2. this database is created on localhost
-- 3.import all the cvs file to dentalemr database

create database dentalemr;-- password is 123456
use dentalemr;
select * from patients;
select * from patient_history;
select * from `procedure`;
select * from office_visits;

----------------------------------- please run this before runing the Java code, thank you----------------------------------------------------------------------------------------------
-- please make sure run these sqls before run Java code
-- 4. AFTER imported all the csv file in to dentalemr database Make all the ID columns AUTO_INCREMENT on parent & children

ALTER TABLE patients
  MODIFY patient_id       INT NOT NULL AUTO_INCREMENT,
  ADD     PRIMARY KEY      (patient_id);

ALTER TABLE patient_history
  MODIFY patient_history_id INT NOT NULL AUTO_INCREMENT,
  ADD     PRIMARY KEY      (patient_history_id);

ALTER TABLE `procedure`
  MODIFY procedure_id    INT NOT NULL AUTO_INCREMENT,
  ADD     PRIMARY KEY      (procedure_id);

ALTER TABLE office_visits
  MODIFY office_visit_id INT NOT NULL AUTO_INCREMENT,
  ADD     PRIMARY KEY      (office_visit_id);

-- 3) add the FKs (and add one on `procedure.patient_id`)
ALTER TABLE patient_history
  ADD CONSTRAINT fk_history_patient
    FOREIGN KEY (patient_id)
    REFERENCES patients(patient_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE `procedure`
  ADD CONSTRAINT fk_procedure_patient
    FOREIGN KEY (patient_id)
    REFERENCES patients(patient_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE office_visits
  ADD CONSTRAINT fk_visits_patient
    FOREIGN KEY (patient_id)
    REFERENCES patients(patient_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;
    
ALTER TABLE office_visits
modify office_vist_date datetime;



  
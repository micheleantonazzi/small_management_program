CREATE VIEW last_billings AS SELECT a.* FROM billings a JOIN (SELECT id_condo, MIN(year) year FROM billings WHERE paid = 0 GROUP BY id_condo) b ON a.id_condo = b.id_condo AND a.year = b.year 

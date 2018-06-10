C:
cd "C:\Program Files\PostgreSQL\9.6\bin"
if not exist "E:\tt\" mkdir E:\tt
set PGPASSWORD=stagia2&& pg_dump -U stagia2 stagia2 > E:\tt\backup.dump
pause	
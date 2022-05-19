@echo off
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_181
set Path=%JAVA_HOME%\bin;%Path%
TITLE = Application (Imedia-SSO SERVER API_)
java -Xms32m -Xmx512m -jar -Xmx512m IMD_SINGLE_SIGN_ON_SV-0.0.1.jar
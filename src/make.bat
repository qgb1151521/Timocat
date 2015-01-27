:: javac -verbose

@echo off

::
:: Compile
::
:: @javac -verbose Server.java
@javac Server.java

:
:: Run
:
@java Server

::
:: Clear
::
@del *.class
@echo on
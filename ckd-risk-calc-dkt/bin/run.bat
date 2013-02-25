
@rem ----- ExeScript Options Begin -----
@rem ScriptType: console,silent,invoker
@rem DestDirectory: current
@rem Icon: default
@rem OutputFile: S:\desktop\ckd-risk-calc\bin\run.exe
@rem ----- ExeScript Options End -----
@echo off

SETLOCAL ENABLEDELAYEDEXPANSION
set BASEDIR=%~dps0\..
set JR=P:\OPT\java\jdk1.7.0_07\bin\javaw.exe

set CP=%BASEDIR%\classes

START %JR% -cp %CP% it.mate.ckd.AppMain


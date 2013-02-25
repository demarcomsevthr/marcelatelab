
@rem ----- ExeScript Options Begin -----
@rem ScriptType: console,silent,invoker
@rem DestDirectory: current
@rem Icon: default
@rem OutputFile: S:\desktop\ckd-risk-calc\bin\run-gr.exe
@rem ----- ExeScript Options End -----
@echo off

SETLOCAL ENABLEDELAYEDEXPANSION
set BASEDIR=%~dps0\..
set JR=P:\OPT\java\jdk1.7.0_07\bin\javaw.exe

set CP=
cd %BASEDIR%
for /r lib %%F in (*.jar) do SET CP=!CP!;%%F

%JR% -cp classes;%CP% it.mate.ckd.AppMainGroovy


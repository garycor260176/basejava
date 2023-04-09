SET filelist=%temp%\filelist-%random%.txt

dir /s /b src\*.java > %filelist%

javac @%filelist% -d out

del %filelist%
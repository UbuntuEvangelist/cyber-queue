rem # for testing xslt. parameters: sources xsl-script result
java -cp dist/QSystem.jar ru.apertum.qsystem.utils.XsltTester "file:///D:/work/qs/qsystem/temp/feed.xml" "file:///D:/work/qs/qsystem/temp/feed.xsl" "temp/xslt.txt"

pause
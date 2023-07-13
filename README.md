# How to Use
1. Download the .jar file from the releases page.
2. Create an executable script (windows: .bat, or linux: .sh)
3. Copy and paste the following script in the next step into your batch file. If you are running the .jar file anywhere else than the server's parent directory, change "server" to the path of the server's files. For example: the server files are in a folder located at "D:\a\b\c\server", so replace to that path.
4. WINDOWS ONLY: `java -jar CrafterBackup.jar server` Run this inside any batch file in the same location as the jar.
5. LINUX ONLY:
```
#!/bin/bash
java.exe -jar CrafterBackup.jar server
```
Run this inside any shell file in the same location as the jar.

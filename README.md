# GetAlexaRank
This tool will help you to get Alexa rank for the given list of url(s)

# Usage
[Download the binary from here](https://github.com/scorta/GetAlexaRank/releases/download/v0.1/GetAlexaRank.jar), then run this command (of course you need Java to be installed on your computer first):

`java -jar <this_program> <input_file> <pause_interval>`

`<this_program>`: normally, it is `GetAlexaRank.jar`

`<input_file>`: file which contains the list of urls need to be checked. Each url need to be in a separated line. If leaves blank, the program will check from the file `list.txt`

`<pause_interval>`: time to pause after making a request to Alexa server (in mili second). This should be 1000 to be safe - Alexa may ban your IP for some time if you make to many request in short period (if you are in a rush, or you have a small list of url, like 100; you can try small number like 500 or less. Even 0 could be okay).

Eg.

`java -jar GetAlexaRank.jar list.txt 1000`

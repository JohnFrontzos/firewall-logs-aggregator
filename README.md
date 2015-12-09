Early Warning Malware Detection project for Android 
=========

Based on [AFWall+] project, the early warning malware detection system, is a under-graduate android project that collects data from AFWall's log file. The result of some calculations based on the the firewall's entries is sending to PROTOS project that creates malware trends statistics. 

## TODO  

* ~~Detach Submodule the AFWall~~
* ~~Check the log file's implementation of AFWALL+~~
* ~~Create a plugin for capturing the entries of the log file~~
* [optional] Create REST API


## App Basic Schema  

![Alt text][UML]

[UML]:https://github.com/JohnFrontzos/firewall-logs-aggregator/blob/develop/art/Early%20Warning%20System.png "UML schema"
[AFWall+]:https://github.com/ukanth/afwall

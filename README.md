Early Warning Malware Detection project for Android 
=========

Based on [AFWall+] project, the early warning malware detection system, is a under-graduate android project that collects data from AFWall's log file. The result of some calculations based on the the firewall's entries is sending to PROTOS project that creates malware trends statistics. 

TODO 

* Analyze and Create custom scripts for IPTABLES rules
* ~~Submodule the AFWall~~
* ~~Check the log file's implemation of AFWALL+ ~~
* Create a plugin for capturing the entries of the log file
* Calculate the data that PROTOS server needs
* REST client side communication
* [optional] Create backend's REST API


App Basic Schema 

![Alt text][UML]


[UML]:/Design/Engineering/Early Warning System.png "UML schema"
[AFWall+]:https://github.com/ukanth/afwall

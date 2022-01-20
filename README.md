# CSE-331-Projects
---                                                      
> All of my CSE 331 Projects

This repo contains all of my projects from CSE-331, which all built up to the main and final project: a fully functional app 
that shows you the quickest way to walk between any two buildings on the University of Washington's campus.

## Notable Projects Within This Repo
---



### CampusPaths

CampusPaths is a single-page webapp that allows you to choose two buildings on the University of Washington's campus and draws the 
shortest walking path between those two buildings.

##### What it looks like:  
![website demo](https://github.com/Greatroot/CSE-331-Projects/blob/main/Animation.gif)


##### How to run this for yourself:

1. Clone this entire GitHub repo onto your computer.
2. Open the CSE-331-Projects folder you just cloned onto your computer in your favorite IDE. 
3. Open the hw-campuspaths-server folder and open this file at ```hw-campuspaths-server/src/main/java/campuspaths/SparkServer.java```
4. Run the main method in the SparkServer class and bada bing bada boom, your server for the CampusPaths server should be running. 
6. Open a terminal and navigate to the CSE-331-Projects folder.
7. Then navigate to the directory hw-campuspaths and run the command 

```
npm start
```
This should run the client side of the CampusMaps website locally.

Please note that if you don't already have node.js installed on your computer, then any "npm" commands won't work. If this is the case, please visit 
- https://docs.npmjs.com/downloading-and-installing-node-js-and-npm
- or https://nodejs.org/en/

8. So all you need to do now is go to your browser and type in http://localhost:3000/ to see a working CampusMaps website. 



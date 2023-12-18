
# Automated Plant Monitoring and Care System

**CSED Capstone Project 2023-2024**

Developed by Members of Team CPG No. 188:-
- Pranjal Arora (102003402, COE-16)
- Naga Madhurya Peram (102003407, COE-16)
- Piyush Sharma (1020034, COE-16)
- Archita Gautam (102003419, COE-16)
Under the guidance of Dr. Neenu Garg, Associate Professor, CSED Department, TIET.


## Problem Statement

Plants have always been an important natural component of human existence and surroundings.There are several critical factors that challenge the health and vitality of plants and overall ecosystem. The primary concerns include:

1. **Disease affecting the plant:**
(infection from diseases in various ways, and the symptoms can vary depending on the type of disease and the specific plant species)

2. **Improper watering:**
(too much or too little watering of plants) 

3. **Sunlight requirements:**
(plant doesn’t receive adequate sunlight, it may become pale, or shed leaves)

4. **Temperature:**
(some plants can endure long hours at high temperatures, while others may be susceptible to high temperatures and temperature changes)

## Our solution
- The main goal of our project is to create an ***Intelligent Plant Care System*** that can:
    - monitor the health of plants using **IoT technology**, by integrating hardware components such as Sensors, Microcontrollers, Wifi Modules etc. 
    - detect diseases in plants using **Machine Learning Models**. 
    - allow users to interact with their plants, using our **Mobile Application "FloraSense"**, and get the real-time stats from plant sensors, as well as get the disease diagnosis.

We thoroughly tested and verified the hardware and software components, and deployed our system in a real plant environment.
We also evaluated the performance and usability of the system. 

## Machine Learning models used:
- **Image augmentation:** Python OpenCV, K-Means Clustering, MinMax Scaler
- **Disease Detection:** Tensorflow ResNet 50 CNN Model, Tensorflow InceptionV3 model

## IoT Hardware used:
- LM35 sensor (Temperature)
- LDR sensor (Sunlight)
- Soil Moisture sensor
- NodeMCU microcontroller
- Wifi Module

## Software Tech-stack used:
- **Mobile Application:** Java
- **Backend:** Python, Flask
- **Database:** SQL yog
- **Sensor response:** Arduino, ThingSpeak Cloud

## Instructions to use our product:
1. Install the basic hardware breadboard to your plant.
2. Register on the Mobile App "FloraSense" using Sign Up, then Login.
3. Detect the disease of the plant, by uploading a picture of the defective leaf/stem through Camera or Gallery, then click "Upload" button. 
4. Monitor the stats of plant health (Soil moisture, Temperature, Sunlight) using "Sensor Data" button in Menu, and get real-time Alerts.






 







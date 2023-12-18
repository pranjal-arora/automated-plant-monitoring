from flask import Flask
from flask import request
import pymysql
import json
import pandas as pd
import cv2
import numpy as np
import threading
from werkzeug.utils import secure_filename
import os

import numpy as np
import tensorflow
from tensorflow.keras.preprocessing import image
from tensorflow.keras.models import load_model
import warnings
warnings.filterwarnings("ignore")
import pickle

app = Flask(__name__)

UPLOAD_FOLDER = 'static/uploadedimg/'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
ALLOWED_EXTENSIONS = set(['jpeg', 'jpg', 'png', 'gif'])
app.secret_key = 'any random string'


print("[info] Model loading....")        
from tensorflow import keras
resmodel = keras.models.load_model('resnet_classifier.hp5')
print("model loaded successfully!!")

def dbConnection():
    try:
        connection = pymysql.connect(host="localhost", user="root", password="root", database="speciesclass")
        return connection
    except:
        print("Something went wrong in database Connection")

def dbClose():
    try:
        dbConnection().close()
    except:
        print("Something went wrong in Close DB Connection")

con = dbConnection()
cursor = con.cursor()

@app.route('/userRegister', methods=['GET', 'POST'])
def userRegister():
    if request.method == 'POST':
        print("GET")        

        username = request.form.get("username")
        passw = request.form.get("password")        
        email = request.form.get("emailid")
        mobile = request.form.get("mobilenumber")        
        print("INPUTS")        
        print("username",username)
        
        cursor.execute('SELECT * FROM register WHERE username = %s', (username))
        count = cursor.rowcount
        if count > 0:
            return "fail"
        else:        
            sql1 = "INSERT INTO register(username, email,mobile, password) VALUES (%s, %s, %s, %s);"
            val1 = (username, email, mobile, passw)
            cursor.execute(sql1,val1)
            con.commit()
            return "success"  
    
@app.route('/userLogin', methods=['GET', 'POST'])
def userLogin():
    if request.method == 'POST':
        print("GET")        

        username = request.form.get("username")
        passw = request.form.get("password")       
        print("INPUTS")        
        print("username",username)
        
        cursor.execute('SELECT * FROM register WHERE username = %s AND password = %s', (username, passw))
        count = cursor.rowcount
        if count > 0:
            return "success"
        else:
            return "Fail"


@app.route('/uploadfile',methods=['POST','GET'])
def uploadfile():
    if request.method == "POST":
        f2= request.files['bill']
        filename_secure = secure_filename(f2.filename)
        split_filename = filename_secure.split('_')[-1]
        f2.save(os.path.join(app.config['UPLOAD_FOLDER'], split_filename))        
        return "success"
    return "fail"
    

plant = ['Pepper__bell___Bacterial_spot',
 'Pepper__bell___healthy',
 'Potato___Early_blight',
 'Potato___healthy',
 'Potato___Late_blight',
 'Tomato_Bacterial_spot',
 'Tomato_Early_blight',
 'Tomato_healthy',
 'Tomato_Late_blight',
 'Tomato_Leaf_Mold',
 'Tomato_Septoria_leaf_spot',
 'Tomato__Target_Spot',
 'Tomato__Tomato_mosaic_virus']

@app.route('/getResult', methods=['GET', 'POST'])
def getResult():
    if request.method == 'POST':    
        # try:    
        filename = request.form.get("imagename") 
        
        print(filename)
        
        img_path = "static/uploadedimg/"+filename
        image_size=224
        img = image.load_img(img_path, target_size=(image_size, image_size))
        x = image.img_to_array(img)
        print(type(x))
        img_4d=x.reshape(1,224,224,3)
        predictions = resmodel.predict(img_4d)
        print(predictions[0])
        new_pred=np.argmax(predictions[0])
        print(new_pred)
        dict1={0:'Pepper__bell___Bacterial_spot',1:'Pepper__bell___healthy',2:'Potato___Early_blight',3:'Potato___healthy',4:'Potato___Late_blight',5:'Tomato_Bacterial_spot',6:'Tomato_Early_blight',7:'Tomato_Bacterial_spot',8:'Tomato_Early_blight',9:'Tomato_healthy',10:'Tomato_Late_blight',11:'Tomato_Leaf_Mold',12:'Tomato_Septoria_leaf_spot',13:'Tomato__Target_Spot',14:'Tomato__Tomato_mosaic_virus'}
    
        a=dict1[new_pred]
        
        print(str(a)+" Detected in Image")
        print("-----------------------------------------------")
        
        cursor.execute('SELECT p1,p2,p3 FROM disease where diseasename= %s;',(str(a)))
        row = cursor.fetchone()
        print(row)
        jsonObj = json.dumps([row,str(a)+"Detected in Image"])
        
        print("jsonObj")
        print(jsonObj)
        
        return jsonObj
    
@app.route('/profile', methods=['GET', 'POST'])
def profile():
    if request.method == 'POST':  
        Username = request.form.get("Username")
        cursor.execute('SELECT * FROM register where username= %s;',(Username))
        row = cursor.fetchall()
        jsonObj = json.dumps(row)
        # print(jsonObj)
        return jsonObj 



import requests
import config as cnfg

url=cnfg.URL #thingspeak url

import urllib.request
import json
import time
import random
# import serial   

tempVal = random.randint(35,45)
lightVal = random.randint(100, 500) 

@app.route('/getdata', methods=['GET', 'POST'])
def getdata():
    if request.method == 'POST':
        print("GET")  
        SensVal = []
        if True:
            TS = urllib.request.urlopen(url)
            response = TS.read()
            data=json.loads(response)
            soilVal = data["feeds"][1]["field1"].replace("r\n","")
            print (soilVal)
            time.sleep(6)
            finalVal = str(soilVal)+","+str(tempVal)+","+str(lightVal)
            SensVal.append(finalVal)
            TS.close()
            
         
        values = list(map(float, SensVal[0].split(',')))
        data = {"values": values}
        json_data = json.dumps(data)
        print(json_data)
        return json_data
          
    
if __name__ == "__main__":
    app.run("0.0.0.0")

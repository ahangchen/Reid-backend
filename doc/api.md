# Reid-backend API

## Data Sensor

#### 后端获取sensor捕捉到的包信息，存入数据库
  - URL: http://localhost:8081/sensor/wifi
  - Type: POST
  - Content-type: x-www-form-urlencoded
  - 参数: 
    - captureTime: 捕获时间，为yyyy-MM-dd hh:mm:ss格式
    - fromSensorId: 传感器id，一个整数，范围[0,2^32)
    - macAddress: 捕捉到的包的mac地址，格式为70:85:c2:3d:c8:eb
    - intensity： 捕捉到的包的信号强度， 范围[-150,150]
  - 返回值:
    - 写入成功
    ```json
    {
        "code": 0,
        "msg": "成功",
        "data": {
            "captureTime": "2017-12-11 07:00:05",
            "fromSensorId": 0,
            "macAddress": "00:11:22:33:44:55:66",
            "intensity": "80"
        }
    }
    ```
    
    - 写入出错(其他接口同理）
    ```json
    {
        "code": -1,
        "msg": "未知错误",
        "data": null
    }
    ```
    
#### 后端获取sensor捕捉到的图片信息，存入数据库
  - URL: http://localhost:8081/sensor/vision
  - Type: POST
  - Content-type: x-www-form-urlencoded
  - 参数: 
    - captureTime: 捕获时间，为yyyy-MM-dd hh:mm:ss格式
    - fromSensorId: 传感器id，一个整数，范围[0,2^32)
    - imgPath: 图片的URL，需要先调用[接口](#前端上传图片文件，后端返回一个URL)来获取，注意同步
  - 返回值:
    - 写入成功
    ```json
    {
        "code": 0,
        "msg": "成功",
        "data": {
            "captureTime": "2017-12-11 07:00:00",
            "fromSensorId": 0,
            "imgPath": "http://222.201.145.237:8888/img/rasp-wifi.png"
        }
    }
    ```
    
    
#### 后端获取sensor捕捉到的音频信息，存入数据库
  - URL: http://localhost:8081/sensor/audio
  - Type: POST
  - Content-type: x-www-form-urlencoded
  - 参数: 
    - captureTime: 捕获时间，为yyyy-MM-dd hh:mm:ss格式
    - fromSensorId: 传感器id，一个整数，范围[0,2^32)
    - audioPath: 音频的URL，需要先调用[接口](#前端上传音频文件，后端返回一个URL)来获取，注意同步
  - 返回值:
    - 写入成功
    ```json
    {
        "code": 0,
        "msg": "成功",
        "data": {
            "captureTime": "2017-12-11 07:00:00",
            "fromSensorId": 0,
            "audioPath": "http://222.201.145.237:8888/img/rasp-wifi.mp3"
        }
    }
    ```

#### 前端上传图片文件，后端返回一个URL
  - URL: http://localhost:8081/file/img
  - Type: POST
  - Content-type: multipart/form-data
  - 参数: 
    - file: 文件二进制流 
  - 返回值：
    - 成功：
    ```json
    {
      "code":0,
      "msg":"成功",
      "data":"http://222.201.145.237:8888/img/rasp-wifi.png"
    }
    ```
    

#### 新增单个传感器信息

#### 批量新增传感器信息

## Web 检索

#### 前端获取后端的传感器列表

#### 前端给出传感器id和时间范围，后端返回符合条件的图片列表
  - URL: http://localhost:8081/sensor/vision/list
  - Type: GET
  - Content-type: multipart/form-data
  - 参数: 
    - id: 传感器id
    - startTime: 起始时间
    - endTime: 终止时间 
  - 返回值：
    - 成功：
    ```json
    {
        "code": 0,
        "msg": "成功",
        "data": [
            {
                "captureTime": "2017-12-11 07:00:00",
                "fromSensorId": 0,
                "imgPath": "http://222.201.145.237:8888/img/rasp-wifi.png"
            },
            {
                "captureTime": "2017-12-11 08:00:00",
                "fromSensorId": 0,
                "imgPath": "http://222.201.145.237:8888/img/rasp-wifi.png"
            }
        ]
    }
    ```
    
#### 前端给出传感器id和时间范围，后端返回符合条件的音频列表

#### 前端给出传感器id和时间范围，后端返回符合条件的mac地址列表

#### 前端给出mac地址，后端返回mac检索到的相关图片列表，以及捕捉时间，对应的传感器id，音频列表


## Machine Learning Reid

#### 前端给出图片文件，后端返回图像检索到的相似图片列表，以及捕捉时间，对应的传感器id，音频列表，mac地址列表

#### 前端给出图片url，后端返回图像检索到的相似图片列表，以及捕捉时间，对应的传感器id，音频列表，mac地址列表
 

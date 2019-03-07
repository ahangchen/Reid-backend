# Reid-backend API

## 1. Data Sensor

#### API1.1
后端获取sensor捕捉到的包信息，存入数据库
  - URL: http://222.201.137.47:12346/sensor/wifi
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
    
#### API1.2
后端获取sensor捕捉到的图片信息，存入数据库，# TODO 并执行匹配mac地址的逻辑
  - URL: http://222.201.137.47:12346/sensor/vision
  - Type: POST
  - Content-type: application/json
  - 参数: json格式
  
  ```json
  {
	"captureTime": "2017-12-17 16:21:00",
	"fromSensorId": "1",
	"imgPath": "http://222.201.137.47:12347/img/rasp-wifi.png",
	"boxes": [[0, 1, 2, 3], [2, 3,3,4]]
}
  ```
    - captureTime: 捕获时间，为yyyy-MM-dd hh:mm:ss格式
    - fromSensorId: 传感器id，一个整数，范围[0,2^32)
    - imgPath: 图片的URL，需要先调用[接口](#前端上传图片文件，后端返回一个URL)来获取，注意同步
    - boxes: 图片中行人的坐标
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

#### API1.5
前端上传图片文件，后端返回一个URL
  - URL: http://222.201.137.47:12346/file/img
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
    

#### API1.6
新增单个传感器信息
 - URL: http://222.201.137.47:12346/sensor/setting
  - Type: POST
  - Content-type: application/json
  - 参数: json格式
  
  ```json
  {
	"id": "1",
	"macAddress": "00:11:22:33:44:66",
	"latitude": 40.0,
	"longitude": 110.0 
  }
  ```
    - macAddress: 传感器上一个无线网卡的mac地址
    - id: 传感器id，一个整数，范围[0,2^32)
    - latitude: 传感器所在纬度
    - longitude: 传感器所在经度
  - 返回值:
    - 写入成功
    
    ```json
    {
        "code": 0,
        "msg": "成功",
        "data": {
            "id": 1,
            "latitude": 40,
            "longitude": 110,
            "macAddress": "00:11:22:33:44:66"
        }
    }
    ```


    
#### API1.8
批量新增传感器信息

## 2.Web 检索
#### API2.1
前端获取后端的传感器列表
- URL: http://222.201.137.47:12346/sensor/list
  - Type: GET
  - Content-type: multipart/form-data
  - 参数: 
    无
- 返回值：
    - 成功：
    
    ```json
    {
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "id": 0,
            "latitude": 40.5,
            "longitude": 110.7,
            "macAddress": "00:11:22:33:44:66"
        },
        {
            "id": 1,
            "latitude": 39.3,
            "longitude": 110.5,
            "macAddress": "02:11:22:33:44:66"
        },
        {
            "id": 2,
            "latitude": 40.2,
            "longitude": 110.1,
            "macAddress": "03:11:22:33:44:66"
        },
        {
            "id": 3,
            "latitude": 41.2,
            "longitude": 111.1,
            "macAddress": "04:11:22:33:44:66"
        }
    ]
    }
    ```


#### API2.4
前端给出传感器id和时间范围，后端返回符合条件的音频列表

#### API2.5
前端给出传感器id和时间范围，后端返回符合条件的mac地址列表
  - URL: http://222.201.137.47:12346/sensor/wifi/list
  - Type: GET
  - Content-type: multipart/form-data
  - 参数: 
    - id: 传感器id
    - startTime: 起始时间,格式类似： 2017-12-11 07:00:00
    - endTime: 终止时间,格式类似 2017-12-11 07:00:00
  - 返回值：
    - 成功：
    
    ```json
    {
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "captureTime": "2017-12-11 07:00:05",
            "fromSensorId": 0,
            "macAddress": "00:11:22:33:44:55:66",
            "intensity": 0
        },
        {
            "captureTime": "2017-12-11 07:00:05",
            "fromSensorId": 0,
            "macAddress": "00:11:22:33:44:55:66",
            "intensity": 0
        },
        {
            "captureTime": "2017-12-11 07:00:00",
            "fromSensorId": 0,
            "macAddress": "00:11:22:33:44:55:66",
            "intensity": 0
        },
        {
            "captureTime": "2017-12-11 07:00:05",
            "fromSensorId": 0,
            "macAddress": "00:11:22:33:44:55:66",
            "intensity": 0
        }
    ]
    }
    ```


#### API2.6
前端给出mac地址和时间段，后端返回mac被检索到时所在的传感器id列表

  - URL: http://222.201.137.47:12346/multi/mac2sensor
  - Type: GET
  - Content-type: multipart/form-data
  - 参数: 
    - macAddress: 被捕捉到的手机的MAC地址
    - startTime: 起始时间,格式类似： 2017-03-01 00:00:00
    - endTime: 终止时间,格式类似 2018-04-04 00:00:00
  - 返回值：
    - 成功：
    
    ```json
    {
        "code": 0,
        "msg": "成功",
        "data": [
            {
                "captureTime": "2017-12-11 07:00:05",
                "fromSensorId": 0,
                "macAddress": "00:11:22:33:44:55:66",
                "intensity": 0
            },
            {
                "captureTime": "2017-12-11 07:00:05",
                "fromSensorId": 0,
                "macAddress": "00:11:22:33:44:55:66",
                "intensity": 0
            },
            {
                "captureTime": "2017-12-11 07:00:00",
                "fromSensorId": 0,
                "macAddress": "00:11:22:33:44:55:66",
                "intensity": 0
            },
            {
                "captureTime": "2017-12-11 07:00:05",
                "fromSensorId": 0,
                "macAddress": "00:11:22:33:44:55:66",
                "intensity": 0
            },
            {
                "captureTime": "2018-03-01 07:00:05",
                "fromSensorId": 1,
                "macAddress": "00:11:22:33:44:55:66",
                "intensity": 0
            }
        ]
    }
    ```


#### API2.7
前端给出mac地址和时间段，后端返回相关图片列表，以及捕捉时间，对应的传感器id

  - URL: http://222.201.137.47:12346/multi/mac2vision
  - Type: GET
  - Content-type: multipart/form-data
  - 参数: 
    - macAddress: 被捕捉到的手机的MAC地址
    - startTime: 起始时间,格式类似： 2017-03-01 00:00:00
    - endTime: 终止时间,格式类似 2018-04-04 00:00:00
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
                "imgPath": "http://222.201.145.237:8888//img/rasp-wifi.png",
                "boxes": [
                    [ 0, 302, 221, 73 ],
                    [ 361, 0, 139, 375]
                ]
            },
            {
                "captureTime": "2017-12-11 07:00:00",
                "fromSensorId": 0,
                "imgPath": "http://222.201.145.237:8888//img/rasp-wifi.png",
                "boxes": [
                    [ 0, 302, 221, 73 ],
                    [ 361, 0, 139, 375]
                ]
            },
            {
                "captureTime": "2017-12-11 07:00:00",
                "fromSensorId": 0,
                "imgPath": "http://222.201.145.237:8888//img/rasp-wifi.png",
                "boxes": null
            }
        ]
    }
    ```
    

#### API2.10
前端给出传感器mac地址和时间范围，后端返回符合条件的图片列表，以及图片中行人的位置
  - URL: http://222.201.137.47:12346/vision/list?startTime=2017-12-11 07:00:01&endTime=2017-12-11 07:00:10&macAddress=00:11:22:33:44:55:66
  - Type: GET
  - Content-type: multipart/form-data
  - 参数: 
    - macAddress: mac地址
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
                "captureTime": "2017-12-11 07:00:03",
                "fromSensorId": 1,
                "imgPath": "http://222.201.137.47:12347/img/rasp-wifi.png",
                "boxes": [
                    [ 0, 302, 221, 73 ],
                    [ 361, 0, 139, 375]
                ],
                "macAddress": "00:11:22:33:44:55:66"
            },
            {
                "captureTime": "2017-12-11 07:00:04",
                "fromSensorId": 1,
                "imgPath": "http://222.201.137.47:12347/img/rasp-wifi.png",
                "boxes": [
                    [ 0, 302, 221, 73 ],
                    [ 361, 0, 139, 375]
                ],
                "macAddress": "00:11:22:33:44:55:66"
            },
            {
                "captureTime": "2017-12-11 07:00:03",
                "fromSensorId": 1,
                "imgPath": "http://222.201.137.47:12347/img/rasp-wifi.png",
                "boxes": [
                    [ 0, 302, 221, 73 ],
                    [ 361, 0, 139, 375]
                ],
                "macAddress": "00:11:22:33:44:55:66"
            }
        ]
    }
    ```

#### API2.11
前端给出mac地址，后端返回mac检索到的相关图片列表，以及捕捉时间，对应的传感器id，音频列表


## 3. Machine Learning Reid

#### API3.1
前端给出图片文件或图片url，后端返回图像检索到的相似图片列表(长度为N，可指定)，以及捕捉时间，对应的传感器id，音频列表，mac地址列表

#### API3.2
前端给出图片url或图片文件，以及拍摄时间和传感器ID，后端返回图像+时空融合检索到的相似图片列表，以及捕捉时间，对应的传感器id，音频列表，mac地址列表


## 4. Alert
#### API 4.1
上传预警图像


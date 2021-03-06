# Reid-backend API

## 1. Data Sensor

#### API1.1
后端获取sensor捕捉到的包信息，存入数据库
  - URL: http://222.201.145.237:8081/wifi/record
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
  - URL: http://222.201.145.237:8081/vision/record
  - Type: POST
  - Content-type: application/json
  - 参数: json格式
  
  ```json
  {
	"captureTime": "2017-12-17 16:21:00",
	"fromSensorId": "1",
	"imgUrl": "http://222.201.137.47:12347/img/rasp-wifi.png",
	"boxes": [[0, 1, 2, 3], [2, 3,3,4]]
}
  ```
    - captureTime: 捕获时间，为yyyy-MM-dd hh:mm:ss格式
    - fromSensorId: 传感器id，一个整数，范围[0,2^32)
    - imgUrl: 图片的URL，需要先调用[接口](#前端上传图片文件，后端返回一个URL)来获取，注意同步
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
            "imgUrl": "http://222.201.145.237:8888/img/rasp-wifi.png"
        }
    }
    ```

#### API1.5
前端上传图片文件，后端返回一个URL
  - URL: http://222.201.145.237:8081/file/img
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
#### API1.5.1
前端上传任意文件，后端返回一个URL
  - URL: http://222.201.145.237:8081/file/other
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
 - URL: http://222.201.145.237:8081/sensor/record
  - Type: POST
  - Content-type: application/json
  - 参数: json格式
  
  ```json
  {
	"id": "1",
	"macAddress": "00:11:22:33:44:66",
	"latitude": 40.0,
	"longitude": 110.0,
	"type": "mp4",
	"remoteUrl": "http://222.201.145.237:8081/video/1.mp4",
	"desp": "中大东门口"
  }
  ```
    - macAddress: 传感器上一个无线网卡的mac地址
    - id: 传感器id，一个整数，范围[0,2^32)
    - latitude: 传感器所在纬度
    - longitude: 传感器所在经度
    - type: 传感器类型，字符串，"mp4"类型在前端展示为可播放的视频，"img_seq"类型在前端展示为最新的n张图片轮播
    - desp: 传感器描述，字符串
  - 返回值:
    - 写入成功
    
    ```json
    {
        "code": 0,
        "msg": "成功",
        "data": {
            "id": "1",
            "macAddress": "00:11:22:33:44:66",
            "latitude": 40.0,
            "longitude": 110.0,
            "type": "mp4",
            "remoteUrl": "http://222.201.145.237:8081/video/1.mp4",
            "desp": "中大东门口"
        }
    }
    ```


#### API1.7
获取当前最大图片id

 - URL: http://222.201.145.237:8081/vision/imgCnt
  - Type: GET
  
  - 返回值:
    - 写入成功
    
    ```json
    {
        "code": 0,
        "msg": "成功",
        "data": 49600
    }
    ```
- data即最大图片id
    
#### API1.8
修改单个传感器信息
 - URL: http://222.201.145.237:8081/sensor/modify
  - Type: POST
  - Content-type: application/json
  - 参数: json格式
  
  ```json
  {
	"id": "1",
	"macAddress": "00:11:22:33:44:66",
	"latitude": 40.0,
	"longitude": 110.0,
	"type": "mp4",
	"remoteUrl": "http://222.201.145.237:8081/video/1.mp4",
	"desp": "中大东门口"
  }
  ```
    - macAddress: 传感器上一个无线网卡的mac地址
    - id: 传感器id，一个整数，范围[0,2^32)
    - latitude: 传感器所在纬度
    - longitude: 传感器所在经度
    - type: 传感器类型，字符串，"mp4"类型在前端展示为可播放的视频，"img_seq"类型在前端展示为最新的n张图片轮播
    - desp: 传感器描述，字符串
    - remoteUrl: 传感器资源地址
  - 返回值:
    - 写入成功
    
    ```json
    {
        "code": 0,
        "msg": "成功",
        "data": {
            "id": "1",
            "macAddress": "00:11:22:33:44:66",
            "latitude": 40.0,
            "longitude": 110.0,
            "type": "mp4",
            "remoteUrl": "http://222.201.145.237:8081/video/1.mp4",
            "desp": "中大东门口"
        }
    }
    ```

#### API1.9 删除单个传感器
- URL: http://222.201.145.237:8081/sensor/delete?id=1
- Type: POST
- Content-type: multi-part/form-data
- 参数: 
  - id: 传感器id

- 返回值:
- 写入成功

```json
{
    "code": 0,
    "msg": "成功",
    "data": {}
}
```

#### API1.10 查询某类传感器
- URL: http://222.201.145.237:8081/sensor/list/type?type=mp4
- Type: POST
- Content-type: multi-part/form-data
- 参数: 
  - type: 传感器类型，字符串，如mp4, img_seq

- 返回值:
- 写入成功

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "id": 0,
            "latitude": 40.5,
            "longitude": 110.7,
            "macAddress": "00:11:22:33:44:66",
            "type": "mp4",
            "remoteUrl": "http://222.201.145.237:8081/video/1.mp4",
            "desp": "中大东门口"
        },
        {
            "id": 1,
            "latitude": 39.3,
            "longitude": 110.5,
            "macAddress": "02:11:22:33:44:66",
            "type": "mp4",
            "remoteUrl": "http://222.201.145.237:8081/video/2.mp4",
            "desp": "中大东门口"
        },
        {
            "id": 2,
            "latitude": 40.2,
            "longitude": 110.1,
            "remoteUrl": "http://222.201.145.237:8081/video/3.mp4",  
            "macAddress": "03:11:22:33:44:66",
            "type": "mp4",
            "desp": "中大东门口"
        },
        {
            "id": 3,
            "latitude": 41.2,
            "longitude": 111.1,
            "macAddress": "04:11:22:33:44:66",
            "remoteUrl": "http://222.201.145.237:8081/video/1.mp4",
            "type": "mp4",
            "desp": "中大东门口"
        }
    ]
}
```
    
## 2.Web 检索
#### API2.1
前端获取后端的传感器列表
- URL: http://222.201.145.237:8081/sensor/list
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
            "macAddress": "00:11:22:33:44:66",
            "type": "mp4",
            "remoteUrl": "http://222.201.145.237:8081/video/1.mp4",
            "desp": "中大东门口"
        },
        {
            "id": 1,
            "latitude": 39.3,
            "longitude": 110.5,
            "macAddress": "02:11:22:33:44:66",
            "type": "mp4",
            "remoteUrl": "http://222.201.145.237:8081/video/2.mp4",
            "desp": "中大东门口"
        },
        {
            "id": 2,
            "latitude": 40.2,
            "longitude": 110.1,
            "remoteUrl": "http://222.201.145.237:8081/video/3.mp4",  
            "macAddress": "03:11:22:33:44:66",
            "type": "mp4",
            "desp": "中大东门口"
        },
        {
            "id": 3,
            "latitude": 41.2,
            "longitude": 111.1,
            "macAddress": "04:11:22:33:44:66",
            "remoteUrl": "http://222.201.145.237:8081/video/1.mp4",
            "type": "mp4",
            "desp": "中大东门口"
        }
    ]
    }
    ```


#### API2.5
前端给出传感器id和时间范围，后端返回符合条件的mac地址列表
  - URL: http://222.201.145.237:8081/wifi/st2mac
  - Type: GET
  - Content-type: multipart/form-data
  - 参数: 
    - id: 传感器id
    - startTime: 起始时间,格式为： 2017-12-11 07:00:00
    - endTime: 终止时间,格式为 2017-12-11 07:00:00
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
        }
    ]
    }
    ```


#### API2.6
前端给出mac地址和时间段，后端返回mac被检索到时所在的传感器id列表

  - URL: http://222.201.145.237:8081/multi/mac2sensor
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
            }
        ]
    }
    ```


#### API2.7
前端给出mac地址和时间段，后端返回相关图片列表，以及捕捉时间，对应的传感器id（是2.6的升级版）

  - URL: http://222.201.145.237:8081/multi/mac2vision
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
                "imgUrl": "http://222.201.145.237:8888//img/rasp-wifi.png",
                "boxes": [
                    [ 0, 302, 221, 73 ],
                    [ 361, 0, 139, 375]
                ]
            },
            {
                "captureTime": "2017-12-11 07:00:00",
                "fromSensorId": 0,
                "imgUrl": "http://222.201.145.237:8888//img/rasp-wifi.png",
                "boxes": [
                    [ 0, 302, 221, 73 ],
                    [ 361, 0, 139, 375]
                ]
            },
            {
                "captureTime": "2017-12-11 07:00:00",
                "fromSensorId": 0,
                "imgUrl": "http://222.201.145.237:8888//img/rasp-wifi.png",
                "boxes": null
            }
        ]
    }
    ```
    

#### API2.10
前端给出传感器mac地址和时间范围，后端返回符合条件的图片列表，以及图片中行人的位置
  - URL: http://222.201.145.237:8081/vision/st2imgs?startTime=2017-12-11 07:00:01&endTime=2017-12-11 07:00:10&macAddress=00:11:22:33:44:55:66
  - Type: GET
  - Content-type: multipart/form-data
  - 参数: 
    - macAddress: mac地址 // 以后可以考虑修改为传感器id
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
                "imgUrl": "http://222.201.137.47:12347/img/rasp-wifi.png",
                "boxes": [
                    [ 0, 302, 221, 73 ],
                    [ 361, 0, 139, 375]
                ],
                "macAddress": "00:11:22:33:44:55:66"
            },
            {
                "captureTime": "2017-12-11 07:00:04",
                "fromSensorId": 1,
                "imgUrl": "http://222.201.137.47:12347/img/rasp-wifi.png",
                "boxes": [
                    [ 0, 302, 221, 73 ],
                    [ 361, 0, 139, 375]
                ],
                "macAddress": "00:11:22:33:44:55:66"
            },
            {
                "captureTime": "2017-12-11 07:00:03",
                "fromSensorId": 1,
                "imgUrl": "http://222.201.137.47:12347/img/rasp-wifi.png",
                "boxes": [
                    [ 0, 302, 221, 73 ],
                    [ 361, 0, 139, 375]
                ],
                "macAddress": "00:11:22:33:44:55:66"
            }
        ]
    }
    ```


#### API 2.11
前端给出sensorId和数量cnt，返回最近的cnt张图片（可用于img_seq展示）
- URL: http://222.201.145.237:8081/vision/recent_detail?cnt=2&sensorId=10
  - Type: GET
  - Content-type: multipart/form-data
  - 参数: 
    - cnt: 图片数量
    - sensorId: 传感器ID
  - 返回值：
    - 成功：
```json
    {
        "code": 0,
        "msg": "成功",
        "data": [
            {
                "captureTime": "2019-04-13 16:21:00",
                "fromSensorId": 1,
                "imgId": 49604,
                "imgUrl": "http://222.201.145.237:8080//reid/img/1832608662019_03_29_19_02_400.jpg",
                "boxes": [
                    [1,2,3,4],
                    [1,2,3,4]
                ]
            },
            {
                "captureTime": "2019-04-13 16:21:00",
                "fromSensorId": 1,
                "imgId": 49603,
                "imgUrl": "http://222.201.145.237:8080//reid/img/8923144312019_03_29_19_02_400.jpg",
                "boxes": [
                    [1,2,3,4],
                    [1,2,3,4]
                ]
            }
        ]
    }
```

#### API 2.12
前端给出sensorId和数量cnt，返回最近的cnt张图片（可用于img_seq展示）
- URL: http://222.201.145.237:8081/vision/recent?perCamera=20
  - Type: GET
  - Content-type: multipart/form-data
  - 参数: 
    - perCamera: 需要返回的图片数量
  - 返回值：
    - 成功：
    ```json
    {
        "code": 0,
        "msg": "成功",
        "data": [
            {
                "captureTime": "2019-04-13 16:21:00",
                "fromSensorId": 1,
                "imgId": 49604,
                "imgUrl": "http://222.201.145.237:8080//reid/img/1832608662019_03_29_19_02_400.jpg",
                "boxes": [
                    [1,2,3,4],
                    [1,2,3,4]
                ]
            },
            {
                "captureTime": "2019-04-13 16:21:00",
                "fromSensorId": 1,
                "imgId": 49603,
                "imgUrl": "http://222.201.145.237:8080//reid/img/8923144312019_03_29_19_02_400.jpg",
                "boxes": [
                    [1,2,3,4],
                    [1,2,3,4]
                ]
            }
        ]
    }
    ```

## 3. Machine Learning Reid

#### API3.1
前端给出图片url，后端返回图像检索到的相似图片列表(长度为N，可指定)，以及捕捉时间，对应的传感器id，按视觉相似度排序

> 是对[Django API 1.1](https://github.com/ahangchen/deep_reid_backend/blob/master/doc/api.md#api11)的一个封装，将本地地址替换为tomcat虚拟目录对应的URL

- URL: http://222.201.145.237:8081/vision/topn
  - Type: POST
  - Content-type: multipart/form-data
  - 参数: 
    - queryUrl: 查询图片的url，要求已通过API1.5上传到服务器上
  - 返回值：
    - 成功：
    ```json
    {
        "code": 0,
        "msg": "成功",
        "data": [
            {
                "captureTime": "2019-04-13 16:21:00",
                "fromSensorId": 1,
                "imgId": 49604,
                "imgUrl": "http://222.201.145.237:8080//reid/img/1832608662019_03_29_19_02_400.jpg",
                "boxes": [
                    [1,2,3,4],
                    [1,2,3,4]
                ]
            },
            {
                "captureTime": "2019-04-13 16:21:00",
                "fromSensorId": 1,
                "imgId": 49603,
                "imgUrl": "http://222.201.145.237:8080//reid/img/8923144312019_03_29_19_02_400.jpg",
                "boxes": [
                    [1,2,3,4],
                    [1,2,3,4]
                ]
            }
        ]
    }
    ```


#### API3.2
前端给出图片url，以及拍摄时间和传感器ID，后端返回图像+时空融合检索到的相似图片列表，以及捕捉时间，对应的传感器id，按融合相似度排序

> 是对[Django API 2.1](https://github.com/ahangchen/deep_reid_backend/blob/master/doc/api.md#api21)的一个封装，将本地地址替换为tomcat虚拟目录对应的URL

- URL: http://222.201.145.237:8081/vision/fusion_topn
  - Type: POST
  - Content-type: multipart/form-data
  - 参数: 
    - queryUrl: 查询图片的url，要求已通过API1.5上传到服务器上
    - c: 拍摄图片的传感器ID，可通过API2.1获得当前所有传感器列表
    - t: 图片拍摄时间，格式为 yyyy-mm-dd hh:MM:ss
  - 返回值：20张最相似的图片，按照相似度排序
    - 成功：
    ```json
    {
        "code": 0,
        "msg": "成功",
        "data": [
            {
                "captureTime": "2019-04-13 16:21:00",
                "fromSensorId": 1,
                "imgId": 49604,
                "imgUrl": "http://222.201.145.237:8080//reid/img/1832608662019_03_29_19_02_400.jpg",
                "boxes": [
                    [1,2,3,4],
                    [1,2,3,4]
                ]
            },
            {
                "captureTime": "2019-04-13 16:21:00",
                "fromSensorId": 1,
                "imgId": 49603,
                "imgUrl": "http://222.201.145.237:8080//reid/img/8923144312019_03_29_19_02_400.jpg",
                "boxes": [
                    [1,2,3,4],
                    [1,2,3,4]
                ]
            }
        ]
    }
    ```
    
#### API3.3
目标检测，是对Django API3的一个封装，把本地地址替换成tomcat封装的URL

- URL: http://222.201.145.237:8081/vision/detect_query
  - Type: POST
  - Content-type: multipart/form-data
  - 参数: 
    - queryImgUrl: 查询图片的url，要求已通过API1.5上传到服务器上
   
  - 返回值：一张已标记行人的整图，多张单人图片
    - 成功：
    ```json
    {
        "code": 0,
        "msg": "成功",
        "data": 
            {
                "annotate": "http://222.201.145.237:8080//reid/img/detect/queryxxxx.jpg",
                "persons": [
                   "/home/cwh/coding/reid_file_backend/detect/queryxxxx/0.jpg",
                   "/home/cwh/coding/reid_file_backend/detect/queryxxxx/1.jpg",
                   "/home/cwh/coding/reid_file_backend/detect/queryxxxx/2.jpg"
                 ]
            }
        
    }
    ```

## 4. Alert
#### API 4.1
上传预警图像


## 5. Fiber API
#### API5.1
新增单个光纤传感器信息 同API1.6

#### API5.2
新增光纤传感器与摄像头的对应关系

- URL: http://222.201.145.237:8081/fiber/map/record
  - Type: POST
  - Content-type: application/json
  - 参数: json格式
  
```json
{
	"cameraId": 1, // 此区间对应的摄像头ID
	"fiberId": 10, //光纤传感器ID
	"startPos": 0.0, // 区间起始位置
	"endPos": 10.0  // 区间终止位置
}
```

- 返回值
```json
{
    "code": 0,
    "msg": "成功",
    "data": {
        "fiberId": 10,
        "cameraId": 1,
        "startPos": 0,
        "endPos": 10
    }
}
```


#### API 5.3
查询指定光纤传感器对应的所有摄像头与位置

 - URL: http://222.201.145.237:8081/fiber/map/query?fiberId=10
  - Type: GET
  - Content-type: multipart/form-data
  - 参数: 
    - fiberId: 光纤传感器ID
  - 返回值：
  
  ```json
  {
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "fiberId": 10,
            "cameraId": 1,
            "startPos": 0,
            "endPos": 10
        },
        {
            "fiberId": 10,
            "cameraId": 2,
            "startPos": 10,
            "endPos": 20
        }
    ]
  }
```

#### API 5.4
新增光纤传感器捕捉到的事件


- URL: http://222.201.145.237:8081/fiber/event/record
  - Type: POST
  - Content-type: application/json
  - 参数: json格式
  
```json
{
	"captureTime": "2019-03-27 14:59:26", // 事件捕捉时间
	"fromSensorId": 10, // 光纤传感器ID
	"eventType": 1, // 事件类型
	"position": 10.0, // 事件位置（在光纤上的哪个位置）
	"confidence": 0.5 // 事件类型的置信度
}
```

- 返回值
```json
{
    "code": 0,
    "msg": "成功",
    "data": {
        "captureTime": "2019-03-27 14:59:26",
        "fromSensorId": 10,
        "position": 10,
        "eventType": 1,
        "confidence": 0.5
    }
}
```

#### API 5.3
查询指定光纤传感器在某段时间之间捕捉到的事件列表

 - URL: http://222.201.145.237:8081/fiber/event/query?fiberId=10&start=2019-03-27 14:00:26&end=2019-03-27 15:30:26
  - Type: GET
  - Content-type: multipart/form-data
  - 参数: 
    - fiberId: 光纤传感器ID
    - start: 起始时间
    - end: 结束时间
  - 返回值：
  
  ```json
  {
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "captureTime": "2019-03-27 14:59:26",
            "fromSensorId": 10,
            "position": 10,
            "eventType": 1,
            "confidence": 0.5
        },
        {
            "captureTime": "2019-03-27 14:59:26",
            "fromSensorId": 10,
            "position": 10,
            "eventType": 1,
            "confidence": 0.5
        }
    ]
}
```

# LicenseDemo

#### 项目介绍
在基于Spring的项目中使用 `TrueLicense `生成和验证`License证书`（服务器许可）的示例代码

#### 技术依赖：
* `Spring Boot`：项目基础架构
* `TrueLicense `：基于`Java`实现的生成和验证服务器许可的简单框架

#### 环境依赖：
* `JDK8+`

#### 两个子项目说明： ####

- `ServerDemo`：用于**开发者**给客户生成`License证书`的示例代码
- `ClientDemo`：**模拟需要给客户部署的业务项目**

#### ServerDemo项目： ####

对外发布了两个RESTful接口：

（1）获取服务器硬件信息 ：

请求地址：`http://127.0.0.1:7000/license/getServerInfos`

（2）生成证书 ：

请求地址：`http://127.0.0.1:7000/license/generateLicense`

请求时需要在Header中添加一个 **Content-Type** ，其值为：**application/json;charset=UTF-8**。请求参数如下： 

```json
{
	"subject": "license_demo",
	"privateAlias": "privateKey",
	"keyPass": "private_password1234",
	"storePass": "public_password1234",
    	"licensePath": "license_test/license.lic",
	"privateKeysStorePath": "/Desktop/license_demo/privateKeys.keystore",
	"issuedTime": "2023-02-10 01:30:01",
	"expiryTime": "2023-04-24 11:10:19",
	"consumerType": "User",
	"consumerAmount": 1,
	"description": "这是证书描述信息",
	"licenseCheckModel": {
		"ipAddress": [ ""],
		"macAddress": [""],
		"cpuSerial": "",
		"mainBoardSerial": "",
		"saltVerify": "1198895aad7d1b31cb2de760d831958b"
	}
}
```

#### ClientDemo项目： ####

项目启动时安装证书，通过`com/license/LicenseCheckListener.java`类实现。用户登录时校验证书的可用性，通过`com/license/LicenseCheckInterceptor.java`类实现。


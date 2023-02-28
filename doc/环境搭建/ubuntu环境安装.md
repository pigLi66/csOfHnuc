## Ubuntu 环境安装

### 一、安装Docker
1.先卸载旧版，如果没有的话，就不用执行了，直接第二步。
```shell
sudo apt-get remove docker docker-engine docker.io containerd runc
```
2.更新apt，并安装证书
```shell
sudo apt update
sudo apt-get install ca-certificates curl gnupg lsb-release
sudo curl -fsSL http://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg | sudo apt-key add -
```
3.写入软件源信息
```shell
sudo add-apt-repository "deb [arch=amd64] http://mirrors.aliyun.com/docker-ce/linux/ubuntu $(lsb_release -cs) stable"
```
4.安装docker
```shell
sudo apt-get install docker-ce docker-ce-cli containerd.io
```
5.看看有没有安装成功
```shell
docker version
```

### 二、配置docker镜像加速
1.配置加速文件
在 `/etc/docker/daemon.json`（Linux，没有请新建）文件中加入一下配置
```json
{
  "registry-mirrors": ["https://7qyk8phi.mirror.aliyuncs.com"]
}
```
2.重启docker
```shell
sudo service docker restart
```
3.检查是否成功加速
```shell
sudo docker info
```
如在输出信息中看到以下地址，表示加速成功
```shell
Registry Mirrors:
 https://7qyk8phi.mirror.aliyuncs.com/
```

### 三、下载docker-compose
docker-compose可以帮我们统一配置和统一管理多个docker容器
1.下载docker-compose
```shell
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
```
2.给二进制文件执行权限
```shell
sudo chmod +x /usr/local/bin/docker-compose
```
3.#测试安装
若有docker-compose version 1.29.2, build 5becea4c，则安装成功
```shell
docker-compose --version
```

### 打包中间件到服务器
1.本项目所有的 `127.0.0.1` ，按需替换为服务器ip
```shell
# IDEA 快捷键
shift+ctrl+F
```
2.将 `环境搭建` 文件夹下的文件，上传到ubuntu的 `/root/docker/`下，并进行授权
```shell
chmod -R 777 ./rocketmq/broker/logs
chmod -R 777 ./rocketmq/broker/store
chmod -R 666 ./minio/data
chmod -R 777 ./elasticsearch/data
```

### 下载中间服务镜像
启动docker-compose, 会自动进行下载，大约7个g数据
```shell
docker-compose up -d --build
```

### 各服务访问路径及密码
请自行127.0.0.1替换为服务器ip

- mysql: 使用数据库连接工具进行连接， 端口3306 账号及密码 root/root
- minio: http://127.0.0.1:9000 账号及密码 admin/admin123456
- redis: 端口6379，没有密码
- nacos: http://127.0.0.1:8848/nacos 账号及密码 nacos/nacos
- kibana: http://127.0.0.1:5601
- rocketmq-console：http://127.0.0.1:8180



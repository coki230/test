/*
前端项目配置外部化工具(config-center-sdk-go), 一下简称 sdk

前端项目目前会有一些配置文件，配置了后端项目的地址，比如音视频项目：

static/global.json
{ "AJAX_BASE_URL": "http://10.255.228.233/cvf/", "supportWebRTC": true }

static/kmedia/kmedia-service.js
window.Kservice = {
serverUrl:"http://10.255.228.236",
rtcUrl:"http://10.255.228.252:3002"
};

配置流转流程：
配置中心  ->    k8s-cluster -> nginx container

sdk 会从config-map拉取配置信息，并成成相应的配置文件

*/

package main

import (
	"fmt"
	"io/ioutil"
	metav1 "k8s.io/apimachinery/pkg/apis/meta/v1"
	"k8s.io/client-go/kubernetes"
	"k8s.io/client-go/rest"
	"os"
	"strings"
	"time"
)

func main() {
	projectName := os.Getenv("PROJECT_NAME")
	configNameSpace := os.Getenv("CONFIG_NS")
	configHotReload := os.Getenv("CONFIG_HOT_RELOAD")

	if len(configNameSpace) == 0 {
		fmt.Println("configNameSpace is null, ingore")
		return
	}

	if len(projectName) == 0 {
		fmt.Println("projectName is null, ingore")
		return
	}

	// creates the in-cluster config
	config, err := rest.InClusterConfig()
	if err != nil {
		panic(err.Error())
	}
	// creates the clientset
	clientset, err := kubernetes.NewForConfig(config)

	if err != nil {
		panic(err.Error())
	}

	for {
		//15 second generate config file
		doGenerate(projectName, clientset, configNameSpace)
		if "false" == configHotReload {
			break
		}
		time.Sleep(15 * time.Second)
	}
}

func doGenerate(projectName string, clientset *kubernetes.Clientset, configNameSpace string) {

	configMapName := fmt.Sprintf("project-%s", projectName)

	configMaps, err := clientset.CoreV1().ConfigMaps(configNameSpace).Get(configMapName, metav1.GetOptions{})
	if err != nil {
		fmt.Println(err.Error())
		return
	}

	if configMaps == nil {
		fmt.Println("config map not found, ignored")
		return
	}

	var fileInfo map[string]string
	fileInfo = make(map[string]string)

	for k, v := range configMaps.Data {
		startsWith := strings.HasPrefix(k, "_gen_")
		endsWith := strings.HasSuffix(k, "_file_")
		if !startsWith {
			continue
		}
		if !endsWith {
			continue
		}

		fileName := k[5 : len(k)-6]

		dataKey := fmt.Sprintf("_gen_%s_data_", fileName)

		fmt.Println(v)

		fileInfo[v] = configMaps.Data[dataKey]
	}

	appRootPath := os.Getenv("APP_ROOT_PATH")

	fmt.Println(appRootPath)

	for k, v := range fileInfo {
		targetFile := fmt.Sprintf("%s%s", appRootPath, k)
		mkdis4Parent(targetFile)
		f, _ := os.Create(targetFile)
		fmt.Println(targetFile)
		defer f.Close()
		f.WriteString(v)
		f.Sync()
	}
}

func mkdis4Parent(filePath string) {
	lastIndex := strings.LastIndex(filePath, "/")
	if -1 == lastIndex {
		return
	}
	parent := filePath[0:lastIndex]
	os.MkdirAll(parent, 0777)
}

func getAppRootPath() string {
	appRootPath := os.Getenv("APP_ROOT_PATH")
	files, _ := ioutil.ReadDir(appRootPath)
	for _, file := range files {
		if file.IsDir() {
			return fmt.Sprintf("%s%s/", appRootPath, file.Name())
		}
	}
	return appRootPath
}

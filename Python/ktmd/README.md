# Interactive-image-segmentation-opencv-qt V-1.0



## 使用opencv进行交互式抠图
这是一个简单的交互式图像分割应用程序编写的python opencv和pyqt。

这个应用程序在opencv的抓拍算法抠图。Grabcut是Graphcut算法的改进版本。查看这些论文([paper1](http://www.cs.cornell.edu/~rdz/Papers/BVZ-pami01-final.pdf)， [paper2](http://www.csd.uwo.ca/~yuri/Papers/iccv01.pdf))的详细信息~~

gui部分主要来自 [labelImg ](https://github.com/tzutalin/labelImg)工具。

## Requirement:

- Ubuntu 16.04
- python3
- pyqt5
- cv2

## Usage:
python app.py

## Result:

![img](https://img2020.cnblogs.com/blog/2283915/202105/2283915-20210504150206444-1702272211.gif)

### input:
![img](https://img2020.cnblogs.com/blog/2283915/202105/2283915-20210504150012563-292943760.jpg)

### output:
![img](https://img2020.cnblogs.com/blog/2283915/202105/2283915-20210504150028333-694595814.png)


## TODO:
- 自动调整图像在主窗口根据鼠标移动
- 镜像项切换
-  Rect编辑和自动消光
- 每一个选择只显示一个Rect




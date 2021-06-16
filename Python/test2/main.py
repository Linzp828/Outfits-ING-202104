import os
import sys
import numpy as np
import cv2
from grab_cut import Grab_cut
from concurrent.futures import ThreadPoolExecutor
import threading
from PyQt5.QtGui import *
from PyQt5.QtCore import *
from PyQt5.QtWidgets import *

class MainWindow():

    def __init__(self, defaultFilename=None):
        self.image_out_np = None
        self.mattingFile = None

    def grabcutMatting(self,filePath):

        if self.mattingFile is None:
            self.mattingFile = Grab_cut()

        def format_shape(s):
            return dict(line_color=s.line_color.getRgb(),
                        fill_color=s.fill_color.getRgb(),
                        points=[(p.x(), p.y()) for p in s.points])

        self.image_out_np = self.mattingFile.image_matting(filePath,10)

        self.showResultImg(self.image_out_np,filePath)


    def showResultImg(self, image_np, filePath):
        factor = 1
        image_np = cv2.resize(image_np, None, fx=factor,
                              fy=factor, interpolation=cv2.INTER_AREA)
        # image_np = cv2.resize((self.pic.height(), self.pic.width()))
        image = QImage(image_np, image_np.shape[1],
                       image_np.shape[0], QImage.Format_ARGB32)

        if os.path.exists(filePath):
            os.remove(filePath)
            print('成功删除文件:', filePath)
        else:
            print('未找到此文件:', filePath)

        position = os.path.splitext(filePath)
        cv2.imwrite(position[0]+'.png', image_np)
        matImg = QPixmap(image)
        self.pic.setPixmap(matImg)
        return matImg

    def saveFile(self, saved_path):
        Grab_cut.resultSave(saved_path, self.image_out_np)

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    mainWindow = MainWindow()
    mainWindow.grabcutMatting(sys.argv[1])
    # mainWindow.grabcutMatting("C:/Users/33722/Desktop/2.png")





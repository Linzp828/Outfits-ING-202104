# from xpinyin import Pinyin
# test = Pinyin()
# result = test.get_pinyin('福州')
# print(result)

import sys
from snownlp import SnowNLP

def transfomer(location):
    s = SnowNLP(location)
    result = ''.join([i for i in s.pinyin])
    print(result[:-3])
    return (result[:-3])

if __name__ == "__main__":
    transfomer(sys.argv[1])
# s = SnowNLP('黑龙江市')
# # print(s.pinyin)
# result = ''.join([i for i in s.pinyin])
# # print(type(result))
# # print(result)
# print(result[:-3])
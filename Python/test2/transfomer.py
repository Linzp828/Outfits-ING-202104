# from xpinyin import Pinyin
# test = Pinyin()
# result = test.get_pinyin('福州')
# print(result)

from snownlp import SnowNLP

s = SnowNLP('福州市')
# print(s.pinyin)
result = ''.join([i for i in s.pinyin])
# print(type(result))
# print(result)
print(result[:-3])
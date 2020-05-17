   实现调用其他接口
   其中test-project为测试的项目 测试的接口为企业微信API 网址：https://open.work.weixin.qq.com/api/doc/90000/90135/91039 可以找到
   调用接口入参出参均默认为json字符串
   测试类：HttpRequestUtil 为具体的网络请求接口，可修改
   测试类：MyRequestClient 为网络调用的入参
   测试类: TestMethodHandler为调用方法，这个地方去设置自己的具体的调用网络请求的步骤

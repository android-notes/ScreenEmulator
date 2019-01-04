## 低密度大屏模拟手机显示效果  Beta1.0

* 拦截Activity创建流程，替换overrideConfig.densityDpi实现修改density -->ActivityCreateCompact

* 拦截window创建，缩放view（各种屏幕像素密度不同，即使都是1080px*1920px实际的显示尺寸也不同，需要缩放实现） -->WindowRootViewCompat

只支持Activity，暂未处理dialog等

[效果视频 http://t.cn/EG4gFDz?m=4324726819779014&u=3099387360](http://t.cn/EG4gFDz?m=4324726819779014&u=3099387360)
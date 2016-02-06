# WTP
公共地标插件

http://www.mcbbs.net/thread-552042-1-1.html

## Download/下载:

暂未发布

## 命令:

### [ 基本 ]

/wtp - 打开帮助菜单

/wlist  - 查看所有地标

/w <地标名>  - 传送到公共地标

### [ 创建以及设置 ]

/wtp create <地标名>  - 花费一定金钱设置一个公共地标

/wtp alias <地标名> <新地标名>  - 花费一定金钱重新设置一个公共地标的名字

/wtp msg <地标名> <欢迎消息> - 设置公共地标的欢迎消息

/wtp relocate <地标名>  - 花费一定金钱重新设置一个公共地标的地点

/wtp delete <地标名>  - 移除一个公共地标

之后添加：

/wtp owner <地标名> <玩家>  - 设置一个公共地标的新主人

/wtp denyadd <地标名> <玩家>  - 添加一个玩家到公共地标黑名单

/wtp denydel <地标名> <玩家>  - 移除一个玩家到公共地标黑名单

### [ 管理 ]

/wtpadmin info <地标名>   - 查看一个公共地标的信息

/wtpadmin limit <玩家ID> <数量>  - 查看/设置玩家所创建的地标的数量

## 权限:

wtp.control - 允许使用/wtp，默认所有玩家拥有

wtp.teleport - 允许使用/w，默认所有玩家拥有

wtp.list - 允许使用/wlist，默认所有玩家拥有

wtp.teleport - 允许使用管理命令

##配置文件:

`Price:`

_创建地标的价格_

`	create: 300`

_重命名的价格_

`	alias: 200`

_改变地标位置的价格_

`	relocation: 300`

_删除地标的价格（负为退钱）_

`	delete: -50`

_添加地标欢迎信息的价格_

`	welcome: 50`

## 插件定制:

找不到想要的插件？

[联系我](http://www.isotopestudio.cc/minecraft.html)
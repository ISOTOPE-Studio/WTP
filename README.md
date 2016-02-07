# WTP
公共地标插件

http://www.mcbbs.net/thread-552042-1-1.html

## Download/下载:

https://github.com/ISOTOPE-Studio/WTP/releases

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

/wtp about  - 查看插件信息

#### 还未添加：

/wtp delete <地标名>  - 移除一个公共地标

/wtp owner <地标名> <玩家>  - 设置一个公共地标的新主人

/wtp denyadd <地标名> <玩家>  - 添加一个玩家到公共地标黑名单

/wtp denydel <地标名> <玩家>  - 移除一个玩家到公共地标黑名单

### [ 管理 ]

/wtpadmin player <玩家>   - 查看一个玩家的地标信息

/wtpadmin warp <地标名>   - 查看一个公共地标的信息

/wtpadmin delete <地标名>   - 删除一个公共地标

/wtpadmin about  - 查看插件信息

## 权限:

wtp.control - 允许使用/wtp

wtp.teleport - 允许使用/w

wtp.admin - 允许使用管理命令

#### 还未添加：

wtp.list - 允许使用/wlist

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

`Limitation:`

_每个玩家可创建的地标的个数_

_默认: 无需权限_

_VIP权限: WTP.create.VIP1, WTP.create.VIP2, 以此类推_

_目前只提供五个VIP权限_

_-1为无限制_

`  create: `

`    default: 2`

`    VIP1: 5`

`    VIP2: 8`

`    VIP3: 10`

`    VIP4: 20`

`    admin: -1`

## 插件定制:

找不到想要的插件？

[联系我](http://www.isotopestudio.cc/minecraft.html)
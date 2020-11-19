使用java问题定位工具:

-jps: jvm进程状态工具，列出系统上的JVM进程
-jinfo: JVM信息查看工具，查看JVM的各种配置信息
-jvisualvm: 图形化界面，综合的JVM监控工具，查看JVM基本状况、栈、堆、内存、CPU
-jconsole: 图形界面： jmx兼容图形工具，监控jvm基本状况，查看MBean
-jstat: 命令行，JVM统计工具，附加到一个JVM进程上搜集和记录JVM的各种性能指标数据
-jstack: 命令行，JVM栈查看工具，可以打印JVM集成的线程栈和锁的情况
  https://fastthread.io/ft-index.jsp 在线分析线程栈
-jcmd: 命令行，JVM命令行调试工具，用于向JVM发送调试命令
-jmap：命令行，JVM堆内存分析工具，可以打印JVM进程的直方图、类加载统计


## 设置JVM参数，配置堆和内部区域的大小

# 最大堆体积
-Xmx value

#初始的最小的堆体积
-Xms value

# 老年代和新生代的比例,默认这个值是2，意味着老年代是新生代的两倍，也就是新生代是堆内存的1/3
-XX:NewRatio=value

# 直接设置新生代的大小
-XX:NewSize=value

# Eden和Survivor的大小，如果是8，标示Survivor区域是Eden的1/8
-XX:SurvivorRatio=value


# JVM内存布局

![JAVA堆内存布局概念](./jvm_heap_layout.png)
新生代: 大部分对象创建销毁的区域。Eden区域，作为对象初始分配的区域；两个Survivor区域，有时也叫做from、to区域，用来保存Minor GC中保留下来的对象。JVM会随意选取一个Survivor区域作为“to”，然后会在GC过程中进行区域间拷贝，也就是将Eden中存活下来的对象和from区域的对象，拷贝到to区域。
老年代: 放常生命周期的对象，通常是从Surivor区域拷贝过来的对象。通常普通对象会在Eden的TLAB上分配，当对象较大时，JVM试图直接分配在Eden其他的位置，如果对象太大，完全无法在新生代找到足够的连续空闲空间，JVM会直接分配到老年代。
永久代：存储JAVA类元数据，常量池，Intern字符串缓存。

JVM内存区域，有的区域以线程为单位，有的区域是整个JVM进程唯一的。
程序计数器:每个线程都有自己的程序计数器，任何时候一个线程都只有一个方法在执行。程序计数器会保存当前正在执行JAVA方法的JVM指令地址
JAVA虚拟机栈: 每个线程创建时都会创建虚拟机栈，内部保存一个个栈帧，对应一次次的方法调用。存储局部变量表，操作数栈，动态链接、方法正常退出或异常退出的定义等。
堆: JAVA内存管理的核心，用来放对象实例，几乎所有的JAVA对象实例都是直接分配在堆上的。堆被所有的线程共享，在虚拟机启动时，我们定义"Xmx"之类的参数控制堆空间指标。
方法区: 所有线程共享的一块内存区域，用于存放元数据(meta data)。例如类结构信息，运行时常量池、字段、方法代码。JAVA8把老年代移除，同时加入了元数据区(metaspace)
本地方法栈: 和JAVA虚拟栈相似，支持堆本地方法的调用，也是每一个线程会创建一个。
![](./java_memory_layout.png)

# JAVA垃圾回收机制

#常见垃圾收集器:
Serial GC："Serial"的体现在收集工作是单线程的，并且在进行垃圾收集过程中，会进入“Stop-The-World”状态。优点是单线程实现精简，一般Client模式下JVM的默认选项。
    -XX:+UseSerialGC
ParNew GC: 他是SerialGC的多线程版本
    -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
CMS(Concurrent Mark Sweep) GC,基于标记-清除(Mark-Sweep)算法。优点，较少时间停顿；缺点: 内存碎片化，很难避免长时间运行下发生full GC。
Parrallel GC: Server模式下的默认GC,也被称为吞吐量优先的GC。算法与SerialGC类似，但实现要复杂得多。特点是新生代和老年代GC并行进行。
    -XX:+UseParraelGC
    开发者友好配置项,可以设置暂停时间和吞吐量目标。
    -XX:MaxGcPauseMillis=value
    -XX:GCTimeRatio=N //GC时间和用户时间比例 1/(N+1)

常见垃圾收集算法:
1. 复制， 将活着的复制到to区域，拷贝过程中对象顺序放置，避免内存碎片化。
2. 标记-清除(Mark-Sweep): 先标记要回收的对象，然后清除。不能避免碎片化，导致找不到大内存，出现fullGC暂停时间长。
3. 标记整理(Mark-Compact)：和标记清除相似，但为避免碎片化，清理过程中会将对象移动，以获得连续内存空间。


# 对象实例收集两种基本算法
    引用计数和可达性分析

Java采用可达性分析，JVM会把虚拟机栈和本地方法栈中正在引用的对象、静态属性引用的对象和常量，作为GC Roots。

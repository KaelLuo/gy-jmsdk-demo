package com.gy.jmsdk.jmclient.demo;

import com.gy.jmsdk.JMApplication;

/**
 * <p>
 *     游戏需要继承 {@link #JMApplication} 并将 AndroidManifest.xml 中 application 节点的
 *     android:name 的值设置成此 Application
 * </p>
 * <p>
 *     例如: android:name="com.gy.jmsdk.jmclient.demo.DemoMainActivity"
 * </p>
 * <p>
 *     游戏请务必按如上方式进行集成，切勿因为 JMApplication 看起来是空实现而忽略这一步。
 *     切勿采用拷贝方法的形式将 JMApplication 中的方法拷贝出来。
 * </p>
 *
 */
public class DemoApplication extends JMApplication {
}

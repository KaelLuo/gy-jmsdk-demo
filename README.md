# JMSDK 集成示例

- [English Document](./README_EN.md)

## 简介

JMSDK 是一个集成了各种不同渠道SDK的聚合SDK， 简单接入后您无需关心各个繁琐的渠道SDK接入细节，而有更充分的精力来打磨游戏产品，细研游戏数值，使您的产品更加出色。

## 导入 JMSDK

JMSDK 本身托管在 jcenter 上，故您只需要参见 app/build.gradle 下 dependencies 设置，引入：

`implementation "com.gy.jmsdk.aars:lib-jmsdk:0.0.1"`

即可。

若出现编译不成功的情况，请在 app build.gradle 下添加

``` buildscript {
        repositories {
            jcenter()
        }
    }
    ```

## JMSDK 接口调用

请参见此示例 DemoApplication 与 DemoaMainActivity 中各个接口的调用
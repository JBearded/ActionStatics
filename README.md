# ActionStatics

## 简介
此项目用来将动态请求生成静态文件，可以将jsp静态化成html，或者将action生成json文件等等。

## 使用
* 根据statics.dtd约束编写xml配置文件
* 实例化StaticManager并运行

xml配置文件

    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE static SYSTEM "statics.dtd">
    <static>
        <config>
            <from>http://www.domain.com</from>
            <to>/data/static/</to>
            <max-thread>20</max-thread>
        </config>
        <schedulers>
            <scheduler delay="5" interval="5" timeout="120" name="html_scheduler">
                <job action="/index.action" file="index.html"></job>
            </scheduler>
            <scheduler delay="10" interval="10" timeout="120" name="json_scheduler">
                <job action="/page.action?page=1" file="page_1.json"></job>
                <job action="/page.action?page=2" file="page_2.json"></job>
            </scheduler>
        </schedulers>
    </static>


代码运行

    StaticManager sm = new StaticManager("static.xml");
    sm.run();
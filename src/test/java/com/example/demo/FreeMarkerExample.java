package com.example.demo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.javatuples.Triplet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerExample {
    public static void main(String[] args) {
        // 创建FreeMarker配置实例
        Configuration cfg = new Configuration(new Version("2.3.31"));
        cfg.setDefaultEncoding("utf-8");

        try {
            // 设置模板文件所在的目录
            Resource resource = new ClassPathResource("templates");
            cfg.setDirectoryForTemplateLoading(resource.getFile());

            // 创建数据模型
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("name", "张三");
            templateData.put("age", 25);

            Triplet<Level, String, Integer> student = Triplet.with(Level.ERROR, "张三", 25);

            templateData.put("stu", student);
            //templateData.put("map", Map.of("name", "李四", "age", "18"));

            templateData.put("map", MapValues.of("level", "name", "age").with(Level.INFO, "张三", 21));

            // 加载模板文件
            Template template = cfg.getTemplate("example.ftl");

            // 创建输出文件的Writer
            //Writer out = new FileWriter(new File("output.txt"));
            Writer out = new StringWriter();

            // 合并模板和数据模型
            template.process(templateData, out);
            // 打印到控制台
            System.out.println(out.toString());
            // 关闭Writer
            out.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}

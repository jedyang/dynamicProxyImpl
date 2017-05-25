package com.yunsheng;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Locale;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;

/**
 * 模拟动态代理
 */
public class MyProxy {
    public static Object newProxyInstance(Class intf, Object target) throws Exception {

        String interfaceName = intf.getName();

        String methodStr = "";

        for (Method m : intf.getMethods()){
            methodStr += "    @Override\n"
                + "    public void " + m.getName() + "() {\n"
                + "        System.out.println(\"before...\");\n"
                + "\n"
                + "        m." + m.getName() + "();\n"
                + "\n"
                + "        System.out.println(\"after...\");\n"
                + "    }\n";
        }

        String code = "package com.yunsheng;\n"
            + "\n"
            + "public class $Proxy0 implements " + interfaceName +"{\n"
            + "\n"
            + "    private  " + interfaceName +" m;\n"
            + "\n"
            + "    public $Proxy0( " + interfaceName +" m) {\n"
            + "        this.m = m;\n"
            + "    }\n"
            + "\n"
            + methodStr
            + "}";

        // 注意：用的ide是idea。eclipse的路径不一样
        String filePath = System.getProperty("user.dir") + "/target/classes/com/yunsheng/$Proxy0.java";
        System.out.println(filePath);
        File codeFile = new File(filePath);
        FileUtils.writeStringToFile(codeFile, code);

        // --------进行编译----------
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(null, Locale.ENGLISH,
            Charset.defaultCharset());
        // 得到java集
        Iterable<? extends JavaFileObject> iterable = fileManager.getJavaFileObjects(filePath);

        // 编译任务
        CompilationTask javaCompilerTask = javaCompiler.getTask(null, fileManager, null, null, null, iterable);

        javaCompilerTask.call();

        fileManager.close();


        // ------------加载------------
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        Class<?> proxy = systemClassLoader.loadClass("com.yunsheng.$Proxy0");

        // 因为只有带参构造器，需要使用构造器newIntance。
        Constructor constructor = proxy.getConstructor(intf);
        return constructor.newInstance(target);
    }
}

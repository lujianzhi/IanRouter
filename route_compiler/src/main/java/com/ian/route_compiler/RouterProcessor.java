package com.ian.route_compiler;

import com.google.auto.service.AutoService;
import com.ian.route_annotation.Route;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by Ian.Lu on 2018/11/12.
 * Project : IanRouter
 */
@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {

    private Messager mMessager;
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //获取带有Route标注的元素集合
        Set<? extends Element> routeElements = roundEnv.getElementsAnnotatedWith(Route.class);
        //"造类"
        TypeSpec typeSpec = processRouterTable(routeElements);

        if (typeSpec != null) {
            try {
                //把我们造出来的类，放到指定包名下面
                JavaFile.builder("com.ian.ianrouter", typeSpec)
                        .build()
                        .writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
                error(e.getMessage());
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> ret = new HashSet<>();
        ret.add(Route.class.getCanonicalName());
        return ret;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void error(String error) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, error);
    }

    private TypeSpec processRouterTable(Set<? extends Element> elements) {
        if (elements == null || elements.size() == 0) {
            return null;
        }
        //构建一个方法参数，emmmm，就是 HashMap<String,Class> activityMap
        ParameterizedTypeName mapTypeName = ParameterizedTypeName.get(ClassName.get(HashMap.class),
                ClassName.get(String.class), ClassName.get(Class.class));
        ParameterSpec mapParameterSpec = ParameterSpec.builder(mapTypeName, "activityMap").build();
        //构建一个方法，方法名叫initActivityMap,是public static的，参数就是上面创建的参数
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("initActivityMap")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(mapParameterSpec);

        //遍历带注解的元素
        for (Element element : elements) {
            Route route = element.getAnnotation(Route.class);
            String[] routerUrls = route.value();
            //遍历注解的内容
            for (String url : routerUrls) {
                //在方法里写语句，占位符里写数据，TypeElement指的是类元素，我们的注解是加在类上的
                methodBuilder.addStatement("activityMap.put($S, $T.class)", url, ClassName.get((TypeElement) element));
            }
        }
        //生成名叫AutoCreateIanRouterActivityMap的类，是public的，然后有一个我们创建的方法initActivityMap
        return TypeSpec.classBuilder("AutoCreateIanRouterActivityMap")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodBuilder.build())
                .build();
    }
}

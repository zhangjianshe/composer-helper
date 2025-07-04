package cn.cangling.docker.composer.server.gwt;

import cn.cangling.docker.composer.client.version.ICompileInfoProvider;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import lombok.extern.slf4j.Slf4j;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.lang.Times;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class CompileVersionGenerator extends com.google.gwt.core.ext.Generator {
    @Override
    public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
        // 生成代理类的package
        final String genPackageName = ICompileInfoProvider.class.getPackageName();

        // 代理类名称
        final String genClassName = ICompileInfoProvider.class.getSimpleName() + "Impl";


        // 代码生成器工厂类
        ClassSourceFileComposerFactory composer =
                new ClassSourceFileComposerFactory(genPackageName, genClassName);


        // 代理类继承需要代理的接口
        composer.addImplementedInterface(ICompileInfoProvider.class.getCanonicalName());

        // 代理类要引用的类包
        composer.addImport(genPackageName + ".*");
        composer.addImport(Date.class.getCanonicalName());

        // 创建一个源代码生成器对象
        PrintWriter printWriter = context.tryCreate(logger, genPackageName, genClassName);

        if (printWriter != null) {
            // 源代码生成器
            SourceWriter sourceWriter = composer.createSourceWriter(context, printWriter);
            // 生成一个无参数构造函数
            sourceWriter.println(genClassName + "() {");
            sourceWriter.println("}");

            // 输出代码方法
            printFactoryMethod(sourceWriter);

            // 写入磁盘
            sourceWriter.commit(logger);
        }
        //hasGenerator=true;
        System.out.println("compile info generated");
        // 返回生成的代理对象类名称
        return composer.getCreatedClassName();
    }

    private void printFactoryMethod(SourceWriter sourceWriter) {
        log.info("================ compile info generator===========");
        sourceWriter.println("public CompileInfo getCompileInfo(){");
        sourceWriter.println(" CompileInfo data=new CompileInfo();");
        String exec = "git --no-pager  log -n 1 --pretty=format:\"%h%n%cE%n%cI\"";
        String commitHash = "";
        String commitAuthor = "";
        long commitTime = System.currentTimeMillis();

        try {
            StringBuilder stringBuilder = Lang.execOutput(exec);
            String ll = stringBuilder.toString().trim();
            ll = Strings.removeFirst(ll, '"');
            ll = Strings.removeLast(ll, '"');
            BufferedReader reader = new BufferedReader(Streams.utf8r(Lang.ins(ll)));
            List<String> lines = new ArrayList<String>();
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            log.info("invoke git commit with {}", Json.toJson(lines));
            if (lines.size() >= 3) {
                commitHash = lines.get(0).trim();
                commitAuthor = lines.get(1);
                String t = lines.get(2).replaceAll("T", " ");
                commitTime = Times.D(t).getTime();
            } else {
                commitAuthor = "Unknown";
                commitTime = System.currentTimeMillis();
                commitHash = "Unknown";
            }
        } catch (Exception e) {
            e.printStackTrace();
            commitAuthor = "Unknown";
            commitTime = System.currentTimeMillis();
            commitHash = "Unknown";
        }
        sourceWriter.println("\t data.gitTime= new Date(" + commitTime + "L);");
        sourceWriter.println("\t data.gitCommit= \"" + commitHash + "\";");
        sourceWriter.println("\t data.gitAuthor= \"" + commitAuthor + "\";");
        sourceWriter.println("\t data.compileTime= new Date(" + System.currentTimeMillis() + "L);");

        sourceWriter.println(" return data;");
        sourceWriter.println("}");
    }
}

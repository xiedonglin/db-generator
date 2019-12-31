package cn.biikee.db.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class FormatDirective implements TemplateDirectiveModel {
    public static String DIRECTIVE_NAME= "format";
    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        if (params.containsKey("blank")) {
            int spaceSize = Integer.valueOf(params.get("blank").toString()).intValue();
            char[] space = new char[spaceSize];
            Arrays.fill(space, ' ');
            env.getOut().write(space, 0, spaceSize);
        }
        body.render(env.getOut());
    }
    
    private static class FormatWriter extends Writer {
        
        private Writer out;
        
        private char[] space;
        
        private StringBuffer sb = new StringBuffer();

        public String getSb() {
            return sb.toString();
        }
        
        public FormatWriter(Writer out, int column) {
            this.out = out;
            this.space = new char[column];
            Arrays.fill(this.space, ' ');
        }
 
        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            len = 1000;
            out.write(space);
            out.write(cbuf);
        }
 
        @Override
        public void flush() throws IOException {
            out.flush();
        }
 
        @Override
        public void close() throws IOException {
            out.close();
        }
        
    }
}

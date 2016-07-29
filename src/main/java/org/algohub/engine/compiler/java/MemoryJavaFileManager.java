package org.algohub.engine.compiler.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.*;
import javax.tools.JavaFileObject.Kind;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * JavaFileManager that keeps compiled .class bytes in memory.
 */
final class MemoryJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private static final String JAVA_SOURCE_FILE_EXT = ".java";
    private static final Logger LOG = LoggerFactory.getLogger(MemoryJavaFileManager.class);

    private Map<String, byte[]> classBytes;

    MemoryJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
        classBytes = new HashMap<>();
    }

    static URI toUri(String name) {
        try {
            final StringBuilder newUri = new StringBuilder();
            newUri.append("mfm:///");
            newUri.append(name.replace('.', '/'));
            if (name.endsWith(JAVA_SOURCE_FILE_EXT)) {
                newUri.replace(newUri.length() - JAVA_SOURCE_FILE_EXT.length(), newUri.length(), JAVA_SOURCE_FILE_EXT);
            }
            return URI.create(newUri.toString());
        } catch (Exception exp) {
            LOG.error("Memory java file, error", exp);
            return URI.create("mfm:///com/sun/script/java/java_source");
        }
    }

    Map<String, byte[]> getClassBytes() {
        return classBytes;
    }

    @Override
    public void close() throws IOException {
        classBytes = null;
    }

    @Override
    public void flush() throws IOException {
        // nothing to flush, it's in memory operation
    }

    @Override
    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className,
                                               Kind kind, FileObject sibling) throws IOException {
        if (kind == Kind.CLASS) {
            return new ClassOutputBuffer(className);
        } else {
            return super.getJavaFileForOutput(location, className, kind, sibling);
        }
    }

    /**
     * A file object that stores Java bytecode into the classBytes map.
     */
    private class ClassOutputBuffer extends SimpleJavaFileObject {
        private String name;

        ClassOutputBuffer(String name) {
            super(MemoryJavaFileManager.toUri(name), Kind.CLASS);
            this.name = name;
        }

        @Override
        public OutputStream openOutputStream() {
            return new FilterOutputStream(new ByteArrayOutputStream()) {
                @Override
                public void close() throws IOException {
                    out.close();
                    ByteArrayOutputStream bos = (ByteArrayOutputStream) out;
                    classBytes.put(name, bos.toByteArray());
                }
            };
        }
    }
}

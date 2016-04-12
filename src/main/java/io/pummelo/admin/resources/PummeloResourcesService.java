package io.pummelo.admin.resources;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * Created by PummeloDeveloper on 16/4/12.
 */
@Service
public class PummeloResourcesService {

    private JarFile jarFile;

    public InputStream getInputStream(JarEntry entry) throws IOException {
        return jarFile.getInputStream(entry);
    }

    public List<JarEntry> getResources(String path) throws IOException {
        List<JarEntry> entries = new ArrayList<>();
        if (jarFile == null) {
            jarFile = findPummeloJarFile();
        }
        if (jarFile != null) {
            Enumeration<JarEntry> entryEnum = jarFile.entries();
            while (entryEnum.hasMoreElements()) {
                JarEntry entry = entryEnum.nextElement();
                if (entry.getName().startsWith(path)) {
                    entries.add(entry);
                }
            }
        }
        return entries;
    }

    private synchronized JarFile findPummeloJarFile() throws IOException {
        Pattern pummeloURLPattern = Pattern.compile("pummelo-admin-[^/]+.jar$");
        URLClassLoader cl = getURLClassLoader(getClass().getClassLoader());
        while (cl != null) {
            for (URL url : cl.getURLs()) {
                if (url != null && "file".equalsIgnoreCase(url.getProtocol())
                        && pummeloURLPattern.matcher(url.getFile()).find()) {
                    return new JarFile(new File(url.getFile()));
                }
            }
            cl = getURLClassLoader(cl.getParent());
        }
        return null;
    }

    private URLClassLoader getURLClassLoader(ClassLoader cl) {
        if (cl != null && cl instanceof URLClassLoader) {
            return (URLClassLoader) getClass().getClassLoader();
        }
        return null;
    }
}

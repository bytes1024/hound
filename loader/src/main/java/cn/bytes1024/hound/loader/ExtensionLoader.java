package cn.bytes1024.hound.loader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 江浩
 */
@Slf4j
@SuppressWarnings("unchecked")
public class ExtensionLoader<T> {

    private static final String DEFAULT_PLUGIN_KEY = "PLUGIN";

    private static final String SERVICES_DIRECTORY = "META-INF/services/";

    private static final String PLUGINS_DIRECTORY = "META-INF/plugins/";

    private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();
    /**
     * 对应的多个实现
     */
    private final ConcurrentMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();

    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();

    private Class<T> type;

    private ExtensionLoader() {
    }

    private ExtensionLoader(Class<T> type) {
        this.type = type;
        cachedClasses.set(getExtensionClasses());
    }

    @SuppressWarnings("unchecked")
    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Extension type == null");
        }
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type(" + type + ") is not interface!");
        }

        ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        if (loader == null) {
            EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<>(type));
            loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        }
        return loader;
    }

    public T getExtension(String name) {
        synchronized (cachedInstances) {
            Map<String, Class<?>> classes = cachedClasses.get();
            if (classes == null) {
                classes = getExtensionClasses();
            }
            Holder<Object> holder = cachedInstances.getOrDefault(name, null);
            if (holder == null) {
                cachedInstances.putIfAbsent(name, holder = new Holder<>());
                if (classes.containsKey(name)) {
                    try {
                        holder.set(classes.get(name).newInstance());
                    } catch (Exception e) {
                        throw new IllegalStateException("Can not create extension " + type + ", cause: " + e.getMessage(), e);
                    }
                }
            }
            return (T) holder.get();
        }
    }

    private Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classes = cachedClasses.get();
        if (classes == null) {
            synchronized (cachedClasses) {
                classes = cachedClasses.get();
                if (classes == null) {
                    classes = loadExtensionClasses();
                    cachedClasses.set(classes);
                }
            }
        }
        return classes;
    }

    private Map<String, Class<?>> loadExtensionClasses() {
        Map<String, Class<?>> extensionClasses = new HashMap<String, Class<?>>(50);

        loadDirectory(extensionClasses, SERVICES_DIRECTORY, type.getName());
        loadDirectory(extensionClasses, PLUGINS_DIRECTORY, type.getName());

        return extensionClasses;
    }


    private void loadDirectory(Map<String, Class<?>> extensionClasses, String dir, String type) {
        String fileName = dir + type;
        try {
            Enumeration<URL> urls;
            //default
            ClassLoader classLoader = ExtensionLoader.class.getClassLoader();

            if (classLoader != null) {
                urls = classLoader.getResources(fileName);
            } else {
                urls = ClassLoader.getSystemResources(fileName);
            }
            if (urls != null) {
                while (urls.hasMoreElements()) {
                    URL resourceURL = urls.nextElement();
                    loadResource(extensionClasses, classLoader, resourceURL);
                }
            }
        } catch (Throwable t) {
            log.error("加载class {} 错误{}", fileName, t);
        }
    }

    private void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader, URL resourceURL) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceURL.openStream(), "utf-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final int ci = line.indexOf('#');
                if (ci >= 0) {
                    line = line.substring(0, ci);
                }
                line = line.trim();
                if (line.length() > 0) {
                    try {
                        String name = null;
                        int i = line.indexOf('=');
                        if (i > 0) {
                            name = line.substring(0, i).trim();
                            line = line.substring(i + 1).trim();
                        }
                        if (line.length() > 0) {
                            loadClass(extensionClasses, Class.forName(line, true, classLoader), name);
                        }
                    } catch (Throwable t) {
                        throw new IllegalStateException("Failed to load extension class(interface: " + type + ", class line: " + line + ") in " + resourceURL + ", cause: " + t.getMessage(), t);
                    }
                }
            }
        } catch (Throwable t) {
            log.error("加载classFile {}错误 {}", resourceURL, t);
        }
    }


    /**
     * @param extensionClasses :
     * @param clazz            :
     * @param name             :
     * @return : void
     * @author 江浩
     */
    private void loadClass(Map<String, Class<?>> extensionClasses, Class<?> clazz, String name) {
        //同一个class 如果没有定义key那么将只会存在一个
        if (StringUtils.isBlank(name)) {
            name = clazz.getName();
        }
        extensionClasses.putIfAbsent(name, clazz);
    }

    public List<T> getSupportedVExtensions() {
        Collection<T> values = getSupportedKVExtensions().values();
        return new ArrayList<>(values);
    }


    @SuppressWarnings("unchecked")
    public Map<String, T> getSupportedKVExtensions() {
        Map<String, Class<?>> classes = getSupportedExtensionClasses();
        if (classes != null && !classes.isEmpty()) {
            classes
                    .keySet()
                    .stream()
                    .filter(k -> !cachedInstances.containsKey(k)).forEach(k -> getExtension(k));
        }

        Map<String, T> extensions = new HashMap<>(50);
        cachedInstances.entrySet().parallelStream().forEach(e -> {
            try {
                extensions.putIfAbsent(e.getKey(), (T) e.getValue().get());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        return extensions;
    }


    public Map<String, Class<?>> getSupportedExtensionClasses() {
        return cachedClasses.get();
    }

}

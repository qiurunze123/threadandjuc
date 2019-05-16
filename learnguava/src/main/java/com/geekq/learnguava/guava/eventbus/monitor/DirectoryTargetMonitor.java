package com.geekq.learnguava.guava.eventbus.monitor;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;

public class DirectoryTargetMonitor implements TargetMonitor
{

    private final static Logger LOGGER = LoggerFactory.getLogger(DirectoryTargetMonitor.class);

    private WatchService watchService;

    private final EventBus eventBus;

    private final Path path;

    private volatile boolean start = false;

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath)
    {
        this(eventBus, targetPath, "");
    }

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath, final String... morePaths)
    {
        this.eventBus = eventBus;
        this.path = Paths.get(targetPath, morePaths);
    }


    @Override
    public void startMonitor() throws Exception
    {
        this.watchService = FileSystems.getDefault().newWatchService();
        this.path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
        LOGGER.info("The directory [{}] is monitoring... ", path);

        this.start = true;
        while (start)
        {
            WatchKey watchKey = null;
            try
            {
                watchKey = watchService.take();
                watchKey.pollEvents().forEach(event ->
                {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path path = (Path) event.context();
                    Path child = DirectoryTargetMonitor.this.path.resolve(path);
                    eventBus.post(new FileChangeEvent(child, kind));
                });
            } catch (Exception e)
            {
                this.start = false;
            } finally
            {
                if (watchKey != null)
                    watchKey.reset();
            }
        }
    }

    @Override
    public void stopMonitor() throws Exception
    {
        LOGGER.info("The directory [{}] monitor will be stop...", path);
        Thread.currentThread().interrupt();
        this.start = false;
        this.watchService.close();
        LOGGER.info("The directory [{}] monitor will be stop done.", path);
    }
}

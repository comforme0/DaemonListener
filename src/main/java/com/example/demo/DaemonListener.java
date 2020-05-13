package com.example.demo;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DaemonListener implements ServletContextListener, Runnable {
    private Thread thread;
    private boolean isShutdown = false;

    private ServletContext sc;

    public void startDaemon() {
        if (thread == null) {
            thread = new Thread(this, "Daemon thread for background task");
        }

        if (!thread.isAlive()) {
            thread.start();
        }
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();

        while (currentThread == thread && !this.isShutdown) {
            try {
                System.out.println("== DaemonListener is running. ==");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("== DaemonListener end. ==");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("== DaemonListener.contextInitialized has been called. ==");
        sc = sce.getServletContext();
        startDaemon();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("== DaemonListener.contextDestroyed has been called. ==");
        this.isShutdown = true;

        try {
            thread.join();
            thread = null;
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}

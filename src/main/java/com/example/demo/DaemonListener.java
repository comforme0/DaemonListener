package com.example.demo;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DaemonListener implements ServletContextListener, Runnable {
    /* 작업을 수행할 thread */
    private Thread thread;
    private boolean isShutdown = false;

    /* context */
    private ServletContext sc;

    /* 작업을 수행한다. */
    public void startDaemon() {
        if (thread == null) {
            thread = new Thread(this, "Daemon thread for background task");
        }

        if (!thread.isAlive()) {
            thread.start();
        }
    }

    /* 스레드가 실제로 작업하는 부분 */
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

    /* 컨텍스트 초기화 시 데몬 스레드를 작동한다. */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("== DaemonListener.contextInitialized has been called. ==");
        sc = sce.getServletContext();
        startDaemon();
    }

    /* 컨텍스트 종료 시 thread를 종료시킨다. */
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

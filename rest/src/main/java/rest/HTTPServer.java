package rest;

import models.UserExterno;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HTTPServer {
    private final int activeRequestCapacity;
    private final int maxCapacityPer60Seconds;
    private final Object syncObject = new Object();

    private final int port;
    LinkedList<UserExterno> loggedUsers = new LinkedList<>();
    private ServerSocket serverSocket;
    private AtomicInteger acceptedRequests;
    private AtomicInteger rejectedRequests;
    private AtomicInteger activeRequests;
    private Date serverStartDate = new Date();
    private Date[] rateController;
    private int rateControllerIndex = 0;

    HTTPServer(int port, int maxCapacity, int maxThrottlingCapacity) {
        activeRequestCapacity = maxCapacity;
        maxCapacityPer60Seconds = maxThrottlingCapacity;
        rateController = new Date[maxThrottlingCapacity];
        this.port = port;
        acceptedRequests = new AtomicInteger();
        acceptedRequests.set(0);
        rejectedRequests = new AtomicInteger();
        rejectedRequests.set(0);
        activeRequests = new AtomicInteger();
        activeRequests.set(0);
    }

    public void start() {
        Socket socket;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is now running at http://localhost:" + port + "...");
        } catch (IOException e) {
            System.out.println("Server failed to open local port " + port + "!");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            while (true) {
                socket = serverSocket.accept();

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                HTTPMessage request = new HTTPMessage(dataInputStream);
                HTTPRouter thread = new HTTPRouter(this, socket, request);

                String method = request.getMethod();
                String endpoint = request.getURI();
                if (method != "" && endpoint != "") {
                    if (method.equals("GET") && endpoint.equals("/status-processamento")) {
                        thread.setPriority(Thread.MAX_PRIORITY);
                    } else if (method.equals("POST") && endpoint.equals("/submeter-conjunto-pedidos")) {
                        thread.setPriority(Thread.MIN_PRIORITY);
                    }
                } else {
                    continue;
                }

                System.out.println(String.format("%s\t%s\t%d", method, endpoint, thread.getPriority()));

                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean acceptRequest() {
        boolean result = false;

        synchronized (syncObject) {
            if (activeRequests.get() >= activeRequestCapacity) {
                rejectedRequests.incrementAndGet();
            } else {
                int x = rateControllerIndex;
                if (rateController[x] != null && (((new Date()).getTime() - rateController[x].getTime()) / 1000 < 60)) {
                    rejectedRequests.incrementAndGet();
                } else {
                    acceptedRequests.incrementAndGet();
                    activeRequests.incrementAndGet();
                    rateController[x] = new Date();
                    rateControllerIndex = (rateControllerIndex + 1) % maxCapacityPer60Seconds;
                    result = true;
                }
            }
        }

        return result;
    }

    public void terminateRequest() {
        synchronized (syncObject) {
            activeRequests.decrementAndGet();
        }
    }

    public String getServerLoad() {
        synchronized (syncObject) {
            if (activeRequestCapacity - activeRequests.get() == 0) {
                return String.format("The maximum capacity is %d, the current requests are %d, the service isn´t available for new requests", activeRequestCapacity, activeRequests.intValue());
            } else {
                return String.format("The maximum capacity is %d, the current requests are %d, the service is available for new requests", activeRequestCapacity, activeRequests.intValue());
            }
        }
    }


    public List serverReport() {
        synchronized (syncObject) {
            List lst = new ArrayList();
            lst.add(serverStartDate);
            lst.add(acceptedRequests.get());
            lst.add(rejectedRequests.get());
            lst.add(activeRequests.get());
            lst.add(maxCapacityPer60Seconds);
            return lst;
        }
    }

}

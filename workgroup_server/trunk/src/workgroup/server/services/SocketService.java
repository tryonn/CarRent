package workgroup.server.services;

import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import workgroup.server.PlattusServer;
import workgroup.server.util.ConfigProperties;
import workgroup.server.util.ConfigProperty;

public abstract class SocketService extends ThreadedService {

    // Constants
    //
    protected static final String PORT_PROP = "port";
    protected static final String TIMEOUT_PROP = "timeout";
    
    // Internal data
    //
    protected ServerSocket serverSocket;
    
    private ConfigProperty propPort = 
        new ConfigProperty(PORT_PROP, 
                           new Integer(0),
                           "TCP/IP socket to use");
    private ConfigProperty propTimeout = 
        new ConfigProperty(TIMEOUT_PROP, 
                           new Integer(5 * 1000),
                           "Milliseconds before " + 
                           "hanging up on client");
    
    private ConfigProperties configInfo = 
        new ConfigProperties(new ConfigProperty[]
        {
            propTimeout,
            propPort
        });


    /**
     * Constructor, taking the port number on which to listen as the
     * sole argument.
     */
    public SocketService(PlattusServer server, String serviceName, int port)
    {
    	super(server, serviceName);
        setPort(port);
    }


    /**
     * Start the SocketServer
     */
    public void start()
        throws Exception
    {
        setState(STARTING);

        int port = ((Number)propPort.getValue()).intValue();
        int timeout = ((Number)propTimeout.getValue()).intValue();

        // We've GOT to have a port # by now, or we can't create
        // the ServerSocket.
        if (port == 0)
            throw new java.net.ConnectException(
                "SocketServer must have a port argument!");

        getServer().log(
            getClass().getName() + ".start(): " +
            "Opening ServerSocket on port " + port);
        serverSocket = new ServerSocket(port);

        // Configure the ServerSocket so we don't block indefinitely
        // inside of accept()
        try
        {
            serverSocket.setSoTimeout(timeout);
                // Only wait for m_timeout milliseconds before coming back
        }
        catch(SocketException ex)
        {
        	getServer().log(ex);
            return;
        }

        // Set the Runnable instance
        //        
        setRunnable(new SocketServerRunner());
        
        // Call up to the base class (ThreadedServer) to let it do
        // its ancestor thing
        //
        super.start();
    }
    /**
     *
     */
    public void stop()
        throws Exception
    {
        // First call up the chain, to make the Thread (in which
        // we're listening to the ServerSocket) stop.
        super.stop();

        // Close the ServerSocket
        serverSocket.close();
    }

    // pause() and resume() are a little poorly defined here; if
    // we have pause() and resume() close and re-open the socket,
    // respectively, they have no differentiation from start()
    // and stop(). On top of that, ThreadedServer already defines
    // pause() and resume() to pause and resume the Thread, so
    // additional redefinition would seem to be unnecessary
    // here.


    /**
     *
     */
    public ConfigProperties getConfigInfo()
    {
        return configInfo;
    }
    /**
     *
     */
    public void setConfigInfo(ConfigProperties props)
    {
        // We need to do a couple of things here; if the port or
        // the timeout values change, we need to shut down the
        // socket and open it again using the new values
        if (((Number)configInfo.get(PORT_PROP).getValue()).intValue() !=
            ((Number)props.get(PORT_PROP).getValue()).intValue() ||
            ((Number)configInfo.get(TIMEOUT_PROP).getValue()).intValue() != 
            ((Number)props.get(TIMEOUT_PROP).getValue()).intValue())
        {
            try
            {
                // Stop the Service
            	getServer().log("Stopping Service: reconfigure");
                stop();

                // Read the new values
                getServer().log("Re-reading config values");
                configInfo.set(props);

                // Restart the Service
                getServer().log("Restarting Service");
                start();
            }
            catch (Exception ex)
            {
            	getServer().error(ex);
            }
        }
    }


    /**
     * Return the port we accept clients on.
     */    
    public int getPort()
    {
        return ((Integer)propPort.getValue()).intValue();
    }
    /**
     * Set the port number we plan to accept clients on; has no effect
     * after the service is started.
     */
    public void setPort(int newPort)
    {
        propPort.setValue(new Integer(newPort));
    }

    
    /**
     * Derived services must override this method. Once a client has
     * connected to us, this method is called to "do the work" of
     * handling the connection.
     */    
    public abstract void serve(Socket socket)
        throws Exception;
    
	// Nested Runnable class
    class SocketServerRunner
        implements Runnable
    {
        public void run()
        {
            Socket socket = null;

            // Start our (nearly) infinite loop waiting for connection
            // requests from clients
            //
            while (true) //(!Thread.currentThread().isInterrupted())
            {
                try
                {
                    socket = serverSocket.accept();

                    getServer().log(
                        getClass().getName() + ": Socket accepted");
                    
                    // Calling getInetAddress() can cause machines
                    // not on a network to block for up to 15 minutes
                    // due to a "feature" within Microsoft's implementation
                    // of sockets. If your machine is on a TCP/IP network,
                    // comment out the following lines for a bit more
                    // information in the log regarding the connection.
                    //
                    //ServerManager.instance().log(
                    //    getClass().getName() + ":" +
                    //    "Socket accepted from " +
                    //    socket.getInetAddress());

                    // Pass it to the derived class
                    serve(socket);
                    
                    // Dervied class is responsible for closing it, since
                    // if we close it, deriveds won't be able to deal with
                    // the socket in a separate thread, if they so choose
                }
                catch (InterruptedIOException ex)
                {
                    if (shouldStop())
                        break;
                }
                catch (SocketException socketEx)
                {
                	getServer().log(
                        "SocketException thrown from serve() on " +
                        "socket " + socket + ":" + 
                        socketEx.toString());
                    break;
                }
                catch (Throwable t)
                {
                	getServer().log(
                        "Exception thrown from serve() on socket " +
                        socket + ":" + t.toString());
                }
            }
        }
    }
}

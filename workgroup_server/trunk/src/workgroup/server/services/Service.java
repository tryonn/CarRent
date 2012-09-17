package workgroup.server.services;

import workgroup.server.PlattusServer;

public abstract class Service {
	
	private PlattusServer server;
	private String serviceName;
	
	public Service(PlattusServer server, String serviceName) {
		this.server = server;
		this.serviceName = serviceName;
	}
	
	public PlattusServer getServer() {
		return this.server;
	}
	
    /**
     * Start the Service.
     */
    abstract public void start()
        throws Exception;
    
    /**
     * Stop the Service.
     */
    abstract public void stop()
        throws Exception;
    
    /**
     * Pause the Service.
     */
    abstract public void pause()
        throws Exception;
    
    /**
     * Resume the Service.
     */
    abstract public void resume()
        throws Exception;
        
    /**
     * Get the current state of the Service; must be one of the
     * following types: STOPPED, STARTING, RUNNING, STOPPING,
     * PAUSING, PAUSED, or RESUMING.
     */
    abstract public String getState();
    public static final String STOPPED = "STOPPED";
    public static final String STARTING = "STARTING";
    public static final String RUNNING = "RUNNING";
    public static final String STOPPING = "STOPPING";
    public static final String PAUSING = "PAUSING";
    public static final String PAUSED = "PAUSED";
    public static final String RESUMING = "RESUMING";

	/**
	 * @return Returns the serviceName.
	 */
	public String getServiceName() {
		return serviceName;
	}
}

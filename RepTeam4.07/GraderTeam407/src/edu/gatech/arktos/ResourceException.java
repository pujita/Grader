
package edu.gatech.arktos;

public class ResourceException extends Throwable {
    
    public static final int RESOURCE_IMAGE       = 0;
    public static final int RESOURCE_FONT        = 1;
    public static final int RESOURCE_CURSOR      = 2;
    public static final int RESOURCE_LOOKANDFEEL = 3;
    
    private static final String[] typeNames = new String[] {"Image", "Font", "Cursor" };
    
    private int type    = -1;
    private String name = null;
    
    public ResourceException(int resourceType, String resourceName, String errorMessage) {
        super(errorMessage);
        
        type = resourceType;
        name = resourceName;
    }
        
    public ResourceException(int resourceType, String resourceName, Throwable cause) {
        super(cause);
        
        type = resourceType;
        name = resourceName;
    }
    
    public ResourceException(int resourceType, String resourceName, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        
        type = resourceType;
        name = resourceName;
    }
    
    public int getResourceType() {
        return type;
    }
    
    public String getResourceTypeAsString() {
        return typeNames[type];
    }
    
    public String getResourceName() {
        return name;
    }
    
}

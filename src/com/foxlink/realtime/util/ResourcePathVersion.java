package com.foxlink.realtime.util;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

public class ResourcePathVersion implements ServletContextAware {  
    
    private ServletContext servletContext;  
      
    @Override  
    public void setServletContext(ServletContext servletContext) {  
        // TODO Auto-generated method stub  
        this.servletContext=servletContext;  
    }  
    public ServletContext getServletContext() {  
        return servletContext;  
    }  
  
    public void init(){  
        String version = "1.1.2";  
        getServletContext().setAttribute("resourceVersion", version);  
    }  
}  

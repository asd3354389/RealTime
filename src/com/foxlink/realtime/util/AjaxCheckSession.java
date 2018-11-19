package com.foxlink.realtime.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

public class AjaxCheckSession extends HandlerInterceptorAdapter{  
    private static Logger logger=Logger.getLogger(AjaxCheckSession.class);
     // URL辅助工具类  
    private UrlPathHelper urlPathHelper = new UrlPathHelper();  
  
    @Override  
    public void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex)  
            throws Exception {  
        // TODO Auto-generated method stub  
      //  super.afterCompletion(request, response, handler, ex);  
    	 System.out.println("===========HandlerInterceptor1 afterCompletion");  
    }  
  
  /*  @Override  
    public void afterConcurrentHandlingStarted(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {  
        // TODO Auto-generated method stub  
        super.afterConcurrentHandlingStarted(request, response, handler);  
    }  */
  
    @Override  
    public void postHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler,  
            ModelAndView modelAndView) throws Exception {  
        // TODO Auto-generated method stub  
      //  super.postHandle(request, response, handler, modelAndView);  
    	  System.out.println("===========HandlerInterceptor1 postHandle");  
    }  
  
    @Override  
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {  
        String requestCTX = urlPathHelper.getContextPath(request);  
        System.out.println("requestCTX:"+requestCTX);  
        String requestUri = request.getRequestURI(); //请求完整路径，可用于登陆后跳转  
        String contextPath = request.getContextPath();  //项目下完整路径  
        String url = requestUri.substring(contextPath.length()); //请求页面  
        logger.debug("======拦截器配置成功======");  
        logger.debug("======拦截来自："+requestUri+"的请求=======");  
        logger.debug("======拦截的页面路径是："+url+"=======");  
        System.out.println("======拦截器配置成功======");  
        System.out.println("======拦截来自："+requestUri+"的请求=======");  
        System.out.println("======拦截的页面路径是："+url+"=======");  
        //throw new Exception("登录超时!");  
        Object username=request.getSession().getAttribute("username");
          
        if(username == null || username == "anonymousUser"){//如果获取不到登录的session  
            //如果是ajax请求  
            if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){  
            	  System.out.print("session超时,发生ajax请求...");
                response.setHeader("sessionstatus", "timeout"); // 响应头设置session状态       
              //  request.getRequestDispatcher(requestUri+"Login").forward(request, response);//转发到登录界面 
            }    
            else{
                //非ajax请求时，session失效的处理
            	 request.getRequestDispatcher(requestUri+"Login").forward(request, response);//转发到登录界面 
            }
            return false;  //session超时，ajax访问返回false,终止后面拦截器的执行
        }
        else        
        	return super.preHandle(request, response, handler);  //让下一个拦截器去处理  
    }  
      
}  

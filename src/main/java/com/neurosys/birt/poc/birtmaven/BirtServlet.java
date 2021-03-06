
package com.neurosys.birt.poc.birtmaven;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;


/*
 * Copyright Â©2017 Neurosystems Technologies Pvt. Ltd. All Rights reserved. This material 

 * contains confidential and proprietary information of Neurosystems Technologies. 
 * Any disclosure, reproduction, dissemination or distribution of the material 
 * contained herein is strictly prohibited.
*/
/**
 * @author Sandeep Kumar Singh
 *
 */


/* Using annotation, ajax can easily call this servlet */
@WebServlet("/GetReportServlet")
public class BirtServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/* Instantiating Report Engine,  */ 
	private IReportEngine birtReportEngine = null;
	protected static Logger logger = Logger.getLogger( "org.eclipse.birt" );

	public BirtServlet() {
	 super();
	}

	/**
	 * Destruction of the servlet. 

	 */
	public void destroy() {
	 super.destroy(); 
	 BirtFactory.destroyBirtEngine();
	}


	/**
	 * The doGet method of the servlet. 

	 *
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	  throws ServletException, IOException {

	 /** setting content type to response */
	 resp.setContentType("text/html");
	 
	 /** commenting other types for future requirement */
	 //resp.setContentType( "application/pdf" ); 
	 //resp.setHeader ("Content-Disposition","inline; filename=test.pdf");  
	
	 /**
	  *  The report parameters is of type java.lang.String 
	  *  reportName: stores the name of the report
	  */
	 
	 String reportName = req.getParameter("ReportName");
	 String startDate = (String)req.getParameter("startDate");
	 String endDate = (String)req.getParameter("endDate");
	 
	 ArrayList<String> paramNames = new ArrayList<String>();


	 
	 
	 ServletContext sc = req.getSession().getServletContext();
	 
	 /** Instantiating ReportEngine **/
	 this.birtReportEngine = BirtFactory.getBirtEngine(sc);
	 
	 /**
	  * 
	  */
	 HTMLRenderContext renderContext = new HTMLRenderContext();
	 renderContext.setBaseImageURL(req.getContextPath()+"/images");
	 renderContext.setImageDirectory(sc.getRealPath("/images"));
	 
	 logger.log( Level.FINE, "image directory " + sc.getRealPath("/images"));  
	 System.out.println("stdout image directory " + sc.getRealPath("/images"));
	 
	 HashMap<String, HTMLRenderContext> contextMap = new HashMap<String, HTMLRenderContext>();
	 contextMap.put( EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext );
	 
	 IReportRunnable design;
	 try
	 {
	  //Open report design
	  design = birtReportEngine.openReportDesign( sc.getRealPath("/Reports")+"/"+reportName );
	  

	  IGetParameterDefinitionTask taskGetParameters = birtReportEngine.createGetParameterDefinitionTask(design);      
      Collection params = taskGetParameters.getParameterDefns(true);
      
      HashMap<String, String> setParameters = new HashMap<String, String>();
      
      if(params.size()!=0){
	      Iterator iter = params.iterator();	     
	    
	      
	      while (iter.hasNext()) {	             
	          	IParameterDefnBase param = (IParameterDefnBase) iter.next();
	              paramNames.add(param.getName());
	          }
	      
	      setParameters.put(paramNames.get(0).toString(), startDate);
	      setParameters.put(paramNames.get(1).toString(), endDate);
      }
      

	  //create task to run and render report
	  IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask( design );  
	  task.setParameterValues(setParameters);
	  task.setAppContext( contextMap );
	  
	  //set output options
	  HTMLRenderOption options = new HTMLRenderOption();
	  options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_HTML);
	  options.setEmbeddable(true);
	  options.setImageHandler(new HTMLServerImageHandler());
	  options.setOutputStream(resp.getOutputStream());
	  task.setRenderOption(options);
	  
	 
	
	  task.run();
	  	
	  	
	  task.close();
	  
	  
	  
	 }catch (Exception e){
	  
	  e.printStackTrace();
	  throw new ServletException( e );
	 }
	}

	/**
	 * The doPost method of the servlet. 

	 *
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. 

	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
	 BirtFactory.initBirtConfig();
	 
	}

}

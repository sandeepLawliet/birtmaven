package com.neurosys.birt.poc.birtmaven;
 
import java.util.ArrayList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.birt.report.engine.api.ReportEngine;


public class BirtEmbeder {
 
    private IReportEngine engine;
    static int counter = 0;
    

 
    public BirtEmbeder() {
        final EngineConfig config = new EngineConfig();
        engine =  new ReportEngine(config);
        engine.changeLogLevel( Level.WARNING );
    }
 
    public void render(String type) {
    	
    	 ArrayList<String> paramNames = new ArrayList<String>();
    	 String startDate = "2017-03-02";
    	 String endDate = "2017-03-04";
        try{
            //Open the report design
            final IReportRunnable design = engine.openReportDesign("/home/ms/sandbox/birtengine/sales.rptdesign");
            
            IGetParameterDefinitionTask taskGetParameters = engine.createGetParameterDefinitionTask(design);      
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
            
          
            
           
         
            
            //Create task to run and render the report,
            final IRunAndRenderTask task = engine.createRunAndRenderTask(design);
            task.setParameterValues(setParameters);
       
          
            task.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, BirtEmbeder.class.getClassLoader());
 
            final IRenderOption options = new RenderOption();
            options.setOutputFormat(type);
            options.setOutputFileName("output/TestReport." + options.getOutputFormat());
            if( options.getOutputFormat().equalsIgnoreCase("html")){
                final HTMLRenderOption htmlOptions = new HTMLRenderOption( options);
                htmlOptions.setImageDirectory("img");
                htmlOptions.setHtmlPagination(true);
                htmlOptions.setHtmlRtLFlag(false);
                htmlOptions.setEmbeddable(true);
                htmlOptions.setSupportedImageFormats("PNG");
 
                //set this if you want your image source url to be altered
                //If using the setBaseImageURL, make sure to set image handler to HTMLServerImageHandler
                //htmlOptions.setBaseImageURL("http://myhost/prependme?image=");
            }else if( options.getOutputFormat().equalsIgnoreCase("pdf")){
                final PDFRenderOption pdfOptions = new PDFRenderOption( options );
                pdfOptions.getIntOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.FIT_TO_PAGE_SIZE);
                pdfOptions.getIntOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.OUTPUT_TO_MULTIPLE_PAGES);
            }
 
            task.setRenderOption(options);
 
            //run and render report
            task.run();
 
            task.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        final BirtEmbeder embeder = new BirtEmbeder();
        embeder.render("html");
        //embeder.render("pdf");
    }
 
}
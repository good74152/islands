package myservice.mynamespace.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.olingo.commons.api.edmx.EdmxReference;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import myservice.mynamespace.data.Storage;
import myservice.mynamespace.service.DemoEdmProvider;
import myservice.mynamespace.service.DemoEntityCollectionProcessor;
import myservice.mynamespace.service.DemoEntityProcessor;
import myservice.mynamespace.service.DemoPrimitiveProcessor;

public class DemoServlet extends HttpServlet {

	  private static final long serialVersionUID = 1L;
	  private static final Logger LOG = LoggerFactory.getLogger(DemoServlet.class);

	  @Override
	  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    try {
	      HttpSession session = req.getSession(true);
	      Storage storage = (Storage) session.getAttribute(Storage.class.getName());
	      if (storage == null) {
	        storage = new Storage();
	        session.setAttribute(Storage.class.getName(), storage);
	      }

	      // create odata handler and configure it with EdmProvider and Processor
	      OData odata = OData.newInstance();
	      ServiceMetadata edm = odata.createServiceMetadata(new DemoEdmProvider(), new ArrayList<EdmxReference>());
	      ODataHttpHandler handler = odata.createHandler(edm);
	      handler.register(new DemoEntityCollectionProcessor(storage));
	      handler.register(new DemoEntityProcessor(storage));
	      handler.register(new DemoPrimitiveProcessor(storage));

	      // let the handler do the work
	      handler.process(req, resp);
	    } catch (RuntimeException e) {
	      LOG.error("Server Error occurred in DemoServlet", e);
	      throw new ServletException(e);
	    }

	  }

}

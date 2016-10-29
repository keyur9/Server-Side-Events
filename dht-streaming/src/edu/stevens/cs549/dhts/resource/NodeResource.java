package edu.stevens.cs549.dhts.resource;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.media.sse.EventOutput;


@Path("/dht")
public class NodeResource {

	/*
	 * Web service API.
	 * 
	 * TODO: Fill in the missing operations.
	 */

	Logger log = Logger.getLogger(NodeResource.class.getCanonicalName());

	@Context
	UriInfo uriInfo;

	@Context
	HttpHeaders headers;

	@GET
	@Path("info")
	@Produces("application/xml")
	public Response getNodeInfoXML() {
		return new NodeService(headers, uriInfo).getNodeInfo();
	}

	@GET
	@Path("info")
	@Produces("application/json")
	public Response getNodeInfoJSON() {
		return new NodeService(headers, uriInfo).getNodeInfo();
	}

	@GET
	@Path("pred")
	@Produces("application/xml")
	public Response getPred() {
		return new NodeService(headers, uriInfo).getPred();
	}
	
	@GET
	@Path("succ")
	@Produces("application/xml")
	public Response getSucc() {
		return new NodeService(headers, uriInfo).getSucc();
	}
	
	@PUT
	@Path("notify")
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response putNotify(TableRep predDb) {
		return new NodeService(headers, uriInfo).notify(predDb);
	}
	
	@PUT
	@Produces("application/xml")
	public Response setKeyValue(@QueryParam("key") String key, @QueryParam("val") String value) {
		return new NodeService(headers, uriInfo).setKeyValue(key, value);
	}
	
	@GET
	@Path("find")
	@Produces("application/xml")
	public Response findSuccessor(@QueryParam("id") String index) {
		int id = Integer.parseInt(index);
		return new NodeService(headers, uriInfo).findSuccessor(id);
	}
	
	@GET
	@Produces("application/xml")
	public Response getKeyValue(@QueryParam("key") String key) {
		return new NodeService(headers, uriInfo).getKeyValue(key);
	}
	
	@DELETE
	@Produces("application/xml")
	public Response deleteKeyValue(@QueryParam("key") String key, @QueryParam("val") String value) {
		return new NodeService(headers, uriInfo).deleteKeyValue(key, value); 
	}
	
	@GET
	@Path("listen")
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput listenOn(@QueryParam("id") int id, @QueryParam("key") String key) {
		return new NodeService(headers, uriInfo).listen(id, key);
	}

	@DELETE
	@Path("listen")
	public void listenOff(@QueryParam("id") int id, @QueryParam("key") String key) {
		new NodeService(headers, uriInfo).stopListening(id, key);
	}

}

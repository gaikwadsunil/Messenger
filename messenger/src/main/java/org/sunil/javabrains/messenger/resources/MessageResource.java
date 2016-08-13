package org.sunil.javabrains.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.sunil.javabrains.messenger.exception.DataNotFoundException;
import org.sunil.javabrains.messenger.model.Message;
import org.sunil.javabrains.messenger.resources.beans.MessageFilterBean;
import org.sunil.javabrains.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class MessageResource {

	MessageService msgService = new MessageService();

	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
		if (filterBean.getYear() > 0) {
			return msgService.getAllMessagesForYear(filterBean.getYear());
		}

		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return msgService.getMessagesPegination(filterBean.getStart(), filterBean.getSize());
		}
		return msgService.getAllMessages();
	}

	/*
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public List<Message>
	 * getMessages(@QueryParam("year") int year,
	 * 
	 * @QueryParam("start") int start,
	 * 
	 * @QueryParam("size") int size) { if (year>0) { return
	 * msgService.getAllMessagesForYear(year); }
	 * 
	 * if (start>=0 && size>0) { return msgService.getMessagesPegination(start,
	 * size); } return msgService.getAllMessages(); }
	 */

	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Message message = msgService.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		if (message == null) {
			throw new DataNotFoundException("Message with id: " + id + " not found");
		}
		return message;
	}

	@POST	
	public Response addMessage(Message msg, @Context UriInfo uriInfo) {
		Message message = msgService.addMessage(msg);
		String newId = String.valueOf(message.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(message).build();
		// return msgService.addMessage(msg);
	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message msg) {
		msg.setId(id);
		return msgService.updateMessage(msg);
	}

	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		msgService.removeMessage(id);
	}

	@Path("/{messageId}/comments")
	public CommentResource getComments() {
		return new CommentResource();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder().path(MessageResource.class).path(Long.toString(message.getId()))
				.build().toString();
		return uri;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		// TODO Auto-generated method stub
		String uri = uriInfo.getBaseUriBuilder().path(MessageResource.class).path(MessageResource.class, "getComments")
				.path(CommentResource.class).resolveTemplate("messageId", message.getId()).build().toString();
		return uri;
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		// TODO Auto-generated method stub
		String uri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(message.getAuthor()).build()
				.toString();
		return uri;
	}
}

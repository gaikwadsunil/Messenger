package org.sunil.javabrains.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.sunil.javabrains.messenger.model.Comment;
import org.sunil.javabrains.messenger.service.CommentService;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

	CommentService commentService=new CommentService();
	
	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId)
	{
		return commentService.getAllCommnets(messageId);
	}
	
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("messageId") long messageId,@PathParam("commentId") long commentId)
	{
		return commentService.getCommnet(messageId, commentId);
	}
	
	@POST
	public Comment addComment(@PathParam("messageId") long messageId,Comment comment)
	{
		return commentService.addCommnet(messageId, comment);
	}
	
	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("messageId") long messageId,@PathParam("commentId") long commentId,Comment comment)
	{
		comment.setId(commentId);
		return commentService.updateCommnet(messageId, comment);
	}
	
	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("messageId") long messageId,@PathParam("commentId") long commentId)
	{
		commentService.removeCommnet(messageId, commentId);
	}
	
}

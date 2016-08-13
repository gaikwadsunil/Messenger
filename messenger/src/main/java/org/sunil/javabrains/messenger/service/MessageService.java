package org.sunil.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.sunil.javabrains.messenger.database.DatabaseClass;
import org.sunil.javabrains.messenger.model.Comment;
import org.sunil.javabrains.messenger.model.Message;

public class MessageService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();

	public MessageService() {
		
		/*messages.put(1l, new Message(1, "Hello World", "sunil"));
		messages.put(2l, new Message(2, "Hello Jersey", "sunil"));*/
		Comment c1=new Comment(1L, "msg1 comment1", "anil");
		Comment c2=new Comment(2L, "msg1 comment2", "anil");	
		Map<Long, Comment> commentsMap=new HashMap<>();
		commentsMap.put(1l, c1);
		commentsMap.put(2l, c2);
		Message m1=new Message(1, "Hello MSG1", "sunil");
		Message m2=new Message(2, "Hello MSG2", "sunil");
		m1.setComments(commentsMap);
		messages.put(1l,m1);
		messages.put(2l, m2);
	}

	public List<Message> getAllMessages() {
		return new ArrayList<>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messageForYearList=new ArrayList<>();
		Calendar cal=Calendar.getInstance();
		for(Message message:messages.values())
		{
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR)==year) {
				messageForYearList.add(message);
			}
		}
		return messageForYearList;
	}
	
	public List<Message> getMessagesPegination(int start,int size) {
		List<Message> list=new ArrayList<>(messages.values());
		if (list.size()<start+size) {
			return new ArrayList<Message>();
		}
		return list.subList(start, start+size);
	}

	public Message getMessage(long id) {
		return messages.get(id);
	}

	public Message addMessage(Message msg) {
		msg.setId(messages.size() + 1);
		messages.put(msg.getId(), msg);
		return msg;
	}

	public Message updateMessage(Message msg) {
		if (msg.getId() <= 0) {
			return null;
		}
		messages.put(msg.getId(), msg);
		return msg;
	}

	public Message removeMessage(long id) {
		return messages.remove(id);

	}
}
